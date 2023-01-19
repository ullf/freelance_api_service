package dev.freelance.freeserve.config;

import dev.freelance.freeserve.service.AbstractClientService;
import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@NoArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private AbstractClientService abstractClientService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(header);
        if (header != null && header.startsWith("Bearer ")) {
            var check = Jwts.parser().setSigningKey("ewUgbh93").parseClaimsJws(header.split(" ")[1].trim());
            UserDetails user = abstractClientService.loadUserByUsername(check.getBody().getSubject());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(user,null,null);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request,response);
        } else {
            filterChain.doFilter(request,response);
        }
        }
    }
