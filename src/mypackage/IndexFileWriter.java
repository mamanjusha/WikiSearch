package mypackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import mypackage.model.Page;
import mypackage.util.Constants;

/**
 * Class to write pages to index files.
 * File format :
 *  
 *  title_1 = id1 1, id2 2
 *  subtitle_4 = id1 1, id2 3
 *   
 * @author Manjusha
 *
 */
public class IndexFileWriter {

	public static void writeToFile(List<Page> pages, final File file) throws IOException {
		final TreeMap<String, TreeMap<String, String>> map = new TreeMap<String, TreeMap<String, String>>();
		final StringSort stringSort = new StringSort();
		for (Page page : pages) {
			addToMap(map, page);
		}
		
		Iterator<String> it = map.keySet().iterator();
		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		while (it.hasNext()) {
			ArrayList<String> ids = new ArrayList<String>();
			String id = it.next();
			String original = id + "=";
			TreeMap<String, String> tre = map.get(id);
			Iterator<String> it1 = tre.keySet().iterator();
			while (it1.hasNext()) {
				String s2 = it1.next();
				ids.add(s2 + " " + tre.get(s2));
			}
			Collections.sort(ids, (Comparator) stringSort);
			for (String s1 : ids)
				original = original + s1 + ",";
			br.write(original, 0, original.length() - 1);
			br.newLine();
		}
		br.close();

	}

	private static void addToMap(final TreeMap<String, TreeMap<String, String>> map, final Page page) {
		addToMap(map, Tokenizer.tokenize(page.getCategories()), Constants.CATEGORY_STRING, page.getPageId());
		addToMap(map, Tokenizer.tokenize(page.getText()), Constants.TEXT_STRING, page.getPageId());
		addToMap(map, Tokenizer.tokenize(page.getTitle()), Constants.TITLE_STRING, page.getPageId());
		addToMap(map, Tokenizer.tokenize(page.getSectionTitles()), Constants.SUBTITLE_STRING, page.getPageId());
		addToMap(map, Tokenizer.tokenize(page.getUrls()), Constants.URL_STRING, page.getPageId());
	}

	private static void addToMap(final TreeMap<String, TreeMap<String, String>> map, final List<String> categoryWords,
			final String categoryString, final String pageId) {
		for (String word : categoryWords) {
			final String key = word + "_" + String.valueOf(Constants.INDEX_TYPE_INTEGER_MAP.get(categoryString));
			if (map.containsKey(key)) {
				TreeMap<String, String> postings = map.get(key);
				if (postings.containsKey(pageId)) {
					int count = Integer.parseInt(postings.get(pageId));
					count++;
					postings.put(pageId, String.valueOf(count++));
				} else
					postings.put(pageId, String.valueOf(1));
				map.put(key, postings);
			} else {
				TreeMap<String, String> postings = new TreeMap<String, String>();
				postings.put(pageId, String.valueOf(1));
				map.put(key, postings);
			}
		}
	}

}

class StringSort implements Comparator<String> {
	public int compare(String s1, String s2) {
		Integer i1 = Integer.valueOf(s1.substring(0, s1.lastIndexOf(' ')));
		Integer i2 = Integer.valueOf(s2.substring(0, s2.lastIndexOf(' ')));
		return i1.compareTo(i2);
	}
}
