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


package com.nokia.sdt.uidesigner.ui.editparts;

import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.LayoutAreaConfigurationAdapter;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.ui.skin.ISkin;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.editparts.policy.*;
import com.nokia.sdt.uidesigner.ui.figure.RootContainerLayoutFigure;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.*;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 *
 */
public class RootContainerLayoutEditPart extends LayoutContainerEditPart {

	private Border figureBorder;
	private LayoutAreaConfigurationAdapter layoutAreaConfigurationListener;

	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.ui.editparts.ModelObjectEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		IFigure figure = new RootContainerLayoutFigure(this);
		figureBorder = figure.getBorder();
		return figure;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.ui.editparts.ModelContainerEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// don't allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// no selection feedback
		NonResizableEditPolicy policy = new NonResizableEditPolicy(getEditor());
		policy.setDragAllowed(false);
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, policy);
		
		XYLayout layout = (XYLayout) getContentPane().getLayoutManager();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ContainerXYLayoutEditPolicy(layout));
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new LayoutObjectDirectEditPolicy());
	}

	@Override
	public void activate() {
		super.activate();
		// wait until listeners are added
		IDisplayModel displayModel = getDisplayModel();
		displayModel.addLayoutAreaListener(layoutAreaConfigurationListener = new LayoutAreaConfigurationAdapter() {
			public void configurationChanged(LayoutAreaConfiguration configuration) {
				setBoundsFromLayoutConfiguration(configuration);
			}
		});
		((ContentsLayoutEditPart) getParent()).setDisplayModel(displayModel);
		// get saved layout and set it if it exists
		IResource resource = getEditor().getDataModelResource();
		LayoutAreaConfiguration savedConfig = EditorUtils.getSavedConfiguration(displayModel, resource);
		if (savedConfig != null) {
			try {
				displayModel.setCurrentConfiguration(savedConfig);
			}
			catch (CoreException x) {
				UIDesignerPlugin.getDefault().log(x);
			}
		}
		setBoundsFromLayoutConfiguration(displayModel.getCurrentConfiguration());
	}
	
	@Override
	public void removeNotify() {
		IDisplayModel displayModel = getDisplayModel();
		super.removeNotify();
		displayModel.removeLayoutAreaListener(layoutAreaConfigurationListener);
		layoutAreaConfigurationListener = null;
	}

	private void setBoundsFromLayoutConfiguration(LayoutAreaConfiguration config) {
		Rectangle bounds;
		if (config == null)
			return;
		ISkin skin = config.getSkin();
		if (skin != null) {
			bounds = skin.getEditorAreaBounds();
		}
		else {
			bounds = new Rectangle(0, 0, config.getSize().x, config.getSize().y);
		}
		
		getLayoutObject().setBounds(bounds);
	}

	protected IDisplayModel getDisplayModel() {
		return (IDisplayModel) getEditor().getAdapter(IDisplayModel.class);
	}

	protected void refreshVisuals() {
	}

	public void boundsChanged(ILayoutObject object) {
	}

	public Border getFigureBorder() {
		return figureBorder;
	}
}
