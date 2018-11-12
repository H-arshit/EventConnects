package com.TheEventWelfare.EventConnects;

/**
 * Created by User on 06-05-2017.
 */

public class DataForRegisterPage {
    private int id ;
    private int islastcontent;
    private String orgname,titlename,dates;
    public DataForRegisterPage(int id , String orgname,String titlename,String dates , int count)
    {
        this.id=id;
        this.orgname=orgname;
        this.titlename=titlename;
        this.dates=dates;
        islastcontent = count;
    }


    public int islastcontent() {
        return islastcontent;
    }

    public void setIslastcontent(int islastcontent) {
        this.islastcontent = islastcontent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getTitlename() {
        return titlename;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }
}
