package bonvio.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http
                .csrf().disable() // DISABLED CSRF protection to make it easier !
                .authorizeRequests()
                .antMatchers("/", "/login/**").permitAll()
//                .antMatchers("/", "/login/callback/vk", "/login/check", "/login/login.json", "/login/login.vk").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin()
//                .loginPage("/auth")
//                .permitAll()  //.passwordParameter("password").usernameParameter("username")
//                .failureUrl("/ay?error")
//                .defaultSuccessUrl("/login/test")
                .successHandler(new AjaxAuthenticationSuccessHandler())
                .failureHandler(new AjaxAuthenticationFailureHandler())
                .and()
                .logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
//                .and()
//                .rememberMe()
//                .tokenValiditySeconds(31536000);
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        //auth.inMemoryAuthentication().withUser("user").password("123").roles("USER");
    }

    public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response, Authentication auth)
                throws IOException, ServletException {
            //Login Success
            response.getWriter().print("{\"error\": false}");
            response.getWriter().flush();
        }
    }

    public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        public void onAuthenticationFailure(HttpServletRequest request,
                                            HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            //Login Failed
            response.getWriter().print("{\"error\": true}");
            response.getWriter().flush();

        }
    }

}
