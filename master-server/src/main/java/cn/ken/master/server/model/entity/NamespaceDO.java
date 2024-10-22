package cn.ken.master.server.model.entity;

import cn.ken.master.core.model.Namespace;
import cn.ken.master.server.common.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("master_namespace")
public class NamespaceDO extends BaseDO {

    /**
     * 命名空间所属应用id
     */
    private Long appId;

    /**
     * 命名空间全类名
     */
    private String className;

    /**
     * 命名空间名称
     */
    private String name;

    /**
     * 命名空间描述
     */
    private String description;

    public static NamespaceDO of(Long appId, Namespace namespace) {
        NamespaceDO namespaceDO = new NamespaceDO();
        namespaceDO.setAppId(appId);
        namespaceDO.setName(namespaceDO.getName());
        namespaceDO.setDescription(namespaceDO.getDescription());
        return namespaceDO;
    }
}
