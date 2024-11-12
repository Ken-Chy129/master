package cn.ken.master.client.core;

import cn.ken.master.client.annotations.Manageable;
import cn.ken.master.client.annotations.Management;
import cn.ken.master.client.util.MasterUtil;
import cn.ken.master.core.model.ManageableFieldDTO;
import cn.ken.master.core.model.ManagementDTO;
import cn.ken.master.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Ken-Chy129
 * @date 2024/8/11
 */
@Slf4j
public class MasterContainer {

    /**
     * key: namespace, value: {key: name, value:Field}
     */
    private static final Map<String, Map<String, ManageableField>> MANAGEABLE_FIELD_MAP = new HashMap<>();

    private static final List<ManagementDTO> MANAGEMENTS = new ArrayList<>();

    /**
     * 添加变量管控类到上下文
     * @param managementClazz 变量管控类
     */
    public static void addManagement(Class<?> managementClazz) {
        Management managementAnnotation = managementClazz.getDeclaredAnnotation(Management.class);
        assert managementAnnotation != null;
        String clazzName = managementClazz.getName();
        String namespace = StringUtil.isBlank(managementAnnotation.namespace()) ? clazzName : managementAnnotation.namespace();
        String desc = managementAnnotation.desc();

        if (MANAGEABLE_FIELD_MAP.containsKey(namespace)) {
            log.error("namespace already exists");
            return;
        }

        Map<String, ManageableField> fieldMap = new HashMap<>();
        Field[] declaredFields = managementClazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (MasterUtil.isManageable(declaredField)) {
                Manageable manageableAnnotation = declaredField.getDeclaredAnnotation(Manageable.class);
                ManageableField manageableField = buildManageableField(manageableAnnotation, declaredField);
                fieldMap.put(declaredField.getName(), manageableField);
            }
        }
        MANAGEABLE_FIELD_MAP.put(namespace, fieldMap);

        ManagementDTO managementDTO = new ManagementDTO();
        managementDTO.setNamespace(namespace);
        managementDTO.setClassName(clazzName);
        managementDTO.setDesc(desc);
        managementDTO.setManageableFieldList(fieldMap.values().stream().map(manageableField -> convert(clazzName, manageableField)).toList());
        MANAGEMENTS.add(managementDTO);
    }

    public static List<ManagementDTO> getAllManageableFields() {
        return MANAGEMENTS;
    }

    public static ManageableField getManageableField(String namespace, String fieldName) {
        Map<String, ManageableField> fieldMap = MANAGEABLE_FIELD_MAP.get(namespace);
        if (fieldMap == null) {
            return null;
        }
        return fieldMap.get(fieldName);
    }

    private static ManageableFieldDTO convert(String clazzName, ManageableField manageableField) {
        ManageableFieldDTO manageableFieldDTO = new ManageableFieldDTO();
        manageableFieldDTO.setClazzName(clazzName);
        manageableFieldDTO.setDesc(manageableField.getDesc());
        manageableFieldDTO.setName(manageableField.getName());
        return manageableFieldDTO;
    }

    private static ManageableField buildManageableField(Manageable manageableAnnotation, Field field) {
        ManageableField manageableField = new ManageableField();
        manageableField.setDesc(manageableAnnotation.desc());
        manageableField.setCallbackClazz(manageableAnnotation.callback());
        manageableField.setName(field.getName());
        manageableField.setField(field);
        return manageableField;
    }

}
