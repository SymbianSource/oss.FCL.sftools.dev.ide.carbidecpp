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
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewListener;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ITranslationUnitParser;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Base implementation of a view
 */
public abstract class ViewBase<Model extends IOwnedModel> implements IView<Model> {
	protected ModelBase model;

	protected IMessage[] messages;

	protected IViewConfiguration viewConfiguration;

	protected List<IViewListener> listeners;

	protected boolean outOfSync;

	protected ITranslationUnitParser parser;

	protected IPath[] referencedPaths;

	// last value passed to #updateCurrentDirectory()
	protected IPath lastDirectoryPath;

	protected IPath currentDirectory;

	protected IPath projectPath;

	protected boolean debug;

	private int slashBackCount;

	private int slashFwdCount;


	public ViewBase(ModelBase model, ITranslationUnitParser parser,
			IViewConfiguration viewConfiguration) {
		Check.checkArg(model);

		this.parser = parser;
		this.model = model;
		this.viewConfiguration = viewConfiguration;
		this.listeners = new LinkedList<IViewListener>();
	}

	/**
	 * Reparse the preprocessor TU.
	 * <p>
	 * Clients must:
	 * <p>
	 * <li>set the TU
	 * <li>set the ppTu
	 * @param documentMap if non-null, a set of existing documents that overrides any that would
	 * otherwise be used while parsing (may be incomplete) 
	 * @return the (new) map of full path to document detected on parse
	 */
	abstract protected Map<IPath, IDocument> internalReparse(Map<IPath, IDocument> documentMap);

	/**
	 * Reparse the preprocessor TU using translation units and documents from the translation unit provider.
	 */
	protected void reparse(boolean useLoadedDocuments) {
		synchronized (model) {
			Map<IPath, IDocument> documentMap;
			
			slashFwdCount = slashBackCount = 0;

			if (useLoadedDocuments) {
				Map<IPath, IDocument> existingDocumentMap = model.getDocumentMap();
				documentMap = internalReparse(existingDocumentMap);
			} else {
				documentMap = internalReparse(null);
			}

			model.mergeDocumentMap(documentMap);
		}

		this.messages = null;
		this.referencedPaths = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#dispose()
	 */
	public void dispose() {
		listeners.clear();
		model.viewDisposed(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getModel()
	 */
	public Model getModel() {
		return (Model) model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getViewConfiguration()
	 */
	public IViewConfiguration getViewConfiguration() {
		return viewConfiguration;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getMessages()
	 */
	public IMessage[] getMessages() {
		if (messages == null) {
			final List<IMessage> messageList = new ArrayList<IMessage>();
			addViewSpecificMessages(messageList);
			messages = (IMessage[]) messageList.toArray(new IMessage[messageList.size()]);
		}		
		return messages;
	}

	/**
	 * Add messages to the list specific to issues encountered while parsing the filtered TU.
	 * @param messageList
	 */
	abstract protected void addViewSpecificMessages(List<IMessage> messageList);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getReferencedFiles()
	 */
	abstract public IPath[] getReferencedFiles();

	/**
	 * Tell whether the view contains uncommitted changes.
	 * 
	 * @return true: changes present
	 */
	protected abstract boolean internalHasChanges();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#hasChanges()
	 */
	public synchronized boolean hasChanges() {
		return internalHasChanges();
	}

	/**
	 * Revert the changes stored in the view. This is called from
	 * {@link #revert()} and
	 * {@link IModel#createView(IViewConfiguration) }. The DOM will be
	 * reparsed automatically.
	 */
	protected abstract void internalRevertChanges();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#revert()
	 */
	public synchronized void revert() {
		doRevert(false);
	}
	
	protected void doRevert(boolean useLoadedDocuments) {
		synchronized (model) {
			if (viewConfiguration != null)
				projectPath = viewConfiguration.getViewParserConfiguration().getProjectLocation();

			reparse(useLoadedDocuments);

			lastDirectoryPath = null;
			updateCurrentDirectory(getModel().getPath());
			
			internalRevertChanges();
			
			outOfSync = false;
	
			fireChanged();
		}
	}

	/**
	 * Implement to handle the commit operation, which calls #internalApplyChanges().
	 * Called after changes have been detected, with the model synchronized.
	 */
	protected abstract void internalCommit();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#commit()
	 */
	public synchronized void commit() throws IllegalStateException {
		Map<IPath, IDocument> savedDocumentMap = null;
		
		synchronized (model) {
			if (outOfSync) {
				// read javadoc for details
				throw new IllegalStateException("view is out of sync with model"); //$NON-NLS-1$
			}
	
			// shortcut
			if (!hasChanges())
				return;

			internalCommit();

			// save off the committed documents...
			if (model.getModelProvider() != null) {
				savedDocumentMap = copyDocumentMap(model.getDocumentMap());
			}
		}
		
		// outside the synchronization, now save the documents
		// (this prevents deadlock when a model's documents are
		// being saved and another thread tries to commit)
		if (model.getModelProvider() != null) {
			try {
				model.getModelProvider().save(model, savedDocumentMap);
			} catch (CoreException e) {
				EpocEnginePlugin.log(e, "Error saving documents");
			}
		}
	}

	/**
	 * Copy the document map
	 * @param documentMap
	 * @return
	 */
	private Map<IPath, IDocument> copyDocumentMap(Map<IPath, IDocument> documentMap) {
		Map<IPath, IDocument> copy = new HashMap<IPath, IDocument>();
		for (Map.Entry<IPath, IDocument> entry : documentMap.entrySet()) {
			copy.put(entry.getKey(), DocumentFactory.createDocument(entry.getValue().get()));
		}
		return copy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#markUnsynchronized()
	 */
	public void markUnsynchronized() {
		synchronized (model) {
			outOfSync = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#needsSynchonize()
	 */
	public synchronized boolean needsSynchonize() {
		synchronized (model) {
			return outOfSync;
		}
	}

	public abstract boolean merge() ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#forceSynchronized()
	 */
	public synchronized void forceSynchronized() {
		synchronized (model) {
			outOfSync = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#addListener(com.nokia.carbide.cpp.epoc.engine.model.IViewListener)
	 */
	public synchronized void addListener(IViewListener listener) {
		Check.checkArg(listener);
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#removeListener(com.nokia.carbide.cpp.epoc.engine.model.IViewListener)
	 */
	public synchronized void removeListener(IViewListener listener) {
		listeners.remove(listener);
	}

	protected void fireChanged() {
		for (IViewListener listener : listeners) {
			listener.viewChanged(this);
		}
	}

	/**
	 * Convert an IPath to a relative path
	 * 
	 * @param basePath
	 *            path against to get relative path
	 * @param projectPath
	 *            project-relative
	 * @return relative directory (or projectPath if not relative)
	 */
	static public IPath fromProjectToRelativePath(IPath basePath, IPath projectPath) {
		return fromProjectToRelativePath(basePath, projectPath, true);
	}

	/**
	 * Convert an IPath to a relative path
	 * 
	 * @param basePath
	 *            path against to get relative path
	 * @param projectPath
	 *            project-relative
	 * @param allowParent
	 *            allow returning relative to the parent(s) of the basePath? 
	 * @return relative directory (or projectPath if not relative)
	 */
	static public IPath fromProjectToRelativePath(IPath basePath, IPath projectPath, boolean allowParent) {
		if (basePath == null)
			return projectPath;
		if (basePath.getDevice() != null && projectPath.getDevice() != null) {
			if (!basePath.getDevice().equalsIgnoreCase(projectPath.getDevice()))
				return projectPath;
		} else if (basePath.getDevice() != null || projectPath.getDevice() != null) {
			return projectPath;
		}
		int match = FileUtils.matchingFirstSegments(basePath, projectPath);
		IPath partial = projectPath.removeFirstSegments(match).setDevice(null);
		if (match < basePath.segmentCount() && !basePath.toString().equals(".")) { //$NON-NLS-1$
			if (!allowParent)
				return projectPath;
			
			// prepend a ".." to each basePath element to get down to the
			// project
			for (int i = match; i < basePath.segmentCount(); i++)
				partial = new Path("..").append(partial); //$NON-NLS-1$
		} else if (partial.segmentCount() == 0) {
			// don't do this, since paths with '.' are mangled by Path()
			//partial = new Path("."); //$NON-NLS-1$
		}
		return partial.makeRelative();
	}

	/**
	 * Combine a path and a filename.
	 * 
	 * @param path
	 * @param name
	 * @return
	 */
	static public IPath combinePaths(IPath path, String name) {
		if (path == null)
			return FileUtils.createPossiblyRelativePath(name);
		else
			return path.append(FileUtils.createPossiblyRelativePath(name));
	}

	static public boolean equalPath(IPath path, IPath path2) {
		return path.toOSString().equalsIgnoreCase(path2.toOSString());
	}

	/**
	 * Get the project location-relative directory for the given source file.
	 * @param fullPath
	 */
	protected IPath getSourceRelativePath(IPath fullPath, boolean allowParent) {
		return fromProjectToRelativePath(projectPath, fullPath.removeLastSegments(1), allowParent);
	}

	/**
	 * Get project path.
	 * @return IPath
	 */
	public IPath getProjectPath() {
		return projectPath;
	}
	
	/**
	 * Update the current directory to the given path.
	 * @param path
	 */
	public void updateCurrentDirectory(IPath fullPath) {
		if (lastDirectoryPath == null || !lastDirectoryPath.equals(fullPath)) {
			currentDirectory = getSourceRelativePath(fullPath, false);
			lastDirectoryPath = fullPath;
		}
		
	}
	
	/**
	 * Get the current directory while scanning or updating.
	 * @return
	 */
	public IPath getCurrentDirectory() {
		return currentDirectory;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public IPath convertModelToProjectPath(IPath modelPath) {
		if (modelPath == null || isAbsolutePath(modelPath))
			return modelPath;
		return fromProjectToRelativePath(getProjectPath(), model.getPath().removeLastSegments(1).append(modelPath), true);
	}
	
	public IPath convertProjectToModelPath(IPath prjPath) {
		if (prjPath == null || isAbsolutePath(prjPath))
			return prjPath;
		return fromProjectToRelativePath(model.getPath().removeLastSegments(1), getProjectPath().append(prjPath));
	}
	
	/**
	 * Symbian coding styles prefer forward slashes, but we want to adhere
	 * to the existing style as much as possible.  This routine tracks
	 * that information per source line.  We assume that all slashes are
	 * either comments, catenations, or parts of pathnames (it's done this
	 * way rather than peppering the entirely of the parser with calls to this).
	 * @param text the original text of something (node, statement, file, etc.)
	 */
	protected void recordSlashInfo(String text) {
		if (text == null)
			return;
		int idx, len = text.length();
		for (idx = 0; idx < len; idx++) {
			char ch = text.charAt(idx);
			char nextCh = (idx + 1 < len) ? text.charAt(idx + 1) : 0;
			if (ch == '\\') {
				// ignore backslash used in catenated lines
				if (nextCh == '\r' || nextCh == '\n') {
					idx++;
					continue;
				}
				slashBackCount++;
			} else if (ch == '/') {
				// ignore comments
				if (nextCh == '*' || nextCh == '/') {
					idx++;
					continue;
				}
				slashFwdCount++;
			} else if (ch == '*' && nextCh == '/') {
				// ignore comments
				idx++;
			}
		}
		
	}

	/** 
	 * This enum defines what kind of slash format we want for paths 
	 * emitted into text.
	 */
	protected enum SlashFormat {
		/** Always use forward slashes. */
		FORWARD,
		/** Always use backslashes. */
		BACKWARD,
		/** Use the predominant style of slash. */
		PREDOMINANT
	};
	
	/** 
	 * Return the slash format to use. 
	 * Subclasses may override.
	 * @return SlashFormat
	 */
	protected SlashFormat getSlashFormat() {
		return SlashFormat.PREDOMINANT;
	}
	
	/**
	 * Get a path converted to the predominant or expected style.
	 * The handling of the predominant style relies on the view having used 
	 * {@link #recordSlashInfo(String)} when reparsing.
	 * @param IPath
	 * @return String in predominant format
	 */
	public String pathString(IPath path) {
		SlashFormat format = getChosenSlashFormat();
		String string = path.toString();
		if (format == SlashFormat.FORWARD)
			return string;
		else
			return string.replace("/", "\\");
	}

	/**
	 * Get the slash format we're deciding to use, based either on the 
	 * fixed choice from {@link #getSlashFormat()} or the actual predominant format
	 * detected in source.
	 * @return SlashFormat, never {@link SlashFormat#PREDOMINANT}
	 */
	private SlashFormat getChosenSlashFormat() {
		SlashFormat format = getSlashFormat();
		if (format == SlashFormat.PREDOMINANT) {
			format = (slashFwdCount >= slashBackCount) ? SlashFormat.FORWARD : SlashFormat.BACKWARD;
		}
		return format;
	}
	
	/**
	 * Get the path separator for the predominant or expected style.
	 * The handling of the predominant style relies on the view having used 
	 * {@link #recordSlashInfo(String)} when reparsing.
	 * @return '/' or '\\'
	 */
	public char pathSeparator() {
		SlashFormat format = getChosenSlashFormat();
		if (format == SlashFormat.FORWARD)
			return '/';
		else
			return '\\';
	}

	/**
	 * Tell if the path is a Win32 path with drive letter or UNC.
	 * @param path
	 */
	public static boolean isWin32DrivePath(IPath path) {
		return path.getDevice() != null 
			|| (!HostOS.IS_WIN32 && path.segmentCount() > 0 && path.segment(0).matches("[A-Za-z]:"));
	}

	/**
	 * Tell if the path is absolute -- e.g., according to the host or to Windows conventions.
	 * @param path
	 */
	public static boolean isAbsolutePath(IPath path) {
		if (path.isAbsolute())
			return true;
		if (isWin32DrivePath(path))
			return true;
		return false;
	}
}
