package com.exchange.utils;

import com.exchange.constants.JwtConstant;
import com.exchange.properties.JwtProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jwt工具包
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/12 10:03
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtUtil {

    JwtEncoder jwtEncoder;
    JwtDecoder jwtDecoder;
    JwtProperties jwtProperties;

    /**
     * 用于生成accessToken和refreshToken
     * @param username
     * @param claims
     * @return
     */
    public Map<String, Object> generateToken(String username, Map<String, Object> claims) {

        Instant now = Instant.now();

        // 自定义 Claims
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", user.getId());
//        claims.put(JwtConstant.JWt_USERNAME, user.getUsername());
//        claims.put(JwtConstant.JWT_ROLE, user.getRoleSet().stream().map(role -> role.getName()).toList());
//        claims.put(JwtConstant.JWT_PERMISSION, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        // 生成 Access Token
        JwtClaimsSet accessTokenClaims = JwtClaimsSet.builder()
                .issuer("https://coin-exchange") // Token 签发方 TODO 如果不写错URL形式会报错 Unable to convert claim 'iss' of type 'class java.lang.String' to URL.
                .issuedAt(now) // 签发时间
                .expiresAt(now.plus(jwtProperties.getAccessTokenValiditySeconds(), ChronoUnit.SECONDS)) // 过期时间
                .subject(username) // 主题（用户标识）
                .claims(claimsMap -> claimsMap.putAll(claims)) // 自定义数据
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessTokenClaims)).getTokenValue();

        // 生成 Refresh Token
        JwtClaimsSet refreshTokenClaims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(jwtProperties.getRefreshTokenValiditySeconds(), ChronoUnit.SECONDS))
                .subject(username)
                .build();
        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshTokenClaims)).getTokenValue();

        // 返回 Token 数据
        Map<String, Object> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    /**
     * 解析 Token
     * @param token
     * @return
     */
    public Jwt decodeToken(String token) {
        try {
            Jwt decode = jwtDecoder.decode(token);
            System.out.println(decode);
            return decode;
        } catch (JwtException e) {
            throw new IllegalArgumentException("token解析失败！！！", e);
        }
    }

    /**
     * 判断 Token 是否过期
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        try {
            Jwt jwt = decodeToken(token);
            Instant expiresAt = jwt.getExpiresAt();
            return expiresAt != null && expiresAt.isBefore(Instant.now());
        } catch (JwtException e) {
            throw new IllegalArgumentException("token已过期！！！", e);
        }
    }

    /**
     * 从 Token 中获取用户名
     *
     * @param token JWT Token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Jwt jwt = decodeToken(token);
        return jwt.getSubject();
    }

    /**
     * 从 Token 中获取角色列表
     *
     * @param token JWT Token
     * @return 角色列表
     */
    public List<String> getRolesFromToken(String token) {
        Jwt jwt = decodeToken(token);
        return jwt.getClaim(JwtConstant.JWT_ROLE);
    }

    /**
     * 从 Token 中获取权限列表
     *
     * @param token JWT Token
     * @return 权限列表
     */
    public List<String> getPermissionsFromToken(String token) {
        Jwt jwt = decodeToken(token);
        return jwt.getClaim(JwtConstant.JWT_PERMISSION);
    }

    /**
     * 从Token中获取用户id
     * @param token
     * @return 用户id
     */
    public Long getUserIdFromToken(String token) {
        Jwt jwt = decodeToken(token);
        return jwt.getClaim(JwtConstant.JWT_USER_ID);
    }

}
