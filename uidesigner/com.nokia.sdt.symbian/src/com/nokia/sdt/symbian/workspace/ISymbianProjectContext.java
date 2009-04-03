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
package com.nokia.sdt.symbian.workspace;

import com.nokia.sdt.emf.dm.Language;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.resources.IFile;

public interface ISymbianProjectContext {

	/**
	 * Returns the specifier for the root model, if it exists
	 */
	IDesignerDataModelSpecifier getRootModel();

	/**
	 * Creates a new specifier for a non-root file. The file does not need
	 * to exist. The specifier is not added to the project's
	 * list of model specifiers. Call addModel once the model
	 * file has been successfully created.
	 */
	IDesignerDataModelSpecifier createNewModelSpecifier(IFile modelFile);
	
	/**
	 * Creates a new specifier for the model root. If the root
	 * already exists for this project then this method will
	 * return null, since the root cannot be replace.
	 * @return
	 */
	IDesignerDataModelSpecifier createNewRootModelSpecifier();
	
	/**
	 * Add the specifier for a newly created model. The root model can be added, if it doesn't
	 * exist already. 
	 * View models can be added at any time. If a duplicate is added then IllegalArgumentException is throw
	 * @throws IllegalStateException if a root model is added when the root already is set
	 * @throws IllegalArgumentException if a duplicate viwe model is added
	 */
	void addModel(IDesignerDataModelSpecifier newModel);	
	
	/**
	 * Set the current language.
	 * @param language a <code>com.nokia.sdt.emf.dm.Language</code>
	 */
	void setCurrentLanguage(Language language);
	
	/**
	 * Get the current language
	 * @return a <code>com.nokia.sdt.emf.dm.Language</code>
	 */
	Language getCurrentLanguage();
}
