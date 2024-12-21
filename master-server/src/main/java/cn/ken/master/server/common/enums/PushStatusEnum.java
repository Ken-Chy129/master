package cn.ken.master.server.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PushStatusEnum {

    SUCCESS(0),
    FAIL(1),
    PARTIAL_FAIL(2),
    ;
    private final int value;
}
