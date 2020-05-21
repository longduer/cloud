package yves.leung.com.auth.configure;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import yves.leung.com.auth.properties.LeungAuthProperties;
import yves.leung.com.auth.properties.LeungClientsProperties;
import yves.leung.com.auth.service.LeungUserDetailService;
import yves.leung.com.auth.translator.LeungWebResponseExceptionTranslator;

@Configuration
//开启认证服务器相关配置
@EnableAuthorizationServer
public class LeungAuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private LeungUserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LeungAuthProperties authProperties;

    @Autowired
    private LeungWebResponseExceptionTranslator exceptionTranslator;

    public LeungAuthorizationServerConfigure() {
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        LeungClientsProperties[] clientsArray = authProperties.getClients();
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if(ArrayUtils.isNotEmpty(clientsArray)){
            for(LeungClientsProperties client: clientsArray){
                if (StringUtils.isBlank(client.getClient())) {
                    throw new Exception("client不能为空");
                }
                if (StringUtils.isBlank(client.getSecret())) {
                    throw new Exception("secret不能为空");
                }
                String[] grantTypes = StringUtils.splitByWholeSeparatorPreserveAllTokens(client.getGrantType(), ",");
                builder.withClient(client.getClient())
                        .secret(passwordEncoder.encode(client.getSecret()))
                        .authorizedGrantTypes(grantTypes)
                        .scopes(client.getScope());
            }
        }

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager)
                .tokenServices(defaultTokenServices())
                .exceptionTranslator(exceptionTranslator); // LeungWebResponseExceptionTranslator异常翻译器生效，我们还需在认证服务器配置类
    }

    /**
     * TokenStore 1: RedisTokenStore令牌存储策略。使用这种策略时，用户的access_token将存储到Redis中，退出登录后，Redis中存储的令牌也会被清除
     * @return
     */
    @Bean
    public TokenStore tokenStore() {

        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        // 问题描述：同一个账号在不同的地点获取令牌是一样的，所以当其中一个用户退出登录后，另一个用户的令牌也会失效
        // 解决每次生成的 token都一样的问题
        // redisTokenStore.setAuthenticationKeyGenerator(oAuth2Authentication -> UUID.randomUUID().toString());
        return redisTokenStore;
    }

    /**
     * TokenStore 2: InMemoryTokenStore策略将令牌存储到内存中，优点就是无需依赖第三方存储，对于开发小型服务是不错的选择；缺点是认证服务器故障重启后，之前存储的令牌就丢失。
     * @return
     */
//    @Bean
//    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
//    }


    /**
     * TokenStore 3: JdbcTokenStore 该策略使用数据库来存储令牌。在使用这种策略之前，我们需要先准备好库表。Spring Security OAuth仓库可以找到相应的脚本：https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql。
     */
//    @Autowired
//    private DataSource dataSource;
//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }

    /**
     * TokenStore 4: JwtTokenStore 前面三种存储策略生成的令牌都是使用UUID生成的无意义字符串，我们也可以使用JwtTokenStore生成JWT格式令牌
     */
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
//        DefaultAccessTokenConverter defaultAccessTokenConverter = (DefaultAccessTokenConverter) accessTokenConverter.getAccessTokenConverter();
//        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
//        userAuthenticationConverter.setUserDetailsService(userDetailService);
//        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
//        accessTokenConverter.setSigningKey("leung");
//        return accessTokenConverter;
//    }


    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true); //  setSupportRefreshToken设置为true表示开启刷新令牌的支持
        tokenServices.setAccessTokenValiditySeconds(authProperties.getAccessTokenValiditySeconds()); // 令牌有效时间为60 * 60 * 24秒
        tokenServices.setRefreshTokenValiditySeconds(authProperties.getRefreshTokenValiditySeconds()); // 刷新令牌有效时间为
        return tokenServices;
    }
}
