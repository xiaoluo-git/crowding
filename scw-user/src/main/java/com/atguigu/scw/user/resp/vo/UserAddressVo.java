package com.atguigu.scw.user.resp.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserAddressVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3371459867101543071L;

	@ApiModelProperty("地址id")
	private Integer id = 1;

	@ApiModelProperty("会员地址")
	private String address = "牙齿大街1号";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
