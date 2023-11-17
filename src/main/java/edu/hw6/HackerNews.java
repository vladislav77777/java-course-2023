package edu.hw6;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {
    private static final String TOP_STORIES_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    private static final String ITEM_URL_FORMAT = "https://hacker-news.firebaseio.com/v0/item/%d.json";
    private final int statusCodeOK = 200;

    public long[] hackerNewsTopStories() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOP_STORIES_URL))
                .build(); // the builder creates GET query by default

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == statusCodeOK) {
                String[] idsStringArray = response.body().replaceAll("[\\[\\]\"]", "").split(",");
                long[] ids = new long[idsStringArray.length];

                for (int i = 0; i < idsStringArray.length; i++) {
                    ids[i] = Long.parseLong(idsStringArray[i].trim());
                }

                return ids;
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return new long[0];
    }

    public String news(long id) {
        try {
            String itemUrl = String.format(ITEM_URL_FORMAT, id);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(itemUrl))
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == statusCodeOK) {
                // Use regex to extract the title from the JSON response
                Pattern pattern = Pattern.compile("\"title\":\"(.*?)\"");
                Matcher matcher = pattern.matcher(response.body());

                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return "Title not found";
    }

}
