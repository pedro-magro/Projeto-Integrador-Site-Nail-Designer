package br.com.nailDesigner.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler  implements AuthenticationSuccessHandler {
	
	@Override 
	public void onAuthenticationSuccess(HttpServletRequest request,
										HttpServletResponse response, 
										Authentication authentication) throws IOException {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		String redirectUrl ="/";
		
		for(GrantedAuthority authority : authorities) {
			String role = authority.getAuthority();
			
			if(role.equals("ROLE_ADMIN")) {
				redirectUrl = "/admin";
			}
			else if (role.equals("ROLE_FUNCIONARIO")) {
                redirectUrl = "/funcionario";
                break;
            } 
			else if (role.equals("ROLE_CLIENTE")) {
                redirectUrl = "/cliente";
                break;
            }
		}
		
		response.sendRedirect(redirectUrl);
		
	}

}
