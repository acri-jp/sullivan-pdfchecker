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
 * @author motchie 
 */
package jp.acri.sullivan;

import java.io.IOException;

/**
 * @author <a href="motchie@acri.jp">Toru Motchie MOCHIDA.</a>
 * @version $Revision$
 */
public class PDFChecker
{
    /**
     */
    private PDFChecker()
    {
    }

    /**
     * @param args an options.
     */
    public static void main(String[] args)
    {
        String filepath = null;

        if (args.length == 0)
        {
            usage();
            return;
        }
        else
        {
            filepath = args[0];
        }

        if (filepath != null)
        {
            AccessibilityChecker checker = new AccessibilityChecker();
            checker.setCheckFilePath(filepath);
            
            try
            {
                if(checker.isPDF())
                {
                    checker.loadPDF();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
            outputResultConsole(checker);
        }
    }

    private static void outputResultConsole(AccessibilityChecker checker)
    {
        System.out.println("Accessibility Check Result ----------------------");
        System.out.println("Check Filename: " + checker.getCheckFilePath());
        System.out.println("Is not encrypted: " + ( checker.isEncrypted() ? "No" : "Yes" ) );
        System.out.println("Is title Available: " + ( checker.isTitleAvailable() ? "Yes" : "No" ) );
        System.out.println("Is bookmark is available: " + ( checker.isBookmarksAvailable() ? "Yes" : "No" ) );
        System.out.println("Is default languade specified: " + ( checker.isLanguageSpecified() ? "Yes" : "No" ) );
    }

    /**
     * This will print out a message telling how to use this command.
     */
    private static void usage()
    {
        System.err.println("usage: " + PDFChecker.class.getName() + " <Check PDF File Path.>");
    }
}