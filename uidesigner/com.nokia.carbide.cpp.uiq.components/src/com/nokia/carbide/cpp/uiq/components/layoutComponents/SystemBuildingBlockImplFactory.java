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


package com.nokia.carbide.cpp.uiq.components.layoutComponents;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.carbide.cpp.uiq.component.layoutManager.TLayoutData;
import com.nokia.carbide.cpp.uiq.component.layoutManager.TLayoutData.TQikLayoutHorizontalAlignment;
import com.nokia.carbide.cpp.uiq.components.util.UiqUtils;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstanceChildListener;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.datamodel.adapter.IQueryContainment;
import com.nokia.sdt.datamodel.adapter.ISetValueCommandExtender;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.uidesigner.ui.command.DataModelCommandWrapper;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.utils.StatusHolder;
import com.nokia.sdt.utils.drawing.GC;

public class SystemBuildingBlockImplFactory implements IImplFactory {
	
	private static final class SlotIdIndex {
		static final int itemSlot1Index = 0;
		static final int itemSlot2Index = 1;
		static final int iconSlot1Index = 2;
		static final int iconSlot2Index = 3;
	}
	
	private static final String[] SLOT_STRING_IDS = new String[] {
		"EQikItemSlot1",	//$NON-NLS-1$
		"EQikItemSlot2",	//$NON-NLS-1$
		"EQikIconSlot1",	//$NON-NLS-1$
		"EQikIconSlot2"		//$NON-NLS-1$
	};
	
	private static final class TypeId {
		static final int oneline = 0;
		static final int captionedTwoline = 1;
		static final int twoline = 2;
		static final int manylines = 3;
		static final int iconOneline = 4;
		static final int iconCaptionedTwoline = 5;
		static final int iconTwoline = 6;
		static final int onelineIcon = 7;
		static final int iconOnelineIcon = 8;
		static final int iconTwolineIcon = 9;
		static final int mediumThumbnailDoubleOneline = 10;
		static final int largeThumbnailThreeline = 11;
		static final int captionedOneline = 12;
		static final int iconCaptionedOneline = 13;
		static final int twolineIcon = 14;
		static final int iconIconOneline = 15;
		static final int halflineHalfline = 16;
		static final int captionedHalfline = 17;
	}
	
	private static final String[] TYPE_STRING_IDS = new String[] {
		"EQikCtOnelineBuildingBlock",						//$NON-NLS-1$
		"EQikCtCaptionedTwolineBuildingBlock",				//$NON-NLS-1$
		"EQikCtTwolineBuildingBlock",						//$NON-NLS-1$
		"EQikCtManylinesBuildingBlock",						//$NON-NLS-1$
		"EQikCtIconOnelineBuildingBlock",					//$NON-NLS-1$
		"EQikCtIconCaptionedTwolineBuildingBlock",			//$NON-NLS-1$
		"EQikCtIconTwolineBuildingBlock",					//$NON-NLS-1$
		"EQikCtOnelineIconBuildingBlock",					//$NON-NLS-1$
		"EQikCtIconOnelineIconBuildingBlock",				//$NON-NLS-1$
		"EQikCtIconTwolineIconBuildingBlock",				//$NON-NLS-1$
		"EQikCtMediumThumbnailDoubleOnelineBuildingBlock",	//$NON-NLS-1$
		"EQikCtLargeThumbnailThreelineBuildingBlock",		//$NON-NLS-1$
		"EQikCtCaptionedOnelineBuildingBlock",				//$NON-NLS-1$
		"EQikCtIconCaptionedOnelineBuildingBlock",			//$NON-NLS-1$
		"EQikCtTwolineIconBuildingBlock",					//$NON-NLS-1$
		"EQikCtIconIconOnelineBuildingBlock",				//$NON-NLS-1$
		"EQikCtHalflineHalflineBuildingBlock",				//$NON-NLS-1$
		"EQikCtCaptionedHalflineBuildingBlock",				//$NON-NLS-1$
	};
	
	private static final class slotIdMask {
		static final int oneline = 0x1;
		static final int captionedTwoline = 0x3;
		static final int twoline = 0x1;
		static final int manylines = 0x1;
		static final int iconOneline = 0x5;
		static final int iconCaptionedTwoline = 0x7;
		static final int iconTwoline = 0x5;
		static final int onelineIcon = 0x5;
		static final int iconOnelineIcon = 0xD;
		static final int iconTwolineIcon = 0xD;
		static final int mediumThumbnailDoubleOneline = 0x7;
		static final int largeThumbnailThreeline = 0x5;
		static final int captionedOneline = 0x3;
		static final int iconCaptionedOneline = 0x7;
		static final int twolineIcon = 0x5;
		static final int iconIconOneline = 0xD;
		static final int halflineHalfline = 0x3;
		static final int captionedHalfline = 0x3;
	}
	
	private static final class AnchorIds {
		public static final int toRow = 0;
		public static final int toCenter = 1;
		public static final int toTop = 2;
	}
	
	public Object getImpl(EObject componentInstance) {
		return new SystemBuildingBlockImplementations(componentInstance);
	}
	
	class SystemBuildingBlockImplementations implements IQueryContainment,
														ISetValueCommandExtender,
														IComponentInstanceChildListener,
														ILayout,
														IVisualAppearance,
														IComponentInstancePropertyListener {
		
		private static final String SLOT_ID = "com.nokia.carbide.uiq.Slot"; //$NON-NLS-1$
		private static final String ITEMSLOT_ID = "com.nokia.carbide.uiq.ItemSlot"; //$NON-NLS-1$
		private static final String ICONSLOT_ID = "com.nokia.carbide.uiq.IconSlot"; //$NON-NLS-1$
		private static final String TYPE_PROPERTY = "type"; //$NON-NLS-1$
		private static final String SLOTID_PROPERTY = "slotId"; //$NON-NLS-1$
				
		private EObject eobjectCI;
		private IDesignerDataModel designerDataModel;
		private List<String> slotIdsToAdd;
		private LayoutSlot[][] slotsGrid;
		private Point preferredSize;
		private boolean isPreparingSlotActions;
		
		public SystemBuildingBlockImplementations(EObject componentInstance) {
			this.eobjectCI = componentInstance;
			this.slotIdsToAdd = new ArrayList<String>();
			this.preferredSize = new Point(0, 0);
		}

		public EObject getEObject() {
			return eobjectCI;
		}
		
		/***** IQueryContainment *****/
		public boolean canContainChild(IComponentInstance child,
				StatusHolder statusHolder) {
			return canContainComponent(child.getComponent(), statusHolder);
		}

		public boolean canContainComponent(IComponent component,
				StatusHolder statusHolder) {
			if (!ModelUtils.isOfType(component, SLOT_ID)) {
				if (statusHolder != null) {
					statusHolder.setStatus(getContainmentError(component));
					return false;
				}
			}
			if (areTypeAndChildrenConsistent()) {
				if (statusHolder != null) {
					statusHolder.setStatus(getConsistencyError(component));
					return false;
				}
			}
			return true;
		}

		public boolean canRemoveChild(IComponentInstance child) {
			return !isExpectedSlot(child.getEObject());
		}

		public boolean isValidComponentInPalette(IComponent component) {
			return canContainComponent(component, null);
		}

		/***** ISetValueCommandExtender *****/
		public Command getExtendedCommand(String propertyName, Object newValue,
				Command command) {
			if ( propertyName.equals(TYPE_PROPERTY) ) {
				if ( newValue instanceof String && !isPreparingSlotActions ) {
					IComponentInstance ci = ModelUtils.getComponentInstance(eobjectCI);
					EObject[] slots = ci.getChildren();
					slotIdsToAdd.clear();
					List<EObject> slotsToRemove = new ArrayList<EObject>();
					
					resolveSlots((String)newValue, slots, slotsToRemove);
					if (slotsToRemove.isEmpty() && slotIdsToAdd.isEmpty()) {
						isPreparingSlotActions = false;
						return command;
					}
					
					designerDataModel = ci.getDesignerDataModel();
					org.eclipse.emf.common.command.CompoundCommand emfCommand =
						new org.eclipse.emf.common.command.CompoundCommand();
					
					emfCommand.append(
							designerDataModel.createRemoveComponentInstancesCommand(slotsToRemove));
					
					for (String slotId : slotIdsToAdd) {
						IComponent slotComponent = getSlotTypeFromId(slotId);
						EObject slotChild = designerDataModel.createNewComponentInstance(slotComponent);
						emfCommand.append(
								designerDataModel.createAddNewComponentInstanceCommand(eobjectCI, slotChild, IDesignerDataModel.AT_END));
					}
					
					DataModelCommandWrapper slotCommands = new DataModelCommandWrapper();
					slotCommands.setDataModelCommand(emfCommand.unwrap());
					
					isPreparingSlotActions = true;
					return command.chain(slotCommands);
				}
			}
			
			return command;
		}
		
		/***** IComponentInstanceChildListener *****/
		public void childAdded(EObject parent, EObject child) {
			IComponentInstance childCI = ModelUtils.getComponentInstance(child);
			IComponent childComponent = childCI.getComponent();
			IPropertySource childPS = ModelUtils.getPropertySource(child);
			if ( childComponent.getId().equals(ITEMSLOT_ID) ) {
				if ( slotIdsToAdd.contains(SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index]) ) {
					childPS.setPropertyValue(SLOTID_PROPERTY, SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index]);
					slotIdsToAdd.remove(SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index]);
				} else if ( slotIdsToAdd.contains(SLOT_STRING_IDS[SlotIdIndex.itemSlot2Index]) ) {
					childPS.setPropertyValue(SLOTID_PROPERTY, SLOT_STRING_IDS[SlotIdIndex.itemSlot2Index]);
					slotIdsToAdd.remove(SLOT_STRING_IDS[SlotIdIndex.itemSlot2Index]);
				} else {
					
				}
			} else if ( childComponent.getId().equals(ICONSLOT_ID) ) {
				if ( slotIdsToAdd.contains(SLOT_STRING_IDS[SlotIdIndex.iconSlot1Index]) ) {
					childPS.setPropertyValue(SLOTID_PROPERTY, SLOT_STRING_IDS[SlotIdIndex.iconSlot1Index]);
					slotIdsToAdd.remove(SLOT_STRING_IDS[SlotIdIndex.iconSlot1Index]);
				} else if ( slotIdsToAdd.contains(SLOT_STRING_IDS[SlotIdIndex.iconSlot2Index]) ) {
					childPS.setPropertyValue(SLOTID_PROPERTY, SLOT_STRING_IDS[SlotIdIndex.iconSlot2Index]);
					slotIdsToAdd.remove(SLOT_STRING_IDS[SlotIdIndex.iconSlot2Index]);
				} else {
					
				}
			} else {
				
			}
			forceLayout();
			isPreparingSlotActions = false;
		}

		public void childRemoved(EObject parent, EObject child) {
			forceLayout();
			isPreparingSlotActions = false;
		}

		public void childrenReordered(EObject parent) {
			
		}
		
		/***** ILayout *****/
		public void layout(ILookAndFeel laf) {
			int typeId = getTypeId();
			if (!areTypeAndChildrenConsistent(typeId)) {
				return;
			}
			setSlotsGrid(typeId);
			distributeSlots(typeId);
			layoutSlotsOverColumns(typeId, laf);
		}
		
		/***** IVisualAppearance *****/
		public void draw(GC gc, ILookAndFeel laf) {
			
		}
		
		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			int typeId = getTypeId();
			if (!areTypeAndChildrenConsistent(typeId)) {
				return new Point(wHint, hHint);
			}
//			layout(laf);
			return preferredSize;
		}
		
		/***** IComponentInstancePropertyListener *****/
		public void propertyChanged(EObject componentInstance, Object propertyId) {
			if ((propertyId instanceof String) && ((String)propertyId).equals("collateralLayout")) { //$NON-NLS-1$
				forceLayout();
			}
		}
		
		private int getTypeId(String type) {
			List<String> typeStringIds = Arrays.asList(TYPE_STRING_IDS);
			return typeStringIds.indexOf(type);
		}
		
		private int getTypeId() {
			int typeId = -1;
			String type;
			IPropertySource ps = ModelUtils.getPropertySource(eobjectCI);
			if (ps != null) {
				Object psPV = ps.getPropertyValue(TYPE_PROPERTY);
				if (psPV != null && psPV instanceof String) {
					type = (String)psPV;
					typeId = getTypeId(type);
				}
			}
			return typeId;
		}
		
		private boolean isExpectedSlot(EObject slotEObj, int slotMask) {
			List<String> slotIdList = Arrays.asList(SLOT_STRING_IDS);
			String slotIdValue = getSlotIdFromEObject(slotEObj);
			int slotIdMaskedPosition = slotIdList.indexOf(slotIdValue);
			return ((int)Math.pow(2, slotIdMaskedPosition) & slotMask) != 0; 
		}
		
		private boolean isExpectedSlot(EObject slotEObj) {
			int typeId = getTypeId();
			int slotMask = getSlotMask(typeId);
			return isExpectedSlot(slotEObj, slotMask);
		}
		
		private void resolveSlots(
				String type,
				EObject[] slots,
				List<EObject> slotsToRemove) {
			List<String> slotIdList = Arrays.asList(SLOT_STRING_IDS);
			List<String> alreadyExistingSlotIds = new ArrayList<String>();
			
			int slotMask = getSlotMask(getTypeId(type));
			
			for ( EObject slot : slots ) {
				if ( !isExpectedSlot(slot, slotMask) ) {
					slotsToRemove.add(slot);
				}
				else {
					String slotIdValue = getSlotIdFromEObject(slot);
					alreadyExistingSlotIds.add(slotIdValue);
				}
			}
			
			for ( int slotIdMaskedPosition = 0; slotIdMaskedPosition < slotIdList.size(); slotIdMaskedPosition++ ) {
				if ( ((int)Math.pow(2, slotIdMaskedPosition) & slotMask) != 0 ) {
					slotIdsToAdd.add(slotIdList.get(slotIdMaskedPosition));
				}
			}
			
			//Finally, remove those that already exist
			slotIdsToAdd.removeAll(alreadyExistingSlotIds);
		}
		
		private String getSlotIdFromEObject(EObject slot) {
			Object slotPVO = ModelUtils.getEditablePropertyValue(slot, SLOTID_PROPERTY);
			if (slotPVO != null && slotPVO instanceof String) {
				return (String)slotPVO;
			}
			return null;
		}
		
		private IComponent getSlotTypeFromId(String slotId) {
			IComponentSet componentSet = designerDataModel.getComponentSet();
			if ( slotId.equals(SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index]) || slotId.equals(SLOT_STRING_IDS[SlotIdIndex.itemSlot2Index]) )
				return componentSet.lookupComponent(ITEMSLOT_ID);
			if ( slotId.equals(SLOT_STRING_IDS[SlotIdIndex.iconSlot1Index]) || slotId.equals(SLOT_STRING_IDS[SlotIdIndex.iconSlot2Index]) )
				return componentSet.lookupComponent(ICONSLOT_ID);
			return null;
		}
		
		private int getSlotMask(int typeId) {
			int slotMask = 0;
			
			switch (typeId) {
			case TypeId.oneline:
			case TypeId.twoline:
			case TypeId.manylines:
				slotMask = slotIdMask.oneline;
				break;
			case TypeId.captionedTwoline:
			case TypeId.captionedOneline:
			case TypeId.halflineHalfline:
			case TypeId.captionedHalfline:
				slotMask = slotIdMask.captionedTwoline;
				break;
			case TypeId.iconOneline:
			case TypeId.iconTwoline:
			case TypeId.onelineIcon:
			case TypeId.largeThumbnailThreeline:
			case TypeId.twolineIcon:
				slotMask = slotIdMask.iconOneline;
				break;
			case TypeId.iconCaptionedTwoline:
			case TypeId.mediumThumbnailDoubleOneline:
			case TypeId.iconCaptionedOneline:
				slotMask = slotIdMask.iconCaptionedTwoline;
				break;
			case TypeId.iconOnelineIcon:
			case TypeId.iconTwolineIcon:
			case TypeId.iconIconOneline:
				slotMask = slotIdMask.iconOnelineIcon;
				break;
			default:
				break;
			}
			
			return slotMask;
		}
		
		private boolean areTypeAndChildrenConsistent(int typeId) {
			int slotMask = getSlotMask(typeId);
			List<String> expectedSlots = getExpectedSlots(slotMask);
			
			IComponentInstance ci = ModelUtils.getComponentInstance(eobjectCI);
			EObject[] children = ci.getChildren();
			List<String> currentSlots = new ArrayList<String>();
			for (EObject child : children) {
				String slotId = getSlotIdFromEObject(child);
				currentSlots.add(slotId);
			}
			
			if (expectedSlots.size() == currentSlots.size() && expectedSlots.containsAll(currentSlots)) {
				return true;
			}
			
			return false;
		}
		
		private boolean areTypeAndChildrenConsistent() {
			return areTypeAndChildrenConsistent(getTypeId());
		}
		
		private List<String> getExpectedSlots(int slotMask) {
			List<String> expectedSlots = new ArrayList<String>();
			List<String> slotIdList = Arrays.asList(SLOT_STRING_IDS);
			for ( int slotIdMaskedPosition = 0; slotIdMaskedPosition < slotIdList.size(); slotIdMaskedPosition++ ) {
				if ( ((int)Math.pow(2, slotIdMaskedPosition) & slotMask) != 0 ) {
					expectedSlots.add(slotIdList.get(slotIdMaskedPosition));
				}
			}
			return expectedSlots;
		}
		
		private void setSlotsGrid(int typeId) {
			slotsGrid = null;
			switch ( typeId ) {
			case TypeId.oneline:
			case TypeId.twoline:
			case TypeId.manylines:
				slotsGrid = new LayoutSlot[1][1];
				break;
			case TypeId.captionedTwoline:
			case TypeId.captionedOneline:
				slotsGrid = new LayoutSlot[2][1];
				break;
			case TypeId.iconOneline:
			case TypeId.iconTwoline:
			case TypeId.onelineIcon:
			case TypeId.largeThumbnailThreeline:
			case TypeId.twolineIcon:
			case TypeId.halflineHalfline:
			case TypeId.captionedHalfline:
				slotsGrid = new LayoutSlot[1][2];
				break;
			case TypeId.iconCaptionedTwoline:
			case TypeId.mediumThumbnailDoubleOneline:
			case TypeId.iconCaptionedOneline:
				slotsGrid = new LayoutSlot[2][2];
				break;
			case TypeId.iconOnelineIcon:
			case TypeId.iconTwolineIcon:
			case TypeId.iconIconOneline:
				slotsGrid = new LayoutSlot[1][3];
				break;
			default:
				break;
			}
		}
		
		private void distributeSlots(int typeId) {
			List<String> slotSequence = new ArrayList<String>();
			
			switch (typeId) {
			case TypeId.oneline:
			case TypeId.twoline: {
				String[] sequence = { SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index] };
				slotSequence = Arrays.asList(sequence);
				break;
			}
			case TypeId.manylines: {
				String[] sequence = { SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index] };
				slotSequence = Arrays.asList(sequence);
				break;
			}
			case TypeId.captionedTwoline:
			case TypeId.captionedOneline: {
				String[] sequence = {
						SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index],
						SLOT_STRING_IDS[SlotIdIndex.itemSlot2Index] };
				slotSequence = Arrays.asList(sequence);
				break;
			}
			case TypeId.onelineIcon:
			case TypeId.twolineIcon: {
				String[] sequence = {
						SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index],
						SLOT_STRING_IDS[SlotIdIndex.iconSlot1Index] };
				slotSequence.addAll(Arrays.asList(sequence));
				break;
			}
			case TypeId.iconOneline:
			case TypeId.iconTwoline: {
				String[] sequence = {
						SLOT_STRING_IDS[SlotIdIndex.iconSlot1Index],
						SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index] };
				slotSequence.addAll(Arrays.asList(sequence));
				break;
			}
			case TypeId.largeThumbnailThreeline: {
				String[] sequence = {
						SLOT_STRING_IDS[SlotIdIndex.iconSlot1Index],
						SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index] };
				slotSequence.addAll(Arrays.asList(sequence));
				break;
			}
			case TypeId.halflineHalfline:
			case TypeId.captionedHalfline: {
				String[] sequence = {
						SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index],
						SLOT_STRING_IDS[SlotIdIndex.itemSlot2Index] };
				slotSequence = Arrays.asList(sequence);
				break;
			}
			case TypeId.iconCaptionedTwoline:
			case TypeId.iconCaptionedOneline: {
				String[] sequence = {
						SLOT_STRING_IDS[SlotIdIndex.iconSlot1Index],
						SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index],
						"", //$NON-NLS-1$
						SLOT_STRING_IDS[SlotIdIndex.itemSlot2Index] };
				slotSequence = Arrays.asList(sequence);
				break;
			}
			case TypeId.mediumThumbnailDoubleOneline: {
				String[] sequence = {
						SLOT_STRING_IDS[SlotIdIndex.iconSlot1Index],
						SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index],
						"", //$NON-NLS-1$
						SLOT_STRING_IDS[SlotIdIndex.itemSlot2Index] };
				slotSequence = Arrays.asList(sequence);
				break;
			}
			case TypeId.iconOnelineIcon:
			case TypeId.iconTwolineIcon: {
				String[] sequence = {
						SLOT_STRING_IDS[SlotIdIndex.iconSlot1Index],
						SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index],
						SLOT_STRING_IDS[SlotIdIndex.iconSlot2Index]};
				slotSequence = Arrays.asList(sequence);
				break;
			}
			case TypeId.iconIconOneline: {
				String[] sequence = {
						SLOT_STRING_IDS[SlotIdIndex.iconSlot1Index],
						SLOT_STRING_IDS[SlotIdIndex.iconSlot2Index],
						SLOT_STRING_IDS[SlotIdIndex.itemSlot1Index]};
				slotSequence = Arrays.asList(sequence);
				break;
			}
			default:
				break;
			}
			
			int k = 0;
			for (int i = 0; i < slotsGrid.length; i++) {
				for (int j = 0; j < slotsGrid[i].length; j++) {
					LayoutSlot layoutSlot = new LayoutSlot();
					EObject slot = findInmediateSlotById(slotSequence.get(k++));
					layoutSlot.setSlot(slot);
					layoutSlot.setAnchorId(AnchorIds.toRow);
					slotsGrid[i][j] = layoutSlot;
				}
			}
		}
		
		private EObject findInmediateSlotById(String slotId) {
			IComponentInstance ci = ModelUtils.getComponentInstance(eobjectCI);
			EObject[] children = ci.getChildren();
			for (EObject child : children) {
				String id = getSlotIdFromEObject(child);
				if (id.equals(slotId)) {
					return child;
				}
			}
			return null;
		}
		
		private void layoutSlotsOverColumns(int typeId, ILookAndFeel laf) {
			int numberOfRows = slotsGrid.length;
			int numberOfColumns = slotsGrid[0].length;
			Point[][] slotsSizes = new Point[ numberOfRows ][ numberOfColumns ];
			int gap = 4;	//TODO: get from LAF?
			Point hintSize = getHintSize(laf);
			int numberOfItemColumns = numberOfColumns;
			int widthConsumedByIcons = 0;
			Point slotElementPositionAnchoredToCenter = null;
			TLayoutData layoutData = new TLayoutData();
			layoutData.obtainData(getEObject());
			//get preferred sizes of slots
			for (int i = 0; i < numberOfRows; i++) {
				//get icon slots preferred sizes from LAF
				for (int j = 0; j < numberOfColumns; j++) {
					LayoutSlot layoutSlot = slotsGrid[i][j];
					if (ModelUtils.isInstanceOf(layoutSlot.getSlot(), ICONSLOT_ID)) {
						slotsSizes[i][j] = layoutSlot.getPreferredSize(-1, -1, laf);
						widthConsumedByIcons += slotsSizes[i][j].x;
						layoutSlot.setFilledWidth(slotsSizes[i][j].x);
						layoutSlot.setNumberOfRowsToFillIn(getNumberOfRowsToFillIn(slotsSizes[i][j].y, laf));
						layoutSlot.setAnchorId(getAnchorId(typeId));
						if (layoutSlot.getAnchorId() == AnchorIds.toCenter) {
							slotElementPositionAnchoredToCenter = new Point(i, j);
						}
						numberOfItemColumns--;
					}
				}
				//get item slots preferred sizes from what is left by icon slots
				int wHintForItemSlots = (hintSize.x - widthConsumedByIcons - (numberOfColumns + 1) * gap) / numberOfItemColumns;
				for (int j = 0; j < numberOfColumns; j++) {
					LayoutSlot layoutSlot = slotsGrid[i][j];
					if (ModelUtils.isInstanceOf(layoutSlot.getSlot(), ITEMSLOT_ID)) {
						Point slotSize = layoutSlot.getPreferredSize(wHintForItemSlots, hintSize.y, laf);
						layoutSlot.setFilledWidth(wHintForItemSlots);
						if (slotSize != null) {
							layoutSlot.setNumberOfRowsToFillIn(getNumberOfRowsToFillIn(slotSize.y, laf));
						}
						slotsSizes[i][j] = slotSize;
					}
				}
			}
			//get heights in number of rows to fill in
			int[] maxNumberOfRowsToFillIn = new int[numberOfRows];
			for (int i = 0; i < numberOfRows; i++) {
				maxNumberOfRowsToFillIn[i] = 0;
				for (int j = 0; j < numberOfColumns; j++) {
					//ignore in case of slot anchored to center
					if (slotElementPositionAnchoredToCenter != null && 
							new Point(i, j).equals(slotElementPositionAnchoredToCenter)) {
						continue;
					}
					LayoutSlot layoutSlot = slotsGrid[i][j];
					if (layoutSlot != null) {
						maxNumberOfRowsToFillIn[i] = Math.max(maxNumberOfRowsToFillIn[i], layoutSlot.getNumberOfRowsToFillIn());
					}
				}
			}
			//get max accumulated number of rows to fill in per column
			//and also get column widths
			int heightInRows = 0;
			int[] columnWidths = new int[numberOfColumns];
			for (int j = 0; j < numberOfColumns; j++) {
				int accumulatedNumberOfRows = 0;
				columnWidths[j] = 0;
				for (int i = 0; i < numberOfRows; i++) {
					accumulatedNumberOfRows += slotsGrid[i][j].getNumberOfRowsToFillIn();
					slotsGrid[i][j].setNumberOfRowsToFillIn(maxNumberOfRowsToFillIn[i]);
					Point slotSize = slotsSizes[i][j];
					if (slotSize != null) {
						if (layoutData.horizontalAlignment == TQikLayoutHorizontalAlignment.EQikLayoutHAlignFill ||
								layoutData.horizontalAlignment == TQikLayoutHorizontalAlignment.EQikLayoutHAlignInherit) {
							columnWidths[j] = slotsGrid[i][j].getFilledWidth();
						} else {
							columnWidths[j] = Math.max(columnWidths[j], slotSize.x);
						}
					}
				}
				heightInRows = Math.max(heightInRows, accumulatedNumberOfRows);
			}
			//layout slots within columns
			int xC = gap;
			for (int j = 0; j < numberOfColumns; j++) {
				int vertOffset = 0;
				for (int i = 0; i < numberOfRows; i++) {
					LayoutSlot layoutSlot = slotsGrid[i][j];
					int yS = vertOffset;
					Point slotSize = slotsSizes[i][j];
					if (slotSize != null) {
//						int xOffset = (columnWidths[j] - slotSize.x) / 2;
						int yOffset = getYOffset(layoutSlot, hintSize.y, slotSize.y, gap, heightInRows);
//						layoutSlot.getSlotLO().setBounds(new Rectangle(xC + xOffset, yS + yOffset, slotSize.x, slotSize.y));
						layoutSlot.getSlotLO().setBounds(new Rectangle(xC, yS + yOffset, columnWidths[j], slotSize.y));
					}
					vertOffset += layoutSlot.getNumberOfRowsToFillIn() * hintSize.y;
				}
				xC += columnWidths[j] + gap;
			}
			preferredSize.x = xC;
			preferredSize.y = heightInRows * hintSize.y;
		}

		private Point getHintSize(ILookAndFeel laf) {
			IComponentInstance ci = ModelUtils.getComponentInstance(getEObject());
			EObject parentObject = ci.getParent();
			ILayoutObject parentLO = ModelUtils.getLayoutObject(parentObject);
			Rectangle parentBounds = parentLO.getBounds();
			return new Point(parentBounds.width, UiqUtils.getRowHeight(getEObject(), laf));
		}
		
		private int getNumberOfRowsToFillIn(int height, ILookAndFeel laf) {
			if (height < 1) {
				return 0;
			}
			int rowHeight = UiqUtils.getRowHeight(getEObject(), laf);
			int heightRatio = height / rowHeight;
			int heightMod = height % rowHeight;
			int numberOfRows = 0;
			if ( heightRatio > 0 ) {
				numberOfRows = ( heightMod > 0 ) ? ++heightRatio : heightRatio;
			} else {
				numberOfRows = 1;
			}
			return numberOfRows;
		}
		
		private int getAnchorId(int typeId) {
			switch (typeId) {
			case TypeId.iconOneline:
			case TypeId.iconCaptionedTwoline:
			case TypeId.onelineIcon:
			case TypeId.iconOnelineIcon:
			case TypeId.largeThumbnailThreeline:
			case TypeId.iconCaptionedOneline:
			case TypeId.iconIconOneline:
				return AnchorIds.toRow;
			case TypeId.iconTwoline:
			case TypeId.iconTwolineIcon:
			case TypeId.twolineIcon:
				return AnchorIds.toTop;
			case TypeId.mediumThumbnailDoubleOneline:
				return AnchorIds.toCenter;
			}
			return AnchorIds.toRow;
		}
		
		private int getYOffset(LayoutSlot layoutSlot, int rowLayoutHeight, int slotHeight, int gap, int heightInRows) {
			int yOffset = 0;
			switch (layoutSlot.getAnchorId()) {
			default:
			case AnchorIds.toRow:
				yOffset = (layoutSlot.getNumberOfRowsToFillIn() * rowLayoutHeight - slotHeight) / 2;
				break;
			case AnchorIds.toTop:
				yOffset = gap;
				break;
			case AnchorIds.toCenter:
				yOffset = (heightInRows * rowLayoutHeight - slotHeight) / 2;
				break;
			}
			return yOffset;
		}
		
		private void forceLayout() {
			ILayoutContainer lc = ModelUtils.getLayoutContainer(eobjectCI);
			if (lc != null) {
				lc.layoutChildren();
			}
		}
		
		private IStatus getContainmentError(IComponent component) {
			return Logging.newStatus(null, IStatus.ERROR,
					MessageFormat.format(Messages.getString("SystemBuildingBlockImplFactory.ContainmentError"), //$NON-NLS-1$
							new Object[] { component.getFriendlyName() }));
		}
		
		private IStatus getConsistencyError(IComponent component) {
			return Logging.newStatus(null, IStatus.ERROR,
					MessageFormat.format(Messages.getString("SystemBuildingBlockImplFactory.ConsistencyError"), //$NON-NLS-1$
							new Object[] { component.getFriendlyName() }));
		}
	}
	
}
