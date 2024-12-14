package cn.ken.master.server.core;

public class AuthContext {

    private final static ThreadLocal<Long> THREAD_LOCAL_APP_ID = new ThreadLocal<>();

    public static void setAppId(Long appId) {
        THREAD_LOCAL_APP_ID.set(appId);
    }

    public static Long getAppId() {
        return THREAD_LOCAL_APP_ID.get();
    }

    public static void removeAppId() {
        THREAD_LOCAL_APP_ID.remove();
    }
}
