package com.atguigu.scw.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TProjectTag;
import com.atguigu.scw.project.bean.TProjectType;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.mapper.TProjectImagesMapper;
import com.atguigu.scw.project.mapper.TProjectMapper;
import com.atguigu.scw.project.mapper.TProjectTagMapper;
import com.atguigu.scw.project.mapper.TProjectTypeMapper;
import com.atguigu.scw.project.mapper.TReturnMapper;
import com.atguigu.scw.project.projectconst.ProjectConst;
import com.atguigu.scw.project.req.vo.ProjectRedisStorageVo;
import com.atguigu.scw.project.service.ProjectCreateService;
import com.atguigu.scw.util.AppDateUtils;

@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {
	
	@Autowired
	private TProjectMapper projectMapper; 
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private TProjectTypeMapper projectTypeMapper;
	
	@Autowired
	private TProjectTagMapper projectTagMapper;
	
	@Autowired
	private TReturnMapper returnMapper;
	
	@Autowired
	private TProjectImagesMapper projectImagesMapper;
	
	@Override
	@Transactional
	public void saveProjectInfo(ProjectRedisStorageVo projectRedisStorageVo, byte code) {
		
		//1.项目表
		TProject project = new TProject();
		project.setName(projectRedisStorageVo.getName());
		project.setRemark(projectRedisStorageVo.getRemark());
		project.setMoney(projectRedisStorageVo.getMoney());
		project.setDay(projectRedisStorageVo.getDay());
		project.setStatus(code + "");
		project.setCreatedate(AppDateUtils.getFormatTime());
		project.setMemberid(Integer.parseInt(redisTemplate.opsForValue().get(projectRedisStorageVo.getAccessToken())));
		projectMapper.insertSelective(project);
		
		//2.项目分类表
		Integer projectId = project.getId();
		List<Integer> typeIds = projectRedisStorageVo.getTypeids();
		for (Integer typeId : typeIds) {
			TProjectType projectType = new TProjectType();
			projectType.setProjectid(projectId);
			projectType.setTypeid(typeId);
			projectTypeMapper.insertSelective(projectType);
		}
		
		//3.项目标签表
		List<Integer> tagIds = projectRedisStorageVo.getTagids();
		for (Integer tagId : tagIds) {
			TProjectTag projectTag = new TProjectTag();
			projectTag.setProjectid(projectId);
			projectTag.setTagid(tagId);
			projectTagMapper.insertSelective(projectTag);
		}
		
		//4.项目回报表
		List<TReturn> projectReturns = projectRedisStorageVo.getProjectReturns();
		for (TReturn tReturn : projectReturns) {
			tReturn.setProjectid(projectId);
			returnMapper.insertSelective(tReturn);
		}
		
		//项目头图表
		String headerImage = projectRedisStorageVo.getHeaderImage();
		TProjectImages projectImages = new TProjectImages();
		projectImages.setProjectid(projectId);
		projectImages.setImgtype((byte)0);
		projectImages.setImgurl(headerImage);
		projectImagesMapper.insertSelective(projectImages);
		
		//项目详情图
		List<String> detailsImages = projectRedisStorageVo.getDetailsImage();
		projectImages.setImgtype((byte)1);
		for (String detailsImage : detailsImages) {
			projectImages.setImgurl(detailsImage);
			projectImagesMapper.insertSelective(projectImages);
		}
		
		//清理redis
		redisTemplate.delete(ProjectConst.TEMP_PROJECT_PREFIX + projectRedisStorageVo.getProjectToken());
	}

}
