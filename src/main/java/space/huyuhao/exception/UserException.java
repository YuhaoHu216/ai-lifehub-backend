package space.huyuhao.exception;

import space.huyuhao.enums.ErrorCode;

public class UserException extends RuntimeException{

    private final int code;

    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
