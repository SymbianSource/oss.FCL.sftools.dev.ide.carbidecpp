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
 
package com.nokia.carbide.internal.bugreport.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import java.util.Hashtable;
import org.eclipse.core.runtime.*;
import com.nokia.carbide.internal.bugreport.export.IProduct;
import com.nokia.carbide.internal.bugreport.resources.HelpContextIDs;
import com.nokia.carbide.internal.bugreport.resources.Messages;

/**
 * This wizard page is shown only if we find multiple plug-ins 
 * which extend this bug_reporter plug-in. This page is then 
 * used for selecting which product is used. 
 *
 */
public class ProductSelectionPage extends WizardPage {

	Label productLabel;
	Combo productCombo;
	Hashtable<String, IConfigurationElement> products = null;
	
	public ProductSelectionPage(Hashtable<String, IConfigurationElement> prdcts) {
		super(Messages.getString("ProductSelectionPage.BugReporting")); //$NON-NLS-1$

		setTitle(Messages.getString("ProductSelectionPage.ProductSelection")); //$NON-NLS-1$
		
		setDescription(Messages.getString("ProductSelectionPage.SelectProduct")); //$NON-NLS-1$
			
		products = prdcts;
		
		// User cannot finish the page before some valid 
		// selection is made.
		setPageComplete(false);
	 }

	public void createControl(Composite parent) {
		Composite composite =  new Composite(parent, SWT.NULL);
		
	    // create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		composite.setLayout(gl);
		
		productLabel = new Label(composite, SWT.LEFT);
		productLabel.setText(Messages.getString("ProductSelectionPage.Product")); //$NON-NLS-1$
		
		productCombo = new Combo(composite, SWT.READ_ONLY);
		String[] productList = (String[])products.keySet().toArray(new String[products.keySet().size()]);
		productCombo.setItems(productList);
		productCombo.select(0);

		setControl(composite);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HelpContextIDs.PRODUCT_SELECTION_PAGE);
	}
	
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return true;
	}
	
	/**
	 * Creates and returns the selected product.
	 * @return the selected product.
	 */
	public IProduct getProduct() {
		IProduct product = null;

		try {
			product = (IProduct)products.get(productCombo.getText()).createExecutableExtension("class");	 //$NON-NLS-1$
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return product;
	}
	
}
