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
function ConditionalWithExtensionsPropertyExtender() {
}

	// Return instances that may provide extension properties
	// The target instance parameter is the instance to receive the
	// additional properties
ConditionalWithExtensionsPropertyExtender.prototype.getPropertyExtenders = function(instance, targetInstance) {
	return [instance, instance.parent];
}
	
ConditionalWithExtensionsPropertyExtender.prototype.getExtensionSetNames = function(instance, targetInstance) {
//println("get extension:"+instance.parent.properties.controlConditionalExtensions);
	if (instance.parent.properties.controlConditionalExtensions)
		return [ "ifControl" ];
	return null;
}
