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

    private final Thread.Builder.OfVirtual managementClientVTB = Thread.ofVirtual().name("ManagementClient-Thread");

    public String queryFieldValue(String ipAddress, Integer port, String namespace, String name) {
        CompletableFuture<String> future = new CompletableFuture<>();
        managementClientVTB.start(() -> {
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
}
