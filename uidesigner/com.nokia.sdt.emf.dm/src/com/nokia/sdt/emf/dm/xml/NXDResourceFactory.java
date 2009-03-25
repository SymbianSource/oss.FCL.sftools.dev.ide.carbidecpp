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
package com.nokia.sdt.emf.dm.xml;

import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.*;
import org.eclipse.emf.ecore.xmi.XMLResource.XMLInfo;
import org.eclipse.emf.ecore.xmi.impl.*;

/**
 * We provide a custom resource factory in order to provider
 * customized XML handling of .nxd and .uidesign files.
 * 
 * We do as much customization as possible via EMF's XMLMap support.
 * This lets us provide names for the <designerData> and <component>
 * elements, and the "id" attributes.
 * 
 * In order to customize property reading and writing we must provide
 * custom XMLLoad, XMLSave, and XMLHandler classes. In the data model properties
 * are stored in a map. Each map entry consists of a key, value pair.
 * EMF's default XML generation is very verbose and includes Java
 * class names in xsi:type attributes. The overriden classes
 * let us provide a much more concise and simple format.
 * 
 * In general the intent is to override only when strictly necessary
 * and let EMF's default handling do as much as possible.
 *
 */
public class NXDResourceFactory implements Resource.Factory {
	
	static final String DEFAULT_ENCODING = "UTF-8"; //$NON-NLS-1$

	@SuppressWarnings("unchecked")
	public Resource createResource(URI uri) {
		Check.checkArg(uri);
		XMLResourceImpl result = new NXDResource(uri);
				
		result.getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING, DEFAULT_ENCODING);
		result.getDefaultSaveOptions().put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.FALSE);

		// Set customization of data model XML via the XML map
		XMLResource.XMLMap xmlMap = new XMLMapImpl();
		
		XMLResource.XMLInfo designerDataInfo = new XMLInfoImpl();
		designerDataInfo.setName(Names.DESIGNER_DATA);
		xmlMap.add(DmPackage.eINSTANCE.getIDesignerData(), designerDataInfo);
		
		XMLResource.XMLInfo designerDataVersionInfo = new XMLInfoImpl();
		designerDataVersionInfo.setXMLRepresentation(XMLInfo.ATTRIBUTE);
		xmlMap.add(DmPackage.eINSTANCE.getIDesignerData_Version(), designerDataVersionInfo);
		
		XMLResource.XMLInfo rootNodeInfo = new XMLInfoImpl();
		rootNodeInfo.setName(Names.COMPONENT);
		xmlMap.add(DmPackage.eINSTANCE.getIDesignerData_RootContainers(), rootNodeInfo);
		
		XMLResource.XMLInfo childNodeInfo = new XMLInfoImpl();
		childNodeInfo.setName(Names.COMPONENT);
		xmlMap.add(DmPackage.eINSTANCE.getINode_Children(), childNodeInfo);

		XMLResource.XMLInfo nodeComponentIDInfo = new XMLInfoImpl();
		nodeComponentIDInfo.setName(Names.ID);
		xmlMap.add(DmPackage.eINSTANCE.getINode_ComponentId(), nodeComponentIDInfo);

		XMLResource.XMLInfo eventBindingInfo = new XMLInfoImpl();
		eventBindingInfo.setName(Names.EVENT_BINDING);
		xmlMap.add(DmPackage.eINSTANCE.getINode_EventBindings(), eventBindingInfo);
		
		XMLResource.XMLInfo eventBindingIDInfo = new XMLInfoImpl();
		eventBindingIDInfo.setName(Names.ID);
		xmlMap.add(DmPackage.eINSTANCE.getIEventBinding_EventID(), eventBindingIDInfo);

		XMLResource.XMLInfo eventBindingHandlerDisplayInfo = new XMLInfoImpl();
		eventBindingHandlerDisplayInfo.setName(Names.EVENT_BINDING_HANDLER_DISPLAY_NAME);
		xmlMap.add(DmPackage.eINSTANCE.getIEventBinding_EventHandlerDisplayText(), eventBindingHandlerDisplayInfo);

		XMLResource.XMLInfo eventBindingHandlerSymbolInfo = new XMLInfoImpl();
		eventBindingHandlerSymbolInfo.setName(Names.EVENT_BINDING_HANDLER_SYMBOL);
		xmlMap.add(DmPackage.eINSTANCE.getIEventBinding_EventHandlerInfo(), eventBindingHandlerSymbolInfo);
		
		XMLResource.XMLInfo localizedStringTableInfo = new XMLInfoImpl();
		localizedStringTableInfo.setName(Names.LOCALIZED_STRING_TABLE);
		xmlMap.add(DmPackage.eINSTANCE.getILocalizedStringBundle_LocalizedStringTables(), localizedStringTableInfo);

		XMLResource.XMLInfo localizedStringInfo = new XMLInfoImpl();
		localizedStringInfo.setName(Names.STRING_ENTRY);
		xmlMap.add(DmPackage.eINSTANCE.getILocalizedStringTable_Strings(), localizedStringInfo);

		XMLResource.XMLInfo localizedStringMapEntryInfo = new XMLInfoImpl();
		localizedStringMapEntryInfo.setName(Names.ID);
		xmlMap.add(DmPackage.eINSTANCE.getEStringToStringMapEntry_Key(), localizedStringMapEntryInfo);

		XMLResource.XMLInfo macroStringInfo = new XMLInfoImpl();
		macroStringInfo.setName(Names.STRING_ENTRY);
		xmlMap.add(DmPackage.eINSTANCE.getIMacroStringTable_StringMacros(), macroStringInfo);

		XMLResource.XMLInfo manifestEntryInfo = new XMLInfoImpl();
		manifestEntryInfo.setName(Names.COMPONENT_MANIFEST_ENTRY);
		xmlMap.add(DmPackage.eINSTANCE.getIComponentManifest_Entries(), manifestEntryInfo);
		
		XMLResource.XMLInfo manifestEntryComponentIDInfo = new XMLInfoImpl();
		manifestEntryComponentIDInfo.setName(Names.ID);
		xmlMap.add(DmPackage.eINSTANCE.getIComponentManifestEntry_ComponentID(), manifestEntryComponentIDInfo);

		//////
		
		XMLResource.XMLInfo resourceMappingsInfo = new XMLInfoImpl();
		resourceMappingsInfo.setName(Names.RESOURCE_MAPPING);
		xmlMap.add(DmPackage.eINSTANCE.getIResourceMappings_Mappings(), resourceMappingsInfo);

		XMLResource.XMLInfo resourceMappingValueInfo = new XMLInfoImpl();
		resourceMappingValueInfo.setXMLRepresentation(XMLInfo.CONTENT);
		xmlMap.add(DmPackage.eINSTANCE.getIResourceMapping_Value(), resourceMappingValueInfo);

		//////

		XMLResource.XMLInfo enumMappingInfo = new XMLInfoImpl();
		enumMappingInfo.setName(Names.ENUM_MAPPING);
		xmlMap.add(DmPackage.eINSTANCE.getIEnumMappings_Mappings(), enumMappingInfo);

		XMLResource.XMLInfo enumContentMappingInfo = new XMLInfoImpl();
		enumContentMappingInfo.setXMLRepresentation(XMLInfo.CONTENT);
		xmlMap.add(DmPackage.eINSTANCE.getIEnumMapping_Value(), enumContentMappingInfo);

		//////

		XMLResource.XMLInfo arrayMappingInfo = new XMLInfoImpl();
		arrayMappingInfo.setName(Names.ARRAY_MAPPING);
		xmlMap.add(DmPackage.eINSTANCE.getIArrayMappings_Mappings(), arrayMappingInfo);

		XMLResource.XMLInfo elementMappingInfo = new XMLInfoImpl();
		elementMappingInfo.setName(Names.ELEMENT_MAPPING);
		xmlMap.add(DmPackage.eINSTANCE.getIArrayMapping_Elements(), elementMappingInfo);

		XMLResource.XMLInfo arrayContentMappingInfo = new XMLInfoImpl();
		arrayContentMappingInfo.setXMLRepresentation(XMLInfo.CONTENT);
		xmlMap.add(DmPackage.eINSTANCE.getIElementMapping_Value(), arrayContentMappingInfo);
		
		//////
		
		XMLResource.XMLInfo generatedFileInfo = new XMLInfoImpl();
		generatedFileInfo.setName(Names.GENERATED_FILES_ENTRY);
		xmlMap.add(DmPackage.eINSTANCE.getIGeneratedFiles_Files(), generatedFileInfo);
	
		
		result.getDefaultLoadOptions().put(XMLResource.OPTION_XML_MAP, xmlMap);
		result.getDefaultSaveOptions().put(XMLResource.OPTION_XML_MAP, xmlMap);
	
		return result;
	}
	
	class NXDResource extends XMLResourceImpl {

		public NXDResource(URI uri) {
			super(uri);
		}
		
		protected XMLHelper createXMLHelper()
		{
			XMLHelperImpl helper = new XMLHelperImpl(this);
			helper.setNoNamespacePackage(DmPackage.eINSTANCE);
			return helper;
		}

		protected XMLLoad createXMLLoad() {
			return new XMLLoadNXD(createXMLHelper());
		}

		protected XMLSave createXMLSave() {
			  return new XMLSaveNXD(createXMLHelper());
		}
		
		
		
	}
	
}
