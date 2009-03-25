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
package com.nokia.sdt.utils.ui;

import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.utils.drawing.*;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.*;

import java.util.*;
import java.util.List;

/**
 * Displays thumbnails with caption for single or multiple selection
 */
public class ThumbnailGridViewer extends StructuredViewer {

	private List<Object> elements;
	// contains items or null
	private List<ThumbnailItem> items;
	private Map<Object, ThumbnailItem> itemMap;
	private Set<Integer> selectedIndices;
	//private Composite scroller;
	Composite composite;
	private int cursorIndex;
	private int style;
	Control keyHandlingWidget;
	private final static int DEFAULT_COLS = 4;
	ThumbnailItemLayout layout;
	
	private GC tempGC;
	private SwtFont itemFont;
	private int revealIndex;
	
	public static final int DEFAULT_HEIGHT = 90;
	public static final int DEFAULT_WIDTH = 90;
	
	private Point scrollOrigin;
	
	/**
	 * Create a thumbnail grid viewer.  Available style bits
	 * are<p>
	 * <li>SWT.MULTI: allow multiple selection
	 * <p>
	 * Remaining bits are passed to the ScrolledComposite
	 * @param parent
	 * @param style
	 * @see ScrolledComposite#ScrolledComposite(Composite, int)
	 */
	public ThumbnailGridViewer(Composite parent, final int style) {
		this.style = style & SWT.MULTI;
		
		composite = new Composite(parent, (style & ~SWT.MULTI) /*| SWT.H_SCROLL*/ | SWT.V_SCROLL);
		
		composite.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		composite.setEnabled(true);
		//scroller.setExpandHorizontal(true);
		//scroller.setExpandVertical(true);
		//scroller.setMinSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		tempGC = new GC(parent);
		itemFont = new SwtFont(tempGC.getFont());
		
		//composite = new ThumbnailGridComposite(scroller, SWT.NONE);
		
		layout = new ThumbnailItemLayout(DEFAULT_COLS, isMultiSelect() ? 2: 0);
		composite.setLayout(layout);
		//composite.setBackground(composite.getParent().getBackground());
		
		//scroller.setContent(composite);
		//scroller.setLayout(new FillLayout());
		elements = new ArrayList<Object>();
		items = new ArrayList();
		itemMap = new HashMap<Object, ThumbnailItem>();
		selectedIndices = new HashSet<Integer>();
		
		scrollOrigin = new Point(0, 0);
		
		/*
		scroller.addControlListener(new ControlListener() {

			public void controlMoved(ControlEvent e) {
				updateScrollSize();
			}

			public void controlResized(ControlEvent e) {
				updateScrollSize();
			}
			
		});*/

		composite.addControlListener(new ControlListener() {

			public void controlMoved(ControlEvent e) {
				updateScrollSize();
			}

			public void controlResized(ControlEvent e) {
				updateScrollSize();
			}
			
		});
		
		Listener scrollListener = new Listener() {

			public void handleEvent(Event event) {
				updateScrollPosition();
			}
			
		};
		
		composite.getVerticalBar().addListener(SWT.Selection, scrollListener);
		//composite.getHorizontalBar().addListener(SWT.Selection, scrollListener);
		
		cursorIndex = -1;
		revealIndex = -1;
		
		keyHandlingWidget = composite;
		keyHandlingWidget.addKeyListener(new KeyAdapter() {
			// this has to be done to become part of the tab order and
			// get the focus from it for traversal events
			public void keyPressed(KeyEvent e) {
				boolean additive = isMultiSelect() && (e.stateMask & SWT.CTRL) != 0;
				boolean range = isMultiSelect() && (e.stateMask & SWT.SHIFT) != 0;
				int index = cursorIndex;
				if (e.keyCode == ' ') {
					if (!selectedIndices.contains(index))
						selectIndex(index, true);
					else if (isMultiSelect())
						unselectIndex(index);
				} else if (e.keyCode == 'a' && additive /* e.g., CTRL and multi-select */) {
					selectAll();
				} else if (e.keyCode == SWT.PAGE_DOWN || e.keyCode == SWT.PAGE_UP) {
					int direction = e.keyCode == SWT.PAGE_DOWN ? 1 : -1;
					reselectTraversal(additive, range, 
							cursorIndex + direction * layout.columns * layout.visibleRows);
				} else if (e.keyCode == SWT.HOME) {
					int target = (e.stateMask & SWT.CTRL) != 0 ? 0 : layout.adjustEdgeIndex(cursorIndex);
					reselectTraversal(false, range, target);
				} else if (e.keyCode == SWT.END) {
					int target = (e.stateMask & SWT.CTRL) != 0 ? elements.size() : layout.adjustEdgeIndex(cursorIndex) + layout.columns;
					reselectTraversal(false, range, target - 1);
				} else {
					e.doit = false;
				}
			}
		});
		keyHandlingWidget.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				redrawCursor();
				if (e.detail == SWT.TRAVERSE_ESCAPE || e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = true;
					return;
				}
				if (cursorIndex < 0 || e.detail == SWT.TRAVERSE_TAB_NEXT) {
					if (cursorIndex < 0 && items.size() > 0) {
						if (isMultiSelect())
							selectIndex(0, false);
						else
							cursorIndex = 0;
					}
					keyHandlingWidget.setFocus();
				} else if (e.detail == SWT.TRAVERSE_ARROW_NEXT || e.detail == SWT.TRAVERSE_ARROW_PREVIOUS) {
					boolean additive = isMultiSelect() && (e.stateMask & SWT.CTRL) != 0;
					boolean range = isMultiSelect() && (e.stateMask & SWT.SHIFT) != 0;
					int direction = e.detail == SWT.TRAVERSE_ARROW_NEXT ? 1 : -1;
					int jump = e.keyCode == SWT.ARROW_LEFT || e.keyCode == SWT.ARROW_RIGHT ? 1 : layout.columns; 
					reselectTraversal(additive, range, cursorIndex + jump * direction);
				}
				redrawCursor();
			}
		});
		
		keyHandlingWidget.addListener(SWT.FocusIn, new Listener() {
			public void handleEvent(Event event) {
				redrawItems();
			}
		});
		keyHandlingWidget.addListener(SWT.FocusOut, new Listener() {
			public void handleEvent(Event event) {
				redrawItems();
			}
		});
	}
	
	/**
	 * Update the selection upon a traversal from the cursor to the given index,
	 * either replacing or toggling the selection.
	 * @param additive was Ctrl held?
	 * @param range was Shift held?
	 * @param items how many items to traverse?
	 * @param limit 
	 */
	protected void reselectTraversal(boolean additive, boolean range,
			int limit) {
		limit = Math.max(0, Math.min(limit, elements.size() - 1));
		if (range) {
			selectRangeTo(limit, additive);
		} else if (!additive) {
			selectIndex(limit, false);
		} else {
			cursorIndex = limit;
			revealIndex(cursorIndex);
		}

	}

	/**
	 * 
	 */
	protected void updateScrollPosition() {
		scrollOrigin = new Point(
				0, //composite.getHorizontalBar().getSelection(),
				composite.getVerticalBar().getSelection());
		layout.setScrollOrigin(scrollOrigin);
		ensureVisibleItemsValid();
		refreshItems();
	}

	/**
	 * 
	 */
	protected void redrawCursor() {
		if (cursorIndex >= 0) {
			ThumbnailItem item = items.get(cursorIndex);
			if (item != null) {
				item.redraw();
			}
		}
	}

	/**
	 * 
	 */
	protected void updateScrollSize() {
		Rectangle bounds = composite.getClientArea();

		int selection = scrollOrigin.y;

		int bottom = Math.max(0, layout.maxHeight * layout.rows);
		
		// clamp selection to visible area
		if (bottom > bounds.height && selection + bounds.height >= bottom)
			selection = bottom - bounds.height;
		
		composite.getVerticalBar().setValues(selection, 0, bottom, 
				layout.visibleRows * layout.maxHeight, 
				layout.maxHeight, Math.min(bounds.height, bottom));

		// resync scroll position and items
		scrollTo(new Point(scrollOrigin.x, selection));
		
		// ensure a revealed item is actually visible
		if (revealIndex >= 0) {
			revealIndex(revealIndex);
		}
		
	}

	/**
	 * Ensure that visible items in the scrolled area are valid,
	 * i.e., that their images have been loaded
	 */
	protected void ensureVisibleItemsValid() {
		composite.setLayoutDeferred(true);
		for (ThumbnailItem item : items) {
			ensureVisibleItemValid(item);
		}
		composite.setLayoutDeferred(false);
	}

	/**
	 * @param item
	 */
	private void ensureVisibleItemValid(ThumbnailItem item) {
		if (item != null && isItemVisible(item) && item.imageDataRef == null) {
			Point size = item.getSize();
			Point newSize = item.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			if (!size.equals(newSize))
				composite.layout();
		}
	}

	/**
	 * Just redraw items, e.g., for focus change.
	 *
	 */
	protected void redrawItems() {
		composite.redraw();
		for (ThumbnailItem item : items) {
			if (item != null)
				item.redraw();
		}
	}
	
	/**
	 * Relayout and redraw the entire composite.
	 *
	 */
	protected void redraw() {
		composite.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		composite.layout();
		composite.redraw();
		for (ThumbnailItem item : items) {
			if (item != null && !item.isDisposed())
				item.redraw();
		}
		updateScrollSize();
	}
	
	/**
	 * Add or remove the items in the range, following Shift-Click behavior.
	 * @param item
	 * @param additive
	 */
	protected void selectRangeTo(int index, boolean additive) {
		int otherIndex = cursorIndex;
		
		boolean enable = !selectedIndices.contains(index); 
		
		int dir = index > otherIndex ? 1 : -1;
		int stepIndex = otherIndex;
		while (stepIndex != index) {
			if (enable) {
				if (!selectedIndices.contains(stepIndex))
					selectedIndices.add(stepIndex);
			}
			else
				selectedIndices.remove(stepIndex);
			stepIndex += dir;
		}
		
		if (!selectedIndices.contains(index))
			selectedIndices.add(index);
		cursorIndex = index;
		syncSelection();
		revealIndex(index);
	}

	/**
	 * Set selection to item at given index, cropping to legal bounds
	 * @param index 
	 * @param additive true: add to selection, false: replace
	 */
	protected void selectIndex(int index, boolean additive) {
		
		if (elements.size() == 0) 
			index = 0;
		else {
			while (index < 0)
				index += elements.size();
			index %= elements.size();
		}
		selectItem(index, additive, true);
	}
	
	/**
	 * Select an item, optionally adding to the selection,
	 * and make it the cursor.
	 * @param item
	 */
	void unselectIndex(int index) {
		cursorIndex = index;
		if (cursorIndex >= 0) {
			selectedIndices.remove(cursorIndex);
			syncSelection();
			revealIndex(cursorIndex);
		}
	}

	protected void selectAll() {
		selectedIndices.clear();
		for (int idx = 0; idx < elements.size(); idx++)
			selectedIndices.add(idx);
		syncSelection();
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.jface.viewers.Viewer#inputChanged(java.lang.Object, java.lang.Object)
	 */
	protected synchronized void inputChanged(Object input, Object oldInput) {
		super.inputChanged(input, oldInput);
		resetElements(input);
		setSelection(StructuredSelection.EMPTY);
		redraw();
	}

	/**
	 * Reset the element array from the input and reset items to match.
	 */
	private void resetElements(Object input) {
		Object[] elementObjects = getFilteredChildren(input);
		elements = Arrays.asList(elementObjects);
		//System.out.println("# elements = " + elements.size());
		layout.setVirtualChildCount(elements.size());
		refreshItems();
	}
	
	/**
	 * Refresh the items map to reflect the visible items
	 */
	private void refreshItems() {
		// get the range of items actually visible
		Pair<Integer, Integer> range = getVisibleItemRange();
		
		itemMap.clear();

		// delete any items not visible
		for (int index = 0; index < items.size(); index++) {
			ThumbnailItem item = items.get(index);
			if (item == null)
				continue;
			if ((index >= elements.size() || item.getElement() != elements.get(index))
					|| (index < range.first || index >= range.second)) {
				items.set(index, null);
				item.dispose();
			}
		}

		// ensure the item list is the right size
		while (items.size() > elements.size())
			items.remove(items.size() - 1);
		
		while (items.size() < elements.size())
			items.add(null);
		
		// create items we need to see, and update mappings and selected state
		for (int index = range.first; index < range.second; index++) {
			ThumbnailItem item = items.get(index);
			if (item == null) {
				item = createItem(index);
				items.set(index, item);
			}
			itemMap.put(item.getElement(), item);
			item.setSelected(selectedIndices.contains(index));
		}
	
		ensureVisibleItemsValid();
		composite.layout();
	}
	
	/**
	 * @return
	 */
	private Pair<Integer, Integer> getVisibleItemRange() {
		Point scrollerSize = composite.getSize();
		if (scrollerSize.x == 0 || scrollerSize.y == 0) {
			return new Pair<Integer, Integer>(0, 0);
		}
		Point origin = scrollOrigin;
		int start = layout.getIndexAtPosition(origin);
		Point corner = new Point(origin.x + scrollerSize.x, origin.y + scrollerSize.y);
		int end = layout.getIndexAtPosition(corner);
		
		return new Pair<Integer, Integer>(Math.max(0, start), Math.min(end, elements.size()));
	}

	/**
	 * @param index
	 * @return
	 */
	private ThumbnailItem createItem(int index) {
		final ThumbnailItem item = new ThumbnailItem(this, (ILabelProvider) getLabelProvider(), itemFont, 
				elements.get(index), index);
		item.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (item.isDisposed())
					return;
				// draw the selection rectangle 
				if (cursorIndex != item.getIndex())
					return;
				Display display = ((Widget) e.getSource()).getDisplay();
				boolean focused = keyHandlingWidget.isFocusControl() || composite.isFocusControl();
				ThumbnailItem cursorItem = items.get(cursorIndex);
				if (cursorItem == null || cursorItem.isDisposed())
					return;
				Point size = cursorItem.getSize();
				if (isMultiSelect() && focused) {
					e.gc.setForeground(cursorItem.isSelected() ? 
							display.getSystemColor(SWT.COLOR_LIST_BACKGROUND) :
								display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
					e.gc.setLineDash(new int[] {1, 1});
					e.gc.drawRectangle(0, 0, size.x - 1, size.y - 1);
				}
				
			}

		});
		return item;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ContentViewer#handleDispose(org.eclipse.swt.events.DisposeEvent)
	 */
	@Override
	protected void handleDispose(DisposeEvent event) {
		super.handleDispose(event);
		elements.clear();
		items.clear();
		itemMap.clear();
		tempGC.dispose();
		tempGC = null;
		itemFont.dispose();
		itemFont = null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.StructuredViewer#handleLabelProviderChanged(org.eclipse.jface.viewers.LabelProviderChangedEvent)
	 */
	@Override
	protected void handleLabelProviderChanged(LabelProviderChangedEvent event) {
		super.handleLabelProviderChanged(event);
		if (items.size() > 0 && !composite.isDisposed()) {
			redraw();
		}
	}	
	
	/**
	 * Select an item, optionally adding to the selection,
	 * and make it the cursor.
	 * @param item
	 */
	void selectItem(int index, boolean additive, boolean reveal) {
		if (!additive) {
			unselectAll();
		}
		cursorIndex = index;
		if (index >= 0) {
			selectedIndices.add(index);
			syncSelection();
			if (reveal)
				revealIndex(index);
		}
		else {
			selectedIndices.clear();
			syncSelection();
		}
		
	}
	
	/**
	 * Get the structured selection corresponding to the selected indices 
	 * @return
	 */
	private ISelection createStructuredSelection() {
		IStructuredSelection selection = new StructuredSelection(getSelectionFromWidget());
		return selection;
	}

	public void selectFirst() {
		if (elements.size() > 0) {
			setSelection(new StructuredSelection(elements.get(0)));
			cursorIndex = 0;
			revealIndex(0);
		}
	}
	
	public void unselectAll() {
		selectedIndices.clear();
		syncSelection();
		cursorIndex = -1;
	}
	
	/**
	 * Synchronize the items with the selected indices
	 */
	private void syncSelection() {
		setSelection(createStructuredSelection());
	}

	/**
	 * Get the top-level widget for the viewer.
	 * @return
	 */
	public Control getViewer() {
		return composite;
	}
	
	public Composite getComposite() {
		return composite;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	public Control getControl() {
		return composite;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.StructuredViewer#doFindInputItem(java.lang.Object)
	 */
	protected Widget doFindInputItem(Object element) {
		if (element.equals(getInput()))
			return getComposite();
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.StructuredViewer#doFindItem(java.lang.Object)
	 */
	protected Widget doFindItem(Object element) {
		return itemMap.get(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.StructuredViewer#doUpdateItem(org.eclipse.swt.widgets.Widget, java.lang.Object, boolean)
	 */
	protected void doUpdateItem(Widget item, Object element, boolean fullMap) {
		//if (item instanceof Item) {
		//	((Item) item).computeSize(SWT.DEFAULT, SWT.DEFAULT);
		//}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.StructuredViewer#getSelectionFromWidget()
	 */
	protected List getSelectionFromWidget() {
		List<Object> selected = new ArrayList<Object>();
		for (Integer index : selectedIndices) {
			int idx = index.intValue();
			if (idx < 0 || idx >= elements.size()) {
				UtilsPlugin.log(new IllegalArgumentException(index.toString()));
			} else {
				selected.add(elements.get(idx));
			}
		}
		return selected;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.StructuredViewer#internalRefresh(java.lang.Object)
	 */
	protected void internalRefresh(Object element) {
		resetElements(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.StructuredViewer#reveal(java.lang.Object)
	 */
	public void reveal(Object element) {
		int index = elements.indexOf(element);
		revealIndex(index);
	}

	private void revealIndex(int index) {
		revealIndex = index;
		if (index < 0)
			return;
		
		Point location = layout.getPositionForIndex(index);
		Point size = new Point(layout.maxWidth, layout.maxHeight);
		

		/*Item item = items.get(index);
		if (item == null) {
			refreshItems();
			item = items.get(index);
			if (item == null)
				return;
		}*/
		
		Point scrollerOrigin = scrollOrigin;
		Point scrollerSize = composite.getSize();
		if (scrollerSize.x == 0 || scrollerSize.y == 0) {
			// try again later
			return;
		}
		
		// scroll only enough to reveal item; avoid big jumps
		Point extent = new Point(location.x + size.x, location.y + size.y);
		Point scrollerExtent = new Point(scrollerOrigin.x + scrollerSize.x, scrollerOrigin.y + scrollerSize.y);
		if (location.x < scrollerOrigin.x || location.y < scrollerOrigin.y) {
			// move up to the item
			scrollTo(location);
		} else if (extent.x > scrollerExtent.x || extent.y > scrollerExtent.y) {
			// move only far enough down to reveal the hidden parts
			scrollTo(new Point(scrollerOrigin.x + (extent.x - scrollerExtent.x),
					scrollerOrigin.y + (extent.y - scrollerExtent.y)));
		}
		//ensureVisibleItemsValid();
		
		// revealed
		revealIndex = -1;
	}

	/**
	 * @param point
	 */
	private void scrollTo(Point point) {
		scrollOrigin = new Point(0, point.y);
		composite.getVerticalBar().setSelection(point.y);
		layout.setScrollOrigin(scrollOrigin);
		refreshItems();
		//composite.getHorizontalBar().setSelection(point.x);
	}

	private boolean isItemVisible(ThumbnailItem item) {
		if (item == null || item.isDisposed())
			return false;
		Point size = item.getSize();
		if (size.x == 0 || size.y == 0)
			return false;
		Point location = item.getLocation();
		Point extent = new Point(location.x + size.x, location.y + size.y);
		Point scrollerOrigin = scrollOrigin;
		Point scrollerSize = composite.getSize();
		Point scrollerExtent = new Point(scrollerOrigin.x + scrollerSize.x, scrollerOrigin.y + scrollerSize.y);
		if (extent.x < scrollerOrigin.x || extent.y < scrollerOrigin.y) {
			return false;
		} else if (location.x >= scrollerExtent.x || location.y >= scrollerExtent.y) {
			return false;
		} else {
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.StructuredViewer#setSelectionToWidget(java.util.List, boolean)
	 */
	protected void setSelectionToWidget(List l, boolean reveal) {
		for (ThumbnailItem item : items) {
			if (item != null)
				item.setSelected(false);
		}
		
		Set<Integer> newSelectedIndices = new HashSet<Integer>();
		
		// Only reveal if none of the elements are visible.
		// This avoids oscillations when shift-selecting to
		// grow a selection.
		Object revealedElement = null;
		
		if (!l.isEmpty()) {
			for (Object element : l) {
				int index = elements.indexOf(element);
				if (index < 0) {
					UtilsPlugin.log(new IllegalArgumentException(element.toString()));
					continue;
				}

				if (reveal && !selectedIndices.contains(index)) {
					revealedElement = element;
					if (cursorIndex < 0)
						cursorIndex = index;
				}
				
				newSelectedIndices.add(index);
				
				/*
				Item item = items.get(index);
				if (item != null)
					item.setSelected(true);
				*/
			}
		}
		
		selectedIndices = newSelectedIndices;

		refreshItems();
		
		// don't syncSelection() here -- this is called /because/ the selection changed
		if (revealedElement != null) {
			reveal(revealedElement);
		}
	}

	/**
	 * @return
	 */
	boolean isMultiSelect() {
		return (style & SWT.MULTI) != 0;
	}

}
