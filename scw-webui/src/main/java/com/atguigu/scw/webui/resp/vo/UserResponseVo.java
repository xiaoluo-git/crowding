package com.atguigu.scw.webui.resp.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserResponseVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("令牌")
	private String accessToken;
	@ApiModelProperty("登录账号")
	private String loginacct;
	@ApiModelProperty("用户名称")
	private String username;
	@ApiModelProperty("邮箱")
	private String email;
	@ApiModelProperty("实名认证状态 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证")
	private String authstatus = "0";
	@ApiModelProperty("用户类型: 0 - 个人， 1 - 企业")
	private String usertype;
	@ApiModelProperty("真实姓名")
	private String realname;
	@ApiModelProperty("身份证号")
	private String cardnum;
	@ApiModelProperty("账户类型: 0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府")
	private String accttype;

}
