package cold.fyre.API;

import java.io.File;

/**
 * Contains several functions that allow accessing, changing, or otherwise interacting with files and their directories.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 1.0
 *
 */
public class FileManager {
	
	public static File getFolder(String path) {
		File toReturn = new File(path);
		if(!toReturn.exists())
			toReturn.mkdir();
		return toReturn;
	}

}
