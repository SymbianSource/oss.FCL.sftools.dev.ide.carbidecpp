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
package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.property.AbstractPropertyEditorFactory;
import com.nokia.sdt.component.symbian.displaymodel.DisplayModelS60;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.LayoutAreaConfigurationListener;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.ModelObjectComboBoxCellEditor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Composite;

import java.util.*;

public class LayoutConfigEditorFactory extends AbstractPropertyEditorFactory {

	private static final String VARIABLE = "variable";
	private static final String VARIABLE_VALUE = "variable"; //$NON-NLS-1$
	private DisplayModelS60 displayModel;
	private List<String> items;
	private LayoutAreaConfigurationListener listener;
	private LabelProvider labelProvider;
	private LayoutConfigComboBoxCellEditor cellEditor;

	public class LayoutConfigComboBoxCellEditor extends ModelObjectComboBoxCellEditor {
		
		public LayoutConfigComboBoxCellEditor(Composite parent, List<String> items, ILabelProvider labelProvider) {
			super(parent, items, labelProvider);
		}

		@Override
		public void dispose() {
			displayModel.removeLayoutAreaListener(listener);
			super.dispose();
		}
	}

	@Override
	public ILabelProvider createLabelProvider(final EObject object, String propertyId) {
		if (labelProvider == null) {
			initDisplayModel(object);
			labelProvider = new LabelProvider() {
			    public String getText(Object element) {
			    	String text = VARIABLE;
			    	LayoutAreaConfiguration config = null;
			    	if (element instanceof String) {
		    			config = getConfigFromId((String) element);
			    	}
			    	if (config != null)
			    		text = config.getDisplayName();
			    	
			    	return text;
			    }
			};
		}
		
		return labelProvider;
	}
	
	public LayoutAreaConfiguration getConfigFromId(String id) {
		if (displayModel == null)
			return null;
		
		Collection<LayoutAreaConfiguration> configs = displayModel.getAllLayoutAreaConfigurations();
		if (configs == null)
			return null;
		
		for (LayoutAreaConfiguration config : configs) {
			if (config.getID().equals(id))
				return config;
		}
		
		return null;
	}
	
	private void initItems(Collection<LayoutAreaConfiguration> configs) {
		items = new ArrayList();
		items.add(VARIABLE_VALUE);
		for (LayoutAreaConfiguration config : configs) {
			items.add(config.getID());
		}
	}
	
	private void initItems() {
		initItems(displayModel.getAllLayoutAreaConfigurations());
	}

	@Override
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyId) {
		initDisplayModel(object);
		initItems();
		displayModel.addLayoutAreaListener(listener = new LayoutAreaConfigurationListener() {
			public void configurationChanged(LayoutAreaConfiguration configuration) {}
			public void configurationChanging(LayoutAreaConfiguration configuration) {}

			public void configurationSetChanged(Collection newConfigurations) {
				Object value = cellEditor.getValue();
				initItems(newConfigurations);
				cellEditor.setModelItems(items, labelProvider);
				cellEditor.setValue(value);
			}
		});
		createLabelProvider(object, propertyId);
		cellEditor = new LayoutConfigComboBoxCellEditor(parent, items, labelProvider);
		return cellEditor;
	}

	private void initDisplayModel(EObject object) {
		if (displayModel == null) {
			try {
				displayModel = (DisplayModelS60) ModelUtils.getDisplayModel(object);
			} catch (CoreException e) {
				Logging.log(Series60ComponentPlugin.getDefault(), e.getStatus());
			}
		}
	}

	@Override
	public ICellEditorValidator createCellEditorValidator(EObject object, String propertyId) {
		return null;
	}
}

