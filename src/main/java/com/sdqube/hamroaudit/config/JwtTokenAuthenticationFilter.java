package com.sdqube.hamroaudit.config;

import com.sdqube.hamroaudit.model.ProximityUserDetails;
import com.sdqube.hamroaudit.service.JwtTokenProvider;
import com.sdqube.hamroaudit.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 7:39 PM
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    final JwtConfig jwtConfig;
    JwtTokenProvider tokenProvider;
    UserService userService;
    String serviceUsername;

    public JwtTokenAuthenticationFilter(String serviceUsername, JwtConfig jwtConfig, JwtTokenProvider tokenProvider, UserService userService) {
        this.jwtConfig = jwtConfig;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.serviceUsername = serviceUsername;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(jwtConfig.getHeader());

        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = header.replace(jwtConfig.getPrefix(), "");
        if (tokenProvider.validateToken(token)) {
            Claims claims = tokenProvider.getClaimsFromJwt(token);
            String username = claims.getSubject();

            UsernamePasswordAuthenticationToken auth = null;

            if (username.equalsIgnoreCase(serviceUsername)) {
                List<String> authorities = (List<String>) claims.get("authorities");
                auth = new UsernamePasswordAuthenticationToken(username, null,
                        authorities.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList()));
            } else {
                auth = userService.findByUsername(username)
                        .map(ProximityUserDetails::new)
                        .map(proximityUserDetails -> {
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(proximityUserDetails, null,
                                            proximityUserDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                                    .buildDetails(httpServletRequest));
                            return authenticationToken;
                        }).orElse(null);
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
