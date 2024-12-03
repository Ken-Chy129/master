package cn.ken.master.core.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class RegisterResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 528525082392233671L;

    /**
     * 心跳间隔
     */
    private Integer heartBeatInterval;

    /**
     * 用于使用模板进行字段初始化
     */
    private List<ManageableFieldDTO> fields;

    public Integer getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public void setHeartBeatInterval(Integer heartBeatInterval) {
        this.heartBeatInterval = heartBeatInterval;
    }

    public List<ManageableFieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<ManageableFieldDTO> fields) {
        this.fields = fields;
    }
}
