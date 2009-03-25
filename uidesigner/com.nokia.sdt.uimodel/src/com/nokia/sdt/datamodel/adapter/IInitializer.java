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


package com.nokia.sdt.datamodel.adapter;


/**
 *  This interface is used to initialize a new component instance after creation.
 *  Example javascript protoype implementing this interface:
 *	<blockquote><pre>
 *
 *function CInstanceInitializer() {
 *
 *}
 *
 *CInstanceInitializer.prototype.initialize = function(instance, isConfigured) {
 *
 *}
 *	</pre></blockquote>
 */
public interface IInitializer extends IModelAdapter {

    /**
     * Initialize the new instance
     * @param isConfigured is true for objects added to the model
     * in a user-configured state. For example, for undo and paste
     * new instances are added to the model, but they will be set
     * to their final configured state after this method has been
     * called.
     */
	void initialize(boolean isConfigured);
	
}
