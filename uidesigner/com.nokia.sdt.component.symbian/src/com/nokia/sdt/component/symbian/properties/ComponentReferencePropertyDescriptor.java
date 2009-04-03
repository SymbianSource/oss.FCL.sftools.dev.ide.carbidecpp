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
package com.nokia.sdt.component.symbian.properties;

import com.nokia.sdt.component.NewComponentReferenceParameter;
import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.component.ComponentReferencePropertyType;
import com.nokia.sdt.emf.component.ReferenceScopeType;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.ModelObjectComboBoxCellEditor;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ComponentReferencePropertyDescriptor extends AbstractPropertyDescriptor {
	
	// If defined, restrict allowed instance to those derived
	// from this component
	private String baseComponentID;
	private IDesignerDataModel model;

	public ComponentReferencePropertyDescriptor(
			ComponentReferencePropertyType propertyType, 
			ComponentReferenceTypeDescriptor typeDescriptor,
			IPropertyValueSource valueSource, ILocalizedStrings strings, boolean forceReadOnly) {
		super(propertyType, typeDescriptor, valueSource, strings, forceReadOnly);
		this.model = valueSource.getDesignerDataModel();
		this.baseComponentID = propertyType.getConstraint();
	}

	static IComponentInstance getCI(EObject object) {
		IComponentInstance result = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
				object, IComponentInstance.class);
		return result;
	}
	
	public ComponentReferencePropertyType getPropertyType() {
		return (ComponentReferencePropertyType) super.getPropertyType();
	}
	
	ICellEditorValidator getValidator() {
		final ComponentReferencePropertyType propertyType = getPropertyType();
		ICellEditorValidator result = new ICellEditorValidator() {
			
			public String isValid(Object value) {
				boolean constraintErr = false;
				boolean childScopeErr = false;
				boolean siblingScopeErr = false;
				String text = null;
				if (value == null) {
					// no validation needed, unless we add a constraint on whether null is allowed
				}
				else if (value instanceof String) {
					// Non-null value, validate on scope and constraint
					text = value.toString();
					EObject node = model.findByNameProperty(text);
					if (node != null) {
						int scope = propertyType.getScope().getValue();
						switch (scope) {
						case ReferenceScopeType.MODEL:
							break;
							
						case ReferenceScopeType.CHILDREN:
							childScopeErr = !ModelUtils.isChildOf(getValueSource().getEObject(), node);
							break;
							
						case ReferenceScopeType.SIBLINGS:
							EObject parent = getValueSource().getEObject().eContainer();
							if (parent != null) {
								siblingScopeErr = !ModelUtils.isChildOf(parent, node);
							}
							break;
							
						}
						
						if (propertyType.getConstraint() != null) {
							constraintErr = !ModelUtils.isInstanceOf(node, propertyType.getConstraint());
						}
					}
				}
				// no check on NewComponentReferenceParameter
				
				String result = null;
				if (siblingScopeErr) {
					String format = Messages.getString("ComponentReferencePropertyDescriptor.siblingScopeErr"); //$NON-NLS-1$
					Object params[] = {text};
					result = MessageFormat.format(format, params);
				}
				else if (childScopeErr) {
					String format = Messages.getString("ComponentReferencePropertyDescriptor.childScopeErr"); //$NON-NLS-1$
					Object params[] = {text};
					result = MessageFormat.format(format, params);
				}
				else if (constraintErr) {
					if (propertyType.getConstraint() != null) {
						String format = Messages.getString("ComponentReferencePropertyDescriptor.0"); //$NON-NLS-1$
						Object params[] = {text, propertyType.getConstraint()};
						result = MessageFormat.format(format, params);
					}
					else {
						String format = Messages.getString("ComponentReferencePropertyDescriptor.1"); //$NON-NLS-1$
						Object params[] = {text};
						result = MessageFormat.format(format, params);
					}
				}
				return result;
			}
		};
		return result;
	}

	private List getValueList() {
		final int scope = getPropertyType().getScope().getValue();
		final EObject thisEObject = getValueSource().getEObject();
		final ArrayList result = new ArrayList();
		
		// Add null for the "None" choice
		result.add(null);
		
		// Add "create new" items
		String creationKeys = getPropertyType().getCreationKeys();
		if (!TextUtils.isEmpty(creationKeys) &&
				CheckForSetValueCommandExtender()) {
			String[] keys = creationKeys.split(","); //$NON-NLS-1$
			for (String key : keys) {
				NewComponentReferenceParameter param = 
					new NewComponentReferenceParameter(key);
				result.add(param);
			}
		}
		
		// Add existing instances, as determined by the scope and constraint
		ComponentInstanceVisitor.Visitor v = new ComponentInstanceVisitor.Visitor() {
			public Object visit(IComponentInstance ci) {
				if (ci.getEObject() != thisEObject && (baseComponentID == null || 
					ModelUtils.isInstanceOf(ci.getEObject(), baseComponentID))) {
					result.add(ci.getName());
				}
				return null;
			}
		};
		
		switch (scope) {
		case ReferenceScopeType.MODEL:
			ComponentInstanceVisitor.preOrderTraversal(model.getRootContainers(), v);
			break;
			
		case ReferenceScopeType.CHILDREN:
			ComponentInstanceVisitor.preOrderTraversal(getValueSource().getEObject(), v);
			break;
			
		case ReferenceScopeType.SIBLINGS:
			EObject parent = getValueSource().getEObject().eContainer();
			if (parent == null) {
				ComponentInstanceVisitor.preOrderTraversal(model.getRootContainers(), v);
			} else {
				ComponentInstanceVisitor.preOrderTraversal(parent, v);
			}
			break;
			
		}
	
		return result;
	}

	public CellEditor doCreatePropertyEditor(Composite parent, int style) {
		ILabelProvider labelProvider = new LabelProvider() {
			public String getText(Object element) {
				String result;
				if (element == null) {
					result = Messages.getString("ComponentReferencePropertyDescriptor.2"); //$NON-NLS-1$
				} else if (element instanceof NewComponentReferenceParameter) {
					NewComponentReferenceParameter param = (NewComponentReferenceParameter) element;
					result = getStrings().getString(param.getCreationKey());
				}
				else
					result = element.toString();
				return result;
			}
		};		
		return new ComponentReferenceCellEditor(parent, getValueList(), labelProvider);
	}

	@Override
	public Object getDefaultValue() {
		return getTypeDescriptor().defaultValue(getValueSource(), getPropertyType().getName());
	}
	
	/**
	 * It's an error if creationKeys are supplied but there's no
	 * SetValueCommandExtender to handle it. Log an message
	 * and skip processing in that case
	 */
	private boolean CheckForSetValueCommandExtender() {
		boolean result = false;
		// could be null in unit tests
		EObject obj = getValueSource().getEObject();
		if (obj != null) {
			Adapter adapter = EcoreUtil.getRegisteredAdapter(obj, ISetValueCommandExtender.class);
			result = adapter != null;
		}
		if (!result) {
			String fmt = Messages.getString("ComponentReferencePropertyDescriptor.creationKeysMissingImpl"); //$NON-NLS-1$
			Object params[] = {getId()};
			ComponentSystemPlugin.log(null, MessageFormat.format(fmt, params));
		}
		return result;
	}
	
	static class ComponentReferenceCellEditor extends ModelObjectComboBoxCellEditor {

		private List valueList;
		
		public ComponentReferenceCellEditor(Composite composite, List values, ILabelProvider labelProvider) {
			super(composite, values, labelProvider);
			this.valueList = values;
			
			final CCombo combo = (CCombo) this.getControl();
			combo.addSelectionListener(new SelectionAdapter() {

				/**
				 * When a NewComponentReferenceParameter item is
				 * selected we want to immediately apply the
				 * value.
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					int index = combo.getSelectionIndex();
					if (index >= 0 && index < valueList.size()) {
						Object value = valueList.get(index);
						if (value instanceof NewComponentReferenceParameter) {
							markDirty();
							setValueValid(true);
							fireApplyEditorValue();
							deactivate();
						}
					}
				}
			});
			
		}
	}
}
