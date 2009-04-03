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

package com.nokia.sdt.sourcegen.core;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.IStatus;

import java.util.*;
import java.util.Map.Entry;

/**
 * Track resources generated during source mapping for a single model.
 * This does not include any information about resources
 * existing in RSS created by the user.
 * 
 *
 */
public class ResourceTracker {
	
    /**
     * This class tracks a generated resource definition or expression.
     * It can track the eventual name of a resource definition,
     * an incomplete resource definition, or a complete resource
     * definition.  Finally, it can represent a resource expression,
     * which may be used to prevent emitting a resource definition
     * if the only reason is completeness.
     * <p>
     * The existence of resource info serves two purposes:<br>
     * 1) Tell that a given named resource definition is already
     * present for a given InstanceInfo,<br>
     * and 2) 
     *  
     * 
     *
     */
    public static class ResourceInfo {
        /** resource name for statement (or null), or null for expression */
        String name;
        /** tell what rsrcFile attribute the mapResource says this lives in (or null) */
        private String rsrcFile;
        /** tell whether a resource is complete (if set, implies rsrc!= null) */
        boolean complete;

        /** Create info for an unknown resource */
        public ResourceInfo() {
            this.name = null;
            this.complete = false;
            this.rsrcFile = null;
        }

        /** Create info for an ungenerated resource */
        public ResourceInfo(String name) {
            this.name = name;
            this.complete = false;
        }
        
        /** Create info for a generated resource */
        public ResourceInfo(IAstResourceDefinition rsrc, boolean defaultLocation) {
        	setResourceDefinition(rsrc, defaultLocation);
        }
        
        public ResourceInfo(ResourceInfo other) {
            this.name = other.name;
            this.complete = other.complete;
            this.rsrcFile = other.rsrcFile;
        }
        
        @Override
        public String toString() {
        	return "[name="+name+", rsrcFile="+rsrcFile+", complete="+complete+"]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        }
        public void setName(String name) {
            Check.checkArg(name);
            this.name = name;
        }
        
        public void setResourceDefinition(IAstResourceDefinition rsrc, boolean defaultFile) {
            Check.checkArg(rsrc);
            //this.rsrc = rsrc;
            this.name = rsrc.getName() != null ? rsrc.getName().getName() : null;
            if (defaultFile)
            	this.rsrcFile = null;
            else
            	this.rsrcFile = rsrc.getAstSourceFile().getSourceFile().getFileName();
        }
        
        public String getName() {
            return name;
        }
        
        public void markComplete() {
            this.complete = true;
        }
        
        public boolean isComplete() {
            return complete;
        }
        
        /**
         * Reset info that refers to an expression so that
         * a resource definition is emitted
         */
        public void markIncomplete() {
            complete = false;
        }

		/**
		 * Find the existing resource definition
		 * @param rssGenerator
		 * @return
		 */
		public IAstResourceDefinition findResourceDefinition(IRssModelGenerator rssGenerator) {
			Check.checkArg(rssGenerator);
			if (name == null)
				return null;
			ITranslationUnit tu = rssGenerator.createOrLoadTranslationUnit(rsrcFile, false);
			if (tu == null)
				return null;
			return tu.findResourceDefinition(name);
		}
    }
    
    class InstanceInfo {
    	String instanceName;
    	String rsrcId;
		public IDesignerDataModelSpecifier dmSpec;

		public InstanceInfo(IComponentInstance instance, String rsrcId) {
			Check.checkArg(instance);
			this.instanceName = instance.getName();
	        this.rsrcId = rsrcId;
	        this.dmSpec = instance.getDesignerDataModel().getModelSpecifier();
		}
		
		public InstanceInfo(IComponentInstance instance) {
			this(instance, null);
		}

		public InstanceInfo(String instanceName, String rsrcId, IDesignerDataModelSpecifier dmSpec) {
			Check.checkArg(instanceName);
			this.instanceName = instanceName;
	        this.rsrcId = rsrcId;
	        this.dmSpec = dmSpec;
		}

		/** Copy instance info. */
		public InstanceInfo(InstanceInfo other) {
			this.instanceName = other.instanceName;
	        this.rsrcId = other.rsrcId;
	        this.dmSpec = other.dmSpec;
		}

        @Override
        public String toString() {
        	return "[instanceName="+instanceName+", rsrcId="+rsrcId+", dmSpec="+dmSpec+"]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        }

    	private boolean isSameModel(IDesignerDataModel model) {
    		// TODO: comparison of names works for now but is imprecise; fix TestDesignerDataModel so it works with paths
    		if (dmSpec == null || model.getModelSpecifier() == null)
    			return true;
    		return dmSpec.getDisplayName().equals(model.getModelSpecifier().getDisplayName());
    		//return dmSpec.getPrimaryResourcePath().equals(model.getModelSpecifier().getPrimaryResourcePath());
    	}

       	private boolean isSameModelSpecifier(IDesignerDataModelSpecifier dmSpec) {
    		// TODO: comparison of names works for now but is imprecise; fix TestDesignerDataModel so it works with paths
    		if (this.dmSpec == null || dmSpec == null)
    			return true;
    		return this.dmSpec.getDisplayName().equals(dmSpec.getDisplayName());
       		//return this.dmSpec.getPrimaryResourcePath().equals(dmSpec.getPrimaryResourcePath());
    	}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (obj instanceof InstanceInfo) {
				InstanceInfo other = (InstanceInfo) obj;
				return other.instanceName.equals(instanceName) &&
				ObjectUtils.equals(other.rsrcId, rsrcId) &&
				isSameModelSpecifier(other.dmSpec);
			}
			return false;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return instanceName.hashCode() 
			^ (rsrcId != null ? rsrcId.hashCode() << 16 : 0)
			^ (dmSpec != null ? dmSpec.getDisplayName().hashCode() : 0);
		}
    }
    
    /** The map of IComponentInstance names + facets to ResourceInfo */
    private Map<InstanceInfo, ResourceInfo> instanceFacetToResourceInfoMap;

    /**
     * 
     */
    public ResourceTracker() {
        this.instanceFacetToResourceInfoMap = new HashMap<InstanceInfo, ResourceInfo>();
    }

    /**
     * 
     */
    public void reset() {
        instanceFacetToResourceInfoMap.clear();
    }
    

    /**
     * Find or create ResourceInfo by the instance generating it,
     * the &lt;mapResource&gt; facet that declares it,
     * and the rsrcId from that facet.
     * <p>
     * The facet is used to distinguish multiple unnamed
     * resources generated by the same instance, 
     * for the purpose of ensuring each resource gets
     * emitted.  (Otherwise 'instanceName' is the key for
     * the first resource and the second isn't generated.)
     * 
     * @param instance the instance generating the resource
     * @param rsrcId the identifier of the resource for the instance, may be null
     * @param facet the MapResourceType facet
     * @return ResourceInfo or null
     */
    public ResourceInfo findOrCreateResourceInfo(IComponentInstance instance, 
            String rsrcId) {
        InstanceInfo iinfo_f = new InstanceInfo(instance, rsrcId);
        ResourceInfo info = instanceFacetToResourceInfoMap.get(iinfo_f);
        if (info == null) {
            info = new ResourceInfo();
            instanceFacetToResourceInfoMap.put(iinfo_f, info);
        }
        //System.out.println("for instance " + instance + " w/rsrcid="+rsrcId +" w/facetid="+facetId
        //		+", got info.name="+info.name+" & info.rsrc="+info.rsrc + " ==> " + info);
        return info;
    }


    /**
     * Find ResourceInfo by the instance generating it
     * and the rsrcId associated with it.  Prefer any
     * info that has a name over an expression.
     * 
     * @param instance the instance generating the resource
     * @param rsrcId the identifier of the resource for the instance, may be null
     * @return ResourceInfo or null
     */
    public ResourceInfo findResourceInfo(IComponentInstance instance, 
            String rsrcId) {
    	String instanceName = instance.getName();
    	if (instanceName == null)
    		return null;
    	
    	IDesignerDataModel model = instance.getDesignerDataModel();
    	ResourceInfo last = null;
    	for (Iterator<Map.Entry<InstanceInfo, ResourceInfo>> iter = 
    		instanceFacetToResourceInfoMap.entrySet().iterator(); iter.hasNext();) {
    		Map.Entry<InstanceInfo, ResourceInfo> entry = iter.next();
    		ResourceInfo info = entry.getValue();
    		if (entry.getKey().instanceName.equals(instanceName)
    			&& ObjectUtils.equals(entry.getKey().rsrcId, rsrcId)
    			&& entry.getKey().isSameModel(model)) {
    			if (last == null || last.getName() == null) 
    				last = info;
    		}
		}
    	
    	//if (last != null)
    	// System.out.println("found rsrcinfo " + last + " ("+last.name + "," +last.rsrc +") " 
    	//		+ " for " + instance + " w/rsrcid=" + rsrcId);
        return last;
    }

    /**
     * Find ResourceInfo by the name of a resource.
     * This can return external resources.
     * @param rsrcName
     * @return ResourceInfo or null
     */
    public ResourceInfo findResourceInfoByResourceName(String rsrcName) {
        for (Iterator iter = instanceFacetToResourceInfoMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<InstanceInfo, ResourceInfo> entry = (Map.Entry<InstanceInfo, ResourceInfo>) iter.next();
			ResourceInfo info = (ResourceInfo) entry.getValue();
			if (info.getName() != null && info.getName().equals(rsrcName))
				return info;
        }
        return null;
    }

	/**
	 * Merge data from another tracker into this one.
	 * This introduces external resources into the map.
	 * @param tracker
	 */
	public void merge(ResourceTracker tracker) {
		for (Iterator iter = tracker.instanceFacetToResourceInfoMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<InstanceInfo, ResourceInfo> entry = (Map.Entry<InstanceInfo, ResourceInfo>) iter.next();
			registerMapping(entry.getKey(), entry.getValue());
		}
	}

    /**
     * Look up resource info for the given resource.
     * This tells whether an existing resource is known or unknown.
     * @param def
     * @return
     */
    public ResourceInfo findInfoForResource(IAstResourceDefinition rsrc) {
        Check.checkArg(rsrc);
        Check.checkArg(rsrc.getName());
        for (Iterator iter = instanceFacetToResourceInfoMap.values().iterator(); iter.hasNext();) {
            ResourceInfo info = (ResourceInfo) iter.next();
            if (ObjectUtils.equals(info.getName(), rsrc.getName())
            		&& (info.rsrcFile == null 
       				|| (rsrc.getAstSourceFile().getSourceFile().getFileName().equals(info.rsrcFile)))
            	)
                return info;
        }
        return null;
    }

    /**
     *  Reset the "isComplete" flags on the generated resources,
     *  so they will be updated. 
     */
    public void resetComplete() {
        for (Iterator iter = instanceFacetToResourceInfoMap.values().iterator(); iter.hasNext();) {
            ResourceInfo info = (ResourceInfo) iter.next();
            info.complete = false;
        }
    }

	/**
	 * Return all the resources associated with the instance
	 * @param instance
	 * @return list of matching ResourceInfo
	 */
	public ResourceInfo[] getResourceInfos(IComponentInstance instance) {
		return getResourceInfos(instance.getName());
	}

	/**
	 * Return all the resources associated with the instance
	 * @param instanceName
	 * @return list of matching ResourceInfo
	 */
	public ResourceInfo[] getResourceInfos(String instanceName) {
		List<ResourceInfo> list = new ArrayList<ResourceInfo>();
        Check.checkArg(instanceName);
        for (Iterator<Map.Entry<InstanceInfo, ResourceInfo>> iter = instanceFacetToResourceInfoMap.entrySet().iterator(); iter.hasNext();) {
        	Map.Entry<InstanceInfo, ResourceInfo> entry = iter.next();
            if (entry.getKey().instanceName.equals(instanceName))
                list.add(entry.getValue());
        }
        return (ResourceInfo[]) list.toArray(new ResourceInfo[list.size()]);
	}
	
	/**
	 * Forget we generated resources for the given instance
	 */
	public void removeResourceInfo(String instanceName) {
        for (Iterator<Map.Entry<InstanceInfo, ResourceInfo>> iter = instanceFacetToResourceInfoMap.entrySet().iterator(); iter.hasNext();) {
        	Map.Entry<InstanceInfo, ResourceInfo> entry = iter.next();
            if (entry.getKey().instanceName.equals(instanceName))
                iter.remove();
        }
	}

	/**
	 * Save the mapping information for the data model
	 */
	public void saveState(IDesignerDataModel dataModel, ISourceGenMappingState state, IRssModelGenerator rssGenerator) {
		IResourceMappings mappings = DmFactory.eINSTANCE.createIResourceMappings();
		for (Iterator iter = instanceFacetToResourceInfoMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<InstanceInfo, ResourceInfo> entry = (Map.Entry<InstanceInfo, ResourceInfo>) iter.next();
			ResourceInfo rsrcInfo = entry.getValue();
			InstanceInfo instanceInfo = entry.getKey();
			// bug 4813: don't emit ungenerated resource (may be implicitly referenced
			// by having been previously generated, but if it's no longer complete,
			// then excepting a bug, it shouldn't be re-reported)
			if (instanceInfo.isSameModel(dataModel) && rsrcInfo.name != null && rsrcInfo.isComplete()) {
				// this is used to ensure we're not emitting 
				// resources associated with the wrong model
				Check.checkState(dataModel.findByNameProperty(instanceInfo.instanceName) != null);

				IResourceMapping mapping = DmFactory.eINSTANCE.createIResourceMapping();
				mapping.setInstanceName(instanceInfo.instanceName);
				mapping.setRsrcId(instanceInfo.rsrcId);
				mapping.setRsrcFile(rsrcInfo.rsrcFile);
				mapping.setValue(rsrcInfo.name);

				// check sanity
				IAstRssSourceFile rssFile = rssGenerator.getRssFile(mapping.getRsrcFile(), false);
				IAstResourceDefinition def = rsrcInfo.findResourceDefinition(rssGenerator);
				if (def == null) {
					// this isn't fatal but it's a problem
					Messages.emit(IStatus.ERROR, rssFile.getSourceFile(), 
							"ResourceTracker.DidNotFindResource", //$NON-NLS-1$
									new Object[] { mapping.getValue(), rssFile.getSourceFile().getFile() });
					
				}
				
				mappings.getMappings().add(mapping);
			}
		}
		
		state.setResourceMappings(mappings);
	}

	/**
	 * Load mapping information
	 * @param model the model to load from (null means other model)
	 * @param state state to read
	 * @param tu the other model's RSS
	 */
	public void loadState(IDesignerDataModel dataModel, ISourceGenMappingState state, IRssModelGenerator generator) {
		Check.checkArg(state);
		Check.checkArg(generator);
		if (state.getResourceMappings() == null)
			return;
		IDesignerDataModelSpecifier dmSpec = dataModel.getModelSpecifier();
		for (Iterator iter = state.getResourceMappings().getMappings().iterator(); iter.hasNext();) {
			IResourceMapping mapping = (IResourceMapping) iter.next();
			if (TextUtils.strlen(mapping.getInstanceName()) > 0
					//&& TextUtils.strlen(mapping.getRsrcId()) > 0
					&& TextUtils.strlen(mapping.getValue()) > 0) {
				ITranslationUnit tu = generator.createOrLoadTranslationUnit(mapping.getRsrcFile(), false);
				IAstRssSourceFile rssFile = generator.getRssFile(mapping.getRsrcFile(), false);
				if (!generator.getSourceGenProvider().getFileTracker().fileExists(rssFile.getSourceFile().getFile())) {
					// the file does not exist: don't complain
					iter.remove();
					continue;
				}
					
				IAstResourceDefinition rsrc = null;
				rsrc = tu.findResourceDefinition(mapping.getValue());
				if (rsrc != null) {
					if (dataModel.findByNameProperty(mapping.getInstanceName()) == null) {
						Messages.emit(IStatus.ERROR, rssFile.getSourceFile(), 
								"ResourceTracker.DidNotFindInstance", //$NON-NLS-1$
										new Object[] { mapping.getInstanceName(), dataModel.getModelSpecifier() });
						iter.remove();
					} else {
						registerMapping(mapping, rsrc, dmSpec);
					}
				} else {
					Messages.emit(IStatus.ERROR, rssFile.getSourceFile(), 
							"ResourceTracker.DidNotFindResource", //$NON-NLS-1$
									new Object[] { mapping.getValue(), rssFile.getSourceFile().getFile() });
					iter.remove();
				}
			} else {
				Messages.emit(IStatus.ERROR, dataModel, 
						"ResourceTracker.IllegalResourceMapping", //$NON-NLS-1$
								new Object[] { mapping  });
				iter.remove();
			}
		}
	}

	/**
	 * Register a resource mapping.
	 * @param mapping
	 */
	public void registerMapping(IResourceMapping mapping, IAstResourceDefinition rsrc, IDesignerDataModelSpecifier dmSpec) {
		Check.checkArg(rsrc);
		InstanceInfo key = new InstanceInfo(mapping.getInstanceName(), mapping.getRsrcId(), dmSpec);
		ResourceInfo value = new ResourceInfo(rsrc, TextUtils.strlen(mapping.getRsrcFile()) == 0);
		instanceFacetToResourceInfoMap.put(key, value);
	}

	/**
	 * Register info from another tracker.  The new entries are marked external.
	 * @param mapping
	 */
	public void registerMapping(InstanceInfo instanceInfo, ResourceInfo resourceInfo) {
		InstanceInfo key = new InstanceInfo(instanceInfo);
		ResourceInfo value = new ResourceInfo(resourceInfo);
		instanceFacetToResourceInfoMap.put(key, value);
	}

	/**
	 * Reset info for the given file
	 * @param fileName null for main file or named file
	 */
	public void resetForRssFile(String fileName) {
		for (Iterator iter = instanceFacetToResourceInfoMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<InstanceInfo, ResourceInfo> entry = (Map.Entry<InstanceInfo, ResourceInfo>) iter.next();
			if (ObjectUtils.equals(entry.getValue().rsrcFile, fileName))
				iter.remove();
		}
	}

	/**
	 * Get all the referenced instances.  Only non-external resources are returned.
	 */
	public String[] getReferencedInstances(IDesignerDataModel model) {
		IDesignerDataModelSpecifier dmSpec = model.getModelSpecifier();
		Set<String> names = new HashSet<String>();
		for (Iterator iter = instanceFacetToResourceInfoMap.keySet().iterator(); iter.hasNext();) {
			InstanceInfo info = (InstanceInfo) iter.next();
			if (info.isSameModelSpecifier(dmSpec))
				names.add(info.instanceName);
		}
		return (String[]) names.toArray(new String[names.size()]);
	}

	/**
	 * Get list of generated resources.  This excludes external resources.
	 * @return
	 */
	public Set<IAstResourceDefinition> getGeneratedResources(IRssModelGenerator generator, IDesignerDataModel model) {
		Set<IAstResourceDefinition> rsrcs = new HashSet<IAstResourceDefinition>();
		for (Iterator iter = instanceFacetToResourceInfoMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<InstanceInfo, ResourceInfo> entry = (Entry<InstanceInfo, ResourceInfo>) iter.next();
			if (entry.getKey().isSameModel(model)) {
				ResourceInfo rsrcInfo = entry.getValue();
				IAstResourceDefinition def = rsrcInfo.findResourceDefinition(generator);
				if (def != null)
					rsrcs.add(def);
			}
		}
		return rsrcs;
	}
}
