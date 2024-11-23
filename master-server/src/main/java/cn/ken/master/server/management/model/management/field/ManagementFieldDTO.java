package cn.ken.master.server.management.model.management.field;

import lombok.Data;

@Data
public class ManagementFieldDTO {

    /**
     * 字段所属命名空间id
     */
    private String namespace;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段描述
     */
    private String description;
}
