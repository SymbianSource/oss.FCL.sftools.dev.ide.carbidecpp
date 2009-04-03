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

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cpp.internal.api.project.core.ResourceChangeListener;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.CacheMap;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ClassUtils;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.component.*;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.datamodel.*;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor.Visitor;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.impl.*;
import com.nokia.sdt.sourcegen.ISourceGenSession;
import com.nokia.sdt.symbian.Messages;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.SDKType;
import com.nokia.sdt.symbian.sdk.SdkUtilities;
import com.nokia.sdt.symbian.sdk.SdkUtilities.SelectableSDKInfo;
import com.nokia.sdt.symbian.workspace.ISymbianDataModelSpecifier;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Implementation of IDesignerDataModel wrapper interface
 */
public class DesignerDataModel implements IDesignerDataModel {
	
	public static final String COMPONENT_PROVIDER_PROPERTY = "com.nokia.sdt.symbian.dm.COMPONENT_PROVIDER"; //$NON-NLS-1$
	public static final String SOURCEGEN_PROVIDER_PROPERTY = "com.nokia.sdt.symbian.dm.SOURCEGEN_PROVIDER"; //$NON-NLS-1$
	public static final String SOURCE_DIRECTORY_ID = "com.nokia.sdt.symbian.dm.SOURCE_DIRECTORY_ID"; //$NON-NLS-1$
	public static final String INCLUDE_DIRECTORY_ID = "com.nokia.sdt.symbian.dm.INCLUDE_DIRECTORY_ID"; //$NON-NLS-1$
	public static final String RESOURCE_DIRECTORY_ID = "com.nokia.sdt.symbian.dm.RESOURCE_DIRECTORY_ID"; //$NON-NLS-1$
	public static final String REGISTRY_DIRECTORY_ID = "com.nokia.sdt.symbian.dm.REGISTRY_DIRECTORY_ID"; //$NON-NLS-1$
	public static final String BUILD_DIRECTORY_ID = "com.nokia.sdt.symbian.dm.BUILD_DIRECTORY_ID"; //$NON-NLS-1$
	public static final String ROOT_APPLICATION_NAME = "com.nokia.sdt.symbian.dm.ROOT_APPLICATION_NAME"; //$NON-NLS-1$
	public static final String ROOT_CONTAINER_OVERRIDE_ID = "com.nokia.sdt.symbian.dm.ROOT_CONTAINER"; //$NON-NLS-1$
	
	public static final String ROOT_DATA_MODEL_FILENAME = "application.uidesign"; //$NON-NLS-1$

	private static final Object CURRENT_VERSION = new Version(1, 1, 0);
	private static final EObject[] NO_ROOT_CONTAINERS = new EObject[0];
	
	private IDesignerDataModelSpecifier modelSpecifier;
	private AdapterFactoryEditingDomain editingDomain;
	private ComposedAdapterFactory adapterFactory;
	private IComponentSet componentSet;
	private HashMap displayModels = new HashMap();
	private IDesignerDataModelProvider provider;
	private boolean isTemporary;
	private ISourceGenSession sourceGenSession;
	/** flag set to indicate sourcegen is disabled temporarily */
	private boolean sourceGenDisabled;
	private IProject temporaryProject;
	private CacheMap cache;
    private boolean inValidateEdit;
    private ListenerList<IDisposeListener> disposeListeners;
    boolean isClipboardDataModel;
    
	/**
	 * Initializes the data model wrapper
	 */
	public DesignerDataModel(IDesignerDataModelProvider provider) {
		Check.checkArg(provider);
		this.provider = provider;
		List factories = new ArrayList();
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new ReflectiveItemProviderAdapterFactory());

		adapterFactory = new ComposedAdapterFactory(factories);
		
		// when running under workbench, use the full GEF clipboard
		// domain; otherwise, use the basic editing domain
        if (Platform.isRunning())
            editingDomain = new GEFClipboardEditingDomain(this, adapterFactory, null, new HashMap());
        else
            editingDomain = new AdapterFactoryEditingDomain(adapterFactory, null, new HashMap());
        
		addAdapterFactory(new ComponentAdapterFactory());
        addAdapterFactory(new ProjectImageInfoProviderAdapterFactory());
	}
	
	public void setModelSpecifier(IDesignerDataModelSpecifier specifier) {
		this.modelSpecifier = specifier;
		this.temporaryProject = null;
		if (modelSpecifier != null) {
			IProjectContext projectContext = specifier.getProjectContext();
			if (projectContext != null) {
				ISymbianProjectContext spc = (ISymbianProjectContext) projectContext.getAdapter(ISymbianProjectContext.class);
				Language currentLanguage = spc.getCurrentLanguage();
				Check.checkContract(currentLanguage != null);
				getDesignerData().getStringBundle().setDefaultLanguage(currentLanguage);
			}
		}
	}
	
	Resource getModelResource() {
		Resource result = null;
		ResourceSet rs = editingDomain.getResourceSet();
		EList resources = rs.getResources();
		if (resources != null && resources.size() >= 1) {
			result = (Resource) resources.get(0);
		}
		return result;
	}
	
	public IDesignerData getDesignerData() {
		IDesignerData result = null;
		if (editingDomain != null) {
			Resource r = getModelResource();
			if (r != null) {
				EList roots = r.getContents();
				if (roots != null && roots.size() == 1)
					result = (IDesignerData) roots.get(0);
			}
		}
		return result;
	}
  
	/**
	 * Creates a new model saved to the given file.
	 * Should be called in the context of a WorkspaceModifyOperation
	 * @param fileURI desired file path.  Any existing file is overwritten
	 * @param encoding optional file encoding, can be null for UTF-8
	 * @throws CoreException
	 */
	
	public void create(URI fileURI, String encoding) throws CoreException, IOException {	
		doCreate(fileURI, encoding, true);
	}
	
	@SuppressWarnings("unchecked")
	private void doCreate(URI fileURI, String encoding, boolean doInitialSave) throws CoreException, IOException {
		
		// initialize the sole resource to point to the file
		Resource r = editingDomain.createResource(fileURI.toString());
	
		// create the root object and add it to the resource
		IDesignerData root = DmFactory.eINSTANCE.createIDesignerData();
		r.getContents().add(root);
		
		root.setDesignerDataModel(this);
	
		if (doInitialSave) {
			// Save the contents of the resource to the file system
            save(r, new NullProgressMonitor());
		}
	}
	
	/**
	 * Creates a temporary data model, which will not be saved to disk
	 */
	public void createTemporary(String name) {
		
		URI fileURI = URI.createFileURI(new File(name + ".uidesign").getAbsolutePath()); //$NON-NLS-1$
		try {
			isTemporary = true;
			doCreate(fileURI, null, false);
		} catch (CoreException x) {
			throw new RuntimeException(x);
		} catch (IOException x) {
			throw new RuntimeException(x);
		}
	}

	public LoadResult load(URI fileURI, IDesignerDataModelSpecifier specifier) {
		try {
			ResourceSet resourceSet = editingDomain.getResourceSet();
			Resource resource = resourceSet.getResource(fileURI, true);
			if (resource == null) {
				IStatus status = Logging.newStatus(SymbianPlugin.getDefault(),
						IStatus.ERROR, 
						Messages.getString("DesignerDataModel.FailedToLoad")); //$NON-NLS-1$
				return new LoadResult(null, status);	
			}
		} catch (Exception x) {
			IStatus status = Logging.newStatus(SymbianPlugin.getDefault(), x);
			return new LoadResult(null, status);
		}

		LoadResult result = null;
		ComponentSetResult csResult = null;

		try {
			csResult = reEstablishComponentSet();
			
			// Note: changed behavior to log component set IStatus messages instead of
			// propagating to the model load result. Reasons:
			// - the only component set message at the time was missing base components
			// - we now delete components that have missing bases from the component set, since they
			//   won't work correctly anyway.
			// - missing component messages are diagnosed as model validation. 
			if (csResult != null && csResult.getStatus() != null) {
				Logging.log(SymbianPlugin.getDefault(), csResult.getStatus());
			}
		} catch (CoreException x) {
			IStatus status = Logging.newStatus(SymbianPlugin.getDefault(), x);
			result = new LoadResult(null, status);
            return result;
		}
		
		IDesignerData designerData = getDesignerData();
		Check.checkContract(designerData != null);
		designerData.setDesignerDataModel(this);
		// mark the manifest as being in synch with last saved
		// model
		designerData.getComponentManifest().markSaved();

		// copy, don't use setter here
		this.modelSpecifier = specifier;
		this.temporaryProject = null;
		
		IStatus srcStatus = loadFromSource();
		if (srcStatus != null) {
			result = new LoadResult(null, srcStatus);
			return result;
		}
		
		result = new LoadResult(this, null);
		return result;
	}
	
	private void disposeSourceSession() {
		if (sourceGenSession != null) {
			if (!sourceGenSession.isDisposed())
				sourceGenSession.dispose();
			sourceGenSession = null;
		}
	}

	/**
	 * Load state from modified sources.
	 * @return
	 */
	private IStatus loadFromSource() {
		if (sourceGenSession == null)
			return null;
		
		if (!isConfiguredForSourceGen())
			return null;
		
		Check.checkState(!sourceGenSession.isDisposed());
		
		synchronized (sourceGenSession) {
			IStatus status = tryRetrievingSourceState();
			if (status == null && isSourceGenEnabled())
				status = tryUpdatingFromSource();
			return status;
		}
	}

	/**
	 * Tell whether the model supports source generation.
	 * @return
	 */
	private boolean isConfiguredForSourceGen() {
			// In the future, may need a separate non-app-model specific
			// model property to this effect, but this needs changes throughout
			// sourcegen.
		if (getProperty(SOURCEGEN_PROVIDER_PROPERTY) == null)
			return false;
		
			// note: this model only appears on the root, so should
			// only be set true there.
		if (ObjectUtils.equals(
				getProperty(SymbianModelUtils.LEGACY_APPLICATION_PROPERTY),
				"true")) //$NON-NLS-1$
			return false;
		
		return true;
	}


	/**
	 * Tell whether the model should generate source.
	 * @return
	 */
	private boolean isSourceGenEnabled() {
		if (sourceGenDisabled)
			return false;
		
		return isConfiguredForSourceGen();
	}

	/**
	 * If configured, load source mapping state to enable updating
	 * existing RSS files. 
	 * <p>
	 * Only call from loadFromSource()
	 */
	private IStatus tryRetrievingSourceState() {
    	if (sourceGenSession.getSourceGenProvider().isOneWayUpdate()) {
    		try {
    			sourceGenSession.loadFromSource();
            } catch (CoreException x) {
                IStatus status = Logging.newStatus(SymbianPlugin.getDefault(), x);
                return status;
            }
    	}
    	return null;
	}

	/**
	 * If configured, update the data model from
	 * source.  Call this after tryRetrievingSourceState()
	 * <p>
	 * Only call from loadFromSource()
	 */
	private IStatus tryUpdatingFromSource() {
    	// see if we make any use of existing files
    	if (sourceGenSession.getSourceGenProvider().isTwoWayImport()) {
    		try {
    			sourceGenSession.loadFromSource();
            } catch (CoreException x) {
                IStatus status = Logging.newStatus(SymbianPlugin.getDefault(), x);
                return status;
            }
    	}
    	return null;
	}



	String stringProperty(IPropertyContainer container, String name) {
		String result = null;
		IPropertyValue pv = (IPropertyValue) container.getProperties().get(name);
		if (pv != null) {
			StringValue stringValue = pv.getStringValue();
			result = container.lookupString(stringValue);
		}
		return result;
	}
	
	private IComponentProvider getComponentProvider() throws CoreException {
		IComponentProvider result = null;
		IDesignerData designerData = getDesignerData();
		IPropertyContainer pc = designerData.getProperties();
		String providerName = stringProperty(pc, COMPONENT_PROVIDER_PROPERTY);
		if (providerName != null) {
			ComponentSystem system = ComponentSystem.getComponentSystem();
			result = system.getProvider(providerName);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private Map getComponentSetProperties() throws CoreException {
		Map result = null;
		IComponentProvider provider = getComponentProvider();
		if (provider != null) {
			// gather all properties for the provider
			String prefix = provider.getPropertyPrefix();
			if (prefix != null) {
				result = new HashMap();
				IPropertyContainer pc = getDesignerData().getProperties();
				for (Iterator iter = pc.getProperties().keySet().iterator(); iter.hasNext();) {
					String currKey = (String) iter.next();
					if (currKey.startsWith(prefix)) {
						result.put(currKey, stringProperty(pc, currKey));
					}
				}
			}
		}
		return result;
	}
	
	private void removeComponentSetProperties() throws CoreException {
		Map properties = getComponentSetProperties();
		if (properties != null) {
			IPropertyContainer pc = getDesignerData().getProperties();
			for (Iterator iter = properties.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				pc.getProperties().removeKey(key);
			}
		}
	}
	
	private void storeComponentSetProperties(String providerName, Map properties) {
		Check.checkArg(!TextUtils.isEmpty(providerName));
		IPropertyContainer pc = getDesignerData().getProperties();
		pc.set(COMPONENT_PROVIDER_PROPERTY, new StringValue(StringValue.LITERAL, providerName), false);
		for (Iterator iter = properties.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			pc.set((String)entry.getKey(), 
					new StringValue(StringValue.LITERAL, entry.getValue().toString()), false);
		}
	}
	
	private ComponentSetResult reEstablishComponentSet() throws CoreException {
		// Look for saved properties for a component set and
		// use to re-establish the component set if found
		ComponentSetResult result = null;
		IDesignerData designerData = getDesignerData();
		IPropertyContainer pc = designerData.getProperties();
		String providerName = stringProperty(pc, COMPONENT_PROVIDER_PROPERTY);
		if (providerName != null) {
			ComponentSystem system = ComponentSystem.getComponentSystem();
			IComponentProvider provider = system.getProvider(providerName);
			if (provider != null) {
				Map componentSetProperties = getComponentSetProperties();
				if (componentSetProperties != null)	{	
					result = provider.reQueryComponents(componentSetProperties);
					if (result != null && result.getComponentSet() != null) {
						setComponentSet(result.getComponentSet());
					}
				}
			}
		}
		return result;
	}
	
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}
	
	public EObject getRoot() {
		return getDesignerData();
	}
	
	@SuppressWarnings("unchecked")
	public EObject[] getRootContainers() {
		EObject result[] = NO_ROOT_CONTAINERS;
		IDesignerData designerData = getDesignerData();
		if (designerData != null) {
			// When present this property gives the name of the single root
			// container. If not present we use the IDesignerData root containers
			String rootContainerOverride = stringProperty(
					designerData.getProperties(), ROOT_CONTAINER_OVERRIDE_ID);
			if (!TextUtils.isEmpty(rootContainerOverride)) {
				EObject rc = findByNameProperty(rootContainerOverride);
				if (rc != null) {
					result = new EObject[1];
					result[0] = rc;
				}
			}
			else {
				int size = designerData.getRootContainers().size();
				if (size > 0) {
					result = (EObject[]) designerData.getRootContainers().toArray(new EObject[size]);
				}
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public EObject createRootContainerInstance(IComponent tool) {
		INode newNode = DmFactory.eINSTANCE.createINode();
		newNode.setComponentId(tool.getId());
		getDesignerData().getRootContainers().add(newNode);
		return newNode;
	}

	public IComponentSet getComponentSet() {
		return componentSet;
	}

    public void setComponentSet(IComponentSet set) throws CoreException {
    	Check.checkArg(set);
    		// we don't initialize properly if component set is
    		// set before the designer data object exists.
    	Check.checkState(getDesignerData() != null);
    	removeComponentSetProperties();
        componentSet = set;
        
        IDesignerData designerData = getDesignerData();
        if (designerData != null) {
            ComponentHelper helper = new ComponentHelper();
            helper.setComponentSet(componentSet);
            designerData.setComponentHelper(helper);
            componentSet.initializeDataModel(this);
            
            Map csProperties = set.getPropertiesForPersistence();
            if (csProperties != null) {
            	String provider = set.getProvider().getId();
            	storeComponentSetProperties(provider, csProperties);
            }
    		doComponentUpdates();
        }
    }

	@SuppressWarnings("unchecked")
	public void addAdapterFactory(AdapterFactory adapterFactory) {
		ResourceSet rs = editingDomain.getResourceSet();
		EList adapters = rs.getAdapterFactories();
		adapters.add(adapterFactory);
	}
	
	public EObject createNewComponentInstance(IComponent tool) {
		INode newNode = DmFactory.eINSTANCE.createINode();
		newNode.setComponentId(tool.getId());
		return newNode;
	}
	
	private void checkInModel(EObject obj) {
		Check.checkArg(obj);
		if (obj instanceof INode) {
			INode node = (INode) obj;
			Check.checkArg(node.getDesignerData() != null);
		}
	}

	public Command createAddNewComponentInstanceCommand(EObject owner, EObject child, 
						int insertionPosition) {
		Command result = null;
		checkInModel(owner);
		if (child instanceof INode) {
			INode childNode = (INode) child;
			// if the node already has a parent then this is illegal
			if (childNode.eContainer() != null)
				throw new IllegalStateException();
			
			IComponent component = getComponentSet().lookupComponent(childNode.getComponentId());
			if (component == null)
				throw new IllegalStateException();
			
			if (insertionPosition == AT_END) {
				insertionPosition = CommandParameter.NO_INDEX;
			}
			result = new CreateChildCommand(owner, childNode, component, insertionPosition);
		}
		else
			throw new IllegalArgumentException();
		
		IChildCommandExtender extender = ModelUtils.getChildCommandExtender(owner);
		if (extender != null) {
			result = extender.getExtendedAddNewComponentInstanceCommand(
						owner, Collections.singletonList(child), insertionPosition, result);
		}
		return result;
	}
	
	private Map<EObject, List<EObject>> createParentToChildListMap(List<EObject> objects) {
		Map<EObject, List<EObject>> parentToObjectsMap = new HashMap();
		for (Iterator<EObject> iter = objects.iterator(); iter.hasNext();) {
			EObject obj = iter.next();
			if (obj != null)
				checkInModel(obj);
			// create a separate objects to remove list for each parent
			IComponentInstance ci = ModelUtils.getComponentInstance(obj);
			EObject parent = ci.getParent();
			if (!parentToObjectsMap.containsKey(parent))
				parentToObjectsMap.put(parent, new ArrayList<EObject>());
			List<EObject> childList = parentToObjectsMap.get(parent);
			childList.add(obj);
		}
		
		return parentToObjectsMap;
	}
	
	private Command createChainedExtendedRemoveCommands(
					Map<EObject, List<EObject>> parentToObjectsMap) {
		// Chain individual commands from all parents, 
		// allowing each parent to extend it
		Command result = null;
		for (EObject parent : parentToObjectsMap.keySet()) {
			List<EObject> childList = parentToObjectsMap.get(parent);
			Command curCommand = new RemoveChildInstancesCommand(childList);
			IChildCommandExtender extender = ModelUtils.getChildCommandExtender(parent);
			if (extender != null) {
				curCommand = 
					extender.getExtendedRemoveComponentInstancesCommand(childList, curCommand);
			}
			if (result == null)
				result = curCommand;
			else
				result = result.chain(curCommand);
		}
		
		return result;
	}

	public Command createRemoveComponentInstancesCommand(List<EObject> objectsToRemove) {
		Check.checkArg(objectsToRemove);
		
		return createChainedExtendedRemoveCommands(createParentToChildListMap(objectsToRemove));
	}

	public Command createMoveComponentInstanceCommand(EObject targetObject, EObject newOwner, int insertionPosition) {
		Check.checkArg(targetObject instanceof INode);
		Check.checkArg(newOwner instanceof INode);
		checkInModel(targetObject);
		checkInModel(newOwner);
		
		Command result = new MoveComponentInstanceCommand((INode)targetObject, (INode)newOwner, insertionPosition);
		IChildCommandExtender extender = ModelUtils.getChildCommandExtender(newOwner);
		if (extender != null) {
			result = extender.getExtendedMoveComponentInstanceCommand(
						targetObject, newOwner, insertionPosition, result);
		}
		return result;
	}

	public Command createCopyComponentInstancesCommand(List objectsToCopy) {
		final EditingDomain editingDomain = getEditingDomain();
		final Command[] resultHolder = 
			new Command[] {	new SetInstancesToClipboardCommand(editingDomain, objectsToCopy) };
		// recurse the objects to copy, looking for potential extenders
		EObject[] objects = (EObject[]) objectsToCopy.toArray(new EObject[objectsToCopy.size()]);
		ComponentInstanceVisitor.preOrderTraversal(objects, new Visitor() {
			public Object visit(IComponentInstance ci) {
				IClipboardCommandExtender extender = ModelUtils.getClipboardCommandExtender(ci.getEObject());
				if (extender != null) {
					resultHolder[0] = extender.getExtendedCopyToClipboardCommand(editingDomain, resultHolder[0]);
				}
				return null;
			}
		});
		return resultHolder[0];
	}

	public Command createCutComponentInstancesCommand(List objectsToCut) {
		Check.checkArg(objectsToCut);
		
		return new CutInstancesToClipboardCommand(this, objectsToCut);
	}

	public Command createPasteComponentInstancesCommand(final EObject owner) {
		final EditingDomain editingDomain = getEditingDomain();
		final Command[] resultHolder = 
			new Command[] { new PasteInstancesFromClipboardCommand(editingDomain, owner) };
		Collection<Object> clipboard = editingDomain.getClipboard();
		if (clipboard != null) {
			// recurse the objects on the clipboard, looking for potential extenders
			EObject[] objects = (EObject[]) clipboard.toArray(new EObject[clipboard.size()]);
			ComponentInstanceVisitor.preOrderTraversal(objects, new Visitor() {
				public Object visit(IComponentInstance ci) {
					IClipboardCommandExtender extender = ModelUtils.getClipboardCommandExtender(ci.getEObject());
					if (extender != null) {
						resultHolder[0] = extender.getExtendedPasteFromClipboardCommand(owner, editingDomain, resultHolder[0]);
					}
					return null;
				}
			});
		}
		return resultHolder[0];
	}

	public Resource[] getResources() {
		Resource[] result = null;
		Resource r = getModelResource();
		if (r != null) {
			result = new Resource[1];
			result[0] = r;
		}
		return result;
	}
	public Resource[] getReadOnlyResources() {
		Resource[] result = null;
		Resource r = getModelResource();
		if (editingDomain.isReadOnly(r)) {
			result = new Resource[1];
			result[0] = r;
		}
		return result;
	}

	public void saveModel(IProgressMonitor monitor) throws Exception {
        Logging.timeStart("saving data model"); //$NON-NLS-1$
        
		Check.checkState(!isTemporary);
		// in the event the file moved, update the resource
		boolean resourceExists = false;
		Resource emfResource = getModelResource();
		if (modelSpecifier != null) {
			URI uri = null;
			if (Platform.isRunning()) {
				IResource workspaceResource = modelSpecifier.getPrimaryResource();
				if (workspaceResource != null) {
					uri = URI.createPlatformResourceURI(
							workspaceResource.getFullPath().toString(), false);
					resourceExists = workspaceResource.exists();
				}
			}
			if (uri == null) {
				IPath projectResourcePath = modelSpecifier.getPrimaryResourcePath();
				if (projectResourcePath != null) {
					uri = URI.createFileURI(projectResourcePath.toOSString());
					resourceExists = projectResourcePath.toFile().exists();
				}
			}
			if (uri != null) {
				emfResource.setURI(uri);
			}
		}
       	ISymbianDataModelSpecifier sms = null;
       	if (modelSpecifier != null) {
    		sms = (ISymbianDataModelSpecifier) modelSpecifier.getAdapter(ISymbianDataModelSpecifier.class);
       	}

		// If the resource already exists update snapshot prior to save, so that it's current when
		// workspace listeners see the change event. If it will first be created then
		// we have to saved first
		if (resourceExists) {
        	if (sms != null) {
        		sms.updateSnapshot(this);
        	}
        }

		save(emfResource, monitor);
		
		if (!resourceExists && modelSpecifier != null) {
        	if (sms != null) {
        		sms.updateSnapshot(this);
        	}
        }
		
		if (modelSpecifier != null) {
			modelSpecifier.modelSaved(this);
		}
        
		Logging.timeEnd();
 	}

		// Note that this can be used to transition
		// a temporary model to a normal file-based model.
	public void saveModelAs(IDesignerDataModelSpecifier modelSpecifier, IProgressMonitor monitor) throws Exception {
		URI uri = null;
		if (modelSpecifier.getPrimaryResource() != null) {
			uri = URI.createPlatformResourceURI(modelSpecifier.getPrimaryResource().getFullPath().toString(), false);
		} else {
			IPath modelPath = modelSpecifier.getPrimaryResourcePath();
			uri = URI.createFileURI(modelPath.toString());
		}
		
		Resource r = getModelResource();
		r.setURI(uri);
		isTemporary = false;
		this.modelSpecifier = modelSpecifier;
		this.temporaryProject = null;
		saveModel(monitor);
	}

	public void dispose() {
		notifyDisposeListeners();
		disposeListeners = null;
		adapterFactory.dispose();
		if (displayModels != null) {
			for (Iterator iter = displayModels.values().iterator(); iter.hasNext();) {
				IDisplayModel model = (IDisplayModel) iter.next();
				iter.remove();
				model.dispose();
			}
		}
		disposeSourceSession();
        if (modelSpecifier != null) {
        	modelSpecifier.modelUnloaded(this);
        }
        if (cache != null) {
        	cache.disposeAll();
        }
        // clear references to help leak checking
        componentSet = null;
        adapterFactory = null;
        displayModels = null;
        editingDomain = null;
        modelSpecifier = null;
        provider = null;
        temporaryProject = null;
        cache = null;
	}

	public Object getNamePropertyId() {
		return INode.NAME_PROPERTY;
	}

	public EObject findByNameProperty(String name) {
		EObject result = null;
		IDesignerData dd = getDesignerData();
		if (dd != null) {
			result = dd.findByNameProperty(name);
		}
		return result;
	}

	public String getInstanceNameRoot(IComponent component) {
		return getDesignerData().getNodeNameProvider().getInstanceNameRoot(component);
	}

	@SuppressWarnings("unchecked")
	public IDisplayModel getDisplayModelForRootContainer(EObject rootContainer) throws CoreException {
		if (!(rootContainer instanceof INode))
			return null;
		INode rootNode = (INode) rootContainer;
		IDisplayModel displayModel = null;
		synchronized (displayModels) {
			displayModel = (IDisplayModel) displayModels.get(rootNode);
			if (displayModel == null) {
				IComponent rootComponent = rootNode.getComponent();
				if (rootComponent != null) {
					IAttributes attr = (IAttributes) rootComponent.getAdapter(IAttributes.class);
					if (attr != null) {
						String className = attr.getAttribute(CommonAttributes.DISPLAY_MODEL_CLASS);
						if (className != null) {
							Bundle bundle = rootComponent.getBundle();
							if (bundle == null)
								bundle = SymbianPlugin.getDefault().getBundle();
							String errorFormat = Messages.getString("DesignerDataModel.1"); //$NON-NLS-1$
							Object errorArgs[] = {className, rootComponent.getId()};

							Object createdObj = ClassUtils.loadAndCreateInstance(
									bundle, className, IDisplayModel.class,									
									SymbianPlugin.getDefault(), errorFormat, errorArgs);
							if (createdObj != null) {
								displayModel = (IDisplayModel) createdObj;
								displayModel.initialize(this, rootContainer);
								displayModels.put(rootNode, displayModel);
							}
						}
					}
				}
			}
		}
		return displayModel;
	}

	public IDisplayModel getExistingDisplayModelForRootContainer(EObject rootContainer) {
		if (!(rootContainer instanceof INode))
			return null;
		INode rootNode = (INode) rootContainer;
		IDisplayModel displayModel = null;
		synchronized (displayModels) {
			displayModel = (IDisplayModel) displayModels.get(rootNode);
		}
		return displayModel;
	}

	public void displayModelDisposed(IDisplayModel displayModel) {
		synchronized (displayModels) {
			for (Iterator iter = displayModels.values().iterator(); iter.hasNext();) {
				IDisplayModel curr = (IDisplayModel) iter.next();
				if (curr == displayModel)
					iter.remove();
			}
		}
	}

	public IDesignerDataModelProvider getProvider() {
		return provider;
	}

	private IModelMessage createSimpleModelMessage(int severity, String msgKey, Object[] msgArgs) {
		ModelMessage msg = new ModelMessage(severity,
				getModelSpecifier().createMessageLocation(),
				msgKey,
				MessageFormat.format(Messages.getString(msgKey), msgArgs), 
				null, null, null, null);
		return msg;
	}
	
	public Collection<IModelMessage> validate() {
		Logging.timeStart("validating model " + modelSpecifier); //$NON-NLS-1$
		Collection<IModelMessage> result = new ArrayList<IModelMessage>();
		
		validateBaselineSDK(result);
		validateComponentManifest(result);
		
		EObject roots[] = getRootContainers();
		if (roots != null) {
			ModelValidationVisitor visitor = new ModelValidationVisitor(this);
			ComponentInstanceVisitor.preOrderTraversal(
					roots, visitor);
			Collection<IModelMessage> messages = visitor.getMessages();
			if (messages != null && messages.size() > 0) {
				result.addAll(messages);
			}

			if (sourceGenSession != null && modelSpecifier != null) {
				// get any sourcegen messages intended to stick to the model
				Collection<IMessage> simpleMessages = sourceGenSession.getMessages();
				MessageLocation location = modelSpecifier.createMessageLocation();
				if (simpleMessages != null) {
					// rewrite to model messages that live on the model
					for (Iterator iter = simpleMessages.iterator(); iter.hasNext();) {
						IMessage message = (IMessage) iter.next();
						ModelMessage modelMsg = new ModelMessage(message, null, null, null, null);
						modelMsg.ref = location;
						result.add(modelMsg);
					}
				}
			}
						
		}
		Logging.timeEnd();
		return result;
	}
 
	/**
	 * Validate that the model's sdkVendor/sdkVersion properties are supported,
	 * disabling sourcegen if so.
	 * @param result list of messages to add to
	 */
	private void validateBaselineSDK(Collection<IModelMessage> result) {
		sourceGenDisabled = false;
		String sdkVendor = getProperty(SymbianModelUtils.SYMBIAN_VENDOR_PROPERTY);
		String sdkVersion = getProperty(SymbianModelUtils.SYMBIAN_VERSION_PROPERTY);
		if (sdkVendor == null) {
			result.add(createSimpleModelMessage(IMessage.ERROR,
					"ProjectContext.MissingSdkInfoProperty", //$NON-NLS-1$
					new Object[] { SymbianModelUtils.SYMBIAN_VENDOR_PROPERTY }));
			sourceGenDisabled = true;
		}
	
		if (sdkVersion == null) {
			result.add(createSimpleModelMessage(IMessage.ERROR,
					"ProjectContext.MissingSdkInfoProperty", //$NON-NLS-1$
					new Object[] { SymbianModelUtils.SYMBIAN_VERSION_PROPERTY }));
			sourceGenDisabled = true;
		}

		if (sdkVendor != null && sdkVersion != null) {
			// validate the baseline matches a build configuration
            ISymbianSDK sdk = SdkUtilities.getBestSDKForVendorAndVersion(sdkVendor, sdkVersion);
            
            if (sdk == null) {
				result.add(createSimpleModelMessage(IMessage.ERROR,
						"ProjectContext.MissingSDK", //$NON-NLS-1
						new Object[] { sdkVendor, sdkVersion }));
				sourceGenDisabled = true;

            } else {
            	IProjectContext context = getProjectContext();
            	IProject project = context != null ? context.getProject() : null;
            	if (project != null) {
            		SDKType sdkType = SymbianModelUtils.getModelSDK(this);
	            	SelectableSDKInfo sdkInfo = SdkUtilities.getSelectableSDKsForProject(project, sdkType.vendorPattern);
	            	if (!sdkInfo.selectableSDKs.contains(sdk)) {
	            		if (!CarbideBuilderPlugin.getBuildManager().isCarbideProject(project)) {
							result.add(createSimpleModelMessage(IMessage.WARNING,
									"ProjectContext.UnconvertedProject", //$NON-NLS-1
									new Object[] { project.getName() }));
	            			
	            		} else {
	            			IModelMessage modelMessage;
	            			if (sdkInfo.minimumConfiguredSDK != null) {
	            				modelMessage = createSimpleModelMessage(IMessage.WARNING,
	            						"ProjectContext.ImpreciseSDKMatch", //$NON-NLS-1
	            						new Object[] { sdkInfo.minimumConfiguredSDK, sdkVendor, sdkVersion });
	            			}
	            			else {
	            				modelMessage = createSimpleModelMessage(IMessage.WARNING,
	            						"ProjectContext.BadSDKInfo", //$NON-NLS-1
	            						new Object[0]);
	            			}
							result.add(modelMessage);
	            		}
						sourceGenDisabled = true;
	            	}
            	}
            }
		}
	}
	
	/**
	 * Emit any new/updated/missing components as info/warning validation messages
	 * @param messages
	 */
	private void validateComponentManifest(Collection<IModelMessage> messages) {
		IDesignerData dd = getDesignerData();
		IComponentManifest manifest = dd.getComponentManifest();
		if (manifest.reflectsLastSave()) {
			Map<String, ComponentManifestDelta> deltas = manifest.getComponentDeltas(dd);
			if (deltas != null) {
				IComponentSet cs = getComponentSet();
				for (ComponentManifestDelta delta : deltas.values()) {
					IModelMessage message = null;
					switch (delta.getType()) {
					case ComponentManifestDelta.MISSING:
						message = createSimpleModelMessage(IMessage.WARNING, 
								"DesignerDataModel.missingComponent",//$NON-NLS-1
								new Object[] {delta.getComponentID()});
						break;

					case ComponentManifestDelta.NEWER:
						message = createSimpleModelMessage(IMessage.INFO, 
								"DesignerDataModel.newerComponent",//$NON-NLS-1
								new Object[] {cs.lookupComponent(delta.getComponentID()).getFriendlyName(),
									delta.getOldVersion(), delta.getNewVersion()});
						break;
						
					case ComponentManifestDelta.OLDER:
						message = createSimpleModelMessage(IMessage.WARNING, 
								"DesignerDataModel.olderComponent",//$NON-NLS-1
								new Object[] {cs.lookupComponent(delta.getComponentID()).getFriendlyName(), 
									delta.getOldVersion(), delta.getNewVersion()});			
						break;
					}
					if (message != null) {
						messages.add(message);
					}
				}
			}
		}
	}

	public IDesignerDataModelSpecifier getModelSpecifier() {
		return modelSpecifier;
	}

	public IProjectContext getProjectContext() {
		IProjectContext result = null;
		if (modelSpecifier != null)
			result = modelSpecifier.getProjectContext();
		else if (temporaryProject != null)
			result = WorkspaceContext.getContext().getContextForProject(temporaryProject);
		return result;
	}
	
	public synchronized ISourceGenSession getSourceGenSession() {
		/*
		if (sourceGenProvider == null)
			return null;
		if (sourceGenSession == null || sourceGenSession.isDisposed())
			sourceGenSession = SymbianModelUtils.createSourceGenSession(sourceGenProvider, this, getModelSpecifier());
			*/
	    return sourceGenSession;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.IDesignerDataModel#setSourceGenSession(com.nokia.sdt.sourcegen.ISourceGenSession)
	 */
	public synchronized void setSourceGenSession(ISourceGenSession session) {
		if (this.sourceGenSession == session)
			return;
		
		disposeSourceSession();
		
		if (session != null) {
			Check.checkArg(session.getDataModel() == this);
			this.sourceGenSession = session;
		}
		
		loadFromSource();
	}

	/**
	 * Save the model.
	 * <p>
	 * Synchronized to avoid having the sourcegen session changed out from under us. 
	 * @param modelResource
	 * @param monitor
	 */
	@SuppressWarnings("unchecked")
	private synchronized void save(final Resource modelResource, IProgressMonitor monitor) {
		IProjectContext projectContext = getProjectContext();
		if (Platform.isRunning() && projectContext != null && projectContext.getProject() != null) {
			// need to save the model in a workspace job to ensure all the
			// resource change events are emitted atomically, and AFTER we've
			// told the Carbide resource listener about any new files.
			IWorkspaceRunnable saveModelJob = new IWorkspaceRunnable() {

				public void run(IProgressMonitor monitor) throws CoreException {
					doSave(modelResource, monitor);
				}
			};
			try {
				ResourcesPlugin.getWorkspace().run(saveModelJob, projectContext.getProject(), IWorkspace.AVOID_UPDATE, monitor);
			} catch (CoreException e) {
				SymbianPlugin.getDefault().log(e);
			}
		} else {
			doSave(modelResource, monitor);
		}
	}
	
	/**
	 * Encapsulate save behavior which will be invoked either as a workspace job
	 * or standalone depending on run environment.
	 * @param modelResource
	 * @param monitor
	 */
	private void doSave(Resource modelResource, IProgressMonitor monitor) {
        monitor.beginTask(MessageFormat.format(Messages.getString("DesignerDataModel.SavingTask"), //$NON-NLS-1$
                new Object[] { getModelSpecifier() != null ?
                        getModelSpecifier().getDisplayName() :
                            getModelResource().getURI().toString() }),
                3 /* units */);
        
        try {
            // clear out any messages from last time
            IProjectContext projectContext = getProjectContext();
            if (projectContext != null)
                projectContext.resetMessages(getComponentSet());

        	IProject theProject = projectContext != null ? projectContext.getProject() : null;

        	// remove unused localized strings and macros
            IDesignerData dd = getDesignerData();
            dd.setCurrentVersion();
        	dd.garbageCollectStrings();
        	dd.getComponentManifest().update(dd);

            // generate sources to memory
        	List<IPath> existingGeneratedFiles = new ArrayList<IPath>();
        	List<IPath> currentlyGeneratedFiles = new ArrayList<IPath>();
        	
            IPath projectLocation = ProjectUtils.getRealProjectLocation(theProject);
            boolean sourcesGenerated = false;
			try {
            	if (sourceGenSession != null && isSourceGenEnabled()) {
            		// get the existing generated files
            		List generatedFiles = dd.getGeneratedFiles().getFiles();
            		for (Object obj : generatedFiles) {
            			IPath path = new Path(obj.toString());
            			if (theProject != null) {
            				// don't assume the stored files have the same case as the design remembers
            				File file = projectLocation.append(path).toFile();
            				IFile rsrc = FileUtils.convertFileToIFile(file);
            				if (rsrc instanceof IFile) {
            					existingGeneratedFiles.add(FileUtils.getComparablePath(path));
            				}
            			}
            		}

            		// note: this call can recycle sourceGenSession if a SourceChangedJob is pending or in progress
            		sourceGenSession.getSourceGenProvider().enableSourceGenChangedListeners(false);
            		sourceGenSession.ensureDataModel(this);
                	sourceGenSession.generateSource(new SubProgressMonitor(monitor, 1, SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK));
                	
                	// now check for newly modified files
                	generatedFiles.clear();
                	List<IPath> generatedSources = sourceGenSession.getSourceGenProvider().getGeneratedSources();
                	for (IPath path : generatedSources) {
                		// ignore "adds" or "deletes" that involve changing a file's case on Win32
                		path = FileUtils.getComparablePath(path);
						generatedFiles.add(path.toString());
						currentlyGeneratedFiles.add(path);
					}
                	
                	sourcesGenerated = true;
            	}
            } catch (Throwable e) {
                IStatus status = Logging.newStatus(SymbianPlugin.getDefault(), e);
                Logging.log(SymbianPlugin.getDefault(), status);
                Logging.showErrorDialog(Messages.getString("DesignerDataModel.Error"), Messages.getString("DesignerDataModel.SourceGenFailed"), status); //$NON-NLS-1$ //$NON-NLS-2$
            }
                
            if (monitor.isCanceled())
                return;

            monitor.subTask(Messages.getString("DesignerDataModel.SavingDataModelSubTask")); //$NON-NLS-1$

            try {
                // save data model
            	modelResource.save(null);
            } catch (IOException e) {
                IStatus status = Logging.newStatus(SymbianPlugin.getDefault(), e);
                Logging.log(SymbianPlugin.getDefault(), status);
                Logging.showErrorDialog(Messages.getString("DesignerDataModel.Error"), Messages.getString("DesignerDataModel.FailedToSave"), status); //$NON-NLS-1$ //$NON-NLS-2$
                
            }
            
            monitor.worked(1);
            // do not allow canceling here, or else sources and DM are out of sync
            
            // commit generated sources
            try {
                if (sourceGenSession != null && isSourceGenEnabled()) {
                	sourceGenSession.getSourceGenProvider().saveGeneratedSources(
                			new SubProgressMonitor(
                					monitor, 1, 
                					SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK));

                	// now ensure any new files are known to the Carbide project before
                	// the resource listener asks the user to add them
                	//
                	// (note: they were created during #generateSource() due to 
                	// an early dependency on files existing for CDT, so the placement of
                	// this call is arbitrary: hence the need to enclose this entire
                	// method in a workspace runnable)
                	if (theProject != null) {
	                	List<IFile> newFiles = new ArrayList<IFile>();
	                	for (IPath genFile : currentlyGeneratedFiles) {
	                		if (!existingGeneratedFiles.contains(genFile)) {
	                			// we uncanonicalized the names so re-search for the real resource
	                			File newFile = projectLocation.append(genFile).toFile();
								IFile file = FileUtils.convertFileToIFile(newFile);
	                			newFiles.add(file);
	                		}
	                	}
	                	ResourceChangeListener.workspacefilesCreated(theProject, newFiles, true);
	                	
	                	// if any files are no longer being generated, ask if they should be deleted
	                	//
	                	// ignore this if sourcegen failed, since the generated file list is suspect
	                	if (sourcesGenerated) {
		                	List<IFile> filesToDelete = new ArrayList<IFile>();
		                	for (IPath path : existingGeneratedFiles) {
		                		if (!currentlyGeneratedFiles.contains(path)) {
		                			// we uncanonicalized the names so re-search for the real resource
		                			File projectFile = projectLocation.append(path).toFile();
		                			IFile file = FileUtils.convertFileToIFile(projectFile);
		                			if (file != null && file.exists()) {
		                				filesToDelete.add(file);
		                			}
		                		}
		                	}
		                	if (!filesToDelete.isEmpty()) {
		                		DeleteProjectFilesJob deleteFilesJob = 
		                			new DeleteProjectFilesJob(filesToDelete, !WorkbenchUtils.isJUnitRunning());
		                		deleteFilesJob.schedule();
		                	}
	                	}
                	}
                }
            } catch (Throwable e) {
                IStatus status = Logging.newStatus(SymbianPlugin.getDefault(), e);
                Logging.log(SymbianPlugin.getDefault(), status);
                Logging.showErrorDialog(Messages.getString("DesignerDataModel.Error"), Messages.getString("DesignerDataModel.FailedToSaveSources"), status); //$NON-NLS-1$ //$NON-NLS-2$
            }
        } finally {
            monitor.done();
            if (sourceGenSession != null && isSourceGenEnabled()) {
        		sourceGenSession.getSourceGenProvider().enableSourceGenChangedListeners(true);
            }
        }
    }
   
   /*
    * (non-Javadoc)
    * @see com.nokia.sdt.datamodel.IDesignerDataModel#getProperty(java.lang.String)
    */
    public String getProperty(String propertyId) {
        IDesignerData designerData = getDesignerData();
        if (designerData != null) {
            IPropertyContainer pc = designerData.getProperties();
            return stringProperty(pc, propertyId);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.nokia.sdt.datamodel.IDesignerDataModel#getProperty(java.lang.String)
     */
     public void setProperty(String propertyId, String value) {
         Check.checkArg(propertyId);
         IDesignerData designerData = getDesignerData();
         if (designerData != null) {
             IPropertyContainer pc = designerData.getProperties();
             if (value != null) {
            	 pc.set(propertyId, new StringValue(StringValue.LITERAL, value), false);
             }
             else {
            	 pc.reset(propertyId);
             }
             
             // reset this flag when the base SDK changes
             if (propertyId.equals(SymbianModelUtils.SYMBIAN_VERSION_PROPERTY)) {
            	 sourceGenDisabled = false;
             }
         }
     }

     /*
      * (non-Javadoc)
      * @see com.nokia.sdt.datamodel.IDesignerDataModel#getProperty(java.lang.String)
      */
      public void removeProperty(String propertyId) {
          Check.checkArg(propertyId);
          IDesignerData designerData = getDesignerData();
          if (designerData != null) {
              IPropertyContainer pc = designerData.getProperties();
              pc.set(propertyId, (StringValue) null, false);
          }
      }

      public Command createAddEventBindingCommand(EObject targetObject, IEventDescriptor event, String userSpecifiedHandlerName) {
    	  return new AddEventBindingCommand(targetObject, event, userSpecifiedHandlerName);
      }
      
      public Command createRemoveEventBindingCommand(IEventBinding eventBinding) {
    	  return new RemoveEventBindingCommand(eventBinding);
      }

    /* (non-Javadoc)
     * @see com.nokia.sdt.datamodel.IDesignerDataModel#getRootComponentInstances()
     */
	@SuppressWarnings("unchecked")
	public IComponentInstance[] getRootComponentInstances() {
		List instances = new ArrayList(1);
		EObject root = getRoot();
		for (Iterator iter = root.eContents().iterator(); iter.hasNext();) {
			EObject child = (EObject) iter.next();
			IComponentInstance instance = (IComponentInstance) EcoreUtil.getRegisteredAdapter(child, IComponentInstance.class);
			if (instance != null)
				instances.add(instance);
		}
		return (IComponentInstance[]) instances.toArray(new IComponentInstance[instances.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.IDesignerDataModel#getCache()
	 */
	public CacheMap getCache() {
		if (cache == null) {
			cache = new CacheMap();
		}
		return cache;
	}
	
	public boolean isModelUpToDate() {
		IDesignerData data = getDesignerData();
		Check.checkState(data != null);
		boolean modelVersionUpTodate = CURRENT_VERSION.equals(data.getVersion());
		if (!modelVersionUpTodate)
			return false;
		IComponentManifest componentManifest = data.getComponentManifest();
		// check to see if deltas include anything except for missing components
		// The case of only missing component should not be treated as a model that is not up-to-date
		// since this will incorrectly trigger the updater dialog
		Map<String, ComponentManifestDelta> componentDeltas = componentManifest.getComponentDeltas(data);
		if (componentDeltas != null) {
			for (ComponentManifestDelta delta : componentDeltas.values()) {
				if (delta.getType() != ComponentManifestDelta.MISSING)
					return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the files related to this model
	 * @param includeModel if true, include the .uidesign file
	 * @param includeGenerated if true, include the generated files
	 */
	public IFile[] getFiles(boolean includeModel, boolean includeGenerated) {
		List<IFile> result = new ArrayList<IFile>();
		if (includeModel) {
			IResource primaryResource = modelSpecifier.getPrimaryResource();
			if (primaryResource instanceof IFile) {
				result.add((IFile) primaryResource);
			}
		}
		if (includeGenerated) {
			IGeneratedFiles generatedFiles = getDesignerData().getGeneratedFiles();
			EList filePaths = generatedFiles.getFiles();
			IProject project = modelSpecifier.getProjectContext().getProject();
			for (Object path : filePaths) {
				result.add(project.getFile(path.toString()));
			}
		}
		return result.toArray(new IFile[result.size()]);
	}

	public IStatus validateEdit(Object context) {
		// this is to catch re-entrancy on the UI thread, not multithreaded access
		if (inValidateEdit) {
			return Status.CANCEL_STATUS;
		}
		
		try {
			inValidateEdit = true;
			IFile files[] = getFiles(true, true);
			Shell shell = null;
			if (context instanceof Shell) {
				shell = (Shell) context;
			}
			IStatus status = FileUtils.validateEdit(files, shell);
			return status;
		} finally {
			inValidateEdit = false;
		}
	}

	public void addDisposeListener(IDisposeListener listener) {
		if (disposeListeners == null)
			disposeListeners = new ListenerList<IDisposeListener>();
		
		disposeListeners.add(listener);
	}

	public void removeDisposeListener(IDisposeListener listener) {
		if (disposeListeners != null) {
			disposeListeners.remove(listener);
		}
	}
	
	private void notifyDisposeListeners() {
		if (disposeListeners != null) {
			for (IDisposeListener listener : disposeListeners) {
				listener.dataModelDisposed(this);
			}
		}
	}
	
	private void doComponentUpdates() {
		INodeVisitor visitor = new INodeVisitor() {
			public Object visit(INode node) {
				IModelUpdater updater = ModelUtils.getModelUpdater(node);
				if (updater != null)
					updater.updateModel(DesignerDataModel.this);
				return null;
			}
		};
		getDesignerData().visitNodes(visitor);	
	}

	public String getComponentId(EObject object) {
		if (object instanceof INode)
			return ((INode) object).getComponentId();
		
		return null;
	}
}
