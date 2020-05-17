package demo;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class UiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }

    @Configuration
    @EnableRedisHttpSession
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .httpBasic().and()
                .logout().and()
                .authorizeRequests()
                    .antMatchers("/index.html", "/", "/home", "/login", "/*.js", "/favicon.ico").permitAll()
                    .anyRequest().authenticated().and()
                .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
            // @formatter:on
        }

        @Bean
    	@Override
    	public UserDetailsService userDetailsService() {
    		UserDetails user =
    			 User.withDefaultPasswordEncoder()
    				.username("user")
    				.password("password")
    				.roles("USER")
    				.build();

    		return new InMemoryUserDetailsManager(user);
    	}
    }

    @GetMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

    @GetMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/token")
    @ResponseBody
    public Map<String, String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

}
