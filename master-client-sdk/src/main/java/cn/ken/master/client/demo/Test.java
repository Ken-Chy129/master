package cn.ken.master.client.demo;

import cn.ken.master.client.core.MasterApp;

/**
 * @author Ken-Chy129
 * @date 2024/8/11
 */
public class Test {

    public static void main(String[] args) {
        MasterApp manager = new MasterApp();
        manager.setHost("xxx");
        manager.setPort(12949);
        manager.setAppName("test");
        manager.addMaster(CommonBizVariables.class);
        manager.start();
    }
}
