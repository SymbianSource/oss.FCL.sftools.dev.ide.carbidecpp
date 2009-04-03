/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.cpp.internal.api.utils.core.*;

/**
 * The base interface for a filtered view onto a model.
 * <p>
 * It allows for transactions of changes to be applied back to the model's DOM.
 * <p>
 * Multiple views may hold changes at the same time (for instance, a dirty
 * editor and a project change listener updating SOURCE statements).
 * <p>
 * The updating model follows the CVS model for handling simultaneous changes:
 * <p>
 * <li>Client makes changes to View A
 * <li>Client makes changes to View B
 * <li>Client commits View A. The #commit() call will synchronize on the IOwnedModel
 * instance and apply changes, updating the DOM and document. Other existing
 * views are out-of-sync (since they reference a stale filtered DOM and may hold
 * changes that no longer apply). Also, due to the model’s document being
 * changed, any other existing views may have unsynchronized cached source
 * ranges into the document. The latter issue is not fatal – any DOM can be
 * rewritten from scratch to recover the “essential” source, but obviously, we
 * want to avoid this where possible.
 * <li>The model notifies listeners (IModelListener#modelUpdated) that changes
 * have occurred. The views themselves are always listeners and are always
 * notified before client listeners.
 * <li>View A ignores notification its own changes
 * <li>View B, since it has changes, marks itself “out of sync”
 * (#isUnsynchronized()). This disallows any commits.
 * <li>Client for View A ignores listener for changes on its own view, if
 * needed.
 * <li>Client for View B periodically checks its synchronization state, or
 * waits until it wants to commit. If out-of-sync on a commit,
 * IllegalStateException is thrown. Finding it’s out of sync, the client decides
 * whether to #revert() or #merge(). Usually it will #merge() to get an updated
 * view combined with its current uncommitted changes. If its changes don’t
 * merge cleanly, #merge() returns false. The listener may decide to show UI at
 * this point. Only #revert(), a successful #merge(), or a #forceSynchronized()
 * will clear the unsynchronized state and allow further writes.
 * <li>Now both views are sync with the model.
 * 
 */
public interface IView<Model> {
	/**
	 * Get the model we're viewing
	 */
	Model getModel();
	
	/**
	 * Dispose view, removing its listeners and detaching it from model 
	 * and losing any changes.  All views must be disposed before the model
	 * can be disposed.
	 */
	void dispose(); 
	
	/**
	 * Get the configuration used to create the view
	 * @return configuration (never null)
	 */
	IViewConfiguration getViewConfiguration();
	
	/**
	 * Get all messages associated with parsing the filtered TU.  This
	 * includes those from the problem nodes and additional diagnostics.
	 * @return array (never null)
	 */
	IMessage[] getMessages();
	
	/**
	 * Get the array of filesystem paths for files read 
	 * while creating the filtered translation units
	 * @return list of paths (never null)
	 */
	IPath[] getReferencedFiles();

	/**
	 * Reverts changes to mirror model contents and marks the view synchronized.
	 *
	 */
	void revert();
	
	/**
	 * Commits changes to the underlying translation unit (visible from IOwnedModel#getTranslationUnit() and IView#getFilteredTranslationUnit())
	 * <p>
	 * When a view is committed, changes may or may not be made to the owning model's document.  A
	 * document change event will be sent if the contents change, and that event will cause the model
	 * to mark any open views (except this one) unsynchronized.
	 * <p>
	 * During a commit, the contents of the view may be recreated.  Any references to objects provided
	 * by the view should be considered stale.
	 * <p>
	 * A commit can throw IllegalStateException if it is unsynchronized.  The code for a commit should
	 * be structured like this:
	 <pre>
		// commit changes
		while (true) {
			try { 
				view.commit();
				break;
			} catch (IllegalStateException e) {  // happens when third party has committed in-between
				// usually UI around this...
				if (!view.merge()) {
					// worst case (could try view.forceSynchronized() too)
					view.revert();
				}
				// further failures should not occur, but loop just in case the
				// UI above took long enough for someone else to sneak in
			}
		}
	 </pre>
	 * @throws IllegalStateException if out of sync 
	 * @see #needsSynchonize()
	 * @see #merge()
	 * @see #revert() 
	 */
	void commit() throws IllegalStateException;

	/**
	 * Mark a view out-of-sync due to changes in the model.
	 * @see #needsSynchonize()
	 */
	void markUnsynchronized(); 

	/**
	 * Tell whether the view needs to be synchronized (merged) with the model.
	 * <p>  
	 * A newly created view never needs synchronization, and a view 
	 * with changes does not necessarily need synchronization.
	 * <p>  
	 * Only when the model is changed underneath (e.g. other views commit, 
	 * the DOM is changed directly, or the document is changed) and 
	 * the view has uncommited changes and wants to commit is 
	 * synchronization needed.
	 * @return true: view needs to be reverted or forced synchronized
	 */
	boolean needsSynchonize();
	
	/**
	 * Merges uncommitted changes with the current model.  <p>
	 * Returns true if pending changes merge cleanly, with view 
	 * left in synchronized state.  
	 * Returns false otherwise, leaving view in a unsynchronized state, 
	 * possibly with partial updates.
	 * @return merge succeeded
	 * @see #needsSynchonize()
	 */
	boolean merge();
	
	/**
	 * Forces view to be marked synchronized, a last resort if #merge() 
	 * returns false and you don’t want to #revert().
	 */
	void forceSynchronized(); 
	
	/**
	 * Add listener, ignore duplicates
	 *
	 */
	void addListener(IViewListener listener); 
	
	/**
	 * Remove listener, ignore if not present 
	 */
	void removeListener(IViewListener listener);

	/**
	 * Get the project root from the view configuration.
	 * @return IPath absolute path to project root
	 */
	IPath getProjectPath();

	/**
	 * Enable debugging, if supported.  Dumps information to System.out.
	 * @param debug true to debug, false to stop  
	 */
	void setDebug(boolean debug);
	
	/**
	 * Convert a project-relative path to an model-relative path.<p>
	 * Null or full paths returned as identity.
	 * @param prjPath project-relative path (or null or full path)
	 * @return path adjusted to be model-relative (except for null or full path which are returned the same)
	 */
	IPath convertProjectToModelPath(IPath prjPath);
	
	/**
	 * Convert a model-relative path to an project-relative path.<p>
	 * Null or full paths returned as identity.
	 * @param modelPath model-relative path (or null or full path)
	 * @return path adjusted to be project-relative (except for null or full path which are returned the same)
	 */
	IPath convertModelToProjectPath(IPath prjPath);
	
	/**
	 * Get a copy of cacheable data for the view.
	 * @return instance of IData containing current data in the view, or <code>null</code> if such data cannot be created
	 */
	IData<IView<Model>> getData();
}
