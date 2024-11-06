package cn.ken.master.server.model.field;

import lombok.Data;

@Data
public class FieldPushReq {

    private Long fieldId;

    private String namespace;

    private String value;

    private String pushType;

    private String machineIds;
}
