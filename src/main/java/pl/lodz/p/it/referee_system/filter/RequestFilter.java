package pl.lodz.p.it.referee_system.filter;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.lodz.p.it.referee_system.service.implementation.AccountServiceImpl;
import pl.lodz.p.it.referee_system.utill.TokenUtills;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter extends OncePerRequestFilter {

    @Autowired
    private AccountServiceImpl userDetailsService;

    @Autowired
    private TokenUtills jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authorizationHeader = httpServletRequest.getHeader("Authorization");


            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtUtil.extractUsername(authorizationHeader.substring(7)));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        catch(ExpiredJwtException ex){
            httpServletResponse.setStatus(401);
        }
        catch(Exception ex){
            httpServletResponse.setStatus(403);
        }
    }
}

