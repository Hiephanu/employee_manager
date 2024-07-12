package com.example.ncc_spring.service.auth;

import com.example.ncc_spring.enums.Role;
import com.example.ncc_spring.exception.ExceptionEntity.UnauthorizedException;
import com.example.ncc_spring.model.dto.JwtDecryptData;
import com.example.ncc_spring.model.dto.JwtEncryptData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private static final int TOKEN_EXPIRED = 1000 * 60 * 60;
    private static  final int REFRESH_TOKEN_EXPIRED = 1000 * 36 * 60* 60;
    private static final String SECRET_KEY = "iloveu3000";

    public String genToken(JwtEncryptData jwtEncryptData) {
        Date nowDate = new Date();
        Date expirationDate = new Date(nowDate.getTime() + TOKEN_EXPIRED);
        return Jwts.builder()
                .claim("account_id", jwtEncryptData.getAccountId())
                .claim("employee_id", jwtEncryptData.getEmployeeId())
                .claim("role", jwtEncryptData.getRole().toString())
                .setIssuedAt(nowDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();
    }

    public String genRefreshToken(JwtEncryptData jwtEncryptData) {
        Date nowDate = new Date();
        Date expirationDate = new Date(nowDate.getTime() + REFRESH_TOKEN_EXPIRED);
        return Jwts.builder()
                .claim("account_id", jwtEncryptData.getAccountId())
                .claim("employee_id", jwtEncryptData.getEmployeeId())
                .claim("role", jwtEncryptData.getRole().toString())
                .setIssuedAt(nowDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();
    }


    public String refreshToken(String refreshToken) {
        try {
           if(verifyToken(refreshToken)) {
               Claims refreshClaims = Jwts.parser()
                       .setSigningKey(SECRET_KEY.getBytes())
                       .parseClaimsJws(refreshToken)
                       .getBody();

               JwtEncryptData jwtEncryptData = JwtEncryptData.builder()
                       .accountId(Long.parseLong(refreshClaims.get("account_id").toString()))
                       .employeeId(Long.parseLong(refreshClaims.get("employee_id").toString()))
                       .role(Role.valueOf(refreshClaims.get("role").toString()))
                       .build();

               return genToken(jwtEncryptData);
           } else {
               throw  new UnauthorizedException("Token verify fail");
           }
        } catch (SignatureException e) {
            throw new UnauthorizedException("Invalid token");
        }
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid refresh token");
        }
    }

    public JwtDecryptData decryptToken(String token) {
        try {
            Claims claims =Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
            return JwtDecryptData.builder()
                    .accountId(Long.parseLong(claims.get("account_id").toString()))
                    .employeeId(Long.parseLong(claims.get("employee_id").toString()))
                    .role(Role.valueOf(claims.get("role").toString()))
                    .build();
        } catch (UnauthorizedException e) {
            throw  new UnauthorizedException("Unauthorized");
        }
    }

}
