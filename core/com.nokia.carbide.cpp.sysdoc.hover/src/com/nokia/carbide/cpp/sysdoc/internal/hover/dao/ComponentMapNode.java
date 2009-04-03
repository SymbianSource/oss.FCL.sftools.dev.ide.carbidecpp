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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.dao;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.parser.util.ObjectMap;

/**
 * A compound structure holds map of components and paths. If an API (such as
 * name spaces) has multiple entries, this class holds all of these option.
 */
public class ComponentMapNode {

	private Map<String, PathNode> componentMap = new HashMap<String, PathNode>();

	public Map<String, PathNode> getComponentMap() {
		return componentMap;
	}

	/**
	 * Initialize a component node and then insert API and its path into a node
	 * 
	 * @param component
	 *            : API's compoment name
	 * @param fqn
	 *            : full qualified name of API
	 * @param path
	 *            : SDL path of the API
	 */
	public void put(String component, String[] fqn, String path) {
		PathNode node = componentMap.get(component);

		if (node == null) {
			node = new PathNode(fqn[0], component, path);
			componentMap.put(component, node);
		}

		for (int i = 1; i < fqn.length; i++) {

			doAddNodeEntry(node.getSub(), i, fqn, component, path);
		}
	}

	/**
	 * Fetch the component node
	 * 
	 * @param component
	 *            Interested name of the component
	 * @return component node for the component
	 */
	public PathNode getNode(String component) {
		PathNode node = componentMap.get(component);
		return node;
	}

	/**
	 * 
	 * Add a node entry into component node.
	 * 
	 * @param map
	 *            : Key value map holding API and path
	 * @param index
	 *            of current API keyword in FQN array to be processed
	 * @param fqn
	 *            Array of keywords constructing full qualifed name of an API
	 * @param componentID
	 *            Component name of API
	 * @param path
	 *            HTML Path content.
	 */
	private static void doAddNodeEntry(ObjectMap map, int index, String[] fqn,
			String componentID, String path) {
		String fqni = fqn[index].intern();

		PathNode n = (PathNode) map.get(fqni);
		if (n == null) {
			n = new PathNode(fqni, componentID, path);
			map.put(fqni, n);
		}

		if (index != fqn.length - 1) {
			doAddNodeEntry(n.getSub(), index + 1, fqn, componentID, path);
		}
	}

}