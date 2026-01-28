package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {

		var tokenJWT = recuperarToken(request);
		
		if (tokenJWT != null) {
			try {
				var subject = tokenService.getSubject(tokenJWT);
				if (subject != null) {
					var usuario = repository.findByLogin(subject);
					if (usuario != null) {
						var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			} catch (RuntimeException ex) {
				// token inválido ou expirado: não autentica, deixa a requisição prosseguir
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

	private String recuperarToken(HttpServletRequest request) {

		var authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader == null) {
			return null; // não lançar exceção — endpoints públicos (ex: /login) não terão token
		}
		
		if (!authorizationHeader.startsWith("Bearer ")) {
			return null; // não é um token Bearer
		}
		
		return authorizationHeader.substring(7).trim(); // extrai o token após "Bearer "
		
	}

}