package com.adammcneilly.petfinder.model;

/**
 * Model for a Pet object.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public class Pet {
    public String petId;
    public String petName;
    public String ownerName;
    public String ownerAddress;
    public String ownerPhone;

    @Override
    public String toString() {
        return "Pet Name: " + petName + "\n" +
                "Pet Owner: " + ownerName + "\n" +
                "Owner Phone: " + ownerPhone + "\n" +
                "Owner Address: " + ownerAddress;
    }
}
