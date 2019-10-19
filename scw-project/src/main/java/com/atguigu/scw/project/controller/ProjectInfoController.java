package com.atguigu.scw.project.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.resp.vo.ReturnPayConfirmVo;
import com.atguigu.scw.project.resp.vo.TMember;
import com.atguigu.scw.project.resp.vo.TProjectDetailsVo;
import com.atguigu.scw.project.resp.vo.TProjectResponseVo;
import com.atguigu.scw.project.service.PageInfoService;
import com.atguigu.scw.project.service.feign.MemberServiceFeign;
import com.atguigu.scw.vo.resp.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "项目信息模块")
@RequestMapping("/project")
@RestController
@Slf4j
public class ProjectInfoController {
	
	@Autowired
	private PageInfoService pageInfoService;
	
	@Autowired
	private MemberServiceFeign memberServiceFeign;
	
	@ApiOperation(value="根据回报ID获取回报信息") 
	@GetMapping("/getReturnById")
	public AppResponse<TReturn> getReturnById(Integer returnId){
		TReturn returnInfo = pageInfoService.getReturnById(returnId);
		
		return AppResponse.ok(returnInfo);
	} 
	
	@ApiOperation(value="获取项目总览列表") 
	@GetMapping("/all/index")
	public AppResponse<List<TProjectResponseVo>> all(){
		List<TProjectResponseVo> projectList = pageInfoService.listAllProject();
		return AppResponse.ok(projectList);
	} 
	
	@ApiOperation(value="获取项目详情信息") 
	@GetMapping("/info/detail/{projectId}")
	public AppResponse<TProjectDetailsVo> detail(@PathVariable("projectId") Integer projectId){
		TProjectDetailsVo projectDetailsVo = pageInfoService.getProjectDetailsByProjectId(projectId);
		return AppResponse.ok(projectDetailsVo);
	} 
	
	@ApiOperation(value="确认回报信息") 
	@GetMapping("/confirm/returns/{projectId}/{returnId}")
	public AppResponse<ReturnPayConfirmVo>  suport(@PathVariable("projectId") Integer projectId,
			@PathVariable("returnId") Integer returnId){
		ReturnPayConfirmVo returnPayConfirmVo = new ReturnPayConfirmVo();
		
		//查询回报信息
		TReturn projectReturn = pageInfoService.getReturnById(returnId);
		returnPayConfirmVo.setReturnId(returnId);
		returnPayConfirmVo.setReturnContent(projectReturn.getContent());
		returnPayConfirmVo.setNum(projectReturn.getCount());
		returnPayConfirmVo.setPrice(projectReturn.getSupportmoney());
		returnPayConfirmVo.setFreight(projectReturn.getFreight());
		returnPayConfirmVo.setSignalpurchase(projectReturn.getSignalpurchase());
		returnPayConfirmVo.setPurchase(projectReturn.getPurchase());
		returnPayConfirmVo.setTotalPrice(new BigDecimal(projectReturn.getCount() * projectReturn.getSupportmoney() + projectReturn.getFreight()));
		
		//查询项目信息
		TProject project = pageInfoService.getProjectById(projectId);
		returnPayConfirmVo.setProjectId(project.getId());
		returnPayConfirmVo.setProjectName(project.getName());
		returnPayConfirmVo.setProjectRemark(project.getRemark());
		
		//查询发起人信息
		Integer memberid = project.getMemberid();
		log.debug("memberid={}",memberid + "");
		AppResponse<TMember> appResponse = memberServiceFeign.getMemberById(memberid);
		TMember member = appResponse.getData();
		log.debug("member:{}", member);
		returnPayConfirmVo.setMemberId(memberid);
		returnPayConfirmVo.setMemberName(member.getLoginacct());
		
		
		return AppResponse.ok(returnPayConfirmVo);
	}

	@ApiOperation(value="获取首页广告项目") 
	@GetMapping("/adv")
	public AppResponse<Object> adv(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="获取首页热门推荐项目") 
	@GetMapping("/recommend/index")
	public AppResponse<Object> index(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="获取首页分类推荐项目") 
	@GetMapping("/recommend/type")
	public AppResponse<Object> type(){
		return AppResponse.ok("ok");
	} 
	
	
	
	@ApiOperation(value="获取项目系统分类信息") 
	@GetMapping("/sys/type")
	public AppResponse<Object> systype(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="获取项目系统标签信息") 
	@GetMapping("/sys/tags")
	public AppResponse<Object> systags(){
		return AppResponse.ok("ok");
	} 
	
	
	
	@ApiOperation(value="获取项目回报档位信息") 
	@GetMapping("/return/info")
	public AppResponse<Object> support(){
		return AppResponse.ok("ok");
	} 	

	
}
