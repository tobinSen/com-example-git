package com.example.spring.oauth.common;

/**
 * 自定义用户认证与授权
 * <p>
 * Description:
 * </p>
 *
 * @author Lusifer
 * @version v1.0.0
 * @date 2019-04-04 23:57:04
 * @see com.funtl.oauth2.server.config
 */
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private TbUserService tbUserService;
//
//    @Autowired
//    private TbPermissionService tbPermissionService;
//
//    //这里的username是服务提供商的授权页面中的username
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        TbUser tbUser = tbUserService.getByUsername(username);
//        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
//        if (tbUser != null) {
//            // 获取用户授权
//            List<TbPermission> tbPermissions = tbPermissionService.selectByUserId(tbUser.getId());
//
//            // 声明用户授权
//            tbPermissions.forEach(tbPermission -> {
//                if (tbPermission != null && tbPermission.getEnname() != null) {
//                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(tbPermission.getEnname());
//                    grantedAuthorities.add(grantedAuthority);
//                }
//            });
//        }
//        return new User(tbUser.getUsername(), tbUser.getPassword(), grantedAuthorities);
//    }
//}
