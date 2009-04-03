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


package com.nokia.sdt.uidesigner.ui.figure;

import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.displaymodel.adapter.LayoutObjectListener;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import java.util.List;

public class TransientLayer extends Layer {
	public class TransientRootFigure extends LocalCoordinatesFigure {
		
		private Image cachedImage;
		private IFigure rootContainerFigure;
		private ZoomListener zoomListener;
		private boolean noChildClipping;
		
		public TransientRootFigure(IFigure rootContainerFigure, boolean noChildClipping) {
			this.rootContainerFigure = rootContainerFigure;
			this.noChildClipping = noChildClipping;
			updateBounds();

			setLayoutManager(new FreeformLayout());
			
			getZoomManager().addZoomListener(zoomListener = new ZoomListener() {
				public void zoomChanged(double zoom) {
					updateBounds();
				}
			});
		}
		
		public void removeNotify() {
			getZoomManager().removeZoomListener(zoomListener);
			super.removeNotify();
		}
		
		public void updateBounds() {
			Rectangle rect = new Rectangle(rootContainerFigure.getBounds());
			rootContainerFigure.translateToAbsolute(rect);
			Viewport viewport = getViewport();
			if (viewport != null)
				viewport.translateFromParent(rect);
			double unscale = 1.0/getZoomManager().getZoom();
			rect.scale(unscale);
			Insets insets = rootContainerFigure.getBorder().getInsets(rootContainerFigure);
			rect.translate(insets.left, insets.right);
			rect.shrink(insets.right, insets.bottom);
			if (!getBounds().equals(rect))
				setBounds(rect);
		}
		
		private Viewport getViewport() {
			IFigure parent = getParent();
			while ((parent != null) && !(parent instanceof Viewport))
				parent = parent.getParent();
			
			return (Viewport) parent;
		}
		
		private void disposeCachedImage() {
			if (cachedImage != null)
				cachedImage.dispose();
			cachedImage = null;
		}
		
		public void repaint() {
			disposeCachedImage();
			super.repaint();
		}
		
		public void cacheBackgroundImage() {
			Rectangle r = new Rectangle(rootContainerFigure.getBounds());
			disposeCachedImage();
			cachedImage = new Image(Display.getDefault(), r.width, r.height);
			GC gc = new GC(cachedImage);
			SWTGraphics g = new SWTGraphics(gc);
			rootContainerFigure.paint(g);
			if (ImageUtils.isAlphaBlendingSupported()) {
				g.setBackgroundColor(ColorConstants.lightGray);
				g.setAlpha(128);
				g.fillRectangle(r);
			}
			
			g.dispose();
			gc.dispose();

			if (!ImageUtils.isAlphaBlendingSupported()) {
				Image newImage = ImageUtils.maskWithSolidColor(Display.getDefault(), cachedImage, 128, ColorConstants.lightGray);
				cachedImage.dispose();
				cachedImage = newImage;
			}
		}
		
		public void paint(Graphics g) {
			updateBounds();
			if (cachedImage == null) {
				cacheBackgroundImage();
			}
			
			if (cachedImage != null) {
				Point location = getLocation();
				Insets insets = rootContainerFigure.getBorder().getInsets(rootContainerFigure);
				location.translate(-insets.left - 1, -insets.top);
				g.drawImage(cachedImage, location);
			}
			paintClientArea(g);
		}

		protected void paintClientArea(Graphics g) {
			if (getChildren().isEmpty())
				return;

			if (noChildClipping) {
				g.pushState();
				g.translate(getBounds().x + getInsets().left,
						getBounds().y + getInsets().top);
				paintChildren(g);
				g.restoreState();
				g.popState();
			}
			else{
				super.paintClientArea(g);
			}
		}
		
		protected void paintChildren(Graphics g) {
			if (noChildClipping) {
				IFigure child;
				List children = getChildren();
				for (int i = 0; i < children.size(); i++) {
					child = (IFigure) children.get(i);
					if (child.isVisible()) {
						child.paint(g);
					}
				}
			}
			else {
				super.paintChildren(g);
			}
		}
	}
	
	private TransientRootFigure transientRootFigure;
	private IDesignerEditor editor;
	private LayoutContentsFigure layoutContentsFigure;
	private FigureListener figureListener;
	private LayoutObjectListener layoutObjectListener;
	private MouseListener mouseListener;
	
	public TransientLayer() {
		setLayoutManager(new FreeformLayout());
	}
	
	public TransientRootFigure getTransientRootFigure() {
		return transientRootFigure;
	}
	
	private EObject getRootContainer() {
		EObject[] rootContainers = editor.getDataModel().getRootContainers();
		return rootContainers[0];
	}
	
	private IFigure getRootContainerFigure() {
		GraphicalViewer upperGraphicalViewer = editor.getUpperGraphicalViewer();
		GraphicalEditPart rootContainerEditPart = 
			(GraphicalEditPart) upperGraphicalViewer.getEditPartRegistry().get(getRootContainer());
		return rootContainerEditPart.getContentPane();
	}
	
	private void hookLayout() {
		layoutContentsFigure.addFigureListener(figureListener = new FigureListener() {
			public void figureMoved(IFigure source) {
				setBounds(source.getBounds());
			}
		});

		ILayoutObject rootContainer = Adapters.getLayoutObject(getRootContainer());
		rootContainer.addListener(layoutObjectListener = new LayoutObjectListener() {
			public void imageChanged(ILayoutObject object) {
				if (object.getEObject().equals(getRootContainer())) {
					transientRootFigure.repaint();
				}
			}

			public void boundsChanged(ILayoutObject object) {
				if (object.getEObject().equals(getRootContainer())) {
					transientRootFigure.updateBounds();
				}
			}
		});
		addMouseListener(mouseListener = new MouseListener.Stub() {
			public void mousePressed(MouseEvent me) {
				IFigure figure = findDescendantAtExcluding(me.x, me.y, null);
				if (figure.equals(TransientLayer.this) ||
						figure.equals(transientRootFigure))
					editor.setLayoutMode();
			}
		});
		transientRootFigure.addMouseListener(mouseListener);
	}
	
	private void unhookLayout() {
		layoutContentsFigure.removeFigureListener(figureListener);
		ILayoutObject rootContainer = Adapters.getLayoutObject(getRootContainer());
		rootContainer.removeListener(layoutObjectListener);
		removeMouseListener(mouseListener);
		transientRootFigure.removeMouseListener(mouseListener);
	}

	public void paint(Graphics g) {
		if (transientRootFigure != null) {
			g.pushState();
			g.translate(getBounds().x + getInsets().left,
					getBounds().y + getInsets().top);
			transientRootFigure.paint(g);
			g.popState();
		}
		else
			super.paint(g);
	}

	public void installRootFigure(IDesignerEditor editor, LayoutContentsFigure layoutContentsFigure, boolean noChildClipping) {
		Check.checkArg(editor);
		this.editor = editor;
		Check.checkArg(layoutContentsFigure);
		this.layoutContentsFigure = layoutContentsFigure;
		IFigure rootContainerFigure = getRootContainerFigure();
		transientRootFigure = new TransientRootFigure(rootContainerFigure, noChildClipping);
		rootContainerFigure.setVisible(false);
		add(transientRootFigure);
		hookLayout();
		setOpaque(true);
	}

	public void uninstallRootFigure() {
		if (transientRootFigure != null) {
			unhookLayout();
			remove(transientRootFigure);
			transientRootFigure = null;
			getRootContainerFigure().setVisible(true);
			setOpaque(false);
		}
	}
	
	public ZoomManager getZoomManager() {
		if (editor != null)
			return (ZoomManager) editor.getAdapter(ZoomManager.class);
		
		return null;
	}
	
// DD: Backed this out for now, as not addressing the issue fully, and
// the problem is not that important because the user can resize the window
// down and to the right. The real problem of growing upward was fixed in TransientObjectEditPart.
//
//	private Rectangle calculateTotalBounds(IFigure root) {
//		Rectangle rect = new Rectangle(root.getBounds());
//		root.translateToAbsolute(rect);
//		List children = root.getChildren();
//		for (Iterator iter = children.iterator(); iter.hasNext();) {
//			IFigure child = (IFigure) iter.next();
//			if (child.isVisible()) {
//				Rectangle childBounds = calculateTotalBounds(child);
//				rect = rect.union(childBounds);
//			}
//		}
//		return rect;
//	}
//	
//	@Override
//	public Dimension getPreferredSize(int wHint, int hHint) {
//		Rectangle rect = new Rectangle(getBounds());
//		translateToAbsolute(rect);
//		if (transientRootFigure != null)
//			rect = rect.union(calculateTotalBounds(transientRootFigure).expand(30, 30));
//		return rect.getSize().expand(rect.x, rect.y);
//	}

	protected IFigure findDescendantAtExcluding(int x, int y, TreeSearch search) {
		return EditorUtils.findDeepestChildAt(this, x, y);
	}
}