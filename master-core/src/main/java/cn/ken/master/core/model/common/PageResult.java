package cn.ken.master.core.model.common;

import cn.ken.master.core.enums.ErrorCode;
import cn.ken.master.core.enums.SystemErrorCodeEnum;

import java.util.Collections;
import java.util.List;

public class PageResult<T> extends Result<T> {

    private Integer pageIndex;

    private Integer pageSize;

    private Long total;

    private Long pageCount;

    private boolean hasMore;

    public PageResult() {
    }

    public PageResult(Boolean success) {
        super(success);
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public static <T> PageResult<T> buildSuccess() {
        PageResult<T> result = new PageResult<>(true);
        result.setTotal(0L);
        result.setHasMore(false);
        return result;
    }

    public static  <P extends List<?>> PageResult<P> buildSuccess(P data) {
        PageResult<P> result = new PageResult<>(true);
        if (data == null || data.isEmpty()) {
            result.setHasMore(false);
            result.setTotal(0L);
            return result;
        }
        result.setData(data);
        return result;
    }

    public static <T> PageResult<T> buildError(String errMsg) {
        PageResult<T> result = new PageResult<>(false);
        result.setErrorMsg(errMsg);
        return result;
    }

    public static <T> PageResult<T> buildError(ErrorCode errCode) {
        PageResult<T> result = new PageResult<>(false);
        result.setErrorCode(errCode.getErrorCode());
        result.setErrorMsg(errCode.getErrorMessage());
        return result;
    }

    public static <T> PageResult<T> buildError(RuntimeException exception) {
        PageResult<T> result = buildError(SystemErrorCodeEnum.EXCEPTION_ERROR);
        result.setDebugInfo(Collections.singleton(exception.getMessage()));
        return result;
    }
}
