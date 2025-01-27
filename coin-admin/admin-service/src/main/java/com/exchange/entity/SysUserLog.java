package com.exchange.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 系统日志
 * @TableName sys_user_log
 */
@TableName(value ="sys_user_log")
@Data
public class SysUserLog {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 组
     */
    private String group;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 日志类型 1查询 2修改 3新增 4删除 5导出 6审核
     */
    private Integer type;

    /**
     * 方法
     */
    private String method;

    /**
     * 参数
     */
    private String params;

    /**
     * 时间
     */
    private Long time;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 描述
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime created;

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
        SysUserLog other = (SysUserLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGroup() == null ? other.getGroup() == null : this.getGroup().equals(other.getGroup()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getParams() == null ? other.getParams() == null : this.getParams().equals(other.getParams()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreated() == null ? other.getCreated() == null : this.getCreated().equals(other.getCreated()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGroup() == null) ? 0 : getGroup().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getParams() == null) ? 0 : getParams().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreated() == null) ? 0 : getCreated().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", group=").append(group);
        sb.append(", userId=").append(userId);
        sb.append(", type=").append(type);
        sb.append(", method=").append(method);
        sb.append(", params=").append(params);
        sb.append(", time=").append(time);
        sb.append(", ip=").append(ip);
        sb.append(", description=").append(description);
        sb.append(", remark=").append(remark);
        sb.append(", created=").append(created);
        sb.append("]");
        return sb.toString();
    }
}
