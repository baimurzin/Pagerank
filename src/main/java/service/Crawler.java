package service;

import java.util.LinkedList;
import java.util.Set;

/**
 * Created by vladislav on 08.04.17.
 */
public class Crawler {

    private SiteParser parser;
    private LinkedList<String> q = new LinkedList<>();

    public Crawler(SiteParser parser) {
        this.parser = parser;
    }

    public StorageManager crawl() {
        StorageManager matrix = new StorageManager();
        q.add("/");
        while (!q.isEmpty()) {
            System.out.println("Queued: " + q.size());
            crawl(q.get(0), matrix);
            q.removeFirst();
        }

        matrix.print();
        return matrix;
    }

    private void crawl(String url, StorageManager matrix) {
        if (matrix.isParsed(url) || (matrix.isFull() && !matrix.isExist(url))) return;
        System.out.println("Processing: " + url);
        Set<String> pages = parser.start(url);
        System.out.println("Founded: " + pages.size());
        pages = matrix.add(url, pages);
        q.addAll(pages);
    }
}
