package cn.ken.master.server.model.entity;

import cn.ken.master.server.common.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("master_machine")
public class MachineDO extends BaseDO {

    /**
     * 机器所属应用id
     */
    private Long appId;

    /**
     * 机器ip地址
     */
    private String ipAddress;

    /**
     * 机器端口号
     */
    private Integer port;

    /**
     * 机器状态
     */
    private Integer status;
}
