package com.project.cities.security;

import com.project.cities.SpringApplicationContext;
import com.project.cities.models.UserEntity;
import com.project.cities.repositories.UserRepository;
import com.project.cities.service.UserService;
import com.project.cities.shared.dto.UserDto;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFIlter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public AuthorizationFIlter(AuthenticationManager authManager, UserRepository userRepository) {
        super(authManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if(token != null) {

            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            //Get username with UserId
            UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");

            if(user != null ) {

                UserEntity userEntity = userRepository.findUserByEmail(user);
                if(userEntity == null) return null;
                UserPrincipal userPrincipal = new UserPrincipal(userEntity);
                return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
            }
            return null;
        }
        return null;
    }


}