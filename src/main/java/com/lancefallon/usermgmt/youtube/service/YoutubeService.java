package com.lancefallon.usermgmt.youtube.service;

import com.lancefallon.usermgmt.config.model.AppProperties;
import com.lancefallon.usermgmt.youtube.model.YoutubeSnippet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeService {

    private final String baseUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=";
    private final String endUrl = "&type=video&maxResults=24&key=";
    private final RestTemplate restTemplate;

    @Value("${youtube.api.key}")
    private String youtubeApiKey;

    public YoutubeService() {
        restTemplate = new RestTemplate();
    }

    public List<YoutubeSnippet> search(String query) {
        String response = restTemplate.getForObject(baseUrl + query + endUrl + youtubeApiKey, String.class);

        List<YoutubeSnippet> youtubeSnippets = new ArrayList<>();

        JSONArray items = new JSONObject(response).getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
            String videoId = items.getJSONObject(i).getJSONObject("id").getString("videoId");
            String title = items.getJSONObject(i).getJSONObject("snippet").getString("title");
            String thumbnail = items.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
            youtubeSnippets.add(new YoutubeSnippet(videoId, title, thumbnail));
        }

        return youtubeSnippets;
    }

}
