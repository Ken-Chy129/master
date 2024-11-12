package cn.ken.master.server.core;

import cn.ken.master.server.utils.AppUtil;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class AppContainer {

    private static final Map<String, Socket> SOCKET_MAP = new HashMap<String, Socket>();

    public static Socket getSocket(String machineKey) {
        return SOCKET_MAP.get(machineKey);
    }

    public static void addSocket(final Socket socket) {
        InetAddress inetAddress = socket.getInetAddress();
        String hostAddress = inetAddress.getHostAddress();
        String port = String.valueOf(socket.getPort());
        SOCKET_MAP.put(AppUtil.generateMachineKey(hostAddress, port), socket);
    }

    public static void removeSocket(final Socket socket) {}




}
