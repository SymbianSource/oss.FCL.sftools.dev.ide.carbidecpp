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
 
package com.nokia.carbide.internal.bugdatacollector.ui.preferences;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import com.nokia.carbide.internal.bugdatacollector.model.DesEncrypter;
import com.nokia.carbide.internal.bugdatacollector.plugin.BugDataCollectorPlugin;
import com.nokia.carbide.internal.bugdatacollector.resources.HelpContextIDs;
import com.nokia.carbide.internal.bugdatacollector.resources.Messages;


/**
 * Preference page for Carbide BugReport. Contains fields for 
 * BugZilla username and password. Contains also link to BugZilla 
 * main page. 
 *
 */
public class BugDataCollectorPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, Listener {

	private Label emailLabel;
	private Text emailText;
	private Label passwordLabel;
	private Text passwordText;
	private Link bugzillaLink;
	
	private Button sdkButton;
	private Button logButton;

	public BugDataCollectorPreferencePage() {
		super(Messages.getString("BugDataCollectorPreferencePage.BugReports")); //$NON-NLS-1$
	}
	
	public void init(IWorkbench arg0) {
	}	
	
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		container.setLayout(gridLayout);
		
		emailLabel = new Label(container, SWT.LEFT);
		emailLabel.setText(Messages.getString("BugDataCollectorPreferencePage.Username")); //$NON-NLS-1$
		
		emailText = new Text(container, SWT.SINGLE | SWT.BORDER);
		emailText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		passwordLabel = new Label(container, SWT.LEFT);
		passwordLabel.setText(Messages.getString("BugDataCollectorPreferencePage.Password")); //$NON-NLS-1$
		
		passwordText = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
		passwordText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		sdkButton = new Button(container, SWT.CHECK);
		sdkButton.setText(Messages.getString("BugDataCollectorPreferencePage.SendSdkInformation")); //$NON-NLS-1$
		
		logButton = new Button(container, SWT.CHECK);
		logButton.setText(Messages.getString("BugDataCollectorPreferencePage.SendDiagnosticLogs")); //$NON-NLS-1$
		
		//Platform.getProduct().getDefiningBundle().getHeaders().get(org.osgi.framework.Constants.BUNDLE_VERSION);
		//String vers = Platform.getProduct().getProperty("aboutText");
		//String productBlurb = Platform.getResourceString(ProductPlugin.getDefault().getBundle(), "%productBlurb");
		bugzillaLink = new Link(container, SWT.NONE);
		String linkAddress = Platform.getResourceString(BugDataCollectorPlugin.getDefault().getBundle(), "%data.LinkUrl"); //$NON-NLS-1$
		String linkText = String.format(Messages.getString("BugDataCollectorPreferencePage.LinkText"), linkAddress); //$NON-NLS-1$
		bugzillaLink.setText(linkText);
		bugzillaLink.addListener(SWT.Selection, this);
		
		
		getPrefsStoreValues();
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HelpContextIDs.PREFERENCE_PAGE);
		
		return container;
	}

	/**
	 * Called when "go to BugZilla main page" link is clicked.
	 */
	public void handleEvent(Event event) {
		try	{
			IWorkbench workbench = PlatformUI.getWorkbench();   
			IWebBrowser browser = workbench.getBrowserSupport().getExternalBrowser();
			browser.openURL(new java.net.URL(event.text));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getPrefsStoreValues(){
		IPreferenceStore store = BugDataCollectorPlugin.getPrefsStore();
		String username = store.getString(BugDataCollectorPreferenceConstants.BR_USERNAME);
		emailText.setText(username);
		String password = store.getString(BugDataCollectorPreferenceConstants.BR_PASSWORD);
		DesEncrypter encrypter = new DesEncrypter();
		passwordText.setText(encrypter.decrypt(password));
		boolean sdk = store.getBoolean(BugDataCollectorPreferenceConstants.BR_SEND_SDK_INFO);
		sdkButton.setSelection(sdk);
		boolean diagnostic = store.getBoolean(BugDataCollectorPreferenceConstants.BR_SEND_DIAGNOSTIC_LOG);
		logButton.setSelection(diagnostic);
	}
	
	@Override
	public boolean performOk() {
		IPreferenceStore store = BugDataCollectorPlugin.getPrefsStore();
	
		store.setValue(BugDataCollectorPreferenceConstants.BR_USERNAME, emailText.getText().trim());
		DesEncrypter encrypter = new DesEncrypter();
		store.setValue(BugDataCollectorPreferenceConstants.BR_PASSWORD, encrypter.encrypt(passwordText.getText().trim()));
		store.setValue(BugDataCollectorPreferenceConstants.BR_SEND_SDK_INFO, sdkButton.getSelection());
		store.setValue(BugDataCollectorPreferenceConstants.BR_SEND_DIAGNOSTIC_LOG, logButton.getSelection());
		
		return super.performOk();
	}
	
	/**
	 * Checks if username and password are set
	 * @return true if username and password are set, false if not.
	 */
	public static boolean hasUsernameAndPassword() {
		IPreferenceStore store = BugDataCollectorPlugin.getPrefsStore();
		String username = store.getString(BugDataCollectorPreferenceConstants.BR_USERNAME);
		String password = store.getString(BugDataCollectorPreferenceConstants.BR_PASSWORD);
		if (username != "" && password != "") //$NON-NLS-1$ //$NON-NLS-2$
			return true;
		return false;
	}
	
	/**
	 * Shows the preference dialog with only this preference page 
	 * available in the tree
	 * @param shell 
	 */
	public static void showYourself(Shell shell) {
		IPreferencePage page = new BugDataCollectorPreferencePage();
		PreferenceManager mgr = new PreferenceManager();
		IPreferenceNode node = new PreferenceNode("1", page); //$NON-NLS-1$
		mgr.addToRoot(node);
		PreferenceDialog dialog = new PreferenceDialog(shell, mgr);
		dialog.create();
		dialog.setMessage(page.getTitle());
		dialog.open();
	}

	/**
	 * Returns the username
	 * @return username
	 */
	public static String getUsername() {
		IPreferenceStore store = BugDataCollectorPlugin.getPrefsStore();
		return store.getString(BugDataCollectorPreferenceConstants.BR_USERNAME);
	}

	/**
	 * Returns the decrypted password.
	 * @return decrypted password.
	 */
	public static String getPassword() {
		IPreferenceStore store = BugDataCollectorPlugin.getPrefsStore();
		String password = store.getString(BugDataCollectorPreferenceConstants.BR_PASSWORD);
		DesEncrypter encrypter = new DesEncrypter();
		return encrypter.decrypt(password);
	}
	
	/**
	 * Returns true if devices.xml is to be send with the report, false if not.
	 * @return true if devices.xml is to be send with the report, false if not.
	 */
	public static boolean sendSdkData() {
		IPreferenceStore store = BugDataCollectorPlugin.getPrefsStore();
		return store.getBoolean(BugDataCollectorPreferenceConstants.BR_SEND_SDK_INFO);
	}
	
	/**
	 * Returns true if Carbide_Debugger_Log.xml is to be send with the report, false if not.
	 * @return true if Carbide_Debugger_Log.xml is to be send with the report, false if not.
	 */
	public static boolean sendDiagnostic() {
		IPreferenceStore store = BugDataCollectorPlugin.getPrefsStore();
		return store.getBoolean(BugDataCollectorPreferenceConstants.BR_SEND_DIAGNOSTIC_LOG);
	}
}
