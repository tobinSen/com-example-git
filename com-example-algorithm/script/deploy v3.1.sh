#!/bin/bash
########################
## sijibao
## deploy shell 
## by sunx
## change  2019-5-9
########################

PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin
. /etc/rc.d/init.d/functions
#shell pid
export TOP_PID=$$


#Data/Time
CTIME=$(date "+%Y-%m-%d-%H-%M")

#Shell ENV
SHELL_NAME="deploy.sh"
SHELL_DIR="/data/jenkins"
PKG_DIR="/data/pkg"
SHELL_LOG="${SHELL_DIR}/logs/${SHELL_NAME}.log"
LOCK_FILE="/tmp/${PJ}.lock"
BUILD_ENV=`cat /data/jenkins/tmp/${PJ}_build_env`
PKG_SERVER=/data/jenkins/tmp/${PJ}_node_env
JK_PKG_DIR=${PKG_DIR}/${BUILD_ENV}/${PJ}
SERVICE_EVN=`cat $PKG_SERVER|egrep "dev|test|prod"|wc -l`
#App ENV
if [ ${SERVICE_EVN} -ne 0 ];then
NODE_PKG_DIR=/wyyt/data/pkg/${PJ}
TOMCAT_APP_DIR=/wyyt/app/${ITEM}
TOMCAT_WEBAPP_DIR=/wyyt/data/autodeploy/tomcat/${PJ}
SERVER_APP_DIR=/wyyt/data/autodeploy/service/${ITEM}
else
NODE_PKG_DIR=${PKG_DIR}/${PJ}
TOMCAT_APP_DIR=/data/pub/${ITEM}
TOMCAT_WEBAPP_DIR=/data/webapps/${PJ}
SERVER_APP_DIR=/data/pub/${ITEM}
fi


shell_log(){
  LOG_INFO=$1
  echo "$CTIME ${SHELL_NAME} ${ITEM}: ${LOG_INFO}" >> ${SHELL_LOG}
}

shell_lock(){
  touch ${LOCK_FILE}
}

shell_unlock(){
  rm -f ${LOCK_FILE}
}

exit_script(){
	shell_unlock&&kill -9  $TOP_PID
}

usage(){
  echo "Usage: $0 [DEPLOY_TYPE ITEM  PJ version] | rollback-list | rollback "
}

build_pkg(){
  echo "build pkg"
  shell_log "Build pkg"
  /bin/sh /data/jenkins/mvn/${PJ}.mvn
  retval=$?
	if [ $retval -eq 0 ];then
		echo "===========构建成功"
	else
		echo "===========构建失败，退出脚本"
		exit_script
	fi
}



get_pkg(){
  echo "get pkg"
  shell_log "Get PKG" 
  PKG_J=`echo ${ITEM}|grep tomcat*|wc -l`
  [ -d ${PKG_DIR}/${BUILD_ENV}/${PJ} ] ||mkdir -p ${PKG_DIR}/${BUILD_ENV}/${PJ}
	if [ ${PKG_J} -eq 1 ];then
		PKG_TYPE=war
		mv /data/jenkins/jenkins-data/workspace/${CB}/target/*${PJ}*.${PKG_TYPE} ${JK_PKG_DIR}/${PJ}-${CTIME}.${PKG_TYPE}
	else
		PKG_TYPE=tar.gz
		mv /data/jenkins/jenkins-data/workspace/${CB}/target/*${PJ}*.${PKG_TYPE} ${JK_PKG_DIR}/${PJ}-${CTIME}.${PKG_TYPE}
	fi
	retval=$?
	if [ $retval -eq 0 ];then
		echo "===========获取构建包成功，重命名为${JK_PKG_DIR}/${PJ}-${CTIME}.${PKG_TYPE}"
	else
		echo "===========获取构建包失败，退出脚本"
		exit_script
	fi
}

scp_pkg(){
  echo "scp pkg"
  shell_log "SCP pkg"
		echo "===================发布包到节点======================"
		ansible -i ${PKG_SERVER} all -m shell -u pub -a "mkdir -p ${NODE_PKG_DIR} >/dev/null 2>&1"
		ansible -i ${PKG_SERVER} all -m copy -u pub -a  "src=${JK_PKG_DIR}/${PJ}-${CTIME}.${PKG_TYPE} dest=${NODE_PKG_DIR}"
  retval=$?
	if [ $retval -eq 0 ];then
		echo "===========传输包到远程节点成功。pub@：${NODE_PKG_DIR}"
	else
		echo "===========传输包到远程节点失败，退出脚本"
		exit_script
	fi
}

deploy_pkg(){
  echo "deploy pkg"
  shell_log "Deploy PKG"
  if [ ${PKG_J} -eq 1 ];then

		echo "=====================部署节点========================="
		echo -e "\n1--------关闭服务"
		ansible -i ${PKG_SERVER} all -m shell -u pub -a "/etc/init.d/${ITEM} stop"
		echo -e "\n2.--------删除软连接${PJ}\n3.--------创建${TOMCAT_APP_DIR}/${PJ}-${CTIME}\n4.--------解压  ${PJ}-${CTIME}.${PKG_TYPE} 到${TOMCAT_WEBAPP_DIR}/${PJ}-${CTIME}\n5.--------创建软连接 ${TOMCAT_WEBAPP_DIR}/${PJ}-${CTIME} ${TOMCAT_APP_DIR}/webapps/${PJ}\n"
		ansible -i ${PKG_SERVER} all -m shell -u pub -a  "rm ${TOMCAT_APP_DIR}/webapps/${PJ} -f&&mkdir -p  ${TOMCAT_WEBAPP_DIR}/${PJ}-${CTIME} &&cd ${NODE_PKG_DIR} && unzip  ${PJ}-${CTIME}.${PKG_TYPE} -d ${TOMCAT_WEBAPP_DIR}/${PJ}-${CTIME} >/dev/null 2>&1 &&  ln -s ${TOMCAT_WEBAPP_DIR}/${PJ}-${CTIME} ${TOMCAT_APP_DIR}/webapps/${PJ}"
		[ $? -eq 0 ] && ansible -i ${PKG_SERVER} all -m shell -u pub -a "/etc/init.d/${ITEM} start"
  else

		echo "=====================部署节点========================="
		echo -e "\n1.--------关闭服务"
		ansible -i ${PKG_SERVER} all -m shell -u pub -a "/etc/init.d/${ITEM} stop"
		echo -e "\n2.--------删除旧${ITEM}\n3.--------解压${PJ}-${CTIME}.${PKG_TYPE}到${SERVER_APP_DIR}"
		ansible -i ${PKG_SERVER} all -m shell -u pub -a "rm ${SERVER_APP_DIR} -rf&&mkdir -p ${SERVER_APP_DIR}&&cd ${NODE_PKG_DIR} &&tar zxf ${PJ}-${CTIME}.${PKG_TYPE} -C ${SERVER_APP_DIR}"
		[ $? -eq 0 ] && ansible -i ${PKG_SERVER} all -m shell -u pub -a "/etc/init.d/${ITEM} start"
  fi
}


rollback(){
  echo "rollback"
  shell_log "rollback"
  PKG_J=`echo ${ITEM}|grep tomcat*|wc -l`
  
  if [ ${PKG_J} -eq 1 ];then
		echo "==========快速回滚到节点============"
		ansible -i ${PKG_SERVER} all -m shell -u pub -a "/etc/init.d/${ITEM} stop"
		ansible -i ${PKG_SERVER} all -m shell -u pub -a "rm /data/pub/${ITEM}/webapps/${PJ} -rf&& ln -s /data/webapps/${PJ}/${DEPLOY_VER} /data/pub/${ITEM}/webapps/${PJ}"
		[ $? -eq 0 ] && ansible -i ${PKG_SERVER} all -m shell -u pub -a "/etc/init.d/${ITEM} start"||shell_unlock exit 
		echo "创建软连接 /data/webapps/${PJ}/${DEPLOY_VER} /data/pub/${ITEM}/webapps/${PJ}"
  else
		echo "===========快速回滚到节点============"
		ansible -i ${PKG_SERVER} all -m shell -u pub -a "/etc/init.d/${ITEM} stop"
		ansible -i ${PKG_SERVER} all -m shell -u pub -a "rm /data/pub/${ITEM} -rf&&mkdir -p /data/pub/${ITEM}&&cd ${PKG_DIR}/${PJ} &&tar zxf  ${DEPLOY_VER}.tar.gz -C /data/pub/${ITEM}"
		[ $? -eq 0 ] && ansible -i ${PKG_SERVER} all -m shell -u pub -a "/etc/init.d/${ITEM} start"||shell_unlock exit 
		echo " 解压 /data/webapps/${ITEM}/${DEPLOY_VER}.tar.gz 到/data/pub/${PJ}"
	fi
}

rollback_list(){
  echo "rollback list"
		echo "=======节点包========="
		ansible -i ${PKG_SERVER} all -m shell -u pub -a "ls -l /data/webapps/${PJ}"
}

test_pkg(){
  echo "Test"
  shell_log "Test"
	ansible -i ${PKG_SERVER} all -m shell -u pub -a "/etc/init.d/${ITEM} status"
}

main(){
  DEPLOY_TYPE=$1
  ITEM=$2
  PJ=$3
  CB=$4
  DEPLOY_VER=$5
  if [ -f "$LOCK_FILE" ];then
     shell_log "${SHELL_NAME} is running"
     echo "===========${SHELL_NAME} is running===========" && exit
  fi
  shell_lock;
  case $DEPLOY_TYPE in
    deploy)
       build_pkg&&get_pkg&&scp_pkg&&deploy_pkg;
       ;;
    rollback)
       rollback;
       ;;
    rollback-list)
       rollback_list;
       ;;
     *)
       usage;
     esac
     shell_unlock;
	 test_pkg;
}

main $1 $2 $3 $4 $5