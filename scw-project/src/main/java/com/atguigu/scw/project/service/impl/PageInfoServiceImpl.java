package com.atguigu.scw.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TProjectImagesExample;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.bean.TReturnExample;
import com.atguigu.scw.project.mapper.TProjectImagesMapper;
import com.atguigu.scw.project.mapper.TProjectMapper;
import com.atguigu.scw.project.mapper.TReturnMapper;
import com.atguigu.scw.project.resp.vo.TProjectDetailsVo;
import com.atguigu.scw.project.resp.vo.TProjectResponseVo;
import com.atguigu.scw.project.service.PageInfoService;

@Service
public class PageInfoServiceImpl implements PageInfoService {

	@Autowired
	private TProjectMapper projectMapper;

	@Autowired
	private TProjectImagesMapper projectImageMapper;

	@Autowired
	private TReturnMapper returnMapper;
	
	

	@Override
	public List<TProjectResponseVo> listAllProject() {
		// TODO Auto-generated method stub
		List<TProjectResponseVo> projectRespVos = new ArrayList<>();

		List<TProject> projectList = projectMapper.selectByExample(null);

		for (TProject tProject : projectList) {

			TProjectResponseVo projectResponseVo = new TProjectResponseVo();
			BeanUtils.copyProperties(tProject, projectResponseVo);

			Integer projectId = tProject.getId();

			// 获取项目图片
			TProjectImagesExample example = new TProjectImagesExample();
			example.createCriteria().andProjectidEqualTo(projectId);
			List<TProjectImages> allImages = projectImageMapper.selectByExample(example);
			for (TProjectImages projectImage : allImages) {
				if (projectImage.getImgtype() == 0) {
					projectResponseVo.setHeaderImage(projectImage.getImgurl());
				}
			}
			projectRespVos.add(projectResponseVo);
		}

		return projectRespVos;
	}

	@Override
	public TProjectDetailsVo getProjectDetailsByProjectId(Integer projectId) {

		TProjectDetailsVo projectDetailsVo = new TProjectDetailsVo();
		TProject project = projectMapper.selectByPrimaryKey(projectId);
		BeanUtils.copyProperties(project, projectDetailsVo);

		// 获取项目图片
		TProjectImagesExample example = new TProjectImagesExample();
		example.createCriteria().andProjectidEqualTo(projectId);
		List<TProjectImages> allImages = projectImageMapper.selectByExample(example);
		for (TProjectImages projectImage : allImages) {
			if (projectImage.getImgtype() == 0) {
				projectDetailsVo.setHeaderImage(projectImage.getImgurl());
			} else {
				projectDetailsVo.getDetailsImages().add(projectImage.getImgurl());
			}
		}

		// 获取项目的回报信息
		TReturnExample example1 = new TReturnExample();
		example1.createCriteria().andProjectidEqualTo(projectId);
		List<TReturn> returns = returnMapper.selectByExample(example1);
		for (TReturn tReturn : returns) {
			projectDetailsVo.getProjectReturns().add(tReturn);
		}

		return projectDetailsVo;
	}

	@Override
	public TProject getProjectById(Integer projectId) {
		// TODO Auto-generated method stub
		return projectMapper.selectByPrimaryKey(projectId);
	}

	@Override
	public TReturn getReturnById(Integer returnId) {
		
		return returnMapper.selectByPrimaryKey(returnId);
	}


}
