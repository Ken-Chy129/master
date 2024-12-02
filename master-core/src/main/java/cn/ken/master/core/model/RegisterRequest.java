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
     * 是否使用默认模板的值初始化受管控变量的值
     * todo: 可以使用其他模板，控制台提供控制每台机器的配置文件
     */
    private Boolean useTemplateValue;

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

    public Boolean getUseTemplateValue() {
        return useTemplateValue;
    }

    public void setUseTemplateValue(Boolean useTemplateValue) {
        this.useTemplateValue = useTemplateValue;
    }

    public List<ManagementDTO> getManagementDTOList() {
        return managementDTOList;
    }

    public void setManagementDTOList(List<ManagementDTO> managementDTOList) {
        this.managementDTOList = managementDTOList;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "appId=" + appId +
                ", accessKey='" + accessKey + '\'' +
                ", port=" + port +
                ", useTemplateValue=" + useTemplateValue +
                ", managementDTOList=" + managementDTOList +
                '}';
    }
}
