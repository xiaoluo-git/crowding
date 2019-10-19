package com.atguigu.scw.project.bean;

import java.io.Serializable;

public class TProjectImages implements Serializable {
    private Integer id;

    private Integer projectid;

    private String imgurl;

    private Byte imgtype;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public Byte getImgtype() {
        return imgtype;
    }

    public void setImgtype(Byte imgtype) {
        this.imgtype = imgtype;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectid=").append(projectid);
        sb.append(", imgurl=").append(imgurl);
        sb.append(", imgtype=").append(imgtype);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}