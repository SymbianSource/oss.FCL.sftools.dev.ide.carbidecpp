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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import com.nokia.carbide.cpp.epoc.engine.image.IImageSource;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * An operation on the selected items in the image list
 *
 */
public abstract class SelectedImageSourcesOperation extends MultiImageListPageOperation {

	private IImageSource[] imageSources;
	private Map<IImageSource, Object> oldState;
	
	/**
	 * Create an operation with the page's current selected images.
	 * @param label
	 */
	public SelectedImageSourcesOperation(String label, MultiImageListPage page) {
		super(label, page);
		this.imageSources = page.getImageSources();
		this.oldState = new HashMap<IImageSource, Object>();
	}
	/**
	 * Create an operation with the supplied set of selected images.
	 * @param label
	 */
	public SelectedImageSourcesOperation(String label, MultiImageListPage page, IImageSource[] sources) {
		super(label, page);
		this.imageSources = sources;
		this.oldState = new HashMap<IImageSource, Object>();
	}

	public IImageSource[] getImageSources() {
		return imageSources;
	}

	/** 
	 * Tell whether the given image source is affected by the operation.
	 * If so, the client must store state ({@link #saveState(IImageSource, Object)}) 
	 * for use in undo.
	 * If all sources are unaffected, the operation is ignored.
	 * @param imageSource
	 * @return true if operation will make a change
	 * @see #doesAnything()
	 */
	abstract boolean operationAffectsImage(IImageSource imageSource);

	abstract void undoImageOperation(IImageSource imageSource);
	abstract void redoImageOperation(IImageSource imageSource);

	/** Called after changes are made (undo or redo): i.e. refresh the UI */
	abstract void changesMade();
	
	/** 
	 * Save state used to undo the operation for the given image.
	 * Note: state is thrown away if {@link #operationAffectsImage(IImageSource)} returns false. 
	 * @param imageSource
	 * @param state
	 */
	protected void saveState(IImageSource imageSource, Object state) {
		oldState.put(imageSource, state);
	}

	/** Get the state saved for the given image source. */
	protected Object getState(IImageSource imageSource) {
		for (Map.Entry<IImageSource, Object> entry : oldState.entrySet()) {
			if (imageSource == entry.getKey())
				return entry.getValue();
		}
		return null;
	}
	
	@Override
	protected boolean doesAnything() {
		boolean anyWork = false;
		for (IImageSource imageSource : getImageSources()) {
			if (operationAffectsImage(imageSource)) {
				Check.checkState(oldState.containsKey(imageSource));
				anyWork = true;
			} else
				oldState.remove(imageSource);
		}
		return anyWork;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.MultiImageEditorOperation#redo()
	 */
	@Override
	protected void redo() {
		boolean changed = false;
		for (IImageSource imageSource : oldState.keySet()) {
			redoImageOperation(imageSource); 
			changed = true;
		}
		if (changed) {
			changesMade();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.MultiImageEditorOperation#undo()
	 */
	@Override
	protected void undo() {
		boolean changed = false;
		for (IImageSource imageSource : oldState.keySet()) {
			undoImageOperation(imageSource); 
			changed = true;
		}
		if (changed) {
			changesMade();
		}
	}
	
	
}
