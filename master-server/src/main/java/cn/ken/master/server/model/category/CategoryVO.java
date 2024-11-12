package cn.ken.master.server.model.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {

    private Long id;

    private String key;

    private String label;

    private List<CategoryVO> children;

    private String linkUrl;

    private String icon;

    private Boolean disabled;
}
