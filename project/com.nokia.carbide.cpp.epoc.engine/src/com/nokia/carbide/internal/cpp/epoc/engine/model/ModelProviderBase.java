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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.IModel;
import com.nokia.carbide.cpp.epoc.engine.model.IModelFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IModelProvider;
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Base implementation of a model provider.  Real implementations
 * need to provide a mechanism for retrieving and saving file contents.
 *
 */
public abstract class ModelProviderBase implements IModelProvider {

	// not static or final so it can change during test
	private boolean DUMP = false;
	
	private static int gProviderCounter;
	private int providerId;
	
	private static final long RELEASE_DELAY = 5 * 1000;
	private boolean cacheModels;
	
	// this acts as the lock on all the state managed by this object
	private Object modelLock = new Object();
	
	private Map<IPath, IOwnedModel> models;
	//private Map<IPath, IModelListener> modelListeners;
	private Map<IPath, Integer> modelUseCount;
	private Map<IPath, Long> releasedModelTimes;
	private Map<IPath, IOwnedModel> releasedModels;
	private Map<IPath, Set<IOwnedModel>> modelFileReferences;
	protected IModelFactory modelFactory;

	private long lastFlushTime;

	public ModelProviderBase(IModelFactory modelFactory) {
		providerId = ++gProviderCounter;
		Check.checkArg(modelFactory);
		this.cacheModels = true;
		this.modelFactory = modelFactory;
		this.modelUseCount = new HashMap<IPath, Integer>();
		this.models = new HashMap<IPath, IOwnedModel>();
		//this.modelListeners = new HashMap<IPath, IModelListener>();
		this.releasedModelTimes = new HashMap<IPath, Long>();
		this.releasedModels = new HashMap<IPath, IOwnedModel>();
		this.modelFileReferences = new HashMap<IPath, Set<IOwnedModel>>();
	}
	
	/** 
	 * Get the canonical form of the path -- a full filesystem path.
	 */
	protected abstract IPath getFullPath(IPath path);
	
	/**
	 * Load the text from the given full path, or return null
	 * for a nonexistent file.
	 * @param fullPath full path to resource
	 * @return contents of file
	 * @throws CoreException if the file could not be read,
	 * for reasons other than not existing.
	 */
	protected abstract String loadStorage(IPath fullPath) throws CoreException;

	/**
	 * Save the text to the given full path, creating it and its parents if
	 * necessary.  The implementation may ignore a save if the text does not differ.
	 * @param path full path to resource
	 * @param text the text
	 */
	protected abstract void saveStorage(IPath fullPath, String text) throws CoreException;

	/**
	 * Enable tracking for the storage underlying a registered model's contents.
	 * This may be called multiple times for an already-registered path.
	 * <p>
	 * If changes are detected, {@link #fireStorageChanged()} should be called.
	 * <p>
	 * A call to {@link #stopTrackingStorage(IPath)} is made around approved
	 * saves of documents, so implementors don't have to handle that.
	 */
	protected abstract void startTrackingStorage(IPath fullPath);

	/**
	 * Disable tracking for the storage underlying a registered model's contents.
	 */
	protected abstract void stopTrackingStorage(IPath fullPath);

	/**
	 * Called by implementors to notify that the storage at the given
	 * path has changed.  This is called by the implementation
	 * when changes to tracked storage can be detected with listeners.
	 * @param path
	 * @see #startTrackingStorage(IPath)
	 * @see #stopTrackingStorage(IPath)
	 */
	protected void fireStorageChanged(IPath fullPath) {
		IOwnedModel model = lookupSharedModel(fullPath);
		if (model != null) {
			// the model document itself was modified
			refreshModel(fullPath, model);
		} else {
			// see if a header for one of the models was modified
			Collection<IOwnedModel> referencingModels = modelFileReferences.get(fullPath);
			if (referencingModels != null) {
				for (IOwnedModel refModel : referencingModels) {
					refreshModel(refModel.getPath(), refModel);
				}
			}
		}
	}

	/**
	 * Check the model's file for changes.  This acts as a polling solution for cases
	 * where changes to tracked storage cannot be implemented with listeners. 
	 * <p>
	 * The implementation need only store modification information when files are
	 * loaded and saved and double-check that information here.  This may return false
	 * if the implementation reports changes with {@link #fireStorageChanged(IPath)}.
	 * <p>
	 * The implementation must not call {@link #fireStorageChanged(IPath)}.
	 * @see #startTrackingStorage(IPath)
	 * @see #stopTrackingStorage(IPath)
	 */
	protected abstract boolean hasTrackedStorageChanged(IPath fullPath);

	public void updateModelDocumentMappings(IOwnedModel model) {
		startTrackingModelStorage(model);
	}
	
	/**
	 * Start tracking storage for all the documents owned by the model.
	 * @param model
	 */
	private void startTrackingModelStorage(IOwnedModel model) {
		Map<IPath, IDocument> documentMap = model.getDocumentMap();
		for (IPath path : documentMap.keySet()) {
			Set<IOwnedModel> models = modelFileReferences.get(path);
			if (models == null) {
				models = new HashSet<IOwnedModel>();
				modelFileReferences.put(path, models);
			}
			models.add(model);
			startTrackingStorage(path);
		}
	}

	/**
	 * Start tracking storage for all the documents owned by the model.
	 * @param model
	 */
	private void stopTrackingModelStorage(IOwnedModel model) {
		Map<IPath, IDocument> documentMap = model.getDocumentMap();
		for (IPath path : documentMap.keySet()) {
			Set<IOwnedModel> models = modelFileReferences.get(path);
			if (models != null) {
				models.remove(model);
				if (models.isEmpty()) {
					modelFileReferences.remove(path);
				}
			}
			stopTrackingStorage(path);
		}
	}

	/**
	 * Refresh the model's contents from storage, if changed.
	 * @param fullPath
	 * @param model
	 */
	private void refreshModel(IPath fullPath, IOwnedModel model) {
		try {
			// get updated file
			String text = loadStorage(fullPath);
			
			// make model reload with new contents
			String currentText = model.getDocument().get();
			if (currentText == null || !currentText.equals(text)) {
				model.getDocument().set(text != null ? text : ""); //$NON-NLS-1$
				
				// the change to the document should trigger a reparse
				// and invalidation of any open views 
			}
			
		} catch (CoreException e) {
			EpocEnginePlugin.log(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModelProvider#createModel(org.eclipse.core.runtime.IPath)
	 */
	public IOwnedModel createModel(IPath somePath) {
		IPath fullPath = getFullPath(somePath);
		return modelFactory.createModel(fullPath, null);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModelProvider#load(com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPOwnedModel)
	 */
	public void load(IOwnedModel model) throws CoreException {
		String text = loadStorage(model.getPath());
		IDocument document = model.getDocument() != null ? model.getDocument() : DocumentFactory.createDocument();
		if (text != null)
			document.set(text);
		if (document != model.getDocument()) {
			model.setDocument(document);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModelProvider#save(com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPOwnedModel)
	 */
	public void save(IOwnedModel model) throws CoreException {
		save(model, model.getDocumentMap());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModelProvider#save(com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPOwnedModel)
	 */
	public void save(IOwnedModel model, Map documentMap_) throws CoreException {
		Map<IPath, IDocument> documentMap = documentMap_;
		
		IPath path = model.getPath();
		
		if (models.containsKey(path)) {
			stopTrackingModelStorage(model);
		}
		
		// check for changed documents and save modified files only
		for (Map.Entry<IPath, IDocument> entry : documentMap.entrySet()) {
			saveStorage(entry.getKey(), entry.getValue().get());
		}
		
		if (models.containsKey(path)) {
			startTrackingModelStorage(model);
		}
	}
	
	/**
	 * Register a model whose document has been established.
	 * Called with modelLock held.
	 * @param model
	 * @throws CoreException
	 */
	private void doRegisterModel(IOwnedModel model) throws CoreException {
		IPath path = model.getPath();
		Check.checkArg(!models.containsKey(path));
		if (DUMP) System.out.println(providerId+") " + "Registering "+ path); //$NON-NLS-1$ //$NON-NLS-2$
		if (releasedModels.containsKey(path)) {
			if (DUMP) System.out.println("--> registering released model"); //$NON-NLS-1$
			releasedModels.remove(path);
			releasedModelTimes.remove(path);
		}
		models.put(path, model);
		model.setModelProvider(this);
		
		/*
		IModelListener modelListener = new IModelListener() {
		
			public void modelChanged(IOwnedModel model) {
			}

			public void modelUpdated(IOwnedModel model, IView view) {
				try {
					save(model);
				} catch (CoreException e) {
					EpocEnginePlugin.log(e);
				}
			}
		};
		
		model.addListener(modelListener);
		modelListeners.put(path, modelListener);
		*/
		
		// owner holds one reference
		modelUseCount.put(path, new Integer(1));
		
		// ensure the model is fully set up
		model.parse();
		startTrackingModelStorage(model);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModelProvider#registerModel(SharedModel)
	 */
	public void registerModel(IOwnedModel model) throws CoreException {
		synchronized (modelLock) {
			if (model.getDocument() != null) {
				// create from initial contents
				save(model);
			} else {
				// try to load, or make empty if not existing
				load(model);
			}
	
			doRegisterModel(model);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModelProvider#unregisterModel(SharedModel)
	 */
	public void unregisterModel(IOwnedModel model) {
		IPath path = model.getPath();
		synchronized (modelLock) {
			if (DUMP) System.out.println(providerId+") " + "Unregistering " + path); //$NON-NLS-1$ //$NON-NLS-2$
			Check.checkArg(models.containsKey(path) || releasedModels.containsKey(path));
			//model.removeListener(modelListeners.get(path));
			//modelListeners.remove(path);
			models.remove(path);
			releasedModels.remove(path);
			releasedModelTimes.remove(path);
			model.setModelProvider(null);
		}
		stopTrackingModelStorage(model);
	}

	private IOwnedModel lookupSharedModel(IPath fullPath) {
		IOwnedModel model;
		if (DUMP) System.out.println(providerId+") " + "Finding shared model " + fullPath); //$NON-NLS-1$ //$NON-NLS-2$
		// try to resurrect released model
		model = releasedModels.get(fullPath);
		if (model != null) {
			if (DUMP) System.out.println("--> resurrecting"); //$NON-NLS-1$
			models.put(fullPath, model);
			releasedModels.remove(fullPath);
			releasedModelTimes.remove(fullPath);
		} else {
			if (DUMP) System.out.println("--> looking up"); //$NON-NLS-1$
			model = models.get(fullPath);
		}
		if (model != null) {
			incrementUseCount(fullPath);
			if (DUMP) System.out.println("--> found, refcount = " + testGetUseCount(model)); //$NON-NLS-1$
		}
		return model;
	}
	
	public IModel findSharedModel(IPath somePath) {
		if (somePath == null) {
			return null;
		}
		IPath fullPath = getFullPath(somePath);
		synchronized (modelLock) {
			IOwnedModel model = lookupSharedModel(fullPath);
			if (model != null && hasTrackedStorageChanged(fullPath)) {
				refreshModel(fullPath, model);
			}
			return model;
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModelProvider#getSharedModel(org.eclipse.core.runtime.IPath, com.nokia.carbide.cpp.epoc.engine.model.IModelConfiguration)
	 */
	public IModel getSharedModel(IPath somePath) throws CoreException {
		if (somePath == null) {
			throw new CoreException(Logging.newStatus(EpocEnginePlugin.getDefault(), 
					new NullPointerException("Model path is null... perhaps outside workspace?"))); //$NON-NLS-1$
		}
		IPath fullPath = getFullPath(somePath);
		IOwnedModel model;
		synchronized (modelLock) {
			if (DUMP) System.out.println(providerId+") " + "Get shared " + fullPath); //$NON-NLS-1$ //$NON-NLS-2$
			model = releasedModels.get(fullPath);
			if (model != null) {
				if (DUMP) System.out.println("--> resurrect"); //$NON-NLS-1$
				Check.checkState(modelUseCount.get(fullPath).equals(0));
				models.put(fullPath, model);
				releasedModels.remove(fullPath);
				releasedModelTimes.remove(fullPath);
			} else {
				if (DUMP) System.out.println("--> lookup"); //$NON-NLS-1$
				model = models.get(fullPath);
			}
			if (model == null) {
				if (DUMP) System.out.println("--> load"); //$NON-NLS-1$
				
				// try to load
				String text = loadStorage(fullPath);
				if (text == null)
					return null;
				
				if (DUMP) System.out.print("--> loading model " + fullPath + "... "); //$NON-NLS-1$ //$NON-NLS-2$
				
				model = modelFactory.createModel(fullPath, DocumentFactory.createDocument(text));
				doRegisterModel(model);
				if (DUMP) System.out.println("loaded."); //$NON-NLS-1$
			} else {
				// check for changes under our nose
				if (hasTrackedStorageChanged(fullPath)) {
					refreshModel(fullPath, model);
				}

				// ensure the model is tracked now, if it wasn't before (e.g. because it was not yet in the workspace)
				startTrackingModelStorage(model);

				incrementUseCount(fullPath);
				if (DUMP) System.out.println("--> incr refcount to " + testGetUseCount(model)); //$NON-NLS-1$
			}
		}
		
		// try cleaning up (this could be called as a job too)
		flushReleasedModels();
		
		return model;
	}

	/**
	 * TESTING ONLY.
	 * <p>
	 * @param model
	 * @return
	 */
	public int testGetUseCount(IOwnedModel model) {
		return modelUseCount.get(model.getPath());
	}
	
	/**
	 * Increment use count for the given model.<p>
	 * Called with #modelLock held.
	 * @param modelPath
	 */
	private void incrementUseCount(IPath modelPath) {
		Integer val = modelUseCount.get(modelPath);
		modelUseCount.put(modelPath, new Integer(val.intValue() + 1));
	}

	/**
	 * Decrement use count for the given model.<p>
	 * Called with #modelLock held.
	 * @param modelPath
	 */
	private int decrementUseCount(IPath modelPath) {
		Integer val = modelUseCount.get(modelPath);
		int newValue = val.intValue() - 1;
		Check.checkState(newValue >= 0);
		modelUseCount.put(modelPath, new Integer(newValue));
		return newValue;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModelProvider#releaseSharedModel(SharedModel)
	 */
	public void releaseSharedModel(IModel sharedModel) {
		IOwnedModel model = (IOwnedModel) sharedModel;
		IPath path = model.getPath();
		synchronized (modelLock) {
			if (DUMP) System.out.println(providerId+") " + "Releasing " + path); //$NON-NLS-1$ //$NON-NLS-2$
			Check.checkState(models.containsKey(path));
			int val = decrementUseCount(path);
			if (val == 0) {
				if (sharedModel.getViews().length != 0) {
					incrementUseCount(path);
					throw new IllegalStateException("Trying to release model with views: " + path); //$NON-NLS-1$
				}
				if (DUMP) System.out.println("--> refcount == 0"); //$NON-NLS-1$
				models.remove(path);
				//modelListeners.remove(path);
				if (cacheModels) {
					if (DUMP) System.out.println("--> released"); //$NON-NLS-1$
					// move to released models cache
					releasedModels.put(path, model);
					releasedModelTimes.put(path, System.currentTimeMillis());
				} else {
					if (DUMP) System.out.println("--> disposing"); //$NON-NLS-1$
					model.setModelProvider(null);
					model.dispose();
					modelUseCount.remove(path);
				}
			} else {
				if (DUMP) System.out.println("--> refcount == " + val); //$NON-NLS-1$
			}
		}
	}


	/**
	 * Testing method to clear out the provider's contents. 
	 */
	public void reset() {
		synchronized (modelLock) {
			if (DUMP) System.out.println(providerId+") " + "Reset!"); //$NON-NLS-1$ //$NON-NLS-2$
			models.clear();
			//modelListeners.clear();
			modelUseCount.clear();
			releasedModelTimes.clear();
			releasedModels.clear();
		}
	}

	/**
	 * Actually flush a model.  This is done some time after the last
	 * #release is called.
	 * <p>
	 * Called with #modelLock held.
	 * @param model
	 * @param path
	 */
	private void flushModel(IPath modelPath) {
		if (!releasedModels.containsKey(modelPath)) {
			EpocEnginePlugin.log(new IllegalArgumentException("Flushing unreleased model? " + modelPath)); //$NON-NLS-1$
		}
		try {
			IOwnedModel model = releasedModels.get(modelPath);
			model.setModelProvider(null);
			model.dispose();
		} catch (IllegalStateException e) {
			throw e;
		}
		releasedModels.remove(modelPath);
		releasedModelTimes.remove(modelPath);
		modelUseCount.remove(modelPath);
	}

	/**
	 * Occasionally scan released models and free them,
	 * using a delay to avoid overhead of rescanning popular models.
	 */
	private void flushReleasedModels() {
		long now = System.currentTimeMillis();
		if (lastFlushTime + RELEASE_DELAY > now) {
			return;
		}
		synchronized (modelLock) {
			if (DUMP) System.out.println(providerId+") " + "Flush models... "); //$NON-NLS-1$ //$NON-NLS-2$

			// work on copy so #flushModel can fix up everything 
			// (else we fix up the time map here, which is not symmetric) 
			Map<IPath, Long> timeMap = new HashMap<IPath, Long>(releasedModelTimes);
			for (Iterator<Map.Entry<IPath, Long>> iter = timeMap.entrySet().iterator(); iter.hasNext(); ) {
				Map.Entry<IPath, Long> entry = iter.next();
				if (entry.getValue() + RELEASE_DELAY < now) {
					if (DUMP) System.out.println("Flushing model " + entry.getKey()); //$NON-NLS-1$
					flushModel(entry.getKey());
				}
			}
			if (DUMP) System.out.println("flushed."); //$NON-NLS-1$
		}
		lastFlushTime = now;
	}

	public void cacheModels(boolean flag) {
		this.cacheModels = flag;
	}
	
	public boolean isCachingModels() {
		return cacheModels;
	}
	
}
