/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
* Test the BldInfViewPathHelper class.
*
*/
package com.nokia.carbide.cpp.internal.api.sdk.sbsv2;

import java.util.HashMap;
import java.util.List;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

/**
 * Interface that wraps all queries to the Symbian Build System (SBSv2/Raptor)
 * 
 * @noimplement
 */
public interface ISBSv2QueryData {
	
	/**
	 * For given SDK, add an SBS build configuration
	 * @param sdk - use null if base SBS data
	 * @param configData - The configuration to add
	 */
	void addConfigurationData(ISymbianSDK sdk, ISBSv2ConfigData configData);
	
	/**
	 * Get all usable SBS configurations for a given SDK, which is the union of
	 * the SBS base configurations and SDK specific configurations.
	 * @param sdk - should not be null
	 * @return -The list of all usable SBS build configurations
	 * @see {@link ISBSv2QueryData#getSDKSpecificConfigData(ISymbianSDK)}
	 * @see {@link ISBSv2QueryData#getBaseSBSConfigurations()}
	 */
	List<ISBSv2ConfigData> getAllConfigurationsForSDK(ISymbianSDK sdk);
	
	/**
	 * Get the SBS configurations that are defined only by the SDK.
	 * This does not include configurations that have altered meanings,
	 * rather they provide a new unique build alias that is not defined in the base SBS installation.
	 * @param sdk - never null
	 * @return The unique SBS configurations to a given SDK. An empty list of none.
	 */
	List<ISBSv2ConfigData> getSDKSpecificConfigData(ISymbianSDK sdk);
	
	/**
	 * Get the map of build alias to sbs configuration data that Raptor defines
	 * without input from any SDK
	 * @return a HashMap of SBS build alias to configuration data
	 */
	HashMap<String, ISBSv2ConfigData> getBaseSBSConfigurations();
	
	/**
	 * Get the product variant list for a given SDK
	 * @param sdk - The SDK, should not be null
	 * @return The list of product variants
	 */
	List<String> getProductsForSDK(ISymbianSDK sdk);
	
	/**
	 * Sets the product variant list by the SDK. Products are typically defined
	 * under /epoc32/sbs_config/variant_configurations.xml
	 * @param sdk - The SDK, must not be null
	 * @param products - The list of build variants that can be applied to any build alias or meaning
	 */
	void addProductListForSDK(ISymbianSDK sdk, List<String> products);

	/**
	 * Find SBS configuration data for a single build configuration.
	 * @param sdk - The SDK that contains the build configuration. Null for a base configuration
	 * @param string - The build alias (e.g. armv5_udeb)
	 * @return SBS configuration data, or null if not found
	 */
	ISBSv2ConfigData getSBSConfigByAlias(ISymbianSDK sdk, String alias);

	/**
	 * Find SBS configuration data for a single build configuration.
	 * @param sdk - The SDK that contains the build configuration. Null for a base configuration
	 * @param string - The build meaning or dotted name (e.g. arm.v5.udeb.rvct2_2)
	 * @return SBS configuration data, or null if not found
	 */
	ISBSv2ConfigData getSBSConfigByMeaning(ISymbianSDK sdk, String meaning);
}
