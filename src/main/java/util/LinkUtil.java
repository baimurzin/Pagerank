package util;

/**
 * Created by vladislav on 07.04.17.
 */
public class LinkUtil {

    private static final String HTTP_PROTOCOL = "http://";
    private static final String HTTPS_PROTOCOL = "https://";
    private static final String SAME_PAGE_ELEM = "#";

    private String site = "";

    public LinkUtil(String site) {
        this.site = site;
    }

    public boolean filter(String check) {
        return check.startsWith(this.site) || !(check.startsWith(HTTP_PROTOCOL) || check.startsWith(HTTPS_PROTOCOL))
                && !check.contains(SAME_PAGE_ELEM);
    }

}
