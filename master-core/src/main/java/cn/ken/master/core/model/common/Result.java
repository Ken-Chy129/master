package cn.ken.master.core.model.common;

import cn.ken.master.core.enums.ErrorCode;
import cn.ken.master.core.enums.SystemErrorCodeEnum;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Ken-Chy129
 * @date 2024/8/11
 */
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 2074303239886411976L;

    private Boolean success;

    private String errorCode;

    private String errorMsg;

    private T data;

    private String traceId;

    private String ip;

    private Collection<String> debugInfo;

    public Result() {
    }

    public Result(Boolean success) {
        this.success = success;
    }

    public static <T> Result<T> buildSuccess() {
        return new Result<>(true);
    }

    public static <T> Result<T> buildSuccess(T data) {
        Result<T> result = new Result<>(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> buildError(String errMsg) {
        Result<T> result = new Result<>(false);
        result.setErrorCode(SystemErrorCodeEnum.BIZ_ERROR.getErrorCode());
        result.setErrorMsg(errMsg);
        return result;
    }

    public static <T> Result<T> buildError(ErrorCode errCode) {
        Result<T> result = new Result<>(false);
        result.setErrorCode(errCode.getErrorCode());
        result.setErrorMsg(errCode.getErrorMessage());
        return result;
    }

    public static <T> Result<T> buildError(ErrorCode errCode, String errMsg) {
        Result<T> result = buildError(errCode);
        result.setDebugInfo(Collections.singleton(errMsg));
        return result;
    }

    public static <T> Result<T> buildError(Exception exception) {
        Result<T> result = buildError(SystemErrorCodeEnum.EXCEPTION_ERROR);
        result.setDebugInfo(Collections.singleton(exception.getMessage()));
        return result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Collection<String> getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(Collection<String> debugInfo) {
        this.debugInfo = debugInfo;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                ", traceId='" + traceId + '\'' +
                ", ip='" + ip + '\'' +
                ", debugInfo=" + debugInfo +
                '}';
    }
}
