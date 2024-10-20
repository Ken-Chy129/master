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
            MachineService machineService = ApplicationContextUtil.getBean(MachineService.class);
            NamespaceService namespaceService = ApplicationContextUtil.getBean(NamespaceService.class);
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
                            // 1.判断应用是否存在并鉴权
                            RegisterRequest request = (RegisterRequest) ois.readObject();
                            Long appId = request.getAppId();
                            String accessKey = request.getAccessKey();
                            Result<Boolean> authorityResult = appService.startApp(appId, accessKey);
                            if (!authorityResult.getSuccess()) {
                                throw new AppStartException(authorityResult.getMessage());
                            }
                            // 2.将当前机器绑定到应用上
                            String ipAddress = socket.getInetAddress().getHostAddress();
                            Integer port = request.getPort();
                            machineService.bindMachine(appId, ipAddress, port);
                            // 3.解析命名空间
                            List<Namespace> namespaceList = request.getNamespaceList();
                            List<NamespaceDO> namespaceDOList = namespaceList.stream()
                                    .map(namespace -> NamespaceDO.of(appId, namespace))
                                    .toList();
                            namespaceService.

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
