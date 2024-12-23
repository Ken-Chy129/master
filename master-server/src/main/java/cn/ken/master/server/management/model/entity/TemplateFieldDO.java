package cn.ken.master.server.management.model.entity;

import cn.ken.master.server.common.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("master_template_field")
public class TemplateFieldDO extends BaseDO {

    /**
     * 所属模板id
     */
    private Long templateId;

    /**
     * 字段id
     */
    private Long fieldId;

    /**
     * 字段所属命名空间
     */
    private String namespace;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段值
     */
    private String fieldValue;
}
