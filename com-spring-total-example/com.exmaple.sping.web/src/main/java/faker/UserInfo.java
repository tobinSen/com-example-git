package faker;

public class UserInfo {

    private String realName;
    private String cellPhone;
    private String universityName;
    private String city;
    private String street;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "realName='" + realName + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", universityName='" + universityName + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    
}
