package bce.bce;

public class customerbean {
    String mobile;
    String name;
    String papers;
    String areas;
    String hawker;
    String date;
    public customerbean() {}
    public customerbean( String mobile, String name, String papers,String areas,String hawker,String date) {
        super();
        this.mobile = mobile;
        this.name=name;
        this.papers=papers;
        this.areas=areas;
        this.hawker=hawker;
        this.date=date;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name=name;
    }


    public String getPapers() {
        return papers;
    }
    public void setPapers(String papers) {
        this.papers=papers;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getHawker() {
        return hawker;
    }
    public void setHawker(String hawker) {
        this.hawker=hawker;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date=date;
    }
}
