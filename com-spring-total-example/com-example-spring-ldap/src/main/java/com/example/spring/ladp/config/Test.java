package com.example.spring.ladp.config;

import org.springframework.util.StringUtils;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

//import com.sijibao.scfs.common.utils.StringUtils;

/**
 * ldap连接demo测试
 * @author Petter
 * @date 2019年1月30日
 */
public class Test {
	//ssl访问方式
	private final String SSL_URL = "ldap://ldap.sijibao.com:636/";
	//普通访问方式
	//private final String URL = "ldap://192.168.200.250:389/";
	private final String URL = "ldap://ldap.sijibao.com:389/";
	//基准DN
	private final String BASEDN = "ou=Sijibao,dc=sijibao,dc=com";
	private final String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private LdapContext ctx = null;
	private final Control[] connCtls = null;

	/**
	 * 管理员连接
	 */
	private void LDAP_connect() {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY);
		env.put(Context.PROVIDER_URL, URL + BASEDN);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		
		//String root = "cn=yindong.peng,ou=产品研发中心,ou=Sijibao,dc=sijibao,dc=com"; // 根据自己情况修改
		String root ="CN=yu.wang,OU=网关资管产品线,OU=产品研发中心,OU=Sijibao,DC=sijibao,DC=com";
		env.put(Context.SECURITY_PRINCIPAL, root); // 管理员
		env.put(Context.SECURITY_CREDENTIALS, "***********"); // 管理员密码

		try {
			ctx = new InitialLdapContext(env, connCtls);
			System.out.println("连接成功");

		} catch (AuthenticationException e) {
			System.out.println("连接失败：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("连接出错：");
			e.printStackTrace();
		}

	}
	
	/**
	 * 直接验证(普通)
	 * @param uid
	 * @param pwd
	 */
	public void ldapFromNormal(String uid, String pwd) {
		try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            //用户名称，cn,ou,dc 分别：用户，组，域
            env.put(Context.SECURITY_PRINCIPAL, "cn="+uid+",OU=运维组,OU=产品研发中心,ou=Sijibao,dc=sijibao,dc=com");
            //用户密码 cn 的密码
            env.put(Context.SECURITY_CREDENTIALS, pwd);
            //url 格式：协议://ip:端口/组,域   ,直接连接到域或者组上面
            env.put(Context.PROVIDER_URL, URL + "dc=sijibao,dc=com");
            //LDAP 工厂
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            //验证的类型     "none", "simple", "strong"
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            LdapContext ldapContext = new InitialLdapContext(env, null);
            System.out.println("ldapContext:" + ldapContext);
            System.out.println("用户" + uid + "登录验证成功");
        } catch (NamingException e) {
        	System.err.println("用户" + uid + "登录验证失败");
        	System.err.println("错误信息："+e.getExplanation());
        }

	}
	
	/**
	 * 直接验证(SSL)
	 * @param uid
	 * @param pwd
	 */
	public void ldapFromSSL(String uid, String pwd) {
		
		String javaHome = System.getProperty("java.home");
		String keystore = javaHome+"/lib/security/cacerts";
		System.out.println("java.home,"+keystore);
		// 加载导入jdk的域证书
        System.setProperty("javax.net.ssl.trustStore", keystore);
        System.setProperty("javax.net.ssl.trustStorePassword", "mima");
		
		Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_PROTOCOL, "ssl");//链接认证服务器
        env.put(Context.PROVIDER_URL, SSL_URL + "dc=sijibao,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn="+uid+",ou=Sijibao,dc=sijibao,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, pwd);
        try {
            LdapContext ldapContext = new InitialLdapContext(env, null);
            System.out.println("ldapContext:" + ldapContext);
            System.out.println("认证成功");// 这里可以改成异常抛出。
        } catch (AuthenticationException e) {
        	System.err.println("认证失败");
        	e.printStackTrace();
        } catch (Exception e) {
        	System.err.println("认证出错");
        	e.printStackTrace();
        }
	}

	/**
	 * 关闭连接
	 */
	private void closeContext() {
		if (ctx != null) {
			try {
				ctx.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}

		}
	}
	/**
	 * 获取用户DN信息
	 * @param uid
	 * @return
	 */
	private String getUserDN(String uid) {
		String userDN = "";
		LDAP_connect();
		try {	
			getList();
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> en = ctx.search("", "cn=" + uid, constraints);

			if (en == null || !en.hasMoreElements()) {
				System.out.println("未找到该用户");
			}
			// maybe more than one element
			while (en != null && en.hasMoreElements()) {
				Object obj = en.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult si = (SearchResult) obj;
					userDN += si.getName();
					userDN += "," + BASEDN;
				} else {
					System.out.println(obj);
				}
			}
		} catch (Exception e) {
			System.out.println("查找用户时产生异常。");
			e.printStackTrace();
		}

		return userDN;
	}
	
	private void getList(){
		try {
			if (ctx != null) {
				SearchControls constraints = new SearchControls();
				constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
				NamingEnumeration<SearchResult> en = ctx.search("", "(&(objectCategory=person)(objectClass=user)(name=*))", constraints);
				while (en.hasMoreElements()) {
					SearchResult sr = (SearchResult) en.next();
					System.out.println("<<<::[" + sr.getName()+"]::>>>>");
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * ldap验证
	 * @param UID
	 * @param password
	 * @return
	 */
	public boolean authenricate(String UID, String password) {
		boolean valide = false;
		String userDN = getUserDN(UID);

		try {
			if(StringUtils.isEmpty(userDN)){
				System.out.println(userDN + " 验证失败");
				valide = false;
			}else{
				ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
				ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
				ctx.reconnect(connCtls);
				System.out.println(userDN + " 验证通过");
				valide = true;
			}
		} catch (AuthenticationException e) {
			System.out.println(userDN + " 验证失败");
			System.out.println(e.toString());
			valide = false;
		} catch (NamingException e) {
			System.out.println(userDN + " 验证失败");
			valide = false;
		}
		closeContext();
		return valide;
	}

	// 测试
	public static void main(String[] args) {
		Test ldap = new Test();
//		ldap.ldapFromNormal("duanwang.liu","1qaz@WSX");
		/*if (ldap.authenricate("duanwang.liu", "1qaz@WSX") == true) {
			System.out.println("该用户认证成功");
		}*/
		/*if (ldap.authenricate("yindong1.peng", "1qaz@WSX") == true) {
			System.out.println("该用户认证成功");
		}
		*/
		
		if (ldap.authenricate("yu.wang", "*******") == true) {
			System.out.println("该用户认证成功");
		}
	
//		if (ldap.authenricate("yindong2.peng", "1qaz@WSX") == true) {
//			System.out.println("该用户认证成功");
//		}
	     
	}

}
