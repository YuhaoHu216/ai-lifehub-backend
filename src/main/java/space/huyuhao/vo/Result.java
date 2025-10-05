package space.huyuhao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWarDeployment;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {

    private Integer code;
    private String message;
    private Object data;



    /** 成功（无数据） */
    public static Result success() {
        return new Result(200, "操作成功", null);
    }

    /** 成功（带数据） */
    public static  Result success(Object data) {
        return new Result(200, "操作成功", data);
    }
    /** 成功（带消息） */
    public static Result success(String message) {
        return new Result(200, message, null);
    }

    /** 成功（自定义消息和数据） */
    public static  Result success(String message, Object data) {
        return new Result(200, message, data);
    }

    /** 失败（默认消息） */
    public static  Result error() {
        return new Result(500, "操作失败", null);
    }

    /** 失败（自定义消息） */
    public static  Result error(String message) {
        return new Result(500, message, null);
    }

    /** 自定义状态码、消息、数据 */
    public static  Result of(int code, String message, Object data) {
        return new Result(code, message, data);
    }
}
