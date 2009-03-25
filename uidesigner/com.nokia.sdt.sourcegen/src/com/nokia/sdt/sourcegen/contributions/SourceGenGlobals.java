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

package com.nokia.sdt.sourcegen.contributions;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.sourcegen.ISourceGenSession;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.core.SourceGenContext;
import com.nokia.sdt.sourcegen.core.ResourceTracker.ResourceInfo;
import com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator;
import com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.provider.SourceGenSession;
import com.nokia.sdt.symbian.workspace.ISymbianDataModelSpecifier;
import com.nokia.sdt.workspace.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier.IRunWithModelAction;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Script-visible interface to contribution engine.
 * 
 * 
 * 
 */
public class SourceGenGlobals {
    static ContributionEngine engine;
    static SourceGenContext context;
    static IRssModelGenerator rssGenerator;
    
    /**
     * Get global dictionary for one sourcegen pass.
     * This is entirely for the use of sourcegen script.
     * It provides common state which is available to all
     * instances in a design when sources are generated --
     * nothing persists past a save of the data model. 
     */
    public static Map getGlobalDictionary() {
    	return engine.getGlobalDictionary();
    }
    
    /**
     * Create a contribution for the given phase
     * 
     * @param phase
     *            the phase, for parent collation
     * @return new contribution, not complete until text set
     */
    public static IContribution createContributionForPhase(String phase) {
        return engine.createContributionForPhase(phase);
    }

    /**
     * Create a contribution at a given location
     * 
     * @param location the unique location id, defined either in
     * this location or some calling parent 
     * @return new contribution, not complete until text set
     */
    public static IContribution createContributionForLocation(String location) {
    	//System.out.println("creating contribution for " + location);
        return engine.createContributionForLocation(location);
    }

    /**
     * Create a contribution
     * 
     * @return new contribution, not complete until location or phase and text set
     */
    public static IContribution createContribution() {
        return engine.createContribution();
 
    }

    /** Look up the ILocation for the given location id. 
     * @param locationId name of the location
     * @return ILocation or null
     */
    public static ILocation findLocation(String locationId) {
    	return engine.findLocation(locationId);
    }
    
    /**
     * Generate all child contributions, in order
     * @param form a regular expression matching form names ("" = blank/no form)
     * @return list of IContribution
     */
    public static List generateChildContributions(String form) {
    	Check.checkArg(form);
        return engine.gatherChildContributions(context.getContributionGatherer(), form);
    }
    

    /**
     * Generate child contributions
     * <p>
     * Called by parent on each child.
     * @param form a regular expression matching form names ("" = blank/no form)
     * @return list of IContribution
     */
    public static List generateChildContributions(IComponentInstance child, String form) {
    	Check.checkArg(form);
        return engine.gatherContributions(context.getContributionGatherer(), child, form, null);
 
    }

    /**
     * Generate child contributions using a referencing instance for context.
     * This is used when component instance A (refInstance) points to component 
     * instance B (instance), and instance B's sourceGen relies on properties
     * and/or events from instance A.  This supplies an additional parameter
     * to sourceGen called 'refInstance' that can be queried.
     * <p>
     * Note: none of the predefined variables are defined for refInstance, 
     * as they are for instance (e.g. children, properties,
     * instanceName, or instanceMemberName).
     * @param instance the instance to generate
     * @param form a regular expression matching form names ("" = blank/no form)
     * @param refInstance the instance holding a reference to this instance
     * @return list of IContribution
     */
    public static List generateRedirectedInstanceContributions(IComponentInstance instance, String form, IComponentInstance refInstance) {
    	Check.checkArg(form);
        return engine.gatherContributions(context.getContributionGatherer(), instance, form, refInstance);
 
    }

    /**
     * Collate contributions by phase.  Takes a list of phase names and
     * orders contributions so that they reflect the ordering of the phases.
     * Note: this will have no effect if a contribution already has a location.
     * <p>
     * @param contributions the list of contributions; modified in place
     * @param phases list of phases (null may be used to represent un-phased contributions)
     */
    public static void collateContributionsByPhase(List contributions, String[] phases) {
        List temp = new LinkedList();
        for (int i = 0; i < phases.length; i++) {
            String phase = phases[i];
            for (Iterator iter = contributions.iterator(); iter.hasNext();) {
                IContribution contrib = (IContribution) iter.next();
                if (phase != null) {
                    if (contrib.getPhase() != null && contrib.getPhase().equals(phase)) {
                        temp.add(contrib);
                        iter.remove();
                    }
                } else {
                    if (contrib.getPhase() == null) {
                        temp.add(contrib);
                        iter.remove();
                    }
                }
            }
        }
        contributions.addAll(temp);
    }
    
    /**
     * Return sublist of contribution matching the given phase
     * @param contributions the list of contributions
     * @param phase the phase (or null to match contributions without a phase)
     * @return new list with matching contributions, or null on error
     */
    public static List getContributionsForPhase(List contributions, String phase) {
        List ret = new LinkedList();
        for (Iterator iter = contributions.iterator(); iter.hasNext();) {
            IContribution contrib = (IContribution) iter.next();
            if (phase != null) {
                if (contrib.getPhase() != null && contrib.getPhase().equals(phase)) {
                    ret.add(contrib);
                }
            } else {
                if (contrib.getPhase() == null) {
                    ret.add(contrib);
                }
            }
        }
        return ret;
    }

    /**
     * Return sublist of contribution matching the given form
     * @param contributions the list of contributions
     * @param form regular expression to match form ("" = blank/no form)
     * @return new list with matching contributions, or null on error
     */
    public static List getContributionsForForm(List contributions, String form) {
    	Check.checkArg(form);
        List ret = new LinkedList();
        for (Iterator iter = contributions.iterator(); iter.hasNext();) {
            IContribution contrib = (IContribution) iter.next();
            String[] forms = {};
            if (contrib.getForm() != null)
            	forms = contrib.getForm().split(IContribution.FORM_SEPARATOR);
            for (int i = 0; i < forms.length; i++) {
				if (forms[i].matches(form))
					ret.add(contrib);
            }
        }
        return ret;
    }

    /**
     * Return sublist of contribution matching the given location Id
     * @param contributions the list of contributions
     * @param locationId the location name (or null to match contributions without location)
     * @return new list with matching contributions, or null on error
     */
    public static List getContributionsForLocation(List contributions, String locationId) {
        ILocation loc = null;
        if (locationId != null) {
            loc = engine.findOrCreateLocation(locationId, true);
            if (loc == null)
                return null;
        }
        
        // This is inefficient (we should be able to return loc.getContribs() directly)
        // but we must guard against returning a sublist that has elements not in the master list!
        List locContribs = new LinkedList();
        for (Iterator iter = loc.getContributions().iterator(); iter.hasNext();) {
			IContribution contrib = (IContribution) iter.next();
			if (contributions.contains(contrib))
				locContribs.add(contrib);
		}
        return locContribs;
    }
    
    /**
     * Assign locations to phased contributions without a location
     * <p>
     * The contributions with the given phase are assigned the given location
     * @param contributions the list of contributions; modified in place
     * @param phase the phase to match (must not be null)
     * @param location the location to assign
     */
    public static void assignLocationsForPhase(List contributions, String phase, String location) {
        List sublist = getContributionsForPhase(contributions, phase);
        if (sublist == null)
            return;
        
        ILocation loc = engine.findOrCreateLocation(location, true);
        if (loc == null)
            return;
        
        for (Iterator iter = sublist.iterator(); iter.hasNext();) {
            IContribution contrib = (IContribution) iter.next();
            if (contrib.getLocation() == null) {
                loc.addContribution(contrib);
            }
        }
    }    
    
    /**
     * Remove duplicate contributions based on location
     * <p>
     * The list is scanned for contributions matching the given location
     * exactly.  If such contributions have the same <i>trimmed</i> text, the 
     * duplicates are removed.
     * <p>
     * Note: this doesn't account for any contributions implicitly supplied
     * by the current component's &lt;defineLocation&gt; contributions.
     * Either call this function from a caller or do not define possibly
     * duplicateable content in &lt;defineLocation&gt; (you can supply
     * this as &lt;template&gt; appearing at that location).
     * @see java.lang.String#trim()
     */
    public static void removeDuplicateContributionsForLocation(List contributions, String location) {
    	
        List sublist = getContributionsForLocation(contributions, location);
        if (sublist == null || sublist.size() == 0)
            return;
        
        Map textToContrib = new HashMap();
        
        for (Iterator iter = sublist.iterator(); iter.hasNext();) {
            IContribution contrib = (IContribution) iter.next();
            String key = contrib.getText().trim();
            if (key.length() == 0)
            	continue;
            if (!textToContrib.containsKey(key)) {
                // not seen before
                textToContrib.put(key, contrib);
            } else {
                // repeat
                contributions.remove(contrib);
                contrib.getLocation().removeContribution(contrib);
            }
        }
    }

    /**
     * Remove contributions with the given phase.
     */
    public static void removeContributionsForPhase(List contributions, String phase) {
        List sublist = getContributionsForPhase(contributions, phase);
        if (sublist == null)
            return;
        
        contributions.removeAll(sublist);
    }

    public static String titleCase(String text) {
        return TextUtils.titleCase(text);
    }
    
    /**
     * Find the enumerator generated by the given name algorithm with
     * the given property value.  These match the
     * attributes used in &lt;mapEnumMember&gt;.  The difference is,
     * this routine does not generate a new enumerator; it returns
     * null if none was generated.
     * @param instance
     * @param propertyPath path to the property
     * //@param uniqueValue the enumerator value which indicates a unique value was generated,
     * //or "*" if a unique value is always generated
     * @param nameAlg the unique name algorithm used 
     * @return enumerator name or null
     */
    public static String findBuiltinOrGeneratedEnumeratorForAlgorithm(IComponentInstance instance, String propertyPath, String nameAlg) {
        if (instance == null)
            return null;

        Object regVal = rssGenerator.getModelManipulator().getTypeHandler().findEnumerator(instance.getName(), propertyPath, nameAlg);
        if (regVal instanceof IAstEnumerator) {
            return ((IAstEnumerator) regVal).getName().getName();
        } else if (regVal instanceof String) {
            return (String) regVal;
        } else
            return null;

    }

    /**
     * Find or create the enumerator generated by the given name algorithm with
     * the given property value.  These match the
     * attributes used in &lt;mapEnumMember&gt;.  This routine can create
     * new enumerators.  Such enumerators will be created in a globally
     * accessible file (i.e. an HRH file for the application).
     * <p>
     * If the enumerator was generated before but not visible in the DOM,
     * this recreates it. 
     * @param instance
     * @param propertyPath path to the property
     * @param nameAlg the unique name algorithm used 
     * @return enumerator name or null
     */
    public static String findOrCreateEnumeratorForAlgorithm(IComponentInstance instance, String propertyPath, String nameAlg) {
        if (instance == null)
            return null;

        IAstRssSourceFile rssFile = rssGenerator.getRssFile(null, true);
		ITranslationUnit tu = rssFile.getTranslationUnit();

        Object regVal = rssGenerator.getModelManipulator().getTypeHandler().findEnumerator(instance.getName(), propertyPath, nameAlg);
        if (regVal instanceof IAstEnumerator) {
            return ((IAstEnumerator) regVal).getName().getName();
        } else if (regVal instanceof String) {
        	// no longer visible or system enum
        	String enumName = (String) regVal;

        	// ensure it's not a #define
        	if (tu.findDefine(enumName) != null)
        		return enumName;
        	
        	// fall through and recreate
        } else {
        	// not known yet
        }
        
        // make an enumerator
        IAstEnumerator enm = rssGenerator.getModelManipulator().getTypeHandler().createUniqueEnumerator(
        		tu, rssFile, instance, nameAlg, instance, propertyPath, true);
        
        // remember it
        rssGenerator.getModelManipulator().getTypeHandler().registerEnumerator(
        		instance.getName(), propertyPath, nameAlg, enm);
        
        // return its name
        return enm.getName().getName();
    }

    /**
     * Query the enumerator generated by the given name algorithm with
     * the given property value.  These match the
     * attributes used in &lt;mapEnumMember&gt;.  This routine does
     * not rely on an enumerator having been created and does not
     * uniquify the value, and can possibly return a bogus result.
     * @param instance
     * @param propertyPath path to the property
     * @param nameAlg the unique name algorithm used 
     * @return enumerator name or null
     */
    public static String queryEnumeratorForAlgorithm(IComponentInstance instance, String propertyPath, String nameAlg) {
        if (instance == null)
            return null;

        String name = rssGenerator.getModelManipulator().getTypeHandler().createEnumeratorName(instance, nameAlg, instance, propertyPath);
        return name;
    }

    /**
     * Get the filename where this enumerator is declared.
     */
    public static String findDefiningFileForEnumerator(String enumerator) {
        IAstEnumerator enm = rssGenerator.getModelManipulator().getTypeHandler().findGeneratedEnumeratorByName(enumerator);
        if (enm == null)
            return null;
        return enm.getAstSourceFile().getSourceFile().getFileName();
    }
    
    /**
     * Get the list of RSS generated files matching the given pattern.
     * 
     */
    public static String[] findGeneratedRssFiles(String regex) {
    	IRssProjectFileManager fileManager = (IRssProjectFileManager) engine.provider.getAdapter(IRssProjectFileManager.class);
    	Check.checkState(fileManager != null);
        Collection<IAstRssSourceFile> modFiles = fileManager.getGeneratedRssFiles();
    	List matches = new ArrayList();
        Pattern pattern = regex != null ? Pattern.compile(regex) : null;
        
        for (Iterator iter = modFiles.iterator(); iter.hasNext();) {
        	IAstRssSourceFile asf = (IAstRssSourceFile) iter.next();
            String filename = asf.getSourceFile().getFileName();
            if (pattern == null || pattern.matcher(filename).matches())
                matches.add(filename);
        }
        
        return (String[]) matches.toArray(new String[matches.size()]);
    }

    /**
     * Get the unnamed resource generated for the given component
     * @param instance the component instance
     * @param rsrcId the identifier of the generated resource, or null
     */
    public static String getGeneratedResource(IComponentInstance instance, String rsrcId) {
    	if (instance == null)
    		return null;
        ResourceInfo info = rssGenerator.getModelManipulator().getResourceTracker().
            findResourceInfo(instance, rsrcId);
        if (info != null && info.isComplete())
            return info.getName();
        return null;
    }

    /**
     * Get the unnamed resource generated for the given component
     */
    public static String getGeneratedResource(IComponentInstance instance) {
        return getGeneratedResource(instance, null);
    }
    
    /**
     * Generate the "glue" contributions for a view data model.
     * These are the contributions that an application needs to get
     * from a view in order to register it.
     * <p>
     * This automagically filters out contributions not matching the
     * form, with the assumption that the default form templates will
     * never apply to the case of generating from a view.
     * 
     */
    public static List generateViewContributions(String viewFilePath, final String form) {
        WorkspaceContext wsContext = WorkspaceContext.getContext();
        
        IDesignerDataModelSpecifier spec = null;
        if (context.getProject() == null) {
            Messages.emit(IMessage.ERROR,
                    engine.createMessageLocation(),
                    "SourceGenGlobals.NotRunningUnderPlatform", //$NON-NLS-1$ 
                    new Object[] { viewFilePath });
            return null;
        }

        IPath viewResourcePath = new Path(viewFilePath);
        if (viewResourcePath != null) {
	        IProjectContext prjContext = wsContext.getContextForProject(context.getProject());
	        IDesignerDataModelSpecifier[] specs = prjContext.getModelSpecifiers();
	        for (int i = 0; i < specs.length; i++) {
	            if (specs[i].getPrimaryResourcePath().equals(viewResourcePath)) {
	                if (specs[i] instanceof ISymbianDataModelSpecifier) {
	                    spec = specs[i];
	                    break;
	                }
	            }
	        }
        }
        if (spec == null) {
            Messages.emit(IMessage.ERROR,
                    new MessageLocation(new Path(".")),  //$NON-NLS-1$
                    "SourceGenGlobals.NoSuchDataModel", //$NON-NLS-1$ 
                    new String[] { viewFilePath });
            return null;
        }

        final List contribs = new LinkedList();
        final IDesignerDataModelSpecifier spec_ = spec;
        spec.runWithLoadedModel(new IRunWithModelAction() {
            
            public Object run(LoadResult lr) {
                IDesignerDataModel model = lr.getModel();
                if (model != null) {
                	/*
                	boolean disposeSession = false;
                	ISourceGenSession session = engine.provider.createSourceGenSession(model, model.getModelSpecifier());
                	disposeSession = true;
                	
                	if (disposeSession) {
	                	// try to load existing data
	                	boolean gotIt = false;
	                	if (session.getSourceGenProvider().isOneWayUpdate()) {
		                	try {
		                		session.loadFromSource();
		                		gotIt = true;
		                	} catch (CoreException e) {
		                		SourceGenPlugin.getDefault().log(e);
		                	}
	                	}
	                	
	                	IRssModelGenerator subRssGenerator = (IRssModelGenerator) session.getAdapter(IRssModelGenerator.class);
	
	                	if (!gotIt) {
	                		// found nothing: generate resources for the submodel 
	                		subRssGenerator.generateResources();
	                	}
                	
	                	rssGenerator.mergeGeneratedInformation(subRssGenerator);
	                	subRssGenerator.dispose();
	                	
	                	session.dispose();
                	} else {
	                	IRssModelGenerator subRssGenerator = (IRssModelGenerator) session.getAdapter(IRssModelGenerator.class);
	                	rssGenerator.mergeGeneratedInformation(subRssGenerator);
                	}*/
                	
                	// do we need to recreate RSS or can we expect to find it in memory?
                	ISourceGenSession session = model.getSourceGenSession();
                	boolean recreate = session == null
                		|| model.getProjectContext() == null
                		|| !model.getProjectContext().getSourceGenProvider().isOneWayUpdate();
                	
                	if (recreate) {
						session = engine.provider.createSourceGenSession(model, model.getModelSpecifier());
	                	IRssModelGenerator subRssGenerator = (IRssModelGenerator) session.getAdapter(IRssModelGenerator.class);
	                	subRssGenerator.generateResources();
	                	rssGenerator.mergeGeneratedInformation(subRssGenerator);
	                	subRssGenerator.dispose();
	                	session.dispose();
                	} else {
	                	IRssModelGenerator subRssGenerator = (IRssModelGenerator) session.getAdapter(IRssModelGenerator.class);
	                	rssGenerator.mergeGeneratedInformation(subRssGenerator);
                	}
                	
                	// and get the top-level contributions
                	contribs.addAll(SourceGenSession.getDataModelContributions(engine, model, form));
                	
                } else {
                    Messages.emit(IMessage.ERROR,
                            spec_,
                            "SourceGenGlobals.CannotLoadDataModel", //$NON-NLS-1$ 
                            new String[] { spec_.getDisplayName(), lr.getStatus().getMessage() });
                }
                return null;
            }}
        );
        
        return contribs;
    }
    
    /**
     * Generate the "glue" contributions for all the view data models.
     * These are the contributions that an application needs to get
     * from a view in order to register it.
     * <p>
     * This automagically filters out contributions not matching the
     * form, with the assumption that the default form templates will
     * never apply to the case of generating from a view.
     * @param form the sourcegen form to pass
     * @return list of contributions 
     */
    public static List generateAllViewContributions(final String form) {
        WorkspaceContext wsContext = WorkspaceContext.getContext();
        
        if (context.getProject() != null) {
            IProjectContext prjContext = wsContext.getContextForProject(context.getProject());
            IDesignerDataModelSpecifier[] origSpecs = prjContext.getModelSpecifiers();
            
            List specList = Arrays.asList(origSpecs);
            Collections.sort(specList, new Comparator() {

                public int compare(Object o1, Object o2) {
                    return ((IDesignerDataModelSpecifier)o1).getDisplayName().
                    compareTo(((IDesignerDataModelSpecifier)o2).getDisplayName());
                }
            });
            
            final List contribs = new LinkedList();
            for (Iterator iter = specList.iterator(); iter.hasNext();) {
                final IDesignerDataModelSpecifier spec = (IDesignerDataModelSpecifier) iter.next();
                if (!(spec instanceof ISymbianDataModelSpecifier)
                        || !((ISymbianDataModelSpecifier) spec).isRoot()) {
                	contribs.addAll(generateViewContributions(spec.getPrimaryResource().getProjectRelativePath().toString(), form));
                }
            }            
            return contribs;        
        } else {
            Messages.emit(IMessage.ERROR,
                    engine.createMessageLocation(), 
                    "SourceGenGlobals.NotRunningUnderPlatform", //$NON-NLS-1$ 
                    new Object[] { "*" }); //$NON-NLS-1$
            return null;
        }
    }

    /**
     * Get a list of project-relative paths specifying view designs.
     */
    public static String[] getAllProjectViewDesigns() {
        WorkspaceContext wsContext = WorkspaceContext.getContext();
        
        if (context.getProject() != null) {
            IProjectContext prjContext = wsContext.getContextForProject(context.getProject());
            IDesignerDataModelSpecifier[] origSpecs = prjContext.getModelSpecifiers();
            
            List specList = Arrays.asList(origSpecs);
            Collections.sort(specList, new Comparator() {

                public int compare(Object o1, Object o2) {
                    return ((IDesignerDataModelSpecifier)o1).getDisplayName().
                    compareTo(((IDesignerDataModelSpecifier)o2).getDisplayName());
                }
            });
            
            List names = new ArrayList(specList.size());
            for (Iterator iter = specList.iterator(); iter.hasNext();) {
            	IDesignerDataModelSpecifier spec = (IDesignerDataModelSpecifier) iter.next();
                if (!(spec instanceof ISymbianDataModelSpecifier)
                        || !((ISymbianDataModelSpecifier) spec).isRoot()) {
                	names.add(spec.getPrimaryResource().getProjectRelativePath().toString());
                }
            }            
            return (String[]) names.toArray(new String[names.size()]);        
        } else {
            Messages.emit(IMessage.ERROR,
                    engine.createMessageLocation(), 
                    "SourceGenGlobals.NotRunningUnderPlatform", //$NON-NLS-1$ 
                    new Object[] { "*" }); //$NON-NLS-1$
            return null;
        }
    }

    /**
     * Tell whether the given form regex matches any of the forms
     * in the array.
     * @param form
     * @param forms
     * @return true: match found
     */
    public static boolean formMatches(String form, Object[] forms) {
    	for (int i = 0; i < forms.length; i++) {
			if (forms[i].toString().matches(form))
				return true;
		}
    	return false;
    }

    /**
     * Create a stock file in the project from a file relative to
     * the current component or an applicable base, if such file 
     * does not already exist.
     * <p>  
     * This is preferred to explicitly defining a location and
     * applying a template when the file must not have any user-specified
     * comment at the top or any variable expansion.
     * @param directoryId the directory ID for the target (see INameGenerator#..._DIRECTORY_ID)
     * @param filename the filename within the directory for the target file
     * @param stockPath component-relative path to stock file
     */
    public static void createFromStockFile(String directoryId, String filename, String stockPath) throws Exception {
    	String dirPath = engine.provider.getNameGenerator().getProjectRelativeDirectory(engine.dataModel, directoryId);
    	IPath projectPath = new Path(dirPath).append(filename);
    
    	// check for existing file
    	if (context.resolveOrCreateFile(projectPath)) {
    		IComponent component = engine.getCurrentComponent();
    		Check.checkState(component != null);
    		boolean foundFile = false;
    		
    		while (component != null) {
	    		File baseDir = component.getBaseDirectory();
	    		if (baseDir != null) {
		    		File file = new File(baseDir, stockPath);
		    		if (file.exists()) {
			            InputStream is = new FileInputStream(file);
			            try {
			            	context.setFileContents(projectPath, is);
			            } finally {
			                is.close();
			            }
			            foundFile = true;
			            break;
		    		}
	    		}
	    		
	            component = component.getComponentBase();
    		}
    		
    		if (!foundFile) { // did not find any component with a directory or the file
    			throw new FileNotFoundException(stockPath);
    		}
    	}
    	
    	context.addExtraSourcePath(projectPath);
    }
    
	public static void setup(ContributionEngine engine, SourceGenContext context, IRssModelGenerator rssGenerator) {
		SourceGenGlobals.engine = engine;
		SourceGenGlobals.context = context;
		SourceGenGlobals.rssGenerator = rssGenerator;
	}
	
	public static void cleanup() {
		SourceGenGlobals.engine = null;
		SourceGenGlobals.context = null;
		SourceGenGlobals.rssGenerator = null;
	}
}
