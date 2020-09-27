package com.fundoo.user.utility;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String createJwtToken(String email) {
        return Jwts.builder().claim("email", email).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(1200).toInstant()))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public Object verify(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return claimsJws.getBody().get("email");
        } catch (ExpiredJwtException e) {
            return "Token Expired";
        }
        catch (Exception e) {
            return " Some other exception in JWT parsing ";
        }
    }
}