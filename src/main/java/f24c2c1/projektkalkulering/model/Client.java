package f24c2c1.projektkalkulering.model;

public interface Client {
    long getId();
    void setId(long id);
    String getName();
    void setName(String name);
    String getContactName();
    void setContactName(String contactName);
    String getAddress();
    void setAddress(String address);
    String getCity();
    void setCity(String city);
    String getZip();
    void setZip(String zip);
    String getPhone();
    void setPhone(String phone);
    String getEmail();
    void setEmail(String email);
}
