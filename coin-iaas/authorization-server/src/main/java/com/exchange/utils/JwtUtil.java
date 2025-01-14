package com.exchange.utils;

import com.exchange.constant.JwtConstant;
import com.exchange.entity.SysUser;
import com.exchange.properties.JwtProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
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
     * @param user
     * @return
     */
    public Map<String, Object> generateToken(SysUser user) {

        Instant now = Instant.now();

        // 自定义 Claims
        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", user.getId());
        claims.put(JwtConstant.JWt_USERNAME, user.getUsername());
        claims.put(JwtConstant.JWT_ROLE, user.getRoleSet().stream().map(role -> role.getName()).toList());
        claims.put(JwtConstant.JWT_PERMISSION, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        // 生成 Access Token
        JwtClaimsSet accessTokenClaims = JwtClaimsSet.builder() // TODO 后续把这些值写成动态配置
                .issuer("self") // Token 签发方
                .issuedAt(now) // 签发时间
                .expiresAt(now.plus(jwtProperties.getAccessTokenValiditySeconds(), ChronoUnit.SECONDS)) // 过期时间
                .subject(user.getUsername()) // 主题（用户标识）
                .claims(claimsMap -> claimsMap.putAll(claims)) // 自定义数据
                .build();
        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessTokenClaims)).getTokenValue();

        // 生成 Refresh Token
        JwtClaimsSet refreshTokenClaims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(jwtProperties.getRefreshTokenValiditySeconds(), ChronoUnit.SECONDS)) // TODO 后续把这些过期时间写成动态配置
                .subject(user.getUsername())
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
            return jwtDecoder.decode(token);
        } catch (JwtException e) {
            throw new IllegalArgumentException("token解析失败！！！", e);
        }
    }

    /**
     * 判断 Token 是否过期
     * @param token
     * @return
     */
    public boolean isTokenExpired(String token) {
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
    public Object getRolesFromToken(String token) {
        Jwt jwt = decodeToken(token);
        return jwt.getClaim(JwtConstant.JWT_ROLE);
    }

    /**
     * 从 Token 中获取权限列表
     *
     * @param token JWT Token
     * @return 权限列表
     */
    public Object getPermissionsFromToken(String token) {
        Jwt jwt = decodeToken(token);
        return jwt.getClaim(JwtConstant.JWT_PERMISSION);
    }

}
