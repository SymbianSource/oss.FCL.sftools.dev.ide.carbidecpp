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

import com.nokia.sdt.datamodel.adapter.IScrollBoundsProvider;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.editparts.IImageProvider;
import com.nokia.sdt.uidesigner.ui.utils.ImageFigureHelper;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;

import java.util.Iterator;
import java.util.List;

/**
 * Figure with scrolling capabilities
 */
public class ScrollingContainerFigure extends LocalCoordinatesFigure {

	class ContentsPane extends FreeformLayer {

		private ScrollingContainerFigure parentFigure;

		public ContentsPane(ScrollingContainerFigure figure) {
			super();
			parentFigure = figure;
		}

		protected void paintFigure(Graphics graphics) {
			// don't draw anything
		}

		public void repaint() {
			dirty = true;
			List children = getChildren();
			for (Iterator<IFigure> iter = children.iterator(); iter.hasNext();) {
				iter.next().repaint();
			}
			super.repaint();
			parentFigure.repaint();
		}

		public Rectangle getFigureBounds() {
			return parentFigure.getBounds();
		}

		public void removeNotify() {
			super.removeNotify();
			helper.disposeImage();
		}

	}
	
	class Viewport extends FreeformViewport {
		@Override
		protected void paintFigure(Graphics graphics) {
			// don't draw anything
		}
	}
	
	public class ScrollPane extends org.eclipse.draw2d.ScrollPane {

		private class ScrollBar extends org.eclipse.draw2d.ScrollBar {
			private final static int SMALL_SCROLL_BAR_WIDTH = 10;
			
			private class ScrollBarLayout extends org.eclipse.draw2d.ScrollBarLayout {
				public ScrollBarLayout(Transposer transposer) {
					super(transposer);
				}
				
				@Override
				protected Dimension calculatePreferredSize(IFigure parent, int w, int h) {
					Insets insets = transposer.t(parent.getInsets());
					Dimension d = new Dimension(SMALL_SCROLL_BAR_WIDTH, SMALL_SCROLL_BAR_WIDTH * 4);
					d.expand(insets.getWidth(), insets.getHeight());
					return transposer.t(d);
				}
			}
			
			@Override
			protected void initialize() {
				ScrollBarLayout scrollBarLayout = new ScrollBarLayout(transposer);
				setLayoutManager(scrollBarLayout);
				setUpClickable(createDefaultUpButton());
				setDownClickable(createDefaultDownButton());
				setPageUp(createPageUp());
				setPageDown(createPageDown());
				setThumb(createDefaultThumb());
			}
		}
		
		@Override
		protected void createHorizontalScrollBar() {
			ScrollBar bar = new ScrollBar();
			bar.setHorizontal(true);
			setHorizontalScrollBar(bar);
		}

		@Override
		protected void createVerticalScrollBar() {
			ScrollBar bar = new ScrollBar();
			setVerticalScrollBar(bar);
		}

		protected void paintFigure(Graphics graphics) {
			// don't draw anything
		}
	}

	private ImageFigureHelper helper;
	private ContentsPane contentsPane;
	private IImageProvider imageProvider;
	private IScrollBoundsProvider scrollBoundsProvider;
	private IDesignerEditor editor;
	private ScrollPane scrollPane;
	private boolean scrollsVertically;
	private boolean scrollsHorizontally;

	private boolean dirty;

	public ScrollingContainerFigure(IImageProvider imageProvider, 
			IScrollBoundsProvider scrollBoundsProvider, 
			IDesignerEditor editor,
			boolean vScroll, boolean hScroll) {
		super();
		this.imageProvider = imageProvider;
		this.scrollBoundsProvider = scrollBoundsProvider;
		this.editor = editor;
		// create scroll pane
		scrollPane = new ScrollPane();
		Check.checkContract(vScroll || hScroll);
		scrollPane.setHorizontalScrollBarVisibility(ScrollPane.NEVER);
		scrollPane.setVerticalScrollBarVisibility(ScrollPane.NEVER);
		scrollsHorizontally = hScroll;
		scrollsVertically = vScroll;
		// create contents pane
		contentsPane = new ContentsPane(this);
		contentsPane.setOpaque(true);
		contentsPane.setLayoutManager(new FreeformLayout());
		setLayoutManager(new LayoutManager() {

			public Object getConstraint(IFigure child) {
				return null;
			}

			public Dimension getMinimumSize(IFigure container, int wHint, int hHint) {
				return getPreferredSize(container, wHint, hHint);
			}

			public Dimension getPreferredSize(IFigure container, int wHint, int hHint) {
				Rectangle r = new Rectangle(container.getBounds());
				Dimension prefSize = r.getSize();
				if (wHint > -1)
					prefSize.width = wHint;
				if (hHint > -1)
					prefSize.height = hHint;
				return prefSize;
			}

			public void invalidate() {
			}

			public void layout(IFigure container) {
				List children = container.getChildren();
				if (children.isEmpty())
					return;
				
				Point location = new Point(0, 0);
				Dimension size = new Dimension(-1, -1);
				if (ScrollingContainerFigure.this.scrollBoundsProvider != null) {
					ILookAndFeel laf = ScrollingContainerFigure.this.editor.getDisplayModel().getLookAndFeel();
					org.eclipse.swt.graphics.Rectangle scrollBounds = 
						ScrollingContainerFigure.this.scrollBoundsProvider.getScrollBounds(laf);
					if (scrollBounds != null) {
						location.setLocation(scrollBounds.x, scrollBounds.y);
						size.setSize(new Dimension(scrollBounds.width, scrollBounds.height));
					}
				}
				IFigure child = (IFigure) children.get(0);
				child.setSize(getPreferredSize(container, size.width, size.height));
				child.setLocation(location);
			}

			public void remove(IFigure child) {
			}

			public void setConstraint(IFigure child, Object constraint) {
			}
			
		});
		add(scrollPane);
		// create viewport
		scrollPane.setViewport(new Viewport());
		scrollPane.setContents(contentsPane);
		helper = new ImageFigureHelper(this);
	}

	public IFigure getContentPane(){
		return contentsPane;
	}
	
	public void setScrollbarsVisible(boolean visible) {
		int visValue = visible ? ScrollPane.AUTOMATIC : ScrollPane.NEVER;
		if (scrollsHorizontally) {
			scrollPane.setHorizontalScrollBarVisibility(visValue);
		}
		if (scrollsVertically) {
			scrollPane.setVerticalScrollBarVisibility(visValue);
		}
	}
	
	public boolean isScrollable() {
		return scrollsHorizontally || scrollsVertically;
	}

	public void removeNotify() {
		super.removeNotify();
		helper.disposeImage();
	}

	protected void paintFigure(Graphics graphics) {
		if (dirty) {
			helper.setImage(ScrollingContainerFigure.this.imageProvider.getImage());
			dirty = false;
		}
		helper.paintImage(graphics);
	}

	public ScrollPane getScrollPane() {
		return scrollPane;
	}
}
