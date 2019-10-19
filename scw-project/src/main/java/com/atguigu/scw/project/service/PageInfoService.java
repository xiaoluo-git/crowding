package com.atguigu.scw.project.service;

import java.util.List;

import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.resp.vo.TProjectDetailsVo;
import com.atguigu.scw.project.resp.vo.TProjectResponseVo;

public interface PageInfoService {

	List<TProjectResponseVo> listAllProject();

	TProjectDetailsVo getProjectDetailsByProjectId(Integer projectId);

	TProject getProjectById(Integer projectId);

	TReturn getReturnById(Integer returnId);





	
}
