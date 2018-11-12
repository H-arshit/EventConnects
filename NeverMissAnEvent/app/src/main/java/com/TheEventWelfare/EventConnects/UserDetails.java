package com.TheEventWelfare.EventConnects;

class UserDetails {
  private   String username ,password ,contact,emailid;

    public UserDetails()
    {
        username = "";
        password = "";
        contact = "";
        emailid = "";
    }
    public String getContact() {
        return contact;
    }

    public String getEmailid() {
        return emailid;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
