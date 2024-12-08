package cn.ken.master.server.common;

import lombok.Data;

@Data
public class BaseQuery {

    private Long id;

    private int pageIndex;

    private int pageSize;

    public int getStartIndex() {
        return (pageIndex - 1) * pageSize;
    }
}
