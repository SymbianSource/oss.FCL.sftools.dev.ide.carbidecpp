/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.nokia.carbide.search.system.internal.ui.text;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;

import com.nokia.carbide.search.system.internal.ui.SearchPlugin;
import com.nokia.carbide.search.system.ui.IQueryListener;
import com.nokia.carbide.search.system.ui.ISearchQuery;
import com.nokia.carbide.search.system.ui.NewSearchUI;
import com.nokia.carbide.search.system.ui.text.AbstractTextSearchResult;
import com.nokia.carbide.search.system.ui.text.Match;


public class SearchResultUpdater implements IResourceChangeListener, IQueryListener {
	private AbstractTextSearchResult fResult;

	public SearchResultUpdater(AbstractTextSearchResult result) {
		fResult= result;
		NewSearchUI.addQueryListener(this);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta= event.getDelta();
		if (delta != null)
			handleDelta(delta);
	}

	private void handleDelta(IResourceDelta d) {
		try {
			d.accept(new IResourceDeltaVisitor() {
				public boolean visit(IResourceDelta delta) throws CoreException {
					switch (delta.getKind()) {
						case IResourceDelta.ADDED :
							return false;
						case IResourceDelta.REMOVED :
							IResource res= delta.getResource();
							if (res instanceof IFile) {
								Match[] matches= fResult.getMatches(res);
								fResult.removeMatches(matches);
							}
							break;
						case IResourceDelta.CHANGED :
							// handle changed resource
							break;
					}
					return true;
				}
			});
		} catch (CoreException e) {
			SearchPlugin.log(e);
		}
	}

	public void queryAdded(ISearchQuery query) {
		// don't care
	}

	public void queryRemoved(ISearchQuery query) {
		if (fResult.equals(query.getSearchResult())) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
			NewSearchUI.removeQueryListener(this);
		}
	}
	
	public void queryStarting(ISearchQuery query) {
		// don't care
	}

	public void queryFinished(ISearchQuery query) {
		// don't care
	}
}
