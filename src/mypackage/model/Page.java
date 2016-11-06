package mypackage.model;

import lombok.Data;
import lombok.ToString;

/**
 * Model class for page.
 * 
 * @author Manjusha
 *
 */
@Data
@ToString(exclude={"title", "text", "urls", "sectionTitles", "categories"})
public final class Page {
	
	private final String pageId;
	
	private String title;
	
	private String text;

	private String urls;
	
	private String sectionTitles;

	private String categories;
}
