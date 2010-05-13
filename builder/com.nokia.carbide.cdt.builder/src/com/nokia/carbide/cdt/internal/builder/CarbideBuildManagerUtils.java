package com.nokia.carbide.cdt.internal.builder;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;

public class CarbideBuildManagerUtils {
	
	/**
	 * In launch configs, convert from the source container mapping type in
	 * Carbide 2.x to its equivalent in 3.x
	 * @throws Exception - any exception will be propagated to its caller
	 */
	public static void convertSourceMappings2xTo3x() throws Exception {
		ILaunchManager launchMgr = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfiguration[] launchConfigSet = launchMgr.getLaunchConfigurations();
		for (int i = 0; i < launchConfigSet.length; i++) {
			ILaunchConfigurationWorkingCopy configuration = launchConfigSet[i].getWorkingCopy();
			String locator = configuration.getAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_MEMENTO, (String) null);
			if (locator == null)
				continue;
			
			// originally intended to parse XML in locator and make sure we're replacing the only right XML
			// element types, but decided the string to replace is uniquely used with one element type
			if (locator.indexOf("com.nokia.cdt.debug.cw.symbian.containerType.mapping") != -1)  { //$NON-NLS-1$
				// create a new XML source locator with the string changed
				String newLocator = locator.replaceAll(
						"com.nokia.cdt.debug.cw.symbian.containerType.mapping", //$NON-NLS-1$
						"com.nokia.cdt.debug.common.containerType.mapping"); //$NON-NLS-1$
				configuration.setAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_MEMENTO, newLocator);
				configuration.doSave();
			}
		}
	}

}
