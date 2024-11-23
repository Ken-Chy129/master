package cn.ken.master.server.management.controller;

import cn.ken.master.core.model.common.PageResult;
import cn.ken.master.server.management.model.entity.ManagementLogDO;
import cn.ken.master.server.management.model.management.log.ManagementLogRequest;
import cn.ken.master.server.management.service.ManagementLogService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/log")
public class ManagementLogController {

    @Resource
    private ManagementLogService managementLogService;

    @GetMapping("/selectByCondition")
    public PageResult<List<ManagementLogDO>> selectByCondition(ManagementLogRequest request) {
        return managementLogService.selectByCondition(request);
    }

}
