package com.can.service;

import cn.easyes.core.biz.EsPageInfo;
import com.can.entity.Document;

import java.util.List;

/**
 * @Description 请描述类的业务用途
 * @Author zhengcan
 * @Date 2023/4/4 13:42
 * @Version 1.0
 */

public interface DocumentService {

    /**
     * 查询
     * @param document
     * @return Integer
     */
    Integer insert(Document document);


    /**
     * 根据ID查询
     * @date 15:48 2023/4/4
     * @param id
     * @return com.can.entity.Document
    **/
    Document selectById(String id);



    /**
     * 根据id修改
     * @param document
     * @return java.lang.Integer
    **/
    Integer updateById(Document document);


    /**
     * 根据ID删除
     * @param id
     * @return java.lang.Integer
    **/
    Integer deleteById(String id);

    /***
     * 查询全部
     * @param
     * @return: java.util.List<com.can.entity.Document>
     * @author: zhengcan
     * @date: 16:12 2023/4/4
    */
    List<Document> selectList();

    /***
     * 分页查询
     * @author: zhengcan
     * @date: 16:19 2023/4/4
     * @param pageIndex
     * @param pageSize
     * @return: cn.easyes.core.biz.EsPageInfo<com.can.entity.Document>
    */
    EsPageInfo<Document> pageQuery(Integer pageIndex, Integer pageSize);

    /***
     * 批量删除
     * @author: zhengcan
     * @date: 16:14 2023/4/4
     * @param ids
     * @return: java.lang.Integer
    */
    Integer deleteBatchIds(List<String> ids);



}
