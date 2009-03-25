/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;

import java.util.*;

/**
 * This is a set of objects that uses a converter class to measure equality.
 * @see StructuredItemListConverter#elementMatches(Object, Object)
 */
public class ConvertedModelSet<ModelType, NodeType extends IASTStatement> {

	private StructuredItemListConverter<NodeType, ModelType> converter;
	private ArrayList<ModelType> contents;

	/**
	 * @param converter
	 */
	public ConvertedModelSet(
			StructuredItemListConverter<NodeType, ModelType> converter) {
		this.converter = converter;
		this.contents = new ArrayList<ModelType>();
	}


	public void add(ModelType model) {
		if (!contains(model))
			contents.add(model);
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof ConvertedModelSet))
			return false;
		ConvertedModelSet<ModelType, NodeType> otherSet = (ConvertedModelSet<ModelType, NodeType>) other;
		if (otherSet.contents.size() != contents.size())
			return false;
		for (ModelType content : contents) {
			if (!otherSet.contains(content))
				return false;
		}
		return true;
	}


	public boolean contains(ModelType model) {
		for (ModelType content : contents) {
			if (content == model || converter.elementMatches(model, content))
				return true;
		}
		return false;
	}


	/**
	 * @return
	 */
	public int size() {
		return contents.size();
	}
	
	public void clear() {
		contents.clear();
	}
}
