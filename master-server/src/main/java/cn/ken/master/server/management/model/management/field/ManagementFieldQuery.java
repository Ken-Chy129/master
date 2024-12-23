package cn.ken.master.server.management.model.management.field;

import cn.ken.master.server.common.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ManagementFieldQuery extends BaseQuery {

    private Long appId;

    private Long namespaceId;

    private String name;

    public static ManagementFieldQuery of(ManagementFieldRequest request) {
        ManagementFieldQuery managementFieldQuery = new ManagementFieldQuery();
        managementFieldQuery.setAppId(request.getAppId());
        managementFieldQuery.setId(request.getFieldId());
        managementFieldQuery.setNamespaceId(request.getNamespaceId());
        managementFieldQuery.setName(request.getFieldName());
        managementFieldQuery.setPageIndex(request.getPageIndex());
        managementFieldQuery.setPageSize(request.getPageSize());
        return managementFieldQuery;
    }
}
