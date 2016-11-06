package mypackage.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.AllArgsConstructor;
import mypackage.model.Page;
import mypackage.util.Constants;

/**
 * 
 * Document parser to parse the Wikipedia document.
 * 
 * @author Manjusha
 *
 */
@AllArgsConstructor
public class DocumentPageParser {

	private DOMParser parser;

	public List<Page> parse(final String filePath) {

		try {
			parser.parse(filePath);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		final Document doc = parser.getDocument();
		final Element file = doc.getDocumentElement();
		final NodeList pages = file.getElementsByTagName(Constants.PAGE_STRING);
		if (pages.getLength() > 0) {
			List<Page> documentPages = new ArrayList<>();
			for (int count = 0; count < pages.getLength(); count++) {
				documentPages.add(parse((Element) pages.item(count)));
			}
			return documentPages;
		}
		return null;
	}

	private Page parse(final Element page) {

		final String id = getElementByTagName(page, Constants.ID_STRING).getNodeValue();
		final String title = getElementByTagName(page, Constants.TITLE_STRING).getNodeValue();
		final String text = getElementByTagName(page, Constants.TEXT_STRING).getNodeValue();
		final String sectionTitles = getSectionTitles(text);
		final String categories = getCategories(text);
		final String urls = getUrls(text);

		final Page documentPage = new Page(id);
		documentPage.setTitle(title);
		documentPage.setCategories(categories);
		documentPage.setSectionTitles(sectionTitles);
		documentPage.setUrls(urls);
		documentPage.setText(text.replaceAll(Constants.URL_PATTERN.pattern(), " "));
		return documentPage;
	}

	private String getUrls(final String text) {
		final StringBuilder urlsBuilder = new StringBuilder();
		Matcher matcher = Constants.URL_PATTERN.matcher(text);

		while (matcher.find()) {
			String st = matcher.group();
			urlsBuilder.append(st).append("\n");
		}
		return urlsBuilder.toString();
	}

	private String getCategories(final String text) {
		final StringBuilder categoryBuilder = new StringBuilder();
		Matcher matcher = Constants.CATEGORY_PATTERN.matcher(text);

		while (matcher.find()) {
			String st = matcher.group();
			categoryBuilder.append(st).append("\n");
		}
		return categoryBuilder.toString();
	}

	private String getSectionTitles(final String text) {
		final StringBuilder titleBuilder = new StringBuilder();
		Matcher matcher = Constants.SUBTITLES_PATTERN.matcher(text);

		while (matcher.find()) {
			String st = matcher.group();
			String[] r = st.split("={2,}+");
			titleBuilder.append(r[1]);
		}
		return titleBuilder.toString();
	}

	private Element getElementByTagName(final Element page, final String type) {
		final NodeList nodeList = page.getElementsByTagName(type);
		return (Element) nodeList.item(0);
	}

}
