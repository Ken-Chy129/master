package cn.ken.master.server.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class MybatisObjectHandler implements MetaObjectHandler {

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void insertFill(MetaObject metaObject) {
        String nowDateTime = dtf.format(LocalDateTime.now());
        this.setFieldValByName("gmtCreate", nowDateTime, metaObject);
        this.setFieldValByName("gmtModified", nowDateTime, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String nowDateTime = dtf.format(LocalDateTime.now());
        this.setFieldValByName("gmtModified", nowDateTime, metaObject);
    }
}
