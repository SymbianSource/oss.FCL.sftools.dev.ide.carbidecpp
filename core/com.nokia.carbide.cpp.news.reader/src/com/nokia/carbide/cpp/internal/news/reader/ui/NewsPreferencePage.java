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

package com.nokia.carbide.cpp.internal.news.reader.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.news.reader.CarbideNewsReaderPlugin;
import com.nokia.carbide.cpp.internal.news.reader.Messages;
import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndFeed;

/**
 * A class for creating and configuring the Carbide.c++ news preference page.
 *
 */
public class NewsPreferencePage extends PreferencePage 
	implements IWorkbenchPreferencePage{

	/**
	 * Inner class to handle labels of the news feeds table.
	 */
	private class NewsPreferenceLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof CarbideSyndFeed) {
				CarbideSyndFeed feed = (CarbideSyndFeed)element;
				return feed.getTitle();
			}
			else {
				return super.getText(element);
			}
		}
	}

	// private content
	private Button launchCtrlButton;
	private Button selectAllButton;
	private Button clearAllButton;
	private Button update06hrButton;
	private Button update12hrButton;
	private Button update24hrButton;
	private CheckboxTableViewer newsFeedsTableViewer;
	private List<CarbideSyndFeed> newsFeeds;
	private int updateInterval;
	private boolean subscriptionChanged;

	/**
	 * The Constructor.
	 */
	public NewsPreferencePage() {
		super();
		subscriptionChanged = false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		final Composite content = new Composite(parent, SWT.NONE);
		final GridLayout contentGridLayout = new GridLayout();
		contentGridLayout.numColumns = 2;
		content.setLayout(contentGridLayout);
		content.setLayoutData(new GridData(GridData.FILL_BOTH));

		launchCtrlButton = new Button(content, SWT.CHECK);
		launchCtrlButton.setText(Messages.Preferences_LaunchCtrlLabel);
		launchCtrlButton.setToolTipText(Messages.Preferences_LaunchCtrlMessage);

		// filler
		new Label(content, SWT.NONE);

		final Group newsFeedsGroup = new Group(content, SWT.NONE);
		newsFeedsGroup.setLayout(new GridLayout());
		final GridData channelsGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		channelsGridData.heightHint = 100;
		channelsGridData.widthHint = 200;
		newsFeedsGroup.setLayoutData(channelsGridData);
		newsFeedsGroup.setText(Messages.Preferences_NewsFeedsGroupLabel);

		newsFeedsTableViewer = CheckboxTableViewer.newCheckList(newsFeedsGroup, SWT.BORDER);
		newsFeedsTableViewer.setLabelProvider(new NewsPreferenceLabelProvider());
		newsFeedsTableViewer.setContentProvider(new ArrayContentProvider());
		getNewsFeeds();
		newsFeedsTableViewer.setInput(newsFeeds);
		getNewsFeedsSubscriptionStatus();
		final Table table = newsFeedsTableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setToolTipText(Messages.Preferences_NewsFeedsTableMessage);
		
		final Composite buttonsComposite = new Composite(content, SWT.NONE);
		final GridLayout buttonsGridLayout = new GridLayout();
		buttonsGridLayout.makeColumnsEqualWidth = true;
		buttonsComposite.setLayout(buttonsGridLayout);
		buttonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		final GridData buttonsGridData = new GridData(SWT.NONE, SWT.NONE, true, false);
		buttonsGridData.widthHint = 80;

		selectAllButton = new Button(buttonsComposite, SWT.NONE);
		selectAllButton.setText(Messages.Preferences_SelectAllLabel);
		selectAllButton.setToolTipText(Messages.Preferences_SelectAllMessage);
		selectAllButton.setLayoutData(buttonsGridData);
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				handleSelectAll();
			}
		});

		clearAllButton = new Button(buttonsComposite, SWT.NONE);
		clearAllButton.setText(Messages.Preferences_ClearAllLabel);
		clearAllButton.setToolTipText(Messages.Preferences_ClearAllMessage);
		clearAllButton.setLayoutData(buttonsGridData);
		clearAllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				handleClearAll();
			}
		});

		final Group feedUpdateGroup = new Group(content, SWT.NONE);
		feedUpdateGroup.setLayout(new GridLayout());
		final GridData feedUpdateGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		feedUpdateGridData.heightHint = 70;
		feedUpdateGridData.widthHint = 200;
		feedUpdateGroup.setLayoutData(feedUpdateGridData);
		feedUpdateGroup.setText(Messages.Preferences_UpdateIntervalLabel);
		feedUpdateGroup.setToolTipText(Messages.Preferences_UpdateIntervalMessage);

		update06hrButton = new Button(feedUpdateGroup, SWT.RADIO);
		update06hrButton.setText(Messages.Preferences_Update06HourLabel);
		update12hrButton = new Button(feedUpdateGroup, SWT.RADIO);
		update12hrButton.setText(Messages.Preferences_Update12HourLabel);
		update24hrButton = new Button(feedUpdateGroup, SWT.RADIO);
		update24hrButton.setText(Messages.Preferences_Update24HourLabel);

		getStoredPreferenceValues();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, NewsUIHelpIDs.NEWSREADER_PREFERENCE_PAGE);
		return content;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		setStoredPreferenceValues();
		return super.performOk();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		launchCtrlButton.setSelection(true);
		newsFeedsTableViewer.setAllChecked(true);
		update06hrButton.setSelection(true);
		update12hrButton.setSelection(false);
		update24hrButton.setSelection(false);
		super.performDefaults();
	}

	/**
	 * Retrieve the stored news feeds.
	 */
	private void getNewsFeeds() {
		if (newsFeeds == null) {
			newsFeeds = new ArrayList<CarbideSyndFeed>();
		}
		newsFeeds.addAll(CarbideNewsReaderPlugin.getFeedManager().getNewsFeeds());
	}

	/**
	 * Retrieve the subscription status of the news feeds table entries.
	 */
	private void getNewsFeedsSubscriptionStatus() {
		for (Iterator<CarbideSyndFeed> iterator = newsFeeds.iterator(); iterator.hasNext();) {
			CarbideSyndFeed feed = iterator.next();
			if (feed.isSubscribed()) {
				newsFeedsTableViewer.setChecked(feed, true);
			}
			else {
				newsFeedsTableViewer.setChecked(feed, false);
			}
		}
	}

	/**
	 * Retrieve the stored value for this preference page.
	 */
	private void getStoredPreferenceValues() {
		IPreferenceStore store = CarbideNewsReaderPlugin.getPrefsStore();
		boolean launchAtStartup = store.getBoolean(NewsPreferenceConstants.LAUNCH_AT_STARTUP);
		launchCtrlButton.setSelection(launchAtStartup);
		updateInterval = store.getInt(NewsPreferenceConstants.UPDATE_INTERVAL);
		getUpdateInterval();
	}

	/**
	 * Retrieve the update interval value and display it.
	 */
	private void getUpdateInterval() {
		switch (updateInterval) {
			case 6 : update06hrButton.setSelection(true); break;
			case 12: update12hrButton.setSelection(true); break;
			case 24: update24hrButton.setSelection(true); break;
			default: update24hrButton.setSelection(true);
		}
	}

	/**
	 * Things to do when the "Clear All" button is selected.
	 */
	private void handleClearAll() {
		newsFeedsTableViewer.setAllChecked(false);
	}

	/**
	 * Things to do when the "Select All" button is selected.
	 */
	private void handleSelectAll() {
		newsFeedsTableViewer.setAllChecked(true);
	}

	/**
	 * Save the subscription status of the news feeds table entries.
	 */
	private void setNewsFeedsSubscriptionStatus() {
		for (Iterator<CarbideSyndFeed> iterator = newsFeeds.iterator(); iterator.hasNext();) {
			CarbideSyndFeed feed = iterator.next();
			boolean subscribed = newsFeedsTableViewer.getChecked(feed);
			if (feed.isSubscribed() != subscribed) {
				subscriptionChanged = true;
			}
			feed.setSubscribed(subscribed);
		}
		if (subscriptionChanged) {
			CarbideNewsReaderPlugin.getFeedManager().unreadEntriesCountChanged();
			subscriptionChanged = false;
		}
	}

	/**
	 * Set the stored value for this preference page.
	 */
	private void setStoredPreferenceValues() {
		IPreferenceStore store = CarbideNewsReaderPlugin.getPrefsStore();
		boolean launchAtStartup = launchCtrlButton.getSelection();
		store.setValue(NewsPreferenceConstants.LAUNCH_AT_STARTUP, launchAtStartup);
		setUpdateInterval();
		int oldUpdateInterval = store.getInt(NewsPreferenceConstants.UPDATE_INTERVAL);
		if (oldUpdateInterval != updateInterval) {
			CarbideNewsReaderPlugin.getFeedManager().resetUpdateTimer();
		}
		store.setValue(NewsPreferenceConstants.UPDATE_INTERVAL, updateInterval);
		setNewsFeedsSubscriptionStatus();
	}

	/**
	 * Save the update interval value.
	 */
	private void setUpdateInterval() {
		if (update06hrButton.getSelection()) {
			updateInterval = 6;
		}
		else 
		if (update12hrButton.getSelection()) {
			updateInterval = 12;
		}
		else 
		if (update24hrButton.getSelection()) {
			updateInterval = 24;
		}
	}

}
