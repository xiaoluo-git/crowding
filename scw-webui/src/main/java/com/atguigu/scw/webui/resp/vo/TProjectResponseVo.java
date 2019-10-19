package com.atguigu.scw.webui.resp.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.atguigu.scw.webui.req.vo.TReturn;

import lombok.Data;

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
    
    private List<String> detailsImages = new ArrayList<>();
    
    private List<TReturn> projectReturns = new ArrayList<>();
}
