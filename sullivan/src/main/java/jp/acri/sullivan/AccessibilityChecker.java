/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * 
 */
package jp.acri.sullivan;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;

/**
 * @author <a href="motchie@acri.jp">Toru Motchie MOCHIDA.</a>
 * @version $Revision.$
 */
public class AccessibilityChecker
{
    private String filepath = "";
    private PDDocument document = null;         // The PDF document itself.
    private PDDocumentInformation info = null;  // Document Information of PDF.
    private PDDocumentCatalog catalog = null;

    private String language = "";

    /**
     * Check if the file is PDF or not.
     * 
     * @throws IOException Something happens when opening a file.
     * @return true if it is PDF.
     * 
     */
    public boolean isPDF() throws IOException
    {
        boolean result = false;
        
        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath(getCheckFilePath());

        if(Files.probeContentType(path).equals("application/pdf"))
        {
            result = true;
        }
    
        return result;
    }
   
    /**
     * 
     * Load PDF file to check and read metadata.
     * 
     * @throws IOException 
     * 
     */
    public void loadPDF() throws IOException
    {
        if( document != null )
        {
            document.close();
        }

        FileInputStream stream = new FileInputStream(getCheckFilePath());
        PDFParser parser = new PDFParser(stream);
        parser.parse();
        document = parser.getPDDocument();

        if( document != null )
        {
            info = document.getDocumentInformation();
            catalog = document.getDocumentCatalog();
        }
    }
    
    /**
     * Check if the PDF file is encrypted.
     * 
     * @return boolean
     * 
     */
    public boolean isEncrypted()
    {
        boolean result = false;
        
        if( document != null && document.isEncrypted() )
        {
            result = true;
        }
        
        return result;
    }

    /**
     * Check if PDF title is available.
     * 
     * @return boolean
     */
    public boolean isTitleAvailable()
    {
        boolean result = false;
        String title = null;

        if( info != null )
        {
            title = info.getTitle();
        }
        
        if( title != null && title.length() > 0 )
        {
            result = true;
        }
        
        return result;
    }

    /**
     * 
     * Exposes the title of PDF.
     * 
     * @return PDF title.
     * 
     */
    public String getTitle()
    {
        String title = null;

        if( info != null )
        {
            title = info.getTitle();
        }

        return title;
    }
    
    /**
     * 
     * Check if bookmarks are available.
     * 
     * @return check result.
     * 
     */
    public boolean isBookmarksAvailable()
    {
        boolean result = false;
        PDDocumentOutline outline = null;

        if( catalog != null )
        {
            outline = catalog.getDocumentOutline();
        }

        if( outline != null )
        {
            result = true;
        }
        
        return result;
    }
    
    /**
     * 
     * Verify that the default language is specified in PDF.
     * 
     * @return true if default language is specified..
     * 
     */
    public boolean isLanguageSpecified()
    {
        boolean result = false;

        if( catalog != null )
        {
            language = catalog.getLanguage();
        }
        
        if( language != null )
        {
            result = true;
        }
        
        return result;
    }
    
    /**
     * 
     * Exposes the default language of PDF.
     * 
     * @return default language.
     * 
     */
    public String getLanguage()
    {
        if( catalog != null )
        {
            language = catalog.getLanguage();
        }

        if( language == null )
        {
            language = "";
        }

        return language;
    }

    /**
     * 
     * Set a file path to check.

     * @param path file path of the PDF to check.
     */
    public void setCheckFilePath(String path)
    {
        filepath = path;
    }

    /**
     * 
     * Get the file path of the PDF to check.
     * 
     * @return file path of the PDF to check.
     * 
     */
    public String getCheckFilePath()
    {
        return filepath;
    }
}