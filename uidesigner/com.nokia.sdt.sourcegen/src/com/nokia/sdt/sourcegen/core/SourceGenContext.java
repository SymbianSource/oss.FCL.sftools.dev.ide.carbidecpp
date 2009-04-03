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

package com.nokia.sdt.sourcegen.core;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.TrackedResource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.MessageLocators;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.contributions.ComponentSourceGenGatherer;
import com.nokia.sdt.sourcegen.contributions.IContributionGatherer;
import com.nokia.sdt.sourcegen.contributions.domains.cpp.*;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.workspace.IProjectContext;
import com.nokia.sdt.workspace.WorkspaceContext;

import org.eclipse.cdt.core.dom.ICodeReaderFactory;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * This context holds the modified file state while
 * generating C/C++ source.
 * 
 *
 */
public class SourceGenContext implements IDisposable, ISourceManipulatorProvider {
    /** Moveable reference to the project */
    TrackedResource trackedProject;
    /** Base directory: set to non-null for testing outside project,
     * otherwise the project's base is used */
    private File baseDir;

    /** Map of IPath to ISourceManipulator */
    private Map pathsToSources;
    
    /** Set of IPath of sources created without ISourceManipulator */
    private Set<IPath> pathsToExtraFiles;
    
    //private WorkInProgressCodeReaderFactory codeReaderFactory;
    private ICodeReaderFactory codeReaderFactory;
	ISourceGenProvider provider;
    
	IContributionGatherer gatherer;
	
    /**
     * 
     */
    public SourceGenContext(ISourceGenProvider provider) {
    	this.provider = provider;
        this.pathsToSources = new HashMap();
        pathsToExtraFiles = new HashSet<IPath>();
        
        //codeReaderFactory = new MemoryBufferCodeReaderFactory(this);
        codeReaderFactory = new WorkInProgressCodeReaderFactory();
        this.gatherer = new ComponentSourceGenGatherer();
    }
    
    /**
     * NOTE: This form is only to be used by CppDomain
     * @param project
     */
    public SourceGenContext(IProject project) {
    	this((ISourceGenProvider)null);
    	setProject(project);
    }

	/* (non-Javadoc)
     * @see com.nokia.sdt.utils.IDisposable#dispose()
     */
    public void dispose() {
    }

    public IContributionGatherer getContributionGatherer() {
    	return gatherer;
    }
    
    public void setProject(IProject project) {
        if (trackedProject != null) {
            trackedProject.dispose();
            trackedProject = null;
        }
        if (project != null) {
        	trackedProject = new TrackedResource(project);
        	setBaseDir(null);
        }
    }
    
    public IProject getProject() {
        if (trackedProject == null)
            return null;
        return trackedProject.getProject();
    }
    
    /**
     * Revert changed sources
     */
    public void revert() {
       	for (Iterator iter = pathsToSources.values().iterator(); iter.hasNext();) {
			ISourceManipulator manip = (ISourceManipulator) iter.next();
			try {
				if (manip.changed())
					manip.revert();
			} catch (CoreException e) {
				SourceGenPlugin.getDefault().log(e);
			}
		}
    }
    
    /**
     * Reset the context for a new round of generation
     * <p>
     * Clears known source files
     */
    public void reset() {
    	for (Iterator iter = pathsToSources.values().iterator(); iter.hasNext();) {
			ISourceManipulator manip = (ISourceManipulator) iter.next();
			try {
				if (manip.changed())
					manip.unload();
			} catch (CoreException e) {
				SourceGenPlugin.getDefault().log(e);
			}
		}
        pathsToSources.clear();
        gatherer.getDeadLocations().clear();
        pathsToExtraFiles.clear();
    }

    /* copied from Eclipse and fixed to work... */
    /*
    private int matchingFirstSegments(IPath my, IPath anotherPath) {
        int anotherPathLen = anotherPath.segmentCount();
        int max = Math.min(my.segmentCount(), anotherPathLen);
        int count = 0;
        for (int i = 0; i < max; i++) {
            if (!my.segment(i).equalsIgnoreCase(anotherPath.segment(i))) {
                return count;
            }
            count++;
        }
        return count;
    }*/

    /**
     * Map the given file to a path in the current workspace.
     * @param fullPath
     * @return the IFile for the file
     */
    private IFile fullPathToWorkspace(String fullPath) {
    	return FileUtils.convertFileToIFile(new File(fullPath));
    	/*
        if (getProject() == null)
            return null;
        
        IPath path = new Path(fullPath);
        IPath rootPath;
        rootPath = trackedProject.getProject().getLocation();
        
        // this is BUGGY!  Case sensitive for some dumb reason
        //int match = path.matchingFirstSegments(rootPath);
        int match = matchingFirstSegments(path, rootPath);
        
        if (match == 0)
            return null;
        
        path = path.removeFirstSegments(match);
        IFile wsFile = FileUtils.convertFileToIFile(getEffectiveBaseDir()) 
        	getProject().getFile(path);
        return wsFile;
        */
    }

    /**
     * Get a manipulator allowing modification of source file
     * @param path project-relative path
     */
    public ISourceManipulator getSourceManipulator(IPath path) {
        if (trackedProject != null) {
            if (trackedProject.getProject().getFile(path) == null)
                return null;
        }
        
        ISourceManipulator sfi = (ISourceManipulator) pathsToSources.get(path); 
        if (sfi == null) {
            if (trackedProject != null)
                sfi = new WorkspaceSourceManipulator(this, path);
            else
                sfi = new StandaloneSourceManipulator(this, path);
            //System.out.println("working with file: " + path);
            pathsToSources.put(path, sfi);
        }
        return sfi;
    }
    
    public ISourceManipulator getSourceManipulator(String fullPath) {
        if (getProject() != null) {
            IFile workspaceFile = fullPathToWorkspace(fullPath);
            if (workspaceFile != null)
                return getSourceManipulator(workspaceFile.getProjectRelativePath());
            else
                return null; //return getSourceManipulator(new Path(fullPath));
        }
        else if (baseDir != null)
            return getSourceManipulator(new Path(fullPath.substring(baseDir.getAbsolutePath().length()+1))); 
        else
            return null;
    }

    /**
     * @param monitor 
     * 
     */
    public void saveGeneratedSources(IProgressMonitor monitor) throws CoreException {
        monitor.beginTask(Messages.getString("SourceGenSession.SavingSourceFiles"), //$NON-NLS-1$ 
                pathsToSources.values().size());
        Check.checkState(provider != null);
        try {
        	writeFiles(provider.getFileTracker(), monitor);
        } finally {
            monitor.done();
        }
    }

    public List<IPath> getGeneratedSources() {
    	List<IPath> files = new ArrayList<IPath>();
    	for (Iterator iter = pathsToSources.values().iterator(); iter.hasNext();) {
			ISourceManipulator manip = (ISourceManipulator) iter.next();
			files.add(manip.getPath());
		}
    	files.addAll(pathsToExtraFiles);
    	return files;
    }
    
    /**
	 *	Write the modified files to disk 
     * @param fileSaver 
	 */
	private void writeFiles(IFileSaver fileSaver, IProgressMonitor monitor) throws CoreException {
        // write all the one-way files
        for (Iterator iter = pathsToSources.values().iterator(); iter.hasNext();) {
            ISourceManipulator sfi = (ISourceManipulator) iter.next();
            if (sfi.changed()) {
            	//System.out.println("saving new version of " + sfi.getPath());
            	sfi.save(fileSaver, new SubProgressMonitor(monitor, 1));
            } else {
            	sfi.unload();
                monitor.worked(1);
            }
        }
	}

	/**
     * Find or create the file at the given path in the project
     * @param path project-relative path
     * @return true: created file
     * @throws CoreException
     */
    public boolean resolveOrCreateFile(IPath path) throws CoreException {
        resolveOrCreatePath(path.removeLastSegments(1));

        if (getProject() != null) {
            IFile file = getProject().getFile(path);
            if (file.exists())
                return false;
            
            file.create(new ByteArrayInputStream(new byte[0]),
            		false, new NullProgressMonitor());
            return true;
        } else if (baseDir != null ){
            File nf = new File(baseDir, path.toString());

            try {
            	return nf.createNewFile();
            } catch (IOException e) {
                throw new CoreException(Logging.newStatus(SourceGenPlugin.getDefault(), 
                        e));
            }
        } else
            return false;
    }

	/**
     * Set contents of the given file, which must exist already.
     * @param path project-relative path
     * @param is stream to initial contents
     * @throws CoreException
     */
    public void setFileContents(IPath path, InputStream is) throws CoreException {
        if (getProject() != null) {
            IFile file = getProject().getFile(path);
            file.setContents(is, false, true, new NullProgressMonitor());
        } else if (baseDir != null ){
            File nf = new File(baseDir, path.toString());
            
            try {
                FileOutputStream fos = new FileOutputStream(nf);
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) > 0)
                	fos.write(buffer, 0, len);
                fos.close();
            } catch (IOException e) {
                throw new CoreException(Logging.newStatus(SourceGenPlugin.getDefault(), 
                        e));
            }
        }
    }

    /**
     * Find or create the directory at the given path in the project
     * @param path
     */
    protected void resolveOrCreatePath(IPath path) throws CoreException {
        if (getProject() != null) {
            
            IFolder folder = getProject().getFolder(path);
            if (folder.exists())
                return;
            
            folder.create(false, true, new NullProgressMonitor());
        } else if (baseDir != null) {
            File nf = new File(baseDir, path.toString());
            if (nf.exists())
                return;
            
            nf.mkdirs();
        }
    }

    /**
     * Set the base directory for writing files
     * @param baseDir if null, use the project base, else
     * use this directory 
     */
    public void setBaseDir(File baseDir) {
        if (baseDir != null) {
            this.baseDir = baseDir;
            this.baseDir.mkdirs();
        } else
            this.baseDir = null;
        
    }

    /**
     * Get the effective base directory (either the overridden
     * one or one based on the project)
     * @return directory
     */
    public File getEffectiveBaseDir() {
        if (baseDir != null)
            return baseDir;
        IProject project = getProject();
        if (project != null && project.getLocation() != null)
            return project.getLocation().toFile();
        else
            return null;
    }

    /**
     */
    public ICodeReaderFactory getCodeReaderFactory() {
        return codeReaderFactory;
    }

 
    public static void emit(IComponentInstance instance, int severity, String msgKey, Object[] args) {
		MessageReporting.emitMessage(
				new Message(
						severity,
						MessageLocators.getComponentOrInstanceLocation(instance),
						msgKey,
						MessageFormat.format(Messages.getString(msgKey), args)));
	}

    public IProjectContext getProjectContext(IDesignerDataModel model) {
    	IProject project = getProject();
		if (project != null) {
			return WorkspaceContext.getContext().getContextForProject(project);
		}
		return null;
    }
    
    public ISourceFormatting getSourceFormatting() {
    	if (provider.getSourceFormatter() != null)
    		return provider.getSourceFormatter().getSourceFormatting();
    	else
    		return null;
    }
   
    public void addExtraSourcePath(IPath extraSourcePath) {
    	pathsToExtraFiles.add(extraSourcePath);
    }
}
