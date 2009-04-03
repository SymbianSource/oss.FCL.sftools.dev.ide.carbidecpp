/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.uiq.components;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.carbide.cpp.uiq.components.controlCollection.ControlCollectionAdapterFactory.CreationTool;
import com.nokia.carbide.cpp.uiq.components.util.UiqUtils;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IChildCommandExtender;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IInitializer;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.datamodel.adapter.IQueryContainment;
import com.nokia.sdt.datamodel.adapter.IScrollBoundsProvider;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.utils.StatusHolder;
import com.nokia.sdt.utils.drawing.GC;

public class CQikListBoxImplFactory implements IImplFactory {
	
	public Object getImpl(EObject componentInstance) {
		return new ListBoxImplementations(componentInstance);
	}
	
	class ListBoxImplementations implements IChildCommandExtender,
												IQueryContainment,
												IInitializer,
												IVisualAppearance,
												ILayout,
												IScrollBoundsProvider {
		
		private static final String LAYOUT_CONTROL_BASE = "com.nokia.carbide.uiq.LayoutControlBase"; //$NON-NLS-1$
		private static final String LISTBOX_TYPE = "com.nokia.carbide.uiq.CQikListBox"; //$NON-NLS-1$
		private static final String LISTBOX_LAYOUT_GROUP_TYPE = "com.nokia.carbide.uiq.ListBoxLayoutGroup"; //$NON-NLS-1$
		private static final String LISTBOX_LAYOUT_TYPE = "com.nokia.carbide.uiq.ListBoxLayout"; //$NON-NLS-1$
		private static final String LISTBOX_ITEM_TYPE = "com.nokia.carbide.uiq.ListBoxItem"; //$NON-NLS-1$
		private static final String GROUP_CHILD_TYPE_ATTR = "group-child-type"; //$NON-NLS-1$
		private static final String HEIGHT_IN_ROWS_PROPERTY = "height_in_rows"; //$NON-NLS-1$
		private static final String TYPE_PROPERTY = "type"; //$NON-NLS-1$
		private static final String GRID_ROWS_PROPERTY = "grid_rows"; //$NON-NLS-1$
		private static final String GRID_COLUMNS_PROPERTY = "grid_columns"; //$NON-NLS-1$
		private static final String SCROLL_DIRECTION_PROPERTY = "scroll_direction"; //$NON-NLS-1$
		private static final String ROWLISTBOX = "EQikRowListBox"; //$NON-NLS-1$
		private static final String GRIDLISTBOX = "EQikGridListBox"; //$NON-NLS-1$
		private static final String VERTICALDIRECTION = "EQikVerticalDirection"; //$NON-NLS-1$
		private static final String HORIZONTALDIRECTION = "EQikHorizontalDirection"; //$NON-NLS-1$
		
		private EObject eobjectCI;
		private IDesignerDataModel ddm;
		
		public ListBoxImplementations(EObject eobjectCI) {
			this.eobjectCI = eobjectCI;
			this.ddm = ModelUtils.getModel(eobjectCI);
			Check.checkState(ddm != null);
		}
		
		public EObject getEObject() {
			return eobjectCI;
		}
		
		/********** IInitializer **********/
		public void initialize(boolean isConfigured) {
			if (isConfigured) {
				return;
			}
			IComponentSet cs = ddm.getComponentSet();
			IComponent layoutGroupComponent = cs.lookupComponent(LISTBOX_LAYOUT_GROUP_TYPE);
			Check.checkState(layoutGroupComponent != null);
			EObject layoutGroup = ddm.createNewComponentInstance(layoutGroupComponent);
			Check.checkState(layoutGroup != null);
			Command command = ddm.createAddNewComponentInstanceCommand(getEObject(), layoutGroup, IDesignerDataModel.AT_END);
			if (command.canExecute()) {
				command.execute();
			}
		}
		
		/********** IChildCommandExtender **********/
		public Command getExtendedAddNewComponentInstanceCommand(EObject owner,
				Collection<EObject> children, int insertionPosition,
				Command command) {
			IComponentInstance ownerComponentInstance = ModelUtils.getComponentInstance(owner);
			if (ownerComponentInstance.getComponentId().equals(LISTBOX_TYPE)) {
				return command;
			}
			// check to see if we have any listbox layouts group and/or listbox item control items to handle
			boolean hasListBoxLayoutGroupItems = false;
			boolean hasListBoxItems = false;
			for (EObject child : children) {
				if (isListBoxLayoutGroupItem(child))
					hasListBoxLayoutGroupItems = true;
				if (isListBoxItem(child))
					hasListBoxItems = true;
			}
			// if so, create a compound command to handle each separately
			if (hasListBoxLayoutGroupItems) {
				CompoundCommand compoundCommand = new CompoundCommand(command.getLabel());
				for (EObject child : children) {
					if(isListBoxLayoutGroupItem(child)){
						IPropertySource listboxReferencePropertySource = ModelUtils.getPropertySource(owner);
						String listboxName = listboxReferencePropertySource.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME).toString();
						IComponentInstance listbox = ModelUtils.lookupReference(ModelUtils.getModel(owner), listboxName);
						if (listbox != null) {
							EObject parent = listbox.getEObject();
							Command addCommand = 
								ddm.createAddNewComponentInstanceCommand(parent, child, insertionPosition);
							compoundCommand.append(addCommand);
						}
					}
				}
				return compoundCommand.unwrap();
			}
			if (hasListBoxItems) {
				CompoundCommand compoundCommand = new CompoundCommand(command.getLabel());
				for (EObject child : children) {
					if(isListBoxItem(child)){
						IPropertySource listboxReferencePropertySource = ModelUtils.getPropertySource(owner);
						String listboxName = listboxReferencePropertySource.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME).toString();
						IComponentInstance listbox = ModelUtils.lookupReference(ModelUtils.getModel(owner), listboxName);
						EObject parent = listbox.getEObject();
						Command addCommand = 
							ddm.createAddNewComponentInstanceCommand(parent, child, insertionPosition);
						compoundCommand.append(addCommand);
					}
				}
				return compoundCommand.unwrap();
			}
			
			return command;
		}

		public Command getExtendedMoveComponentInstanceCommand(
				EObject targetObject, EObject newOwner, int insertionPosition,
				Command command) {
			return command;
		}

		public Command getExtendedRemoveComponentInstancesCommand(
				List<EObject> objectsToRemove, Command command) {
			return command;
		}
		
		/********** IQueryContainment **********/
		public boolean canContainChild(IComponentInstance child,
				StatusHolder statusHolder) {
			return canContainComponent(child.getComponent(), statusHolder);
		}

		public boolean canContainComponent(IComponent component,
				StatusHolder statusHolder) {
			if (!( ModelUtils.isOfType(component, LISTBOX_ITEM_TYPE) || ModelUtils.isOfType(component, LISTBOX_LAYOUT_GROUP_TYPE) )) {
				if (statusHolder != null) {
					statusHolder.setStatus(getContainmentError(component));
				}
				return false;
			}
			if (ModelUtils.isOfType(component, LISTBOX_LAYOUT_GROUP_TYPE)) {
				if (doesLayoutGroupAlreadyExist()) {
					if (statusHolder != null) {
						statusHolder.setStatus(getLayoutGroupContainmentError(component));
					}
					return false;
				}
			}
			return true;
		}

		public boolean canRemoveChild(IComponentInstance child) {
			if (ModelUtils.isInstanceOf(child.getEObject(), LISTBOX_LAYOUT_GROUP_TYPE)) {
				return false;
			}
			return true;
		}

		public boolean isValidComponentInPalette(IComponent component) {
			return canContainComponent(component, null);
		}
		
		/********** IVisualAppearance **********/
		public void draw(GC gc, ILookAndFeel laf) {
		}

		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			Object heightInRowsObj = ModelUtils.getEditablePropertyValue(getEObject(), HEIGHT_IN_ROWS_PROPERTY);
			if (heightInRowsObj != null) {
				int heightInRows = Integer.parseInt(heightInRowsObj.toString());
				if (heightInRows == -1) {
					heightInRows = 2;
				}
				if (heightInRows > 0) {
					return new Point(wHint, heightInRows * UiqUtils.getRowHeight(getEObject(), laf));
				}
			}
			return null;
		}

		/********** ILayout **********/
		public void layout(ILookAndFeel laf) {
			Object listboxTypeObj = ModelUtils.getEditablePropertyValue(getEObject(), TYPE_PROPERTY);
			if (listboxTypeObj == null) {
				return;
			}
			int lbxRows = getDimmensionValue(GRID_ROWS_PROPERTY);
			int lbxColumns = getDimmensionValue(GRID_COLUMNS_PROPERTY);
			String scrollDirection = VERTICALDIRECTION;
			List<EObject> items = Utilities.getLayoutChildren(getEObject());	//ListboxLayoutGroup is a non-layout-object
			if (listboxTypeObj != null && listboxTypeObj.toString().equals(ROWLISTBOX)) {
				lbxRows = items.size();
				lbxColumns = 1;
			} else {
				if (listboxTypeObj != null && listboxTypeObj.toString().equals(GRIDLISTBOX)) {
					if (lbxRows < 1) {
						lbxRows = 3;	//TODO: get from LAF?
					}
					if (lbxColumns < 1) {
						lbxColumns = 3;	//TODO: get from LAF?
					}
					Object scrollDirectionObj = ModelUtils.getEditablePropertyValue(getEObject(), SCROLL_DIRECTION_PROPERTY);
					if (scrollDirectionObj != null) {
						scrollDirection = scrollDirectionObj.toString();
					}
				}
			}
			
			int gridRows = lbxRows, gridColumns = lbxColumns;
			if (scrollDirection.equals(VERTICALDIRECTION)) {
				gridRows = items.size() / lbxColumns;
				if (items.size() % lbxColumns > 0) {
					gridRows++;
				}
			} else {
				if (scrollDirection.equals(HORIZONTALDIRECTION)) {
					gridColumns = items.size() / lbxRows;
					if (items.size() % lbxRows > 0) {
						gridColumns++;
					}
				}
			}
			
			Point[][] sizesInGrid = new Point[ gridRows ][ gridColumns ];
			//Initialize array
			for (int i = 0; i < gridRows; i++) {
				for (int j = 0; j < gridColumns; j++) {
					sizesInGrid[i][j] = new Point(0, 0);
				}
			}
			//Get preferred children sizes
			Point prefSize = getPreferredSize(UiqUtils.getCQikContainerBoundsFromLaf(getEObject(), laf).width, -1, laf);
			int wI = prefSize.x / lbxColumns;
			int hI = 0;
			if (lbxRows != 0) {
				hI = prefSize.y / lbxRows;
			}
			
			int i = 0, j = 0;
			Point childPrefSize = null;
			if (scrollDirection.equals(VERTICALDIRECTION)) {
				for (int k = 0; k < items.size(); k++) {
					EObject child = items.get(k);
					childPrefSize = getChildPreferredSize(child, wI, hI, laf);
					if (childPrefSize != null) {
						sizesInGrid[i][j] = childPrefSize;
					}
					if (++j > lbxColumns - 1) {
						j = 0;
						i++;
					}
				}
			} else {
				if (scrollDirection.equals(HORIZONTALDIRECTION)) {
					for (int k = 0; k < items.size(); k++) {
						EObject child = items.get(k);
						childPrefSize = getChildPreferredSize(child, wI, hI, laf);
						if (childPrefSize != null) {
							sizesInGrid[i][j] = childPrefSize;
						}
						if (++i > gridRows - 1) {
							i = 0;
							j++;
						}
					}
				}
			}
			//Get heights
			int rowHeights[] = new int [ gridRows ];
			for (i = 0; i < gridRows; i++) {
				int maxHeightInRow = 0;
				for (j = 0; j < gridColumns; j++) {
					maxHeightInRow = Math.max(maxHeightInRow, sizesInGrid[i][j].y);
				}
				rowHeights[i] = maxHeightInRow;
			}
			//Get widths
			int columnWidths[] = new int [ gridColumns ];
			for (j = 0; j < gridColumns; j++) {
				int maxWidthInColumn = 0;
				for (i = 0; i < gridRows; i++) {
					maxWidthInColumn = Math.max(maxWidthInColumn, sizesInGrid[i][j].x);
				}
				columnWidths[j] = maxWidthInColumn;
			}
			//Layout items
			if (scrollDirection.equals(VERTICALDIRECTION)) {
				layoutItemsVertically(items, sizesInGrid, columnWidths, rowHeights, gridColumns);
			} else {
				if (scrollDirection.equals(HORIZONTALDIRECTION)) {
					layoutItemsHorizontally(items, sizesInGrid, columnWidths, rowHeights, gridRows);
				}
			}
		}
		
		/********** IScrollBoundsProvider **********/
		public Rectangle getScrollBounds(ILookAndFeel laf) {
			return null;
		}

		private boolean isListBoxLayoutGroupItem(EObject eobject) {
			String componentId = ddm.getComponentId(eobject);
			if (componentId != null) {
				IComponent component = ddm.getComponentSet().lookupComponent(componentId);
				return ModelUtils.isOfType(component, LISTBOX_LAYOUT_GROUP_TYPE);
			}
			return false;
		}

		private boolean isListBoxItem(EObject eobject) {
			String componentId = ddm.getComponentId(eobject);
			if (componentId != null) {
				IComponent component = ddm.getComponentSet().lookupComponent(componentId);
				return ModelUtils.isOfType(component, LISTBOX_ITEM_TYPE);
			}
			return false;
		}
		
		private boolean doesLayoutGroupAlreadyExist() {
			EObject listbox = getEObject();
			if (ModelUtils.isInstanceOf(listbox, LAYOUT_CONTROL_BASE)) {
				IPropertySource listboxReferencePS = ModelUtils.getPropertySource(listbox);
				String listboxName = listboxReferencePS.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME).toString();
				IComponentInstance listboxCI = ModelUtils.lookupReference(ModelUtils.getModel(listbox), listboxName);
				if (listboxCI == null) {
					return false;
				}
				listbox = listboxCI.getEObject();
			}
			EObject layoutGroup = ModelUtils.findImmediateChildWithAttributeValue(listbox, GROUP_CHILD_TYPE_ATTR, LISTBOX_LAYOUT_TYPE);
			if (layoutGroup != null) {
				return true;
			}
			return false;
		}
		
		private IStatus getLayoutGroupContainmentError(IComponent component) {
			IComponentInstance ci = ModelUtils.getComponentInstance(getEObject());
			return Logging.newStatus(null, IStatus.ERROR, MessageFormat.format(
					Messages.getString("CQikListBoxImplFactory.LayoutGroupContainmentError"), //$NON-NLS-1$
					ci.getComponent().getFriendlyName(), component.getFriendlyName()));
		}
		
		private IStatus getContainmentError(IComponent component) {
			IComponentInstance ci = ModelUtils.getComponentInstance(getEObject());
			return Logging.newStatus(null, IStatus.ERROR, MessageFormat.format(
					Messages.getString("CQikListBoxImplFactory.ContainmentError"), //$NON-NLS-1$
					ci.getComponent().getFriendlyName(), component.getFriendlyName()));
		}
		
		private int getDimmensionValue(String dimmension) {
			int dimmensionValue = -1;
			Object dimmensionObj = ModelUtils.getEditablePropertyValue(getEObject(), dimmension);
			if (dimmensionObj != null) {
				dimmensionValue = Integer.parseInt(dimmensionObj.toString());
			}
			return dimmensionValue;
		}
		
		private Point getChildPreferredSize(EObject child, int wHint, int hHint, ILookAndFeel laf) {
			IVisualAppearance visual = (IVisualAppearance) EcoreUtil.getRegisteredAdapter(child, IVisualAppearance.class);
	    	if (visual != null)
	    		return visual.getPreferredSize(wHint, hHint, laf);
	    	
			return null;
		}
		
		private void layoutItemsVertically(List<EObject> items, Point[][] sizesInGrid, int columnWidths[], int rowHeights[], int gridColumns) {
			int i = 0, j = 0;
			int xI = 0, yI = 0, wI, hI;
			int offsetX = 0, offsetY = 0;
			for (int k = 0; k < items.size(); k++) {
				EObject child = items.get(k);
				xI += offsetX;
				yI = offsetY;
				wI = sizesInGrid[i][j].x;
				hI = sizesInGrid[i][j].y;
				ILayoutObject childLO = ModelUtils.getLayoutObject(child);
				childLO.setBounds(new Rectangle(xI, yI, wI, hI));
				offsetX = columnWidths[j];
				if (++j > gridColumns - 1) {
					offsetX = 0;
					xI = 0;
					offsetY = yI + rowHeights[i];
					j = 0;
					i++;
				}
			}
		}
		
		private void layoutItemsHorizontally(List<EObject> items, Point[][] sizesInGrid, int columnWidths[], int rowHeights[], int gridRows) {
			int i = 0, j = 0;
			int xI = 0, yI = 0, wI, hI;
			int offsetX = 0, offsetY = 0;
			for (int k = 0; k < items.size(); k++) {
				EObject child = items.get(k);
				xI = offsetX;
				yI += offsetY;
				wI = sizesInGrid[i][j].x;
				hI = sizesInGrid[i][j].y;
				ILayoutObject childLO = ModelUtils.getLayoutObject(child);
				childLO.setBounds(new Rectangle(xI, yI, wI, hI));
				offsetY = rowHeights[i];
				if (++i > gridRows - 1) {
					offsetY = 0;
					yI = 0;
					offsetX = xI + columnWidths[j];
					i = 0;
					j++;
				}
			}
		}
	}
}
