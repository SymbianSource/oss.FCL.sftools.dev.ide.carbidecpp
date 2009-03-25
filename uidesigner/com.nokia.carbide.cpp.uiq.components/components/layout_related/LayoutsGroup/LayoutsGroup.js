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


function LayoutsGroup() {
}

////////////////////////////////////////////////////////////////////////////////
//ILayout

LayoutsGroup.prototype.layout = function(instance, laf) {
	for (var i in instance.children) {
		var viewLayout = instance.children[i];
		if (viewLayout.component.id == "com.nokia.carbide.uiq.ViewLayout") {
			viewLayout.setLayoutBounds(laf.getRectangle("ViewLayout.bounds"));
			for (var j in viewLayout.children)	{
				var viewPage = viewLayout.children[j];
				if (viewPage.component.id == "com.nokia.carbide.uiq.ViewPage") {
					viewPage.setLayoutBounds(laf.getRectangle("ViewPage.bounds"));
					for (var k in viewPage.children) {
						var container = viewPage.children[k];
						if (container.attributes["is-qikcontainer"] == "true"){
							container.setLayoutBounds(laf.getRectangle("ViewCQikContainer.bounds"));
						}
					}
				}
			}
		}
	}
}

