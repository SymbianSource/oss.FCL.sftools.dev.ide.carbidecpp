/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.sdt.emf.dm.ILocalizedStringBundle;
import com.nokia.sdt.emf.dm.ILocalizedStringTable;

import java.util.Collection;

import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.util.Messages;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import java.text.MessageFormat;
import java.util.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ILocalized String Bundle</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.ILocalizedStringBundleImpl#getLocalizedStringTables <em>Localized String Tables</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ILocalizedStringBundleImpl extends EObjectImpl implements ILocalizedStringBundle {
	/**
	 * The cached value of the '{@link #getLocalizedStringTables() <em>Localized String Tables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalizedStringTables()
	 * @generated
	 * @ordered
	 */
	protected EList localizedStringTables = null;
	
	private IStringKeyProvider keyProvider;
	
	private Language defaultLanguage;
	
	private ListenerList<IListener> listeners;

	private Set<String> userGeneratedKeys;
	
	private String untranslatedStringTemplate = Messages.getString("ILocalizedStringBundleImpl.untranslatedStringTemplate"); //$NON-NLS-1$
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected ILocalizedStringBundleImpl() {
		super();
		// This is a fallback default. The UI should establish
		// the desired default.
		defaultLanguage = new Language(Language.LANG_American);
		
		ChildListener adapter = new ChildListener();
		adapter.setTarget(this);
		eAdapters().add(adapter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.Literals.ILOCALIZED_STRING_BUNDLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getLocalizedStringTables() {
		if (localizedStringTables == null) {
			localizedStringTables = new EObjectContainmentEList(ILocalizedStringTable.class, this, DmPackage.ILOCALIZED_STRING_BUNDLE__LOCALIZED_STRING_TABLES);
		}
		return localizedStringTables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DmPackage.ILOCALIZED_STRING_BUNDLE__LOCALIZED_STRING_TABLES:
				return ((InternalEList)getLocalizedStringTables()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DmPackage.ILOCALIZED_STRING_BUNDLE__LOCALIZED_STRING_TABLES:
				return getLocalizedStringTables();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DmPackage.ILOCALIZED_STRING_BUNDLE__LOCALIZED_STRING_TABLES:
				getLocalizedStringTables().clear();
				getLocalizedStringTables().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case DmPackage.ILOCALIZED_STRING_BUNDLE__LOCALIZED_STRING_TABLES:
				getLocalizedStringTables().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DmPackage.ILOCALIZED_STRING_BUNDLE__LOCALIZED_STRING_TABLES:
				return localizedStringTables != null && !localizedStringTables.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	public ILocalizedStringTable findLocalizedStringTable(Language language) {
		if (language == null)
			return null;
		
		ILocalizedStringTable result = null;
		for (Iterator iter = getLocalizedStringTables().iterator(); iter.hasNext();) {
			ILocalizedStringTable lst = (ILocalizedStringTable) iter.next();
			if (lst.getLanguage().equals(language)) {
				result = lst;
				break;
			}
		}
		return result;
	}

	public String findString(Language language, String key) {
		String result = null;
		ILocalizedStringTable lst = findLocalizedStringTable(language);
		if (lst != null) {
			result = (String) lst.getStrings().get(key);
		}
		return result;
	}

	public Map findStrings(String key) {
		Map result = null;
		for (Iterator iter = localizedStringTables.iterator(); iter.hasNext();) {
			ILocalizedStringTable element = (ILocalizedStringTable) iter.next();
			if (element.getStrings().containsKey(key)) {
				Object curr = element.getStrings().get(key);
				if (result == null)
					result = new HashMap();
				result.put(element.getLanguage(), curr);
			}
		}
		return result;
	}

	public boolean hasStringTable(ILocalizedStringTable table) {
		return localizedStringTables.contains(table);
	}

	public boolean hasStringKey(String key) {
		boolean result = false;
		for (Iterator iter = localizedStringTables.iterator(); 
					iter.hasNext() && !result;) {
			ILocalizedStringTable lst = (ILocalizedStringTable) iter.next();
			result = lst.getStrings().containsKey(key);
		}
		return result;
	}

	public StringValue registerString(Language language, String key, String value) {
		Check.checkArg(language);
		if (key == null) {
			key = keyProvider.assignLocalizedStringKey();
		}
		ILocalizedStringTable lst = addLocalizedStringTable(language);
		lst.getStrings().put(key, value);
		return new StringValue(StringValue.LOCALIZED, key);
	}
	
	/**
	 * Get a valid table to use. Prefers the default language but will return any.
	 */
	private ILocalizedStringTable defaultOrAnyTable() {
		ILocalizedStringTable result = findLocalizedStringTable(defaultLanguage);
		if (result == null && localizedStringTables != null && localizedStringTables.size() > 0) {
			result = (ILocalizedStringTable) localizedStringTables.get(0);
		}
		return result;
	}
	
	private String makeUntranslatedString(String startValue) {
		if (startValue == null) {
			startValue = "";
		}
		Object params[] = {startValue};
		return MessageFormat.format(untranslatedStringTemplate, params);
	}

	public ILocalizedStringTable addLocalizedStringTable(Language language) {
		ILocalizedStringTable result = findLocalizedStringTable(language);
		if (result != null)
			return result;
		
		ILocalizedStringTable defaultTable = defaultOrAnyTable();
		result = DmFactory.eINSTANCE.createILocalizedStringTable();
		result.setLanguage(language);
		
		// Populate the new string table with entries for each extant key.
		// The initial value is a value based on the current value in the current (or first)
		// language, decorated to stand out as not yet translated.
		if (defaultTable != null) {
			for (Iterator iter = defaultTable.getStrings().iterator(); iter.hasNext();) {
				EStringToStringMapEntryImpl element = (EStringToStringMapEntryImpl) iter.next();	
				String initValue = makeUntranslatedString(element.getTypedValue());
				result.getStrings().put(element.getTypedKey(), initValue);
			}
		}
		getLocalizedStringTables().add(result);	
		return result;
	}

	public void setKeyProvider(IStringKeyProvider provider) {
		Check.checkArg(provider);
		keyProvider = provider;
	}
	
	public IStringKeyProvider getKeyProvider() {
		Check.checkState(keyProvider != null);
		return keyProvider;
	}

	public void setDefaultLanguage(Language language) {
		Check.checkArg(language);
		Language prevDefault = defaultLanguage;
		defaultLanguage = language;
		
		// Model synchronization should have ensured that
		// there's already a table for this language, but
		// the user could always modify things manually, so
		// ensure it here
		addLocalizedStringTable(defaultLanguage);
		
		if (!ObjectUtils.equals(prevDefault, language)) {
			fireDefaultLanguageChanged(language);
		}
	}

	public Language getDefaultLanguage() {
		return defaultLanguage;
	}

	public String getLocalizedStringDefault(String key) {
		ILocalizedStringTable lst = addLocalizedStringTable(defaultLanguage);
		String result = (String) lst.getStrings().get(key);
		return result;
	}
	
	public StringValue addLocalizedStringDefault(String value) {
		ILocalizedStringTable lst = addLocalizedStringTable(defaultLanguage);
		String key = keyProvider.assignLocalizedStringKey();
		Check.checkContract(!lst.getStrings().containsKey(key));

		return addAndSynch(lst, key, value);
	}

		// adds the string with the given key, value to the given string table,
		// and updates the other string tables by adding an empty string with
		// the same key.
	private StringValue addAndSynch(ILocalizedStringTable stringTable, String key, String value) {

		stringTable.getStrings().put(key, value);
		
		// insert special value for non-default languages
		String nonTranslatedValue = makeUntranslatedString(value);
		for (Iterator iter = localizedStringTables.iterator(); iter.hasNext();) {
			ILocalizedStringTable currTable = (ILocalizedStringTable) iter.next();
			if (currTable != stringTable) {
				Check.checkState(!currTable.getStrings().containsKey(key));
				currTable.getStrings().put(key, nonTranslatedValue);
			}
		}
		return new StringValue(StringValue.LOCALIZED, key);
	}

	public StringValue updateLocalizedStringDefault(String key, String value) {
		StringValue result;
		ILocalizedStringTable lst = addLocalizedStringTable(defaultLanguage);
		if (lst.getStrings().containsKey(key)) {
			lst.getStrings().put(key, value);
			result = new StringValue(StringValue.LOCALIZED, key);
		}
		else {
			result = addAndSynch(lst, key, value);
		}
		return result;
	}

	public void removeLocalizedStringAllLanguages(String key) {
		Check.checkArg(key);
		for (Iterator iter = localizedStringTables.iterator(); iter.hasNext();) {
			ILocalizedStringTable currTable = (ILocalizedStringTable) iter.next();
			currTable.getStrings().removeKey(key);
		}
	}

	public void addListener(IListener listener) {
		if (listeners == null) {
			listeners = new ListenerList<IListener>();
		}
		listeners.add(listener);
	}

	public void removeListener(IListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}
		
	private void fireTableAdded(ILocalizedStringTable table) {
		Language language = table.getLanguage();
		if (listeners != null) {
			for (IListener l : listeners) {
				l.stringTableAdded(language);
			}
		}
	}
	
	private void fireTableRemoved(ILocalizedStringTable table) {
		Language language = table.getLanguage();
		if (listeners != null) {
			for (IListener l : listeners) {
				l.stringTableRemoved(language);
			}
		}	
	}
	
	private void fireDefaultLanguageChanged(Language language) {
		if (listeners != null) {
			for (IListener l : listeners) {
				l.defaultLanguageChanged(language);
			}
		}	
	}
	
	static class ChildListener extends AdapterImpl {
		
		private void objectAdded(Object object) {
			if (object instanceof ILocalizedStringTable) {
				ILocalizedStringBundleImpl bundle = (ILocalizedStringBundleImpl) getTarget();
				bundle.fireTableAdded((ILocalizedStringTable)object);
			}
		}
		
		private void objectRemoved(Object object) {
			if (object instanceof ILocalizedStringTable) {
				ILocalizedStringBundleImpl bundle = (ILocalizedStringBundleImpl) getTarget();
				bundle.fireTableRemoved((ILocalizedStringTable)object);
			}
		}

		@Override
		public void notifyChanged(Notification msg) {
			switch (msg.getEventType()) {
			// child was added or removed
			case Notification.ADD:
				objectAdded(msg.getNewValue());
				break;
			case Notification.ADD_MANY: {
				EList items = (EList) msg.getNewValue();
				for (Iterator iter = items.iterator(); iter.hasNext();) {
					objectAdded(iter.next());
				}
				break;
			}
			
			case Notification.REMOVE:
				objectRemoved(msg.getOldValue());
				break;
				
			case Notification.REMOVE_MANY: {
				EList items = (EList) msg.getNewValue();
				for (Iterator iter = items.iterator(); iter.hasNext();) {
					objectRemoved(iter.next());
				}
				break;
			}
			}
		}
	}

	public Set<String> getUserGeneratedStringKeys() {
		if (userGeneratedKeys == null)
			userGeneratedKeys = new HashSet<String>();
		return userGeneratedKeys;
	}

} //ILocalizedStringBundleImpl
