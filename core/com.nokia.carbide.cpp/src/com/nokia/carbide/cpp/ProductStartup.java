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
package com.nokia.carbide.cpp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchWindow;

public class ProductStartup implements IStartup {


	public void replaceAboutAction(final IWorkbenchWindow window)
	{
		
		if (window == null || !(window instanceof WorkbenchWindow))
			return;
		MenuManager manager = ((WorkbenchWindow)window).getMenuManager();
		final IPreferenceStore store = ProductPlugin.getDefault().getPreferenceStore();

		for (IContributionItem item : manager.getItems()) {
			if (item instanceof MenuManager) {
				MenuManager mgr = (MenuManager) item;
				if (item.getId().equals("help"))
				{
					final IContributionItem contrib = mgr.remove("about");

					if (contrib instanceof ActionContributionItem) {
						final IAction about = ((ActionContributionItem) contrib)
								.getAction();
						mgr.add(new ActionContributionItem((IAction) Proxy
								.newProxyInstance(about.getClass()
										.getClassLoader(),
										new Class[] { IAction.class },
										new InvocationHandler() {

											public Object invoke(Object arg0, Method method, Object[] arg2) throws Throwable {

												if ("runWithEvent"
														.equals(method
																.getName())
														|| "run".equals(method
																.getName())) {
													new CustomAboutDialog(
															window.getShell())
															.open();
													return null;
												}
												return method.invoke(about,
														arg2);
																						}
										})));
					}
					
				}
			}
		}

	}
	
	public void initializeMenus() {

		PlatformUI.getWorkbench().addWindowListener(new IWindowListener() {

			public void windowActivated(IWorkbenchWindow window) {
			}

			public void windowClosed(IWorkbenchWindow window) {
			}

			public void windowDeactivated(IWorkbenchWindow window) {
			}

			public void windowOpened(IWorkbenchWindow window) {
				replaceAboutAction(window);
			}
		});

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				replaceAboutAction(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
			}
		});
		
	}

	public void earlyStartup() {
		
		initializeMenus();
		
	}

}
