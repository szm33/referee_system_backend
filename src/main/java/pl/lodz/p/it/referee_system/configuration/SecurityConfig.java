package pl.lodz.p.it.referee_system.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.lodz.p.it.referee_system.entity.ApplicationUser;
import pl.lodz.p.it.referee_system.filter.RequestFilter;
import pl.lodz.p.it.referee_system.service.AccountServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private AccountServiceImpl accountService;
//
//    public SecurityConfig(AccountServiceImpl accountService) {
//        this.accountService = accountService;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/trains").permitAll()
//                .antMatchers("/trains/sort").permitAll()
//                .antMatchers("/trains/train/**").authenticated()
//                .antMatchers("/trains/**").hasAnyRole("ResourcesManager","Admin")
//                .antMatchers("/users/registration").permitAll()
//                .antMatchers("/tickets/add").hasAnyRole("Client","ResourcesManager","Admin")
//                .antMatchers("/tickets/show").hasAnyRole("Client","ResourcesManager","Admin")
//                .antMatchers("/tickets/**").hasAnyRole("ResourcesManager","Admin")
//                //.antMatchers("/users/**").permitAll()
//                .antMatchers("/users/**").hasRole("Admin")
//                .and()
//                .formLogin().permitAll()
//                .and()
//                .logout();
//    }
//
//    @Bean
//    DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(this.accountService);
//
//        return daoAuthenticationProvider;
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private AccountServiceImpl userDetailsService;

    @Autowired
    private RequestFilter requestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/referee").hasRole("USER")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
//                .antMatchers(HttpMethod.GET, "/referee").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/").hasAnyRole("USER")
//                .and()
//                .formLogin().defaultSuccessUrl("/referee",true).permitAll()
//                .and()
//                .logout().permitAll();

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
