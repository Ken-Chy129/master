package cn.ken.master.server.management.model.management.template;

import cn.ken.master.core.model.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateFieldRequest extends PageRequest {

    private Long id;

    private Long templateId;

    private Long namespaceId;

    private Long fieldId;

    private String namespace;

    private String fieldName;

    private String fieldValue;
}
