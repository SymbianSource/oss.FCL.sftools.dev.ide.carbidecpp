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
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.IMessageListener;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.contributions.*;
import com.nokia.sdt.sourcegen.core.*;
import com.nokia.sdt.sourcegen.core.ResourceTracker.ResourceInfo;
import com.nokia.sdt.sourcegen.doms.rss.*;
import com.nokia.sdt.symbian.ISymbianSourceGenSession;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.LocalizationFileFormat;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier.IRunWithModelAction;

import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import java.io.File;
import java.util.*;

/**
 * This implements source generation for the data model.  Two-way
 * and one-way modes are brought together here.  
 * 
 *
 */
public class SourceGenSession implements ISymbianSourceGenSession, IMessageListener {
    
    private static final String SOURCEGEN_UPGRADING_LOG_FILE_NAME = "Source_Upgrading_Log.txt";
	private IRssModelGenerator rssGenerator;
	private RssProvider rssProvider;
	private ISourceGenProvider provider;
	private IRssModelProxy proxy;
	private ContributionEngine engine;
    private IVariableProvider varProvider;
	private IDesignerDataModel dataModel;
	private List<IMessage> messages;
	private boolean isUpgrading;
    
    /**
     * Create a source generation session for the given data model.
     * @param generator 
     */
    public SourceGenSession(ISourceGenProvider provider, IRssModelProxy proxy) {
    	Check.checkArg(provider);
    	this.provider = provider;
    	
        Check.checkArg(proxy);
        this.proxy = proxy;
        this.dataModel = proxy.requestDataModel();

        varProvider = new VariableProvider(this, provider.getProjectName());
        
        this.engine = new ContributionEngine(provider, 
        		varProvider, 
        		(SourceGenContext) provider.getAdapter(SourceGenContext.class),
        		dataModel,
        		this);

        this.rssGenerator = null;
        this.messages = new ArrayList<IMessage>();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	return "SourceGenSession for " + getDataModel().getModelSpecifier() + "; disposed = "+ isDisposed(); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.IDisposable#dispose()
     */
    public synchronized void dispose() {
    	Check.checkState(!isDisposed());

    	// whether this is non-null is how we validate legal sessions
    	provider.removeSourceGenSession(this);
    	provider = null;
    	
    	if (rssGenerator != null) {
    		rssGenerator.dispose();
    		rssGenerator = null;
    	}
    	rssProvider = null;
    	varProvider = null;
    	engine = null;
    	proxy.dispose();
    	proxy = null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class adapter) {
    	if (adapter == IRssModelGenerator.class) {
    		acquireRssGenerator();
    		ensureLocalizedStringFormat();
    		return rssGenerator;
    	}
    	if (adapter == SourceGenContext.class) {
    		Check.checkState(false);
    	}
    	if (adapter == ContributionEngine.class) {
    		return engine;
    	}
    	return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenSession#isDisposed()
     */
    public boolean isDisposed() {
    	return provider == null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenSession#getSourceGenProvider()
     */
    public ISourceGenProvider getSourceGenProvider() {
    	return provider;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenSession#getDataModel()
     */
    public IDesignerDataModel getDataModel() {
    	return dataModel;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenSession#getVariableProvider()
     */
    public IVariableProvider getVariableProvider() {
    	return varProvider;
    }
    
    /**
     * Public for testing.
     * @return
     */
    public IRssModelProxy getModelProxy() {
    	return proxy;
    }
    
	private void getNewRssGenerator() {
		if (rssGenerator != null) {
			rssGenerator.dispose();
			rssGenerator = null;
		}
		acquireRssGenerator();
	}

    
	private void acquireRssGenerator() {
		if (rssGenerator == null) {
			if (rssProvider == null)
				rssProvider = new RssProvider(provider);
			rssGenerator = rssProvider.createModelGenerator(this, proxy);
		}
	}
	
	/**
	 * Determine what the localized file format is.
	 * @param rootModel
	 * @return
	 */
	private int getLocalizedFileFormat(IDesignerDataModel rootModel) {
		if (rootModel != null) {
			LocalizationFileFormat format = SymbianModelUtils.getLocalizationFormat(rootModel);
			return format == LocalizationFileFormat.RLS? FORMAT_RLS : FORMAT_LOC;			
		}
		return FORMAT_LOC;        	
	}

	/**
	 * Determine what the localized file format is.
	 * @param rootProxy
	 * @return
	 */
	private int getLocalizedFileFormat(IRssRootModelProxy rootProxy) {
		if (rootProxy == proxy) {
			// use the current, possibly modified value
			return getLocalizedFileFormat(rootProxy.requestDataModel());
		} else {
			// get the value from the possibly cached, unedited model
			Object ret = rootProxy.getModelSpecifier().runWithLoadedModelNoSourceGen(new IRunWithModelAction() {

				public Object run(LoadResult lr) {
					if (lr.getModel() != null)
						return new Integer(getLocalizedFileFormat(lr.getModel()));
					return new Integer(FORMAT_LOC);
				}
			});
			return ((Integer) ret).intValue();
		}
	}

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenSession#loadFromSource()
     */
    public synchronized void loadFromSource() throws CoreException {
    	acquireRssGenerator();
    	ensureLocalizedStringFormat();
    	
    	if (dataModel instanceof DesignerDataModel) {
    		IDesignerData data = ((DesignerDataModel) dataModel).getDesignerData();
    		rssGenerator.loadState(dataModel, data);
    	}
    	
    	// no cpp stuff here
    }
    
	/**
	 * 
	 */
	private void ensureLocalizedStringFormat() {
		// always update string format
		IRssRootModelProxy rootProxy = proxy.getProjectInfo().getRootModelProxy();
		if (rootProxy != null) {
			int fileFormat = getLocalizedFileFormat(rootProxy);
			IRssModelStringHandler stringHandler = rssProvider.createStringHandler(fileFormat);
			rssGenerator.setStringHandler(stringHandler);
		}
	}

	/* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenSession#updateFromSource(com.nokia.sdt.sourcegen.IFileLoader, org.eclipse.core.runtime.IProgressMonitor)
     */
    public synchronized Command updateFromSource(IProgressMonitor monitor) throws CoreException {
    	// e.g. loadFromSource() must have been called first
    	if (rssGenerator != null) {
	    	Command command = rssGenerator.updateFromSource(monitor);
	    	command.canExecute();
	    	return command;
    	} else {
    		return new IdentityCommand();
    	}
    }

    
    public void setUpgradingMode(boolean isUpgrading) {
    	this.isUpgrading = isUpgrading;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenSession#generateSource(IFileLoader, IProgressMonitor)
     */
    public synchronized void generateSource(IProgressMonitor monitor) throws CoreException {
        // try to build off previously generated
        // RSS if possible, else start over
        
    	Check.checkState(!isDisposed());
   	
        monitor.beginTask(Messages.getString("SourceGenSession.GeneratingSourceTask"), //$NON-NLS-1$
                2);
        
        messages.clear();
        MessageReporting.addListener(this);
        
        try {
            Logging.timeStart("two-way sourcegen"); //$NON-NLS-1$
            
            if (!provider.isOneWayUpdate()) {
            	provider.resetGeneratedSources();
            	getNewRssGenerator();
            }
            else
            	acquireRssGenerator();
            
            ensureLocalizedStringFormat();
            
            rssGenerator.generateResources();
            Logging.timeEnd();
            monitor.worked(1);
            
            Logging.timeStart("one-way sourcegen"); //$NON-NLS-1$
            generateOneWay(true);
            Logging.timeEnd();
            monitor.worked(1);

            // save data in model
            if (provider.isTwoWayExport()) {
            	rssGenerator.saveState(((DesignerDataModel) dataModel).getDesignerData());
            }
            
        } finally {
            MessageReporting.removeListener(this);
            monitor.done();
        }

    }
    

    /**
     * Generate the contributions for a given data model
     * @param engine the contribution engine
     * @param model the data model whose roots to iterate
     * @param form the sourcegen form to pass
     * @return list of contributions
     */
    public static List getDataModelContributions(ContributionEngine engine, IDesignerDataModel model, String form) {
    	IComponentInstance[] roots = model.getRootComponentInstances();

        List contribs = null;

        for (int i = 0; i < roots.length; i++) {
            // recursively gather contributions
        	ComponentSourceGenGatherer gatherer = new ComponentSourceGenGatherer();
            List rootContribs = engine.gatherContributions(gatherer, roots[i], form);
            if (contribs == null)
                contribs = rootContribs;
            else
                contribs.addAll(rootContribs);
        }

        return contribs;
    }
    
    private void generateOneWay(boolean fullMode) throws CoreException {
    	synchronized (provider) {
    		SourceGenContext context = (SourceGenContext) provider.getAdapter(SourceGenContext.class);
	    	context.reset();
	    	engine.reset();
	        SourceGenGlobals.setup(engine, context, rssGenerator);
	
	        List contribs = null;
	        
	        contribs = getDataModelContributions(engine, dataModel, null);
	        if (contribs == null)
	            return;
	        
	        if (isUpgrading) {
	        	// remove non-patch contributions
	        	engine.removeNonPatchContributions(contribs);
	        }
	        
	        if (fullMode) {
	        	// validate the contributions
	        	engine.validateContributions(contribs);
	        
	        	// mark dead locations
	        	engine.markDeadLocations(context.getContributionGatherer().getDeadLocations());
	        	
	        	// now apply them to the project
	        	engine.applyContributions(contribs);
	        } else {
	        	// find what files would be generated
	        	engine.getFilesForContributions(contribs);
	        }
	        
	        SourceGenGlobals.cleanup();
    	}
    }
    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.symbian.ISymbianSourceGenSession#getGeneratedResource(com.nokia.sdt.datamodel.adapter.IComponentInstance)
     */
    public String getGeneratedResource(IComponentInstance instance) {
    	Check.checkState(!isDisposed());
    	acquireRssGenerator();
        ResourceInfo info = rssGenerator.getModelManipulator().getResourceTracker().findResourceInfo(instance, null);
        if (info != null && info.findResourceDefinition(rssGenerator) != null) {
            return info.getName();
        }
        return null;

    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ISymbianSourceGenSession#getResourceFileNameBase()
     */
    public String getResourceFileNameBase() {
    	Check.checkState(!isDisposed());
    	IRssModelProxy rootProxy = proxy.getProjectInfo().getRootModelProxy();
    	if (rootProxy == null)
    		rootProxy = proxy;
    	return rootProxy. getRssBaseName();
    }

	/**
	 * @param rssFiles
	 * @param generatedSources
	 * @return
	 */
	private List<IPath> combine(Collection<ISourceFile> rssFiles, List<IPath> generatedSources) {
	
		if (generatedSources == null)
			generatedSources = new ArrayList<IPath>();
			
		if (rssFiles != null) {
	    	for (Iterator iter = rssFiles.iterator(); iter.hasNext();) {
	    		ISourceFile file = (ISourceFile) iter.next();
				File f = file.getFile();
				IPath path = FileUtils.convertToWorkspacePath(new Path(f.getAbsolutePath()));
				if (path != null) {
					IPath projPath = path.removeFirstSegments(1).setDevice(null);
					generatedSources.add(projPath);
				}
			}
		}
		
		return generatedSources;
	}

	public synchronized List<IPath> scanForGeneratedSources(IProgressMonitor monitor) {
    	Check.checkState(!isDisposed());

    	monitor.beginTask(Messages.getString("SourceGenSession.GeneratingSourceTask"), //$NON-NLS-1$
	            2);
	
	    SourceGenContext context = (SourceGenContext) provider.getAdapter(SourceGenContext.class);
	    Collection<ISourceFile> rssFiles = null;
	    try {
	    	// always start from scratch
	    	provider.resetGeneratedSources();
	    	
	    	getNewRssGenerator();
	    	ensureLocalizedStringFormat();
	    	
	    	rssFiles = rssGenerator.scanForGeneratedFiles();
	    	monitor.worked(1);
	        generateOneWay(false);
	        monitor.worked(1);
	        
	        //// revert any changes made
	        //rssGenerator.dispose();
	        //rssGenerator = null;
			//provider.revertGeneratedSources();
	    } catch (CoreException e) {
	    	SourceGenPlugin.getDefault().log(e);
	    } finally {
	    	monitor.done();
	    }
	    
	    return combine(rssFiles, context.getGeneratedSources());
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenSession#ensureDataModel(com.nokia.sdt.datamodel.IDesignerDataModel)
	 */
	public void ensureDataModel(IDesignerDataModel model) {
		proxy.setDataModel(model);
		this.dataModel = model;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenSession#getMessages()
	 */
	public Collection<IMessage> getMessages() {
		return messages;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IMessageListener#isHandlingMessage(com.nokia.sdt.utils.IMessage)
	 */
	public boolean isHandlingMessage(IMessage msg) {
		/*IPath wsPath = msg.getPath();
		if (wsPath != null && provider != null && ObjectUtils.equals(wsPath.segment(0), provider.getProjectName()))
			return true;
		return false;*/
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IMessageListener#emitMessage(com.nokia.sdt.utils.IMessage)
	 */
	public void emitMessage(IMessage msg) {
		messages.add(msg);
	}

	/** Test method */
	public ISourceGenPatchInfo getPatchInfo(ISourceGenComponentVersionProvider provider) {
		return engine.getPatchInfo(provider);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenSession#getSourceGenUpradingProvider()
	 */
	public ISourceGenUpgradingProvider getSourceGenUpgradingProvider(ISourceGenComponentVersionProvider versionProvider) {
		SourceGenContext context = (SourceGenContext) provider.getAdapter(SourceGenContext.class);
		final ISourceGenPatchInfo patchInfo = engine.getPatchInfo(versionProvider);
		
		final PatchRefactoringEngine refactoringEngine = new PatchRefactoringEngine(context,
				new Path(SOURCEGEN_UPGRADING_LOG_FILE_NAME));
		
		final Change changes = refactoringEngine.gatherSourceChanges(patchInfo);
		final RefactoringStatus status = refactoringEngine.getValidationStatus();
		
		return new ISourceGenUpgradingProvider() {
			public RefactoringStatus getValidationStatus() {
				return status;
			}

			public Change getChanges() {
				return changes;
			}
			
			public void detectSkippedPatches() {
				refactoringEngine.detectSkippedPatches();
			}
			
			/* (non-Javadoc)
			 * @see com.nokia.sdt.sourcegen.ISourceGenUpgradingProvider#getPatchContext()
			 */
			public PatchContext getPatchContext() {
				return refactoringEngine.getPatchContext();
			}
		};
	}
}
