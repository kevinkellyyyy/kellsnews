package id.ac.umn.kevinkellyisyanta;

public class Sumber {
    private String idSum, nameSum, descSum, urlSum;

    public Sumber(String idSum, String nameSum, String descSum, String urlSum) {
        this.idSum = idSum;
        this.nameSum = nameSum;
        this.descSum = descSum;
        this.urlSum = urlSum;
    }

    public String getIdSum() {
        return idSum;
    }

    public void setIdSum(String idSum) {
        this.idSum = idSum;
    }

    public String getNameSum() {
        return nameSum;
    }

    public void setNameSum(String nameSum) {
        this.nameSum = nameSum;
    }

    public String getDescSum() {
        return descSum;
    }

    public void setDescSum(String descSum) {
        this.descSum = descSum;
    }

    public String getUrlSum() {
        return urlSum;
    }

    public void setUrlSum(String urlSum) {
        this.urlSum = urlSum;
    }
}
