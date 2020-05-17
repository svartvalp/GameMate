package com.svartvalp.GameMate.Security;

import com.svartvalp.GameMate.JWT.JWTUtils;
import com.svartvalp.GameMate.Services.IUserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JWTAuthenticationConverter implements ServerAuthenticationConverter {

    private JWTUtils jwtUtils;
    private IUserService userService;

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
        List<String> auth =  serverWebExchange.getRequest().getHeaders().get("Authorization");
        try {
            if (auth != null && auth.size() > 0) {
                String token = auth.get(0).split(" ")[1];
                String username = jwtUtils.verifyTokenAndReturnSubject(token);
                    return userService.findUserByNickName(username)
                            .map(user -> new UsernamePasswordAuthenticationToken(
                                    user.getNickname(),
                                    user.getPassword(), List.of(new SimpleGrantedAuthority("user"))));
            }
            return Mono.empty();
        } catch (JwtException e) {
            return Mono.empty();
        }
    }
}
