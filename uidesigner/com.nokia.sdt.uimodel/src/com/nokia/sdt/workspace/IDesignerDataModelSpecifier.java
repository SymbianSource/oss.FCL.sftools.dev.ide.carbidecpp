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
package com.nokia.sdt.workspace;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.swt.graphics.Image;

/**
 * Abstract specifier for a data model that can be loaded.
 * Does not need to correspond to a single file.
 *
 */
public interface IDesignerDataModelSpecifier extends IAdaptable {

	void dispose();
	
	/**
	 * To handle project renames do not cache this
	 * reference, but call to get it as needed
	 */
	IProjectContext	getProjectContext();
	
	/**
	 * Get the path to the primary resource,
	 * relative to the project, if open, or
	 * the current directory otherwise (for testing).
	 * <p>
	 * In principle a model can be comprised of 
	 * multiple resources. In such cases there should
	 * be a root, or primary resource, e.g. the file a
	 * user opens in the user interface.
	 * <p>
	 * @return path, never null
	 */
	IPath getPrimaryResourcePath();
	
	/**
	 * Get the primary IResource
	 * <p>
	 * In principle a model can be comprised of 
	 * multiple resources. In such cases there should
	 * be a root, or primary resource, e.g. the file a
	 * user opens in the user interface.
	 * 
	 * To handle file renames do not cache this value,
	 * but get it as needed
	 * @return resource, may be null
	 */
	IResource getPrimaryResource();
	
	String getDisplayName();
	
	/**
	 * If the design is editable, return null, else return a localized
	 * string describing why it cannot be edited.
	 * @return string for error or null
	 */
	String isEditable();
		
	boolean isUIDesign();
	
	/**
	 * The returned image is owned by the caller and
	 * must be disposed
	 */
	Image getSnapshot();
	
	/**
	 * Loads the data model. 
	 * <p>
	 * Initializes the model with a sourcegen session,
	 * which implies synchronization with user edits.
	 * The model may be presented to the action with
	 * changes from what is on disk.
	 */
	LoadResult load();

	/**
	 * Loads the data model without sourcegen support.
	 * <p>
	 * Use this to read the model as it appears on disk,
	 * without incorporating any changes from generated files
	 * back into the model, or to read the model before
	 * sourcegen is completely initialized.
	 * <p>
	 * The model, if saved, will also not generate sources.
	 */
	LoadResult loadNoSourceGen();

	/**
	 * Execute the action with a loaded model.
	 * Uses a cached model if available, otherwise
	 * a model is loaded and unloaded within this call.
	 * @return the result of IRunWithModelAction#run
	 * @see #load()
	 */
	Object runWithLoadedModel(IRunWithModelAction action);
	
	/**
	 * Execute the action with a loaded model.
	 * Uses a cached model if available, otherwise
	 * a model is loaded and unloaded within this call.
	 * <p>
	 * Loads model without sourcegen support.
	 * @return the result of IRunWithModelAction#run
	 * @see #loadNoSync()
	 */
	Object runWithLoadedModelNoSourceGen(IRunWithModelAction action);
	
	void openInEditor() throws CoreException;
	
	/**
	 * Called internally by IDesignerDataModel implementation
	 */
	void modelUnloaded(IDesignerDataModel model);
	void modelSaved(IDesignerDataModel model);
	
	interface IRunWithModelAction {
		Object run(LoadResult lr);
	}
    
    /**
     * Create a message location referring to this data model
     * or instances owned by this data model.
     */
    MessageLocation createMessageLocation();

	/**
	 * Notify the specifier that its model resource(s) changed.
	 */
	void notifyModelResourceChanged();
}
