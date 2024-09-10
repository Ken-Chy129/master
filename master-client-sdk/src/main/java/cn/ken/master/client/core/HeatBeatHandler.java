package cn.ken.master.client.core;

import cn.ken.master.core.model.Request;
import cn.ken.master.core.model.Result;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

/**
 * @author Ken-Chy129
 * @date 2024/8/12
 */
public class HeatBeatHandler extends Thread {

    private final Socket serverSocket;

    public HeatBeatHandler(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }


    @Override
    public void run() {
        try (
                ObjectInputStream in = new ObjectInputStream(serverSocket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(serverSocket.getOutputStream())
        ) {
            while (true) {
                try {
                    Request request = (Request) in.readObject();
                    if (Objects.isNull(request)) {
                        out.writeObject(Result.error("请输入正确的方法名"));
                        continue;
                    }
                } catch (ClassNotFoundException e) {

                }
            }
        } catch (IOException e) {

        }
    }
}
