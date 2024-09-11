package cn.ken.master.client.core;

import cn.ken.master.client.annotations.ControllableVariable;
import cn.ken.master.client.annotations.Master;
import cn.ken.master.client.exception.MasterException;
import cn.ken.master.client.util.MasterUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Ken-Chy129
 * @date 2024/8/11
 */
public class MasterContainer {

    /**
     * key: appName, value: MasterApp对象
     */
    private static final Map<String, MasterApp> MASTER_APP_MAP = new HashMap<>();

    /**
     * key: appName_namespace, value: Master注解对象
     */
    private static final Map<String, Master> MASTER_ANNOTATION_MAP = new HashMap<>();

    /**
     * key: appName_namespace, value: {key: fieldName, value:Field}
     */
    private static final Map<String, Map<String, ControllableVariable>> CONTROLLABLE_VARIABLE_ANNOTATION_MAP = new HashMap<>();

    /**
     * key: appName_namespace, value: {key: fieldName, value:Field}
     */
    private static final Map<String, Map<String, Field>> NASTER_FIELD_MAP = new HashMap<>();

    /**
     * 应用注册
     */
    public static void registerApp(MasterApp masterApp) {
        // 1.参数校验
        String appName = masterApp.getAppName();
        if (MASTER_APP_MAP.containsKey(appName)) {
            throw new MasterException(appName + " is already registered!");
        }
        // 2.添加MasterApp
        MASTER_APP_MAP.put(masterApp.getAppName(), masterApp);
        // 3.遍历app所有变量管控类，保存相关信息
        List<Class<?>> masterClazzList = masterApp.getMasterClazzList();
        for (Class<?> masterClazz : masterClazzList) {
            // 3.1.保存Master注解信息
            Master annotation = masterClazz.getDeclaredAnnotation(Master.class);
            String namespace = getNamespace(masterClazz, annotation);
            MASTER_ANNOTATION_MAP.put(MasterUtil.generateAppNamespaceKey(appName, namespace), annotation);

            // 3.2.保存ControllableVariable注解信息
            Map<String, Field> fieldMap = new HashMap<>();
            Map<String, ControllableVariable> variableMap = new HashMap<>();
            Field[] declaredFields = masterClazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (MasterUtil.isMasterVariable(declaredField)) {
                    fieldMap.put(declaredField.getName(), declaredField);
                    variableMap.put(declaredField.getName(), declaredField.getDeclaredAnnotation(ControllableVariable.class));
                }
            }
            CONTROLLABLE_VARIABLE_ANNOTATION_MAP.put(MasterUtil.generateAppNamespaceKey(appName, namespace), variableMap);
            NASTER_FIELD_MAP.put(MasterUtil.generateAppNamespaceKey(appName, namespace), fieldMap);
        }
    }

    private static String getNamespace(Class<?> masterClazz, Master annotation) {
        assert annotation != null;
        String namespace = annotation.namespace();
        if (Objects.isNull(namespace)) {
            // 没有设置则使用全类名
            namespace = masterClazz.getName();
        }
        return namespace;
    }

    /**
     * 移除应用
     */
    public static void removeApp(String appName) {
        MasterApp masterApp = MASTER_APP_MAP.remove(appName);
        List<Class<?>> masterClazzList = masterApp.getMasterClazzList();
        for (Class<?> masterClazz : masterClazzList) {
            Master annotation = masterClazz.getDeclaredAnnotation(Master.class);
            String namespace = getNamespace(masterClazz, annotation);
            MASTER_ANNOTATION_MAP.remove(MasterUtil.generateAppNamespaceKey(appName, namespace));
            CONTROLLABLE_VARIABLE_ANNOTATION_MAP.remove(MasterUtil.generateAppNamespaceKey(appName, namespace));
            NASTER_FIELD_MAP.remove(MasterUtil.generateAppNamespaceKey(appName, namespace));
        }
    }

    /**
     * 根据appName获取MasterApp对象
     */
    public static MasterApp getMasterApp(String appName) {
        return MASTER_APP_MAP.get(appName);
    }

    public static Map<String, Master> getMasterAnnotationMap() {
        return MASTER_ANNOTATION_MAP;
    }

    public static Map<String, Map<String, ControllableVariable>> getAllControllableVariableMap() {
        return CONTROLLABLE_VARIABLE_ANNOTATION_MAP;
    }

    public static Map<String, ControllableVariable> getControllableVariableMapByNamespace(String namespace) {
        return CONTROLLABLE_VARIABLE_ANNOTATION_MAP.get(namespace);
    }

    public static Map<String, Map<String, Field>> getNasterFieldMap() {
        return NASTER_FIELD_MAP;
    }

    /**
     * 查询指定变量管控类所有可管控字段
     */
    public static Map<String, Field> getMasterField(String namespace) {
        return NASTER_FIELD_MAP.get(namespace);
    }

    /**
     * 查询指定变量管控类指定字段
     */
    public static Field getMasterField(String namespace, String name) {
        return Optional.ofNullable(NASTER_FIELD_MAP.get(namespace))
                .map(fieldMap -> fieldMap.get(name))
                .orElse(null);
    }

    /**
     * 查询指定变量管控类指定字段的值
     */
    public static Object getMasterFieldValue(String namespace, String name) {
        return Optional.ofNullable(NASTER_FIELD_MAP.get(namespace))
                .map(fieldMap -> fieldMap.get(name))
                .map(filed -> {
                    try {
                        return filed.get(null);
                    } catch (IllegalAccessException e) {
                        throw new MasterException(e.getMessage());
                    }
                })
                .orElseThrow(null);
    }

}
