/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 */
public class MultiLineDataQueryCodeImplFactory implements IImplFactory {
	
	static class InitializerAndCommandExtender implements IInitializer, ISetValueCommandExtender {
		
		// type values
		private final static String FirstEdwin = "EMultiDataFirstEdwin"; //$NON-NLS-1$
		private final static String FirstSecEd = "EMultiDataFirstSecEd"; //$NON-NLS-1$
		private final static String FirstTimeEd = "EMultiDataFirstTimeEd"; //$NON-NLS-1$
		private final static String FirstDateEd = "EMultiDataFirstDateEd"; //$NON-NLS-1$
		private final static String FirstDurEd = "EMultiDataFirstDurEd"; //$NON-NLS-1$
		private final static String FirstPhoneEd = "EMultiDataFirstPhoneEd"; //$NON-NLS-1$
		private final static String FirstNumEd = "EMultiDataFirstNumEd"; //$NON-NLS-1$
		private final static String FirstPinEd = "EMultiDataFirstPinEd"; //$NON-NLS-1$
		private final static String FirstIpEd = "EMultiDataFirstIpEd"; //$NON-NLS-1$
		private final static String SecondEdwin = "EMultiDataSecondEdwin"; //$NON-NLS-1$
		private final static String SecondSecEd = "EMultiDataSecondSecEd"; //$NON-NLS-1$
		private final static String SecondTimeEd = "EMultiDataSecondTimeEd"; //$NON-NLS-1$
		private final static String SecondDateEd = "EMultiDataSecondDateEd"; //$NON-NLS-1$
		private final static String SecondDurEd = "EMultiDataSecondDurEd"; //$NON-NLS-1$
		private final static String SecondPhoneEd = "EMultiDataSecondPhoneEd"; //$NON-NLS-1$
		private final static String SecondNumEd = "EMultiDataSecondNumEd"; //$NON-NLS-1$
		private final static String SecondPinEd = "EMultiDataSecondPinEd"; //$NON-NLS-1$
		private final static String SecondIpEd = "EMultiDataSecondIpEd"; //$NON-NLS-1$

		// Editor component Ids
		private final static String CEikEdwin = "com.nokia.sdt.series60.CEikEdwin"; //$NON-NLS-1$
		private final static String SecretEditor = "com.nokia.sdt.series60.SecretEditor"; //$NON-NLS-1$
		private final static String CEikTimeEditor = "com.nokia.sdt.series60.CEikTimeEditor"; //$NON-NLS-1$
		private final static String CEikDateEditor = "com.nokia.sdt.series60.CEikDateEditor"; //$NON-NLS-1$
		private final static String CEikDurationEditor = "com.nokia.sdt.series60.CEikDurationEditor"; //$NON-NLS-1$
		private final static String CAknIntegerEdwin = "com.nokia.sdt.series60.CAknIntegerEdwin"; //$NON-NLS-1$
		private final static String CAknIpFieldEditor = "com.nokia.sdt.series60.CAknIpFieldEditor"; //$NON-NLS-1$

		// Special data query
		private final static String MultiIpDataQuery = "com.nokia.sdt.series60.MultiIpDataQuery"; //$NON-NLS-1$
		
		private final static String[][] typesAndComponentIds = 
		{	{FirstEdwin, CEikEdwin },
			{FirstSecEd, SecretEditor },
			{FirstTimeEd, CEikTimeEditor },
			{FirstDateEd, CEikDateEditor },
			{FirstDurEd, CEikDurationEditor },
			{FirstPhoneEd, CEikEdwin },
			{FirstNumEd, CAknIntegerEdwin },
			{FirstPinEd, SecretEditor },
			{FirstIpEd, CAknIpFieldEditor },
			{SecondEdwin, CEikEdwin },
			{SecondSecEd, SecretEditor },
			{SecondTimeEd, CEikTimeEditor },
			{SecondDateEd, CEikDateEditor },
			{SecondDurEd, CEikDurationEditor },
			{SecondPhoneEd, CEikEdwin },
			{SecondNumEd, CAknIntegerEdwin },
			{SecondPinEd, SecretEditor },
			{SecondIpEd, CAknIpFieldEditor },
		};
		
		private static Map<Object, String> typeToComponentIdMap;
		
		static {
			typeToComponentIdMap = new HashMap();
			for (int i = 0; i < typesAndComponentIds.length; i++) {
				typeToComponentIdMap.put(typesAndComponentIds[i][0], typesAndComponentIds[i][1]);
			}
		}
		
		private EObject instance;
		private IComponentSet componentSet;
		private final static String DEF_COMPONENT_ID = CEikEdwin;
		private final static String DEF_IP_COMPONENT_ID = CAknIpFieldEditor;
		private static final String TYPE_PROPERTY_NAME = "type";
		private static final String TYPE2_PROPERTY_NAME = "type2";

		public InitializerAndCommandExtender(EObject componentInstance) {
			this.instance = componentInstance;
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IInitializer#initialize()
		 */
		public void initialize(boolean isConfigured) {
			if (isConfigured) return;
			// add the default child 
			IComponentInstance componentInstance = ModelUtils.getComponentInstance(getEObject());
			IDesignerDataModel dataModel = componentInstance.getDesignerDataModel();
			Check.checkContract(dataModel != null);
			componentSet = dataModel.getComponentSet();
			Check.checkContract(componentSet != null);
			IComponent component = getDefaultComponent(componentSet);
			Check.checkContract(component != null);
			EObject child = dataModel.createNewComponentInstance(component);
			org.eclipse.emf.common.command.Command command = 
				dataModel.createAddNewComponentInstanceCommand(
						getEObject(), child, IDesignerDataModel.AT_END);
			if (command.canExecute())
				command.execute();
			child = dataModel.createNewComponentInstance(component);
			command = dataModel.createAddNewComponentInstanceCommand(
						getEObject(), child, IDesignerDataModel.AT_END);
			if (command.canExecute())
				command.execute();
		}
		
		private String getDefaultComponentId(IComponentSet componentSet) {
			if (Utilities.getComponentInstance(getEObject()).getComponentId().equals(MultiIpDataQuery))
				return DEF_IP_COMPONENT_ID;
			return DEF_COMPONENT_ID;
		}
		
		private IComponent getDefaultComponent(IComponentSet componentSet) {
			return componentSet.lookupComponent(getDefaultComponentId(componentSet));
		}
	
		/* (non-Javadoc)
		 * @see com.nokia.sdt.uidesigner.derived.ISetValueCommandExtender#getExtendedCommand(java.lang.String, com.nokia.sdt.uidesigner.ui.command.SetValueCommand)
		 */
		public Command getExtendedCommand(String propertyName, Object newValue, Command command) {
			boolean isFirstTypeProperty = propertyName.equals(TYPE_PROPERTY_NAME);
			if (isFirstTypeProperty || propertyName.equals(TYPE2_PROPERTY_NAME)) {
				int index = isFirstTypeProperty ? 0 : 1;
				return command.chain(new ChangeQueryChildCommand(getEObject(), newValue, 
						typeToComponentIdMap, getDefaultComponentId(componentSet), index));
			}
			return command;
		}
	
		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
		 */
		public EObject getEObject() {
			return instance;
		}
	}

	public Object getImpl(EObject componentInstance) {
		return new InitializerAndCommandExtender(componentInstance);
	}
}
