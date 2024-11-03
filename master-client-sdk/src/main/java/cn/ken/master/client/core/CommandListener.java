package cn.ken.master.client.core;

import cn.ken.master.client.handle.RequestHandleStrategy;
import cn.ken.master.client.handle.RequestHandlerFactory;
import cn.ken.master.core.model.CommandRequest;
import cn.ken.master.core.model.Result;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * 命令监听处理
 *
 * @author Ken-Chy129
 * @date 2024/8/11
 */
@Slf4j
public class CommandListener extends Thread {

    private final Integer serverProviderPort;

    private ExecutorService executorService;

    private int state;

    // todo:状态枚举

    public CommandListener(Integer serverProviderPort) {
        this.state = 0;
        this.serverProviderPort = serverProviderPort;
    }

    @Override
    public void run() {
        try (
                ServerSocket serverSocket = new ServerSocket(serverProviderPort);
        ) {
            while (state == 0) {
                try (
                    Socket socket = serverSocket.accept();
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
                ) {
                    CommandRequest commandRequest = (CommandRequest) in.readObject();
                    if (Objects.isNull(commandRequest)) {
                        continue;
                    }
                    RequestHandleStrategy requestHandler = RequestHandlerFactory.getRequestHandler(commandRequest.getType());
                    if (Objects.isNull(requestHandler)) {
                        out.writeObject(Result.error("请求的方法不存在"));
                        continue;
                    }
                    Result<?> result = requestHandler.handleRequest(commandRequest);
                    out.writeObject(result);
                } catch (Exception e) {
                    log.error("指令处理异常:{}", e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopListen() {
        state = -1;
    }

//    /**
//     * 将服务端发送的请求进行解析
//     * @param rawRequest 格式为：/请求方法名?参数名=值&参数名=值
//     * @return 解析后的请求对象
//     */
//    private CommandRequest parseRequest(String rawRequest) {
//        CommandRequest commandRequest = new CommandRequest();
//        if (StringUtil.isBlank(rawRequest)) {
//            return commandRequest;
//        }
//        String[] split = rawRequest.split("\\?");
//        if (split.length != 2) {
//            return commandRequest;
//        }
//        String[] requestNameSplit = split[0].split("/");
//        String requestName = requestNameSplit.length == 2 ? requestNameSplit[1] : requestNameSplit[0];
//        commandRequest.setRequestName(requestName.strip());
//        String parameters = split[1];
//        for (String parameter : parameters.split("&")) {
//            if (StringUtil.isBlank(parameter)) {
//                continue;
//            }
//            String[] pair = parameter.split("=");
//            if (pair.length != 2) {
//                continue;
//            }
//            String key = pair[0].strip();
//            String value = URLDecoder.decode(pair[1].strip(), StandardCharsets.UTF_8);
//            commandRequest.addParameter(key, value);
//        }
//        return commandRequest;
//    }
}
