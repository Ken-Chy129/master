package cn.ken.master.server.service;

import cn.ken.master.core.model.ManagementDTO;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.model.entity.FieldDO;
import cn.ken.master.server.model.field.FieldPushReq;
import cn.ken.master.server.model.field.FieldVO;

import java.util.List;

public interface FieldService {

    void insert(FieldDO fieldDO);

    Result<List<FieldDO>> selectByNamespaceId(Long namespaceId);

    Result<List<FieldDO>> selectByAppId(Long appId);

    Result<Boolean> pushFieldValue(FieldPushReq fieldPushReq);

   void registerField(Long appId, List<ManagementDTO> managementDTOList);

    Result<FieldVO> getFieldValue(Long fieldId);

}
