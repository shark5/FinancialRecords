package com.jun.financialrecords.model;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jun on 2015/5/26.
 * 投资类型
 */
public class InvestType extends DataSupport {
    private int id;
    private String name;
    private List<InvestProject> investProjectList = new ArrayList<InvestProject>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InvestProject> getInvestProjectList() {
        return investProjectList;
    }

    public void setInvestProjectList(List<InvestProject> investProjectList) {
        investProjectList = investProjectList;
    }

    @Override
    public String toString() {
        return "InvestType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", investProjectList=" + investProjectList +
                '}';
    }
}
