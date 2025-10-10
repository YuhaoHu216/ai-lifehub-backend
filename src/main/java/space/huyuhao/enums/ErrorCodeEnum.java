package space.huyuhao.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    USER_NOT_FOUND(4001, "用户不存在"),
    INVALID_TOKEN(4002, "Token无效"),
    PERMISSION_DENIED(4003, "没有权限"),
    SEND_EMAIL_FREQUENT(4004,"频繁发送验证码"),
    SHOP_NOT_FOUND(4005,"店铺不存在");

    private final int code;
    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}

