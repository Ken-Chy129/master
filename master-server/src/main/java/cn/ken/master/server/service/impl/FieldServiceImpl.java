package cn.ken.master.server.service.impl;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.core.AppContainer;
import cn.ken.master.server.mapper.MachineMapper;
import cn.ken.master.server.model.entity.FieldDO;
import cn.ken.master.server.mapper.FieldMapper;
import cn.ken.master.server.model.field.FieldPushReq;
import cn.ken.master.server.service.FieldService;
import cn.ken.master.server.utils.AppUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldServiceImpl implements FieldService {

    @Resource
    private FieldMapper fieldMapper;

    @Resource
    private MachineMapper machineMapper;

    @Override
    public void insert(FieldDO fieldDO) {
        fieldMapper.insert(fieldDO);
    }

    @Override
    public Result<List<FieldDO>> selectByNamespaceId(Long namespaceId) {
        LambdaQueryWrapper<FieldDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FieldDO::getNamespaceId, namespaceId);
        List<FieldDO> fieldDOS = fieldMapper.selectList(queryWrapper);
        return Result.success(fieldDOS);
    }

    @Override
    public Result<List<FieldDO>> selectByAppId(Long appId) {
        LambdaQueryWrapper<FieldDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FieldDO::getAppId, appId);
        List<FieldDO> fieldDOS = fieldMapper.selectList(queryWrapper);
        return Result.success(fieldDOS);
    }

    @Override
    public Result<Boolean> pushFieldValue(FieldPushReq fieldPushReq) {
        List<String> machineIdList = Arrays.stream(fieldPushReq.getMachineIds().split(",")).toList();
        // todo: 此处socket不应该是保存在map中，即服务端不能一直维持socket连接，而是每次需要发送请求的时候再去建立连接，否则会导致当注册的应用和机器过多时长时间维持着非常多的socket
        // 应用启动时首先向服务端注册应用，并上报变量初始值以及提供的接口的端口号，之后双方建立长连接定期发送心跳包以维持机器状态
        // 服务端在启动时便初始化Socket接收应用注册消息，每次有应用注册的时候便保存到数据库中，之后当控制台发起变更消息时则会找到ip地址和端口号去进行建立连接发送http请求进行修改
        // 应用启动时可以选择是否通过持久化值进行更新当前的变量值
        List<Socket> socketList = machineIdList.stream()
                .map(machineMapper::selectById)
                .map(AppUtil::generateMachineKey)
                .map(AppContainer::getSocket)
                .toList();
//        socketList.stream().forEach(
//                socket -> {
//                    socket.getOutputStream()
//                }
//        );
        return null;
    }
}
