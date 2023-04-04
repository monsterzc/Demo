package com.can.entity;

import cn.easyes.annotation.HighLight;
import cn.easyes.annotation.IndexField;
import cn.easyes.annotation.IndexId;
import cn.easyes.annotation.IndexName;
import cn.easyes.annotation.rely.FieldType;
import cn.easyes.annotation.rely.IdType;
import lombok.Data;

/**
 * @Description 请描述类的业务用途
 * @Author zhengcan
 * @Date 2023/4/4 13:38
 * @Version 1.0
 */

@Data
@IndexName("document")
public class Document {

    /**
     * es中的唯一id
     */
    @IndexId(type = IdType.NONE)
    private String id;

    /**
     * 文档标题
     */
    @IndexField(value = "title")
    private String title;

    /**
     * 文档内容
     */
    @HighLight(mappingField = "highlightContent")//  对应下面highlightContent变量名
    @IndexField(value = "content",fieldType = FieldType.TEXT)
    private String content;

    /**
     * 创建时间
     */
    @IndexField(value = "create_time",fieldType = FieldType.DATE,dateFormat = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    /**
     * 不存在的字段
     */
    @IndexField(exist = false)
    private String notExistField;

    /**
     * 地理位置经纬度坐标 例如: "40.13933715136454,116.63441990026217"
     */
    @IndexField(fieldType = FieldType.GEO_POINT)
    private String location;

    /**
     * 圆形(例如圆形，矩形)
     */
    @IndexField(fieldType = FieldType.GEO_SHAPE)
    private String geoLocation;

    /**
     * 自定义字段名称
     */
    @IndexField(value = "can")
    private String customField;

    /**
     * 高亮返回值被映射的字段
     */
    private String highlightContent;


}
