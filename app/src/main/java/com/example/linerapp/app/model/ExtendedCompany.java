package com.example.linerapp.app.model;

/**
 * Created by Ильнар on 25.06.2014.
 */
public class ExtendedCompany extends Company {

    protected String description;

    protected int categotyId;

    protected int subCategoryId;

    protected String eMail;

    protected String vkontakte;

    protected String twitter;

    protected String facebook;

    protected String linkedin;

    protected String site;

    protected String logoFileName;

    public ExtendedCompany(int id, String name, String address) {
        super(id, name, address);
    }

    public ExtendedCompany(int id, String name, String address, String description, int categotyId,
                           int subCategoryId, String eMail, String vkontakte, String twitter,
                           String facebook, String linkedin, String site, String logoFileName) {
        super(id, name, address);
        this.description = description;
        this.categotyId = categotyId;
        this.subCategoryId = subCategoryId;
        this.eMail = eMail;
        this.vkontakte = vkontakte;
        this.twitter = twitter;
        this.facebook = facebook;
        this.linkedin = linkedin;
        this.site = site;
        this.logoFileName = logoFileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategotyId() {
        return categotyId;
    }

    public void setCategotyId(int categotyId) {
        this.categotyId = categotyId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getVkontakte() {
        return vkontakte;
    }

    public void setVkontakte(String vkontakte) {
        this.vkontakte = vkontakte;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLogoFileName() {
        return logoFileName;
    }

    public void setLogoFileName(String logoFileName) {
        this.logoFileName = logoFileName;
    }
}
