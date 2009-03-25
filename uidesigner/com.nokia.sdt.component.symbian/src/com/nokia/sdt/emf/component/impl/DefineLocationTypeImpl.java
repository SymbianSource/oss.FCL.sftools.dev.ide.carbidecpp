/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.DefineLocationType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Define Location Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getInline <em>Inline</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getScript <em>Script</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getExpandMacro <em>Expand Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getBaseLocation <em>Base Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getFile <em>File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getFilter <em>Filter</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getIfEvents <em>If Events</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getIsEventHandler <em>Is Event Handler</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getOwned <em>Owned</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl#getRealize <em>Realize</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DefineLocationTypeImpl extends EObjectImpl implements DefineLocationType {
	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
    protected FeatureMap group;

	/**
	 * The default value of the '{@link #getBaseLocation() <em>Base Location</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBaseLocation()
	 * @generated
	 * @ordered
	 */
    protected static final String BASE_LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBaseLocation() <em>Base Location</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBaseLocation()
	 * @generated
	 * @ordered
	 */
    protected String baseLocation = BASE_LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDir() <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDir()
	 * @generated
	 * @ordered
	 */
    protected static final String DIR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDir() <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDir()
	 * @generated
	 * @ordered
	 */
    protected String dir = DIR_EDEFAULT;

	/**
	 * The default value of the '{@link #getDomain() <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
    protected static final String DOMAIN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDomain() <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
    protected String domain = DOMAIN_EDEFAULT;

	/**
	 * The default value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
    protected static final String FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
    protected String file = FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getFilter() <em>Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFilter()
	 * @generated
	 * @ordered
	 */
    protected static final String FILTER_EDEFAULT = "default";

	/**
	 * The cached value of the '{@link #getFilter() <em>Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFilter()
	 * @generated
	 * @ordered
	 */
    protected String filter = FILTER_EDEFAULT;

	/**
	 * This is true if the Filter attribute has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean filterESet;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
    protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
    protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getIfEvents() <em>If Events</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIfEvents()
	 * @generated
	 * @ordered
	 */
	protected static final List IF_EVENTS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIfEvents() <em>If Events</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIfEvents()
	 * @generated
	 * @ordered
	 */
	protected List ifEvents = IF_EVENTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getIsEventHandler() <em>Is Event Handler</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsEventHandler()
	 * @generated
	 * @ordered
	 */
	protected static final String IS_EVENT_HANDLER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsEventHandler() <em>Is Event Handler</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsEventHandler()
	 * @generated
	 * @ordered
	 */
	protected String isEventHandler = IS_EVENT_HANDLER_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
    protected static final String LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
    protected String location = LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getOwned() <em>Owned</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getOwned()
	 * @generated
	 * @ordered
	 */
    protected static final String OWNED_EDEFAULT = "true";

	/**
	 * The cached value of the '{@link #getOwned() <em>Owned</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getOwned()
	 * @generated
	 * @ordered
	 */
    protected String owned = OWNED_EDEFAULT;

	/**
	 * This is true if the Owned attribute has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean ownedESet;

	/**
	 * The default value of the '{@link #getRealize() <em>Realize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRealize()
	 * @generated
	 * @ordered
	 */
	protected static final String REALIZE_EDEFAULT = "false";

	/**
	 * The cached value of the '{@link #getRealize() <em>Realize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRealize()
	 * @generated
	 * @ordered
	 */
	protected String realize = REALIZE_EDEFAULT;

	/**
	 * This is true if the Realize attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean realizeESet;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected DefineLocationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.DEFINE_LOCATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, ComponentPackage.DEFINE_LOCATION_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getTemplate() {
		return getGroup().list(ComponentPackage.Literals.DEFINE_LOCATION_TYPE__TEMPLATE);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getInline() {
		return getGroup().list(ComponentPackage.Literals.DEFINE_LOCATION_TYPE__INLINE);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getScript() {
		return getGroup().list(ComponentPackage.Literals.DEFINE_LOCATION_TYPE__SCRIPT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getExpandMacro() {
		return getGroup().list(ComponentPackage.Literals.DEFINE_LOCATION_TYPE__EXPAND_MACRO);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getBaseLocation() {
		return baseLocation;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setBaseLocation(String newBaseLocation) {
		String oldBaseLocation = baseLocation;
		baseLocation = newBaseLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_LOCATION_TYPE__BASE_LOCATION, oldBaseLocation, baseLocation));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDir() {
		return dir;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDir(String newDir) {
		String oldDir = dir;
		dir = newDir;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_LOCATION_TYPE__DIR, oldDir, dir));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDomain() {
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDomain(String newDomain) {
		String oldDomain = domain;
		domain = newDomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_LOCATION_TYPE__DOMAIN, oldDomain, domain));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getFile() {
		return file;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setFile(String newFile) {
		String oldFile = file;
		file = newFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_LOCATION_TYPE__FILE, oldFile, file));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getFilter() {
		return filter;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setFilter(String newFilter) {
		String oldFilter = filter;
		filter = newFilter;
		boolean oldFilterESet = filterESet;
		filterESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_LOCATION_TYPE__FILTER, oldFilter, filter, !oldFilterESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetFilter() {
		String oldFilter = filter;
		boolean oldFilterESet = filterESet;
		filter = FILTER_EDEFAULT;
		filterESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.DEFINE_LOCATION_TYPE__FILTER, oldFilter, FILTER_EDEFAULT, oldFilterESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetFilter() {
		return filterESet;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_LOCATION_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getIfEvents() {
		return ifEvents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIfEvents(List newIfEvents) {
		List oldIfEvents = ifEvents;
		ifEvents = newIfEvents;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_LOCATION_TYPE__IF_EVENTS, oldIfEvents, ifEvents));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIsEventHandler() {
		return isEventHandler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsEventHandler(String newIsEventHandler) {
		String oldIsEventHandler = isEventHandler;
		isEventHandler = newIsEventHandler;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_LOCATION_TYPE__IS_EVENT_HANDLER, oldIsEventHandler, isEventHandler));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setLocation(String newLocation) {
		String oldLocation = location;
		location = newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_LOCATION_TYPE__LOCATION, oldLocation, location));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOwned() {
		return owned;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwned(String newOwned) {
		String oldOwned = owned;
		owned = newOwned;
		boolean oldOwnedESet = ownedESet;
		ownedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_LOCATION_TYPE__OWNED, oldOwned, owned, !oldOwnedESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetOwned() {
		String oldOwned = owned;
		boolean oldOwnedESet = ownedESet;
		owned = OWNED_EDEFAULT;
		ownedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.DEFINE_LOCATION_TYPE__OWNED, oldOwned, OWNED_EDEFAULT, oldOwnedESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetOwned() {
		return ownedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRealize() {
		return realize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRealize(String newRealize) {
		String oldRealize = realize;
		realize = newRealize;
		boolean oldRealizeESet = realizeESet;
		realizeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_LOCATION_TYPE__REALIZE, oldRealize, realize, !oldRealizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRealize() {
		String oldRealize = realize;
		boolean oldRealizeESet = realizeESet;
		realize = REALIZE_EDEFAULT;
		realizeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.DEFINE_LOCATION_TYPE__REALIZE, oldRealize, REALIZE_EDEFAULT, oldRealizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRealize() {
		return realizeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.DEFINE_LOCATION_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DEFINE_LOCATION_TYPE__TEMPLATE:
				return ((InternalEList)getTemplate()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DEFINE_LOCATION_TYPE__INLINE:
				return ((InternalEList)getInline()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DEFINE_LOCATION_TYPE__SCRIPT:
				return ((InternalEList)getScript()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DEFINE_LOCATION_TYPE__EXPAND_MACRO:
				return ((InternalEList)getExpandMacro()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.DEFINE_LOCATION_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case ComponentPackage.DEFINE_LOCATION_TYPE__TEMPLATE:
				return getTemplate();
			case ComponentPackage.DEFINE_LOCATION_TYPE__INLINE:
				return getInline();
			case ComponentPackage.DEFINE_LOCATION_TYPE__SCRIPT:
				return getScript();
			case ComponentPackage.DEFINE_LOCATION_TYPE__EXPAND_MACRO:
				return getExpandMacro();
			case ComponentPackage.DEFINE_LOCATION_TYPE__BASE_LOCATION:
				return getBaseLocation();
			case ComponentPackage.DEFINE_LOCATION_TYPE__DIR:
				return getDir();
			case ComponentPackage.DEFINE_LOCATION_TYPE__DOMAIN:
				return getDomain();
			case ComponentPackage.DEFINE_LOCATION_TYPE__FILE:
				return getFile();
			case ComponentPackage.DEFINE_LOCATION_TYPE__FILTER:
				return getFilter();
			case ComponentPackage.DEFINE_LOCATION_TYPE__ID:
				return getId();
			case ComponentPackage.DEFINE_LOCATION_TYPE__IF_EVENTS:
				return getIfEvents();
			case ComponentPackage.DEFINE_LOCATION_TYPE__IS_EVENT_HANDLER:
				return getIsEventHandler();
			case ComponentPackage.DEFINE_LOCATION_TYPE__LOCATION:
				return getLocation();
			case ComponentPackage.DEFINE_LOCATION_TYPE__OWNED:
				return getOwned();
			case ComponentPackage.DEFINE_LOCATION_TYPE__REALIZE:
				return getRealize();
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
			case ComponentPackage.DEFINE_LOCATION_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__TEMPLATE:
				getTemplate().clear();
				getTemplate().addAll((Collection)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__INLINE:
				getInline().clear();
				getInline().addAll((Collection)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__SCRIPT:
				getScript().clear();
				getScript().addAll((Collection)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__EXPAND_MACRO:
				getExpandMacro().clear();
				getExpandMacro().addAll((Collection)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__BASE_LOCATION:
				setBaseLocation((String)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__DIR:
				setDir((String)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__DOMAIN:
				setDomain((String)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__FILE:
				setFile((String)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__FILTER:
				setFilter((String)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__ID:
				setId((String)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__IF_EVENTS:
				setIfEvents((List)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__IS_EVENT_HANDLER:
				setIsEventHandler((String)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__LOCATION:
				setLocation((String)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__OWNED:
				setOwned((String)newValue);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__REALIZE:
				setRealize((String)newValue);
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
			case ComponentPackage.DEFINE_LOCATION_TYPE__GROUP:
				getGroup().clear();
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__TEMPLATE:
				getTemplate().clear();
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__INLINE:
				getInline().clear();
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__SCRIPT:
				getScript().clear();
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__EXPAND_MACRO:
				getExpandMacro().clear();
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__BASE_LOCATION:
				setBaseLocation(BASE_LOCATION_EDEFAULT);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__DIR:
				setDir(DIR_EDEFAULT);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__DOMAIN:
				setDomain(DOMAIN_EDEFAULT);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__FILE:
				setFile(FILE_EDEFAULT);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__FILTER:
				unsetFilter();
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__IF_EVENTS:
				setIfEvents(IF_EVENTS_EDEFAULT);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__IS_EVENT_HANDLER:
				setIsEventHandler(IS_EVENT_HANDLER_EDEFAULT);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__OWNED:
				unsetOwned();
				return;
			case ComponentPackage.DEFINE_LOCATION_TYPE__REALIZE:
				unsetRealize();
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
			case ComponentPackage.DEFINE_LOCATION_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case ComponentPackage.DEFINE_LOCATION_TYPE__TEMPLATE:
				return !getTemplate().isEmpty();
			case ComponentPackage.DEFINE_LOCATION_TYPE__INLINE:
				return !getInline().isEmpty();
			case ComponentPackage.DEFINE_LOCATION_TYPE__SCRIPT:
				return !getScript().isEmpty();
			case ComponentPackage.DEFINE_LOCATION_TYPE__EXPAND_MACRO:
				return !getExpandMacro().isEmpty();
			case ComponentPackage.DEFINE_LOCATION_TYPE__BASE_LOCATION:
				return BASE_LOCATION_EDEFAULT == null ? baseLocation != null : !BASE_LOCATION_EDEFAULT.equals(baseLocation);
			case ComponentPackage.DEFINE_LOCATION_TYPE__DIR:
				return DIR_EDEFAULT == null ? dir != null : !DIR_EDEFAULT.equals(dir);
			case ComponentPackage.DEFINE_LOCATION_TYPE__DOMAIN:
				return DOMAIN_EDEFAULT == null ? domain != null : !DOMAIN_EDEFAULT.equals(domain);
			case ComponentPackage.DEFINE_LOCATION_TYPE__FILE:
				return FILE_EDEFAULT == null ? file != null : !FILE_EDEFAULT.equals(file);
			case ComponentPackage.DEFINE_LOCATION_TYPE__FILTER:
				return isSetFilter();
			case ComponentPackage.DEFINE_LOCATION_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case ComponentPackage.DEFINE_LOCATION_TYPE__IF_EVENTS:
				return IF_EVENTS_EDEFAULT == null ? ifEvents != null : !IF_EVENTS_EDEFAULT.equals(ifEvents);
			case ComponentPackage.DEFINE_LOCATION_TYPE__IS_EVENT_HANDLER:
				return IS_EVENT_HANDLER_EDEFAULT == null ? isEventHandler != null : !IS_EVENT_HANDLER_EDEFAULT.equals(isEventHandler);
			case ComponentPackage.DEFINE_LOCATION_TYPE__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case ComponentPackage.DEFINE_LOCATION_TYPE__OWNED:
				return isSetOwned();
			case ComponentPackage.DEFINE_LOCATION_TYPE__REALIZE:
				return isSetRealize();
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
		result.append(" (group: ");
		result.append(group);
		result.append(", baseLocation: ");
		result.append(baseLocation);
		result.append(", dir: ");
		result.append(dir);
		result.append(", domain: ");
		result.append(domain);
		result.append(", file: ");
		result.append(file);
		result.append(", filter: ");
		if (filterESet) result.append(filter); else result.append("<unset>");
		result.append(", id: ");
		result.append(id);
		result.append(", ifEvents: ");
		result.append(ifEvents);
		result.append(", isEventHandler: ");
		result.append(isEventHandler);
		result.append(", location: ");
		result.append(location);
		result.append(", owned: ");
		if (ownedESet) result.append(owned); else result.append("<unset>");
		result.append(", realize: ");
		if (realizeESet) result.append(realize); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //DefineLocationTypeImpl
