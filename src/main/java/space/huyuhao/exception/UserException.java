package space.huyuhao.exception;


import space.huyuhao.enums.ErrorCodeEnum;

public class UserException extends RuntimeException{

    private final int code;

    public UserException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
