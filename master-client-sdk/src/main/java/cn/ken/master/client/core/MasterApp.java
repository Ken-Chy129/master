package cn.ken.master.client.core;

import cn.ken.master.client.exception.MasterErrorCode;
import cn.ken.master.client.exception.MasterException;
import cn.ken.master.client.util.MasterUtil;
import cn.ken.master.core.enums.RequestTypeEnum;
import cn.ken.master.core.model.Request;
import cn.ken.master.core.model.Result;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Master管理类
 * @author Ken-Chy129
 * @date 2024/8/11
 */
@Slf4j
public class MasterApp {

    /**
     * 服务端主机地址
     */
    private String host;

    /**
     * 服务端端口号
     */
    private Integer port;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 心跳间隔时间
     */
    private Integer heartbeatInterval = 5 * 1000;

    /**
     * Master应用运行状态
     */
    private Boolean running;

    /**
     * 与服务端连接的套接字
     */
    private Socket socket;

    /**
     * 应用的变量管控类列表
     */
    private List<Class<?>> masterClazzList;

    public MasterApp() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(Integer heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public List<Class<?>> getMasterClazzList() {
        return masterClazzList;
    }

    public void setMasterClazzList(List<Class<?>> masterClazzList) {
        this.masterClazzList = masterClazzList;
    }

    @Override
    public String toString() {
        return "MasterApp{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", appName='" + appName + '\'' +
                ", description='" + description + '\'' +
                ", heartbeatInterval=" + heartbeatInterval +
                ", running=" + running +
                ", socket=" + socket +
                ", masterClazzList=" + masterClazzList +
                '}';
    }

    /**
     * 向服务端注册变量管控类
     * @param clazz 变量管控类
     */
    public void addMaster(Class<?> clazz) {
        // 校验clazz
        if (!MasterUtil.isMasterClazz(clazz)) {
            throw new MasterException(MasterErrorCode.REGISTERED_CLAZZ_INVALID);
        }
        // 保存变量控制类
        masterClazzList.add(clazz);
    }

    public void start() {
        // 1.参数校验
        checkStartKeyParameters();
        Socket socket;
        try {
            // 2.连接服务端
            socket = new Socket(host, port);
        } catch (IOException e) {
            throw new MasterException(e.getMessage());
        }
        this.socket = socket;
        this.running = true;
        // 3.向服务端上报应用名
        reportToServer(socket);
        // 4.注册到到Master容器进行管理
        MasterContainer.registerApp(this);
        // 5.启动线程监听服务端命令
        new CommandListener(this).start();
        // 6.启动线程定时发送心跳检测包
        new HeatBeatHandler(this).start();
    }

    /**
     * 向服务端上报应用启动，会发送应用名和应用描述
     * @param socket 与服务端的连接套接字
     */
    private void reportToServer(Socket socket) {
        try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            Request request = new Request();
            request.setRequestCode(RequestTypeEnum.REGISTER.getCode());
            request.setParameterMap(Map.of("appName", appName, "description", description));
            out.writeObject(request);
            Result<Integer> result = (Result<Integer>) in.readObject();
            if (Objects.isNull(result) || !result.getSuccess() || Objects.isNull(result.getData())) {
                // todo：重试
                throw new MasterException("应用上报失败");
            }
            this.heartbeatInterval = result.getData();
        } catch (ClassNotFoundException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 校验启动关键参数
     */
    private void checkStartKeyParameters() {
        if (Objects.isNull(appName)) {
            throw new MasterException("MasterApp缺少应用名，无法正常启动");
        }
        if (Objects.isNull(host) || Objects.isNull(port)) {
            throw new MasterException("MasterApp缺少服务端信息，无法正常启动");
        }
        if (Objects.isNull(masterClazzList) || masterClazzList.isEmpty()) {
            throw new MasterException("MasterApp缺少管控类配置，无法正常启动");
        }
    }

}
