package com.example.accessibilityserviceproject.http;


import java.io.Serializable;

/**
 * @ClassName BaseResponse
 * @Description 服务器返回的数据格式
 * @Author xinqi
 * @Date 2018-12-20 12:18
 * @Version 1.0
 */
public class NewBaseResponse<T> implements Serializable {
    private int code;
    private String message;
    private String reason;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**
     * 请求是否成功，失败提示服务器返回的message
     */
    public boolean isSuccess() {
        return isSuccess(true);
    }

    public boolean isSuccess(boolean showMsg) {
        boolean success;
        switch (getCode()) {
            case 1:
                //请求成功
                success = true;
                break;
            default:
                //其他情况，如密码不正确，用户不存在等..
                success = false;
                String msg = getMessage();
        }
        return success;
    }

    public <E> NewBaseResponse<E> converter(E data) {
        NewBaseResponse<E> result = new NewBaseResponse<>();
        result.code = code;
        result.message = message;
        result.reason = reason;
        result.data = data;
        return result;
    }
}
