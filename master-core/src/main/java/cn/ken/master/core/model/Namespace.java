package cn.ken.master.core.model;

import java.util.List;

/**
 * 命名空间
 */
public class Namespace {

    /**
     * 别名
     */
    private String simpleName;

    /**
     * 全类名
     */
    private String className;

    /**
     * 描述
     */
    private String desc;

    /**
     * 命名空间下可管理的字段
     */
    private List<Field> manageableFieldList;
}
