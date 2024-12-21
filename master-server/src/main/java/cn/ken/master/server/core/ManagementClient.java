package cn.ken.master.server.core;

import cn.ken.master.core.enums.RequestTypeEnum;
import cn.ken.master.core.model.CommandRequest;
import cn.ken.master.core.model.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Slf4j
@Component
public class ManagementClient {

    Logger logger = Logger.getLogger(ManagementClient.class.getName());

    private final Thread.Builder.OfVirtual managementClientGetVTB = Thread.ofVirtual().name("ManagementClient_GET-Thread");
    private final Thread.Builder.OfVirtual managementClientPutVTB = Thread.ofVirtual().name("ManagementClient_PUT-Thread");

    public String queryFieldValue(String ipAddress, Integer port, String namespace, String name) {
        CompletableFuture<String> future = new CompletableFuture<>();
        managementClientGetVTB.start(() -> {
            try (
                    Socket socket = new Socket(ipAddress, port);
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ) {
                CommandRequest request = new CommandRequest();
                request.setNamespace(namespace);
                request.setName(name);
                request.setType(RequestTypeEnum.FIELD_VALUE_GET.getCode());
                out.writeObject(request);
                Result<String> result = (Result<String>) in.readObject();
                future.complete(result.getData());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        try {
            return future.get();
        } catch (Exception e) {
            return null;
        }
    }

    public Result<String> putFieldValue(String ipAddress, Integer port, String namespace, String name, String newValue) {
        CompletableFuture<String> future = new CompletableFuture<>();
        managementClientPutVTB.start(() -> {
            try (
                    Socket socket = new Socket(ipAddress, port);
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ) {
                CommandRequest request = new CommandRequest();
                request.setNamespace(namespace);
                request.setName(name);
                request.setNewValue(newValue);
                request.setType(RequestTypeEnum.FIELD_VALUE_PUT.getCode());
                logger.info(String.format("尝试修改机器%s:%s, namespace:%s, name:%s, newValue:%s", ipAddress, port, namespace, name, newValue));
                out.writeObject(request);
                Result<String> result = (Result<String>) in.readObject();
                future.complete(result.getData());
            } catch (Exception e) {
                logger.warning(e.getMessage());
                future.completeExceptionally(e);
            }
        });
        try {
            return Result.buildSuccess(future.get());
        } catch (Exception e) {
            return Result.buildError(e.getMessage());
        }
    }
}
