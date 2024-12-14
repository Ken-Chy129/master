package cn.ken.master.server.interceptor;


import cn.ken.master.core.enums.SystemErrorCodeEnum;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.core.util.StringUtil;
import cn.ken.master.server.core.AuthContext;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <pre>
 * <p>拦截需要登录的接口，根据ThreadLocal中是否存有值来判断用户是否登录</p>
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2022/12/10 14:40
 */
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestPath = request.getServletPath();
        // todo:不需要校验appId的接口
        if (requestPath.startsWith("/app")) {
            return true;
        }
        String appId = request.getParameter("appId");
        AuthContext.setAppId(Long.parseLong(appId));
        if (StringUtil.isBlank(appId)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(JSON.toJSONString(Result.buildError(SystemErrorCodeEnum.PERMISSION_ERROR)));
            System.out.println("error");
            return false;
        }
        // todo:权限校验
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContext.removeAppId();
    }
}
