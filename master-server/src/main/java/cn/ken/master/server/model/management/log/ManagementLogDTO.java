package cn.ken.master.server.model.management.log;

import cn.ken.master.core.model.common.PageRequest;
import lombok.Data;

import java.util.Set;

@Data
public class ManagementLogDTO {

    private String appId;

    private String namespace;

    private String name;

    private Set<String> machineSet;

    private String modifier;

    public static ManagementLogDTO of(ManagementLogRequest request) {
        ManagementLogDTO dto = new ManagementLogDTO();
        dto.setAppId(request.getAppId());
        dto.setNamespace(request.getNamespace());
        dto.setName(request.getName());
        dto.setModifier(request.getModifier());
        return dto;
    }
}
