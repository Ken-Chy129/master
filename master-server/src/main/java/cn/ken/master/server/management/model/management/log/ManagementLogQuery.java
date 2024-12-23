package cn.ken.master.server.management.model.management.log;

import cn.ken.master.server.common.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagementLogQuery extends BaseQuery {

    private Long appId;

    private Long fieldId;

    private String namespace;

    private String fieldName;

    private Set<String> machineSet;

    private String modifier;

    public static ManagementLogQuery of(ManagementLogRequest request) {
        ManagementLogQuery query = new ManagementLogQuery();
        query.setAppId(request.getAppId());
        query.setFieldId(request.getFieldId());
        query.setFieldName(request.getFieldName());
        query.setModifier(request.getModifier());
        query.setPageIndex(request.getPageIndex());
        query.setPageSize(request.getPageSize());
        return query;
    }
}
