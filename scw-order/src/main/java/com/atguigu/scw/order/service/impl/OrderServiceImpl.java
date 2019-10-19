package com.atguigu.scw.order.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.atguigu.scw.enums.OrderStatusEnumes;
import com.atguigu.scw.order.bean.TOrder;
import com.atguigu.scw.order.mapper.TOrderMapper;
import com.atguigu.scw.order.req.vo.OrderInfoSubmitVo;
import com.atguigu.scw.order.resp.vo.TReturn;
import com.atguigu.scw.order.service.OrderService;
import com.atguigu.scw.order.service.feign.ProjectServiceFeign;
import com.atguigu.scw.util.AppDateUtils;
import com.atguigu.scw.vo.resp.AppResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private TOrderMapper orderMapper;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private ProjectServiceFeign projectServiceFeign;

	@Override
	public TOrder saveOrder(OrderInfoSubmitVo vo) {
		TOrder order = new TOrder();
		String accessToken = vo.getAccessToken();
		String memberId = redisTemplate.opsForValue().get(accessToken);
		order.setMemberid(Integer.parseInt(memberId));
		order.setProjectid(vo.getProjectid());
		order.setReturnid(vo.getReturnid());
		order.setOrdernum(UUID.randomUUID().toString().replaceAll("-",""));
		order.setCreatedate(AppDateUtils.getFormatTime());
		
		Integer returnId = vo.getReturnid();
		log.debug("returnId={}",returnId);
		AppResponse<TReturn> appResponse = projectServiceFeign.getReturnById(returnId);
		log.debug("获取项目回报appResponse={}",appResponse);
		TReturn tReturn = appResponse.getData();
		Integer money = vo.getRtncount() * tReturn.getSupportmoney() + tReturn.getFreight() ;
		order.setMoney(money );
		order.setRtncount(vo.getRtncount());
		order.setStatus(OrderStatusEnumes.UNPAY.getCode() +"");
		order.setAddress(vo.getAddress());
		order.setInvoice(vo.getInvoice().toString());
		order.setInvoictitle(vo.getInvoictitle());
		order.setRemark(vo.getRemark());
		
		orderMapper.insertSelective(order);
		return order;
	}
}
