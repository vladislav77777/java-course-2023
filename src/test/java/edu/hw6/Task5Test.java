package edu.hw6;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

public class Task5Test {

    @Test
    void testTopStories() {
        HackerNews hackerNews = new HackerNews();
        long[] topStories = hackerNews.hackerNewsTopStories();

        if (topStories.length > 0) {
            System.out.println("Top Stories: " + Arrays.toString(topStories));

        }
    }

    @Test
    void testNewTitle() {
        HackerNews hackerNews = new HackerNews();
        String newsTitle = hackerNews.news(37570037);
        System.out.println("News Title: " + newsTitle);
    }
}
