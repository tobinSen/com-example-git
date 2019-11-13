
/*
*   shrift --gen java .thrift文件
* */

namespace java com.example.spring.netty.thrift
namespace py com.example.spring.netty.thrift

//用别名定义数据类型
typedef i16 short
typedef i32 int
typedef bool boolean
typedef string String

struct Person {
    1: optional String username,
    2: optional int age,
    3: optional boolean married
}

exception DataException {
    1: optional String message,
    2: optional String callStack,
    3: optional String date
}

service PersonService {
    Person getPersionByUsername(1: required String username) throws (1: DataException dataException),

    void savePerson(1: required Person person) throws(1: DataException dataException)
}