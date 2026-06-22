package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/**
 * Log files are named with the following convention:
 * log_YYYY-MM-dd_HH-mm-ss.txt
 * Logs are stored in CookieClickerTools/Logs
 */
public class Logger {
	
	private static final String LOG_FILE_NAME = 
			"Logs/log_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd_HH-mm-ss"))+ ".txt";
	private static String logMessage = "";
	private static FileWriter fileWriter = null;
	
	/**
	 * Opens the file and writes the header if the file was successfully opened
	 */
	public static void buildLogger() {
		Logger.log("Building Logger");
		openFile();
		if ( fileWriter != null)
			buildFileHeader();
	}
	
	/**
	 * Combines the current time with the given string and saves it to the log
	 * @param s the string to be logged
	 */
	public static void log(String s) {
		logMessage = "[" + LocalTime.now() + "] : " + s;
		System.out.println(logMessage);
		if ( fileWriter != null ) {
			writeToFile(logMessage);
		}

	}
	
	/**
	 * Takes the current date and a message and writes it to the file
	 */
	public static void buildFileHeader() {
		String fileHeader = "[" + LocalDate.now() + "] : " + "Log File For CookieClickerTools";
		writeToFile(fileHeader);
	}
	/**
	 * Opens the file and creates the FileWriter
	 */
	public static void openFile() {
		File file = new File(LOG_FILE_NAME);
		try {
			fileWriter = new FileWriter(file,false);
			log("File Opened");
		} catch (IOException e) {
			fileWriter = null;
			log("Could not open file, continuing without saving logs.");
		}
	}
	/**
	 * Writes the given string to the file open in the FileWriter
	 * @param s The message to be written to the file
	 */
	private static void writeToFile(String s) {
		//System.out.println("Logger.witeToFile() called");
		try {
			fileWriter.write(s + "\n");
			fileWriter.flush();
			//System.out.println("Successfully added to file");
		} catch (IOException e) {
			log("WARNING: Failed to write \"" + s + "\" to the log file.");
		} catch ( NullPointerException e ) {
			log("NullPointerException on Logger, logger file invalid or missing.");
		}
	}
	
	/**
	 * Closes the file currently open in the FileWriter
	 */
	@OnShutdown
	public static void closeFile() {
		try {
			fileWriter.close();
			//prevents endless looping for the case where the window 
			//is closed and the rest of the program is still active
			fileWriter = null; 
		} catch (IOException e) {
			log("Failed to close log file.");
		}
		
	}

	//Getters
	public static FileWriter getFileWriter() {
		return fileWriter;
	}
	
	
}
