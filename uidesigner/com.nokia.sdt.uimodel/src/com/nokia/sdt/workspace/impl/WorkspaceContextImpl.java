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
package com.nokia.sdt.workspace.impl;

import com.nokia.sdt.uimodel.Messages;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.sdt.utils.MessageReporting;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class WorkspaceContextImpl extends WorkspaceContext implements IResourceChangeListener {
	/**
	 * Name of the component provider extension point
	 */
	public static final String PROVIDER_EXTENSION = "projectContextProvider"; //$NON-NLS-1$
	
	private ArrayList projects = new ArrayList();
	private IProjectContextProvider providers[];
	private ArrayList listeners = new ArrayList();

	private WorkspaceMessageHandler workspaceMessageListener;

	public WorkspaceContextImpl() {
		Check.checkState(instance == null);
	}

    protected void init() {
        loadExtensions();
        registerWorkspaceListener();
        populateInitialProjects();
        
        if (workspaceMessageListener == null) {
    		workspaceMessageListener = new WorkspaceMessageHandler();
        	MessageReporting.addListener(workspaceMessageListener);
        } else {
            workspaceMessageListener.reset();
        }
        
  //      UnloadCachedModelsJob.initRecurringJob();
    }
    
	public synchronized IProjectContext[] getProjects() {
		IProjectContext[] result = null;
		if (projects.size() > 0) {
			result = (IProjectContext[])projects.toArray(
					new IProjectContext[projects.size()]);
		}
		return result;
	}
	
	public synchronized IProjectContext getContextForProject(IProject project) {
		return findOrCreateContextForProject(project, true);
	}
	
	public synchronized IProjectContext getExistingContextForProject(IProject project) {
		return findOrCreateContextForProject(project, false);
	}

	@Override
	public void projectChanged(IProject project) {
		handleChangedProject(project, null);
	}

	private synchronized void populateInitialProjects() {
		 IWorkspace workspace = ResourcesPlugin.getWorkspace();
		 IWorkspaceRoot root = workspace.getRoot();
		 IProject[] wksProjects = root.getProjects();
		 if (wksProjects != null) {
			 for (int i = 0; i < wksProjects.length; i++) {
				IProject project = wksProjects[i];
				findOrCreateContextForProject(project, true);
			}
		 }
	}
	
	private IProjectContext findOrCreateContextForProject(IProject project, 
				boolean create)  {
		IProjectContext result = null;
		for (Iterator iter = projects.iterator(); iter.hasNext();) {
			IProjectContext currContext = (IProjectContext) iter.next();
			if (currContext.getProject() == project) {
				result = currContext;
				break;
			}
		}
		if (result == null && create && project.isOpen()) {
			for (int i = 0; i < providers.length; i++) {
				IProjectContextProvider currProvider = providers[i];
				result = currProvider.createContextForProject(this, project);
				if (result != null) {
					projects.add(result);
					break;
				}
			}
		}
		return result;
	}
	
	@Override
	public IProjectContext discoverProjectContext(Object object) {
		IProjectContext result = null;
		if (object instanceof IProjectContext) {
			result = (IProjectContext) object;
		} else {
			IResource resource = (IResource) ObjectUtils.getAdapter(object, IResource.class);
			if (resource != null) {
				IProject project = resource.getProject();
				if (project != null) {
					result = getContextForProject(project);
				}
			}
		}
		return result;
	}

	private void registerWorkspaceListener() {
	    IWorkspace workspace = ResourcesPlugin.getWorkspace();
	    int eventMask = IResourceChangeEvent.POST_CHANGE | 
	    				IResourceChangeEvent.PRE_CLOSE;
	    workspace.addResourceChangeListener(this, eventMask);
	}
	
	public void resourceChanged(IResourceChangeEvent event) {

		IResource resource = event.getResource();
	/*
		Object source = event.getSource();
		
		IResourceDelta delta = event.getDelta();
		int deltaKind = 0;
		IResource deltaResource = null;
		if (delta != null) {
			deltaResource = delta.getResource();
			deltaKind = delta.getKind();
			deltaResource = delta.getResource();
		}
	*/	
		int eventType = event.getType();
		switch (eventType) {
		case IResourceChangeEvent.PRE_DELETE:
			if (resource instanceof IProject)
				projectClosingOrDeleted((IProject)resource);
			break;
		case IResourceChangeEvent.POST_CHANGE:
			processResourceDelta(event.getDelta());
			break;
		case IResourceChangeEvent.PRE_CLOSE:
			if (resource instanceof IProject)
				projectClosingOrDeleted((IProject)resource);
			break;
		}
	}
	
	private void processResourceDelta(IResourceDelta delta) {
		IResource deltaResource = delta.getResource();
		
		// dumpDebugDelta(delta);
		
		if (deltaResource instanceof IWorkspaceRoot) {
			IResourceDelta[] affectedChildren = delta.getAffectedChildren();
			if (affectedChildren != null) {
				for (int i = 0; i < affectedChildren.length; i++) {
					processResourceDelta(affectedChildren[i]);
				}
			}
		}
		else if (deltaResource instanceof IProject) {
			IProject project = (IProject) deltaResource;
			int kind = delta.getKind();
			int flags = delta.getFlags();
			if (kind == IResourceDelta.CHANGED ||
				kind == IResourceDelta.ADDED) {
				if (project.isOpen() && flags != IResourceDelta.DESCRIPTION) {
					handleChangedProject(project, delta);
				}
			}
			else if ((kind & IResourceDelta.REMOVED)!= 0) {
				projectClosingOrDeleted(project);
			}
		}
	}
	
	/* Debug code to dump resource delta info. The toDebugString call 
	 * relies on an Eclipse internal class, so only uncomment for debugging
	 */
/*
	private void debugDumpDelta(IResourceDelta delta) {
		IResourceDeltaVisitor rdv = new IResourceDeltaVisitor() {
			int x = 1;
			public boolean visit(IResourceDelta delta) throws CoreException {
				System.out.println();
				System.out.println("delta "+x++);
				int kind = delta.getKind();
				int flags = delta.getFlags();
				IPath fullPath = delta.getFullPath();
				IResource resource = delta.getResource();
				IPath movedFromPath = delta.getMovedFromPath();
				System.out.println(resource.toString());
				System.out.println("kind:"+ Integer.toHexString(kind));
				System.out.println("flags:"+ Integer.toHexString(flags));
				if (fullPath != null) System.out.println("full:"+fullPath.toString());
				if (movedFromPath != null) System.out.println("move from:" +movedFromPath.toString());
				ResourceDelta rd = (ResourceDelta) delta;
				System.out.println(rd.toDebugString());
				System.out.println("---");
				return true;
			}
			
		};
		try {
			delta.accept(rdv);
		}
		catch (CoreException x) {
			System.out.println(x);
		}
	}
*/
	
		// project has been created, opened, or contents changed
	private void handleChangedProject(IProject project, IResourceDelta delta) {
		IProjectContext context = null;
		boolean changed = false;
		boolean added = false;
		
		synchronized(this) {
			context = findOrCreateContextForProject(project, false);
			if (context != null) {
				changed = context.refresh(delta);
			}
			else {
				context = findOrCreateContextForProject(project, true);
				added = true;
			}
		}
		
		if ((context != null) && (changed || added)) {
			IWorkspaceContextListener[] l = listeners();
			if (l != null) {
				for (int i = 0; i < l.length; i++) {
					IWorkspaceContextListener listener = l[i];
					if (added)
						listener.projectContextAdded(context);
					else
						listener.projectContextChanged(context);
				}
			}
		}
	}
	
	private void projectClosingOrDeleted(IProject project) {
		IProjectContext context = null;
		synchronized(this) {
			context = findOrCreateContextForProject(project, false);
			if (context != null) {
				projects.remove(context);
			}
		}
		
		if (context != null) {
			IWorkspaceContextListener[] l = listeners();
			if (l != null) {
				for (int i = 0; i < l.length; i++) {
					IWorkspaceContextListener listener = l[i];
					listener.projectContextRemoved(context);
				}
			}
			context.dispose();
		}
	}

	private void loadExtensions() {
		
		// Get implementors of the projectContextProvider extension point
		IExtensionRegistry er = Platform.getExtensionRegistry();
		IExtensionPoint ep = er.getExtensionPoint(
				UIModelPlugin.PLUGIN_ID, PROVIDER_EXTENSION);
		
		// Iterate over all providers and instantiate all
		ArrayList providerList = new ArrayList();
		IExtension[] extensions = ep.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] ces = extension.getConfigurationElements();
			if (ces != null && ces.length >= 1) {
				IConfigurationElement providerElement = ces[0];
				IProjectContextProvider provider;
				try {
					provider = (IProjectContextProvider) providerElement.createExecutableExtension("class");
					providerList.add(provider);
				} catch (CoreException e) {
					Logging.log(UIModelPlugin.getDefault(), 
							Logging.newStatus(UIModelPlugin.getDefault(),
									IStatus.ERROR,
									MessageFormat.format(Messages.getString("ComponentSystem.2"),
											new Object[] { providerElement.getAttribute("class") })
							));
				}
			}
		}
		providers = (IProjectContextProvider[]) providerList.toArray(
				new IProjectContextProvider[providerList.size()]);
	}

	public synchronized IDesignerDataModelSpecifier findSpecifierForResource(IResource resource) {
		Check.checkArg(resource);
		IDesignerDataModelSpecifier result = null;
		IProject project = resource.getProject();
		if (project != null) {
			IProjectContext context = findOrCreateContextForProject(project, true);
			if (context != null) {
				IPath relPath = resource.getProjectRelativePath();
				if (relPath != null) {
					result = context.findSpecifierForPath(relPath);
				}
			}
		}
		return result;
	}
	
	private synchronized IWorkspaceContextListener[] listeners() {
		IWorkspaceContextListener[] result = null;
		if (listeners.size() > 0) {
			result = (IWorkspaceContextListener[]) listeners.toArray(
							new IWorkspaceContextListener[listeners.size()]);
		}
		return result;
	}

	public synchronized void addListener(IWorkspaceContextListener listener) {
		ArrayList newList = new ArrayList(listeners);
		newList.add(listener);
		listeners = newList;
	}

	public synchronized void removeListener(IWorkspaceContextListener listener) {
		ArrayList newList = new ArrayList(listeners);
		newList.remove(listener);
		listeners = newList;
	}
	
	public void unloadCachedModels() {
		IProjectContext projects[] = getProjects();
		if (projects != null) {
			for (int i = 0; i < projects.length; i++) {
				projects[i].unloadCachedModels();
			}
		}
		workspaceMessageListener.reset();
	}
	
	@Override
	public void scheduleUnloadJob() {
		UnloadCachedModelsJob job = new UnloadCachedModelsJob(false);
		job.schedule(UnloadCachedModelsJob.JOB_DELAY);
	}

}
