package com.marketmaven.MarketMaven.Ecommerce.security;


import com.marketmaven.MarketMaven.Ecommerce.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component //it tells spring to automaticalyy detect and registor this class a bean and it will create a instance of this class.
//If component is not there filter would not be detected.
@Slf4j //Simple logging Facade for Java.looging abstraction allows to use various logging frameworks
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService; //for loading user details
    @Override
    protected void doFilterInternal(HttpServletRequest request , HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if(token != null) {
            String username=jwtUtils.getUsernameFomToken(token);
            UserDetails userDetails=customUserDetailsService.loadUserByUsername(username);
            if(StringUtils.hasText(username)&& jwtUtils.isTokenValid(token, userDetails)) {
                log.info("Valid JWT FOR {}",username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && StringUtils.startsWithIgnoreCase(token,"Bearer"))
        {
            return token.substring(7);
        }
        return null;
    }
}
//Authorization:
//Bearer  ..........