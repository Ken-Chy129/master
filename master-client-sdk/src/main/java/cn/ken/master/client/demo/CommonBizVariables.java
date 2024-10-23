package cn.ken.master.client.demo;


import cn.ken.master.client.annotations.ManageableField;
import cn.ken.master.client.annotations.Management;

/**
 * 通用变量管控类
 */
@Management
public class CommonBizVariables {

    @ManageableField(desc = "是否开启xxxx缓存")
    public static Boolean OPEN_XXX_CACHE = Boolean.TRUE;
}
