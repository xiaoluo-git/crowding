package com.atguigu.scw.webui.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.resp.vo.ReturnPayConfirmVo;
import com.atguigu.scw.webui.resp.vo.TProjectDetailsVo;
import com.atguigu.scw.webui.resp.vo.UserAddressVo;
import com.atguigu.scw.webui.resp.vo.UserResponseVo;
import com.atguigu.scw.webui.service.ProjectServiceFeign;
import com.atguigu.scw.webui.service.UserServiceFeign;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ProjectController {
	
	@Autowired
	private ProjectServiceFeign projectServiceFeign;
	
	@Autowired
	private UserServiceFeign userServiceFeign;
	
	@GetMapping("/project/projectInfo")
	public String projectInfo(Integer projectId,Model model) {
		
		AppResponse<TProjectDetailsVo> appResponse = projectServiceFeign.detail(projectId);
		TProjectDetailsVo projectDetailsVo = appResponse.getData();
		model.addAttribute("projectDetailsVo",projectDetailsVo);
		return "project/index";
	}
	
	@GetMapping("/project/confirm/order/{count}")
	public String confirmOrder(@PathVariable("count")Integer count, HttpSession session,Model model) {
		UserResponseVo userResponseVo = (UserResponseVo) session.getAttribute("userResponseVo");
		if(userResponseVo == null) {
			return "redirect:/login?count=" + count ;
		}
		String accessToken = userResponseVo.getAccessToken();
		log.debug("accessToken={}",accessToken);
		AppResponse<List<UserAddressVo>> appResponse = userServiceFeign.address(accessToken);
		log.debug("appResponse={}",appResponse);
		List<UserAddressVo> data = appResponse.getData();
		model.addAttribute("userAddressVoList", data);
		model.addAttribute("count", count);
		
		//修改session数据
		ReturnPayConfirmVo vo = (ReturnPayConfirmVo) session.getAttribute("returnPayConfirmVo");
		vo.setNum(count);
		vo.setTotalPrice(new BigDecimal(count * vo.getPrice() + vo.getFreight()));
		session.setAttribute("returnPayConfirmVo", vo);
		return "project/pay-step-2";
	}
	
	@GetMapping("/project/suport/{projectId}/{returnId}")
	public String suport(@PathVariable("projectId") Integer projectId,
			@PathVariable("returnId") Integer returnId,Model model,HttpSession session){
		AppResponse<ReturnPayConfirmVo> appResponse = projectServiceFeign.suport(projectId, returnId);
		ReturnPayConfirmVo returnPayConfirmVo = appResponse.getData();
		model.addAttribute("returnPayConfirmVo",returnPayConfirmVo);
		session.setAttribute("returnPayConfirmVo",returnPayConfirmVo);
		return "project/pay-step-1";
		
	}
}
