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

package com.nokia.carbide.cpp.internal.news.reader.editor;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.nokia.carbide.cpp.internal.news.reader.CarbideNewsReaderPlugin;
import com.nokia.carbide.cpp.internal.news.reader.Messages;
import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndEntry;
import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndFeed;
import com.nokia.carbide.cpp.internal.news.reader.ui.NewsPreferenceConstants;
import com.nokia.carbide.cpp.internal.news.reader.ui.NewsUIHelpIDs;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.impl.DateParser;

/**
 * A form page to display Carbide.c++ news contents.
 *
 */
public class NewsPage extends FormPage {

	public final static String NEWS_PAGE_ID = "news";
	public final static String NEWS_PAGE_TITLE = "Carbide.c++ News Page";
	public final static String NEWS_SUMMARY_TITLE = "All News";
	private final static String ECLIPSE_NETWORK_PREFERENCES_ID = "org.eclipse.ui.net.NetPreferences";
	private final static String FAKE_URL_PREFIX = "http://localhost/news/";
	private final static String LINK_MARK_ALL_ENTRIES_READ = FAKE_URL_PREFIX + "removeAll";
	private final static String LINK_MARK_ENTRY_READ = FAKE_URL_PREFIX + "removeEntry";
	private final static String LINK_NEWS_PREFERENCES = FAKE_URL_PREFIX + "newsPreferences";
	private final static String LINK_NETWORK_PREFERENCES = FAKE_URL_PREFIX + "networkPreferences";
	private final static String LINK_SHOW_ALL_ENTRIES = FAKE_URL_PREFIX + "showAll";
	private final static String LINK_SHOW_UNREAD_ENTRIES = FAKE_URL_PREFIX + "showUnreadOnly";
	private final static String LINK_UPDATE_FEEDS = FAKE_URL_PREFIX + "updateFeeds";
	private final static String DELIMETER = "::";
	private final static String HTML_BODY_HEADER = "<html><head><title></title><style type=\"text/css\">div.item {font-family : sans-serif; font-size : 12px; margin-bottom : 16px;} div.itemBody {padding-top : 3px; padding-bottom : 3px;} div.itemInfo {background-color : #EEEEEE; color : #333333;} div.feedflare {display: none;} a.itemTitle {font-size : 12px; font-weight : bold;} a.markItemRead {font-size : 10px; color : #333333;}</style></head><body>";
	private final static String HTML_BODY_FOOTER = "</body></html>";
	private final static String HTML_CONTROLS_HEADER_START = "<div class=\"item\"><div class=\"itemInfo\">";
	private final static String HTML_CONTROLS_HEADER_END = "</div></div>";
	private final static String HTML_CONTROLS_DIVIDER = " | ";
	private final static String HTML_MARK_ALL_ENTRIES_READ_CONTROL = "<a class=\"markItemRead\" href=\"" + LINK_MARK_ALL_ENTRIES_READ + "\">Mark All Read</a>";
	private final static String HTML_NEWS_PREFERENCES_CONTROL = "<a class=\"markItemRead\" href=\"" + LINK_NEWS_PREFERENCES + "\">Preferences</a>";
	private final static String HTML_NETWORK_PREFERENCES_CONTROL = "<a href=\"" + LINK_NETWORK_PREFERENCES + "\">Network Connections Preferences</a>";
	private final static String HTML_SHOW_ALL_ENTIES_CONTROL = "<a class=\"markItemRead\" href=\"" + LINK_SHOW_ALL_ENTRIES + "\">All Items</a>";
	private final static String HTML_SHOW_UNREAD_ENTIES_CONTROL = "<a class=\"markItemRead\" href=\"" + LINK_SHOW_UNREAD_ENTRIES + "\">Unread Items Only</a>";
	private final static String HTML_UPDATE_FEEDS_CONTROL = "<a class=\"markItemRead\" href=\"" + LINK_UPDATE_FEEDS + "\">Update</a>";
	private Pattern MarkEntryReadPattern = Pattern.compile("about:removeEntry::(.*)::(.*)");
	private List<CarbideSyndFeed> newsFeeds;
	private int currentFeed;
	private Button launchCtrlButton;
	private SectionPart gsSectionPart;
	private SectionPart newsFeedsSectionPart;
	private SectionPart newsContentsSectionPart;
	private TableViewer gsTableViewer;
	private TableViewer newsFeedsTableViewer;
	private Browser newsBrowser;
	private boolean showUnreadOnly;

	/**
	 * The constructor.
	 */
	public NewsPage() {
		super(NEWS_PAGE_ID, NEWS_PAGE_TITLE);
		currentFeed = 0;
		showUnreadOnly = true;
	}

	/**
	 * The constructor.
	 * @param editor - the editor hosting this page
	 */
	public NewsPage(FormEditor editor) {
		super(editor, NEWS_PAGE_ID, NEWS_PAGE_TITLE);
		currentFeed = 0;
		showUnreadOnly = true;
	}

	/**
	 * Refresh the contents of this page.
	 */
	public void refresh() {
		refreshGettingStartedSection();
		refreshNewsFeeds();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText(Messages.NewsPage_Title);
		Composite container = form.getBody();
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		toolkit.paintBordersFor(container);

		// News Feeds Section
		createNewsFeedsSection(container, toolkit);

		// News Contents Section
		createNewsContentsSection(container, toolkit);

		// Getting Started Section
		createGettingStartedSection(container, toolkit);

		// Button controlling whether to launch this page during startup
		launchCtrlButton = toolkit.createButton(form.getBody(), Messages.NewsPage_LaunchCtrlLabel, SWT.CHECK);
		launchCtrlButton.setToolTipText(Messages.NewsPage_LaunchCtrlMessage);
		launchCtrlButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setStoredPreferenceValues();
			}
		});
		getStoredPreferenceValues();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(container, NewsUIHelpIDs.NEWSREADER_EDITOR_PAGE);
	}

	/**
	 * Create the getting started section.
	 * @param container - the container composite of this section
	 * @param toolkit - the toolkit of this section
	 */
	private void createGettingStartedSection(Composite container, FormToolkit toolkit) {
		gsSectionPart = new SectionPart(container, toolkit, Section.TITLE_BAR | Section.EXPANDED);
		final GridData layoutData = new GridData(SWT.FILL, SWT.FILL, false, true);
		layoutData.widthHint = 250;
		layoutData.heightHint = 150;
		gsSectionPart.getSection().setLayoutData(layoutData);
		gsSectionPart.getSection().setText(Messages.NewsPage_GettingStartedSectionTitle);
		
		Composite composite = toolkit.createComposite(gsSectionPart.getSection());
		final GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		toolkit.paintBordersFor(composite);
		gsSectionPart.getSection().setClient(composite);
		
		gsTableViewer = new TableViewer(composite, SWT.NONE);
		final GridData layoutData1 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gsTableViewer.getTable().setLayoutData(layoutData1);
		gsTableViewer.setLabelProvider(new NewsPageLabelProvider(SWT.COLOR_DARK_BLUE));
		gsTableViewer.setContentProvider(new ArrayContentProvider());
		if (CarbideNewsReaderPlugin.getFeedManager().getResourceFeed() != null) {
			gsTableViewer.setInput(CarbideNewsReaderPlugin.getFeedManager().getResourceFeed().getEntries());
		}
		gsTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				try {
					Object element = ((IStructuredSelection)event.getSelection()).getFirstElement();
					if (element instanceof CarbideSyndEntry) {
						CarbideSyndEntry entry = (CarbideSyndEntry)element;
						newsBrowser.setText(createGettingStartedContents(entry));
					}
				} catch (Exception e) {
					CarbideNewsReaderPlugin.log(e);
				}
			}
		});
	}

	/**
	 * Create the new feeds section.
	 * @param container - the container composite of this section
	 * @param toolkit - the toolkit of this section
	 */
	private void createNewsFeedsSection(Composite container, FormToolkit toolkit) {
		newsFeedsSectionPart = new SectionPart(container, toolkit, Section.TITLE_BAR | Section.EXPANDED);
		final GridData layoutData = new GridData(SWT.FILL, SWT.FILL, false, true);
		layoutData.widthHint = 250;
		layoutData.heightHint = 150;
		newsFeedsSectionPart.getSection().setLayoutData(layoutData);
		newsFeedsSectionPart.getSection().setText(Messages.NewsPage_NewsFeedsSectionTitle);
		
		Composite composite = toolkit.createComposite(newsFeedsSectionPart.getSection());
		final GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		toolkit.paintBordersFor(composite);
		newsFeedsSectionPart.getSection().setClient(composite);
		
		newsFeedsTableViewer = new TableViewer(composite, SWT.NONE);
		final GridData layoutData1 = new GridData(SWT.FILL, SWT.FILL, true, true);
		newsFeedsTableViewer.getTable().setLayoutData(layoutData1);
		newsFeedsTableViewer.setLabelProvider(new NewsPageLabelProvider(SWT.COLOR_DARK_BLUE));
		newsFeedsTableViewer.setContentProvider(new ArrayContentProvider());
		if (CarbideNewsReaderPlugin.getFeedManager().getSubscribedNewsFeeds() != null) {
			createNewsFeeds();
			newsFeedsTableViewer.setInput(newsFeeds);
			newsFeedsTableViewer.getTable().setSelection(currentFeed);
			newsFeedsTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					setCurrentFeed();
				}
			});
		}
	}

	/**
	 * Create the news content section.
	 * @param container - the container composite of this section
	 * @param toolkit - the toolkit of this section
	 */
	private void createNewsContentsSection(Composite container, FormToolkit toolkit) {
		newsContentsSectionPart = new SectionPart(container, toolkit, Section.TITLE_BAR);
		final GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.verticalSpan = 2;
		newsContentsSectionPart.getSection().setLayoutData(layoutData);
		newsContentsSectionPart.getSection().setText(Messages.NewsPage_NewsContentsSectionTitle);
		
		Composite composite = toolkit.createComposite(newsContentsSectionPart.getSection());
		final GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		toolkit.paintBordersFor(composite);
		newsContentsSectionPart.getSection().setClient(composite);		

		newsBrowser = new Browser(composite, SWT.NONE);
		final GridData browserLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		newsBrowser.setLayoutData(browserLayoutData);
		try {
			if (CarbideNewsReaderPlugin.getFeedManager().getSubscribedNewsFeeds() != null) {
				newsBrowser.setText(createNewsContents());
			}
		} catch (Exception e) {
			CarbideNewsReaderPlugin.log(e);
		}
		
		// note: the non-navigable links do not work on all hosts, hence the mouse listener 
		newsBrowser.addLocationListener(new LocationListener() {
			public void changing(LocationEvent event) {
				handleChangingLocation(event);
			}

			public void changed(LocationEvent event) {
			}
		});
	}

	/**
	 * Create the Getting Started content.
	 * @param entry - getting started entry in question
	 * @return HTML getting started entry content
	 */
	private String createGettingStartedContents(CarbideSyndEntry entry) {
		String contents = HTML_BODY_HEADER;
		String content = "";
		if (entry != null) {
			String entryLink = entry.getLink();
			String entryTitle = entry.getTitle();
			content += "<div class=\"item\"><a class=\"itemTitle\" href=\""
					+ entryLink + "\">" + entryTitle + "</a>";

			if (entry.getDescription() != null) {
				String entryDescription = entry.getDescription();
				content += "<div class=\"itemBody\">" + entryDescription + "</div>";
			}

			String title = Messages.NewsPage_GettingStartedSectionTitle;
	        String entryDate = "";
	        Date date = entry.getPublishedDate();
	        if (date != null) {
	        	entryDate = DateParser.formatRFC822(date);
	        }
	        content += "<div class=\"itemInfo\">" + title + " (" + entryDate + ")</div></div>";
		}
		contents += createGettingStartedControlsHeader() + content + HTML_BODY_FOOTER;
		return contents;
	}

	/**
	 * Create the HTML controls header for Getting Started content.
	 * @return HTML controls header for Getting Started content
	 */
	private String createGettingStartedControlsHeader() {
		String header = HTML_CONTROLS_HEADER_START; // controls header start

		header += HTML_NEWS_PREFERENCES_CONTROL;
		header += HTML_CONTROLS_DIVIDER;
		header += HTML_UPDATE_FEEDS_CONTROL;

		header += HTML_CONTROLS_HEADER_END; // controls header end
		return header;
	}

	/**
	 * Create the HTML news content.
	 * @return HTML news content
	 */
	private String createNewsContents() {
		String contents = HTML_BODY_HEADER;
		String news = "";
		if (CarbideNewsReaderPlugin.getFeedManager().isCacheEmpty()) {
			news += createNewsErrorMessage();
		}
		else if (currentFeed < newsFeeds.size()) {
			CarbideSyndFeed feed = newsFeeds.get(currentFeed);
			news += getFeedContents(feed);
		}
		contents += createNewsControlsHeader() + news + HTML_BODY_FOOTER;
		return contents;
	}

	/**
	 * Create the HTML controls header for news content.
	 * @return HTML controls header for news content
	 */
	private String createNewsControlsHeader() {
		String header = HTML_CONTROLS_HEADER_START; // controls header start

		header += HTML_MARK_ALL_ENTRIES_READ_CONTROL;
		header += HTML_CONTROLS_DIVIDER;

		if (showUnreadOnly) {
			header += HTML_SHOW_ALL_ENTIES_CONTROL;
			header += HTML_CONTROLS_DIVIDER;
		}
		else {
			header += HTML_SHOW_UNREAD_ENTIES_CONTROL;
			header += HTML_CONTROLS_DIVIDER;
		}

		header += HTML_NEWS_PREFERENCES_CONTROL;
		header += HTML_CONTROLS_DIVIDER;
		header += HTML_UPDATE_FEEDS_CONTROL;

		header += HTML_CONTROLS_HEADER_END; // controls header end
		return header;
	}

	/**
	 * Create error message when local cache is empty and network connection fails.
	 * @return error message.
	 */
	private String createNewsErrorMessage() {
		String message = "<div class=\"itemBody\">Unable to fetch feeds from news servers. Please check the "
				+ HTML_NETWORK_PREFERENCES_CONTROL + ".</div>";
		return message;
	}

	/**
	 * Retrieve the cached news feeds.
	 */
	private void createNewsFeeds() {
		if (newsFeeds == null) {
			newsFeeds = new ArrayList<CarbideSyndFeed>();
		}

		CarbideSyndFeed summaryFeed = createSummaryFeed();
		newsFeeds.add(summaryFeed);
		List<CarbideSyndFeed> subscribedNewsFeeds = CarbideNewsReaderPlugin.getFeedManager().getSubscribedNewsFeeds();
		newsFeeds.addAll(subscribedNewsFeeds);
	}

	/**
	 * Create a summary feed for entries from all available news feeds.
	 * @return summary feed
	 */
	private CarbideSyndFeed createSummaryFeed() {
		List<CarbideSyndFeed> newsFeeds = CarbideNewsReaderPlugin.getFeedManager().getSubscribedNewsFeeds();
		SyndFeed sFeed = new SyndFeedImpl();
		sFeed.setTitle(NEWS_SUMMARY_TITLE);
		CarbideSyndFeed summaryFeed = new CarbideSyndFeed(sFeed);
		List<CarbideSyndEntry> entries = new ArrayList<CarbideSyndEntry>();
		for (Iterator<CarbideSyndFeed> iterator = newsFeeds.iterator(); iterator.hasNext();) {
			CarbideSyndFeed feed = iterator.next();
			entries.addAll(feed.getEntries());
		}
		Collections.sort(entries);
		summaryFeed.setEntries(entries);
		return summaryFeed;
	}

	/**
	 * Retrieve contents of a feed in HTML format.
	 * @param feed - the feed in question
	 * @return feed contents in HTML format
	 */
	private String getFeedContents(CarbideSyndFeed feed) {
		String contents = "";
		List<CarbideSyndEntry> entries;
		if (showUnreadOnly) {
			entries = feed.getUnreadEntries();
		}
		else {
			entries = feed.getEntries();
		}
		for (Iterator<CarbideSyndEntry> iterator = entries.iterator(); iterator.hasNext();) {
			CarbideSyndEntry entry = iterator.next();
			contents += getFeedEntryContents(feed, entry);
		}
		return contents;
	}

	/**
	 * Retrieve contents of a feed entry in HTML format.
	 * @param feed - feed in question
	 * @param entry - feed entry in question
	 * @return feed entry content in HTML format
	 */
	private String getFeedEntryContents(CarbideSyndFeed feed, CarbideSyndEntry entry) {
		String result = "";
		if (entry != null) {
			String entryLink = entry.getLink();
			String entryTitle = entry.getTitle();
			result += "<div class=\"item\"><a class=\"itemTitle\" href=\""
					+ entryLink + "\">" + entryTitle + "</a>";

			if (entry.getDescription() != null) {
				String entryDescription = entry.getDescription();
				result += "<div class=\"itemBody\">" + entryDescription + "</div>";
			}

			String feedTitle = feed.getTitle();
	        String entryDate = "";
	        Date date = entry.getPublishedDate();
	        if (date == null) {
	        	date = feed.getPublishedDate();
	        }
	        if (date != null) {
	        	entryDate = DateParser.formatRFC822(date);
	        }
	        result += "<div class=\"itemInfo\">" + feedTitle + " (" + entryDate + ") ";

	        feedTitle = feedTitle.replaceAll("\"", "&quot;");
	        entryTitle = entryTitle.replaceAll("\"", "&quot;");
	        if (!entry.isRead()) {
		        result += "<a class=\"markItemRead\" href=\""
						+ LINK_MARK_ENTRY_READ
						+ DELIMETER + feedTitle
						+ DELIMETER + entryTitle
						+ "\">Mark Read</a>";
	        }

	        result += "</div></div>";
		}
		return result;
	}

	/**
	 * Retrieve the stored preference settings values of this page.
	 */
	private void getStoredPreferenceValues() {
		IPreferenceStore store = CarbideNewsReaderPlugin.getPrefsStore();
		boolean launchAtStartup = store.getBoolean(NewsPreferenceConstants.LAUNCH_AT_STARTUP);
		launchCtrlButton.setSelection(launchAtStartup);
	}

	/**
	 * Handle navigation of the browser widget in the news contents section.
	 * @param event - LocationEvent sent by the browser widget
	 */
	private void handleChangingLocation(LocationEvent event) {
		try {
			String targetLocation = event.location;
			if (targetLocation.startsWith(FAKE_URL_PREFIX)) {
				event.doit = false;
				if (targetLocation.startsWith(LINK_MARK_ALL_ENTRIES_READ)) {
					handleMarkAllRead();
				}
				else if (targetLocation.startsWith(LINK_MARK_ENTRY_READ)) {
					Matcher matcher = MarkEntryReadPattern.matcher(targetLocation);
					if (matcher.matches()) {
						String feedTitle = matcher.group(1).replaceAll("%20", " ");
						String entryTitle = matcher.group(2).replaceAll("%20", " ");
						handleMarkEntryRead(feedTitle, entryTitle);
					}
				}
				else if (targetLocation.startsWith(LINK_NEWS_PREFERENCES)) {
					handleOpenNewsPreferencePage();
				}
				else if (targetLocation.startsWith(LINK_NETWORK_PREFERENCES)) {
					handleOpenNetworkPreferencePage();
				}
				else if (targetLocation.startsWith(LINK_SHOW_ALL_ENTRIES)) {
					handleShowAll();
				}
				else if (targetLocation.startsWith(LINK_SHOW_UNREAD_ENTRIES)) {
					handleShowUnreadOnly();
				}
				else if (targetLocation.startsWith(LINK_UPDATE_FEEDS)) {
					handleUpdateFeeds();
				}
			} else {
				if (targetLocation.startsWith("http")) {
					event.doit = false;
					URL url = new URL(event.location);
					PlatformUI.getWorkbench().getBrowserSupport().createBrowser(null).openURL(url);
				}
			}
		} catch (Exception e) {
			CarbideNewsReaderPlugin.log(e);
		}
	}

	/**
	 * Handle marking all entries from a given feed as read.
	 */
	private void handleMarkAllRead() {
		if (currentFeed < newsFeeds.size()) {
			CarbideSyndFeed feed = newsFeeds.get(currentFeed);
			for (Iterator<CarbideSyndEntry> eIterator = feed.getEntries().iterator(); eIterator.hasNext();) {
				CarbideSyndEntry entry = eIterator.next();
				entry.setRead(true);
			}
		}
		newsBrowser.setText(createNewsContents());
		newsFeedsTableViewer.refresh();
		CarbideNewsReaderPlugin.getFeedManager().unreadEntriesCountChanged();
	}

	/**
	 * Handle marking a feed entry as read.
	 * @param feedTitle - title of the feed in question
	 * @param entryTitle - title of the feed entry in question
	 */
	private void handleMarkEntryRead(String feedTitle, String entryTitle) {
		CarbideSyndFeed feed = newsFeeds.get(currentFeed);
		if (feed != null) {
			CarbideNewsReaderPlugin.getFeedManager().markEntryAsRead(feed.getEntries(), entryTitle);
		}
		newsBrowser.setText(createNewsContents());
		newsFeedsTableViewer.refresh();
		CarbideNewsReaderPlugin.getFeedManager().unreadEntriesCountChanged();
	}

	/**
	 * Handle opening the Carbide.c++ news preference page.
	 */
	private void handleOpenNewsPreferencePage() {
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(getSite().getShell(), NewsPreferenceConstants.PREFERENCE_PAGE_ID, null, null);
		dialog.open();
		newsBrowser.setText(createNewsContents());
	}

	/**
	 * Handle opening the Eclipse network connections preference page.
	 */
	private void handleOpenNetworkPreferencePage() {
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(getSite().getShell(), ECLIPSE_NETWORK_PREFERENCES_ID, null, null);
		dialog.open();
		newsBrowser.setText(createNewsContents());
		CarbideNewsReaderPlugin.loadFeeds();
	}

	/**
	 * Handle displaying all read and unread feed entries.
	 */
	private void handleShowAll() {
		showUnreadOnly = false;
		newsBrowser.setText(createNewsContents());
		newsFeedsTableViewer.refresh();
	}

	/**
	 * Handle displaying only unread feed entries.
	 */
	private void handleShowUnreadOnly() {
		showUnreadOnly = true;
		newsBrowser.setText(createNewsContents());
		newsFeedsTableViewer.refresh();
	}

	/**
	 * Handle updating feeds.
	 */
	private void handleUpdateFeeds() {
		newsBrowser.setText(createNewsContents());
		CarbideNewsReaderPlugin.updateFeeds();
	}

	/**
	 * Update content of the getting started section.
	 */
	private void refreshGettingStartedSection() {
		if (CarbideNewsReaderPlugin.getFeedManager().getResourceFeed() != null) {
			gsTableViewer.setInput(CarbideNewsReaderPlugin.getFeedManager().getResourceFeed().getEntries());
			gsTableViewer.refresh();
		}
	}

	/**
	 * Update the news feeds list.
	 */
	private void refreshNewsFeeds() {
		if (CarbideNewsReaderPlugin.getFeedManager().getSubscribedNewsFeeds() != null) {
			newsFeeds.clear();
			CarbideSyndFeed summaryFeed = createSummaryFeed();
			newsFeeds.add(summaryFeed);
			List<CarbideSyndFeed> subscribedNewsFeeds = CarbideNewsReaderPlugin.getFeedManager().getSubscribedNewsFeeds();
			newsFeeds.addAll(subscribedNewsFeeds);
			newsFeedsTableViewer.refresh();
		}
	}

	/**
	 * Set the current feed based on user selection in the news feeds section.
	 */
	private void setCurrentFeed() {
		currentFeed = newsFeedsTableViewer.getTable().getSelectionIndex();
		if (currentFeed == -1) {
			currentFeed = 0;
		}
		newsBrowser.setText(createNewsContents());
		newsFeedsTableViewer.getTable().setSelection(currentFeed);
	}

	/**
	 * Save the preference settings values of this page.
	 */
	private void setStoredPreferenceValues() {
		IPreferenceStore store = CarbideNewsReaderPlugin.getPrefsStore();
		boolean launchAtStartup = launchCtrlButton.getSelection();
		store.setValue(NewsPreferenceConstants.LAUNCH_AT_STARTUP, launchAtStartup);
	}

}
