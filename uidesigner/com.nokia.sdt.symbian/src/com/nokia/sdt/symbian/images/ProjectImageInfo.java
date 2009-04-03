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

package com.nokia.sdt.symbian.images;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileModel;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.internal.project.ui.images.*;
import com.nokia.carbide.cpp.internal.ui.images.CachingImageLoader;
import com.nokia.carbide.cpp.ui.images.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;
import com.nokia.cpp.internal.api.utils.core.TrackedResource;
import com.nokia.cpp.internal.api.utils.core.TrackedResource.IListener;
import com.nokia.sdt.datamodel.images.IProjectImageInfo;
import com.nokia.sdt.datamodel.images.IProjectImageInfoListener;
import com.nokia.sdt.symbian.SymbianPlugin;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.util.*;

/**
 * This class manages the information about the available multi-image
 * lists in the project.
 *
 */
public class ProjectImageInfo implements IProjectImageInfo, IDisposable, IImageContainerModelListener {

	
    private TrackedResource trackedProject;

    //private Map<CaseInsensitivePathWrapper, MultiImageInfo> imageListsToMultiImageInfos;
    //private Map<CaseInsensitivePathWrapper, CaseInsensitivePathWrapper> imageListsToBinaryFiles;
    //private Map<CaseInsensitivePathWrapper, CaseInsensitivePathWrapper> binaryFilesToImageLists;

    private List<MultiImageInfo> multiImageInfos;
    
    private boolean dirty;

	private String projectName;
	private String targetNameBase;
    
	/** project-relative path to MMP path + IMMPAifInfo map */
	private Map<IPath, Pair<IPath, IMMPAIFInfo>> aifFileInfoMap;
	
	/** project-relative path to target path map for images found
	 * via PRJ_EXPORTS or PKG files
	 */
	private Set<IImageModel> projectImageModels;

	private List<IProjectImageInfoListener> listeners;

	private ICarbideProjectInfo carbideProjectInformation;

	private IImageContainerModel externalImageContainerModel;

	private IImageLoader imageLoader;

	private IImageConverterFactory imageConverterFactory;

	private List<IImageContainerModel> containerModels;
	
	private boolean supportsSVG;
	
    /**
     * This wraps an IPath and overrides its equality and hashing
     * algorithms so it compares case-insensitively.
     *
     */
    static class CaseInsensitivePathWrapper {
        IPath path;

        public CaseInsensitivePathWrapper(IPath path) {
            this.path = path;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return path.toString();
        }
        
        /* (non-Javadoc)
         * @see org.eclipse.core.runtime.Path#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Path)
                return super.equals(obj);
            if (!(obj instanceof CaseInsensitivePathWrapper))
                return false;
            return ((CaseInsensitivePathWrapper) obj).path.toOSString().equalsIgnoreCase(path.toOSString());
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return path.toOSString().toLowerCase().hashCode();
        }
    }
    
    public ProjectImageInfo(IProject project) {
        Check.checkArg(project);
        this.projectName = project.getName();
        this.aifFileInfoMap = new LinkedHashMap<IPath, Pair<IPath,IMMPAIFInfo>>();
        this.targetNameBase = projectName;
        this.projectImageModels = new LinkedHashSet<IImageModel>();
        this.listeners = new ArrayList<IProjectImageInfoListener>();
        this.imageLoader = new CachingImageLoader();
        
        // this model is outside the scope of the other container models 
        // so is not cleared
        this.externalImageContainerModel = CarbideImageModelFactory.createNullImageContainerModel(
        		null, new CachingImageLoader());
        externalImageContainerModel.addListener(this);

        this.containerModels = new ArrayList<IImageContainerModel>();
        ensureTrackedProject();
    }
    
	private void registerImageContainerModel(
			IImageContainerModel containerModel) {
		containerModel.addListener(this);
		containerModels.add(containerModel);
		projectImageModels.addAll(Arrays.asList(containerModel.createImageModels()));
	}

	private void releaseImageContainerModels() {
		for (IImageContainerModel model : containerModels) {
        	model.removeListener(this);
        }
        containerModels.clear();
        projectImageModels.clear();
	}
	
	public synchronized void addListener(IProjectImageInfoListener listener) {
    	if (!listeners.contains(listener))
    		listeners.add(listener);
    }
    
    public synchronized void removeListener(IProjectImageInfoListener listener) {
    	listeners.remove(listener);
    }
    

    protected synchronized void fireDirty() {
    	IProjectImageInfoListener[] array = (IProjectImageInfoListener[]) listeners.toArray(new IProjectImageInfoListener[listeners.size()]);
    	for (IProjectImageInfoListener listener : array) {
    		listener.dirtyNotification(this);
    	}
    }

    protected synchronized void fireChanged() {
    	IProjectImageInfoListener[] array = (IProjectImageInfoListener[]) listeners.toArray(new IProjectImageInfoListener[listeners.size()]);
    	for (IProjectImageInfoListener listener : array) {
    		listener.changed(this);
    	}
    }

    public IProject getProject() {
    	return trackedProject != null ? trackedProject.getProject() : null;
    }
    /**
	 * 
	 */
	private void ensureTrackedProject() {
		if (trackedProject == null) {
	        this.trackedProject = new TrackedResource(ResourcesPlugin.getWorkspace().getRoot().findMember(projectName));
	        if (trackedProject != null) {
	        	initProjectInformation();
	        }
		}		
	}

	public void dispose() {
		listeners.clear();
        for (Iterator iter = multiImageInfos.iterator(); iter.hasNext();) {
            MultiImageInfo info = (MultiImageInfo) iter.next();
            info.dispose();
        }
        if (externalImageContainerModel != null) {
        	externalImageContainerModel.removeListener(this);
        	externalImageContainerModel.getImageLoader().dispose();
        	externalImageContainerModel = null;
        }
        aifFileInfoMap.clear();
        releaseImageContainerModels();
        if (trackedProject != null) {
            trackedProject.dispose();
        }
        //ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
        trackedProject = null;
    }
    
    private void reportError(CoreException e) {
        IStatus status = Logging.newStatus(SymbianPlugin.getDefault(), e);
        Logging.log(SymbianPlugin.getDefault(), status);
        Logging.showErrorDialog(Messages.getString("ImageEditorDialogData.Error"), Messages.getString("ImageEditorDialogData.CouldNotScanImageLists"),  //$NON-NLS-1$ //$NON-NLS-2$
                status);
    }
    
    /**
     * Initialize project information, namely, the list of
     * *.mbmdef and *.mifdef files
     */
    private void initProjectInformation() {
    	multiImageInfos = new ArrayList<MultiImageInfo>();
    	
    	releaseImageContainerModels();

        //imageListsToBinaryFiles = new HashMap<CaseInsensitivePathWrapper, CaseInsensitivePathWrapper>();
        //binaryFilesToImageLists = new HashMap<CaseInsensitivePathWrapper, CaseInsensitivePathWrapper>();
        //imageListsToMultiImageInfos = new HashMap<CaseInsensitivePathWrapper, MultiImageInfo>();
        final IProject project = trackedProject.getProject();
        if (project == null || !project.exists() || !project.isOpen())
            return;
            
        carbideProjectInformation = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

        this.imageConverterFactory = CarbideImageModelFactory.getImageConverterFactory(carbideProjectInformation);

        this.supportsSVG = CarbideImageModelFactory.doesProjectSupportSVG(carbideProjectInformation);
        
        // watch for the project going missing
        trackedProject.addListener(new IListener() {

            public void resourceChanged(TrackedResource resource) {
                dirty = true;
                fireDirty();
            }

            public void resourceMoved(TrackedResource resource, IPath oldPath) {
                dirty = true;
                fireDirty();
            }

            public void resourceDeleted(TrackedResource resource) {
                dirty = true;
                fireDirty();
            }
            
        });
        
        // and watch for anything in the project changing,
        // so we can refresh our lists
        //ResourcesPlugin.getWorkspace().addResourceChangeListener(this);

        try {
            project.accept(new IResourceProxyVisitor() {
    
                public boolean visit(IResourceProxy resourceProxy) throws CoreException {
                	String name = resourceProxy.getName();
                	String extension = ""; //$NON-NLS-1$
                	int idx = name.lastIndexOf('.');
                	if (idx >= 0)
                		extension = name.substring(idx + 1);
                	
                    if (extension.compareToIgnoreCase(BuildLogic.MMP_EXTENSION) == 0) {
                    	scanMMPFile(resourceProxy.requestResource().getLocation());
                    } else if (BuildLogic.ICON_MAKEFILE_PATTERN.matcher(name).matches()) {
                    	scanIconMakefile(resourceProxy.requestResource().getLocation());
                    } else if (name.compareToIgnoreCase(BuildLogic.BLDINF_PATH) == 0) {
                    	scanBldInfFile(resourceProxy.requestResource().getLocation());
                    }
                    return true;
                }
                
            }, 0);
        } catch (CoreException e) {
            reportError(e);
        }
     
        dirty = false;
        fireChanged();
    }

    /**
     * Scan the MMP file and create any MultiImageInfos from its
     * START BITMAP blocks. 
	 * @param path
	 */
	protected void scanMMPFile(final IPath path) {
		final IProject project = trackedProject.getProject();
		final IPath projectLocation = ProjectUtils.getRealProjectLocation(project);
		EpocEnginePlugin.runWithMMPData(path, 
			new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new MMPDataRunnableAdapter() {

				public Object run(IMMPData data) {
					String targetFile = data.getSingleArgumentSettings().get(EMMPStatement.TARGET);
					if (targetFile != null && targetFile.length() > 0) {
						targetNameBase = new Path(targetFile).removeFileExtension().lastSegment();
					} else {
						targetNameBase = projectName;
					}
					
					// iterate bitmap sections
					for (IMultiImageSource multiImageSource : data.getMultiImageSources()) {
						MultiImageInfo info = new MultiImageInfo(
								projectLocation,
								FileUtils.removePrefixFromPath(projectLocation, path),
								multiImageSource);
							
						multiImageInfos.add(info);
						
						MultiImageInfoImageContainerModel containerModel = 
							new MultiImageInfoImageContainerModel(ProjectImageInfo.this, info);
						registerImageContainerModel(containerModel);
					}
					
					// and AIF info
					for (IMMPAIFInfo info : data.getAifs()) {
						aifFileInfoMap.put(info.getResource(),
								new Pair<IPath, IMMPAIFInfo>(path, info));
						
						AIFImageContainerModel containerModel = new AIFImageContainerModel(
								getImageLoader(),
								getImageConverterFactory(),
								projectLocation, path, info);
						registerImageContainerModel(containerModel);
					}
					return null;
				}
		});
	}

	/**
     * Scan the Icon makefile and create any MultiImageInfos from its
     * mifconv calls. 
	 * @param path
	 */
	protected void scanIconMakefile(IPath path) {
		IProject project = trackedProject.getProject();
		final IPath projectLocation = ProjectUtils.getRealProjectLocation(project);
		
		IImageMakefileModel model = null;
		IImageMakefileView view = null;
		try {
			model = EpocEnginePlugin.getImageMakefileModelProvider().getSharedModel(path);
			
			view = model.createView(new DefaultImageMakefileViewConfiguration(
					project, 
					null,
					new AllNodesViewFilter()));
		
			List<IMultiImageSource> multiImageSources = view.getMultiImageSources();
			for (IMultiImageSource multiImageSource : multiImageSources) {
				MultiImageInfo info;
				info = new MultiImageInfo(
							ProjectUtils.getRealProjectLocation(project),
						FileUtils.removePrefixFromPath(projectLocation, path),
						multiImageSource);
				multiImageInfos.add(info);
				
				MultiImageInfoImageContainerModel containerModel = new MultiImageInfoImageContainerModel(
						ProjectImageInfo.this, info);
				registerImageContainerModel(containerModel);
			}
			
		} catch (CoreException e) {
			SymbianPlugin.getDefault().log(e);
		} finally {
			if (view != null)
				view.dispose();
			if (model != null)
				EpocEnginePlugin.getImageMakefileModelProvider().releaseSharedModel(model);			
		}
	}

	/**
     * Scan the bld.inf file and store any file mappings for images
     * from PRJ_EXPORTS sections
	 * @param path
	 */
	protected void scanBldInfFile(final IPath path) {
		final IProject project = trackedProject.getProject();
		
		EpocEnginePlugin.runWithBldInfView(path, 
			new DefaultViewConfiguration(project, null, new AllNodesViewFilter()), 
			new BldInfViewRunnableAdapter() {

				public Object run(IBldInfView view) {
					IImageContainerModel containerModel = new ProjectExportImageContainerModel(
							getImageLoader(),
							getImageConverterFactory(),
							carbideProjectInformation);
					registerImageContainerModel(containerModel);
					return null;
				}
		});
	}

	
	/**
     * Get the list of system-relative binary multi-image files
     */
    public String[] getMultiImageFileList() {
        List<String> list = new ArrayList<String>();
        for (MultiImageInfo info : multiImageInfos) {
            //CaseInsensitivePathWrapper wrapper = (CaseInsensitivePathWrapper) iter.next();
        	list.add(info.getBinaryFile().toOSString());
            //list.add(wrapper.path.toOSString());
        }
        Collections.sort(list);
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * Get the multi-image files
     */
    public MultiImageInfo[] getMultiImageInfos() {
    	return (MultiImageInfo[]) multiImageInfos.toArray(new MultiImageInfo[multiImageInfos.size()]);
    }
    

    /**
	      * Find or load the multi-image info for the given source file,
	      * which is presumed to be written to the given binary path.
	      * @param path project-relative path
	      * @param binaryPath assumed binary path, or null to guess
	      * @return info for multi-image file
	      */
	    /*
	    public MultiImageInfo getInfoForImageList(IPath path, IPath binaryPath) {
	        CaseInsensitivePathWrapper projectPathWrapper = new CaseInsensitivePathWrapper(path);
	        MultiImageInfo info = imageListsToMultiImageInfos.get(projectPathWrapper);
	        if (info == null) {
	            if (trackedProject != null && trackedProject.getProject() != null) {
	                try {
	                    info = new MultiImageInfo(trackedProject.getProject(), projectPathWrapper.path, binaryPath);
	                } catch (CoreException e) {
	                    SymbianPlugin.getDefault().log(e);
	                    return null;
	                }
	            }
	            if (info != null)
	                imageListsToMultiImageInfos.put(projectPathWrapper, info);
	        }
	        return info;
	    }
	*/
	
	/**
     * Find or load the multi-image info for the given binary file (cached)
     * @param binaryPath system-relative path
     * @return info for the multi-image file
     */
    public MultiImageInfo getInfoForBinaryFile(IPath binaryPath) {
    	for (MultiImageInfo info : multiImageInfos) {
    		if (info.getBinaryFile().toOSString().equalsIgnoreCase(binaryPath.toOSString())) {
    			return info;
    		}
    	}
    	// try again, only checking filenames
    	for (MultiImageInfo info : multiImageInfos) {
    		if (info.getBinaryFile().lastSegment().equalsIgnoreCase(binaryPath.lastSegment())) {
    			return info;
    		}
    	}
    	return null;
    }
    /*
    public MultiImageInfo getInfoForBinaryFile(IPath binaryPath) {
        CaseInsensitivePathWrapper projectPathWrapper = binaryFilesToImageLists.get(new CaseInsensitivePathWrapper(binaryPath));
        if (projectPathWrapper == null) {
            // look for a similar file: be liberal in accepting file
            for (Iterator iter = binaryFilesToImageLists.keySet().iterator(); iter.hasNext();) {
                CaseInsensitivePathWrapper binFile = (CaseInsensitivePathWrapper) iter.next();
                String name = binFile.path.lastSegment();
                if (name.equalsIgnoreCase(binaryPath.lastSegment())) {
                    projectPathWrapper = binaryFilesToImageLists.get(binFile);
                    break;
                }
            }
            if (projectPathWrapper == null)
                return null;
        }
        MultiImageInfo info = getInfoForImageList(projectPathWrapper.path, binaryPath);
        if (info != null) {
            // if binary file is guessed, this has no effect, otherwise it remaps the file
            CaseInsensitivePathWrapper binPath = new CaseInsensitivePathWrapper(info.getBinaryFile());
            CaseInsensitivePathWrapper srcPath = new CaseInsensitivePathWrapper(new Path(info.getSourceFile())); 
            binaryFilesToImageLists.put(binPath, srcPath);
            imageListsToBinaryFiles.put(srcPath, binPath); 
        }
        return info;
    }
*/
    /**
     * Find or load the multi-image info for the given binary file (cached)
     * @param binaryPath system-relative path
     * @return info for the multi-image file
     */
    public MultiImageInfo getInfoForBinaryFile(String binaryPath) {
    	return getInfoForBinaryFile(new Path(binaryPath));
    }
    
    /**
     * Get the multi-image info for the AIF file
     * @param projectAifFile project-relative RSS file for AIF or null for default project AIF
     * @return pair of full path to contributing MMP plus IMMPAIFInfo, or null
     */
    public Pair<IPath, IMMPAIFInfo> getInfoForAifFile(IPath projectAifFile) {
    	if (projectAifFile == null) {
    		if (aifFileInfoMap.size() > 0) {
	    		// try for one named after the executable
	        	for (Map.Entry<IPath, Pair<IPath, IMMPAIFInfo>> entry : aifFileInfoMap.entrySet()) {
	        		if (entry.getKey().removeFileExtension().lastSegment().equalsIgnoreCase(
	        				targetNameBase)) {
	        			return entry.getValue();
	        		}
	        	}

	        	// else, get the first one
    			return aifFileInfoMap.values().iterator().next();
    		}
    		return null;
    	}
    	
    	for (Map.Entry<IPath, Pair<IPath, IMMPAIFInfo>> entry : aifFileInfoMap.entrySet()) {
    		if (entry.getKey().toOSString().equalsIgnoreCase(
    				projectAifFile.toOSString())) {
    			return entry.getValue();
    		}
    	}
    	return null;
    }

    /**
     * Tell whether the project info changed
     */
    public boolean isDirty() {
        return dirty;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
     */
    /*
    public void resourceChanged(IResourceChangeEvent event) {
        IResourceDelta delta = event.getDelta();
        if (delta != null) {
            if (trackedProject == null || trackedProject.getProject() == null)
                return;
            try {
	            // notice anything changing anywhere in the project
	            if (delta.findMember(trackedProject.getProject().getFullPath()) != null) {
	            	delta.accept(new IResourceDeltaVisitor() {
	
						public boolean visit(IResourceDelta delta) throws CoreException {
			            	IPath path = delta.getProjectRelativePath();
			            	if (path != null && path.segmentCount() > 0 &&
			            			IMAGE_LIKE_FILE_MATCHER.matcher(path.lastSegment()).matches()) {
			            		dirty = true;
			            		fireDirty();
			            		return false;
			            	}
							return true;
						}
	            	}, 
	            	IResourceDelta.CHANGED+IResourceDelta.ADDED+IResourceDelta.REMOVED);
	            }
            } catch (CoreException e) {
            	
            }
        }
    }*/
    
    /**
     * Get the array of project images which are available to build
     * @return array, never null
     */
    public IImageModel[] getProjectImageModels() {
    	return (IImageModel[]) projectImageModels.toArray(new IImageModel[projectImageModels.size()]);
    }
    
	/**
	 * Refresh the stored data
	 */
	public void refresh() {
        //ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		initProjectInformation();
	}

	/**
	 * @return
	 */
	public IImageLoader getImageLoader() {
		return imageLoader;
	}

	/**
	 * @return
	 */
	public IImageConverterFactory getImageConverterFactory() {
		return imageConverterFactory;
	}

	/**
	 * @return
	 */
	public ICarbideProjectInfo getCarbideProjectInfo() {
		return carbideProjectInformation;
	}

	/**
	 * Get the model that supports images living on the filesystem.
	 * @return
	 */
	public IImageContainerModel getExternalImageContainerModel() {
		return externalImageContainerModel;
	}
	
	/**
	 * Tell whether the project has any build configurations that support SVG.
	 * @return
	 */
	public boolean supportsSVG() {
		return supportsSVG;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageContainerModelListener#changed(com.nokia.carbide.cpp.ui.images.IImageContainerModel)
	 */
	public synchronized void changed(IImageContainerModel model) {
		dirty = true;
		fireDirty();
	}
	
}
