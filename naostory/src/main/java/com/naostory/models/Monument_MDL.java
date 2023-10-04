package com.naostory.models;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String url;
    private String longitude;
    private String latitude;
}
