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
/**
 * Utilities for UIQ components
 * 
 */
package com.nokia.carbide.cpp.uiq.components.util;

import java.util.Hashtable;

import com.nokia.carbide.cpp.uiq.components.controlCollection.ControlCollectionAdapterFactory.CreationTool;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor.Visitor;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

public abstract class UiqUtils {
	
	private static final String VIEW_LAYOUT_ID = "com.nokia.carbide.uiq.ViewLayout"; //$NON-NLS-1$
	private static final String VIEW_COMPONENT_ID = "com.nokia.carbide.uiq.CQikView"; //$NON-NLS-1$
	private static final String CQIKCONTAINER_ID = "com.nokia.carbide.uiq.CQikContainer"; //$NON-NLS-1$
	private static final String LAYOUT_CONTROL_TYPE = "com.nokia.carbide.uiq.LayoutControlBase";	//$NON-NLS-1$
	private static final String CONTROL_COLLECTION_TYPE = "com.nokia.carbide.uiq.ControlCollection"; //$NON-NLS-1$
	private static final String CONTROL_COLLECTION_ITEM_BASE_TYPE = "com.nokia.carbide.uiq.ControlCollectionItemBase";	//$NON-NLS-1$
	private static final String ISQIKCONTAINER_ATTRIBUTE = "is-qikcontainer";	//$NON-NLS-1$
	private static final String ISSYSTEMBUILDINGBLOCK_ATTRIBUTE = "is-system-building-block";	//$NON-NLS-1$
	private static final String CONTROL_PROPERTY = "control";	//$NON-NLS-1$
	private static final String ROWLAYOUTMANAGER_VIEW_ROWHEIGHT = "RowLayoutManager.View.rowHeight"; //$NON-NLS-1$
	private static final String ROWLAYOUTMANAGER_DIALOG_ROWHEIGHT = "RowLayoutManager.Dialog.rowHeight"; //$NON-NLS-1$
	
	private static final Hashtable<String, Integer> sbbTypes = new Hashtable<String, Integer>();
	
	public static final class SbbTypeIds {
		public static final int oneline = 0;
		public static final int captionedTwoline = 1;
		public static final int twoline = 2;
		public static final int manylines = 3;
		public static final int iconOneline = 4;
		public static final int iconCaptionedTwoline = 5;
		public static final int iconTwoline = 6;
		public static final int onelineIcon = 7;
		public static final int iconOnelineIcon = 8;
		public static final int iconTwolineIcon = 9;
		public static final int mediumThumbnailDoubleOneline = 10;
		public static final int largeThumbnailThreeline = 11;
		public static final int captionedOneline = 12;
		public static final int iconCaptionedOneline = 13;
		public static final int twolineIcon = 14;
		public static final int iconIconOneline = 15;
		public static final int halflineHalfline = 16;
		public static final int captionedHalfline = 17;
	}
	
	public static EObject findTopQikContainer(EObject eobject) {
		EObject topQikContainer = null;
		IComponentInstance rootCI = ModelUtils.getComponentInstance(eobject);
		do {
			if (ModelUtils.booleanAttribute(rootCI.getComponent(), ISQIKCONTAINER_ATTRIBUTE)) {
				topQikContainer = rootCI.getEObject();
			}
			EObject parent = rootCI.getParent();
			rootCI = ModelUtils.getComponentInstance(parent);
		} while (rootCI != null);
        return topQikContainer;
	}
	
	public static EObject findViewLayoutOrContainer(EObject eobject) {
		EObject layoutOrContainer = null;
		EObject group = eobject;
		boolean foundGroup = false;
		do {
			String groupChildType = ModelUtils.readAttribute(group, "group-child-type");
			if (groupChildType != null && (
					groupChildType.equals(VIEW_LAYOUT_ID) ||
					groupChildType.equals(CQIKCONTAINER_ID))) {
				foundGroup = true;
			} else {
				layoutOrContainer = group;
			}
			IComponentInstance layoutOrContainerCI = ModelUtils.getComponentInstance(layoutOrContainer);
			if (layoutOrContainerCI != null) {
				group = layoutOrContainerCI.getParent();
			}
		} while (group != null && !foundGroup);
        return foundGroup ? layoutOrContainer : null;
	}
	
	public static boolean canGroupElementContainComponent(EObject startingNode, IComponent component) {
		EObject layoutOrContainer = findViewLayoutOrContainer(startingNode);
		//Multi and Single Page View, and View Dialog case
		if (layoutOrContainer != null && ModelUtils.isInstanceOf(layoutOrContainer, VIEW_LAYOUT_ID)) {
			IComponentInstance layoutOrContainerCI = ModelUtils.getComponentInstance(layoutOrContainer);
			if (layoutOrContainerCI != null) {
				EObject[] viewPages = layoutOrContainerCI.getChildren();
				for (EObject viewPage : viewPages) {
					IComponentInstance viewPageCI = ModelUtils.getComponentInstance(viewPage);
					if (viewPageCI != null) {
						EObject[] qikContainers = viewPageCI.getChildren();
						for (EObject qikContainer : qikContainers) {
							if (!canQikContainerContainComponent(qikContainer, component)) {
								return false;
							}
						}
					}
				}
			}
		} else {
			//Simple Dialog case
			if (layoutOrContainer != null && !canQikContainerContainComponent(layoutOrContainer, component)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean canGroupElementContainChild(EObject startingNode, IComponentInstance childCI) {
		EObject layoutOrContainer = findViewLayoutOrContainer(startingNode);
		//Multi and Single Page View, and View Dialog case
		if (layoutOrContainer != null && ModelUtils.isInstanceOf(layoutOrContainer, VIEW_LAYOUT_ID)) {
			IComponentInstance layoutOrContainerCI = ModelUtils.getComponentInstance(layoutOrContainer);
			if (layoutOrContainerCI != null) {
				EObject[] viewPages = layoutOrContainerCI.getChildren();
				for (EObject viewPage : viewPages) {
					IComponentInstance viewPageCI = ModelUtils.getComponentInstance(viewPage);
					if (viewPageCI != null) {
						EObject[] qikContainers = viewPageCI.getChildren();
						for (EObject qikContainer : qikContainers) {
							if (!canQikContainerContainChild(qikContainer, childCI)) {
								return false;
							}
						}
					}
				}
			}
		} else {
			//Simple Dialog case
			if (layoutOrContainer != null && !canQikContainerContainChild(layoutOrContainer, childCI)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean canGroupElementContainQikContainer(EObject startingNode, EObject qikContainerUnderTest) {
		EObject layoutOrContainer = findViewLayoutOrContainer(startingNode);
		//Multi and Single Page View, and View Dialog case
		if (layoutOrContainer != null && ModelUtils.isInstanceOf(layoutOrContainer, VIEW_LAYOUT_ID)) {
			IComponentInstance layoutOrContainerCI = ModelUtils.getComponentInstance(layoutOrContainer);
			if (layoutOrContainerCI != null) {
				EObject[] viewPages = layoutOrContainerCI.getChildren();
				for (EObject viewPage : viewPages) {
					IComponentInstance viewPageCI = ModelUtils.getComponentInstance(viewPage);
					if (viewPageCI != null) {
						EObject[] qikContainers = viewPageCI.getChildren();
						for (EObject qikContainer : qikContainers) {
							if (!canQikContainerContainQikContainer(qikContainer, qikContainerUnderTest)) {
								return false;
							}
						}
					}
				}
			}
		} else {
			//Simple Dialog case
			if (layoutOrContainer != null && !canQikContainerContainQikContainer(layoutOrContainer, qikContainerUnderTest)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean canGroupElementContainSBB(EObject startingNode, EObject sbbUnderTest) {
		EObject layoutOrContainer = findViewLayoutOrContainer(startingNode);
		if (layoutOrContainer != null && ModelUtils.isInstanceOf(layoutOrContainer, VIEW_LAYOUT_ID)) {
			IComponentInstance layoutOrContainerCI = ModelUtils.getComponentInstance(layoutOrContainer);
			if (layoutOrContainerCI != null) {
				EObject[] viewPages = layoutOrContainerCI.getChildren();
				for (EObject viewPage : viewPages) {
					IComponentInstance viewPageCI = ModelUtils.getComponentInstance(viewPage);
					if (viewPageCI != null) {
						EObject[] qikContainers = viewPageCI.getChildren();
						for (EObject qikContainer : qikContainers) {
							if (!canQikContainerContainSBB(qikContainer, sbbUnderTest)) {
								return false;
							}
						}
					}
				}
			}
		} else {
			//Simple Dialog case
			if (layoutOrContainer != null && !canQikContainerContainSBB(layoutOrContainer, sbbUnderTest)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean canQikContainerContainComponent(EObject qikContainer, IComponent component) {
		if (ModelUtils.isOfType(component, LAYOUT_CONTROL_TYPE)) {
			CreationTool creationTool = (CreationTool) component.getAdapter(CreationTool.class);
			if (creationTool != null) {
				final String controlRefValue = creationTool.getControlRefValue();
				Object object = ComponentInstanceVisitor.preOrderTraversal(qikContainer, new Visitor() {
					public Object visit(IComponentInstance ci) {
						EObject curObject = ci.getEObject();
						if (ModelUtils.isInstanceOf(curObject, LAYOUT_CONTROL_TYPE)) {
							IPropertySource ps = ModelUtils.getPropertySource(curObject);
							Object propertyValue = ps.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME);
							if (controlRefValue.equals(propertyValue))
								return curObject;
						}
						return null;
					}
				});
				if (object != null) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean canQikContainerContainChild(EObject qikContainer, final IComponentInstance child) {
		IComponent childComponent = child.getComponent();
		if (ModelUtils.isOfType(childComponent, LAYOUT_CONTROL_TYPE)) {
			IPropertySource ps = ModelUtils.getPropertySource(child.getEObject());
			if (ps instanceof IPropertySource) {
				Object pv = ps.getPropertyValue(CONTROL_PROPERTY);
				final String controlRefValue = pv.toString();
				Object object = ComponentInstanceVisitor.preOrderTraversal(qikContainer, new Visitor() {
					public Object visit(IComponentInstance ci) {
						if (ci == child)
							return null; // don't return this object
						EObject curObject = ci.getEObject();
						if (ModelUtils.isInstanceOf(curObject, LAYOUT_CONTROL_TYPE)) {
							IPropertySource ps = ModelUtils.getPropertySource(curObject);
							Object propertyValue = ps.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME);
							if (controlRefValue.equals(propertyValue))
								return curObject;
						}
						return null;
					}
				});
				if (object != null) {
					return false;
				}
			}
		}
		if (ModelUtils.isOfType(childComponent, CONTROL_COLLECTION_ITEM_BASE_TYPE)) {
			if (qikContainer != null) {
				final String controlRefValue = child.getName();
				Object object = ComponentInstanceVisitor.preOrderTraversal(qikContainer, new Visitor() {
					public Object visit(IComponentInstance ci) {
						EObject curObject = ci.getEObject();
						if (ModelUtils.isInstanceOf(curObject, LAYOUT_CONTROL_TYPE)) {
							IPropertySource ps = ModelUtils.getPropertySource(curObject);
							Object propertyValue = ps.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME);
							if (controlRefValue.equals(propertyValue))
								return curObject;
						}
						return null;
					}
				});
				if (object != null) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean canQikContainerContainQikContainer(final EObject qikContainer, EObject qikContainerUnderTest) {
		IComponentInstance qikContainerUnderTestCI = ModelUtils.getComponentInstance(qikContainerUnderTest);
		if (qikContainerUnderTestCI != null) {
			EObject[] children = qikContainerUnderTestCI.getChildren();
			Object object = ComponentInstanceVisitor.preOrderTraversal(children, new Visitor() {
				public Object visit(IComponentInstance ci) {
					IComponent ciComponent = ci.getComponent();
					EObject ciObj = ci.getEObject();
					if (ModelUtils.isOfType(ciComponent, LAYOUT_CONTROL_TYPE)) {
						if (!canQikContainerContainChild(qikContainer, ci)) {
							return ciObj;
						}
					}
					if (ModelUtils.booleanAttribute(ciComponent, ISSYSTEMBUILDINGBLOCK_ATTRIBUTE)) {
						if (!canQikContainerContainSBB(qikContainer, ciObj)) {
							return ciObj;
						}
					}
					return null;
				}
			});
			if (object != null) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean canQikContainerContainSBB(EObject qikContainer, EObject sbbUnderTest) {
		IComponentInstance sbbCI = ModelUtils.getComponentInstance(sbbUnderTest);
		if (sbbCI != null) {
			EObject[] slots = sbbCI.getChildren();
			for (EObject slot : slots) {
				IComponentInstance slotCI = ModelUtils.getComponentInstance(slot);
				if (slotCI != null) {
					EObject[] layoutControls = slotCI.getChildren();
					if (layoutControls != null) {
						for (EObject layoutControl : layoutControls) {
							IComponentInstance layoutControlCI = ModelUtils.getComponentInstance(layoutControl);
							if (layoutControlCI != null && !canQikContainerContainChild(qikContainer, layoutControlCI)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public static EObject getControlCollection(EObject eobject) {
		EObject controlCollection = null;
		if (eobject != null) {
			IDesignerDataModel ddm = ModelUtils.getModel(eobject);
			Object object = ComponentInstanceVisitor.preOrderTraversal(ddm.getRootContainers(), new Visitor() {
				public Object visit(IComponentInstance ci) {
					if (ci.getComponentId().equals(CONTROL_COLLECTION_TYPE))
						return ci.getEObject();
					return null;
				}
			});
			controlCollection = ModelUtils.getEObject(object);
		}
		return controlCollection;
	}
	
	public static int getRowHeight(EObject eobj, ILookAndFeel laf) {
		IComponentInstance ci = ModelUtils.getComponentInstance(eobj);
		IComponentInstance rootCI = ModelUtils.getRootInstance(ci);
		String rootComponentId = rootCI.getComponentId();
		if (rootComponentId != null) {
			if ( rootCI.getComponentId().equals(VIEW_COMPONENT_ID) )
				return laf.getInteger(ROWLAYOUTMANAGER_VIEW_ROWHEIGHT, 27);
			else
				return laf.getInteger(ROWLAYOUTMANAGER_DIALOG_ROWHEIGHT, 28);
		}
		return 27;
	}
	
	public static Rectangle getCQikContainerBoundsFromLaf(EObject eobj, ILookAndFeel laf) {
		IComponentInstance ci = ModelUtils.getComponentInstance(eobj);
		IComponentInstance rootCI = ModelUtils.getRootInstance(ci);
		if (ModelUtils.isInstanceOf(rootCI.getEObject(), VIEW_COMPONENT_ID)) {
			return laf.getRectangle("ViewCQikContainer.bounds"); //$NON-NLS-1$
		} else {
			return laf.getRectangle("SimpleDialogCQikContainer.bounds"); //$NON-NLS-1$
		}
	}
	
	public static EObject getViewLayout(EObject object) {
		IComponentInstance instance = ModelUtils.getComponentInstance(object);
		if (instance == null)
			return null;
		if (instance.getComponentId().equals(VIEW_LAYOUT_ID) && isMultiPageView(instance))
			return object;
		
		EObject parent = instance.getParent();
		instance = ModelUtils.getComponentInstance(parent);
		if (instance == null)
			return null;
		if (instance.getComponentId().equals(VIEW_LAYOUT_ID) && isMultiPageView(instance))
			return parent;
		
		return null;
		
	}
	
	public static boolean isMultiPageView(IComponentInstance viewLayoutInstance) {	
		EObject viewLayouts = viewLayoutInstance.getParent();
		Check.checkState(viewLayouts != null);
		IComponentInstance viewLayoutsInstance = ModelUtils.getComponentInstance(viewLayouts);
		
		Check.checkState(viewLayoutsInstance != null);
		EObject view = viewLayoutsInstance.getParent();
		Check.checkState(view != null);
		IPropertySource viewProperties = ModelUtils.getPropertySource(view);
		
		Check.checkState(viewProperties != null);
		String typeValue = (String)viewProperties.getPropertyValue("type");
		if (typeValue != null && (typeValue.equals("multiPage") || typeValue.equals("dialog"))) {
			return true;
		} else {		
			return false;
		}
	}
	
	
	/**
	 * Get the type id of a system building block
	 * 
	 * No tests are done to see if sbbObject actually represents a valid SBB
	 * 
	 * @param sbbObject a system building block
	 * @return the type id of the system building block, -1 if SBB type id couldn't be obtained
	 */
	public static int getSbbTypeId(EObject sbbObject) {
		Object sbbTypeObject = ModelUtils.getEditablePropertyValue(sbbObject, "type");
		if (sbbTypeObject != null) {
			initializeSbbTypes();
			Integer sbbTypeInt = sbbTypes.get(sbbTypeObject.toString());
			if (sbbTypeInt != null) {
				return sbbTypeInt.intValue();
			}
		}
		return -1;
	}
	
	private static void initializeSbbTypes() {
		if (sbbTypes.isEmpty()) {
			sbbTypes.put("EQikCtOnelineBuildingBlock", new Integer(SbbTypeIds.oneline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtCaptionedTwolineBuildingBlock", new Integer(SbbTypeIds.captionedTwoline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtTwolineBuildingBlock", new Integer(SbbTypeIds.twoline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtManylinesBuildingBlock", new Integer(SbbTypeIds.manylines));	//$NON-NLS-1$
			sbbTypes.put("EQikCtIconOnelineBuildingBlock", new Integer(SbbTypeIds.iconOneline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtIconCaptionedTwolineBuildingBlock", new Integer(SbbTypeIds.iconCaptionedTwoline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtIconTwolineBuildingBlock", new Integer(SbbTypeIds.iconTwoline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtOnelineIconBuildingBlock", new Integer(SbbTypeIds.onelineIcon));	//$NON-NLS-1$
			sbbTypes.put("EQikCtIconOnelineIconBuildingBlock", new Integer(SbbTypeIds.iconOnelineIcon));	//$NON-NLS-1$
			sbbTypes.put("EQikCtIconTwolineIconBuildingBlock", new Integer(SbbTypeIds.iconTwolineIcon));	//$NON-NLS-1$
			sbbTypes.put("EQikCtMediumThumbnailDoubleOnelineBuildingBlock", new Integer(SbbTypeIds.mediumThumbnailDoubleOneline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtLargeThumbnailThreelineBuildingBlock", new Integer(SbbTypeIds.largeThumbnailThreeline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtCaptionedOnelineBuildingBlock", new Integer(SbbTypeIds.captionedOneline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtIconCaptionedOnelineBuildingBlock", new Integer(SbbTypeIds.iconCaptionedOneline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtTwolineIconBuildingBlock", new Integer(SbbTypeIds.twolineIcon));	//$NON-NLS-1$
			sbbTypes.put("EQikCtIconIconOnelineBuildingBlock", new Integer(SbbTypeIds.iconIconOneline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtHalflineHalflineBuildingBlock", new Integer(SbbTypeIds.halflineHalfline));	//$NON-NLS-1$
			sbbTypes.put("EQikCtCaptionedHalflineBuildingBlock", new Integer(SbbTypeIds.captionedHalfline));	//$NON-NLS-1$
		}
	}
}
