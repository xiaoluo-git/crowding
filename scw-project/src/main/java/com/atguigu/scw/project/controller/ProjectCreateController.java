package com.atguigu.scw.project.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.atguigu.scw.enums.ProjectStatusEnume;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.component.OOSTemplate;
import com.atguigu.scw.project.projectconst.ProjectConst;
import com.atguigu.scw.project.req.vo.BaseInfoVo;
import com.atguigu.scw.project.req.vo.ProjectBaseInfoVo;
import com.atguigu.scw.project.req.vo.ProjectRedisStorageVo;
import com.atguigu.scw.project.req.vo.ProjectReturnVo;
import com.atguigu.scw.project.service.ProjectCreateService;
import com.atguigu.scw.vo.resp.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "项目发起模块")
@RequestMapping("/project/create")
@RestController
@Slf4j
public class ProjectCreateController {
	
	@Autowired
	private OOSTemplate oosTemplate;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private ProjectCreateService projectCreateService;

	@ApiOperation(value = "1项目初始创建")
	@PostMapping("/init")
	public AppResponse<Object> init(BaseInfoVo baseInfoVo) {
		try {
			String accessToken = baseInfoVo.getAccessToken();
			if(StringUtils.isEmpty(accessToken)) {
				AppResponse<Object> appResponse = AppResponse.fail(null);
				appResponse.setMsg("项目发布必须携带此信息");
				return appResponse;
			}
			log.debug("accessToken:",accessToken);
			String id = redisTemplate.opsForValue().get(accessToken);
			if(StringUtils.isEmpty(id)) {
				AppResponse<Object> appResponse = AppResponse.fail(null);
				appResponse.setMsg("请先登录系统");
				return appResponse;
			}
			log.debug("用户id：{}",id);
			ProjectRedisStorageVo projectRedisStorageVo = new ProjectRedisStorageVo();
			BeanUtils.copyProperties(baseInfoVo, projectRedisStorageVo);
			String projectToken = ProjectConst.TEMP_PROJECT_PREFIX + UUID.randomUUID().toString().replaceAll("-", "");
			projectRedisStorageVo.setProjectToken(projectToken );
			
			String projectRedisStorageVoJson = JSON.toJSONString(projectRedisStorageVo);
			
			log.debug("拷贝的projectRedisStorageVoJson：",projectRedisStorageVoJson);
			redisTemplate.opsForValue().set(projectToken, projectRedisStorageVoJson);
			
			return AppResponse.ok(projectRedisStorageVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppResponse<Object> appResponse = AppResponse.fail(null);
			appResponse.setMsg(e.getMessage());
			log.debug("发起项目失败：{}",e.getMessage());
			return appResponse;
		}
	}
	
	@ApiOperation(value = "2收集基本信息")
	@PostMapping("/baseInfo")
	public AppResponse<Object> baseInfo(ProjectBaseInfoVo projectBseInfoVo) {
		try {
			String accessToken = projectBseInfoVo.getAccessToken();
			if(StringUtils.isEmpty(accessToken)) {
				AppResponse<Object> appResponse = AppResponse.fail(null);
				appResponse.setMsg("项目发布必须携带此信息");
				return appResponse;
			}
			log.debug("accessToken:",accessToken);
			String id = redisTemplate.opsForValue().get(accessToken);
			if(StringUtils.isEmpty(id)) {
				AppResponse<Object> appResponse = AppResponse.fail(null);
				appResponse.setMsg("请先登录系统");
				return appResponse;
			}
			log.debug("用户id：{}",id);
			String projectRedisStorageVoJson = redisTemplate.opsForValue().get(ProjectConst.TEMP_PROJECT_PREFIX + projectBseInfoVo.getProjectToken());
			log.debug("拷贝前的json串：{}" ,projectRedisStorageVoJson);
			ProjectRedisStorageVo projectRedisStorageVo = JSON.parseObject(projectRedisStorageVoJson, ProjectRedisStorageVo.class);
			
			BeanUtils.copyProperties(projectBseInfoVo, projectRedisStorageVo);
			
			projectRedisStorageVoJson = JSON.toJSONString(projectRedisStorageVo);
			
			log.debug("拷贝后的json串：{}" ,projectRedisStorageVoJson);
			
			redisTemplate.opsForValue().set(ProjectConst.TEMP_PROJECT_PREFIX + projectBseInfoVo.getProjectToken(), projectRedisStorageVoJson);
			
			return AppResponse.ok(projectRedisStorageVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppResponse<Object> appResponse = AppResponse.fail(null);
			appResponse.setMsg("项目发布失败：" + e.getMessage());
			return appResponse;
		}
	}
	
	@ApiOperation("项目发起第3步-项目保存项目回报信息")
	@PostMapping("/savereturn")
	public AppResponse<Object> saveReturnInfo(@RequestBody List<ProjectReturnVo> projectReturnVos){

		try {
			String accessToken = projectReturnVos.get(0).getAccessToken();
			if(StringUtils.isEmpty(accessToken)) {
				AppResponse<Object> appResponse = AppResponse.fail(null);
				appResponse.setMsg("项目发布必须携带此信息");
				return appResponse;
			}
			log.debug("accessToken:",accessToken);
			String id = redisTemplate.opsForValue().get(accessToken);
			if(StringUtils.isEmpty(id)) {
				AppResponse<Object> appResponse = AppResponse.fail(null);
				appResponse.setMsg("请先登录系统");
				return appResponse;
			}
			log.debug("用户id：{}",id);
			log.debug("projectReturnVos:",projectReturnVos);
			
			String projectToken = ProjectConst.TEMP_PROJECT_PREFIX +  projectReturnVos.get(0).getProjectToken();
			String projectRedisStorageVoJson = redisTemplate.opsForValue().get(projectToken);
			ProjectRedisStorageVo projectRedisStorageVo = JSON.parseObject(projectRedisStorageVoJson,ProjectRedisStorageVo.class);
			List<TReturn> projectReturns = projectRedisStorageVo.getProjectReturns();
			for (ProjectReturnVo projectReturnVo : projectReturnVos) {
				TReturn tReturn = new TReturn();
				BeanUtils.copyProperties(projectReturnVo, tReturn);
				projectReturns.add(tReturn);
			}
			
			projectRedisStorageVoJson = JSON.toJSONString(projectRedisStorageVo);
			
			redisTemplate.opsForValue().set(ProjectConst.TEMP_PROJECT_PREFIX + projectReturnVos.get(0).getProjectToken(), projectRedisStorageVoJson);
			
			return AppResponse.ok(projectRedisStorageVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppResponse<Object> appResponse = AppResponse.fail(null);
			appResponse.setMsg("项目发布失败：" + e.getMessage());
			return appResponse;
		}
	}
	
	
	@ApiOperation(value = "项目提交审核申请")
	@PostMapping("/submit")
	public AppResponse<Object> submit(String accessToken,String projectToken,String option) {
	
			try {
				if(StringUtils.isEmpty(accessToken)) {
					AppResponse<Object> appResponse = AppResponse.fail(null);
					appResponse.setMsg("项目发布必须携带此信息");
					return appResponse;
				}
				log.debug("accessToken:",accessToken);
				String id = redisTemplate.opsForValue().get(accessToken);
				if(StringUtils.isEmpty(id)) {
					AppResponse<Object> appResponse = AppResponse.fail(null);
					appResponse.setMsg("请先登录系统");
					return appResponse;
				}
				log.debug("用户id：{}",id);
				
				projectToken = ProjectConst.TEMP_PROJECT_PREFIX +  projectToken;
				String projectRedisStorageVoJson = redisTemplate.opsForValue().get(projectToken);
				ProjectRedisStorageVo projectRedisStorageVo = JSON.parseObject(projectRedisStorageVoJson,ProjectRedisStorageVo.class);
				
				if(option.equals("0")) {
					log.debug("保存为草稿");
					projectCreateService.saveProjectInfo(projectRedisStorageVo,ProjectStatusEnume.DRAFT.getCode());
					return AppResponse.ok("保存草稿成功");
					
				}else if(option.equals("1")) {
					
					log.debug("发布项目");
					projectCreateService.saveProjectInfo(projectRedisStorageVo,ProjectStatusEnume.SUBMIT_AUTH.getCode());
					return AppResponse.ok("发布项目成功");
				}else {
					AppResponse<Object> appResponse = AppResponse.fail(null);
					appResponse.setMsg("项目不支持此操作");
					return appResponse;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppResponse<Object> appResponse = AppResponse.fail(null);
				appResponse.setMsg("项目发布失败：" + e.getMessage());
				return appResponse;
			}
			
	}
	
	@ApiOperation(value = "项目草稿保存")
	@PostMapping("/tempsave")
	public AppResponse<Object> tempsave() {
		return AppResponse.ok("ok");
	}
	
	
	@ApiOperation(value = "上传图片")
	@PostMapping("/upload")
	public AppResponse<Object> upload(@RequestParam("uploadfile") MultipartFile[] files) {
		
		try {
			List<String> fileList = new ArrayList<>();
			for(MultipartFile file:files) {
				String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + file.getOriginalFilename();
				InputStream inputStream = file.getInputStream();
				String filePath = oosTemplate.upload(fileName, inputStream);
				fileList.add(filePath);
			}
			log.debug("文件上传列表{}",fileList);
			return AppResponse.ok(fileList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppResponse<Object> appResponse = AppResponse.fail(null);
			appResponse.setMsg("上传文件失败");
			log.error("文件上传失败");
			return appResponse;
		}
	}
	
	@ApiOperation(value = "添加项目回报档位")
	@PostMapping("/return")
	public AppResponse<Object> returnDetail() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "删除项目回报档位")
	@DeleteMapping("/return")
	public AppResponse<Object> deleteReturnDetail() {
		return AppResponse.ok("ok");
	}
	

	@ApiOperation(value = "确认项目法人信息")
	@PostMapping("/confirm/legal")
	public AppResponse<Object> legal() {
		return AppResponse.ok("ok");
	}
	
	
}
