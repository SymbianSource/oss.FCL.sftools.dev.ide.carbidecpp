package com.nokia.cdt.debug.cw.symbian.tests;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TestsPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.cdt.debug.cw.symbian.tests";

	// The shared instance
	private static TestsPlugin plugin;
	
	/**
	 * The constructor
	 */
	public TestsPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TestsPlugin getDefault() {
		return plugin;
	}

	/************* Utility methods **********************/
	
	public static String[] getFileNamesInFolder(final String folderName, final String fileExtention) {
		File folder = new File(folderName);
		
		String[] files = folder.list(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith(fileExtention);
			}
			
		});
	
		return files;
	}
	
	public static String[] getFileFullNamesInFolder(final String folderName, final String fileExtention) {
		String[] files = getFileNamesInFolder(folderName, fileExtention);

		for (int i=0; i < files.length; i++) {
			files[i] = folderName + "\\" + files[i];
		}
		
		return files;
	}
	
	/**
	 * Get location of this plugin.
	 * 
	 * @return
	 * @throws IOException
	 */
    public static String getPluginPath() throws IOException {
        if (!Platform.isRunning()) {
            // get file relative to CWD (i.e. this project)
            File f = new File(".");
            f = f.getCanonicalFile();
            return f.getAbsolutePath();
        } else {

        	if (getDefault() == null)
				return null;
			Bundle bundle = getDefault().getBundle();
			if (bundle == null)
				return null;
			URL url = FileLocator.find(bundle, new Path("."), null);
			if (url == null)
				TestCase.fail("could not find URL for bundle: " + bundle);
			url = FileLocator.resolve(url);
			TestCase.assertEquals("file", url.getProtocol());

			return url.getPath();
        }
    }

	 /**
		 * Load a file relative to this plugin in the running workbench
		 * 
		 * @param file
		 * @return File
		 * @throws IOException
		 */
    public static File pluginRelativeFile(String fileName) throws IOException {
        String bundlePath = getPluginPath();

        if (bundlePath == null)
        	return null;
        
        return new File(bundlePath, fileName);
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
            // get file relative to running plugin (still this project)
            f = pluginRelativeFile(file);
        }
        if (f == null)
            TestCase.fail("Cannot find file " + file + " relative to project");
        return f;
    }
}
