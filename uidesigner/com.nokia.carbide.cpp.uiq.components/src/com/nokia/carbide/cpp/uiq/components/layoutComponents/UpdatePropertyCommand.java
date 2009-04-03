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
package com.nokia.carbide.cpp.uiq.components.layoutComponents;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;
public class UpdatePropertyCommand extends AbstractCommand {
	
	protected static final String LABEL = "UpdatePropertyCommand.label";
	protected static final String DESCRIPTION = "UpdatePropertyCommand.description";
	
	private final PropertyInfo propertyToChange;
	private final EObject eObject;
	private final IComponentInstance instance;
	private final IDesignerDataModel model;
	
	class NodeInfo {
		int savedIndex;
		String ownerName;
		String nodeName;
	}	
	
	public UpdatePropertyCommand(EObject eObject, IComponentInstance instance, IDesignerDataModel model, 
			String compoundPropertyName, String subPropertyName) {
		super(LABEL, DESCRIPTION);
		
		Check.checkArg(eObject);
		Check.checkArg(instance);
		Check.checkArg(model);
		Check.checkArg(compoundPropertyName);
		Check.checkArg(subPropertyName);
		
		this.propertyToChange = new PropertyInfo(compoundPropertyName, subPropertyName);
		this.eObject = eObject;
		this.instance = instance;
		this.model = model;
		//saveNodesReferences
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
	 */
	protected boolean prepare() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	public void execute() {
//		System.out.println("***UpdatePropertyCommand.execute");
		if (!canExecute())
			return;
		//this.propertyToChange.getPropertySource().setPropertyValue(this.propertyToChange.getName(), this.propertyToChange.getNewValue());
		
		EObject newLayoutManager = null;
		for (EObject child : instance.getChildren()) {
			if (isLayoutManagerItem(child, model)) {
				newLayoutManager = child;
				break;
			}
		}
		
		IPropertySource instanceProperties = ModelUtils.getPropertySource(eObject);
		IPropertySource childProperties = ModelUtils.getPropertySource(newLayoutManager);
		IPropertySource  layoutManagerProperty = (IPropertySource )instanceProperties.getPropertyValue(this.propertyToChange.getCompoundPropertyName()); //$NON-NLS-1$
		this.propertyToChange.backupOldValue(layoutManagerProperty);
		layoutManagerProperty.setPropertyValue(this.propertyToChange.getSubPropertyName(), childProperties.getPropertyValue("name")); //$NON-NLS-1$
		
		
		this.propertyToChange.setOldValue(this.propertyToChange.getPropertySource().getPropertyValue("type"));
		layoutManagerProperty.setPropertyValue("type", ModelUtils.getComponentInstance(newLayoutManager).getComponent().getFriendlyName()); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	public void redo() {
//		System.out.println("***UpdatePropertyCommand.redo");
		execute();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
	 */
	public void undo() {
//		System.out.println("***UpdatePropertyCommand.undo");
		this.propertyToChange.restoreOldValue();
		this.propertyToChange.getPropertySource().setPropertyValue("type", this.propertyToChange.getOldValue());
		//restoreReferences();
	}

	class PropertyInfo {
		String compoundPropertyName;
		String subPropertyName;
		Object oldValue;
		IPropertySource propertySource;
		
		public PropertyInfo(String compoundPropertyName, String subPropertyName) {
			super();
			this.compoundPropertyName = compoundPropertyName;
			this.subPropertyName = subPropertyName;
		}
		
		public void backupOldValue(IPropertySource propertySource) {
			this.propertySource = propertySource;
			this.oldValue = propertySource.getPropertyValue(this.subPropertyName);
		}
		
		public void restoreOldValue() {
			propertySource.setPropertyValue(this.subPropertyName, this.oldValue);
		}

		public String getCompoundPropertyName() {
			return compoundPropertyName;
		}
		public void setCompoundPropertyName(String compoundPropertyName) {
			this.compoundPropertyName = compoundPropertyName;
		}
		public String getSubPropertyName() {
			return subPropertyName;
		}
		public void setSubPropertyName(String subPropertyName) {
			this.subPropertyName = subPropertyName;
		}
		public Object getOldValue() {
			return oldValue;
		}
		public void setOldValue(Object oldValue) {
			this.oldValue = oldValue;
		}
		public IPropertySource getPropertySource() {
			return propertySource;
		}
		public void setPropertySource(IPropertySource propertySource) {
			this.propertySource = propertySource;
		}
	}
	
	private static final String LAYOUT_MANAGER_ITEM_BASE_TYPE = "com.nokia.carbide.uiq.LayoutManager"; //$NON-NLS-1$
	
	private boolean isLayoutManagerItem(EObject object, IDesignerDataModel model) {
		String componentId = model.getComponentId(object);
		if (componentId != null) {
			IComponent component = model.getComponentSet().lookupComponent(componentId);
			return ModelUtils.isOfType(component, LAYOUT_MANAGER_ITEM_BASE_TYPE);
		}
		
		return false;
	}
}
