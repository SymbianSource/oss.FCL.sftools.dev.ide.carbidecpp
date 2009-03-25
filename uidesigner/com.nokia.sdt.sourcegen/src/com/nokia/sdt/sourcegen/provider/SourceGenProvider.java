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

package com.nokia.sdt.sourcegen.provider;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.contributions.*;
import com.nokia.sdt.sourcegen.contributions.domains.cpp.CppDomain;
import com.nokia.sdt.sourcegen.core.*;
import com.nokia.sdt.sourcegen.doms.rss.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile;
import com.nokia.sdt.sourcegen.doms.rss.impl.*;
import com.nokia.sdt.symbian.ISymbianNameGenerator;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Display;

import java.io.File;
import java.text.MessageFormat;
import java.util.*;

/**
 * Standard implementation of ISourceGenProvider.
 * 
 *
 */
public class SourceGenProvider implements ISourceGenProvider, IWorkspaceContextListener {

	private static final int JOB_DELAY = 100; // ms

	private static Map<String, Class> nameAlgorithmRegistry = new HashMap<String, Class>();

	private IFileTracker fileTracker;

	private RssProjectInfo projectInfo;

	private String projectName;

	private IProject project;

	private ISourceFormatter sourceFormatter;

	private INameGenerator nameGenerator;

	private IIncludeFileLocator includeHandler;

	private IRssProjectFileManager rssFileManager;

	private int flags = FLAG_ONE_WAY_EXPORT;

	private File baseDir;
    private SourceGenContext srcGenContext;

	private ISourceGenModelGatherer gatherer;

	private List<ISourceGenSession> existingSessions;

	private List<ISourceGenChangeListener> sourceChangeListeners;

	private boolean enableSourceGenListenerFiring;

	private boolean pendingSourceGenListenerFiring;

	private SourceChangedJob sourceChangedJob;

	/**
	 * This empty constructor is needed to instantiate the extension
	 * 
	 */
	public SourceGenProvider() {
		this.rssFileManager = new RssProjectFileManager(this);
        this.srcGenContext = new SourceGenContext(this);
        	
        this.existingSessions = new LinkedList<ISourceGenSession>();
        this.sourceChangeListeners = new ArrayList<ISourceGenChangeListener>();
        this.enableSourceGenListenerFiring = true;

        setBaseDirectory(new File(".")); //$NON-NLS-1$
		setProjectName("UnnamedProject"); //$NON-NLS-1$
		setSourceFormatter(null);
		setNameGenerator(null);
		setIncludeFileLocator(null);
		setFileTracker(null);
		setModelGatherer(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#getProject()
	 */
	public IProject getProject() {
		return project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		// see also #getProjectInfo()
		if (adapter.equals(IRssProjectInfo.class))
			return projectInfo;
		if (adapter.equals(IRssProjectFileManager.class))
			return rssFileManager;
		if (adapter.equals(SourceGenContext.class))
			return srcGenContext;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#setProject(org.eclipse.core.resources.IProject)
	 */
	public void setProject(IProject project) {
		if (this.project != null)
			WorkspaceContext.getContext().removeListener(this);
		this.project = project;
		this.projectInfo = null;
		if (project != null) {
			setBaseDirectory(null);
			setProjectName(project.getName());
			srcGenContext.setProject(project);
			WorkspaceContext.getContext().addListener(this);
		} else {
			srcGenContext.setProject(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#getProjectName()
	 */
	public String getProjectName() {
		return projectName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#setProjectName(java.lang.String)
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#setBaseDirectory(java.io.File)
	 */
	public void setBaseDirectory(File baseDir) {
		if (baseDir == null)
			if (project == null || project.getLocation() == null)
				this.baseDir = new File("."); //$NON-NLS-1$
			else
				this.baseDir = project.getLocation().toFile().getAbsoluteFile();
		else
			this.baseDir = baseDir.getAbsoluteFile();
		
		if (project == null)
			srcGenContext.setBaseDir(baseDir);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#getBaseDirectory()
	 */
	public File getBaseDirectory() {
		return baseDir;
	}

	/**
	 * Get the project info if needed.
	 * 
	 */
	private void acquireProjectInfo(IDesignerDataModel rootModel) {
		if (projectInfo == null) {
			projectInfo = new RssProjectInfo(this, project);
			projectInfo.populateProxies(gatherer, rootModel);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.utils.IDisposable#dispose()
	 */
	public void dispose() {
		sourceChangeListeners.clear();
		
		if (sourceChangedJob != null)
			sourceChangedJob.cancel();
		
		for (Iterator iter = new ArrayList<ISourceGenSession>(existingSessions).iterator(); iter.hasNext();) {
			ISourceGenSession session = (ISourceGenSession) iter.next();
			session.dispose();
		}
		
		if (includeHandler instanceof IDisposable) {
			((IDisposable) includeHandler).dispose();
		}
		includeHandler = null;
		if (nameGenerator instanceof IDisposable) {
			((IDisposable) nameGenerator).dispose();
		}
		nameGenerator = null;
		if (sourceFormatter instanceof IDisposable) {
			((IDisposable) sourceFormatter).dispose();
		}
		
		if (this.project != null)
			WorkspaceContext.getContext().removeListener(this);

		sourceFormatter = null;

		rssFileManager.reset();
		rssFileManager = null;
		existingSessions = null;
		project = null;
		projectName = null;
		if (projectInfo != null)
			projectInfo.dispose();
		projectInfo = null;
		fileTracker = null;
	}

	public IIncludeFileLocator getIncludeFileLocator() {
		return includeHandler;
	}

	public void setIncludeFileLocator(IIncludeFileLocator includeHandler) {
		if (this.includeHandler instanceof IDisposable && this.includeHandler != includeHandler) {
			((IDisposable) this.includeHandler).dispose();
		}
		if (includeHandler != null)
			this.includeHandler = includeHandler;
		else {
			this.includeHandler = new IIncludeFileLocator() {
				public File findIncludeFile(String file, boolean isUser,
						File currentDir) {
					File f;
					if (currentDir != null) {
						f = new File(currentDir, file);
						if (f.exists())
							return f;
					}
					f = new File(file);
					if (f.exists())
						return f;
					return null;
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see com.nokia.sdt.sourcegen.IIncludeFileLocator#getIncludePaths()
				 */
				public String[] getIncludePaths() {
					return null;
				}
			};
		}
	}

	public INameGenerator getNameGenerator() {
		return nameGenerator;
	}

	public void setNameGenerator(INameGenerator nameGenerator) {
		if (this.nameGenerator instanceof IDisposable && this.nameGenerator != nameGenerator) {
			((IDisposable)this.nameGenerator).dispose();
		}
		if (nameGenerator != null)
			this.nameGenerator = nameGenerator;
		else {
			this.nameGenerator = new ISymbianNameGenerator() {

				public String getApplicationName() {
					return "NoNameGeneratorApplication"; //$NON-NLS-1$
				}

				public String getViewName(EObject root) {
					return "NoNameGeneratorView"; //$NON-NLS-1$
				}

				public String getProjectRelativeDirectory(
						IDesignerDataModel dataModel, String moniker) {
					return "."; //$NON-NLS-1$
				}

				public String getRssBaseName(IDesignerDataModelSpecifier dmSpec) {
					return "NoNameGenerator"; //$NON-NLS-1$
				}
			};
		}
	}

	public ISourceFormatter getSourceFormatter() {
		return sourceFormatter;
	}

	public void setSourceFormatter(ISourceFormatter formatter) {
		if (this.sourceFormatter instanceof IDisposable && this.sourceFormatter != formatter) {
			((IDisposable)this.sourceFormatter).dispose();
		}
		if (formatter != null)
			this.sourceFormatter = formatter;
		else
			this.sourceFormatter = ISourceFormatter.NULL_SOURCE_FORMATTER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenSession#setFileTracker(com.nokia.sdt.utils.IFileTracker)
	 */
	public void setFileTracker(IFileTracker tracker) {
		if (this.fileTracker instanceof IDisposable && this.fileTracker != tracker) {
			((IDisposable) this.fileTracker).dispose();
		}
		if (tracker != null)
			this.fileTracker = tracker;
		else {
			if (Platform.isRunning()) {
				this.fileTracker = new WorkspaceFileTracker();
			} else {
				this.fileTracker = new StandaloneFileTracker();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenSession#getFileTracker()
	 */
	public IFileTracker getFileTracker() {
		return fileTracker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#getFileManager()
	 */
	public IRssProjectFileManager getFileManager() {
		return rssFileManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#createSourceGenSession(com.nokia.sdt.datamodel.IDesignerDataModel)
	 */
	public ISourceGenSession createSourceGenSession(
			IDesignerDataModel dataModel, IDesignerDataModelSpecifier dmSpec) {
		Check.checkArg(dataModel);

		acquireProjectInfo(null);

		IRssModelProxy proxy;
		if (dmSpec == null) {
			proxy = new RssStandaloneModelProxy(projectInfo, dataModel,
					new Path("standaloneModel.nxd"), "standalone", "UNNA"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			projectInfo.setRootModelProxy((IRssRootModelProxy) proxy);
		} else {
			proxy = projectInfo.getModelProxy(dmSpec);
			if (proxy == null) {
				// this can happen in a new project
				proxy = projectInfo.registerProxyFor(dmSpec);
			}
			Check.checkState(proxy != null);
			proxy.setDataModel(dataModel);
		}

		ISourceGenSession session = new SourceGenSession(this, proxy);
		existingSessions.add(session);
		return session;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#removeSourceGenSession(com.nokia.sdt.sourcegen.ISourceGenSession)
	 */
	public void removeSourceGenSession(ISourceGenSession session) {
		Check.checkArg(existingSessions.contains(session));
		existingSessions.remove(session);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#gotoEventBindingSource(com.nokia.sdt.datamodel.adapter.IEventBinding)
	 */
	public SourceLocation lookupEventBindingSource(IEventBinding binding)
			throws CoreException {
		// basic sanity checking
		if (!Platform.isRunning())
			throw new CoreException(Logging.newStatus(SourceGenPlugin
					.getDefault(), IStatus.ERROR, Messages
					.getString("SourceGenProvider.HeadlessEclipse"))); //$NON-NLS-1$
		IComponentInstance instance = ModelUtils.getComponentInstance(binding
				.getOwner());
		if (instance == null) {
			throw new CoreException(Logging.newStatus(SourceGenPlugin
					.getDefault(), IStatus.ERROR, Messages
					.getString("SourceGenProvider.NoComponentInstance"))); //$NON-NLS-1$
		}
		IDesignerDataModel model = instance.getDesignerDataModel();
		Check.checkState(model != null);
		IProjectContext context = model.getProjectContext();
		if (context == null || context.getProject() == null) {
			throw new CoreException(Logging.newStatus(SourceGenPlugin
					.getDefault(), IStatus.ERROR, Messages
					.getString("SourceGenProvider.OutsideWorkspaceError"))); //$NON-NLS-1$
		}

		// now resolve the encoded string into information
		// a particular domain can interpret
		IProject project = context.getProject();
		String symbolInfo = binding.getHandlerSymbolInformation();

		if (symbolInfo == null)
			throw new CoreException(Logging.newStatus(SourceGenPlugin
					.getDefault(), IStatus.ERROR, MessageFormat.format(Messages
					.getString("SourceGenProvider.NoHandlerSymbolInfo"), //$NON-NLS-1$
					new Object[] { binding.getEventDescriptor().getId(), 
						instance.getComponentId() })));

		CoreException genericError = new CoreException(Logging.newStatus(
				SourceGenPlugin.getDefault(), IStatus.ERROR,
				MessageFormat.format(Messages
						.getString("SourceGenProvider.CannotResolveSymbol"), //$NON-NLS-1$
						new Object[] { symbolInfo })));

		// parse into "domain@data"
		int idx = symbolInfo.indexOf('@');
		if (idx < 0)
			throw genericError;

		DomainRegistry domainRegistry = createDomainRegistry();
		String domainName = symbolInfo.substring(0, idx);
		
		IDomain domain = domainRegistry.getDomain(domainName);
		if (domain == null) {
			throw genericError;
		}

		// decode the data into a filepath and an offset
		SourceLocation loc = null;
		try {
			loc = domain.decodeEventHandlerSymbol(symbolInfo);
		} catch (SourceGenException e) {
			throw genericError;
		}

		// get the file in the project
		Check.checkState(loc.filePath != null);
		IResource rsrc = project.findMember(loc.filePath);
		if (!(rsrc instanceof IFile)) {
			throw new CoreException(Logging.newStatus(SourceGenPlugin
					.getDefault(), IStatus.ERROR, MessageFormat.format(Messages
					.getString("SourceGenProvider.CannotLocateSource"), //$NON-NLS-1$
					new Object[] { loc.filePath })));
		}

		loc.file = (IFile) rsrc;
		return loc;
	}

	public static void registerNameAlgorithm(String nameAlg, Class klass) {
		Check.checkArg(nameAlg);
		Check.checkArg(klass);
		nameAlgorithmRegistry.put(nameAlg, klass);
	}

	/**
	 * Create a name algorithm
	 * 
	 * @param nameAlg
	 * @return
	 * @throws CoreException
	 */
	public static INameAlgorithm createNameAlgorithm(String nameAlg)
			throws CoreException {
		// Get implementors of the componentProvider extension point
		IExtensionRegistry er = Platform.getExtensionRegistry();
		if (er != null) {
			IExtensionPoint ep = er
					.getExtensionPoint(INameAlgorithm.NAME_ALGORITHM_EXTENSION_POINT);
			if (ep != null) {

				// Iterate over all providers looking for the requested one
				IExtension[] extensions = ep.getExtensions();
				for (int i = 0; i < extensions.length; i++) {
					IExtension extension = extensions[i];
					IConfigurationElement[] ces = extension
							.getConfigurationElements();
					if (ces != null) {
						for (int j = 0; j < ces.length; j++) {
							IConfigurationElement providerElement = ces[j];
							String id = providerElement.getAttribute("id"); //$NON-NLS-1$
							if (id != null
									&& id.equals(nameAlg)
									&& providerElement.getAttribute("class") != null) { //$NON-NLS-1$
								return (INameAlgorithm) providerElement
										.createExecutableExtension("class"); //$NON-NLS-1$
							}
						}
					}
				}
			}
		}

		// check hardcoded values
		Class klass = nameAlgorithmRegistry.get(nameAlg);
		if (klass != null) {
			try {
				return (INameAlgorithm) klass.newInstance();
			} catch (Exception e) {
				throw new CoreException(Logging.newStatus(SourceGenPlugin
						.getDefault(), e));
			}
		}

		return null;
	}

	/**
	 * Execute something for each Symbian data model.
	 * 
	 * @param project
	 * @param action
	 */
	public static void runForEachModelSpecifier(IProject project,
			IRunWithModelSpecifiersAction action) {
		Check.checkArg(project);
		WorkspaceContext wsContext = WorkspaceContext.getContext();
		IProjectContext prjContext = wsContext.getContextForProject(project);
		IDesignerDataModelSpecifier[] specs = prjContext.getModelSpecifiers();
		if (specs.length == 0) {
			action.runWhenNoModelSpecifiers();
		} else {
			for (int i = 0; i < specs.length; i++) {
				action.run(specs[i], SymbianModelUtils
						.isRootDataModel(specs[i]));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#setSourceGenFlags(int)
	 */
	public void setSourceGenFlags(int flags) {
		Check.checkArg(flags != 2);
		this.flags = flags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#getSourceGenFlags()
	 */
	public int getSourceGenFlags() {
		return flags;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#isOneWayUpdate()
	 */
	public boolean isOneWayUpdate() {
		return (flags & FLAG_ONE_WAY_UPDATE) == FLAG_ONE_WAY_UPDATE;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#isTwoWayImport()
	 */
	public boolean isTwoWayImport() {
		return (flags & FLAG_TWO_WAY_IMPORT) == FLAG_TWO_WAY_IMPORT;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#isTwoWayExport()
	 */
	public boolean isTwoWayExport() {
		return (flags & ISourceGenProvider.FLAG_TWO_WAY_EXPORT) != 0;
	}
	
	/**
	 * 
	 */
	public IRssProjectInfo getProjectInfo() {
		acquireProjectInfo(null);
		return projectInfo;
	}

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenSession#saveGeneratedSources(com.nokia.sdt.sourcegen.IFileSaver)
     */
    public synchronized void saveGeneratedSources(IProgressMonitor monitor) throws CoreException {
    	IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

			public void run(IProgressMonitor monitor) throws CoreException {
		        Logging.timeStart("saving sources"); //$NON-NLS-1$

		        // don't do another source update job after this
		        if (!pendingSourceGenListenerFiring) {
		        	// reset when the first source update job is run
		        	//ignoreSourceChangesDueToSaving = true;
		        }
		        
		        IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 2);
		        try {
		        	//rssGenerator.writeFiles(provider.getFileTracker(), monitor);
		        	rssFileManager.saveModifiedSources(monitor);
		        	subMonitor.worked(1);
		        	srcGenContext.saveGeneratedSources(monitor);
		        	subMonitor.worked(1);
		        } finally {
		        	subMonitor.done();
		        }
		        Logging.timeEnd();
				
			}
    		
    	};

    	if (Platform.isRunning()) {
    		ResourcesPlugin.getWorkspace().run(runnable, getProject(), IWorkspace.AVOID_UPDATE, monitor);
    	} else {
    		runnable.run(monitor);
    	}
    }

	/**
	 * @param rssFiles
	 * @param generatedSources
	 * @return
	 */
	private List<IPath> combine(Collection<IAstRssSourceFile> rssFiles, List<IPath> generatedSources) {

		if (generatedSources == null)
			generatedSources = new ArrayList<IPath>();
			
		if (rssFiles != null) {
	    	for (Iterator iter = rssFiles.iterator(); iter.hasNext();) {
	    		IAstRssSourceFile file = (IAstRssSourceFile) iter.next();
				File f = file.getSourceFile().getFile();
				IPath path = FileUtils.removePrefixFromPath(new Path(baseDir.getAbsolutePath()), new Path(f.getAbsolutePath()));
				if (path != null)
					generatedSources.add(path);
			}
		}
		
    	return generatedSources;
	}
	
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#getGeneratedSources()
     */
    public List<IPath> getGeneratedSources() {
    	Collection<IAstRssSourceFile> rssFiles = rssFileManager.getGeneratedRssFiles();
        return combine(rssFiles, srcGenContext.getGeneratedSources());
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#revertGeneratedSources()
     */
    public void revertGeneratedSources() throws CoreException {
    	rssFileManager.revert();
    	srcGenContext.revert();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#resetGeneratedSources()
     */
    public void resetGeneratedSources() {
    	rssFileManager.reset();
    	srcGenContext.reset();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#updateProjectInfo(com.nokia.sdt.datamodel.IDesignerDataModel)
     */
    public void updateProjectInfo(final IDesignerDataModel rootModel) {
		// force this to happen on the UI thread
		if (Platform.isRunning() && Display.getCurrent() == null) {
			WorkspaceJob job = new WorkspaceJob(Messages.getString("SourceGenProvider.UpdateProjectInfoJobName")) { //$NON-NLS-1$

				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					Display.getDefault().asyncExec(new Runnable() {

						public void run() {
							safeUpdateProjectInfo(rootModel);
						}
					});
					return Status.OK_STATUS;
				}
			};
			job.setSystem(true);
			job.setUser(false);
			job.schedule();
		} else {
			safeUpdateProjectInfo(rootModel);
		};
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#updateProjectInfo()
     */
    public void updateProjectInfo() {
    	updateProjectInfo(null);
    }
    
    private void safeUpdateProjectInfo(IDesignerDataModel rootModel) {
		if (projectInfo == null)
			acquireProjectInfo(rootModel);
		else
			projectInfo.populateProxies(gatherer, rootModel);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#setModelGatherer(com.nokia.sdt.sourcegen.ISourceGenModelGatherer)
     */
    public void setModelGatherer(ISourceGenModelGatherer gatherer) {
    	if (gatherer != null)
    		this.gatherer = gatherer;
    	else
    		this.gatherer = new ISourceGenModelGatherer() {

    			// default behavior is to take all the specifiers
				public List<IDesignerDataModelSpecifier> getGeneratableModelSpecifiers(IDesignerDataModel rootModel) {
					IProjectContext context = null;
					if (project != null)
						context = WorkspaceContext.getContext().getContextForProject(project);
					if (context != null)
						return Arrays.asList(context.getModelSpecifiers());
					return Collections.EMPTY_LIST;
				}
    	};
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#isGeneratedFile(org.eclipse.core.runtime.IPath)
     */
    public boolean isGeneratedTwoWayFile(IPath path) {
    	File file = new File(baseDir, path.toString());
    	synchronized (rssFileManager) {
	    	ISourceFile sf = rssFileManager.findSourceFile(file);
	    	if (sf == null)
	    		return false;
	    	//IAstRssSourceFile rssFile = rssFileManager.findRssFile(sf);
	    	//return rssFile != null;
	    	return true;
    	}
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#updateSourceState()
     */
    public IStatus updateSourceState(IProgressMonitor monitor, boolean force) {
    	// maybe we're disposed (could happen if project deleted before job runs)
    	if (project == null)
    		return null;
    	
    	IStatus result = null;
    	monitor.beginTask(Messages.getString("SourceGenProvider.SynchronizingStatus"), sourceChangeListeners.size() + 1); //$NON-NLS-1$
    	try {
    		boolean anyChanges = false;
    		if (monitor.isCanceled())
    			return result;
    		
    		if (force)
    			rssFileManager.reset();
    		else
    			anyChanges = rssFileManager.synchronizeSourceFiles();
    		monitor.worked(1);
    		
    		if (anyChanges || force) {
				for (Iterator iterator = sourceChangeListeners.iterator(); iterator.hasNext();) {
					ISourceGenChangeListener listener = (ISourceGenChangeListener) iterator.next();
					IDesignerDataModel model = listener.getListeningDataModel();
					if (model == null || monitor.isCanceled() 
							|| (model.getModelSpecifier().getPrimaryResource() != null
									&& !model.getModelSpecifier().getPrimaryResource().exists()))
						continue;
					model.setSourceGenSession(null);
					ISourceGenSession session = SymbianModelUtils.createSourceGenSession(this, model, model.getModelSpecifier());
					if (session != null) {
						try {
							model.setSourceGenSession(session);
							Command command = session.updateFromSource(monitor);
							if (command.canExecute()) {
								listener.applyModifiedSourceChanges(command);
							}
						} catch (CoreException e) {
							result = Logging.newStatus(SourceGenPlugin.getDefault(), e);
							Logging.log(SourceGenPlugin.getDefault(), result);
						}
					}
					monitor.worked(1);
				}
    		}
    	} finally {
    		monitor.done();
    	}
    	return result;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#addSourceChangeListener(com.nokia.sdt.sourcegen.ISourceGenChangeListener)
     */
    public void addSourceChangeListener(ISourceGenChangeListener listener) {
    	if (sourceChangeListeners.contains(listener))
    		return;
    	sourceChangeListeners.add(listener);
    		
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#removeSourceChangeListener(com.nokia.sdt.sourcegen.ISourceGenChangeListener)
     */
    public void removeSourceChangeListener(ISourceGenChangeListener listener) {
    	sourceChangeListeners.remove(listener);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#enableSourceGenChangedListeners(boolean)
     */
    public boolean enableSourceGenChangedListeners(boolean flag) {
    	boolean oldflag = this.enableSourceGenListenerFiring;
    	this.enableSourceGenListenerFiring = flag;
    	
    	if (flag) {
	    	// do any pending work
	    	flag &= pendingSourceGenListenerFiring;
	    	
	    	pendingSourceGenListenerFiring = false;
	    	
	    	if (flag) {
	    		fireSourceChanged(false);
	    	}
    	} else {
    		// halt or wait for any scheduled job to complete
    		if (sourceChangedJob != null) {
    			sourceChangedJob.cancel();
    			// we can't join since this runs in the UI thread, but if it's
    			// not running now, it won't start, either.
				sourceChangedJob = null;
    		}
    	}
    	return oldflag;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenProvider#fireSourceChanged()
     */
    public void fireSourceChanged(boolean force) {
    	//if (!force && ignoreSourceChangesDueToSaving)
    	//	return;
    	
    	if (!enableSourceGenListenerFiring) {
    		pendingSourceGenListenerFiring = true;
    		return;
    	}
    	
    	// don't schedule more than one job at a time
    	if (sourceChangedJob == null 
    			|| sourceChangedJob.getResult() != null 
    			|| sourceChangedJob.getState() == Job.NONE) {
    		sourceChangedJob = new SourceChangedJob(this, force);
    		sourceChangedJob.schedule(JOB_DELAY);
    	} else if (sourceChangedJob != null && force) {
    		sourceChangedJob.setForce(force);
    	}
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.workspace.IWorkspaceContextListener#projectContextAdded(com.nokia.sdt.workspace.IProjectContext)
	 */
	public void projectContextAdded(IProjectContext projectContext) {
		updateProjectInfo();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.workspace.IWorkspaceContextListener#projectContextChanged(com.nokia.sdt.workspace.IProjectContext)
	 */
	public void projectContextChanged(IProjectContext projectContext) {
		updateProjectInfo();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.workspace.IWorkspaceContextListener#projectContextRemoved(com.nokia.sdt.workspace.IProjectContext)
	 */
	public void projectContextRemoved(IProjectContext projectContext) {
		updateProjectInfo();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#updateDesignSpecifierInfo(com.nokia.sdt.workspace.IDesignerDataModelSpecifier)
	 */
	public void updateDesignSpecifierInfo(IDesignerDataModelSpecifier dmSpec) {
		// this happens after the spec has been renamed
		if (projectInfo != null) {
			IRssModelProxy proxy = projectInfo.registerProxyFor(dmSpec);
			rssFileManager.registerProxySourceFile(proxy, null);
		}
	}
    
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenProvider#createDomainRegistry()
	 */
	public DomainRegistry createDomainRegistry() {
		DomainRegistry registry = new DomainRegistry();
		registry.registerDomain("cpp", new CppDomain(
				project,
				getSourceFormatter(),
				srcGenContext
		));
		return registry;
	}
}
