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
* START_USECASES: CU10 END_USECASES
*
*
*/


function ContainersGroup() {
}

////////////////////////////////////////////////////////////////////////////////
//ILayout

ContainersGroup.prototype.layout = function(instance, laf) {
	for (var k in instance.children) {
		var container = instance.children[k];
		if (container.attributes["is-qikcontainer"] == "true") {
			container.setLayoutBounds(laf.getRectangle("SimpleDialogCQikContainer.bounds"));
		}
	}
}

