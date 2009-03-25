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


include("../containers/containerLibrary.js")
include("../srcgenLibrary.js")

function AvkonViewReferenceImpl() {
}

AvkonViewReferenceImpl.prototype.propertyChanged = function(instance, property) {
	if ((property == "tabText") || (property == "tabImage") || (property == "inTabGroup")) {
		var statusPane = getStatusPane(instance.parent.children);
		if (statusPane != null) {
			var naviTabs = findImmediateChildByComponentID(statusPane.children, "com.nokia.sdt.series60.NaviTabs");
			if (naviTabs != null)
				naviTabs.forceRedraw(); // force tab group to redraw
		}
	}
}

AvkonViewReferenceImpl.prototype.getViewableSize = function(instance, propertyId, laf) {
	var bounds = laf.getRectangle("navi.content.bounds");
	return new Point(bounds.width, bounds.height);
}

AvkonViewReferenceImpl.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	return new Point(ImageUtils.ALIGN_CENTER, ImageUtils.ALIGN_CENTER_OR_TOP);
}

AvkonViewReferenceImpl.prototype.isScaling = function(instance, propertyId, laf) {
	return isScalingIcons();
}

AvkonViewReferenceImpl.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}

