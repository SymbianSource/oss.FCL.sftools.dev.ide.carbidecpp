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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.ui.texteditor.ITextEditor;

import com.ibm.icu.text.MessageFormat;
import com.nokia.carbide.search.system.internal.ui.Messages;
import com.nokia.carbide.search.system.internal.ui.SearchMessages;
import com.nokia.carbide.search.system.ui.IContextMenuConstants;
import com.nokia.carbide.search.system.ui.ISearchResultViewPart;
import com.nokia.carbide.search.system.ui.text.AbstractTextSearchResult;
import com.nokia.carbide.search.system.ui.text.AbstractTextSearchViewPage;
import com.nokia.carbide.search.system.ui.text.Match;
import com.nokia.carbide.search.system2.internal.ui.OpenSearchPreferencesAction;


public class FileSearchPage extends AbstractTextSearchViewPage implements IAdaptable {
	
	private static final String KEY_SORTING= "com.nokia.carbide.search.system.resultpage.sorting"; //$NON-NLS-1$
	private static final String KEY_LIMIT= "com.nokia.carbide.search.system.resultpage.limit"; //$NON-NLS-1$
	
	private static final int DEFAULT_ELEMENT_LIMIT = 1000;

	private ActionGroup fActionGroup;
	private IFileSearchContentProvider fContentProvider;
	private int fCurrentSortOrder;
	private SortAction fSortByNameAction;
	private SortAction fSortByPathAction;
	
	private EditorOpener fEditorOpener= new EditorOpener();

		
	private static final String[] SHOW_IN_TARGETS= new String[] { IPageLayout.ID_RES_NAV };
	private  static final IShowInTargetList SHOW_IN_TARGET_LIST= new IShowInTargetList() {
		public String[] getShowInTargetIds() {
			return SHOW_IN_TARGETS;
		}
	};

	public FileSearchPage() {
		fSortByNameAction= new SortAction(SearchMessages.FileSearchPage_sort_name_label, this, FileLabelProvider.SHOW_LABEL_PATH); 
		fSortByPathAction= new SortAction(SearchMessages.FileSearchPage_sort_path_label, this, FileLabelProvider.SHOW_PATH_LABEL); 

		setElementLimit(new Integer(DEFAULT_ELEMENT_LIMIT));
	}
	
	public void setElementLimit(Integer elementLimit) {
		super.setElementLimit(elementLimit);
		int limit= elementLimit.intValue();
		getSettings().put(KEY_LIMIT, limit);
	}	
	
	public StructuredViewer getViewer() {
		return super.getViewer();
	}
	
	private void addDragAdapters(StructuredViewer viewer) {
		Transfer[] transfers= new Transfer[] { ResourceTransfer.getInstance() };
		int ops= DND.DROP_COPY | DND.DROP_LINK;
		viewer.addDragSupport(ops, transfers, new ResourceTransferDragAdapter(viewer));
	}	

	protected void configureTableViewer(TableViewer viewer) {
		viewer.setUseHashlookup(true);
		FileLabelProvider innerLabelProvider= new FileLabelProvider(this, fCurrentSortOrder);
		viewer.setLabelProvider(new SystemLabelProvider(this));
		viewer.setContentProvider(new FileTableContentProvider(this));
//		viewer.setComparator(new DecoratorIgnoringViewerSorter(innerLabelProvider));
		viewer.setSorter(new TableSorter());
		addColumns(viewer);
		fContentProvider= (IFileSearchContentProvider) viewer.getContentProvider();
		addDragAdapters(viewer);
	}
	
	private static final int COLUMN_NAME = 0;
	private static final int COLUMN_TYPE = 1;
	private static final int COLUMN_SIZE = 2;
	private static final int COLUMN_DATE = 3;
	private static final int COLUMN_PATH = 4;

	private class TableSorter extends ViewerSorter {

		// sort direction
		private boolean sortAscending;
		
		//last column sorted
		private int column = -1;
		
		/* 
		 * decide on which column to sort by, and the sort ordering
		 */
		public void doSort(int column) {
			// ignore the column passed in and use the id set by the column selection handler
			if (column == this.column) {
				// sort in other order
				sortAscending = !sortAscending;
			} else {
				switch (column) {
					case COLUMN_NAME:
					case COLUMN_TYPE:
					case COLUMN_PATH:
					{
		            	// sort in ascending order
		            	sortAscending = true;
		                break;
					}
					case COLUMN_SIZE:
					case COLUMN_DATE:
					{
		            	// sort in descending order
		            	sortAscending = false;
		                break;
					}
					default:
					{
						// ignore the column
						return;
					}
				}
				this.column = column;
			}
		}
		
		/*
		 * compare two items from a table column
		 */
		public int compare(Viewer viewer, Object e1, Object e2) {
			int comparison = 0;
			
			if (!(e1 instanceof IFileStore) || !(e2 instanceof IFileStore))
				return 0;
			
			IFileStore r1 = (IFileStore) e1;
			IFileStore r2 = (IFileStore) e2;

			switch (column) {
				case COLUMN_NAME:
				{
					comparison = r1.getName().compareTo(r2.getName());
					break;
				}
				case COLUMN_TYPE:
				{
					int position;
					String type1 = r1.getName();
					String type2 = r2.getName();
					
					position = type1.lastIndexOf('.');
					if (position >= 0)
						type1 = type1.substring(position + 1);
					
					position = type2.lastIndexOf('.');
					if (position >= 0)
						type2 = type2.substring(position + 1);

					comparison = type1.compareToIgnoreCase(type2);
					break;
				}
				case COLUMN_SIZE:
				{
					long lng = r1.fetchInfo().getLength() - r2.fetchInfo().getLength();
					comparison = lng < 0 ? -1 : (lng == 0 ? 0 : 1) ;
					break;
				}
				case COLUMN_DATE:
				{
					long lng = r1.fetchInfo().getLastModified() - r2.fetchInfo().getLastModified(); 
					comparison = lng < 0 ? -1 : (lng == 0 ? 0 : 1) ;
					break;
				}
				case COLUMN_PATH:
				{
					comparison = r1.toString().compareTo(r2.toString());
					break;
				}
				default:
				{
					break;
				}
			}

			// for descending order, reverse the sense of the compare
			if (!sortAscending)
				comparison = -comparison;
			return comparison;
		}
		
		public boolean getSortAscending() {
			return sortAscending;
		}
	}
	
	private class SystemLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(SearchMessages.FileSearchPage_dateFormat);
		
		AbstractTextSearchViewPage page;

		public SystemLabelProvider(AbstractTextSearchViewPage page) {
			this.page = page;
		}


		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			
			if (!(element instanceof IFileStore))
				return ""; //$NON-NLS-1$

			IFileStore fileStore = (IFileStore) element;
			Table table = ((TableViewer) getViewer()).getTable();
			
			int column = (Integer) table.getColumn(columnIndex).getData();
			
			switch (column) {
				case COLUMN_NAME:
				{
					String text = fileStore.getName();
					
					int matchCount = 0;
					AbstractTextSearchResult result= page.getInput();
					if (result != null)
						matchCount= result.getMatchCount(element);
					if (matchCount <= 1)
						return text;
					String format= SearchMessages.FileLabelProvider_count_format; 
					return MessageFormat.format(format, new Object[] { text, new Integer(matchCount) });
				}
				case COLUMN_TYPE:
				{
					int position;
					String type = fileStore.getName();
					
					position = type.lastIndexOf('.');
					if (position >= 0)
						type = type.substring(position + 1);
					return type.toLowerCase();
				}
				case COLUMN_SIZE:
				{
					return fileStore.fetchInfo().getLength() + ""; //$NON-NLS-1$
				}
				case COLUMN_DATE:
				{
					Date date = new Date(fileStore.fetchInfo().getLastModified());
					return dateFormat.format(date);
				}
				case COLUMN_PATH:
				{
					return fileStore.toString();
				}
				default:
				{
					break;
				}
			}
			
			// should never get here
			return ""; //$NON-NLS-1$
		}
		
	}
	
	private class ColumnSelectionHandler extends SelectionAdapter
	{
		TableViewer viewer;
		int column;

		ColumnSelectionHandler(TableViewer viewer, int column) {
			this.viewer = viewer;
			this.column = column;
		}
		
		public void widgetSelected(SelectionEvent event) {
			final TableSorter sorter = (TableSorter) viewer.getSorter();
			
			BusyIndicator.showWhile(event.display, new Runnable() {
				public void run() {
					sorter.doSort(column);
				}
			});
			viewer.getTable().setSortColumn((TableColumn)event.getSource());
			viewer.getTable().setSortDirection(sorter.getSortAscending() ? SWT.UP : SWT.DOWN);
			viewer.refresh();
			viewer.getTable().redraw();
		}
	}

	protected void addColumns(TableViewer viewer) {
		TableColumn column;
		Table table = viewer.getTable();
		
		// name
		column = new TableColumn(table, SWT.LEFT);
		column.setText(SearchMessages.FileSearchPage_name);
		column.setWidth(135);
		column.setMoveable(true);
		column.setResizable(true);
		column.setData(new Integer(COLUMN_NAME));
		column.addSelectionListener(new ColumnSelectionHandler(viewer, COLUMN_NAME));

		// type
		column = new TableColumn(table, SWT.LEFT);
		column.setText(SearchMessages.FileSearchPage_type);
		column.setWidth(66);
		column.setMoveable(true);
		column.setResizable(true);
		column.setData(new Integer(COLUMN_TYPE));
		column.addSelectionListener(new ColumnSelectionHandler(viewer, COLUMN_TYPE));

		// size
		column = new TableColumn(table, SWT.RIGHT);
		column.setText(SearchMessages.FileSearchPage_size);
		column.setWidth(60);
		column.setMoveable(true);
		column.setResizable(true);
		column.setData(new Integer(COLUMN_SIZE));
		column.addSelectionListener(new ColumnSelectionHandler(viewer, COLUMN_SIZE));

		// date modified
		column = new TableColumn(table, SWT.LEFT);
		column.setText(SearchMessages.FileSearchPage_dateModified);
		column.setWidth(120);
		column.setMoveable(true);
		column.setResizable(true);
		column.setData(new Integer(COLUMN_DATE));
		column.addSelectionListener(new ColumnSelectionHandler(viewer, COLUMN_DATE));

		// path
		column = new TableColumn(table, SWT.LEFT);
		column.setText(SearchMessages.FileSearchPage_Path);
		column.setWidth(200);
		column.setMoveable(true);
		column.setResizable(true);
		column.setData(new Integer(COLUMN_PATH));
		column.addSelectionListener(new ColumnSelectionHandler(viewer, COLUMN_PATH));
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(new FillLayout(SWT.HORIZONTAL));
		table.setRedraw(true);		
	}

	protected void configureTreeViewer(TreeViewer viewer) {
		viewer.setUseHashlookup(true);
		FileLabelProvider innerLabelProvider= new FileLabelProvider(this, FileLabelProvider.SHOW_LABEL);
		viewer.setLabelProvider(new DecoratingLabelProvider(innerLabelProvider, PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator()));
		viewer.setContentProvider(new FileTreeContentProvider(this, viewer));
//		viewer.setComparator(new DecoratorIgnoringViewerSorter(innerLabelProvider));
		fContentProvider= (IFileSearchContentProvider) viewer.getContentProvider();
		addDragAdapters(viewer);
	}

	protected void showMatch(Match match, int offset, int length, boolean activate) throws PartInitException {
		IFileStore file= (IFileStore) match.getElement();
		IEditorPart editor= fEditorOpener.open(file, activate);
		if (offset >= 0 && length != 0) {
			if (editor instanceof ITextEditor) {
				ITextEditor textEditor= (ITextEditor) editor;
				textEditor.selectAndReveal(offset, length);
			} else if (editor instanceof IGotoMarker) {
				IGotoMarker mark = (IGotoMarker) editor;

				FileStoreMarker marker = new FileStoreMarker();
				HashMap<String,Object> attributes= new HashMap<String,Object>(4);
				attributes.put(IMarker.CHAR_START, new Integer(offset));
				attributes.put(IMarker.CHAR_END, new Integer(offset + length));

				try {
					marker.setAttributes(attributes);
				} catch (CoreException e) {
				}
				
				mark.gotoMarker(marker);
			} else if (editor != null) {
				showWithMarker(editor, file, offset, length);
			}
			this.setFocus();
		}
	}
	
	private void showWithMarker(IEditorPart editor, IFileStore file, int offset, int length) throws PartInitException {
		FileStoreMarker marker = new FileStoreMarker();
		HashMap<String,Object> attributes= new HashMap<String,Object>(4);
		attributes.put(IMarker.CHAR_START, new Integer(offset));
		attributes.put(IMarker.CHAR_END, new Integer(offset + length));

		try {
			marker.setAttributes(attributes);
			IDE.gotoMarker(editor, marker);
		} catch (CoreException e) {
			throw new PartInitException(SearchMessages.FileSearchPage_error_marker, e); 
		}
	}
	
	// marker that is not tied to a workspace resource
	private class FileStoreMarker extends PlatformObject implements IMarker {
		
		HashMap<String,Object> attributes = new HashMap<String,Object>();

		public void delete() throws CoreException {
		}

		public boolean exists() {
			return true;
		}

		public Object getAttribute(String attributeName) throws CoreException {
			Assert.isNotNull(attributeName);
			return attributes.get(attributeName);
		}

		public int getAttribute(String attributeName, int defaultInt) {
			Assert.isNotNull(attributeName);

			Object attribute = attributes.get(attributeName);
			if (attribute != null && attribute instanceof Integer)
				return (Integer)attribute;
			else
				return defaultInt;
		}

		public String getAttribute(String attributeName, String defaultString) {
			Assert.isNotNull(attributeName);

			Object attribute = attributes.get(attributeName);
			if (attribute != null && attribute instanceof String)
				return (String)attribute;
			else
				return defaultString;
		}

		public boolean getAttribute(String attributeName, boolean defaultBoolean) {
			Assert.isNotNull(attributeName);

			Object attribute = attributes.get(attributeName);
			if (attribute != null && attribute instanceof Boolean)
				return (Boolean)attribute;
			else
				return defaultBoolean;
		}

		public Map getAttributes() throws CoreException {
			return attributes;
		}

		public Object[] getAttributes(String[] attributeNames) throws CoreException {
			Assert.isNotNull(attributeNames);

			Object[] array = new Object[attributeNames.length];
			for (int i = 0; i < attributeNames.length; i++)
				array[i] = attributes.get(attributeNames[i]);
			return array;
		}

		public long getCreationTime() throws CoreException {
			return 0;
		}

		public long getId() {
			return 0;
		}

		public IResource getResource() {
			return null;
		}

		public String getType() throws CoreException {
			return TEXT;
		}

		public boolean isSubtypeOf(String typeName) throws CoreException {
			return (typeName != null) && typeName.equals(TEXT);
		}

		public void setAttribute(String attributeName, int intValue) throws CoreException {
			Assert.isNotNull(attributeName);
			attributes.put(attributeName, new Integer(intValue));
		}

		public void setAttribute(String attributeName, Object value) throws CoreException {
			Assert.isNotNull(attributeName);
			attributes.put(attributeName, value);
		}

		public void setAttribute(String attributeName, boolean booleanValue)	throws CoreException {
			Assert.isNotNull(attributeName);
			attributes.put(attributeName, new Boolean(booleanValue));			
		}

		public void setAttributes(Map map) throws CoreException {
			Assert.isNotNull(map);
			attributes.putAll(map);
		}

		public void setAttributes(String[] attributeNames, Object[] values) throws CoreException {
			Assert.isNotNull(attributeNames);
			Assert.isNotNull(values);
			
			for (int i = 0; i < attributeNames.length; i++) {
				if (attributeNames[i] == null)
					continue;
				
				if (i < values.length)
					attributes.put(attributeNames[i], values[i]);
				else
					attributes.put(attributeNames[i], null);
			}
		}

		@Override
		public Object getAdapter(Class arg0) {
			return null;
		}	
	}

	protected void fillContextMenu(IMenuManager mgr) {
		super.fillContextMenu(mgr);
		fActionGroup.setContext(new ActionContext(getSite().getSelectionProvider().getSelection()));
		fActionGroup.fillContextMenu(mgr);
		FileSearchQuery query= (FileSearchQuery) getInput().getQuery();
//		if (!"".equals(query.getSearchString())) { //$NON-NLS-1$
//		ReplaceAction2 replaceAction= new ReplaceAction2(this, (IStructuredSelection) getViewer().getSelection());
//		if (replaceAction.isEnabled())
//			mgr.appendToGroup(IContextMenuConstants.GROUP_REORGANIZE, replaceAction);
//				
//		ReplaceAction2 replaceAll= new ReplaceAction2(this);
//		if (replaceAll.isEnabled())
//			mgr.appendToGroup(IContextMenuConstants.GROUP_REORGANIZE, replaceAll);
	}
	
	public void setViewPart(ISearchResultViewPart part) {
		super.setViewPart(part);
		fActionGroup= new NewTextSearchActionGroup(part);
	}
	
	public void init(IPageSite site) {
		super.init(site);
		IMenuManager menuManager = site.getActionBars().getMenuManager();
		menuManager.appendToGroup(IContextMenuConstants.GROUP_PROPERTIES, new OpenSearchPreferencesAction());
	}
	
	public void dispose() {
		fActionGroup.dispose();
		super.dispose();
	}

	protected void elementsChanged(Object[] objects) {
		if (fContentProvider != null)
			fContentProvider.elementsChanged(objects);
	}

	protected void clear() {
		if (fContentProvider != null)
			fContentProvider.clear();
	}

	public void setSortOrder(int sortOrder) {
		fCurrentSortOrder= sortOrder;
		DecoratingLabelProvider lpWrapper= (DecoratingLabelProvider) getViewer().getLabelProvider();
		((FileLabelProvider) lpWrapper.getLabelProvider()).setOrder(sortOrder);
		getViewer().refresh();
		getSettings().put(KEY_SORTING, fCurrentSortOrder);
	}
	
	public void restoreState(IMemento memento) {
		super.restoreState(memento);
		try {
			fCurrentSortOrder= getSettings().getInt(KEY_SORTING);
		} catch (NumberFormatException e) {
			fCurrentSortOrder= fSortByNameAction.getSortOrder();
		}
		int elementLimit= DEFAULT_ELEMENT_LIMIT;
		try {
			elementLimit= getSettings().getInt(KEY_LIMIT);
		} catch (NumberFormatException e) {
		}
		if (memento != null) {
			Integer value= memento.getInteger(KEY_SORTING);
			if (value != null)
				fCurrentSortOrder= value.intValue();
			
			value= memento.getInteger(KEY_LIMIT);
			if (value != null)
				elementLimit= value.intValue();
		}
		setElementLimit(new Integer(elementLimit));
	}
	public void saveState(IMemento memento) {
		super.saveState(memento);
		memento.putInteger(KEY_SORTING, fCurrentSortOrder);
		memento.putInteger(KEY_LIMIT, getElementLimit().intValue());
	}
	
	public Object getAdapter(Class adapter) {
		if (IShowInTargetList.class.equals(adapter)) {
			return SHOW_IN_TARGET_LIST;
		}
		return null;
	}
	
	public String getLabel() {
		String label= super.getLabel();
		StructuredViewer viewer= getViewer();
		if (viewer instanceof TableViewer) {
			TableViewer tv= (TableViewer) viewer;

			AbstractTextSearchResult result= getInput();
			if (result != null) {
				int itemCount= ((IStructuredContentProvider) tv.getContentProvider()).getElements(getInput()).length;
				int fileCount= getInput().getElements().length;
				if (itemCount < fileCount) {
					String format= SearchMessages.FileSearchPage_limited_format; 
					return Messages.format(format, new Object[]{label, new Integer(itemCount), new Integer(fileCount)});
				}
			}
		}
		return label;
	}

}
