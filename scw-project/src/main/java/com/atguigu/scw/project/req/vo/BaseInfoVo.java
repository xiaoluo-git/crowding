package com.atguigu.scw.project.req.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BaseInfoVo {
	@ApiModelProperty("用户登录令牌")
	private String accessToken;
}
