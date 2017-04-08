package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.LinkUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static sun.plugin.javascript.navig.JSType.URL;

/**
 * Created by vladislav on 08.04.17.
 */
public class SiteParser {

    private String site = "";
    public SiteParser(String site) {
        this.site = site;
    }

    public Set<String> start(String url) {
        Document doc;
        LinkUtil linkUtil = new LinkUtil(this.site);
        try {
            doc = Jsoup.connect(this.site + url).get();
        } catch (IOException e) {
            return new HashSet<String>();
        }
        Elements links = doc.select("a[href]");
        return links.stream()
                .map(l -> l.attr("href"))
                .map(l -> l.startsWith("/") ? this.site + l : l)
                .filter(linkUtil::filter)
                .map(l -> {
                    try {
                        return  new URL(l).getPath();
                    } catch (MalformedURLException e) {
                        return  null;
                    }
                })
                .filter(Objects::nonNull)
                .map(l -> l.equals("") ? "/" : l)
                .collect(Collectors.toSet());
    }
}
