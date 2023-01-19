package dev.freelance.freeserve.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Entity
public class BuyerClient extends AbstractClient {

    public BuyerClient(String name,String surname) {
        this.setIndicator(false);
        this.setName(name);
        this.setSurname(surname);
    }

}
