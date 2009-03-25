/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.command.Command;

import java.util.Collection;
import java.util.List;

/**
 * This interface defines the behavior for handling source manipulation,
 * both for saving and for updating the data model from source.
 * <p>
 * It manages one project's data model.  The ISourceGenProvider
 * provides the project-level context.
 * <p>
 * A session is long-lived, being created upon load and kept around
 * until a save, and will manage the state that allows for
 * efficient saves after parsing structures from source upon a load.
 * (If not reused between load and save, the save operation will 
 * essentially be a one-way operation and overwrite "two-way" sources.)
 * 
 */
public interface ISourceGenSession extends IDisposable, IAdaptable {
	/**
	 * Tell whether the session was disposed.
	 */
	public boolean isDisposed();
	
	/**
	 * Get the data model
	 */
	public IDesignerDataModel getDataModel();

	/**
	 * Get the provider
	 */
	public ISourceGenProvider getSourceGenProvider();

	/**
	 * Get the variable provider
	 */
	public IVariableProvider getVariableProvider();
	
    /**
	 * Get the current state of the data model and sources.<p>
	 * This creates any DOMs by reading files from disk and restores
	 * the notion of what resources were generated, how array members
	 * map to instances, and finds out which localized strings are 
	 * user-generated.  No changes are made to the data model or sources.<p>
	 * This assumes that any loaded ISourceFiles are up to date.
	 */
	public void loadFromSource() throws CoreException;

	/**
     * Generate a command to update the data model from the source code (two-way)
     * <p>
     * This may only be called after loadFromSource().
     * <p>
     * This should be called on data model loads before validation 
     * and perhaps even before data model saves (this is optional) 
     * to process user edits of two-way source code and synchronize
     * them into the data model.
     *  
     * @return a Command which applies changes to the model
     * @throws CoreException if files cannot be read from disk
     */
    public Command updateFromSource(IProgressMonitor monitor) throws CoreException;

	/**
	 * Tell the session to make changes only related to upgrading sources
	 * from one version to another.  Some patches will affect the structure
	 * of the file, so it is not possible to apply some one-way templates until
	 * patches are applied.
	 * @param isUpgrading
	 */
	public void setUpgradingMode(boolean isUpgrading);


    /**
     * Generate source (one-way and two-way) from the given
     * model.  This may modify the data model.  
     * <p>
     * This should be called before the data model is saved.
     * <p>
     * If any two-way structures are in memory from an
     * #updateFromSource() call, they will be used to
     * udpate two-way files.   
     * <p>
     * Most changes to files are kept in memory until
     * ISourceGenProvider#saveGeneratedSources(IFileSaver) is called, but
     * some file entries may be created in the project (!)
     * due to CDT requirements.
     * <p>
     * Any patches to components are kept in memory and not applied
     * to source until #getPatchInfo() and #applySourcePatches() are called.
     * @param monitor
     * @throws CoreException propagated from IFileLoader calls 
     */
    public void generateSource(IProgressMonitor monitor) throws CoreException;

	/**
	 * Do a minimal sourcegen pass to find out what files
	 * will be generated.  This does not make any changes
	 * to the project.  Changes are retained in memory but not
	 * guaranteed to be useful (i.e. they may be incomplete).
	 * It is recommended to use this in a fresh provider.
	 * @return list of workspace-relative paths
	 */
	public List<IPath> scanForGeneratedSources(IProgressMonitor monitor);

	/**
	 * Ensure the current model matches this one.
	 * @param model
	 */
	public void ensureDataModel(IDesignerDataModel model);

	/**
	 * Get messages related to source generation which should 
	 * stick to the model until the next load or save.
	 * @return live reference to collection
	 */
	public Collection<IMessage> getMessages();

	/**
	 * Create a refactoring engine for use in applying required patches
	 * to source as a result of component version changes.<p>
	 * Only works after generating source!
	 * @param versionProvider the provider for component change information
	 * @returns null if no refactoring is required or available. 
	 */
	public ISourceGenUpgradingProvider getSourceGenUpgradingProvider(ISourceGenComponentVersionProvider versionProvider);

}
