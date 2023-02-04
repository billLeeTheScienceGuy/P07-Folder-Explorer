//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Folder Explorer
// Course:   CS 300 Fall 2021
//
// Author:   Bill Lee
// Email:    blee266@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    NA
// Partner Email:   NA
// Partner Lecturer's Name: NA
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         None
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////
import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
/*
 * This class contains multiple functions in order to explore directories and look up files.
 */

public class FolderExplorer {
	/*
	 * This method returns a list of the names of all files and directories in the
	 * the given folder currentDirectory.
	 * 
	 * @param currentDirectory is the directory that the user is currently in.
	 * 
	 * @Throws NotDirectoryException with a description error message if the
	 * 				provided currentDirectory does not exist or if it is not a directory.
	 */
	public static ArrayList<String> getContents(File currentDirectory) throws NotDirectoryException {
		ArrayList<String> listNames = new ArrayList<>();
		if (!currentDirectory.isDirectory()) {
			throw new NotDirectoryException("What you have selected is not a directory");
		} else {
			for (int i = 0; i < currentDirectory.list().length; ++i) {
				listNames.add(currentDirectory.list()[i]);
			}
		}
		return listNames;
	}
	/*
	 * This method is a recursive method that lists the names of all the files (not
	 * directories) in the given directory and its sub-directories.
	 * 
	 * @param currentDirectory is the directory that the user is currently in.
	 * 
	 * @returns all the contents inside the directory.
	 * 
	 * @Throws NotDirectoryException with a description error message if the
	 * 				provided currentDirectory does not exist or if it is not a directory
	 */

	public static ArrayList<String> getDeepContents(File currentDirectory) throws NotDirectoryException {
		ArrayList<String> listNames = new ArrayList<>();
		if (!currentDirectory.isDirectory()) {
			throw new NotDirectoryException("What you have selected is not a directory");
		} else {
			for (int i = 0; i < currentDirectory.listFiles().length; ++i) {
				if (!currentDirectory.listFiles()[i].isDirectory()) {
					listNames.add(currentDirectory.list()[i]);
				} else {
					listNames.addAll(getDeepContents(currentDirectory.listFiles()[i]));
				}
			}
		}
		return listNames;
	}
	/*
	 * This method searches the given directory and all of its sub-directories for
	 * an exact match to the provided fileName.
	 * 
	 * @returns a path to the file, if it exists.
	 * 
	 * @param currentDirectory is the directory that the user is currently in.
	 * 
	 * @param fileName is the name of the file that is being looked up.
	 * 
	 * @Throws NoSuchElementException with a descriptive error message if the search
	 * 				operation returns with no results found (including the case if fileName is
	 * 				null or currentDirectory does not exist, or was not a directory).
	 */

	public static String lookupByName(File currentDirectory, String fileName) {

		if (fileName == null || !currentDirectory.isDirectory() || !currentDirectory.exists()) {
			throw new NoSuchElementException("Error: File is corrupted or isn't a directory");
		}
		//store is the string that stores the values during recursive calls.
		String store = "Base";
		for (int i = 0; i < currentDirectory.listFiles().length && store.equals("Base"); ++i) {
			if (currentDirectory.listFiles()[i].isFile()
					&& currentDirectory.listFiles()[i].getName().equals(fileName)) {
				return currentDirectory.listFiles()[i].getPath();
			}
			if (currentDirectory.listFiles()[i].isDirectory()) {
				try {
					store = lookupByName(currentDirectory.listFiles()[i], fileName);
				} catch (NoSuchElementException e2) {
				}
			}

		}
		// If the original content of store hasn't changed even after the recursive
		// call, then the file does not exist.
		if (store.equals("Base")) {
			throw new NoSuchElementException("The file doesn't exist");
		}
		return store;
	}
	/*
	 * This method is a recursive method that searches the given folder and its
	 * sub-directories for ALL files that contain the given key in part of their
	 * name.
	 * 
	 * @param currentDirectory is the directory that the user is currently in.
	 * 
	 * @param key is the keyword that is used to search the files that contain it.
	 * 
	 * @returns An arraylist of all the names of files that match and an empty
	 * 				arraylist when the operation returns with no results found (including the
	 * 				case where currentDirectory is not a directory).
	 * 
	 */

	public static ArrayList<String> lookupByKey(File currentDirectory, String key) {
		//store is the arraylist that stores the values during recursive calls.
		ArrayList<String> store = new ArrayList<>();
		for (int i = 0; i < currentDirectory.listFiles().length; ++i) {
			if (!currentDirectory.listFiles()[i].isFile()) {
				store.addAll(lookupByKey(currentDirectory.listFiles()[i], key));
			} else if (currentDirectory.listFiles()[i].getName().contains(key)) {
				store.add(currentDirectory.listFiles()[i].getName());
			}
		}
		return store;
	}
	/*
	 * This method is a recursive method that searches the given folder and its
	 * sub-directories for ALL files whose size is within the given max and min
	 * values, inclusive.
	 * 
	 * @param currentDirectory is the directory that the user is currently in.
	 * 
	 * @param sizeMin is the minimum size that the file needs to be in order to be
	 * 				searched up
	 * 
	 * @param sizeMax is the maximum size that the file needs to be in order to be
	 * 				searched up
	 * 
	 * @returns an array list of the names of all files whose size are within the
	 * 			boundaries and an empty arraylist if the search operation returns with no
	 * 			results found (including the case where currentDirectory is not a directory).
	 */

	public static ArrayList<String> lookupBySize(File currentDirectory, long sizeMin, long sizeMax) {
		//store is the arraylist that stores the values during recursive calls.
		ArrayList<String> store = new ArrayList<>();
		for (int i = 0; i < currentDirectory.listFiles().length; ++i) {
			if (!currentDirectory.listFiles()[i].isFile()) {
				store.addAll(lookupBySize(currentDirectory.listFiles()[i], sizeMin, sizeMax));
			}
			else if (currentDirectory.listFiles()[i].getTotalSpace() >= sizeMin
					&& currentDirectory.listFiles()[i].getTotalSpace() <= sizeMax) {
					store.add(currentDirectory.listFiles()[i].getName());
				}
			}
		
		return store;
	}
}
