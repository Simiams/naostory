package com.naostory.clients;

import java.io.IOException;

import com.naostory.models.Monument_MDL;
import com.naostory.repositories.IMonument_REP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
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
            String wikipediaPageURL = "https://fr.wikipedia.org/" + monument.getUrl();
            String className = "mw-parser-output";
            String id = "mw-content-text";

            try {
                Document doc = Jsoup.connect(wikipediaPageURL).get();
                Objects.requireNonNull(Objects.requireNonNull(doc.getElementById(id)).getElementsByClass(className)).get(0).children().forEach(element -> {
                    if (element.tagName().equals("p") && !element.text().isEmpty()) {
                        if (monument.getContent() == null)
                            monument.setContent(element.toString());
                        else
                            monument.setContent(Objects.requireNonNull(monument.getContent()) + element.toString());
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
                        monument.setUrl(col.get(0).select("a").attr("href"));
                        monument.setAddress(col.get(1).text());
                        monument.setCoordinates(col.get(2).text());
                        monument.setLongitude(col.get(2).select("a").attr("data-lon"));
                        monument.setLatitude(col.get(2).select("a").attr("data-lat"));
                        monument.setDate(col.get(5).text());
                        monument.setImage(transformImageUrl("https:" + col.get(6).select("img").attr("src"), 390));
                        monument.setTopLeftCornerLat((Float.parseFloat(monument.getLatitude()) + 0.002f) + "");
                        monument.setTopLeftCornerLong((Float.parseFloat(monument.getLongitude()) - 0.002f) + "");
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
        iMonumentRep.saveAll(monuments);
        return monuments;
    }

    public static String transformImageUrl(String imageUrl, int newWidth) {
        try {
            URL url = new URL(imageUrl);
            String path = url.getPath();
            String[] pathSegments = path.split("/");
            String fileName = pathSegments[pathSegments.length - 1];
            String newImageUrl = url.getProtocol() + "://" + url.getHost() + path.replace(fileName, newWidth + "px-" + fileName);
            return newImageUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return imageUrl;
        }
    }
}
