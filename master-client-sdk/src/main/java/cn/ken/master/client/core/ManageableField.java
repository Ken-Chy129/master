package cn.ken.master.client.core;

import cn.ken.master.client.callback.MasterCallback;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * 受管控字段
 */
@Data
public class ManageableField {

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段描述
     */
    private String desc;

    /**
     * 处理器
     */
    private Class<? extends MasterCallback> callbackClazz;

    /**
     * 字段值
     */
    private Object value;

    /**
     * 保存的Field类，用于修改字段值
     */
    private Field field;

}
