package com.nokia.carbide.internal.discovery.ui.view;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalPage;

public class SupportPage implements IPortalPage {

	public SupportPage() {
	}

	@Override
	public String getText() {
		return Messages.SupportPage_Title;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return Activator.getImageDescriptor("icons/Carbide_c_icon_16x16.png"); //$NON-NLS-1$
	}

	@Override
	public Control createControl(Composite parent, IEditorPart part) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		return composite;
	}

	@Override
	public void init() {
	}
	
	@Override
	public IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater) {
		return new IActionBar[0];
	}

	@Override
	public void dispose() {
	}

}
