package com.jun.financialrecords.model;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by jun on 2015/5/26.
 * 投资明细
 */
public class InvestDetail extends DataSupport {
    private int id;
    private InvestProject investProject;
    private Date dateTime;
    private float nowAmount;
    private float increase;             // 单次涨幅

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InvestProject getInvestProject() {
        return investProject;
    }

    public void setInvestProject(InvestProject investProject) {
        this.investProject = investProject;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public float getNowAmount() {
        return nowAmount;
    }

    public void setNowAmount(float nowAmount) {
        this.nowAmount = nowAmount;
    }

    public float getIncrease() {
        return increase;
    }

    public void setIncrease(float increase) {
        this.increase = increase;
    }

    @Override
    public String toString() {
        return "InvestDetail{" +
                "id=" + id +
                ", investProject=" + investProject +
                ", dateTime='" + dateTime + '\'' +
                ", nowAmount=" + nowAmount +
                ", increase=" + increase +
                '}';
    }
}
