package com.atguigu.springboot.mapper;

import org.apache.ibatis.annotations.Select;

import com.atguigu.springboot.bean.TAdmin;

public interface TAdminMapper {
	@Select("select * from t_admin where id = #{id}")
	TAdmin getAdminById(Integer id);

}
