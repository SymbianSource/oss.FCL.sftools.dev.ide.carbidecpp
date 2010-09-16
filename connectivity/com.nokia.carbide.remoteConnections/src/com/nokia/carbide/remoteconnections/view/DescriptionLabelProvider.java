package com.nokia.carbide.remoteconnections.view;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus;
import com.nokia.carbide.remoteconnections.internal.ui.ConnectionUIUtils;
import com.nokia.carbide.remoteconnections.settings.ui.SettingsWizard;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class DescriptionLabelProvider extends StyledCellLabelProvider {

	private class Element {
		private String text;

		public Element(String text) {
			this.text = text;
		}
		
		public String getText() {
			return text;
		}
	}
	
	private class LinkElement extends Element {
		private String href;
		
		public LinkElement(String href, String text) {
			super(text);
			this.href = href;
		}
		
		public String getHref() {
			return href;
		}
	}
	
	private static final Pattern HREF_PATTERN = 
		Pattern.compile("<a href=\"([^\"]*)\">(.*?)</a>", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static final String AGENT_INSTALLERS_URL = "about:agentInstallers"; //$NON-NLS-1$

	private final ConnectionsView connectionsView;
	private Styler hyperLinkStyler;
	private TreeViewerColumn treeViewerColumn;
	private Listener mouseListener;

	public DescriptionLabelProvider(ConnectionsView connectionsView, TreeViewerColumn treeViewerColumn) {
		this.connectionsView = connectionsView;
		this.treeViewerColumn = treeViewerColumn;
		hookColumn();
		hyperLinkStyler = new Styler() {
			@Override
			public void applyStyles(TextStyle textStyle) {
				textStyle.foreground = ConnectionUIUtils.COLOR_HYPERLINK;
				textStyle.underline = true;
			}
		};
	}
	
	private void hookColumn() {
		final Control control = treeViewerColumn.getViewer().getControl();
		final Cursor originalCursor = control.getCursor();
		final Cursor handCursor = treeViewerColumn.getColumn().getDisplay().getSystemCursor(SWT.CURSOR_HAND);
		mouseListener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
					case SWT.MouseEnter:
					case SWT.MouseMove:
						if (getLinkHRef(event) != null) {
							control.setCursor(handCursor);
						}
						else {
							control.setCursor(originalCursor);
						}
						break;
					case SWT.MouseExit:
						control.setCursor(originalCursor);
						break;
					case SWT.MouseDown:
						String linkHRef = getLinkHRef(event);
						if (linkHRef != null)
							handleLinkClicked(linkHRef, event);
						break;
					default:
				}
			}

			private String getLinkHRef(Event event) {
				Point pt = new Point(50, event.y);
				ViewerCell cell = getViewer().getCell(pt);
				if (cell == null)
					return null;
				TreeItem item = (TreeItem) cell.getItem();
				String text = getText(cell.getElement());
				List<Element> elements = parseText(text);
				if (elements.isEmpty())
					return null;
				int locMouseX = event.x - item.getTextBounds(ConnectionsView.DESCRIPTION_COLUMN_INDEX).x;
				GC gc = new GC(item.getDisplay());
				try {
					gc.setFont(item.getFont());
					int elementStartX = 0;
					int elementEndX = 0;
					for (Element element : elements) {
						elementEndX += gc.stringExtent(element.getText()).x;
						if (element instanceof LinkElement) {
							if (locMouseX >= elementStartX && locMouseX < elementEndX) {
								LinkElement linkElement = (LinkElement) element;
								return linkElement.getHref();
							}
						}
						elementStartX = elementEndX + 1;
					}
				}
				finally {
					gc.dispose();
				}
				return null;
			}
			
			private void handleLinkClicked(final String linkHRef, final Event event) {
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					public void run() {
						if (linkHRef.equalsIgnoreCase(AGENT_INSTALLERS_URL)) {
							Point pt = new Point(50, event.y);
							ViewerCell cell = getViewer().getCell(pt);
							if (cell == null)
								return;
							TreeItem treeItem = findParentTreeItem((TreeItem) cell.getItem());
							TreeNode node = (TreeNode) treeItem.getData();
							final Object value = node.getValue();
							if (value instanceof IConnection) {
								Display.getDefault().asyncExec(new Runnable() {
									public void run() {
										SettingsWizard wizard = new SettingsWizard((IConnection) value);
										wizard.setSelectedTabInSettingsPage(2);
										wizard.open(connectionsView.getViewSite().getShell());
									}
								});
							}
						}
						else {
							try {
								URL url = new URL(linkHRef);
								WorkbenchUtils.showWebPageInExternalBrowser(url.toExternalForm());
							} catch (MalformedURLException e) {
							}
						}
					}
				});
			}

			private TreeItem findParentTreeItem(TreeItem item) {
				TreeItem parentItem = item.getParentItem();
				while (parentItem != null) {
					item = parentItem;
					parentItem = item.getParentItem();
				}
				return item;
			}

		};
		Widget widget = treeViewerColumn.getViewer().getControl();
		widget.addListener(SWT.MouseEnter, mouseListener);
		widget.addListener(SWT.MouseMove, mouseListener);
		widget.addListener(SWT.MouseExit, mouseListener);
		widget.addListener(SWT.MouseDown, mouseListener);
	}
	
	private void unhookColumn() {
		Widget widget = treeViewerColumn.getViewer().getControl();
		if (widget == null || widget.isDisposed())
			return;
		widget.removeListener(SWT.MouseEnter, mouseListener);
		widget.removeListener(SWT.MouseMove, mouseListener);
		widget.removeListener(SWT.MouseExit, mouseListener);
		widget.removeListener(SWT.MouseDown, mouseListener);
	}

	@Override
	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		String text = getText(element);
		List<Element> elements = parseText(text);
		
		StyledString styledString = getStyledString(elements);
		cell.setText(styledString.toString());
		cell.setStyleRanges(styledString.getStyleRanges());

		// we need this to avoid letting the bold name column influence the others 
		cell.setFont(JFaceResources.getDefaultFont());

		super.update(cell);
	}
	
	private List<Element> parseText(String text) {
		List<Element> elements = new ArrayList<Element>();
		if (text != null) {
			Matcher m = HREF_PATTERN.matcher(text);
			int start = 0;
			int end;
			while (m.find()) {
				end = m.start();
				if (start <= end)
					elements.add(new Element(text.substring(start, end)));
				elements.add(new LinkElement(m.group(1), m.group(2)));
				start = m.end();
			}
			end = text.length();
			if (start <= end)
				elements.add(new Element(text.substring(start, end)));
		}
		return elements;
	}

	private StyledString getStyledString(List<Element> elements) {
		StyledString styledString = new StyledString();
		for (Element element : elements) {
			styledString.append(element.getText(), element instanceof LinkElement ? hyperLinkStyler : null);
		}
		return styledString;
	}

	private String getText(Object obj) {
		TreeNode node = (TreeNode) obj;
		Object value = node.getValue();
		if (value instanceof IConnectedService) {
			IStatus status = ((IConnectedService) value).getStatus();
			IConnection connection = ConnectionsView.findConnection((IConnectedService) value);
			if (!status.getEStatus().equals(EStatus.IN_USE) ||
					!(connection != null && ConnectionUIUtils.isSomeServiceInUse(connection))) { // if in-use, we show it in the connection row
				String longDescription = status.getLongDescription();
				if (longDescription != null)
					longDescription = TextUtils.canonicalizeNewlines(longDescription, " "); //$NON-NLS-1$
				return longDescription;
			}
		}
		else if (value instanceof IConnection) {
			IConnectionStatus status = this.connectionsView.getConnectionStatus((IConnection) value);
			if (status != null) {
				return status.getLongDescription();
			}
			else if (ConnectionUIUtils.isSomeServiceInUse((IConnection) value)) {
				return Messages.getString("DescriptionLabelProvider_InUseDesc"); //$NON-NLS-1$
			}
		}
		
		return null;
	}
	
	@Override
	public void dispose() {
		unhookColumn();
		super.dispose();
	}
}