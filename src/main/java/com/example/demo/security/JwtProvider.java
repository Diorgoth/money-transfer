package com.example.demo.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

     static final long expireTime = 1000 * 60 * 60 * 24;
     static final String securitKey = "maxfiysuzhechkimbilmasin324244323dytfghftucyfhtd56r56d65d";

    public String generateToken(String username){

        Date expireDate = new Date(System.currentTimeMillis()+ expireTime);


        String token =  Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,securitKey)
                .compact();
        return token;


        

    }

    public String getEmailFromToken(String token){

        try {
            String email = Jwts
                    .parser()
                    .setSigningKey(securitKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return email;
        }catch (Exception e){
            return null;
        }

    }



}
