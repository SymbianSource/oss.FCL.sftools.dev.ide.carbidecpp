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

import com.nokia.carbide.internal.api.templatewizard.ui.UIDComposite;
import com.nokia.carbide.internal.template.gen.Template.*;
import com.nokia.carbide.internal.template.gen.Template.util.TemplateResourceFactoryImpl;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.framework.Bundle;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

public class TemplateLoader {
	
	/**
	 * Load a template
	 * @param bundle the owning bundle
	 * @param url 
	 * @param doValidate
	 * @return EMF template information, never <code>null</code>
	 * @throws CoreException if template could not be loaded or was invalid
	 */
	public static TemplateType loadTemplate(Bundle bundle, URL url, boolean doValidate) throws CoreException  {
		if (bundle == null || url == null) {
			throw new CoreException(createAndLogError(Messages.getString("TemplateLoader.InvalidTemplateBundleOrURL"), null)); //$NON-NLS-1$
		}

		URI xmlURI = URI.createURI(url.toString());

		TemplateResourceFactoryImpl factory = new TemplateResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);

		try {
			r.load(null);
		} catch (IOException e) {
			throw new CoreException(createAndLogError(Messages.getString("TemplateLoader.FailedToLoadTemplate"), e)); //$NON-NLS-1$
		}
		EList contents = r.getContents();
	
		DocumentRoot root = (DocumentRoot) contents.get(0);
		TemplateType templateType = root.getTemplate();

		if (doValidate)
			validate(templateType, bundle, url);
		
		return templateType;
	}
	
	/**
	 * Validate the template data, throwing an exception on error. 
	 * @param templateType EMF data
	 * @param bundle owning bundle
	 * @param url URL to template
	 * @throws CoreException on error
	 */
	public static void validate(TemplateType templateType, Bundle bundle, URL url) throws CoreException {

		failIfNull(templateType.getVersion(), Messages.getString("TemplateLoader.TemplateVersionError"), url); //$NON-NLS-1$
		
		// no longer an error since plugin.xml provides this
		//failIfNull(templateType.getLabel(), Messages.getString("TemplateLoader.TemplateLabelError"), url); //$NON-NLS-1$
		
		// validate wizard pages
		List<WizardPageType> wizardPages = templateType.getWizardPage();
		for (WizardPageType wizardPageType : wizardPages) {
			failIfNull(wizardPageType.getId(), Messages.getString("TemplateLoader.WizardPageIdError"), url); //$NON-NLS-1$
			failIfNull(wizardPageType.getLabel(), Messages.getString("TemplateLoader.WizardPageLabelError"), url); //$NON-NLS-1$
			EList contents = wizardPageType.eContents();
			for (Iterator iter = contents.iterator(); iter.hasNext();) {
				Object object = (Object) iter.next();
				if (object instanceof BaseFieldType) {
					BaseFieldType baseFieldType = (BaseFieldType) object;
					failIfNull(baseFieldType.getId(), Messages.getString("TemplateLoader.FieldIdError"), url); //$NON-NLS-1$
					failIfNull(baseFieldType.getLabel(), Messages.getString("TemplateLoader.FieldLabelError"), url); //$NON-NLS-1$
					if (baseFieldType instanceof UidFieldType) {
						UidFieldType uidFieldType = (UidFieldType) baseFieldType;
						String min = uidFieldType.getMin();
						failIfNull(min, Messages.getString("TemplateLoader.UIDFieldMinError"), url); //$NON-NLS-1$
						Long[] minval = new Long[1];
						failIfFalse(parseLong(min, minval), Messages.getString("TemplateLoader.UIDFieldMinParseError"), url); //$NON-NLS-1$
						String max = uidFieldType.getMax();
						failIfNull(max, Messages.getString("TemplateLoader.UIDFieldMaxError"), url); //$NON-NLS-1$
						Long[] maxval = new Long[1];
						failIfFalse(parseLong(max, maxval), Messages.getString("TemplateLoader.UIDFieldMaxParseError"), url); //$NON-NLS-1$
						failIfFalse(minval[0].longValue() <= maxval[0].longValue(), 
								Messages.getString("TemplateLoader.UIDFieldMinMaxReversedError"), url); //$NON-NLS-1$
					}
				}
			}
		}
		
		// validate processes
		List<ProcessType> processes = templateType.getProcess();
		for (ProcessType processType : processes) {
			TemplateProcessEngine.loadClassFromProcess(processType, url, bundle);
		}
	}
	
	private static boolean parseLong(String valStr, Long[] longVal) {
		try {
			longVal[0] = Long.parseLong(valStr.substring(UIDComposite.HEX_PREFIX.length()), 16);
		}
		catch (NumberFormatException e) {
			return false;
		}
		catch (StringIndexOutOfBoundsException e) {
			return false;
		}
		return true;
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
