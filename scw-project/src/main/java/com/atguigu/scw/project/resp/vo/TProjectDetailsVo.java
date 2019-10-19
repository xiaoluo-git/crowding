package com.atguigu.scw.project.resp.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.atguigu.scw.project.bean.TReturn;

import lombok.Data;


@Data
public class TProjectDetailsVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer memberid;
	
	private Integer id;//项目id

    private String name;

    private String remark;

    private Long money;

    private Integer day;

    private String status;

    private String deploydate;

    private Long supportmoney = 0L;

    private Integer supporter = 0;

    private Integer completion = 0;

    private String createdate;

    private Integer follower = 0;
    
    
    private String headerImage;
    
    private List<String> detailsImages = new ArrayList<>();
    
    private List<TReturn> projectReturns = new ArrayList<>();
}
