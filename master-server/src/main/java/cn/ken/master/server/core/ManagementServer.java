package cn.ken.master.server.core;

import cn.ken.master.core.model.RegisterRequest;
import cn.ken.master.core.model.Result;
import cn.ken.master.server.model.entity.AppDO;
import cn.ken.master.server.service.AppService;
import cn.ken.master.server.service.FieldService;
import cn.ken.master.server.utils.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class ManagementServer extends Thread {

    public ManagementServer() {
        super("ManagementServer-Thread");
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(12949)) {
            AppService appService = ApplicationContextUtil.getBean(AppService.class);
            FieldService fieldService = ApplicationContextUtil.getBean(FieldService.class);
            Builder.OfVirtual appStartVTB = Thread.ofVirtual().name("AppStart-Thread");
            log.info("ManagementServer启动成功，端口号:{}, ip地址:{}", serverSocket.getLocalPort(), serverSocket.getLocalSocketAddress());
            while (true) {
                Socket socket = serverSocket.accept();
                appStartVTB.start(() -> {
                    ObjectOutputStream out = null;
                    ObjectInputStream in;
                    try {
                        out = new ObjectOutputStream(socket.getOutputStream());
                        in = new ObjectInputStream(socket.getInputStream());
                        log.info("应用连接|host:{},port:{}", socket.getInetAddress().getHostAddress(), socket.getPort());
                        //todo:新增事务处理
                        // 1.应用启动
                        RegisterRequest request = (RegisterRequest) in.readObject();
                        Long appId = request.getAppId();
                        String accessKey = request.getAccessKey();
                        String ipAddress = socket.getInetAddress().getHostAddress();
                        Integer port = request.getPort();
                        appService.startAppOnMachine(appId, accessKey, ipAddress, port);
                        // 2.解析受管控字段
                        fieldService.registerField(appId, request.getNamespaceList());
                        out.writeObject(Result.success("success"));
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        if (out != null) {
                            try {
                                out.writeObject(Result.error(e.getMessage()));
                            } catch (IOException ignored) {}
                        }
                    } finally {
                        if (socket != null) {
                            try {
                                socket.close();
                            } catch (IOException ignored) {}
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerApp() {
        AppService appService = ApplicationContextUtil.getBean(AppService.class);
        AppDO appDO = new AppDO();
        appService.insert(appDO);
    }
}
