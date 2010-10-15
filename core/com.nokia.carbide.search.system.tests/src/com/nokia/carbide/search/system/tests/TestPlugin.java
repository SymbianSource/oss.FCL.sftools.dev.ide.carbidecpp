package com.nokia.carbide.search.system.tests;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class TestPlugin extends Plugin {

	private static BundleContext bundleContext;
	private static TestPlugin plugin;

	public static TestPlugin getDefault() {
		return plugin;
	}

	static BundleContext getBundleContext() {
		return bundleContext;
	}

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.search.system.tests";

	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		TestPlugin.bundleContext = bundleContext;
		plugin = this;
		URL pluginURL = FileLocator.find(bundleContext.getBundle(), new Path(""), null); //$NON-NLS-1$
		pluginURL = FileLocator.resolve(pluginURL);
		String pluginFilePath = pluginURL.getFile();
		pluginPath = new Path(pluginFilePath);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		plugin = null;
		super.stop(bundleContext);
	}

	public static String projectRelativePath(String file) throws Exception {
		File f;
		if (!Platform.isRunning()) {
			// get file relative to CWD (i.e. this project)
			f = new File(file);
			f = f.getCanonicalFile();
		} else {
			// get file relative to running plugin (still this project)
			f = new File(getDefault().getPluginFilePath(file).toOSString());
		}

		return f.getAbsolutePath();
	}
	private Path pluginPath;

	/**
	 * Returns a path relative to this plugin.
	 * 
	 * @param inPluginPath
	 * @return
	 */
	public IPath getPluginFilePath(String inPluginPath) {
		return pluginPath.append(inPluginPath);
	}
	
	public static String getPluginPath(String inPluginPath) {
		return getDefault().getPluginFilePath(inPluginPath).toOSString();
	}
}
