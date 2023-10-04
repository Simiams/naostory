package com.naostory.controllers;

import com.naostory.clients.Wiki_CLT;
import com.naostory.models.Monument_MDL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Wiki_CTR {
    private final Wiki_CLT wikiClt;

    @Autowired
    public Wiki_CTR(Wiki_CLT wikiClt) {
        this.wikiClt = wikiClt;
    }

    @GetMapping("/hello")
    public String getMonuments() {
        return "hello";
    }

    @GetMapping("/wiki")
    public List<Monument_MDL> getAllMonuments() {
        return wikiClt.getAllMonuments();
    }

//    @GetMapping("/content")
//    public String getWikiContent() {
////        return wikiClt.getWikiContent();
//    }

}
