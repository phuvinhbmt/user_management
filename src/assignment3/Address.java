package assignment3;
public class Address {
    private int unit, streetNumber, postCode;
    private String street, suburb, city;
    State state;

    public Address(int unit, int streetNumber, String street, String suburb, String city, State state, int postCode) {
        this.unit = unit;
        this.streetNumber = streetNumber;
        this.postCode = postCode;
        this.street = street;
        this.suburb = suburb;
        this.city = city;
        this.state = state;
    }


    public int getUnit() {
        return unit;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public int getPostCode() {
        return postCode;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCity() {
        return city;
    }

    public State getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    public void updateAddress(int unit, int streetNumber, String street, String suburb, String city, State state, int postCode) {
        setUnit(unit);
        setStreetNumber(streetNumber);
        setStreet(street);
        setPostCode(postCode);
        setSuburb(suburb);
        setCity(city);
        setState(state);
    }
    @Override 
    public String toString() {
        return String.format("%d %d %s %s %s %s %d ", this.unit, this.streetNumber, this.street, this.suburb, this.city, this.state, this.postCode);
    }
    
}
