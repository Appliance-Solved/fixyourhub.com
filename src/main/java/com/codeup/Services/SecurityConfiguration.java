package com.codeup.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by larryg on 7/6/17.
 */
    @Configuration
    @EnableWebSecurity
    @ComponentScan(basePackageClasses = UserWithRoles.class)
    public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserDetailsLoader userDetails;

        @Bean(name = "passwordEncoder")
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/dashboard") // after login goes to login handler
                    .permitAll() // Anyone can go to the login page
                    .and()
                    .authorizeRequests()
                    .antMatchers("/", "/logout") // anyone can see the home and logout page
                    .permitAll()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/login?logout")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/dashboard", "/servicer/dashboard", "/user/dashboard") // only authenticated users can go through handle
                    .authenticated()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/servicer/dashboard") // only servicer users can view service dashboard
                    .hasAuthority("SERVICER")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/user/dashboard") // only users can view user dashboard
                    .hasAuthority("USER")
            ;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
        }
    }

