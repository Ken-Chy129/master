package cn.ken.master.server.model.management.log;

import lombok.Data;

@Data
public class ManagementLogRequest {

    private String namespace;

    private String name;

    private String machines;

    private String modifier;
}
