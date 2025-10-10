package space.huyuhao.exception;


import lombok.Getter;
import space.huyuhao.enums.ErrorCodeEnum;

@Getter
public class MyException extends RuntimeException{

    private final int code;

    public MyException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

}
