/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation, Nokia and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Juerg Billeter, juergbi@ethz.ch - 47136 Search view should show match objects
 *     Ulrich Etter, etteru@ethz.ch - 47136 Search view should show match objects
 *     Roman Fuchs, fuchsro@ethz.ch - 47136 Search view should show match objects
 *******************************************************************************/
package com.nokia.carbide.search.system.internal.ui.text;

import org.eclipse.core.filesystem.IFileStore;

import com.nokia.carbide.search.system.ui.text.Match;

public class FileMatch extends Match {
	private long fCreationTimeStamp;
	
	public FileMatch(IFileStore element, int offset, int length) {
		super(element, offset, length);
		fCreationTimeStamp= element.fetchInfo().getLastModified();
	}
	
	public IFileStore getFile() {
		return (IFileStore) getElement();
	}

	public long getCreationTimeStamp() {
		return fCreationTimeStamp;
	}
}
