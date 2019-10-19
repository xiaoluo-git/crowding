package com.atguigu.scw.order.service;

import com.atguigu.scw.order.bean.TOrder;
import com.atguigu.scw.order.req.vo.OrderInfoSubmitVo;

public interface OrderService {

	TOrder saveOrder(OrderInfoSubmitVo orderInfoSubmitVo);

}
