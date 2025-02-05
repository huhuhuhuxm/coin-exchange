package com.exchange.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exchange.entity.SysUserLog;
import com.exchange.model.R;
import com.exchange.service.SysUserLogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户日志controller
 * @author huxuanming
 * @version 1.0
 * @date 2025/2/5 18:26
 */
@Slf4j
@RestController
@RequestMapping("/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserLogController {

    SysUserLogService sysUserLogService;

    /**
     * 查询日志分页
     * @param current
     * @param size
     * @return
     */
    @Operation(description = "查询日志分页")
    @GetMapping("/sysUserLog")
    public R<Page<SysUserLog>> findByPage(@RequestParam Long current, @RequestParam Long size) {
        Page<SysUserLog> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        page.addOrder(OrderItem.desc("created"));
        Page<SysUserLog> sysUserLogPage = sysUserLogService.page(page);
        log.info("sysUserLogPage：{}", sysUserLogPage.getRecords());
        return R.ok(sysUserLogPage);
    }

}
