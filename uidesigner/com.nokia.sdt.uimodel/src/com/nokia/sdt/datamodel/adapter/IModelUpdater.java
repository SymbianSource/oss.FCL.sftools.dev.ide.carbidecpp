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

import com.nokia.sdt.datamodel.IDesignerDataModel;

/**
 * An interface to allow components to update the data model after it is loaded
 * Example javascript protoype implementing this interface:
 *	<blockquote><pre>
 *
 * function CModelUpdater() {
 *
 * }
 *
 * CModelUpdater.prototype.updateModel = function(instance, dataModel) {
 * }
 *
 *	</pre></blockquote>
 */
public interface IModelUpdater extends IModelAdapter {
	
	void updateModel(IDesignerDataModel dataModel);

}
