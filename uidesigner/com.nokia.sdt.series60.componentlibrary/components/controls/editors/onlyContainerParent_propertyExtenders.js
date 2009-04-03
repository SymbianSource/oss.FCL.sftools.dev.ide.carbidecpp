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


include("../../embeddedControlImplLibrary.js")
include("../../settingsListLibrary.js")

/*
	Controls within a form do not obey the font or alignment settings,
	so do not expose them in a form.
	
	For now, we assume that a settings list also ignores this.
*/

function OnlyContainerParentPropertyExtenders() {
}

	// Return instances that may provide extension properties
	// The target instance parameter is the instance to receive the
	// additional properties
OnlyContainerParentPropertyExtenders.prototype.getPropertyExtenders = function(instance, targetInstance) {
	if (targetInstance == instance) {
		if (isForm(instance.parent) || isSettingItemList(instance.parent))
			return [instance.parent, instance];
		else
			return [instance];
	}

	return null;
}
	
OnlyContainerParentPropertyExtenders.prototype.getExtensionSetNames = function(instance, targetInstance) {
	if (targetInstance == instance) {
		if (!isForm(instance.parent) 
			&& !isSettingItemList(instance.parent)
		)
			return [ "container" ];
	}

	return null;
}

