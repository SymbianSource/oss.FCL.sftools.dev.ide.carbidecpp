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

import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;

import java.util.Collection;

/**
 * This interface encapsulates RSS model generation (creating
 * and updating).  
 * 
 *
 */
public interface IRssModelGenerator extends IDisposable {
	/**
	 * Get the model this is based on
	 * @return model proxy
	 */
	public IRssModelProxy getModelProxy();

	/**
	 * Get the provider
	 */
	public ISourceGenProvider getSourceGenProvider();
	
	/**
	 * Set the primary RSS file.  
	 * @param file the current file, or null for the default
	 */
	public void setRssFileForTesting(IAstRssSourceFile file);

	/**
	 * Find or create the RSS file for the given name.
	 * Pass null to get the primary file.  
	 * @param fileName filename to use (no path) (or null)
	 * @return a file
	 */
	public IAstRssSourceFile getRssFile(String fileName, boolean allowCreate);

	/**
	 * Find or create the translation unit for the model
	 * using the given filename.  Pass null to get the
	 * primary translation unit.  This method is preferred
	 * over IRssProjectFileManager#createOrLoadTranslationUnit() 
	 * since it populates the file with initial content
	 * according to the model.
	 * @param fileName (or null)
	 * @param allowModify true: update/generate the TU if necessary, false: only load
	 * @return translation unit
	 */
	public ITranslationUnit createOrLoadTranslationUnit(String fileName, boolean allowModify);

	/**
	 * Reset the generator to a pristine state.
	 * No RSS DOM is retained.
	 */
	public void reset();

	/**
	 * Load resources from disk and load mapping state from the model.
	 * This may make changes to transient data in the model
	 * (specifically, which user-defined strings are added?).
	 * @param model
	 * @param data
	 */
	public void loadState(IDesignerDataModel model, IDesignerData data) throws CoreException;

	/**
	 * Using mapping data loaded via #loadState(), create a command
	 * to update the data model with any changes.
	 * @param monitor 
	 */
	public Command updateFromSource(IProgressMonitor monitor);

	/**
	 * Generate resources from the data model.
	 */
	public void generateResources();

	/**
	 * Generate resources for one instance (testing).
	 */
	public void generateResources(IComponentInstance instance);

	/**
	 * Delete resources for the given (deleted) instance.
	 */
	public void removeResourcesForInstance(String instanceName);

	/**
	 * Get the model manipulator, which handles ownership and
	 * modification of the RSS DOMs managed by the model.
	 */
	public IRssModelManipulator getModelManipulator();

	/**
	 * Set the string handler for localized strings.
	 * If not called or null passed in, no string handling
	 * is performed.
	 * @param stringHandler
	 */
	public void setStringHandler(IRssModelStringHandler stringHandler);

	/**
     * Merge the generated information from another context into this one.
     * @param generator
     */
    public void mergeGeneratedInformation(IRssModelGenerator generator);

	/**
	 * Save the generated state in the data model
	 */
	public void saveState(IDesignerData data);

	/**
	 * Return list of files actually generated,
	 * or expected to be generated
	 */
	public Collection<ISourceFile> scanForGeneratedFiles();

	/**
	 * Reference a resource during generation.
	 * @param definition
	 */
	public void referenceResource(IAstResourceDefinition definition);

}
