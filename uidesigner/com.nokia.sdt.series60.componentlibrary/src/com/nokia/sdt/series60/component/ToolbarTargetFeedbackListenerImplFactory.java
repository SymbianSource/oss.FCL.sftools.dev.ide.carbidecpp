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


package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.symbian.displaymodel.*;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.datamodel.util.ModelUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

public class ToolbarTargetFeedbackListenerImplFactory implements IImplFactory {

	public class TargetFeedbackListener extends AbstractTargetFeedbackListener {
		
		private static final String ORIENTATION_PROPERTY = "orientation"; //$NON-NLS-1$
		private static final String VERTICAL_ORIENTATION = "Vertical"; //$NON-NLS-1$
		
		private RowLayoutTargetFeedbackFigure feedbackFigure;

		public TargetFeedbackListener(EObject container) {
			super(container);
			IComponentInstance ci = ModelUtils.getComponentInstance(container);
			ci.addPropertyListener(new IComponentInstancePropertyListener() {
				public void propertyChanged(EObject componentInstance, Object propertyId) {
					if (propertyId.equals(ORIENTATION_PROPERTY)) {
						feedbackFigure.setOrientation(isVertical());
					}
				}
			});
		}

		@Override
		protected ITargetFeedbackFigure createTargetFeedbackFigure() {
			return feedbackFigure = new RowLayoutTargetFeedbackFigure(isVertical());
		}

		private boolean isVertical() {
			IPropertySource ps = ModelUtils.getPropertySource(getEObject());
			Object value = ps.getPropertyValue(ORIENTATION_PROPERTY);
			return value.equals(VERTICAL_ORIENTATION);
		}

		public void endTargetFeedback() {
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject instance) {
		return new TargetFeedbackListener(instance);
	}

}
