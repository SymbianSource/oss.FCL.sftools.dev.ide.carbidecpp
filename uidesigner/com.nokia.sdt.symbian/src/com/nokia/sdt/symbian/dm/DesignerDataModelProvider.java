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

import com.nokia.sdt.datamodel.*;
import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.sourcegen.ISourceGenSession;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;

public class DesignerDataModelProvider implements IDesignerDataModelProvider {
	
	private static int tempCounter = 1;

	public LoadResult load(URI fileURI, IDesignerDataModelSpecifier specifier, ISourceGenProvider provider) {
		LoadResult result = null;
		DesignerDataModel model = new DesignerDataModel(this);
		ISourceGenSession session = null;
		if (provider != null) {
			session = provider.createSourceGenSession(model, specifier);
			model.setSourceGenSession(session);
		}
		result = model.load(fileURI, specifier);
		return result;
	}

	public IDesignerDataModel create(URI fileURI, String encoding) throws Exception {
		DesignerDataModel model = new DesignerDataModel(this);
		model.create(fileURI, encoding);
		DesignerDataModelNotifier.instance().fireDataModelInitialized(model);
		return model;
	}

	public IDesignerDataModel createTemporary() {
		DesignerDataModel model = new DesignerDataModel(this);
		String name = "TEMP_NXD_MODEL_" + tempCounter++;
		model.createTemporary(name);
		DesignerDataModelNotifier.instance().fireDataModelInitialized(model);
		return model;
	}

	public IDesignerDataModel createCompatibleTemporary(IDesignerDataModel sourceModel) throws CoreException {
		Check.checkArg(sourceModel);
		Check.checkArg(sourceModel.getProvider() == this);
		Check.checkArg(sourceModel.getComponentSet() != null);
		
		IDesignerDataModel result = createTemporary();
		result.setComponentSet(sourceModel.getComponentSet());
		return result;
	}
}
