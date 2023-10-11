package com.tanvipanchal.invitationdateplanner.model;

import java.util.List;

public class Partners {
    private List<Partner> partners;

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }

    @Override
    public String toString() {
        return "Partners{" +
                "partners=" + partners +
                '}';
    }
}
