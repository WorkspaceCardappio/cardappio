package br.com.cardappio.entity;

import java.rmi.server.UID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Restaurant {
    @Id
    private UID id;

}
