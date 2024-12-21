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

    /**
     * 来源模板id
     */
    private Long fromTemplateId;

    private Long templateId;

    private Long templateFieldId;

    private Long namespaceId;

    private Long fieldId;

    private String namespace;

    private String fieldName;

    private String fieldValue;

    /**
     * 推送方式
     */
    private String machineType;

    /**
     * 推送的机器
     */
    private String machines;

}
