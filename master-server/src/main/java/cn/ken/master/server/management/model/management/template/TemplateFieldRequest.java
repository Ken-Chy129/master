package cn.ken.master.server.management.model.management.template;

import cn.ken.master.core.model.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateFieldRequest extends PageRequest {

    private Long templateId;

    private String namespace;

    private String fieldName;
}
