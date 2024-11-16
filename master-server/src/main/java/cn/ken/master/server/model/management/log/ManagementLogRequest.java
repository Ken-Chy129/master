package cn.ken.master.server.model.management.log;

import cn.ken.master.core.model.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagementLogRequest extends PageRequest {

    private String appId;

    private String namespace;

    private String name;

    private String machines;

    private String modifier;
}
