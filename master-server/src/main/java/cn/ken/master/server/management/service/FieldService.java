package cn.ken.master.server.management.service;

import cn.ken.master.core.model.ManagementDTO;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.management.model.management.field.FieldPushReq;
import cn.ken.master.server.management.model.management.field.FieldVO;
import cn.ken.master.server.management.model.management.field.ManagementFieldDTO;
import cn.ken.master.server.management.model.management.field.ManagementFieldRequest;

import java.util.List;

public interface FieldService {


    Result<Boolean> pushFieldValue(FieldPushReq fieldPushReq);

    void registerField(Long appId, List<ManagementDTO> managementDTOList);

    Result<FieldVO> getFieldValue(Long fieldId);

    Result<List<ManagementFieldDTO>> selectByCondition(ManagementFieldRequest request);
}
