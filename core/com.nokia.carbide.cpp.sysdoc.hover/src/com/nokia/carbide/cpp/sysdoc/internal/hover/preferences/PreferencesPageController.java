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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.preferences;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.MessagesConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibPluginController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibProperties;
import com.nokia.carbide.cpp.sysdoc.internal.hover.exceptions.NotAvailableSDLException;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.DialogHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

/**
 * Controller class for the preference page. Core hover classes obtains
 * preferences and set preference. It also have utility methods which used in
 * preference page.
 */
final public class PreferencesPageController {

	private static PreferencesPageController instance = null; // new
	// PreferencesPageController();
	private Map<String, PropertyChangeEvent> changedProperties = new HashMap<String, PropertyChangeEvent>();
	private IPropertyChangeListener propertyChangeListener;

	// private DevLibProperties activeDevLibLoc;

	private PreferencesPageController() {
		addPropertyChangeListener();
	}

	public static PreferencesPageController getInstance() {
		if (instance == null) {
			instance = new PreferencesPageController();
		}
		return instance;
	}

	public void initPreferenceController() {
		initActiveDevLibLocation();
	}

	public Map<String, PropertyChangeEvent> getChangedProperties() {
		return changedProperties;
	}

	private void addPropertyChangeListener() {
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();
		propertyChangeListener = new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				changedProperties.put(event.getProperty(), event);
				Logger.logDebug("Changed property:" + event.getProperty());
			}
		};
		preferenceStore.addPropertyChangeListener(propertyChangeListener);
	}

	public void removePropertyChangeListener() {
		if (propertyChangeListener == null) {
			return;
		}
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();
		preferenceStore.removePropertyChangeListener(propertyChangeListener);
	}

	/**
	 * This method is called after all properties has changed in preference
	 * page. Then it invokes necessary modules in hover section
	 */
	public void analyseRecentPropertyChanges() {
		boolean enableIndexGeneration = !isDeActivatedHovering();
		boolean requiresIndexGeneration = false;
		if (changedProperties.size() == 0) {
			return;
		}

		Set<String> keySet = changedProperties.keySet();
		for (String property : keySet) {

			// If deactivated checkbox is changed
			if (property.equals(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING)) {
				PropertyChangeEvent event = changedProperties
						.get(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING);
				boolean deActivated = (Boolean) event.getNewValue();
				Logger.logDebug("DeActivated Old value" + event.getOldValue()
						+ " new value" + event.getNewValue());
				if (deActivated) {
					HoverManager.getInstance().setEnabled(false);
					enableIndexGeneration = false;
				} else {
					enableIndexGeneration = true;
					requiresIndexGeneration = true;
				}
				continue;
			}

			// if active developer library item has changed
			if (property.equals(HoverConstants.PREFERENCE_DEV_LIB_LOC)) {
				PropertyChangeEvent event = changedProperties
						.get(HoverConstants.PREFERENCE_DEV_LIB_LOC);
				String oldValue = (String) event.getOldValue();
				String newValue = (String) event.getNewValue();
				if (oldValue.equals(newValue)) {
					continue;
				}
				requiresIndexGeneration = true;
				Logger.logDebug("New index is generating. Old value"
						+ event.getOldValue() + " new value"
						+ event.getNewValue());
				continue;
			}

		}

		Logger.logDebug("enableIndexGeneration:" + enableIndexGeneration
				+ " requiresIndexGeneration:" + requiresIndexGeneration);
		if (enableIndexGeneration && requiresIndexGeneration) {
			Logger.logDebug("before gen index");
			generateNewIndex();
			Logger.logDebug("after gen index");
		}

		resetChangedProperties();
	}

	private void resetChangedProperties() {
		changedProperties.clear();
	}

	private void generateNewIndex() {
		DevLibProperties activeDevLibLoc = probeActiveDevLibLocation();
		HoverManager.setActiveDevLibProperties(activeDevLibLoc);
		HoverManager.getInstance().startIndex();
	}

	public String probeDefaultDevLibLocation() {

		Set<DevLibProperties> symbianPluginHelpsList = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();
		if (!symbianPluginHelpsList.isEmpty()) {
			Iterator<DevLibProperties> it = symbianPluginHelpsList.iterator();
			DevLibProperties devLibProbs = it.next();
			String devLibName = devLibProbs.getUserFriendlyName();
			return devLibName;
		}
		return null;
	}

	private String getPreferedDevLibLocation() {
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();
		String prefDevLibLoc = preferenceStore
				.getString(HoverConstants.PREFERENCE_DEV_LIB_LOC);
		return prefDevLibLoc;
	}

	private void setPreferedDevLibLocation(DevLibProperties devLibProp) {
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();
		preferenceStore.setValue(HoverConstants.PREFERENCE_DEV_LIB_LOC,
				devLibProp.getUserFriendlyName());

	}

	public static boolean isDeActivatedHovering() {
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();
		boolean deactivated = preferenceStore
				.getBoolean(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING);
		return deactivated;
	}

	/**
	 * Find first available SDL. That can be either from installed SDL plug-in
	 * or available SDL list
	 * 
	 * @return the URL of SDL if available. If not, returns null.
	 * @throws NotAvailableSDLException
	 */
	private DevLibProperties probeActiveDevLibLocation()
			throws NotAvailableSDLException {

		String preDevLib = getPreferedDevLibLocation();
		DevLibProperties devLibProp = DevLibPluginController.getInstance()
				.findDevLibPropFromLabel(preDevLib);

		if (devLibProp == null) {
			Set<DevLibProperties> devLibPropertiesSet = DevLibPluginController
					.getInstance().getDevLibPropertiesSet();
			if (devLibPropertiesSet.isEmpty()) {
				throw new NotAvailableSDLException(
						MessagesConstants.NO_VALID_DEVELOPER_LIBRARY + ":"
								+ preDevLib);
			} else {
				Iterator<DevLibProperties> it = devLibPropertiesSet.iterator();
				devLibProp = it.next();
				String msg = MessagesConstants.PREVIOUS_DEVLIB_NOTFOUND
						+ preDevLib + " " + MessagesConstants.NEW_DEVLIB_USED
						+ devLibProp.getUserFriendlyName();
				setPreferedDevLibLocation(devLibProp);
				DialogHelper.displayErrorDialog(msg);
				Logger.logWarn(msg);
			}
		}
		return devLibProp;
	}

	public boolean isOverwrite() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store
				.getBoolean(HoverConstants.PREFERENCE_AUTO_DEVLIB_SELECTION);
	}

	public static void setDeActivateHoverPlugin(boolean b) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, b);
	}

	private void initActiveDevLibLocation() {

		if (isDeActivatedHovering()) {
			setDeActivateHoverPlugin(true);
			return;
		}
		DevLibProperties probedActiveDevLibLocation = probeActiveDevLibLocation();
		if (probedActiveDevLibLocation == null) {
			setDeActivateHoverPlugin(true);
		}
		HoverManager.setActiveDevLibProperties(probedActiveDevLibLocation);
	}

	public static class PreferencePageUtil {
		public static final String TOKEN = ";";

		public static String[] convertStringToList(String longStr) {
			String list[] = longStr.split(TOKEN);
			return list;
		}

		public static String convertStrArrayToLongString(String[] arr) {
			StringBuilder sb = new StringBuilder();

			for (String str : arr) {
				if (sb.length() > 0) {
					sb.append(TOKEN);
				}
				sb.append(str);
			}
			return sb.toString();
		}

	}

	public String isValidDevLibLocation(String path) {
		return null;
	}
}
