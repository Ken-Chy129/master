package cn.ken.master.server.core;

import cn.ken.master.core.enums.RequestTypeEnum;
import cn.ken.master.core.model.CommandRequest;
import cn.ken.master.core.model.Result;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

@Component
public class ManagementClient {

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

    public String putFieldValue(String ipAddress, Integer port, String namespace, String name, String newValue) {
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
}
