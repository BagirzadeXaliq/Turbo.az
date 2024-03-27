package com.example.turboaz.service.utility;

import com.example.turboaz.dao.entity.AuthorityEntity;
import com.example.turboaz.dao.entity.UserEntity;
import com.example.turboaz.dao.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@PropertySource("classpath:application.properties")
public class JwtUtil {

    private final UserRepository userRepository;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long accessTokenValidity;

    private static Key jwtKey;

    public JwtUtil(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Key initializeJwtKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        jwtKey = Keys.hmacShaKeyFor(keyBytes);
        return jwtKey;
    }

    public String generateToken(UserEntity user) {
        initializeJwtKey();
        user = userRepository.findByUsername(user.getUsername()).orElseThrow();
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        Set<AuthorityEntity> authorities = user.getAuthorities().stream()
                .map(authority -> (AuthorityEntity) authority)
                .collect(Collectors.toSet());
        List<String> roles = new ArrayList<>();
        for (AuthorityEntity authority : authorities) {
            roles.add(authority.getName());
        }
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("authorities",roles);
        claimsMap.put("username", user.getUsername());
        claimsMap.put("user_id", user.getUserId());
        Date now  = new Date();
        Date validity = new Date(now.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        final JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(validity)
                .addClaims(claimsMap)
                .signWith(jwtKey, SignatureAlgorithm.HS512);
        log.info("JWT token created for user: {}", user.getUsername());
        return jwtBuilder.compact();
    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            log.error("Error due to: {}", ex.getMessage());
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Error due to: {}", ex.getMessage());
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String tokenHeader = "Authorization";
        String bearerToken = request.getHeader(tokenHeader);
        String tokenPrefix = "Bearer ";
        if (bearerToken != null && bearerToken.startsWith(tokenPrefix)) {
            return bearerToken.substring(tokenPrefix.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) {
        return claims.getExpiration().after(new Date());
    }

    public Integer getUserId(Claims claims){
        return (Integer) claims.get("user_id");
    }

    public Collection<GrantedAuthority> extractAuthorities(Claims claims) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (claims.containsKey("authorities")) {
            List<String> roles = (List<String>) claims.get("authorities");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }
        return authorities;
    }

}