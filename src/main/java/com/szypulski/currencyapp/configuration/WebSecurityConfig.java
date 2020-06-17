package com.szypulski.currencyapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public BCryptPasswordEncoder getEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  UserDetailsService userService;

 /* @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Autowired
  public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService)
        .passwordEncoder(getEncoder());
  }*/

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(getEncoder());
  }

  /*@Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().
        authorizeRequests()
        .antMatchers(HttpMethod.DELETE,"/users").hasAnyRole("ADMIN")
        .antMatchers(HttpMethod.POST,"/users").permitAll()
        .antMatchers("/test/*").hasRole("USER")
        .antMatchers("/consume/*").hasRole("USER")
        .and().formLogin();
  }*/
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().
        authorizeRequests()
        .antMatchers(HttpMethod.DELETE, "/users").hasAnyRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/users").permitAll()
        .antMatchers("/test/*").hasRole("USER")
        .antMatchers("/consume/*").hasRole("USER")
        //.anyRequest().authenticated()
        .and()
        .oauth2Login();
  }
}
