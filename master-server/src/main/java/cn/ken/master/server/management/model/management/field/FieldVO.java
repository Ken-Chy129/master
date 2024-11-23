package cn.ken.master.server.management.model.management.field;

import lombok.Data;

import java.util.Map;

@Data
public class FieldVO {

    private String namespace;

    private String className;

    private String description;

    private String name;

    private Map<String, String> machineValueMap;
}
