package com.naostory.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Objects;

public class Wiki_CLT {

        public static void main(String[] args) {
        String wikipediaPageURL = "https://fr.wikipedia.org/wiki/Albert_Einstein";
        String id = "mw-content-text";

        try {
            Document doc = Jsoup.connect(wikipediaPageURL).get();
            Elements paragraphs = Objects.requireNonNull(doc.getElementById(id)).children();

            System.out.println(paragraphs.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
}
