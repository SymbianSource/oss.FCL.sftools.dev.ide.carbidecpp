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


function ControlCollectionPropertyExtenders() {
}

	// Return instances that may provide extension properties
	// The target instance parameter is the instance to receive the
	// additional properties
ControlCollectionPropertyExtenders.prototype.getPropertyExtenders = function(instance, targetInstance) {
//	println("instance:" + instance);
//	println("targetInstance:" + targetInstance);
	if (targetInstance.parent == instance) 
			{
			return [instance];
			}
	return null;
}
	
ControlCollectionPropertyExtenders.prototype.getExtensionSetNames = function(instance, targetInstance) {
	return [ "default" ];
}

