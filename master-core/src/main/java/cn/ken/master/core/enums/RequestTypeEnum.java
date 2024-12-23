package cn.ken.master.core.enums;

/**
 * @author Ken-Chy129
 * @date 2024/8/11
 */
public enum RequestTypeEnum {

    /**
     * 心跳包
     */
    HEARTBEAT(0),
    /**
     * 应用上报
     */
    REGISTER(1),
    /**
     * 获取指定变量的值
     */
    FIELD_VALUE_GET(11),
    /**
     * 修改变量
     */
    FIELD_VALUE_PUT(12),
    ;

    private final int code;

    RequestTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static RequestTypeEnum getFromCode(int code) {
        for (RequestTypeEnum value : RequestTypeEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
