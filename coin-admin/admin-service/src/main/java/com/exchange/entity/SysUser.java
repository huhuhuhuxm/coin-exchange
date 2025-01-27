package com.exchange.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 平台用户
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String fullname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0-无效； 1-有效；
     */
    private Integer status;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 修改人
     */
    private Long modifyBy;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 修改时间
     */
    private LocalDateTime lastUpdateTime;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysUser other = (SysUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getFullname() == null ? other.getFullname() == null : this.getFullname().equals(other.getFullname()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getModifyBy() == null ? other.getModifyBy() == null : this.getModifyBy().equals(other.getModifyBy()))
            && (this.getCreated() == null ? other.getCreated() == null : this.getCreated().equals(other.getCreated()))
            && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getFullname() == null) ? 0 : getFullname().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getModifyBy() == null) ? 0 : getModifyBy().hashCode());
        result = prime * result + ((getCreated() == null) ? 0 : getCreated().hashCode());
        result = prime * result + ((getLastUpdateTime() == null) ? 0 : getLastUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", fullname=").append(fullname);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", status=").append(status);
        sb.append(", createBy=").append(createBy);
        sb.append(", modifyBy=").append(modifyBy);
        sb.append(", created=").append(created);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append("]");
        return sb.toString();
    }
}
