package application;

//import java.util.*;

public class Address {
    private String street;
    private String city;
    private String state;
    private String zipcode;

    public Address(final String str, final String c, final String sta, final String zip) {
        this.street = str;
        this.city = c;
        this.state = sta;
        this.zipcode = zip;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zipcode;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public void setZipcode(final String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s %s", street, city, state, zipcode);
    }

    public static void main(String[] args) {
        Address a = new Address("22222 Charlie Way", "Fake City", "CR", "32432");
        System.out.println(a);
    }
}