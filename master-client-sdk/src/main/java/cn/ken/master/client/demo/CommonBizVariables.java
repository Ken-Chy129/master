package cn.ken.master.client.demo;


import cn.ken.master.client.annotations.ManageableField;

/**
 * 通用变量管控类
 */
public class CommonBizVariables {

    @ManageableField(desc = "是否开启xxxx缓存")
    public static Boolean OPEN_XXX_CACHE = Boolean.TRUE;
}
