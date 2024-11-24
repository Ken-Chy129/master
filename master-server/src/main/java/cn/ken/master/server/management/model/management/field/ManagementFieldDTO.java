package cn.ken.master.server.management.model.management.field;

import lombok.Data;

@Data
public class ManagementFieldDTO {

    /**
     * 字段id
     */
    private Long id;

    /**
     * 字段所属命名空间id
     */
    private Long namespaceId;

    /**
     * 字段所属命名空间名称
     */
    private String namespace;

    /**
     * 字段所在类的全类名
     */
    private String className;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段描述
     */
    private String description;
}
