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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss.impl;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.core.Tuple;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.doms.rss.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.sdt.sourcegen.provider.SourceGenProvider;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.*;

import java.util.*;

/**
 * Handle types for our RSS DOM
 * 
 *
 */
public class RssModelTypeHandler implements IRssModelTypeHandler {
	static class EnumKey extends Tuple {
		public EnumKey(String instanceName, String propertyId, String nameAlg) {
			super(instanceName, propertyId == null ? "" : propertyId, nameAlg);
		}
		public EnumKey(EnumKey key) {
			super(key.get(INSTANCE_NAME), key.get(PROPERTY_ID), key.get(NAME_ALGORITHM));
		}
		final static int INSTANCE_NAME = 0;
		final static int PROPERTY_ID = 1;
		final static int NAME_ALGORITHM = 2;
		
		/** Set if the entry came from another model */
		boolean isExternal;
		
		public String getInstanceName() {
			return (String) get(INSTANCE_NAME);
		}
		public String getPropertyId() {
			return (String) get(PROPERTY_ID);
		}
		public String getNameAlgorithm() {
			return (String) get(NAME_ALGORITHM);
		}
	}

	static class ArrayKey extends Tuple {
		public ArrayKey(String arrayInstanceName, String arrayPropertyId, String arrayMemberId) {
			super(arrayInstanceName, arrayPropertyId, arrayMemberId);
		}
		final static int INSTANCE_NAME = 0;
		final static int PROPERTY_ID = 1;
		final static int MEMBER_ID = 2;

		/** Set if the entry came from another model */
		boolean isExternal;

		public String getInstanceName() {
			return (String) get(INSTANCE_NAME);
		}
		public String getPropertyId() {
			return (String) get(PROPERTY_ID);
		}
		public String getMemberId() {
			return (String) get(MEMBER_ID);
		}
	}

	private IRssProjectFileManager manager;
	/** map of enum key (getEnumKey) to IAstEnumerator or String */
	private Map<EnumKey, Object> enumKeysToEnumeratorMap;
	private INameGenerator nameGenerator;
	private Map<String, INameAlgorithm> nameAlgorithms;
	private Map<ArrayKey, Map<String, String>> arrayMap;

	/**
	 * 
	 */
	public RssModelTypeHandler(IRssProjectFileManager manager, IRssModelManipulator manipulator) {
		Check.checkArg(manipulator);
		this.manager = manager;
		this.nameGenerator = manipulator.getNameGenerator();
		this.nameAlgorithms = new HashMap<String, INameAlgorithm>();
		this.enumKeysToEnumeratorMap = new HashMap<EnumKey, Object>();
		this.arrayMap = new HashMap<ArrayKey, Map<String, String>>();
	}
	
	private EnumKey getEnumKey(String instanceName, String propertyId, String nameAlg) {
		// dereferences also map to instance
	    if (propertyId.equals(".") || propertyId.equals("") //$NON-NLS-1$ //$NON-NLS-2$ 
	    		|| propertyId.endsWith("->")) //$NON-NLS-1$
	        propertyId = null;
	    return new EnumKey(instanceName, propertyId, nameAlg);
	}
	
	private ArrayKey getArrayKey(String arrayInstanceName, String arrayPropertyId, String arrayMemberId, String uniqueValue) {
		return new ArrayKey(arrayInstanceName, arrayPropertyId, arrayMemberId);
	}

	/**
	 * Register an IAstEnumerator as the generated value of the given
	 * instance's property.
	 * @param instance
	 * @param propertyId
	 * @param enm
	 */
	public void registerEnumerator(String instanceName, String propertyId, String nameAlg, IAstEnumerator enm) {
	    enumKeysToEnumeratorMap.put(getEnumKey(instanceName, propertyId, nameAlg), enm);        
	}

	/**
	 * Register a string as the generated value of the given
	 * instance's property.
	 * @param instance
	 * @param propertyId
	 * @param enm
	 */
	public void registerEnumerator(String instanceName, String propertyId, String nameAlg, String enm) {
	    enumKeysToEnumeratorMap.put(getEnumKey(instanceName, propertyId, nameAlg), enm);        
	}

	/**
	 * Create an object used to convert instance/property pairs to enumerators.
	 * @param nameAlg extension nameAlgorithm.id
	 * @return
	 */
	public INameAlgorithm getNameAlgorithm(String nameAlg) {
		INameAlgorithm alg = nameAlgorithms.get(nameAlg);
		if (alg == null) {
			try {
				alg = SourceGenProvider.createNameAlgorithm(nameAlg);
			} catch (CoreException e) {
				SourceGenPlugin.getDefault().log(e);
			}
			nameAlgorithms.put(nameAlg, alg);
		}
		return alg;
	}

	/** Look up the IAstEnumerator or String registered as the value of the given
	 * instance's property.
	 * @param instance
	 * @param propertyId
	 * @param nameAlg 
	 * @return registered value
	 */
	public Object findEnumerator(String instanceName, String propertyId, String nameAlg) {
	    return enumKeysToEnumeratorMap.get(getEnumKey(instanceName, propertyId, nameAlg));
	}

	public IAstEnumerator findGeneratedEnumeratorByName(String name) {
	    for (Iterator iter = enumKeysToEnumeratorMap.values().iterator(); iter.hasNext();) {
	        Object val = iter.next();
	        if (val instanceof IAstEnumerator) {
	            IAstEnumerator enm = (IAstEnumerator) val;
	            if (enm.getName().getName().equals(name))
	                return enm;
	        }
	    }
	    return null;
	}

	/** 
	 * Create an enumerator name using the given name algorithm.
	 * This remembers the generated name but does not emit its
	 * declaration anywhere (the caller is assumed to be creating
	 * an enum or referencing one from a previously generated file).
	 * @param nameAlg name generation algorithm
	 * @param instance the instance owning the property
	 * @param propertyId the property path 
	 * @return name
	 */
	public String createEnumeratorName(IComponentInstance container, String nameAlg, IComponentInstance instance, String propertyId) {
	    String containerName = nameGenerator.getViewName(container.getEObject());
	    
	    // get only the raw property name
	    int idx = propertyId.lastIndexOf('.');
	    if (idx >= 0)
	        propertyId = propertyId.substring(idx + 1);
	    
	    String instName = instance.getName();
	    if (instName == null)
	        instName = "Unnamed"; //$NON-NLS-1$
	
	    INameAlgorithm algorithm = getNameAlgorithm(nameAlg);
	    if (algorithm == null) {
	    	Utilities.emit(instance, IStatus.ERROR, "SourceGenContext.UnknownAlgorithm", //$NON-NLS-1$ 
	                new String[] { nameAlg, instance.getComponentId() });
	        return null;
	    }
	    
	    String enumName = algorithm.getEnumeratorName(instance, containerName, instName, propertyId);
	    if (enumName == null)
	        return null;
	    
	    enumName = Utilities.getCleanIdentifierName(enumName);
	
	    // remember the enumerator for queries from one-way
	    registerEnumerator(instance.getName(), propertyId, nameAlg, enumName);
	
	    return enumName;
	}
	
	/**
     * Register a unique enumeration declaration. Creates the enumeration if it
     * does not exist, in given file's *.hrh file. Creates the *.hrh file if
     * it does not exist.
     * <p>
     * The enumeration may be unnamed enumeration, in which case only one which
     * contains an initial enumerator matching the given initial value is
     * returned.
     * 
     * @param context
     *            the RSS context
     * @param enumDeclName
     *            the enumeration name (or null for an unnamed enum)
     * @param initialValue
     *            the initial value of the enumerator (integer literal)
     * @return enumeration
     */
    public IAstEnumDeclaration findOrCreateEnumeration(
            IAstRssSourceFile enumHeaderFile,
            String enumDeclName,
            String initialValue) {
        
        Check.checkArg(enumHeaderFile);
        // initialValue can be null
        // enumDeclName can be null
        
        IAstEnumDeclaration decl = null;
        
        // if we want an unnamed enumerator, place the enumerator
        // in an existing or new enumeration that lives in the
        // expected *.hrh file and whose first enum 
        // starts with the same initial value
        if (enumDeclName == null) {
            IAstEnumDeclaration[] decls = (IAstEnumDeclaration[]) enumHeaderFile.getFileNodes(IAstEnumDeclaration.class);
            for (int i = 0; i < decls.length; i++) {
                IAstEnumDeclaration edecl = (IAstEnumDeclaration) decls[i];

                // unnamed?
                if (edecl.getName() != null)
                    continue;
                
                IAstEnumerator[] enms = edecl.getEnumerators();
                if (enms.length == 0) {
                    // add to this blank one
                    decl = edecl;
                    break;
                }

                // if first enum is uninitialized, don't bother it
                if (enms[0].getInitializerExpression() == null)
                    continue;
                
                // make sure the first initializer matches our literal
                IAstExpression expr = enms[0].getInitializerExpression().getExpression();
                if (expr != null) {
	                if (!(expr instanceof IAstLiteralExpression))
	                    continue;
	                
	                IAstLiteralExpression lit = (IAstLiteralExpression) expr;
	                if (lit.getKind() == IAstLiteralExpression.K_INTEGER
	                		&& initialValue != null
	                        && lit.getValue().equals(initialValue)) {
	                    // a match!
	                    decl = edecl;
	                    break;
	                }
                } else {
                	// or if unnamed starts with no initializer
                	if (initialValue == null) {
                		// a match!
                		decl = edecl;
                		break;
                	}
                }
            }
        } else {
            IAstEnumDeclaration[] decls = (IAstEnumDeclaration[]) enumHeaderFile.getFileNodes(IAstEnumDeclaration.class);
            for (int i = 0; i < decls.length; i++) {
                IAstEnumDeclaration edecl = (IAstEnumDeclaration) decls[i];
                if (edecl.getName() != null 
                		&& edecl.getName().getName().equals(enumDeclName)) {
                	decl = edecl;
                	break;
                }
            }
        }

        // no enumeration found, make one
        if (decl == null) {
            decl = new AstEnumDeclaration(new AstName(enumDeclName, null));
            enumHeaderFile.addFileNode(decl);
        }
        
        if (enumHeaderFile.getTranslationUnit() != null)
            enumHeaderFile.getTranslationUnit().refresh();
        
        return decl;
    }

    public IAstEnumerator getUniqueEnumerator(
            IComponentInstance instance, String propertyId, 
            ITranslationUnit tu, IAstRssSourceFile rssFile, String enumDeclName,
            String initialValue, String value,
            String enumName, int maximumConflicts, boolean includeIt) {
        Check.checkArg(tu);
        Check.checkArg(rssFile);
        Check.checkArg(enumName);

        // the value for the initial is the explicit value if not defined
        if (initialValue == null) {
        	initialValue = value;
        }
        
        // use or define the enumeration 
        IAstEnumDeclaration decl = null;

        // usually look for a named enumeration
        if (enumDeclName != null) {
            decl = tu.findEnumDeclaration(enumDeclName);
            
            // The entire TU may have an enum with this name, but it
            // needs to be accessible from the rssFile.  We assume that
            // since these are unique enumerators, any collisions across
            // RSS files are accidents, so force a new enum in such a case.
            if (decl != null) {
            	if (!tu.isAuxiliaryFile(decl.getAstSourceFile())) {
            		if (tu.findIncludedFiles(decl.getAstSourceFile().getSourceFile()).length == 0)
	                    decl = null;
            	}
            }
        }
        
        // either named enumeration not found 
        // or unnamed enumeration requested
        if (decl == null) {
            // find the appropriate file: <basename>.hrh
            IAstRssSourceFile hrhFile = (IAstRssSourceFile) manager.findOrCreateDerivedSourceFile(
                    instance.getDesignerDataModel(),
                    rssFile,
                    INameGenerator.INCLUDE_DIRECTORY_ID, null,  //$NON-NLS-1$
                    ".hrh", AstRssSourceFile.class, null, includeIt); //$NON-NLS-1$

            if (manager.findRssFile(hrhFile.getSourceFile()) == null)
            	manager.registerRssFile(hrhFile);
            
            // find or create an enum {} 
            decl = findOrCreateEnumeration(hrhFile, enumDeclName, initialValue);
        }
        
        // see whether the enumerator is unique
        IAstEnumerator enm = null;
        String theUniqueName = null; 
        Check.checkArg(maximumConflicts > 0);
        for (int unique = 0; unique < maximumConflicts; unique++) {
            if (unique == 0)
                theUniqueName = enumName;
            else
                theUniqueName = enumName + (unique + 1);
            
            // N.B.: this doesn't NEED to be unique across the TU
            // due to namespaces in C++, but this doesn't work
            // for RSS.
            enm = tu.findEnumerator(theUniqueName);
            if (enm == null)
                break;
        }
        
        if (enm != null) {
            // died with an existing enumerator
            MessageReporting.emitMessage(Messages.create(Message.ERROR,
                    new MessageLocation(new Path(decl.getAstSourceFile().getSourceFile().getFile().getAbsolutePath())),
                    "RssEngine.CannotUniquifyEnum", //$NON-NLS-1$
                    new Object[] {
                        enumName,
                        decl.getAstSourceFile().getSourceFile().getFile().getAbsolutePath() }));
            return null;
        }
        
        // now see if this is the first enumerator
        IAstName enumNameNode = new AstName(theUniqueName, null);
        
        // assign a value if provided
        if (value == null && decl.getEnumerators().length == 0) {
        	value = initialValue;
        }
        
        if (value != null) {
            IAstLiteralExpression lit = new AstLiteralExpression(IAstLiteralExpression.K_INTEGER, value);
            enm = new AstEnumerator(enumNameNode, new AstInitializerExpression(lit));
        }
        else {
            enm = new AstEnumerator(enumNameNode, null);
        }
        
        // add the enumerator
        decl.addEnumerator(enm);

        tu.refresh();

        return enm;
    }

	/** 
	 * Create a unique enumerator using the given name algorithm,
	 * adding a declaration and enum to a generated *.hrh file,
	 * and remember it for later so it can looked up.
	 * @param nameAlg
	 * @param instance the instance owning the enumerator
	 * @param propertyId the property path
	 * @return IAstEnumerator or null
	 */
	public IAstEnumerator createUniqueEnumerator(
			ITranslationUnit tu, IAstRssSourceFile rssFile,
			IComponentInstance container,
			String nameAlg, 
	        IComponentInstance instance, String propertyId,
	        boolean includeHeader) {
	    String containerName = nameGenerator.getViewName(container.getEObject());
	    
	    // get only the raw property name
	    int idx = propertyId.lastIndexOf('.');
	    if (idx >= 0)
	        propertyId = propertyId.substring(idx + 1);
	    
	    String instName = instance.getName();
	    if (instName == null)
	        instName = "Unnamed"; //$NON-NLS-1$
	    
	    INameAlgorithm algorithm = getNameAlgorithm(nameAlg);
	    if (algorithm == null) {
	    	Utilities.emit(instance, IStatus.ERROR, 
	    			"SourceGenContext.UnknownAlgorithm", //$NON-NLS-1$
	    			new String[] { nameAlg, instance.getComponentId() });
	        return null;
	    }
	    
	    String enumDeclName = algorithm.getEnumDeclarationName(instance, containerName, instName, propertyId);
	    String enumName = algorithm.getEnumeratorName(instance, containerName, instName, propertyId);
	    String initialValue = algorithm.getInitialEnumeratorValue(instance, propertyId);
	    String value = algorithm.getEnumeratorValue(instance, propertyId);
	
	    enumDeclName = Utilities.getCleanIdentifierName(enumDeclName);
	    enumName = Utilities.getCleanIdentifierName(enumName);
	
	    IAstEnumerator enm = getUniqueEnumerator(
	            instance, propertyId,
	            tu, rssFile, enumDeclName, 
	            initialValue, value, enumName, IComponentSourceMapping.MAX_NAME_CONFLICTS,
	            includeHeader);
	
	    // remember the enumerator for further queries
	    registerEnumerator(instance.getName(), propertyId, nameAlg, enm);
	
	    if (!includeHeader) {
	    	// ensure we recognize the non-included file needs to be generated
	    	manager.replaceRssFile((IAstRssSourceFile) enm.getAstSourceFile());
	    }
	    	
	    return enm;        
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#getNameAlgorithmEnumeratorValue(java.lang.String, com.nokia.sdt.datamodel.adapter.IComponentInstance, java.lang.String)
	 */
	public String getNameAlgorithmEnumeratorValue(String nameAlg,
			IComponentInstance instance, String propertyId) {
	    INameAlgorithm algorithm = getNameAlgorithm(nameAlg);
	    if (algorithm == null) {
	    	Utilities.emit(instance, IStatus.ERROR, 
	    			"SourceGenContext.UnknownAlgorithm", //$NON-NLS-1$
	    			new String[] { nameAlg, instance.getComponentId() });
	        return null;
	    }
	    
	    String value = algorithm.getEnumeratorValue(instance, propertyId);
	    return value;
	}
	
	private Collection<EnumKey> getEnumeratorKeysForInstance(String instanceName) {
		List<EnumKey> keys = new ArrayList<EnumKey>();
		for (Iterator iter = enumKeysToEnumeratorMap.keySet().iterator(); iter.hasNext();) {
			EnumKey key = (EnumKey) iter.next();
			if (!key.isExternal && key.getInstanceName().equals(instanceName)) {
				keys.add(key);
			}
		}
		return keys;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#getEnumeratorMappingsForInstance(java.lang.String)
	 */
	public Object[] getEnumeratorMappingsForInstance(String instanceName) {
		Collection<EnumKey> keys = getEnumeratorKeysForInstance(instanceName);
		Object[] values = new Object[keys.size()];
		int idx = 0;
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			EnumKey key = (EnumKey) iter.next();
			values[idx++] = enumKeysToEnumeratorMap.get(key);
		}
		return values;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#removeEnumeratorMappingsForInstance(java.lang.String)
	 */
	public void removeEnumeratorMappingsForInstance(String instanceName) {
		Collection<EnumKey> keys = getEnumeratorKeysForInstance(instanceName);
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			EnumKey key = (EnumKey) iter.next();
			enumKeysToEnumeratorMap.remove(key);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#merge(com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler)
	 */
	public void merge(IRssModelTypeHandler typeHandler) {
		for (Iterator iter = ((RssModelTypeHandler)typeHandler).enumKeysToEnumeratorMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<EnumKey, Object> entry = (Map.Entry<EnumKey, Object>) iter.next();
			EnumKey key = new EnumKey(entry.getKey());
			key.isExternal = true;
			enumKeysToEnumeratorMap.put(key, entry.getValue());
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#reset()
	 */
	public void reset() {
		enumKeysToEnumeratorMap.clear();
		arrayMap.clear();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#resetPhantomEnumerators()
	 */
	public void resetPhantomEnumerators() {
		for (Iterator iter = enumKeysToEnumeratorMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<EnumKey, Object> entry = (Map.Entry<EnumKey, Object>) iter.next();
			if (!(entry.getValue() instanceof IAstEnumerator))
				iter.remove();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#resetForRssFile(java.lang.String)
	 */
	public void resetForRssFile(String fileName) {
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#findInstanceForArrayElement(com.nokia.sdt.datamodel.adapter.IComponentInstance, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String findInstanceForArrayElement(IComponentInstance arrayInstance, String arrayPropertyId, String arrayMemberId, String uniqueValue) {
		ArrayKey key = new ArrayKey(arrayInstance.getName(), arrayPropertyId, arrayMemberId);
		Map<String, String> array = arrayMap.get(key);
		if (array == null)
			return null;
		return array.get(uniqueValue);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#removeArrayElement(com.nokia.sdt.datamodel.adapter.IComponentInstance, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void removeArrayElement(IComponentInstance arrayInstance, String arrayPropertyId, String arrayMemberId, String uniqueValue, String elementName) {
		ArrayKey key = new ArrayKey(arrayInstance.getName(), arrayPropertyId, arrayMemberId);
		Map<String, String> array = arrayMap.get(key);
		Check.checkArg(array != null);
		array.remove(uniqueValue);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#saveState(com.nokia.sdt.emf.dm.ISourceGenMappingState)
	 */
	public void saveState(IDesignerDataModel dataModel, ISourceGenMappingState state) {
		IEnumMappings enumMappings = DmFactory.eINSTANCE.createIEnumMappings();
		
		for (Iterator iter = enumKeysToEnumeratorMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<EnumKey, Object> entry = (Map.Entry<EnumKey, Object>) iter.next();
			EnumKey key = entry.getKey();
			if (!key.isExternal) {
				IEnumMapping mapping = DmFactory.eINSTANCE.createIEnumMapping();
				mapping.setInstanceName(key.getInstanceName());
				mapping.setPropertyId(key.getPropertyId());
				mapping.setNameAlgorithm(key.getNameAlgorithm());
				if (entry.getValue() instanceof IAstEnumerator)
					mapping.setValue(((IAstEnumerator) entry.getValue()).getName().getName());
				else if (entry.getValue() != null)
					mapping.setValue(entry.getValue().toString());
				
				enumMappings.getMappings().add(mapping);
			}
		}
		
		state.setEnumMappings(enumMappings);
		
		IArrayMappings arrayMappings = DmFactory.eINSTANCE.createIArrayMappings();

		for (Iterator iter = arrayMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<ArrayKey, Map<String, String>> entry = (Map.Entry<ArrayKey, Map<String, String>>) iter.next();
			ArrayKey key = entry.getKey();
			Map<String, String> array = entry.getValue();
			
			if (!key.isExternal) {
				IArrayMapping arrayMapping = DmFactory.eINSTANCE.createIArrayMapping();
				arrayMapping.setInstanceName(key.getInstanceName());
				arrayMapping.setPropertyId(key.getPropertyId());
				arrayMapping.setMemberId(key.getMemberId());

				for (Iterator iterator = array.entrySet().iterator(); iterator
						.hasNext();) {
					Map.Entry<String, String> entry2 = (Map.Entry<String, String>) iterator.next();
					IElementMapping elementMapping = DmFactory.eINSTANCE.createIElementMapping();
					elementMapping.setUniqueValue(entry2.getKey());
					elementMapping.setValue(entry2.getValue());
					arrayMapping.getElements().add(elementMapping);
				}
				
				arrayMappings.getMappings().add(arrayMapping);
			}
		}

		state.setArrayMappings(arrayMappings);

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#loadState(com.nokia.sdt.datamodel.IDesignerDataModel, com.nokia.sdt.emf.dm.ISourceGenMappingState)
	 */
	public void loadState(IDesignerDataModel dataModel, ISourceGenMappingState state, ITranslationUnit tu) {
		IEnumMappings enumMappings = state.getEnumMappings();
		if (enumMappings == null)
			return;
		for (Iterator iter = enumMappings.getMappings().iterator(); iter.hasNext();) {
			IEnumMapping mapping = (IEnumMapping) iter.next();
			if (TextUtils.strlen(mapping.getInstanceName()) > 0
					&& TextUtils.strlen(mapping.getValue()) > 0) {
				IAstEnumerator enm = tu.findEnumerator(mapping.getValue());
				registerEnumMapping(mapping, enm);
			} else {
				Messages.emit(IStatus.ERROR,
						dataModel,
						"RssModelTypeHandler.IllegalEnumMapping",
						new Object[] { 
							mapping });
			}
		}
		
		IArrayMappings arrayMappings = state.getArrayMappings();
		for (Iterator iter = arrayMappings.getMappings().iterator(); iter.hasNext();) {
			IArrayMapping arrayMapping = (IArrayMapping) iter.next();
			if (TextUtils.strlen(arrayMapping.getInstanceName()) > 0
					//&& TextUtils.strlen(arrayMapping.getPropertyId()) > 0
					&& TextUtils.strlen(arrayMapping.getMemberId()) > 0) {
				Map<String, String> map = registerArrayMapping(arrayMapping);
				for (Iterator iterator = arrayMapping.getElements().iterator(); iterator.hasNext();) {
					IElementMapping element = (IElementMapping) iterator.next();
					if (TextUtils.strlen(element.getUniqueValue()) > 0
							&& TextUtils.strlen(element.getValue()) > 0) {
						registerArrayElementMapping(map, element);
					} else {
						Messages.emit(IStatus.ERROR,
								dataModel,
								"RssModelTypeHandler.IllegalElementMapping",
								new Object[] { element } );
					}
				}
			} else {
				Messages.emit(IStatus.ERROR,
						dataModel,
						"RssModelTypeHandler.IllegalArrayMapping",
						new Object[] { arrayMapping } );
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#registerEnumMapping(com.nokia.sdt.emf.dm.IEnumMapping, com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumerator)
	 */
	public void registerEnumMapping(IEnumMapping mapping, IAstEnumerator enm) {
		EnumKey key = new EnumKey(mapping.getInstanceName(), mapping.getPropertyId(), mapping.getNameAlgorithm());
		if (enm != null) {
			enumKeysToEnumeratorMap.put(key, enm);
		} else {
			// assume system enum
			enumKeysToEnumeratorMap.put(key, mapping.getValue());
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#registerArrayMapping(com.nokia.sdt.emf.dm.IArrayMapping)
	 */
	public Map<String, String> registerArrayMapping(IArrayMapping arrayMapping) {
		ArrayKey arrayKey = new ArrayKey(arrayMapping.getInstanceName(), 
				arrayMapping.getPropertyId(),
				arrayMapping.getMemberId());
		Map<String, String> map = arrayMap.get(arrayKey);
		if (map == null) {
			map = new HashMap<String, String>();
			arrayMap.put(arrayKey, map);
		}
		return map;
	}
	
	private void registerArrayElementMapping(Map<String, String> map, IElementMapping elementMapping) {
		map.put(elementMapping.getUniqueValue(), elementMapping.getValue());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#registerArrayElementMapping(com.nokia.sdt.emf.dm.IArrayMapping, com.nokia.sdt.emf.dm.IElementMapping)
	 */
	public void registerArrayElementMapping(IArrayMapping arrayMapping, IElementMapping elementMapping) {
		ArrayKey arrayKey = new ArrayKey(arrayMapping.getInstanceName(), 
				arrayMapping.getPropertyId(),
				arrayMapping.getMemberId());
		Map<String, String> map = arrayMap.get(arrayKey);
		Check.checkArg(map);
		registerArrayElementMapping(map, elementMapping);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelTypeHandler#registerArrayElement(com.nokia.sdt.datamodel.adapter.IComponentInstance, java.lang.String, java.lang.String, java.lang.String, com.nokia.sdt.datamodel.adapter.IComponentInstance)
	 */
	public void registerArrayElement(IComponentInstance arrayInstance, String arrayPropertyId, String arrayMemberId, String uniqueValue, String elementName) {
		ArrayKey key = getArrayKey(arrayInstance.getName(), arrayPropertyId, arrayMemberId, uniqueValue);
		Map<String, String> array = arrayMap.get(key);
		if (array == null) {
			array = new HashMap<String, String>();
			arrayMap.put(key, array);
		}
		array.put(uniqueValue, elementName);
	}
}
