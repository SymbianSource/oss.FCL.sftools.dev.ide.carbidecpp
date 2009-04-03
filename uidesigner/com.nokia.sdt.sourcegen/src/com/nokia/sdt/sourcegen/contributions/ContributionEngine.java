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
import com.nokia.cpp.internal.api.utils.core.IMessageListener;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;
import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.cpp.internal.api.utils.core.VariableSubstitutionEngine;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.core.SourceGenContext;
import com.nokia.sdt.sourcegen.provider.SourceGenProvider;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.osgi.framework.Version;

import java.text.MessageFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * This class handles the recursive generation and gathering
 * of contributions by invoking code from the IComponentSourceGen
 * interface and making IContribution instances.
 * <p>
 * The routines in this class are used in the context of a stack
 * of ContributionContext objects, indicating a recursive
 * descent from a root to leaf component instances.  Each instance
 * maintains a set of locations which children can reference.
 * <p>
 * The global routines in SourceGenGlobals are intended for use
 * from Javascript invoked by the IComponentSourceGen interface
 * (either automatically generated or hand-written).
 * <p>
 * For testing use, testingPushFrame/testingPopFrame can manipulate
 * this stack.
 * 
 *
 */
public class ContributionEngine {
    /** Stack of ContributionContext.  
     */
    Stack<ContributionContext> generateStack;
    DomainRegistry domainRegistry;
    /** Track the locations per component instance by name */
    Map<IComponentInstance, Map<String, ILocation>> componentToLocationsMap;

	private Map globalDictionary = new HashMap();
	IVariableProvider varProvider; /// used in globals
	ISourceGenProvider provider; /// used in globals
	
	// keep track of multiple events bound to the same location:
	// not in itself wrong, but possibly wrong if different event types map
	private Map<ILocation, List<IEventBinding>> locationToEventBindingMap;
	// mapping of event names to the locations they generate
	private Map<String, Set<ILocation>> eventToLocationMap;
	private Map<IEventBinding, ILocation> eventLocations;
	IDesignerDataModel dataModel;
	private ISourceGenSession session;

	private List<PatchContribution> patchContributions;

    /**
     * Create a contribution engine for the given project
     */
    public ContributionEngine(ISourceGenProvider provider, 
    		IVariableProvider variableProvider, 
    		SourceGenContext context,
    		IDesignerDataModel model,
    		ISourceGenSession session) {
        Check.checkArg(context);
        Check.checkArg(model);
        Check.checkArg(session);
        //this.context = context;
        this.dataModel = model;
        this.session = session;
        this.provider = provider;
    	this.varProvider = variableProvider;
        this.generateStack = new Stack();
        this.componentToLocationsMap = new HashMap<IComponentInstance, Map<String,ILocation>>();
        this.locationToEventBindingMap = new HashMap<ILocation, List<IEventBinding>>();
        this.eventToLocationMap = new HashMap<String, Set<ILocation>>();
        this.eventLocations = new HashMap<IEventBinding, ILocation>();
        this.patchContributions = null;
        
        this.domainRegistry = ((SourceGenProvider)provider).createDomainRegistry();
    }

    public ContributionContext testingPushFrame(IContributionGatherer gatherer, IComponentInstance instance, String form) {
    	return testingPushFrame(gatherer, instance, form, null);
    }
    
    public ContributionContext testingPushFrame(IContributionGatherer gatherer, IComponentInstance instance, String form, IComponentInstance refInstance) {
    	if (form == null)
    		form = ""; //$NON-NLS-1$
        ContributionContext frame = ContributionContext.createFrame(this, gatherer, instance, refInstance, form);
        if (frame != null) {
        	if (!validateNoRecursion(frame))
        		return null;
            generateStack.push(frame);
            return frame;
        }
        return null;
    }
    
    /**
     * Ensure this frame won't introduce recursive sourcegen
	 * @param frame
	 * @return
	 */
	private boolean validateNoRecursion(ContributionContext frame) {
		for (ContributionContext ctx : generateStack) {
			if (ObjectUtils.equals(ctx.instance.getName(), frame.instance.getName())
					&& ObjectUtils.equals(ctx.form, frame.form)) {
	            Messages.emitAndLog(IMessage.ERROR, 
	                    frame.instance,
	                    "ContributionEngine.RecursiveGeneration", new Object[] {  //$NON-NLS-1$
	            		currentFrame().component.getId(), frame.component.getId(),
	            		frame.form } );
				return false;
			}
				
		}
		return true;
	}

	public void testingPopFrame() {
        generateStack.pop();
    }
    
    /**
     * Recursively gather contributions for the given component instance,
     * with no redirection.
     * @param gatherer the implementation which gathers contributions
     * @param instance
     * @return the full list of contributions from instance and children
     */
    public List gatherContributions(IContributionGatherer gatherer, IComponentInstance instance, String form) {
    	return gatherContributions(gatherer, instance, form, null);
    }
 
    /**
     * Recursively gather contributions for the given component instance
     * @param gatherer the implementation which gathers contributions
     * @param instance
     * @param refInstance an instance which references instance, for use in
     * "redirected" sourcegen (see {@link SourceGenGlobals#generateRedirectedInstanceContributions(IComponentInstance, String, IComponentInstance)}
     * @return the full list of contributions from instance and children
     */
    public List gatherContributions(IContributionGatherer gatherer, IComponentInstance instance, String form, IComponentInstance refInstance) {
        Check.checkArg(gatherer);
        Check.checkArg(instance);
        if (form == null)
        	form = ""; //$NON-NLS-1$
        
        List contribs = Collections.EMPTY_LIST;
        
        ContributionContext frame = ContributionContext.createFrame(this, gatherer, instance, refInstance, form);
        if (frame != null) {
        	if (!validateNoRecursion(frame))
        		return contribs;
            generateStack.push(frame);
            
            // generate the contributions for this instance.
            // the script should include <inline> code that
            // calls Engine.generateChildContributions in order
            // to call back to this object
            contribs = frame.gatherer.generate(frame);
          
            // mark any owned locations not contributed to and eventually delete them
            //removeDeadOwnedLocations(frame);
            findDeadOwnedLocations(gatherer.getDeadLocations(), frame);
            
            generateStack.pop();
        
            // add any contributions that initialize locations
            // at the head of the list
            if (contribs == null || contribs == Collections.EMPTY_LIST)
                contribs = frame.locationDefiningContributions;
            else
                contribs.addAll(0, frame.locationDefiningContributions);
        }  else {
            // iterate the children of this component and perform
            // default contribution gathering
            List subList = gatherChildContributions(gatherer, instance, form, refInstance);
            if (subList != Collections.EMPTY_LIST) {
                if (contribs == null || contribs == Collections.EMPTY_LIST)
                    contribs = subList;
                else
                    contribs.addAll(subList);
            }
        }

        // now, find any unreferenced owned locations
        // and delete their contents
        //cleanDeadOwnedLocations(model, gatherer);

        return contribs;
    }

	/**
     * @param gatherer
     * @param instance
     * @param form
     * @return contributions generated from the children of instance
     */
    private List gatherChildContributions(IContributionGatherer gatherer, IComponentInstance instance, String form, IComponentInstance refInstance) {
        Check.checkArg(instance);
        List list = new LinkedList();
        EObject[] kids = instance.getChildren();
        if (kids != null) for (int i = 0; i < kids.length; i++) {
            IComponentInstance kid = (IComponentInstance) EcoreUtil.getRegisteredAdapter(kids[i], IComponentInstance.class);
            if (kid != null) {
                List subList = gatherContributions(gatherer, kid, form, refInstance);
                if (subList != null && subList != Collections.EMPTY_LIST) {
                	list.addAll(subList);
                }
            }
        }
        return list;
    }

    public List gatherChildContributions(IContributionGatherer gatherer, String form) {
    	return gatherChildContributions(gatherer, form, null);
    }
    
    /**
     */
    public List gatherChildContributions(IContributionGatherer gatherer, String form, IComponentInstance refInstance) {
        ContributionContext context = currentFrame();
        if (context == null)
            return null;
        return gatherChildContributions(gatherer, context.getInstance(), form, refInstance);
    }

    /**
     * Create a half-initialized contribution for this phase
     * @param phase
     * @return new contribution
     */
    public IContribution createContributionForPhase(String phase) {
        ContributionContext frame = currentFrame();
        return new Contribution(phase, frame.instance.getComponentId(), getComponentOrInstanceLocation(frame.instance));
    }
    
    /**
     * Create a half-initialized IContribution located at the
     * given location.
     * @param locationId
     * @return new contribution
     */
    public IContribution createContributionForLocation(String locationId) {
        ILocation loc = findOrCreateLocation(locationId, true);
        
        // always provide a contribution, even if the location is bogus,
        // so we don't get cascading errors
        if (loc == null)
            loc = new RootLocation();
        
        ContributionContext frame = currentFrame();
        return new Contribution(loc, frame.instance.getComponentId(), getComponentOrInstanceLocation(frame.instance));
    }

    /**
     * Create a half-initialized IContribution
     * @return new contribution
     */
    public IContribution createContribution() {
        ContributionContext frame = currentFrame();
        return new Contribution(getComponentOrInstanceLocation(frame.instance), frame.instance.getComponentId());
    }

    private MessageLocation getComponentOrInstanceLocation(IComponentInstance instance) {
    	return Messages.getComponentOrInstanceLocation(instance);
    }
    
    /**
	 * Find the location for the given locationId, if it was
	 * previously referenced
	 * @param locationId 
	 * @return ILocation or null (message reported)
	 */
	public ILocation findLocation(String locationId) {
	    // find the ILocation
	    ILocation loc = null;
	    for (int pos = generateStack.size(); pos-- > 0; ) {
	        ContributionContext frame = (ContributionContext) generateStack.get(pos);
            Map<String, ILocation> locationsMap = getLocations(frame);
	        loc = (ILocation) locationsMap.get(locationId);
	        if (loc != null)
	        	break;
	    }
	    return loc;
	}

	/**
	 * Generate a location, by marking it for creation if not existing
	 * and deleting it if owned.
	 * @param frame
	 * @param loc
	 */
	private void generateLocation(ContributionContext frame, ILocation loc) {
	    // if the location has no correspondent in current source,
	    // or it's an owned location which will be completely regenerated,
	    // we need to get its defining contributions in order to regenerate it
	    if (loc.getState() == ILocation.S_MISSING
	            || loc.isOwned()) {
	
	    	// mark location now so we don't recursive re-define locations
	    	// and duplicate code (e.g. in cases where event handlers are shared)
	    	boolean existed = false;
		    if (loc.isOwned()) { 
		    	existed = loc.getState() == ILocation.S_TO_REPLACE;
		        loc.setState(ILocation.S_TO_REPLACE);
		    } else {
		        if (loc.getState() == ILocation.S_MISSING) {
		            loc.setState(ILocation.S_QUEUED);
		        }
		        else if (loc.getState() == ILocation.S_RESOLVED) {
		            // already found, delete the location and replace
		            loc.setState(ILocation.S_TO_REPLACE);
		        }
		    }
		    
		    // don't regenerate the location if multiply defined
		    if (!existed) {
			    // add contributions to define this location
			    List locContribs = frame.gatherer.generateLocation(frame, loc.getPrimarySourceGenLocation().getId());
			    
			    // if no location set, make the location templates be defined for this location
			    for (Iterator iterator = locContribs.iterator(); iterator
			            .hasNext();) {
			        IContribution contrib = (IContribution) iterator.next();
			        if (contrib.getLocation() == null) {
			            loc.addContribution(contrib);
			        }
			    }
			    
			    frame.locationDefiningContributions.addAll(locContribs);
		    }
		    /*
		    if (loc.isOwned()) {
		        loc.setState(ILocation.S_TO_REPLACE);
		    } else {
		        if (loc.getState() == ILocation.S_MISSING) {
		            loc.setState(ILocation.S_QUEUED);
		        }
		        else if (loc.getState() == ILocation.S_RESOLVED) {
		            // already found, delete the location and replace
		            loc.setState(ILocation.S_TO_REPLACE);
		        }
		    }
		    */
		}
	}

	/**
     * Create the location for the given locationId
     * @param locationId 
     * @param generate true: generate/prime the location, false: just locate it
     * @return ILocation or null (message reported)
     */
    public ILocation findOrCreateLocation(String locationId, boolean generate) {
        // find the ILocation
        ILocation loc = null;
        for (int pos = generateStack.size(); pos-- > 0; ) {
            ContributionContext frame = (ContributionContext) generateStack.get(pos);
            Map<String, ILocation> locationsMap = getLocations(frame);
            loc = (ILocation) locationsMap.get(locationId);
            if (loc != null) {
            	//System.out.println("found ILocation: " + loc);
                break;
            }
            
            Map locationMap = frame.info.getLocationIdToLocationMap(); 
            ISourceGenLocation sloc = (ISourceGenLocation) locationMap.get(locationId);
            if (sloc != null) {
                // define the location and any bases 
                loc = createLocation(frame, sloc, generate); 
            	//System.out.println("added ILocation: " + loc);
                
                // add the location now to prevent recursion
            	if (generate)
            		locationsMap.put(locationId, loc);
                
                if (loc != null && generate) {
                	generateLocation(frame, loc);
                }
                return loc;
            }
        }
        
        if (loc == null) {
            Messages.emitAndLog(IMessage.ERROR, 
                    currentFrame().instance,
                    "SourceGenGlobals.UnknownLocation", new Object[] { locationId, currentFrame().component.getId() } ); //$NON-NLS-1$
        }
        return loc;
    }

	/**
	 * Get the map of String -> ILocation for the component in the frame.
	 * All frames referencing the same component share the same map.
	 * @param frame
	 */
	private Map<String, ILocation> getLocations(ContributionContext frame) {
        Map<String, ILocation> locationsMap = componentToLocationsMap.get(frame.instance);
        if (locationsMap == null) {
        	locationsMap = new HashMap<String, ILocation>();
        	componentToLocationsMap.put(frame.instance, locationsMap);
        }
		return locationsMap;
	}

	/**
	 * Go through all the locations which might be dead --
	 * i.e., not generated by one component instance -- and see
	 * if this is still the case.  If so, mark the location for
	 * deletion.
	 * @param deadLocations
	 */
	public void markDeadLocations(Set<ILocation> deadLocations) {
		for (Iterator iterator = deadLocations.iterator(); iterator
				.hasNext();) {
			ILocation loc = (ILocation) iterator.next();
			
			// see if loc actually has contributions; 
			// if not, then it's really dead
			if (loc.getContributions() == null || loc.getContributions().size() == 0) {
    			if (loc.isOwned() && loc.getState() == ILocation.S_RESOLVED) {
    				// mark the location to be eventually deleted
    				loc.setState(ILocation.S_DELETE);
    			} else {
    				// don't stir the waters
    				if (loc.getState() == ILocation.S_MISSING)
    					loc.setState(ILocation.S_UNKNOWN);
    			}
			}
		}
	}

	static class TemplateMessageListener implements IMessageListener {
		List<IMessage> messages = new ArrayList(4);

		public boolean isHandlingMessage(IMessage msg) {
			return true;
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.utils.IMessageListener#emitMessage(com.nokia.sdt.utils.IMessage)
		 */
		public void emitMessage(IMessage msg) {
			messages.add(msg);
		}
	}
	
	/**
     * Create an ILocation from the source gen data.
     * This expands any template variables present in
     * the ISourceGenLocation.
     * <p>
     * This creates the location and any locations for
     * the base locations, which, if recursing, may result in adding
     * contributions to generate base locations.
     * @param frame
     * @param sloc
     * @param generate true: generate/prime the location, false: just find it
     * @return ILocation, may be null
     */
    private ILocation createLocation(ContributionContext frame, ISourceGenLocation sloc, boolean generate) {
        
        IDomain domain;
        String dir;
        String file;
        String location;
        ILocation loc = null;

        // If this location depends on events, find the firs bound
        // event it matches.  This determines what value the 'event.*'
        // variables have.
        boolean eventLocation = (sloc.getIfEvents() != null);
        IEventBinding binding = null;
        if (eventLocation) {
        	for (Iterator iter = sloc.getIfEvents().iterator(); iter.hasNext();) {
				String ifEvent = (String) iter.next();
				binding = frame.instance.findEventBinding(ifEvent);
				if (binding != null)
					break;
			}
        }

        // TODO: replace this message hooking code with exceptions
        TemplateMessageListener msgListener = new TemplateMessageListener();
        
        if (sloc.getBaseLocation() != null) {
            ILocation baseLocation = findOrCreateLocation(sloc.getBaseLocation(), generate);
            if (baseLocation == null)
                return null;
            // define variables relevant to the event binding used in the defineLocation
            varProvider.defineEventVariables(frame.variables, binding);
            domain = baseLocation.getDomain();
            location = substituteVariables(msgListener, frame, sloc.getLocation());
            if (msgListener.messages.size() == 0) {
            	try {
            		loc = domain.makeLocation(baseLocation, sloc, location); //$NON-NLS-1$
            	} catch (SourceGenException e) {
            		reportSourceGenException(e, frame.getInstance().getComponent());
            		loc = null;
            	}
            }
        } else {
            String domainName = sloc.getDomain();
            domain = domainRegistry.getDomain(domainName);
            if (domain == null) {
                Messages.emit(IMessage.ERROR, sloc.createMessageLocation(), "ContributionEngine.NoSuchDomain", (new String[] { sloc.getId(),
				domainName,
				sloc.getComponentId() }), false);
                return null; 
            }
            // define variables relevant to the event binding used in the defineLocation
            varProvider.defineEventVariables(frame.variables, binding);
            dir = substituteVariables(msgListener, frame, sloc.getDirectory());
            file = substituteVariables(msgListener, frame, sloc.getFile());
            location = substituteVariables(msgListener, frame, sloc.getLocation());
            if (msgListener.messages.size() == 0) {
            	try {
            		loc = domain.makeLocation(sloc, dir, file, location);
            	} catch (SourceGenException e) {
            		reportSourceGenException(e, frame.getInstance().getComponent());
            	}
            }
        }
        
        if (msgListener.messages.size() > 0) {
	    	// Not generating and the event is not bound, so the 
        	// variables likely referenced to define the location are missing.
	    	// Don't report errors.
	    	if (!generate && eventLocation && binding == null) {
	    		// ignore
	    	} else {
	    		for (Iterator iter = msgListener.messages.iterator(); iter.hasNext();) {
					IMessage msg = (IMessage) iter.next();
					MessageReporting.emitMessage(msg);
				}
	    	}
	    	return null;
        }        
        
        // register the event binding symbol 
        if (generate && loc != null && binding != null && sloc.isEventHandler()) {
        	binding.setHandlerSymbolInformation(
        			domain.encodeLocationToEventHandlerSymbol(loc));
        	
        	eventLocations.put(binding, loc);
        	
        	String eventName = binding.getHandlerName();
        	Set<ILocation> locations = eventToLocationMap.get(eventName);
        	if (locations == null) {
        		locations = new HashSet<ILocation>(1);
        		eventToLocationMap.put(eventName, locations);
        	}
        	locations.add(loc);
        	
        	List<IEventBinding> bindings = locationToEventBindingMap.get(loc);
        	if (bindings == null) {
        		bindings = new ArrayList<IEventBinding>(1);
        		locationToEventBindingMap.put(loc, bindings);
        	}
        	bindings.add(binding);
        }
        
        return loc;
        
    }

    
    
    /**
	 * @param e
	 */
	private void reportSourceGenException(SourceGenException e,
			IComponent component) {
		String baseMsg = MessageFormat.format(e.getLocMsg(), e.getArgs());
		String componentFmt = Messages.getString("ContributionEngine.ErrorGeneratedFromComponent"); //$NON-NLS-1$
		MessageReporting.emitMessage(new Message(IMessage.ERROR, component.createMessageLocation(),
				e.getKey(), componentFmt, new Object[] { baseMsg, component.getId() }));
	}

	/**
     * @param msgListener the handler for messages
     * @param frame the frame for the component in which the expression was defined 
     * @param expr the expression text
     * @return substituted text
     */
    private String substituteVariables(TemplateMessageListener msgListener, ContributionContext frame, String expr) {
        if (expr == null)
            return null;
    
        VariableSubstitutionEngine engine = new VariableSubstitutionEngine(
        		msgListener, frame.getInstance().getComponent().createMessageLocation());
        return engine.substitute(frame.variables, expr);
    }

    interface IDomainContributionHandler {
    	public void applyContributions(IDomain domain, List<IContribution> contributions) throws CoreException;
    }
    
    public void iterateContributions(List contribs, IDomainContributionHandler handler) throws CoreException {
        if (contribs.size() == 0)
            return;
        
        // sort the contributions by domain
        Collections.sort(contribs, new Comparator() {

            public int compare(Object o1, Object o2) {
                IContribution c1 = (IContribution) o1;
                IContribution c2 = (IContribution) o2;
                IDomain d1 = c1.getLocation().getDomain(); 
                IDomain d2 = c2.getLocation().getDomain();
                if (d1 != null) {
                	if (d2 != null) {
                		int match = d1.getId().compareTo(d2.getId());
                		if (match == 0) {
                			if (ObjectUtils.equals(IContribution.MODE_AT_END, c1.getMode())) {
                				match = 1;
                    		} else if (ObjectUtils.equals(IContribution.MODE_AT_END, c2.getMode())) {
                    			match = -1;
                    		}
                		}
                		return match;
                	}
                	return 1;
                } else {
                	if (d2 != null)
                		return -1;
                	else
                		return 0;
                }
            }}
        );
        
        // handle them by domain
        List<IContribution> domainContribs = new ArrayList<IContribution>();
        IDomain lastDomain = null;
        for (Iterator iter = contribs.iterator(); iter.hasNext();) {
            IContribution contrib = (IContribution) iter.next();
            IDomain domain = contrib.getLocation().getDomain();
            if (lastDomain != null && domain != lastDomain) {
            	//domain.generateSource(domainContribs);
            	handler.applyContributions(domain, domainContribs);
                domainContribs.clear();
                domainContribs.add(contrib);
            } else {
                domainContribs.add(contrib);
                if (!iter.hasNext()) {
                	//domain.generateSource(domainContribs);
                	handler.applyContributions(domain, domainContribs);
                }
            }
            lastDomain = domain;
        }
    }

    /**
     * Apply the list of contributions to the project
     */
    public void applyContributions(List contribs) throws CoreException {
    	iterateContributions(contribs, new IDomainContributionHandler() {

			public void applyContributions(IDomain domain, List<IContribution> contributions) throws CoreException {
				if (domain != null)
					domain.generateSource(contributions);
			}
    	});
    }

    /**
     * Scan contributions to find what files are generated
     */
    public Collection<IPath> getFilesForContributions(List contribs) throws CoreException {
    	final Collection[] files = new Collection[1];
    	iterateContributions(contribs, new IDomainContributionHandler() {
			public void applyContributions(IDomain domain, List<IContribution> contributions) throws CoreException {
				files[0] = domain.getGeneratedFiles(contributions);
			}
    	});
    	return files[0];
    }

    /**
     * Get the current stack frame
     * @return the current frame, or null
     */
    public ContributionContext currentFrame() {
        if (generateStack.size() > 0)
            return (ContributionContext) generateStack.peek();
        else
            return null;
    }
    
    /**
     * Remove any contributions not related to patching
     * @param contribs
     */
	public void removeNonPatchContributions(List contribs) {
		for (Iterator iter = contribs.iterator(); iter.hasNext();) {
            IContribution contrib = (IContribution) iter.next();
            
            if (!PatchContribution.isPatchContribution(contrib)) {
            	iter.remove();
            }
		}		
	}

    /**
     * Validate that the contributions are finalized and filter out
     * ones that are invalid or used for special purposes (i.e. patches)
     * @param contribs
     */
    public void validateContributions(List contribs) {
        if (patchContributions == null)
        	patchContributions = new ArrayList<PatchContribution>();
    	
        if (contribs == null)
            return;
        
        // validate the contributions
        int index = 0;
        for (Iterator iter = contribs.iterator(); iter.hasNext();) {
            IContribution contrib = (IContribution) iter.next();
            String name = contrib.getId() != null ? contrib.getId() : "<unnamed #"+index+">"; //$NON-NLS-1$ //$NON-NLS-2$
            boolean remove = false;
            
            // remove patches first
            if (PatchContribution.isPatchContribution(contrib)) {
            	patchContributions.add(new PatchContribution(contrib));
            	remove = true;
            } else if (contrib.getLocation() == null) {
                if (contrib.getPhase() != null) {
                    Messages.emit(IMessage.ERROR,
                    		contrib.getMessageLocation(),
                            "ContributionEngine.ContribWithPhaseAndLocation", //$NON-NLS-1$
                        new String[] { 
                                name,
                                    contrib.getPhase(),
                                    contrib.getComponentId(),
                                    contrib.getText() }, false);
                } else {
                    Messages.emit(IMessage.ERROR,
                    		contrib.getMessageLocation(),
                            "ContributionEngine.ContribWithoutLocation", //$NON-NLS-1$
                            new String[] { 
                                name,
                                contrib.getComponentId(),
                                        contrib.getText()}, false);
                }
                remove = true;
            } else {
            	// ensure that "setLocation()" by itself wasn't called
            	if (!contrib.getLocation().getContributions().contains(contrib)) {
                    Messages.emit(IMessage.ERROR,
                    		contrib.getMessageLocation(),
                            "ContributionEngine.ContribWithIncorrectLocation", //$NON-NLS-1$
                            new Object[] { 
                                name,
                                contrib.getComponentId(),
                                        contrib.getText()}, false);
            	}
            }
            if (contrib.getText() == null) {
                Messages.emit(IMessage.ERROR,
                		contrib.getMessageLocation(),
                        "ContributionEngine.ContribWithoutText", //$NON-NLS-1$
                        new String[] { 
                            name,
                            contrib.getComponentId() }, false);
                remove = true;
            }
            if (remove) {
                iter.remove();
            }
            index++;
        }

        // validate that there is only one copy
        index = 0;
        for (Iterator iter = contribs.iterator(); iter.hasNext();) {
            IContribution contrib = (IContribution) iter.next();
            for (int index2 = index + 1; index2 < contribs.size(); index2++)
            	if (contribs.get(index2) == contrib) {
            		Check.checkState(false);
            	}
            index++;
        }
        
        // Validate event handler sharing.
        validateEvents();
    }
    
    /**
     * Get contributions identified for patching purposes
     * @return
     */
    public List<PatchContribution> getPatchContributions() {
    	return patchContributions;
    }

    /**
	 * Validate event handler sharing.
	 * 
	 * (1) Multiple event bindings may use the
	 * same event handler name but result in different methods, which may
	 * have unexpected results.
	 * 
	 * (2) It's usually an error to map an event to another non-event handler.
	 * 
	 * The messages are logged to the session for the data model validation phase.
	 */
	private void validateEvents() {
		IDesignerDataModelSpecifier dmSpec = dataModel.getModelSpecifier();
		MessageLocation msgLoc = (dmSpec != null) ? dmSpec.createMessageLocation() : 
			new MessageLocation(new Path(".")); //$NON-NLS-1$

		for (Iterator iter = eventToLocationMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, Set<ILocation>> entry = (Map.Entry<String, Set<ILocation>>) iter.next();
			String eventName = entry.getKey();
			Set<ILocation> locations = entry.getValue();
			
			// check for non-unknown locations
			int knownLocations = 0;
			for (ILocation loc : locations) {
				int state = loc.getState();
				if (state != ILocation.S_UNKNOWN && state != ILocation.S_MISSING)
					knownLocations++;
			}
			
			if (knownLocations > 1) {
				IMessage message = Messages.create(IStatus.WARNING, msgLoc,
						"ContributionEngine.UnsharedEventHandlerWarning", //$NON-NLS-1$
						new Object[] { eventName });
				session.getMessages().add(message);
			}
		}

		for (Iterator iter = eventLocations.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<IEventBinding, ILocation> entry = (Entry<IEventBinding, ILocation>) iter.next();
			IEventBinding binding = entry.getKey();
			String eventName = binding.getEventDescriptor().getDisplayText();
			ILocation location = entry.getValue();
			
			ISourceGenLocation[] slocs = location.getSourceGenLocations();
			for (int i = 0; i < slocs.length; i++) {
				if (!slocs[i].isEventHandler()) {
					IComponentInstance instance = ModelUtils.getComponentInstance(binding.getOwner());
					IMessage message = Messages.create(IStatus.ERROR, msgLoc,
							"ContributionEngine.EventHandlerOverridesError", //$NON-NLS-1$
							new Object[] { eventName, instance.getName(),
							location.getLocationPath(), location.getDir(), location.getFile() });
					session.getMessages().add(message);
					break;
				}
			}
		}
	}

	/**
     * @return Returns the domainRegistry.
     */
    public DomainRegistry getDomainRegistry() {
        return domainRegistry;
    }

    /**
     * 
     */
    public void reset() {
    	componentToLocationsMap.clear();
    	locationToEventBindingMap.clear();
    	eventToLocationMap.clear();
    	eventLocations.clear();
        //context.reset();
        domainRegistry.reset();
        globalDictionary.clear();
        patchContributions = null;
        generateStack.clear();
    }

    /**
     * Get a message corresponding to the current component
     */
    public MessageLocation createMessageLocation() {
        ContributionContext ctx = currentFrame();
        if (ctx != null) {
            return ctx.component.createMessageLocation();
        }
        return new MessageLocation(new Path(".")); //$NON-NLS-1$
    }

	/**
	 * Delete owned locations which are not already defined by
	 * generated contributions.  This ensures we don't end up with
	 * a lot of leftover sections in source.
	 * @param frame
	 */
	void removeDeadOwnedLocations(ContributionContext frame) {
	    Map<String, ILocation> locationsMap = getLocations(frame);
		for (Iterator iter = frame.info.getLocationIdToLocationMap().entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			String locationId = (String) entry.getKey();
			ISourceGenLocation sloc = (ISourceGenLocation) entry.getValue();
			
	        ILocation loc = (ILocation) locationsMap.get(locationId);
	        if (loc == null) { 
	        	// make the location without generating it if it's
	        	// already missing
	        	loc = createLocation(frame, sloc, false); 
	        	
	        	// DO NOT add the location, it's NOT generated
	        	//locationsMap.put(locationId, loc);
	                
	        	if (loc != null) { 
	    			if (loc.isOwned() && loc.getState() == ILocation.S_RESOLVED) {
	    				// mark the location to be eventually deleted
	    				loc.setState(ILocation.S_DELETE);
	    			} else {
	    				// don't stir the waters
	    				if (loc.getState() == ILocation.S_MISSING)
	    					loc.setState(ILocation.S_UNKNOWN);
	    			}
	        	}
	        }
		}
	}

	/**
	 * Get a list of  owned locations which are not already defined by
	 * generated contributions.  Eventually we will re-iterate this
	 * list, search for those never later defined, and delete them.
	 * @param frame
	 */
	private void findDeadOwnedLocations(Collection<ILocation> deadLocations, ContributionContext frame) {
	    Map<String, ILocation> locationsMap = getLocations(frame);
		for (Iterator iter = frame.info.getLocationIdToLocationMap().entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			String locationId = (String) entry.getKey();
			ISourceGenLocation sloc = (ISourceGenLocation) entry.getValue();
			
	        ILocation loc = (ILocation) locationsMap.get(locationId);
	        if (loc == null) { 
	        	// make the location without generating it if it's
	        	// already missing
	        	loc = createLocation(frame, sloc, false);
	        	if (loc != null)
	        		deadLocations.add(loc);
	        }
		}
	}

	/**
	 * Get the global dictionary for use by scripts during
	 * one sourcegen pass.
	 * @return Map
	 */
	public Map getGlobalDictionary() {
		return globalDictionary ;
	}

	public IComponent getCurrentComponent() {
		ContributionContext ctx = currentFrame();
		if (ctx != null)
			return ctx.component;
		else
			return null;
	}

	/**
	 * Examine sourcegen info and provide patches available based on
	 * the version changes.
	 * @param versionProvider
	 * @return
	 */
	public ISourceGenPatchInfo getPatchInfo(ISourceGenComponentVersionProvider versionProvider) {
		Check.checkArg(patchContributions != null);
		
		List<ISourceGenPatch> patches = new ArrayList<ISourceGenPatch>();
		Map<String, Pair<Version, Version>> versionDeltas = versionProvider.getComponentVersionDeltas();
		int serialNumber = 0;
		
		// go through identified patch contributions and 
		// gather those that match known components upgrading within
		// the specified range
		for (Iterator iter = patchContributions.iterator(); iter.hasNext();) {
			PatchContribution patchContrib = (PatchContribution) iter.next();
			String componentId = patchContrib.getContribution().getComponentId();
			if (versionDeltas.containsKey(componentId)) {
				Pair<Version, Version> versions = versionDeltas.get(componentId);
				if (patchContrib.getFromVersion().compareTo(versions.first) >= 0
						&& patchContrib.getToVersion().compareTo(versions.second) <= 0)
					patches.add(new SourceGenPatch(patchContrib, ++serialNumber));
			}
		}

		final ISourceGenPatch[] patchArray = (ISourceGenPatch[]) patches.toArray(new ISourceGenPatch[patches.size()]);
		return new ISourceGenPatchInfo() {

			public ISourceGenPatch[] getSourceGenPatches() {
				return patchArray;
			}
		};
		
	}


}
