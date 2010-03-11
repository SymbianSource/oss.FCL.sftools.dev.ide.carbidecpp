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

package com.nokia.cdt.debug.common.internal.source.lookup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.cdt.debug.internal.core.sourcelookup.InternalSourceLookupMessages;
import org.eclipse.cdt.debug.internal.core.sourcelookup.MapEntrySourceContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.AbstractSourceContainerTypeDelegate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class SymbianSourceContainerType extends AbstractSourceContainerTypeDelegate {

	private final static String ELEMENT_MAPPING = "mapping"; //$NON-NLS-1$
	private final static String ELEMENT_MAP_ENTRY = "mapEntry"; //$NON-NLS-1$
	private final static String ATTR_EPOCROOT = "epocroot"; //$NON-NLS-1$
	private final static String ATTR_MEMENTO = "memento"; //$NON-NLS-1$

	public ISourceContainer createSourceContainer( String memento ) throws CoreException {
		Node node = parseDocument( memento );
		if ( node.getNodeType() == Node.ELEMENT_NODE ) {
			Element element = (Element)node;
			if ( ELEMENT_MAPPING.equals( element.getNodeName() ) ) {
				String epocroot = element.getAttribute( ATTR_EPOCROOT );
				if ( epocroot == null ) 
					epocroot = ""; //$NON-NLS-1$
				List entries = new ArrayList();
				Node childNode = element.getFirstChild();
				while( childNode != null ) {
					if ( childNode.getNodeType() == Node.ELEMENT_NODE ) {
						Element child = (Element)childNode;
						if ( ELEMENT_MAP_ENTRY.equals( child.getNodeName() ) ) {
							String childMemento = child.getAttribute( ATTR_MEMENTO );
							if ( childMemento == null || childMemento.length() == 0 ) {
								abort( InternalSourceLookupMessages.getString( "MappingSourceContainerType.0" ), null ); //$NON-NLS-1$
							}
							ISourceContainerType type = DebugPlugin.getDefault().getLaunchManager().getSourceContainerType( MapEntrySourceContainer.TYPE_ID );
							MapEntrySourceContainer entry = (MapEntrySourceContainer)type.createSourceContainer( childMemento );
							entries.add( entry );
						}
					}
					childNode = childNode.getNextSibling();
				}
				SymbianSourceContainer container = new SymbianSourceContainer( new Path( epocroot ) );
				Iterator it = entries.iterator();
				while( it.hasNext() ) {
					container.addMapEntry( (MapEntrySourceContainer)it.next() );
				}
				return container;
			}
			abort( InternalSourceLookupMessages.getString( "MappingSourceContainerType.1" ), null ); //$NON-NLS-1$
		}
		abort( InternalSourceLookupMessages.getString( "MappingSourceContainerType.2" ), null ); //$NON-NLS-1$
		return null;		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.sourcelookup.ISourceContainerTypeDelegate#getMemento(org.eclipse.debug.core.sourcelookup.ISourceContainer)
	 */
	public String getMemento( ISourceContainer container ) throws CoreException {
		Document document = newDocument();
		Element element = document.createElement( ELEMENT_MAPPING );
		element.setAttribute( ATTR_EPOCROOT, ((SymbianSourceContainer)container).getEpocRoot().toOSString() );
		ISourceContainer[] entries = ((SymbianSourceContainer)container).getSourceContainers();
		for ( int i = 0; i < entries.length; ++i ) {
			Element child = document.createElement( ELEMENT_MAP_ENTRY );
			child.setAttribute( ATTR_MEMENTO, entries[i].getType().getMemento( entries[i] ) );
			element.appendChild( child );
		}
		document.appendChild( element );
		return serializeDocument( document );
	}

}
