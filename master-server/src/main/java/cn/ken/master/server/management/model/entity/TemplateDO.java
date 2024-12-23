package cn.ken.master.server.management.model.entity;

import cn.ken.master.server.common.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("master_template")
public class TemplateDO extends BaseDO {

    /**
     * 模板所属应用id
     */
    private Long appId;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板描述
     */
    private String description;
}
