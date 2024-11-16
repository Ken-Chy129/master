package cn.ken.master.server.handler;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.common.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Result<?> exceptionHandle(BusinessException e) {
        log.error("业务异常:{}", e.getMessage());
        return Result.buildError(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandle(Exception e) {
        log.error("接口出现内部异常:{}", e.getMessage());
        return Result.buildError(e);
    }
}
