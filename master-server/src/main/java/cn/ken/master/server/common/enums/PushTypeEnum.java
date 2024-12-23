package cn.ken.master.server.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PushTypeEnum {

    FIELD(0),
    TEMPLATE(1),
    ;

    private final int value;
}
