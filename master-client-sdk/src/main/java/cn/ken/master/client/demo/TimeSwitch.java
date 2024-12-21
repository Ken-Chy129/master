package cn.ken.master.client.demo;

import cn.ken.master.client.annotations.Manageable;
import cn.ken.master.client.annotations.Management;

@Management(namespace = "TimeSwitch")
public class TimeSwitch {

    @Manageable(desc = "通用阿拉丁超时时间")
    public static Integer COMMON_ALD_TIMEOUT = 100;
}
