package com.nokia.carbide.internal.discovery.ui.editor;

import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class StackComposite extends SharedBackgroundComposite {

	private StackLayout stackLayout;
	private Control currentControl;

	public StackComposite(Composite parent, Composite backgroundParent) {
		super(parent, backgroundParent);
		stackLayout = new StackLayout();
		setLayout(stackLayout);
	}

	public void showControl(Control control) {
		if (control == currentControl)
			return;

		stackLayout.topControl = control;
		layout();
	}
}

