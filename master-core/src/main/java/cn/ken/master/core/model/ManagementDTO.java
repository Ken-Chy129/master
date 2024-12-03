package cn.ken.master.core.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 命名空间
 */
public class ManagementDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6261016102135107042L;

    /**
     * 命名空间
     */
    private String namespace;

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
    private List<ManageableFieldDTO> manageableFieldList;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

    public List<ManageableFieldDTO> getManageableFieldList() {
        return manageableFieldList;
    }

    public void setManageableFieldList(List<ManageableFieldDTO> manageableFieldList) {
        this.manageableFieldList = manageableFieldList;
    }

    @Override
    public String toString() {
        return "ManagementDTO{" +
                "namespace='" + namespace + '\'' +
                ", className='" + className + '\'' +
                ", desc='" + desc + '\'' +
                ", manageableFieldList=" + manageableFieldList +
                '}';
    }
}
