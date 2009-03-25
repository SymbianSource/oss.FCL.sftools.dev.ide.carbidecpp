/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.sdt.emf.dm.IComponentManifest;
import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.emf.dm.IGeneratedFiles;
import com.nokia.sdt.emf.dm.ILocalizedStringBundle;
import com.nokia.sdt.emf.dm.IMacroStringTable;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.emf.dm.IPropertyContainer;
import com.nokia.sdt.emf.dm.ISourceGenMappingState;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.emf.dm.*;
import com.nokia.cpp.internal.api.utils.core.ListenerList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.osgi.framework.Version;

import java.util.Collection;
import java.util.Iterator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IDesigner Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IDesignerDataImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IDesignerDataImpl#getComponentManifest <em>Component Manifest</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IDesignerDataImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IDesignerDataImpl#getRootContainers <em>Root Containers</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IDesignerDataImpl#getStringBundle <em>String Bundle</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IDesignerDataImpl#getMacroTable <em>Macro Table</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IDesignerDataImpl#getSourceMappingState <em>Source Mapping State</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IDesignerDataImpl#getGeneratedFiles <em>Generated Files</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IDesignerDataImpl extends EObjectImpl implements IDesignerData {
	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated NOT
	 * @ordered
	 */
	protected static final Version VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected Version version = VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getComponentManifest() <em>Component Manifest</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentManifest()
	 * @generated
	 * @ordered
	 */
	protected IComponentManifest componentManifest = null;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected IPropertyContainer properties = null;

	/**
	 * The cached value of the '{@link #getRootContainers() <em>Root Containers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootContainers()
	 * @generated
	 * @ordered
	 */
	protected EList rootContainers = null;

	/**
	 * The cached value of the '{@link #getStringBundle() <em>String Bundle</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringBundle()
	 * @generated
	 * @ordered
	 */
	protected ILocalizedStringBundle stringBundle = null;

	/**
	 * The cached value of the '{@link #getMacroTable() <em>Macro Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMacroTable()
	 * @generated
	 * @ordered
	 */
	protected IMacroStringTable macroTable = null;

	/**
	 * The cached value of the '{@link #getSourceMappingState() <em>Source Mapping State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceMappingState()
	 * @generated
	 * @ordered
	 */
	protected ISourceGenMappingState sourceMappingState = null;

	/**
	 * The cached value of the '{@link #getGeneratedFiles() <em>Generated Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGeneratedFiles()
	 * @generated
	 * @ordered
	 */
	protected IGeneratedFiles generatedFiles = null;

	private IDesignerDataModel designerDataModel;
	private ComponentHelper componentHelper;
	private INodeNameProvider nodeNameProvider = new DefaultNodeNameProvider();
    private ListenerList<IModelPropertyListener> modelPropertyListeners;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IDesignerDataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.Literals.IDESIGNER_DATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ILocalizedStringBundle getStringBundle() {
		if (stringBundle == null) {
			stringBundle = DmFactory.eINSTANCE.createILocalizedStringBundle();
			stringBundle.setKeyProvider(new DefaultStringKeyProvider(this));
		}
		return stringBundle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public NotificationChain basicSetStringBundle(ILocalizedStringBundle newStringBundle, NotificationChain msgs) {
		ILocalizedStringBundle oldStringBundle = stringBundle;
		stringBundle = newStringBundle;
		stringBundle.setKeyProvider(new DefaultStringKeyProvider(this));
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__STRING_BUNDLE, oldStringBundle, newStringBundle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringBundle(ILocalizedStringBundle newStringBundle) {
		if (newStringBundle != stringBundle) {
			NotificationChain msgs = null;
			if (stringBundle != null)
				msgs = ((InternalEObject)stringBundle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DmPackage.IDESIGNER_DATA__STRING_BUNDLE, null, msgs);
			if (newStringBundle != null)
				msgs = ((InternalEObject)newStringBundle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DmPackage.IDESIGNER_DATA__STRING_BUNDLE, null, msgs);
			msgs = basicSetStringBundle(newStringBundle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__STRING_BUNDLE, newStringBundle, newStringBundle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public IMacroStringTable getMacroTable() {
		if (macroTable == null) {
			macroTable = DmFactory.eINSTANCE.createIMacroStringTable();
		}
        if (macroTable.getKeyProvider() == null)
            macroTable.setKeyProvider(new DefaultStringKeyProvider(this));
		return macroTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMacroTable(IMacroStringTable newMacroTable, NotificationChain msgs) {
		IMacroStringTable oldMacroTable = macroTable;
		macroTable = newMacroTable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__MACRO_TABLE, oldMacroTable, newMacroTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMacroTable(IMacroStringTable newMacroTable) {
		if (newMacroTable != macroTable) {
			NotificationChain msgs = null;
			if (macroTable != null)
				msgs = ((InternalEObject)macroTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DmPackage.IDESIGNER_DATA__MACRO_TABLE, null, msgs);
			if (newMacroTable != null)
				msgs = ((InternalEObject)newMacroTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DmPackage.IDESIGNER_DATA__MACRO_TABLE, null, msgs);
			msgs = basicSetMacroTable(newMacroTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__MACRO_TABLE, newMacroTable, newMacroTable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISourceGenMappingState getSourceMappingState() {
		return sourceMappingState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSourceMappingState(ISourceGenMappingState newSourceMappingState, NotificationChain msgs) {
		ISourceGenMappingState oldSourceMappingState = sourceMappingState;
		sourceMappingState = newSourceMappingState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__SOURCE_MAPPING_STATE, oldSourceMappingState, newSourceMappingState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceMappingState(ISourceGenMappingState newSourceMappingState) {
		if (newSourceMappingState != sourceMappingState) {
			NotificationChain msgs = null;
			if (sourceMappingState != null)
				msgs = ((InternalEObject)sourceMappingState).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DmPackage.IDESIGNER_DATA__SOURCE_MAPPING_STATE, null, msgs);
			if (newSourceMappingState != null)
				msgs = ((InternalEObject)newSourceMappingState).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DmPackage.IDESIGNER_DATA__SOURCE_MAPPING_STATE, null, msgs);
			msgs = basicSetSourceMappingState(newSourceMappingState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__SOURCE_MAPPING_STATE, newSourceMappingState, newSourceMappingState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public IGeneratedFiles getGeneratedFiles() {
		if (generatedFiles == null) {
			generatedFiles = DmFactory.eINSTANCE.createIGeneratedFiles();
		}
		return generatedFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGeneratedFiles(IGeneratedFiles newGeneratedFiles, NotificationChain msgs) {
		IGeneratedFiles oldGeneratedFiles = generatedFiles;
		generatedFiles = newGeneratedFiles;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__GENERATED_FILES, oldGeneratedFiles, newGeneratedFiles);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGeneratedFiles(IGeneratedFiles newGeneratedFiles) {
		if (newGeneratedFiles != generatedFiles) {
			NotificationChain msgs = null;
			if (generatedFiles != null)
				msgs = ((InternalEObject)generatedFiles).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DmPackage.IDESIGNER_DATA__GENERATED_FILES, null, msgs);
			if (newGeneratedFiles != null)
				msgs = ((InternalEObject)newGeneratedFiles).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DmPackage.IDESIGNER_DATA__GENERATED_FILES, null, msgs);
			msgs = basicSetGeneratedFiles(newGeneratedFiles, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__GENERATED_FILES, newGeneratedFiles, newGeneratedFiles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DmPackage.IDESIGNER_DATA__COMPONENT_MANIFEST:
				return basicSetComponentManifest(null, msgs);
			case DmPackage.IDESIGNER_DATA__PROPERTIES:
				return basicSetProperties(null, msgs);
			case DmPackage.IDESIGNER_DATA__ROOT_CONTAINERS:
				return ((InternalEList)getRootContainers()).basicRemove(otherEnd, msgs);
			case DmPackage.IDESIGNER_DATA__STRING_BUNDLE:
				return basicSetStringBundle(null, msgs);
			case DmPackage.IDESIGNER_DATA__MACRO_TABLE:
				return basicSetMacroTable(null, msgs);
			case DmPackage.IDESIGNER_DATA__SOURCE_MAPPING_STATE:
				return basicSetSourceMappingState(null, msgs);
			case DmPackage.IDESIGNER_DATA__GENERATED_FILES:
				return basicSetGeneratedFiles(null, msgs);
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
			case DmPackage.IDESIGNER_DATA__VERSION:
				return getVersion();
			case DmPackage.IDESIGNER_DATA__COMPONENT_MANIFEST:
				return getComponentManifest();
			case DmPackage.IDESIGNER_DATA__PROPERTIES:
				return getProperties();
			case DmPackage.IDESIGNER_DATA__ROOT_CONTAINERS:
				return getRootContainers();
			case DmPackage.IDESIGNER_DATA__STRING_BUNDLE:
				return getStringBundle();
			case DmPackage.IDESIGNER_DATA__MACRO_TABLE:
				return getMacroTable();
			case DmPackage.IDESIGNER_DATA__SOURCE_MAPPING_STATE:
				return getSourceMappingState();
			case DmPackage.IDESIGNER_DATA__GENERATED_FILES:
				return getGeneratedFiles();
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
			case DmPackage.IDESIGNER_DATA__VERSION:
				setVersion((Version)newValue);
				return;
			case DmPackage.IDESIGNER_DATA__COMPONENT_MANIFEST:
				setComponentManifest((IComponentManifest)newValue);
				return;
			case DmPackage.IDESIGNER_DATA__ROOT_CONTAINERS:
				getRootContainers().clear();
				getRootContainers().addAll((Collection)newValue);
				return;
			case DmPackage.IDESIGNER_DATA__STRING_BUNDLE:
				setStringBundle((ILocalizedStringBundle)newValue);
				return;
			case DmPackage.IDESIGNER_DATA__MACRO_TABLE:
				setMacroTable((IMacroStringTable)newValue);
				return;
			case DmPackage.IDESIGNER_DATA__SOURCE_MAPPING_STATE:
				setSourceMappingState((ISourceGenMappingState)newValue);
				return;
			case DmPackage.IDESIGNER_DATA__GENERATED_FILES:
				setGeneratedFiles((IGeneratedFiles)newValue);
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
			case DmPackage.IDESIGNER_DATA__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case DmPackage.IDESIGNER_DATA__COMPONENT_MANIFEST:
				setComponentManifest((IComponentManifest)null);
				return;
			case DmPackage.IDESIGNER_DATA__ROOT_CONTAINERS:
				getRootContainers().clear();
				return;
			case DmPackage.IDESIGNER_DATA__STRING_BUNDLE:
				setStringBundle((ILocalizedStringBundle)null);
				return;
			case DmPackage.IDESIGNER_DATA__MACRO_TABLE:
				setMacroTable((IMacroStringTable)null);
				return;
			case DmPackage.IDESIGNER_DATA__SOURCE_MAPPING_STATE:
				setSourceMappingState((ISourceGenMappingState)null);
				return;
			case DmPackage.IDESIGNER_DATA__GENERATED_FILES:
				setGeneratedFiles((IGeneratedFiles)null);
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
			case DmPackage.IDESIGNER_DATA__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case DmPackage.IDESIGNER_DATA__COMPONENT_MANIFEST:
				return componentManifest != null;
			case DmPackage.IDESIGNER_DATA__PROPERTIES:
				return properties != null;
			case DmPackage.IDESIGNER_DATA__ROOT_CONTAINERS:
				return rootContainers != null && !rootContainers.isEmpty();
			case DmPackage.IDESIGNER_DATA__STRING_BUNDLE:
				return stringBundle != null;
			case DmPackage.IDESIGNER_DATA__MACRO_TABLE:
				return macroTable != null;
			case DmPackage.IDESIGNER_DATA__SOURCE_MAPPING_STATE:
				return sourceMappingState != null;
			case DmPackage.IDESIGNER_DATA__GENERATED_FILES:
				return generatedFiles != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public IComponentManifest getComponentManifest() {
		if (componentManifest == null) {
			componentManifest = DmFactory.eINSTANCE.createIComponentManifest();
		}
		return componentManifest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComponentManifest(IComponentManifest newComponentManifest, NotificationChain msgs) {
		IComponentManifest oldComponentManifest = componentManifest;
		componentManifest = newComponentManifest;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__COMPONENT_MANIFEST, oldComponentManifest, newComponentManifest);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentManifest(IComponentManifest newComponentManifest) {
		if (newComponentManifest != componentManifest) {
			NotificationChain msgs = null;
			if (componentManifest != null)
				msgs = ((InternalEObject)componentManifest).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DmPackage.IDESIGNER_DATA__COMPONENT_MANIFEST, null, msgs);
			if (newComponentManifest != null)
				msgs = ((InternalEObject)newComponentManifest).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DmPackage.IDESIGNER_DATA__COMPONENT_MANIFEST, null, msgs);
			msgs = basicSetComponentManifest(newComponentManifest, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__COMPONENT_MANIFEST, newComponentManifest, newComponentManifest));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Version getVersion() {
		return version;
	}

	public void setCurrentVersion() {
		setVersion(new Version("1.1.0"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(Version newVersion) {
		Version oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRootContainers() {
		if (rootContainers == null) {
			rootContainers = new EObjectContainmentEList(INode.class, this, DmPackage.IDESIGNER_DATA__ROOT_CONTAINERS);
		}
		return rootContainers;
	}

	public void garbageCollectStrings() {
		StringGarbageCollector sgc = new StringGarbageCollector();
		sgc.gc(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public IPropertyContainer getProperties() {
		if (properties == null) {
			properties = DmFactory.eINSTANCE.createIPropertyContainer();
			IPropertyContainerImpl impl = (IPropertyContainerImpl) properties;
			impl.setOwner(this);
			
			impl.setPropertyChangeListener(new IInternalPropertyChangeListener() {

				public boolean queryPropertyChange(Object propertyId, Object newValue) {
					return true;
				}

				public void propertyChanged(Object propertyId, IPropertyValue oldValue, IPropertyValue newValue) {
					String strValue = null;
					if (newValue != null && newValue.hasStringValue()) {
						strValue = newValue.getStringValue().getValue();
					}
					firePropertyChanged(propertyId.toString(), strValue);
				}
			});
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(IPropertyContainer newProperties, NotificationChain msgs) {
		IPropertyContainer oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DmPackage.IDESIGNER_DATA__PROPERTIES, oldProperties, newProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

	public void setComponentHelper(ComponentHelper helper) {
		componentHelper = helper;
	}
	
	public ComponentHelper getComponentHelper() {
		return componentHelper;
	}

	public INodeNameProvider getNodeNameProvider() {
		return nodeNameProvider;
	}

	public void setNodeNameProvider(INodeNameProvider provider) {
		nodeNameProvider = provider;	
	}

	public void setDesignerDataModel(IDesignerDataModel dataModel) {
		this.designerDataModel = dataModel;
	}
	public IDesignerDataModel getDesignerDataModel() {
		return designerDataModel;
	}

	public Object visitNodes(INodeVisitor visitor) {
		Object result = null;
		EList roots = getRootContainers();
		for (Iterator iter = roots.iterator(); iter.hasNext();) {
			INode root = (INode) iter.next();
			result = root.visitPreorder(visitor);
			if (result != null)
				break;
		}
		return result;
	}
	
	public INode findByNameProperty(final String name) {
		if (name == null) return null;
		INodeVisitor visitor = new INodeVisitor() {
			public Object visit(INode node) {
				if (name.equals(node.getName())) {
					return node;
				}
				return null;
			}
		};
		return (INode) visitNodes(visitor);	
	}
	
	private void firePropertyChanged(String propertyId, String value) {
		if (modelPropertyListeners != null) {
			for (IModelPropertyListener l : modelPropertyListeners) {
				l.propertyChanged(propertyId, value);
			}
		}
	}
	
	public void addModelPropertyListener(IModelPropertyListener listener) {
		if (modelPropertyListeners == null) {
			modelPropertyListeners = new ListenerList<IModelPropertyListener>();
		}
		modelPropertyListeners.add(listener);
	}
	
	public void removeModelPropertyListener(IModelPropertyListener listener) {
		if (modelPropertyListeners != null) {
			modelPropertyListeners.remove(listener);
		}
	}

} //IDesignerDataImpl
