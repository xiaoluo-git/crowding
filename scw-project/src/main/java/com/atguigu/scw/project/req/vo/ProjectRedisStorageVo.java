package com.atguigu.scw.project.req.vo;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.scw.project.bean.TReturn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("整合的分布的VO对象")
public class ProjectRedisStorageVo extends BaseInfoVo{
	
	@ApiModelProperty("项目的token")
	private String projectToken;
	
	@ApiModelProperty("项目的分类id ")
	private List<Integer> typeids;
	@ApiModelProperty("项目的标签id ")
    private List<Integer> tagids;
	
	@ApiModelProperty("项目名称")
	private String name;
	@ApiModelProperty("项目简介")
    private String remark;
	@ApiModelProperty("项目金额")
    private Long money;
	@ApiModelProperty("项目天数 ")
    private Integer day;
	
	@ApiModelProperty("项目头部图片")
	private String headerImage;
	@ApiModelProperty("项目详情图片")
    private List<String> detailsImage;
	
	//发起人信息：自我介绍，详细自我介绍，联系电话，客服电话
	
	@ApiModelProperty("项目回报 ")
    private List<TReturn> projectReturns = new ArrayList<TReturn>();
}
