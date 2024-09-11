package cn.ken.master.client.core;

import cn.ken.master.core.enums.RequestTypeEnum;
import cn.ken.master.core.model.Request;

import java.io.*;
import java.net.Socket;

/**
 * @author Ken-Chy129
 * @date 2024/8/12
 */
public class HeatBeatHandler extends Thread {

    private final MasterApp masterApp;

    public HeatBeatHandler(MasterApp masterApp) {
        this.masterApp = masterApp;
    }

    @Override
    public void run() {
        Socket socket = masterApp.getSocket();
        Integer heartbeatInterval = masterApp.getHeartbeatInterval();
        try (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            while (masterApp.getRunning()) {
                Request request = new Request();
                request.setRequestCode(RequestTypeEnum.HEARTBEAT.getCode());
                out.writeObject(request);
                try {
                    Thread.sleep(heartbeatInterval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {

        }
    }
}
