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
package com.nokia.sdt.symbian.workspace.impl;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.datamodel.*;
import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.symbian.SymbianSourceFormatter;
import com.nokia.sdt.symbian.dm.*;
import com.nokia.sdt.symbian.sdk.SdkUtilities;
import com.nokia.sdt.symbian.workspace.ISymbianDataModelSpecifier;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.workspace.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier.IRunWithModelAction;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.File;
import java.util.*;

public class ProjectContext implements IProjectContext, ISymbianProjectContext, ISymbianPrivateProjectContext, ISourceGenModelGatherer {

	private IProject project;
	DesignerDataModelSpecifier rootDesign;
	ArrayList<DesignerDataModelSpecifier> viewDesigns = new ArrayList<DesignerDataModelSpecifier>();
	private Language currentLanguage;
	private ProjectMessageHandler messageHandler;
	private ISourceGenProvider sourceGenProvider;
	
	// ms delay for UI job scheduling for jobs related to design file changes
	private static final int FILE_JOB_DELAY = 100; 
	
	private final static QualifiedName CURRENT_LANGUAGE_KEY = 
		new QualifiedName(SymbianPlugin.PI_NAME, "CURRENT_LANGUAGE");
	/** session property indicating the design has a known context... used only to
	 * filter commands in the UI at the moment.
	 */
	private final static QualifiedName IS_UIDESIGNER_PROJECT_KEY = 
		new QualifiedName(SymbianPlugin.PI_NAME, "IS_UIDESIGNER_PROJECT");
	
	public ProjectContext(IProject project) throws CoreException {
		Check.checkArg(project);
		Check.checkArg(project.isOpen());
		this.project = project;
		this.project.setSessionProperty(IS_UIDESIGNER_PROJECT_KEY, Boolean.TRUE.toString());
		messageHandler = new ProjectMessageHandler(project);
		MessageReporting.addListener(messageHandler);
		refresh(null);
	}
	
	public synchronized void dispose() {
		try {
			this.project.setSessionProperty(IS_UIDESIGNER_PROJECT_KEY, null);
		} catch (CoreException e) {
			// project may be deleted, so ignore error
		}
		for (IDesignerDataModelSpecifier modelSpecifier : viewDesigns) {
			modelSpecifier.dispose();
		}
		viewDesigns.clear();
		if (rootDesign != null) {
			rootDesign.dispose();
			rootDesign = null;
		}
		MessageReporting.removeListener(messageHandler);
		messageHandler = null;
		if (sourceGenProvider != null) {
			sourceGenProvider.dispose();
			sourceGenProvider = null;
		}
	}
		
	private ArrayList<IFile> findViewNXDs()  {
		final ArrayList<IFile> list = new ArrayList();
		IResourceProxyVisitor visitor = new IResourceProxyVisitor() {
			public boolean visit(IResourceProxy proxy) throws CoreException {
				if (SymbianModelUtils.isDesignName(proxy.getName()) && proxy.isAccessible()) {
					IResource r = proxy.requestResource();
					if (r instanceof IFile) {
						list.add((IFile)r);
					}
				}
				return true;
			}
		};
		try {
			project.accept(visitor, IResource.NONE);
		} catch (CoreException x) {
			SymbianPlugin.getDefault().log(x);
		}
		return list;
	}

	private IDesignerDataModelSpecifier getSpecifierForFile(IFile file) {
		IDesignerDataModelSpecifier result = null;
		if (rootDesign != null && rootDesign.getModelFile().equals(file)) {
			result = rootDesign;
		}
		else {
			for (DesignerDataModelSpecifier modelSpecifier : viewDesigns) {
				if (modelSpecifier.getModelFile().equals(file)) {
					result = modelSpecifier;
					break;
				}
			}
		}
		return result;
	}
	
	private IDesignerDataModelSpecifier addSpecifierForFile(IFile file) {
		DesignerDataModelSpecifier result;
		result = new DesignerDataModelSpecifier(file, false);
		viewDesigns.add(result);
		return result;
	}

	public IProject getProject() {
		return project;
	}

	public synchronized IDesignerDataModelSpecifier[] getModelSpecifiers() {
		int size = viewDesigns.size();
		if (rootDesign != null)
			size++;
		
		IDesignerDataModelSpecifier[] result = new IDesignerDataModelSpecifier[size];
		int index = 0;
		if (rootDesign != null) {
			result[0] = rootDesign;
			index = 1;
		}
		for (Iterator iter = viewDesigns.iterator(); iter.hasNext();) {
			IDesignerDataModelSpecifier modelSpecifier = (IDesignerDataModelSpecifier) iter.next();
			result[index++] = modelSpecifier;
		}
		return result;
	}

	public Object getAdapter(Class adapter) {
		Object result = null;
		if (adapter.isInstance(this)) {
			result = this;
		}
		return result;
	}

	public synchronized IDesignerDataModelSpecifier getRootModel() {
		return rootDesign;
	}

	public synchronized IDesignerDataModelSpecifier findSpecifierForPath(IPath relPath) {
		IDesignerDataModelSpecifier result = null;
		// due to case-insensitivity and renaming, the path cannot be directly looked up
		//IResource resource = project.findMember(relPath);
		File designFile = ProjectUtils.getRealProjectLocation(project).append(relPath).toFile();
		IFile file = FileUtils.convertFileToIFile(designFile);
		if (file != null) {
			for (Iterator iter = viewDesigns.iterator(); iter.hasNext();) {
				DesignerDataModelSpecifier specifier = (DesignerDataModelSpecifier) iter.next();
				if (specifier.getModelFile().equals(file)) {
					result = specifier;
					break;
				}
			}
			if (rootDesign != null && rootDesign.getModelFile().equals(file)) {
				result = rootDesign;
			}
			
			// try to create if we should have a specifier but don't yet
			if (result == null && file.exists()) {
				if (SymbianModelUtils.isDesignName(file.getName())) {
					if (file.equals(getRootModelFile()) ) {
						rootDesign = (DesignerDataModelSpecifier) createNewRootModelSpecifier();
						result = rootDesign;
					} else {
						DesignerDataModelSpecifier spec = new DesignerDataModelSpecifier(file, false);
						viewDesigns.add(spec);
						result = spec;
					}
				}
			}
		}
		return result;
	}

	public IDesignerDataModelSpecifier createNewModelSpecifier(IFile modelFile) {
		// check if there's already a specifier for this file
		IPath relPath = modelFile.getProjectRelativePath();
		IDesignerDataModelSpecifier modelSpecifier = findSpecifierForPath(relPath);
		if (modelSpecifier != null)
			throw new IllegalArgumentException(modelFile.toString());
		
		return new DesignerDataModelSpecifier(modelFile, false);
	}
	
	public IDesignerDataModelSpecifier createNewRootModelSpecifier() {
		if (rootDesign != null) return null;
		IFile modelFile = getRootModelFile();
		IDesignerDataModelSpecifier result = new DesignerDataModelSpecifier(
				modelFile, true);
		return result;
	}
	
	public void addModel(IDesignerDataModelSpecifier newModel) {
		Check.checkArg(newModel instanceof DesignerDataModelSpecifier);
		ISymbianDataModelSpecifier sms = (ISymbianDataModelSpecifier) newModel.getAdapter((ISymbianDataModelSpecifier.class));
		
		IFile modelFile = sms.getModelFile();
		IPath relPath = modelFile.getProjectRelativePath();
		if (sms.isRoot()) {
			if (rootDesign != null)
				throw new IllegalStateException();
			else
				rootDesign = (DesignerDataModelSpecifier) newModel;
		}
		else {
			IDesignerDataModelSpecifier modelSpecifier = findSpecifierForPath(relPath);
			if (modelSpecifier != null)
				throw new IllegalArgumentException(modelFile.toString());
			viewDesigns.add((DesignerDataModelSpecifier) newModel);
		}
	}

	public boolean refresh(IResourceDelta delta) {
		// Create new specifiers for newly detected models. We don't remove view model
		// specifiers here or dispose of specifiers. The specifiers are listening for
		// changes to their own files and will remove and dispose of themselves as
		// appropriate. For example, when a view design is renamed the user might want
		// to update the app model to refer to the renamed file.
		boolean designsChanged = false;
		boolean sourceChanged = false;
		if (delta != null) {
			Pair<Boolean, Boolean> changes = refreshFromDelta(delta);
			designsChanged = changes.first;
			sourceChanged = changes.second;
		} else {
			designsChanged = refreshNoDelta();
		}
		
		if (designsChanged) {
			// clean up sourcegen state
			//if (sourceGenProvider != null)
			//	sourceGenProvider.updateProjectInfo();
		}
		if (sourceChanged) {
			// scan for source changes
			if (sourceGenProvider != null) {
				sourceGenProvider.fireSourceChanged(false);
			}
		}
		return designsChanged;
	}
	
	/**
	 * Visitor to handle refreshes of the project context. Only
	 * needs to handle new and renamed models. DataModelSpecifiers listen
	 * for changes to their own files and will remove themselves when deleted.
	 *
	 */
	class ResourceDeltaVisitor implements IResourceDeltaVisitor {
		boolean anyChanges;
		boolean anySourceChanges;
		IWorkspaceRoot root;
		IFile rootModelFile;
		
		ResourceDeltaVisitor() {
			root = ResourcesPlugin.getWorkspace().getRoot();
			rootModelFile = getRootModelFile();
		}

		public boolean visit(IResourceDelta delta) throws CoreException {
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				processFileAdded(delta);
				break;
			case IResourceDelta.CHANGED:
				processFileChanged(delta);
				break;
			case IResourceDelta.REMOVED:
				processFileRemoved(delta);
				break;
			}
			return true;
		}
		
		private void processFileAdded(IResourceDelta delta) {
			if (delta.getResource() instanceof IFile) {
				IFile newFile = (IFile) delta.getResource();
				boolean newIsDesign = SymbianModelUtils.isDesignName(newFile.getName());
				DesignerDataModelSpecifier specifier = null;
				if ((delta.getFlags() & IResourceDelta.MOVED_FROM) != 0) {
					// May be a renamed model, don't create new specifier
					// not that designs moved from other projects won't be updated, 
					// just those within a project. Cross-project moves are treated
					// the same as a remove in the old project and add in the new project.
					IFile oldFile = root.getFile(delta.getMovedFromPath());
					specifier = (DesignerDataModelSpecifier) getSpecifierForFile(oldFile);
					if (specifier != null) {
						DesignFileMovedJob job = new DesignFileMovedJob(specifier, delta.getMovedFromPath());
						if (specifier.isRoot()) {
							Check.checkState(specifier == rootDesign);
							// rename root design to some other name
							rootDesign = null;
							specifier = null;
							anyChanges = true;
						} else if (newIsDesign) {
							// rename view design, still a design name
							specifier.updateFile(newFile);
						} else {
							// was a design, is no longer
							removeSpecifier(specifier);
							// update for temporary use by the job
							specifier.updateFile(newFile);
							anyChanges = true;
						}
						
						job.schedule(FILE_JOB_DELAY);
					}
				}
					
				if (specifier == null && newIsDesign) {
					specifier = (DesignerDataModelSpecifier) getSpecifierForFile(newFile);
					if (specifier == null) {
						if (newFile.equals(rootModelFile)) {
							specifier = (DesignerDataModelSpecifier) createNewRootModelSpecifier();
						} else {
							addSpecifierForFile(newFile);
						}
						anyChanges = true;
					}
				}
				
				if (!newIsDesign
						&& sourceGenProvider != null
					&& sourceGenProvider.isGeneratedTwoWayFile(newFile.getProjectRelativePath())) {
					// bizarre, but could happen if out of sync
					anySourceChanges = true;
				}
			}
		}		
			
		private void processFileChanged(IResourceDelta delta) {
			if (delta.getResource() instanceof IFile) {
				IFile file = (IFile) delta.getResource();
				IDesignerDataModelSpecifier specifier = getSpecifierForFile(file);
				if (specifier != null) {
					// alert specifier that any cached model is invalid
					specifier.notifyModelResourceChanged();
					DesignerDataModelNotifier.instance().fireDataModelChanged(specifier);
				} else if (sourceGenProvider != null
						&& sourceGenProvider.isGeneratedTwoWayFile(file.getProjectRelativePath())) {
						anySourceChanges = true;
				}
			}				
		}
		
		private void processFileRemoved(IResourceDelta delta) {
			if (delta.getResource() instanceof IFile) {
				IFile file = (IFile) delta.getResource();
				DesignerDataModelSpecifier specifier = (DesignerDataModelSpecifier) getSpecifierForFile(file);
				if (specifier != null) {
					// If the file was moved within the same project then
					// ignore as we'll also get an add event that contains the 
					// moved_from information
					if ((IResourceDelta.MOVED_TO & delta.getFlags()) != 0) {
						IFile moveToFile = root.getFile(delta.getMovedToPath());
						if (moveToFile.getProject() != file.getProject()) {
							removeSpecifier(specifier);
							// update specifier for use by job
							specifier.updateFile(moveToFile);
							DesignFileMovedJob job = new DesignFileMovedJob(specifier, file.getFullPath());
							job.schedule(FILE_JOB_DELAY);
						}
					} else {
						removeSpecifier(specifier);
						DesignFileDeletedJob job = new DesignFileDeletedJob(specifier);
						job.setRule(getProject());
						job.setPriority(WorkspaceContext.QUERY_REMOVE_FROM_APP_ON_DELETE_PRIORITY);
						job.schedule(FILE_JOB_DELAY);
						anyChanges = true;
					}
				} else if (sourceGenProvider != null 
						&& sourceGenProvider.isGeneratedTwoWayFile(file.getProjectRelativePath())) {
					anySourceChanges = true;					
				}
			}	
		}
	}
	
	
	private Pair<Boolean, Boolean> refreshFromDelta(IResourceDelta delta) {
		Pair<Boolean, Boolean> result = new Pair(Boolean.FALSE, Boolean.FALSE);
		try {
			ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
			delta.accept(visitor);
			result.first = visitor.anyChanges;
			result.second = visitor.anySourceChanges;
		}
		catch (CoreException x) {
			SymbianPlugin.getDefault().log(x);
		}
		return result;
	}
		
	
	
	private boolean refreshNoDelta() {
		boolean changed = false;
		
		// Create root model specifier if needed
		IFile rootFile = getRootModelFile();
		boolean rootExists = rootFile.exists();
		if (rootExists && rootDesign == null) {
			rootDesign = (DesignerDataModelSpecifier) createNewRootModelSpecifier();
			changed = true;
		}
		else if (!rootExists && rootDesign != null) {
			// root can't be renamed or moved, so forget the reference
			rootDesign = null;
			changed = true;
		}

		// Add new models
		ArrayList<IFile> list = findViewNXDs();
		for (IFile modelFile : list) {
			IDesignerDataModelSpecifier specifier = getSpecifierForFile(modelFile);
			if (specifier == null) {
				addSpecifierForFile(modelFile);
				changed = true;
			}
		}
        return changed;
		
	}
	
	void removeSpecifier(DesignerDataModelSpecifier specifier) {
		if (specifier == rootDesign) {
			rootDesign = null;
		} else {
			viewDesigns.remove(specifier);
		}
		if (sourceGenProvider != null)
			sourceGenProvider.updateProjectInfo();
	}

	/**
	 * Get the root data model file.
	 */
	private IFile getRootModelFile() {
		IFile result = null;
		for (int i = 0; i < SymbianModelUtils.UIDESIGN_EXTENSIONS.length; i++) {
			IFile file = project.getFile(SymbianModelUtils.ROOT_UIDESIGN_NAME + SymbianModelUtils.UIDESIGN_EXTENSIONS[i]);
			if (file.exists()) {
				result = file;
				break;
			}
		}
		if (result == null) {
			result = project.getFile(SymbianModelUtils.ROOT_UIDESIGN_NAME + SymbianModelUtils.UIDESIGN_EXTENSIONS[0]);
		}
		return result;
	}

	public void setCurrentLanguage(Language language) {
		if (language.equals(currentLanguage))
			return;
		
		currentLanguage = language;
		try {
			project.setPersistentProperty(CURRENT_LANGUAGE_KEY, String.valueOf(language.getLanguageCode()));
		} 
		catch (CoreException x) {
			SymbianPlugin.getDefault().log(x);
		}
	}
	
	private Language readLanguageProperty() {
		Language result = null;
		try {
			String languageIdString = project.getPersistentProperty(CURRENT_LANGUAGE_KEY);
			if (!TextUtils.isEmpty(languageIdString)) {
				result = new Language(Integer.parseInt(languageIdString));
			}
		} 
		catch (NumberFormatException x) {
			SymbianPlugin.getDefault().log(x);
		}
		catch (CoreException x) {
			SymbianPlugin.getDefault().log(x);
		}
		return result;
	}

	public Language getCurrentLanguage() {
		if (currentLanguage != null)
			return currentLanguage;

		Language language = readLanguageProperty();
		
		// when no local property, get the first set language from the root model
		if (language == null) {
			IDesignerDataModelSpecifier rootModel = getRootModel();
			if (rootModel != null) {
				language = (Language) rootModel.runWithLoadedModelNoSourceGen(new IRunWithModelAction() {
					public Object run(LoadResult lr) {
						Language result = null;
						if (lr.getModel() != null) {
							DesignerDataModel dm = (DesignerDataModel) lr.getModel();
							ILocalizedStringBundle bundle = dm.getDesignerData().getStringBundle();
							List tables = bundle.getLocalizedStringTables();
							if (tables.size() > 0) {
								ILocalizedStringTable lst = (ILocalizedStringTable) tables.get(0);
								result = lst.getLanguage();
							}
						}
						return result;
					}
				});
			}
		}
		
		// This is only as a last resort, the wizard ensures a initial language
		// when the root model is created
		if (language == null) {
			language = new Language(Language.LANG_American);
		}
		currentLanguage = language;

		return language;
	}
	
	public void ensureCurrentLanguage(DesignerDataModel rootModel) {
		// ensure the language property is in synch with some
		// language actually present in the root model
		Language language = currentLanguage;
		if (language == null) {
			language = readLanguageProperty();
		}
		
		ILocalizedStringBundle bundle = rootModel.getDesignerData().getStringBundle();
		if (bundle != null) {
			boolean changeProperty = true;
			if (language != null) {
				changeProperty = bundle.findLocalizedStringTable(language) == null;
			}
			if (changeProperty) {
				List tables = bundle.getLocalizedStringTables();
				if (tables != null && tables.size() > 0) {
					ILocalizedStringTable tbl = (ILocalizedStringTable) tables.get(0);
					language = tbl.getLanguage();
				}
			}
			// unlikely case of no language tables
			if (language == null) {
				language = new Language(Language.LANG_American);
				bundle.addLocalizedStringTable(language);
			}
		}
		
		if (language == null) {
			language = new Language(Language.LANG_American);
		}
		setCurrentLanguage(language);
	}

	public void unloadCachedModels() {
		for (DesignerDataModelSpecifier specifier : viewDesigns) {
			specifier.clearModelCache();
		}
		if (rootDesign != null) {
			rootDesign.clearModelCache();
		}
		messageHandler.reset();

		// Don't reset provider if editors have models open.
		IDesignerDataModelEditor[] editors = EditorServices.findEditorsForProject(project);
		if (editors == null || editors.length == 0) {
			// refresh sourcegen provider
			setSourceGenProvider(null);
		}
	}
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.workspace.IProjectContext#resetMessages(IComponentSet set)
     */
    public void resetMessages(IComponentSet set) {
        messageHandler.reset();
    }
    
    private class InitializeSourceProviderAction implements IRunWithModelAction {
		
		public Object run(LoadResult lr) {
			if (lr.getModel() == null)
				return null;
			IDesignerDataModel dataModel = lr.getModel();

			// get the real SDK directory
            String sdkVendor = dataModel.getProperty(SymbianModelUtils.SYMBIAN_VENDOR_PROPERTY); //$NON-NLS-1$
            if (sdkVendor == null) {
                SymbianPlugin.getDefault().log("Missing "+SymbianModelUtils.SYMBIAN_VENDOR_PROPERTY+" property in design "  //$NON-NLS-1$ //$NON-NLS-2$
                		+ dataModel.getModelSpecifier().getPrimaryResourcePath()); //$NON-NLS-1$
                sdkVendor = ISymbianSDK.S60_FAMILY_ID;
            }
            String sdkVersion = dataModel.getProperty(SymbianModelUtils.SYMBIAN_VERSION_PROPERTY); //$NON-NLS-1$
            if (sdkVersion == null) {
                SymbianPlugin.getDefault().log("Missing "+SymbianModelUtils.SYMBIAN_VERSION_PROPERTY+" property in design " +  //$NON-NLS-1$ //$NON-NLS-2$
                		dataModel.getModelSpecifier().getPrimaryResourcePath()); //$NON-NLS-1$
                sdkVersion = "3.0"; //$NON-NLS-1$
            }
            
            // get a workable SDK for the purposes of parsing
            ISymbianSDK sdk = SdkUtilities.getBestSDKForVendorAndVersion(sdkVendor, sdkVersion);
            File sdkRoot = (sdk != null) ? new File(sdk.getEPOCROOT()) : null;
            sourceGenProvider.setIncludeFileLocator(new CdtIncludeFileLocator(project, sourceGenProvider.getNameGenerator(), dataModel, sdkRoot));
            
            // use view references to get view models if a known application,
            // else search project for any view designs (e.g. for unit tests)
            if (!SymbianModelUtils.isUnknownApplication(dataModel)) {
	            sourceGenProvider.setModelGatherer(ProjectContext.this);
            } else {
            	sourceGenProvider.setModelGatherer(null);
            }
            
			return null;
		}
	}
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.workspace.IProjectContext#getSourceGenProvider()
     */
    public ISourceGenProvider getSourceGenProvider() {
    	if (sourceGenProvider == null) {
    		try {
				sourceGenProvider = SourceGenUtils.loadSourceGenProvider(SymbianModelUtils.SOURCEGEN_PROVIDER_ID, getProject());
				sourceGenProvider.setSourceGenFlags(
						ISourceGenProvider.FLAG_ONE_WAY_UPDATE);
	            sourceGenProvider.setNameGenerator(new SymbianNameGenerator(getProject()));
	            sourceGenProvider.setSourceFormatter(new SymbianSourceFormatter(new WorkspaceSourceFormatting(project)));
	            sourceGenProvider.setIncludeFileLocator(new CdtIncludeFileLocator(project, null, null, null));

				IDesignerDataModelSpecifier rootModel = getRootModel();
				if (rootModel == null) {
					// ensure the root design is available, if existing, 
					// in the rare case we get here before the project context is initialized completely
					refresh(null);
					rootModel = getRootModel();
				}
				if (rootModel != null)
					rootModel.runWithLoadedModelNoSourceGen(new InitializeSourceProviderAction());
			} catch (CoreException e) {
				SymbianPlugin.getDefault().log(e);
			}
    	}
    	return sourceGenProvider;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.workspace.IProjectContext#setSourceGenProvider(com.nokia.sdt.sourcegen.ISourceGenProvider)
     */
    public void setSourceGenProvider(ISourceGenProvider sgProvider) {
    	if (sourceGenProvider != null && sourceGenProvider != sgProvider) {
    		sourceGenProvider.dispose();
    	}
    	this.sourceGenProvider = sgProvider;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceGenModelGatherer#getGeneratableModelSpecifiers(com.nokia.sdt.datamodel.IDesignerDataModel)
     */
    public List<IDesignerDataModelSpecifier> getGeneratableModelSpecifiers(IDesignerDataModel rootModel) {
    	final List<IDesignerDataModelSpecifier> allSpecs = new ArrayList<IDesignerDataModelSpecifier>();
    	if (rootModel != null) {
    		allSpecs.add(rootModel.getModelSpecifier());
    		allSpecs.addAll(SymbianModelUtils.getSpecifiersForReferencedDesigns(rootModel));
    	}
    	else if (rootDesign != null) {
    		allSpecs.add(rootDesign);
    		rootDesign.runWithLoadedModelNoSourceGen(new IRunWithModelAction() {

				public Object run(LoadResult lr) {
					if (lr.getModel() != null)
						allSpecs.addAll(SymbianModelUtils.getSpecifiersForReferencedDesigns(lr.getModel()));
					return null;
				}
    		});
    	}
    	return allSpecs;
    }
}
