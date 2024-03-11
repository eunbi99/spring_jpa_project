package com.europehang.europe.jwt;

import com.europehang.europe.common.enums.ErrorCode;
import com.europehang.europe.exception.CustomException;
import com.europehang.europe.jwt.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private final String secretKey;
    private final long accessTokenValidityInSeconds;
    private final long refreshTokenValidityInSeconds;

    private Key key;


    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
                            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds) {
       this.secretKey = secret;
       this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
       this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
    }

    /*
        빈이 생성되고 주입받은 후 secret값을 Base64 Decode해서 key 변수에 할당
     */
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /*
        Authenication객체의 권한정보를 이용해서 Access Token을 생성
     */
    public String createAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));


        Instant now = Instant.now();
        Instant validity = now.plus(accessTokenValidityInSeconds, ChronoUnit.SECONDS);

         String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(Date.from(validity))
                .compact();

         return accessToken;
    }

    // Refresh Token 생성
    public String createRefreshToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Instant now = Instant.now();
        Instant validity = now.plus(refreshTokenValidityInSeconds, ChronoUnit.SECONDS);

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(Date.from(validity))
                .compact();

        return refreshToken;
    }

    /*
     * Token에 담겨있는 정보를 통해 Authentication 객체 리턴
     * - 토큰으로 claims를 만들고 이를 이용해 User 객체를 만들어 Authentication 객체를 리턴
     */

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(),"",authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /*
     * 토큰의 유효성 검증을 수행
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
            throw new CustomException(ErrorCode.INVALID_SIGNATURE);
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
            throw new CustomException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch(IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
            throw new CustomException(ErrorCode.MALFORMED_TOKEN);
        }
    }
}
