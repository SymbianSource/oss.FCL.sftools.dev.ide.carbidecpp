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


package com.nokia.sdt.symbian.workspace;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.symbian.workspace.impl.ProjectContextProvider;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.resources.IProject;

import java.util.ArrayList;
import java.util.Collection;

public class SymbianLanguageUtils {
	
	/**
	 * Returns a collection of language code strings as they would appear in the MMP LANG statement.
	 * E.g., [ 01, 02, 10 ]
	 * 
	 * @param project an IProject
	 * @return a collection String.
	 */
	public static Collection<String> getDesignLanguages(IProject project, boolean updating) {
		WorkspaceContext wc = WorkspaceContext.getContext();
		if (updating)
			ProjectContextProvider.beginProjectUpdating(project);
		IProjectContext pc = wc.getContextForProject(project);
		if (updating)
			ProjectContextProvider.endProjectUpdating(project);
		if (pc == null)
			return null;
		
		ISymbianProjectContext spc = 
			(ISymbianProjectContext) pc.getAdapter(ISymbianProjectContext.class);
		if (spc != null) {
			IDesignerDataModelSpecifier specifier = spc.getRootModel();
			if (specifier == null)
				return null;
			
			return (Collection<String>) specifier.runWithLoadedModelNoSourceGen(
				new IDesignerDataModelSpecifier.IRunWithModelAction() {
					public Object run(LoadResult lr) {
						IDesignerDataModel model = lr.getModel();
						if (model != null) {
							DesignerDataModel dm = (DesignerDataModel) model;
							ILocalizedStringBundle bundle = dm.getDesignerData().getStringBundle();
							if (bundle != null) {
								Collection<String> languages = new ArrayList();
								for (Object object : bundle.getLocalizedStringTables()) {
									ILocalizedStringTable lst = (ILocalizedStringTable) object;
									Language language = lst.getLanguage();
									int code = language.getLanguageCode();
									StringBuffer buffer = new StringBuffer();
									buffer.append(code);
									if (code < 10)
										buffer.insert(0, 0);
									languages.add(buffer.toString());
								}
								return languages;
							}
						}
						
						return null;
					}
				});
		}

		return null;
	}

}
