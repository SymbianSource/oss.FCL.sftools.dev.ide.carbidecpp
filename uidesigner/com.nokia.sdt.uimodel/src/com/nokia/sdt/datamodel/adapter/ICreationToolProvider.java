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


package com.nokia.sdt.datamodel.adapter;

import com.nokia.sdt.editor.*;


import java.util.Collection;

/**
 * An interface to allow instances to add arbitrary entries in the palette 
 * as well as provide a factory for creating new objects from them.
 */
public interface ICreationToolProvider extends IModelAdapter {
	
	/**
	 * @param editor {@link IDesignerEditor}
	 * @see IDesignerEditor#updatePalette
	 * @return a non-null Collection of ICreatableTool objects
	 */
	Collection<ICreationTool> getCreationTools(IDesignerEditor editor);
	
}
