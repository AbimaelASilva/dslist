package com.famdev.dslist.infra.security;

import java.io.IOException;
import java.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.famdev.dslist.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.recoverToken(request);

        System.err.println("Token: " + token);

        if (token != null) {
            var login = tokenService.validateToken(token);
            UserDetails userDetails = userRepository.findByLogin(login);

            var autentication = new UsernamePasswordAuthenticationToken(filterChain, userDetails);
            SecurityContextHolder.getContext().setAuthentication(autentication);

            //

            System.out.println("✅ Usuário autenticado: " + userDetails.getUsername());
        } else {
            System.err.println("❌ Token inválido ou usuário não autenticado");
        }
        filterChain.doFilter(request, response);

        



    }

    private String recoverToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer "))
            return null;

        return token.replace("Bearer ", "");
    }

}
