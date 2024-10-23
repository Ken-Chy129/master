package cn.ken.master.client.handle;

import cn.ken.master.client.annotations.ManageableField;
import cn.ken.master.core.model.Field;
import cn.ken.master.core.model.Request;
import cn.ken.master.core.model.Result;
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
        Map<String, ManageableField> controllableVariableMap = MasterContainer.getControllableVariableMapByNamespace(namespace);
        if (Objects.isNull(controllableVariableMap)) {
            return Result.error("namespace不存在");
        }
        List<Field> manageableFieldList = new ArrayList<>();
        for (var variableEntry : controllableVariableMap.entrySet()) {
            String variableName = variableEntry.getKey();
            ManageableField controllableVariable = variableEntry.getValue();
            String description = controllableVariable.desc();
            Field field = new Field();
            field.setName(variableName);
            field.setDesc(description);
            manageableFieldList.add(field);
        }
        return Result.success(manageableFieldList);
    }
}
