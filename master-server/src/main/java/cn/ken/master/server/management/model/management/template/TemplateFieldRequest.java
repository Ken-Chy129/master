package cn.ken.master.server.management.model.management.template;

import cn.ken.master.core.model.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateFieldRequest extends PageRequest {

    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板描述
     */
    private String description;

    private Long templateId;

    private Long namespaceId;

    private Long fieldId;

    private String namespace;

    private String fieldName;

    private String fieldValue;
}
