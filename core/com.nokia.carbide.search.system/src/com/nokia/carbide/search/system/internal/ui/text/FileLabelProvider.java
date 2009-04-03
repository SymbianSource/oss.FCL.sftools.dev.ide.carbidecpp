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

import java.io.File;

import com.ibm.icu.text.MessageFormat;
import com.nokia.carbide.search.system.internal.ui.SearchMessages;
import com.nokia.carbide.search.system.ui.text.AbstractTextSearchResult;
import com.nokia.carbide.search.system.ui.text.AbstractTextSearchViewPage;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import org.eclipse.core.resources.IResource;

import org.eclipse.swt.graphics.Image;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;

import org.eclipse.ui.model.WorkbenchLabelProvider;



public class FileLabelProvider extends LabelProvider {
		
	public static final int SHOW_LABEL= 1;
	public static final int SHOW_LABEL_PATH= 2;
	public static final int SHOW_PATH_LABEL= 3;
	
	private static final String fgSeparatorFormat= "{0} - {1}"; //$NON-NLS-1$
	
	private final WorkbenchLabelProvider fLabelProvider;
	private final AbstractTextSearchViewPage fPage;
		
	private int fOrder;
	private String[] fArgs= new String[2];

	public FileLabelProvider(AbstractTextSearchViewPage page, int orderFlag) {
		fLabelProvider= new WorkbenchLabelProvider();
		fOrder= orderFlag;
		fPage= page;
	}

	public void setOrder(int orderFlag) {
		fOrder= orderFlag;
	}
	
	public int getOrder() {
		return fOrder;
	}
	
	public String getResourceText(IResource resource) {
		String text= null;

		if (!resource.exists())
			text= SearchMessages.FileLabelProvider_removed_resource_label; 
		
		else {
			IPath path= resource.getFullPath().removeLastSegments(1);
			if (path.getDevice() == null)
				path= path.makeRelative();
			if (fOrder == SHOW_LABEL || fOrder == SHOW_LABEL_PATH) {
				text= fLabelProvider.getText(resource);
				if (path != null && fOrder == SHOW_LABEL_PATH) {
					fArgs[0]= text;
					fArgs[1]= path.toString();
					text= MessageFormat.format(fgSeparatorFormat, fArgs);
				}
			} else {
				if (path != null)
					text= path.toString();
				else
					text= ""; //$NON-NLS-1$
				if (fOrder == SHOW_PATH_LABEL) {
					fArgs[0]= text;
					fArgs[1]= fLabelProvider.getText(resource);
					text= MessageFormat.format(fgSeparatorFormat, fArgs);
				}
			}
		}
		
		return text;
	}

	public String getFileStoreText(IFileStore fileStore) {
		String text= null;

		if (!fileStore.fetchInfo().exists())
			text= SearchMessages.FileLabelProvider_removed_resource_label; 
		
		else {
			File file= null;
			try {
				file = fileStore.toLocalFile(EFS.NONE, null);
			} catch (CoreException e) {
				return null;
			}
			
			IPath path = (new Path(file.getAbsolutePath())).removeLastSegments(1);

			if (fOrder == SHOW_LABEL || fOrder == SHOW_LABEL_PATH) {
				text= file.getName();
				if (path != null && fOrder == SHOW_LABEL_PATH) {
					fArgs[0]= text;
					fArgs[1]= path.toString();
					text= MessageFormat.format(fgSeparatorFormat, fArgs);
				}
			} else {
				if (path != null)
					text= path.toString();
				else
					text= ""; //$NON-NLS-1$
				if (fOrder == SHOW_PATH_LABEL) {
					fArgs[0]= text;
					fArgs[1]= file.getName();
					text= MessageFormat.format(fgSeparatorFormat, fArgs);
				}
			}
		}
		
		return text;
	}

	public String getText(Object element) {
		String text= null;

		if (element instanceof IResource)
			text = getResourceText((IResource)element);
		else if (element instanceof IFileStore)
			text = getFileStoreText((IFileStore)element);
		else
			return null;
		
		int matchCount= 0;
		AbstractTextSearchResult result= fPage.getInput();
		if (result != null)
			matchCount= result.getMatchCount(element);
		if (matchCount <= 1)
			return text;
		String format= SearchMessages.FileLabelProvider_count_format; 
		return MessageFormat.format(format, new Object[] { text, new Integer(matchCount) });
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (!(element instanceof IResource))
			return null;

		IResource resource= (IResource)element;
		Image image= fLabelProvider.getImage(resource);
		return image;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
	 */
	public void dispose() {
		super.dispose();
		fLabelProvider.dispose();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		return fLabelProvider.isLabelProperty(element, property);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
		super.removeListener(listener);
		fLabelProvider.removeListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
		super.addListener(listener);
		fLabelProvider.addListener(listener);
	}
}
