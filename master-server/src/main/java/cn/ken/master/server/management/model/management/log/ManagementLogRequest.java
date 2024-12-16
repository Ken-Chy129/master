package cn.ken.master.server.management.model.management.log;

import cn.ken.master.core.model.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagementLogRequest extends PageRequest {

    private Long namespaceId;

    private Long fieldId;

    private String fieldName;

    private String machines;

    private String modifier;
}
