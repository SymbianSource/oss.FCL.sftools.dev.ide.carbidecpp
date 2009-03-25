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
package com.nokia.sdt.sourcegen.doms.rss.dom;


/** 
 * Interface to deal with generating text given a tree of segments.
 * The segments specify formatting, raw text, and references to
 * segment lists from other nodes.  This is responsible for either
 * creating raw text, predicting the new text, or writing new text
 * and updating source ranges to match.
 */
public interface ISegmentHandler {

	/** Handle an embedded IAstNode */
	public void handleAstNode(IAstNode node);

	/** Handle text, which is either a SpecialSegment or toString()-able */
	public void handleText(Object text);
}