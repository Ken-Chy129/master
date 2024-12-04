package cn.ken.master.server.management.model.management.template;

import cn.ken.master.server.common.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateFieldQuery extends BaseQuery {

    private Long templateId;

    private String namespace;

    private String fieldName;

    public static TemplateFieldQuery of(TemplateFieldRequest request) {
        TemplateFieldQuery templateFieldQuery = new TemplateFieldQuery();
        templateFieldQuery.setTemplateId(request.getTemplateId());
        templateFieldQuery.setNamespace(request.getNamespace());
        templateFieldQuery.setFieldName(request.getFieldName());
        templateFieldQuery.setPageIndex(request.getPageIndex());
        templateFieldQuery.setPageSize(request.getPageSize());
        return templateFieldQuery;
    }
}
