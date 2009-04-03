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

import com.nokia.carbide.cpp.epoc.engine.model.IModel;
import com.nokia.carbide.cpp.epoc.engine.model.IModelListener;
import com.nokia.carbide.cpp.epoc.engine.model.IModelLoadResults;
import com.nokia.carbide.cpp.epoc.engine.model.IModelProvider;
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.rewriter.DocumentUpdater;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ProblemVisitor;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Base model implementation
 *
 */
public abstract class ModelBase<View> implements IOwnedModel<View> {

	private IPath fullPath;
	private Map<IPath, IDocument> documents;
	private Map<IDocument, IDocumentListener> documentListeners;
	private IASTTranslationUnit tu;
	private List<IView> views;
	private List<IModelListener> listeners;
	private TranslationUnitListener tuListener;
	private IModelProvider<IOwnedModel<View>, IModel<View>> provider;

	class ModelDocumentListener implements IDocumentListener {

		/* (non-Javadoc)
		 * @see org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent)
		 */
		public void documentAboutToBeChanged(DocumentEvent event) {
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
		 */
		public void documentChanged(DocumentEvent event) {
			// reparse the model
			parse();
		}
		
	}
	
	public ModelBase(IPath path, IDocument document) {
		Check.checkArg(path);
		this.fullPath = path;
		this.documents = new HashMap<IPath, IDocument>();
		this.documents.put(path, document);
		this.documentListeners = new HashMap<IDocument, IDocumentListener>();
		IDocumentListener modelDocumentListener = new ModelDocumentListener();
		if (document != null) {
			this.documentListeners.put(document, modelDocumentListener);
			document.addDocumentListener(modelDocumentListener);
		}
		this.views = new LinkedList<IView>();
		this.listeners = new LinkedList<IModelListener>();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel#dispose()
	 */
	public synchronized void dispose() {
		if (views.size() != 0)
			throw new IllegalStateException("cannot dispose model with views active"); //$NON-NLS-1$
		listeners.clear();
		if (tu != null) {
			tu.removeListener(tuListener);
			tuListener = null;
			tu = null;
		}
		for (Map.Entry<IDocument, IDocumentListener> entry : documentListeners.entrySet()) {
			entry.getKey().removeDocumentListener(entry.getValue());
		}
		documentListeners.clear();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel#getDocument()
	 */
	public IDocument getDocument() {
		synchronized (documents) {
			return documents.get(fullPath);
		}
	}

	public void setDocument(IDocument document) {
		setDocument(fullPath, document);
	}
	
	public IDocument getDocument(IPath path) {
		synchronized (documents) {
			return documents.get(path);
		}
	}
	
	public void setDocument(IPath path, IDocument document) {
		synchronized (documents) {
			IDocument existingDocument = documents.get(path);
			IDocumentListener documentListener = null;
			if (existingDocument != null) {
				Check.checkContract(views.size() == 0);

				documentListener = documentListeners.get(existingDocument);
				existingDocument.removeDocumentListener(documentListener);
				// this is a temporary change, apparently (?) (removing it causes a failure in bld.inf view tests?)
				documentListeners.put(existingDocument, null);
			}
			documents.put(path, document);
			if (document != null) {
				if (documentListener == null) {
					documentListener = new ModelDocumentListener();
					documentListeners.put(document, documentListener);
				}
				document.addDocumentListener(documentListener);
			}
		}
	}
	
	public Map<IPath, IDocument> getDocumentMap() {
		synchronized (documents) {
			return new HashMap<IPath, IDocument>(documents);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModel#getPath()
	 */
	public IPath getPath() {
		return fullPath;
	}
	
	public IASTTranslationUnit getTranslationUnit() {
		return tu;
	}

	/**
	 * Reparse the document without disturbing listeners or the TU object.
	 */
	protected Pair<IASTTranslationUnit, IMessage[]> reparse() {
		IDocumentParser parser = ParserFactory.createPreParser();
		IDocument document = null;
		synchronized (documents) {
			document = documents.get(fullPath);
		}
		IASTTranslationUnit tu = parser.parse(fullPath, document);
		
		// find problems
		IMessage[] messages;
		if (parser.hadErrors()) {
			ProblemVisitor visitor = new ProblemVisitor();
			visitor.visit(tu);
			messages = visitor.getMessages();
		} else {
			messages = new IMessage[0];
		}

		return new Pair<IASTTranslationUnit, IMessage[]>(tu, messages);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel#parse()
	 */
	public synchronized IModelLoadResults parse() {
		synchronized (documents) {
			Check.checkState(documents.get(fullPath) != null);
		}
		
		if (this.tu != null) {
			this.tu.removeListener(tuListener);
			this.tu = null;
			this.tuListener = null;
		}
		
		final Pair<IASTTranslationUnit, IMessage[]> info = reparse();
		
		// make it so
		this.tu = info.first;
		this.tuListener = new TranslationUnitListener();
		this.tu.addListener(tuListener);
		
		// model changed
		fireModelChanged();
		
		// sorry views, you're out of date
		for (IView view : views) {
			view.markUnsynchronized();
		}
		
		return new IModelLoadResults() {
			public IMessage[] getMessages() {
				return info.second;
			}
		};
		
	}

	/**
	 * 
	 */
	private void fireModelChanged() {
		for (IModelListener listener : listeners) {
			listener.modelChanged(this);
		}
	}

	/**
	 * Create the view on the model.
	 * @return
	 */
	abstract protected View createView(ModelBase model, IViewConfiguration configuration);
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModel#createView(com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewConfiguration)
	 */
	public synchronized View createView(IViewConfiguration configuration) {
		// must be parsed first
		Check.checkState(tu != null);
		View view = createView(this, configuration);
		((IView)view).revert();
		synchronized (views) {
			views.add((IView) view);
		}
		return view;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModel#getViews()
	 */
	public IView[] getViews() {
		synchronized (views) {
			return (IView[]) views.toArray(new IView[views.size()]);
		}
	}
	
	/** Internal notification that a view was disposed. */
	void viewDisposed(IView view) {
		Check.checkState(views.contains(view));
		views.remove(view);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModel#addListener(com.nokia.carbide.cpp.epoc.engine.model.IModelListener)
	 */
	public void addListener(IModelListener listener) {
		Check.checkArg(listener);
		synchronized (listeners) {
			if (!listeners.contains(listener))
				listeners.add(listener);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModel#removeListener(com.nokia.carbide.cpp.epoc.engine.model.IModelListener)
	 */
	public void removeListener(IModelListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	/**
	 * Internal method used to allow changes to the DOM.
	 * Must be followed by #unlock()
	 */
	public synchronized void lock() {
		tuListener.allowChanges(true);
	}

	/**
	 * Internal method used to release write lock on the DOM.
	 */
	public synchronized void unlock() {
		tuListener.allowChanges(false);
	}

	/**
	 * Internal method used to allow changes to the documents.
	 * Must be followed by #unlockDocuments()
	 */
	public synchronized void lockDocuments() {
		for (Map.Entry<IDocument, IDocumentListener> entry : documentListeners.entrySet()) {
			entry.getKey().removeDocumentListener(entry.getValue());
		}
	}

	/**
	 * Internal method used to release write lock on the documents.
	 */
	public synchronized void unlockDocuments() {
		for (Map.Entry<IDocument, IDocumentListener> entry : documentListeners.entrySet()) {
			entry.getKey().addDocumentListener(entry.getValue());
		}
	}

	/**
	 * Write the document from a changed view.  This is called under
	 * the control of IView#commit().
	 * @param view
	 */
	public synchronized boolean commitDocument(IPreprocessorResults ppResults, Map<File, IASTTranslationUnit> updatedFileMap, IView view) {

		// write file changes
		boolean changed = false;
		lockDocuments();
		try {
			for (Map.Entry<File, IASTTranslationUnit> entry : updatedFileMap.entrySet()) {
				IASTTranslationUnit inclTu = entry.getValue();
				if (inclTu != null) {
					changed |= DocumentUpdater.updateDocuments(ppResults, inclTu);
				}
			}
		} finally {
			unlockDocuments();
		}

		// Even if no changes made, need to reparse the TU to replace
		// any non-PP statements back with PP-statements.
		// Also, when changes are made, we need updated source ranges
		// and updated problems.
		Pair<IASTTranslationUnit, IMessage[]> info = reparse();
		if (info.first != null && tu != null) {
			info.first.getNodes().setParent(null);
			tu.setNodes(info.first.getNodes());
		}
		
		return changed;
	}
	
	
	/**
	 * Mark other views out of sync due to operations that modify the model
	 * (such as a commit on a given view).  The changedView itself might
	 * not actually be changed (firing listeners is in another method).
	 */
	public void desyncOtherViews(IView changedView) {
		// tell other views they're out-of-sync
		IView[] viewsCopy;
		synchronized (views) {
			viewsCopy = (IView[]) views.toArray(new IView[views.size()]);
		}
		for (IView view : viewsCopy) {
			if (view != changedView)
				view.markUnsynchronized();
		}
	}

	/**
	 * Notify that a view changed.
	 * @param changedView the view which changed (indicating actual known changes)
	 * 
	 */
	public void fireViewChanged(IView changedView) {
		// notify listeners
		IModelListener[] listenersCopy;
		synchronized (listeners) {
			listenersCopy = (IModelListener[]) listeners.toArray(new IModelListener[listeners.size()]);
		}
		
		for (IModelListener listener : listenersCopy) {
			listener.modelUpdated(this, changedView);
		}
	}

	/**
	 * Merge the map of full paths to documents from a view
	 * @param documentMap
	 */
	public synchronized void mergeDocumentMap(Map<IPath, IDocument> documentMap) {
		for (Map.Entry<IPath, IDocument> entry : documentMap.entrySet()) {
			documents.put(entry.getKey(), entry.getValue());
		}
		if (provider != null) {
			provider.updateModelDocumentMappings(this);
		}
	}
	

	public void setModelProvider(
			IModelProvider<IOwnedModel<View>, IModel<View>> modelProvider) {
		this.provider = modelProvider;
	}
	
	public IModelProvider<IOwnedModel<View>, IModel<View>> getModelProvider() {
		return provider;
	}

}
