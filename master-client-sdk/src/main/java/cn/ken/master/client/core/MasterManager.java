package cn.ken.master.client.core;

import cn.ken.master.client.exception.MasterErrorCode;
import cn.ken.master.client.exception.MasterException;
import cn.ken.master.client.util.MasterUtil;
import cn.ken.master.core.model.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Master管理类
 * @author Ken-Chy129
 * @date 2024/8/11
 */
@Data
@Slf4j
public class MasterManager {

    /**
     * 服务提供暴露端口
     */
    private Integer serverProviderPort = 8888;

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

    public void init(List<Class<?>> clazzList) {
        if (host == null || port == null || appId == null || accessKey == null) {
            log.error("缺少核心参数，Master初始化失败");
            throw new MasterException(MasterErrorCode.MASTER_INIT_ERROR);
        }

        for (Class<?> aClass : clazzList) {
            // 1.校验clazz
            if (!MasterUtil.isMasterClazz(aClass)) {
                log.error("{} 未被@MasterConfig注解标识, 无法注册为变量管理类", aClass.getName());
                continue;
            }
            // 2.保存变量控制类
            MasterContainer.addManagement(aClass);
        }

        // 启动线程监听服务端命令
        new CommandListener(serverProviderPort).start();

        // 应用启动注册
        Integer heatBeatInterval = register();
        if (heatBeatInterval > 0) {
            // 定时发送心跳包

        }
    }

    private Integer register() {
        try (
                Socket socket = new Socket(host, port);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            log.info("成功连接服务端,服务端ip地址为:{},端口号为:{},本地端口号为:{},", host, port, socket.getLocalPort());
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setAppId(appId);
            registerRequest.setPort(serverProviderPort);
            registerRequest.setAccessKey(accessKey);
            registerRequest.setNamespaceList(MasterContainer.getAllManageableFields());
            out.writeObject(registerRequest);
            Result<?> result = (Result<?>) in.readObject();
            if (result.isSuccess()) {
                log.info("应用注册成功");
                return (Integer) result.getData();
            } else {
                log.error("应用注册失败:{}", result.getMessage());
            }
        } catch (Exception e) {
            log.error("应用注册发生异常:{}", e.getMessage());
            throw new MasterException(e.getMessage());
        }
        return -1;
    }

}
