package cn.ken.master.server.core;

import cn.ken.master.core.model.Namespace;
import cn.ken.master.core.model.RegisterRequest;
import cn.ken.master.core.model.Result;
import cn.ken.master.server.common.AppStartException;
import cn.ken.master.server.model.entity.AppDO;
import cn.ken.master.server.model.entity.NamespaceDO;
import cn.ken.master.server.service.AppService;
import cn.ken.master.server.service.FieldService;
import cn.ken.master.server.service.MachineService;
import cn.ken.master.server.service.NamespaceService;
import cn.ken.master.server.utils.ApplicationContextUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    appStartVTB.start(() -> {
                        PrintWriter pw = null;
                        try {
                            //todo:新增事务处理
                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            pw = new PrintWriter(socket.getOutputStream(), true);
                            // 1.应用启动
                            RegisterRequest request = (RegisterRequest) ois.readObject();
                            Long appId = request.getAppId();
                            String accessKey = request.getAccessKey();
                            String ipAddress = socket.getInetAddress().getHostAddress();
                            Integer port = request.getPort();
                            Result<Boolean> authorityResult = appService.startAppOnMachine(appId, accessKey, ipAddress, port);
                            if (!authorityResult.getSuccess()) {
                                throw new AppStartException(authorityResult.getMessage());
                            }
                            // 2.解析受管控字段
                            Result<Boolean> booleanResult = fieldService.registerField(appId, request.getNamespaceList());

                        } catch (AppStartException e) {
                            assert pw != null;
                            pw.print(Result.error(e.getMessage()));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
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
