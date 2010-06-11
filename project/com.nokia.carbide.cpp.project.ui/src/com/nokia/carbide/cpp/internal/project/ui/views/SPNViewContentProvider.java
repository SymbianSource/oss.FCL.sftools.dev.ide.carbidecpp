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
package com.nokia.carbide.cpp.internal.project.ui.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.CoreModelUtil;
import org.eclipse.cdt.core.model.ElementChangedEvent;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementDelta;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.IElementChangedListener;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.core.model.ExternalTranslationUnit;
import org.eclipse.cdt.ui.CElementGrouping;
import org.eclipse.cdt.ui.CElementSorter;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.nokia.carbide.cdt.builder.BldInfViewPathHelper;
import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.DefaultImageMakefileViewConfiguration;
import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cdt.builder.DefaultViewConfiguration;
import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cdt.builder.EpocEnginePathHelper;
import com.nokia.carbide.cdt.builder.ImageMakefileViewPathHelper;
import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideConfigurationChangedListener;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectPropertyChangedListener;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cpp.epoc.engine.BldInfDataRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.ImageMakefileDataRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.MMPDataRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource;
import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSourceReference;
import com.nokia.carbide.cpp.epoc.engine.image.IImageSource;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileData;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPData;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefaultModelDocumentProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefaultTranslationUnitProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.DependencyScanner;

/**
 * Content provider for the SymbianProjectNavigatorView
 * 
 */
public class SPNViewContentProvider extends BaseWorkbenchContentProvider 
									implements ICarbideConfigurationChangedListener, ICarbideProjectPropertyChangedListener, IAdapterFactory, IElementChangedListener {
	
	private TreeViewer viewer;

	private boolean filteringEnabled = true;
	private boolean displaySourcePaths = true;
	
	// these are used to save/restore view state
	private static final String TAG_SELECTION = "selection"; //$NON-NLS-1$
	private static final String TAG_EXPANDED = "expanded"; //$NON-NLS-1$
	private static final String TAG_IPROJECT = "iproject"; //$NON-NLS-1$
	private static final String TAG_IFILE = "ifile"; //$NON-NLS-1$
	private static final String TAG_ITRANSLATIONUNIT = "itranslationunit"; //$NON-NLS-1$
	private static final String TAG_CONTAINER = "spn_container"; //$NON-NLS-1$
	private static final String TAG_PATH = "path"; //$NON-NLS-1$
	private static final String TAG_CONTAINER_SEPARATOR = "#"; //$NON-NLS-1$
	private static final String TAG_FILTERING_ENABLED = "filtering"; //$NON-NLS-1$
	private static final String TAG_DISPLAYSOURCEPATHS_ENABLED = "display_sourcepaths"; //$NON-NLS-1$
	private static final String TAG_SORTING_ENABLED = "sorting"; //$NON-NLS-1$

	private SPNContainerFactory containerFactory = new SPNContainerFactory();

	private class SPNViewSorter extends CElementSorter {
		public SPNViewSorter() {
			super();
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			
			// use the CDT sorter for CDT objects
			if (e1 instanceof ICElement || e1 instanceof CElementGrouping ||
					e2 instanceof ICElement || e2 instanceof CElementGrouping) {
				return super.compare(viewer, e1, e2);
			}

			// sort mmp objects if necessary
			if (sortMMPsAplhabetically) {
				if (e1 instanceof SPNMMPContainer && e2 instanceof SPNMMPContainer) {
					return (((SPNMMPContainer)e1).getLabel(null).compareToIgnoreCase(((SPNMMPContainer)e2).getLabel(null)));
				}
			}
			
			// leave them in current order
			return 0;
		}
	}
	
	private SPNViewSorter viewSorter = new SPNViewSorter();
	private boolean sortMMPsAplhabetically = false;
	
	
	public SPNViewContentProvider () {
		super();
		this.viewer = null;
	}
	
	
	SPNViewContentProvider(TreeViewer viewer) {
		super();
		this.viewer = viewer;

		CarbideBuilderPlugin.addBuildConfigChangedListener(this);
		CarbideBuilderPlugin.addProjectPropertyChangedListener(this);
        CoreModel.getDefault().addElementChangedListener(this);

		IAdapterManager manager = Platform.getAdapterManager();
		manager.registerAdapters(this, IResource.class);
	}

	@Override
	public void dispose() {
		super.dispose();
		CarbideBuilderPlugin.removeBuildConfigChangedListener(this);
		CarbideBuilderPlugin.removeProjectPropertyChangedListener(this);
        CoreModel.getDefault().removeElementChangedListener(this);

		IAdapterManager manager = Platform.getAdapterManager();
		manager.unregisterAdapters(this);
	}
	
	public ViewerSorter getViewSorter() {
		return viewSorter;
	}


	/**
	 * Factory used to create and keep track of container instances.  In order to restore selection and
	 * expansion of container elements in the tree we have use the same instance of a container (Object.equals
	 * is not enough!).  
	 */
	private class SPNContainerFactory {
		// maps to keep track of existing containers so we don't create more than one instance
		private HashMap<IFile, SPNBldInfContainer> bldInfContainerMap = new LinkedHashMap<IFile, SPNBldInfContainer>();
		private HashMap<IFile, SPNBldInfIncludesContainer> bldinfIncludesContainerMap = new LinkedHashMap<IFile, SPNBldInfIncludesContainer>();
		private HashMap<IFile, SPNMMPContainer> mmpContainerMap = new LinkedHashMap<IFile, SPNMMPContainer>();
		private HashMap<IFile, SPNImageMakefileContainer> imageMakefileContainerMap = new LinkedHashMap<IFile, SPNImageMakefileContainer>();
		private HashMap<IFile, SPNUIDesignsContainer> uiDesignsContainerMap = new LinkedHashMap<IFile, SPNUIDesignsContainer>();
		private HashMap<IFile, SPNSourcesContainer> sourcesContainerMap = new LinkedHashMap<IFile, SPNSourcesContainer>();
		private HashMap<IFile, SPNSourcePathsContainer> sourcePathsContainerMap = new LinkedHashMap<IFile, SPNSourcePathsContainer>();
		private HashMap<IFile, SPNResourcesContainer> resourcesContainerMap = new LinkedHashMap<IFile, SPNResourcesContainer>();
		private HashMap<IFile, SPNIncludesContainer> includesContainerMap = new LinkedHashMap<IFile, SPNIncludesContainer>();
		private HashMap<IFile, SPNImagesContainer> imagesContainerMap = new LinkedHashMap<IFile, SPNImagesContainer>();
		private HashMap<IFile, SPNDocumentsContainer> documentsContainerMap = new LinkedHashMap<IFile, SPNDocumentsContainer>();
		private HashMap<IFile, SPNDefContainer> defContainerMap = new LinkedHashMap<IFile, SPNDefContainer>();
		private HashMap<IFile, SPNAIFContainer> aifContainerMap = new LinkedHashMap<IFile, SPNAIFContainer>();
		private HashMap<IPath, SPNMIFContainer> makefileImageContainerMap = new LinkedHashMap<IPath, SPNMIFContainer>();

		SPNBldInfContainer getBldInfContainer(IFile bldInfFile, boolean create) {
			if (create && !bldInfContainerMap.containsKey(bldInfFile)) {
				bldInfContainerMap.put(bldInfFile, new SPNBldInfContainer(bldInfFile));
			}
			return bldInfContainerMap.get(bldInfFile);
		}

		SPNBldInfIncludesContainer getInfIncludesContainer(IFile infFile) {
			if (!bldinfIncludesContainerMap.containsKey(infFile)) {
				bldinfIncludesContainerMap.put(infFile, new SPNBldInfIncludesContainer(infFile));
			}
			return bldinfIncludesContainerMap.get(infFile);
		}

		SPNMMPContainer getMMPContainer(IFile mmpFile, boolean create) {
			if (create && !mmpContainerMap.containsKey(mmpFile)) {
				mmpContainerMap.put(mmpFile, new SPNMMPContainer(mmpFile));
			}
			return mmpContainerMap.get(mmpFile);
		}

		SPNImageMakefileContainer getImageMakefileContainer(IFile makeFile, boolean create) {
			if (create && !imageMakefileContainerMap.containsKey(makeFile)) {
				imageMakefileContainerMap.put(makeFile, new SPNImageMakefileContainer(makeFile));
			}
			return imageMakefileContainerMap.get(makeFile);
		}

		SPNUIDesignsContainer getUIDesignsContainer(IFile bldInfFile) {
			if (!uiDesignsContainerMap.containsKey(bldInfFile)) {
				uiDesignsContainerMap.put(bldInfFile, new SPNUIDesignsContainer(bldInfFile));
			}
			return uiDesignsContainerMap.get(bldInfFile);
		}

		SPNSourcesContainer getSourcesContainer(IFile mmpFile) {
			if (!sourcesContainerMap.containsKey(mmpFile)) {
				sourcesContainerMap.put(mmpFile, new SPNSourcesContainer(mmpFile));
			}
			return sourcesContainerMap.get(mmpFile);
		}
		
		SPNSourcePathsContainer getSourcePathsContainer(IFile mmpFile) {
			if (!sourcePathsContainerMap.containsKey(mmpFile)) {
				sourcePathsContainerMap.put(mmpFile, new SPNSourcePathsContainer(mmpFile));
			}
			return sourcePathsContainerMap.get(mmpFile);
		}
		
		SPNResourcesContainer getResourcesContainer(IFile mmpFile) {
			if (!resourcesContainerMap.containsKey(mmpFile)) {
				resourcesContainerMap.put(mmpFile, new SPNResourcesContainer(mmpFile));
			}
			return resourcesContainerMap.get(mmpFile);
		}
		
		SPNIncludesContainer getIncludesContainer(IFile mmpFile) {
			if (!includesContainerMap.containsKey(mmpFile)) {
				includesContainerMap.put(mmpFile, new SPNIncludesContainer(mmpFile));
			}
			return includesContainerMap.get(mmpFile);
		}

		SPNImagesContainer getImagesContainer(IFile mmpFile) {
			if (!imagesContainerMap.containsKey(mmpFile)) {
				imagesContainerMap.put(mmpFile, new SPNImagesContainer(mmpFile));
			}
			return imagesContainerMap.get(mmpFile);
		}

		SPNDocumentsContainer getDocumentsContainer(IFile mmpFile) {
			if (!documentsContainerMap.containsKey(mmpFile)) {
				documentsContainerMap.put(mmpFile, new SPNDocumentsContainer(mmpFile));
			}
			return documentsContainerMap.get(mmpFile);
		}

		SPNDefContainer getDefContainer(IFile mmpFile) {
			if (!defContainerMap.containsKey(mmpFile)) {
				defContainerMap.put(mmpFile, new SPNDefContainer(mmpFile));
			}
			return defContainerMap.get(mmpFile);
		}

		SPNAIFContainer getAIFContainer(IFile mmpFile) {
			if (!aifContainerMap.containsKey(mmpFile)) {
				aifContainerMap.put(mmpFile, new SPNAIFContainer(mmpFile));
			}
			return aifContainerMap.get(mmpFile);
		}

		SPNMBMContainer getMBMContainer(IFile mmpFile, IPath mbmPath) {
			SPNImagesContainer container = getImagesContainer(mmpFile);
			if (container != null) {
				return container.getMBMContainer(mbmPath);
			}
			return null;
		}

		SPNMIFContainer getMIFContainer(IFile makeFile, IPath mifPath) {
			if (!makefileImageContainerMap.containsKey(mifPath)) {
				makefileImageContainerMap.put(mifPath, new SPNMIFContainer(makeFile, mifPath));
			}
			return makefileImageContainerMap.get(mifPath);
		}

		public void projectDeleted(IProject project) {
			// mark the containers are needing reparsing - easier than removing entries
			// from all container maps.
			for (IFile file : bldInfContainerMap.keySet()) {
				if (file != null) {
					IProject parentProject = file.getProject();
					if (parentProject != null && parentProject.equals(project)) {
						SPNBldInfContainer container = bldInfContainerMap.get(file);
						container.setNeedsReparse(true);
						break;
					}
				}
			}
		}
	}
	
	
	/**
	 * Abstract class which represents an object that acts as a container, e.g. has children
	 *
	 */
	private abstract class SPNContainer implements IWorkbenchAdapter, IAdaptable, ISPNStatusLineDecorator, ISPNViewRefreshable {
		
		protected boolean needsReparse = true;
		private String typeString;
		protected List<Object> children = new ArrayList<Object>(0);
		
		
		SPNContainer(String typeString) {
			this.typeString = typeString;
		}
		
		public Object[] getChildren(Object o) {
			if (needsReparse) {
				synchronized(children) {
					children.clear();
					children.addAll(getChildren());
				}
				needsReparse = false;
			}
			return children.toArray();
		}

		protected void setChildren(List<Object> newChildren) {
			synchronized(children) {
				children.clear();
				children.addAll(newChildren);
			}
		}

		protected abstract List<Object> getChildren();
		
		protected abstract String getTagString();

		public void setNeedsReparse(boolean recurse) {
			if (recurse) {
				// call setNeedsReparse on children, iff it has children.
				if (children.size() > 0) {
					for (Object o : getChildren(null)) {
						if (o instanceof SPNContainer) {
							((SPNContainer)o).setNeedsReparse(recurse);
						}
					}
				}
			}
			needsReparse = true;			
		}
		
		public String getTypeString() {
			return typeString;
		}

		public Object getParent(Object o) {
			return null;
		}
		
		public Object getAdapter(Class adapter) {
			if (IWorkbenchAdapter.class.equals(adapter)) {
				return this;
			}
			return null;
		}
		
		public void refresh() {
			setNeedsReparse(true);
		}
	}

	/**
	 * Abstract class which represents an IFile that acts as a container, e.g. has children
	 *
	 */
	private abstract class SPNFileContainer extends SPNContainer {

		protected static final String BLD_INF_CONTAINER_TYPE = "BLD_INF_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String MMP_CONTAINER_TYPE = "MMP_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String IMAGE_MAKEFILE_CONTAINER_TYPE = "IMAGE_MAKEFILE_CONTAINER_TYPE"; //$NON-NLS-1$

		protected IFile file;

		
		/**
		 * Constructor.
		 * 
		 * @param file the file acting as a container (bld.inf, mmp or make file)
		 * @param typeString the type string defining what type of file container this is
		 * 			 see {@link #BLD_INF_CONTAINER_TYPE}, {@link #MMP_CONTAINER_TYPE}, {@link #IMAGE_MAKEFILE_CONTAINER_TYPE}
		 */
		SPNFileContainer(IFile file, String typeString) {
			super(typeString);
			this.file = file;
		}
		
		public ImageDescriptor getImageDescriptor(Object object) {
			Object o = file.getAdapter(IWorkbenchAdapter.class);
			if (o != null) {
				return ((IWorkbenchAdapter)o).getImageDescriptor(file);
			}
			return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		}

		public String getLabel(Object o) {
			return file.getName();
		}
		
		public Object getAdapter(Class adapter) {
			if (IResource.class.equals(adapter) || IFile.class.equals(adapter)) {
				return file;
			} else if (IProject.class.equals(adapter)) {
				return file.getProject();
			}
			return super.getAdapter(adapter);
		}

		@Override
		public String toString() {
			if (file != null) {
				return file.getFullPath().toOSString();
			}
			return super.toString();
		}

		public String getStatusLineText() {
			return file.getFullPath().toOSString();
		}

		@Override
		public void refresh() {
			super.refresh();
			// checks for file system changes
			if (file != null) {
				IProject project = file.getProject();
				if (project != null) {
					try {
						project.refreshLocal(IResource.DEPTH_INFINITE, null);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}

	/**
	 * Abstract class which represents an IFolder that acts as a container, e.g. has children
	 *
	 */
	private abstract class SPNFolderContainer extends SPNContainer {

		protected static final String UIDESIGNS_CONTAINER_TYPE = "UIDESIGNS_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String SOURCES_CONTAINER_TYPE = "SOURCES_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String SOURCEPATHS_CONTAINER_TYPE = "SOURCEPATHS_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String RESOURCES_CONTAINER_TYPE = "RESOURCES_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String INCLUDES_CONTAINER_TYPE = "INCLUDES_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String IMAGES_CONTAINER_TYPE = "IMAGES_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String DOCUMENTS_CONTAINER_TYPE = "DOCUMENTS_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String DEF_CONTAINER_TYPE = "DEF_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String MBM_CONTAINER_TYPE = "MBM_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String MIF_CONTAINER_TYPE = "MAKEFILE_IMAGE_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String AIF_CONTAINER_TYPE = "AIF_CONTAINER_TYPE"; //$NON-NLS-1$
		protected static final String BLD_INF_INCLUDES_CONTAINER_TYPE = "BLD_INF_INCLUDES_CONTAINER_TYPE"; //$NON-NLS-1$

		protected IFile parentFile;
		protected String name;

		
		/**
		 * Main constructor.
		 * 
		 * @param parentFile the parent file acting as a container (bld.inf, mmp or make file)
		 * @param typeString the type string defining what type of file container this is
		 * 			 see {@link #UIDESIGNS_CONTAINER_TYPE},
		 *               {@link #SOURCES_CONTAINER_TYPE},
		 *               {@link #RESOURCES_CONTAINER_TYPE},
		 *               {@link #INCLUDES_CONTAINER_TYPE},
		 * 			     {@link #IMAGES_CONTAINER_TYPE},
		 *               {@link #DOCUMENTS_CONTAINER_TYPE},
		 *               {@link #DEF_CONTAINER_TYPE},
		 *               {@link #MBM_CONTAINER_TYPE},
		 *               {@link #MIF_CONTAINER_TYPE}
		 */
		SPNFolderContainer(IFile parentFile, String typeString, String name) {
			super(typeString);
			this.parentFile = parentFile;
			this.name = name;
		}
		
		public ImageDescriptor getImageDescriptor(Object object) {
			return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
		}

		public String getLabel(Object o) {
			return name;
		}

		public Object getAdapter(Class adapter) {
			if (IProject.class.equals(adapter)) {
				return parentFile.getProject();
			}
			return super.getAdapter(adapter);
		}

		@Override
		public String toString() {
			return name;
		}
		
		public String getStatusLineText() {
			return name + Messages.SPNViewContentProvider_ForText + parentFile.getFullPath().toOSString();
		}

		@Override
		public void setNeedsReparse(boolean recurse) {
			needsReparse = true;			
		}

		@Override
		public void refresh() {
			super.refresh();
			// checks for file system changes
			if (parentFile != null) {
				IProject project = parentFile.getProject();
				if (project != null) {
					try {
						project.refreshLocal(IResource.DEPTH_INFINITE, null);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	/**
	 * Class which represents an IFile that does not exist.  It's listed in the bld.inf or mmp file but
	 * does not actually exist in the file system.
	 *
	 */
	private class SPNNonExistentFile implements IWorkbenchAdapter, IAdaptable, ISPNStatusLineDecorator, ISPNViewRefreshable {

		protected IFile file;

		
		/**
		 * Constructor.
		 * 
		 * @param file the file that does not actually exist
		 */
		SPNNonExistentFile(IFile file) {
			this.file = file;
		}
		
		public ImageDescriptor getImageDescriptor(Object object) {
			return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_WARN_TSK);
		}

		public String getLabel(Object o) {
			return file.getName();
		}
		
		public Object getAdapter(Class adapter) {
			if (IWorkbenchAdapter.class.equals(adapter)) {
				return this;
			} else if (IProject.class.equals(adapter)) {
				return file.getProject();
			}
			return null;
		}

		@Override
		public String toString() {
			if (file != null) {
				return file.getFullPath().toOSString();
			}
			return super.toString();
		}

		public Object[] getChildren(Object o) {
			return new Object[0];
		}

		public Object getParent(Object o) {
			return null;
		}

		public String getStatusLineText() {
			return file.getFullPath().toOSString() + Messages.SPNViewContentProvider_DoesNotExist;
		}

		public void refresh() {
			IProject project = file.getProject();
			refreshProject(project);
			if (viewer != null) {
				viewer.refresh(CoreModel.getDefault().create(project));
			}
		}
		
	}

	/**
	 * Class which represents a file that is external to the workspace.
	 *
	 */
	private class SPNNonWorkspaceFile implements IWorkbenchAdapter, IAdaptable, ISPNStatusLineDecorator, INonWorkspaceFileEntry {

		protected File file;

		
		/**
		 * Constructor.
		 * 
		 * @param file the file that does not actually exist
		 */
		SPNNonWorkspaceFile(File file) {
			this.file = file;
		}
		
		public ImageDescriptor getImageDescriptor(Object object) {
			return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		}

		public String getLabel(Object o) {
			return file.getName();
		}
		
		public Object getAdapter(Class adapter) {
			if (IWorkbenchAdapter.class.equals(adapter)) {
				return this;
			}
			return null;
		}

		@Override
		public String toString() {
			if (file != null) {
				return file.getAbsolutePath();
			}
			return super.toString();
		}

		public Object[] getChildren(Object o) {
			return new Object[0];
		}

		public Object getParent(Object o) {
			return null;
		}

		public String getStatusLineText() {
			return file.getAbsolutePath();
		}
		
		public IPath getAbsolutePath() {
			return new Path(file.getAbsolutePath());
		}
	}

	/**
	 * Class which represents a bld.inf file whose children will be mmp and/or make files
	 *
	 */
	private class SPNBldInfContainer extends SPNFileContainer {
		
		SPNBldInfContainer(IFile file) {
			super(file, BLD_INF_CONTAINER_TYPE);
		}

		@Override
		protected String getTagString() {
			return BLD_INF_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + file.getFullPath();
		}

		@Override
		public List<Object> getChildren() {
			final List<Object> objects = new ArrayList<Object>(0);
	        final IFile modelFile = this.file;
			
			// schedule a job to fetch the children.  note that this is asynchronous so the
			// bld.inf container will be refreshed at the end of the job
			Job bldInfChildrenJob = new Job(Messages.SPNViewContentProvider_FetchingBldInfChildrenJob) {

				@Override
				protected IStatus run(final IProgressMonitor monitor) {
					final IProject project = file.getProject();
			        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

			        if (cpi == null || cpi.getDefaultConfiguration() == null) {
						monitor.done();
						return Status.OK_STATUS;
			        }

					EpocEnginePlugin.runWithBldInfData(cpi.getWorkspaceRelativeBldInfPath(), 
						new DefaultViewConfiguration(project, cpi.getDefaultConfiguration().getBuildContext(), new AcceptedNodesViewFilter()), 
						new BldInfDataRunnableAdapter() {

							public Object run(IBldInfData view) {
								// get the list of files referenced via #include
								IPath[] refFilePaths = view.getReferencedFiles();
								if (refFilePaths.length > 1) {
									SPNBldInfIncludesContainer container = new SPNBldInfIncludesContainer(modelFile);
									for (IPath filePath : refFilePaths) {
										monitor.worked(1);
										if (monitor.isCanceled()) {
											return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
										}

										IFile file = getIFileFromBldInfViewPath(project, filePath);
										if (file != null) {
											// exclude the model file
											if (file.equals(modelFile))
												continue;

											container.addIncludeFile(file);
										}
										else {
											container.handleFileNotFound(filePath);
										}
									}
									objects.add(container);
								}
								
								// get the list of mmp files
								IMMPReference refs[] = view.getAllMMPReferences();
								for (IMakMakeReference ref : refs) {
									monitor.worked(1);
									if (monitor.isCanceled()) {
										return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
									}

									IFile file = getIFileFromBldInfViewPath(project, ref.getPath());
									if (file != null) {
										if (!filteringEnabled || (cpi.isBuildingFromInf() && cpi.isBuildingTestComps())) {
											// show everything
											if (file.exists()) {
												objects.add(containerFactory.getMMPContainer(file, true));
											} else {
												objects.add(new SPNNonExistentFile(file));
											}
										} else {
											// only show what's actually being built
											if (cpi.isBuildingFromInf()) {
												// filter out test components
												List<IMakMakeReference> filteredMMPs = view.getMakMakeReferences();
												for (IMakMakeReference mmp : filteredMMPs) {
													// ignore the case as the file system case may not be the case
													// listed in the bld.inf file.
													if (mmp.getPath().equals(ref.getPath())) {
														if (file.exists()) {
															objects.add(containerFactory.getMMPContainer(file, true));
														} else {
															objects.add(new SPNNonExistentFile(file));
														}
														break;
													}
												}
											} else {
												// filter out anything not selected to be built
												List<String> filteredMMPs = cpi.getInfBuildComponents();
												for (String mmp : filteredMMPs) {
													// ignore the case as the file system case may not be the case
													// listed in the bld.inf file.
													if (mmp.equalsIgnoreCase(ref.getPath().lastSegment())) {
														if (file.exists()) {
															objects.add(containerFactory.getMMPContainer(file, true));
														} else {
															objects.add(new SPNNonExistentFile(file));
														}
														break;
													}
												}
											}
										}
									} else {
						    			fileNotFound(ref.getPath(), objects);
									}
								}

								// get the list of make files
								IMakefileReference makefiles[] = view.getAllMakefileReferences();
								for (IMakefileReference makefile : makefiles) {						
									monitor.worked(1);
									if (monitor.isCanceled()) {
										return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
									}

									final IFile file = getIFileFromBldInfViewPath(project, makefile.getPath());
									if (file != null) {
										boolean addToList = false;
										if (!filteringEnabled || (cpi.isBuildingFromInf() && cpi.isBuildingTestComps())) {
											// show everything
											addToList = true;
										} else {
											// only show what's actually being built
											if (cpi.isBuildingFromInf()) {
												// filter out test components
												List<IMakMakeReference> filteredMakes = view.getMakMakeReferences();
												for (IMakMakeReference make : filteredMakes) {
													// ignore the case as the file system case may not be the case
													// listed in the bld.inf file.
													if (make.getPath().equals(makefile.getPath())) {
														addToList = true;
														break;
													}
												}
											} else {
												// filter out anything not selected to be built
												List<String> filteredMakes = cpi.getInfBuildComponents();
												for (String make : filteredMakes) {
													// ignore the case as the file system case may not be the case
													// listed in the bld.inf file.
													if (make.equalsIgnoreCase(makefile.getPath().lastSegment())) {
														addToList = true;
														break;
													}
												}
											}
										}

										if (addToList) {
											// see if this is an image make file.  if so add it to the list of
											// image make files so we can expand it to show the images.
											EpocEnginePlugin.runWithImageMakefileData(file.getFullPath(),
												new DefaultImageMakefileViewConfiguration(cpi, new AcceptedNodesViewFilter()),
												new ImageMakefileDataRunnableAdapter() {

													public Object failedLoad(CoreException exception) {
														// not an image make file
														if (file.exists()) {
															objects.add(file);
														} else {
															objects.add(new SPNNonExistentFile(file));
														}
														return null;
													}

													public Object run(IImageMakefileData data) {
														// if we get here then it is an image make file
														if (file.exists()) {
															objects.add(containerFactory.getImageMakefileContainer(file, true));
														} else {
															objects.add(new SPNNonExistentFile(file));
														}
														return null;
													}
											});
										}
									} else {
						    			fileNotFound(makefile.getPath(), objects);
									}
								}
								
								// get the list of extension makefiles
								BldInfViewPathHelper pathHelper = new BldInfViewPathHelper(view, 
										cpi.getDefaultConfiguration().getBuildContext());
								
								Set<IPath> visitedPaths = new HashSet<IPath>();
										
								IExtension[] extensions = view.getAllExtensions();
								for (IExtension extension : extensions) {						
									monitor.worked(1);
									if (monitor.isCanceled()) {
										return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
									}

									IPath baseExtensionMakefilePath = pathHelper.convertExtensionTemplateToFilesystem(extension.getTemplatePath());
									if (baseExtensionMakefilePath == null)
										continue;

									// extensions can reference the same file many times
									if (visitedPaths.contains(baseExtensionMakefilePath))
										continue;
									
									visitedPaths.add(baseExtensionMakefilePath);
									
									IPath extensionMakefilePath = baseExtensionMakefilePath.addFileExtension("mk");
									IPath extensionMetaPath = baseExtensionMakefilePath.addFileExtension("meta");

									addBldInfExtensionMakefile(objects, project, cpi, view,
											extensionMakefilePath);
									addBldInfExtensionMakefile(objects, project, cpi, view,
											extensionMetaPath);
								}
								return null;
							}

							private void addBldInfExtensionMakefile(
									final List<Object> objects,
									final IProject project,
									final ICarbideProjectInfo cpi,
									IBldInfData view,
									IPath targetPath) {
								// extension makefiles are always in EPOCROOT, but who knows,
								// the user may have linked EPOC32 into the workspace, so
								// give it a shot
								final IFile file = getIFileFromBldInfViewPath(project, targetPath);
								if (file != null) {
									// TODO: handle the filtering of extension makefiles when that's supported
									boolean addToList = true;
									if (addToList) {
										if (file.exists()) {
											objects.add(file);
										} else {
											objects.add(new SPNNonExistentFile(file));
										}
									}
								} else {
									fileNotFound(targetPath, objects);
								}
							}
					});
					
					// add the .pkg file if any
		    		ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
		    		if (buildConfig != null) {
		    			List<ISISBuilderInfo> sisInfoList = buildConfig.getSISBuilderInfoList();
		    			if (sisInfoList != null && sisInfoList.size() > 0) {
		    				for (ISISBuilderInfo sisInfo : sisInfoList) {
			    				IPath pkgPath = sisInfo.getPKGFullPath();
			    				if (pkgPath != null && pkgPath.toFile().exists()) {
			    					IFile pkgFile = getIFileForLocation(project, pkgPath);
			    					if (pkgFile != null) {
			    						objects.add(pkgFile);
			    					} else {
			    						fileNotFound(pkgPath, objects);
			    					}
			    				}
		    				}
		    			}
		    		}
					
					// we need to set the children of SPNContainer now since this is done is a job and getChildren
					// always returns an empty list.
					setChildren(objects);

					// Are we in the UIThread? If so spin it until we are done
					if (viewer != null) {
						if (viewer.getControl().getDisplay().getThread() == Thread.currentThread()) {
							viewer.refresh(SPNBldInfContainer.this);
							viewer.setExpandedState(SPNBldInfContainer.this, true);
						} else {
							viewer.getControl().getDisplay().asyncExec(new Runnable(){
								public void run() {
									viewer.refresh(SPNBldInfContainer.this);
									viewer.setExpandedState(SPNBldInfContainer.this, true);
								}
							});
						}
					}
					
					monitor.done();
					return Status.OK_STATUS;
				}
				
			};
			bldInfChildrenJob.setUser(true);
			bldInfChildrenJob.schedule();			

    		return objects;
		}

	}

	/**
	 * Class which represents the files referenced from a bld.inf file using #include
	 *
	 */
	private class SPNBldInfIncludesContainer extends SPNFolderContainer {

		private List<Object> includeFiles;

		SPNBldInfIncludesContainer(IFile infFile) {
			super(infFile, BLD_INF_INCLUDES_CONTAINER_TYPE, Messages.SPNViewContentProvider_BldInfIncludes);
			includeFiles = new ArrayList<Object>(0);
		}

		public void addIncludeFile (IFile includeFile) {
			if (includeFile.exists()) {
				this.includeFiles.add(includeFile);				
			}
			else {
				this.includeFiles.add(new SPNNonExistentFile(includeFile));
			}
		}

		@Override
		protected String getTagString() {
			return BLD_INF_INCLUDES_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath();
		}

		@Override
		protected List<Object> getChildren() {
			return includeFiles;
		}

		public void handleFileNotFound(IPath filePath) {
			fileNotFound(filePath, includeFiles);
		}

	}
	
	/**
	 * Class which represents an mmp file whose children will be source, resource, includes and images folders
	 *
	 */
	private class SPNMMPContainer extends SPNFileContainer {
		
		SPNMMPContainer(IFile file) {
			super(file, MMP_CONTAINER_TYPE);
		}

		@Override
		protected String getTagString() {
			return MMP_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + file.getFullPath();
		}

		@Override
		public void setNeedsReparse(boolean recurse) {
			if (recurse) {
				// call setNeedsReparse on all possible children
				SPNSourcesContainer sourcesContainer = containerFactory.getSourcesContainer(file);
				if (sourcesContainer != null) {
					sourcesContainer.setNeedsReparse(recurse);
				}
				SPNResourcesContainer resourcesContainer = containerFactory.getResourcesContainer(file);
				if (resourcesContainer != null) {
					resourcesContainer.setNeedsReparse(recurse);
				}
				SPNImagesContainer imagesContainer = containerFactory.getImagesContainer(file);
				if (imagesContainer != null) {
					imagesContainer.setNeedsReparse(recurse);
				}
				SPNIncludesContainer includesContainer = containerFactory.getIncludesContainer(file);
				if (includesContainer != null) {
					includesContainer.setNeedsReparse(recurse);
				}
				SPNDocumentsContainer documentsContainer = containerFactory.getDocumentsContainer(file);
				if (documentsContainer != null) {
					documentsContainer.setNeedsReparse(recurse);
				}
				SPNDefContainer defContainer = containerFactory.getDefContainer(file);
				if (defContainer != null) {
					defContainer.setNeedsReparse(recurse);
				}
				SPNAIFContainer aifContainer = containerFactory.getAIFContainer(file);
				if (aifContainer != null) {
					aifContainer.setNeedsReparse(recurse);
				}
			}
			needsReparse = true;			
		}

		@Override
		public List<Object> getChildren() {
			final List<Object> objects = new ArrayList<Object>(0);

			// schedule a job to fetch the children.  note that this is asynchronous so the
			// bld.inf container will be refreshed at the end of the job
			Job mmpChildrenJob = new Job(Messages.SPNViewContentProvider_FetchingMMPChildrenJob + file.getName()) {

				@Override
				protected IStatus run(final IProgressMonitor monitor) {
					// see if there are any sources
					SPNSourcesContainer sourcesContainer = containerFactory.getSourcesContainer(file);
					if (sourcesContainer != null) {
						if (sourcesContainer.getChildren(null).length > 0) {
							objects.add(sourcesContainer);
						}
					}
					monitor.worked(1);
					if (monitor.isCanceled()) {
						return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
					}

					// see if there are any resources
					SPNResourcesContainer resourcesContainer = containerFactory.getResourcesContainer(file);
					if (resourcesContainer != null) {
						if (resourcesContainer.getChildren(null).length > 0) {
							objects.add(resourcesContainer);
						}
					}
					monitor.worked(1);
					if (monitor.isCanceled()) {
						return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
					}

					// see if there are any images
					SPNImagesContainer imagesContainer = containerFactory.getImagesContainer(file);
					if (imagesContainer != null) {
						if (imagesContainer.getChildren(null).length > 0) {
							objects.add(imagesContainer);
						}
					}
					monitor.worked(1);
					if (monitor.isCanceled()) {
						return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
					}
					
					// since it can be time consuming to get the include files just add
					// the includes container no matter what.
					objects.add(containerFactory.getIncludesContainer(file));
					
					// see if there are any documents
					SPNDocumentsContainer documentsContainer = containerFactory.getDocumentsContainer(file);
					if (documentsContainer != null) {
						if (documentsContainer.getChildren(null).length > 0) {
							objects.add(documentsContainer);
						}
					}
					monitor.worked(1);
					if (monitor.isCanceled()) {
						return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
					}

					// see if there are any def files
					SPNDefContainer defContainer = containerFactory.getDefContainer(file);
					if (defContainer != null) {
						if (defContainer.getChildren(null).length > 0) {
							objects.add(defContainer);
						}
					}
					monitor.worked(1);
					if (monitor.isCanceled()) {
						return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
					}
					
					// see if there are any aif files
					SPNAIFContainer aifContainer = containerFactory.getAIFContainer(file);
					if (aifContainer != null) {
						if (aifContainer.getChildren(null).length > 0) {
							objects.add(aifContainer);
						}
					}
					monitor.worked(1);
					if (monitor.isCanceled()) {
						return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
					}
					
					// we need to set the children of SPNContainer now since this is done is a job and getChildren
					// always returns an empty list.
					setChildren(objects);

					// Are we in the UIThread? If so spin it until we are done
					if (viewer != null) {
						if (viewer.getControl().getDisplay().getThread() == Thread.currentThread()) {
							viewer.refresh(SPNMMPContainer.this);
							viewer.setExpandedState(SPNMMPContainer.this, true);
						} else {
							viewer.getControl().getDisplay().asyncExec(new Runnable(){
								public void run() {
									viewer.refresh(SPNMMPContainer.this);
									viewer.setExpandedState(SPNMMPContainer.this, true);
								}
							});
						}
					}
					
					monitor.done();
					return Status.OK_STATUS;
				}
				
			};
			mmpChildrenJob.setUser(true);
			mmpChildrenJob.schedule();			

			return objects;
		}
	}

	/**
	 * Class which represents an image make file whose children will be image folders
	 *
	 */
	private class SPNImageMakefileContainer extends SPNFileContainer {
	
		SPNImageMakefileContainer(IFile file) {
			super(file, IMAGE_MAKEFILE_CONTAINER_TYPE);
		}
	
		@Override
		protected String getTagString() {
			return IMAGE_MAKEFILE_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + file.getFullPath();
		}
	
		@Override
		public List<Object> getChildren() {
			final List<Object> objects = new ArrayList<Object>(0);
	
			IProject project = file.getProject();
	        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			
			EpocEnginePlugin.runWithImageMakefileData(file.getFullPath(), 
					new DefaultImageMakefileViewConfiguration(cpi, new AcceptedNodesViewFilter()), 
					new ImageMakefileDataRunnableAdapter() {
	
					public Object run(IImageMakefileData data) {
						List<IMultiImageSource> images = data.getMultiImageSources();
						for (IMultiImageSource image : images) {
							objects.add(containerFactory.getMIFContainer(file, image.getTargetFilePath()));
						}
						return null;
					}
			});
	
			return objects;
		}
	}

	/**
	 * Class which represents the sources folder whose children will be all sources for the current build config of a project
	 *
	 */
	private class SPNUIDesignsContainer extends SPNFolderContainer {
		
		SPNUIDesignsContainer(IFile bldInfFile) {
			super(bldInfFile, UIDESIGNS_CONTAINER_TYPE, Messages.SPNViewContentProvider_UIDesigns);
		}

		@Override
		protected String getTagString() {
			return UIDESIGNS_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath();
		}

		@Override
		protected List<Object> getChildren() {
			final List<Object> designs = new ArrayList<Object>(0);
			
			// if a project has UI designs then there must be at least one at the project root.  if there
			// is one or more at the root then we need to search the entire project recursively.
			IPath projectDirPath = parentFile.getProject().getLocation();
			if (projectDirPath != null) {
				File projectDir = projectDirPath.toFile();
				if (projectDir.isDirectory() && projectDir.exists()) {
					boolean hasUIDesigns = false;
					for (File child : projectDir.listFiles()) {
						if (child.isFile() && child.getName().toLowerCase().endsWith(".uidesign")) { //$NON-NLS-1$
							hasUIDesigns = true;
							break;
						}
					}
					
					if (hasUIDesigns) {
						findUIDesignFilesInDirectory(projectDir, designs);
					}
				}
			}
			
			return designs;
		}
		
		private void findUIDesignFilesInDirectory(File directory, List<Object> files) {
			for (File child : directory.listFiles()) {
				if (child.isDirectory()) {
					findUIDesignFilesInDirectory(child, files);
				} else if (child.isFile()) {
					if (child.getName().toLowerCase().endsWith(".uidesign")) { //$NON-NLS-1$
						IFile file = getIFileForLocation(this.parentFile.getProject(), new Path(child.getAbsolutePath()));
						if (file != null) {
							files.add(file);
						}
					}
				}
			}
		}
	}

	/**
	 * Class which represents all sources for the current build config of a project.
	 * If the flag "displaySourcePaths" is enabled, source files will be group under
	 * SOURCEPATH folders.
	 *
	 */
	private class SPNSourcesContainer extends SPNFolderContainer {
		
		SPNSourcesContainer(IFile mmpFile) {
			super(mmpFile, SOURCES_CONTAINER_TYPE, Messages.SPNViewContentProvider_Sources);
		}

		@Override
		protected String getTagString() {
			return SOURCES_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath();
		}

		@Override
		protected List<Object> getChildren() {
			final List<Object> sources = new ArrayList<Object>(0);
			
			final IProject project = parentFile.getProject();
	        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

	        if (cpi == null || cpi.getDefaultConfiguration() == null)
	        	return sources;

	        final ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
	        final IFile mmpFile = parentFile;
			EpocEnginePlugin.runWithMMPData(parentFile.getFullPath(), 
					new DefaultMMPViewConfiguration(project, cpi.getDefaultConfiguration().getBuildContext(), new AcceptedNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					public Object run(IMMPData data) {
						if (defaultConfig != null) {
							MMPViewPathHelper helper = new MMPViewPathHelper(defaultConfig);

							if (displaySourcePaths) {
								// add SOURCEPATH folders to children list
								List <SPNSourcePathsContainer> containerList = new ArrayList<SPNSourcePathsContainer> (0);
								for (IPath path : data.getRealSourcePaths()) {
									IPath fullPath = helper.convertMMPToFilesystem(EMMPPathContext.SOURCEPATH, path);
									SPNSourcePathsContainer container = new SPNSourcePathsContainer(mmpFile, path, fullPath);
									containerList.add(container);
								}
								sources.addAll(containerList);

								// try to group source files into SOURCEPATH folders
								for (IPath src : data.getSources()) {
									IPath fullPath = helper.convertMMPToFilesystem(EMMPPathContext.SOURCE, src);
									SPNSourcePathsContainer container = findSourcePathContainer(containerList, fullPath);
									if (container != null) {
										// add source file to children list of matching SOURCEPATH folder 
										container.addSourceFile(src);
									}
									else {
										// since no matching SOURCEPATH folder is found, 
										// add source file to children list as is
										addMMPViewPathFileToList(defaultConfig, src, EMMPPathContext.SOURCE, sources, true);
									}
								}
							}
							else {
								// add source files to children list
								for (IPath src : data.getSources()) {
									addMMPViewPathFileToList(defaultConfig, src, EMMPPathContext.SOURCE, sources, true);
								}
							}
						}
						return null;
					}
			});
			
			return sources;
		}
	}

	/**
	 * Class which represents SOURCEPATH folder whose children will be all sources 
	 * found in a given SOURCEPATH for the current build config of a project
	 *
	 */
	private class SPNSourcePathsContainer extends SPNFolderContainer {

		private IPath fullPath;
		private List<IPath> sourceFiles;

		/**
		 * Main constructor.
		 * @param mmpFile the parent mmp file acting as a container 
		 * @param sourcePath the source path declared in a SOURCEPATH statement
		 * @param fullPath the full source path
		 */
		SPNSourcePathsContainer(IFile mmpFile, IPath sourcePath, IPath fullPath) {
			// use ".." when sourcePath is empty
			super(mmpFile, SOURCEPATHS_CONTAINER_TYPE, sourcePath.isEmpty() ? ".." : sourcePath.toOSString());
			this.fullPath = fullPath;
			sourceFiles = new ArrayList<IPath>(0);
		}

		SPNSourcePathsContainer(IFile mmpFile) {
			super(mmpFile, SOURCEPATHS_CONTAINER_TYPE, Messages.SPNViewContentProvider_SourcePaths);
			this.fullPath = null;
			sourceFiles = new ArrayList<IPath>(0);
		}
		
		/**
		 * Add to list of source files belonging to this folder.
		 * @param sourceFile incoming source file
		 */
		public void addSourceFile(IPath sourceFile) {
			this.sourceFiles.add(sourceFile);
		}

		/**
		 * Retrieve the full path of this SOURCEPATH folder.
		 */
		public IPath getFullPath() {
			return fullPath;
		}

		@Override
		protected String getTagString() {
			return SOURCEPATHS_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath();
		}

		@Override
		protected List<Object> getChildren() {
			final List<Object> sources = new ArrayList<Object>(0);
			
			final IProject project = parentFile.getProject();
	        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

	        if (cpi == null || cpi.getDefaultConfiguration() == null)
	        	return sources;

			final ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
			EpocEnginePlugin.runWithMMPData(parentFile.getFullPath(), 
					new DefaultMMPViewConfiguration(project, cpi.getDefaultConfiguration().getBuildContext(), new AcceptedNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					public Object run(IMMPData view) {
						if (defaultConfig != null) {
							for (IPath sourceFile : sourceFiles) {
								addMMPViewPathFileToList(defaultConfig, sourceFile, EMMPPathContext.SOURCE, sources, true);
							}
						}
						return null;
					}
			});

			return sources;
		}

		@Override
		public Object getAdapter(Class adapter) {
			if (IResource.class.equals(adapter)) {
				EpocEnginePathHelper helper = new EpocEnginePathHelper(parentFile.getProject());
				if (fullPath != null) {
					// handle the case where source path is the same as project path
					if (fullPath.equals(parentFile.getProject().getLocation())) {
						return parentFile.getProject();
					}
					else {
						IPath workspacePath = helper.convertFilesystemToWorkspace(fullPath);
						if (workspacePath != null) {
							IFolder folder = parentFile.getProject().getWorkspace().getRoot().getFolder(workspacePath);
							if (folder != null && folder.exists()) {
								return folder;
							}
						}
					}
				}
			}
			return super.getAdapter(adapter);
		}
	}

	/**
	 * Class which represents the resources folder whose children will be all resources for the current build config of a project
	 *
	 */
	private class SPNResourcesContainer extends SPNFolderContainer {

		SPNResourcesContainer(IFile mmpFile) {
			super(mmpFile, RESOURCES_CONTAINER_TYPE, Messages.SPNViewContentProvider_Resources);
		}

		@Override
		protected String getTagString() {
			return RESOURCES_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath();
		}

		@Override
		protected List<Object> getChildren() {
			final List<Object> resources = new ArrayList<Object>(0);

			final IProject project = parentFile.getProject();
	        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			
	        if (cpi == null || cpi.getDefaultConfiguration() == null)
	        	return resources;
	        
			EpocEnginePlugin.runWithMMPData(parentFile.getFullPath(), 
					new DefaultMMPViewConfiguration(project, cpi.getDefaultConfiguration().getBuildContext(), new AcceptedNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					public Object run(IMMPData view) {
						// check new style resource blocks
						List<IMMPResource> resourceBlocks = view.getResourceBlocks();
						for (IMMPResource block : resourceBlocks) {
							addMMPViewPathFileToList(cpi.getDefaultConfiguration(), block.getSource(), EMMPPathContext.START_RESOURCE, resources, true);
						}

						// check old style resource statements
						List<IPath> resourceFiles = view.getUserResources();
						for (IPath res : resourceFiles) {
							addMMPViewPathFileToList(cpi.getDefaultConfiguration(), res, EMMPPathContext.RESOURCE, resources, true);
						}

						// check system resource statements
						List<IPath> systemResourceFiles = view.getSystemResources();
						for (IPath res : systemResourceFiles) {
							addMMPViewPathFileToList(cpi.getDefaultConfiguration(), res, EMMPPathContext.SYSTEMRESOURCE, resources, true);
						}

						return null;
					}
			});

			return resources;
		}
	}

	/**
	 * Class which represents the includes folder whose children will be all header files for the current build config
	 * of a project that are contained under the project root (not in epoc32\include)
	 *
	 */
	private class SPNIncludesContainer extends SPNFolderContainer {

		private class IncludeListFileLocator implements IIncludeFileLocator {
			private List<File> includeDirs = new ArrayList<File>(0);

			public IncludeListFileLocator() {
			}

			public void setIncludeDirs(List<File> includeDirs) {
				this.includeDirs = includeDirs;
			}
			
			public File findIncludeFile(String file, boolean isUser, File currentDir) {
				File target;
				if (currentDir != null) {
					target = new File(currentDir, file);
					if (target.exists()) {
						try {
							target = target.getCanonicalFile();
						} catch (IOException e) {
						}
						return target;
					}
				}
				for (File dir : includeDirs) {
					target = new File(dir, file);
					if (target.exists()) {
						try {
							target = target.getCanonicalFile();
						} catch (IOException e) {
						}
						return target;
					}
				}
				return null;
			}
			
			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator#getSystemPaths()
			 */
			public File[] getSystemPaths() {
				return new File[0];
			}
			
			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator#getUserPaths()
			 */
			public File[] getUserPaths() {
				return (File[]) includeDirs.toArray(new File[includeDirs.size()]);
			}
		}
		

		SPNIncludesContainer(IFile mmpFile) {
			super(mmpFile, INCLUDES_CONTAINER_TYPE, Messages.SPNViewContentProvider_Includes);
		}

		@Override
		protected String getTagString() {
			return INCLUDES_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath();
		}

		@Override
		protected List<Object> getChildren() {
			// schedule a job to fetch the includes.  note that this is asynchronous so the
			// includes container will be refreshed at the end of the job
			Job includesScannerJob = new Job(Messages.SPNViewContentProvider_ScanningForIncludes) {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					Set<String> includeDirPaths = new HashSet<String>();

					// get a list of sources and resources to scan
					final List<File> sourcesToScan = new ArrayList<File>();
					SPNSourcesContainer sourcesContainer = containerFactory.getSourcesContainer(parentFile);
					if (sourcesContainer != null) {
						sourcesToScan.addAll(gatherFiles(sourcesContainer));
					}

					SPNResourcesContainer resourcesContainer = containerFactory.getResourcesContainer(parentFile);
					if (resourcesContainer != null) {
						sourcesToScan.addAll(gatherFiles(resourcesContainer));
					}

					monitor.beginTask("", sourcesToScan.size() + 1); //$NON-NLS-1$
					
					final IProject project = parentFile.getProject();

					// figure out the epoc32/include directory for filtering
					IPath sdkEpoc32IncludeDir = null;
					ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
					if (cpi != null) {
						ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
						if (buildConfig != null) {
							ISymbianSDK sdk = buildConfig.getSDK();
							if (sdk != null) {
								sdkEpoc32IncludeDir = sdk.getIncludePath();
							}
						}
					}

					if (sourcesToScan.size() > 0) {
						IncludeListFileLocator locator = new IncludeListFileLocator();
						DependencyScanner scanner = new DependencyScanner(locator, DefaultTranslationUnitProvider.getInstance(), DefaultModelDocumentProvider.getInstance());
						
						final List<File> includeDirs = getIncludeDirectories(sdkEpoc32IncludeDir);
						locator.setIncludeDirs(includeDirs);
						
						for (File file : sourcesToScan) {
							monitor.setTaskName(file.getAbsolutePath());
							
							scanner.scanFile(file);

							monitor.worked(1);
							if (monitor.isCanceled()) {
								return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
							}
						}

						for (File file : scanner.getUserIncludes()) {
							includeDirPaths.add(file.toString());
						}
						for (File file : scanner.getSystemIncludes()) {
							includeDirPaths.add(file.toString());
						}
					}
					
					List<Object> includes = new ArrayList<Object>(0);
					for (String inc : includeDirPaths) {
						IPath incFilePath = new Path(inc);
						if (sdkEpoc32IncludeDir != null && !sdkEpoc32IncludeDir.isPrefixOf(incFilePath)) {
							IFile incFile = getIFileForLocation(project, incFilePath);
							if (incFile != null && incFile.exists()) {
								ITranslationUnit tu = CoreModelUtil.findTranslationUnit(incFile);
								includes.add(tu != null ? tu : incFile);
							} else {
								// try to create an external translation unit for it
								String id = CoreModel.getRegistedContentTypeId(project, incFilePath.lastSegment());
								if (id != null) {
									includes.add(new ExternalTranslationUnit(CoreModel.getDefault().create(project), URIUtil.toURI(incFilePath), id));
								} else {
					    			fileNotFound(incFilePath, includes);
								}
							}
						}
						monitor.worked(1);
						if (monitor.isCanceled()) {
							return new Status(IStatus.CANCEL, ProjectUIPlugin.PLUGIN_ID, IStatus.CANCEL, "", null); //$NON-NLS-1$
						}
					}
					
					// we need to set the children of SPNContainer now since this is done is a job and getChildren
					// always returns an empty list.
					setChildren(includes);

					//Are we in the UIThread? If so spin it until we are done
					if (viewer.getControl().getDisplay().getThread() == Thread.currentThread()) {
						viewer.refresh(SPNIncludesContainer.this);
						viewer.setExpandedState(SPNIncludesContainer.this, true);
					} else {
						viewer.getControl().getDisplay().asyncExec(new Runnable(){
							public void run() {
								viewer.refresh(SPNIncludesContainer.this);
								viewer.setExpandedState(SPNIncludesContainer.this, true);
							}
						});
					}
					
					monitor.done();
					return Status.OK_STATUS;
				}
				
			};
			includesScannerJob.setUser(true);
			includesScannerJob.schedule();			

			return new ArrayList<Object>(0);
		}
		
		private List<File> getIncludeDirectories(final IPath sdkEpoc32IncludeDir) {
			final List<File> includes = new ArrayList<File>(0);
			
			final IProject project = parentFile.getProject();
	        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

	        if (cpi == null || cpi.getDefaultConfiguration() == null)
	        	return includes;

			EpocEnginePlugin.runWithMMPData(parentFile.getFullPath(), 
					new DefaultMMPViewConfiguration(project, cpi.getDefaultConfiguration().getBuildContext(), new AcceptedNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					private MMPViewPathHelper helper = new MMPViewPathHelper(cpi.getDefaultConfiguration());
					
					public Object failedLoad(CoreException exception) {
						return null;
					}

					public Object run(IMMPData view) {
						gatherIncludes(includes, view.getUserIncludes(), EMMPPathContext.USERINCLUDE);
						gatherIncludes(includes, view.getSystemIncludes(), EMMPPathContext.SYSTEMINCLUDE);
						return null;
					}

					private void gatherIncludes(List<File> includes, List<IPath> includePaths, EMMPPathContext context) {
						for (IPath inc : includePaths) {
							
							IPath path = helper.convertMMPToFilesystem(context, inc);
							// ignore paths from epoc32/include
							if (path != null && sdkEpoc32IncludeDir != null && !sdkEpoc32IncludeDir.isPrefixOf(path)) {
								includes.add(path.toFile());
							}
						}
						
					}
			});

			return includes;	
		}
		
		private List<File> gatherFiles(SPNFolderContainer container) {
			final List<File> filesToScan = new ArrayList<File>();
			for (Object src : container.getChildren(null)) {
				if (src instanceof IFile) {
					filesToScan.add(((IFile)src).getFullPath().toFile());
                } else if (src instanceof ITranslationUnit) {
                	ITranslationUnit tu = (ITranslationUnit)src;
                	IPath location = tu.getLocation();
                	if (location != null) {
                		filesToScan.add(location.toFile());
                	}
                } else if (src instanceof SPNSourcePathsContainer) {
                	filesToScan.addAll(gatherFiles((SPNSourcePathsContainer)src));
                }
			}
			return filesToScan;
		}
	}

	/**
	 * Class which represents the images folder whose children will be all mbm's for the current build config of a project
	 *
	 */
	private class SPNImagesContainer extends SPNFolderContainer {

		private HashMap<IPath, SPNMBMContainer> mbmContainerMap = new LinkedHashMap<IPath, SPNMBMContainer>();

		
		SPNImagesContainer(IFile mmpFile) {
			super(mmpFile, IMAGES_CONTAINER_TYPE, Messages.SPNViewContentProvider_Images);
		}

		@Override
		protected String getTagString() {
			return IMAGES_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath();
		}

		@Override
		protected List<Object> getChildren() {
			final List<Object> mbmContainers = new ArrayList<Object>(0);

			IProject project = parentFile.getProject();
	        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

	        if (cpi == null || cpi.getDefaultConfiguration() == null)
	        	return mbmContainers;

			EpocEnginePlugin.runWithMMPData(parentFile.getFullPath(), 
					new DefaultMMPViewConfiguration(project, cpi.getDefaultConfiguration().getBuildContext(), new AcceptedNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					public Object run(IMMPData view) {
						// check new style resource blocks
						List<IMultiImageSource> mbms = view.getMultiImageSources();
						for (IMultiImageSource mbm : mbms) {
							mbmContainers.add(containerFactory.getMBMContainer(parentFile, mbm.getTargetFilePath()));
						}
						return null;
					}
			});

			return mbmContainers;
		}
		
		SPNMBMContainer getMBMContainer(IPath mbmPath) {
			if (!mbmContainerMap.containsKey(mbmPath)) {
				mbmContainerMap.put(mbmPath, new SPNMBMContainer(parentFile, mbmPath));
			}
			return mbmContainerMap.get(mbmPath);
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.carbide.cpp.internal.project.ui.views.SPNViewContentProvider.SPNFolderContainer#getAdapter(java.lang.Class)
		 */
		@SuppressWarnings("unchecked") //$NON-NLS-1$
		@Override
		public Object getAdapter(Class adapter) {
			// allow action contributions to work on the MMP of this pseudo container
			if (adapter.isAssignableFrom(IResource.class))
				return parentFile;
			return super.getAdapter(adapter);
		}

		@Override
		public void setNeedsReparse(boolean recurse) {
			// this is the only SPNFolderContainer that can have
			// containers as children, so we need to mark them as
			// needs reparse.
			if (recurse) {
				// call setNeedsReparse on children, iff it has children.
				if (children.size() > 0) {
					for (Object o : getChildren(null)) {
						if (o instanceof SPNContainer) {
							((SPNContainer)o).setNeedsReparse(recurse);
						}
					}
				}
			}
			needsReparse = true;			
		}
	}

	/**
	 * Class which represents the documents folder whose children will be all documents for the current build config of a project
	 *
	 */
	private class SPNDocumentsContainer extends SPNFolderContainer {
		
		SPNDocumentsContainer(IFile mmpFile) {
			super(mmpFile, DOCUMENTS_CONTAINER_TYPE, Messages.SPNViewContentProvider_Documents);
		}

		@Override
		protected String getTagString() {
			return DOCUMENTS_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath();
		}

		@Override
		protected List<Object> getChildren() {
			final List<Object> documents = new ArrayList<Object>(0);
			
			final IProject project = parentFile.getProject();
	        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

	        if (cpi == null || cpi.getDefaultConfiguration() == null)
	        	return documents;

			EpocEnginePlugin.runWithMMPData(parentFile.getFullPath(), 
					new DefaultMMPViewConfiguration(project, cpi.getDefaultConfiguration().getBuildContext(), new AcceptedNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					public Object run(IMMPData view) {
						List<IPath> docs = view.getDocuments();
						ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
						if (defaultConfig != null) {
							for (IPath doc : docs) {
								addMMPViewPathFileToList(defaultConfig, doc, EMMPPathContext.DOCUMENT, documents, false);
							}
						}
						return null;
					}
			});
			
			return documents;
		}
	}

	/**
	 * Class which represents the def folder whose child will be the def file of the current build config of a project, if any
	 *
	 */
	private class SPNDefContainer extends SPNFolderContainer {
		
		SPNDefContainer(IFile mmpFile) {
			super(mmpFile, DEF_CONTAINER_TYPE, Messages.SPNViewContentProvider_DefFile);
		}

		@Override
		protected String getTagString() {
			return DEF_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath();
		}

		@Override
		protected List<Object> getChildren() {
			final List<Object> defs = new ArrayList<Object>(0);
			
			final IProject project = parentFile.getProject();
	        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

	        if (cpi == null || cpi.getDefaultConfiguration() == null)
	        	return defs;

			EpocEnginePlugin.runWithMMPData(parentFile.getFullPath(), 
					new DefaultMMPViewConfiguration(project, cpi.getDefaultConfiguration().getBuildContext(), new AcceptedNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					public Object run(IMMPData view) {
						IPath defFile = view.getDefFile();
						if (defFile != null) {
							ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
							if (defaultConfig != null) {
								addMMPViewPathFileToList(defaultConfig, defFile, EMMPPathContext.DEFFILE, defs, false);
							}
						}
						return null;
					}
			});
			
			return defs;
		}
	}

	/**
	 * Class which represents an mbm folder whose children will be all bitmaps used in the mbm
	 *
	 */
	private class SPNMBMContainer extends SPNFolderContainer implements IMBMFileEntry {

		private IPath targetFilePath;

		SPNMBMContainer(IFile mmpFile, IPath mbmPath) {
			super(mmpFile, MBM_CONTAINER_TYPE, mbmPath.toString());
			this.targetFilePath = mbmPath;
		}
		
		@Override
		protected String getTagString() {
			return MBM_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath() + TAG_CONTAINER_SEPARATOR + name;
		}

		public String getLabel(Object o) {
			return targetFilePath.lastSegment();
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			return CarbideUIPlugin.getSharedImages().getImageDescriptor(
					ICarbideSharedImages.IMG_MBM_FILE_16_16);
		}
		
		@Override
		protected List<Object> getChildren() {
			final List<Object> mbmSources = new ArrayList<Object>(0);

			final IProject project = parentFile.getProject();
	        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

	        if (cpi == null || cpi.getDefaultConfiguration() == null)
	        	return mbmSources;

			EpocEnginePlugin.runWithMMPData(parentFile.getFullPath(), 
					new DefaultMMPViewConfiguration(project, cpi.getDefaultConfiguration().getBuildContext(), new AcceptedNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					public Object run(IMMPData view) {
						// check start bitmap blocks
						List<IMultiImageSource> mbms = view.getMultiImageSources();
						for (IMultiImageSource mbm : mbms) {
							if (mbm.getTargetFilePath().equals(targetFilePath)) {
								List<IImageSource> imgSources = mbm.getSources();
								for (IImageSource src : imgSources) {
									addMMPViewPathFileToList(cpi.getDefaultConfiguration(), src.getPath(), EMMPPathContext.START_BITMAP_SOURCE, mbmSources, false);
									if (src instanceof IBitmapSource) {
										IBitmapSource bmSrc = (IBitmapSource) src;
										if (bmSrc.getMaskPath() != null) {
											addMMPViewPathFileToList(cpi.getDefaultConfiguration(), bmSrc.getMaskPath(), EMMPPathContext.START_BITMAP_SOURCE, mbmSources, false);
										}
									}
								}
							}
						}
						return null;
					}
			});

			return mbmSources;
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.carbide.cpp.internal.project.ui.views.IMBMMIFFileEntry#getModelFile()
		 */
		public IFile getModelFile() {
			return parentFile;
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.carbide.cpp.internal.project.ui.views.IMBMMIFFileEntry#getTargetFilePath()
		 */
		public IPath getTargetFilePath() {
			return targetFilePath;
		}
	}

	/**
	 * Class which represents an make file image folder whose children will be all bitmaps and/or svg files used in the image
	 *
	 */
	private class SPNMIFContainer extends SPNFolderContainer implements IMIFFileEntry {

		private IPath targetFilePath;
		SPNMIFContainer(IFile makeFile, IPath targetFilePath) {
			super(makeFile, MIF_CONTAINER_TYPE, targetFilePath.toString());
			this.targetFilePath = targetFilePath;
		}
		
		@Override
		protected String getTagString() {
			return MIF_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath() + TAG_CONTAINER_SEPARATOR + name;
		}

		public String getLabel(Object o) {
			return targetFilePath.lastSegment();
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			return CarbideUIPlugin.getSharedImages().getImageDescriptor(
					ICarbideSharedImages.IMG_MIF_FILE_16_16);
		}

		@Override
		protected List<Object> getChildren() {
			final List<Object> imageSources = new ArrayList<Object>(0);

			final IProject project = parentFile.getProject();
	        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			
			EpocEnginePlugin.runWithImageMakefileData(parentFile.getFullPath(), 
					new DefaultImageMakefileViewConfiguration(cpi, new AcceptedNodesViewFilter()), 
					new ImageMakefileDataRunnableAdapter() {

					public Object run(IImageMakefileData data) {
						List<ICarbideBuildConfiguration> configs = cpi.getBuildConfigurations();
						List<ISymbianBuildContext> buildContexs = new ArrayList<ISymbianBuildContext>();
						for (ICarbideBuildConfiguration config : configs){
							buildContexs.add(config.getBuildContext());
						}
						ImageMakefileViewPathHelper helper = new ImageMakefileViewPathHelper(
								data, 
								(ISymbianBuildContext[]) configs.toArray(new ISymbianBuildContext[buildContexs.size()])); 
						List<IMultiImageSource> images = data.getMultiImageSources();
						for (IMultiImageSource image : images) {
							if (image.getTargetFilePath().equals(targetFilePath)) {
								List<IImageSource> imgSources = image.getSources();
								for (IImageSource src : imgSources) {
									addImagePaths(imageSources, project, helper, src);
								}
							}
						}
						return null;
					}

					private void addImagePaths(
								final List<Object> imageSources,
								final IProject project,
								ImageMakefileViewPathHelper helper, IImageSource src) {
						IPath path = src.getPath();
						if (path != null) {
							IPath imagePath = helper.findCandidateImagePath(path);
							if (imagePath != null) {
								if (imagePath.isAbsolute()) {
									// if it's under the project root then we can get an IFile for it
									if (project.getLocation().isPrefixOf(imagePath)) {
										IFile file = project.getFile(imagePath);
										if (file != null) {
											if (file.exists()) {
												imageSources.add(file);
											} else {
												imageSources.add(new SPNNonExistentFile(file));
											}
										} else {
											fileNotFound(imagePath, imageSources);
										}
									} else {
										fileNotFound(imagePath, imageSources);
									}
								} else {
									IFile file = project.getFile(imagePath);
									if (file != null) {
										if (file.exists()) {
											imageSources.add(file);
										} else {
											imageSources.add(new SPNNonExistentFile(file));
										}
									} else {
										fileNotFound(imagePath, imageSources);
									}
								}
							}
						}
						IPath maskPath = helper.findCandidateMaskPath(src);
						if (maskPath != null) {
							if (maskPath.isAbsolute()) {
								// if it's under the project root then we can get an IFile for it
								if (project.getLocation().isPrefixOf(maskPath)) {
									IFile file = project.getFile(maskPath);
									if (file != null) {
										if (file.exists()) {
											imageSources.add(file);
										} else {
											imageSources.add(new SPNNonExistentFile(file));
										}
									} else {
										fileNotFound(maskPath, imageSources);
									}
								} else {
									fileNotFound(maskPath, imageSources);
								}
							} else {
								IFile file = project.getFile(maskPath);
								if (file != null) {
									if (file.exists()) {
										imageSources.add(file);
									} else {
										imageSources.add(new SPNNonExistentFile(file));
									}
								} else {
									fileNotFound(maskPath, imageSources);
								}
							}
						}
					}
			});

			return imageSources;
		}

		/* (non-Javadoc)
		 * @see com.nokia.carbide.cpp.internal.project.ui.views.IMIFFileEntry#getModelFile()
		 */
		public IFile getModelFile() {
			return parentFile;
		}
		/* (non-Javadoc)
		 * @see com.nokia.carbide.cpp.internal.project.ui.views.IMIFFileEntry#getTargetFilePath()
		 */
		public IPath getTargetFilePath() {
			return targetFilePath;
		}
	}

	/**
	 * Class which represents an AIF whose children will be a resource file and possibly images
	 *
	 */
	private class SPNAIFContainer extends SPNFolderContainer {
	
		SPNAIFContainer(IFile mmpFile) {
			super(mmpFile, AIF_CONTAINER_TYPE, Messages.SPNViewContentProvider_Aif);
		}
	
		@Override
		protected String getTagString() {
			return AIF_CONTAINER_TYPE + TAG_CONTAINER_SEPARATOR + parentFile.getFullPath();
		}
	
		@Override
		public List<Object> getChildren() {
			final List<Object> aifs = new ArrayList<Object>(0);
			
			final IProject project = parentFile.getProject();
	        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

	        if (cpi == null || cpi.getDefaultConfiguration() == null)
	        	return aifs;

			EpocEnginePlugin.runWithMMPData(parentFile.getFullPath(), 
					new DefaultMMPViewConfiguration(project, cpi.getDefaultConfiguration().getBuildContext(), new AcceptedNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					public Object run(IMMPData view) {
						List<IMMPAIFInfo> theAifs = view.getAifs();
						for (IMMPAIFInfo aif : theAifs) {
							addMMPViewPathFileToList(cpi.getDefaultConfiguration(), aif.getResource(), EMMPPathContext.AIF_SOURCE, aifs, true);
							for (IBitmapSourceReference bmpRef : aif.getSourceBitmaps()) {
								addMMPViewPathFileToList(cpi.getDefaultConfiguration(), bmpRef.getPath(), EMMPPathContext.AIF_BITMAP, aifs, true);
								
								IPath maskPath = bmpRef.getMaskPath();
								if (maskPath != null) {
									addMMPViewPathFileToList(cpi.getDefaultConfiguration(), maskPath, EMMPPathContext.AIF_BITMAP, aifs, true);
								}
							}
						}
						
						return null;
					}
			});
			
			return aifs;
		}
	}
	
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof SPNIncludesContainer || element instanceof SPNBldInfContainer || element instanceof SPNMMPContainer) {
			// always say that the bld.inf, mmp and includes have children even if they don't yet.
			// we don't want to actually scan for children until they expand the node as these
			// operations can take some time.  we put these potentially long running operations in
			// a job so the user can see the progress and even cancel if they want to.
			return true;
		} else if (element instanceof ICProject) {
			// if the project is open then assume it has children (bld.inf).  we do this because
			// depending on timing, the view could be refreshed after the project is created, but
			// before the ICarbideProjectInfo is fully setup.  this works around a bug where 
			// sometimes after creating a new project it shows up with no children until refreshed.
			if (((ICProject)element).getProject().isAccessible()) {
				return true;
			}
		}
		return super.hasChildren(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object element) {
		if (element instanceof IViewSite || element instanceof IWorkspaceRoot) {
			return getCarbideCppProjects();
		} else if (element instanceof ICProject) {
			return getProjectChildren(((ICProject)element).getProject());
		} else if (element instanceof SPNContainer) {
			return ((SPNContainer)element).getChildren(null);
		}
		
		return super.getChildren(element);
	}
	
	private Object[] getCarbideCppProjects() {
		List<ICProject> list = new ArrayList<ICProject>();
		
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			try {
				// add closed projects because we have no way to tell if they
				// are Carbide.c++ projects or not.
				if (!project.isOpen() || (project.isAccessible() && project.hasNature(CarbideBuilderPlugin.CARBIDE_PROJECT_NATURE_ID))) {
					list.add(CoreModel.getDefault().create(project));
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return list.toArray();
	}

	private Object[] getProjectChildren(IProject project) {
		List<Object> children = new ArrayList<Object>(0);
		
		if (!project.isOpen()) {
			return children.toArray();
		}

		// get the bld.inf file for the project and create a container for it
        ICarbideProjectInfo info = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
        if (info != null) {
    		IPath infPath = info.getProjectRelativeBldInfPath();
    		if (infPath != null) {
        		IFile file = getIFileFromBldInfViewPath(project, infPath);
        		if (file != null) {
        			children.add(containerFactory.getBldInfContainer(file, true));

            		// see if there are any ui designs
            		SPNUIDesignsContainer uiDesignsContainer = containerFactory.getUIDesignsContainer(file);
            		if (uiDesignsContainer != null) {
            			if (uiDesignsContainer.getChildren(null).length > 0) {
            				children.add(uiDesignsContainer);
            			}
            		}
        		} else {
        			ProjectUIPlugin.log(new FileNotFoundException("bld.inf file not found: " + infPath.toOSString())); //$NON-NLS-1$
        		}
    		}
        }

        return children.toArray();
	}

	public boolean doubleClickShouldNotExpand(Object object) {
		if (object instanceof SPNFileContainer || object instanceof IMBMMIFFileEntry || object instanceof ITranslationUnit) {
			return true;
		}
		return false;
	}
	
	public void fileChanged(IFile file) {
		// see if this is one of our bld.inf or mmp or image makefiles.  if so then we
		// need to reparse this container and its children.
		SPNFileContainer container = containerFactory.getBldInfContainer(file, false);
		if (container == null) {
			container = containerFactory.getMMPContainer(file, false);
			if (container == null) {
				container = containerFactory.getImageMakefileContainer(file, false);
			}
		}
		if (container != null) {
			container.setNeedsReparse(true);
			
			final SPNFileContainer refreshMe = container;
			// and now refresh the container node
			if (viewer != null) {
				viewer.getControl().getDisplay().asyncExec(new Runnable() {
					public void run() {
						viewer.refresh(refreshMe);
					}
				});
			}
		}
	}

	public void buildConfigurationChanged(ICarbideBuildConfiguration currentConfig) {
		// when they change the build config of a project we need to refresh the
		// project node as its contents may be different for this build config.
		IPath infPath = currentConfig.getCarbideProject().getWorkspaceRelativeBldInfPath();
		IFile bldInfFile = ResourcesPlugin.getWorkspace().getRoot().getFile(infPath);
		if (bldInfFile != null && bldInfFile.exists()) {
			SPNFileContainer container = containerFactory.getBldInfContainer(bldInfFile, false);
			if (container != null) {
				container.setNeedsReparse(true);
			}
		}

		// now refresh the project node on the main thread
		final ICProject project = CoreModel.getDefault().create(currentConfig.getCarbideProject().getProject());
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (viewer != null) {
					viewer.refresh(project);
				}
			}
		});
		
	}

	public void projectPropertyChanged(ICarbideProjectInfo cpi) {
		// when there is change in project property we need to refresh the
		// project node as its contents may be different for this build config.
		IPath infPath = cpi.getWorkspaceRelativeBldInfPath();
		IFile bldInfFile = ResourcesPlugin.getWorkspace().getRoot().getFile(infPath);
		if (bldInfFile != null && bldInfFile.exists()) {
			SPNFileContainer container = containerFactory.getBldInfContainer(bldInfFile, false);
			if (container != null) {
				container.setNeedsReparse(true);
			}
		}

		// now refresh the project node on the main thread
		final ICProject project = CoreModel.getDefault().create(cpi.getProject());
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (viewer != null) {
					viewer.refresh(project);
				}
			}
		});
	}
	
	public void enableFiltering(boolean enable) {
		if (enable != filteringEnabled) {
			filteringEnabled = enable;
			
			// filtering has changed so we need to refresh all projects.  the only difference
			// though may be the children of the bld.inf as the filter is to show all bld.inf
			// children or only those that will be built.  just set the reparse flag for all
			// bld.inf containers.
			if (viewer != null) {
				for (TreeItem node : viewer.getTree().getItems()) {
					if (node.getData() instanceof ICProject) {
				        ICarbideProjectInfo info = CarbideBuilderPlugin.getBuildManager().getProjectInfo(((ICProject)node.getData()).getProject());
				        if (info != null) {
				    		IPath infPath = info.getWorkspaceRelativeBldInfPath();
							IFile bldInfFile = ResourcesPlugin.getWorkspace().getRoot().getFile(infPath);
							if (bldInfFile != null && bldInfFile.exists()) {
								SPNFileContainer container = containerFactory.getBldInfContainer(bldInfFile, false);
								if (container != null) {
									container.setNeedsReparse(false);
									viewer.refresh(container);
								}
							}
				        }
					}
				}
			}
		}
	}

	public boolean isFilteringEnabled() {
		return filteringEnabled;
	}
	
	public void enableMMPSorting(boolean enable) {
		if (enable != sortMMPsAplhabetically) {
			sortMMPsAplhabetically = enable;
			viewer.refresh();
		}
	}

	public boolean isMMPSortingEnabled() {
		return sortMMPsAplhabetically;
	}

	protected void fileNotFound(IPath filePath, List<Object> list) {
		File file = filePath.toFile();
		if (file != null && file.exists()) {
			list.add(new SPNNonWorkspaceFile(file));
			return;
		}
	
		ProjectUIPlugin.log(new FileNotFoundException("File not found: " + filePath.toOSString())); //$NON-NLS-1$
	}

	protected void addMMPViewPathFileToList(ICarbideBuildConfiguration config, IPath pathFromView, EMMPPathContext context, List<Object> list, boolean translationUnit) {
		MMPViewPathHelper helper = new MMPViewPathHelper(config);
		IPath fullPath = helper.convertMMPToFilesystem(context, pathFromView);
		if (fullPath != null) {
			if (fullPath.toFile().exists()) {
				IFile file = getIFileForLocation(config.getCarbideProject().getProject(), fullPath);
				if (file != null) {
					ITranslationUnit tu = CoreModelUtil.findTranslationUnit(file);
					list.add(translationUnit && tu != null ? tu : file);
				} else {
					if (translationUnit) {
						// try to create an external translation unit for it
						String id = CoreModel.getRegistedContentTypeId(config.getCarbideProject().getProject(), fullPath.lastSegment());
						if (id != null) {
							list.add(new ExternalTranslationUnit(CoreModel.getDefault().create(config.getCarbideProject().getProject()), URIUtil.toURI(fullPath), id));
						} else {
							list.add(new SPNNonWorkspaceFile(fullPath.toFile()));
						}
					} else {
						list.add(new SPNNonWorkspaceFile(fullPath.toFile()));
					}
				}
			} else {
				IPath resolvedProjectRelativePath = helper.convertMMPToProject(context, pathFromView);
				if (resolvedProjectRelativePath != null) {
					list.add(new SPNNonExistentFile(config.getCarbideProject().getProject().getFile(resolvedProjectRelativePath)));
				}
			}
		} else {
			ProjectUIPlugin.log(new FileNotFoundException("File not found: " + pathFromView.toOSString())); //$NON-NLS-1$
		}
	}

	protected IFile getIFileFromBldInfViewPath(IProject project, IPath pathFromBldInfView) {
		EpocEnginePathHelper helper = new EpocEnginePathHelper(project);
		IPath resolvedProjectRelativePath = helper.convertToProject(pathFromBldInfView);
		if (resolvedProjectRelativePath != null)
			return project.getFile(resolvedProjectRelativePath);
		else
			return null;
	}

	public void refreshProject(IProject project) {
		if (!project.isOpen()) {
			return;
		}

		// get the bld.inf file for the project and mark it as needs parsing
        ICarbideProjectInfo info = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
        if (info != null) {
    		IPath infPath = info.getProjectRelativeBldInfPath();
    		if (infPath != null) {
        		IFile file = getIFileFromBldInfViewPath(project, infPath);
        		if (file != null) {
        			SPNBldInfContainer container = containerFactory.getBldInfContainer(file, false);
        			if (container != null) {
        				container.refresh();
        			}

            		// see if there are any ui designs
            		SPNUIDesignsContainer uiDesignsContainer = containerFactory.getUIDesignsContainer(file);
            		if (uiDesignsContainer != null) {
            			uiDesignsContainer.refresh();
            		}
        		}
    		}
        }
	}

	public void projectDeleted(IProject project) {
		// remove any cached info about this project
		containerFactory.projectDeleted(project);
	}
	
	public void saveTreeStateAndSelection(IMemento memento) {
		if (viewer == null) {
			return;
		}

		// save expanded elements
		Object expandedElements[] = viewer.getExpandedElements();
		if (expandedElements.length > 0) {
			IMemento expandedMem = memento.createChild(TAG_EXPANDED);
			for (int i = 0; i < expandedElements.length; i++) {
				Object o = expandedElements[i];
				if (o instanceof ICProject) {
					IMemento elementMem = expandedMem.createChild(TAG_IPROJECT);
					elementMem.putString(TAG_PATH, ((ICProject)o).getProject().getName());
				} else if (o instanceof IFile) {
					IMemento elementMem = expandedMem.createChild(TAG_IFILE);
					elementMem.putString(TAG_PATH, ((IFile)o).getFullPath().toOSString());
				} else if (o instanceof ITranslationUnit) {
					IMemento elementMem = expandedMem.createChild(TAG_ITRANSLATIONUNIT);
					elementMem.putString(TAG_PATH, ((ITranslationUnit)o).getPath().toOSString());
				} else if (o instanceof SPNContainer) {
					IMemento elementMem = expandedMem.createChild(TAG_CONTAINER);
					elementMem.putString(TAG_PATH, ((SPNContainer)o).getTagString());
				}
			}
		}

		// save selection
		Object elements[] = ((IStructuredSelection) viewer.getSelection()).toArray();
		if (elements.length > 0) {
			IMemento selectionMem = memento.createChild(TAG_SELECTION);
			for (int i = 0; i < elements.length; i++) {
				if (elements[i] instanceof ICProject) {
					IMemento elementMem = selectionMem.createChild(TAG_IPROJECT);
					elementMem.putString(TAG_PATH, ((ICProject)elements[i]).getProject().getName());
				} else if (elements[i] instanceof IFile) {
					IMemento elementMem = selectionMem.createChild(TAG_IFILE);
					elementMem.putString(TAG_PATH, ((IFile)elements[i]).getFullPath().toOSString());
				} else if (elements[i] instanceof ITranslationUnit) {
					IMemento elementMem = selectionMem.createChild(TAG_ITRANSLATIONUNIT);
					elementMem.putString(TAG_PATH, ((ITranslationUnit)elements[i]).getPath().toOSString());
				} else if (elements[i] instanceof SPNContainer) {
					IMemento elementMem = selectionMem.createChild(TAG_CONTAINER);
					elementMem.putString(TAG_PATH, ((SPNContainer)elements[i]).getTagString());
				}
			}
		}
		
		memento.putInteger(TAG_FILTERING_ENABLED, filteringEnabled ? 1 : 0);
		memento.putInteger(TAG_DISPLAYSOURCEPATHS_ENABLED, displaySourcePaths ? 1 : 0);
		memento.putInteger(TAG_SORTING_ENABLED, sortMMPsAplhabetically ? 1 : 0);
	}

	public void restoreTreeStateAndSelection(IMemento memento) {
		if (viewer == null) {
			return;
		}

		IMemento childMem = memento.getChild(TAG_EXPANDED);
		if (childMem != null) {
			final List<Object> elements = new ArrayList<Object>();
			for (IMemento project : childMem.getChildren(TAG_IPROJECT)) {
				String path = project.getString(TAG_PATH);
				if (path != null) {
					IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(path);
					if (p != null) {
						elements.add(CoreModel.getDefault().create(p));
					}
				}
			}
			for (IMemento file : childMem.getChildren(TAG_IFILE)) {
				String path = file.getString(TAG_PATH);
				if (path != null) {
					IFile f = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
					if (f != null) {
						elements.add(f);
					}
				}
			}
			for (IMemento file : childMem.getChildren(TAG_ITRANSLATIONUNIT)) {
				String path = file.getString(TAG_PATH);
				if (path != null) {
					IFile f = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
					if (f != null) {
						ICElement element = CoreModel.getDefault().create(f.getFullPath());
						if (element != null) {
							elements.add(element);
						}
					}
				}
			}
			for (IMemento container : childMem.getChildren(TAG_CONTAINER)) {
				String path = container.getString(TAG_PATH);
				if (path != null) {
					SPNContainer c = getSPNContainerFromTag(path);
					if (c != null) {
						elements.add(c);
					}
				}
			}

			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					viewer.setExpandedElements(elements.toArray());
				}
			});
		}
		
		childMem = memento.getChild(TAG_SELECTION);
		if (childMem != null) {
			final List<Object> list = new ArrayList<Object>();
			for (IMemento project : childMem.getChildren(TAG_IPROJECT)) {
				String path = project.getString(TAG_PATH);
				if (path != null) {
					IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(path);
					if (p != null) {
						list.add(CoreModel.getDefault().create(p));
					}
				}
			}
			for (IMemento file : childMem.getChildren(TAG_IFILE)) {
				String path = file.getString(TAG_PATH);
				if (path != null) {
					IFile f = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
					if (f != null) {
						list.add(f);
					}
				}
			}
			for (IMemento file : childMem.getChildren(TAG_ITRANSLATIONUNIT)) {
				String path = file.getString(TAG_PATH);
				if (path != null) {
					IFile f = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
					if (f != null) {
						ICElement element = CoreModel.getDefault().create(f.getFullPath());
						if (element != null) {
							list.add(element);
						}
					}
				}
			}
			for (IMemento container : childMem.getChildren(TAG_CONTAINER)) {
				String path = container.getString(TAG_PATH);
				if (path != null) {
					SPNContainer c = getSPNContainerFromTag(path);
					if (c != null) {
						list.add(c);
					}
				}
			}

			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					viewer.setSelection(new StructuredSelection(list));
				}
			});
		}

		Integer i = memento.getInteger(TAG_FILTERING_ENABLED);
		if (i != null) {
			filteringEnabled = i.intValue() == 1;
		}

		i = memento.getInteger(TAG_DISPLAYSOURCEPATHS_ENABLED);
		if (i != null) {
			displaySourcePaths = i.intValue() == 1;
		}

		i = memento.getInteger(TAG_SORTING_ENABLED);
		if (i != null) {
			sortMMPsAplhabetically = i.intValue() == 1;
		}
	}

	private SPNContainer getSPNContainerFromTag(String tag) {
		String[] elements = tag.split(TAG_CONTAINER_SEPARATOR);
		if (elements.length == 2) {
			// in the form type#fullFilePath
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(elements[1]));
			if (file != null && file.exists()) {
				if (elements[0].equals(SPNFileContainer.BLD_INF_CONTAINER_TYPE)) {
					return containerFactory.getBldInfContainer(file, true);
				} else if (elements[0].equals(SPNFileContainer.MMP_CONTAINER_TYPE)) {
					return containerFactory.getMMPContainer(file, true);
				} else if (elements[0].equals(SPNFileContainer.IMAGE_MAKEFILE_CONTAINER_TYPE)) {
					return containerFactory.getImageMakefileContainer(file, true);
				} else if (elements[0].equals(SPNFolderContainer.UIDESIGNS_CONTAINER_TYPE)) {
					return containerFactory.getUIDesignsContainer(file);
				} else if (elements[0].equals(SPNFolderContainer.SOURCES_CONTAINER_TYPE)) {
					return containerFactory.getSourcesContainer(file);
				} else if (elements[0].equals(SPNFolderContainer.SOURCEPATHS_CONTAINER_TYPE)) {
					return containerFactory.getSourcePathsContainer(file);
				} else if (elements[0].equals(SPNFolderContainer.RESOURCES_CONTAINER_TYPE)) {
					return containerFactory.getResourcesContainer(file);
				} else if (elements[0].equals(SPNFolderContainer.INCLUDES_CONTAINER_TYPE)) {
					return containerFactory.getIncludesContainer(file);
				} else if (elements[0].equals(SPNFolderContainer.IMAGES_CONTAINER_TYPE)) {
					return containerFactory.getImagesContainer(file);
				} else if (elements[0].equals(SPNFolderContainer.DOCUMENTS_CONTAINER_TYPE)) {
					return containerFactory.getDocumentsContainer(file);
				} else if (elements[0].equals(SPNFolderContainer.DEF_CONTAINER_TYPE)) {
					return containerFactory.getDefContainer(file);
				} else if (elements[0].equals(SPNFolderContainer.AIF_CONTAINER_TYPE)) {
					return containerFactory.getAIFContainer(file);
				} else if (elements[0].equals(SPNFolderContainer.BLD_INF_INCLUDES_CONTAINER_TYPE)) {
					return containerFactory.getInfIncludesContainer(file);
				}
			}
		} else if (elements.length == 3) {
			// in the form type#fullFilePath#name
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(elements[1]));
			if (file != null && file.exists()) {
				if (elements[0].equals(SPNFolderContainer.MBM_CONTAINER_TYPE)) {
					return containerFactory.getMBMContainer(file, new Path(elements[2]));
				} else if (elements[0].equals(SPNFolderContainer.MIF_CONTAINER_TYPE)) {
					return containerFactory.getMIFContainer(file, new Path(elements[2]));
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static Class[] PROPERTIES = new Class[] {
		IResource.class
	};

	@SuppressWarnings("unchecked")
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject instanceof SPNContainer) {
			return ((SPNContainer)adaptableObject).getAdapter(adapterType);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Class[] getAdapterList() {
		return PROPERTIES;
	}

	private IFile getIFileForLocation(IProject project, IPath absolutePath) {
		// there could be more than one IFile in the workspace mapped to this file system location.  try
		// to get the one owned by this project.
		IFile file = null;
		IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(absolutePath);
		if (files.length > 0) {
			for (IFile f : files) {
				if (f.getProject().equals(project)) {
					file = f;
					break;
				}
			}
		}
		return file;
	}

	/**
	 * Display or hide SOURCEPATH folders and refresh projects if necessary.
	 * @param enable - flag indicating whether SOURCEPATH folders should be displayed
	 */
	public void enableDisplaySourcePaths(boolean enable) {
		if (enable != displaySourcePaths) {
			displaySourcePaths = enable;
			
			// refresh all projects
			if (viewer != null) {
				for (TreeItem node : viewer.getTree().getItems()) {
					if (node.getData() instanceof ICProject) {
				        ICarbideProjectInfo info = CarbideBuilderPlugin.getBuildManager().getProjectInfo(((ICProject)node.getData()).getProject());
				        if (info != null) {
				    		IPath infPath = info.getWorkspaceRelativeBldInfPath();
							IFile bldInfFile = ResourcesPlugin.getWorkspace().getRoot().getFile(infPath);
							if (bldInfFile != null && bldInfFile.exists()) {
								SPNFileContainer container = containerFactory.getBldInfContainer(bldInfFile, false);
								if (container != null) {
									container.setNeedsReparse(true);
									viewer.refresh(container);
								}
							}
				        }
					}
				}
			}
		}
	}

	/**
	 * Should SOURCEPATH folders be displayed?
	 */
	public boolean isSourcePathsDisplayed() {
		return displaySourcePaths;
	}

	/**
	 * Given a source file, find the best matching SOURCEPATH container.
	 * @param containerList list of available SOURCEPATH containers
	 * @param sourceFile full path of source file
	 * @return SOURCEPATH container if one is found, null otherwise
	 */
	private SPNSourcePathsContainer findSourcePathContainer(List<SPNSourcePathsContainer> containerList, IPath sourceFile) {
		SPNSourcePathsContainer bestMatchContainer = null;
		for (SPNSourcePathsContainer container : containerList) {
			IPath newFullPath = container.getFullPath();
			if ((newFullPath != null) && (newFullPath.isPrefixOf(sourceFile))) {
				if (bestMatchContainer == null) {
					bestMatchContainer = container;
				}
				else {
					IPath oldFullPath = bestMatchContainer.getFullPath();
					if (oldFullPath != null) {
						int oldPathLength = oldFullPath.toString().length();
						int newPathLength = newFullPath.toString().length();
						if (oldPathLength < newPathLength ) {
							bestMatchContainer = container;
						}
					}
					else {
						bestMatchContainer = container;
					}
				}
			}
			
		}
		return bestMatchContainer;
	}

	/**
	 * A CElement has changed.  We only display ITranslationUnit's, so only listen for those changes.
	 */
    public void elementChanged(final ElementChangedEvent event) {
		try {
			processDelta(event.getDelta());
		} catch(CModelException e) {
			ProjectUIPlugin.log(e);
			e.printStackTrace();
		}
	}

	protected void processDelta(ICElementDelta delta) throws CModelException {
		if (delta.getKind() == ICElementDelta.CHANGED) {
			final ICElement element = delta.getElement();
			if (element instanceof ITranslationUnit) {
				if (viewer != null) {
					viewer.getControl().getDisplay().asyncExec(new Runnable() {
						public void run() {
							viewer.refresh(element);
						}
					});
				}
				return;
			}
		}
		
		for (ICElementDelta child : delta.getAffectedChildren()) {
			processDelta(child);
		}
	}
}
