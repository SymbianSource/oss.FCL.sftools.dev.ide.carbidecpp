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

package com.nokia.carbide.templatewizard.tests;

import com.nokia.carbide.internal.api.template.engine.*;
import com.nokia.carbide.internal.template.gen.Template.ParameterType;
import com.nokia.carbide.internal.template.gen.Template.ProcessType;
import com.nokia.carbide.internal.template.gen.Template.TemplateType;
import com.nokia.carbide.internal.templatewizard.tests.TestsPlugin;
import com.nokia.carbide.template.engine.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.osgi.framework.Bundle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.*;

import junit.framework.TestCase;

/**
 * Test that the CopyFile* processes refer to extant files
 */
public class TemplateCopyFilesTest extends TestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	private ILoadedTemplate loadTemplate(File file, boolean validate) throws CoreException, IOException {
		assertNotNull(file);
		assertTrue(file.exists());
        Bundle bundle = TestsPlugin.getDefault().getBundle();
		URL templateUrl = file.toURL();
		TemplateType templateType = TemplateLoader.loadTemplate(bundle, templateUrl, validate);
        assertNotNull(templateType);
		ITemplate template = new Template(templateUrl, bundle, null, null, null, null, null, null, null);
		return template.getLoadedTemplate();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@SuppressWarnings("unchecked")
	public void testTemplates() throws Exception {
		String errors = "";
		for (ITemplate templateInfo : TemplateEngine.getInstance().getFilteredTemplates(null)) {
			if (templateInfo instanceof Template) {
				ILoadedTemplate template;
				try {
					template = templateInfo.getLoadedTemplate();
				} catch (CoreException e) {
					// too bad!
					e.printStackTrace();
					continue;
				}
				if (template instanceof LoadedTemplate) {
					LoadedTemplate loadedTemplate = (LoadedTemplate) template;
					String error = validateProcesses(templateInfo.getTemplateId(), 
							templateInfo.getBundle(),
							templateInfo.getTemplateUrl(),
							loadedTemplate.getTemplateType().getProcess());
					if (error != null)
						errors += error;
				}
			}
		}
		if (errors.length() > 0)
			fail(errors);
	}
	
	@SuppressWarnings("unchecked")
	private String validateProcesses(String templateId, Bundle bundle, URL templateBase, EList<ProcessType> processes) {
		if (processes == null)
			return null;
		List<String> invalidFiles = new ArrayList<String>();
		for (ProcessType process : processes) {
			String nameOrClass = process.getName();
			if (nameOrClass == null)
				nameOrClass = process.getClass_();
			if (nameOrClass == null)
				continue;
			if (!nameOrClass.contains("CopyFile"))
				continue;
			EList<ParameterType> parameters = process.getParameter();
			for (ParameterType parameter : parameters) {
				FeatureMap attributes = parameter.getAnyAttribute();
				for (Iterator iter = attributes.iterator(); iter.hasNext();) {
					FeatureMap.Entry entry = (FeatureMap.Entry) iter.next();
					String attributeName = entry.getEStructuralFeature().getName();
					if (attributeName.equals("sourcePath")) {
						String rawString = (String) entry.getValue();
						if (!rawString.contains("$(")) {
							String fileName = templateBase.getFile();
							IPath path = new Path(fileName).removeLastSegments(1);
							IPath sourcePath =  path.append(rawString);
							URL url = FileLocator.find(bundle, sourcePath, null);
							if (url == null) {
								invalidFiles.add(rawString);
								continue;
							}
							try {
								InputStream stream = url.openStream();
								stream.close();
							} catch (IOException e) {
								invalidFiles.add(rawString);
							}
						}
					}
				}
			}
		}
		if (!invalidFiles.isEmpty()) {
			return (templateId + " references nonexistent files (check capitalization):\n"
					+ TextUtils.catenateStrings(invalidFiles.toArray(), "\n")
					+ "\n");
		}
		return null;
	}
}
