package com.atguigu.scw.user.req.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class UserRegistVo {
	
	@ApiModelProperty("手机号")
	private String loginacct;
	@ApiModelProperty("密码")
	private String userpswd;
	@ApiModelProperty("邮箱")
	private String email;
	@ApiModelProperty("验证码")
	private String code;
	@ApiModelProperty(" 用户类型: 0 - 个人， 1 - 企业")
	private String usertype;

}
