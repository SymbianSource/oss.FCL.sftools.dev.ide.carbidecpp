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
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.sdt.utils.IFileTracker;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;

import java.io.File;
import java.util.List;

/**
 * This provides the master interface to the source generation engine
 * for one particular project.  
 * <p>
 * TODO: divide this into two classes, one for the actual provider,
 * as created through extension point, and one for the project-specific
 * context.  This serves as both.
 * <p>
 * It manages project-global state for all sourcegen operations, by
 * maintaining the project's RSS DOMs.  It creates sessions  
 * for use in handling sourcegen for one particular model (holding 
 * local information about generated resources, enums, strings,
 * etc).  This interface ensures the sessions do not conflict with
 * each other and share files/DOMs across all the models in a project.
 * <p>
 * Access to sourcegen-specific information is provided through
 * adapters.
 * 
 *
 */
public interface ISourceGenProvider extends IDisposable, IAdaptable {
	/** ID for the sourcegen provider extension point */
    public static final String SOURCEGEN_PROVIDER_EXTENSION = "com.nokia.sdt.uimodel.sourceGenProvider"; //$NON-NLS-1$

    /**
     * Flag telling if two-way export is supported.
     * If set, data is emitted on save to
     * enable two-way import (enabled separately --
     * if only enabled here, it's a debugging flag)
     */
    public static final int FLAG_TWO_WAY_EXPORT = 1;

    /**
     * Flag telling if one-way update is supported.
     * If set, previously exported two-way data is
     * used to update sources on the next save.
     * Implies FLAG_TWO_WAY_EXPORT.
     * @see #FLAG_TWO_WAY_EXPORT
     */
    public static final int FLAG_ONE_WAY_UPDATE = FLAG_TWO_WAY_EXPORT + 2;

    /**
     * Flag telling if two-way RSS import is supported.
     * If set, exported two-way data is
     * consumed from the model.
     * @see #FLAG_TWO_WAY_EXPORT
     */
    public static final int FLAG_TWO_WAY_IMPORT = 4;

    /**
     * Setting to indicate sources are overwritten and
     * never re-read.
     */
    public static final int FLAG_ONE_WAY_EXPORT = 0;

	/**
	 * Get the project.
	 * @return
	 */
	public IProject getProject();

    /**
     * Initialize the project.  If not null, this also initializes the base directory
     * and overrides any custom project name.
     */
    public void setProject(IProject project);

	/**
	 * Get the project name.
	 * @return
	 */
	public String getProjectName();

	/**
	 * Set the nominal project name.  Setting the project overrides this.
	 */
	public void setProjectName(String projectName);

    /**
     * Initialize the base directory, which is the nominal project
     * directory, but may be somewhere else for testing.
     */
    public void setBaseDirectory(File baseDir);

	/**
	 * Get the base directory, which is nominally the project directory.
	 * @return directory
	 */
	public File getBaseDirectory();

    /**
     * Set the include file locator.  If not called, a default
     * locator is used.
     */
    public void setIncludeFileLocator(IIncludeFileLocator locator);

    /**
     * Get the include file locator.
     */
    public IIncludeFileLocator getIncludeFileLocator();

    /**
     * Set the name generator.  If not called, a default
     * name generator is used.
     */
    public void setNameGenerator(INameGenerator generator);
    
    /**
     * Get the name generator.
     */
    public INameGenerator getNameGenerator();

	/**
	 * Set the source formatter.  If not set, an ugly formatter is used.
	 */
	public void setSourceFormatter(ISourceFormatter formatter);

	/**
	 * Get the source formatter.
	 */
	public ISourceFormatter getSourceFormatter();

    /**
     * Set the file tracker, which handles loading and saving text.
     * If not set, a default one will be used, which properly
     * handles non-workspace and workspace situations.
     * @param tracker
     */
    public void setFileTracker(IFileTracker tracker);

    /**
     * Get the file tracker.
     */
    public IFileTracker getFileTracker();

	/**
     * Set the source gen flags
     * @param flags a bitmask of FLAG_xxx
     */
    public void setSourceGenFlags(int flags);
    
    /**
     * Get the source gen flags
     * @return a bitmask of FLAG_xxx
     */
    public int getSourceGenFlags();
    
    /**
     * Tell whether existing sources are used
     * (same as getSourceGenFlags() & FLAG_ONE_WAY_UPDATE is set)
     */
    public boolean isOneWayUpdate();

	/**
     * Tell whether the model should be updated from existing sources
     * (same as getSourceGenFlags() & FLAG_TWO_WAY_IMPORT is set)
	 */
	public boolean isTwoWayImport();

	/**
	 * Tell if two-way export is supported
	 * (same as getSourceGenFlags() & FLAG_TWO_WAY_EXPORT is set)
	 */
	public boolean isTwoWayExport();

	/**
     * Provide all the generated files known at this time.
     * This can be called before and after a ISourceGenSession#generateSource() call
     * and a difference performed to determine what new files will
     * be modified upon a #saveGeneratedSources() call.
     * To avoid saving the files, use #revertGeneratedSources().
     * @see ISourceGenSession#generateSource(IProgressMonitor)
     * @see #saveGeneratedSources(IProgressMonitor)
     * @see #revertGeneratedSources()
     */
    public List<IPath> getGeneratedSources();
    
    /**
     * Revert any modified sources generated previously and
     * ensure that in-memory models match the disk.
     * @throws CoreException
     */
    public void revertGeneratedSources() throws CoreException;
    
    /**
     * Save changes to files made during source generation.
     * <p>
     * This does not modify the data model.  This
     * should be called after the data model is saved.
     * @param monitor
     * @throws CoreException propagated from IFileSaver calls 
     */
    public void saveGeneratedSources(IProgressMonitor monitor) throws CoreException;

    /**
     * Create a source generation session.  
     * @param model the model to generate
     * @param dmSpec model specifier
     */
    public ISourceGenSession createSourceGenSession(IDesignerDataModel model, IDesignerDataModelSpecifier dmSpec);

	/**
	 * Unregister a source gen session.
	 * @param session
	 */
	public void removeSourceGenSession(ISourceGenSession session);

    /**
     * Go to the source for an event binding in the editor.
     * @return location (never null)
     */
    public SourceLocation lookupEventBindingSource(IEventBinding binding) throws CoreException;

	/**
	 * Reset any generated state.
	 */
	public void resetGeneratedSources();

	/**
	 * Set the object that finds which models to generate source for
	 * (on a project-wide basis)).  This does not change the proxies;
	 * use #updateProjectInfo() for that.
	 * @param gatherer object or null to use default pattern (search project)
	 */
	public void setModelGatherer(ISourceGenModelGatherer gatherer);
	
	/**
	 * Update project info since design information has changed.
	 */
	public void updateProjectInfo();

	/**
	 * Update project info since design information has changed, using the given root.
	 */
	public void updateProjectInfo(IDesignerDataModel rootModel);

	/**
	 * Tell whether a file at the given path is generated source
	 * for this project.
	 * @param path base-relative path
	 * @return
	 */
	public boolean isGeneratedTwoWayFile(IPath path);

	/**
	 * Update source state, i.e. synchronize modified
	 * sources with cached DOMs and data models.
	 * @param force force update even if sources appear clean
	 */
	public IStatus updateSourceState(IProgressMonitor monitor, boolean force);
	
	/**
	 * Add listener for changes to source owned by this model
	 * @param listener
	 */
	void addSourceChangeListener(ISourceGenChangeListener listener);

	/**
	 * Remove listener for changes to source owned by this model
	 * @param listener
	 */
	void removeSourceChangeListener(ISourceGenChangeListener listener);

	/**
	 * Control whether listeners are fired (i.e. during sourcegen)
	 * @param flag
	 * @return previous setting
	 */
	public boolean enableSourceGenChangedListeners(boolean flag);

	/**
	 * Notify that source has (possibly) changed, so schedule
	 * a job to find out how it impacts the models
	 */
	public void fireSourceChanged(boolean force);

	/**
	 * Update info related to the given design specifier (namely, renaming)
	 * @param viewDesignSpecifier
	 */
	public void updateDesignSpecifierInfo(IDesignerDataModelSpecifier dmSpec);

}
