package app.netlify.leones.gym.back.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/leonesgym/clientes", "/leonesgym/clientes/page/**", "/leonesgym/uploads/img/**",
				"/images/**", "/leonesgym/clientes/periodos", "/leonesgym/clientes/qr/{id}", "/leonesgym/clientes/numero/control/{numcontrol}",
				"/leonesgym/clientes**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leonesgym/clientes/{id}").hasAnyRole("USER", "ADMIN")
//		.antMatchers(HttpMethod.POST, "/leonesgym/upload").hasAnyRole("USER", "ADMIN")
		.antMatchers(HttpMethod.POST, "/leonesgym/clientes").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/clientes/datos").permitAll()
		.antMatchers(HttpMethod.POST, "/leonesgym/usuarios").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/usuarios/page/**").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/usuarios/roles").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/usuarios/{id}").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/historial/page/**").permitAll()
//		.antMatchers(HttpMethod.PUT, "/leonesgym/clientes").hasAnyRole("USER", "ADMIN")
//		.antMatchers(HttpMethod.DELETE, "/leonesgym/clientes").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://leonesgym.web.app"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

}
