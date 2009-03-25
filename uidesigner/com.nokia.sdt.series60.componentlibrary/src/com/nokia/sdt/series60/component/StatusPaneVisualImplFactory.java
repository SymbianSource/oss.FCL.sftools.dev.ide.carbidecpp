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
package com.nokia.sdt.series60.component;

import com.nokia.cpp.internal.api.utils.core.CacheMap;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.symbian.laf.Series60LAF;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.*;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.symbian.S60ComponentAttributes;
import com.nokia.sdt.symbian.dm.S60ModelUtils;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.utils.drawing.GC;
import com.nokia.sdt.workspace.IProjectContext;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class StatusPaneVisualImplFactory implements IImplFactory, AknViewVisualImplKeys {
	
	public Object getImpl(EObject componentInstance) {
		return new VisualAppearance(componentInstance);
	}
	
    private static IDisplayModel doGetDisplayModel(IDesignerDataModel model, EObject container) {
        IDisplayModel result = null;
        try {
            result = model.getDisplayModelForRootContainer(container);
        }
        catch (CoreException x) {
            Series60ComponentPlugin.log(x);
        }
        return result;
    }
    
    
    static IProjectContext getProjectContext(IComponentInstance ci) {
        IProjectContext result = null;
        IDesignerDataModel model = ci.getDesignerDataModel();
        if (model != null) {
            result = model.getProjectContext();
        }
        return result;
    }
    
    static ISymbianProjectContext getSymbianProjectContext(IProjectContext projectContext) {
        ISymbianProjectContext result = (ISymbianProjectContext) 
            projectContext.getAdapter(ISymbianProjectContext.class);
        return result;
    }

    static ImageData loadImageData(String pathStr) throws IOException {
        ImageData result = null;
        Path path = new Path(pathStr);
        InputStream is = FileLocator.openStream(Series60ComponentPlugin.getDefault().getBundle(), path, false);
        if (is != null) {
            ImageLoader loader = new ImageLoader();
            ImageData[] data = loader.load(is);
            is.close();
            if (data != null && data.length >= 1) {
                result = data[0];
            }
        }
        return result;
    }
    
    static Image loadImage(String pathStr, String maskPathStr) {
        Image result = null;
        try {
            ImageData imageData = loadImageData(pathStr);
            ImageData imageMaskData = loadImageData(maskPathStr);
            if (imageData != null && imageMaskData != null) {
                result = ImageUtils.createMaskedImage(Display.getDefault(),
                            imageData, imageMaskData);
            }
        }
        catch (IOException x) {
            Series60ComponentPlugin.log(x);
        }
        return result;
    }

	static class VisualAppearance implements IVisualAppearance, LayoutAreaConfigurationListener {
        
        private EObject emfObject;
		private IComponentInstance componentInstance;
		private ILayoutObject layoutObject;
		private Image contextImage; // not owned

		VisualAppearance(EObject emfObject) {
			this.emfObject = emfObject;
			this.componentInstance = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
					emfObject, IComponentInstance.class);
			this.layoutObject = (ILayoutObject) EcoreUtil.getRegisteredAdapter(
					emfObject, ILayoutObject.class);
			Check.checkArg(componentInstance);
			Check.checkArg(layoutObject);
            
            try {
                IDisplayModel displayModel = ModelUtils.getDisplayModel(emfObject);
                displayModel.addLayoutAreaListener(this);
            } catch (CoreException e) {
                Series60ComponentPlugin.log(e);
            }
 
		}
		
		private boolean parenHasCachedImage() {
			EObject rootContainer = componentInstance.getRootContainer();
			IDisplayModel displayModel = doGetDisplayModel(componentInstance.getDesignerDataModel(), rootContainer);
			CacheMap imageCache = (CacheMap) displayModel.getCache().get(CACHEIMAGEKEY);
			if (imageCache != null) {
				return imageCache.get(STATUS_PANE_CONTEXT_IMAGE) != null;
			}
			return false;
		}

        private boolean shouldDrawDefaultIcon(ILookAndFeel laf) {
            // draw the default icon if:
            // LAF requires an icon to be drawn -and-
            // have no status pane context child (with an override icon) -and- 
            // status pane's parent is not an avkon view -or- 
            // status pane's parent has no stored appui provided image
            if (!laf.getBoolean(Series60LAF.SHOW_CONTEXT_ICON, true))
                return false;
            
            if ((ModelUtils.findFirstComponentInstance(emfObject, S60ModelUtils.S60_CONTEXT_ICON_BASE, true) != null))
                return false;
            
            IComponentInstance parentInstance = ModelUtils.getComponentInstance(componentInstance.getParent());
            boolean parentIsAvkonView = ModelUtils.isInstanceOf(parentInstance.getEObject(), S60ModelUtils.S60_AVKON_VIEW);
            return !parentIsAvkonView || !parenHasCachedImage();
        }

        private Image getContextIcon(ILookAndFeel laf) {
            if (!shouldDrawDefaultIcon(laf))
                return null;
            if (contextImage != null)
                return contextImage;
            contextImage = laf.getImage("context.default.icon", //$NON-NLS-1$
                        laf.getDimension("context.icon.size"));
            return contextImage;
        }
		
		public void draw(GC gc, ILookAndFeel laf) {
            Image icon = getContextIcon(laf);
			if (icon != null) {
				Point pos = laf.getPosition(Series60LAF.CONTEXT_ICON_POSITION);
				if (pos != null) {
					gc.drawImage(icon, pos.x, pos.y);
				}
			}
			Image vgaToolbarImage = laf.getImage("vga.status.toolbar"); //$NON-NLS-1$
			if (vgaToolbarImage != null) {
				Rectangle r = laf.getRectangle(Series60LAF.STATUS_BAR1_BOUNDS);
				ImageData imageData = vgaToolbarImage.getImageData();
				gc.drawImage(vgaToolbarImage, 0, 0, imageData.width, imageData.height,
					r.x, r.y, r.width, r.height);
				
			}
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#getPreferredSize(int, int, com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			return null;
		}

		public EObject getEObject() {
			return emfObject;
		}

        /* (non-Javadoc)
         * @see com.nokia.sdt.displaymodel.LayoutAreaConfigurationListener#configurationChanging(com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration)
         */
        public void configurationChanging(LayoutAreaConfiguration configuration) {
            contextImage = null;
        }

        /* (non-Javadoc)
         * @see com.nokia.sdt.displaymodel.LayoutAreaConfigurationListener#configurationChanged(com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration)
         */
        public void configurationChanged(LayoutAreaConfiguration configuration) {
        }

        /* (non-Javadoc)
         * @see com.nokia.sdt.displaymodel.LayoutAreaConfigurationListener#configurationSetChanged(java.util.Collection)
         */
        public void configurationSetChanged(Collection newConfigurations) {
        }
	}

    private static void cacheInstanceSnapshot(CacheMap imageCache, EObject instance, String imageKey, String positionKey, Point offset) {
        if (instance != null) {
            ILayoutObject lo = ModelUtils.getLayoutObject(instance);
            if (lo != null) {
                Image image = lo.getImage();
                Rectangle bounds = lo.getBounds();
                Point position = new Point(bounds.x, bounds.y);
                position.x += offset.x;
                position.y += offset.y;
                
                if (image != null) // put throws if the value is null
                    imageCache.put(imageKey, image);
                imageCache.put(positionKey, position);
            }
        }
    }
    
    /**
     * @param imageCache
     * @param statusPaneInstance
     */
    public static void cacheSnapshots(CacheMap imageCache, EObject statusPaneInstance) {
        Rectangle statusBounds = ModelUtils.getLayoutObject(statusPaneInstance).getBounds();
        Point statusPaneOffset = new Point(statusBounds.x, statusBounds.y);

        EObject instance = ModelUtils.findFirstComponentInstance(statusPaneInstance, S60ModelUtils.S60_CONTEXT_ICON_BASE, true);
        if (instance != null)
            cacheInstanceSnapshot(imageCache, instance, STATUS_PANE_CONTEXT_IMAGE, STATUS_PANE_CONTEXT_POSITION, statusPaneOffset);
        else {
            ILookAndFeel laf;
            try {
                laf = ModelUtils.getDisplayModel(statusPaneInstance).getLookAndFeel();
                Point pos = laf.getPosition(Series60LAF.CONTEXT_ICON_POSITION);
                if (pos != null) {
                    Image contextImage = laf.getImage("context.default.icon", //$NON-NLS-1$
                            laf.getDimension("context.icon.size"));
                    // store a copy
                    imageCache.put(STATUS_PANE_CONTEXT_IMAGE, ImageUtils.copyImage(Display.getDefault(), contextImage));
                    Point contextOffset = new Point(pos.x + statusPaneOffset.x, pos.y + statusPaneOffset.y);
                    imageCache.put(STATUS_PANE_CONTEXT_POSITION, contextOffset);
                }
            } catch (CoreException e) {
                Series60ComponentPlugin.log(e);
            }
        }

        instance = ModelUtils.findFirstComponentInstance(statusPaneInstance, S60ModelUtils.S60_TITLE_PANE_BASE, true);
        cacheInstanceSnapshot(imageCache, instance, STATUS_PANE_TITLE_IMAGE, STATUS_PANE_TITLE_POSITION, statusPaneOffset);
        
        instance = ModelUtils.findFirstComponentInstance(statusPaneInstance, S60ModelUtils.S60_CONTROL_PANE_BASE, true);
        cacheInstanceSnapshot(imageCache, instance, CONTROL_PANE_IMAGE, CONTROL_PANE_POSITION, new Point(0,0));
        
        instance = ModelUtils.findImmediateChildWithAttributeValue(statusPaneInstance, 
                        S60ComponentAttributes.NAVIPANE_CONTENT, "true");
        cacheInstanceSnapshot(imageCache, instance, NAVI_CONTENT_IMAGE, NAVI_CONTENT_POSITION, statusPaneOffset);
        
    }
}
