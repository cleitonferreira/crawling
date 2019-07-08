package br.com.crawling.model;

import java.io.File;
import java.io.FilenameFilter;
 
public class MyFileFilter implements FilenameFilter {
     
    @Override
    public boolean accept(File directory, String fileName) {
        if (fileName.endsWith(".txt") || fileName.endsWith(".pdf") || fileName.endsWith(".docx") || fileName.endsWith(".doc") || fileName.endsWith(".ppt") || fileName.endsWith(".pptx") ) {
            return true;
        }
        return false;
    }

}

