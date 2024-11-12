package cn.ken.master.server.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MachineStatus {

    RUNNING(1),

    ;

    private final int code;
}
