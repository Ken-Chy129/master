package cn.ken.master.core.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 命名空间
 */
public class Namespace implements Serializable {

    @Serial
    private static final long serialVersionUID = 6261016102135107042L;

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

    @Override
    public String toString() {
        return "Namespace{" +
                "simpleName='" + simpleName + '\'' +
                ", className='" + className + '\'' +
                ", desc='" + desc + '\'' +
                ", manageableFieldList=" + manageableFieldList +
                '}';
    }
}
