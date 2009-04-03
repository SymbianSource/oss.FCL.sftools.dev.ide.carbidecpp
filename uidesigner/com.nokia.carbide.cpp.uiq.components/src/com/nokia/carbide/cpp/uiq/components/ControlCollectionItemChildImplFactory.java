/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.uiq.components;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.carbide.cpp.uiq.components.controlCollection.ControlCollectionAdapterFactory.CreationTool;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IClipboardCommandExtender;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;

public class ControlCollectionItemChildImplFactory implements IImplFactory {
	
	public Object getImpl(EObject componentInstance) {
		return new ControlCollectionItemChildClipboardCommandExtender(componentInstance);
	}
	
	class ControlCollectionItemChildClipboardCommandExtender implements IClipboardCommandExtender {
		
		private static final String LAYOUT_CONTROL_BASE = "com.nokia.carbide.uiq.LayoutControlBase"; //$NON-NLS-1$
				
		EObject eobjectCI;
		
		public ControlCollectionItemChildClipboardCommandExtender(EObject eobjectCI) {
			this.eobjectCI = eobjectCI;
		}
		
		public EObject getEObject() {
			return eobjectCI;
		}

		public Command getExtendedCopyToClipboardCommand(EditingDomain editingDomain, Command command) {
			return command;
		}

		public Command getExtendedPasteFromClipboardCommand(EObject owner, EditingDomain editingDomain, Command command) {
			IComponentInstance ownerCI = ModelUtils.getComponentInstance(owner);
			EObject rootContainer = ownerCI.getRootContainer();
			if (rootContainer.equals(owner)) {
				return command;
			}
			if (ModelUtils.isInstanceOf(owner, LAYOUT_CONTROL_BASE)) {
				IPropertySource layoutControlItemPS = ModelUtils.getPropertySource(owner);
				String controlCollectionItemName = layoutControlItemPS.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME).toString();
				IComponentInstance controlCollectionItemCI = ModelUtils.lookupReference(ModelUtils.getModel(owner), controlCollectionItemName);
				EObject controlCollectionItemEO = controlCollectionItemCI.getEObject();
				IDesignerDataModel ddm = ModelUtils.getModel(getEObject());
				Command pasteCommand = ddm.createPasteComponentInstancesCommand(controlCollectionItemEO);
				return pasteCommand;
			}
			return command;
		}
		
	}

}
