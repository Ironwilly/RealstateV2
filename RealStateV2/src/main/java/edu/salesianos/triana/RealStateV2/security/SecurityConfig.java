package edu.salesianos.triana.RealStateV2.security;


import edu.salesianos.triana.RealStateV2.security.jwt.JwtAccessDeniedHandler;
import edu.salesianos.triana.RealStateV2.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthorizationFilter filter;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/**").anonymous()
                .antMatchers(HttpMethod.POST, "/auth/register/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/propietario/**").hasAnyRole("ADMIN","PROPIETARIO")
                .antMatchers(HttpMethod.POST, "/vivienda/").hasRole("PROPIERTARIO")
                .antMatchers(HttpMethod.PUT, "/vivienda/**").hasAnyRole("ADMIN","PROPIETARIO")
                .antMatchers(HttpMethod.DELETE, "/vivienda/").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.POST, "/vivienda/**/inmobiliaria/**").hasAnyRole("ADMIN","PROPIETARIO")
                .antMatchers(HttpMethod.DELETE, "vivienda/**/inmobiliaria/").hasAnyRole("ADMIN","PROPIETARIO")
                .antMatchers(HttpMethod.POST, "/inmobiliaria/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/inmobiliaria/**").hasAnyRole("ADMIN","GESTOR")
                .antMatchers(HttpMethod.DELETE, "/inmobiliaria/**").hasAnyRole("ADMIN","GESTOR")
                .antMatchers(HttpMethod.GET, "/inmobiliaria/**").hasAnyRole("ADMIN","GESTOR")
                .antMatchers(HttpMethod.GET, "/vivienda/").authenticated()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);


        http.headers().frameOptions().disable();

    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}