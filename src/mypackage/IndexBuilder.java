package mypackage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xerces.parsers.DOMParser;

import lombok.AllArgsConstructor;
import mypackage.model.Page;
import mypackage.parser.DocumentPageParser;

/**
 * Takes wikidump as input, writes index to specified output directory.
 * 
 * @author Manjusha
 *
 */
@AllArgsConstructor
public class IndexBuilder {
	private DocumentPageParser parser;
	private String wikiDumpPath;
	private String destinationDirectoryPath;
	private static final int NUMBER_OF_INPUT_FILES_FOR_INDEX_FILE = 10;

	public static void main(String[] args) throws IOException {
		final IndexBuilder builder = new IndexBuilder(new DocumentPageParser(new DOMParser()), args[0], args[1]);

		final List<File> files = new ArrayList<File>();
		builder.listInputFiles(builder.wikiDumpPath, files);

		File outputDirectory = new File(builder.destinationDirectoryPath);
		if (!outputDirectory.isDirectory()) {
			outputDirectory.mkdir();
		}

		int numberOfOutputIndexFiles = files.size() / NUMBER_OF_INPUT_FILES_FOR_INDEX_FILE;
		if (files.size() % NUMBER_OF_INPUT_FILES_FOR_INDEX_FILE > 0) {
			numberOfOutputIndexFiles++;
		}
		int filesCount = 0;
		for (int count = 0; count < numberOfOutputIndexFiles; count++) {
			List<Page> pages = new ArrayList<Page>();
			while (true) {
				pages.addAll(builder.parser.parse(files.get(filesCount++).getPath()));
				if (filesCount % NUMBER_OF_INPUT_FILES_FOR_INDEX_FILE == 0 || filesCount >= files.size()) {
					IndexFileWriter.writeToFile(pages, new File(outputDirectory, String.valueOf(count)));
					break;
				}
			}
		}
	}

	private void listInputFiles(final String wikiDumpPath, final List<File> files) {
		File file = new File(wikiDumpPath);
		if (file.isDirectory()) {
			String[] listedFiles = file.list();
			for (String listedFile : listedFiles) {
				listInputFiles(listedFile, files);
			}
		} else {
			files.add(file);
		}
	}

}
