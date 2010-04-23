package com.nokia.carbide.remoteconnections.internal.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI2;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI2.IListener;

/**
 * Dialog for selecting a connection (via {@link IConnectionsManager#ensureConnection(String, com.nokia.carbide.remoteconnections.interfaces.IService)}.
 */
public class SelectConnectionDialog extends TitleAreaDialog {
	/**
	 * 
	 */
	private final IClientServiceSiteUI2 ui;

	/**
	 * @param parentShell
	 * @param ui
	 */
	public SelectConnectionDialog(Shell parentShell,
			IClientServiceSiteUI2 ui) {
		super(parentShell);
		this.ui = ui;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite c = (Composite) super.createDialogArea(parent);
		GridLayout layout = (GridLayout) c.getLayout();
		layout.marginWidth = 6;
		layout.marginHeight = 6;
		ui.createComposite(c);
		ui.addListener(new IListener() {
			public void connectionSelected() {
				updateStatus(ui.getSelectionStatus());
			}

		});
		
		return c;
	}

	private void updateStatus(IStatus selectionStatus) {
		setTitle(Messages.getString("SelectConnectionDialog.TitleLabel")); //$NON-NLS-1$
		setMessage(Messages.getString("SelectConnectionDialog.Description")); //$NON-NLS-1$
		switch (selectionStatus.getSeverity()) {
		case IStatus.ERROR:
			setMessage(selectionStatus.getMessage(), IMessageProvider.ERROR);
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			break;
		case IStatus.WARNING:
			setMessage(selectionStatus.getMessage(), IMessageProvider.WARNING);
			getButton(IDialogConstants.OK_ID).setEnabled(true);
			break;
		case IStatus.INFO:
			setMessage(selectionStatus.getMessage(), IMessageProvider.INFORMATION);
			getButton(IDialogConstants.OK_ID).setEnabled(true);
			break;
		default:
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
	}

	@Override
	public void create() {
		super.create();
		updateStatus(ui.getSelectionStatus());
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("SelectConnectionDialog.DialogTitle")); //$NON-NLS-1$
	}

	@Override
	protected boolean isResizable() {
		return true;
	}
}