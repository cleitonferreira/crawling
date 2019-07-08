package br.com.crawling.model;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class MainUrl {

	private HashSet<String> links;

	public MainUrl() {
		links = new HashSet<String>();
	}

	public static void main(String[] args) throws IOException {
		 Validate.isTrue(args.length != 2, "fornecer 2 argumentos: url inicial(String) e a profundidade do crawler(int)");
		 String url = args[1];
		 System.out.println("Url: "+ url);

		//new ListLinks().getPageLinks("https://github.com/cleitonferreira", 0, 1);
		
		new MainUrl().getPageLinks(args[1], 0, Integer.parseInt(args[2]));

	}

	public void getPageLinks(String URL, int start, int finish) {

		if ((!links.contains(URL))) {

			if (start < finish) {
				try {
					Document document = Jsoup.connect(URL).get();
					Elements linksOnPage = document.select("a[href]");

					print("\nLinks: (%d)", linksOnPage.size());
					start++;
					for (Element page : linksOnPage) {
						print(" Page: <%s>  (%s)", page.attr("abs:href"), trim(page.text(), 35));
						getPageLinks(page.attr("abs:href"), start, finish);
					}
				} catch (IOException e) {
					System.err.println("Error - '" + URL + "': " + e.getMessage());
				}
			}

		}
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
}
