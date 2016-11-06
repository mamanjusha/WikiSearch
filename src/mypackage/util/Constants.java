package mypackage.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 
 * Constants class
 * 
 * @author Manjusha
 *
 */
public class Constants {
	public static final Pattern SUBTITLES_PATTERN = Pattern.compile("={2,}+.+={2,}");
	public static final Pattern CATEGORY_PATTERN = Pattern.compile("\\[\\[[c|C]ategory:.*\\]\\]");
	public static final Pattern URL_PATTERN = Pattern.compile("http[s]?:[^\\s\\]<\\{\\}|]+");
	public static final String TITLE_STRING = "title";
	public static final String TEXT_STRING = "text";
	public static final String PAGE_STRING = "page";
	public static final String ID_STRING = "id";
	public static final String CATEGORY_STRING = "category";
	public static final String SUBTITLE_STRING = "subtitle";
	public static final String URL_STRING = "url";
	public static final String STOP_WORDS = " i a about 0 00 an are as at be by com de en for from how in is it la of on or that the this to was what when where who will with und the www other and quot lt gt 100 ";
	
	public static final Map<String, Integer> INDEX_TYPE_INTEGER_MAP;
	static {
		INDEX_TYPE_INTEGER_MAP = new HashMap<>();
		INDEX_TYPE_INTEGER_MAP.put(TITLE_STRING, 1);
		INDEX_TYPE_INTEGER_MAP.put(CATEGORY_STRING, 2);
		INDEX_TYPE_INTEGER_MAP.put(TEXT_STRING, 3);
		INDEX_TYPE_INTEGER_MAP.put(SUBTITLE_STRING, 4);
		INDEX_TYPE_INTEGER_MAP.put(URL_STRING, 5);
	}
}
