package cn.ken.master.server.model.management.log;

import cn.ken.master.server.common.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagementLogQuery extends BaseQuery {

    private Long appId;

    private String namespace;

    private String name;

    private Set<String> machineSet;

    private String modifier;

    public static ManagementLogQuery of(ManagementLogRequest request) {
        ManagementLogQuery query = new ManagementLogQuery();
        query.setAppId(request.getAppId());
        query.setNamespace(request.getNamespace());
        query.setName(request.getName());
        query.setModifier(request.getModifier());
        query.setPageIndex(request.getPageIndex());
        query.setPageSize(request.getPageSize());
        return query;
    }
}
