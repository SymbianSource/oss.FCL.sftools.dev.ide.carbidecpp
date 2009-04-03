/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.cpp.uiq.components.controlCollection;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import java.text.MessageFormat;

public class ClipboardCommandExtenderFactory implements IImplFactory {

	public class ClipboardExtender implements IClipboardCommandExtender {

		private static final String CONTROL_COLLECTION_TYPE = 
			"com.nokia.carbide.uiq.ControlCollection";	//$NON-NLS-1$
		private final EObject instance;

		public ClipboardExtender(EObject instance) {
			this.instance = instance;
		}
		
		public Command getExtendedCopyToClipboardCommand(EditingDomain editingDomain, Command command) {
			return command;
		}

		public Command getExtendedPasteFromClipboardCommand(EObject owner, EditingDomain editingDomain, Command command) {
			IComponentInstance ci = ModelUtils.getComponentInstance(owner);
			EObject rootContainer = ci.getRootContainer();
			if (ci.getComponentId().equals(CONTROL_COLLECTION_TYPE) || owner.equals(rootContainer))
				return command;
		
			final String ownerName = ci.getName();
			return new AbstractCommand() {

				@Override
				protected boolean prepare() {
					return true;
				}

				@Override
				public boolean canUndo() {
					return false;
				}

				public void execute() {
					String errorMessage = 
						MessageFormat.format(Messages.getString("ClipboardCommandExtenderFactory.PasteError"), //$NON-NLS-1$
							new Object[] { ownerName } );
					IStatus status = Logging.newStatus(null, IStatus.ERROR, errorMessage);
					Logging.showErrorDialog(Messages.getString("ClipboardCommandExtenderFactory.ErrorTitle"), Messages.getString("ClipboardCommandExtenderFactory.ErrorMessage"), status); //$NON-NLS-1$ //$NON-NLS-2$
				}

				public void redo() {
				}
				
			};
		}
		
		public EObject getEObject() {
			return instance;
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject instance) {
		return new ClipboardExtender(instance);
	}

}
