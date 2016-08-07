package com.adammcneilly.petfinder.model;

/**
 * Model representing an owner.
 *
 * Created by adam.mcneilly on 8/7/16.
 */
public class Owner {
    public String id;
    public String firstName;
    public String lastName;
    public String address;
    public String phone;

    public String fullName() {
        return firstName + " " + lastName;
    }
}
