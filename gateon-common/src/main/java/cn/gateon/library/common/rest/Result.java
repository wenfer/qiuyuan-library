package cn.gateon.library.common.rest;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
@Setter
@Getter
public class Result<T> implements Serializable {

    public static Result SUCCESS = new Result(0, "操作成功");

    private String msg;

    private int code = 0;

    private T data;

    public Result() {
    }

    public boolean success() {
        return this.code == 0;
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> fail(String msg, T t) {
        Result<T> result = new Result<>(-1, msg);
        result.data = t;
        return result;
    }

    public static <T> Result<T> withData(T data) {
        Result<T> result = new Result<>();
        result.data = data;
        return result;
    }


    public static <T> Result<T> fail(String msg) {
        return new Result<>(-1, msg);
    }
}
