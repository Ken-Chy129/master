package cn.ken.master.server.common.base;

import cn.ken.master.server.core.AuthContext;

public class BaseController {

    protected Long getAppId() {
        return AuthContext.getAppId();
    }
}
