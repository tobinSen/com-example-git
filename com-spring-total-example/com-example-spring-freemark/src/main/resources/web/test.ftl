<#--
··· ${}:FreeMarker将会输出真实的值来替换大括号内表达式
    #指令名：#if
 1.基本指令
   if指令：<#if option(对象名.属性名 == 值)></#if> 4点
   list指令：<#list lists as list>
                <$list.name> <$list.age>
             </#list>
             <#list lists>
                <#items as list>
                    ${list}
                </#items>
             </#list>
   include指令：<#include "/copyright_footer.html">

 2.使用内建函数
    user?html 给出 user 的HTML转义版本， 比如 & 会由 &amp; 来代替。
    animal?index 给出了在 animals 中基于0开始的 animal的索引值
    user?length 给出 user 值中 字符的数量(对于 "John Doe" 来说就是8)
    fruits?join(", ") 通过连接所有项，将列表转换为字符串， 在每个项之间插入参数分隔符(比如 "orange,banana")

 3.处理不存在的变量
    a、${变量值!默认值}  <h1>Welcome ${user!"visitor"}!</h1>
    b、??用于判断是否存在 <#if user??><h1>Welcome ${user}!</h1></#if>

 4.常用的逻辑操作符：
    逻辑 或： ||
    逻辑 与： &&
    逻辑 非： !

 5.定义变量
    <#assign x = 1>
 6.Import | marcos
    1、定义宏：<#macro branchService></#macro>
    2、导入宏：<#import "/macro/main.ftl" as marcos>
    3、使用该宏的命名空间：<@marcos.page>接口列表</@marcos.page>
-->

<html>
<head>
    <title>Welcome!</title>
</head>
<body>
<h1>
    <#if ''== false>显示内容</#if><#--等于-->
    <#if animals.python.price != 0>Pythons are not free today!</#if><#--不等于-->
<#--小于-->
    <#if animals.python.price < animals.elephant.price>Pythons are cheaper than elephants today.</#if>
<#--if else情况-->
    <#if animals.python.price < animals.elephant.price>
        Pythons are cheaper than elephants today.
    <#else>
        Pythons are not cheaper than elephants today.
    </#if>
<#-- if elesif else-->
    <#if animals.python.price < animals.elephant.price>
        Pythons are cheaper than elephants today.
    <#elseif animals.elephant.price < animals.python.price>
        Elephants are cheaper than pythons today.
    <#else>
        Elephants and pythons cost the same today.
    </#if>
<#--本身是boolean类型值-->
    <#if animals.python.protected>
        Pythons are protected animals!
    </#if>

<#--list指令-->

     <#list animals as animal>
        <tr>
         <td>${animal.name}
     <td>${animal.price} Euros
     </#list>

<#--写法二：-->
    <#list misc.fruits>
      <ul>
        <#items as fruit>
          <li>${fruit}
        </#items>
      </ul>
    </#list>

<#--当内容为空的时候处理方法-->
    <#list misc.fruits as fruit>
        ${fruit}<#sep>,
    <#else>None
    </#list>

    <p>Fruits: ${fruits?join(", ", "None")}</p>

    <p>Session: ${(session.freeMarker.enable) !=false}</p>
    <p>Session: ${(session.freeMarker.enable)??}</p>

    <#assert seq = ["A", "B", "C", "D", "E"]>
<#--截取序号-->
    <#list seq[1..3] as i>${i}</#list>

<#--强制转换为int类型-->
${(x/2)?int}

    Welcome ${user}<#if user == "Big Joe">, our beloved leader</#if>!
</h1>
<p>Our latest product:
    <a href="${latestProduct.url}">${latestProduct.name}</a>!
<#switch .caller_template_name>

    <#list map?keys as key>
<ul>
    <li>${map[key].name}</li>
</ul>
    </#list>

</#switch>
</body>
</html>