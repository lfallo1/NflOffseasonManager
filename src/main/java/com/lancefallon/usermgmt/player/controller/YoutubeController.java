package com.lancefallon.usermgmt.player.controller;

import com.lancefallon.usermgmt.youtube.model.YoutubeSnippet;
import com.lancefallon.usermgmt.youtube.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/youtube")
public class YoutubeController {

    @Autowired
    private YoutubeService youtubeService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<YoutubeSnippet>> searchYoutube(@RequestParam("q") String query) {
        return new ResponseEntity<>(youtubeService.search(query), HttpStatus.OK);
    }

}
