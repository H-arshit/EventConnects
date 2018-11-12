package com.TheEventWelfare.EventConnects;

/**
 * Created by User on 21-06-2017.
 */

public class UploadDetails {

    private String typename,Titlename,Ven,Price,dates,time,orgname,phonenum,email,weblink,bookinglink,descript,pastimagecode,upcomingimagecode,userid;

    public void setPrice(String price) {
        Price = price;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    public void setBookinglink(String bookinglink) {
        this.bookinglink = bookinglink;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPastimagecode(String pastimagecode) {
        this.pastimagecode = pastimagecode;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitlename(String titlename) {
        Titlename = titlename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public void setVen(String ven) {
        Ven = ven;

    }

    public void setUpcomingimagecode(String upcomingimagecode) {
        this.upcomingimagecode = upcomingimagecode;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public String getUserid() {
        return userid;
    }

    public String getWeblink() {
        return weblink;
    }

    public String getBookinglink() {
        return bookinglink;
    }

    public String getDates() {
        return dates;
    }

    public String getDescript() {
        return descript;
    }

    public String getOrgname() {
        return orgname;
    }

    public String getPastimagecode() {
        return pastimagecode;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public String getPrice() {
        return Price;
    }

    public String getTime() {
        return time;
    }

    public String getTitlename() {
        return Titlename;
    }

    public String getTypename() {
        return typename;
    }

    public String getUpcomingimagecode() {
        return upcomingimagecode;
    }

    public String getVen() {
        return Ven;
    }

}
