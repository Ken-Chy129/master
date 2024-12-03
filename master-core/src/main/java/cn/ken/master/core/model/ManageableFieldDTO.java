package cn.ken.master.core.model;

import java.io.Serial;
import java.io.Serializable;

public class ManageableFieldDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3556293581160862661L;

    /**
     * 命名空间
     */
    private String namespace;
    /**
     * 字段名
     */
    private String name;

    /**
     * 字段描述
     */
    private String desc;

    /**
     * 字段值
     */
    private String value;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ManageableFieldDTO{" +
                "namespace='" + namespace + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
