package bce.bce;

public class billbean {
    String mobile;
    String DFrom;
    String DTo;
    String Bill;
    String status;
    public billbean() {}
    public billbean(String mobile, String DFrom, String DTo, String Bill,String status) {
        super();
        this.mobile = mobile;
        this.DFrom=DFrom;
        this.DTo=DTo;
        this.Bill=Bill;
        this.status=status;

    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getDFrom() {
        return DFrom;
    }
    public void setDFrom(String DFrom) {
        this.DFrom=DFrom;
    }

    public String getDTo() {
        return DTo;
    }
    public void setDTo(String DTo) {
        this.DTo=DTo;
    }


    public String getBill() {
        return Bill;
    }
    public void setBill(String Bill) {
        this.Bill=Bill;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status=status;
    }
}
