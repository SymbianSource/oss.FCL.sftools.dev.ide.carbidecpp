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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;
import java.util.ArrayList;
import org.eclipse.core.runtime.Platform;

import com.nokia.carbide.internal.bugreport.export.IProduct;
import com.nokia.carbide.internal.bugreport.resources.Messages;

/**
 * A class for handling field-value pair which are sent to the server.
 *
 */
public class FieldsHandler {
	
	public static final String FIELD_PRODUCT = "product"; //$NON-NLS-1$
	public static final String FIELD_SUMMARY = "short_desc"; //$NON-NLS-1$
	public static final String FIELD_DESCRIPTION = "comment"; //$NON-NLS-1$
	public static final String FIELD_ATTACHMENT = "attachment_file"; //$NON-NLS-1$
	public static final String FIELD_TYPE = "cf_type"; //$NON-NLS-1$
	public static final String FIELD_ATTACHMENT_DESCRIPTION = "description"; //$NON-NLS-1$
	public static final String FIELD_CONTENT_TYPE = "contenttypemethod"; //$NON-NLS-1$
	public static final String FIELD_DATA = "data"; //$NON-NLS-1$
	public static final String FIELD_PASSWORD = "password"; //$NON-NLS-1$
	public static final String FIELD_VALUE_AUTODETECT = "autodetect"; //$NON-NLS-1$
	public static final String FIELD_HEADER_CHAR = "@"; //$NON-NLS-1$
	public static final String TAG_RESPONSE_ERROR = "<error>"; //$NON-NLS-1$
	public static final String TAG_RESPONSE_ERROR_CLOSE = "</error>"; //$NON-NLS-1$
	public static final String TAG_RESPONSE_BUG_ID = "<bug>"; //$NON-NLS-1$
	public static final String TAG_RESPONSE_BUG_ID_CLOSE = "</bug>"; //$NON-NLS-1$
	private static final String PASSWORD_FOR_UI = "******"; //$NON-NLS-1$
	private static final String ATTACHMENT_ZIP_FILE = "bugreport_attachments.zip"; //$NON-NLS-1$
	private static final int MAX_ZIP_FILES_IN_SUMMARY = 5;
	
	Hashtable<String, String> finalFields = null;
	
	public FieldsHandler() {
	}
	
	/**
	 * Returns the hashtable which contains all the fields to be sent to the server.
	 * Make sure you call prepareFinalFields before calling this.
	 * @return fields and values which can be sent to server
	 */
	public Hashtable<String, String> getFieldsForSending() {
		return finalFields;
	}
	
	/**
	 * Combines bug_reporter's fields and product's fields to one final hashtable, which can be 
	 * sent to the server.
	 * @param reportFields bug_reporter's fields
	 * @param product product plug-in
	 * @return hashtable which contains all fields and values which are sent to server
	 */
	public void prepareFinalFields(Hashtable<String, String> reportFields, IProduct product) {
		finalFields = new Hashtable<String, String>();
		finalFields.put(FIELD_PRODUCT, product.getProductName());
		finalFields.putAll(reportFields);
		
		String[] productAttachments = product.getAttachments();
		
		// user provided an attachment
		if (finalFields.containsKey(FIELD_ATTACHMENT)) {
			// user and product provided attachments
			if (productAttachments != null ) {
				zipFiles(productAttachments, finalFields.get(FIELD_ATTACHMENT));
				attachZipFile(product);
			// only user provided an attachment, add needed fields
			} else {
				File f = new File(finalFields.get(FIELD_ATTACHMENT));
				finalFields.put(FIELD_ATTACHMENT_DESCRIPTION, f.getName());
				finalFields.put(FIELD_CONTENT_TYPE, FIELD_VALUE_AUTODETECT);
			}
		// user did not provide an attachment, but the product did.
		} else if (productAttachments != null) {
			zipFiles(productAttachments);
			attachZipFile(product);
		}
		
		// Add fields from product plugin
		Hashtable<String, String> productFields = product.getFields();
		Iterator<Map.Entry<String, String>> i = productFields.entrySet().iterator();
		while (i.hasNext()) { // iterate through all fields from the product
			Map.Entry<String, String> productField = i.next();
			String key = productField.getKey();
			String value = productField.getValue();
			// product tries to provide a field which the bug_reporter defines itself.
			if (finalFields.containsKey(key)) { 
				// some bug_reporter fields can be appended to
				if (isAppendableKey(key)) { 
					String newValue = finalFields.get(key);
					newValue += System.getProperty("line.separator"); //$NON-NLS-1$
					newValue += value;
					finalFields.put(key, newValue);
				// unappendable field, overwrite bug_reporter's field with product's field				
				} else {
					// product provides an attachment, ignore this because product's attachments are already handled.
					if (key == FIELD_ATTACHMENT) {
						continue;
					}
					finalFields.put(key, value);
				}
			// unique field, add it
			} else {
				finalFields.put(key, value);
			}
		}
	}
	
	/**
	 * Formats the fields and values to a user friendly form.
	 * @return user friendly form of the field-value pairs
	 */
	public String getSummary() {
		if (finalFields == null) {
			return ""; //$NON-NLS-1$
		}
		String summary = ""; //$NON-NLS-1$
		String newLine = System.getProperty("line.separator"); //$NON-NLS-1$
		
		summary += getSummaryLine(FIELD_PRODUCT, newLine);
		summary += getSummaryLine(FIELD_TYPE, newLine);
		summary += getSummaryLine(FIELD_SUMMARY, newLine);
		summary += getSummaryLine(FIELD_DESCRIPTION, newLine);
		summary += getSummaryLine(FIELD_ATTACHMENT, newLine);
		
		Iterator<Map.Entry<String, String>> i = finalFields.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry<String, String> productField = i.next();
			String key = productField.getKey();
			if (summary.contains(FIELD_HEADER_CHAR+key+newLine))	// don't allow duplicate fields
				continue;
			summary += getSummaryLine(key, newLine);
		}		
		
		return summary;
	}
	
	/**
	 * Returns a user friendly form of one field-value pair.
	 * @param field 
	 * @param newLine new line character(s) e.g. \r\n
	 * @return user frindly form of one field-value pair.
	 */
	private String getSummaryLine(String field, String newLine) {
		if (finalFields == null)
			return ""; //$NON-NLS-1$
		String value = finalFields.get(field);
		if (value == null || value == "") //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		if (field == FIELD_ATTACHMENT && value.toLowerCase().endsWith(".zip")) { // show (max 5) files inside the zip //$NON-NLS-1$
			ArrayList<String> filesInZip = getFilesInZip(value);
			if (filesInZip.size() > 0) {
				String retVal = newLine+FIELD_HEADER_CHAR+field+newLine+value+newLine;
				for (int i = 0; i < filesInZip.size(); i++) {
					retVal += "  "+Messages.getString("FieldsHandler.StartFile")+filesInZip.get(i)+Messages.getString("FieldsHandler.EndFile")+newLine; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return retVal;
			}
		} else if (field.contains(FIELD_PASSWORD)) {
			value = PASSWORD_FOR_UI;
		}
		return newLine+FIELD_HEADER_CHAR+field+newLine+value+newLine;
	}	
	
	/**
	 * Checks if the given field is such a field which can be appended to. E.g. if product tries 
	 * to provide a summary field, we don't override bug_reporter's summary field, but we append 
	 * the product's summary field value to bug_reporter's summary field value. If this method 
	 * returns false, we overwrite bug_reporter's field value with product's field value.
	 * @param key key which is to be checked.
	 * @return true if key is appendable, false if not. 
	 */
	private boolean isAppendableKey(String key) {
		if (key.equals(FIELD_DESCRIPTION) ||
			key.equals(FIELD_SUMMARY))
			return true;
		return false;
	}
	
	/**
	 * Zips given files into a zip file
	 * @param filenames list of files to be zipped
	 */
	private void zipFiles(String[] filenames) {
		zipFiles(filenames, null);
	}
	
	/**
	 * Zips given files into a zip file
	 * @param filenames list of files to be zipped
	 * @param additionalFile additional file to be added to the zip file
	 */
	private void zipFiles(String[] filenames, String additionalFile) {
		// Create a buffer for reading the files
		byte[] buf = new byte[1024];

		try {
			// Create the ZIP file
			String outFilename = getAttachmentZipPath();
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
			

			// Compress the files
			for (int i=0; i<=filenames.length; i++) {
				String filename = ""; //$NON-NLS-1$
				
				if (i == filenames.length) {
					if (additionalFile == null) {
						break;
					} else {
						filename = additionalFile;
					}
				} else {
					filename = filenames[i];
				}

				FileInputStream in = new FileInputStream(filename);
				File f = new File(filename);
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(f.getName()));


				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				// Complete the entry
				out.closeEntry();
				in.close();
			}

			// Complete the ZIP file
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}	
	
	/**
	 * Reads given zip file and return max 5 names from the zip.
	 * @param zipPath path to zip file
	 * @return list of names of files in the zip file (max 5)
	 */
	private ArrayList<String> getFilesInZip(String zipPath) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream(zipPath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze;
			int added = 0;
			while((ze=zis.getNextEntry())!=null){
				if (added == MAX_ZIP_FILES_IN_SUMMARY) {
					list.add(Messages.getString("FieldsHandler.MoreFiles")); //$NON-NLS-1$
					break;
				}
				list.add(ze.getName());
				zis.closeEntry();
				added++;
			}

			zis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Sets zipped attachment file into the send report
	 * @param product product for getting the MaxAttachmentSize
	 */
	private void attachZipFile(IProduct product) {
		File f = new File(getAttachmentZipPath());
		if (f.exists() && f.isFile()) {
			if ( f.length() <= product.getMaxAttachmentSize()) {
				finalFields.put(FIELD_ATTACHMENT, getAttachmentZipPath());
				finalFields.put(FIELD_ATTACHMENT_DESCRIPTION, f.getName());
				finalFields.put(FIELD_CONTENT_TYPE, FIELD_VALUE_AUTODETECT);
			}
		}
	}
	
	/**
	 * Returns the location of the zipped attachment file
	 * @return the location of the zipped attachment file
	 */
	private String getAttachmentZipPath() {
		return Platform.getLocation().addTrailingSeparator().toOSString() + ATTACHMENT_ZIP_FILE;
	}
}
