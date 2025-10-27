package com.freq.arvand.arvand.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtSevice {
    private UserDetailService userDetailService;

    public JwtSevice(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    private long expiration = 1000 * 60 * 15;
    private String secret = "Ece72f7qGgGHtL3iDzu4G9dTG8JEehJxq7Vdn7ElDuo";
    public boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllCalims(jwt);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllCalims(String jwt) {
        return
                Jwts.parserBuilder()
                        .setSigningKey(getSignInkey())
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
    }

    private Key getSignInkey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUserName(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }
    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = userDetailService.loadUserByUsername(extractUserName(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
