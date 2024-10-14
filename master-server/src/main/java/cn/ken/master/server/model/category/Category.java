package cn.ken.master.server.model.category;

import cn.ken.master.server.common.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("master_category")
public class Category extends BaseDO {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 跳转连接
     */
    private String linkUrl;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 分类状态
     */
    private Integer status;

    /**
     * 是否是根目录
     * @return true: 是, false: 否
     */
    public boolean isRoot() {
        return parentId == -1;
    }

    /**
     * 模型转换
     */
    public CategoryVO toCategoryVO() {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(getId());
        categoryVO.setName(getName());
        categoryVO.setLinkUrl(getLinkUrl());
        categoryVO.setStatus(getStatus());
        return categoryVO;
    }
}
