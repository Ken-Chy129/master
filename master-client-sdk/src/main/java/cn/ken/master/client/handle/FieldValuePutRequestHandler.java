package cn.ken.master.client.handle;

import cn.ken.master.client.core.ManageableField;
import cn.ken.master.client.core.MasterContainer;
import cn.ken.master.core.model.CommandRequest;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.core.util.StringUtil;
import com.alibaba.fastjson2.JSON;

import java.lang.reflect.Field;

/**
 * 变量修改请求处理器
 *
 * @author Ken-Chy129
 * @date 2024/8/11
 */
public class FieldValuePutRequestHandler implements RequestHandleStrategy {

    @Override
    public Result<String> handleRequest(CommandRequest commandRequest) {
        String namespace = commandRequest.getNamespace();
        String name = commandRequest.getName();
        if (StringUtil.isBlank(namespace) || StringUtil.isBlank(name)) {
            return Result.buildError("缺少参数");
        }
        ManageableField manageableField = MasterContainer.getManageableField(namespace, name);
        if (manageableField == null) {
            return Result.buildError(String.format("%s-%s不存在", namespace, name));
        }
        Field field = manageableField.getField();
        try {
            String oldValue = JSON.toJSONString(field.get(null));
            String newValue = commandRequest.getNewValue();
            field.set(null, JSON.parseObject(newValue, field.getType()));
            return Result.buildSuccess(oldValue);
        } catch (IllegalAccessException e) {
            return Result.buildError(e.getMessage());
        }
    }
}
