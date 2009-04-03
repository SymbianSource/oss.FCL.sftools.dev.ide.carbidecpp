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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.sourcegen.INameAlgorithm;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;

import java.util.Map;

/**
 * This interface encapsulates type handling, such as enum
 * and array management, on an RSS DOM.
 * 
 *
 */
public interface IRssModelTypeHandler {
	/**
	 * Register an IAstEnumerator as the generated value of the given
	 * instance's property.
	 * @param instance
	 * @param propertyId
	 * @param enm
	 */
	public void registerEnumerator(String instanceName, String propertyId, 
			String nameAlg, IAstEnumerator enm);

	/**
	 * Register a string as the generated value of the given
	 * instance's property.
	 * @param instance
	 * @param propertyId
	 * @param enm
	 */
	public void registerEnumerator(String instanceName, String propertyId, 
			String nameAlg, String enm);
	
	/** Look up the IAstEnumerator or String registered as the value of the given
	 * instance's property.
	 * @param instance
	 * @param propertyId
	 * @param nameAlg 
	 * @return registered value: IAstEnumerator for generated or #include'd enum
	 * value, or String for unknown value (possibly system #define)
	 */
	public Object findEnumerator(String instanceName, String propertyId, String nameAlg);

	public IAstEnumerator findGeneratedEnumeratorByName(String name);

	/** 
	 * Create an enumerator name using the given name algorithm.
	 * This remembers the generated name but does not emit its
	 * declaration anywhere (the caller is assumed to be creating
	 * an enum or referencing one from a previously generated file).
	 * @param nameAlg name generation algorithm
	 * @param instance the instance owning the property
	 * @param propertyId the property path 
	 * @return name
	 */
	public String createEnumeratorName(IComponentInstance container, String nameAlg, IComponentInstance instance, String propertyId);
	
	/**
     * Register a unique enumeration declaration. Creates the enumeration if it
     * does not exist, in given file's *.hrh file. Creates the *.hrh file if
     * it does not exist.
     * <p>
     * The enumeration may be unnamed enumeration, in which case only one which
     * contains an initial enumerator matching the given initial value is
     * returned.
     * 
     * @param context
     *            the RSS context
     * @param enumDeclName
     *            the enumeration name (or null for an unnamed enum)
     * @param initialValue
     *            the initial value of the enumerator (integer literal)
     * @return enumeration
     */
    public IAstEnumDeclaration findOrCreateEnumeration(
            IAstRssSourceFile enumHeaderFile,
            String enumDeclName,
            String initialValue);

    /**
     * Uniquify and register an enumerator.  The given enumerator is
     * added to the given enumeration.  Creates the enumeration
     * if it does not exist, in the main file's *.hrh file.
     * Creates the *.hrh file if it does not exist.  
     * The enumerator may be added to an unnamed enumeration,
     * but only one which contains an enumerator with the same initial 
     * value is used.  
     * @param context the RSS context
     * @param instance the instance with which the enumerator is associated
     * @param propertyId the property with which the enumerator is associated
     * @param tu the translation unit
     * @param rssFile
     * @param enumDeclName the enumeration name (or null for an unnamed enum)
     * @param initialValue the value of the enumerator or <code>null</code> when
     * creating a new enumeration
     * @param value the value of the enumerator or <code>null</code> 
     * @param enumName the expected enumerator name
     * @param maximumConflicts maximum number of times to try for a unique name
     * @param includeIt  true: add an #include for any generated file
     * @return enumerator, or null if error occurred (logged)
     */
    public IAstEnumerator getUniqueEnumerator(
            IComponentInstance instance, String propertyId, 
            ITranslationUnit tu, IAstRssSourceFile rssFile, String enumDeclName,
            String initialValue, String value, 
            String enumName, int maximumConflicts, boolean includeIt);


	/** 
	 * Uniquify and register an enumerator using the given name algorithm,
	 * adding a declaration and enum to a generated *.hrh file,
	 * and remember it for later so it can looked up.
	 * @param tu master translation unit
	 * @param rssFile the base file
	 * @param nameAlg
	 * @param instance the instance owning the enumerator
	 * @param propertyId the property path
	 * @return IAstEnumerator or null
	 */
	public IAstEnumerator createUniqueEnumerator(
			ITranslationUnit tu, IAstRssSourceFile rssFile,
			IComponentInstance container,
			String nameAlg, 
	        IComponentInstance instance, String propertyId,
	        boolean includeHeader);

	/**
	 * Get the value for a given instance+property's generated enumerator for the given
	 * algorithm 
	 * @param nameAlgorithm
	 * @param instance
	 * @param propertyId
	 * @see INameAlgorithm#getEnumeratorValue(IComponentInstance, String)
	 * @return value of this enumerator, or <code>null</code>
	 */
	public String getNameAlgorithmEnumeratorValue(String nameAlgorithm,
			IComponentInstance instance, String propertyId);
	
	/**
	 * Remember the association between a mapResourceElement and the
	 * array element.
	 * @param arrayInstance the instance generating the array
	 * @param arrayPropertyId the property path providing the array
	 * @param arrayMemberId the property the mapArrayMember generates
	 * @param uniqueValue the value of the field the mapResourceElement#instanceIdentifyingMember provides
	 * @param elementInstance the instance mapped as an element
	 */
	public void registerArrayElement(IComponentInstance arrayInstance,
			String arrayPropertyId,
			String arrayMemberId,
			String uniqueValue,
			String elementInstanceName);

	/**
	 * Given an array element expression, find the instance name
	 * associated with it, or null.
	 */
	public String findInstanceForArrayElement(
			IComponentInstance arrayInstance,
			String arrayPropertyId,
			String arrayMemberId,
			String uniqueValue);

	/**
	 * Remove the mapping from an element to an array.
	 * @param instance
	 * @param propertyId
	 * @param memberName
	 * @param uniqueValue the value of the field the mapResourceElement#instanceIdentifyingMember provides
	 * @param elementName
	 */
	public void removeArrayElement(
			IComponentInstance instance, 
			String propertyId, 
			String memberName, 
			String uniqueValue, 
			String elementName);

	/**
	 * Merge type information with another
	 * @param typeHandler
	 */
	public void merge(IRssModelTypeHandler typeHandler);

	/**
	 * Reset mappings.
	 */
	public void reset();

	/**
	 * Save the state of the mappings.
	 * @param model 
	 * @param state
	 */
	public void saveState(IDesignerDataModel model, ISourceGenMappingState state);

	/**
	 * Load (merge) state from the model.  Call reset() if necessary.  
	 * @param dataModel
	 * @param state
	 */
	public void loadState(IDesignerDataModel dataModel, ISourceGenMappingState state, ITranslationUnit tu);

	/**
	 * Register an enumerator mapping.
	 * @param mapping
	 * @param enm resolved enum, or null 
	 */
	public void registerEnumMapping(IEnumMapping mapping, IAstEnumerator enm);

	/**
	 * Register an array mapping.
	 * @param arrayMapping
	 * @return map
	 */
	public Map<String, String> registerArrayMapping(IArrayMapping arrayMapping);

	/**
	 * Register an element mapping for an array.
	 * @param arrayMapping
	 * @param elementMapping
	 */
	public void registerArrayElementMapping(IArrayMapping arrayMapping, IElementMapping elementMapping);

	/**
	 * Remove the phantom enumerators that are not associated with the DOM.
	 */
	public void resetPhantomEnumerators();

	/**
	 * Reset state for the given file
	 * @param fileName
	 */
	public void resetForRssFile(String fileName);

	/**
	 * Return the enumerators defined for an instance
	 * @param instanceName
	 * @return array of IAstEnumerator or String elements
	 */
	public Object[] getEnumeratorMappingsForInstance(String instanceName);

	/**
	 * Delete enum mappings for the named instance. 
	 * @param instanceName
	 */
	public void removeEnumeratorMappingsForInstance(String instanceName);

}
