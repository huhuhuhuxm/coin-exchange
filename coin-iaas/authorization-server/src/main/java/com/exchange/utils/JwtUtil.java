package com.exchange.utils;

import com.exchange.entity.SysUser;
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
        claims.put("username", user.getUsername());
        claims.put("roles", user.getRoleSet().stream().map(role -> role.getName()).toList());
        claims.put("permissions", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        // 生成 Access Token
        JwtClaimsSet accessTokenClaims = JwtClaimsSet.builder()
                .issuer("self") // Token 签发方
                .issuedAt(now) // 签发时间
                .expiresAt(now.plus(1, ChronoUnit.HOURS)) // 过期时间
                .subject(user.getUsername()) // 主题（用户标识）
                .claims(claimsMap -> claimsMap.putAll(claims)) // 自定义数据
                .build();
        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessTokenClaims)).getTokenValue();

        // 生成 Refresh Token
        JwtClaimsSet refreshTokenClaims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .subject(user.getUsername())
                .build();
        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshTokenClaims)).getTokenValue();

        // 返回 Token 数据
        Map<String, Object> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }
}
