package cn.ken.master.server.common;

import lombok.Data;

@Data
public class BaseQuery {

    private long id;

    private int pageIndex;

    private int pageSize;
}
