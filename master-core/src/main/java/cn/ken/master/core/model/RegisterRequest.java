package cn.ken.master.core.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class RegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6589392299304059964L;

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
    private List<ManagementDTO> managementDTOList;

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

    public List<ManagementDTO> getNamespaceList() {
        return managementDTOList;
    }

    public void setNamespaceList(List<ManagementDTO> managementDTOList) {
        this.managementDTOList = managementDTOList;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "appId=" + appId +
                ", accessKey='" + accessKey + '\'' +
                ", port=" + port +
                ", managementDTOList=" + managementDTOList +
                '}';
    }
}
