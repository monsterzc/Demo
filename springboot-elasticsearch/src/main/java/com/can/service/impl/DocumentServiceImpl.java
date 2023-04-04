package com.can.service.impl;

import cn.easyes.core.biz.EsPageInfo;
import cn.easyes.core.conditions.LambdaEsQueryWrapper;
import com.can.entity.Document;
import com.can.mapper.DocumentMapper;
import com.can.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 请描述类的业务用途
 * @Author zhengcan
 * @Date 2023/4/4 13:46
 * @Version 1.0
 */

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public Integer insert(Document document) {
        return documentMapper.insert(document);
    }

    @Override
    public Document selectById(String id) {
        return documentMapper.selectById(id);
    }

    @Override
    public Integer updateById(Document document) {
        return documentMapper.updateById(document);
    }

    @Override
    public Integer deleteById(String id) {
        return documentMapper.deleteById(id);
    }

    @Override
    public List<Document> selectList() {
        LambdaEsQueryWrapper<Document> documentWrapper = new LambdaEsQueryWrapper<>();
        return documentMapper.selectList(documentWrapper);
    }

    @Override
    public EsPageInfo<Document> pageQuery(Integer pageIndex, Integer pageSize) {
        LambdaEsQueryWrapper<Document> documentWrapper = new LambdaEsQueryWrapper<>();
        return documentMapper.pageQuery(documentWrapper,pageIndex,pageSize);
    }

    @Override
    public Integer deleteBatchIds(List<String> ids) {
        return documentMapper.deleteBatchIds(ids);
    }
}
