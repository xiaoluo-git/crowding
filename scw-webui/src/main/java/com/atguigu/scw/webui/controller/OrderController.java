package com.atguigu.scw.webui.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.config.AlipayConfig;
import com.atguigu.scw.webui.req.vo.OrderFormInfoSubmitVo;
import com.atguigu.scw.webui.req.vo.OrderInfoSubmitVo;
import com.atguigu.scw.webui.resp.vo.ReturnPayConfirmVo;
import com.atguigu.scw.webui.resp.vo.TOrder;
import com.atguigu.scw.webui.resp.vo.UserResponseVo;
import com.atguigu.scw.webui.service.OrderServiceFeign;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class OrderController {
	
	@Autowired
	private OrderServiceFeign orderServiceFeign;
	
	@PostMapping("/order/pay")
	@ResponseBody
	public String confirmOrder(OrderFormInfoSubmitVo  vo,HttpSession session) {
		log.debug("提交订单-立即支付：OrderFormInfoSubmitVo={}",vo);
		
		//1、下订单
		OrderInfoSubmitVo orderInfoSubmitVo = new OrderInfoSubmitVo();
		BeanUtils.copyProperties(vo, orderInfoSubmitVo);
		UserResponseVo userResponseVo = (UserResponseVo) session.getAttribute("userResponseVo");
		if(userResponseVo == null) {
			return "redirect:/login";
		}
		
		String accessToken = userResponseVo.getAccessToken();
		orderInfoSubmitVo.setAccessToken(accessToken);
		ReturnPayConfirmVo returnPayConfirmVo=(ReturnPayConfirmVo) session.getAttribute("returnPayConfirmVo");
		if(returnPayConfirmVo == null) {
			return "redirect:/login";
		}
		Integer returnId = returnPayConfirmVo.getReturnId();
		Integer projectId = returnPayConfirmVo.getProjectId();
		Integer rtncount = returnPayConfirmVo.getNum();
		orderInfoSubmitVo.setProjectid(projectId);
		orderInfoSubmitVo.setReturnid(returnId);
		orderInfoSubmitVo.setRtncount(rtncount);
		AppResponse<TOrder> appResponse= orderServiceFeign.saveOrder(orderInfoSubmitVo);
		
		TOrder order = appResponse.getData();
		log.debug("订单信息order={}:",order);
		//2.支付 
		
		
		String result = payOrder(order.getOrdernum(),order.getMoney(),"5757",order.getRemark());
		return result;
	}

	private String payOrder(String ordernum, Integer money, String projectName, String remark) {
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
			
			//设置请求参数
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
			alipayRequest.setReturnUrl(AlipayConfig.return_url);
			alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
			
			
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ ordernum +"\"," 
					+ "\"total_amount\":\""+ money +"\"," 
					+ "\"subject\":\""+ projectName +"\"," 
					+ "\"body\":\""+ remark +"\"," 
					+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
			
			log.debug("sign_type={}",AlipayConfig.sign_type);
			//请求
			String result = alipayClient.pageExecute(alipayRequest).getBody();
			return result;
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping("/order/updateOrderStatus")
	public String updateOrderStatus() {
		
		log.debug("支付宝异步通知、、");
		return "success";
	}
}
