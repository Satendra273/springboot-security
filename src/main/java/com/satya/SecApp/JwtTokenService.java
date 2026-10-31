package com.satya.SecApp;

import com.satya.SecApp.entity.Role;
import com.satya.SecApp.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secret ;


    public String generateToken(User user){

      Set<Role> roles = user.getRoles();
      List<String> roleNames = roles.stream()
                .map(Role::getName)
                .toList();

      long now = System.currentTimeMillis();

      return Jwts.builder()
              .subject(user.getEmail())
              .claim("userId" , user.getId())
              .claim("roles",roles)
              .issuedAt(new Date(now))
              .expiration(
                      new Date(now + 1000*60*30)
              ).signWith(getKey(secret))
              .compact();

  }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseSignedClaims(token);
    }


    public Claims getClaims(String token) {
        return validateToken(token)
                .getPayload();
    }

    public String getUsername(String token) {

        return getClaims(token)
                .getSubject();
    }

    public Long getUserId(String token) {

        return getClaims(token)
                .get("userId", Long.class);
    }

    public List<String> getRoles(String token) {

        return getClaims(token)
                .get("roles", List.class);
    }


  public SecretKey getKey(String secret){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

    public Date getExpiration(String token) {

        return getClaims(token)
                .getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return getExpiration(token)
                .before(new Date());
    }

}
