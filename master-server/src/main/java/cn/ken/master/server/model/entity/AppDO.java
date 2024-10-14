package cn.ken.master.server.model.entity;

import cn.ken.master.server.common.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ken-Chy129
 * @date 2024/8/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("master_app")
public class AppDO extends BaseDO {

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 应用状态
     */
    private Integer status;
}
