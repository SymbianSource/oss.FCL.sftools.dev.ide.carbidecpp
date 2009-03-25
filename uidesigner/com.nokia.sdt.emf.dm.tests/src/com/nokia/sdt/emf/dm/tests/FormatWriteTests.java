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

package com.nokia.sdt.emf.dm.tests;

import com.nokia.sdt.emf.dm.*;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

import org.custommonkey.xmlunit.XMLTestCase;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.framework.Bundle;

import java.io.*;
import java.net.URL;

public class FormatWriteTests extends XMLTestCase {

	IDesignerData designerData;
	ResourceSet resourceSet;
	File outputFile;
	
	static final String PROJECT_NAME = "datamodel-format-write-tests";
	static final String FILE_NAME = "model.nxd";
	static final String REFERENCE_FILE = "/data/reference-output.nxd";

	protected void setUp() throws Exception {
		IProject project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
		File projectDir = new File(project.getLocation().toOSString());
		outputFile = new File(projectDir, FILE_NAME);
		
		resourceSet = new ResourceSetImpl();
		
		URI uri = URI.createFileURI(outputFile.toString());
		Resource r = resourceSet.createResource(uri);
		
		// create the root object and add it to the resource
		designerData = DmFactory.eINSTANCE.createIDesignerData();
		r.getContents().add(designerData);				
	}

	protected void tearDown() throws Exception {
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}
	
	private StringValue literal(String s) {
		return new StringValue(StringValue.LITERAL, s);
	}

	public void testFormatWrite() throws Exception {
		
		designerData.getProperties().set("dd1", literal("100"));
		IPropertyValue ddCompound = designerData.getProperties().createPropertyContainerForProperty("dd2");
		ddCompound.getCompoundValue().set("dd2.a", literal("abc"));
		ddCompound.getCompoundValue().set("dd2.b", literal("def"));
		designerData.getProperties().getProperties().put("dd2", ddCompound);
		
		ILocalizedStringBundle stringBundle = designerData.getStringBundle();
		Language lang = new Language(Language.LANG_Hebrew);
		stringBundle.addLocalizedStringTable(lang);
		
		INode rootNode = DmFactory.eINSTANCE.createINode();
		rootNode.setComponentId("com.nokia.sdt.series60.CCoeControl");
		designerData.getRootContainers().add(rootNode);
		rootNode.getProperties().set("name", literal("CCoeControl1"));	
		StringValue sv = stringBundle.addLocalizedStringDefault("abc def ghi");
		IPropertyValue pv = rootNode.getProperties().set("another", sv);
		
		INode childNode1 = DmFactory.eINSTANCE.createINode();
		childNode1.setComponentId("child-component1");
		rootNode.getChildren().add(childNode1);

		childNode1.getProperties().set("name", literal("child1"));
		IPropertyValue locProperty = childNode1.getProperties().createPropertyContainerForProperty("location");
		locProperty.getCompoundValue().set("x", literal("100"));
		locProperty.getCompoundValue().set("y", literal("200"));
		childNode1.getProperties().getProperties().put("location", locProperty);
		
		IPropertyValue listProperty = childNode1.getProperties().createSequenceForProperty(null);
		pv = DmFactory.eINSTANCE.createIPropertyValue();
		pv.setStringValue(literal("first item"));
		listProperty.getSequenceValue().add(pv);
		pv = DmFactory.eINSTANCE.createIPropertyValue();
		pv.setStringValue(literal("second item"));
		listProperty.getSequenceValue().add(pv);
		childNode1.getProperties().getProperties().put("stringarray", listProperty);
				
		INode childNode2 = DmFactory.eINSTANCE.createINode();
		childNode2.setComponentId("child-component2");
		rootNode.getChildren().add(childNode2);
		childNode2.getProperties().set("name", literal("child2"));
		
		IEventBinding binding = DmFactory.eINSTANCE.createIEventBinding();
		binding.setEventID("com.nokia.testevent");
		binding.setEventHandlerDisplayText("handlerMethod");
		binding.setEventHandlerInfo("CMyClass::handlerMethod");
		childNode2.getEventBindings().add(binding);
		
		binding = DmFactory.eINSTANCE.createIEventBinding();
		binding.setEventID("com.nokia.anotherevent");
		binding.setEventHandlerDisplayText("anotherHandlerMethod");
		binding.setEventHandlerInfo("CMyClass::anotherHandlerMethod");
		childNode2.getEventBindings().add(binding);
		
		stringBundle.addLocalizedStringDefault("localized string 1");
		stringBundle.addLocalizedStringDefault("this has special chars:&<>;' ");

		Resource r = (Resource) resourceSet.getResources().get(0);
		r.save(null);
		
		// compare against reference data for the same content
		Bundle bundle = Platform.getBundle("com.nokia.sdt.emf.dm.tests");
		Path path = new Path(REFERENCE_FILE);
		URL url = Platform.find(bundle, path);
		url = Platform.asLocalURL(url);
		File referenceFile = new File(url.getFile());
		assertTrue(referenceFile.exists());

		Reader outputReader = new FileReader(outputFile);
		Reader referenceReader = new FileReader(referenceFile);
		try {
			assertXMLEqual(referenceReader, outputReader);
		}
		finally {
			outputReader.close();
			referenceReader.close();
		}
	}

}
