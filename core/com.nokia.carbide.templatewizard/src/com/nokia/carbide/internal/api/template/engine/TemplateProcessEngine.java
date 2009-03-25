/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.api.template.engine;

import com.nokia.carbide.internal.template.gen.Template.ProcessType;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.carbide.templatewizard.process.IProcess;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;

import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class that reads wizard template process extensions, maintains list of registered
 * processes, and handles loading processes.
 */
public class TemplateProcessEngine {
	private static final String EXTENSION_ID = TemplateWizardPlugin.ID + ".templateProcesses"; //$NON-NLS-1$

	private static final String ID = "id"; //$NON-NLS-1$
	private static final String BUNDLE = "bundle"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	
	private static TemplateProcessEngine instance;
	
	static class ProcessInfo {
		private String bundle;
		private String klass;
		public ProcessInfo(String bundle, String klass) {
			this.bundle = bundle;
			this.klass = klass;
		}
		public String getBundle() {
			return bundle;
		}
		public void setBundle(String bundle) {
			this.bundle = bundle;
		}
		public String getClassname() {
			return klass;
		}
		public void setClassname(String klass) {
			this.klass = klass;
		}
		
	}
	
	private Map<String, ProcessInfo> processInfos;
	
	public static TemplateProcessEngine getInstance() {
		if (instance == null) {
			instance = new TemplateProcessEngine();
		}
		return instance;
	}
	
	private TemplateProcessEngine() {
		readProcesses();
	}

	private void readProcesses() {
		processInfos = new HashMap<String, ProcessInfo>();
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(EXTENSION_ID);
		IExtension[] extensions = extensionPoint.getExtensions();
		
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			String pluginId = extension.getNamespaceIdentifier();
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				IConfigurationElement element = elements[j];

				String id = element.getAttribute(ID);
				String klass = element.getAttribute(CLASS);
				String bundle = element.getAttribute(BUNDLE);
				
				if (bundle == null) {
					bundle = pluginId;
				}
				
				ProcessInfo info = new ProcessInfo(bundle, klass);
				processInfos.put(id, info);
			}
		}
	}
	
	public static void logError(String message, Throwable t) {
		Logging.log(TemplateWizardPlugin.getDefault(), Logging.newSimpleStatus(IStatus.ERROR, t));
	}
	
	/**
	 * Load the class referenced by a process.  This is either the registered processType
	 * extension (specified by 'name') or the old-style direct class reference (specified
	 * by 'class' and 'bundle').
	 * @param processType
	 * @param url
	 * @param bundle
	 * @return Class instance, never <code>null</code>
	 * @throws CoreException
	 */
	public static Class loadClassFromProcess(ProcessType processType, URL url, Bundle bundle) throws CoreException {
		String className = null;
		String bundleName = null;
		
		String processTypeName = processType.getName();
		if (processTypeName != null) {
			ProcessInfo info = getInstance().getProcessInfo(processTypeName);
			failIfNull(info, MessageFormat.format(
					Messages.getString("TemplateProcessEngine.UnregisteredProcessError"), //$NON-NLS-1$
					processTypeName), url);
			className = info.getClassname();
			bundleName = info.getBundle();
		} else {
			className = processType.getClass_();
			bundleName = processType.getBundle();
		}
		
		
		failIfNull(className, Messages.getString("TemplateProcessEngine.NoNameOrClassAttributeError"), url); //$NON-NLS-1$
		if (bundleName != null)
			bundle = Platform.getBundle(bundleName);
		
		if (bundle == null) {
			String classNotFoundError = 
				MessageFormat.format(Messages.getString("TemplateProcessEngine.CannotFindBundleError"), bundleName); //$NON-NLS-1$
			throw new CoreException(createAndLogError(classNotFoundError, null));
		}
		Class cls = null;
		try {
			cls = bundle.loadClass(className);
		}
		catch (NoClassDefFoundError e) {
			String classNotFoundError = 
				MessageFormat.format(Messages.getString("TemplateProcessEngine.NoClassInBundleError"),  //$NON-NLS-1$
									new Object[] { className, bundle.getSymbolicName() });
			failIfFalse(false, classNotFoundError, url, e);
		}
		catch (ClassNotFoundException e) {
			String classNotFoundError = 
				MessageFormat.format(Messages.getString("TemplateProcessEngine.NoClassInBundleError"),  //$NON-NLS-1$
									new Object[] { className, bundle.getSymbolicName() });
			failIfFalse(false, classNotFoundError, url, e);
		}
		String noIProcessError = 
			MessageFormat.format(Messages.getString("TemplateProcessEngine.NotIProcessError"),  //$NON-NLS-1$
										new Object[] { cls });
		failIfFalse(IProcess.class.isAssignableFrom(cls), noIProcessError, url);
		
		return cls;
	}

	/**
	 * Look up the process info for a given unique identifier
	 * @param processTypeName
	 * @return
	 */
	protected ProcessInfo getProcessInfo(String processTypeName) {
		return processInfos.get(processTypeName);
	}

	private static void failIfFalse(boolean condition, String message, URL url) throws CoreException {
		failIfFalse(condition, message, url, null);
	}
	
	private static void failIfFalse(boolean condition, String message, URL url, Throwable t) throws CoreException {
		if (condition)
			return;
		
		String error = 
			MessageFormat.format("{0} @ {1}", new Object[] { message, url.toExternalForm() }); //$NON-NLS-1$
		IStatus status = createAndLogError(error, t);
		throw new CoreException(status);
	}

	private static void failIfNull(Object object, String message, URL url) throws CoreException {
		failIfFalse(object != null, message, url);
	}
	
	private static IStatus createAndLogError(String message, Throwable t) {
		IStatus error = Logging.newStatus(TemplateWizardPlugin.getDefault(), IStatus.ERROR, message, t);
		Logging.log(TemplateWizardPlugin.getDefault(), error);
		return error;
	}
}
