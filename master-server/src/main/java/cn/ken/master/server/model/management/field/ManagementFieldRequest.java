package cn.ken.master.server.model.management.field;

import cn.ken.master.core.model.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagementFieldRequest extends PageRequest {

    private String namespaceId;

    private String fieldName;


}
