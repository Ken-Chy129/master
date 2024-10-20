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

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Field> getManageableFieldList() {
        return manageableFieldList;
    }

    public void setManageableFieldList(List<Field> manageableFieldList) {
        this.manageableFieldList = manageableFieldList;
    }
}
