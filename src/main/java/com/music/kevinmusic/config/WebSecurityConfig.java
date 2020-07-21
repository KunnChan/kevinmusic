package com.music.kevinmusic.config;

import com.music.kevinmusic.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${com.kc.rock.allowed.origin:*}")
    private String allowedOrigin;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager){
//        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/**"));
//        filter.setAuthenticationManager(authenticationManager);
//        return filter;
//    }
//    public RestUrlAuthFilter restUrlAuthFilter(AuthenticationManager authenticationManager){
//        RestUrlAuthFilter filter = new RestUrlAuthFilter(new AntPathRequestMatcher("/**"));
//        filter.setAuthenticationManager(authenticationManager);
//        return filter;
//    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring().antMatchers(
                "/v2/api-docs", "/configuration/ui", "/swagger-resources",
                "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
//                UsernamePasswordAuthenticationFilter.class);
//
//        http.addFilterBefore(restUrlAuthFilter(authenticationManager()),
//                UsernamePasswordAuthenticationFilter.class);

        http
               // .cors().and()
                .csrf().ignoringAntMatchers("/h2-console/**", "/xapiv1/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(allowedOrigin);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
