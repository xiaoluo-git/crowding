package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.TAdmin;

public interface TAdminMapper {

    TAdmin selectByPrimaryKey(Integer id);

}