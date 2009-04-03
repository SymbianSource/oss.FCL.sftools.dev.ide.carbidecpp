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
*/
package com.nokia.carbide.cpp.internal.sdk.core.xml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesFactory;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.util.DevicesResourceFactoryImpl;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class DevicesLoader {

	public static DevicesType loadDevices(URL url) throws URISyntaxException, IOException  {
		if (url == null)
			return null;
		URI xmlURI = URI.createURI(url.toString());

		DevicesResourceFactoryImpl factory = new DevicesResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);

		r.load(null);
		EList contents = r.getContents();
	
		DocumentRoot root = (DocumentRoot) contents.get(0);
		DevicesType devices = root.getDevices();
		
		return devices;
	}
	
	/**
	 * Delete a device entry from devices.xml
	 * @param inDeviceToRemove
	 * @param url
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static boolean deleteDeviceEntry(DeviceType inDeviceToRemove, URL url) throws URISyntaxException, IOException{
		if (url == null)
			return false;
		URI xmlURI = URI.createURI(url.toString());
		
		DevicesResourceFactoryImpl factory = new DevicesResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);

		r.load(null);
		EList contents = r.getContents();
	
		DocumentRoot root = (DocumentRoot) contents.get(0);
		DevicesType devices = root.getDevices();
		EList devicesList = devices.getDevice();
		// find a match in the configuration name and remove it
		for (Iterator i = devicesList.iterator(); i.hasNext();) {
			DeviceType currDevice = (DeviceType)i.next();
			 if (currDevice.getId().equals(inDeviceToRemove.getId())){
				 devicesList.remove(currDevice);
				 break;
			 }
		}
		
		r.save(null);
		return true;
	}
	
	/**
	 * Write a device entry to devices.xml. If the device entry already exists it just updates it
	 * otherwise a new entry is added
	 * @param sdk-  The Symbian SDK to write to devices.xml
	 * @param url -  Full path to the devices.xml file
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static boolean updateDevice(ISymbianSDK sdk, URL url) throws URISyntaxException, IOException{
 		if (url == null)
			return false;
		URI xmlURI = URI.createURI(url.toString());
		
		DevicesResourceFactoryImpl factory = new DevicesResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);
		r.load(null);
		EList contents = r.getContents();
		DocumentRoot root = (DocumentRoot) contents.get(0);
		DevicesType devices = root.getDevices();
		EList devicesList = devices.getDevice();
		
		// Find the device entry we are updating
		boolean deviceExists = false;
		for (Iterator i = devicesList.iterator(); i.hasNext();) {
			DeviceType currDevice = (DeviceType)i.next();
			 if (currDevice.getId().equals(sdk.getUniqueId())){
				 deviceExists = true;
				 currDevice.setEpocroot(sdk.getEPOCROOT());
				 currDevice.setId(sdk.getUniqueId());
				 currDevice.setName(sdk.getName());
				 if (currDevice.getUserdeletetable() != null){
					 currDevice.setUserdeletable(currDevice.getUserdeletetable());
				 }
				 // Workaround boog 3125. CW 3.0 and prior misnamed the attribute "userdeletetable"
				 // It should have been "userdeletable". So in order to parser the CW data correctly
				 // we'll read it in but set it back to null while setting the correct value.
				 // This will make sure the devices.xml file gets updated correctly on the next write.
				 // However, older versions of CW may no longer be able to read the file.
				 currDevice.setUserdeletetable(null);
				 break;
			 }
		}
		
		if (deviceExists == false){
			// Create the device entry, it doesn't exist
			DeviceType newDeviceEntry = DevicesFactory.eINSTANCE.createDeviceType();
			newDeviceEntry.setId(sdk.getUniqueId());
			newDeviceEntry.setEpocroot(sdk.getEPOCROOT());
			newDeviceEntry.setName(sdk.getName());
			if (sdk.isDefaultSDK() == true){
				newDeviceEntry.setDefault(DefaultType.YES_LITERAL);
			} else {
				newDeviceEntry.setDefault(DefaultType.NO_LITERAL);
			}
			newDeviceEntry.setUserdeletable("no");
			newDeviceEntry.setUserdeletetable(null); // just to be sure it doens't get written out
			devicesList.add(newDeviceEntry); // Add the new/modified config to the list so new data is written			
		}
		
		// write to disk
		r.save(null);
		return true;
	}
	
	/**
	 * Sets the 'default' device attribute. Sets all othere devices to default=false.
	 * @param sdk
	 * @param url
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void setDefaultDevice(ISymbianSDK sdk, URL url) throws URISyntaxException, IOException{
 		if (url == null)
			return;
		URI xmlURI = URI.createURI(url.toString());
		
		DevicesResourceFactoryImpl factory = new DevicesResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);
		r.load(null);
		EList contents = r.getContents();
		DocumentRoot root = (DocumentRoot) contents.get(0);
		DevicesType devices = root.getDevices();
		EList devicesList = devices.getDevice();
		
		// Iterate all the devices and set the all to default=false
		// Set the input sdk paramater's default to whatever it's default value is
		for (Iterator i = devicesList.iterator(); i.hasNext();) {
			DeviceType currDevice = (DeviceType)i.next();
			 if (currDevice.getId().equals(sdk.getUniqueId())){
				 if (sdk.isDefaultSDK()){
					 currDevice.setDefault(DefaultType.YES_LITERAL);
				 } else {
					 currDevice.setDefault(DefaultType.NO_LITERAL);
				 }
			 }else {
				 currDevice.setDefault(DefaultType.NO_LITERAL);
			 }
		}
		
		// write to disk
		r.save(null);
		return;
	}
}
