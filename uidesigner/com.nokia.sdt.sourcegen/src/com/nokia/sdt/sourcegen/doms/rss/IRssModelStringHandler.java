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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile;
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.eclipse.emf.common.command.Command;

/**
 * Interface for string import/export in RSS.<p>
 * This assumes that strings are exported once per model,
 * and that if a model generates multiple RSS files,
 * each one shares the same exported (loc/rls) files.
 * 
 * 
 */
public interface IRssModelStringHandler {

	/**
	 * Export strings from the data model
	 * @param dataModel
	 * @param mainFile the main RSS/RSSI file for the model
	 * @param targetFile the file into which to include strings (usually == mainFile, except for additional RSS files) 
	 * @param fileManager
	 */
	public void exportStrings(IDesignerDataModel dataModel,
			IAstRssSourceFile mainFile, IAstRssSourceFile targetFile,
			IRssProjectFileManager fileManager);

	/**
	 * Import strings into the data model,
	 * returning two Commands which will do the work
	 * of (1) registering the user-defined strings present in the
	 * localization files and (2) changing existing strings as
	 * needed from the source.  The first command does not
	 * change the persisted state of the model; the second does.   
	 * 
	 * @param dataModel the model
	 * @param rssFile
	 * @param fileManager
	 * @return commands that register user-defined strings and make any changes
	 */
	public Pair<Command, Command> importStrings(IDesignerDataModel dataModel,
			IAstRssSourceFile rssFile, IRssProjectFileManager fileManager);
}
