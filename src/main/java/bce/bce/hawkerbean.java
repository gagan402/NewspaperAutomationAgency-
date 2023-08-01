package bce.bce;

public class hawkerbean {
    String hname;
    String mobile;
    String allo_areas;
    String doj;

    public hawkerbean() {}
    public hawkerbean(String hname, String mobile, String allo_areas, String doj) {
        super();
        this.hname = hname;
        this.mobile = mobile;
        this.allo_areas = allo_areas;
        this.doj = doj;
    }
    public String getHname() {
        return hname;
    }
    public void setHname(String hname) {
        this.hname = hname;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getAllo_areas() {
        return allo_areas;
    }
    public void setAllo_areas(String allo_areas) {
        this.allo_areas = allo_areas;
    }
    public String getDoj() {
        return doj;
    }
    public void setDoj(String doj) {
        this.doj = doj;
    }

}
