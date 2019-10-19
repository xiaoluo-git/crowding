package com.atguigu.scw.project.resp.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.atguigu.scw.project.bean.TReturn;

import lombok.Data;

//项目热点的VO
@Data
public class TProjectResponseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;//项目id

	private Integer memberid;
	
	private String name;

    private String remark;
    
    private String headerImage;
    
    
}
