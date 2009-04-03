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

package com.nokia.carbide.templatewizard.tests;

import com.nokia.carbide.internal.api.template.engine.TemplateLoader;
import com.nokia.carbide.internal.template.gen.Template.*;
import com.nokia.carbide.internal.templatewizard.tests.TestsPlugin;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Bundle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

public class TemplateLoaderTest extends TestCase {
	private String FILEPATH = "Data/TestTemplate/template.xml";
	private String FILEPATH_BADTEMPLATE = "Data/TestTemplate/bad_template.xml";
	private File templateFile;
	private File bad_templateFile;
	private ILocalizedStrings strings;
	
	protected void setUp() throws Exception {
		super.setUp();
		templateFile = pluginRelativeFile(FILEPATH);
		assertNotNull(templateFile);
		assertTrue(templateFile.exists());
		bad_templateFile = pluginRelativeFile(FILEPATH_BADTEMPLATE);
		assertNotNull(bad_templateFile);
		assertTrue(bad_templateFile.exists());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testLoadTemplate() throws Exception {
		// should not throw
        TemplateType template = TemplateLoader.loadTemplate(TestsPlugin.getDefault().getBundle(), templateFile.toURL(), true);
        List<WizardPageType> pages = template.getWizardPage();
        assertTrue(pages != null && pages.size() == 2);
	}
	
	public void testBadTemplate() throws Exception {
		TemplateType template = null;
		try {
			template = TemplateLoader.loadTemplate(TestsPlugin.getDefault().getBundle(), bad_templateFile.toURL(), true);
			fail("Should have failed to load bad template");
		}
		catch (Exception e) {
		}
		assertNull(template);
	}

    private File pluginRelativeFile(String file) throws IOException {
        Bundle bundle = TestsPlugin.getDefault().getBundle();
		URL url = FileLocator.find(bundle, new Path("."), null);
        if (url == null)
            fail("could not make URL from bundle " + bundle + " and path " + file);
        url = FileLocator.resolve(url);
        TestCase.assertEquals("file", url.getProtocol());
        return new File(url.getPath(), file);
    }
    
    private ILocalizedStrings createLocalizedStringSupport() {
		String fileName = templateFile.getName();
		int dotIndex = fileName.lastIndexOf('.');
		String baseName = dotIndex < 0 ? fileName : fileName.substring(0, dotIndex);
		return new LocalizedStrings(templateFile.getParentFile(), baseName);
    }
    
    private void testString(String s) throws Exception {
        assertTrue(s.startsWith("%"));
        s = strings.checkPercentKey(s);
        assertFalse(s.startsWith("%"));
        assertFalse(s.startsWith("!"));
    }
    
    public void testLocalizedLabelsAndDescs() throws Exception {
        TemplateType template = TemplateLoader.loadTemplate(TestsPlugin.getDefault().getBundle(), templateFile.toURL(), true);
        assertNotNull(template);
        strings = createLocalizedStringSupport();
        assertNotNull(strings);
       
        testString(template.getLabel());
        testString(template.getDescription());
        
        List<WizardPageType> pages = template.getWizardPage();
        for (WizardPageType page : pages) {
        	testString(page.getLabel());
        	testString(TextUtils.catenateBrokenLines(page.getDescription()));
        	for (Iterator<EObject> iter = page.eContents().iterator(); iter.hasNext(); ) {
        		EObject obj = iter.next();
        		if (obj instanceof BaseFieldType) {
        			BaseFieldType field = (BaseFieldType) obj;
        			testString(field.getLabel());
        			testString(TextUtils.catenateBrokenLines(field.getDescription()));
	        	}
			}
		}
    }
}
