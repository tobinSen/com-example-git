package com.example.spring.netty.thrift;

/**
 * Thrift ：是一个RPC远程框架，（跨语言，那么肯定有一个语言是兼容多语言中的共性（idl））
 * 是一个cs架构
 *
 * 数据类型：
 *  Thrift 不支持无符号类型，因为很多编程语言不支持无符号语言，比如java
 *  byte: 有符号字节
 *  i16： 16位整数
 *  i32： 32位有符号整数
 *  i64： 64位有符号整数
 *  double：64位浮点数
 *  string：字符串
 *
 *  容器类型：
 *      集合中的元素可以是除了service之外的任务类型，包括exception
 *
 *     list:一系列有T类型
 *     set:
 *     map:
 *
 *  工作原理：
 *      数据传输使用socket(多种语言均支持)，数据再以特定格式(string等)发送，接收方语言进行
 *      定义thrift的文件，由thrift文件(IDL) 生成双方语言的接口、model、在生成的mode以及接口中会有解码编码的代码
 *
 *  Thrift IDL文件
 *      namespace java com.test.thrift.demo
 *      struct  News{
 *          1:i32 id;
 *          2:string title;
 *          3string context;
 *      }
 *
 *      service indexNewsOperatorServices{
 *          bool indexNew(1:NewsModel indexNews),
 *          bool removeNewsById(1:i32 id)
 *      }
 *
 *  结构体(struct)
 *     就像C语言一样，Thrift支持struct类型，目的就是将一些数据聚合在一次，方便传输管理，struct的定义形式如下：
 *     struct People {
 *         1: string name;
 *         2: i32 age;
 *         3: string gender;
 *     }
 *
 *  枚举(enum)
 *      枚举的定义形式和Java的Enum定义类似：
 *      enum Gender{
 *          MALE,
 *          FEMALE
 *      }
 *
 *  异常(exception)
 *      Thrift 支持自定义exception，规则与struct一样
 *      exception  RequestException{
 *          1: i32 code;
 *          2: string reason;
 *      }
 *
 *  服务（service）
 *      Thrift 定义服务相当于Java中创建Interface一样，创建的service经过代码生成命令之后就会生成客户端和服务端的框架代码
 *      定义形式如下：
 *          service HelloWordService {
 *              //service中定义的函数，相当于Java interface中定义的方法
 *              String doAction(1:String name,2:i32 age);
 *          }
 *  类型定义：
 *      Thrift支持类似C++一样的typedef定义：
 *          typedef i32 int
 *          typedef i64 long
 *  常量(const)
 *      thrift 也支持常量的定义，使用const关键字
 *      const i32 MAX_RETRIES_TIME = 10
 *      const String MY_WEBSITE = "http://facebook.com"
 *  命令空间 相当于java中的package
 *      主要目的的是组织代码。thrift使用关键字namespace 定义命令空间
 *           namespace java com.test.thrift.demo
 *      格式：namespace 语言名  路径
 *
 *  文件包含：
 *      Thrift也支持文件包含，相当于C/C++中的include Java中的import 使用关键字include定义：
 *          include "global.thrift"
 *  注释：
 *      #或//开头语句
 *      /*\/注释
 *
 *  可选与必选：
 *      thrift提供了两个关键字required ,optional ,分别用于表示对应的字段是必填的还是可选的
 *      struct People {
 *          1: required String name;
 *          2: optional i32 age;
 *      }
 *
 *   生产代码：
 *
 */
public class ThirftDemo {
}
