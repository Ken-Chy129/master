package cn.ken.master.core.model;

import java.io.Serial;
import java.io.Serializable;

public class ManageableFieldDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3556293581160862661L;

    /**
     * 字段所属命名空间
     */
    private String clazzName;

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

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
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
                "clazzName='" + clazzName + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
