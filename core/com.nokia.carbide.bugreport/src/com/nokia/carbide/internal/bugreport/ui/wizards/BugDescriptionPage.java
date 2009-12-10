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

import java.io.File;
import java.util.Hashtable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.dialogs.MessageDialog;
import com.nokia.carbide.internal.bugreport.export.IProduct;
import com.nokia.carbide.internal.bugreport.model.*;
import com.nokia.carbide.internal.bugreport.resources.HelpContextIDs;
import com.nokia.carbide.internal.bugreport.resources.Messages;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;


/**
 * Details Description wizard page. This page is used entering issue type, summary, 
 * description and attachment file.
 *
 */
public class BugDescriptionPage extends WizardPage implements SelectionListener,
																ModifyListener {
	Label typeLabel;
	Combo typeCombo;
	Label summaryLabel;
	Text summaryText = null;
	Label descriptionLabel;
	Text descriptionText;
	Label attachmentLabel;
	Text attachmentText;
	Button browseButton;
	Link uiServiceLink;
	IProduct product = null;
	long maxAttachmentSize = 1024*1024; // megabyte
	
	public BugDescriptionPage(){
		super(Messages.getString("BugDescriptionPage.BugReporting")); //$NON-NLS-1$
			
		// User cannot finish the page before some valid 
		// selection is made.
		setPageComplete(false);
	 }
	
	/**
	 * Sets the product for this page.
	 * @param prdct product plug-in
	 */
	public void setProduct(IProduct prdct) {
		if (prdct == null)
			return;
		
		product = prdct;
		
		setTitle(product.getPageTitleText());
		setDescription(product.getPageDescriptionText());
		maxAttachmentSize = product.getMaxAttachmentSize();
		
		// if page is constructed.
		if (summaryText != null) {
			summaryText.setTextLimit(product.getMaxSummaryLength());
			descriptionLabel.setText(product.getDescriptionLabelText());
			descriptionText.setTextLimit(product.getMaxDescriptionLength());
			String linkText = product.getUiServiceLinkText();
			if (linkText == null || linkText == "") { //$NON-NLS-1$
				uiServiceLink.setVisible(false);
			} else {
				uiServiceLink.setVisible(true);
				uiServiceLink.setText("<a>"+linkText+"</a>"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			
		}
	}

	public void createControl(Composite parent) {
		Composite composite =  new Composite(parent, SWT.NULL);
		
	    // create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		composite.setLayout(gl);
		
		GridData twoColumnGridData = new GridData(GridData.FILL_HORIZONTAL);
		twoColumnGridData.horizontalSpan = 2;

		typeLabel = new Label(composite, SWT.LEFT);
		typeLabel.setText(Messages.getString("BugDescriptionPage.Type")); //$NON-NLS-1$
		typeLabel.setLayoutData(twoColumnGridData);
		
		typeCombo = new Combo(composite, SWT.READ_ONLY);
		typeCombo.setItems(new String[] {Messages.getString("BugDescriptionPage.Defect"),  //$NON-NLS-1$
										 Messages.getString("BugDescriptionPage.Enhancement")}); //$NON-NLS-1$
		typeCombo.select(0);
		GridData comboGD = new GridData();
		comboGD.horizontalSpan = 2;
		typeCombo.setLayoutData(comboGD);

		summaryLabel = new Label(composite, SWT.LEFT);
		summaryLabel.setText(Messages.getString("BugDescriptionPage.ShortSummary")); //$NON-NLS-1$
		summaryLabel.setLayoutData(twoColumnGridData);
		
		summaryText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData summaryTextGD = new GridData(GridData.FILL_HORIZONTAL);
		summaryTextGD.horizontalSpan = 2;
		summaryText.setLayoutData(summaryTextGD);
		summaryText.addModifyListener(this);
		if (product == null) {
			summaryText.setTextLimit(255);
		} else {
			summaryText.setTextLimit(product.getMaxSummaryLength());
		}
		
		descriptionLabel = new Label(composite, SWT.LEFT | SWT.WRAP);
		if (product == null) {
			descriptionLabel.setText(Messages.getString("BugDescriptionPage.Description")); //$NON-NLS-1$
		} else {
			descriptionLabel.setText(product.getDescriptionLabelText());
		}
		descriptionLabel.setLayoutData(twoColumnGridData);
		
		descriptionText = new Text(composite, SWT.MULTI | SWT.BORDER);
		if (product == null) {
			descriptionText.setTextLimit(1000);
		} else {
			descriptionText.setTextLimit(product.getMaxDescriptionLength());
		}
		GridData descriptionGD = new GridData(GridData.FILL_BOTH);
		descriptionGD.horizontalSpan = 2;
		descriptionText.setLayoutData(descriptionGD);
		descriptionText.addModifyListener(this);

		attachmentLabel = new Label(composite, SWT.LEFT);
		attachmentLabel.setText(Messages.getString("BugDescriptionPage.AttachmentFile")); //$NON-NLS-1$
		attachmentLabel.setLayoutData(twoColumnGridData);
		
		attachmentText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		attachmentText.setTextLimit(1000);
		GridData single = new GridData(GridData.FILL_HORIZONTAL);
		attachmentText.setLayoutData(single);
		attachmentText.addModifyListener(this);
		
		browseButton = new Button(composite, SWT.PUSH);
		browseButton.setText(Messages.getString("BugDescriptionPage.Browse")); //$NON-NLS-1$
		browseButton.addSelectionListener(this);
		
		uiServiceLink = new Link(composite, SWT.NONE);
		uiServiceLink.addSelectionListener(this);
		if (product == null) {
			uiServiceLink.setVisible(false);
		} else {
			String linkText = product.getUiServiceLinkText();
			if (linkText == null || linkText == "") { //$NON-NLS-1$
				uiServiceLink.setVisible(false);
			} else {
				uiServiceLink.setText("<a>"+linkText+"</a>"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		
		setControl(composite);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HelpContextIDs.BUG_DESCRIPTION_PAGE);
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	public void widgetSelected(SelectionEvent event) {
		if (event.widget == browseButton) {
			FileDialog dialog = new FileDialog(this.getShell(), SWT.OPEN);
			dialog.setText(Messages.getString("BugDescriptionPage.SelectAttachmentFile")); //$NON-NLS-1$
			BrowseDialogUtils.initializeFrom(dialog, attachmentText);
			String result = dialog.open();
			attachmentText.setText(result);
		} else if (event.widget == uiServiceLink) {
			product.showUiService(this.getShell());
		}
	}

	public void modifyText(ModifyEvent arg0) {
		try {
			getWizard().getContainer().updateButtons();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean canFlipToNextPage() {
		
		if (attachmentFileIsValidOrEmpty() &&
			summaryText.getText().length() > 0 && 
			descriptionText.getText().length() > 0)
			return true;
		
		return false;
	}
	
	/**
	 * Checks that the selected attachment file is a valid file (or 
	 * no file is even selected) and that it's too big in size. 
	 * @return true if attachment file is valid and not too big, otherwise false.
	 */
	boolean attachmentFileIsValidOrEmpty() {
		String attachmentFilePath = attachmentText.getText();
		
		// no attachment -> ok
		if (attachmentFilePath.length() == 0) {
			this.setErrorMessage(null);
			return true;
		// check that attachment file is ok
		} else {
			File file = new File(attachmentFilePath);
			if (file.isFile() && file.exists() && file.length() <= maxAttachmentSize) { // file is ok
				this.setErrorMessage(null);
				return true;
			} else if (!file.isFile() || !file.exists()) { // file does not exist or is a directory
				this.setErrorMessage(Messages.getString("BugDescriptionPage.AttachmentFileIsInvalid"));	 //$NON-NLS-1$
				return false;
			} else { // file is too big
				if (maxAttachmentSize > 1024*1024) { // format error for megabyte size
					this.setErrorMessage(Messages.getString("BugDescriptionPage.AttachmentTooBig1")+maxAttachmentSize/(1024*1024)+Messages.getString("BugDescriptionPage.MegaByte")); //$NON-NLS-1$ //$NON-NLS-2$
				} else if (maxAttachmentSize > 1024) { // format error for kilobyte size
					this.setErrorMessage(Messages.getString("BugDescriptionPage.AttachmentTooBig2")+maxAttachmentSize/1024+Messages.getString("BugDescriptionPage.KiloByte")); //$NON-NLS-1$ //$NON-NLS-2$
				} else { // format error for byte size
					this.setErrorMessage(Messages.getString("BugDescriptionPage.AttachmentTooBig3")+maxAttachmentSize+Messages.getString("BugDescriptionPage.Byte")); //$NON-NLS-1$ //$NON-NLS-2$
				}
				return false;
			}
		}
	}
	
	/**
	 * Returns the fields and their values (entered by user) which are to 
	 * be sent to the server.
	 * @return fields and values which need to be sent to server
	 */
	public Hashtable<String, String> getFields() {
		Hashtable<String, String> fields = new Hashtable<String, String>();
		fields.put(FieldsHandler.FIELD_SUMMARY, summaryText.getText());
		fields.put(FieldsHandler.FIELD_DESCRIPTION, descriptionText.getText());
		fields.put(FieldsHandler.FIELD_TYPE, typeCombo.getText());
		if (attachmentText.getText().trim() != "") //$NON-NLS-1$
			fields.put(FieldsHandler.FIELD_ATTACHMENT, attachmentText.getText().trim());
		return fields;
	}
	
	/**
	 * The product plug-in might need to ask user some settings before we can move to 
	 * next page. This method asks the product if UI service is needed; if UI is needed,
	 * method queries the user if (s)he wants to provide the necessary information, and 
	 * opens the product's UI if user wants to do so.
	 * @return true if we can move to next page, false if not.
	 */
	public boolean canChangePage() {
		if (!product.uiServiceNeeded())
			return true;
		
		if (MessageDialog.openQuestion(this.getShell(), Messages.getString("BugDescriptionPage.BugReporting"), product.getUiServiceText())) //$NON-NLS-1$
			product.showUiService(this.getShell());
		
		return false;
	}	
}
