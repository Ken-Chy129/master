package cn.ken.master.client.core;

import cn.ken.master.client.exception.MasterErrorCode;
import cn.ken.master.client.exception.MasterException;
import cn.ken.master.client.util.MasterUtil;
import cn.ken.master.core.enums.RequestTypeEnum;
import cn.ken.master.core.model.Request;
import cn.ken.master.core.model.Result;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Master管理类
 * @author Ken-Chy129
 * @date 2024/8/11
 */
@Slf4j
public class MasterApp {

    // 服务端主机地址
    private String host;

    // 服务端端口号
    private Integer port;

    // 应用名
    private String appName;

    // 应用描述
    private String description;

    // 心跳间隔时间
    private Integer heartbeatInterval;

    public MasterApp() {
    }

    public MasterApp(String host, Integer port, String appName) {
        this.host = host;
        this.port = port;
        this.appName = appName;
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "MasterApp{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", appName='" + appName + '\'' +
                '}';
    }

    /**
     * 向服务端注册变量管控类
     * @param clazz 变量管控类
     */
    public void addMaster(Class<?> clazz) {
        // 1.校验clazz
        if (!MasterUtil.isMasterClazz(clazz)) {
            throw new MasterException(MasterErrorCode.REGISTERED_CLAZZ_INVALID);
        }
        // 3.保存变量控制类
        // todo:先保存到类的成员变量，调用start时保存到Containner，加上appName区分
        MasterContainer.addVariableMaster(clazz);
    }

    public void start() {
        // 1.连接服务端
        try (
                Socket serverSocket = new Socket(host, port)
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

    private void register(Socket socket) {
        try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            // 向服务端上报应用启动
            Request request = new Request();
            request.setRequestCode(RequestTypeEnum.REGISTER.getCode());
            request.setParameterMap(Map.of("appName", appName, "description", description));
            out.writeObject(request);
            Result<String> result = (Result<String>) in.readObject();
            if (!result.getSuccess()) {
                // todo：重试
                throw new MasterException("应用注册失败");
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
