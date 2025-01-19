package com.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exchange.entity.Config;
import com.exchange.service.ConfigService;
import com.exchange.mapper.ConfigMapper;
import org.springframework.stereotype.Service;

/**
* @author hxm
* @description 针对表【config(平台配置信息)】的数据库操作Service实现
* @createDate 2025-01-19 12:41:08
*/
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config>
    implements ConfigService{

}




