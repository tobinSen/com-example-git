package com.example.spring.shiro.configuration;

import com.google.common.collect.Range;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomerAuthorizingRealm extends AuthorizingRealm {

    //修改默认的凭证匹配器
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME); //算法md5
        matcher.setHashIterations(1024); //hash散列次数
        super.setCredentialsMatcher(matcher);
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return new SimpleAuthenticationInfo("tom", "123", ByteSource.Util.bytes("salt"), this.getName());
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    public static void main(String[] args) {
        Range<Integer> rangeTotal = Range.closedOpen(1, 10);
        Range<Integer> range = Range.closedOpen(1, 22); //包含关系
        System.out.println(rangeTotal.encloses(range));
    }

}
