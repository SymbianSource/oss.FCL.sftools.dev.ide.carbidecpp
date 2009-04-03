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
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;
import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.core.ResourceTracker.ResourceInfo;
import com.nokia.sdt.sourcegen.doms.rss.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.*;

/**
 * Base class for RSS model generation
 * 
 * 
 * 
 */
public class RssModelGenerator implements IRssModelGenerator {
	private IRssModelProxy proxy;

	private ISourceFormatter formatter;

	private IDesignerDataModel dataModel;

	private IAstRssSourceFile rssFile;

	private ITranslationUnit translationUnit;

	private IRssModelManipulator manipulator;

	private IRssModelStringHandler stringHandler;

	private Map<IRssModelProxy, IAstRssSourceFile> proxyToSourceFile;

	private Command localizedStringChangesCommand;

	private IRssProjectFileManager fileManager;

	private ISourceGenProvider provider;

	/** 
	 * List of translation units referenced during a given generation pass.
	 * Used to properly initialize the TUs with string tables and req'd NAME statements.
	 */
	private Set<ITranslationUnit> referencedTranslationUnits;

	private Set<IAstResourceDefinition> unreferencedResources;

	public RssModelGenerator(ISourceGenProvider provider, ISourceGenSession session, IRssModelProxy proxy) {
		Check.checkArg(provider);
		Check.checkArg(proxy);

		this.provider = provider;
		this.fileManager = (IRssProjectFileManager) provider.getAdapter(IRssProjectFileManager.class);
		this.proxy = proxy;
		this.formatter = provider.getSourceFormatter();
		this.dataModel = proxy.requestDataModel();

		this.stringHandler = null;
		this.manipulator = new RssModelManipulator(provider, session, proxy);
		
		this.proxyToSourceFile = new HashMap<IRssModelProxy, IAstRssSourceFile>();
		
		this.referencedTranslationUnits = new HashSet<ITranslationUnit>(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.utils.IDisposable#dispose()
	 */
	public void dispose() {
		manipulator.getTypeHandler().reset();
		referencedTranslationUnits.clear();
		proxyToSourceFile.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#getModelProxy()
	 */
	public IRssModelProxy getModelProxy() {
		return proxy;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#getSourceGenProvider()
	 */
	public ISourceGenProvider getSourceGenProvider() {
		return provider;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#reset()
	 */
	public void reset() {
		manipulator.getResourceTracker().reset();
		rssFile = null;
		translationUnit = null;
		proxyToSourceFile.clear();
		referencedTranslationUnits.clear();
	}

	public void loadState(IDesignerDataModel model, IDesignerData data) throws CoreException {
		Check.checkArg(model);
		Check.checkArg(data);
		
		// clear out
		rssFile = null;
		translationUnit = null;
		
		// load/create the primary RSS if not already found
		translationUnit = createOrLoadTranslationUnit(null, false);
		rssFile = getRssFile(null, false);
		
		// only update stuff for a view model if it is included from the root
		boolean modelActive = false;
		ISourceFile rssFileSf = fileManager.getRssSourceFile(proxy, null);
		if (proxy.isRootModel())
			modelActive = true;
		else {
			IRssModelProxy rootProxy = proxy.getProjectInfo().getRootModelProxy();
			if (rootProxy != null) {
				IAstRssSourceFile mainRssFile = fileManager.findOrCreateRssFile(rootProxy, null, false);
				if (mainRssFile != null && mainRssFile.findInclude(rssFileSf) != null)
					modelActive = true;
			}
		}
		
		if (modelActive) { 
			if (rssFile != null) {
				// now load mappings
				ISourceGenMappingState mappingState = data.getSourceMappingState();
				if (mappingState != null) {
					manipulator.getResourceTracker().loadState(model, mappingState, this);
					manipulator.getTypeHandler().loadState(model, mappingState, translationUnit);
				}
				
				// now check for localized string changes
				if (stringHandler != null) {
					Pair<Command, Command> commands = stringHandler.importStrings(model, rssFile, fileManager);
					// apply changes to user-defined string table
					Check.checkState(commands.first.canExecute());
					commands.first.execute();
					// store away changes to existing macros
					localizedStringChangesCommand = commands.second;
				} else {
					localizedStringChangesCommand = UnexecutableCommand.INSTANCE;
				}
			} else {
				data.setSourceMappingState(DmFactory.eINSTANCE.createISourceGenMappingState());
				localizedStringChangesCommand = UnexecutableCommand.INSTANCE;
				//manipulator.getResourceTracker().reset();
				//manipulator.getTypeHandler().reset();
			}
		} else {
			// Model is inactive.  Shouldn't be here.
			
			// We can get here when renaming a file to a different case.
			// The file exists but its #include was removed temporarily.
			if (rssFileSf != null && rssFileSf.getFile().exists()) {
				MessageReporting.emit(
					IMessage.ERROR,
					dataModel.getModelSpecifier().createMessageLocation(),
					"ModelRssGenerator.MissingRssInclude", //$NON-NLS-1$
					Messages.getString("ModelRssGenerator.MissingRssInclude"), //$NON-NLS-1$
					new Object[] { rssFileSf.getFileName(),
							dataModel.getModelSpecifier().getDisplayName() });
			}
			localizedStringChangesCommand = UnexecutableCommand.INSTANCE;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#updateFromSource(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public Command updateFromSource(IProgressMonitor monitor) {
		Check.checkContract(localizedStringChangesCommand != null);
		
		CompoundCommand commands = new CompoundCommand();
		commands.append(localizedStringChangesCommand);
		
		commands.setLabel(Messages.getString("RssModelGenerator.UpdateDesignCommandLabel")); //$NON-NLS-1$
		commands.setDescription(Messages.getString("RssModelGenerator.UpdateDesignCommandDescription")); //$NON-NLS-1$
		return commands.unwrap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#generateResources()
	 */
	public void generateResources() {
		generate(true, dataModel.getRoot());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#generateResources(com.nokia.sdt.datamodel.adapter.IComponentInstance)
	 */
	public void generateResources(IComponentInstance instance) {
		generate(true, (instance != null) ? instance.getEObject() : null);
	}

	/**
	 *	Make a list of resources we think we generated last time.
	 * 
	 */
	private void gatherExistingGeneratedResources() {
		Check.checkState(unreferencedResources == null);
		unreferencedResources = manipulator.getResourceTracker().getGeneratedResources(this, dataModel);
	}

	/**
	 * Delete generated resources not referenced during this round.
	 */
	private void deleteUnreferencedGeneratedResources() {
		Check.checkState(unreferencedResources != null);
		for (Iterator iter = unreferencedResources.iterator(); iter.hasNext();) {
			IAstResourceDefinition def = (IAstResourceDefinition) iter.next();
			def.removeFromParent();
		}
		unreferencedResources = null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#referenceResource(com.nokia.sdt.sourcegen.doms.rss.dom.IAstResourceDefinition)
	 */
	public void referenceResource(IAstResourceDefinition definition) {
		// should only be called during resource generation
		if (unreferencedResources != null)
			unreferencedResources.remove(definition);
	}

	/**
	 * Remove resources for deleted instances.
	 */
	private void removeDeletedInstanceResources() {
		String[] instances = manipulator.getResourceTracker().getReferencedInstances(dataModel);
		for (int i = 0; i < instances.length; i++) {
			EObject instance = dataModel.findByNameProperty(instances[i]);
			if (instance == null) {
				removeResourcesForInstance(instances[i]);
				manipulator.getTypeHandler().removeEnumeratorMappingsForInstance(instances[i]);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#removeInstance(com.nokia.sdt.datamodel.adapter.IComponentInstance)
	 */
	public void removeResourcesForInstance(String instanceName) {
		ResourceInfo[] infos = manipulator.getResourceTracker()
				.getResourceInfos(instanceName);
		for (int i = 0; i < infos.length; i++) {
			IAstResourceDefinition rsrc = infos[i].findResourceDefinition(this);
			if (rsrc != null)
				((IAstListElement) rsrc).removeFromParent();
		}
		manipulator.getResourceTracker().removeResourceInfo(instanceName);
		
		// also remove the associated type info
		Object enums[] = manipulator.getTypeHandler().getEnumeratorMappingsForInstance(instanceName);
		for (int i = 0; i < enums.length; i++) {
			if (enums[i] instanceof IAstEnumerator) {
				IAstEnumerator enm = (IAstEnumerator) enums[i];
				IAstEnumDeclaration decl = enm.getEnumeration();
				// ensure we're not peeking at a system enum
				if (decl != null) {
					decl.removeEnumeratorPreservingStartValues(enm);
					
					// delete empty enums since RCOMP complains
					if (decl.getEnumerators().length == 0)
						decl.removeFromParent();
				}
			}
		}
		manipulator.getTypeHandler().removeEnumeratorMappingsForInstance(instanceName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#getModelManipulator()
	 */
	public IRssModelManipulator getModelManipulator() {
		return manipulator;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#getRssFile(java.lang.String)
	 */
	public IAstRssSourceFile getRssFile(String fileName, boolean allowCreate) {
		IAstRssSourceFile rssFile = fileManager.findOrCreateRssFile(proxy, fileName, allowCreate);
		if (rssFile == null)
			return null;
		return rssFile;
	}
	
	/**
	 * Reset state for the given file.
	 * @param fileName actual name or null for main model file (*.rssi)
	 */
	void resetForFile(String fileName, IAstRssSourceFile rssFile) {
		manipulator.getResourceTracker().resetForRssFile(fileName);
		manipulator.getTypeHandler().resetForRssFile(fileName);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#createOrLoadTranslationUnit(java.lang.String, boolean)
	 */
	public ITranslationUnit createOrLoadTranslationUnit(String fileName, boolean allowModify) {
		ITranslationUnit tu = fileManager.findTranslationUnit(proxy, fileName);
		if (tu == null) {
			// make a translation unit

			tu = fileManager.createOrLoadTranslationUnit(proxy, fileName);
			
			fileManager.registerTranslationUnit(proxy, fileName, tu);
		}
		
		if (!referencedTranslationUnits.contains(tu)) {
			
			if (allowModify) {
				updateTranslationUnit(fileName, tu);
			}
			
			referencedTranslationUnits.add(tu);
		}
		return tu;
	}

	/**
	 * Update contents of a translation unit on first access
	 * @param fileName
	 * @param tu
	 */
	private void updateTranslationUnit(String fileName, ITranslationUnit tu) {
		IRssRootModelProxy rootProxy = proxy.getProjectInfo().getRootModelProxy();
		
		// add, if missing, a NAME statement uniquely identifying these resources
		IAstRssSourceFile primaryRssFile = (IAstRssSourceFile) tu.getSourceFile();
		IAstRssSourceFile rssFile = getRssFile(fileName, true);

		// This assumes that a non-primary RSS file does not need a NAME
		if (rootProxy != null && fileName == null) {
			IAstNameStatement[] names = (IAstNameStatement[]) primaryRssFile
					.getFileNodes(IAstNameStatement.class);
			if (names.length == 0) {
				IAstNameStatement name = new AstNameStatement(rootProxy.getNameValue());
				primaryRssFile.addFileNode(name);
			}
		}
		
		// get localized strings
		if (stringHandler != null) {
			// update string #includes, ensuring the same ones
			// are shared with all the rss created by this model
			stringHandler.exportStrings(dataModel, 
					getRssFile(null, true), 
					rssFile, fileManager);
		}
	}
	

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#setRssFileTesting(com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile)
	 */
	public void setRssFileForTesting(IAstRssSourceFile file) {
		proxy.setRssFileName(file.getSourceFile().getFileName());
		if (fileManager.findRssFile(file.getSourceFile()) == null)
			fileManager.registerRssFile(file);
		fileManager.registerProxySourceFile(proxy, file.getSourceFile());
		fileManager.registerTranslationUnit(getModelProxy(), null, file.getTranslationUnit());
	}


	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#setStringHandler(com.nokia.sdt.sourcegen.doms.rss.IRssModelStringHandler)
	 */
	public void setStringHandler(IRssModelStringHandler stringHandler) {
		this.stringHandler = stringHandler;
	}

	/** Initialize before generation. */
	protected void init() {
		updateIncludes();
	}

	/** 
	 * 	Finish after generation.
	 *	@return true if everything looks good and the DOM can stay in memory;
	 *	false if the DOM is out-of-date and needs to be refreshed next time someone asks  
	 */
	protected boolean term() {
		return updateIncludes();
	}

	private boolean updateIncludes() {
		boolean cleanTu = true;
		
		// update #includes on both sides of the save, because both models are
		// not necessarily saved at the same time, and we want to avoid missyncs
		if (!proxy.isRootModel()) {
			IAstRssSourceFile mainFile = ((IAstRssSourceFile) translationUnit.getSourceFile());
			IAstPreprocessorIncludeDirective incl = mainFile.findInclude(rssFile.getSourceFile());
			if (incl == null)
				incl = mainFile.findInclude(rssFile.getSourceFile().getFileName());
			if (incl != null) {
				if (incl.getFile() == null || incl.getFile().isReadOnly()) {
					// update dummy include
					incl.setFile(rssFile);
				}
			}
			if (shouldIncludeViewModel(proxy)) {
				// ensure include is at end
				if (incl == null) {
					translationUnit.getSourceFile().appendFileNode(
							new AstPreprocessorModelIncludeNode(rssFile));
					translationUnit.refresh();
				}
			} else {
				/*  only let root do this
				if (incl != null) {
					translationUnit.getSourceFile().removeFileNode(incl);
					translationUnit.refresh();
				}
				*/
			}
			
			// ensure the file is written, even if empty (root expects it)
			rssFile.getSourceFile().setDirty(true);
		} else {
			// add #includes for RSSI files we expect to be
			// generated by view data models
			IRssModelProxy[] proxies = proxy.getProjectInfo().getViewModelProxies();

			for (int i = 0; i < proxies.length; i++) {
				IAstRssSourceFile subFile = null;
				if (provider.isOneWayUpdate())
					subFile = fileManager.findOrCreateRssFile(proxies[i], null, false);
				if (subFile == null) {
					// Something is wrong, generate a dummy file.  We need to reparse eventually... but when?
					subFile = new AstRssSourceFile(fileManager.getRssSourceFile(proxies[i], null));
					subFile.setReadOnly(true);
					//cleanTu = false;
				}
				if (subFile != null) {
					IAstPreprocessorIncludeDirective incl = rssFile.findInclude(subFile.getSourceFile());
					if (incl == null)
						incl = rssFile.findInclude(subFile.getSourceFile().getFileName());
					if (shouldIncludeViewModel(proxies[i])) {
						// always remove and re-add include at very end of file
						if (incl != null) {
							rssFile.removeFileNode(incl);
							incl.setParent(null);
							rssFile.appendFileNode(incl);
						} else {
							// add to end of file
							rssFile.appendFileNode(
									new AstPreprocessorModelIncludeNode(subFile));
						}
					} else {
						if (incl != null) {
							// remove
							rssFile.removeFileNode(incl);
						}
					}
				}
			}
			
			// remove #includes not provided by proxies
			IAstPreprocessorModelIncludeNode[] modelIncls = (IAstPreprocessorModelIncludeNode[]) rssFile.getFileNodes(IAstPreprocessorModelIncludeNode.class);
			for (int i = 0; i < modelIncls.length; i++) {
				String modelName = modelIncls[i].getFilename();
				boolean found = false;
				for (int j = 0; j < proxies.length; j++) {
					if (proxies[j].getRssFileName().equalsIgnoreCase(modelName)) {
						found = true;
						break;
					}
				}
				if (!found)
					rssFile.removeFileNode(modelIncls[i]);
			}

			translationUnit.refresh();
		}
		return cleanTu;
	}

	/**
	 * Tell whether a view model should be included in its parent.
	 * We do this if (1) the root is for an unknown app or 
	 * (2) the view is referenced in a design reference by the root 
	 * @return
	 */
	private boolean shouldIncludeViewModel(IRssModelProxy proxy) {
		IRssRootModelProxy rootProxy = proxy.getProjectInfo().getRootModelProxy();
		IDesignerDataModel rootModel = rootProxy != null ? rootProxy.requestDataModel() : null;
		if (rootModel == null)
			return false;
		
		if (SymbianModelUtils.isUnknownApplication(rootModel)) {
			return true;
		}
		else {
			List<IDesignerDataModelSpecifier> designs = SymbianModelUtils.getSpecifiersForReferencedDesigns(rootModel);
			for (IDesignerDataModelSpecifier dmSpec : designs) {
				if (ObjectUtils.equals(dmSpec, proxy.getModelSpecifier())) {
					return true;
				}
			}
			return false;
		}
	}

	protected void generate(boolean fullMode, EObject root) {

		if (dataModel == null)
			return;

		generateModelRss(fullMode, root);
	}

	/**
	 * Create or update RSS from the data model. The tree is assumed to exist in
	 * memory. If not, it is rewritten from scratch. Thus, this model supports
	 * one-way and two-way generation.
	 * 
	 * @param fullMode
	 *            true: do all the work, false: just find files
	 * @param root the root object (may be null)
	 */
	private void generateModelRss(boolean fullMode, EObject root) {

		// clear out TUs so we know when to update their req'd info
		referencedTranslationUnits.clear();

		// get current/primary file
		translationUnit = createOrLoadTranslationUnit(null, true);
		rssFile = getRssFile(null, true);
		if (rssFile == null)
			return;

		removeDeletedInstanceResources();
		gatherExistingGeneratedResources();
		
		if (root == dataModel.getRoot())
			init();

		if (fullMode) {
			manipulator.getResourceTracker().resetComplete();

			if (root != null)
				generateTwoWay(root);

		}

		if (root == dataModel.getRoot()) {
			translationUnit = createOrLoadTranslationUnit(null, true);
			rssFile = getRssFile(null, true);
			boolean cleanTu = term();
			if (!cleanTu) {
				fileManager.removeTranslationUnit(translationUnit);
				//fileManager.createOrLoadTranslationUnit(proxy, null);
			}
		}

		deleteUnreferencedGeneratedResources();

		// update modified source files
		for (Iterator iter = referencedTranslationUnits.iterator(); iter.hasNext();) {
			ITranslationUnit aTu = (ITranslationUnit) iter.next();
			aTu.rewrite(formatter);
		}

	}

	private void generateTwoWay(EObject object) {

		IComponentInstance instance = (IComponentInstance) EcoreUtil
				.getRegisteredAdapter(object, IComponentInstance.class);
		if (instance != null) {
			IComponent component = instance.getComponent();
			if (component == null) {
				MessageReporting.emit(
					IMessage.ERROR,
					dataModel.getModelSpecifier()
							.createMessageLocation(),
					"ModelRssGenerator.MissingComponent", //$NON-NLS-1$
					Messages
							.getString("ModelRssGenerator.MissingComponent"), //$NON-NLS-1$
					new Object[] { instance.getName(),
							instance.getComponentId() });

			} else {
				// just go through every instance and emit resources
				IComponentSourceMapping sourceMapping = (IComponentSourceMapping) component
						.getAdapter(IComponentSourceMapping.class);
				if (sourceMapping != null)
					sourceMapping.exportInstance(this, instance);
			}
		}

		for (Iterator iter = object.eContents().iterator(); iter.hasNext();) {
			EObject eKid = (EObject) iter.next();
			generateTwoWay(eKid);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#mergeGeneratedInformation(com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator)
	 */
	public void mergeGeneratedInformation(IRssModelGenerator generator) {
		manipulator.getResourceTracker().merge(
				generator.getModelManipulator().getResourceTracker());
		manipulator.getTypeHandler().merge(
				generator.getModelManipulator().getTypeHandler());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator#saveState()
	 */
	public void saveState(IDesignerData data) {
		ISourceGenMappingState state = DmFactory.eINSTANCE.createISourceGenMappingState();
		manipulator.getResourceTracker().saveState(data.getDesignerDataModel(), state, this);
		manipulator.getTypeHandler().saveState(data.getDesignerDataModel(), state);
		data.setSourceMappingState(state);
	}

	public Collection<ISourceFile> scanForGeneratedFiles() {
		// Unfortunately, we have to generate all the resources
		// because the scanning for C/C++ sourcegen also requires
		// executing code that may depend on resource names
		generate(true, dataModel.getRoot());
	
		Collection<ISourceFile> files = new LinkedHashSet<ISourceFile>();
		for (Iterator iter = fileManager.getGeneratedRssFiles()
				.iterator(); iter.hasNext();) {
			IAstSourceFile file = (IAstSourceFile) iter.next();
			files.add(file.getSourceFile());
		}
		return files;
	}
}
