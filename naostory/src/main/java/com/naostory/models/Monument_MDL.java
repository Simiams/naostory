package com.naostory.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "horses")
@Getter
@Setter
@NoArgsConstructor
public class Wiki_MDL {

    @Id
    private Long id;
    private String name;
    private String address;
    private String coordinates;
    private String date;
    private String image;
}
