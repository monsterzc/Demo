package com.can.aop;

/**
 * @className: Convert
 * @description: 接口
 * @author: zhengcan
 * @date: 2023/4/2
 **/
public interface Convert<PARAM> {


    /**
     * convert
     */
    OperateLogDo convert(PARAM param);
}
