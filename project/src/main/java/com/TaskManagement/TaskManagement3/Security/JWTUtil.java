package com.TaskManagement.TaskManagement3.Security;
// import com.TaskManagement.TaskManagement3.DTO.*;
import java.security.*;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import com.TaskManagement.TaskManagement3.Entity.UserAuth;
import com.TaskManagement.TaskManagement3.Enum.Permissions;

@Component
public class JWTUtil {
    private final Key key;
    private final long expirationTime = 1000L * 60 * 60 * 12; // 12 hours
    public JWTUtil(){
        String secret=System.getenv("JWT_SECRET");
        if(secret==null|| secret.isEmpty()){
            secret="Replace This with a Secret Key";
        }
        key=Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(UserAuth user){
        Map<String, Object> claims = new HashMap<>();
        Set<Permissions>perm=RoleBasedPermission.getRolePermission().get(user.getRole());
        List<String>permName=perm==null?new ArrayList<>():perm.stream().map(Enum::name).collect(Collectors.toList());
        claims.put("permissions", permName);
        Date now=new Date();
        Date expire=new Date(now.getTime()+expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserOfficialEmail())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key)
                .compact();
    }
 public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                .parseClaimsJws(token);

            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody(); 
    }

    public String getUserEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String extractToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
	
 
}
