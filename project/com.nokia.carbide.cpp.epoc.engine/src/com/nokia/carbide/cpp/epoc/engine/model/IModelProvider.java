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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import java.util.Map;

/**
 * Interface managing shared access to models.
 * <p>
 * The provider gives access to the factory, for creation of private Model
 * instances, and provides shared instances through #findOrCreateModel(). The
 * model provider is responsible for the real contents of the files provided to
 * it through the #createModel(IPath) call, thus clients must use the provider
 * for safe access to the file. Further, the provider exposes only the base
 * interface IModel to limit inadvertent changes to shared models.
 * <p>
 * The domain of files managed is not specified by this interface �- a model
 * provider may work entirely on memory buffers. But if an implementations of
 * the model provider manage resources in the workspace, it is responsible for
 * synchronizing the models with the workspace; e.g. persisting changes to
 * IDocuments to the workspace and listening for changes to such resources
 * outside its own APIs.
 * 
 * 
 */
public interface IModelProvider<Model extends IOwnedModel, SharedModel extends IModel> {
	/**
	 * Create a <b>new</b> model with an <b>empty</b> document, which is slated to live
	 * at the given workspace path.  (See {@link #getSharedModel(IPath)}
	 * for the normal case of loading a model from an existing file.)
	 * <p>
	 * The returned model is not registered and its document is not tracked
	 * until {@link #registerModel(IOwnedModel))} is called.
	 * 
	 * @param workspacePath
	 *            workspace-relative path.
	 * @return model (never null)
	 */
	Model createModel(IPath workspacePath);

	/**
	 * Load the document of a model from persistent storage.  Only allowed on owned models.
	 * This either updates the existing document or replaces a null document
	 * with a new one.
	 * @param model
	 */
	void load(Model model) throws CoreException;

	/**
	 * Save the contents of a model's document(s) to persistent storage.  
	 * Only allowed on owned models.  
	 * @param model
	 * @deprecated since 1.4 (Carbide 2.0), this variant is unsafe, since the model
	 * is no longer locked during a save, so the document map from a model 
	 * might be changing. 
	 */
	void save(Model model) throws CoreException;

	/**
	 * Save the contents of a model's document(s) to persistent storage.  
	 * Only allowed on owned models.
	 * <p>
	 * This variant is called directly from a model when views commit themselves.
	 * The model is no longer locked, but the provided document map is consistent
	 * and may be persisted as a group.  This change was made to avoid deadlocks
	 * when a model save triggers a resource change event which also wants to reread 
	 * the model.
	 * @param model
	 * @param documentMap a copy of the documents generated by the last view commit
	 * @since 1.4 (Carbide 2.0)
	 */
	void save(Model model, Map<IPath, IDocument> documentMap) throws CoreException;


	/**
	 * Make a model visible to clients of the provider and manage its contents.
	 * <p>
	 * Once registered, the model's owned model is counted as a shared model
	 * reference.  The caller should {@link #releaseSharedModel(IModel) release} 
	 * this model eventually.
	 * <p>
	 * If the model has a <code>null</code> IDocument, then it will be loaded
	 * from storage.  Otherwise, it will be persisted immediately from the document
	 * contents.   Then, the model will be parsed.
	 * <p>
	 * The registered model always has a document, though it may be empty if
	 * the load failed.
	 * <p>
	 * Once registered, changes to the model's document are persisted to underlying storage,
	 * and changes to the persisted storage cause reloads of the model.
	 * @param model a newly created model
	 * @throws IllegalArgumentException if already registered
	 * @throws CoreException if problems registering model, loading (when existing), or saving contents
	 */
	void registerModel(Model model) throws CoreException;

	/**
	 * Make a model invisible to clients of the provider and stop managing its
	 * contents.  Any pending changes in the view, if committed, will not be
	 * automatically persisted, and changes to the persisted storage are
	 * no longer tracked.
	 * 
	 * @param model a registered model
	 * @throws IllegalArgumentException if not registered
	 */
	void unregisterModel(Model model);

	/**
	 * Find a registered Model instance for the given path.
	 * <p>
	 * The path is used to identify the model and is a key to its real contents
	 * (usually the given workspace resource, as implied by the name). The
	 * client has limited access to the model.  The real work happens through
	 * IView.
	 * <p>
	 * The model must be {@link #releaseSharedModel(IModel) released} after use.
	 * @param workspacePath 
	 * @return existing model or null
	 */
	SharedModel findSharedModel(IPath workspacePath);

	/**
	 * Load a model for shared use.  This is the normal client use of the provider.
	 * <p>
	 * This finds a registered model instance. If none registered, it loads a new 
	 * model instance and registers it, then loads and parse its contents.
	 * <p>
	 * If the persisted resource does not exist, a <code>null</code> model is returned.
	 * <p>
	 * The client is responsible for {@link #releaseSharedModel(IModel) releasing} 
	 * the model when done.
	 * <p>
	 * The client has limited (read-only) direct access to the model but may
	 * create views and make changes through them.
	 * 
	 * @param workspacePath
	 * @return model or null if no such path registered
	 * @throws org.eclipse.core.runtime.CoreException
	 *             if model cannot be created, e.g. due to a problem loading it
	 *             when it otherwise appears to exist
	 */
	SharedModel getSharedModel(IPath workspacePath) throws CoreException;

	/**
	 * Indicate that the client is no longer using the model, which allows it to
	 * be eventually cleaned up.
	 * <p>
	 * View created on the model must be disposed first.
	 * 
	 * @param model
	 * @throws IllegalStateException
	 *             if all clients have released the model, but views are still
	 *             undisposed (this condition is delayed until all clients have
	 *             released the model, so the last client to release will be
	 *             blamed :( )
	 */
	void releaseSharedModel(SharedModel model) throws IllegalStateException;
	
	/**
	 * Update tracked files using the map of IPath -> IDocument mappings for the model.
	 * These can change as different #includes are detected as a result
	 * of different IViewFilters.  The provider tracks these documents
	 * so that changes to #included files can be saved to disk.
	 */
	void updateModelDocumentMappings(Model model);
}
