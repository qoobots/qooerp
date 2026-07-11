package com.qoobot.qooerp.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.qooerp.auth.entity.AuthDevice;
import com.qoobot.qooerp.auth.service.AuthDeviceService;
import com.qoobot.qooerp.auth.mapper.AuthDeviceMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【auth_device(用户设备表)】的数据库操作Service实现
* @createDate 2026-03-03 21:36:51
*/
@Service
public class AuthDeviceServiceImpl extends ServiceImpl<AuthDeviceMapper, AuthDevice>
    implements AuthDeviceService{

}




