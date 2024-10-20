package cn.ken.master.server.utils;

import cn.ken.master.server.model.entity.MachineDO;

public class AppUtil {

    public static String generateMachineKey(String ipAddress, String port) {
        return ipAddress + ":" + port;
    }

    public static String generateMachineKey(MachineDO machineDO) {
        String ipAddress = machineDO.getIpAddress();
        String port = machineDO.getPort();
        return generateMachineKey(ipAddress, port);
    }
}