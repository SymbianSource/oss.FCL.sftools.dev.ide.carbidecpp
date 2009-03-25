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
package com.nokia.sdt.component.symbian.properties;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.component.symbian.implementations.IImplementationTypeFactory;
import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IModelAdapter;
import com.nokia.sdt.datamodel.adapter.IPropertyExtenders;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;

public class PropertyExtenderImplementationFactory implements
		IImplementationTypeFactory {

	public Class getInterface() {
		return IPropertyExtenders.class;
	}

	public Class getCodeImplAdapterClass() {
		return CodeAdapter.class;
	}

	public Class getScriptImplAdapterClass() {
		return ScriptAdapter.class;
	}
	
	private static EObject[] convertResult(Object retval) {
		EObject[] result = null;
		if (retval instanceof EObject[]) {
			result = (EObject[]) retval;
		}
		else if (retval instanceof Object[]) {
			ArrayList list = new ArrayList();
			Object[] array = (Object[]) retval;
			for (int i = 0; i < array.length; i++) {
				if (array[i] instanceof EObject) {
					list.add(array[i]);
				}
				else if (array[i] instanceof IModelAdapter) {
					list.add(((IModelAdapter)array[i]).getEObject());
				}
			}
			if (list.size() > 0) {
				result = (EObject[]) list.toArray(new EObject[list.size()]);
			}
		}
		return result;
	}
	
	public static class ScriptAdapter extends ScriptAdapterImplBase implements IPropertyExtenders {

		// NOTE: this script adapter does not use WrappedInstance because
		// WrappedInstance depends on IPropertySource, and this implementation
		// is invoked in the process of creating the IPropertySource.
		public ScriptAdapter() {
			super(IPropertyExtenders.class, IScriptPropertyExtenders.class);
		}

		public EObject[] getPropertyExtenders(final EObject instance) {
			EObject[] result = null;
			try {
				Object retval = invokeScriptCode(getEObject(), new IScriptCodeWrapper() {
					
					public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
					}
					
					public Object run() {
						// note that these wrapped instances do not have property support. We're in the process of creating the IPropertySource here, so
						// we can't provide one for the target, unless we specially prepared one without extension properties. We also don't try to
						// provide properties for the providing instance in order to avoid circularity problems.
						WrappedInstance thisInstance = ComponentWrappers.getInstance(getEObject()).getWrappedInstance(ModelUtils.getComponentInstance(getEObject()), null);
						WrappedInstance targetInstance = ComponentWrappers.getInstance(getEObject()).getWrappedInstance(ModelUtils.getComponentInstance(instance), null);
						return ((IScriptPropertyExtenders)getImpl()).getPropertyExtenders(thisInstance, targetInstance);
					} }
				);
				if (retval != null) {
					result = convertResult(retval);
				}
			} catch (ScriptException e) {
				// already logged
			}
			return result;
		}

		public String[] getExtensionSetNames(final EObject instance) {
			String[] result = null;
			try {
				result = (String[]) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {
					
					public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
					}
					
					public Object run() {
						WrappedInstance thisInstance = ComponentWrappers.getInstance(getEObject()).getWrappedInstance(ModelUtils.getComponentInstance(getEObject()), null);
						WrappedInstance targetInstance = ComponentWrappers.getInstance(getEObject()).getWrappedInstance(ModelUtils.getComponentInstance(instance), null);
						return ((IScriptPropertyExtenders)getImpl()).getExtensionSetNames(thisInstance, targetInstance);
					} }
				);
			} catch (ScriptException e) {
				// already logged
			}
			return result;
		}

		protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
		}
	}
	
	public static class CodeAdapter extends AdapterImpl implements IPropertyExtenders, ICodeImplAdapter {
		private IPropertyExtenders extender;
		public void init(Object impl, EObject instance) {
			Check.checkArg(impl instanceof IPropertyExtenders);
			this.extender = (IPropertyExtenders) impl;
			setTarget(instance);
		}
		
		public boolean isAdapterForType(Object type) {
			return IPropertyExtenders.class.equals(type);
		}

		public EObject[] getPropertyExtenders(EObject instance) {
			return extender.getPropertyExtenders(instance);
		}
		
		public String[] getExtensionSetNames(EObject instance) {
			return extender.getExtensionSetNames(instance);
		}

		public Object getImpl() {
			return extender;
		}
		public EObject getEObject() {
			return (EObject) getTarget();
		}
	}

}
