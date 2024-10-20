package cn.ken.master.client.handle;

import cn.ken.master.client.annotations.ControllableVariable;
import cn.ken.master.client.core.MasterContainer;
import cn.ken.master.core.model.Request;
import cn.ken.master.core.model.Result;
import cn.ken.master.core.model.ManageableField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ken-Chy129
 * @date 2024/8/23
 */
public class VariableGetAllRequestHandler implements RequestHandleStrategy {

    @Override
    public Result<List<ManageableField>> handleRequest(Request request) {
        Map<String, Map<String, ControllableVariable>> masterVariableMap = MasterContainer.getAllControllableVariableMap();
        List<ManageableField> manageableFieldList = new ArrayList<>();
        for (var entry : masterVariableMap.entrySet()) {
            String namespace = entry.getKey();
            Map<String, ControllableVariable> variableMap = entry.getValue();
            for (var variableEntry : variableMap.entrySet()) {
                String variableName = variableEntry.getKey();
                ControllableVariable controllableVariable = variableEntry.getValue();
                String description = controllableVariable.desc();
                ManageableField manageableField = new ManageableField(namespace, variableName, description);
                manageableFieldList.add(manageableField);
            }
        }
        return Result.success(manageableFieldList);
    }
}
