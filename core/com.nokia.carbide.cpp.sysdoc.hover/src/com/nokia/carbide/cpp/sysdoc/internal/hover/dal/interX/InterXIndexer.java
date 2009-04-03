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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.cdt.core.parser.util.ObjectMap;
import org.eclipse.core.internal.preferences.Base64;
import org.eclipse.core.runtime.OperationCanceledException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.MessagesConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.ComponentMapNode;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.PathNode;
import com.nokia.carbide.cpp.sysdoc.internal.hover.exceptions.HoverException;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.ProgressJob;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.ProgressMonitorHelper;

/**
 * A data-structure holder to map fully-qualified API names to html help
 * contents.
 */
public class InterXIndexer {

	private ProgressMonitorHelper indexProgressMonitor;
	protected ObjectMap lookup;
	protected int size;
	private static int dublicateNumber = 0;
	private InterXProperties interXProps;

	public InterXIndexer(InterXProperties interXProps) {
		this.interXProps = interXProps;		
	}
	
	
	/**
	 * Start a progress process to display interchange file indexing
	 * 
	 * @throws Exception
	 */
	public void startIndexing() throws Exception {
		final ProgressJob runnableJob = new ProgressJob();
		Runnable indexingTask = new Runnable() {
			public void run() {
				InterXIndexer.this.parse();
				if (!InterXIndexController.isIndexingCancelled()){
					InterXIndexController.setIndexingCompleted(true);					
				}			
			}
		};
		runnableJob.setTask(indexingTask);
		indexProgressMonitor = new ProgressMonitorHelper(runnableJob,
				interXProps.getUserFriendlyName()
						+ MessagesConstants.HOVER_HELP_PROGRESS_MESSAGE);
		indexProgressMonitor.setTaskSize(interXProps.getObjCount());
		indexProgressMonitor.run();
	}

	/**
	 * Parse the interchange file and put into map which holds API and path
	 * key-value pair
	 */
	protected void parse() {

		lookup = new ObjectMap(1000);
		DefaultHandler handler = new DefaultHandler() {
			String component;

			@Override
			public void startElement(String uri, String localName, String name,
					Attributes attributes) throws SAXException {
				
				if (indexProgressMonitor.isMonitorCanceled()) {
					throw new OperationCanceledException();
				}
				
				if (InterXIndexController.isIndexingCancelled()){
					return;					
				}

				if (name
						.equals(InterXProperties.INTERCHANGEFILE_ELEMENT_SYSTEMWIDELINKS)) {
					String root_dir = attributes
							.getValue(InterXProperties.INTERX_ATTR_ROOT_DIR);
					String config = attributes
							.getValue(InterXProperties.INTERX_ATTR_CONFIGURATION);
					assert (interXProps.getRootDir().equals(root_dir));
				} else if (name.equals(InterXProperties.INTERX_ATTR_OBJ)) {
					String path = attributes
							.getValue(InterXProperties.INTERX_ATTR_PATH);
					String key = decode(attributes
							.getValue(InterXProperties.INTERX_ATTR_ID));
					int rparen = key.indexOf("(");
					if (rparen != -1) {
						key = key.substring(0, rparen);
					}
					indexProgressMonitor.subTask(component);
					indexProgressMonitor.worked();
					try {
						addEntry(key, component, path);
					} catch (Exception e) {
						Logger.logError(e);
					}
				} else if (name.equals(InterXProperties.INTERX_ATTR_COMPONENT)) {
					component = attributes
							.getValue(InterXProperties.INTERX_ATTR_COMPONENT_NAME);
				}
			}

			@Override
			public void endElement(String uri, String localName, String name)
					throws SAXException {
				super.endElement(uri, localName, name);
			}
		};

		// if an exception occurs during index population we log it and
		// remain in a possibly empty or half-populated state.
		try {
			InputStream inputStream = interXProps.getInterXURL().openStream();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(inputStream, handler);
			inputStream.close();
			Logger.logDebug("Dublicate size:" + dublicateNumber
					+ " total size:" + size + " per:"
					+ ((double) dublicateNumber / (double) size));
		} catch (Exception se) {
			throw new HoverException(se.toString());
		}
	}

	/**
	 * Given a full qualified API name, list of available PathNodes are
	 * retrieved...
	 * 
	 * @param fqnStr
	 *            : Fully Qualified Name of the API
	 * @return Get PathNodes of a given FQN API
	 * 
	 */
	public List<PathNode> getPathAsNodes(String fqnStr) {
		String[] fqnArray = fqnStr.split("::");
		ComponentMapNode componentNode = (ComponentMapNode) lookup
				.get(fqnArray[0]);
		if (componentNode == null) {
			return null;
		}
		Set<String> componentNamesKeySet = componentNode.getComponentMap()
				.keySet();
		List<PathNode> nodeList = new ArrayList<PathNode>();
		for (String componentID : componentNamesKeySet) {
			PathNode n = componentNode.getNode(componentID);
			PathNode tailNode = n.visitFQNNodeChain(fqnArray);
			if (tailNode != null) {
				nodeList.add(tailNode);
			}
		}
		return nodeList;
	}

	/**
	 * 
	 * @return number of entries in interchange file
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Add an fully qualified API name to the map
	 * 
	 * @param fqn
	 *            : Fully Qualified name as key in the map
	 * @param componentID
	 *            : Compoment id of FQN API
	 * @param path
	 *            : Value in the map entry representing path of the content help
	 */
	public void addEntry(String fqn, String componentID, String path) {
		size++;
		doAddComponentNodeEntry(lookup, 0, fqn.split("::"), componentID
				.intern(), path);
	}

	/**
	 * Add psrt of an fqn to the data structure
	 * 
	 * @param map
	 *            : Map which whill holds fqn
	 * @param index
	 *            : index indicating to be processed parts of FQN
	 * @param fqn
	 *            : full qualified API name
	 * @param componentID
	 *            : Component name of the API
	 * @param path
	 *            : path of the API
	 */
	private void doAddComponentNodeEntry(ObjectMap map, int index,
			String[] fqn, String componentID, String path) {
		String fqni = fqn[index].intern();
		ComponentMapNode componentNode = (ComponentMapNode) map.get(fqni);
		if (componentNode == null) {
			componentNode = new ComponentMapNode();
			map.put(fqni, componentNode);
		}
		componentNode.put(componentID, fqn, path);
	}

	/**
	 * Decodes the keys (API names) which are in interchange files
	 * 
	 * @param String
	 *            to be decoded
	 * @return Decoded String
	 */
	private static String decode(String s) {
		String temp = s.substring(1).replace('-', '+').replace('.', '/')
				.replace('_', '=');
		temp = new String(Base64.decode(temp.getBytes()));
		return temp;
	}
		
	public String getRootDir() {
		return this.interXProps.getRootDir();
	}

	public ComponentMapNode getValueAt(int i) {
		if (lookup.size() > i) {
			return (ComponentMapNode) lookup.getAt(i);
		}
		return null;
	}


}