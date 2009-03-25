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
public class SingleLineDataQueryCodeImplFactory implements IImplFactory {
	
	static class InitializerAndCommandExtender implements IInitializer, ISetValueCommandExtender {

		private final static String[][] typesAndComponentIds = 
		{	{"EDataLayout", "com.nokia.sdt.series60.CEikEdwin" },
			{"EPhoneLayout", "com.nokia.sdt.series60.CEikEdwin" },
			{"EPinLayout", "com.nokia.sdt.series60.SecretEditor" },
			{"ECodeLayout", "com.nokia.sdt.series60.SecretEditor" },
			{"EDateLayout", "com.nokia.sdt.series60.CEikDateEditor" },
			{"ETimeLayout", "com.nokia.sdt.series60.CEikTimeEditor" },
			{"EDurationLayout", "com.nokia.sdt.series60.CEikDurationEditor" },
			{"EFloatingPointLayout", "com.nokia.sdt.series60.CEikFloatingPointEditor" },
			{"EFixedPointLayout", "com.nokia.sdt.series60.CEikFixedPointEditor" },
			{"ENumberLayout", "com.nokia.sdt.series60.CAknIntegerEdwin" },
			{"EIpLayout", "com.nokia.sdt.series60.CAknIpFieldEditor" },
		};
		
		private static Map<Object, String> typeToComponentIdMap;
		
		static {
			typeToComponentIdMap = new HashMap();
			for (int i = 0; i < typesAndComponentIds.length; i++) {
				typeToComponentIdMap.put(typesAndComponentIds[i][0], typesAndComponentIds[i][1]);
			}
		}
		
		private EObject instance;
		private final static String DEF_COMPONENT_ID = "com.nokia.sdt.series60.CEikEdwin"; //$NON-NLS-1$
		private static final String TYPE_PROPERTY_NAME = "type";

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
			IComponentSet componentSet = dataModel.getComponentSet();
			Check.checkContract(componentSet != null);
			IComponent component = componentSet.lookupComponent(DEF_COMPONENT_ID);
			Check.checkContract(component != null);
			EObject child = dataModel.createNewComponentInstance(component);
			org.eclipse.emf.common.command.Command command = 
				dataModel.createAddNewComponentInstanceCommand(getEObject(), child, 0);
			if (command.canExecute())
				command.execute();
		}
	
		/* (non-Javadoc)
		 * @see com.nokia.sdt.uidesigner.derived.ISetValueCommandExtender#getExtendedCommand(java.lang.String, com.nokia.sdt.uidesigner.ui.command.SetValueCommand)
		 */
		public Command getExtendedCommand(String propertyName, Object newValue, Command command) {
			if (propertyName.equals(TYPE_PROPERTY_NAME)) {
				return command.chain(new ChangeQueryChildCommand(getEObject(), newValue, 
													typeToComponentIdMap, DEF_COMPONENT_ID, 0));
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
