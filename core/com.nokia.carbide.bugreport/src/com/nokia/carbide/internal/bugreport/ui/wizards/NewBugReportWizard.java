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

import org.eclipse.jface.wizard.*;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.core.runtime.*;
import com.nokia.carbide.internal.bugreport.export.IProduct;
import com.nokia.carbide.internal.bugreport.model.BugReportConsole;
import com.nokia.carbide.internal.bugreport.model.Communication;
import com.nokia.carbide.internal.bugreport.model.ConsoleLine;
import com.nokia.carbide.internal.bugreport.model.FieldsHandler;
import com.nokia.carbide.internal.bugreport.model.ProductHandler;
import com.nokia.carbide.internal.bugreport.resources.*;

import java.util.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Bug_Report wizard
 *
 */
public class NewBugReportWizard extends Wizard implements IWizard {

	static private final ImageDescriptor bannerImgDescriptor = 
		ImageResourceManager.getImageDescriptor(ImageKeys.WIZARD_BANNER);
	
	ProductSelectionPage productPage;
	BugDescriptionPage bugPage;
	SummaryPage summaryPage;
	FieldsHandler fieldsHandler = null;
	ProductHandler productHandler = null;
	BugReportConsole console = null;
	
	
	public NewBugReportWizard() throws RuntimeException {
		setDefaultPageImageDescriptor(bannerImgDescriptor);
		productHandler = new ProductHandler();
		fieldsHandler = new FieldsHandler();
		console = new BugReportConsole();
	}
	
	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {
		      public void run(IProgressMonitor monitor) {
		         monitor.beginTask(Messages.getString("NewBugReportWizard.SendingReport"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
		         String bugNumber = Communication.sendBugReport(fieldsHandler.getFieldsForSending(), productHandler.getProduct());
		         ConsoleLine line = new ConsoleLine(productHandler.getProduct(), bugNumber);
		         console.addLineToConsole(line);
		         
		         monitor.done();
		      }
		   });
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(this.getShell(), Messages.getString("NewBugReportWizard.BugReporting"), e.getCause().getLocalizedMessage()); //$NON-NLS-1$
			return false;
		}

		return true;
	}
	
	@Override
	public void addPages() {
		
		// product is not initialized if there were multiple product plugins.
		// in this case add a product selection page.
		if (productHandler.getProduct() == null) {
			productPage = new ProductSelectionPage(productHandler.getProducts());
			addPage(productPage);
		}
		
		bugPage = new BugDescriptionPage();
		addPage(bugPage);
		bugPage.setProduct(productHandler.getProduct());
		
		summaryPage = new SummaryPage();
		addPage(summaryPage);
		
		setWindowTitle(Messages.getString("NewBugReportWizard.BugReporting")); //$NON-NLS-1$
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		
		// if there was a product selection page (because of multiple product plugins)
		// get the selected product and give it to bugPage. Return bugPage for showing.
		if (page == productPage) {
			IProduct product = productPage.getProduct();
			if (product == null) {
				throw new RuntimeException(Messages.getString("NewBugReportWizard.CouldNotInitializeProduct")); //$NON-NLS-1$
			}
			productHandler.setProduct(product);
			bugPage.setProduct(product);
			return bugPage;
		// Changing from bug_page to summary page.
		} else if (page == bugPage) {
			if (bugPage.canChangePage()) {
				Hashtable<String, String> fields = bugPage.getFields();
				fieldsHandler.prepareFinalFields(fields, productHandler.getProduct());
				summaryPage.setSummaryText(fieldsHandler.getSummary());
				return summaryPage;
			}
		}
		return null;
	}		
	
	@Override
	public boolean canFinish() {
		if (getContainer().getCurrentPage() == summaryPage)
			return true;
		
		return false;
	}
	
	@Override
	public boolean needsProgressMonitor() {
		return true;
	}
	
}
