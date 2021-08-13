package com.zwq.cloud.config;

import com.zwq.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * @author wuqing.zhu
 * @date 2021/08/13 14:22
 * 授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端id
                .withClient("client")
                // 密钥
                .secret(passwordEncoder.encode("123123"))
                // 配置token的有效期
                .accessTokenValiditySeconds(10)
                // 重定向地址  授权成功跳转的地址
                .redirectUris("http://www.baidu.com")
                // 授权范围
                .scopes("all")
                /**
                 * 授权类型
                 * 1.authorization_code 授权码模式
                 * 2.password 密码模式  需要重写configure(AuthorizationServerEndpointsConfigurer endpoints)方法
                 * 3. refresh_token 刷新令牌
                 */
                //    code授权码模式  一共有4种类型
                //.authorizedGrantTypes("password");

                //.authorizedGrantTypes("authorization_code");

                //都支持
                .authorizedGrantTypes("password", "refresh_token", "authorization_code");

    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    /**
     * 使用密码授权模式 所需配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService);


    }
}
