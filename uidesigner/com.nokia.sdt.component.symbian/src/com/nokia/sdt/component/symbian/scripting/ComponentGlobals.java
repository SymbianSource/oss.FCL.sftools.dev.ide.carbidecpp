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
package com.nokia.sdt.component.symbian.scripting;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.Component;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.IModelMessage;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.images.IImagePropertyRendering;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.scripting.IScriptContext;
import com.nokia.sdt.scripting.IScriptScope;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.sdt.symbian.ScalableText;
import com.nokia.sdt.symbian.dm.ModelMessage;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.SDKType;
import com.nokia.sdt.symbian.images.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.utils.MessageReporting;
import com.nokia.sdt.utils.drawing.IFont;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;
import com.nokia.sdt.workspace.IProjectContext;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier.IRunWithModelAction;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.mozilla.javascript.Wrapper;
import org.osgi.framework.Version;

import java.io.File;

/**
 * This class provides global routines for all components 
 * (across data models and projects) to use.  From script,
 * these are overridden with component-specific variants
 * that supply the current component.  
 * 
 *
 */
public class ComponentGlobals {

    private static IComponent component;
    static IDesignerDataModel dataModel;
    
    public static void setupGlobals(IScriptScope scope) throws ScriptException {
        
        try {
            scope.defineObject("lookupString",
                    ComponentGlobals.class.getMethod(
                                    "scriptLookupString", 
                                    new Class[] { String.class })
                                    );
            scope.defineObject("lookupInstanceByName",
                    ComponentGlobals.class.getMethod(
                            "scriptLookupInstanceByName", 
                            new Class[] { String.class })
            );
            scope.defineObject("getComponentVersions",
                    ComponentGlobals.class.getMethod(
                                    "scriptGetComponentVersions", 
                                    new Class[0])
                                    );

            scope.defineObject("logStatus", 
            		ComponentGlobals.class.getMethod(
                            "scriptLogStatus", 
                            new Class[] { Object.class }));

            scope.defineObject("log", 
            		ComponentGlobals.class.getMethod(
                            "scriptLog", 
                            new Class[] { String.class }));

            scope.defineObject("error", 
            		ComponentGlobals.class.getMethod(
                            "scriptError", 
                            new Class[] { String.class }));

            scope.defineObject("warning", 
            		ComponentGlobals.class.getMethod(
                            "scriptWarning", 
                            new Class[] { String.class }));
            
            scope.defineObject("isInRootModel", 
            		ComponentGlobals.class.getMethod(
            				"scriptIsInRootModel", 
            				new Class[0]));
            
            scope.defineObject("getProjectName", 
            		ComponentGlobals.class.getMethod(
                            "scriptGetProjectName", 
                            new Class[0] ));

            scope.defineObject("getRootModelInstanceOfType", 
            		ComponentGlobals.class.getMethod(
                            "scriptGetRootModelInstanceOfType", 
                            new Class[] { String.class } ));
    
            scope.defineObject("newModelMessage", 
            		ComponentGlobals.class.getMethod(
                            "scriptNewModelMessage", 
                            new Class[] { Integer.class, String.class, WrappedInstance.class,
                            		Object.class, Object.class} ));

            scope.defineObject("chooseScalableText", 
            		ComponentGlobals.class.getMethod(
                            "scriptChooseScalableText", 
                            new Class[] { String.class, Object.class, Integer.class} ));

            scope.defineObject("createImagePropertyRendering", 
            		ComponentGlobals.class.getMethod(
                            "scriptCreateImagePropertyRendering", 
                            new Class[0]));

            scope.defineObject("createSymbianImageAIFRendering", 
            		ComponentGlobals.class.getMethod(
                            "scriptCreateSymbianImageAIFRendering", 
                            new Class[0]));

            scope.defineObject("findExistingLookAndFeel", 
            		ComponentGlobals.class.getMethod(
            				"scriptFindExistingLookAndFeel", 
            				new Class[] { WrappedInstance.class } ));
            
        } catch (Exception e) {
            ComponentSystemPlugin.log(e);
        }
    }
    
    /**
     * Look up a string defined in the component's properties.
     * @param id string id
     * @return a localized string
     */
    static public String scriptLookupString(String id) {
        Check.checkState(component != null);
            
        return ((Component) component).getLocalizedStrings().getString(id);
    }

    /**
     * Get the current component versions
     * @return a Version class
     */
    static public Version scriptGetComponentVersions() {
        if (dataModel != null) {
        	return SymbianModelUtils.getComponentVersions(dataModel);
        }
        return null;
    }
    
    /**
     * Find an instance with the given name in the current data model.  
     * This can be used to resolve a reference property to a name. 
     * @param instanceName the name of an instance in the same model
     * @return an instance or null
     */
    static public WrappedInstance scriptLookupInstanceByName(String instanceName) {
    	if (dataModel == null)
    		return null;
    	EObject object = dataModel.findByNameProperty(instanceName);
    	if (object == null)
    		return null;
    	return ComponentWrappers.getInstance(dataModel).getWrappedInstance(object);
    }
    
    static public boolean scriptIsInRootModel() {
		IDesignerDataModelSpecifier specifier = dataModel.getModelSpecifier();
		if (specifier != null) {
			return SymbianModelUtils.isRootDataModel(specifier);
		} else {
			// check if it looks like some form of root model
			return !SymbianModelUtils.isUnknownApplication(dataModel); 
		}
    }

    public static String scriptGetProjectName() {
    	if (dataModel == null)
    		return null;
    	IProjectContext ctx = dataModel.getProjectContext();
    	if (ctx == null)
    		return null;
    	if (ctx.getProject() == null)
    		return null;
    	return ctx.getProject().getName();
    }
    
    public static WrappedInstance scriptGetRootModelInstanceOfType(final String componentId) {
    	IProjectContext projectContext = dataModel.getProjectContext();
    	if (projectContext == null)
    		return null;
    	IDesignerDataModelSpecifier dmSpec = SymbianModelUtils.getRootDataModel(projectContext);
    	if (dmSpec == null)
    		return null;
    	
    	EObject instance = (EObject) dmSpec.runWithLoadedModelNoSourceGen(new IRunWithModelAction() {

			public Object run(LoadResult lr) {
				EObject match = null;
				if (lr.getModel() != null) {
					IComponentInstance[] roots = lr.getModel().getRootComponentInstances();
					for (int i = 0; i < roots.length; i++) {
						match = ModelUtils.findFirstComponentInstance(roots[i].getEObject(), componentId, true); 
						if (match != null)
							break;
					}
				}
				return match;
			}
    	});
    	
    	if (instance != null)
    		return ComponentWrappers.getInstance(dataModel).getWrappedInstance(instance);
    	
    	return null;
    }

    //////////////////////////
    
    public static void scriptLog(String str) {
	    IStatus status = Logging.newSimpleStatus(1, 
	            IStatus.INFO,
	            str,
	          	null);
	                            
	    MessageReporting.emit(
	            getMessageLocation(),
	            "ScriptGlobals.LogMessage", 
	            str,
	            status);
	            
	    //Logging.log(ComponentSystemPlugin.getDefault(), status);
	}

	public static void scriptError(String str) {
	    IStatus status = Logging.newSimpleStatus(1,
	            IStatus.ERROR,
	            str,
	            null);
	    
	    MessageReporting.emit(
	    		getMessageLocation(),
	            "ScriptGlobals.LogMessage", //$NON-NLS-1$
	            str,
	            status);
	}

	public static void scriptWarning(String str) {
	    IStatus status = Logging.newSimpleStatus(1,
	            IStatus.WARNING,
	            str,
	            null);
	    
	    MessageReporting.emit(
	    		getMessageLocation(),
	            "ScriptGlobals.LogMessage", //$NON-NLS-1$
	            str,
	            status);
	            
	}

	public static void scriptLogStatus(Object statusObj) {
	    if (statusObj instanceof Wrapper)
	        statusObj = ((Wrapper) statusObj).unwrap();
	    IStatus status = (IStatus) statusObj;
	    MessageReporting.emit(
	    		getMessageLocation(),
	            "ScriptGlobals.LogStatus", //$NON-NLS-1$ 
	            status.getMessage(), //$NON-NLS-1$
	            status);
	            
	    Logging.log(ComponentSystemPlugin.getDefault(), status);
	}

	/**
     * Wrap the given code, which may include other scripts, in a
     * safe way 
     * @param context
     * @param includeBase base directory for any includes
     * @param component the component the script is using
     * @param wrapper wrapped code
     * @return anything returned from the wrapped code
     */
    public static Object wrapScriptHandlingCode(IScriptContext context, File includeBase, IComponent component, IDesignerDataModel dataModel, IScriptContextWrapper wrapper) throws ScriptException {
        IComponent previous = ComponentGlobals.component;
        IDesignerDataModel previousDm = ComponentGlobals.dataModel;
        ComponentGlobals.component = component;
        ComponentGlobals.dataModel = dataModel;
        Object ret = null;
        try {
            ret = ScriptGlobals.wrapScriptHandlingCode(context, includeBase, wrapper);
        } finally {
            ComponentGlobals.component = previous;
            ComponentGlobals.dataModel = previousDm;
        }
        return ret;
    }


    private static MessageLocation getMessageLocation() {
    	if (dataModel.getModelSpecifier() != null)
    		return dataModel.getModelSpecifier().createMessageLocation();
    	else
    		return new MessageLocation(new Path(".")); //$NON-NLS-1$
    }
    
    public static IModelMessage scriptNewModelMessage(Integer severityObj, 
			String message, WrappedInstance instance, 
			Object propertyID, Object eventID) {
    	int severity = IStatus.ERROR;
    	String instanceName = null;
    	MessageLocation location = getMessageLocation();
    	String componentID = null;
    	if (instance != null) {
    		instanceName = instance.getName();
    		componentID = instance.getComponentId();
    	} 
    	if (severityObj != null) {
    		severity = severityObj.intValue();
    	}
    	ModelMessage result = new ModelMessage(severity,
    			location, "ScriptGlobals.ModelMessage", //$NON-NLS-1$
    			message, componentID,
    			instanceName, propertyID, eventID);
    	return result;
    }
    
    public static String scriptChooseScalableText(String text, Object fontObj, Integer availableWidth) { 	
    	// if not S60 >= 2.8, ignore
    	String result = text;
    	if (ScalableText.isScalableTextAvailable(dataModel)) {
    		IFont font = null;
    		Object obj = ScriptingUtils.unwrap(fontObj);
    		if (obj instanceof IFont) {
    			font = (IFont) obj;
    		}
    		if (text != null && availableWidth != null && font != null)
    			result = ScalableText.chooseScalableText(text, (IFont)font, availableWidth);
    	}
    	return result;
    }

    /**
     * Create a new instance of IImageRendering for use in rendering images.
     * This may be reused as needed.
     * @return IImagePropertyRendering instance
     * @see IImagePropertyRendering
     */
    public static IImagePropertyRendering scriptCreateImagePropertyRendering() {
    	SDKType sdkType = SymbianModelUtils.getModelSDK(dataModel);
    	if (sdkType == SDKType.S60) {
    		return new S60ImagePropertyRendering();
    	} else if (sdkType == SDKType.UIQ) {
    		return new UIQImagePropertyRendering();
    	} else {
    		return new SymbianImagePropertyRendering();
    	}
	}
    
    /**
     * Create a new instance of ISymbianImageAIFRendering for use in rendering images
     * from an AIF file.
     * This may be reused as needed.
     * @return ISymbianImageAIFRendering instance
     * @see ISymbianImageAIFRendering
     */
    public static ISymbianImageAIFRendering scriptCreateSymbianImageAIFRendering() {
    	return new SymbianImageAIFRendering();
	}
    
    /**
     * Return the ILookAndFeel for the IDisplayModel associated with the IDesignerDataModel
     * for this instance. If no IDisplayModel has yet been created returns null.
     * @param instance WrappedInstance
     * @return ILookAndFeel
     */
    public static ILookAndFeel scriptFindExistingLookAndFeel(WrappedInstance instance) {
    	IDisplayModel existingDisplayModel = ModelUtils.getExistingDisplayModel(instance.getEObject());
    	if (existingDisplayModel != null)
    		return existingDisplayModel.getLookAndFeel();
    	
    	return null;
    }
}
