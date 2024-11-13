package cn.ken.master.server.core;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class AppManager {

    public static void init() {
        ManagementServer managementServer = new ManagementServer();
        managementServer.start();
    }



}
