package cn.ken.master.client.util;

import cn.ken.master.client.annotations.ManageableField;
import cn.ken.master.client.annotations.Management;
import cn.ken.master.client.exception.MasterErrorCode;
import cn.ken.master.client.exception.MasterException;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author Ken-Chy129
 * @date 2024/8/12
 */
public class MasterUtil {

    /**
     * 校验类是否为合法的变量管控类
     */
    public static boolean isMasterClazz(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            return false;
        }
        Management declaredAnnotation = clazz.getDeclaredAnnotation(Management.class);
        return declaredAnnotation != null;
    }

    /**
     * 校验变量是否可被管控
     */
    public static boolean isMasterVariable(Field field) {
        if (Objects.isNull(field)) {
            return false;
        }
        ManageableField declaredAnnotation = field.getDeclaredAnnotation(ManageableField.class);
        if (Objects.isNull(declaredAnnotation)) {
            return false;
        }
        if (!field.canAccess(null)) {
            throw new MasterException(String.format(MasterErrorCode.CONTROLLABLE_VARIABLE_INACCESSIBLE.getMsg(), field.getName()));
        }
        return true;
    }
}
