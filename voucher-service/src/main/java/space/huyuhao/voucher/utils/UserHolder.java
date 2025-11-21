package space.huyuhao.voucher.utils;


import space.huyuhao.voucher.dto.UserDTO;

/**
 * <p>
 *     用户信息存储
 * </p>
 * @author  hyh
 * @since 2025-10-06
 */
public class UserHolder {
    private static final ThreadLocal<UserDTO> userHolder = new ThreadLocal<UserDTO>();
    // 保存用户信息
    public static void saveUser(UserDTO user) {userHolder.set(user);}
    // 获取用户信息
    public static UserDTO getUser() {return userHolder.get();}
    // 删除用户信息
    public static void removeUser() {userHolder.remove();}
}
