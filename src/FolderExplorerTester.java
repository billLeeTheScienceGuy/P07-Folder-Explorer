
//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Folder Explorer Tester
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
import java.util.Arrays;
import java.util.List;

/*
 * This class tests the functionality of the FolderExplorer class.
 */
public class FolderExplorerTester {
	/*
	 * This method tests the functionality of the getContents method of the
	 * FolderExplorer class.
	 * 
	 * @param folder is the name of the folder of the files that is used to test the
	 * 				methods.
	 * 
	 * @return true if the test has passed without errors.
	 */
	public static boolean testGetContents(File folder) {
		try {
			// Scenario 1
			// list the basic contents of the cs300 folder
			ArrayList<String> listContent = FolderExplorer.getContents(folder);
			// expected output must contain "exams preparation", "grades",
			// "lecture notes", "programs", "reading notes", "syllabus.txt",
			// and "todo.txt" only.
			String[] contents = new String[] { "exams preparation", "grades", "lecture notes", "programs",
					"reading notes", "syllabus.txt", "todo.txt" };
			List<String> expectedList = Arrays.asList(contents);
			// check the size and the contents of the output
			if (listContent.size() != 7) {
				System.out.println("Problem detected: cs300 folder must contain 7 elements.");
				return false;
			}
			for (int i = 0; i < expectedList.size(); i++) {
				if (!listContent.contains(expectedList.get(i))) {
					System.out.println("Problem detected: " + expectedList.get(i)
							+ " is missing from the output of the list contents of cs300 folder.");
					return false;
				}
			}
			// Scenario 2 - list the contents of the grades folder
			File f = new File(folder.getPath() + File.separator + "grades");
			listContent = FolderExplorer.getContents(f);
			if (listContent.size() != 0) {
				System.out.println("Problem detected: grades folder must be empty.");
				return false;
			}
			// Scenario 3 - list the contents of the p02 folder
			f = new File(folder.getPath() + File.separator + "programs" + File.separator + "p02");
			listContent = FolderExplorer.getContents(f);
			if (listContent.size() != 1 || !listContent.contains("FishTank.java")) {
				System.out.println("Problem detected: p02 folder must contain only " + "one file named FishTank.java.");
				return false;
			}
//Scenario 4 - List the contents of a file
			f = new File(folder.getPath() + File.separator + "todo.txt");
			try {
				listContent = FolderExplorer.getContents(f);
				System.out.println("Problem detected: Your FolderExplorer.getContents() must "
						+ "throw a NotDirectoryException if it is provided an input which is not" + "a directory.");
				return false;

			} catch (NotDirectoryException e) { // catch only the expected exception
//no problem detected
			}
//Scenario 5 - List the contents of not found directory/file
			f = new File(folder.getPath() + File.separator + "music.txt");
			try {
				listContent = FolderExplorer.getContents(f);
				System.out.println("Problem detected: Your FolderExplorer.getContents() must "
						+ "throw a NotDirectoryException if the provided File does not exist.");
				return false;
			} catch (NotDirectoryException e) {
//behavior expected
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown" + " a non expected exception.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * This method tests the base functionality of the getDeepContents method of the
	 * FolderExplorer class.
	 * 
	 * @param folder is the name of the folder of the files that is used to test the
	 * 				methods.
	 * 
	 * @return true if the test has passed without errors.
	 */
	public static boolean testDeepGetContentsBaseCase(File folder) {
		try {
			File file = new File(folder.getPath() + File.separator + "reading notes");
			ArrayList<String> contents = new ArrayList<String>(FolderExplorer.getDeepContents(file));
			String[] expectedContents = new String[] { "zyBooksCh1.txt", "zyBooksCh2.txt", "zyBooksCh3.txt",
					"zyBooksCh4.txt" };
			List<String> expectedList = Arrays.asList(expectedContents);
			if (contents.size() != 4) {
				System.out.println("Problem detected: reading notes folder must contain 4 elements.");
				return false;
			}
			if (!contents.equals(expectedList)) {
				System.out.println("Error: the base case of getDeepContents is not returning correctly");
				return false;
			}
		} catch (NotDirectoryException e) {
			System.out.println(e);
			return false;
		} 
		catch (Exception e) {
			System.out.println("Error: testDeepGetContentsBaseCase is throwing an Exception");
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/*
	 * This method tests the recursive functionality of the getDeepContents method
	 * of the FolderExplorer class.
	 * 
	 * @param folder is the name of the folder of the files that is used to test the
	 * 				methods.
	 * 
	 * @return true if the test has passed without errors.
	 */
	public static boolean testDeepListRecursiveCase(File folder) {
		try {
			File file = new File(folder.getPath() + File.separator + "lecture notes" + File.separator + "unit1");
			ArrayList<String> contents = new ArrayList<String>(FolderExplorer.getDeepContents(file));
			String[] expectedContents = new String[] { "ExceptionHandling.txt", "proceduralProgramming.txt",
					"UsingObjects.txt" };
			List<String> expectedList = Arrays.asList(expectedContents);
			if (contents.size() != 3) {
				System.out.println("Problem detected: unit1 folder must contain 3 elements.");
				return false;
			}
			if (!contents.equals(expectedList)) {
				System.out.println("Error: the recursive case of getDeepContents is not returning correctly");
			}
		} catch (NotDirectoryException e) {
			System.out.println(e);
			return false;
		} catch (Exception e) {
			System.out.println("Error: testDeepListRecursiveCase is throwing an Exception");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * This method tests the functionality of the lookupByName method of the
	 * FolderExplorer class.
	 * 
	 * @param folder is the name of the folder of the files that is used to test the
	 * 				methods.
	 * 
	 * @return true if the test has passed without errors.
	 */
	public static boolean testLookupByFileName(File folder) {
		String fileName = "zyBooksCh1.txt";
		String correctPath = "cs300\\reading notes\\zyBooksCh1.txt";
		if (!correctPath.equals(FolderExplorer.lookupByName(folder, fileName))) {
			System.out.println("Error: The output of the lookupByName does not match the expected output");
			return false;
		}

		return true;

	}

	/*
	 * This method tests the functionality of the lookupByKey method of the
	 * FolderExplorer class.
	 * 
	 * @param folder is the name of the folder of the files that is used to test the
	 * 				methods.
	 * 
	 * @return true if the test has passed without errors.
	 */
	public static boolean testLookupByKeyBaseCase(File folder) {
		try {
			File file = new File(folder.getPath() + File.separator + "exams preparation" + File.separator + "exam1");
			ArrayList<String> contents = new ArrayList<String>(FolderExplorer.lookupByKey(file, ".java"));
			String[] expectedContents = new String[] { "codeSamples.java" };
			List<String> expectedList = Arrays.asList(expectedContents);
			if (contents.size() != 1) {
				System.out.println("Problem detected: there should be only one .java file in the folder");
				return false;
			}
			if (!contents.equals(expectedList)) {
				System.out.println("Error: the base case of lookupByKey is not returning correctly");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Error: testLookupByKeyBaseCase is throwing an Exception");
			e.printStackTrace();
			return false;
		}
		return true;

		/*
		 * This method calls all the test methods and checks if they passed or not
		 * 
		 * @param folder is the name of the folder of the files that is used to test the
		 * 				methods.
		 * 
		 * @returns true if all the methods have passed the test.
		 */
	}

	public static boolean runAllTests(File folder) {
		if (testGetContents(folder) == false) {
			System.out.println("Error: testGetContents is returning false");
			return false;
		}
		if (testDeepGetContentsBaseCase(folder) == false) {
			System.out.println("Error: testDeepGetContentsBaseCase is returning false");
			return false;
		}
		if (testDeepListRecursiveCase(folder) == false) {
			System.out.println("Error: testDeepListRecursiveCase is returning false");
			return false;
		}
		if (testLookupByFileName(folder) == false) {
			System.out.println("Error: testLookupByFileName is returning false");
			return false;
		}
		if (testLookupByKeyBaseCase(folder) == false) {
			System.out.println("Error: testLookupByKeyBaseCase is returning false");
			return false;
		} else {
			System.out.println("All tests passed");
			return true;
		}
	}

	/*
	 * This is the main method that calls the runAllTests method with the name of
	 * the folder.
	 */
	public static void main(String[] args) {
		System.out.println(runAllTests(new File("cs300")));
	}
}
