#!/bin/bash
#本脚本基本无需改动，注意要点均已用中文说明
#建议使用 . xx.sh 命令执行脚本。如果使用sh xx.sh执行，注意is_exist方法里的注释
#获取脚本名称
SCRIPT=$0
#获取进程名称，必须为完整程序名，否则可能会误操作其他进程
APP_NAME=$1
#获取操作符
OPERATOR=$2

usage() {
    echo "Usage: sh $SCRIPT [app_name] [start|stop|restart|status]"
    exit 1
}

#判断是否输入了两个参数
#注意①
if [ $# != 2 ]; then
    usage
fi

is_exist(){
  #过滤grep命令本身
    #注意②
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  #使用sh xx.sh命令执行的话，启用下面代码
  #pid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v $SCRIPT|awk '{print $2}' `
  if [ -z "${pid}" ]; then
  return 1
  else
    return 0
  fi
}

start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    nohup java -jar $APP_NAME > /dev/null 2>&1 &
}

stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
  else
    echo "${APP_NAME} is not running"
  fi
}

status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

restart(){
  stop
  start
}

case "$OPERATOR" in
  "start")
    start ;;
  "stop")
    stop ;;
  "status")
    status ;;
  "restart")
    restart ;;
  *)
    usage ;;
esac