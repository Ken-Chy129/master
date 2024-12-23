package cn.ken.master.client.handle;

import cn.ken.master.core.model.CommandRequest;
import cn.ken.master.core.model.common.Result;

/**
 * 请求处理策略类
 *
 * @author Ken-Chy129
 * @date 2024/8/11
 */
public interface RequestHandleStrategy {

    Result<?> handleRequest(CommandRequest commandRequest);
}
