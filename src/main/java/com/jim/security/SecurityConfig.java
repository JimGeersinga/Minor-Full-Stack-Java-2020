package com.jim.security;

//
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/swagger/*", "/auth"   )
//                .permitAll()
//                .and()
//                .formLogin().disable();
//    }
//
//    @Bean
//    protected AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, UserService userService) {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setPasswordEncoder(passwordEncoder);
//        authProvider.setUserDetailsService(userService);
//
//        return authProvider;
//    }
//
//    @Bean
//    protected PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
