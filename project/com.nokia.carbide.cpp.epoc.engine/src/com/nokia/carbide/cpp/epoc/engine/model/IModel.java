/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.model;


import org.eclipse.core.runtime.IPath;

/**
 * This manages a single file's contents and provides "views" onto its contents
 * and resolves changes to such views to rewrite the contents.
 * <p>
 * Instances of this interface are possibly shared and their documents managed
 * by an IModelProvider.
 * <p>
 * WARNING: Upcasting to IOwnedModel to access the document is not
 * allowed unless you are 100% sure you just created the model yourself and have
 * not yet relinquished it to the provider!
 * 
 * 
 */
public interface IModel<View> {
	/** 
	 * Get full filesystem path to file
	 */
	IPath getPath(); 
	
	/**
	 * Creates a view onto the contents of the model using the given configuration and filter.
	 * The model must be parsed first.
	 */
	View createView(IViewConfiguration configuration);  
	
	/**
	 * Get a copy of the active views on model
	 * @return array of views (never null)
	 */
	IView[] getViews();
	
	/**
	 * Add listener, ignore duplicates
	 */
	void addListener(IModelListener listener);
	
	/**
	 * Remove listener, ignore missing
	 */
	void removeListener(IModelListener listener);

	/**
	 * Get the model provider owning this model, if any.
	 * <p>
	 * Registering a model via @link{IModelProvider#registerModel()}
	 * or retrieving one via @link{IModelProvider#getSharedModel()} will
	 * set the model's provider.
	 */
	IModelProvider<IOwnedModel<View>, IModel<View>> getModelProvider();
}
