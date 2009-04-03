/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.emf.dm;

import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Version;

/**
 * @model
 */
public interface IDesignerData extends EObject {
	
	// Listener interface for changes to model string properties. 
    // these are string properties belonging to the designer data node
    public interface IModelPropertyListener  {
    	void propertyChanged(String propertyId, String propertyValue);
    }
    
    /**
     * @model dataType="org.osgi.framework.Version"
     * This is used for versioning the XML format
     */
    Version getVersion();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IDesignerData#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(Version value);

	/**
	 * Set the version attribute to reflect the current version,
	 * as opposed to whatever value existed when the model was loaded
	 */
	void setCurrentVersion();
	/**
	 * @model changeable="false" containment="true"
	 */
	IPropertyContainer getProperties();
	
	/**
	 * @model type="com.nokia.sdt.emf.dm.INode"
	 * containment="true"
	 */
	EList getRootContainers();
	
	/**
	 * @model containment="true"
	 */
	ILocalizedStringBundle getStringBundle();
	
	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IDesignerData#getStringBundle <em>String Bundle</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>String Bundle</em>' containment reference.
	 * @see #getStringBundle()
	 * @generated
	 */
	void setStringBundle(ILocalizedStringBundle value);

	/**
	 * @model containment="true"
	 */
	IComponentManifest getComponentManifest();


	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IDesignerData#getComponentManifest <em>Component Manifest</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Manifest</em>' containment reference.
	 * @see #getComponentManifest()
	 * @generated
	 */
	void setComponentManifest(IComponentManifest value);

	/**
	 * @model containment="true"
	 */
	IMacroStringTable getMacroTable();
	
	/**
	 * @param value the new value of the '<em>Macro Table</em>' containment reference.
	 * @see #getMacroTable()
	 * @generated
	 */
	void setMacroTable(IMacroStringTable value);

	/**
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIDesignerData_SourceMappingState()
	 * @model containment="true"
	 * @generated
	 */
	ISourceGenMappingState getSourceMappingState();

	/**
	 * Tracks the generated files for the model
	 * @model containment="true"
	 */
	IGeneratedFiles getGeneratedFiles();
	
	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IDesignerData#getGeneratedFiles <em>Generated Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generated Files</em>' containment reference.
	 * @see #getGeneratedFiles()
	 * @generated
	 */
	void setGeneratedFiles(IGeneratedFiles value);

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IDesignerData#getSourceMappingState <em>Source Mapping State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Mapping State</em>' containment reference.
	 * @see #getSourceMappingState()
	 * @generated
	 */
	void setSourceMappingState(ISourceGenMappingState value);

	Object visitNodes(INodeVisitor visitor);
	
	void garbageCollectStrings();
	
	INode findByNameProperty(String name);

	void setComponentHelper(ComponentHelper helper);
	ComponentHelper getComponentHelper();
	
	void setNodeNameProvider(INodeNameProvider provider);
	INodeNameProvider getNodeNameProvider();
	
	void setDesignerDataModel(IDesignerDataModel dataModel);
	IDesignerDataModel getDesignerDataModel();
	
	void addModelPropertyListener(IModelPropertyListener listener);
	void removeModelPropertyListener(IModelPropertyListener listener);
	
}
