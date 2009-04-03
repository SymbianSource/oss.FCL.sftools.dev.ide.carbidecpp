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
package com.nokia.carbide.cpp.uiq.component.layoutManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.uiq.components.UIQComponentPlugin;
import com.nokia.sdt.component.ComponentSetResult;
import com.nokia.sdt.component.ComponentSystem;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentProvider;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.property.AbstractPropertyEditorFactory;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.ComponentSDKSelector;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.ModelObjectComboBoxCellEditor;

/**
 * Creates editor for string list properties.
 * 
 */
public class LayoutManagerSelectorEditorFactory extends AbstractPropertyEditorFactory {

	protected ILabelProvider labelProvider;

	//protected LayoutManagerSelectorData data;
	
	/**
	 * 
	 */
	public LayoutManagerSelectorEditorFactory() {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createLabelProvider(org.eclipse.emf.ecore.EObject,
	 *      java.lang.Object)
	 */
	public ILabelProvider createLabelProvider(final EObject object, String propertyId) {
		return labelProvider = new LabelProvider() {
		    public String getText(Object element) {
		    	if ((element == null) || (element.toString().length() == 0))
		    		return ""; //$NON-NLS-1$
		    	else {
		    		if (element instanceof IPropertySource ) {
		    			IPropertySource  layoutManagerProperty = (IPropertySource )element;
						if (layoutManagerProperty.getPropertyValue("type") != null)
							return (String)layoutManagerProperty.getPropertyValue("type");
						else
							return "";
						
		    		} else if (element instanceof IComponent){
//		    			System.out.println("***labelProvider***getFriendlyName()");
		    			return ((IComponent)element).getFriendlyName();
		    		} else {
		    			return element.toString();
		    		}
		    	}
		        
		    }
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createCellEditor(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public CellEditor createCellEditor(Composite parent, EObject object,
			String propertyId) {
		
		IPropertySource containerProperties = ModelUtils.getPropertySource(object);
		IPropertySource  layoutManagerProperty = (IPropertySource )containerProperties.getPropertyValue("layoutManager"); //$NON-NLS-1$
		String referencedLayoutManagerName = (String)layoutManagerProperty.getPropertyValue("referenceLayoutManager");
		IComponent referencedLayoutManagerType = ModelUtils.lookupReference(ModelUtils.getModel(object), referencedLayoutManagerName).getComponent();
		return new ComponentTypeCellEditor(parent, getValueList(), labelProvider, ModelUtils.getModel(object), referencedLayoutManagerType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createCellEditorValidator(org.eclipse.emf.ecore.EObject,
	 *      java.lang.Object)
	 */
	public ICellEditorValidator createCellEditorValidator(EObject object,
			String propertyId) {
		return null;
	}
	
	private List<IComponent> getValueList() {
		final ArrayList<IComponent> result = new ArrayList<IComponent>();
		//result.add(null);
		
		String SDK_NAME = "com.uiq"; //$NON-NLS-1$
		String VERSION = "3.0"; //$NON-NLS-1$
		Version version = new Version(VERSION);
        
		ComponentSDKSelector filter = new ComponentSDKSelector(SDK_NAME, version);
		
		ComponentSystem cs = ComponentSystem.getComponentSystem();
		IComponentProvider componentProvider = null;
		try {
			componentProvider = cs.getProvider(ComponentProvider.PROVIDER_ID);
		} 
		catch (CoreException e) {
			Check.reportFailure(Messages.getString("ViewWizard.ComponentProviderError"), e); //$NON-NLS-1$
		}
		ComponentSetResult csResult = componentProvider.queryComponents(filter);
		if (csResult.getStatus() != null) {
			Logging.log(UIQComponentPlugin.getDefault(), csResult.getStatus());
			Logging.showErrorDialog(null, null, csResult.getStatus());
		}
		IComponentSet componentSet = csResult.getComponentSet();
		
		Iterator<IComponent> componentIterator = componentSet.iterator();
		while (componentIterator.hasNext()) {
			IComponent component = componentIterator.next();
			IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
			if (!component.isAbstract() && attributes.getAttribute("is-layout-manager") != null && attributes.getAttribute("is-layout-manager").equals("true")) {
//				System.out.println(component);
				result.add(component);
			}
		}
		
		return result;
	}
	
	static public class ComponentTypeCellEditor extends ModelObjectComboBoxCellEditor {

		private IDesignerDataModel model;
		private IComponent initialValue = null;
		private IComponent lastValue = null;
		
		public ComponentTypeCellEditor(Composite composite, List values, ILabelProvider labelProvider, IDesignerDataModel model, IComponent initialValue) {
			super(composite, values, labelProvider);
			this.model = model;
			this.initialValue = initialValue;
			
			updateValue(initialValue);
			lastValue = initialValue;
			
		}

		private void updateValue(IComponent value) {
			for (int i=0; i<modelItems.size(); i++) {
				if (((IComponent)modelItems.get(i)).getId().equals(value.getId())) {
					super.doSetValue(modelItems.get(i));
					break;
				}
			}
		}
		
		protected void doSetValue(Object value) {
			lastValue = (IComponent)this.getValue();
			if (value != null) {
				int index = modelItems.indexOf(value);
				if (index != -1)
					super.doSetValue(value); 
				else {
					if (value instanceof IPropertySource) {
						IComponentInstance referencedLayoutManager = ModelUtils.lookupReference(this.model,  (String)((IPropertySource)value).getPropertyValue("referenceLayoutManager"));
						updateValue(referencedLayoutManager.getComponent());
					} else if (value instanceof IComponent) {
						super.doSetValue(value);
					}
				}
			}
		}
		protected void fireApplyEditorValue() {
			if (this.getValue() != null && lastValue != null && !lastValue.equals(this.getValue())) {
				super.fireApplyEditorValue();
				this.initialValue = (IComponent)this.getValue();
			} else {
				fireCancelEditor();
			}
		}
		
		protected void fireCancelEditor() {
			super.fireCancelEditor();
			
			updateValue(initialValue);		
			this.lastValue = this.initialValue;
		}
	}

}