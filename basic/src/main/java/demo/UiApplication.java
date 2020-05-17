package demo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UiApplication {

	@RequestMapping("/resource")
	public Map<String,Object> home() {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}

	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}

    @Configuration
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    	@Override
    	protected void configure(HttpSecurity http) throws Exception {
    		// @formatter:off
    		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.httpBasic();
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

}
