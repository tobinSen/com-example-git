#!/usr/bin/env bash

if [[ 1 -eq 1 ]]; then
echo '=='
else
    echo '!='
fi

case $1 in
 "1")
    echo "1"
    ;;
  "2")
    echo "2"
    ;;
    *)
    echo "23"
    ;;
esac

for item in item1 item2 item3 ; do
echo item

done

for (( a = 0; a < 10; ++a )); do
    echo a
done
#
#while [[ 1 -lt 10 ]]
#do
#echo'while'
#
#done

demoFun(){
 echo "第一个自定义shell函数 ${1}" # 这里是入参
 return 10
}
demoFun 120
echo "自定义函数返回值 $?"

until true;
do
echo "until";
done
