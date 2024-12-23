package cn.ken.master.client.demo;

import cn.ken.master.client.annotations.Manageable;
import cn.ken.master.client.annotations.Management;

/**
 * 时间相关字段
 */
@Management(namespace = "TimeField")
public class TimeField {

    @Manageable(desc = "通用阿拉丁超时时间")
    public static Integer COMMON_ALD_TIMEOUT = 100;
}
