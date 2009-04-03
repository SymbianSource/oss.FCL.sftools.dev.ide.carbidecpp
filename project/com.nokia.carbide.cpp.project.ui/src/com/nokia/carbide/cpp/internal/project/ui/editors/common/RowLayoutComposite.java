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
package com.nokia.carbide.cpp.internal.project.ui.editors.common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * Composite class for use in forms. It works beter with 
 * a vertically-oriented RowLayout by constraining its 
 * height to the height of the parent, thus forcing
 * multiple columns.
 * Because we don't have access to modifying the root composite of
 * a form this composite must be the child of the root container, with
 * all the real contain contained within this composite
 *
 */
public class RowLayoutComposite extends Composite {
	
	private final ScrolledForm form;

	/**
	 * Initialize a FillLayout on the parent and
	 * a RowLayout on this composite
	 * @param parent
	 * @param style
	 */
	public RowLayoutComposite(Composite parent, int style, ScrolledForm form) {
		super(parent, style);
		this.form = form;
		parent.setLayout(new FillLayout());
		
		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Control ctl = (Control) e.getSource();
				ctl.getParent().setSize(ctl.getSize());
				getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						RowLayoutComposite.this.form.reflow(true);
					}
				});
			}
		});
	
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.spacing = 5;
		rowLayout.fill = true;
		rowLayout.pack = true;
		setLayout(rowLayout);
	}
	
	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point parentSize = getParent().getSize();
		if (hHint == SWT.DEFAULT) {
			hHint = parentSize.y;
		}
		Point size = super.computeSize(wHint, hHint, changed);
		return size;
	}
}
