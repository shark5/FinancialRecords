package com.jun.financialrecords.model;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by jun on 2015/5/26.
 * 投资项目
 */
public class InvestProject extends DataSupport {
    private int id;
    private String projectName;         // 名称
    private float amount;               // 本金
    private float totalAmount;          // 现金
    private float profit;               // 收益
    private Date dateTime;              // 时间
    private float increase;             // 总涨幅
    private InvestType investType;      // 投资类型

    private List<InvestDetail> investDetailList = new ArrayList<InvestDetail>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public float getIncrease() {
        return increase;
    }

    public void setIncrease(float increase) {
        this.increase = increase;
    }

    public InvestType getInvestType() {
        return investType;
    }

    public void setInvestType(InvestType investType) {
        this.investType = investType;
    }

    public List<InvestDetail> getInvestDetailList() {
        return investDetailList;
    }

    public void setInvestDetailList(List<InvestDetail> investDetailList) {
        this.investDetailList = investDetailList;
    }

    @Override
    public String toString() {
        return "InvestProject{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", amount=" + amount +
                ", totalAmount=" + totalAmount +
                ", profit=" + profit +
                ", dateTime=" + dateTime +
                ", increase=" + increase +
                ", investType=" + investType +
                ", investDetailList=" + investDetailList +
                '}';
    }
}
