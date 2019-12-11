package cerulean.project.config;


import cerulean.project.services.MongoDBUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Web security config class built on top of this tutorial.
 * https://medium.com/@gtommee97/rest-authentication-with-spring-security-and-mongodb-28c06da25fb1
 *
 */
@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MongoDBUserDetailsManager mongoDBUserDetailsManager;


    @Component
    public class CorsFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
            response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mongoDBUserDetailsManager);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("api/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
                // TODO: Replace this with REST login if you wanna be fancy
                .formLogin()
                .loginProcessingUrl("/api/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()

        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}