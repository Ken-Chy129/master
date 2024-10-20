package cn.ken.master.client.handle;

import cn.ken.master.client.annotations.ControllableVariable;
import cn.ken.master.core.model.Request;
import cn.ken.master.core.model.Result;
import cn.ken.master.core.model.ManageableField;
import cn.ken.master.core.constant.RequestParameterKeyConstants;
import cn.ken.master.client.core.MasterContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 变量查询请求处理器
 *
 * @author Ken-Chy129
 * @date 2024/8/11
 */
public class VariableGetRequestHandler implements RequestHandleStrategy {

    @Override
    public Result<?> handleRequest(Request commandRequest) {
        Map<String, String> parameterMap = commandRequest.getParameterMap();
        String namespace = parameterMap.get(RequestParameterKeyConstants.NAMESPACE);
        if (Objects.isNull(namespace)) {
            return Result.error("请输入namespace");
        }
        Map<String, ControllableVariable> controllableVariableMap = MasterContainer.getControllableVariableMapByNamespace(namespace);
        if (Objects.isNull(controllableVariableMap)) {
            return Result.error("namespace不存在");
        }
        List<ManageableField> manageableFieldList = new ArrayList<>();
        for (var variableEntry : controllableVariableMap.entrySet()) {
            String variableName = variableEntry.getKey();
            ControllableVariable controllableVariable = variableEntry.getValue();
            String description = controllableVariable.desc();
            ManageableField manageableField = new ManageableField(namespace, variableName, description);
            manageableFieldList.add(manageableField);
        }
        return Result.success(manageableFieldList);
    }
}
