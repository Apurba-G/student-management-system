
package com.sms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // Generate JWT with username and token version
    public String generateToken(String username, long tokenVersion) {

        Date issuedAt = new Date();
        Date expiration = new Date(
                issuedAt.getTime() + jwtExpiration
        );

        return Jwts.builder()
                .subject(username)
                .claim("tokenVersion", tokenVersion)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    // Extract username from JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    // Extract expiration date from JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract token version from JW
    public long extractTokenVersion(String token) {
        Object version = extractClaim(token, claims -> claims.get("tokenVersion"));

        if (version instanceof Number number){
            return number.longValue();
        }
        return -1L;
    }


    // Validate JWT username, expiration and token version
    public boolean isTokenValid(String token, String username, long expectedTokenVersion) {

        String extractedUsername = extractUsername(token);

        return extractedUsername.equals(username)
                && !isTokenExpired(token)
                && extractTokenVersion(token) == expectedTokenVersion;
    }
    // Check whether JWT is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    // Extract all JWT claims after verifying its signature
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    // Generic method for extracting individual claims
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
