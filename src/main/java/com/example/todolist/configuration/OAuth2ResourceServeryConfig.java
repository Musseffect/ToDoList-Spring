package com.example.todolist.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
public class OAuth2ResourceServeryConfig {

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration  {
        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler(){
            return new OAuth2MethodSecurityExpressionHandler();
        }
    }

    @Configuration
    @EnableResourceServer
    public static class ResourceServerConfig extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.cors().and().authorizeRequests()
                    .antMatchers("/**").authenticated();
        }

        @Primary
        @Bean
        public RemoteTokenServices tokenService() {
            RemoteTokenServices tokenService = new RemoteTokenServices();
            tokenService.setCheckTokenEndpointUrl(
                    "http://localhost:8081/auth/oauth/check_token");
            tokenService.setClientId("todoAppImplicit");
            tokenService.setClientSecret("secret");
            return tokenService;
        }

        @Bean
        PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
    }

}
