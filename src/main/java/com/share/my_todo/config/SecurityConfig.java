package com.share.my_todo.config;

import com.share.my_todo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**","/js/**","/assets/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/member/signIn", "/member/signUp", "/notice/checkNotice","/member/test").permitAll()
                .antMatchers("/admin/**").hasRole("Admin")
                .anyRequest().authenticated()
                .and()
                .formLogin() // 로그인 관련 설정
                .loginPage("/member/signIn") //로그인 페이지
                .loginProcessingUrl("/member/signIn") //로그인 form의 action url 기본값은 '/login.thml'
                .defaultSuccessUrl("/todo/main") //로그인에 성공하면 이동할 페이지
                .and()
                .logout() // 로그아웃 설정
                .logoutUrl("/member/signOut") //로그아웃 form의 action url
                .logoutSuccessUrl("/member/signIn") // 로그아웃 성공하면 이동할 페이지
                .invalidateHttpSession(true) // 세션 관련
                .and()
                .exceptionHandling() // 예외 핸들러
                .accessDeniedPage("/member/deniedPage"); // 권한없는 접근시 이동할 페이지

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
