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
package com.nokia.cpp.internal.api.utils.ui;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.testing.TestableObject;

import java.net.MalformedURLException;
import java.net.URL;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.utils.ui.noexport.UtilsUIPlugin;

@SuppressWarnings("restriction")
public abstract class WorkbenchUtils {

	/**
	 * Return true if running JUnit tests, false if normal interactive run
	 */
	public static boolean isJUnitRunning() {
		boolean result = false;
		TestableObject testableObject = PlatformUI.getTestableObject();
		if (testableObject != null) {
			result = testableObject.getTestHarness() != null;
		}
		return result;
	}
	
	/**
	 * Get the active workbench shell.
	 * This can return null if the shell is in the background!
	 * @return current foreground workbench window shell
	 */
	public static Shell getActiveShell() {
		if (!Platform.isRunning())
			return null;
		
		final Shell[] shell = { null };
		
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				final IWorkbench workbench;
				try {
					workbench = PlatformUI.getWorkbench();
				} catch (IllegalStateException e) {
					return;
				}
				
				final IWorkbenchWindow[] activeWorkbenchWindow = { null };
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						activeWorkbenchWindow[0] = workbench.getActiveWorkbenchWindow();
					}
				}); 
				if (activeWorkbenchWindow[0] == null)
					return;
				
				shell[0] = activeWorkbenchWindow[0].getShell();
				
			}
		});
		
		return shell[0];
	}
	
	/**
	 * Get a shell that is preferably the active workbench window,
	 * but pick some other workbench shell or even a random SWT
	 * shell if none is active.  Fail if no shells are available.
	 * @return shell, never null
	 * @throws IllegalStateException if no shells visible
	 */
	public static Shell getSafeShell() {
		Shell shell = getActiveShell();
		if (shell != null)
			return shell;
		
		try {
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (activeWorkbenchWindow != null) {
				shell = activeWorkbenchWindow.getShell();
			}
			if (shell == null) {
				for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
					shell = window.getShell();
					if (shell != null)
						return shell;
				}
			}
		} catch (IllegalStateException e) {
			// platform not running
		}

		// resort to SWT
		final Shell[] shellEntry = { null };
		
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				Shell[] shells = PlatformUI.getWorkbench().getDisplay().getShells();
				if (shells.length > 0) {
					shellEntry[0] = shells[0];
				}
			}
		});
		shell = shellEntry[0];
		
		Check.checkState(shell != null);
		return shell;
	}
	
	/**
	 * This should be called in IWorkbenchPart.createPartControl(Composite parent)
	 * to set the help context id for that IViewPart or IEditorPart. 
	 * 
	 * @param control the control on which to register the context id
	 * @param contextId the context id to use when F1 help is invoked
	 */
	public static void setHelpContextId(Control control, String contextId) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control, contextId);
	}
	
	/**
	 * Reads the General > Workspace > Save automatically before build option and, if true,
	 * saves all open editor windows
	 *
	 */
	public static void saveOpenEditorsIfRequired(){
		IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		if (store != null && store.getBoolean(IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD)){
			PlatformUI.getWorkbench().saveAllEditors(false);
		}
	}

	/**
	 * Load and provide a view part with the given id.
	 * @param viewID
	 * @return IViewPart
	 * @throws PartInitException
	 */
	public static IViewPart getView(final String viewID) throws PartInitException {		
		final IViewPart[] parts = { null } ;
		final PartInitException ex[] = { null };
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
				if (workbenchWindow == null) {
					if (workbench.getWorkbenchWindowCount() == 0)
						return;
					workbenchWindow = workbench.getWorkbenchWindows()[0];
				}
				IWorkbenchPage page = workbenchWindow.getActivePage();
				if (page == null) {
					if (workbenchWindow.getPages().length == 0)
						return;
					page = workbenchWindow.getPages()[0];
				}
				IViewPart view;
				try {
					view = page.showView(viewID);
				} catch (PartInitException e) {
					ex[0] = e;
					return;
				}
				parts[0] = view;				
			}
			
		});
		if (ex[0] != null)
			throw ex[0];
		return parts[0];
	}

	/**
	 * Open an external browser and go to the given URL.
	 * Note this function must be called in UI thread.
	 * 
	 * @param address
	 */
	static public void showWebPageInExternalBrowser(String address) {
		IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
		// use external browser since internal one which opens in an editor has no UI
		// and somehow seems to ignore proxy settings
		
		try {
			URL url = new URL(address);
			browserSupport.getExternalBrowser().openURL(url);
		} catch (MalformedURLException e) {
			UtilsUIPlugin.log(e, "Malformed URL: " + address);
		} catch (PartInitException e) {
			UtilsUIPlugin.log(e, "Cannot launch browser to navigate to the URL.");
		}
	}	
}
