package space.huyuhao.utils;

import space.huyuhao.po.User;

/**
 * <p>
 *     用户信息存储
 * </p>
 * @author  hyh
 * @since 2025-10-06
 */
public class UserHolder {
    private static final ThreadLocal<User> userHolder = new ThreadLocal<User>();
    // 保存用户信息
    public static void saveUser(User user) {userHolder.set(user);}
    // 获取用户信息
    public static User getUser() {return userHolder.get();}
    // 删除用户信息
    public static void removeUser() {userHolder.remove();}
}
