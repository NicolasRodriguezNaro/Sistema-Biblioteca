package com.universidad.biblioteca.backend_server.jwt;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtService {
    private final Key signingKey;
    public JwtService(String secret) { this.signingKey = Keys.hmacShaKeyFor(secret.getBytes()); }

    public String generateAccessToken(Integer idUsuario, int tokenVersion, long ttlSeconds, Map<String,Object> extra) {
        Instant now = Instant.now();
        JwtBuilder b = Jwts.builder()
                .setSubject(String.valueOf(idUsuario))
                .claim("tv", tokenVersion)
                .addClaims(extra == null ? Map.of() : extra)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(ttlSeconds)))
                .signWith(signingKey, SignatureAlgorithm.HS256);
        return b.compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
    }
    
    public Integer getUserId(String token) { return Integer.valueOf(parse(token).getBody().getSubject()); }
    public Integer getTokenVersion(String token) { return parse(token).getBody().get("tv", Integer.class); }
}
