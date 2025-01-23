package com.exchange.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户上下文工具
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/23 19:15
 */
public class UserContextUtil {

    // 定义 ThreadLocal，存储用户相关信息（例如账号和角色）
    private static final ThreadLocal<Map<String, Object>> USER_CONTEXT = ThreadLocal.withInitial(HashMap::new);

    // 定义常量作为 Map 的 key
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_ROLES = "roles";

    /**
     * 设置用户名
     *
     * @param userId 用户名
     */
    public static void setUserID(Long userId) {
        USER_CONTEXT.get().put(KEY_USER_ID, userId);
    }

    /**
     * 获取用户名
     *
     * @return 当前线程存储的用户名
     */
    public static Long getUserId() {
        return (Long) USER_CONTEXT.get().get(KEY_USER_ID);
    }

    /**
     * 设置用户角色
     *
     * @param roles 用户角色
     */
    public static void setRoles(String roles) {
        USER_CONTEXT.get().put(KEY_ROLES, roles);
    }

    /**
     * 获取用户角色
     *
     * @return 当前线程存储的用户角色
     */
    public static String getRoles() {
        return (String) USER_CONTEXT.get().get(KEY_ROLES);
    }

    /**
     * 清除当前线程的用户信息
     */
    public static void clear() {
        USER_CONTEXT.remove();
    }

}
