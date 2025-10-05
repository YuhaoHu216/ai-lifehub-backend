package space.huyuhao.enums;

public enum ErrorCode {
    USER_NOT_FOUND(4001, "用户不存在"),
    INVALID_TOKEN(4002, "Token无效"),
    PERMISSION_DENIED(4003, "没有权限"),
    SEND_EMAIL_FREQUENT(4004,"频繁发送验证码");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
}

