package com.shalinijain.myapplication6;

import java.io.Serializable;

/**
 * Created by Admin on 31-03-2018.
 */

public class Government implements Serializable {
    private String city;
    private String state;
    private String zip;

    private String address;
    private String contact_no;
    private String urlofweb;
    private String email_id;
    private String urlofphoto;

    private String googleplus_channelid;
    private String facebook_channelid;
    private String twiter_channedlid;
    private String youtube_channeelid;

    private String office_name;
    private String official_name;
    private String party;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getUrlofweb() {
        return urlofweb;
    }

    public void setUrlofweb(String urlofweb) {
        this.urlofweb = urlofweb;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUrlofphoto() {
        return urlofphoto;
    }

    public void setUrlofphoto(String urlofphoto) {
        this.urlofphoto = urlofphoto;
    }

    public String getGoogleplus_channelid() {
        return googleplus_channelid;
    }

    public void setGoogleplus_channelid(String googleplus_channelid) {
        this.googleplus_channelid = googleplus_channelid;
    }

    public String getFacebook_channelid() {
        return facebook_channelid;
    }

    public void setFacebook_channelid(String facebook_channelid) {
        this.facebook_channelid = facebook_channelid;
    }

    public String getTwiter_channedlid() {
        return twiter_channedlid;
    }

    public void setTwiter_channedlid(String twiter_channedlid) {
        this.twiter_channedlid = twiter_channedlid;
    }

    public String getYoutube_channeelid() {
        return youtube_channeelid;
    }

    public void setYoutube_channeelid(String youtube_channeelid) {
        this.youtube_channeelid = youtube_channeelid;
    }

    public String getOffice_name() {
        return office_name;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public String getOfficial_name() {
        return official_name;
    }

    public void setOfficial_name(String title) {
        this.official_name = title;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    @Override
    public String toString() {
        return "Government{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", address='" + address + '\'' +
                ", contact_no='" + contact_no + '\'' +
                ", urlofweb='" + urlofweb + '\'' +
                ", email_id='" + email_id + '\'' +
                ", urlofphoto='" + urlofphoto + '\'' +
                ", googleplus_channelid='" + googleplus_channelid + '\'' +
                ", facebook_channelid='" + facebook_channelid + '\'' +
                ", twiter_channedlid='" + twiter_channedlid + '\'' +
                ", youtube_channeelid='" + youtube_channeelid + '\'' +
                ", office_name='" + office_name + '\'' +
                ", title='" + official_name + '\'' +
                ", party='" + party + '\'' +
                '}';
    }
}