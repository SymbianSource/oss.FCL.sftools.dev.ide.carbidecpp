/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
 
package com.nokia.carbide.internal.bugreport.model;

import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.nokia.carbide.internal.bugreport.export.IProduct;
import com.nokia.carbide.internal.bugreport.plugin.BugReporterPlugin;
import com.nokia.carbide.internal.bugreport.resources.Messages;

/**
 * Class for handling product plug-ins. 
 *
 */
public class ProductHandler {
	final String EXTENSION_PRODUCT = "product"; //$NON-NLS-1$
	final String EXTENSION_PRODUCT_NAME = "productName"; //$NON-NLS-1$
	
	Hashtable<String, IConfigurationElement> products = null;
	IProduct product = null;

	/**
	 * Reads plugins which extend com.nokia.s60tools.bugreporter.product
	 * @throws RuntimeException if no products found, could not initialize product etc.
	 */
	public ProductHandler() throws RuntimeException {
		IExtensionRegistry er = Platform.getExtensionRegistry();
		IExtensionPoint ep = er.getExtensionPoint(BugReporterPlugin.PLUGIN_ID, EXTENSION_PRODUCT); //$NON-NLS-1$
		IExtension[] extensions = ep.getExtensions();
		
		// No product extending plugins found
		if (extensions == null || extensions.length < 1) {
			throw new RuntimeException(Messages.getString("ProductHandler.NoProductsFound")); //$NON-NLS-1$
		}

		products = new Hashtable<String, IConfigurationElement>();
		
		// read all found products
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] ce = extensions[i].getConfigurationElements();
			if (ce != null && ce.length > 0) {
				products.put(ce[0].getAttribute(EXTENSION_PRODUCT_NAME), ce[0]); //$NON-NLS-1$
			}
		}
		
		if (products.isEmpty()) {
			throw new RuntimeException(Messages.getString("ProductHandler.InvalidProducts")); //$NON-NLS-1$
		}
		
		// if only one product found, "start" it
		if (products.size() == 1) {
			try {
				product = (IProduct)products.elements().nextElement().createExecutableExtension("class"); //$NON-NLS-1$
			} catch (CoreException e) {
				e.printStackTrace();
				throw new RuntimeException(Messages.getString("ProductHandler.CouldNotInitializeProduct")); //$NON-NLS-1$
			}
		}
	}
	
	/**
	 * Returns the currently selected product.
	 * @return product
	 */
	public IProduct getProduct() {
		return product;
	}
	
	/**
	 * Used for setting the selected product. If product is selected via 
	 * product selection wizard page, pass it here with this method.
	 * @param prdct
	 */
	public void setProduct(IProduct prdct) {
		product = prdct;
	}
	
	/**
	 * Returns a list of all found product plug-ins.
	 * @return list of all found products
	 */
	public Hashtable<String, IConfigurationElement> getProducts() {
		return products;
	}
}
