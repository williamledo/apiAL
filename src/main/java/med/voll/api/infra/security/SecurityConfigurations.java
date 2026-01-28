package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	        .csrf(csrf -> csrf.disable())
	        .sessionManagement(session ->   //Desabilita a criação de sessão
	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Aplicação RESTful não deve criar sessão
	        )
	        .authorizeHttpRequests(auth -> auth 
	            .requestMatchers(HttpMethod.POST, "/login").permitAll() //Permite acesso sem autenticação ao endpoint de login
	            .anyRequest().authenticated() //Exige autenticação para todas as outras requisições
	        )
	        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //Adiciona o filtro de segurança antes do filtro de autenticação padrão
	        .build(); 
	}


	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
		
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
