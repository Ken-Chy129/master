package cn.ken.master.server.management.model.entity;

import cn.ken.master.server.common.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("master_management_log")
public class ManagementLogDO extends BaseDO {

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 变更的机器，ip:port
     */
    private String machine;

    /**
     * 变更的命名空间
     */
    private String namespace;

    /**
     * 变更的字段id
     */
    private Long fieldId;

    /**
     * 变更的字段名
     */
    private String fieldName;

    /**
     * 变更前的值
     */
    private String beforeValue;

    /**
     * 变更后的值
     */
    private String afterValue;

    /**
     * 变更状态
     */
    private Integer status;

    /**
     * 推送类型
     */
    private Integer pushType;

    /**
     * 场景模板id
     */
    private Long templateId;

    /**
     * 归属父变更id（用于场景模板）
     */
    private Long parentId;

    /**
     * 变更人,todo:定义到baseDo，添加和修改时拦截并获取当前操作的用户填入用户名
     */
    private String modifier;

    /**
     * 详细信息
     */
    private String detailMsg;
}
