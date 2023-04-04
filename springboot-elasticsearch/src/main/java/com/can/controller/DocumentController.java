package com.can.controller;

import cn.easyes.core.biz.EsPageInfo;
import com.can.entity.Document;
import com.can.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 请描述类的业务用途
 * @Author zhengcan
 * @Date 2023/4/4 16:25
 * @Version 1.0
 */

@RequestMapping("/document")
@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/hello")
    public String hello(){
        return "hello can!";
    }

    /**
     * 新增
     */
    @PostMapping
    public Integer insert(@RequestBody Document document) {
        return this.documentService.insert(document);
    }

    /**
     * 查询
     */
    @GetMapping("{id}")
    public Document selectById(@PathVariable String id) {
        return this.documentService.selectById(id);
    }

    /**
     * 更新
     */
    @PutMapping
    public Integer updateById(@RequestBody Document document) {
        return this.documentService.updateById(document);
    }

    /**
     * 删除
     */
    @DeleteMapping("{id}")
    public Integer deleteById(@PathVariable String id) {
        return this.documentService.deleteById(id);
    }

    /**
     * 查询列表
     */
    @GetMapping
    public List<Document> selectList() {
        return this.documentService.selectList();
    }

    /**
     * 分页查询
     */
    @GetMapping("paging")
    public EsPageInfo<Document> pageQuery(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                          @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize) {
        return this.documentService.pageQuery(pageIndex, pageSize);
    }

    /**
     * 批量删除
     */
    @DeleteMapping
    public Integer deleteBatchIds(@RequestBody List<String> ids) {
        return this.documentService.deleteBatchIds(ids);
    }





}
