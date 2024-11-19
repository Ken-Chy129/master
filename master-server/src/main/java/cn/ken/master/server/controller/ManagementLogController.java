package cn.ken.master.server.controller;

import cn.ken.master.core.model.common.PageResult;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.model.entity.ManagementLogDO;
import cn.ken.master.server.model.management.log.ManagementLogRequest;
import cn.ken.master.server.service.ManagementLogService;
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
