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
package com.nokia.sdt.component.symbian.sourcemapping;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.ITypeDescriptor;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.properties.TypeDescriptors;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.images.*;
import com.nokia.sdt.datamodel.util.MessageLocators;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.emf.component.ChoiceType;
import com.nokia.sdt.emf.component.MapArrayMemberType;
import com.nokia.sdt.emf.component.MapArrayTypeType;
import com.nokia.sdt.emf.component.MapBitmaskMemberType;
import com.nokia.sdt.emf.component.MapBitmaskTypeType;
import com.nokia.sdt.emf.component.MapEnumMemberType;
import com.nokia.sdt.emf.component.MapEnumTypeType;
import com.nokia.sdt.emf.component.MapFixedMemberType;
import com.nokia.sdt.emf.component.MapFixedTypeType;
import com.nokia.sdt.emf.component.MapIdentifierMemberType;
import com.nokia.sdt.emf.component.MapIdentifierTypeType;
import com.nokia.sdt.emf.component.MapInstanceMemberType;
import com.nokia.sdt.emf.component.MapInstanceTypeType;
import com.nokia.sdt.emf.component.MapMemberFromTypeType;
import com.nokia.sdt.emf.component.MapReferenceMemberType;
import com.nokia.sdt.emf.component.MapResourceElementType;
import com.nokia.sdt.emf.component.MapResourceMemberType;
import com.nokia.sdt.emf.component.MapResourceType;
import com.nokia.sdt.emf.component.MapResourceTypeType;
import com.nokia.sdt.emf.component.MapSimpleMemberType;
import com.nokia.sdt.emf.component.MappingArrayType;
import com.nokia.sdt.emf.component.MappingBitmaskType;
import com.nokia.sdt.emf.component.MappingEnumType;
import com.nokia.sdt.emf.component.MappingFixedType;
import com.nokia.sdt.emf.component.MappingIdentifierType;
import com.nokia.sdt.emf.component.MappingInstanceType;
import com.nokia.sdt.emf.component.MappingReferenceType;
import com.nokia.sdt.emf.component.MappingResourceType;
import com.nokia.sdt.emf.component.MappingSimpleType;
import com.nokia.sdt.emf.component.SelectType;
import com.nokia.sdt.emf.component.SourceMappingType;
import com.nokia.sdt.emf.component.SourceTypeMappingType;
import com.nokia.sdt.emf.component.StandaloneType;
import com.nokia.sdt.emf.component.TwoWayMappingType;
import com.nokia.sdt.sourcegen.IComponentSourceMapping;
import com.nokia.sdt.sourcegen.core.ResourceTracker.ResourceInfo;
import com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator;
import com.nokia.sdt.sourcegen.doms.rss.IRssModelManipulator;
import com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.utils.MessageReporting;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.ui.views.properties.IPropertySource;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstanceSourceMapper {

    SourceMappingType srcmap;
    IComponentInstance container;
    InstanceMappingContext mappingContext;
    Stack mappingContextStack;
    IRssModelManipulator rssManipulator;
    
    // not set until mapResource()
    ITranslationUnit tu;
    IAstRssSourceFile mainFile;
    
    /** If set, map a RESOURCE statement, else map an expression */
    public static final int MAP_STATEMENT = 1;
    /** If unset, map a resource expression (constant just for convenience) */
    public static final int MAP_EXPRESSION = 0;
    /** If set, forcibly generate the resource (statement),
     * even if already generated as an expression */ 
    public static final int MAP_FORCE = 2;
    /** 'select' property selector for the current array index */
	private static final String INDEX_SELECTOR = "#(arrayIndex)"; //$NON-NLS-1$
    
    static class PropertyMapping {
        /** The TwoWayMappingType derivative */
        Class klazz;
        /** The mapper */
        IPropertyMapper mapper;
        
        public PropertyMapping(Class klazz, IPropertyMapper mapper) {
            this.klazz = klazz;
            this.mapper = mapper;
        }
    }
    
    /** of PropertyMapping */
    private List propertyMappers;
	private IRssModelGenerator rssGenerator;
	private IRssProjectFileManager fileManager;

    static class ResourceMapping {
        /** The mapped resource */
        IAstResource rsrc;
        /** The identifier for the resource (may be null) */
        String rsrcId;
        /**
         * @param rsrc
         * @param id
         */
        public ResourceMapping(IAstResource rsrc, String id) {
            this.rsrc = rsrc;
            rsrcId = id;
        }
    }
    
    public InstanceSourceMapper(IRssModelGenerator generator, IComponentInstance instance) {
        Check.checkArg(instance);

        this.rssGenerator = generator;
        this.rssManipulator = generator.getModelManipulator();
        this.fileManager = (IRssProjectFileManager) generator.getSourceGenProvider().getAdapter(IRssProjectFileManager.class); 

        this.mappingContextStack = new Stack();
        this.container = ModelUtils.getRootInstance(instance);
        
        propertyMappers = new ArrayList();
        propertyMappers.add(new PropertyMapping(MappingSimpleType.class,
                new SimplePropertyMapper(this)));
        propertyMappers.add(new PropertyMapping(MappingInstanceType.class,
                new InstancePropertyMapper(this)));
        propertyMappers.add(new PropertyMapping(MappingReferenceType.class,
                new ReferencePropertyMapper(this)));
        // note: mapReference can also work when pointing to an instance
        propertyMappers.add(new PropertyMapping(MappingReferenceType.class,
                new InstancePropertyMapper(this)));
        propertyMappers.add(new PropertyMapping(MappingFixedType.class,
                new FixedPropertyMapper(this)));
        propertyMappers.add(new PropertyMapping(MappingEnumType.class, 
                new EnumPropertyMapper(this)));
        propertyMappers.add(new PropertyMapping(MappingResourceType.class,
                new ResourcePropertyMapper(this)));
        propertyMappers.add(new PropertyMapping(MappingIdentifierType.class,
                new IdentifierPropertyMapper(this)));
        propertyMappers.add(new PropertyMapping(MappingArrayType.class, 
                new ArrayFromSequencePropertyMapper(this)));
        propertyMappers.add(new PropertyMapping(MappingArrayType.class, 
                new ArrayFromContainerPropertyMapper(this)));
        propertyMappers.add(new PropertyMapping(MappingBitmaskType.class, 
                new BitmaskPropertyMapper(this)));
       
    }

    void pushContext(InstanceMappingContext mapCtx) {
        mappingContext = mapCtx;
        mappingContextStack.push(mappingContext);
    }
    
    void popContext() {
        mappingContextStack.pop();
        if (mappingContextStack.size() > 0)
            mappingContext = (InstanceMappingContext) mappingContextStack.peek();
        else
            mappingContext = null;
    }

    /**
     * Get the translation unit
     * @return ITranslationUnit
     */
    public ITranslationUnit getTranslationUnit() {
        return tu;
    }
    
    /**
     * Export (create or update) a resource definition for the instance. 
     * This generic entry point doesn't care how the resource 
     * is emitted, only that it gets emitted. So if someone else 
     * has emitted an instance's resources as a subexpression,
     * we don't emit it again here. An explicit reference (LLINK) 
     * from another component results in a separate resource definition 
     * from the #findOrCreateResources(IComponentInstance, boolean, boolean) path.
     * @param component 
     * @param instance 
     */
    public void export(SourceMappingType srcmap, IComponentInstance instance, IComponent component) {
        mapInstance(srcmap.getGroup(), instance, 
                ModelUtils.getPropertySource(instance.getEObject()),
                MAP_STATEMENT,
                null);
    }
    
	String getComponentStack() {
    	LinkedHashSet<String> compressedIds = new LinkedHashSet<String>();
        for (int i = mappingContextStack.size(); i-- > 0; ) {
            InstanceMappingContext ctx = (InstanceMappingContext) mappingContextStack.get(i);
            compressedIds.add(ctx.getInstance().getComponentId());
        }
    	
        StringBuffer buf = new StringBuffer();
        boolean first = true;
        for (String id : compressedIds) {
        	if (!first)
        		buf.append(", "); //$NON-NLS-1$
        	else
        		first = false;
            buf.append(id);
        }
        return buf.toString();
    }
    
    /**
     * Map a single component instance to a single resource definition.
     * 
     * @param mr the facet for mapping resources
     * @return the basic resource definition
     */
    private IAstResource getTargetResource(MappingResourceType mr,
            ResourceInfo info, int mapFlags, IAstResource existing) {
        // get the headers 
        ensureIncludedFiles(mr.getHeaders());
        
        // now look up the struct name
        String type = mr.getStruct();
        IAstStructDeclaration decl = null;
        if (type != null) 
            decl = tu.findStructDeclaration(type);
        if (decl == null) {
            emit(IStatus.ERROR, 
                    "InstanceSourceMapper.MissingStructDecl", //$NON-NLS-1$
                    new Object[] { 
                        type, 
                        getHeaderListString(mr.getHeaders()), 
                        getComponentStack() }
            );
            return null;
        }

        // now get a resource definition to modify:
        // the tracked resource trumps the DOM
        IAstResource def = info != null ? info.findResourceDefinition(rssGenerator) : null;
        if (def == null)
        	def = existing;
        if ((mapFlags & MAP_STATEMENT) != 0) {
            // make a top-level resource definition
        	if (!(def instanceof IAstResourceDefinition))
        		def = findOrCreateResourceDefinition(decl, info, mappingContext.getRsrcName());
        } else {
            // make a resource expression
        	if (!(def instanceof IAstResourceExpression))
        		def = new AstResourceExpression(decl);
        }
        if (def instanceof IAstResourceDefinition)
        	rssGenerator.referenceResource((IAstResourceDefinition) def);
        return def;
    }

    /** Get an appropriate property mapper for the given type and property */
    IPropertyMapper getPropertyMapper(IAstArrayDeclarator array, 
            IAstSimpleDeclaration type, 
            String propertyName, 
            TwoWayMappingType mapping) {
    	

        for (Iterator iter = propertyMappers.iterator(); iter.hasNext();) {
            PropertyMapping pm = (PropertyMapping) iter.next();
            if (pm.klazz.isInstance(mapping)
                    && pm.mapper.checkMapping(array, type, 
                            mappingContext.getPropertyContext(), propertyName))
                return pm.mapper;
        }
        return null;
    }
    
    /** Look up the mapping for the given property type and typeid */
    TwoWayMappingType resolveTypeMapping(String propertyId, String typeId) {
    	// find the type id for this new property source
    	String propertyTypeName = null;
        NodePathLookupResult result = lookupProperty(mappingContext.getPropertyContext(), propertyId, false);
       	if (result != null && result.status == null) {
        	ITypeDescriptor typeDescriptor = TypeDescriptors.getTypeDescriptorForPropertyDescriptor(result.descriptor);
	    	if (typeDescriptor != null) {
	    		propertyTypeName = TypeDescriptors.getTypeDescriptorId(typeDescriptor);
	    	}
        }
        
    	if (propertyTypeName == null) {
    		 emit(IStatus.ERROR, 
                     "InstanceSourceMapper.InvalidTypeMappingType", //$NON-NLS-1$
                     new Object[] { 
                         propertyId }
             );
    		return null;
    	}
    	
    	// push the property source temporarily (for <select>)
    	InstanceMappingContext innerContext = new InstanceMappingContext(
    			result.instance,
    			result.result instanceof IPropertySource ? (IPropertySource) result.result : result.properties);
    	pushContext(innerContext);
    	try {
	    	// now get its associated source mapping info
	    	SourceTypeMappingType sourceTypeMappingType = getSourceTypeMappingTypeForPropertyType(propertyTypeName);
	    	if (sourceTypeMappingType == null) {
	            emit(IStatus.ERROR, 
	                    "InstanceSourceMapper.MissingTypeMapping", //$NON-NLS-1$
	                    new Object[] { 
	                        propertyId,
	                        typeId }
	            );
	            return null;
	    	}
	    	
	    	// now find the entry that matches the desired id
	    	for (FeatureMap.Entry entry : sourceTypeMappingType.getGroup()) {
	    		String theId = null;
	    		Object v = entry.getValue();
	    		if (v instanceof MapResourceTypeType) {
					theId = ((MapResourceTypeType) v).getTypeId();
	    		} else if (v instanceof MapBitmaskTypeType) {
	    			theId = ((MapBitmaskTypeType) v).getTypeId();
	    		} else if (v instanceof MapArrayTypeType) {
	    			theId = ((MapArrayTypeType) v).getTypeId();
	    		} else if (v instanceof MapFixedTypeType) {
	    			theId = ((MapFixedTypeType) v).getTypeId();
	    		} else if (v instanceof MapEnumTypeType) {
	    			theId = ((MapEnumTypeType) v).getTypeId();
	    		} else if (v instanceof MapIdentifierTypeType) {
	    			theId = ((MapIdentifierTypeType) v).getTypeId();
	    		} else if (v instanceof MapInstanceTypeType) {
	    			theId = ((MapInstanceTypeType) v).getTypeId();
	    		} else if (v instanceof SelectType) {
	    			theId = null;
	    			FeatureMap group = selectChoice((SelectType) v);
	    			if (group != null) {
	    				if (group.size() == 0) {
	    					return null;
	    				} else if (group.size() > 1) {
	    			        emit(IStatus.ERROR, 
	    			                "InstanceSourceMapper.AmbiguousTypeMapping", //$NON-NLS-1$
	    			                new Object[] { 
	    			                    sourceTypeMappingType }
	    			        );
	    			        return null;
	    				} else {
	    					v = group.get(0).getValue();
	    				}
	    			}
	    		} else {
	    			Check.checkState(false);
	    		}
				if (ObjectUtils.equals(typeId, theId)) {
					// ensure unique name
					// @see {@link SourceMappingAdapterXML#updateAndValidateResourceIds() 
					if (v instanceof MappingResourceType) {
						if (((MappingResourceType) v).getId() == null) {
							((MappingResourceType) v).setId("$" + propertyTypeName + (typeId != null ? "$" + typeId : ""));
						}
					}
					
					return (TwoWayMappingType) v;
				}
	    	}
	    	
	        emit(IStatus.ERROR, 
	                "InstanceSourceMapper.MissingTypeMappingTypeId", //$NON-NLS-1$
	                new Object[] { 
	                    propertyId,
	                    propertyTypeName,
	                    typeId }
	        );
	        return null;
    	} finally {
    		popContext();
    	}
	}

    /** Look up the mapping for the given property type */
	private SourceTypeMappingType getSourceTypeMappingTypeForPropertyType(String propertyTypeName) {
    	SourceTypeMappingType sourceTypeMappingType = null;
    	ITypeDescriptor typeDescriptor = mappingContext.getInstance().getDesignerDataModel().getComponentSet().lookupTypeDescriptor(propertyTypeName);
    	if (typeDescriptor != null) {
    		sourceTypeMappingType = TypeDescriptors.getSourceTypeMappingTypeFromTypeDescriptor(typeDescriptor);
    	} else {
    		// how could the undefined type have generated a property source?
    		Check.checkState(false);
    	}
    	return sourceTypeMappingType;
	}

	/**
     * Map a portion of a component instance to a single resource.
     * 
     * @param mr the facet for mapping resources
     * @param mapFlags mask of MAP_xxx
     * @param existing known existing resource or null
     * @return the basic resource definition, which may have a different name
     */
    public IAstResource mapResource(MappingResourceType mr, int mapFlags, IAstResource existing) {

    	//if (!filterResourceById(mr.getId()))
    	//	return null;
    	
    	MapResourceType mrt = null;
    	
    	boolean defaultLocation = true;
    	
    	// get the file
    	if (mr instanceof MapResourceType) {
    		mrt = (MapResourceType) mr;
    		String fileName = templateSubstitution(mrt.getRssFile());
    		if (fileName != null)
    			defaultLocation = false;
    		// TODO: change if reading
    		tu = rssGenerator.createOrLoadTranslationUnit(fileName, true);
    		mainFile = rssGenerator.getRssFile(fileName, true);
    		//if (mainFile == null)
    		//	return null;
    		//tu = mainFile.getTranslationUnit();
    		Check.checkState(tu != null);
    	}
    	
    	IAstResource rsrc = null;
        ResourceInfo info = null;

        // get info
        
        // Make resource id even MORE unique
        String rsrcId = mr.getId();
        if (mappingContext.getArrayIndexProperty() != null) {
        	rsrcId += "." + mappingContext.getArrayIndex();
        }
        info = rssManipulator.getResourceTracker().findOrCreateResourceInfo(
            mappingContext.getInstance(),
           rsrcId);

        if (mappingContext.isRootPropertySource()) {
           
            if ((mapFlags & MAP_STATEMENT) != 0) {
                // see if the rsrc was already generated
                if ((mapFlags & MAP_FORCE) == 0 && info.isComplete())
                    return null;

                if (info.findResourceDefinition(rssGenerator) != null) {
                    // change the potential resource name to the previously generated one
                	mappingContext.setRsrcName(info.getName());
                    if (info.isComplete()) {
                        //return info.getResourceDefinition();
                    	rsrc = info.findResourceDefinition(rssGenerator);
                    	Check.checkState(rsrc != null);
                    	rssGenerator.referenceResource((IAstResourceDefinition) rsrc);
                    	return rsrc;
                    }
                    
                    rsrc = info.findResourceDefinition(rssGenerator);
                    rssGenerator.referenceResource((IAstResourceDefinition) rsrc);
                } else if (mr instanceof MapResourceType && TextUtils.strlen(((MapResourceType) mr).getBaseName()) > 0) {
                	mappingContext.setRsrcName(((MapResourceType) mr).getBaseName());
                }
            }
        }

        // make a new name, if none other found
        if (mappingContext.getRsrcName() == null) {
            String rsrcName = null;
            rsrcName = rssManipulator.getResourceHandler().getDerivedResourceName(
                    mappingContext.getInstance().getDesignerDataModel(), 
                    mappingContext.getInstance().getName());
            mappingContext.setRsrcName(rsrcName);
        }
        
        // and, of course, keep unnamed resources unnamed
        if (mrt != null && mrt.isUnnamed()) {
        	mappingContext.setRsrcName(null);
        	if (existing == null) {
            	// for unnamed, pick the first one of the given type -- no other way to distinguish
            	IAstResourceDefinition[] mainDefs = (IAstResourceDefinition[]) mainFile.getFileNodes(IAstResourceDefinition.class);
            	for (int i = 0; i < mainDefs.length; i++) {
    				if (ObjectUtils.equals(mainDefs[i].getStructType().getStructName().getName(), mr.getStruct())) {
    					existing = mainDefs[i];
    					break;
    				}
    				
    			}
        		
        	}
        }

        // get the resource
        if (rsrc == null)
        	rsrc = getTargetResource(mr, info, mapFlags, existing);
        if (rsrc == null)
            return null;

        IAstStructDeclaration decl = tu.findStructDeclaration(mr.getStruct());
        if (decl == null)
            return null;

        // see if the resource changed types 
        if (!decl.getStructName().getName().equals(
        		rsrc.getStructType().getStructName().getName())) {
        	rsrc.setStructType(decl);
        }
        
        mapResourceList(null, mr.getGroup(), rsrc, decl, mapFlags);
        
        // mark generated resource as complete
        if (info != null) {
        	// if exported multiple times, keep one with a standalone resource
        	if (rsrc instanceof IAstResourceDefinition)
        		info.setResourceDefinition((IAstResourceDefinition) rsrc, defaultLocation);
            info.markComplete();
        }

        // Register the unique array element.  
        if (mr instanceof MapResourceElementType
        		&& ((MapResourceElementType) mr).getInstanceIdentifyingMember() != null) {
        	InstanceMappingContext arrayCtx = findArrayMappingContext();
        	Check.checkState(arrayCtx != null);
        	arrayCtx.getArrayElementMapper().registerMapping(
        			(MapResourceElementType) mr,
        			rsrc);
        }

        return rsrc;
    }

	/**
     * Substitute variables from the name generator 
     * @param rssFile
     * @return
     */
    private String templateSubstitution(String rssFile) {
    	if (rssFile == null)
    		return null;
    	if (rssFile.indexOf("${") < 0)
    		return rssFile;
    	
    	// possible variable substitution
    	Map variables = new HashMap();
    	rssGenerator.getModelManipulator().getVariableProvider().defineInstanceVariables(
    			variables, 
    			mappingContext.getInstance().getEObject());

        VariableSubstitutionEngine subst = new VariableSubstitutionEngine(
        		new IMessageListener() {
        			public boolean isHandlingMessage(IMessage msg) {
        				return true;
        			}
					public void emitMessage(IMessage msg) {
						MessageReporting.emitMessage(msg);
					}
        		}, 
        		mappingContext.getInstance().getComponent().createMessageLocation());
        return subst.substitute(variables, rssFile);
	}

	private InstanceMappingContext findArrayMappingContext() {
    	for (int idx = mappingContextStack.size(); --idx >= 0; ) {
    		InstanceMappingContext ctx = (InstanceMappingContext) mappingContextStack.get(idx);
    		if (ctx.getArrayElementMapper() != null)
    			return ctx;
    	}
    	return null;
	}

	/**
	 * Emit the #include of the mbg file for a suspected image property.
	 * @param tu
	 * @param instance
	 * @param imageInfo
	 */
	private void addImageIncludesForInstance(final IAstRssSourceFile rssFile, 
			IComponentInstance instance,
			IPropertySource ps) {
		IImagePropertyInfo info = ModelUtils.getImagePropertyInfo(instance.getEObject(), ps);
		if (info != null) {
			String generatedInclude = info.getIncludeFilename();
			addImageInclude(rssFile, generatedInclude);
		} else {
			IMultiImagePropertyInfo multiInfo = ModelUtils.getMultiImagePropertyInfo(instance.getEObject(), ps);
			if (multiInfo != null) {
				Map<String, IImagePropertyInfo> map = multiInfo.getImagePropertyInfoMap();
				for (Map.Entry<String, IImagePropertyInfo> entry : map.entrySet()) {
					addImageInclude(rssFile, entry.getValue().getIncludeFilename());
				}
			}				
		} 
	}

	private void addImageInclude(final IAstRssSourceFile rssFile,
			String generatedInclude) {
		if (generatedInclude != null) {
			IAstPreprocessorIncludeDirective include = rssFile.findInclude(generatedInclude);
			if (include == null) {
				IAstPreprocessorIncludeDirective incl = new AstPreprocessorIncludeDirective(
						generatedInclude, false, null);
				rssFile.addFileNode(incl);
			} else if (include.isUserPath()) {
				// update from old version
				include.setUserPath(false);
			}
		}
	}


	/**
     * Map the list of mapping elements to resources.  This is 
     * used for the mapping of items inside a &lt;mapResource&gt;  
     * @param resources list of output resources, containing ResourceMapping elements
     * @param group the list of elements in the mapping (i.e. mapResourceMember, etc)
     * @param def
     * @param decl
     * @param mapFlags mask of MAP_xxx 
     *  
     */
    private void mapResourceList(List resources, FeatureMap group, IAstResource def, IAstStructDeclaration decl, 
            int mapFlags) {
        for (Iterator iter = group.iterator(); iter.hasNext(); ) {
            Object value = ((FeatureMap.Entry) iter.next()).getValue();
            
            if (value instanceof SelectType) {
                //selectChoice(resources, (SelectType) value, def, decl, mapFlags);
            	FeatureMap subGroup = selectChoice((SelectType) value);
            	if (subGroup != null) {
            		mapResourceList(resources, subGroup, def, decl, mapFlags);
            	}
            } else if (value instanceof TwoWayMappingType) {
                TwoWayMappingType mm = (TwoWayMappingType) value;
                PropertyAndMember pam = getMemberMappingInfo(mm);
                Check.checkState(pam != null);
                mapMember(mm, def, decl, pam.member, pam.property, pam.suppressDefault);
            } else
                Check.checkState(false);
        }
    }

    /**
     * Map the list of mapping elements to resources at the top level.
     * @param resources list of output resources, containing ResourceMapping elements
     * @param values the elements in the mapping (i.e. mapResourceMember, etc)
     * @param mapFlags mask of MAP_xxx 
     * @param rsrcId desired rsrc id to pick
     */
    private void mapList(List resources, Object[] values, int mapFlags, String rsrcId) {
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            if (value == null)
                continue;
            if (value instanceof FeatureMap.Entry)
                value = ((FeatureMap.Entry) value).getValue();
            
            // check for <mapResource> or a <mapResourceElement> 
            if (value instanceof MappingResourceType) {
                MappingResourceType mrt = (MappingResourceType) value;
                
                // Skip if asking for a specific resource which doesn't match.
                // Note: rsrcId may be null while mrt.getId() is non-null when
                // auto-generated for uniqueness, so don't bail on that case.
                if (rsrcId != null && !ObjectUtils.equals(rsrcId, mrt.getId())) {
                	continue;
                }
                if (mrt instanceof MapResourceType) {
                	StandaloneType standaloneType = ((MapResourceType) mrt).getStandalone();
                	if (standaloneType.getValue() == StandaloneType.ALWAYS) {
                		mapFlags |= MAP_FORCE;
                	} else if (standaloneType.getValue() == StandaloneType.NEVER) {
                		// don't emit implicitly, only explicitly
                		if ((mapFlags & MAP_STATEMENT) != 0 && (mapFlags & MAP_FORCE) == 0) {
                			continue;
                		}
                	}
                }
                IAstResource rsrc = mapResource(mrt, mapFlags, null);
                if (resources != null && rsrc != null) {
                    resources.add(new ResourceMapping(rsrc, mrt.getId()));
                }
            } else if (value instanceof MappingInstanceType) {
            	MappingInstanceType mit = (MappingInstanceType) value;
            	
                IAstResource[] rsrcs = findOrCreateResources(mappingContext.getInstance(), 
                		mappingContext.getProperties(), mapFlags | MAP_FORCE, mit.getRsrcId());
                if (resources != null && rsrcs != null && rsrcs.length == 1) {
                    resources.add(new ResourceMapping(rsrcs[0], mit.getRsrcId()));
                }
            } else if (value instanceof SelectType) {
                FeatureMap subGroup = selectChoice((SelectType) value);
                if (subGroup != null) {
                	mapList(resources, subGroup.toArray(), mapFlags, rsrcId);
                }
            } else if (value instanceof TwoWayMappingType) {
                emit(Message.ERROR, "InstanceSourceMapper.IllegalNesting", //$NON-NLS-1$
                        new Object[] { ((TwoWayMappingType)value), 
                        mappingContext.getInstance().getComponentId() });
            } else
                Check.checkState(false);
        }
    }

    static class PropertyAndMember {
        public String property;
        public String member;
        public boolean suppressDefault;
        public PropertyAndMember(String property, String member, boolean suppressDefault) {
            this.property = property;
            this.member = member;
            this.suppressDefault = suppressDefault;
        }
    }
    
    PropertyAndMember getMemberMappingInfo(TwoWayMappingType mm) {
        String member = null;
        String property = null;
        boolean suppressDefault = true;
        
        // YUCK!
        if (mm instanceof MapMemberFromTypeType) {
        	member = ((MapMemberFromTypeType)mm).getMember();
            property = ((MapMemberFromTypeType)mm).getProperty();
            suppressDefault =((MapMemberFromTypeType)mm).isSuppressDefault();
        } else if (mm instanceof MapFixedMemberType) {
            member = ((MapFixedMemberType)mm).getMember();
            property = null;
            suppressDefault = ((MapFixedMemberType)mm).isSuppressDefault();
        } else if (mm instanceof MapSimpleMemberType) {
            member = ((MapSimpleMemberType)mm).getMember();
            property = ((MapSimpleMemberType)mm).getProperty();
            suppressDefault = ((MapSimpleMemberType)mm).isSuppressDefault();
        } else if (mm instanceof MapReferenceMemberType) {
            member = ((MapReferenceMemberType)mm).getMember();
            property = ((MapReferenceMemberType)mm).getProperty();
            suppressDefault = ((MapReferenceMemberType)mm).isSuppressDefault();
        } else if (mm instanceof MapInstanceMemberType) {
            member = ((MapInstanceMemberType)mm).getMember();
            property = ((MapInstanceMemberType)mm).getProperty();
            suppressDefault = ((MapInstanceMemberType)mm).isSuppressDefault();
        } else if (mm instanceof MapEnumMemberType) {
            member = ((MapEnumMemberType)mm).getMember();
            property = ((MapEnumMemberType)mm).getProperty();
            suppressDefault = ((MapEnumMemberType)mm).isSuppressDefault();
        } else if (mm instanceof MapIdentifierMemberType) {
            member = ((MapIdentifierMemberType)mm).getMember();
            property = ((MapIdentifierMemberType)mm).getProperty();
            suppressDefault = ((MapIdentifierMemberType)mm).isSuppressDefault();
        } else if (mm instanceof MapResourceMemberType) {
            member = ((MapResourceMemberType)mm).getMember();
            property = ((MapResourceMemberType)mm).getProperty();
            suppressDefault = ((MapResourceMemberType)mm).isSuppressDefault();
        } else if (mm instanceof MapArrayMemberType) {
            member = ((MapArrayMemberType)mm).getMember();
            property = ((MapArrayMemberType)mm).getProperty();
            suppressDefault = ((MapArrayMemberType)mm).isSuppressDefault();
        } else if (mm instanceof MapBitmaskMemberType) {
            member = ((MapBitmaskMemberType)mm).getMember();
            property = ((MapBitmaskMemberType)mm).getProperty();
            suppressDefault = ((MapBitmaskMemberType)mm).isSuppressDefault();
        } else
            return null;
        return new PropertyAndMember(property, member, suppressDefault);
    }

    /**
     * Handle a &lt;choice&gt; element inside &lt;select&gt;
     */
    private FeatureMap selectChoice(SelectType type) {
        
        String value = ""; //$NON-NLS-1$
        NodePathLookupResult result = null; // only set for property="..."
        if (type.getProperty() != null
        		&& type.getProperty().equals(INDEX_SELECTOR)) {
        	InstanceMappingContext arrayCtx = findArrayMappingContext();
        	if (arrayCtx != null) {
        		value = arrayCtx.getArrayIndexProperty();
        		if (value == null)
        			value = "" + arrayCtx.getArrayIndex();
        	} else {
                emit(Message.ERROR,
                        getInstanceLocation(),
                        "InstanceSourceMapper.InvalidIndexSelectorUse", //$NON-NLS-1$
                        new Object[] {  
                        mappingContext.getInstance().getName(), 
                        mappingContext.getInstance().getComponentId() });
                return null;
        	}
        } else if (type.getProperty() != null) {
            result = ModelUtils.readProperty(
                    mappingContext.getInstance().getEObject(), 
                    mappingContext.getProperties(), 
                    type.getProperty(), true);
            if (result.status == null) {
                Object property = result.result;
                if (property == null)
                    return null;
                // if selecting on an array, use its size as the selector
                if (property instanceof ISequencePropertySource) {
                	property = new Integer(((ISequencePropertySource) property).size());
                }
                value = property.toString();
            } else {
                if (result.key.indexOf("InvalidPropertyPath") >= 0) { //$NON-NLS-1$
                    emit(result.key, getComponentOrInstanceLocation(), result.status);
                    return null;
                }
                // for now, delay error report until we see if
                // this select has an explicit <choice> matching
                // a missing property
                value = ""; //$NON-NLS-1$
            }
        } else if (type.getPropertyExists() != null) {
        	// check whether the given property path exists
        	result = ModelUtils.readProperty(
                    mappingContext.getInstance().getEObject(), 
                    mappingContext.getProperties(), 
                    type.getPropertyExists(), true);
            if (result.status == null) {
            	// not a failure, but property might still be missing
                Object property = result.result;
                if (property == null) {
                    value = Boolean.FALSE.toString();
                } else {
                	value = Boolean.TRUE.toString();
                }
            } else {
            	// failure of the syntax or something else likely
            	// related to drilling down past a missing property
            	value = Boolean.FALSE.toString();
            }
        } else if (type.getIsComponentInstanceOf() != null) {
        	// check whether the current object is an instance of a given component
        	if (ModelUtils.isInstanceOf(mappingContext.getInstance().getEObject(), 
        			type.getIsComponentInstanceOf())) {
        		value = Boolean.TRUE.toString();
        	} else {
        		value = Boolean.FALSE.toString();
        	}
        } else if (type.getAttribute() != null) {
            // match an attribute value
            IAttributes attrs = null;
            if (mappingContext.getComponent() != null)
            	attrs = (IAttributes) mappingContext.getComponent().getAdapter(IAttributes.class);
            if (attrs != null) {
                value = attrs.getAttribute(type.getAttribute());
                if (value == null)
                    value = ""; //$NON-NLS-1$
            }
        } else {
            emit(Message.ERROR,
                    getInstanceLocation(),
                    "InstanceSourceMapper.InvalidSelectUsage", //$NON-NLS-1$
                    new Object[] {  
                    mappingContext.getInstance().getName(), 
                    mappingContext.getInstance().getComponentId() });
            return null;
        }
        
        EList choices = type.getChoice();
        ChoiceType choice = null;
        for (Iterator iter = choices.iterator(); iter.hasNext();) {
            choice = (ChoiceType) iter.next();
            // default value has no attribute
            if (choice.getValue() == null) {
                break;
            }
            if (choice.getValue().equals(value)) {
                break;
            }
            choice = null;
        }
        if (choice == null) {
            if (result != null && result.status != null)
                emit(result.key, getComponentOrInstanceLocation(), result.status);
            emit(Message.ERROR,
                    getInstanceLocation(),
                    "InstanceSourceMapper.NoChoiceMatched", //$NON-NLS-1$
                    new Object[] { 
                    (type.getProperty() != null ? type.getProperty() : type.getAttribute()), 
                    value, 
                    mappingContext.getInstance().getName(), 
                    mappingContext.getInstance().getComponentId(),
                    (type.getProperty() != null ? "property" : "attribute") }); //$NON-NLS-1$ //$NON-NLS-2$
            return null;
        }
        
        /*
        if (def == null)
            mapList(resources, choice.getGroup().toArray(), mapFlags);
        else
            mapResourceList(resources, choice.getGroup(), def, decl, mapFlags);
        */
        return choice.getGroup();
    }

    private MessageLocation getComponentOrInstanceLocation() {
        return MessageLocators.getComponentOrInstanceLocation(mappingContext.getInstance());
    }

    /** Map a property to a single member (possibly indexed) of a struct */
    private void mapMember(TwoWayMappingType mm, IAstResource def, IAstStructDeclaration decl, 
            String memberSpecifier, String propertyName, boolean suppressDefault) {

        Check.checkArg(memberSpecifier);
        
        String memberName = memberSpecifier;
        boolean arrayElement = false;
        int index = -1;
        
        Pattern pattern = Pattern.compile("(.+)\\[(.*)\\]"); //$NON-NLS-1$
        Matcher matcher = pattern.matcher(memberSpecifier);
        if (matcher.matches()) {
            memberName = matcher.group(1);
            try {
            	if (matcher.group(2).length() == 0)
            		index = -1;
            	else
            		index = Integer.parseInt(matcher.group(2));
                arrayElement = true;
            } catch (NumberFormatException e) {
                emit(Message.ERROR,
                        "InstanceSourceMapper.NonNumericIndexInMember",  //$NON-NLS-1$
                        new Object[] { memberSpecifier, matcher.group(2), 
                        mappingContext.getInstance().getComponentId() });
                return;
            }
        }
        
        IAstMemberDeclaration member = lookupMember(decl, memberName);
        if (member == null)
            return;
        
        IAstSimpleDeclaration memberType = member.getMemberType();
        IAstArrayDeclarator array = member.getArrayDeclarator();

        if (arrayElement) {
            // no subarrays allowed...
            array = null;
        }
        
    	// first, promote mapMemberToType to its actual mapping
    	if (mm instanceof MapMemberFromTypeType) {
    		mm = resolveTypeMapping(propertyName, ((MapMemberFromTypeType) mm).getTypeId());
    	}
    	
        // now, if mapping from an array, apply the index to the value
    	if (propertyName != null && propertyName.equals(".") && mappingContext.getArrayIndexProperty() != null) { //$NON-NLS-1$
    		propertyName = mappingContext.getArrayIndexProperty();
    	}

        // find a way to map the property to the member
        IPropertyMapper mapper = getPropertyMapper(array, memberType, propertyName, mm);
        if (mapper == null) {
            emit(Message.ERROR, "InstanceSourceMapper.CannotMapProperty",  //$NON-NLS-1$
                    new Object[] { propertyName, mappingContext.getInstance().getComponentId(), memberName, decl.getStructName().getName() });
            return;
        }
        
        // now create the expression and add it if it differs from default
        IAstExpression defaultInit = null;
        if (!arrayElement && suppressDefault && member.getInitializerExpression() != null)
            defaultInit = member.getInitializerExpression().getExpression();

        // map it
    	IAstMemberInitializer memberInit = def.findMemberInitializer(member.getMemberName().getName());
    	IAstExpression existing = null;
    	if (memberInit != null) {
    		existing = memberInit.getInitializerExpression().getExpression();
    		if (arrayElement) {
    			if (existing instanceof IAstExpressionList) {
    				IAstExpressionList elements = (IAstExpressionList) existing;
    				// index == -1 means end of array
    				if (index >= 0 && index < elements.size())
    					existing = elements.getExpression(index);
    				else
    					existing = null;
    			} else {
    				reportUnexpectedSyntax(existing, propertyName, Messages.getString("InstanceSourceMapper.ArrayElement")); //$NON-NLS-1$
    				existing = null;
    			}
    		}
    	}
    	
    	InstanceMappingContext ctx = mappingContext.copy();
    	ctx.setMemberName(memberName);
    	pushContext(ctx);
    	IAstExpression expr = null;
    	try {
	    	expr = mapper.mapToResource(array, memberType, 
	                mappingContext.getPropertyContext(), 
	                propertyName, 
	                mm, defaultInit, !arrayElement && suppressDefault,
	                existing);
	    	
	        // check for possible image property and ensure header is available
	        if (SymbianModelUtils.isImageRssMemberName(container.getDesignerDataModel(), memberName)) {
	            // export any image #includes
	            NodePathLookupResult result = lookupProperty(mappingContext.getPropertyContext(), propertyName, true);
	            if (result.status == null) {
	            	addImageIncludesForInstance(mainFile, 
	            			result.instance,
	            			result.properties);
	            }
	        }
	    	
    	} finally {
    		popContext();
    	}
    	
        // most mappers are lazy; double-check whether the new value is still the same
        if (existing != null && expr != null && existing != expr) {
        	if (existing.equalValue(expr)
        			|| existing.getNewText(null).equals(expr.getNewText(null)))
        		expr = existing;
        }
        
        // place the expression on the member
        if (expr != null) {
            if (!arrayElement) {
                // initialize member
            	if (memberInit != null && memberInit.getInitializerExpression().getExpression() == expr)
            		;
            	else
            		def.addInitializer(new AstMemberInitializer(
                        new AstIdExpression(member.getMemberName()), 
                        new AstInitializerExpression(expr)));
            } else {
                // add initializer to end of array initializer expression
                IAstMemberInitializer arrayInit = def.findMemberInitializer(memberName);
                IAstExpressionList elist = null;
                if (arrayInit == null) {
                    // add new empty initializer
                    elist = new AstExpressionList();
                    arrayInit = new AstMemberInitializer(new AstIdExpression(member.getMemberName()), 
                            new AstInitializerExpression(elist));
                    def.addInitializer(arrayInit);
                } else {
                    // add element to initializer list
                    IAstExpression ex = arrayInit.getInitializerExpression().getExpression();
                    Check.checkState(ex instanceof IAstExpressionList);
                    elist = (IAstExpressionList) ex;
                }

                // ensure the index is correct
                IAstExpression exprs[] = elist.getList();
                if (index >= 0 && index < exprs.length) {
                	elist.setExpression(index, expr);
                } else if (index > exprs.length) {
                    emit(Message.ERROR, "InstanceSourceMapper.IllegalMemberArrayIndex",  //$NON-NLS-1$
                            new Object[] { memberSpecifier, Integer.toString(exprs.length), propertyName, 
                            mappingContext.getInstance().getComponentId(), memberName, decl.getStructName().getName() });
                } else {
                	elist.addExpression(expr);
                }
            }
        } else {
        	// remove any existing initializer
        	if (memberInit != null)
        		def.removeInitializer(memberInit);
        }
        
    }

    /**
     * Find or create a RESOURCE with the given type.
     *  <p>
     * Tries to reuse existing definition if possible, but uses
     * a new one if the existing one is in a read-only
     * location or if the struct type doesn't match.
     * @param decl the type of the STRUCT
     * @param info the info for this resource
     * @param baseRsrcName the base name used to name the resource
     * @return a usable resource or null
     */
    private IAstResource findOrCreateResourceDefinition(IAstStructDeclaration decl,
            ResourceInfo info, String baseRsrcName) {
        // find out the name for the resource
        String rsrcName;
        IAstResourceDefinition def = null;
        
        if (baseRsrcName == null) {
            // unnamed: this is easy
            def = new AstResourceDefinition(decl, null);
            mainFile.appendFileNode(def);
            return def;
        }
        
        for (int ctr = 0; ctr < IComponentSourceMapping.MAX_NAME_CONFLICTS; ctr++) {
            if (ctr == 0)
                rsrcName = baseRsrcName; //$NON-NLS-1$
            else
                rsrcName = baseRsrcName + "_" + (ctr + 1); //$NON-NLS-1$ //$NON-NLS-2$
            
            // see if the name is free for our use
            ResourceInfo owner = rssManipulator.getResourceTracker().findResourceInfoByResourceName(rsrcName);
            if (owner != null && owner != info)
                continue;
                
            // look for an existing resource, else add it
            def = rssManipulator.getResourceHandler().findOrCreateUniqueResourceDefinition(
            		tu, decl, rsrcName);
            if (def != null) {
                // register the resource
                if (info != null)
                    info.setResourceDefinition(def, true);
                if (def.getTranslationUnit() == null)
                	mainFile.appendFileNode(def);
                return def;
            }
        }

        emit(IStatus.ERROR, 
                    "InstanceSourceMapper.CannotCreateUniqueResource", //$NON-NLS-1$
                    new Object[] { 
                        baseRsrcName, 
                        mappingContext.getInstance().getName(),
                        mappingContext.getComponent().getId() }
            );
        return null;
    }

    /**
     * Look up a property relative to a given instance/property context
     * (i.e. this may find a submember of a compound property within an instance).
     * @param propertyContext the current instance and property source
     * @param propertyPath the path to a new (deeper) property 
     * @param resolveEditableValue if true: if destination is a compound property,
     * try to resolve it to a simpler value with IReconcileProperty
     * @return lookup result (null if no match)
     */
    public NodePathLookupResult lookupProperty(PropertyContext propertyContext, String propertyPath, boolean resolveEditableValue) {
        NodePathLookupResult result = ModelUtils.readProperty(
                propertyContext.instance.getEObject(), 
                propertyContext.properties, 
                propertyPath, resolveEditableValue);
        if (result.status != null) {
            emit(result.key, getComponentOrInstanceLocation(), result.status);
            return null;
        }
        return result;
    }

    /**
     * Find the given member in the given struct
     * @param decl the STRUCT declaration
     * @param memberName member name
     * @return member declaration or null
     */
    private IAstMemberDeclaration lookupMember(IAstStructDeclaration decl, String memberName) {
        IAstMemberDeclaration member = decl.findMember(memberName);
        if (member == null) {
            emit(Message.ERROR, "InstanceSourceMapper.IllegalStructMember",  //$NON-NLS-1$
                    new Object[] { memberName, decl.getStructName().getName(), 
                    mappingContext.getInstance().getComponentId() });
        }
        return member;
    }

    /**
     * Add to the main source file the headers given in the list.
     * 
     * @param headers
     */
    public void ensureIncludedFiles(List headers) {
        if (headers != null) {
            for (Iterator iter = headers.iterator(); iter.hasNext();) {
                String header = (String) iter.next();

                File ifile = fileManager.ensureIncludedFile(tu, header); 
                if (ifile == null) {
                	String paths = Arrays.toString(
                		rssGenerator.getSourceGenProvider().getIncludeFileLocator().getIncludePaths());
                    emit(IStatus.ERROR, 
                            "InstanceSourceMapper.CannotFindHeader", //$NON-NLS-1$
                            new Object[] { header, 
                            mappingContext.getInstance().getComponentId(),
                            paths }); 
                }
            }
        }
    }

    private String getHeaderListString(List headers) {
        StringBuffer hl = new StringBuffer();
        if (headers != null) {
            for (Iterator iter = headers.iterator(); iter.hasNext();) {
                String header = (String) iter.next();
                hl.append(header);
                if (iter.hasNext())
                    hl.append(","); //$NON-NLS-1$
            }
        }
        return hl.toString();
    }

    /**
     * Find or create resource definition(s) for the given instance.
     * 
     * @param refInstance the instance to find/create resources for
     * @param mapFlags mask of MAP_xxx
     * @param rsrcId the generated resource id to return (may be null to matched unnamed ones)
     * @return definitions (null on reported error) 
     */
    public IAstResource[] findOrCreateResources(IComponentInstance refInstance,
            IPropertySource refProperties,
            int mapFlags, String rsrcId) {
        
        IComponent refComponent = refInstance.getComponent();
        if (refComponent == null) {
            emit(Message.ERROR, "InstanceSourceMapper.MissingComponentForReference",  //$NON-NLS-1$
                    new Object[] { mappingContext.getInstance().getName(), refInstance.getName(), refInstance.getComponentId() });
            return null;
        }
        IComponentSourceMapping csm = (IComponentSourceMapping) 
            refComponent.getAdapter(IComponentSourceMapping.class);
        if (csm == null) {
            emit(Message.ERROR, "InstanceSourceMapper.NoResourceForReference",  //$NON-NLS-1$
                    new Object[] { mappingContext.getInstance().getName(), refInstance.getName(), refComponent.getId() });
            return null;
        }
        
        SourceMappingType smt = ((SourceMappingAdapterXML)csm).getSourceMapping();
        Check.checkState(smt != null);
        
        // map skeletons of the referent resources
        ResourceMapping[] map = mapInstance(smt.getGroup(), refInstance, refProperties, mapFlags, rsrcId);
        
        // return the matching entries
        List matches = new ArrayList();
        for (int i = 0; i < map.length; i++) {
        	if (//ObjectUtils.equals(map[i].rsrcId, rsrcId) && 
            		((mapFlags & MAP_STATEMENT) != 0) == (map[i].rsrc instanceof IAstResourceDefinition)) {
                matches.add(map[i].rsrc);
            }

        }
        return (IAstResource[]) matches.toArray(new IAstResource[matches.size()]);
    }

    /**
     * Find or create resource definition(s) for the given instance.
     * @param group facets for mapping
     * @param instance the instance to find/create resources for
     * @param properties the properties to scan
     * @param mapFlags mask of MAP_xxx
     * @param rsrcId the rsrc to generate (may be null)
     * @return definitions (null on reported error) 
     */

    public ResourceMapping[] mapInstance(FeatureMap group, IComponentInstance instance, IPropertySource properties, 
            int mapFlags, String rsrcId) {

        pushContext(new InstanceMappingContext(instance, properties));
        
        List resources = new ArrayList();
        mapList(resources, group.toArray(), mapFlags, rsrcId);

        popContext();
        return (ResourceMapping[]) resources.toArray(new ResourceMapping[resources.size()]);
    }

    /**
     * Find or create resource definition(s) for the given instance.
     * @param values facet values for mapping
     * @param instance the instance to find/create resources for
     * @param properties the properties to scan
     * @param mapFlags mask of MAP_xxx
     * @return definitions (null on reported error) 
     */

    public ResourceMapping[] mapInstance(Object[] values, IComponentInstance instance, IPropertySource properties, 
            int mapFlags, String rsrcId) {

        pushContext(new InstanceMappingContext(instance, properties));
        
        List resources = new ArrayList();
        mapList(resources, values, mapFlags, rsrcId);

        popContext();
        return (ResourceMapping[]) resources.toArray(new ResourceMapping[resources.size()]);
    }

    /**
     * Create or find a resource definition which is the target of a reference.
     * This discovers what the name of the referenced resource will be, creating
     * one if none exists. We do not actually map the resource; we just place an
     * empty resource in the TU, which will be filled out later.
     * <p>
     * This will regenerate a resource even if it's been emitted as 
     * an expression.
     * 
     * @param refId the name of the instance
     * @param rsrcId the resource id to match or null
     * @return definition or null (error logged)
     */
    public IAstResourceDefinition mapReferencedResource(String refId, String rsrcId) {
        
        IComponentInstance refInstance = ModelUtils.lookupReference(mappingContext.getInstance().getDesignerDataModel(), refId);
        IPropertySource refProperties = null;
        if (refInstance != null)
            refProperties = ModelUtils.getPropertySource(refInstance.getEObject());
        if (refInstance == null || refProperties == null) {
            emit(Message.ERROR, "InstanceSourceMapper.UnresolvedReference",  //$NON-NLS-1$
                    new Object[] { mappingContext.getInstance().getName(), refId });
            return null;
        }
        
        int mapFlags = MAP_STATEMENT | MAP_FORCE;

        // get stubs for referents
        IAstResource[] defs = findOrCreateResources(refInstance, refProperties, mapFlags, rsrcId);
        if (defs == null)
            return null;
        
        if (defs.length != 1) {
            if (defs.length > 1) {
                if (rsrcId != null)
                    emit(Message.ERROR, "InstanceSourceMapper.AmbiguousResourceWithIdForReference",  //$NON-NLS-1$
                            new Object[] { mappingContext.getInstance().getName(), rsrcId, 
                            refId, refInstance.getComponentId() });
                else
                    emit(Message.ERROR, "InstanceSourceMapper.AmbiguousResourceForReference",  //$NON-NLS-1$
                        new Object[] { mappingContext.getInstance().getName(), 
                            refId, refInstance.getComponentId() });
            }
            else {
                return null;
            }
        }

        return (IAstResourceDefinition)defs[0];
    }

    private void emit(String key, MessageLocation location, IStatus status) {
        // the message is already created, so directly construct
        // a message instead of using Messages.create(), which tries
        // to format the message anew
        MessageReporting.emitMessage(new Message(status.getSeverity(),
                getInstanceLocation(),
                key, status.getMessage()));
    }

    private MessageLocation getInstanceLocation() {
        return MessageLocators.getInstanceLocation(mappingContext.getInstance());
    }

    public void emit(int severity, MessageLocation location, String key, Object[] args) {
        MessageReporting.emitMessage(Messages.create(severity, location, key, args));
    }

    /**
     * Emit a message
     * @param severity (Message.xxx)
     * @param msgKey
     */
    public void emit(int severity, String msgKey) {
        MessageReporting.emitMessage(Messages.create(severity, getComponentOrInstanceLocation(), msgKey));
    }

    /**
     * Emit a message
     * @param severity (Message.xxx)
     * @param msgKey
     * @param arg
     */
    public void emit(int severity, String msgKey, Object arg) {
        MessageReporting.emitMessage(Messages.create(severity, getComponentOrInstanceLocation(), msgKey, arg));
    }
    
    /**
     * Emit a message
     * @param severity (Message.xxx)
     * @param msgKey
     * @param args
     */
    public void emit(int severity, String msgKey, Object[] args) {
        MessageReporting.emitMessage(Messages.create(severity, getComponentOrInstanceLocation(), msgKey, args));
    }

    IAstEnumerator createUniqueEnumerator(String nameAlg, 
            IComponentInstance instance, String propertyId) {
    	return rssManipulator.getTypeHandler().createUniqueEnumerator(
    			tu, mainFile,
    			container, nameAlg, instance, 
    			propertyId, true);
    }

	public String createEnumeratorName(String nameAlgorithm, IComponentInstance instance, String propertyId) {
		return rssManipulator.getTypeHandler().createEnumeratorName(container, nameAlgorithm, instance, propertyId);
	}

	public Object findUniqueEnumerator(String nameAlgorithm, IComponentInstance instance, String propertyId) {
    	return rssManipulator.getTypeHandler().findEnumerator(
    			instance.getName(), propertyId, nameAlgorithm);
	}

	IAstExpression findOrCreateUniqueEnumeratorExpression(String nameAlgorithm, IComponentInstance instance, String propertyId) {
    	Object enumObj = findUniqueEnumerator(
        		nameAlgorithm, instance, propertyId);

    	if (enumObj instanceof String) {
    		// tentative; ensure it's registered in RSS
    		IAstEnumerator enm = rssManipulator.getTypeHandler().createUniqueEnumerator(
    				tu, mainFile, container, nameAlgorithm,
    				instance, propertyId, true);
    		if (enm != null)
    			return new AstIdExpression(enm.getName());
    		else
    			return null;
    	}
    	
        IAstEnumerator uniqueEnum = (IAstEnumerator) enumObj; 
        if (uniqueEnum == null) {
        	uniqueEnum = createUniqueEnumerator(
                nameAlgorithm, instance, propertyId);
        } else {
        	// ensure the enum has the correct value still, if it has a value
        	String value = rssManipulator.getTypeHandler().getNameAlgorithmEnumeratorValue(nameAlgorithm, instance, propertyId);
        	if (value != null) {
        		IAstLiteralExpression expr = new AstLiteralExpression(IAstLiteralExpression.K_INTEGER, value);
        		if (!expr.equalValue(uniqueEnum.getInitializerExpression().getExpression())) {
        			uniqueEnum.setInitializerExpression(new AstInitializerExpression(expr));
        		}
        	}
        }
        if (uniqueEnum == null)
            return null;

        return new AstIdExpression(uniqueEnum.getName());
	}

	/**
	 * Report a generic error that the DOM node does not match
	 * what we expected 
	 * @param node the errant node
	 * @param propertyId the property being mapped
	 * @param expectedInfo localized string summarizing what was expected
	 */
	public void reportUnexpectedSyntax(IAstNode node, String propertyId, String expectedInfo) {
		if (node.getSourceRange() == null) {
			// internal error
			ComponentSystemPlugin.log(new IllegalStateException("unexpected syntax in generated node " + node + " / " + propertyId + " / " +expectedInfo)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return;
		}
		
		emit(IStatus.ERROR, getComponentOrInstanceLocation(),
				"InstanceSourceMapper.UnexpectedSyntax", //$NON-NLS-1$
				new Object[] { expectedInfo,
					node.getSourceRange().getFile().getFileName(),
					node.getSourceRange().getLine(),
					mappingContext.getInstance().getName(),
					mappingContext.getComponent().getId() });
	}
}
