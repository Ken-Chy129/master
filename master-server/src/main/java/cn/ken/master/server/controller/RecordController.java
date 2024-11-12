package cn.ken.master.server.controller;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.model.entity.RecordDO;
import cn.ken.master.server.service.RecordService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("record")
public class RecordController {

    @Resource
    private RecordService recordService;

    /**
     *
     */
    @GetMapping("/findByFieldId")
    public Result<List<RecordDO>> findByFieldId(@RequestParam("fieldId") Long fieldId) {
        return recordService.selectByFieldId(fieldId);
    }

}
