package cn.ken.master.server.model.management.field;

import cn.ken.master.server.common.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ManagementFieldQuery extends BaseQuery {

    private Long appId;

    private String namespaceId;

    private String name;

    public static ManagementFieldQuery of(ManagementFieldRequest request) {
        ManagementFieldQuery managementFieldQuery = new ManagementFieldQuery();
        managementFieldQuery.setAppId(request.getAppId());
        managementFieldQuery.setNamespaceId(request.getNamespaceId());
        managementFieldQuery.setName(request.getFieldName());
        managementFieldQuery.setPageIndex(request.getPageIndex());
        managementFieldQuery.setPageSize(request.getPageSize());
        return managementFieldQuery;
    }
}
