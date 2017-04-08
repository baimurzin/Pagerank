import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.Crawler;
import service.PageRankCalc;
import service.SiteParser;
import service.StorageManager;
import util.LinkUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by vladislav on 06.04.17.
 */
public class Run {

    private final static String URL = "http://kpfu.ru";

    public static void main(String[] args) throws IOException {
        PrintWriter pr = new PrintWriter("result.txt");
        SiteParser parser = new SiteParser(URL);
        PageRankCalc pageRankCalc = new PageRankCalc();
        Crawler crawler = new Crawler(parser);
        StorageManager matrix = crawler.crawl();

        List<Double> ranks = pageRankCalc.calculate(matrix);
        Map<String, Double> result = new HashMap<>();
        IntStream.range(0, ranks.size())
                .forEach(r -> result.put(matrix.getPage(r), ranks.get(r)));
        result.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(e -> {
                    pr.print(e.getKey() + "\t");
                    pr.println(e.getValue());
                });
        pr.println("Execution time: " + pageRankCalc.getTime());

        pr.close();
    }
}
