
package com.github.gateway.component.config;

import com.github.gateway.component.handler.AccessDeniedHandler;
import com.github.common.bean.config.FilterIgnorePropertiesConfig;
import com.github.common.security.ZlPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * @author wsp
 * @date 2017/10/27
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;
    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.headers().frameOptions().disable();

        //登陆
        http.authorizeRequests().antMatchers("/login").permitAll();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();

        //未认证请求
        filterIgnorePropertiesConfig.getUrls().forEach(url -> registry.antMatchers(url).permitAll());

        //验证角色权限
        registry.anyRequest()
                .access("@permissionService.hasPermission(request,authentication)");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.expressionHandler(expressionHandler);
        resources.accessDeniedHandler(accessDeniedHandler);
    }

    /**
     * 配置解决 spring-security-oauth问题
     * https://github.com/spring-projects/spring-security-oauth/issues/730
     *
     * @param applicationContext ApplicationContext
     * @return OAuth2WebSecurityExpressionHandler
     */
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    /**
     * 加密方式
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new ZlPasswordEncoder();
    }
}