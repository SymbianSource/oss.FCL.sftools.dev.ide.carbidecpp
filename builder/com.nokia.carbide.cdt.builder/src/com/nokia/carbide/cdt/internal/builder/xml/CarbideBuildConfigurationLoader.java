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
package com.nokia.carbide.cdt.internal.builder.xml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.nokia.carbide.cdt.internal.builder.CarbideProjectInfo;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.util.CarbideBuildConfigResourceFactoryImpl;


public class CarbideBuildConfigurationLoader {
	
	public static final short SETTINGS_VERSION_0 = 0; // Carbide 1.2 Alpha 1 and Beta 1 (through B18)
	public static final short SETTINGS_VERSION_1 = 1; // Carbide 1.2 Build 19+. Here we convert from save WINSCW settings to just generating them dynamically
	public static final short SETTINGS_CURRENT_VERSION = SETTINGS_VERSION_1; // current settings version in the .carbide_build_settings file
	
	/**
	 * Load a Carbide build configurations file
	 * @param url - Full path to the .settings\.carbide_settings file
	 * @return CarbideBuilderConfigInfoType - a model that represents a full set of project configurations
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static CarbideBuilderConfigInfoType loadBuildConfigurations(URL url) throws URISyntaxException, IOException  {
		if (url == null)
			return null;
		URI xmlURI = URI.createURI(url.toString());

		CarbideBuildConfigResourceFactoryImpl factory = new CarbideBuildConfigResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);

		r.load(null);
		EList contents = r.getContents();
	
		DocumentRoot root = (DocumentRoot) contents.get(0);
		CarbideBuilderConfigInfoType buildConifgs = root.getCarbideBuilderConfigInfo();
		
		return buildConifgs;
	}
	
	/**
	 * Save a new configuation list to file
	 * @param config-  The configuration to add to the list
	 * @param url -  Full path to the .settings\.carbide_settings file
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static boolean writeBuildConfigurationToDisk(ConfigurationType config, URL url) throws URISyntaxException, IOException{
 		if (url == null)
			return false;
		URI xmlURI = URI.createURI(url.toString());

		CarbideBuildConfigResourceFactoryImpl factory = new CarbideBuildConfigResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);

		r.load(null);
		EList contents = r.getContents();
		DocumentRoot root = (DocumentRoot) contents.get(0);
		CarbideBuilderConfigInfoType buildConifgs = root.getCarbideBuilderConfigInfo();
		buildConifgs.setVersion(SETTINGS_CURRENT_VERSION);
		EList configList = buildConifgs.getConfiguration();
		// Find the config we are updating and remove it.
		for (Iterator i = configList.iterator(); i.hasNext();) {
			ConfigurationType currConfig = (ConfigurationType)i.next();
			 if (currConfig.getName().equals(config.getName())){
				 configList.remove(currConfig);
				 break;
			 }
			
		}
		
		configList.add(config); // Add the new/modified config to the list so new data is written
		
		// write to disk
		r.save(null);
		return true;
	}
	
	/**
	 * Delete a build configuration
	 * @param config
	 * @param url
	 * @return true on success
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static boolean deleteBuildConfiguration(ConfigurationType inConfig, URL url) throws URISyntaxException, IOException{
		if (url == null)
			return false;
		URI xmlURI = URI.createURI(url.toString());

		CarbideBuildConfigResourceFactoryImpl factory = new CarbideBuildConfigResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);

		r.load(null);
		EList contents = r.getContents();
		DocumentRoot root = (DocumentRoot) contents.get(0);
		CarbideBuilderConfigInfoType buildConifgs = root.getCarbideBuilderConfigInfo();
		EList configList = buildConifgs.getConfiguration();
		
		// find a match in the configuration name and remove it
		for (Iterator i = configList.iterator(); i.hasNext();) {
			ConfigurationType currConfig = (ConfigurationType)i.next();
			 if (currConfig.getName().equals(inConfig.getName())){
				 configList.remove(currConfig);
				 break;
			 }
			
		}
		
		r.save(null);
		return true;
	}
	
}
