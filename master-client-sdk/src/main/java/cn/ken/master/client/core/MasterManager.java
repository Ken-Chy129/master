package cn.ken.master.client.core;

import cn.ken.master.client.exception.MasterErrorCode;
import cn.ken.master.client.exception.MasterException;
import cn.ken.master.client.util.MasterUtil;
import cn.ken.master.core.enums.RequestTypeEnum;
import cn.ken.master.core.model.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Master管理类
 * @author Ken-Chy129
 * @date 2024/8/11
 */
@Slf4j
public class MasterManager {

    /**
     * 服务端主机地址
     */
    private String host;

    /**
     * 服务端端口号
     */
    private Integer port;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 应用密钥
     */
    private String accessKey;

    public MasterManager() {
    }

    public MasterManager(String host, Integer port, Long appId, String accessKey) {
        this.host = host;
        this.port = port;
        this.appId = appId;
        this.accessKey = accessKey;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @Override
    public String toString() {
        return "MasterManager{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", appId=" + appId +
                ", accessKey='" + accessKey + '\'' +
                '}';
    }

    public void init(List<Class<?>> clazzList) {
        for (Class<?> aClass : clazzList) {
            // 1.校验clazz
            if (!MasterUtil.isMasterClazz(aClass)) {
                throw new MasterException(MasterErrorCode.REGISTERED_CLAZZ_INVALID);
            }
            // 2.保存变量控制类
            MasterContainer.addVariableMaster(aClass);
        }
        if (check()) {
            log.error("");
            return;
        }
        // 1.连接服务端
        try (
                Socket serverSocket = new Socket(host, port);
        ) {
            // 1.发送应用名
            register(serverSocket);

            // 2.启动线程监听服务端命令
            new CommandListener(serverSocket).start();

            // 4.启动线程定时发送心跳检测包
            new HeatBeatHandler(serverSocket).start();
        } catch (UnknownHostException e) {
            throw new MasterException(MasterErrorCode.SERVER_HOST_INVALID);
        } catch (IllegalArgumentException e) {
            throw new MasterException(MasterErrorCode.SERVER_PORT_INVALID);
        } catch (IOException e) {
            throw new MasterException(MasterErrorCode.SERVER_CONNECT_ERROR);
        }
    }

    private boolean check() {
        if (host == null || port == null || appId == null || accessKey == null) {
            return false;
        }
        return true;
    }

    private void register(Socket socket) throws IOException {
        try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setAppId(appId);
            registerRequest.setPort(8888);
            registerRequest.setAccessKey("test");
//            MasterContainer.
            Namespace namespace = new Namespace();
            namespace.setClassName("cn.ken.test.TestSwitch");
            namespace.setDesc("this is a demo switch");
            namespace.setSimpleName("TestSwitch");
            Field field = new Field();
            field.setNamespace("cn.ken.test.TestSwitch");
            field.setName("Test");
            field.setDesc("this is a demo field");
            field.setValue("hello");
            namespace.setManageableFieldList(List.of(field));
            registerRequest.setNamespaceList(List.of(namespace));
            out.writeObject(registerRequest);
            Result<String> result = (Result<String>) in.readObject();
            System.out.println(result);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
