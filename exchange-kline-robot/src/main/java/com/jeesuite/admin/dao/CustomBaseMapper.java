package com.jeesuite.admin.dao;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用Mapper接口
 *
 * @param <T>
 */
public interface CustomBaseMapper<T> extends BaseMapper<T>, MySqlMapper<T> {

}
