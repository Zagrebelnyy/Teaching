import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ParseHTML {
    private String url;
    private Document doc;
    Elements elements;
    String element;
    ArrayList<String> data;

    public ParseHTML(String url, String elements, String element) throws IOException {
        this.url = url;
        doc = Jsoup.connect(this.url).get();
        this.element = element;
        this.data = new ArrayList<String>();
        this.elements = doc.select(elements);
        for (Element elem : this.elements.select(this.element)) {
            this.data.add(elem.text());
        }

    }

    public ArrayList getData() {
        return this.data;
    }
}
