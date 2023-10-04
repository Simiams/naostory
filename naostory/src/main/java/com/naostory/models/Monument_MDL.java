package com.naostory.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "monuments")
@Getter
@Setter
@NoArgsConstructor
public class Monument_MDL {

    @Id
    private String id;
    private String name;
    private String address;
    private String coordinates;
    private String date;
    private String image;
    private String content;
}
