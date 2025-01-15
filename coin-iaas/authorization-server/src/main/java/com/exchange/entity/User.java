package com.exchange.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 普通用户类
 */
@TableName("User")
@Data
public class User implements UserDetails, Serializable {
    /**
     * 自增id
     */
    @TableId
    private Long id;

    /**
     * 用户类型：1-普通用户；2-代理人
     */
    private Integer type;

    /**
     * 用户名
     */
    private String username;

    /**
     * 国际电话区号
     */
    private String countryCode;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 交易密码
     */
    @TableField("paypassword")
    private String payPassword;

    /**
     * 交易密码设置状态
     */
    @TableField("paypass_setting")
    private Integer payPassSetting;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 证件类型:1，身份证；2，军官证；3，护照；4，台湾居民通行证；5，港澳居民通行证；9，其他
     */
    private Integer idCardType;

    /**
     * 认证状态：0-未认证；1-初级实名认证；2-高级实名认证
     */
    private Integer authStatus;

    /**
     * Google令牌秘钥
     */
    private String gaSecret;

    /**
     * Google认证开启状态,0,未启用，1启用
     */
    private Integer gaStatus;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 代理商级别
     */
    private Integer level;

    /**
     * 认证时间
     */
    @TableField("authtime")
    private LocalDateTime authTime;

    /**
     * 登录数
     */
    private Integer logins;

    /**
     * 状态：0，禁用；1，启用
     */
    @TableLogic(value = "1", delval = "0")
    private Integer status;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邀请关系
     */
    private String inviteRelation;

    /**
     * 直接邀请人ID
     */
    @TableField("direct_inviteid")
    private String directInviteId;

    /**
     * 是否开启平台币抵扣手续费：0 否 1是
     */
    private Integer isDeductible;

    /**
     * 审核状态,1通过,2拒绝,0,待审核
     */
    private Integer reviewsStatus;

    /**
     * 代理商拒绝原因
     */
    private String agentNote;

    /**
     * API的KEY
     */
    private String accessKeyId;

    /**
     * API的密钥
     */
    private String accessKeySecret;

    /**
     * 引用认证状态id
     */
    private Long refeAuthId;

    /**
     * 修改时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isEnabled() {
        return this.status == 1;
    }

}
