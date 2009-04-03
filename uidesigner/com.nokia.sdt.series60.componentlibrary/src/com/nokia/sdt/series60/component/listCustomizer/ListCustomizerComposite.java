/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.series60.component.listCustomizer;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ListCustomizerComposite extends ThumbnailWithDescriptionComposite {

	private final static String IMAGE_CACHE_ID = "ListCustomizerComposite.ImageCache"; //$NON-NLS-1$
	
    static class ImageCache implements IDisposable {
        public Map map = new HashMap();
        public void dispose() {
            for (Iterator iter = map.values().iterator(); iter.hasNext();) {
                Image image = (Image) iter.next();
                image.dispose();
            }
            map.clear();
        }
    }

	private static class ThumbData {
		public Image image;
		public String caption;
		public String description;
		public String value;

		public boolean equals(Object obj) {
			return (this == obj) || value.equals(obj);
		}
	}
	
	private IComponent component;
	private ThumbData[] data;
	private Map<String, ThumbData> dataMap;
	private String theValue;
	
	/**
	 * @param parent
	 * @param style
	 * @param instance 
	 */
	public ListCustomizerComposite(Composite parent, int style, EObject instance) {
		super(parent, style);
		IComponentInstance componentInstance = 
			(IComponentInstance) EcoreUtil.getRegisteredAdapter(instance, IComponentInstance.class);
		component = componentInstance.getComponent();

		String[] styles = ListLayoutData.getComponentListBoxStyles(component);
		initData(styles);
		
		getThumbnailViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					ThumbData thumbData = dataMap.get(selection.getFirstElement());
					getDescriptionText().setText(thumbData.description);
					theValue = thumbData.value;
				}
			}
		});
		getThumbnailViewer().setContentProvider(new ArrayContentProvider());
		getThumbnailViewer().setLabelProvider(new LabelProvider() {
			public Image getImage(Object element) {
				ThumbData data = dataMap.get(element.toString());
				return data.image;
			}

			public String getText(Object element) {
				ThumbData data = dataMap.get(element.toString());
				return data.caption;
			}
		});
		getThumbnailViewer().setInput(dataMap.keySet());
		getThumbnailViewer().getComposite().setFocus();
		
		setViewerTitle(Messages.getString("ListCustomizerComposite.ViewerTitle")); //$NON-NLS-1$
		setDescriptionTitle(Messages.getString("ListCustomizerComposite.DescriptionTitle")); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) 
			getThumbnailViewer().getComposite().forceFocus();
	}
	
	/**
	 * 
	 */
	private void initData(String[] styles) {
		data = new ThumbData[styles.length];
		dataMap = new HashMap<String, ThumbData>();
		for (int i = 0; i < styles.length; i++) {
			data[i] = new ThumbData();
			String image = Messages.getString("ListCustomizerComposite." + styles[i] + ".Image"); //$NON-NLS-1$ $NON-NLS-2$
			data[i].image = loadThumb(makeKey(image)); //$NON-NLS-1$ $NON-NLS-2$
			data[i].caption = Messages.getString("ListCustomizerComposite." + styles[i] + ".Caption"); //$NON-NLS-1$ $NON-NLS-2$
			data[i].description = Messages.getString("ListCustomizerComposite." + styles[i] + ".Description"); //$NON-NLS-1$ $NON-NLS-2$
			data[i].value = styles[i];
			dataMap.put(data[i].value, data[i]);
		}
	}

	// The key for each image is the canonical absolute path
	private String makeKey(String relPath) {
		String result = null;
		if (relPath != null && relPath.length() > 0) {
			IFacetContainer fc = (IFacetContainer) component;
			File baseDir = fc.getBaseDirectory();
			File imageFile = new File(baseDir, relPath);
			try {
				result = imageFile.getCanonicalPath();
			}
			catch (IOException e) {
				Check.reportFailure(Messages.getString("ListCustomizerComposite.BadPathError") + component.getId(), e); //$NON-NLS-1$
			}
		}
		return result;
	}
	
	private static synchronized Image loadThumb(String key) {
		Image result = (Image)getImageMap().get(key);
		if (result == null) {
            try {
    			ImageData id = new ImageData(key);
    			ImageDescriptor idesc = ImageDescriptor.createFromImageData(id);
    			result = idesc.createImage();
    			getImageMap().put(key, result);
            } catch (Exception e) {
                Logging.log(ComponentSystemPlugin.getDefault(), 
                        Logging.newStatus(ComponentSystemPlugin.getDefault(), e));
                result = new Image(Display.getDefault(), 1, 1);
            }
		}
		return result;
	}

    private static Map getImageMap() {
        ImageCache cache = (ImageCache) GlobalCache.getCache().get(IMAGE_CACHE_ID);
        if (cache == null) {
            cache = new ImageCache();
            GlobalCache.getCache().put(IMAGE_CACHE_ID, cache);
        }
        return cache.map;
    }

    public String getValue() {
    	return theValue;
    }
}
