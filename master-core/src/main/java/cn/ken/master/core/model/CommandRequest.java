package cn.ken.master.core.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Ken-Chy129
 * @date 2024/8/11
 */
public class CommandRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8334056973957217685L;
    
    /**
     * 请求类型
     */
    private int type;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 变量名
     */
    private String name;

    /**
     * 字段值（修改时使用）
     */
    private String newValue;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
