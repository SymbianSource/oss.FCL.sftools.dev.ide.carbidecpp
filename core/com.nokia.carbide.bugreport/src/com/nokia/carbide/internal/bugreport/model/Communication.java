/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
 
package com.nokia.carbide.internal.bugreport.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;

import com.nokia.carbide.internal.bugreport.export.IProduct;
import com.nokia.carbide.internal.bugreport.plugin.BugReporterPlugin;
import com.nokia.carbide.internal.bugreport.resources.Messages;

/**
 * This class provides only one static method for sending the 
 * bug_report to the server.
 *
 */
public class Communication {

	/**
	 * This class is not meant to be instantiated.
	 *
	 */
	private Communication() {		
	}
	
	/**
	 * Sends given fields as a bug_report to the server provided by product.
	 * @param fields values which are sent to the server
	 * @param product product provides e.g. server URL
	 * @return bug_id of the added bug_entry
	 * @throws RuntimeException if an error occurred. Error can be either some communication error 
	 * or error message provided by the server (e.g. invalid password)
	 */
	public static String sendBugReport(Hashtable<String, String> fields, IProduct product) 
										throws RuntimeException {
		
		// Nothing to send
		if (fields == null || fields.size() < 1) {
			throw new RuntimeException(Messages.getString("Communication.NothingToSend")); //$NON-NLS-1$
		}
		
		String bugNumber = ""; //$NON-NLS-1$
		String url = product.getUrl();
		if (url.startsWith("https")) { //$NON-NLS-1$
			// we'll support HTTPS with trusted (i.e. signed) certificates
//			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol"); //$NON-NLS-1$ //$NON-NLS-2$
		}
        PostMethod filePost = new PostMethod(url);
        Part[] parts = new Part[fields.size()];
        int i = 0;
		Iterator<Map.Entry<String, String>> it = fields.entrySet().iterator();
		
		// create parts
		while (it.hasNext()) {
			Map.Entry<String, String> productField = it.next();
			
			// attachment field
			if (productField.getKey() == FieldsHandler.FIELD_ATTACHMENT) {
				File f = new File(productField.getValue());
				try {
					parts[i] = new FilePart(FieldsHandler.FIELD_DATA, f);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					parts[i] = new StringPart(FieldsHandler.FIELD_DATA, Messages.getString("Communication.NotFound")); //$NON-NLS-1$
				}
			// string field
			} else {
				parts[i] = new StringPart(productField.getKey(), productField.getValue());
			}
			i++;
		}		
        
        filePost.setRequestEntity(
            new MultipartRequestEntity(parts, filePost.getParams())
            );

        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
        setProxyData(client, filePost);
        int status = 0;
        String receivedData = ""; //$NON-NLS-1$
        try {
        	status = client.executeMethod(filePost);
        	receivedData =  filePost.getResponseBodyAsString(1024*1024);
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        } finally {
        	filePost.releaseConnection();
        }

        // HTTP status codes: 2xx = Success
    	if (status >= 200 && status < 300) {
        	// some error occurred in the server side (e.g. invalid password)
        	if (receivedData.contains(FieldsHandler.TAG_RESPONSE_ERROR) && 
        		 receivedData.contains(FieldsHandler.TAG_RESPONSE_ERROR_CLOSE)) {
        		int beginIndex = receivedData.indexOf(FieldsHandler.TAG_RESPONSE_ERROR) + 
        			FieldsHandler.TAG_RESPONSE_ERROR.length();
        		int endIndex = receivedData.indexOf(FieldsHandler.TAG_RESPONSE_ERROR_CLOSE);
        		String error = receivedData.substring(beginIndex, endIndex);
        		error = error.trim();
        		throw new RuntimeException(error);
        	// bug_entry was added successfully to database, read the bug_number
        	} else if (receivedData.contains(FieldsHandler.TAG_RESPONSE_BUG_ID) && 
        				receivedData.contains(FieldsHandler.TAG_RESPONSE_BUG_ID_CLOSE)) {
        		int beginIndex = receivedData.indexOf(FieldsHandler.TAG_RESPONSE_BUG_ID) + 
        			FieldsHandler.TAG_RESPONSE_BUG_ID.length();
        		int endIndex = receivedData.indexOf(FieldsHandler.TAG_RESPONSE_BUG_ID_CLOSE);
        		bugNumber = receivedData.substring(beginIndex, endIndex);
        		bugNumber = bugNumber.trim();
        	// some unknown error
        	} else {
        		throw new RuntimeException(Messages.getString("Communication.UnknownError")); //$NON-NLS-1$
        	}
        // some HTTP error (status code other than 2xx)
    	} else {
    		String error = Messages.getString("Communication.HttpError") + status; //$NON-NLS-1$
    		throw new RuntimeException(error);
    	}
        
        return bugNumber;
	}

	public static void setProxyData(HttpClient client, PostMethod postMethod) {
		IProxyService proxyService = BugReporterPlugin.getDefault().getProxyService();
		boolean proxiesEnabled = proxyService.isProxiesEnabled();
		if (!proxiesEnabled)
			return;
		IProxyData proxyData = proxyService.getProxyData(IProxyData.HTTP_PROXY_TYPE);
		String host = proxyData.getHost();
		int port = proxyData.getPort();
		client.getHostConfiguration().setProxy(host, port);
		if (proxyData.isRequiresAuthentication()) {
			String userId = proxyData.getUserId();
			String password = proxyData.getPassword();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userId, password);
			AuthScope authScope = new AuthScope(host, port);
			client.getState().setCredentials(authScope, credentials);
			postMethod.setDoAuthentication(true);
		}
	}
	
}
