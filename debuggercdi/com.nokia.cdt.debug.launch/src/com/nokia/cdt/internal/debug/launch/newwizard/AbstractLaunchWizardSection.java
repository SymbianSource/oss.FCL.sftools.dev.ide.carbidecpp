/*
 * Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.cdt.internal.debug.launch.newwizard;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

/**
 * 
 */
public abstract class AbstractLaunchWizardSection implements IWizardSection {

	private static final String CHANGE_LABEL = Messages.getString("AbstractLaunchWizardSection.ChangeLabel"); //$NON-NLS-1$
	protected final LaunchWizardData data;
	private String sectionName;

	protected IStatus status;
	protected Label descriptionLabel;
	protected Button changeButton;
	protected Composite control;
	private ISectionChangeListener changeListener;
	protected final UnifiedLaunchOptionsPage launchOptionsPage;


	public AbstractLaunchWizardSection(LaunchWizardData data, String sectionName, UnifiedLaunchOptionsPage launchOptionsPage) {
		this.data = data;
		this.sectionName = sectionName;
		this.launchOptionsPage = launchOptionsPage;
		status = Status.OK_STATUS;
	}
	
	abstract protected void dispose();

	public IStatus getStatus() {
		return status;
	}

	/** Initialize the data for this section (before UI shown). */
	public abstract void initializeSettings();
	
	/** Validate the settings and update status. */
	abstract protected void validate();
	
	/** Update the UI when data changes. Called after validate(). */
	protected abstract void updateUI();

	/** Create the UI for this section. */
	public abstract void createControl(Composite parent);

	/** Create the dialog for the Change... button. */
	protected abstract AbstractLaunchSettingsDialog createChangeSettingsDialog(Shell shell, LaunchWizardData dialogData);
	/** Refresh the section after the Change... dialog has been closed. */
	protected abstract void refresh();

	public Control getControl() {
		return control; 
	}

	public String getSectionName() {
		return sectionName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.IWizardSection#setChangeListener(com.nokia.cdt.internal.debug.launch.wizard2.IWizardSection.ISectionChangeListener)
	 */
	public void setChangeListener(ISectionChangeListener listener) {
		this.changeListener = listener;
	}
	
	protected void createSection(Composite parent, int acceleratorIndex) {
		Composite composite = new Composite(parent, SWT.NONE);
		
		GC gc = new GC(parent);
		int INDENT = gc.getAdvanceWidth('m') * 4;
		gc.dispose();
		
		GridLayoutFactory.fillDefaults().numColumns(2).margins(6, 0).applyTo(composite);
		
		// spacing
		Label spacer = new Label(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(spacer);
		
		Label titleLabel = new Label(composite, SWT.NONE);
		titleLabel.setText(sectionName);
		titleLabel.setFont(JFaceResources.getBannerFont());
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(titleLabel);
		
		descriptionLabel = new Label(composite, SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, true).indent(INDENT, 0).hint(350, SWT.DEFAULT).applyTo(descriptionLabel);
		
		String label = MessageFormat.format("{0}&{1}", //$NON-NLS-1$
				CHANGE_LABEL.substring(0, acceleratorIndex), CHANGE_LABEL.substring(acceleratorIndex));
		changeButton = new Button(composite, SWT.PUSH);
		changeButton.setText(label);
		Point minSize = changeButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		GridDataFactory.defaultsFor(changeButton).grab(false, false).hint(minSize.x + INDENT, SWT.DEFAULT).indent(6, 0).applyTo(changeButton);
		changeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doChange();
				if (changeListener != null)
					changeListener.changed();
			}
		});
		
		composite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				descriptionLabel.pack();
			}
		});
		
		this.control = composite;

		control.addDisposeListener(new DisposeListener() {
			
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}
		});
		
		validate();
		updateUI();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#doChange()
	 */
	protected void doChange() {
		LaunchWizardData dialogData = data.copy();
		AbstractLaunchSettingsDialog dialog = createChangeSettingsDialog(getControl().getShell(), dialogData);
		if (dialog.open() == Window.OK) {
			data.apply(dialogData);
			refresh();
			launchOptionsPage.getWizard().getContainer().getShell().pack();
		}
	}

	protected static IStatus error(String msg, Object... args) {
		return new Status(IStatus.ERROR, LaunchPlugin.PLUGIN_ID,
				MessageFormat.format(msg, args));
	}

	protected IStatus warning(String msg, Object... args) {
		return new Status(IStatus.WARNING, LaunchPlugin.PLUGIN_ID,
				MessageFormat.format(msg, args));
	}
}