/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.osgi.framework.Version;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.emf.dm.DataModelPlugin;
import com.nokia.sdt.emf.dm.DmFactory;
import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.sdt.emf.dm.IComponentManifest;
import com.nokia.sdt.emf.dm.IComponentManifestEntry;
import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.emf.dm.INodeVisitor;
import com.nokia.sdt.emf.dm.ComponentManifestDelta;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IComponent Manifest</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IComponentManifestImpl#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IComponentManifestImpl extends EObjectImpl implements IComponentManifest {
	/**
	 * The cached value of the '{@link #getEntries() <em>Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntries()
	 * @generated
	 * @ordered
	 */
	protected EList entries = null;
	
	private boolean reflectsSave;
	
	private Map<String, ComponentManifestDelta> cachedDeltas;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IComponentManifestImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.Literals.ICOMPONENT_MANIFEST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getEntries() {
		if (entries == null) {
			entries = new EObjectContainmentEList(IComponentManifestEntry.class, this, DmPackage.ICOMPONENT_MANIFEST__ENTRIES);
		}
		return entries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DmPackage.ICOMPONENT_MANIFEST__ENTRIES:
				return ((InternalEList)getEntries()).basicRemove(otherEnd, msgs);
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
			case DmPackage.ICOMPONENT_MANIFEST__ENTRIES:
				return getEntries();
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
			case DmPackage.ICOMPONENT_MANIFEST__ENTRIES:
				getEntries().clear();
				getEntries().addAll((Collection)newValue);
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
			case DmPackage.ICOMPONENT_MANIFEST__ENTRIES:
				getEntries().clear();
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
			case DmPackage.ICOMPONENT_MANIFEST__ENTRIES:
				return entries != null && !entries.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	class NodeVisitor implements INodeVisitor {
		LinkedHashSet<IComponent> components = new LinkedHashSet<IComponent>();
		public Object visit(INode node) {
			IComponent component = node.getComponent();
			while (component != null) {
				components.add(component);
				component = component.getComponentBase();
			}
			return null;
		}
	}

	public void update(IDesignerData root) {
		NodeVisitor visitor = new NodeVisitor();
		root.visitNodes(visitor);
		EList entries = getEntries();
		reflectsSave = false;
		entries.clear();
		for (IComponent component : visitor.components) {
			IComponentManifestEntry entry = DmFactory.eINSTANCE.createIComponentManifestEntry();
			entry.setComponentID(component.getId());
			entry.setVersion(component.getComponentVersion());
			entries.add(entry);
		}
		cachedDeltas = null;
	}

	public IComponentManifestEntry lookup(String componentID) {
		IComponentManifestEntry result = null;
		for (Iterator iter = getEntries().iterator(); iter.hasNext();) {
			IComponentManifestEntry entry = (IComponentManifestEntry) iter.next();
			if (entry.getComponentID().equals(componentID)) {
				result = entry;
				break;
			}
		}
 		return result;
	}

	public boolean reflectsLastSave() {
		return reflectsSave;
	}

	public void markSaved() {
		reflectsSave = true;
	}

	public Map<String, ComponentManifestDelta> getComponentDeltas(IDesignerData designerData) {
		if (cachedDeltas != null)
			return cachedDeltas;
		
		IComponentSet componentSet = designerData.getComponentHelper().getComponentSet();
		Map<String, ComponentManifestDelta> map = new HashMap<String, ComponentManifestDelta>();
		EList entries = getEntries();
		if (entries.size() > 0) {
			for (Iterator iter = entries.iterator(); iter.hasNext();) {
				IComponentManifestEntry entry = (IComponentManifestEntry) iter.next();
				IComponent component = componentSet.lookupComponent(entry.getComponentID());
				if (component != null) {
					if (component.getComponentVersion() == null || entry.getVersion() == null) {
						Logging.log(DataModelPlugin.getDefault(),
								Logging.newStatus(DataModelPlugin.getDefault(), IStatus.ERROR, 
										"The component '" + component.getId() + "' does not define a version")); //$NON-NLS-1$ //$NON-NLS-2$
					} else {
						int compare = entry.getVersion().compareTo(component.getComponentVersion());
						if (compare != 0) {
							ComponentManifestDelta delta = new ComponentManifestDelta(component.getId(),
									compare < 0? ComponentManifestDelta.NEWER : ComponentManifestDelta.OLDER,
									entry.getVersion(), component.getComponentVersion());
							map.put(component.getId(), delta);
						}
					}
				} else {
					map.put(entry.getComponentID(), 
							new ComponentManifestDelta(entry.getComponentID(), 
									ComponentManifestDelta.MISSING, entry.getVersion(), null));
				}
			}		
		} else {
			// The initial model format had no manifest. For that version, we
			// assume each component version was at 1.0
			Version noManifestVersion = new Version("1.0.0");
			update(designerData);
			for (Iterator iter = entries.iterator(); iter.hasNext();) {
				IComponentManifestEntry entry = (IComponentManifestEntry) iter.next();
				int compare = noManifestVersion.compareTo(entry.getVersion());
				if (compare != 0) {
					ComponentManifestDelta delta = new ComponentManifestDelta(entry.getComponentID(),
							compare < 0? ComponentManifestDelta.NEWER : ComponentManifestDelta.OLDER,
									noManifestVersion, entry.getVersion());
					map.put(entry.getComponentID(), delta);
				}
			}			
		}
		cachedDeltas = map;
		return map.size() > 0? map : null;
	}
	

} //IComponentManifestImpl
