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


package com.nokia.carbide.templatewizard.symbian.tests;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

import junit.framework.TestCase;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.osgi.framework.Bundle;

import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.internal.api.template.engine.*;
import com.nokia.carbide.internal.template.gen.Template.*;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

public class LiveTemplatesValidationTest extends TestCase {
	private final static String COPY_FILES_PROCESS =
		"com.nokia.carbide.cpp.project.core.processes.CopyFilesAndFormatAsCpp";
	private static Class COPY_FILES_CLASS; 

	protected void setUp() throws Exception {
		super.setUp();
		COPY_FILES_CLASS = ProjectCorePlugin.getDefault().getBundle().loadClass(COPY_FILES_PROCESS);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCopyFilesSourcePathsCorrect() throws Exception {
		List<String> newlineAndTabProblems = new ArrayList();
		List<ITemplate> templates = TemplateEngine.getInstance().getTemplates(null);
		assertTrue(TemplateEngine.isLoaded());
		assertNotNull(templates);
		for (ITemplate template : templates) {
			URL templateUrl = template.getTemplateUrl();
			URL parentPathURL = FileUtils.getParentPathURL(templateUrl);
			TemplateType templateType = ((LoadedTemplate)template.getLoadedTemplate()).getTemplateType();
			assertNotNull(templateType);
			List<ProcessType> processList = templateType.getProcess();
			for (ProcessType processType : processList) {
				String class_ = processType.getClass_();
				String bundle = processType.getBundle();
				if (isCorrectCopyFilesClass(bundle, class_)) {
					assertSourcePathsCorrectAndFileTermNLsAndTabs(newlineAndTabProblems, parentPathURL, processType);
				}
			}
		}
		assertTrue("\n" + TextUtils.formatTabbedList(newlineAndTabProblems), newlineAndTabProblems.isEmpty());
	}
	
	private boolean isCorrectCopyFilesClass(String bundleId, String class_) throws Exception {
		Bundle bundle = Platform.getBundle(bundleId);
		Class cls = bundle.loadClass(class_);
		return COPY_FILES_CLASS.isAssignableFrom(cls);
	}

	private void assertSourcePathsCorrectAndFileTermNLsAndTabs(List<String> newlineAndTabProblems, URL directoryUrl, ProcessType processType) throws Exception {
		List<ParameterType> parameter = processType.getParameter();
		for (ParameterType parameterType : parameter) {
			if (parameterType.getName().equals("file")) {
				FeatureMap attributes = parameterType.getAnyAttribute();
				for (Iterator iter = attributes.iterator(); iter.hasNext();) {
					FeatureMap.Entry entry = (FeatureMap.Entry) iter.next();
					String name = entry.getEStructuralFeature().getName();
					if (name.equals("sourcePath")) {
						String relativePath = (String) entry.getValue();
						URL url = new URL(directoryUrl, relativePath);
						InputStream inputStream = url.openStream();
						assertFileHasTermNLsAndTabs(newlineAndTabProblems, attributes, url, inputStream);
					}
				}
			}
		}
	}
	
	private boolean isNewLine(char c) {
		return c == '\n' || c == '\r';
	}

	private void assertFileHasTermNLsAndTabs(List<String> newlineAndTabProblems, FeatureMap attributes, URL url, InputStream is) throws Exception {
		if (!isApplicable(url.getFile(), attributes))
			return;
		
		String fileContents = new String(FileUtils.readInputStreamContents(is, null));
		if (!isNewLine(fileContents.charAt(fileContents.length() - 1)))
			newlineAndTabProblems.add(url.toExternalForm() + " had no terminating newline");
		
		String[] lines = fileContents.split("\\r|\\n|\\r\\n");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (line.matches(" *\\t*  +.*"))
				newlineAndTabProblems.add((url.toExternalForm() + " had leading spaces at line " + (i/2 + 1)));
		}
	}
	
	private boolean isApplicable(String fileName, FeatureMap attributes) {
		Path p = new Path(fileName);
		String extension = p.getFileExtension();
		if ("uidesign".equalsIgnoreCase(extension))
			return false;
		if ("xml".equalsIgnoreCase(extension))
			return false;
		
		return isSubstitute(attributes);
	}

	private boolean isSubstitute(FeatureMap attributes) {
		for (Iterator iter = attributes.iterator(); iter.hasNext();) {
			FeatureMap.Entry entry = (FeatureMap.Entry) iter.next();
			String name = entry.getEStructuralFeature().getName();
			if (name.equals("substitute")) {
				return Boolean.parseBoolean((String) entry.getValue());
			}
		}
		
		return true;
	}

	public void testValidateTemplateExtensions() throws Exception {
		List<ITemplate> templates = TemplateEngine.readTemplates(false);
		for (ITemplate template : templates) {
			TemplateLoader.validate(((LoadedTemplate)template.getLoadedTemplate()).getTemplateType(), template.getBundle(), template.getTemplateUrl());
		}
	}
	
	public void testAllTemplatesLocalized() throws Exception {
		List<ITemplate> templates = TemplateEngine.readTemplates(false);
		for (ITemplate template : templates) {
			assertTemplateLocalized(template);
		}
	}

    private void testString(String s, ITemplate template) throws Exception {
        assertTrue("'" + s + "' did not start with '%' in template " + template.getTemplateUrl(), s.startsWith("%"));
        s = template.getLocalizedString(s);
        assertFalse("'" + s + "' did start with '%' in template " + template.getTemplateUrl(), s.startsWith("%"));
        assertFalse("'" + s + "' did start with '!' in template " + template.getTemplateUrl(), s.startsWith("!"));
    }
    
	private void assertTemplateLocalized(ITemplate template) throws Exception {
		TemplateType templateType = ((LoadedTemplate)template.getLoadedTemplate()).getTemplateType();
		testString(templateType.getLabel(), template);
		testString(TextUtils.catenateBrokenLines(templateType.getDescription()), template);
		List<WizardPageType> wizardPages = templateType.getWizardPage();
		for (WizardPageType wpt : wizardPages) {
			testString(wpt.getLabel(), template);
			testString(TextUtils.catenateBrokenLines(wpt.getDescription()), template);
        	for (Iterator<EObject> iter = wpt.eContents().iterator(); iter.hasNext(); ) {
        		EObject obj = iter.next();
        		if (obj instanceof BaseFieldType) {
        			BaseFieldType field = (BaseFieldType) obj;
        			testString(field.getLabel(), template);
        			testString(TextUtils.catenateBrokenLines(field.getDescription()), template);
	        	}
			}
		}
	}
}
