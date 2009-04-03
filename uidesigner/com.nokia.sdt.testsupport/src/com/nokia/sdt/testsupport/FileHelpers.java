/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/
package com.nokia.sdt.testsupport;


import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;


/**
 * These routines are useful for unit tests.
 * Provides methods to locate files relative to the project,
 * which will work either standalone or from Eclipse.  Please
 * use these if possible when the UI editor is not required;
 * this will help you write tests that you can run in a finite
 * amount of time. ;)
 *
 * NOTE: use TestHelpers.setPlugin(Plugin) before using any
 * of this.
 * 
 * 
 *
 */
public class FileHelpers {

    /**
     * Load a file relative to this plugin in the running workbench.
     * @param file
     * @return File
     * @throws IOException
     */
    public static File pluginRelativeFile(String file) throws IOException {
        if (TestHelpers.plugin == null)
            return null;
        Bundle bundle = TestHelpers.plugin.getBundle();
        if (bundle == null)
            return null;
        /*
        URL url = FileLocator.find(bundle, new Path(file), null);
        if (url == null)
            TestCase.fail("could not make URL from bundle " + bundle + " and path " + file);
        url = FileLocator.resolve(url);
        TestCase.assertEquals("file", url.getProtocol());
        return new File(url.getPath());
        */
        // This approach allows you to create possibly non-existing files.
        // The approach above will fail early.
        URL url = FileLocator.find(bundle, new Path("."), null);
        if (url == null)
            TestCase.fail("could not make URL for bundle " + bundle);
        url = FileLocator.resolve(url);
        TestCase.assertEquals("file", url.getProtocol());
        return new File(url.getPath(), file);
        
    }

    /**
     *  Find a file relative to the project.  Works if running
     *  in the workbench or standalone.
     *  @param file the relative path (from the project) to the file
     *  @return File
     */
    public static File projectRelativeFile(String file) throws Exception {
        File f;
        if (!Platform.isRunning()) {
            // get file relative to CWD (i.e. this project)
            f = new File(file);
            f = f.getCanonicalFile();
        } else {
            if (TestHelpers.plugin == null)
                TestCase.fail("Please invoke #setPlugin(Plugin) so I can find plugin-relative files\n"
                        +"(if you're already doing that, ensure 'Eclipse-AutoStart: true' is set in the plugin)");
            // get file relative to running plugin (still this project)
            f = pluginRelativeFile(file);
        }
        if (f == null)
            TestCase.fail("Cannot find file " + file + " relative to project");
        return f;
    }

    /**
     * Read the contents of the file into a string, optionally cleaning
     * it up for ease of comparison
     * @param file full path or project-relative file
     * @param whitespaceCleaning if true, remove leading/trailing space from the
     * file text 
     * @param newlineCleaning if true, convert newlines from file text to canonical format ('\n')
     */
    public static String readFile(File file, boolean whitespaceCleaning, boolean newlineCleaning) throws IOException {
        StringBuffer sb = new StringBuffer();
        try {
            Reader reader = new InputStreamReader(
                    new FileInputStream(file), "UTF-8");
            char[] buf = new char[1024];
            int len;
            while ((len = reader.read(buf)) > 0) {
                sb.append(buf, 0, len);
            }
            reader.close();
        } catch (IOException e) {
            TestCase.fail("Couldn't find " + file);
        }
        
        if (whitespaceCleaning) {
            int idx = 0;
            while (idx < sb.length() && Character.isWhitespace(sb.charAt(idx)))
                idx++;
            sb.delete(0, idx);
            
            idx = sb.length();
            while (idx > 0 && Character.isWhitespace(sb.charAt(idx - 1)))
                idx--;
            
            sb.delete(idx, sb.length());
        }
        
        String sbText = sb.toString();
        
        if (newlineCleaning) {
        	sbText = cleanNewlines(sbText);
        }
    
        return sbText;
    }

    public static String cleanNewlines(String text) {
        Pattern p = Pattern.compile("\r\n|\r(?!\n)", Pattern.MULTILINE);
        Matcher m = p.matcher(text);
        return m.replaceAll("\n");
    }
    /**
     * Read the contents of the file into a string, optionally cleaning
     * it up for ease of comparison
     * @param file full path or project-relative file
     * @param whitespaceCleaning if true, remove leading/trailing space from the
     * file text 
     * @param newlineCleaning if true, convert newlines from file text to canonical format ('\n')
     */
    public static String readFile(String file, boolean whitespaceCleaning, boolean newlineCleaning) throws Exception {
        File f;
        f = new File(file);
        if (!f.exists())
            f = projectRelativeFile(file);
        
        return readFile(f, whitespaceCleaning, newlineCleaning);
    }

    /**
     * Compare the contents of the file to the text string, optionally
     * cleaning them up to make comparisons easier
     * @param file project-relative file
     * @param txt
     * @param whitespaceCleaning if true, remove <b>leading and trailing</b> 
     * space from the file text and the canonical text
     * @param newlineCleaning if true, convert newlines from file text to canonical format ('\n')
     */
    public static void compareFiles(String file, String txt, boolean whitespaceCleaning, boolean newlineCleaning) throws Exception {
        String fileText = FileHelpers.readFile(file, whitespaceCleaning, newlineCleaning);
    
        if (whitespaceCleaning) {
            int idx = 0;
            while (idx < txt.length() && Character.isWhitespace(txt.charAt(idx)))
                idx++;
            txt = txt.substring(idx);
            
            idx = txt.length();
            while (idx > 0 && Character.isWhitespace(txt.charAt(idx - 1)))
                idx--;
            
            txt = txt.substring(0, idx);
        }
        
        TestCase.assertEquals(fileText, txt);
    }

    /**
     * Compare generated source, ignoring all formatting.
     * This will remove all whitespace from strings
     * (except for newlines) and compare them on that basis.
     * @param expected expected text
     * @param actual actual text
     */
    public static void sourceEquals(String expected, String actual) {
        sourceEquals(expected, actual, false);
    }

    /**
     * Compare generated source, ignoring all formatting.
     * This will remove all whitespace from strings
     * (except for newlines) and compare them on that basis.
     * @param expected expected text
     * @param actual actual text
     */
    public static void sourceEquals(String expected, String actual, boolean ignoreNewlines) {
        Pattern patt;
        
        String mungedExpected, mungedActual;
        
        // if not ignoring newlines, map embedded whitespace to single spaces;
        // else, map all whitespace and newlines to nothing
        patt = Pattern.compile(ignoreNewlines ? "(\\s*\n*)+" : "([\\t\\v ]+)", Pattern.MULTILINE);
        Matcher matcher = patt.matcher(expected);
        mungedExpected = matcher.replaceAll(ignoreNewlines ? "" : " ");
        matcher = patt.matcher(actual);
        mungedActual = matcher.replaceAll(ignoreNewlines ? "" : " ");

        if (!ignoreNewlines) {
            // now replace multiple newlines and surrounding whitespace with single or none
            patt = Pattern.compile("(\\s*\n+\\s*)+", Pattern.MULTILINE);
            matcher = patt.matcher(mungedExpected);
            mungedExpected = matcher.replaceAll("\n");
            matcher = patt.matcher(mungedActual);
            mungedActual = matcher.replaceAll("\n");
        }
        
        // remove leading & trailing whitespace
        mungedExpected = mungedExpected.trim();
        mungedActual = mungedActual.trim();

        // compare the real chars
        if (!mungedExpected.equals(mungedActual)) {
            // if they differ, the formatted version must differ too,
            // and we use this so we can compare the results
            TestCase.assertEquals(expected, actual);
        }
    }

    /**
     * Compare the contents of the file to the text string.
     * If updateFile is false, the strings must match via assertEquals.
     * Otherwise, the strings must only match via sourceEquals,
     * and if successful, the reference file is updated.
     * 
     * @param file project-relative file
     * @param txt the text to compare with
     * @param updateFile false: do exact match, true: do loose match, and update text if valid
     * @param createFile true: create file if missing, else fail 
     */
    public static void compareAndUpdateSourceFile(String file, String txt, boolean updateFile, boolean createFile) throws Exception {
        String fileText = null;
        
        // get comparison file
        if (!createFile) {
            try {
            	// convert newlines
                fileText = FileHelpers.readFile(file, false, true);
            } catch (IOException e) {
                TestCase.fail("reference file " + file + " not found");
            }

        	// convert newlines
            txt = cleanNewlines(txt);
            
            if (!updateFile) {
                // test match with existing file
                TestCase.assertEquals(fileText, txt);
                return;
            }
            else {
                // check contents and don't rewrite if formatting
                // matches
                if (fileText.equals(txt))
                    return;
                    
                // compare token-wise to ensure the formatting is
                // all that changed
                sourceEquals(fileText, txt, updateFile);
            }
        }
        
        if (updateFile || createFile) {
            // the comparison succeeded or we want to write a new file
            try {
                System.out.println("rewriting "+file + "\n" + txt);
                File f = projectRelativeFile(file);
                Writer writer = new OutputStreamWriter(
                        new FileOutputStream(f), "UTF-8");
                char[] buf = txt.toCharArray();
                writer.write(buf);
                writer.close();                
            } catch (IOException e) {
                TestCase.fail("failed to rewrite " + file);
            }
        }
    }

    /**
     * Compare the contents of the file to the text string.
     * If updateFile is false, the strings must match via assertEquals.
     * Otherwise, the strings must only match via sourceEquals,
     * and if successful, the reference file is updated.
     * 
     * @param ref reference file
     * @param cur the file to compare with
     * @param updateFile false: do exact match, true: do loose match, and update text if valid
     * @param createFile true: create file if missing, else fail 
     */
    public static void compareAndUpdateSourceFile(File ref, File cur, boolean updateFile, boolean createFile) throws Exception {
    	// convert newlines
        String curText = FileHelpers.readFile(cur, false, true);

        String refText = null;
        if (!createFile) {
            try {
            	// convert newlines
                refText = FileHelpers.readFile(ref, false, true);
            } catch (IOException e) {
            }

            if (!updateFile) {
                // test exact match with existing file
                TestCase.assertEquals(refText, curText);
                return;
            }
            else {
                // check contents and don't rewrite if formatting
                // matches
                if (refText.equals(curText))
                    return;
                    
                // compare token-wise to ensure the formatting is
                // all that changed
                sourceEquals(refText, curText, updateFile);
            }
        }
        
        if (updateFile || createFile) {
            // the comparison succeeded or we want to write a new file
            try {
                System.out.println("rewriting "+ref + "\n" + curText);
                Writer writer = new OutputStreamWriter(
                        new FileOutputStream(ref), "UTF-8");
                char[] buf = curText.toCharArray();
                writer.write(buf);
                writer.close();                
            } catch (IOException e) {
                TestCase.fail("failed to rewrite " + ref);
            }
        }
    }

    /**
     * Compare two trees of files recursively.<p>
     * Any file in refDir must exist in projDir.<p>
     * Any file in projDir, except for dot-files, must exist in refDir.<p>
     * And the contents must match, excepting leading and trailing whitespace. 
     * @param refDir
     * @param projDir
     */
    public static void compareTrees(File refDir, File projDir) throws Exception {
        if (!refDir.exists() || !refDir.isDirectory())
            TestCase.fail("no such reference directory: " + refDir);
        if (!projDir.exists() || !projDir.isDirectory())
            TestCase.fail("no such project directory: " + projDir);
        
        File[] refs = refDir.listFiles();
        for (int i = 0; i < refs.length; i++) {
            File projRef = new File(projDir, refs[i].getName());
            if (refs[i].isDirectory())
                compareTrees(refs[i], projRef);
            else {
                if (!projRef.exists())
                    TestCase.fail("missing file in project: " + projRef);
                else
                    compareFiles(refs[i], projRef, true, true);
            }
        }
        
        File[] projs = projDir.listFiles();
        for (int i = 0; i < projs.length; i++) {
            if (!projs[i].getName().startsWith(".")) {
                File refRef = new File(refDir, projs[i].getName());
                if (!refRef.exists()) {
                    TestCase.fail("unexpected additional file in project: " + projs[i]);
                }
            }
        }
    }

    public interface IFileTreeComparisonFilter {

		/**
		 * Tell whether the given reference directory should be
		 * used in the comparison or created on update. 
		 * @param refDir reference directory
		 * @param cmpDir comparison file
		 * @return
		 */
		boolean includeReferenceDirectory(File refDir, File cmpDir);

		/**
		 * Tell whether the given reference file should be
		 * used in the comparison or created on update.
		 * @param refFile reference
		 * @param cmpFile comparison file
		 * @return
		 */
		boolean includeReferenceFile(File refFile, File cmpFile);
    	
    }
    
    /**
     * Compare two trees of source files recursively.<p>
     * Any file in refDir must exist in projDir.<p>
     * Any file in projDir, except for dot-files, must exist in refDir.<p>
     * And the contents must match, excepting leading and trailing whitespace. 
     * @param refDir
     * @param projDir
     * @param updateFiles false: do exact match, true: do loose match, and update text if valid
     * @param createFiles true: create file if missing, else fail 
     * @param filter judge which files should be compared
     */
    public static void compareSourceTrees(File refDir, File projDir, boolean updateFiles, boolean createFiles, 
    		IFileTreeComparisonFilter filter) throws Exception {
    	if (!filter.includeReferenceDirectory(refDir, projDir))
    		return;
        if (!refDir.exists() || !refDir.isDirectory()) {
            if (createFiles) {
                if (!refDir.mkdirs())
                    TestCase.fail("could not create directory: " + refDir);
            } else {
                TestCase.fail("no such reference directory: " + refDir);
            }
        }
        if (!projDir.exists() || !projDir.isDirectory()) {
            if (createFiles) {
                if (!projDir.mkdirs())
                    TestCase.fail("could not create directory: " + projDir);
            } else {
                TestCase.fail("no such project directory: " + projDir);
            }
        }
        
        File[] refs = refDir.listFiles();
        for (int i = 0; i < refs.length; i++) {
            File projRef = new File(projDir, refs[i].getName());
            if (refs[i].isDirectory())
                compareSourceTrees(refs[i], projRef, updateFiles, createFiles, filter);
            else {
            	if (!filter.includeReferenceFile(refs[i], projRef)) 
            		continue;
                if (!projRef.exists())
                    TestCase.fail("missing file in project: " + projRef);
                else
                    compareAndUpdateSourceFile(refs[i], projRef, updateFiles, createFiles);
            }
        }
        
        File[] projs = projDir.listFiles();
        for (int i = 0; i < projs.length; i++) {
        	File refRef = new File(refDir, projs[i].getName());
        	if (filter.includeReferenceFile(refRef, projs[i])) {
            	
                if (!refRef.exists()) {
                    if (createFiles) {
                        if (projs[i].isDirectory())
                            FileUtils.copyTreeNoParent(projs[i], refRef, null);
                        else
                            FileUtils.copyFile(projs[i], refRef);
                    } else {
                    	if (!(projs[i].isDirectory() && !filter.includeReferenceDirectory(projs[i], refRef)))
                    		TestCase.fail("unexpected additional file in project: " + projs[i]);
                    }
                }
            }
        }
            
    }

    public static class BasicTreeComparisonFilter implements IFileTreeComparisonFilter {

		protected Pattern pattern;
		private boolean match;

		/**
		 * Create basic comparison filter
		 * @param ignoreDesigns true: ignore design files and dotfiles,
		 * false: only ignore dotfiles
		 */
		public BasicTreeComparisonFilter(boolean ignoreDesigns) {
			this(ignoreDesigns ? "\\..*|.*\\.(uidesign|nxd)" : //$NON-NLS-1$ 
				 "\\..*", false);			
		}
		/**
		 * Create a filter that handles/ignores files matching the regex
		 * 
		 * @param regex regular expression for files, or null for all
		 * @param match true: accept files matching the given pattern, false: ignore them
		 */
		public BasicTreeComparisonFilter(String regex, boolean match) {
			this.pattern = regex == null ? null : Pattern.compile(regex);
			this.match = match;
		}

		/**
		 * Create a filter that ignores files matching the regex
		 * 
		 * @param regex regular expression for ignored files, or null to ignore none
		 */
		public BasicTreeComparisonFilter(String regex, int flags) {
			Check.checkArg(regex);
			this.pattern = Pattern.compile(regex, flags);
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.testsupport.FileHelpers.IFileTreeComparisonFilter#includeReferenceDirectory(java.io.File)
		 */
		public boolean includeReferenceDirectory(File refDir, File cmpDir) {
			return !refDir.getName().equals("CVS"); //$NON-NLS-1$
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.testsupport.FileHelpers.IFileTreeComparisonFilter#includeReferenceFile(java.io.File)
		 */
		public boolean includeReferenceFile(File file, File cmpFile) {
			if (pattern == null)
				return match;
			boolean include = pattern.matcher(file.getName()).matches() == match;
			return include;
		}
    }
    
    /**
     * Compare two trees of source files recursively.<p>
     * Any file in refDir must exist in projDir.<p>
     * Any file in projDir, except for dot-files, must exist in refDir.<p>
     * And the contents must match, excepting leading and trailing whitespace. 
     * @param refDir
     * @param projDir
     * @param updateFiles false: do exact match, true: do loose match, and update text if valid
     * @param createFiles true: create file if missing, else fail 
     * @param ignoreDesigns true: ignore uidesign/nxd files and dotfiles, false: ignore dotfiles
     */
    public static void compareSourceTrees(File refDir, File projDir, boolean updateFiles, boolean createFiles, boolean ignoreDesigns) throws Exception {
    	compareSourceTrees(refDir, projDir, updateFiles, createFiles, 
    			 new BasicTreeComparisonFilter(ignoreDesigns)); //$NON-NLS-1$
    }

    /**
     * Compare the contents of two files, optionally
     * cleaning them up to make comparisons easier
     * @param refFile reference file
     * @param otherFile file to compare with
     * @param whitespaceCleaning if true, remove <b>leading and trailing</b> 
     * space from the file text and the canonical text
     * @param newlineCleaning if true, convert newlines from file text to canonical format ('\n')
     */
    public static void compareFiles(File refFile, File otherFile, boolean whitespaceCleaning, boolean newlineCleaning) throws Exception {
        
        String refText = FileHelpers.readFile(refFile, whitespaceCleaning, newlineCleaning);
        String otherText = FileHelpers.readFile(otherFile, whitespaceCleaning, newlineCleaning);
        
        TestCase.assertEquals(refText, otherText);
    }

  public static void compareFiles(String message, File refFile, File otherFile, boolean whitespaceCleaning, boolean newlineCleaning) throws Exception {
        
        String refText = FileHelpers.readFile(refFile, whitespaceCleaning, newlineCleaning);
        String otherText = FileHelpers.readFile(otherFile, whitespaceCleaning, newlineCleaning);
        
        TestCase.assertEquals(message, refText, otherText);
    }

}
