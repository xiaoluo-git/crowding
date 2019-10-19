package com.atguigu.scw.project.service;

import com.atguigu.scw.enums.ProjectStatusEnume;
import com.atguigu.scw.project.req.vo.ProjectRedisStorageVo;

public interface ProjectCreateService {

	void saveProjectInfo(ProjectRedisStorageVo projectRedisStorageVo, byte code);

}
