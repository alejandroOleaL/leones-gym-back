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
		//http.authorizeRequests().antMatchers("/leonesgym/**").permitAll()
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/leonesgym/clientes", "/leonesgym/clientes/page/**", "/leonesgym/uploads/img/**",
				"/images/**", "/leonesgym/clientes/periodos", "/leonesgym/clientes/qr/{id}", "/leonesgym/clientes/numero/control/{numcontrol}",
				"/leonesgym/clientes/numero/control/**", "/leonesgym/clientes/qr/**", "/leonesgym/clientes/registros/**",
				"/leonesgym/clientes/**", "/leonesgym/clientes/enviar/**", "/leonesgym/clientes/enviar/{id}", 
				"/leonesgym/clientes/vencidos/page/**", "/leonesgym/clientes/activos/page/**", "/leonesgym/clientes/{id}", "/leonesgym/productos/{id}",
				"/leonesgym/operaciones/page/**").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/clientes/{id}").hasAnyRole("USER", "ADMIN")
		.antMatchers(HttpMethod.POST, "/leonesgym/upload").hasAnyRole("USER", "ADMIN")
		.antMatchers(HttpMethod.POST, "/leonesgym/clientes").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/clientes/datos").permitAll()
		.antMatchers(HttpMethod.POST, "/leonesgym/usuarios").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/usuarios/page/**").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/usuarios/roles").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/usuarios/**").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/clientes/enviar/**").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/clientes/enviar/{id}").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/usuarios/{id}").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/ventas/{id}").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/ventas/filtrar-productos/{nomb}").permitAll()
		.antMatchers(HttpMethod.DELETE, "/leonesgym/usuarios/**").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/historial/page/**").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/productos/page/**").permitAll()
		.antMatchers(HttpMethod.GET, "/leonesgym/username/{username}").permitAll()
		.antMatchers(HttpMethod.POST, "/leonesgym/productos").permitAll()
		.antMatchers(HttpMethod.PUT, "/leonesgym/productos/{id}").permitAll()
		.antMatchers(HttpMethod.DELETE, "/leonesgym/productos/**").permitAll()
		.antMatchers(HttpMethod.POST, "/leonesgym/ventas").permitAll()
		.antMatchers(HttpMethod.PUT, "/leonesgym/clientes").hasAnyRole("USER", "ADMIN")
		.antMatchers(HttpMethod.DELETE, "/leonesgym/clientes").hasRole("ADMIN")
//		http.authorizeRequests().antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/clientes", "/leones-gym-back/leonesgym/clientes/page/**", 
//				"/leones-gym-back/leonesgym/uploads/img/**",
//				"/leones-gym-back/images/**", "/leones-gym-back/leonesgym/clientes/periodos", "/leones-gym-back/leonesgym/clientes/qr/{id}", 
//				"/leones-gym-back/leonesgym/clientes/numero/control/{numcontrol}",
//				"/leones-gym-back/leonesgym/clientes/numero/control/**", "/leones-gym-back/leonesgym/clientes/qr/**", "/leones-gym-back/leonesgym/clientes/registros/**",
//				"/leones-gym-back/leonesgym/clientes/**", "/leones-gym-back/leonesgym/clientes/enviar/**", "/leones-gym-back/leonesgym/clientes/enviar/{id}", 
//				"/leones-gym-back/leonesgym/clientes/vencidos/page/**", "/leones-gym-back/leonesgym/clientes/activos/page/**", "/leones-gym-back/leonesgym/clientes/{id}", 
//				"/leones-gym-back/leonesgym/productos/{id}",
//				"/leones-gym-back/leonesgym/operaciones/page/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/clientes/{id}").hasAnyRole("USER", "ADMIN")
//		.antMatchers(HttpMethod.POST, "/leones-gym-back/leonesgym/upload").hasAnyRole("USER", "ADMIN")
//		.antMatchers(HttpMethod.POST, "/leones-gym-back/leonesgym/clientes").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/clientes/datos").permitAll()
//		.antMatchers(HttpMethod.POST, "/leones-gym-back/leonesgym/usuarios").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/usuarios/page/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/usuarios/roles").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/usuarios/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/clientes/enviar/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/clientes/enviar/{id}").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/usuarios/{id}").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/ventas/{id}").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/ventas/filtrar-productos/{nomb}").permitAll()
//		.antMatchers(HttpMethod.DELETE, "/leones-gym-back/leonesgym/usuarios/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/historial/page/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/productos/page/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/leonesgym/username/{username}").permitAll()
//		.antMatchers(HttpMethod.POST, "/leones-gym-back/leonesgym/productos").permitAll()
//		.antMatchers(HttpMethod.PUT, "/leones-gym-back/leonesgym/productos/{id}").permitAll()
//		.antMatchers(HttpMethod.DELETE, "/leones-gym-back/leonesgym/productos/**").permitAll()
//		.antMatchers(HttpMethod.POST, "/leones-gym-back/leonesgym/ventas").permitAll()
//		.antMatchers(HttpMethod.PUT, "/leones-gym-back/leonesgym/clientes").hasAnyRole("USER", "ADMIN")
//		.antMatchers(HttpMethod.DELETE, "/leones-gym-back/leonesgym/clientes").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://leonesgym.web.app", "http://localhost:8090"));
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
