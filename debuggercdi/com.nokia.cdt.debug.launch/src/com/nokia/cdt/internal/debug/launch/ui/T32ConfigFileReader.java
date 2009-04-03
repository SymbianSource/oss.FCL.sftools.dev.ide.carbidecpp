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
package com.nokia.cdt.internal.debug.launch.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class T32ConfigFileReader {
	
	private String portNumber;
	private boolean isValid;
	private String t32Path;
	
	public T32ConfigFileReader(String t32ConfigFilePath) {
		
		//set the port from the config file
		try {
		        BufferedReader in = new BufferedReader(new FileReader(t32ConfigFilePath));
		        String str;
		        while ((str = in.readLine()) != null) {
		        	// looking for SYS setting
		        	int index = str.indexOf("SYS="); //$NON-NLS-1$
		            if (index>=0) {
		            	t32Path = str.substring(4);
		            	continue;
		            }
		        	// looking for PORT setting
		            index = str.indexOf("PORT="); //$NON-NLS-1$
		            if (index >= 0) {
		            	portNumber = str.substring(5);
		            }		            
		        }
		        in.close();
		 	} catch (IOException e) {
		 		// If we are here, then it could be that port number is not specified in the config file.
		 		isValid = false;		    	
		 }
		 isValid = true;
		 
	}
	
	public boolean isValid() { return isValid; }
	
	public String getPortNumber() { return portNumber; }
	
	public String getT32Path() { return t32Path; }
	
}
