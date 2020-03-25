package com.example.spring.hutool.ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class LdapDemo {

    public static void main(String[] args) throws Exception {
        Hashtable env = new Hashtable();
        String LDAP_URL = "ldap://<IP>:389"; // LDAP 访问地址
        String adminName = "example\\user"; // 注意用户名的写法：domain\User 或 cn=user
        String adminPassword = "userpassword"; // 密码
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAP_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        LdapContext dc = new InitialLdapContext(env, null);// 初始化上下文
        System.out.println("认证成功");


        //新增
        String root = "dc=example,dc=com"; // LDAP的根节点的DC

        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("sAMAccountName");
        objclassSet.add("employeeID");
        objclassSet.add("sAMAccountName");
        objclassSet.add("employeeID");
        attrs.put(objclassSet);
        attrs.put("ou", "newUserName");
        dc.createSubcontext("ou=" + "newUserName" + "," + root, attrs);

        //删除
        try {
            dc.destroySubcontext("dn");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in delete():" + e);
        }

        //重命名
        dc.rename("oldDN", "newDN");

        //更新
        ModificationItem[] mods = new ModificationItem[1];
        /* 修改属性 */
        // Attribute attr0 = new BasicAttribute("employeeID", "test");
        // mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);
        /* 删除属性 */
        // Attribute attr0 = new BasicAttribute("description", "test");
        // mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attr0);
        /* 添加属性 */
        Attribute attr0 = new BasicAttribute("employeeID", "");
        mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr0);
        /* 修改 */
        dc.modifyAttributes("dn" + ",dc=example,dc=com", mods);


        // 创建搜索控件
        SearchControls searchCtls = new SearchControls();
// 设置搜索范围
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
// 设置搜索过滤条件
        String searchFilter = "sAMAccountName=" + "";
// 设置搜索域节点
        String searchBase = "DC=example,DC=COM";
// 定制返回属性
        String returnedAtts[] = {"url", "whenChanged", "employeeID", "name", "userPrincipalName", "physicalDeliveryOfficeName", "departmentNumber", "telephoneNumber", "homePhone", "mobile", "department", "sAMAccountName", "whenChanged", "mail"};
// 不定制属性，返回所有的属性集
// searchCtls.setReturningAttributes(null);
        int totalResults = 0;
        try {
            NamingEnumeration answer = dc.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes Attrs = sr.getAttributes();
                if (Attrs != null) {
                    try {
                        for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore(); ) {
                            Attribute Attr = (Attribute) ne.next();
                            // 读取属性值
                            for (NamingEnumeration e = Attr.getAll(); e.hasMore(); totalResults++) {
                                // 接受循环遍历读取的userPrincipalName用户属性
                                String user = e.next().toString();
                            }
                            // 读取属性值
                            // Enumeration values = Attr.getAll();
                            // if (values != null) {
                            //          while (values.hasMoreElements()) {
                            //                   System.out.println(" 2AttributeValues=" + values.nextElement());
                            //          }
                            // }
                        }
                    } catch (NamingException e) {
                        System.err.println("Throw Exception : " + e);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Throw Exception : " + e);
        }

        //关闭
        if (dc != null) {
            try {
                dc.close();
            } catch (NamingException e) {
                System.out.println("NamingException in close():" + e);
            }
        }
    }
}
