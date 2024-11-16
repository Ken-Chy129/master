package cn.ken.master.core.enums;

public enum SystemErrorCodeEnum implements ErrorCode {

    SUCCESS("SUCCESS", "成功"),
    PARAM_ERROR("PARAM_ERROR", "参数异常"),
    EXCEPTION_ERROR("EXCEPTION_ERROR", "系统内部异常"),
    PERMISSION_ERROR("PERMISSION_ERROR", "没有权限"),
    BIZ_ERROR("BIZ_ERROR", "业务异常"),
    ;

    SystemErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private final String errorCode;

    private final String errorMessage;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
