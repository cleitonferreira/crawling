package br.com.crawling.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainFile {


	private static String DIRETORIO = "/Users/cleitonferreiraa/Documents/cortex/";


	public static void main(String[] args) {
		
		getFiles(DIRETORIO);
	
	}
	
	public static void getFiles(String path) {
	    File dir = new File(path);
	    String[] children = dir.list();
	    
	    
	    if (children != null) {
	        for (int i = 0; i < children.length; i++) {
	            // Get filename of file or directory
	            String filename = children[i];
	            File file = new File(path + File.separator + filename);
	            if (!file.isDirectory()) {
	            	
	            	FilenameFilter filter = new MyFileFilter();

	            	if(filter.accept(file, filename)) {
	            		System.out.println(file.getAbsolutePath());
	                    System.out.println(filename);
	                    
	                    if(filename.endsWith(".txt")) {
	                    	List<String> list = new ArrayList<String>();

	                		try (BufferedReader br = Files.newBufferedReader(Paths.get(file.getAbsolutePath()))) {
	                			list = br.lines().collect(Collectors.toList());

	                		} catch (IOException e) {
	                			e.printStackTrace();
	                		}
	                	
	                		list.forEach(System.out::println); 	                		
	                    }
	                    System.out.println();
	            	}

	            } else {
	                getFiles(path + File.separator + filename);
	            }
	        }
	    }
	}

}
