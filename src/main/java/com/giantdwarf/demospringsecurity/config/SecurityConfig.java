package com.giantdwarf.demospringsecurity.config;

import com.giantdwarf.demospringsecurity.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**", "signup").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated();
        http.formLogin()
            .loginPage("/login")    //DefaultLoginGeneratingFilter가 등록안됨
            .permitAll();

        http.httpBasic();

        http.logout().logoutSuccessUrl("/");

//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                .sessionFixation()
//                    .changeSessionId()
//                .maximumSessions(1)
//                    .expiredUrl("/");

        http.exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler());
//                .accessDeniedPage("/access-denied");

        http.rememberMe()
                .userDetailsService(accountService)
                .key(("remember-me-sample"));

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL); // 하위 쓰레드로 SecurityCentex 공유
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
