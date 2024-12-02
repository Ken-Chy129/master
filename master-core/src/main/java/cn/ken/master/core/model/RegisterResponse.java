package cn.ken.master.core.model;

import java.util.List;

public class RegisterResponse {

    /**
     * 心跳间隔
     */
    private Integer heartBeatInterval;

    /**
     * 用于使用模板进行字段初始化
     */
    private List<ManagementDTO> fields;

    public Integer getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public void setHeartBeatInterval(Integer heartBeatInterval) {
        this.heartBeatInterval = heartBeatInterval;
    }

    public List<ManagementDTO> getFields() {
        return fields;
    }

    public void setFields(List<ManagementDTO> fields) {
        this.fields = fields;
    }
}
