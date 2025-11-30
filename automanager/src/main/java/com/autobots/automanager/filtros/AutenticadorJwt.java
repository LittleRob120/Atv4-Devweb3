package com.autobots.automanager.filtros;

import com.autobots.automanager.jwt.GeradorJwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class AutenticadorJwt extends OncePerRequestFilter {
    private final GeradorJwt geradorJwt;
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public AutenticadorJwt(GeradorJwt geradorJwt) {
        this.geradorJwt = geradorJwt;
    }

    @Override
    protected boolean shouldNotFilter(javax.servlet.http.HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();
        if (PATH_MATCHER.match("/actuator/health", path)) return true;
        if (PATH_MATCHER.match("/h2-console/**", path)) return true;
        if ("GET".equals(method) && (PATH_MATCHER.match("/clientes/**", path) || PATH_MATCHER.match("/usuarios/**", path)))
            return true;
        if ("POST".equals(method) && PATH_MATCHER.match("/clientes/**", path))
            return true; // permitir seed de clientes
        return false;
    }

    @Override
    protected void doFilterInternal(javax.servlet.http.HttpServletRequest request,
                                    javax.servlet.http.HttpServletResponse response,
                                    javax.servlet.FilterChain filterChain)
            throws java.io.IOException, javax.servlet.ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = header.substring("Bearer ".length()).trim();
            Claims claims = Jwts.parser()
                    .setSigningKey(geradorJwt.getSegredo().getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            var authentication = new UsernamePasswordAuthenticationToken(
                    username, null, List.of(new SimpleGrantedAuthority("ROLE_" + role))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
        }
    }
}