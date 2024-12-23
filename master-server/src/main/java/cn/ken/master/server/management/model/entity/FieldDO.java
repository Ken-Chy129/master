package cn.ken.master.server.management.model.entity;

import cn.ken.master.server.common.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("master_field")
public class FieldDO extends BaseDO {

    /**
     * 字段所属应用id
     */
    private Long appId;

    /**
     * 字段所属命名空间id
     */
    private Long namespaceId;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段描述
     */
    private String description;
}
