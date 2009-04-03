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
package com.nokia.sdt.component.symbian.sourcegen;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.util.MessageLocators;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.contributions.IContribution;
import com.nokia.sdt.sourcegen.templates.backend.ITextChunkVisitor;
import com.nokia.sdt.sourcegen.templates.backend.TextChunkBackEnd;
import com.nokia.sdt.sourcegen.templates.core.IBackEnd;
import com.nokia.sdt.sourcegen.templates.core.TemplateCompiler;
import com.nokia.sdt.sourcegen.templates.frontend.FrontEnd;
import com.nokia.sdt.sourcegen.templates.frontend.IMessageLocationFactory;
import com.nokia.sdt.utils.MessageReporting;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.IVariableLookupCallback;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.core.VariableSubstitutionEngine;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This adapter reads the facets described by the &lt;srcgen&gt;
 * element of a component definition and generates Javascript
 * code which, when invoked, will generate the contributions
 * for the current component instance.
 * 
 * 
 *
 */
public class SourceGenXMLParser {

	// this can't be final, it's set from unit tests
    public static boolean DUMP = false;
    
    public final static String SEPARATOR = "$"; //$NON-NLS-1$
    public final static String RX_SEPARATOR = "\\$"; //$NON-NLS-1$
    
    /** Variable name exposed to script containing current contribution text */
    public final static String CONTRIB_VAR = "contrib"; //$NON-NLS-1$
    public final static String CONTRIBS_VAR = "contribs"; //$NON-NLS-1$
    public final static String CONTRIB_TEXT_VAR = "contribText"; //$NON-NLS-1$

    // contribs list
    private final static int LOCALS_CONTRIBS = 1;
    // contrib, contribText
    private final static int LOCALS_CONTRIB = 2;
    // formrx
    private final static int LOCALS_FORMRX = 4;

    /** Provide canonical text to provide useful locals to 
     * make script functions more concise.
     * They all accept 'instance' as a parameter.  
     */
    private static final String INSTANCE_VAR_EXTRACTION_TEXT = 
        "var children = instance.children;\n"+ //$NON-NLS-1$
        "var properties = instance.properties;\n"+ //$NON-NLS-1$
        "var instanceName = instance.name;\n"+ //$NON-NLS-1$
        "var instanceMemberName = instance.memberName;\n"; //$NON-NLS-1$

	protected static final Pattern MODIFIER_PATTERN = Pattern.compile("([^:]+)::(.*)"); //$NON-NLS-1$

	protected static final String MODIFIER_AS_FUNCTION_DECL_ARGS = "as-function-declaration-args"; //$NON-NLS-1$
	protected static final String MODIFIER_AS_FUNCTION_DEFN_ARGS = "as-function-definition-args"; //$NON-NLS-1$
	protected static final String MODIFIER_AS_FUNCTION_LOCATION_ARGS = "as-function-location-args"; //$NON-NLS-1$
	protected static final String MODIFIER_AS_FUNCTION_CALL_ARGS = "as-function-call-args"; //$NON-NLS-1$
	protected static final String MODIFIER_AS_STRING = "as-string"; //$NON-NLS-1$
	protected static final String MODIFIER_IS_DEFINED = "is-defined"; //$NON-NLS-1$
	protected static final String MODIFIER_APPEND_SPACE_UNLESS_EMPTY = "append-space-unless-empty"; //$NON-NLS-1$
	protected static final String MODIFIER_ADD_SPACES_UNLESS_EMPTY = "add-spaces-unless-empty"; //$NON-NLS-1$
	protected static final String MODIFIER_SPLIT_AND_INDENT = "split-and-indent"; //$NON-NLS-1$
	protected static final String MODIFIER_TO_UPPER = "to-upper"; //$NON-NLS-1$
	protected static final String MODIFIER_TO_LOWER= "to-lower"; //$NON-NLS-1$
	protected static final String MODIFIER_TO_TITLE = "to-title"; //$NON-NLS-1$

	private static final String INLINE_SCOPE_FUNCTION = "function"; //$NON-NLS-1$
	private static final String INLINE_SCOPE_PROTOTYPE = "prototype"; //$NON-NLS-1$
	private static final String INLINE_SCOPE_FILE = "file"; //$NON-NLS-1$




    private IComponent component;

    public class TemplateAttributes {
        /** The target for generated code */
        StringBuffer jsBuffer;
        /** The current location="..." */
        String location;
        /** The current mode="..." */
        String mode;
        /** The current phase="..." */
        String phase;
        /** The current form="..."/forms="..." list, separated by IContribution#FORM_SEPARATOR */
        String forms;
        /** The JS code for matching the form */
        String formMatcher;
        /** Current group ID */
        String group;
        public boolean forDefineLocation;
        /** The current ifEvent="..." */
		public List ifEvents;
		/** Current ifExpr="..." */
		public String ifExpr;
		public boolean definingMacro;
        
        public TemplateAttributes copy() {
            TemplateAttributes t = new TemplateAttributes();
            t.forDefineLocation = forDefineLocation;
            t.jsBuffer = jsBuffer;
            t.location = location;
            t.mode = mode;
            t.phase = phase;
            t.forms = forms;
            t.formMatcher = formMatcher;
            t.group = group;
            t.ifEvents = ifEvents;
            t.ifExpr = ifExpr;
            t.definingMacro = definingMacro;
            return t;
        }
    };
    
    /** Stack of template attributes */
    Stack templateAttributeStack;
    /** Current attributes */
    TemplateAttributes attrs;
    
    /** The buffer for generic JS code */
    StringBuffer js;
    /** Final script code */
    String jsCode;
    
    /** The name of the prototype */
    String prototype;
    
    /** First base component */
    IComponentSourceGenInfo base;
    
    /** Map of template form + id to ISourceGenTemplate
     * (id can be used multiple times with different forms
     * and groups, so its key is [FORM.][GROUP.]ID 
     */
    protected Map templateIdMap;
    
    /** Map of template group id to ISourceGenTemplateGroup
     * (a template group has no form of its own, so it is indexed merely by id)
     */
    protected Map templateGroupMap;

    /** Map of location id to ISourceGenLocation */
    private Map locationMap;
    
    /** Counter for auto-generated ids */ 
    private static int generatedId;
    
    private IComponent baseComponent;

    private SourceGenMacroSupport macroSupport;
    
    static class MacroInfo {
    	public MacroInfo(ExpandMacroType macro, ResolvedMacro resolvedMacro) {
    		this.macro = macro;
    		this.resolvedMacro = resolvedMacro;
    		this.arguments = new HashMap<String, String>();
		}
    	/** The macro declaration */
    	ResolvedMacro resolvedMacro;
    	/** The expand macro */
		ExpandMacroType macro;
		/** Map of variable name to argument value.  Every known variable
		 * is defined here (though possibly with a null value).
		 */
    	Map<String, String> arguments;
    }
	private Stack<MacroInfo> macroStack;

	protected MessageLocation componentLoc;

    public SourceGenXMLParser(IComponent component,
            SourceGenType sourceGen) throws ScriptException {
        Check.checkArg(component);
        Check.checkArg(sourceGen);
        
        this.component = component;
        this.componentLoc = MessageLocators.getWorkspaceRelativeLocation(component.createMessageLocation());

        this.baseComponent = component.getComponentBase();
        this.base = null;
        while (this.baseComponent != null) {
            IComponentSourceGen baseSg = (IComponentSourceGen) this.baseComponent.getAdapter(IComponentSourceGen.class);
            if (baseSg != null) {
                this.base = baseSg.getSourceGenInfo();
                break;
            }
            this.baseComponent = this.baseComponent.getComponentBase();
        }
        
        // make prototype a legal ID based on the component
        Pattern p = Pattern.compile("([^A-Za-z0-9_])"); //$NON-NLS-1$
        Matcher m = p.matcher(component.getId());
        prototype = m.replaceAll(RX_SEPARATOR); //$NON-NLS-1$

        compileSourceGen(sourceGen);
    }
    
    public String getJavascript() {
    	return jsCode;
    }
    
    public ComponentSourceGenInfo getSourceGenInfo() {
        return new ComponentSourceGenInfo(
        		base,
                prototype, 
                templateIdMap, 
                templateGroupMap,
                locationMap,
                macroSupport);
    }
    
    /**
     * Verify that the id is compatible with an identifier.
     * Note: we don't restrict the initial character since
     * we expect this to be a <i>suffix</i> of a Javascript
     * identifier.
     * @param id the identifier to check
     * @return the same id or a munged legal version
     */
    private String verifyOrFixId(String id) {
        Pattern p = Pattern.compile("[A-Za-z0-9_]+"); //$NON-NLS-1$
        Matcher m = p.matcher(id);
        if (!m.matches()) {
            //emit(IStatus.ERROR, "SourceGenAdapterScript.IllegalId", new Object[] { id, component.getId() }); //$NON-NLS-1$
            p = Pattern.compile("[^A-Za-z0-9_]"); //$NON-NLS-1$
            m = p.matcher(id);
            id = m.replaceAll("\\$"); //$NON-NLS-1$
        }
        return id;
    }
    
    private String verifyOrFixOrCreateId(String id) {
    	if (id == null)
    		return "generated$" + generatedId++; //$NON-NLS-1$
    	return verifyOrFixId(id);
    }
    
    private void compileSourceGen(SourceGenType sourceGen) throws ScriptException {
        //System.out.println("sourceGen="+component.getId()); //$NON-NLS-1$

        js = new StringBuffer();
        
        // header
        js.append("// Generated code from "); //$NON-NLS-1$
        js.append(component.getId());
        js.append("\n\n"); //$NON-NLS-1$

        // setup prototype
        js.append("function "); //$NON-NLS-1$
        js.append(prototype);
        js.append("() {\n"); //$NON-NLS-1$
        if (base != null && base.getPrototype() != null) {
            js.append("\t"); //$NON-NLS-1$
            js.append(base.getPrototype());
            js.append(".apply(this);\n"); //$NON-NLS-1$
        }
        js.append("}\n\n"); //$NON-NLS-1$
        if (base != null && base.getPrototype() != null) {
            js.append(prototype);
            js.append(".prototype = new "); //$NON-NLS-1$
            js.append(base.getPrototype());
            js.append("();\n"); //$NON-NLS-1$
        } else {
            js.append(prototype);
            js.append(".prototype = new Object();\n"); //$NON-NLS-1$
        }
        js.append(prototype);
        js.append(".constructor = "); //$NON-NLS-1$
        js.append(prototype);
        js.append(";\n\n"); //$NON-NLS-1$
        
        // use LinkedHashMap to keep the items in order
        templateIdMap = new LinkedHashMap();
        // use LinkedHashMap to keep the items in order
        templateGroupMap = new LinkedHashMap();
        
        // copy existing locations from base
        locationMap = base != null ? new LinkedHashMap(base.getLocationIdToLocationMap())
                : new LinkedHashMap();

        // ditto for macros
        Map<String, ResolvedMacro> baseNameToMacroMap = null;
        if (base != null)
        	baseNameToMacroMap = base.getNameToMacroMap();
        macroSupport = new SourceGenMacroSupport(baseNameToMacroMap);
        	
        	
        
        macroStack = new Stack<MacroInfo>();
        templateAttributeStack = new Stack();

        // compile all the scripts and templates
        compileGeneratedCode(sourceGen);
        Check.checkState(templateAttributeStack.empty());

        // define the locations for this component
        templateAttributeStack.push(attrs = new TemplateAttributes());
        compileDefineLocations(sourceGen.eContents());
        templateAttributeStack.pop();

        // get the final code
        jsCode = js.toString();
        js = null;

        // template-replace the references to our variables
        // ${jsObject} --> component.getId()
        // ${jsSuper} --> component.getComponentBase().getId()

        Pattern pattern = Pattern.compile("\\$\\{jsObject\\}"); //$NON-NLS-1$
        Matcher matcher = pattern.matcher(jsCode);
        jsCode = matcher.replaceAll(TextUtils.regexEscape(prototype));
        
        pattern = Pattern.compile("\\$\\{jsSuper\\}"); //$NON-NLS-1$
        matcher = pattern.matcher(jsCode);
        if (matcher.find()) {
            if (baseComponent == null) {
                emit(IStatus.ERROR, "SourceGenAdapterScript.JsSuperWithoutBase", //$NON-NLS-1$
                        component.getId());
            } else {
                jsCode = matcher.replaceAll(TextUtils.regexEscape(base.getPrototype()));
            }
        }
        
        jsCode = TextUtils.canonicalizeNewlines(jsCode);
        
        // dump code to file 
        if (sourceGen.isDebug() || SourceGenAdapterFactory.isDebuggableComponent(component.getId())) {
            try {
                IPath debugFilePath = SourceGenAdapterFactory.getDebugJavaScriptLocation(component);
                if (debugFilePath != null) {
                    File outFile = new File(debugFilePath.toOSString());
                    PrintStream ps = new PrintStream(new FileOutputStream(outFile));
                    ps.print(jsCode);
                    ps.close();
                }
            } catch (IOException e) {
                System.out.println("Failed to write file ("+e+"\n"+ //$NON-NLS-1$ //$NON-NLS-2$
                        "Dump of code:\n" + jsCode); //$NON-NLS-1$
            }
        }
        
        if (DUMP) {
            System.out.println("dump of code:\n" + jsCode); //$NON-NLS-1$
        }
    }
    
    /** 
     * Get some path for a message, either the generated Javascript, if applicable,
     * or the component's definition file.
     * @param component
     * @return path to generated *.js or the *.component
     */
    public static IPath getNearestComponentSourceGenLocation(SourceGenType sourceGen, IComponent component) {
        if (sourceGen.isDebug() || SourceGenAdapterFactory.isDebuggableComponent(component.getId()))
            return SourceGenAdapterFactory.getDebugJavaScriptLocation(component);
        return component.createMessageLocation().getLocation();
    }

    
    private StringBuffer getBuffer() {
        //return ((TemplateAttributes) templateAttributeStack.peek()).jsBuffer;
    	return attrs.jsBuffer;
    }

    private void enterConditionalSourceGen(StringBuffer target) {
        // only invoke code for particular forms
        //target.append("println(form);\n");
        if (attrs.formMatcher != null) {
            target.append("\t"); //$NON-NLS-1$
            target.append(attrs.formMatcher);
            target.append(" {\n"); //$NON-NLS-1$
        }

        // only invoke code if event bound
        if (attrs.ifEvents != null) {
        	target.append("var event;\nif ("); //$NON-NLS-1$
        	boolean first = true;
        	for (Iterator iter = attrs.ifEvents.iterator(); iter.hasNext();) {
				String ifEvent = (String) iter.next();
				if (!first)
					target.append(" || "); //$NON-NLS-1$
				else
					first = false;
            	target.append("((event = instance.events['"); //$NON-NLS-1$
            	target.append(ifEvent);
            	target.append("']) != null)"); //$NON-NLS-1$
				
			}
        	if (first)
        		target.append("false"); //$NON-NLS-1$
        	target.append(") {\n"); //$NON-NLS-1$
        }
        
        // only invoke code if expr matches
        if (attrs.ifExpr != null) {
        	target.append("if ("); //$NON-NLS-1$
        	target.append(attrs.ifExpr);
        	target.append(") {\n"); //$NON-NLS-1$
        }
	}

    private void exitConditionalSourceGen(StringBuffer target) {
        // only invoke code if expr matches
        if (attrs.ifExpr != null) {
        	target.append("\t} // ifExpr\n"); //$NON-NLS-1$
        }

        // only invoke code if event bound
        if (attrs.ifEvents != null) {
        	target.append("\t} //event\n"); //$NON-NLS-1$
        }

        // only invoke code for particular forms
        if (attrs.formMatcher != null) {
            target.append("\t} //form\n"); //$NON-NLS-1$
        }
	}

    private void compileDefineLocations(EList group) {
       
        // make a function for each location
        for (Iterator iter = group.iterator(); iter.hasNext();) {
            EObject obj = (EObject) iter.next();
            if (obj instanceof DefineLocationType) {
                compileDefineLocation((DefineLocationType) obj);
            } else if (obj instanceof TemplateGroupType) {
            	// groups can define locations too
            	templateAttributeStack.push(attrs.copy());
            	applyAttributes(null, null, null, null, ((TemplateGroupType)obj).getIfEvents(), null);
            	EList kids = ((TemplateGroupType) obj).eContents();
                for (Iterator subiter = kids.iterator(); subiter.hasNext();) {
                    EObject subobj = (EObject) subiter.next();
                    if (subobj instanceof DefineLocationType)
                    	compileDefineLocation((DefineLocationType) subobj);
                }
            	attrs = (TemplateAttributes) templateAttributeStack.pop();
            }
        }
        
        StringBuffer buffer = new StringBuffer();
        
        // make a lookup table
        buffer.append("generateLocationTable = {\n"); //$NON-NLS-1$
        for (Iterator iter = locationMap.values().iterator(); iter.hasNext();) {
            ISourceGenLocation loc = (ISourceGenLocation) iter.next();
            buffer.append(MessageFormat.format("\t''{0}'' : {1}.prototype.{2},\n", //$NON-NLS-1$
            		new Object[] { loc.getId(), prototype, loc.getFunction() }));
        }
        buffer.append("};\n"); //$NON-NLS-1$
        
        buffer.append(prototype);
        buffer.append(".prototype.generateLocation = function(instance, refInstance, form, id) {\n"); //$NON-NLS-1$
        setupLocals(buffer, LOCALS_CONTRIBS);

        buffer.append("\tgenerateLocationTable[id].apply(this, [instance, refInstance, form, " + CONTRIBS_VAR + "]);\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append("\treturn " + CONTRIBS_VAR + ";\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append("}\n\n"); //$NON-NLS-1$
        
        js.append(buffer);
    }

    private void compileGeneratedCode(EObject group) {
    	templateAttributeStack.push(attrs = new TemplateAttributes());
        attrs.jsBuffer = new StringBuffer();

        getBuffer().append(prototype);
        getBuffer().append(".prototype.generate = function(instance, refInstance, form) {\n"); //$NON-NLS-1$
        setupLocals(getBuffer(), LOCALS_CONTRIBS + LOCALS_CONTRIB + LOCALS_FORMRX);
        
        compileSourcegen(group);

        getBuffer().append("\treturn " + CONTRIBS_VAR +";\n"); //$NON-NLS-1$ //$NON-NLS-2$
        getBuffer().append("}\n\n"); //$NON-NLS-1$
        js.append(getBuffer());
        attrs = (TemplateAttributes) templateAttributeStack.pop();
    }
    
    private void compileSourcegen(EObject group) {
        
        for (Iterator iter = group.eContents().iterator(); iter.hasNext();) {
            EObject eObj = (EObject) iter.next();
            if (eObj instanceof TemplateType) {
                compileTemplate((TemplateType) eObj);
            } else if (eObj instanceof TemplateGroupType) {
                compileTemplateGroup((TemplateGroupType) eObj);
            } else if (eObj instanceof UseTemplateType) {
                compileUseTemplate((UseTemplateType) eObj);
            } else if (eObj instanceof UseTemplateGroupType) {
                compileUseTemplateGroup((UseTemplateGroupType) eObj);
            } else if (eObj instanceof InlineType) {
                compileInline((InlineType) eObj);
            } else if (eObj instanceof ScriptType) {
                compileScript((ScriptType) eObj);
            } else if (eObj instanceof DefineLocationType) {
            	DefineLocationType defloc = (DefineLocationType) eObj;
            	compileRealizeLocation(defloc);
            	//compileDefineLocation((DefineLocationType) eObj);
            	// handled elsewhere
            } else if (eObj instanceof DefineMacroType) {
            	DefineMacroType dmt = (DefineMacroType) eObj;
            	ResolvedMacro existing = macroSupport.findMacro(dmt.getId());
        		if (existing != null && existing.component.equals(component)) {
           			macroSupport.defineMacroError(component.createMessageLocation(), dmt, 
           					"SourceGenXMLParser.RedefiningMacroError", //$NON-NLS-1$
        					new Object[] { dmt.getId() });
        		} else {
        			macroSupport.defineMacro(component, component.createMessageLocation(), (DefineMacroType) eObj);
        		}
            } else if (eObj instanceof ExpandMacroType) {
                compileExpandMacro((ExpandMacroType) eObj);
            } else {
                Check.checkState(false);
            }
        }
        
    }
    
    private boolean getBoolean(DefineLocationType defloc, String attr, String value) {
    	if (value == null || value.equals("false")) //$NON-NLS-1$
    		return false;
    	if (value.equals("true")) //$NON-NLS-1$
    		return true;
    	emitLit(IMessage.ERROR, "SourceGenXMLParser.InvalidBoolean",  //$NON-NLS-1$
    			Messages.getString("SourceGenXMLParser.InvalidBoolean"), //$NON-NLS-1$
    			new Object[] { value, attr, defloc.getId() });
    	return false;
    }

	private ISourceGenTemplate compileRealizeLocation(DefineLocationType defloc) {
    	// if asked for, realize it now
    	if (getBoolean(defloc, "realize", defloc.getRealize())) { //$NON-NLS-1$
    		TemplateType template = ComponentFactory.eINSTANCE.createTemplateType();
    		template.setId(defloc.getId() + "$realize"); //$NON-NLS-1$
    		template.setLocation(defloc.getId());
    		template.setIfEvents(defloc.getIfEvents());
    		template.setValue(""); //$NON-NLS-1$
    		return compileTemplate(template);
    	}
    	return null;
	}

	private void compileDefineLocation(final DefineLocationType defloc) {
       if (defloc.getLocation() == null) {
            // dunno why this gets through: the xsd doesn't allow this
            defloc.setLocation(""); //$NON-NLS-1$
        }
 
     	if (defloc.getId() == null) {
            emit(Message.ERROR, "SourceGenXMLParser.NeedLocationId",  //$NON-NLS-1$
                    new Object[] { defloc.getLocation(), component.getId() });
            return;
    	}
        if (defloc.getBaseLocation() != null) {
            // if set, the domain, directory, and file attributes cannot be set
            if (defloc.getDomain() != null) {
                emit(Message.ERROR, "SourceGenXMLParser.CannotSpecifyWithBase",  //$NON-NLS-1$
                        new Object[] { "domain", component.getId() }); //$NON-NLS-1$
                return;
            }
            if (defloc.getDir() != null) {
                emit(Message.ERROR, "SourceGenXMLParser.CannotSpecifyWithBase",  //$NON-NLS-1$
                        new Object[] { "dir", component.getId() }); //$NON-NLS-1$
                return;
            }
            if (defloc.getFile() != null) {
                emit(Message.ERROR, "SourceGenXMLParser.CannotSpecifyWithBase",  //$NON-NLS-1$
                        new Object[] { "file", component.getId() }); //$NON-NLS-1$
                return;
            }
        } else {
            // if not set, the domain, directory, and file attributes must be set
            if (defloc.getDomain() == null) {
                emit(Message.ERROR, "SourceGenXMLParser.MustSpecifyWithoutBase",  //$NON-NLS-1$
                        new Object[] { "domain", component.getId() }); //$NON-NLS-1$
                return;
            }
            if (defloc.getDir() == null) {
                emit(Message.ERROR, "SourceGenXMLParser.MustSpecifyWithoutBase",  //$NON-NLS-1$
                        new Object[] { "dir", component.getId() }); //$NON-NLS-1$
                return;
            }
            if (defloc.getFile() == null) {
                emit(Message.ERROR, "SourceGenXMLParser.MustSpecifyWithoutBase",  //$NON-NLS-1$
                        new Object[] { "file", component.getId() }); //$NON-NLS-1$
                return;
            }
        }

        // fix up any errant ids
        defloc.setId(verifyOrFixId(defloc.getId()));
        if (defloc.getBaseLocation() != null)
            defloc.setBaseLocation(verifyOrFixId(defloc.getBaseLocation()));

        final String funcName = "defineLocation" + SEPARATOR + defloc.getId(); //$NON-NLS-1$

        // call the routine
        //getBuffer().append("\tthis.");
        //getBuffer().append(funcName);
        //getBuffer().append("(instance, refInstance, form, " + CONTRIBS_VAR + ");\n");

        // now define the routine
        templateAttributeStack.push(attrs.copy());
        try {
	        applyAttributes(null, null, null, null, defloc.getIfEvents(), null);
	
	        attrs.location = defloc.getId();
	        attrs.forDefineLocation = true;
	        attrs.jsBuffer = new StringBuffer();

        	// forms are not supported (see ComponentSourceGenAdapterScript#generateLocation(IContributionContext, String))
        	attrs.formMatcher = null;
        	attrs.forms = null;

	        // define the defineLocation$xxx variant
	        getBuffer().append(prototype);
	        getBuffer().append(".prototype."//$NON-NLS-1$
	                + funcName + " = function(instance, refInstance, form, " + CONTRIBS_VAR + ") {\n"); //$NON-NLS-1$ //$NON-NLS-2$
	        setupLocals(getBuffer(), LOCALS_CONTRIB + LOCALS_FORMRX);
	        
	        // compile the embedded templates/inline/scripts
	        compileSourcegen(defloc);

	        getBuffer().append("}\n\n"); //$NON-NLS-1$
	        js.append(getBuffer());
	        
	        final String filter = defloc.getFilter() != null 
        	&& defloc.getFilter().length() > 0
        	&& !defloc.getFilter().equals("default")  //$NON-NLS-1$
        		? defloc.getFilter() : null;
        		
    		final List ifEvents = attrs.ifEvents;
        
    		final boolean isOwned = getBoolean(defloc, "owned", defloc.getOwned()); //$NON-NLS-1$
    		final boolean isEventHandler = getBoolean(defloc, "isEventHandler", defloc.getIsEventHandler()); //$NON-NLS-1$
			final boolean isVariable = ifEvents != null 
				|| defloc.getLocation().indexOf('$') >= 0;
				
	        ISourceGenLocation loc = new ISourceGenLocation() {
	
	            public String getComponentId() {
	                return component.getId();
	            }
	            
	            public MessageLocation createMessageLocation() {
	            	return component.createMessageLocation();
	            }
	            
	            public String getBaseLocation() {
	                return defloc.getBaseLocation();
	            }
	
	            public String getDomain() {
	                return defloc.getDomain();
	            }
	
	            public String getDirectory() {
	                return defloc.getDir();
	            }
	
	            public String getFile() {
	                return defloc.getFile();
	            }
	
	            public String getId() {
	                return defloc.getId();
	            }
	
	            public String getLocation() {
	                return defloc.getLocation();
	            }
	
	            public boolean isOwned() { 
	                return isOwned;
	            }
	        
	            public String getFunction() {
	                return funcName;
	            }
	
	            public String getFilter() {
	                return filter;
	            }
	
				public List getIfEvents() {
					return ifEvents;
				}

				public boolean isEventHandler() {
					return isEventHandler;
				}

				public boolean isVariable() {
					return isVariable;
				}
	        };
	        
	        registerLocation(loc);
	        
        } finally {
        	attrs = (TemplateAttributes) templateAttributeStack.pop();
        }

    }

    private void registerLocation(ISourceGenLocation loc) {
        // we allow redefinitions of inherited locations
        String key = loc.getId();
        ISourceGenLocation old = (ISourceGenLocation) locationMap.get(key);
        if (old != null) {
            if (old.getComponentId().equals(component.getId())) {
                emit(Message.ERROR, "SourceGenXMLParser.ReusedLocationId", //$NON-NLS-1$ 
                    new String[] { loc.getId(), component.getId() });
                return;
            }
        }
        locationMap.put(key, loc);        
    }

    private String getFormName(List forms) {
    	StringBuilder formBuilder = new StringBuilder();
    	if (forms != null) {
	    	for (ListIterator iter = forms.listIterator(); iter.hasNext();) {
				String form = (String) iter.next();
				if (formBuilder.length() > 0)
					formBuilder.append(IContribution.FORM_SEPARATOR);
				formBuilder.append(form);
			}
    	}
    	return formBuilder.toString();
    }
    
    private ISourceGenTemplate compileTemplate(final TemplateType type) {
        //System.out.println("template="+type); //$NON-NLS-1$
        StringBuffer target;
        ISourceGenTemplate sgt = null;
        
        templateAttributeStack.push(attrs.copy());
        try {
            applyAttributes(type.getLocation(), type.getPhase(), type.getMode(), getForms(type.getForm(), type.getForms()), type.getIfEvents(), type.getIfExpr());
        
            addComment(type);
            
            boolean inFunction = false;
            
            boolean isNamed = type.getId() != null && type.getId().length() > 0;
            if (!isNamed && attrs.definingMacro) {
            	isNamed = true;
            }
            if (isNamed) {
                type.setId(verifyOrFixOrCreateId(type.getId()));
                
                if (templateRegistered(type.getId(), attrs.group, attrs.forms)) {
                    emit(IStatus.ERROR, "SourceGenAdapterScript.TemplateIdInUse",  //$NON-NLS-1$
                            new String[] { type.getId(), component.getId() }); //$NON-NLS-1$
                    return null;
                }
                
                // if a template has an id, it is placed in its
                // own function, for later calling by derived components
                final String function = getTemplateFunction(type.getId(), attrs.group, attrs.forms);
                final String form = attrs.forms == null ? "" : attrs.forms; //$NON-NLS-1$
                final String id = type.getId();
                final String location = attrs.location == null ? null : new String(attrs.location);
                final String group = attrs.group == null ? null : new String(attrs.group);
                final List ifEvents = attrs.ifEvents;
                
                // remember we defined the template
                sgt = new ISourceGenTemplate() {

                    public String toString() {
                        return "<Template '" + id + "' @" + location + ">"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    }
                    
                    public String getId() {
                        return id;
                    }

                    public String getForms() {
                        return form;
                    }
                    
                    public String getFunction() {
                        return function;
                    }

                    public String getGroup() {
                        return group;
                    }
                    
					public List getIfEvents() {
						return ifEvents;
					}
                
                };
                
                registerTemplate(sgt);
                
                // call it from the generate() function
                getBuffer().append("\tthis."); //$NON-NLS-1$
                getBuffer().append(function);
                getBuffer().append("(instance, refInstance, form, " + CONTRIBS_VAR + ");\n"); //$NON-NLS-1$ //$NON-NLS-2$
                
                target = js;
                target.append("\n"); //$NON-NLS-1$
                target.append(prototype);
                target.append(".prototype."); //$NON-NLS-1$
                target.append(function);
                target.append(" = function(instance, refInstance, form, " + CONTRIBS_VAR + ") {\n"); //$NON-NLS-1$ //$NON-NLS-2$
                setupLocals(target, LOCALS_CONTRIB + LOCALS_FORMRX);
                
                inFunction = true;
            } else {
                // else, the code goes directly in the generate() function
                target = getBuffer();
            }

            // test various conditions
            enterConditionalSourceGen(target);
            
            // expand the template code into real code
            
            String newContrib;
            // 1) implicit contribution is generated
            if (attrs.location != null) {
                newContrib = "\t" + CONTRIB_VAR + " = Engine.createContributionForLocation(\"" //$NON-NLS-1$ //$NON-NLS-2$
                				+ attrs.location
                				+ "\");\n"; //$NON-NLS-1$
            } else if (attrs.phase != null) {
                newContrib = "\t" + CONTRIB_VAR + " = Engine.createContributionForPhase(\"" //$NON-NLS-1$ //$NON-NLS-2$
                			+ attrs.phase
                			+ "\");\n"; //$NON-NLS-1$
            } else {
                newContrib = "\t" + CONTRIB_VAR + " = Engine.createContribution();\n"; //$NON-NLS-1$ //$NON-NLS-2$
            }
            if (attrs.forDefineLocation)
                newContrib += "\t" + CONTRIB_VAR + ".setDefinesLocation(true);\n"; //$NON-NLS-1$ //$NON-NLS-2$
            if (attrs.forms != null) {
                newContrib += "\t" + CONTRIB_VAR +".setForm(\"" //$NON-NLS-1$ //$NON-NLS-2$
                			+ attrs.forms
            				+ "\");\n"; //$NON-NLS-1$
            }
            if (attrs.mode != null) {
            	newContrib += "\t" + CONTRIB_VAR + ".setMode(" //$NON-NLS-1$ //$NON-NLS-2$
        					+ TextUtils.quote(attrs.mode, '"')
        					+ ");\n"; //$NON-NLS-1$
            }

            // 2) add user code
            //target.append("\tvar " + CONTRIB_TEXT_VAR + ";\n");
            String templateName = (type.getId() != null ? type.getId() : "<unnamed>") + //$NON-NLS-1$
            "(" + component.getId() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
            String expanded = expandTemplate(templateName, type.getValue(), CONTRIB_VAR, CONTRIBS_VAR, newContrib);
            target.append(expanded);
            
            // exit conditions
            exitConditionalSourceGen(target);
            
            if (inFunction) {
                // close function
                target.append("}\n"); //$NON-NLS-1$
            }
        } finally {
            attrs = (TemplateAttributes) templateAttributeStack.pop();
        }
        return sgt;
    }

    private ISourceGenTemplate compileInline(InlineType type) {
	    addComment(type);
	    
	    if (type.getScope().equals(INLINE_SCOPE_FUNCTION)) {

	    	boolean isNamed = false;
	    	//if (type.getId() != null && type.getId().length() > 0)
	    	//	isFunction = true;
	    	isNamed = type.getId() != null && type.getId().length() > 0;
	    	if (attrs.definingMacro)
	    		isNamed = true;
	    	
    		if (isNamed) {
    			// treat as template

		    	TemplateType template = ComponentFactory.eINSTANCE.createTemplateType();
		    	template.setForms(type.getForms());
		    	template.setId(type.getId());
		    	template.setIfEvents(type.getIfEvents());
		    	template.setIfExpr(type.getIfExpr());
		    	template.setValue("<%" + TextUtils.escapeStrings(type.getValue(), '"') + "%>");//$NON-NLS-1$ //$NON-NLS-2$
		    	return compileTemplate(template);
    		}

    		// else expand inline 
    		enterConditionalSourceGen(getBuffer());
	        getBuffer().append(type.getValue());
	        exitConditionalSourceGen(getBuffer());
	        return null;
	        
	    } else if (type.getScope().equals(INLINE_SCOPE_PROTOTYPE) || type.getScope().equals(INLINE_SCOPE_FILE)) {
	    	
	    	// no conditionals here
	    	if (type.getIfEvents() != null)
		        emit(IStatus.ERROR, "SourceGenXMLParser.NonFunctionInlineWithCondition", //$NON-NLS-1$ 
		                new String[] { "ifEvents", component.getId() }); //$NON-NLS-1$
	    	if (type.getIfExpr() != null)
		        emit(IStatus.ERROR, "SourceGenXMLParser.NonFunctionInlineWithCondition", //$NON-NLS-1$ 
		                new String[] { "ifExpr", component.getId() }); //$NON-NLS-1$
	    	if (type.getForms() != null)
		        emit(IStatus.ERROR, "SourceGenXMLParser.NonFunctionInlineWithCondition", //$NON-NLS-1$ 
		                new String[] { "forms", component.getId() }); //$NON-NLS-1$
	    		
	        js.append(type.getValue());
	        
	        // file/prototype level code is automatically inherited
	        // via the component
	        return null;
	    } else {
	        emit(IStatus.ERROR, "SourceGenAdapterScript.InlineWithUnknownScope", //$NON-NLS-1$ 
	                new String[] { type.getScope(), component.getId() });
	        return null;
	    }
	    
	}

	private List getForms(String form, List forms) {
    	if (forms == null || forms.size() == 0) {
    		if (form == null)
    			return null;
    		forms = new ArrayList(1);
    		forms.add(form);
    		return forms;
    	}
    	forms = new ArrayList(forms);
    	if (form != null)
    		forms.add(0, form);
    	Collections.sort(forms);
		return forms;
	}

	private void setupLocals(StringBuffer target, int flags) {
    	target.append(INSTANCE_VAR_EXTRACTION_TEXT);
    	if ((flags & LOCALS_CONTRIBS) != 0)
    		target.append("\tvar " + CONTRIBS_VAR + " = new java.util.LinkedList();\n"); //$NON-NLS-1$ //$NON-NLS-2$
    	if ((flags & LOCALS_CONTRIB) != 0) {
	        target.append("\tvar " + CONTRIB_TEXT_VAR + ";\n"); //$NON-NLS-1$ //$NON-NLS-2$
	        target.append("\tvar " + CONTRIB_VAR + ";\n"); //$NON-NLS-1$ //$NON-NLS-2$
    	}
    	if ((flags & LOCALS_FORMRX) != 0) {
	        target.append("\tvar formrx = new RegExp(\"^(\"+form+\")$\");\n"); //$NON-NLS-1$
    	}
	}

	private String expandTemplate(String name, String code, String contrib_var, String contribs_var, String newContrib) {
        FrontEnd fe = new FrontEnd(true);
        fe.setSource(code);
        fe.setMessageLocationFactory(new IMessageLocationFactory() {
			public MessageLocation createLocation(int line, int column) {
                MessageLocation loc = componentLoc;
                if (line != 0) {
                    loc = new MessageLocation(loc.getLocation(), line, column);
                }
                return loc;
            }
        });
        ITextChunkVisitor visitor = new TemplateJavaScriptFormatter(
        		CONTRIB_TEXT_VAR, contrib_var, contribs_var,
        		newContrib);
        IBackEnd be = new TextChunkBackEnd(visitor);
        TemplateCompiler compiler = new TemplateCompiler(fe, be);
        compiler.compile();
        return visitor.toString();
    }

    private boolean templateRegistered(String id, String group, String forms) {
        String key = templateKey(id, group, forms);
        return templateIdMap.containsKey(key);
    }

    private String templateKey(String id, String group, String forms) {
        return (group != null ? group + "." : "") //$NON-NLS-1$ //$NON-NLS-2$
        + id
        + (forms != null && !forms.equals("") ? "#" + forms : ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        
    }
    
    private boolean templateRegistered(ISourceGenTemplate sgt) {
        String key = templateKey(sgt.getId(), sgt.getGroup(), sgt.getForms());
        return templateIdMap.containsKey(key);
    }

    private void registerTemplate(ISourceGenTemplate sgt) {
        String key = templateKey(sgt.getId(), sgt.getGroup(), sgt.getForms());
        templateIdMap.put(key, sgt);
    }

    private String templateGroupKey(String id, String forms) {
        return id + (forms != null && !forms.equals("") ? "#" + forms : ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        
    }
    
    private boolean templateGroupRegistered(ISourceGenTemplateGroup sgtg) {
        String key = templateGroupKey(sgtg.getId(), sgtg.getForms());
        return templateGroupMap.containsKey(key);
    }

    private boolean templateGroupRegistered(String id, String form) {
        String key = templateGroupKey(id, form);
        return templateGroupMap.containsKey(key);
    }

    private void registerTemplateGroup(ISourceGenTemplateGroup sgtg) {
        String key = templateGroupKey(sgtg.getId(), sgtg.getForms());
        templateGroupMap.put(key, sgtg);
    }


    private void compileTemplateGroup(final TemplateGroupType type) {
        //System.out.println("template-group="+type); //$NON-NLS-1$

        addComment(type);

        List forms = getForms(type.getForm(), type.getForms());
        String formsName = getFormName(forms);

        List elements = null;
        if (type.getId() != null) {
            if (templateGroupRegistered(type.getId(), formsName)) {
                if (forms != null)
                    emit(IStatus.ERROR, "SourceGenAdapterScript.TemplateGroupIdFormInUse", //$NON-NLS-1$
                            new Object[] { type.getId(), formsName, component.getId() }); 
                else
                    emit(IStatus.ERROR, "SourceGenAdapterScript.TemplateGroupIdInUse", //$NON-NLS-1$ 
                            new Object[] { type.getId(), component.getId() });
                return;
            }
            final String id = type.getId();
            final String forms_ = formsName;
            final List<ISourceGenTemplate> fElements = new ArrayList();
            elements = fElements;
            
            // remember we defined the group
            ISourceGenTemplateGroup sgtg = new ISourceGenTemplateGroup() {

                public String getId() {
                    return id;
                }
 
                public String getForms() {
                    return forms_;
                }
                
                public List getTemplates() {
                    return fElements;
                }
            };
            
            registerTemplateGroup(sgtg);
        }
        

        for (Iterator iter = type.eContents().iterator(); iter.hasNext();) {
            try {
                templateAttributeStack.push(attrs.copy());
                applyAttributes(type.getLocation(), type.getPhase(), type.getMode(), forms, type.getIfEvents(), type.getIfExpr());
                attrs.group = type.getId();
                
                EObject object = (EObject) iter.next();
                if (object instanceof TemplateType) {
                	ISourceGenTemplate t = compileTemplate((TemplateType) object);
                    if (elements != null && t != null) {
                        // only record templates with ids
                        elements.add(t);
                    }
                } else if (object instanceof DefineLocationType) {
                	ISourceGenTemplate t = compileRealizeLocation((DefineLocationType) object);
                	if (elements != null && t != null) {
                        // only record templates with ids
                        elements.add(t);
                    }
                } else if (object instanceof InlineType) {
                	ISourceGenTemplate t = compileInline((InlineType) object);
                    if (elements != null && t != null) {
                        // only record templates with ids
                        elements.add(t);
                    }
                } else if (object instanceof ExpandMacroType) {
                	List<ISourceGenTemplate> subElements =
                		compileExpandMacro((ExpandMacroType) object);
                	if (elements != null && subElements != null) {
                        // only record expand macro with ids
                		elements.addAll(subElements);
                	}
                } else {
                    Check.checkState(false);
                }
            } finally {
                attrs = (TemplateAttributes) templateAttributeStack.pop();
            }
        }
    
    }

    private void compileUseTemplate(UseTemplateType type) {
        //System.out.println("use-template="+type); //$NON-NLS-1$
        
        if (base == null || base.getPrototype() == null) {
            emit(IStatus.ERROR, "SourceGenAdapterScript.UseTemplateWithoutBase", component.getId()); //$NON-NLS-1$
            return;
        }
        
        addComment(type);

        if (type.getIds().equals("*")) { //$NON-NLS-1$
            // use all the named templates from bases
        	for (IComponentSourceGenInfo info = base; info != null; info = info.getBaseInfo()) {
        		boolean found = false;
	            for (Iterator iter = info.getTemplateIdToSourceGenTemplateMap().values().iterator(); iter.hasNext();) {
	                ISourceGenTemplate sgt = (ISourceGenTemplate) iter.next();
	                if (groupMatches(sgt.getGroup(), attrs.group)) {
	                    callSuper(sgt);
	                    found = true;
	                }
	            }
	            if (found)
	            	break;
        	}
        } else {
            String[] ids = type.getIds().split(",| "); //$NON-NLS-1$
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                id = verifyOrFixId(id);
                
                // need to search through each element to find every instance
                // of an id used with a given form
                boolean found = false;
            	for (IComponentSourceGenInfo info = base; info != null; info = info.getBaseInfo()) {
	                for (Iterator iter = info.getTemplateIdToSourceGenTemplateMap().values().iterator(); iter.hasNext();) {
	                    ISourceGenTemplate sgt = (ISourceGenTemplate) iter.next();
	                    if (groupMatches(sgt.getGroup(), attrs.group)
	                            && sgt.getId().equals(id)) {
	                        callSuper(sgt);
	                        found = true;
	                    }
	                }
	                if (found)
	                	break;
            	}
                if (!found) {
                    emit(IStatus.ERROR, "SourceGenAdapterScript.UseTemplateBadId", new Object[] { id, baseComponent.getId(), component.getId() }); //$NON-NLS-1$
                }
            }
        }
    }

    private boolean groupMatches(String group, String group2) {
        return (group == null && group2 == null)
        || (group != null && group2 != null && group.equals(group2));
    }

    /**
     * Call the template from the superclass.
     * @param sgt the template
     */
    private void callSuper(ISourceGenTemplate sgt) {
        if (templateRegistered(sgt)) {
            emit(IStatus.ERROR, "SourceGenAdapterScript.UseTemplateReuse", //$NON-NLS-1$ 
                    new String[] { sgt.getId(), component.getId() } );
        } else {
            registerTemplate(sgt);
            getBuffer().append("this."); //$NON-NLS-1$
            getBuffer().append(sgt.getFunction());
            getBuffer().append("(instance, refInstance, form, " + CONTRIBS_VAR + ");\n"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    private void useTemplateGroup(ISourceGenTemplateGroup sgtg, UseTemplateGroupType type) {
        if (!type.getUseTemplate().isEmpty()) {
            for (Iterator iterator = type.getUseTemplate().iterator(); iterator
                    .hasNext();) {
                UseTemplateType ut = (UseTemplateType) iterator.next();
                try {
                    templateAttributeStack.push(attrs.copy());
                    applyAttributes(null, null, null, null, null, null);
                    attrs.group = sgtg.getId();
                    compileUseTemplate(ut);
                } finally {
                    attrs = (TemplateAttributes) templateAttributeStack.pop();
                }
            }
        } else {
            callSuperGroup(sgtg);
        }
    }
    
    private void compileUseTemplateGroup(UseTemplateGroupType type) {
        //System.out.println("use-template-group="+type); //$NON-NLS-1$

        addComment(type);
        
        if (type.getIds().equals("*")) { //$NON-NLS-1$
            // use all the named template groups from bases
            boolean found = false;
        	for (IComponentSourceGenInfo info = base; info != null; info = info.getBaseInfo()) {
	            for (Iterator iter = info.getTemplateGroupIdToSourceGenTemplateGroupMap().values().iterator(); iter.hasNext();) {
	                ISourceGenTemplateGroup sgtg = (ISourceGenTemplateGroup) iter.next();
	                useTemplateGroup(sgtg, type);
	                found = true;
	            }
	            if (found)
	            	break;
        	}
        }
        else {
            String[] ids = type.getIds().split(",| "); //$NON-NLS-1$
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                id = verifyOrFixId(id);
                
                boolean found = false;
            	for (IComponentSourceGenInfo info = base; info != null; info = info.getBaseInfo()) {
	                for (Iterator iter = info.getTemplateGroupIdToSourceGenTemplateGroupMap().values().iterator(); iter.hasNext();) {
	                    ISourceGenTemplateGroup sgtg = (ISourceGenTemplateGroup) iter.next();
	                    if (sgtg.getId().equals(id)) {
	                        found = true;
	                        useTemplateGroup(sgtg, type);
	                    }
	                }
	                if (found)
	                	break;
            	}
                if (!found) {
                	String baseId = baseComponent != null? baseComponent.getId() : ""; //$NON-NLS-1$
                	emit(IStatus.ERROR, "SourceGenAdapterScript.UseTemplateGroupBadId",//$NON-NLS-1$
                            new Object[] { id, baseId, component.getId() });
                }
            }
        }
    }

    private void callSuperGroup(ISourceGenTemplateGroup sgtg) {
        if (templateGroupRegistered(sgtg)) {
            emit(IStatus.ERROR, "SourceGenAdapterScript.UseTemplateGroupReuse", //$NON-NLS-1$ 
                    new String[] { sgtg.getId(), component.getId() }); 
        } else {
            registerTemplateGroup(sgtg);
            for (Iterator iter = sgtg.getTemplates().iterator(); iter.hasNext();) {
                ISourceGenTemplate sgt = (ISourceGenTemplate) iter.next();
                callSuper(sgt);
            }
        }
    }

    private void compileScript(ScriptType type) {
        //System.out.println("script="+type); //$NON-NLS-1$
        // not implemented yet
        Check.checkState(false);
    }

    private void applyAttributes(String location, String phase, String mode, List forms, List ifEvents, String ifExpr) {
        // apply any of the attributes to this template
        if (location != null && location.length() > 0)
            attrs.location = location;

        if (mode != null && mode.length() > 0)
            attrs.mode = mode;

        if (phase != null && phase.length() > 0) {
            if (attrs.forDefineLocation) {
                emit(Message.ERROR, "SourceGenXMLParser.NoPhaseInDefineLocation", //$NON-NLS-1$ 
                        new Object[] { phase, component.getId() });
            } else {
                attrs.phase = phase;
            }
        }

        boolean formsChanged = (forms != null) || attrs.formMatcher == null;
        	
        if (forms != null && forms.size() != 0) {
        	List newForms = new ArrayList();
        	for (ListIterator iter = forms.listIterator(); iter.hasNext();) {
				String form = (String) iter.next();
				newForms.add(verifyOrFixId(form));
			}
        	forms = newForms;
        	attrs.forms = getFormName(forms);
        }
        
        if (formsChanged) {
	        StringBuffer matcher = new StringBuffer();
	        
	        // match the given forms
	
	        matcher.append("if ("); //$NON-NLS-1$
	        boolean first = true;
	        if (forms != null) for (Iterator iter = forms.iterator(); iter.hasNext();) {
				String form = (String) iter.next();
				if (!first)
					matcher.append(" || "); //$NON-NLS-1$
				if (form.equals("ALL")) //$NON-NLS-1$
					matcher.append("true"); //$NON-NLS-1$
				else {
					// 	/^form$/.test("AknView")
		            matcher.append("formrx.test(\""); //$NON-NLS-1$
		            matcher.append(form);
		            matcher.append("\")"); //$NON-NLS-1$
				}
	            first = false;
	        }
	        if (first) {
	        	// default behavior for defineLocation is for all forms to match by default,
	        	// but outside, for only blank forms to match
	        	if (attrs.forDefineLocation)
	        		matcher.append("true"); //$NON-NLS-1$
	        	else
	        		matcher.append("formrx.test(\"\")"); //$NON-NLS-1$
	        }
	        matcher.append(')');
	        
	        attrs.formMatcher = matcher.toString();
        }
        
        if (ifEvents != null) {
        	/*
        	 // DON'T verify, so events can be handled in common base components
        	  * 
        	// verify that any events handled by the component are actual events
        	IEventDescriptorProvider provider = (IEventDescriptorProvider)
    			component.getAdapter(IEventDescriptorProvider.class);
        	if (provider != null) {
	        	for (Iterator iter = ifEvents.iterator(); iter.hasNext();) {
					String ifEvent = (String) iter.next();
					
					if (provider.findEventDescriptor(ifEvent) == null)
		                emit(IStatus.ERROR, "SourceGenAdapterScript.IfEventNotMatched", //$NON-NLS-1$ 
		                        new String[] { ifEvent, component.getId() }); 
	        	}
        	}*/
        	attrs.ifEvents = ifEvents.size() > 0 ? ifEvents : null;
        }
        
        if (ifExpr != null)
        	attrs.ifExpr = ifExpr;
    }
    
    /** Get the function name for a given template */
    private String getTemplateFunction(String id, String group, String forms) {
    	if (forms != null)
    		forms = forms.replace(IContribution.FORM_SEPARATOR,	"$"); //$NON-NLS-1$
        return "generate" + SEPARATOR + id //$NON-NLS-1$
            + (group != null ? SEPARATOR + group : "") //$NON-NLS-1$ //$NON-NLS-2$
            + (forms != null ? SEPARATOR + SEPARATOR + forms : ""); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private void addComment(EObject type) {
    	addComment(type, null);
    }
    
    private void addComment(EObject type, String name) {
        // comment for diagnosis
        getBuffer().append("\n// "); //$NON-NLS-1$
        Pattern p = Pattern.compile("(\r|\n)", Pattern.MULTILINE); //$NON-NLS-1$
        Matcher m = p.matcher(type.eClass().getName());
        getBuffer().append(m.replaceAll(" ")); //$NON-NLS-1$
        if (name != null) {
        	getBuffer().append(' ');
        	getBuffer().append(name);
        }
        getBuffer().append("\n"); //$NON-NLS-1$
    }

    
    /**
     * Get the arguments for use by the macro expansion.
     * We iterate the list of arguments available to the macro and
     * search for the relevant value either in the explicitly
     * expanded arguments, attribute arguments, passed-in arguments,
     * or renamed arguments.
     * @param info
     * @return true: succeeded
     */
	private boolean gatherArguments(MacroInfo info) {
		boolean success = true;

		// first, gather arguments in which we may need to make expansions
		List<String> unexpandedArguments = new ArrayList<String>();
		
    	// gather attribute arguments
    	for (Iterator iter = info.macro.getAnyAttribute().iterator(); iter.hasNext();) {
    		FeatureMap.Entry entry = (Entry) iter.next();
    		EAttribute attr = (EAttribute) entry.getEStructuralFeature();
    		String argName = attr.getName();
    		String argVal = entry.getValue().toString();
			if (info.arguments.containsKey(argName)) {
				macroError("SourceGenXMLParser.DupExpandArgument", //$NON-NLS-1$
						new Object[] { argName });
				success = false;
			} else if (!info.resolvedMacro.arguments.containsKey(argName)) {
				macroError("SourceGenXMLParser.UnknownExpandArgument", //$NON-NLS-1$
						new Object[] { argName });
				success = false;
				
			} else {
				info.arguments.put(argName, argVal);
				unexpandedArguments.add(argName);
			}
			//System.out.println("expanding attribute argument " +argName+" to "+argVal);
		}
    	
    	// and expandArguments
    	for (Iterator iter = info.macro.getExpandArgument().iterator(); iter.hasNext();) {
    		ExpandArgumentType expandArgumentType = (ExpandArgumentType) iter.next();
    		String argName = expandArgumentType.getName();
    		String argVal = expandArgumentType.getValue();
			if (info.arguments.containsKey(argName)) {
				macroError("SourceGenXMLParser.DupExpandArgument", //$NON-NLS-1$
						new Object[] { argName });
				success = false;
			}
			//System.out.println("expanding expandArgument " +argName+" to "+argVal);
			argVal = TemplateJavaScriptFormatter.clipWhitespaceFromText(argVal, true, true);
			info.arguments.put(argName, argVal);
			unexpandedArguments.add(argName);
		}
    	
    	// now get the passed-in arguments, which have already been expanded
		if (macroStack.size() > 1) {
	    	if (info.macro.getPassArguments() != null) {
	    		if (macroStack.size() == 1) {
	    			macroError("SourceGenXMLParser.BadPassArguments", //$NON-NLS-1$
	    					new Object[0]);
	    			success = false;
	    		} else {
	    			// for each passed argument, find its value from the caller
	    			for (Iterator<String> iter = info.macro.getPassArguments().iterator(); iter.hasNext();) {
						success &= gatherPassedArgument(info, iter.next());
					}
	    		}
	    	} else {
	    		// nothing explicitly specified, so pass all arguments, except possibly those in dontPassArguments
	    		MacroInfo callingInfo = macroStack.get(macroStack.size() - 2);
				for (Map.Entry<String, String> entry : callingInfo.arguments.entrySet()) {
					String argName = entry.getKey();
					if (!info.arguments.containsKey(argName)) {
						if (info.macro.getDontPassArguments() == null
								|| !info.macro.getDontPassArguments().contains(argName)) {
							//success &= gatherPassedArgument(info, argName);
							info.arguments.put(argName, entry.getValue());
						} else {
							info.arguments.put(argName, null);
						}
					}
				}
	    	}
		} else {
			if (info.macro.getPassArguments() != null || info.macro.getDontPassArguments() != null) {
				macroError("SourceGenXMLParser.BadPassArguments", //$NON-NLS-1$
						new Object[0]);
				success = false;
			}
		}
		
    	// now, fill in otherwise unspecified optional arguments with defaults
    	for (Iterator iter = info.resolvedMacro.arguments.entrySet().iterator(); iter.hasNext();) {
    		Map.Entry<String, MacroArgumentType> entry = (Map.Entry<String, MacroArgumentType>) iter.next();
    		String argName = entry.getKey();
    		MacroArgumentType theArgument = entry.getValue();
    		
    		// argument may be specified as the text child
			if (!info.arguments.containsKey(argName)) {
				if (theArgument.getValue() != null && theArgument.getValue().trim().length() > 0) {
					info.arguments.put(argName, TemplateJavaScriptFormatter.clipWhitespaceFromText(theArgument.getValue(), true, true));
					unexpandedArguments.add(argName);
				}
			}
			
			// if not, use the default
			if (!info.arguments.containsKey(argName)) {
				if (theArgument.isOptional()) {
					if (theArgument.getDefault() != null || theArgument.getValue() != null) {
						String argVal = theArgument.getDefault();
						//System.out.println("expanding default argument " +argName+" to "+argVal);
						info.arguments.put(argName, argVal);
						unexpandedArguments.add(argName);
					} else { 
						// else leave undefined 
						//System.out.println("not expanding optional argument w/o default " +argName);
						info.arguments.put(argName, null);
					}
				}
			}
		}
    			
    	// expand all the current arguments
    	for (String argName : unexpandedArguments) {
    		String val = info.arguments.get(argName);
    		val = expandMacroString(val, info);
    		info.arguments.put(argName, val);
		}
    	
    	// now, emit errors if required arguments are missing
    	//System.out.println("For " + info.macro.getName() + ":");
    	for (Iterator iter = info.resolvedMacro.arguments.entrySet().iterator(); iter.hasNext();) {
    		Map.Entry<String, MacroArgumentType> entry = (Map.Entry<String, MacroArgumentType>) iter.next();
    		String argName = entry.getKey();
    		MacroArgumentType theArgument = entry.getValue();
			if (!info.arguments.containsKey(argName)) {
				if (!theArgument.isOptional()) {
					macroError("SourceGenXMLParser.MissingExpandArgument", //$NON-NLS-1$
							new Object[] { argName });
					success = false;
				}
			} else {
				//System.out.println("Passing " + argName + " = " + info.arguments.get(argName));
			}
		}

    	return success;
	}

	private boolean gatherPassedArgument(MacroInfo info, String argName) {
		boolean success = true;
		String fromArgName = argName;
		
		// check for aliasing, e.g. targetArg=incomingArg
		int equalsIdx = argName.indexOf('=');
		if (equalsIdx > 0) {
			fromArgName = argName.substring(equalsIdx + 1);
			argName = argName.substring(0, equalsIdx);
		}
		
		String argVal = null;
		boolean foundArg = false;

		MacroInfo callingInfo = macroStack.get(macroStack.size() - 2);
		if (callingInfo.arguments.containsKey(fromArgName)) {
			foundArg = true;
			argVal = callingInfo.arguments.get(fromArgName);
		}

		/*
		for (ListIterator iterator = macroStack.listIterator(macroStack.size()); iterator.hasPrevious();) {
			MacroInfo callingInfo = (MacroInfo) iterator.previous();
			if (callingInfo == info)
				continue;
			if (callingInfo.arguments.containsKey(fromArgName)) {
				foundArg = true;
				argVal = callingInfo.arguments.get(fromArgName);
				break;
			} else if (callingInfo.resolvedMacro.arguments.containsKey(argName)) {
				foundArg = true;
				break;
			}
		}
		*/
		if (!foundArg) {
			macroError("SourceGenXMLParser.UnknownPassArgument", //$NON-NLS-1$
					new Object[] { argName });
			success = false;
		}
		//System.out.println("expanding passArgument " +argName+" to "+argVal);
		info.arguments.put(argName, argVal);
		return success;
	}
	
	private List<ISourceGenTemplate> compileExpandMacro(ExpandMacroType type) {
    	ResolvedMacro resolvedMacro = macroSupport.findMacro(type.getName());
    	if (resolvedMacro == null) {
    		macroError("SourceGenXMLParser.NoSuchMacro", //$NON-NLS-1$
    				new Object[] { type.getName() });
    		return null;
    	}
    	
        addComment(type, type.getName());

        List<ISourceGenTemplate> elements = null;
    	MacroInfo info = new MacroInfo(type, resolvedMacro);
    	macroStack.push(info);
    	templateAttributeStack.push(attrs.copy());
    	try {
    		//System.out.println("expanding macro " + type.getName());
	    	// get all the arguments to pass
	    	if (gatherArguments(info)) {
	    		// and expand the templates and inlines to remove macro argument references
	    		elements = compileMacroSourceGen(info);
	    	}
    	} finally {
    		attrs = (TemplateAttributes) templateAttributeStack.pop();
    		macroStack.pop();
    	}
    	return elements;
	}


    private List<ISourceGenTemplate> compileMacroSourceGen(MacroInfo info) {
    	List<ISourceGenTemplate> elements = new ArrayList<ISourceGenTemplate>();
    	ResolvedMacro resolvedMacro = macroSupport.findMacro(info.macro.getName());
    	applyAttributes(null, null, null, info.macro.getForms(), 
    			info.macro.getIfEvents(), info.macro.getIfExpr());
    	attrs.definingMacro = true;
    	for (Iterator iter = resolvedMacro.macro.eContents().iterator(); iter.hasNext();) {
            EObject eObj = (EObject) iter.next();
            ISourceGenTemplate element = null;
            if (eObj instanceof ImportArgumentsType || eObj instanceof MacroArgumentType)
            	continue;
            if (eObj instanceof TemplateType) {
                element = compileTemplate(expandTemplate((TemplateType) eObj, info));
            } else if (eObj instanceof InlineType) {
                element = compileInline(expandInline((InlineType) eObj, info));
            } else if (eObj instanceof DefineLocationType) {
            	DefineLocationType defloc = expandDefineLocation((DefineLocationType) eObj, info);
            	element = compileRealizeLocation(defloc);
            } else if (eObj instanceof ExpandMacroType) {
            	List subElements = compileExpandMacro(expandExpandMacro((ExpandMacroType) eObj, info));
            	if (subElements != null)
            		elements.addAll(subElements);
            } else {
                Check.checkState(false);
            }	
            if (element != null)
            	elements.add(element);
		}
    	return elements;
	}

    class MacroVariableLookup implements IVariableLookupCallback {
    	private MacroInfo info;
		public MacroVariableLookup(MacroInfo info) {
			this.info = info;
		}
    	public String getValue(String var) {
    		String value = null;
			Matcher matcher = MODIFIER_PATTERN.matcher(var);
			if (matcher.matches()) {
				String varName = matcher.group(1);
				String modifierInfo = matcher.group(2);
				String basicValue = info.arguments.get(varName);
				
				// boolean tests
				if (modifierInfo.equals(MODIFIER_IS_DEFINED)) {
					value = (basicValue != null ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$
				} 
				else if (basicValue == null) {
					if (!info.resolvedMacro.arguments.containsKey(varName)) {
						macroError("SourceGenXMLParser.UnknownMacroArgument", //$NON-NLS-1$
								new Object[] { varName, info.macro.getName() } );
					} else {
						/*
						macroError("SourceGenXMLParser.UnsetMacroArgumentInModifier",
								new Object[] { varName, modifier, info.macro.getName() } );
								*/
						return "/* ERROR: expansion of unset variable " + varName +" with modifiers " + modifierInfo + " */"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return null;
				}
				else  {
					String[] modifiers = modifierInfo.split("::");
					value = basicValue;
					for (String modifier : modifiers) {
						value = applyModifier(modifier, varName, value, info);
						if (value == null)
							return null;
					}
				}
					
			} else {
				if (info.arguments.containsKey(var)) {
					value = info.arguments.get(var);
					if (value == null) { 
						return "/* ERROR: expansion of unset variable " + var + " */"; //$NON-NLS-1$ //$NON-NLS-2$
					}
					//if (value.contains("$(")) 
					//	System.out.println("### unexpanded variable: " + var);
						
				} else {
					//System.out.println("### unknown variable: " + var);
				}
			}
			return value;
		}
		private String applyModifier(String modifier, String varName, String basicValue, MacroInfo info2) {
			String value;
			if (modifier.equals(MODIFIER_AS_STRING)) {
				value = TextUtils.quote(basicValue, '"');
			} else if (modifier.equals(MODIFIER_AS_FUNCTION_DECL_ARGS)) {
				value = basicValue;
			} else if (modifier.equals(MODIFIER_AS_FUNCTION_DEFN_ARGS)) {
				String defnValue = basicValue.replaceAll(
						"(\\s*=[^,]+)", ""); //$NON-NLS-1$ //$NON-NLS-2$
				value = defnValue;
			} else if (modifier.equals(MODIFIER_AS_FUNCTION_LOCATION_ARGS)) {
				String defnValue = basicValue.replaceAll(
						"(\\s*=[^,]+)", ""); //$NON-NLS-1$ //$NON-NLS-2$
				String locValue = defnValue.replaceAll(
						"(\\s+(/\\*\\s*)?\\w+(\\s*\\*/)?)(?=([,)]|$))", ""); //$NON-NLS-1$ //$NON-NLS-2$
				value = locValue;
			} else if (modifier.equals(MODIFIER_AS_FUNCTION_CALL_ARGS)) {
				String defnValue = basicValue.replaceAll(
						"(\\s*=[^,]+)", ""); //$NON-NLS-1$ //$NON-NLS-2$
				Pattern argPattern = Pattern.compile
					("(\\w+)(,\\s+|$)");
				Matcher argMatcher = argPattern.matcher(defnValue);
				String argsValue = "";
				while (argMatcher.find()) {
					argsValue += argMatcher.group(1) + argMatcher.group(2);
				}
				//String argsValue = defnValue.replaceAll(
						//"(?<=(^|\\s+))[^,]+(?=\\w+)", ""); //$NON-NLS-1$ //$NON-NLS-2$
				//		"[^,]+(?=(?>\\w+)(,|$))", ""); //$NON-NLS-1$ //$NON-NLS-2$
				value = argsValue;
			} else if (modifier.equals(MODIFIER_APPEND_SPACE_UNLESS_EMPTY)) {
				if (basicValue.length() == 0)
					return basicValue;
				return basicValue + " "; //$NON-NLS-1$
			} else if (modifier.equals(MODIFIER_ADD_SPACES_UNLESS_EMPTY)) {
				if (basicValue.length() == 0)
					return basicValue;
				return " " + basicValue + " "; //$NON-NLS-1$ //$NON-NLS-2$
			} else if (modifier.equals(MODIFIER_TO_UPPER)) {
				return basicValue.toUpperCase();
			} else if (modifier.equals(MODIFIER_TO_LOWER)) {
				return basicValue.toLowerCase();
			} else if (modifier.equals(MODIFIER_TO_TITLE)) {
				return TextUtils.titleCase(basicValue);
			} else if (modifier.equals(MODIFIER_SPLIT_AND_INDENT)) {
				String[] args = basicValue.split(","); //$NON-NLS-1$
				if (args.length <= 1)
					return basicValue;
				StringBuffer formatted = new StringBuffer();
				for (int i = 0; i < args.length; i++) {
					formatted.append("\n\t\t"); //$NON-NLS-1$
					formatted.append(args[i].trim());
					if (i + 1 < args.length)
						formatted.append(","); //$NON-NLS-1$
				}
				return formatted.toString();
			} else {
				macroError(
						"SourceGenXMLParser.UnknownMacroVariableModifier", //$NON-NLS-1$
						new Object[] { modifier, varName, info.macro.getName() } );
				return null;
			}
			return value;
		}    	
    }

    private String expandMacroString(String content, final MacroInfo info) {
    	if (content == null)
    		return null;
    	VariableSubstitutionEngine engine = new VariableSubstitutionEngine(null, null);
    	engine.setVariableToken('(');
    	engine.allowRecursion(true);
    	String expanded = engine.substitute(new MacroVariableLookup(info), content);
    	//System.out.println(content + " -> " + expanded);
    	return expanded;
    }
    
    private List expandMacroList(List list, MacroInfo info) {
    	if (list == null)
    		return null;
    	List expanded = new ArrayList();
    	for (Iterator iter = list.iterator(); iter.hasNext();) {
			String value = (String) iter.next();
			expanded.add(expandMacroString(value, info));
		}
    	return expanded;
    }
    
    private TemplateType expandTemplate(TemplateType type, MacroInfo info) {
		TemplateType expanded = ComponentFactory.eINSTANCE.createTemplateType();
		copyURI(expanded, type);
		expanded.setForm(expandMacroString(type.getForm(), info));
		expanded.setForms(expandMacroList(type.getForms(), info));
		if (type.getId() != null && type.getId().length() > 0) {
			macroError("SourceGenXMLParser.InvalidTemplateIdInMacro", //$NON-NLS-1$
					new Object[] { type.getId() });
		}
		//expanded.setId(expandMacroString(type.getId(), info));
		expanded.setId(null);
		expanded.setIfEvents(expandMacroList(type.getIfEvents(), info));
		expanded.setIfExpr(expandMacroString(type.getIfExpr(), info));
		expanded.setLocation(expandMacroString(type.getLocation(), info));
		expanded.setMode(expandMacroString(type.getMode(), info));
		expanded.setPhase(expandMacroString(type.getPhase(), info));
		
		expanded.setValue(expandMacroString(type.getValue(), info));
		return expanded;
	}

	private InlineType expandInline(InlineType type, MacroInfo info) {
		InlineType expanded = ComponentFactory.eINSTANCE.createInlineType();
		copyURI(expanded, type);
		if (type.getId() != null && type.getId().length() > 0) {
			macroError("SourceGenXMLParser.InvalidTemplateIdInMacro", //$NON-NLS-1$
					new Object[] { type.getId() });
		}
		expanded.setForms(expandMacroList(type.getForms(), info));
		expanded.setScope(expandMacroString(type.getScope(), info));
		expanded.setIfEvents(expandMacroList(type.getIfEvents(), info));
		expanded.setIfExpr(expandMacroString(type.getIfExpr(), info));
		expanded.setValue(expandMacroString(type.getValue(), info));
		return expanded;
	}

	private DefineLocationType expandDefineLocation(DefineLocationType type, MacroInfo info) {
		DefineLocationType expanded = ComponentFactory.eINSTANCE.createDefineLocationType();
		copyURI(expanded, type);
		expanded.setId(expandMacroString(type.getId(), info));
		expanded.setIfEvents(expandMacroList(type.getIfEvents(), info));
		expanded.setLocation(expandMacroString(type.getLocation(), info));
		expanded.setBaseLocation(expandMacroString(type.getBaseLocation(), info));
		expanded.setDir(expandMacroString(type.getDir(), info));
		expanded.setFile(expandMacroString(type.getFile(), info));
		expanded.setFilter(expandMacroString(type.getFilter(), info));
		expanded.setDomain(expandMacroString(type.getDomain(), info));
		expanded.setIsEventHandler(expandMacroString(type.getIsEventHandler(), info));
		expanded.setOwned(expandMacroString(type.getOwned(), info));
		expanded.setRealize(expandMacroString(type.getRealize(), info));
		for (Iterator iter = type.eContents().iterator(); iter.hasNext();) {
			EObject eObj = (EObject) iter.next();
			if (eObj instanceof TemplateType)
				expanded.getTemplate().add(expandTemplate((TemplateType) eObj, info));
			else if (eObj instanceof InlineType)
				expanded.getInline().add(expandInline((InlineType) eObj, info));
			else if (eObj instanceof ExpandMacroType)
				expanded.getExpandMacro().add(expandExpandMacro((ExpandMacroType) eObj, info));
			else
				Check.checkState(false);
		}
		compileDefineLocation(expanded);
		return expanded;
	}

	private ExpandMacroType expandExpandMacro(ExpandMacroType type, MacroInfo info) {
		ExpandMacroType expanded = ComponentFactory.eINSTANCE.createExpandMacroType();
		copyURI(expanded, type);
		expanded.setForms(expandMacroList(type.getForms(), info));
		expanded.setIfEvents(expandMacroList(type.getForms(), info));
		expanded.setIfExpr(expandMacroString(type.getIfExpr(), info));
		expanded.setName(expandMacroString(type.getName(), info));
		expanded.setPassArguments(expandMacroList(type.getPassArguments(), info));
		expanded.setDontPassArguments(expandMacroList(type.getDontPassArguments(), info));
		for (Iterator iter = type.getAnyAttribute().iterator(); iter.hasNext();) {
			FeatureMap.Entry entry = (FeatureMap.Entry) iter.next();
			String argVal = entry.getValue().toString();
			expanded.getAnyAttribute().add(entry.getEStructuralFeature(), expandMacroString(argVal, info));
		}
		for (Iterator iter = type.getExpandArgument().iterator(); iter.hasNext();) {
			ExpandArgumentType argument = (ExpandArgumentType) iter.next();
			ExpandArgumentType expandedArgument = ComponentFactory.eINSTANCE.createExpandArgumentType();
			expandedArgument.setName(expandMacroString(argument.getName(), info)); 
			expandedArgument.setValue(expandMacroString(argument.getValue(), info));
			expanded.getExpandArgument().add(expandedArgument);
		}
		return expanded;
	}

	private void copyURI(EObject expanded, EObject type) {
		if (type instanceof InternalEObject) {
			URI uri = ((InternalEObject) type).eProxyURI();
			((InternalEObject) expanded).eSetProxyURI(uri);
		}
	}

	public void emit(int severity, String msgKey) {
        MessageReporting.emitMessage(new Message(severity, 
                component.createMessageLocation(), 
                msgKey, 
                Messages.getString(msgKey)));
    }

    public void emit(int severity, String msgKey, Object arg) {
        MessageReporting.emitMessage(new Message(severity,
                component.createMessageLocation(), 
                msgKey,
                MessageFormat.format(Messages.getString(msgKey), new Object[] { arg })
                ));
    }

    public void emit(int severity, String msgKey, Object[] args) {
        MessageReporting.emitMessage(new Message(severity, 
                component.createMessageLocation(), 
                msgKey,  
                MessageFormat.format(Messages.getString(msgKey), args)
                ));
    }

    private void macroError(String msgKey, Object[] args) {
    	macroErrorLit(msgKey, Messages.getString(msgKey), args);
    }

    private void macroErrorLit(String msgKey, String message, Object[] args) {
    	String outerFormat = Messages.getString("SourceGenXMLParser.MacroErrorFormat"); //$NON-NLS-1$
    	StringBuffer macroNames = new StringBuffer();
    	for (Iterator iter = macroStack.iterator(); iter.hasNext();) {
			MacroInfo info = (MacroInfo) iter.next();
			macroNames.append('\'');
			macroNames.append(info.macro.getName());
			macroNames.append('\'');
			if (iter.hasNext())
				macroNames.append('>');
		}

    	StringBuffer macroRefs = new StringBuffer();
    	for (Iterator iter = macroStack.iterator(); iter.hasNext();) {
			MacroInfo info = (MacroInfo) iter.next();
			URI uri = getEObjectURI(info.macro);
			if (uri != null) {
				macroRefs.append("from: "); //$NON-NLS-1$
				macroRefs.append(uri);
				if (iter.hasNext())
					macroRefs.append("\n"); //$NON-NLS-1$
			}
    	}
    	
    	String messageHead = MessageFormat.format(message, args);
    	String messageTail = ""; //$NON-NLS-1$
    	if (macroNames.length() > 0 || macroRefs.length() > 0)
    		messageTail = MessageFormat.format(outerFormat, 
    			new Object[] { macroNames.toString(), macroRefs.toString() });
    	
        MessageReporting.emitMessage(new Message(IMessage.ERROR, 
                component.createMessageLocation(), 
                msgKey,  
                "{0}", //$NON-NLS-1$
                new Object[] { messageHead + " " + messageTail } //$NON-NLS-1$
                ));
    }

    private URI getEObjectURI(EObject eObj) {
    	if (eObj instanceof InternalEObject)
    		return ((InternalEObject)eObj).eProxyURI();
    	else
    		return null;
	}

    
    private void emitLit(int severity, String msgKey, String message, Object[] args) {
        MessageReporting.emitMessage(new Message(severity, 
                component.createMessageLocation(), 
                msgKey,  
                MessageFormat.format(message, args)
                ));
    }

}
