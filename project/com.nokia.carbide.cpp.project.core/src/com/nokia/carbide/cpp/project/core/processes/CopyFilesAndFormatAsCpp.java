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
package com.nokia.carbide.cpp.project.core.processes;

import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.templatewizard.processes.CopyFiles;

import org.eclipse.cdt.core.ToolFactory;
import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

/**
 * Copy files and format per CDT tab policy
 *
 */
public class CopyFilesAndFormatAsCpp extends CopyFiles {

	@Override
	public String postProcessContent(String input) {
		CodeFormatter formatter = ToolFactory.createCodeFormatter(null);
		IDocument document = new Document(input);
		TextEdit edit = 
			formatter.format(CodeFormatter.K_TRANSLATION_UNIT, input, 0, input.length(), 0, null);
		try {
			edit.apply(document);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return document.get();
	}
	
	protected Plugin getPlugin() {
		return ProjectCorePlugin.getDefault();
	}
}
