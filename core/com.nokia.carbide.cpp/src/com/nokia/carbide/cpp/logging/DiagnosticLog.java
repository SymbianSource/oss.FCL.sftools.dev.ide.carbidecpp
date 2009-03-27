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
package com.nokia.carbide.cpp.logging;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class DiagnosticLog {
	private String name;
	private String id;
	private Logger logger;
	private File file;

	private class LogHandler extends Handler {

		FileHandler fileHandler;
		File file;
		
		public LogHandler(File file) {
			this.file = file;
		}

		@Override
		public void close() throws SecurityException {
			if (fileHandler != null)
				fileHandler.close();
		}

		@Override
		public void flush() {
			if (fileHandler != null)
				fileHandler.flush();
		}

		@Override
		public void publish(LogRecord record) {
			if (fileHandler == null)
			{
				try {
					fileHandler= new FileHandler(file.getAbsolutePath());
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 				
			}
			if (fileHandler != null)
				fileHandler.publish(record);
		}
		
	}
	
	public DiagnosticLog(String name, String id, String logFilePath) {
		this.name = name;
		this.id = id;
		this.logger = Logger.getLogger(id);
		logger.setLevel(Level.OFF);
		logger.setUseParentHandlers(false);
		this.file = new File(logFilePath);
		logger.addHandler(new LogHandler(file));		
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public File getFile() {
		return file;
	}

	public Logger getLogger() {
		return logger;
	}

}