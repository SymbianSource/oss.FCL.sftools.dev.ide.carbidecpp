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
package com.nokia.sdt.symbian.dm;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IEnhancedClipboardProvider;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.IComponentEditor;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.impl.NodeCopier;
import com.nokia.sdt.symbian.SymbianPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.OverrideableCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.gef.ui.actions.Clipboard;

import java.util.*;

public class GEFClipboardEditingDomain extends AdapterFactoryEditingDomain implements IEnhancedClipboardProvider {

	public static final String CLIPBOARD_ROOT = "clipboardRoot"; //$NON-NLS-1$

	private Clipboard gefClipboard; 
	// will work between editors, and between windows belonging to the same instance of the workbench
	// However, it doesn't work across Eclipse instances
	
	private INode containerNode;
	private final IDesignerDataModel ownerModel;
	private IDesignerDataModel clipboardDataModel;
	private Map<Object, EObject> extraObjects;
	
	private class ContentsType extends Pair<EList, Map<Object, EObject>> {
		public ContentsType(EList first, Map<Object, EObject> second) {
			super(first, second);
		}
	}
	
	public GEFClipboardEditingDomain(IDesignerDataModel ownerModel, AdapterFactory adapterFactory, CommandStack commandStack, Map resourceToReadOnlyMap) {
		super(adapterFactory, commandStack, resourceToReadOnlyMap);
		this.ownerModel = ownerModel;
		
		gefClipboard = Clipboard.getDefault();
		extraObjects = new HashMap<Object, EObject>();
	}
	
	public Command createCommand(Class commandClass, CommandParameter commandParameter) {
		// for debugging purposes
		return super.createCommand(commandClass, commandParameter);
	}

	public Command createOverrideCommand(OverrideableCommand command) {
		// for debugging purposes
		return super.createOverrideCommand(command);
	}

	public Collection getClipboard() {
		Object contents = gefClipboard.getContents();
		if (contents == null)
			return null;
		
		Check.checkContract(contents instanceof ContentsType);
		ContentsType contentsType = (ContentsType) contents;
		EList objects = contentsType.first;
		Map<Object, EObject> extraObjects = contentsType.second;
		
		List<INode> simpleClipboard = new ArrayList<INode>();
		for (INode node : (List<INode>) objects) {
			if (!extraObjects.values().contains(node))
				simpleClipboard.add(node);
		}
		
		return simpleClipboard;
	}

	public void setClipboard(Collection clipboard) {
		if (clipboard != null && !clipboard.isEmpty()) {
			INode node = (INode) clipboard.iterator().next();
			Language defaultLanguage = null;
			IDesignerData designerData = node.getDesignerData();
			if (designerData != null) {
				ILocalizedStringBundle stringBundle = designerData.getStringBundle();
				if (stringBundle != null)
					defaultLanguage = stringBundle.getDefaultLanguage();
			}
			try {
				ensureClipboardModel(defaultLanguage);
			} catch (CoreException e) {
				Logging.log(SymbianPlugin.getDefault(), e.getStatus());
			}
			if (containerNode != null) {
				extraObjects.clear();
				containerNode.getChildren().clear();
				addToClipboardContents(clipboard);
			}
		}
	}
	
	private void addToClipboardContents(Collection<EObject> newContents) {
		for (EObject object : newContents) {
			Check.checkState(object instanceof INode);
			INode node = (INode) object;
			NodeCopier.copyNode(node, containerNode, CommandParameter.NO_INDEX, false, new NodeCopier.IFilter() {
				public boolean test(INode node) {
					IComponentEditor componentEditor = ModelUtils.getComponentEditor(node);
					return (componentEditor == null) || !componentEditor.isTemporaryObject();
				}
			});
		}
		
		ContentsType contents = new ContentsType(containerNode.getChildren(), extraObjects);
		gefClipboard.setContents(contents);
	}
	
	private void ensureClipboardModel(Language defaultLanguage) throws CoreException {
		if (clipboardDataModel != null)
			clipboardDataModel.dispose();
		
		Check.checkContract(!((DesignerDataModel) ownerModel).isClipboardDataModel);
		clipboardDataModel = ownerModel.getProvider().createCompatibleTemporary(ownerModel);
		DesignerDataModel designerDataModel = ((DesignerDataModel) clipboardDataModel);
		designerDataModel.isClipboardDataModel = true;
		IDesignerData designerData = designerDataModel.getDesignerData();
		containerNode = DmFactory.eINSTANCE.createINode();
		IPropertyContainer properties = containerNode.getProperties();
		properties.set(INode.NAME_PROPERTY, properties.createLiteral(CLIPBOARD_ROOT)); //$NON-NLS-1$
		
		designerData.getRootContainers().add(containerNode);
		if (defaultLanguage != null)
			designerData.getStringBundle().setDefaultLanguage(defaultLanguage);
	}

	public void copyExtraObject(Object key, EObject extraObject) {
		if (extraObject != null) {
			String extraObjectName = ((INode) extraObject).getName();
			// only allow a single object in the clipboard model with a given name
			EObject clipObj = clipboardDataModel.findByNameProperty(extraObjectName);
			if (clipObj == null) {
				addToClipboardContents(Collections.singletonList(extraObject));
				clipObj = clipboardDataModel.findByNameProperty(extraObjectName);
			}
			this.extraObjects.put(key, clipObj);
		}
	}

	public void pasteExtraObject(Object key, EObject newParent, int insertPosition) {
		Object contents = gefClipboard.getContents();
		Check.checkContract(contents instanceof ContentsType);
		ContentsType contentsType = (ContentsType) contents;
		EObject object = contentsType.second.get(key);
		NodeCopier.copyNode((INode) object, (INode) newParent, insertPosition, true);
	}

}
