package com.nokia.cdt.debug.common.internal.source.lookup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;


public class SourceMappingUtils {

	/** Name kept for legacy launch config settings purposes */
	private static final String EPOCROOT_SETTING = "com.nokia.cdt.debug.cw.symbian.Epoc_Root" ; //$NON-NLS-1$

	/**
	 * Add the source mapping for the workign copy of the launch config. Clients should ensure to call doSave() after this call.
	 * @param configuration
	 * @throws CoreException
	 */
	public static void addSourceMappingToLaunch(ILaunchConfigurationWorkingCopy configuration) throws CoreException {
		String memento = null;
		String type = null;

		memento = configuration.getAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_MEMENTO, (String) null);
		type = configuration.getAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_ID, (String) null);
		if (type == null) {
			type = configuration.getType().getSourceLocatorId();
		}
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ISourceLocator locator = launchManager.newSourceLocator(type);
		if (locator instanceof AbstractSourceLookupDirector) {
			AbstractSourceLookupDirector director = (AbstractSourceLookupDirector) locator;
			if (memento == null) {
				director.initializeDefaults(configuration);
			} else {
				director.initializeFromMemento(memento, configuration);
			}

			addSourceMappingToDirector(director, configuration);
			
			configuration.setAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_MEMENTO, director.getMemento());
			configuration.setAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_ID, director.getId());
		}
	}
	
	private static void addSourceMappingToDirector(AbstractSourceLookupDirector director, ILaunchConfiguration configuration) throws CoreException {

		ArrayList containerList = new ArrayList(Arrays.asList(director.getSourceContainers()));

		boolean hasSymbianContainer = false;

		SymbianSourceContainer symbianContainer = null;
		
		for (Iterator iter = containerList.iterator(); iter.hasNext() && !hasSymbianContainer;) {
			ISourceContainer container = (ISourceContainer) iter.next();
			if (container instanceof SymbianSourceContainer)
			{
				hasSymbianContainer = true;
			}
		}

		if (!hasSymbianContainer) {
			
			String epocRootPath = configuration.getAttribute( EPOCROOT_SETTING, (String)null );
			if (epocRootPath == null)
			{
				String exeName = configuration.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, "");
				IPath executable = new Path(exeName);
				String[] segs = executable.segments();
				for (int i = 0; i < segs.length; i++) {
					if (segs[i].equalsIgnoreCase("epoc32"))
						epocRootPath = executable.removeLastSegments(segs.length - i).toOSString();				
				}
			}
			if (epocRootPath != null)
			{
				symbianContainer = new SymbianSourceContainer(new Path(epocRootPath));
				symbianContainer.init(director);
				containerList.add(symbianContainer);
			}
		}
		
		director.setSourceContainers((ISourceContainer[]) containerList.toArray(new ISourceContainer[containerList.size()]));
	}
	
	
}
