package com.naostory.clients;

import java.io.IOException;

import com.naostory.models.Monument_MDL;
import com.naostory.repositories.IMonument_REP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class Wiki_CLT {

    private final IMonument_REP iMonumentRep;

    @Autowired
    public Wiki_CLT(IMonument_REP iMonumentRep) {
        this.iMonumentRep = iMonumentRep;
    }


    public Monument_MDL getWikiContent(Monument_MDL monument) {
            String wikipediaPageURL = "https://fr.wikipedia.org/wiki/Albert_Einstein";
            String className = "mw-parser-output";

            try {
                Document doc = Jsoup.connect(wikipediaPageURL).get();
                Objects.requireNonNull(doc.getElementsByClass(className)).get(0).children().forEach(element -> {
                    if (element.tagName().equals("p")) {
                        monument.setContent(monument.getContent() + element.text());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return monument;
        }

        public List<Monument_MDL> getMonuments() {
            String wikipediaPageURL = "https://fr.wikipedia.org/wiki/Liste_des_monuments_historiques_de_Nantes";
            List<Monument_MDL> monuments = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(wikipediaPageURL).get();
                Elements rows = doc.select("table").select("tr");
                rows.forEach(row -> {
                    Elements col = row.select("td");
                    if (!col.isEmpty()) {
                        Monument_MDL monument = new Monument_MDL();
                        monument.setName(col.get(0).text());
                        monument.setAddress(col.get(1).text());
                        monument.setCoordinates(col.get(2).text());
                        monument.setDate(col.get(3).text());
                        monument.setImage(col.get(4).text());
                        monuments.add(monument);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return monuments;
        }

    public List<Monument_MDL> getAllMonuments() {
        List<Monument_MDL> monuments = new ArrayList<>();
        getMonuments().forEach(monument -> {
            monuments.add(getWikiContent(monument));
        });
        return monuments;
    }
}
