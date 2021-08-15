package com.zwq.cloud.config;

import com.zwq.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("jwtTokenStore")
    TokenStore tokenStore;

    @Autowired
    JwtToeknEnhancer jwtToeknEnhancer;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端id
                .withClient("client")
                // 密钥
                .secret(passwordEncoder.encode("123123"))
                // 配置token的有效期
                .accessTokenValiditySeconds(100)

                // 配置刷新令牌的有效期
                .refreshTokenValiditySeconds(1000)


                // 重定向地址  授权成功跳转的地址  跳转到客户端
                .redirectUris("http://localhost:8081/login")

                // 自动授权 不需要在页面上点了
                .autoApprove(true)

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


    /**
     * 使用密码授权模式 所需配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 配置jwt内容增强器

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(jwtToeknEnhancer);
        enhancers.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancers);


        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                // 配置存储令牌策略
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                // jwt内容增强器
                .tokenEnhancer(enhancerChain)
        ;

    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 获取秘钥需要身份认证 使用单点登录必须配置
        security.tokenKeyAccess("isAuthenticated()");
    }
}
