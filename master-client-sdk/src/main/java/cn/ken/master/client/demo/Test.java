package cn.ken.master.client.demo;

import cn.ken.master.client.core.MasterManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ken-Chy129
 * @date 2024/8/11
 */
public class Test {

    public static void main(String[] args) {
        MasterManager manager = new MasterManager();
        manager.setHost("127.0.0.1");
        manager.setPort(12949);
        manager.setAppId(10086L);
        manager.setAccessKey("123213asasdsa123rv3216tht");
        List<Class<?>> classList = new ArrayList<>();
        classList.add(CommonBizVariables.class);
        manager.init(classList);
        try {
            Thread.sleep(12213123123123L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
