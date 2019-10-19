package com.atguigu.scw.order.bean;

import java.io.Serializable;

public class TTransaction implements Serializable {
    private Integer id;

    private String ordersn;

    private String paysn;

    private Integer memberid;

    private Float amount;

    private Byte paystate;

    private String source;

    private Byte status;

    private String completiontime;

    private String note;

    private String createat;

    private String updateat;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn == null ? null : ordersn.trim();
    }

    public String getPaysn() {
        return paysn;
    }

    public void setPaysn(String paysn) {
        this.paysn = paysn == null ? null : paysn.trim();
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Byte getPaystate() {
        return paystate;
    }

    public void setPaystate(Byte paystate) {
        this.paystate = paystate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCompletiontime() {
        return completiontime;
    }

    public void setCompletiontime(String completiontime) {
        this.completiontime = completiontime == null ? null : completiontime.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getCreateat() {
        return createat;
    }

    public void setCreateat(String createat) {
        this.createat = createat == null ? null : createat.trim();
    }

    public String getUpdateat() {
        return updateat;
    }

    public void setUpdateat(String updateat) {
        this.updateat = updateat == null ? null : updateat.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ordersn=").append(ordersn);
        sb.append(", paysn=").append(paysn);
        sb.append(", memberid=").append(memberid);
        sb.append(", amount=").append(amount);
        sb.append(", paystate=").append(paystate);
        sb.append(", source=").append(source);
        sb.append(", status=").append(status);
        sb.append(", completiontime=").append(completiontime);
        sb.append(", note=").append(note);
        sb.append(", createat=").append(createat);
        sb.append(", updateat=").append(updateat);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}