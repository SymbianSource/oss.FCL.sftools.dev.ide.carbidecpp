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
package com.nokia.sdt.symbian.dm;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.SetPropertyCommand;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.sourcegen.INameGenerator;
import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.sourcegen.ISourceGenSession;
import com.nokia.sdt.symbian.*;
import com.nokia.sdt.symbian.sdk.SdkUtilities;
import com.nokia.sdt.symbian.workspace.ISymbianDataModelSpecifier;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.workspace.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier.IRunWithModelAction;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Version;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class SymbianModelUtils {
	
	public static final String S60_SDK = "com.nokia.series60"; //$NON-NLS-1$
	public static final String UIQ_SDK = "com.uiq"; //$NON-NLS-1$
	
	// fixed property IDs
	public static final String APPUI_INITIALVIEW = "initialDesign"; //$NON-NLS-1$
	public static final String DESIGNREF_DESIGNPATH = "filePath"; //$NON-NLS-1$
	public static final String DESIGNREF_BASENAME = "baseName"; //$NON-NLS-1$
	public static final String DESIGNREF_ISAPPUICONTAINER = "isAppUIContainer"; //$NON-NLS-1$
	public static final String VIEWREF_IN_TAB_GROUP = "inTabGroup"; //$NON-NLS-1$
	public static final String VIEWREF_TAB_TEXT = "tabText"; //$NON-NLS-1$
	public static final String VIEWREF_TAB_IMAGE = "tabImage"; //$NON-NLS-1$
	public static final String IMAGE_COMPOUND_FILE = "bmpfile"; //$NON-NLS-1$
	public static final String LEGACY_APPLICATION_PROPERTY = "legacyApplication"; //$NON-NLS-1$
	
	// fixed property values
	public static final String LOC_FILE_FORMAT = "loc"; //$NON-NLS-1$
	public static final String RLS_FILE_FORMAT = "rls"; //$NON-NLS-1$

	// fixed model property IDs
	public static final String SYMBIAN_VENDOR_PROPERTY = "com.nokia.sdt.component.symbian.vendor"; //$NON-NLS-1$
	public static final String SYMBIAN_VERSION_PROPERTY = "com.nokia.sdt.component.symbian.version"; //$NON-NLS-1$
	public static final String SYMBIAN_LOCALIZATION_FILE_FORMAT = "com.nokia.sdt.symbian.dm.LOCALIZED_FILE_FORMAT"; //$NON-NLS-1$
	public static final String SOURCEGEN_PROVIDER_ID = "com.nokia.sdt.sourcegen.Symbian-Provider"; //$NON-NLS-1$
	
	public static final String[] UIDESIGN_EXTENSIONS = {".uidesign", ".nxd"}; //$NON-NLS-1$ //$NON-NLS-2$
	static public final String ROOT_UIDESIGN_NAME = "application"; //$NON-NLS-1$
	
	public enum SDKType {
		S60(SdkUtilities.S60_VENDOR_PATTERN),
		UIQ(SdkUtilities.UIQ_VENDOR_PATTERN),		
		UNKNOWN(""); //$NON-NLS-1$
		
		public final String vendorPattern;
		
		SDKType(String p) {
			this.vendorPattern = p;
		}
	}
	
	public enum LocalizationFileFormat {
		LOC,
		RLS
	}

	/**
	 * Common information we need to look up on designs,
	 * gather in one runWithLoadedModel invocation
	 */
	public static class ViewDesignInfo {
		/** name property value of the top-level component instance in the referenced design */ 
		public String baseName;
		/** The component id for the top level layout container */
		public String userContainerComponentID;
		/** The friendly name of the component for the top level layout container */
		public String userContainerFriendlyName;
		/** Result of model load */
		public IStatus status;
		/** The name of the component that references a design of this type */
		public String designReferenceComponentId;
		/** Indicates if the type of design is allowed as initial content */
		public boolean isAppUIContainer;
	}

	// We use this attribute so we can find the AppUi independent of S60 or UIQ
	private static final String APPUI_ATTRIBUTE = "is-symbian-appui";//$NON-NLS-1$

	public static String getSDKName(IDesignerDataModel model) {
		return model.getProperty(SYMBIAN_VENDOR_PROPERTY);
	}
	
	public static String getSDKVersion(IDesignerDataModel model) {
		return model.getProperty(SYMBIAN_VERSION_PROPERTY);
	}
	
	public static Command setSDKVersion(IDesignerDataModel model, String versionString) {
		String label = Messages.getString("SymbianModelUtils.setSDKVersionUndoLabel"); //$NON-NLS-1$
		SetModelPropertyCommand result = new SetModelPropertyCommand((DesignerDataModel) model, 
				SYMBIAN_VERSION_PROPERTY, versionString, label);
		return result;
	}
	
	public static SDKType getModelSDK(IDesignerDataModel model) {
		SDKType result;
		String sdkName = getSDKName(model);
		if (S60_SDK.equals(sdkName)) {
			result = SDKType.S60;
		}
		else if (UIQ_SDK.equals(sdkName)) {
			result = SDKType.UIQ;
		}
		else {
			result = SDKType.UNKNOWN;
		}
		return result;
	}
	
	public static LocalizationFileFormat getLocalizationFormat(IDesignerDataModel rootModel) {
		// .loc is the default
		LocalizationFileFormat result = LocalizationFileFormat.LOC;
		DesignerDataModel dm = (DesignerDataModel) rootModel;
		String property = dm.getProperty(SYMBIAN_LOCALIZATION_FILE_FORMAT);
		if (RLS_FILE_FORMAT.equals(property)) {
			result = LocalizationFileFormat.RLS;
		}
		return result;
	}
	
	public static Command setLocalizationFormat(IDesignerDataModel rootModel, LocalizationFileFormat format) {
		Command result = null;
		String newValue = null;
		switch(format) {
		case LOC:
			newValue = LOC_FILE_FORMAT;
			break;
		case RLS:
			newValue = RLS_FILE_FORMAT;
			break;
		}
		if (newValue != null) {
			String label = Messages.getString("SymbianModelUtils.setLocalizationFormatUndoLabel"); //$NON-NLS-1$
			result = new SetModelPropertyCommand((DesignerDataModel) rootModel,
					SYMBIAN_LOCALIZATION_FILE_FORMAT, newValue, label);
		}
		return result;
	}
	
	public static EObject findAppUi(IDesignerDataModel model) {
		EObject result = null;
		EObject[] rootContainers = model.getRootContainers();
		Object objResult = ComponentInstanceVisitor.preOrderTraversal(rootContainers,
				new ComponentInstanceVisitor.Visitor() {
					public Object visit(IComponentInstance ci) {
						Object result = null;
						IComponent component = ci.getComponent();
						if (component != null) {
							IAttributes attr = (IAttributes) component
									.getAdapter(IAttributes.class);
							if (attr != null && attr.getBooleanAttribute(
											APPUI_ATTRIBUTE, false)) {
								result = ci;
							}
						}
						return result;
					}
				});
		result = ModelUtils.getEObject(objResult);
		return result;
	}
	
	public static List<IDesignerDataModelSpecifier> getSpecifiersForReferencedDesigns(
							IDesignerDataModel rootModel) {
		ArrayList<IDesignerDataModelSpecifier> result = new ArrayList<IDesignerDataModelSpecifier>();
		EObject appUiObj = SymbianModelUtils.findAppUi(rootModel);
		if (appUiObj != null) {
			IProjectContext pc = rootModel.getModelSpecifier().getProjectContext();
			if (pc != null) {
				IComponentInstance appUi = ModelUtils
						.getComponentInstance(appUiObj);
				if (appUi != null) {
					for (EObject childObj : appUi.getChildren()) {
						if (SymbianModelUtils.isDesignReference(childObj)) {
							IPropertySource designPS = ModelUtils.getPropertySource(childObj);
							String childPath = (String) designPS.getPropertyValue(
									SymbianModelUtils.DESIGNREF_DESIGNPATH);
							
							IPath path = new Path(childPath);
							IDesignerDataModelSpecifier specifier = pc.findSpecifierForPath(path);
							if (specifier != null) {
								result.add(specifier);
							}
						}
					}
				}
			}
		}
		return result;
	}
		
	public static void logMissingComponentMessage(String componentName) {
		String fmt = Messages.getString("SymbianModelUtils.missingComponent"); //$NON-NLS-1$
		Object params[] = {componentName};
		String msg = MessageFormat.format(fmt, params);
		SymbianPlugin.getDefault().log(msg);
	}

	public static void logMissingComponentInstanceMessage(String componentName) {
		String fmt = Messages.getString("SymbianModelUtils.missingComponentInstance"); //$NON-NLS-1$
		Object params[] = {componentName};
		String msg = MessageFormat.format(fmt, params);
		SymbianPlugin.getDefault().log(msg);
	}

	/**
	 * Gathers common information from a (potentially) unloaded design,
	 * for either S60 or UIQ.<p>
	 * Does not synchronize with source changes. 
	 */
	public static ViewDesignInfo getViewDesignInfo(IDesignerDataModelSpecifier specifier) {
		ViewDesignInfo result = null;
		if (specifier != null && specifier.isUIDesign()) {
			result = (ViewDesignInfo) specifier.runWithLoadedModelNoSourceGen(new IRunWithModelAction() {
				public Object run(LoadResult lr) {
					ViewDesignInfo runResult = null;
					if (lr != null) {
						if (lr.getModel() != null) {
							SDKType sdkType = getModelSDK(lr.getModel());
							if (sdkType == SDKType.S60) {
								runResult = new S60ViewDesignInfo(lr.getModel());
							} else if (sdkType == SDKType.UIQ) {
								runResult = new UIQViewDesignInfo(lr.getModel());
							}
						}
					}
					if (runResult == null) {
						// either no model or no known SDK -- test SDKs have the latter
						runResult = new ViewDesignInfo();
					}
					runResult.status = lr != null ? lr.getStatus() : Status.CANCEL_STATUS;
					return runResult;
				}
			});
		} else {
			result = new ViewDesignInfo();
		}
		return result;
	}
	
	public static String getBaseName(EObject root) {
		EObject container = ModelUtils.findImmediateChildWithAttributeValue(root, 
					CommonAttributes.IS_TOP_LEVEL_CONTENT_CONTAINER, "true"); //$NON-NLS-1$
		if (container == null) {
			return ModelUtils.getComponentInstance(root).getName();
		}
		IComponentInstance containerInstance = ModelUtils.getComponentInstance(container);
		if (containerInstance == null)
			return null;
		return containerInstance.getName();
	}
	
	/**
	 * Get the base model name for the given specifier.
	 */
	public static String getModelBaseName(IDesignerDataModelSpecifier dmSpec) {
		if (dmSpec == null)
			return "unnamed"; //$NON-NLS-1$
		String fileName = dmSpec.getPrimaryResourcePath().lastSegment();
		int dot = fileName.indexOf("."); //$NON-NLS-1$
		if (dot >= 0)
			return fileName.substring(0, dot);
		else
			return fileName;
	}
	
	/**
	 * Tell if the specifier refers to the root data model.
	 * @param dmSpec
	 * @return
	 */
	public static boolean isRootDataModel(IDesignerDataModelSpecifier dmSpec) {
		if (dmSpec == null)
			return false;
		ISymbianDataModelSpecifier sdmSpec = (ISymbianDataModelSpecifier) dmSpec.getAdapter(ISymbianDataModelSpecifier.class);
		if (sdmSpec == null)
			return false;
		return sdmSpec.isRoot();
	}

	/**
	 * Return the root data model specifier
	 */
	public static IDesignerDataModelSpecifier getRootDataModel(IProjectContext prjContext) {
		Check.checkArg(prjContext);
		IDesignerDataModelSpecifier[] specs = prjContext.getModelSpecifiers();
		for (int i = 0; i < specs.length; i++) {
			if (isRootDataModel(specs[i]))
				return specs[i];
		}
		return null;
	}

	/**
	 * Return the root data model specifier
	 */
	public static IDesignerDataModelSpecifier getRootDataModel(IProject project) {
		Check.checkArg(project);
		WorkspaceContext wsContext = WorkspaceContext.getContext();
		IProjectContext prjContext = wsContext.getContextForProject(project);
		return getRootDataModel(prjContext);
	}
	
	public static boolean isDesignName(String name) {
		boolean result = false;
		for (int i = 0; i < UIDESIGN_EXTENSIONS.length; i++) {
			String extension = UIDESIGN_EXTENSIONS[i];
			if (name.endsWith(extension)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public static void dumpModel(DesignerDataModel model) {
		IDesignerData dd = model.getDesignerData();
		INodeVisitor visitor = new INodeVisitor() {
			public Object visit(INode node) {
				System.out.println(node.toString());
				return null;
			}
		};
		System.out.println("======================================================"); //$NON-NLS-1$
		dd.visitNodes(visitor);	
		System.out.println("======================================================"); //$NON-NLS-1$
	}
	
	public static Version getComponentVersions(IDesignerDataModel model) {
        String versions = getSDKVersion(model);
        if (versions != null)
        	return new Version(versions);
        return null;
	}

	/**
	 * Create an undoable command to add a view reference child to the appui.
	 * <p>
	 * If this is the first view reference then the command will also set
	 * the initial view property, otherwise it will not be changed.
	 * <p>
	 * The returned command has already been executed, but can be
	 * used for undo and redo
	 * @param model
	 * @param designPath
	 * @return undoable Command, or null if failed to find appropriate way to add design reference
	 */
	public static Command addViewReference(IDesignerDataModel model, IDesignerDataModelSpecifier specifier) {
		Command result = null;
		ViewDesignInfo designInfo = getViewDesignInfo(specifier);
		if (designInfo == null) {
			return null;
		}
		if (designInfo.designReferenceComponentId == null) {
			return null;
		}
		EObject appUi = findAppUi(model);
		if (appUi != null) {
			
			// Create a compound command whose result is the result of the first command.
			// This lets the view reference be accessed via getResult
			CompoundCommand cc = new CompoundCommand(0);
			IComponentSet cs = model.getComponentSet();
			IComponent designRefComponent = cs.lookupComponent(designInfo.designReferenceComponentId);
			if (designRefComponent != null) {
				// create and add child
				EObject viewRefInstance = model.createNewComponentInstance(designRefComponent);
				Command command = model.createAddNewComponentInstanceCommand(appUi, viewRefInstance, IDesignerDataModel.AT_END);
				cc.appendAndExecute(command);
	
				// initialize the file path property
				String relativePath = specifier.getPrimaryResourcePath().toString();
				IPropertySource viewRefPS = ModelUtils.getPropertySource(viewRefInstance);
				SetPropertyCommand spc = new SetPropertyCommand(viewRefPS, 
						DESIGNREF_DESIGNPATH, relativePath);
				cc.appendAndExecute(spc);
				
				// ... and the container name property
				spc = new SetPropertyCommand(viewRefPS, 
						DESIGNREF_BASENAME, designInfo.baseName);
				cc.appendAndExecute(spc);
				
				// ... and a property indicating if this could be the main UI Design of the project
				String sdkName = getSDKName(model);
				if (UIQ_SDK.equals(sdkName)) {
					spc = new SetPropertyCommand(viewRefPS, 
							DESIGNREF_ISAPPUICONTAINER, designInfo.isAppUIContainer);
					cc.appendAndExecute(spc);
				}
				
				// if the appui has no other views then set this as the initial view
				IPropertySource appUiPS = ModelUtils.getPropertySource(appUi);
				Object initialView = appUiPS.getPropertyValue(APPUI_INITIALVIEW);
				if (initialView != null && initialView.toString().length() == 0) {
					IComponentInstance viewRefCI = ModelUtils.getComponentInstance(viewRefInstance);
					spc = new SetPropertyCommand(appUiPS, APPUI_INITIALVIEW, viewRefCI.getName());
					cc.appendAndExecute(spc);
				}
				
				result = cc;
			}
			else {
				logMissingComponentMessage(designInfo.designReferenceComponentId);
			}
		}
		return result;
	}

	/**
	 * Tell whether the object is an instance of a design reference component.  The attribute
	 * {@link S60ComponentAttributes#IS_DESIGN_REFERENCE} identifies these.
	 * @param child
	 * @return
	 */
	public static boolean isDesignReference(IComponent component) {
		String result = ModelUtils.readAttribute(component, SymbianComponentAttributes.IS_DESIGN_REFERENCE);
		if (result != null && result.equals("true")) { //$NON-NLS-1$
			return true;
		}
		return false;
	}
	
	/**
	 * Tell whether the object is an instance of a design reference component.  The attribute
	 * {@link S60ComponentAttributes#IS_DESIGN_REFERENCE} identifies these.
	 * @param child
	 * @return
	 */
	public static boolean isDesignReference(EObject child) {
		String result = ModelUtils.readAttribute(child, SymbianComponentAttributes.IS_DESIGN_REFERENCE);
		if (result != null && result.equals("true")) { //$NON-NLS-1$
			return true;
		}
		return false;
	}
	

	/**
	 * Ensure that some valid design reference is set for a root model, for generic
	 * SDK types without specific types of design references.
	 * 
	 * @param model
	 * @return
	 */
	private static Command ensureInitialGenericView(IDesignerDataModel rootModel) {
		Command result = null;
		EObject appUi = findAppUi(rootModel);
		if (appUi != null) {
			IPropertySource appUiPS = ModelUtils.getPropertySource(appUi);
			String initialViewName = (String) appUiPS.getPropertyValue(APPUI_INITIALVIEW);
			boolean haveValidInitialView = false;
			if (initialViewName != null) {
				EObject designRef = rootModel.findByNameProperty(initialViewName);
				if (designRef != null) {
					haveValidInitialView = SymbianModelUtils.isDesignReference(designRef);
				}
			}
			if (!haveValidInitialView) {
				EObject newInitialDesignRef = null;
				IComponentInstance appUiCI = ModelUtils.getComponentInstance(appUi);
				EObject[] children = appUiCI.getChildren();
				if (children != null) {
					for (EObject child : children) {
						if (SymbianModelUtils.isDesignReference(child)) {
							newInitialDesignRef = child;
							break;
						}
					}
				}
				if (newInitialDesignRef != null) {
					IComponentInstance viewRefCI = ModelUtils.getComponentInstance(newInitialDesignRef);
					SetPropertyCommand spc = new SetPropertyCommand(appUiPS, APPUI_INITIALVIEW, viewRefCI.getName());
					result = spc;
				}
			}
		}
		return result;
	}

	/**
	 * Ensure that some valid design reference is set for a root model.
	 * 
	 * @param model
	 * @return
	 */
	public static Command ensureInitialView(IDesignerDataModel rootModel) {
		SDKType sdkType = getModelSDK(rootModel);
		if (sdkType == SDKType.S60) {
			return S60ModelUtils.ensureInitialS60View(rootModel);
		} else if (sdkType == SDKType.UIQ) {
			return UIQModelUtils.ensureInitialUIQDesign(rootModel);
		}
		return ensureInitialGenericView(rootModel);
	}
	
	/**
	 * Tell if the root design is a legacy application, which indicates
	 * that source is not generated and that the model contains only
	 * design references. 
	 */
	public static boolean isLegacyApplication(IDesignerDataModel model) {
		EObject appUi = findAppUi(model);
		if (appUi != null) {
			String legacyProperty = model.getProperty(LEGACY_APPLICATION_PROPERTY);
			return Boolean.valueOf(legacyProperty);
		}
		return false;
	}

	/**
	 * Tell whether the root model is an unknown application type
	 * (neither S60, UIQ, nor legacy).  This is mainly used to distinguish
	 * "real" root models using view design references vs. root
	 * models used in unit tests.
	 */
	public static boolean isUnknownApplication(IDesignerDataModel model) {
		EObject appUi = findAppUi(model);
		return appUi == null;
	}
	
	/**
	 * Create a Symbian sourcegen session from the given provider and data model.
	 * This takes several parameters that are usually derived from the data model
	 * but which are not established at all times (e.g. when running the wizard).  
	 * @param sourceGenProvider
	 * @param dataModel
	 * @param dmSpec model specifier
	 * @return sourcegen session, or null if sourcegen not enabled
	 */
	public static ISourceGenSession createSourceGenSession(ISourceGenProvider sourceGenProvider, 
			IDesignerDataModel dataModel,
			IDesignerDataModelSpecifier dmSpec) {
		Check.checkArg(sourceGenProvider);
		Check.checkArg(dataModel);
		if (isLegacyApplication(dataModel))
			return null;
		
		IProject project = sourceGenProvider.getProject();
        if (Platform.isRunning() && project != null) {
        	// only set up under a running platform (otherwise, use defaults and let test code customize)
            String sdkVendor = dataModel.getProperty(SYMBIAN_VENDOR_PROPERTY); //$NON-NLS-1$
            if (sdkVendor == null) {
                //SymbianPlugin.getDefault().log("Missing "+SYMBIAN_VENDOR_PROPERTY+" property in design "  //$NON-NLS-1$ //$NON-NLS-2$
                //		+ dmSpec.getPrimaryResourcePath()); //$NON-NLS-1$
                sdkVendor = ISymbianSDK.S60_FAMILY_ID;
            }
            String sdkVersion = dataModel.getProperty(SYMBIAN_VERSION_PROPERTY); //$NON-NLS-1$
            if (sdkVersion == null) {
                //SymbianPlugin.getDefault().log("Missing "+SYMBIAN_VERSION_PROPERTY+" property in design " +  //$NON-NLS-1$ //$NON-NLS-2$
                //		dmSpec.getPrimaryResourcePath()); //$NON-NLS-1$
                sdkVersion = "3.0"; //$NON-NLS-1$
            }
            
            ISymbianSDK sdk = SdkUtilities.getBestSDKForVendorAndVersion(sdkVendor, sdkVersion);
            File sdkRoot = (sdk != null) ? new File(sdk.getEPOCROOT()) : null;
            INameGenerator nameGenerator = new SymbianNameGenerator(project);
            sourceGenProvider.setNameGenerator(nameGenerator);
            sourceGenProvider.setIncludeFileLocator(new CdtIncludeFileLocator(project, nameGenerator, dataModel, sdkRoot));
            sourceGenProvider.setSourceFormatter(new SymbianSourceFormatter(new WorkspaceSourceFormatting(project)));
        }
        ISourceGenSession sourceGenSession = sourceGenProvider.createSourceGenSession(dataModel, dmSpec);
		return sourceGenSession;
	}

	/**
	 * Tell if the given RSS field name is known to map to an image property.
	 * Used as an optimization in RSS mapping -- does not need to be exact.
	 * @param designerDataModel
	 * @param memberName
	 * @return
	 */
	public static boolean isImageRssMemberName(
			IDesignerDataModel designerDataModel, String memberName) {
		SDKType type = getModelSDK(designerDataModel);
		if (type == SDKType.UIQ)
			return UIQModelUtils.isImageRssMemberNameImpl(memberName);
		else if (type == SDKType.S60)
			return S60ModelUtils.isImageRssMemberNameImpl(memberName);
		else
			return memberName.equals("bmpfile"); //$NON-NLS-1$  // base case for unit tests
	}
	
	
}
