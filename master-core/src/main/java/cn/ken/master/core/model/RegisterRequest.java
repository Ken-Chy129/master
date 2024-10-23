package cn.ken.master.core.model;

import java.util.List;

public class RegisterRequest {

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 应用密钥
     */
    private String accessKey;

    /**
     * 应用所提供服务的端口号
     */
    private Integer port;

    /**
     * 命名空间
     */
    private List<Namespace> namespaceList;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public List<Namespace> getNamespaceList() {
        return namespaceList;
    }

    public void setNamespaceList(List<Namespace> namespaceList) {
        this.namespaceList = namespaceList;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "appId=" + appId +
                ", accessKey='" + accessKey + '\'' +
                ", port=" + port +
                ", namespaceList=" + namespaceList +
                '}';
    }
}
