/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.MapResourceType;

import com.nokia.sdt.emf.component.StandaloneType;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Resource Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MapResourceTypeImpl#getBaseName <em>Base Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MapResourceTypeImpl#getRssFile <em>Rss File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MapResourceTypeImpl#getStandalone <em>Standalone</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MapResourceTypeImpl#isUnnamed <em>Unnamed</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MapResourceTypeImpl extends MappingResourceTypeImpl implements MapResourceType {
	/**
	 * The default value of the '{@link #getBaseName() <em>Base Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseName()
	 * @generated
	 * @ordered
	 */
	protected static final String BASE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBaseName() <em>Base Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseName()
	 * @generated
	 * @ordered
	 */
	protected String baseName = BASE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getRssFile() <em>Rss File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRssFile()
	 * @generated
	 * @ordered
	 */
	protected static final String RSS_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRssFile() <em>Rss File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRssFile()
	 * @generated
	 * @ordered
	 */
	protected String rssFile = RSS_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getStandalone() <em>Standalone</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getStandalone()
	 * @generated
	 * @ordered
	 */
    protected static final StandaloneType STANDALONE_EDEFAULT = StandaloneType.DEFAULT_LITERAL;

	/**
	 * The cached value of the '{@link #getStandalone() <em>Standalone</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getStandalone()
	 * @generated
	 * @ordered
	 */
    protected StandaloneType standalone = STANDALONE_EDEFAULT;

	/**
	 * This is true if the Standalone attribute has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean standaloneESet;

	/**
	 * The default value of the '{@link #isUnnamed() <em>Unnamed</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isUnnamed()
	 * @generated
	 * @ordered
	 */
    protected static final boolean UNNAMED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUnnamed() <em>Unnamed</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isUnnamed()
	 * @generated
	 * @ordered
	 */
    protected boolean unnamed = UNNAMED_EDEFAULT;

	/**
	 * This is true if the Unnamed attribute has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean unnamedESet;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected MapResourceTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.MAP_RESOURCE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBaseName() {
		return baseName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseName(String newBaseName) {
		String oldBaseName = baseName;
		baseName = newBaseName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAP_RESOURCE_TYPE__BASE_NAME, oldBaseName, baseName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRssFile() {
		return rssFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRssFile(String newRssFile) {
		String oldRssFile = rssFile;
		rssFile = newRssFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAP_RESOURCE_TYPE__RSS_FILE, oldRssFile, rssFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StandaloneType getStandalone() {
		return standalone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStandalone(StandaloneType newStandalone) {
		StandaloneType oldStandalone = standalone;
		standalone = newStandalone == null ? STANDALONE_EDEFAULT : newStandalone;
		boolean oldStandaloneESet = standaloneESet;
		standaloneESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAP_RESOURCE_TYPE__STANDALONE, oldStandalone, standalone, !oldStandaloneESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetStandalone() {
		StandaloneType oldStandalone = standalone;
		boolean oldStandaloneESet = standaloneESet;
		standalone = STANDALONE_EDEFAULT;
		standaloneESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.MAP_RESOURCE_TYPE__STANDALONE, oldStandalone, STANDALONE_EDEFAULT, oldStandaloneESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetStandalone() {
		return standaloneESet;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isUnnamed() {
		return unnamed;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setUnnamed(boolean newUnnamed) {
		boolean oldUnnamed = unnamed;
		unnamed = newUnnamed;
		boolean oldUnnamedESet = unnamedESet;
		unnamedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAP_RESOURCE_TYPE__UNNAMED, oldUnnamed, unnamed, !oldUnnamedESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetUnnamed() {
		boolean oldUnnamed = unnamed;
		boolean oldUnnamedESet = unnamedESet;
		unnamed = UNNAMED_EDEFAULT;
		unnamedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.MAP_RESOURCE_TYPE__UNNAMED, oldUnnamed, UNNAMED_EDEFAULT, oldUnnamedESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetUnnamed() {
		return unnamedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.MAP_RESOURCE_TYPE__BASE_NAME:
				return getBaseName();
			case ComponentPackage.MAP_RESOURCE_TYPE__RSS_FILE:
				return getRssFile();
			case ComponentPackage.MAP_RESOURCE_TYPE__STANDALONE:
				return getStandalone();
			case ComponentPackage.MAP_RESOURCE_TYPE__UNNAMED:
				return isUnnamed() ? Boolean.TRUE : Boolean.FALSE;
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
			case ComponentPackage.MAP_RESOURCE_TYPE__BASE_NAME:
				setBaseName((String)newValue);
				return;
			case ComponentPackage.MAP_RESOURCE_TYPE__RSS_FILE:
				setRssFile((String)newValue);
				return;
			case ComponentPackage.MAP_RESOURCE_TYPE__STANDALONE:
				setStandalone((StandaloneType)newValue);
				return;
			case ComponentPackage.MAP_RESOURCE_TYPE__UNNAMED:
				setUnnamed(((Boolean)newValue).booleanValue());
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
			case ComponentPackage.MAP_RESOURCE_TYPE__BASE_NAME:
				setBaseName(BASE_NAME_EDEFAULT);
				return;
			case ComponentPackage.MAP_RESOURCE_TYPE__RSS_FILE:
				setRssFile(RSS_FILE_EDEFAULT);
				return;
			case ComponentPackage.MAP_RESOURCE_TYPE__STANDALONE:
				unsetStandalone();
				return;
			case ComponentPackage.MAP_RESOURCE_TYPE__UNNAMED:
				unsetUnnamed();
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
			case ComponentPackage.MAP_RESOURCE_TYPE__BASE_NAME:
				return BASE_NAME_EDEFAULT == null ? baseName != null : !BASE_NAME_EDEFAULT.equals(baseName);
			case ComponentPackage.MAP_RESOURCE_TYPE__RSS_FILE:
				return RSS_FILE_EDEFAULT == null ? rssFile != null : !RSS_FILE_EDEFAULT.equals(rssFile);
			case ComponentPackage.MAP_RESOURCE_TYPE__STANDALONE:
				return isSetStandalone();
			case ComponentPackage.MAP_RESOURCE_TYPE__UNNAMED:
				return isSetUnnamed();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (baseName: ");
		result.append(baseName);
		result.append(", rssFile: ");
		result.append(rssFile);
		result.append(", standalone: ");
		if (standaloneESet) result.append(standalone); else result.append("<unset>");
		result.append(", unnamed: ");
		if (unnamedESet) result.append(unnamed); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //MapResourceTypeImpl
