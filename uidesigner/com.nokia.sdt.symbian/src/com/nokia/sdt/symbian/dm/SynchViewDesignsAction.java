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

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier.IRunWithModelAction;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.widgets.Shell;

import java.util.*;

/**
 * Synchronizes state from a root model to a view model.
 * The synchronized data is the component versions and
 * set of included languages. 
 *
 */
public class SynchViewDesignsAction implements IRunWithModelAction {
	
	private IDesignerDataModel rootModel;
	private boolean synchComponentVersions;
	private boolean synchLanguages;
	private Shell shell;
	
	public SynchViewDesignsAction(IDesignerDataModel rootModel,
			boolean synchComponentVersions, boolean synchLanguages,
			Shell shell) {
		this.rootModel = rootModel;
		this.synchComponentVersions = synchComponentVersions;
		this.synchLanguages = synchLanguages;
		this.shell = shell;
	}
		
	public void synchDesigns(Collection<IDesignerDataModelSpecifier> designSpecifiers) {
		IDesignerDataModelSpecifier rootModelSpecifier = rootModel.getModelSpecifier();
		for (IDesignerDataModelSpecifier specifier : designSpecifiers) {
			if (!specifier.equals(rootModelSpecifier)) {
				specifier.runWithLoadedModel(this);
			}
		}				
	}

	public Object run(LoadResult lr) {
		IDesignerDataModel model = lr.getModel();
		if (model != null) {
			
			IStatus status = model.validateEdit(shell);
			if (!status.isOK()) {
				return status;
			}
			
			if (synchComponentVersions) {
				Command command = SymbianModelUtils.setSDKVersion(model, SymbianModelUtils.getSDKVersion(rootModel));
				Check.checkContract(command.canExecute());
				command.execute();
			}
			
			if (synchLanguages) {
				ILocalizedStringBundle dest = getStringBundle(model);
				ILocalizedStringBundle src = getStringBundle(rootModel);
				Set<Language> destExisting = getBundleLanguages(dest);
				List tables = src.getLocalizedStringTables();
				// add any missing languages
				for (Iterator iter = tables.iterator(); iter.hasNext();) {
					ILocalizedStringTable lst = (ILocalizedStringTable) iter.next();
					ILocalizedStringTable destTable = dest.findLocalizedStringTable(lst.getLanguage());
					if (destTable == null) {
						dest.addLocalizedStringTable(lst.getLanguage());
					}
					destExisting.remove(lst.getLanguage());
				}
				// remove any unneeded languages
				for (Language lang : destExisting) {
					ILocalizedStringTable table = dest.findLocalizedStringTable(lang);
					dest.getLocalizedStringTables().remove(table);
				}
			}
			
			try {
				EditorServices.saveModels(Collections.singletonList(model), null);
			} catch (Exception x) {
				SymbianPlugin.getDefault().log(x);
			}
		}
		return null;
	}
	
	private Set<Language> getBundleLanguages(ILocalizedStringBundle lsb) {
		Set<Language> result = new HashSet<Language>();
		List tables = lsb.getLocalizedStringTables();
		for (Iterator iter = tables.iterator(); iter.hasNext();) {
			ILocalizedStringTable table = (ILocalizedStringTable) iter.next();
			result.add(table.getLanguage());
		}
		return result;
	}

	private ILocalizedStringBundle getStringBundle(IDesignerDataModel model) {
		DesignerDataModel dm = (DesignerDataModel) model;
		return dm.getDesignerData().getStringBundle();
	}
}
