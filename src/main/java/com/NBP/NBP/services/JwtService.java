package com.NBP.NBP.services;

import com.NBP.NBP.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("3600000")
    private long jwtExpiration;

    // Extract username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Generic method to extract a specific claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Generate a JWT token for the given user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof User user) {
            claims.put("userId", user.getId());

            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            if (authorities != null && !authorities.isEmpty()) {
                List<String> roles = authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                claims.put("roles", roles);
            } else {
                claims.put("roles", Collections.emptyList());
            }
        }

        String token = buildToken(claims, userDetails);
        logger.debug("Generated token in jwtService: {}", token);
        return token;
    }

    // Build the JWT token with the given claims
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        logger.info("Building token for user: {}", userDetails.getUsername());
        try {
            return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            logger.error("Error while building token", e);
            throw e;
        }
    }

    // Check if the token is valid
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Error while extracting claims from token", e);
            throw e;
        }
    }

    // Get the signing key for the token
    private Key getSignInKey() {
        byte[] keyBytes = hexStringToByteArray(secretKey);  // Convert hex string to byte array
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Convert hex string to byte array
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    // Get the expiration time for the token
    public long getExpirationTime() {
        return jwtExpiration;
    }
}
