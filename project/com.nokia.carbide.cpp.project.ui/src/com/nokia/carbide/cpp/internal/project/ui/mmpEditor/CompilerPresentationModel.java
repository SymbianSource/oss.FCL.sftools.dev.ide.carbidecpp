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
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.ChangeMapValueOperation;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.EMMPListSelector;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.IncludeDirectoryDialog;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.ToolOptionsDialog;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;

import java.util.*;
import java.util.Map.Entry;

/**
 * Data model for the compiler options tree view in the source pane.
 * This model sits between the viewer and the "real" MMP view model in order to provide
 * the nodes for creatable settings items, path conversion, etc. 
 */
public class CompilerPresentationModel extends TreePresentationModel {
	
	private final MMPEditorContext editorContext;
	final IncludesContainer userIncludes;
	final IncludesContainer sysIncludes;
	final Macros macros;
	final CompilerOptions compilerOptions;
	final LinkerOptions linkerOptions;
	
	public class IncludesContainer extends BaseTreeNode {
		EMMPListSelector listSelector;
		EMMPPathContext pathContext;
		List<PathElement> elements;
		
		public IncludesContainer(EMMPListSelector listSelector, EMMPPathContext pathContext) {
			super(listSelector.getDisplayText());
			this.listSelector = listSelector;
			this.pathContext = pathContext;
			refreshFromModel();
		}
		
		protected Collection getChildCollection() {
			return listSelector.fetchList(editorContext.mmpView);
		}
		
		public Object[] getChildren() {
			return elements.toArray();
		}
		
		PathElement findPathElementForIPath(IPath path) {
			PathElement result = null;
			for (PathElement element : elements) {
				if (element.mmpViewPath.equals(path)) {
					result = element;
					break;
				}
			}
			return result;
		}
		
		public boolean isChild(Object element) {
			boolean result = false;
			if (element instanceof PathElement) {
				PathElement pe = (PathElement) element;
				result = pe.container == this;
			}
			return result;
		}
		
		public boolean canMoveUp(Object element) {
			boolean result = false;
			if (element instanceof PathElement) {
				PathElement pe = (PathElement) element;
				result = pe.index > 0;
			}
			return result;
		}
		
		@SuppressWarnings("unchecked") //$NON-NLS-1$
		public boolean canMoveDown(Object element) {
			boolean result = false;
			if (element instanceof PathElement) {
				PathElement pe = (PathElement) element;
				List<IPath> paths = listSelector.fetchList(editorContext.mmpView);
				result = pe.index < paths.size() - 1;
			}
			return result;
		}
		
		public void doAdd() {
			IncludeDirectoryDialog dialog = new IncludeDirectoryDialog(getViewer().getControl().getShell(),
					editorContext.project, pathContext, editorContext.pathHelper,
					editorContext.activeBuildConfig);
			int dlgResult = dialog.open();
			if (dlgResult == Dialog.OK) {
				IPath path = dialog.getResult();
				if (path != null) {
					List<IPath> items = new ArrayList<IPath>();
					items.add(path);
					AddListValueOperation op = new AddListValueOperation(
							editorContext.mmpView,
							new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
							ControlHandler.getHandlerForControl(getViewer().getControl()),
							listSelector, items);
					editorContext.executeOperation(op);
					refreshFromModel();
					refreshViewerElement(this);
				}
			}
		}
		public void doEdit(Object element) {
			Check.checkArg(element instanceof PathElement);
			PathElement pe = (PathElement) element;
			IncludeDirectoryDialog dialog = new IncludeDirectoryDialog(getViewer().getControl().getShell(),
					editorContext.project, pathContext, editorContext.pathHelper,
					editorContext.activeBuildConfig);
			dialog.setInitialPath(pe.convertedPath != null? pe.convertedPath : pe.mmpViewPath );
			int dlgResult = dialog.open();
			if (dlgResult == Dialog.OK) {
				IPath newMMPPath = dialog.getResult();
				if (newMMPPath != null) {
					Map<Integer, Object> replaceMap = new HashMap<Integer, Object>();
					replaceMap.put(pe.index, newMMPPath);
					ReplaceListValueOperation op = new ReplaceListValueOperation(
							editorContext.mmpView, 
							new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
							ControlHandler.getHandlerForControl(getViewer().getControl()),
							listSelector, replaceMap);
					editorContext.executeOperation(op);
					refreshFromModel();
					refreshViewerElement(this);
				}
			}
		}

		public void doRemove(Object element) {
			Check.checkArg(element instanceof PathElement);
			int index = elements.indexOf(element);
			if (index >= 0) {
				RemoveListValueOperation op = new RemoveListValueOperation(
						editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
						ControlHandler.getHandlerForControl(getViewer().getControl()),
						listSelector, Collections.singletonList(index));
				editorContext.executeOperation(op);
				refreshFromModel();
				refreshViewerElement(this);
			}
		}
		
		public void doMove(Object element, int positionDelta) {
			Check.checkArg(element instanceof PathElement);
			PathElement pe = (PathElement) element;
			Map<Integer, Integer> moveMap = new HashMap<Integer, Integer>();
			int newIndex = pe.index + positionDelta;
			moveMap.put(pe.index, newIndex);
			ControlHandler handler = ControlHandler.getHandlerForViewer(getViewer());
			MoveListValueOperation op = new MoveListValueOperation(
					editorContext.mmpView, 
					new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
					handler, listSelector, moveMap);
			editorContext.executeOperation(op);
			refreshFromModel();
			refreshViewerElement(this);	
			handler.setValue(elements.toArray()[newIndex]);
		}

		@SuppressWarnings("unchecked") //$NON-NLS-1$
		@Override
		public void refreshFromModel() {
			elements = new ArrayList<PathElement>();
			List<IPath> paths = listSelector.fetchList(editorContext.mmpView);
			for (int i = 0; i < paths.size(); i++) {
				IPath currPath = paths.get(i);
				PathElement pe = new PathElement(i, currPath, convertPath(pathContext, currPath), this);
				elements.add(pe);
			}
		}

		public String getImageKey() {
			return pathContext == EMMPPathContext.SYSTEMINCLUDE?
					ICarbideSharedImages.IMG_SYSTEM_INCLUDES_16_16 :
					ICarbideSharedImages.IMG_USER_INCLUDES_16_16;
		}		
	}

	public class Macros extends BaseTreeNode {
		
		Macros() {
			super(Messages.CompilerSettingsModel_macros);
		}
		
		protected Collection getChildCollection() {
			return editorContext.mmpView.getListArgumentSettings().get(EMMPStatement.MACRO);
		}
		
		String doMacroInputDialog(String initialValue) {
			InputDialog dialog = new InputDialog(getViewer().getControl().getShell(),
					Messages.CompilerSettingsModel_editMacroTitle, 
					Messages.CompilerSettingsModel_editMacroPrompt, 
					initialValue, null);
			String result = null;
			if (dialog.open() == Dialog.OK) {
				result = dialog.getValue();
			}
			return result;
		}
		
		public void doAdd() {
			String newMacro = doMacroInputDialog(""); //$NON-NLS-1$
			if (newMacro != null) {;
				if (newMacro.length() > 0) {
					List<String> items = new ArrayList<String>();
					items.add(newMacro);
					AddListValueOperation op = new AddListValueOperation(editorContext.mmpView,
							new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
							ControlHandler.getHandlerForControl(getViewer().getControl()),
							EMMPListSelector.MACROS, items);
					editorContext.executeOperation(op);
					refreshViewerElement(this);					
				}
			}	
		}
		
		public void doEdit(Object element) {
			String newValue = doMacroInputDialog(element.toString());
			if (newValue != null && newValue.length() > 0 && !newValue.equals(element)) {
				List macroList = EMMPListSelector.MACROS.fetchList(editorContext.mmpView);
				int index = macroList.indexOf(element);
				if (index >= 0) {
					Map<Integer, Object> replaceMap = new HashMap<Integer, Object>();
					replaceMap.put(index, newValue);
					ReplaceListValueOperation op = new ReplaceListValueOperation(
							editorContext.mmpView, 
							new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
							ControlHandler.getHandlerForControl(getViewer().getControl()),
							EMMPListSelector.MACROS, replaceMap);
					editorContext.executeOperation(op);
					refreshViewerElement(this);
				}
			}
		}
		
		public void doRemove(Object element) {
			Check.checkArg(element instanceof String);
			List macroList = EMMPListSelector.MACROS.fetchList(editorContext.mmpView);
			int index = macroList.indexOf(element);
			if (index >= 0) {
				RemoveListValueOperation op = new RemoveListValueOperation(
						editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()),
						ControlHandler.getHandlerForControl(getViewer().getControl()),
						EMMPListSelector.MACROS, Collections.singletonList(index));
				editorContext.executeOperation(op);
				refreshViewerElement(this);
			}
		}

		public String getImageKey() {
			return ICarbideSharedImages.IMG_MACROS_16_16;
		}
	}
	
	public abstract class ToolOptions extends BaseTreeNode {
		String inputTitle;
		String inputPrompt;
		EMMPStatement mmpStatement;
		
		ToolOptions(String displayText, String inputTitle, String inputPrompt, 
				EMMPStatement mmpStatement) {
			super(displayText);
			this.inputTitle = inputTitle;
			this.inputPrompt= inputPrompt;
			this.mmpStatement = mmpStatement;
		}
		
		Collection getChildCollection() {
			return getOptions().entrySet();
		}
		
		abstract Map<String, String> getOptions();

		public void doAdd() {
			ToolOptionsDialog dialog = new ToolOptionsDialog(getViewer().getControl().getShell(), 
					inputTitle, true);
			Map<String, String> optionsMap = getOptions();
			dialog.setCurrentOptions(optionsMap);
			if (dialog.open() == Dialog.OK) {
				String toolChain = dialog.getToolChain();
				String options = dialog.getOptions();
				ChangeMapValueOperation op = new ChangeMapValueOperation(
						editorContext.mmpView, mmpStatement, 
						optionsMap, toolChain, options, getViewer().getControl(),
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()));
				editorContext.executeOperation(op);
				refreshViewerElement(this);
			}
		}
		public void doEdit(Object element) {
			Map.Entry entry = (Entry) element;
			ToolOptionsDialog dialog = new ToolOptionsDialog(getViewer().getControl().getShell(), 
					inputTitle, false);
			dialog.setInitialValues(entry.getKey().toString(), entry.getValue().toString());
			Map<String, String> optionsMap = getOptions();
			dialog.setCurrentOptions(optionsMap);
			if (dialog.open() == Dialog.OK) {
				String toolChain = dialog.getToolChain();
				String options = dialog.getOptions();
				ChangeMapValueOperation op = new ChangeMapValueOperation(
						editorContext.mmpView, mmpStatement, 
						optionsMap, toolChain, options, getViewer().getControl(),
						new FormEditorEditingContext(editorContext.editor, getViewer().getControl()));
				editorContext.executeOperation(op);
				refreshViewerElement(this);
			}
		}
		public void doRemove(Object element) {
			Map.Entry entry = (Entry) element;
			Map<String, String> optionsMap = getOptions();
			ChangeMapValueOperation op = new ChangeMapValueOperation(
					editorContext.mmpView, mmpStatement, 
					optionsMap, entry.getKey(), null, getViewer().getControl(),
					new FormEditorEditingContext(editorContext.editor, getViewer().getControl()));
			editorContext.executeOperation(op);
			refreshViewerElement(this);
		}
	}

	public class CompilerOptions extends ToolOptions {
		
		CompilerOptions() {
			super(Messages.CompilerSettingsModel_compilerOptions, 
					Messages.CompilerSettingsModel_editCompileOptionsDialogTitle,
					Messages.CompilerSettingsModel_enterOptionsTextPrompt, EMMPStatement.OPTION);
		}

		@Override
		Map<String, String> getOptions() {
			return editorContext.mmpView.getOptions();
		}

		public String getImageKey() {
			return ICarbideSharedImages.IMG_COMPILER_OPTIONS_16_16;
		}
	}
	
	public class LinkerOptions extends ToolOptions {
		
		LinkerOptions() {
			super(Messages.CompilerSettingsModel_linkerOptionsSettingDisplayText, 
					Messages.CompilerSettingsModel_editLinkerOptionsDialogTitle,
					Messages.CompilerSettingsModel_editLinkerOptionsPrompt, EMMPStatement.LINKEROPTION);
		}

		@Override
		Map<String, String> getOptions() {
			return editorContext.mmpView.getLinkerOptions();
		}

		public String getImageKey() {
			return ICarbideSharedImages.IMG_COMPILER_OPTIONS_16_16;
		}
	}
	
	
	public class PathElement {
		int index;
		IPath mmpViewPath;
		IPath convertedPath;
		ITreeNode container;
		
		public PathElement(int index, IPath mmpViewPath, IPath convertedPath, ITreeNode container) {
			super();
			this.index = index;
			this.mmpViewPath = mmpViewPath;
			this.convertedPath = convertedPath;
			this.container = container;
		}

		public String toString() {
			return convertedPath != null? convertedPath.toString() : mmpViewPath.toString();
		}
	}
	
	public CompilerPresentationModel(MMPEditorContext editorContext) {
		this.editorContext = editorContext;
		userIncludes = new IncludesContainer(EMMPListSelector.USER_INCLUDES, EMMPPathContext.USERINCLUDE);
		sysIncludes = new IncludesContainer(EMMPListSelector.SYS_INCLUDES, EMMPPathContext.SYSTEMINCLUDE);
		compilerOptions = new CompilerOptions();
		linkerOptions = new LinkerOptions();
		macros = new Macros();
		initializeNodes(new ITreeNode[]{userIncludes, sysIncludes, macros, compilerOptions, linkerOptions});
	}

	IPath convertPath(EMMPPathContext context, IPath mmpViewPath) {
		IPath result = editorContext.pathHelper.convertMMPToProject(context, mmpViewPath);
		return result;
	}
}
