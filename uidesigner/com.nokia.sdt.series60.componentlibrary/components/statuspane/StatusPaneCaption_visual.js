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


include("../implLibrary.js")
include("../renderLibrary.js")
include("../srcgenLibrary.js")
include("titleLibrary.js")

function StatusPaneCaptionVisual() {
}

StatusPaneCaptionVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	
	drawTitleText(instance, laf, graphics, properties.longCaption);
}

setupCommonDirectLabelEditing(StatusPaneCaptionVisual.prototype, "longCaption",
	null,	
	function(instance, laf) { return laf.getFont("titleFont"); 
	}
)
