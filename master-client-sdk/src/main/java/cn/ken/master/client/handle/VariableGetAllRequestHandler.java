package cn.ken.master.client.handle;

import cn.ken.master.client.annotations.ManageableField;
import cn.ken.master.client.core.MasterContainer;
import cn.ken.master.core.model.Request;
import cn.ken.master.core.model.Result;
import cn.ken.master.core.model.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ken-Chy129
 * @date 2024/8/23
 */
public class VariableGetAllRequestHandler implements RequestHandleStrategy {

    @Override
    public Result<List<Field>> handleRequest(Request request) {
        Map<String, Map<String, ManageableField>> masterVariableMap = MasterContainer.getAllControllableVariableMap();
        List<Field> manageableFieldList = new ArrayList<>();
        for (var entry : masterVariableMap.entrySet()) {
            String namespace = entry.getKey();
            Map<String, ManageableField> variableMap = entry.getValue();
            for (var variableEntry : variableMap.entrySet()) {
                String variableName = variableEntry.getKey();
                ManageableField controllableVariable = variableEntry.getValue();
                String description = controllableVariable.desc();
                Field field = new Field();
                field.setName(variableName);
                field.setNamespace(namespace);
                field.setDesc(description);
                manageableFieldList.add(field);
            }
        }
        return Result.success(manageableFieldList);
    }
}
