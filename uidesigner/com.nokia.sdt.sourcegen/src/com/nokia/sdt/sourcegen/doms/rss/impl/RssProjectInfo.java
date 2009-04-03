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
package com.nokia.sdt.sourcegen.doms.rss.impl;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.cpp.internal.api.utils.core.TrackedResource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.doms.rss.*;
import com.nokia.sdt.symbian.ISymbianNameGenerator;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.workspace.ISymbianDataModelSpecifier;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.resources.IProject;

import java.util.*;

/**
 * 
 *
 */
public class RssProjectInfo implements IRssProjectInfo, IDisposable {

	private IRssRootModelProxy rootProxy;
	private List<IRssModelProxy> viewProxies;
	private TrackedResource trackedProject;
	private ISourceGenProvider provider;
	
	public RssProjectInfo(ISourceGenProvider provider, IProject project) {
		this.provider = provider;
		this.viewProxies = new ArrayList<IRssModelProxy>();
		this.trackedProject = project != null ? new TrackedResource(project) : null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IDisposable#dispose()
	 */
	public void dispose() {
		if (trackedProject != null) {
			trackedProject.dispose();
			trackedProject = null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectInfo#getProject()
	 */
	public IProject getProject() {
		return trackedProject != null ? trackedProject.getProject() : null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectInfo#getRootModelProxy()
	 */
	public IRssRootModelProxy getRootModelProxy() {
		return rootProxy;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectInfo#getViewModelProxies()
	 */
	public IRssModelProxy[] getViewModelProxies() {
		return (IRssModelProxy[]) viewProxies.toArray(new IRssModelProxy[viewProxies.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectInfo#setRootModelProxy(com.nokia.sdt.sourcegen.doms.rss.IRssRootModelProxy)
	 */
	public void setRootModelProxy(IRssRootModelProxy proxy) {
		Check.checkArg(proxy);
		Check.checkState(rootProxy == null);
		this.rootProxy = proxy;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectInfo#addViewModelProxy(com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy)
	 */
	public void addViewModelProxy(IRssModelProxy proxy) {
		Check.checkArg(proxy);
		Check.checkArg(!viewProxies.contains(proxy));
		for (Iterator iter = viewProxies.iterator(); iter.hasNext();) {
			IRssModelProxy viewProxy = (IRssModelProxy) iter.next();
			Check.checkArg(!FileUtils.getComparablePath(viewProxy.getDesignPath()).equals(
					FileUtils.getComparablePath(proxy.getDesignPath())));
		}
		viewProxies.add(proxy);
	}
	
	private static boolean isRootDataModel(IDesignerDataModelSpecifier dmSpec) {
        if (dmSpec == null)
        	return false;
        ISymbianDataModelSpecifier sms = (ISymbianDataModelSpecifier) dmSpec.getAdapter(ISymbianDataModelSpecifier.class);
        if (sms == null)
        	return false;
        return sms.isRoot();
    }

	public void populateProxies(ISourceGenModelGatherer gatherer, IDesignerDataModel rootModel) {
		IProject project = getProject();
		if (project == null)
			return;
		
		IProjectContext context = WorkspaceContext.getContext().getContextForProject(project);
		if (context == null)
			return;

		// get currently known model specifiers
		List<IDesignerDataModelSpecifier> dmSpecs = gatherer.getGeneratableModelSpecifiers(rootModel);

		// dispose all the cached models
		if (rootProxy != null)
			rootProxy.dispose();
		for (Iterator iter = viewProxies.iterator(); iter.hasNext();) {
			IRssModelProxy viewProxy = (IRssModelProxy) iter.next();
			viewProxy.dispose();
		}

		// get proxies for every currently generatable item
		for (IDesignerDataModelSpecifier dmSpec : dmSpecs)
			registerProxyFor(dmSpec);
		
		// remove obsolete proxies
		if (rootProxy != null && !dmSpecs.contains(rootProxy.getModelSpecifier())) {
			rootProxy.dispose();
			rootProxy = null;
		}
		
		for (Iterator iter = viewProxies.iterator(); iter.hasNext();) {
			IRssModelProxy viewProxy = (IRssModelProxy) iter.next();
			IDesignerDataModelSpecifier viewSpec = viewProxy.getModelSpecifier();
			if (viewSpec == null)
				continue;
			if (!dmSpecs.contains(viewSpec)) {
				viewProxy.dispose();
				iter.remove();
			}
		}
	}

	public IRssModelProxy registerProxyFor(IDesignerDataModelSpecifier dmSpec) {
		Check.checkArg(dmSpec);
		//Check.checkState(getModelProxy(dmSpec) == null);
		INameGenerator nameGenerator = provider.getNameGenerator();
		IRssModelProxy proxy = null;
		if (isRootDataModel(dmSpec)) {
			if (rootProxy == null) {
				// only the root model has a NAME
				UniqueRssNameGenerator generator = new UniqueRssNameGenerator(nameGenerator.getApplicationName());
				IRssRootModelProxy newRootProxy = new RssRootModelProxy(this, dmSpec, nameGenerator.getApplicationName(), (String) generator.next());
				proxy = newRootProxy;
				
				//// we may be running with another copy of the model
				//if (rootProxy != null)
				//	rootProxy = null;
				
				setRootModelProxy(newRootProxy);
			} else {
				rootProxy.setModelSpecifier(dmSpec);
				proxy = rootProxy;
			}
		} else {
			//String mainName = SymbianModelUtils.getModelBaseName(dmSpec);
			//nameGenerator.getModelBaseName(dmSpec)
			String rssName = nameGenerator instanceof ISymbianNameGenerator ?
					((ISymbianNameGenerator) nameGenerator).getRssBaseName(dmSpec) :
						SymbianModelUtils.getModelBaseName(dmSpec);

			boolean found = false;
			for (Iterator iter = viewProxies.iterator(); iter.hasNext();) {
				IRssModelProxy existingProxy = (IRssModelProxy) iter.next();
				if (existingProxy.getModelSpecifier() == dmSpec 
						|| existingProxy.getRssBaseName().equalsIgnoreCase(rssName)) {
					existingProxy.setModelSpecifier(dmSpec);
					existingProxy.setRssBaseName(rssName);
					proxy = existingProxy;
					found = true;
					break;
				}
			}
			if (!found) {
				proxy = new RssModelProxy(this, dmSpec, rssName);
				viewProxies.add(proxy);
			}
		}
		return proxy;
	}

	public IRssModelProxy getModelProxy(IDesignerDataModelSpecifier dmSpec) {
		Check.checkArg(dmSpec);
		if (rootProxy != null && rootProxy.getModelSpecifier() == dmSpec)
			return rootProxy;
		for (Iterator iter = viewProxies.iterator(); iter.hasNext();) {
			IRssModelProxy proxy = (IRssModelProxy) iter.next();
			if (proxy.getModelSpecifier() == dmSpec)
				return proxy;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectInfo#getModelProxy(com.nokia.sdt.datamodel.IDesignerDataModel)
	 */
	public IRssModelProxy getModelProxy(IDesignerDataModel dataModel) {
		Check.checkArg(dataModel);
		IDesignerDataModelSpecifier dmSpec = dataModel.getModelSpecifier();
		if (dmSpec != null) {
			IRssModelProxy sProxy = getModelProxy(dmSpec);
			if (sProxy != null)
				return sProxy;
		}
		
		if (rootProxy.requestDataModel() == dataModel)
			return rootProxy;
		for (Iterator iter = viewProxies.iterator(); iter.hasNext();) {
			IRssModelProxy proxy = (IRssModelProxy) iter.next();
			if (proxy.requestDataModel() == dataModel)
				return proxy;
		}
		return null;
	}


}
