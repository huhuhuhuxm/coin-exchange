package com.exchange.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exchange.entity.Notice;
import com.exchange.model.R;
import com.exchange.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/2/7 18:31
 */
@Slf4j
@RestController
@RequestMapping("/notices")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeController {

    NoticeService noticeService;

    /**
     * 公告管理分页查询
     * @param current
     * @param size
     * @param startTime
     * @param endTime
     * @param title
     * @param status
     * @return
     */
    @Operation(description = "公告管理分页查询")
    @GetMapping
    public R<Page<Notice>> findByPage(@RequestParam Long current,
                                      @RequestParam Long size,
                                      @RequestParam(required = false) String startTime,
                                      @RequestParam(required = false) String endTime,
                                      @RequestParam(required = false) String title,
                                      @RequestParam(required = false) Integer status) {
        Page<Notice> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        page.addOrder(OrderItem.desc("created"));

        Page<Notice> noticePage = noticeService.page(page, new LambdaQueryWrapper<Notice>()
                .like(StringUtils.hasText(title), Notice::getTitle, title)
                .between(StringUtils.hasText(startTime) && StringUtils.hasText(endTime), Notice::getCreated, startTime, endTime)
                .eq(status!=null, Notice::getStatus, status));
        return R.ok(noticePage);
    }


    /**
     * 新增公告
     * @param notice
     * @return
     */
    @Operation(description = "新增公告")
    @PostMapping
    public R addNotice(@RequestBody Notice notice) {
        // 默认状态为启用
        notice.setStatus(1);
        boolean isSave = noticeService.save(notice);
        if (isSave) {
            return R.ok("新增成功！！！");
        }
        return R.fail("新增失败！！！");
    }

    /**
     * 编辑公告
     * @param notice
     * @return
     */
    @Operation(description = "编辑公告")
    @PatchMapping
    public R<String> updateNotice(@RequestBody Notice notice) {
        // TODO 目前无法解决 com.fasterxml.jackson.databind.exc.InvalidDefinitionException:
        // Java 8 date/time type `java.time.LocalDateTime` not supported by default:
        // add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling
        // (through reference chain: java.util.HashMap["notice"]->com.exchange.entity.Notice["lastUpdateTime"]) 只能把notice中的时间字段变成null
        notice.setLastUpdateTime(null);
        notice.setCreated(null);
        boolean isUpdate = noticeService.updateById(notice);
        if (isUpdate) {
            return R.ok("编辑成功！！！");
        }
        return R.fail("编辑失败！！！");
    }

    /**
     * 修改公告状态
     * @param notice
     * @return
     */
    @Operation(description = "修改公告状态")
    @PostMapping("/updateStatus")
    public R<String> updateStatus(Notice notice) {
        boolean isUpdate = noticeService.updateById(notice);
        if (isUpdate) {
            return R.ok("状态修改成功！！！");
        }
        return R.fail("状态修改失败！！！");
    }


    /**
     * 批量删除公告
     * @param ids
     * @return
     */
    @Operation(description = "批量删除公告")
    @PostMapping("/delete")
    public R<String> deleteByIds(@RequestBody List<Long> ids) {
        boolean isDelete = noticeService.removeBatchByIds(ids);
        if (isDelete) {
            return R.ok("删除成功！！！");
        }
        return R.fail("删除失败！！！");
    }

}
