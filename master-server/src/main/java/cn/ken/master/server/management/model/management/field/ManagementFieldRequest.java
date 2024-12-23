package cn.ken.master.server.management.model.management.field;

import cn.ken.master.core.model.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagementFieldRequest extends PageRequest {

    private Long fieldId;

    private Long namespaceId;

    private String fieldName;

}
