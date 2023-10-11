package com.tanvipanchal.invitationdateplanner.model;

import java.util.List;

public class Countries {

    private List<Country> countries;

    public Countries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "Countries{" +
                "countries=" + countries +
                '}';
    }
}
