package cn.ken.master.server.advice;

import cn.ken.master.core.model.common.PageRequest;
import cn.ken.master.core.model.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Aspect
@Component
public class PageResultAdvice {

    @Around("pageResultPointcut()")
    public Object debug(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if (result instanceof PageResult<?> pageResult) {
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof PageRequest pageRequest) {
                    pageResult.setPageIndex(pageRequest.getPageIndex());
                    pageResult.setPageSize(pageRequest.getPageSize());
                    Long total = pageResult.getTotal();
                    if (Objects.nonNull(total)) {
                        pageResult.setPageCount((long) Math.ceil((double) total / pageRequest.getPageSize()));
                        pageResult.setHasMore(pageResult.getPageCount() > pageRequest.getPageIndex());
                    }
                }
            }
        }
        return result;
    }

    @Pointcut("execution(* cn.ken.master.server.*.controller.*.*(..))")
    public void pageResultPointcut() {}

}

