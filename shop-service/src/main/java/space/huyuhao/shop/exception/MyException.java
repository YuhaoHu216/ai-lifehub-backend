package space.huyuhao.shop.exception;


import lombok.Getter;
import space.huyuhao.shop.enums.ErrorCodeEnum;


@Getter
public class MyException extends RuntimeException{

    private final int code;

    public MyException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

}
