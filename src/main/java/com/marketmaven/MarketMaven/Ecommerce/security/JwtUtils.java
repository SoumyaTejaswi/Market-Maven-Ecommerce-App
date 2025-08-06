package com.marketmaven.MarketMaven.Ecommerce.security;

import com.marketmaven.MarketMaven.Ecommerce.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service //marking the class with Service ensures that a single instance of JwtUtils is created and managed by spring,making it available throughout the application for JWT token operations like generation,validation...
@Slf4j //Simple logging Facade for Java.looging abstraction allows to use various logging frameworks

public class JwtUtils {
    private static final long EXPIRATION_TIME_IN_MILISEC = 1000L * 60L*60L*24L*30L*6L;//token validity period set to expire in 6 months(milliseonds*minutes*hours*24days*30 days*6 months)
    private SecretKey key;//SecretKey instance for JWT Signing

    @Value("${secreteJwtString}") //a variable from the application properties
    private String secreteJwtString; //Make sure the value in the application properties is 32 chars long.
    @PostConstruct
    private void init(){
        byte[] keyBytes = secreteJwtString.getBytes(StandardCharsets.UTF_8);//initializes the secretKey used for signing JWTs by converting  secreteJWTString into byte array
        this.key=new SecretKeySpec(keyBytes,"HmacSHA256");//securing the key with HMAC SHA256 algo to use for genrration and validation.
    }
    //Method to generate our token
    public String generateToken(User user){//Takes a user object as input.   eg:example@domain.com
        String username=user.getEmail();//extracts the email from the user to use it as username //username:example@domain.com
        return generateToken(username);//generates token
        //This method provides a convienent way to generate token directly from user entities.

    }
    //After the username is extracted from the user it comes to this method.
    public String generateToken(String username){//Takes username generated in the previous method as input
        //actual token generation logic
        return Jwts.builder()//template to create token.
                .subject(username)//sets the subject of token as its username. //example@domain.com
                .issuedAt(new Date(System.currentTimeMillis()))//adds the issued at claim,indicating when the token was created. //2025-08-01T10:00:00Z
                .expiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME_IN_MILISEC))//sets the expiration claim of when the token will expire. //2026-01-01T10:00:00Z
                .signWith(key)//signs the token using the key.//signed with the secret key.
                .compact();//for converting it into compact URL Safe string format.
        //token key may look like :eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGRvbWFpbi5jb20iLCJpYXQiOjE2OTU4NTYwMDAsImV4cCI6MTcwMTQ1NjAwMH0.abc123signature
        //Acts as a core implementation that builds the JWT token.
    }
    //Methods to parse the token
    public  String getUsernameFomToken(String token){//extract username from your token.
        return extractClaims(token, Claims::getSubject);//this method retrives the subject claims from the token,which is username
        //extractclaims method parses the token,verifies it using secret key and
    }
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }
    /*
    1.Creates a JWT Parser
    2.verify the token signature with a key
    3.parses its token and retrieves its claim
    4.extracts the payload claim from the token.
    5.applies the provided fun to extract claims*/
    //Implementation to check whether the token is valid
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUsernameFomToken(token);//extracts the username from the token
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));//compares the extracted username with the username from userdetails. and ensures that token is not expired.
    }
    /*Validating the provided token.*/
    private boolean isTokenExpired(String token){//checks if token is expired.
        return extractClaims(token, Claims::getExpiration).before(new Date());//extracts the expiry date from claims and compares with current date.
    }
}
//Utility class JwtUtils for handling JSON Web tokens.
//This class handles :token generation for authentication,token validation,username extraction from tokens,expiration checking.
