package com.example.turboaz.service.auth;

import com.example.turboaz.dao.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String SECRET_KEY = "ff31c08be7688c301867d7927c739f890b88b5dcb6d8316dea4fc4547a0f073c";

    public String extractUsername(String token){
        log.info("Extracting username from token: {}", token);
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserEntity user) {
        log.info("Generating token for user: {}", user.getUsername());
        return generateToken(new HashMap<>(),user);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails user) {
        log.info("Generating token for user: {}", user.getUsername());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        log.info("Validating token for user: {}", userDetails.getUsername());
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        log.info("Checking token expiration");
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        log.info("Extracting all claims from token: {}", token);
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

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
        log.info("Resolving token from request");
        String TOKEN_HEADER = "Authorization";
        String bearerToken = request.getHeader(TOKEN_HEADER);
        String TOKEN_PREFIX = "Bearer ";
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Long getUserId(Claims claims){
        Long userId = claims.get("user_id", Long.class);
        if (userId == null) {
            throw new IllegalArgumentException("Missing or invalid user_id claim in JWT");
        }
        return userId;
    }

    private Key getSignInKey(){
        log.info("Retrieving signing key");
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}