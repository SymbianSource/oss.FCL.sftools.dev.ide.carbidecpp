/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.util;

import com.nokia.sdt.emf.component.ComponentFactory;
import com.nokia.sdt.emf.component.ComponentPackage;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.*;
import org.eclipse.emf.ecore.xmi.impl.*;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Map;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see com.nokia.sdt.emf.component.util.ComponentResourceFactoryImpl
 * @generated
 */
public class ComponentResourceImpl extends XMLResourceImpl {
	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param uri the URI of the new resource.
	 * @generated NOT
	 */
	public ComponentResourceImpl(URI uri) {
		super(uri);
		
		// TODO: Remove this when EMF bug 187386 is fixed
		resourceSet = new ResourceSetImpl();
	}
	
	static class ComponentXMLHandler extends SAXXMLHandler {

		public ComponentXMLHandler(XMLResource xmiResource, XMLHelper helper,
				Map<?, ?> options) {
			super(xmiResource, helper, options);
		}

		@Override
		protected EFactory getFactoryForPrefix(String prefix) {
			return ComponentFactory.eINSTANCE;
		}
		
		@Override
		protected void handleUnknownFeature(String prefix, String name,
				boolean isElement, EObject peekObject, String value) {
			if (!isElement && (name.equals("xmlns") || name.equals("xsi") || prefix.equals("xsi") || name.equals("ecore")))
				return;
			super.handleUnknownFeature(prefix, name, isElement, peekObject, value);
		}
	}
	static class ComponentXMLLoadImpl extends XMLLoadImpl {

		public ComponentXMLLoadImpl(XMLHelper helper) {
			super(helper);
		}
		@Override
		protected DefaultHandler makeDefaultHandler() {
			return new ComponentXMLHandler(resource, helper, options);
			//return super.makeDefaultHandler();
		}
	}
	
	@Override
	protected XMLHelper createXMLHelper() {
		XMLHelper helper = super.createXMLHelper();
		helper.setNoNamespacePackage(ComponentPackage.eINSTANCE);
		return helper;
	}
	
	@Override
	protected XMLLoad createXMLLoad() {
		return new ComponentXMLLoadImpl(createXMLHelper());
		//return super.createXMLLoad();
	}

} //ComponentResourceFactoryImpl
