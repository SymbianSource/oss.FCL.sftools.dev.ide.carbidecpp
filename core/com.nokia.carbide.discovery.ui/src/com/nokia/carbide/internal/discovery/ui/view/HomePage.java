package com.nokia.carbide.internal.discovery.ui.view;

import org.eclipse.jface.resource.ImageDescriptor;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;
import com.nokia.carbide.internal.discovery.ui.extension.AbstractBrowserPortalPage;

public class HomePage extends AbstractBrowserPortalPage {

	@Override
	public String getText() {
		return Messages.HomePage_Title;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return Activator.getImageDescriptor("icons/Carbide_c_icon_16x16.png"); //$NON-NLS-1$
	}

}
