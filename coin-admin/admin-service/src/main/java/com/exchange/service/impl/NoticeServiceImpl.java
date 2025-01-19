package com.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exchange.entity.Notice;
import com.exchange.service.NoticeService;
import com.exchange.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

/**
* @author hxm
* @description 针对表【notice(系统资讯公告信息)】的数据库操作Service实现
* @createDate 2025-01-19 12:41:08
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
    implements NoticeService{

}




