package com.atguigu.scw.project.req.vo;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ProjectBaseInfoVo extends BaseInfoVo {
	
	@ApiModelProperty("项目的token")
	private String projectToken;
	
	@ApiModelProperty("项目的分类id ")
	private List<Integer> typeids = new ArrayList<>();
	@ApiModelProperty("项目的标签id ")
    private List<Integer> tagids = new ArrayList<>();
	
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
    private List<String> detailsImage = new ArrayList<>();

}
