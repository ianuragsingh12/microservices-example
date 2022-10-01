///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.user.security.jwt;
//
//import com.example.user.exception.JwtTokenMissingException;
//import com.example.user.exception.JwtTokenMalformedException;
//import com.example.user.security.service.UserDetailsImpl;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
///**
// *
// * @author ianur
// */
//@Slf4j
//@Component
//public class JwtUtil_bkp {
//
//    @Value("${jwt.secret:secret}")
//    private String jwtSecret;
//
//    @Value("${jwt.token.validity:2}")
//    private long tokenValidity;
//
//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    public Claims getClaims(final String token) {
//        try {
//            Claims body = Jwts.parser()
//                    .setSigningKey(jwtSecret)
//                    .parseClaimsJws(token).
//                    getBody();
//            return body;
//        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
//            log.error(e.getMessage() + " => " + e);
//        }
//        return null;
//    }
//
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap();
//        return doGenerateToken(claims, userDetails.getUsername());
//    }
//
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity * 1000 * 60 * 60))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
//        try {
//            Jwts.parser()
//                    .setSigningKey(jwtSecret)
//                    .parseClaimsJws(token);
//        } catch (SignatureException ex) {
//            throw new JwtTokenMalformedException("Invalid JWT signature");
//        } catch (MalformedJwtException ex) {
//            throw new JwtTokenMalformedException("Invalid JWT token");
//        } catch (ExpiredJwtException ex) {
//            throw new JwtTokenMalformedException("Expired JWT token");
//        } catch (UnsupportedJwtException ex) {
//            throw new JwtTokenMalformedException("Unsupported JWT token");
//        } catch (IllegalArgumentException ex) {
//            throw new JwtTokenMissingException("JWT claims string is empty.");
//        }
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    public String generateJwtToken(Authentication authentication) {
//
//        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//
//        return Jwts.builder()
//                .setSubject((userPrincipal.getUsername()))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + tokenValidity * 1000 * 60 * 60))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public String getUserNameFromJwtToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(jwtSecret)
//                    .parseClaimsJws(authToken);
//            return true;
//        } catch (SignatureException e) {
//            log.error("Invalid JWT signature: {}", e.getMessage());
//        } catch (MalformedJwtException e) {
//            log.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            log.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            log.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            log.error("JWT claims string is empty: {}", e.getMessage());
//        }
//        return false;
//    }
//}
