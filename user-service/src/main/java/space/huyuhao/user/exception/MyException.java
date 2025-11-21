package space.huyuhao.user.exception;


import lombok.Getter;
import space.huyuhao.user.enums.ErrorCodeEnum;


@Getter
public class MyException extends RuntimeException{

    private final int code;

    public MyException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

}
