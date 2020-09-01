package cold.fyre.API;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.bukkit.Bukkit;

/**
 * Contains several functions that allow accessing, changing, or otherwise interacting with files and their directories.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public class FileManager {
	
	/**
	 * Returns a File instance of the Folder of the path given. If the
	 * folder does not exist, then one is created.
	 * @param path - Path of folder.
	 * @return File of Folder.
	 */
	public static File getFolder(String path) {
		File toReturn = new File(path);
		if(!toReturn.exists())
			toReturn.mkdir();
		return toReturn;
	}
	
	/**
	 * Gets the Folder that contains all the plugins and their PluginDataFolder.
	 * @return File of plugins Folder.
	 */
	public static File getPluginsFolder() { return new File(Bukkit.getServer().getWorldContainer() +"/plugins"); }
	
	/**
	 * Checks if the file is not null. If the file is null, then the file is created and returned.
	 * @param file - file to check.
	 * @return file
	 */
	public static File NotNull(File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return file;
	}
	
	/**
	 * Renames the current file to the name given then returns the file.
	 * @param file - file to rename.
	 * @param newName - Name of file.
	 * @return File
	 */
	public static File renameFile(File file, String newName) {
		String fileExtension = "." + file.getName().split(".")[1];
		File toChange = new File(file.getPath() + newName + fileExtension);
		file.renameTo(toChange);
		return file;
	}
	
	/**
	 * Moves a file from one folder to another.
	 * @param from - File to move.
	 * @param folder - Folder to move to.
	 * @return File with new location.
	 */
	public static File moveFileToFolder(File from, File folder) {
		File toMove = new File(folder, from.getName());
		
		try {
			if(!toMove.exists())
				toMove.createNewFile();
			
			Files.copy(from.toPath(), new FileOutputStream(toMove));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		from.delete();
		return toMove;
	}

}
