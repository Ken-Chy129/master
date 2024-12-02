package cn.ken.master.client.demo;


import cn.ken.master.client.annotations.Manageable;
import cn.ken.master.client.annotations.Management;

/**
 * 通用变量管控类
 */
@Management
public class CommonBizVariables {

    @Manageable(desc = "是否开启xxxx缓存啊")
    public static Boolean OPEN_XXX_CACHE = Boolean.TRUE;

    @Manageable(desc = "白名单ip地址")
    public static String WHITE_IP_ADDRESS = "127.0.0.1";

    @Manageable(desc = "测试字段")
    public static String TEST_FIELD = "test";
}
