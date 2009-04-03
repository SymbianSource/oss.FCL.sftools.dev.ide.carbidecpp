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

package com.nokia.sdt.symbian.dm;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.cpp.internal.api.utils.core.MultiResourceChangeListenerDispatcher.IResourceChangeHandler;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.sourcegen.IIncludeFileLocator;
import com.nokia.sdt.sourcegen.INameGenerator;
import com.nokia.sdt.symbian.ISymbianNameGenerator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import java.io.File;
import java.util.*;

/**
 * Find include files the same way CDT would -- well, sort of,
 * because the logic for that (including finding files relative
 * to the current source file, #include_next, etc) is locked up 
 * inside BaseScanner.
 * <p>
 * This actually uses the list of include paths that CDT provides
 * to search for files.  As a last ditch it uses an SDK location
 * to check for a match.
 * 
 *
 */
public class CdtIncludeFileLocator implements IIncludeFileLocator, IResourceChangeHandler, IDisposable {

    private IProject project;
    private File sdkIncludes;
	private String incPath;
	private String rsrcPath;
	private String srcPath;
	private String buildPath;
    
    private String[] includePaths;
	private ICarbideProjectInfo projectInfo;
	private Set<IPath>  epocFilesToWatch;
    
    /**
     * 
     */
    public CdtIncludeFileLocator(IProject project, INameGenerator nameGenerator, IDesignerDataModel rootModel, File sdkHome) {
        Check.checkArg(project);
        this.project = project;
        this.projectInfo = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

        // track all the MMPs in the project and flush the cache 
        
        // TODO: refactor to use listener on CarbideBuildManager if/when that's created
        
        // TODO: detect added/removed MMPs as well... 
        // currently the UI Designer assumes that one MMP is associated
        // with a project, so this isn't yet necessary.
        List<IPath> makMakeRefs = new ArrayList<IPath>();
		List<IPath> testMakMakeReferences = new ArrayList<IPath>();
		
		EpocEngineHelper.getAllMakMakeFiles(project, makMakeRefs, testMakMakeReferences);
		makMakeRefs.addAll(testMakMakeReferences);
		
		Set<IPath> filesToWatch = new HashSet<IPath>();
		for (IPath path : makMakeRefs) {
			if (FileUtils.getSafeFileExtension(path).equalsIgnoreCase("mmp")) { //$NON-NLS-1$
				EpocEngineHelper.addIncludedFilesFromMMP(projectInfo, null, path, filesToWatch);
			}
		}
		
		epocFilesToWatch = new HashSet<IPath>();
		for (IPath path : filesToWatch) {
			IPath wsPath = FileUtils.convertToWorkspacePath(path);
			if (wsPath != null) {
				epocFilesToWatch.add(wsPath);
				EpocEnginePlugin.getMultiResourceChangeListenerDispatcher().addResource(
						wsPath, this);
			}
		}
        
        if (nameGenerator != null && rootModel != null) { 
	        this.incPath = nameGenerator.getProjectRelativeDirectory(rootModel, ISymbianNameGenerator.INCLUDE_DIRECTORY_ID);
	        this.rsrcPath = nameGenerator.getProjectRelativeDirectory(rootModel, ISymbianNameGenerator.RESOURCE_DIRECTORY_ID);
	        this.srcPath = nameGenerator.getProjectRelativeDirectory(rootModel, ISymbianNameGenerator.SOURCE_DIRECTORY_ID);
	        this.buildPath = nameGenerator.getProjectRelativeDirectory(rootModel, ISymbianNameGenerator.BUILD_DIRECTORY_ID);
        }
        this.sdkIncludes = new File(new File(sdkHome, "epoc32"), "include"); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public synchronized void dispose() {
    	if (epocFilesToWatch != null) {
	    	for (IPath path : epocFilesToWatch) {
	    		EpocEnginePlugin.getMultiResourceChangeListenerDispatcher().removeResource(
						path, this);
	    	}
	    	epocFilesToWatch = null;
    	}
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IIncludePathHandler#findIncludeFile(java.lang.String, boolean, java.io.File)
     */
    public synchronized File findIncludeFile(String file, boolean isUser, File currentDir) {
        File f;
        if (currentDir != null) {
            f = new File(currentDir, file);
            if (f.exists())
                return f;
        }
        
        refreshIncludes();
        for (int i = 0; i < includePaths.length; i++) {
            f = new File(includePaths[i], file);
            if (f.exists())
                return f;
        }
        
        return null;
    }

    /**
     * Ensure the includes are up-to-date for the tracked project.
     */
    private void refreshIncludes() {
        if (includePaths == null) {
            includePaths = null;
            
            List<File> userPaths = new ArrayList();
            List<File> systemPaths = new ArrayList();
            
            EpocEngineHelper.getProjectIncludePaths(
            		 projectInfo, null,
            		userPaths, systemPaths);
            
            List<String> allPaths = new ArrayList<String>();
            for (File path : userPaths)
            	allPaths.add(path.getAbsolutePath());
            for (File path : systemPaths)
            	allPaths.add(path.getAbsolutePath());
            
            // ensure expected paths are available -- we get lies from CDT
            File projectBase = project.getLocation().toFile();
            if (srcPath != null)
            	allPaths.add(new File(projectBase, srcPath).getAbsolutePath());
            if (incPath != null)
            	allPaths.add(new File(projectBase, incPath).getAbsolutePath());
            if (rsrcPath != null)
            	allPaths.add(new File(projectBase, rsrcPath).getAbsolutePath());
            if (buildPath != null)
            	allPaths.add(new File(projectBase, buildPath).getAbsolutePath());
            
            // this is totally a last-ditch effort to deal
            // with unrecognized or broken projects
            allPaths.add(sdkIncludes.getAbsolutePath());
            
            includePaths = (String[]) allPaths.toArray(new String[allPaths.size()]);
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IIncludeFileLocator#getIncludePaths()
     */
    public synchronized String[] getIncludePaths() {
        refreshIncludes();
        return includePaths;
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.MultiResourceChangeListenerDispatcher.IResourceChangeHandler#resourceChanged(org.eclipse.core.runtime.IPath)
	 */
	public synchronized void resourceChanged(IPath workspacePath) {
		includePaths = null;
	}
}
