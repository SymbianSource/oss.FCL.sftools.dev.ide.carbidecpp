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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.carbide.cpp.internal.project.ui.editors.images.*;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.EMMPListSelector;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.WrappedUndoableOperation;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.*;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.ProjectFileResourceProxyVisitor;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import java.text.MessageFormat;
import java.util.*;

/**
 * Model providing mapping of the IMMPView model to the tree presentation
 * used in the GUI
 *
 */
public class ResourcePresentationModel extends TreePresentationModel {

	final private MMPEditorContext editorContext;	
	final private ResourceBlocksNode resourceBlocks; 
	final private ResourceListNode userResourceList;
	final private ResourceListNode sysResourceList;
	final private LanguagesListNode languageList;
	final private BMPListNode bitmapsList;
	final private AIFListNode aifList;
	private boolean supportOldStyleResource; // RESOURCE & SYSTEMRESOURCE
	private  boolean supportAIF;
		
	/**
	 * Parent node of all START RESOURCE entries
	 */
	class ResourceBlocksNode extends BaseTreeNode {
		
		List<ResourceBlockNode> elements;
		
		ResourceBlocksNode() {
			super(Messages.ResourceModel_resourcesNode);
			refreshFromModel();
		}
		
		@Override
		public void refreshFromModel() {
			List<IMMPResource> resourceBlocks = editorContext.mmpView.getResourceBlocks();
			elements = new ArrayList<ResourceBlockNode>();
			for (IMMPResource resource : resourceBlocks) {
				elements.add(new ResourceBlockNode(resource));
			}
		}

		protected Collection getChildCollection() {
			return elements;
		}
		
		public void doAdd() {
			IMMPResource resourceBlock = editorContext.mmpView.createMMPResource();
			ResourceBlockDialog dialog = new ResourceBlockDialog(getViewer().getControl().getShell(),
					editorContext.project, resourceBlock, editorContext.pathHelper,
					editorContext.activeBuildConfig, editorContext.getSecureID());
			setDialog(dialog);
			if (dialog.open() == Dialog.OK) {
				List<IMMPResource> items = new ArrayList<IMMPResource>();
				items.add(resourceBlock);
				AddListValueOperation op = new AddListValueOperation(editorContext.mmpView,
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
						ControlHandler.getHandlerForControl(getViewer().getControl()),
						EMMPListSelector.START_RESOURCE, items);
				editorContext.executeOperation(op);
				refreshFromModel();
				refreshViewerElement(this);
			}
		}
		
		IMMPResource copyResourceBlock(IMMPResource resourceBlock) {
			IMMPResource result = editorContext.mmpView.createMMPResource();
			result.setHeaderFlags(resourceBlock.getHeaderFlags());
			result.setLanguages(new ArrayList<EMMPLanguage>(resourceBlock.getLanguages()));
			IPath sourcePath = resourceBlock.getSource();
			if (sourcePath != null) {
				result.setSource(new Path(sourcePath.toString()));
			}
			result.setTargetFile(resourceBlock.getTargetFile());
			IPath targetPath = resourceBlock.getTargetPath();
			if (targetPath != null) {
				result.setTargetPath(new Path(targetPath.toString()));
			}
			result.setUid2(resourceBlock.getUid2());
			result.setUid3(resourceBlock.getUid3());
			return result;
		}

		@Override
		public boolean isChild(Object element) {
			return element instanceof ResourceBlockNode;
		}

		@Override
		public boolean canEdit(Object element) {
			return super.canEdit(element) || element instanceof LanguagesListNode;
		}

		public void doEdit(Object element) {
			if (element instanceof ResourceBlockNode) {
				ResourceBlockNode currValue = (ResourceBlockNode) element;
				int index = editorContext.mmpView.getResourceBlocks().indexOf(currValue.resourceBlock);
				Check.checkState(index >= 0);
				IMMPResource newValue = copyResourceBlock(currValue.resourceBlock);
				ResourceBlockDialog dialog = new ResourceBlockDialog(getViewer().getControl().getShell(),
						editorContext.project, newValue, editorContext.pathHelper,
						editorContext.activeBuildConfig,
						editorContext.getSecureID());
				setDialog(dialog);
				if (dialog.open() == Dialog.OK) {
					Map<Integer, Object> replaceMap = new HashMap<Integer, Object>();
					replaceMap.put(index, newValue);
					ReplaceListValueOperation op = new ReplaceListValueOperation(
							editorContext.mmpView, 
							new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
							ControlHandler.getHandlerForControl(getViewer().getControl()),
							EMMPListSelector.START_RESOURCE, replaceMap);
					editorContext.executeOperation(op);
					refreshViewerElement(this);
				}
			} else if (element instanceof LanguagesListNode) {
				LanguagesListNode llNode = (LanguagesListNode) element;
				llNode.doEdit(llNode);
			}
		}

		public void doRemove(Object element) {
			int index = elements.indexOf(element);
			if (index >= 0) {
				RemoveListValueOperation op = new RemoveListValueOperation(
						editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
						ControlHandler.getHandlerForControl(getViewer().getControl()),
						EMMPListSelector.START_RESOURCE, Collections.singletonList(index));
				editorContext.executeOperation(op);
				refreshFromModel();
				refreshViewerElement(this);
			}
		}

		public String getImageKey() {
			return ICarbideSharedImages.IMG_START_RESOURCE_BLOCK_16_16;
		}
	}
	
	/**
	 * This node represents a single START RESOURCE block. It is a parent
	 * because it contains a language node with the list of languages below it.
	 */
	class ResourceBlockNode extends BaseTreeNode {
		private IMMPResource resourceBlock;
		private LanguagesListNode languageNode;

		protected ResourceBlockNode(IMMPResource resourceBlock) {
			super(resourceBlock.getSource().toString());
			this.resourceBlock = resourceBlock;
			this.languageNode = new LanguagesListNode(resourceBlock);
		}
		
		IMMPResource getMMPResource() {
			return resourceBlock;
		}

		@Override
		Collection getChildCollection() {
			List<LanguagesListNode> result = new ArrayList<LanguagesListNode>();
			result.add(languageNode);
			return result;
		}

		@Override
		public boolean isChild(Object element) {
			boolean result = super.isChild(element);
			if (!result && element instanceof LanguagesListNode) {
				LanguagesListNode llNode = (LanguagesListNode) element;
				result = llNode.resourceBlock == resourceBlock;
			}
			return result;
		}

		@Override
		public boolean canRemove(Object element) {
			return super.canRemove(element) &&
				!(element instanceof LanguagesListNode);
		}

		public void doAdd() {
			resourceBlocks.doAdd();
		}

		public void doEdit(Object element) {
			resourceBlocks.doEdit(this);
		}

		public void doRemove(Object element) {
			resourceBlocks.doRemove(this);
		}

		public String getImageKey() {
			return null;
		}
	}
	
	// for old-style RESOURCE & SYSTEMRESOURCE statements
	class ResourceListNode extends BaseTreeNode {
		private final ICarbideListProvider listProvider;
		private final String imageKey;

		ResourceListNode(ICarbideListProvider listProvider) {
			super(listProvider.getDisplayText());
			this.listProvider = listProvider;
			if (listProvider.equals(EMMPListSelector.SYSTEM_RESOURCES))
				imageKey = ICarbideSharedImages.IMG_SYSTEM_RESOURCE_LIST_16_16;
			else
				imageKey = ICarbideSharedImages.IMG_RESOURCE_LIST_16_16;
		}

		protected Collection getChildCollection() {
			return listProvider.fetchList(editorContext.mmpView);
		}
		
		private List<IPath> getRSSFiles() {
			ProjectFileResourceProxyVisitor visitor = new ProjectFileResourceProxyVisitor("rss", true); //$NON-NLS-1$
			List<IPath> result = null;
			try {
				editorContext.project.accept(visitor, IResource.NONE);
				result = visitor.getRequestedFiles();
			} catch (CoreException x) {
				ProjectUIPlugin.log(x);
			}
			return result;
		}
		
		public void doAdd() {
			MMPFilePathDialog dialog = new MMPFilePathDialog(getViewer().getControl().getShell(),
					null, EMMPPathContext.START_RESOURCE, editorContext.pathHelper,
					editorContext.activeBuildConfig,
					getRSSFiles(), new String[] {"*.rss"}); //$NON-NLS-1$
			setDialog(dialog);
			if (dialog.open() == Dialog.OK) {
				List<IPath> items = new ArrayList<IPath>();
				items.add(dialog.getResultPath());
				AddListValueOperation op = new AddListValueOperation(editorContext.mmpView,
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
						ControlHandler.getHandlerForControl(getViewer().getControl()),
						listProvider, items);
				editorContext.executeOperation(op);
				refreshViewerElement(this);
			}
		}

		public void doEdit(Object element) {
			if (element instanceof IPath) {
				IPath value = (IPath) element;
				MMPFilePathDialog dialog = new MMPFilePathDialog(getViewer().getControl().getShell(),
						value, EMMPPathContext.START_RESOURCE, editorContext.pathHelper,
						editorContext.activeBuildConfig,
						getRSSFiles(), new String[] {"*.rss"}); //$NON-NLS-1$
				setDialog(dialog);
				if (dialog.open() == Dialog.OK) {
					int index = listProvider.fetchList(editorContext.mmpView).indexOf(value);
					Check.checkState(index >= 0);
					Map<Integer, Object> replaceMap = new HashMap<Integer, Object>();
					replaceMap.put(index, dialog.getResultPath());
					ReplaceListValueOperation op = new ReplaceListValueOperation(
							editorContext.mmpView, 
							new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
							ControlHandler.getHandlerForControl(getViewer().getControl()),
							listProvider, replaceMap);
					editorContext.executeOperation(op);
					refreshViewerElement(this);
				}
			}
		}

		public void doRemove(Object element) {
			int index = listProvider.fetchList(editorContext.mmpView).indexOf(element);
			if (index >= 0) {
				RemoveListValueOperation op = new RemoveListValueOperation(
						editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
						ControlHandler.getHandlerForControl(getViewer().getControl()),
						listProvider, Collections.singletonList(index));
				editorContext.executeOperation(op);
				refreshViewerElement(this);
			}
		}

		public String getImageKey() {
			return imageKey;
		}
	}
	
	class AIFListNode extends BaseTreeNode {
		AIFListNode() {
			super(Messages.ResourceModel_aifNode);
		}
		
		protected Collection getChildCollection() {
			return editorContext.mmpView.getAifs();
		}

		private List<IPath> getRSSFiles() {
			ProjectFileResourceProxyVisitor visitor = new ProjectFileResourceProxyVisitor("rss", true); //$NON-NLS-1$
			List<IPath> result = null;
			try {
				editorContext.project.accept(visitor, IResource.NONE);
				result = visitor.getRequestedFiles();
			} catch (CoreException x) {
				ProjectUIPlugin.log(x);
			}
			return result;
		}

		public void doAdd() {
			IMMPAIFInfo info = editorContext.mmpView.createMMPAIFInfo();
			info.setColor(true);
			AIFEditorContext context = new AIFEditorContext();
			context.initFromAIF(editorContext.activeBuildConfig.getCarbideProject(),
					editorContext.mmpModel.getPath(),
					info, 
					editorContext.mmpView.getAifs(),
					editorContext.pathHelper, getRSSFiles());
			AIFEditorDialog dialog = new AIFEditorDialog(getViewer().getControl().getShell(), context);
			setDialog(dialog);
			if (dialog.open() == Dialog.OK && context.isDirty()) {
				context.doSave();
				List<IMMPAIFInfo> items = new ArrayList<IMMPAIFInfo>();
				items.add(info);
				AddListValueOperation op = new AddListValueOperation(editorContext.mmpView,
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
						ControlHandler.getHandlerForControl(getViewer().getControl()),
						EMMPListSelector.AIF, items);
				editorContext.executeOperation(op);
				refreshViewerElement(this);
			}
		}
		
		public void doEdit(Object element) {
			if (element instanceof IMMPAIFInfo) {
				IMMPAIFInfo info = (IMMPAIFInfo) element;
				AIFEditorContext context = new AIFEditorContext();
				context.initFromAIF(
						editorContext.activeBuildConfig.getCarbideProject(),
						editorContext.mmpModel.getPath(),
						info,
						editorContext.mmpView.getAifs(),
						editorContext.pathHelper, getRSSFiles());
				AIFEditorDialog dialog = new AIFEditorDialog(getViewer().getControl().getShell(), context);
				setDialog(dialog);
				if (dialog.open() == Dialog.OK && context.isDirty()) {
					IMMPAIFInfo newInfo = context.getMMPAIFInfo();
					int index = editorContext.mmpView.getAifs().indexOf(info);
					Check.checkState(index >= 0);
					
					Map<Integer, Object> replaceMap = new HashMap<Integer, Object>();
					replaceMap.put(index, newInfo);
					ReplaceListValueOperation op = new ReplaceListValueOperation(
							editorContext.mmpView, 
							new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
							ControlHandler.getHandlerForControl(getViewer().getControl()),
							EMMPListSelector.AIF, replaceMap);
					editorContext.executeOperation(op);
					refreshViewerElement(this);
				}
			}
		}

		public void doRemove(Object element) {
			int index = EMMPListSelector.AIF.fetchList(editorContext.mmpView).indexOf(element);
			if (index >= 0) {
				RemoveListValueOperation op = new RemoveListValueOperation(
						editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
						ControlHandler.getHandlerForControl(getViewer().getControl()),
						EMMPListSelector.AIF, Collections.singletonList(index));
				editorContext.executeOperation(op);
				refreshViewerElement(this);
			}
		}

		public String getImageKey() {
			return ICarbideSharedImages.IMG_AIF_FILE_16_16;
		}
	}
	
	class BMPListNode extends BaseTreeNode {
		BMPListNode() {
			super(Messages.ResourceModel_bitmapNodeLabel);
		}
		
		private IMMPBitmap lookupBitmap(IPath targetFilePath) {
			for (IMMPBitmap bitmap : editorContext.mmpView.getBitmaps()) {
				if (bitmap.getTargetFilePath().equals(targetFilePath))
					return bitmap;
			}
			Check.checkState(false);
			return null;
		}
		
		Collection getChildCollection() {
			List<IMMPBitmap> bitmaps = editorContext.mmpView.getBitmaps();
			List<IPath> mbmPaths = new ArrayList<IPath>(bitmaps.size());
			for (IMMPBitmap bitmap : bitmaps)
				mbmPaths.add(bitmap.getTargetFilePath());
			return mbmPaths;
		}

		private MultiImageEditorContext createEditorContext(IMMPBitmap bitmap) {
			MultiImageEditorContext context = new MultiImageEditorContext();
			context.initFromView(editorContext.mmpView, bitmap);
			context.setMultiImageSources(editorContext.mmpView.getMultiImageSources());
			return context;
		}
		
		public void doAdd() {
			final IMMPBitmap bitmap = editorContext.mmpView.createMMPBitmap();
			bitmap.setHeaderFlags(EGeneratedHeaderFlags.Header);
			
			MultiImageEditorContext context = createEditorContext(bitmap);
			MultiImageEditorDialog dialog = new MultiImageEditorDialog(editorContext.editor.getSite().getShell(), context);
			setDialog(dialog);
			if (dialog.open() == IDialogConstants.OK_ID) {
				// initialize the contents of the bitmap beforehand (non-undoable)
				// since we need to access this entry by its target file path,
				// which is blank by default.
				context.doSave();
		
				// make the "add" operation
				IUndoableOperation addOperation = new CarbideViewOperation(editorContext.mmpView, 
						MessageFormat.format(Messages.ResourcePresentationModel_AddBitmapSourceCommandLabel, new Object[] { bitmap.getTargetFile() }),
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
						ControlHandler.getHandlerForViewer(getViewer())) {

		
					@Override
					public IStatus doOperation(boolean updateControl) {
						editorContext.mmpView.getBitmaps().add(bitmap);
						if (updateControl) {
							refreshViewerElement(BMPListNode.this);
						}
						return Status.OK_STATUS;
					}
		
					@Override
					protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
						editorContext.mmpView.getBitmaps().remove(bitmap);
						refreshViewerElement(BMPListNode.this);
						return Status.OK_STATUS;
					}
					
				};
		
				context.dispose();
				editorContext.executeOperation(addOperation);
				refreshViewerElement(this);
			}
		}

		public void doEdit(Object element) {
			if (element instanceof IPath) {
				IMMPBitmap bitmap = lookupBitmap((IPath) element);
				MultiImageEditorContext context = createEditorContext(bitmap);
				MultiImageEditorDialog dialog = new MultiImageEditorDialog((Shell) editorContext.editor.getAdapter(Shell.class), context);
				setDialog(dialog);
				if (dialog.open() == Dialog.OK && context.isDirty()) {
					IUndoableOperation operation = context.getEditingOperation();
					WrappedUndoableOperation editingOperation = new WrappedUndoableOperation(
							editorContext.mmpView,
							MessageFormat.format(Messages.ResourcePresentationModel_EditBitmapSourceCommandLabel, new Object[] { bitmap.getTargetFile() }),
							new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
							operation);
					editorContext.executeOperation(editingOperation);
					refreshViewerElement(this);
				}
				context.dispose();
			}
		}

		public void doRemove(Object element) {
			final IMMPBitmap bitmap = lookupBitmap((IPath) element);
			// make the "remove" operation
			IUndoableOperation editingOperation = new CarbideViewOperation(editorContext.mmpView, 
					MessageFormat.format(Messages.ResourcePresentationModel_RemoveBitmapSourceCommandLabel, new Object[] { bitmap.getTargetFile() }),
					new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
					ControlHandler.getHandlerForViewer(getViewer())) {

				@Override
				public IStatus doOperation(boolean updateControl) {
					editorContext.mmpView.getBitmaps().remove(bitmap);
					if (updateControl) {
						refreshViewerElement(BMPListNode.this);
					}
					return Status.OK_STATUS;
				}

				@Override
				protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					editorContext.mmpView.getBitmaps().add(bitmap);
					refreshViewerElement(BMPListNode.this);
					return Status.OK_STATUS;
				}
			};

				
			editorContext.executeOperation(editingOperation);
			refreshViewerElement(this);
		}

		public String getImageKey() {
			return ICarbideSharedImages.IMG_MBM_FILE_16_16;
		}
	}
	
	class LanguagesListNode extends BaseTreeNode {
		// non-null if this is a language list from a resource block,
		// otherwise it's the mmp language list
		private IMMPResource resourceBlock;
		
		LanguagesListNode() {
			super(Messages.ResourceModel_languagesNode);
		}

		LanguagesListNode(IMMPResource resourceBlock) {
			super(Messages.ResourceModel_languagesNode);
			this.resourceBlock = resourceBlock;
		}
		
		@Override
		public boolean canAdd() {
			return false;
		}
		
		@Override
		public boolean canEdit(Object element) {
			return true;
		}

		Collection getChildCollection() {
			Collection result;
			if (resourceBlock != null) {
				result = resourceBlock.getLanguages();
			} else {
				result = editorContext.mmpView.getLanguages();
			}
			return result;
		}

		public void doAdd() {	
			// not enabled
		}
		
		private ICarbideListProvider listProvider() {
			ICarbideListProvider listProvider;
			if (resourceBlock != null) {
				listProvider = new MMPResourceBlockLanguagesListProvider(resourceBlock.getSource());
			} else {
				listProvider = EMMPListSelector.LANGUAGES;
			}
			return listProvider;
		}

		public void doEdit(Object element) {
			ICarbideListProvider listProvider = listProvider();
			ListSelectionDialog dialog = new LanguageSelectionDialog(getViewer().getControl().getShell(),
					EMMPLanguage.values(),
					new ArrayContentProvider(), new LabelProvider(), 
					Messages.ResourceModel_editLanguagesDialogPrompt);
			setDialog(dialog);
			dialog.setInitialElementSelections(listProvider.fetchList(editorContext.mmpView));
			if (dialog.open() == Dialog.OK) {
				Object[] selectedLanguages = dialog.getResult();
				List<Object> languages = new ArrayList<Object>();
				for (Object lang : selectedLanguages) {
					languages.add((EMMPLanguage) lang);
				}
				
				ReplaceAllListValueOperation op = new ReplaceAllListValueOperation(editorContext.mmpView,
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
						ControlHandler.getHandlerForControl(getViewer().getControl()),
						listProvider, languages);
				editorContext.executeOperation(op);
				refreshViewerElement(this);
			}
		}
		
		public void doRemove(Object element) {
			int index = listProvider().fetchList(editorContext.mmpView).indexOf(element);
			if (index >= 0) {
				RemoveListValueOperation op = new RemoveListValueOperation(
						editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
						ControlHandler.getHandlerForControl(getViewer().getControl()),
						listProvider(), Collections.singletonList(index));
				editorContext.executeOperation(op);
				Object nodeToRefresh = resourceBlock != null? resourceBlocks : this;
				refreshViewerElement(nodeToRefresh);
			}
		}

		public String getImageKey() {
			return ICarbideSharedImages.IMG_LANGUAGES_16_16;
		}
	}
	
	/**
	 * Fetches the language list from the START RESOURCE block for
	 * command operations
	 */
	static class MMPResourceBlockLanguagesListProvider implements ICarbideListProvider {
		
		private final IPath sourcePath;

		MMPResourceBlockLanguagesListProvider(IPath sourcePath) {
			Check.checkArg(sourcePath);
			this.sourcePath = sourcePath;
		}

		@SuppressWarnings("unchecked") //$NON-NLS-1$
		public List<Object> fetchList(IView view) {
			List result = null;
			List list = EMMPListSelector.START_RESOURCE.fetchList(view);
			for (Object entry : list) {
				IMMPResource resourceBlock = (IMMPResource) entry;
				if (sourcePath.equals(resourceBlock.getSource())) {
					result = resourceBlock.getLanguages();
				}
			}
			return result;
		}

		public String getDisplayText() {
			return Messages.ResourceModel_resourceLanguagesListDisplayText;
		}
	}
	
	private void initNodes() {
		
		// Only support old-style resources if some are present already
		List<IPath> userResources = editorContext.mmpView.getUserResources();
		List<IPath> systemResources = editorContext.mmpView.getSystemResources();
		supportOldStyleResource = userResources.size() > 0 || systemResources.size() > 0;
		
		// Only support AIF if we're on a pre 9.0 (EKA1) system
		//supportAIF = editorContext.activeBuildConfig.getSDK().isEKA1();
		supportAIF = false;  // Carbide 3.0 does not support EKA1

		List<ITreeNode> nodes = new ArrayList<ITreeNode>();
		nodes.add(resourceBlocks);
		if (supportOldStyleResource) {
			nodes.add(userResourceList);
			nodes.add(sysResourceList);
		}
		
		nodes.add(bitmapsList);

		nodes.add(languageList);
				
		if (supportAIF) {
			nodes.add(aifList);
		}
		
		initializeNodes(nodes.toArray(new ITreeNode[nodes.size()]));	
	}
	
	

	@Override
	public void refreshFromModel() {
		initNodes();
		super.refreshFromModel();
	}

	public ResourcePresentationModel(final MMPEditorContext editorContext) {
		this.editorContext = editorContext;
		
		resourceBlocks = new ResourceBlocksNode();
		userResourceList = new ResourceListNode(EMMPListSelector.USER_RESOURCES);
		sysResourceList = new ResourceListNode(EMMPListSelector.SYSTEM_RESOURCES);
		bitmapsList = new BMPListNode();
		languageList = new LanguagesListNode();
		aifList = new AIFListNode();
			
		initNodes();
	}
	
	ResourceBlockNode findBlockForMMPResource(IMMPResource mmpResource) {
		ResourceBlockNode result = null;
		Object[] children = resourceBlocks.getChildren();
		for (Object child : children) {
			ResourceBlockNode rbn = (ResourceBlockNode) child;
			if (rbn.resourceBlock == mmpResource) {
				result = rbn;
				break;
			}
		}
		return result;
	}
}
