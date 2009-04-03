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

package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.contributions.*;
import com.nokia.sdt.sourcegen.core.*;
import com.nokia.sdt.utils.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The C/C++ domain handler
 * <p>
 * This domain operates through use of the CDT DOM
 *  
 * 
 *
 */
public class CppDomain implements IDomain {

    static final Pattern TABS_TO_SPACES = Pattern.compile("\t"); //$NON-NLS-1$
    static final String BEGIN_SECTION_REGEX = "//\\s*\\[\\[\\["; //$NON-NLS-1$
    static final String END_SECTION_REGEX = "//\\s*\\]\\]\\]"; //$NON-NLS-1$
    static Pattern BEGIN_SECTION_PATTERN = Pattern.compile(BEGIN_SECTION_REGEX);
    static Pattern END_SECTION_PATTERN = Pattern.compile(END_SECTION_REGEX);
    static Pattern BEGIN_OR_END_SECTION_PATTERN = Pattern.compile("(" + BEGIN_SECTION_REGEX + ")|(" + END_SECTION_REGEX + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    static Pattern leadingSlashes = Pattern.compile("/+(.*)"); //$NON-NLS-1$
    static Pattern repeatedSlashes = Pattern.compile("/+"); //$NON-NLS-1$

    public boolean DUMP = false;
    public boolean DUMP_OWNED_CLEANUP = false;
    public boolean DUMP_FULL = false;
    public boolean DUMP_MULTI = false;
    private static int DUMP_CNT = 0;
//    public PrintStream DUMP_OUT = System.out;
	// this is the dir+basename: .txt or #.txt is added based on DUMP_MULTI
    public File DUMP_FILE = new File("c:/contribs"); //$NON-NLS-1$
    public PrintStream DUMP_OUT;
    
    /** Map of full location paths to ILocation */
    private Map locationpathsToLocations;
   
    IProgressMonitor monitor;

    private ILocation root;
    private String cachedIndent;
	private String currentEOL = "\n"; //$NON-NLS-1$
    
	ISourceFormatter formatter;
	ISourceManipulatorProvider sourceManipulatorProvider;
	IProject project;
	
    public CppDomain(IProject project, ISourceFormatter formatter, ISourceManipulatorProvider sourceManipulatorProvider) {
    	Check.checkArg(formatter);
    	Check.checkArg(sourceManipulatorProvider);
        this.locationpathsToLocations = new HashMap(); 
        this.monitor = new NullProgressMonitor();   
        this.root = new RootLocation();
        this.formatter = formatter;
        this.project = project;	// may be null
    	this.cachedIndent = getIndentString(formatter.getSourceFormatting());
    	this.sourceManipulatorProvider = sourceManipulatorProvider;
    	sourceManipulatorProvider.reset();
    }

    private String getIndentString(ISourceFormatting formatting) {
        if (formatting.isUsingTabs())
            return "\t"; //$NON-NLS-1$
        else {
            int spaces = formatting.getIndentSpaces();
            if (spaces <= 8)
                return "        ".substring(8 - spaces); //$NON-NLS-1$
            StringBuffer indent = new StringBuffer();
            while (spaces-- > 0)
                indent.append(' ');
            return indent.toString();
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IDomain#getId()
     */
    public String getId() {
        return "cpp"; //$NON-NLS-1$
    }

    private ILocation makeLocation(ILocation parent, ISourceGenLocation sloc, 
            String dir, String file, String relpath) throws SourceGenException {
        Matcher m;

        String fullpath;
        
        // simplify path to ensure we get better matches
        if (relpath.startsWith("/")) //$NON-NLS-1$
            relpath = relpath.substring(1);

        if (parent != null && parent.getLocationPath() != null)
            fullpath = parent.getLocationPath() + "/" + relpath; //$NON-NLS-1$
        else
            fullpath = relpath;
        
        if (fullpath.startsWith("/")) //$NON-NLS-1$
            fullpath = fullpath.substring(1);

        m = repeatedSlashes.matcher(fullpath);
        fullpath = m.replaceAll("/"); //$NON-NLS-1$
        
        // remove spaces to avoid mismatches on space changes
        fullpath = fullpath.replaceAll("\\s+", ""); //$NON-NLS-1$ //$NON-NLS-2$
        
        String key = "dir(" + dir + ")/file(" + file + ")/" + fullpath; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        
        ILocation loc = (ILocation) locationpathsToLocations.get(key);
        
        if (loc == null) {
        	//System.out.println("key: "+key);
            loc = CppLocation.createLocation(this, parent, sloc, dir, 
                    file, relpath);
            locationpathsToLocations.put(key, loc);
        } else {
            loc.addSourceGenLocation(sloc);
        }
        return loc;
    }
    
	/* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IDomain#makeLocation(java.lang.String)
     */
    public ILocation makeLocation(ISourceGenLocation sloc, String dir, String file, String fullpath) throws SourceGenException {
        return makeLocation(root, sloc, dir, file, fullpath);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IDomain#makeLocation(com.nokia.sdt.sourcegen.contributions.ILocation, com.nokia.sdt.sourcegen.ISourceGenLocation, java.lang.String)
     */
    public ILocation makeLocation(ILocation baseLocation, ISourceGenLocation location, String relPath) throws SourceGenException {
        Check.checkArg(baseLocation instanceof CppLocation);
        return makeLocation((CppLocation) baseLocation, location,
                baseLocation.getDir(), baseLocation.getFile(),
                relPath);
    }
    


    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IDomain#generateSource(com.nokia.sdt.sourcegen.IFileLoader, java.util.List)
     */
    public void generateSource(List contribs)
            throws CoreException {

    	if ((DUMP || DUMP_FULL) && DUMP_OUT == null)
			try {
				File dumpFile = DUMP_MULTI 
						? new File(DUMP_FILE.getAbsolutePath() + (DUMP_CNT++) + ".txt") //$NON-NLS-1$
						: new File(DUMP_FILE.getAbsolutePath() + ".txt"); //$NON-NLS-1$
				DUMP_OUT = new PrintStream(dumpFile);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

        if (DUMP) {
            DUMP_OUT.println("+++++ all project contributions:\n"); //$NON-NLS-1$
            for (Iterator iter = contribs.iterator(); iter.hasNext();) {
                IContribution contrib = (IContribution) iter.next();
                DUMP_OUT.println("\t"+contrib); //$NON-NLS-1$
            }
            DUMP_OUT.println("\n"); //$NON-NLS-1$
        }
        
        // search all the contributions and remove any which are not
        // defining locations and pointing to unfiltered resolved locations
        for (Iterator iter = contribs.iterator(); iter.hasNext();) {
            IContribution contrib = (IContribution) iter.next();
            ILocation loc = contrib.getLocation();
            if (!contrib.definesLocation() 
                    && loc.getState() == ILocation.S_RESOLVED
                    && loc.getContributionFilter() == null) {
            	if (DUMP_FULL) {
            		DUMP_OUT.println("removing existing contribution: " + contrib); //$NON-NLS-1$
            	}
                loc.removeContribution(contrib);
                iter.remove();
            }
        }
       
        // the top-level children of the root should be Cpp locations
        // pointing to files
        for (Iterator iter = root.getChildren().iterator(); iter.hasNext();) {
            ILocation loc = (ILocation) iter.next();
            Check.checkState(loc instanceof CppLocation);
            CppLocation cpploc = (CppLocation) loc;
            if (cpploc.getLocationPath() != null && !cpploc.getLocationPath().equals("")) { //$NON-NLS-1$
                Messages.emit(IMessage.ERROR,
                        loc.getPrimarySourceGenLocation().createMessageLocation(),
                        "CppDomain.ExpectedFileRoot", //$NON-NLS-1$
                        new String[] { 
                            loc.getPrimarySourceGenLocation().getId(), 
                            loc.getLocationPath(),
                            loc.getPrimarySourceGenLocation().getComponentId() });
                continue;
            }
            if (DUMP) DUMP_OUT.println("==> " + cpploc.getPath()); //$NON-NLS-1$
            
            List fileContribs = new ArrayList();
            for (Iterator iterator = contribs.iterator(); iterator.hasNext();) {
                IContribution contrib = (IContribution) iterator.next();
                if (contrib.getLocation().getPath().equals(cpploc.getPath())) {
                    fileContribs.add(contrib);
                    iterator.remove();
                }
            }
//            if (fileContribs.size() > 0)
            // always get a manipulator, otherwise it will look like we are no longer generating the file!
                applyFileContributions(cpploc, fileContribs);
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IDomain#getGeneratedFiles(java.util.List)
     */
    public Collection<IPath> getGeneratedFiles(List contributions) throws CoreException {
    	Collection<IPath> files = new LinkedHashSet<IPath>();
    	
        // the top-level children of the root should be Cpp locations
        // pointing to files
        for (Iterator iter = root.getChildren().iterator(); iter.hasNext();) {
            ILocation loc = (ILocation) iter.next();
            Check.checkState(loc instanceof CppLocation);
            CppLocation cpploc = (CppLocation) loc;
            files.add(cpploc.getPath());
        }

        return files;
    }
    
    /**
     * Reset filters on locations
     * @param loc
     */
    private void resetLocationFilters(ILocation loc) {
        if (loc.getContributionFilter() != null)
            loc.getContributionFilter().reset();
        for (Iterator iter = loc.getChildren().iterator(); iter.hasNext();) {
            ILocation subloc = (ILocation) iter.next();
            resetLocationFilters(subloc);
        }
    }

	/**
     * Apply all the locations for the file represented by the given file location.
     * <p>
     * To apply contributions, we need to know where they go.  In normal builds,
     * most of these locations (files, functions, enums) exist in the source
     * from the beginning.  But to get to the beginning, we have to apply the
     * &lt;defineLocation&gt; contributions and then re-parse in order for
     * those locations to exist. 
     * <p>
     * Thus, the generic algorithm is this:
     * <p>
     * 1) From the outside (generateContributions), find all the contributions
     * that would normally be generated.  This includes the normal templates
     * and inlines.  Also include contributions that define missing or owned
     * locations.  Mark such locations S_QUEUED or S_DELETE.<br>
     * 1a) All locations should be marked S_RESOLVED, S_QUEUED, or S_DELETE.<br>
     * 2) When applying contributions, go file by file.<br>
     * 3) Remove any S_DELETE locations.<br>
     * 4) Iterate the contributions for the file.<br>
     * 5) Ensure the contribution's location exists:<br>
     * 5a) Find the closest parent location which is not S_RESOLVED.<br>
     * 5b) If the location is marked S_QUEUED or S_DELETE,
     * apply the defining contributions, and remove these from the contribs
     * list.  Mark the location S_PENDING.  Skip the original contribution for now.<br>
     * 6) If the contribution's location is marked S_RESOLVED, apply it to
     * the file and remove it from the contribs list.<br>
     * 7) After considering all contributions, if any locations are marked
     * S_PENDING, regenerate all the locations:<br>
     * From the top-down, mark them S_UNKNOWN and requery their
     * states.  This recreates their CodeRanges and marks the locations
     * S_MISSING or S_RESOLVED.  If any locations we expected to be
     * S_RESOLVED are S_MISSING, then issue an error -- the user may have
     * supplied broken contributions.<br>
     * 8) Repeat from (3) until all locations are S_RESOLVED.  At this point
     * there should be a final round of applying contributions to known
     * locations and we're done.
     */
    private void applyFileContributions(CppLocation fileLoc, List contribs) throws CoreException {
        ISourceManipulator srcManip = sourceManipulatorProvider.getSourceManipulator(fileLoc.getPath());
        if (srcManip == null) {
            Messages.emit(IMessage.ERROR,
                    fileLoc.createMessageLocation(),
                    "ContributionEngine.CannotLocatePath", //$NON-NLS-1$
                    new String[] { fileLoc.getPath().toString() });
            return;
        }
        
        // safely modify source, tossing away any changes if we have bugs
        try {
            applyFileContributionsInner(fileLoc, contribs, srcManip);
        } catch (CoreException e) {
            try {
                srcManip.revert();
            } catch (Exception e2) {
                
            }
            throw e;
        } catch (Error e) {
            try {
                srcManip.revert();
            } catch (Exception e2) {
                
            }
            throw e;
        }
    }
    
    private void applyFileContributionsInner(CppLocation fileLoc, List contribs, ISourceManipulator srcManip) throws CoreException {
        resetCachedRanges(fileLoc);
        
        if (DUMP) DUMP_OUT.println("======== " +fileLoc.getPath()); //$NON-NLS-1$
        try {
            if (!srcManip.exists()) {
            	// if there are no contributions, we should abort here.
            	// File only contributions should be minimally realized to create them!
            	// Rest of fix for bug 6021 - without this, apps would create empty view files!
            	if (contribs.isEmpty())
            		return;
            	
            	// ensure file is created first, to get any file template
            	this.currentEOL = formatter.getSourceFormatting().getEOL();
                if (currentEOL.equals("\r")) { //$NON-NLS-1$
                	// this will totally break CDT
                	currentEOL = System.getProperty("line.separator"); //$NON-NLS-1$
                	formatter.getSourceFormatting().setEOL(currentEOL);
                    Messages.emit(IMessage.WARNING,
                    		fileLoc.createMessageLocation(),
                            "CppDomain.IgnoringMacOSLineEndingSetting", //$NON-NLS-1$
                            new String[0]); 
                }
            	
            	srcManip.create();

            	// then load that text next
            }

            // should load only once per sourcegen session --
            // if loading more than once, be sure you called #reset()
            srcManip.load();

        } catch (CoreException e) {
            // obviously, may not yet exist
        }

    	// if there are no contributions, we should abort here.
    	// File only contributions should be minimally realized to create them!
    	// Rest of fix for bug 6021 - without this, apps would create empty view files!
    	if (contribs.isEmpty())
    		return;

        // now discover what line endings to use
    	currentEOL = scanForLineEndings(srcManip.getCurrentText()); 

        if (currentEOL.equals("\r")) { //$NON-NLS-1$
        	// this will totally break CDT
        	IStatus status = Logging.newStatus(SourceGenPlugin.getDefault(), 
        			IStatus.ERROR,
        			MessageFormat.format(
        					Messages.getString("CppDomain.AbortingSaveDueToMacOSLineEndings"), //$NON-NLS-1$
        					new Object[] { fileLoc.getPath().toString() }));
        	throw new CoreException(status);
        }

        
        //rescanLocations(fileLoc, ILocation.S_UNKNOWN);
        
        resetLocationFilters(fileLoc);
        
        if (DUMP) {
            DUMP_OUT.println("+++++ all contributions:\n"); //$NON-NLS-1$
            for (Iterator iter = contribs.iterator(); iter.hasNext();) {
                IContribution contrib = (IContribution) iter.next();
                DUMP_OUT.println("\t"+contrib); //$NON-NLS-1$
            }
            DUMP_OUT.println("\n"); //$NON-NLS-1$
        }
        
        // track every location we have created for this file
        Set<CppLocation> allCreatedLocations = new HashSet<CppLocation>();
        
        do {
            // prepare to change the file
            ITextReplacer textReplacer = srcManip.getTextReplacer();	// needed to ensure wc/tunit/current_text available
            TextChanger changer = new TextChanger(srcManip.getCurrentText());
            
            // track locations created this pass
            List createdLocations = new ArrayList();
            
            // remove any S_DELETE locations
            /*boolean deletedAny =*/ findAndRemoveDeletedLocations(srcManip, allCreatedLocations, changer, fileLoc, srcManip.getPersistedText(), true);

            // Apply contributions while generating locations.
            // This will happen several times since we can't create more
            // than one level of location at a time (need reparse).
            boolean changed = applyContributionsPass(srcManip, changer, contribs, createdLocations, allCreatedLocations);

            // apply the changes to the text
            if (DUMP_FULL) DUMP_OUT.println("--> after apply contribs:\n" + changer.dump(srcManip.getPersistedText())); //$NON-NLS-1$
			changer.apply(textReplacer);

            if (DUMP_FULL || (DUMP && !changed)) {
                DUMP_OUT.println((changed?"delta ":"") + "==>\n" + new String(srcManip.getCurrentText())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                DUMP_OUT.println("==EOF"); //$NON-NLS-1$
            }

            if (changed) {

                // reparse the file from the working buffer
                forceReparse(srcManip, fileLoc);
                allCreatedLocations.addAll(createdLocations);
                
                // now ensure we find the created locations
                for (Iterator iter = createdLocations.iterator(); iter.hasNext();) {
                    ILocation loc = (ILocation) iter.next();
                    if (loc.getState() != ILocation.S_RESOLVED) {
                        Messages.emit(IMessage.ERROR,
                                fileLoc.createMessageLocation(),
                                "CppDomain.PossiblyBadDefiningLocation",  //$NON-NLS-1$
                                new String[] { 
                                    loc.getPrimarySourceGenLocation().getId(),
                                    loc.getLocationPath(), 
                                    loc.getPath().toString(),
                                    loc.getPrimarySourceGenLocation().getComponentId() });
                        // falsely mark it resolved so we can continue working
                        loc.setState(ILocation.S_RESOLVED);
                    }
                }
            }
            else {
            	// If this fails, the location's states are not correct
            	// and contributions are in limbo and will never be applied.
            	//
            	// Only defining contributions or mode-ful contributions are allowed to be left over.
            	for (Iterator iter = contribs.iterator(); iter
						.hasNext();) {
					IContribution contrib = (IContribution) iter.next();
					Check.checkState(contrib.definesLocation() || contrib.getMode() != null);
				}
                break;
            }
            
        } while (contribs.size() > 0);
        
        // reparse the file from the working buffer
        forceReparse(srcManip, fileLoc);

        // if no more changes, remove old owned sections
        if (removeUnreferencedOwnedSections(allCreatedLocations, srcManip, fileLoc)) {
        	// reparse the file from the working buffer
        	forceReparse(srcManip, fileLoc);
        }
    }

	/**
	 * Scan text to determine what the line endings are.
	 * This checks only for the first line, and assumes the
	 * rest are consistent.
	 * @return
	 */
	private String scanForLineEndings(char[] text) {
		if (text != null) {
			for (int idx = 0; idx < text.length; idx++) {
				if (text[idx] == '\r') {
					if (idx + 1 < text.length && text[idx+1] == '\n')
						return "\r\n"; //$NON-NLS-1$
					else
						return "\r"; //$NON-NLS-1$
				} else if (text[idx] == '\n') {
					return "\n"; //$NON-NLS-1$
				}
			}
		}
		// no lines yet...
		return formatter.getSourceFormatting().getEOL();
	}

	static class RangeLocationTuple {
		CodeRange range;
		CppLocation loc;
		public RangeLocationTuple(CodeRange range, CppLocation loc) {
			this.range = range;
			this.loc = loc;
		}
	}
	
	static class RangeMap {
		CodeRange root;
		Map<CodeRange, CppLocation> map = new HashMap<CodeRange, CppLocation>();
		
		RangeMap(CodeRange root) {
			this.root = root;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			List<Map.Entry<CodeRange, CppLocation>> entries = new ArrayList<Map.Entry<CodeRange,CppLocation>>(map.entrySet());
			Collections.sort(entries, new Comparator<Map.Entry<CodeRange, CppLocation>>() {
				public int compare(Entry<CodeRange, CppLocation> o1, Entry<CodeRange, CppLocation> o2) {
					return o1.getKey().getOffset() - o2.getKey().getOffset();
				}
			});
			for (Iterator iter = entries.iterator(); iter.hasNext();) {
				Map.Entry<CodeRange, CppLocation> entry = (Map.Entry<CodeRange, CppLocation>) iter.next();
				builder.append(entry.getKey().getOffset() + " - " + entry.getKey().getEndOffset() + " --> " + entry.getValue() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return builder.toString();
		}
		
		public void put(CodeRange range, CppLocation loc) {
			map.put(range, loc);
		}
		
		/** Get the location containing the given range, or null if the
		 * given range is outside a location.
		 */
		public CppLocation get(int start, int end) {
			
			CodeRange range = root;
			CodeRange[] kids = null;
			LOOP: while (true) {
				kids = range.getChildren();
				for (int i = 0; i < kids.length; i++) {
					if (start == kids[i].getOffset()) {
						// must be a match since two ranges cannot start at the same place
						return map.get(kids[i]);
					} else if (start > kids[i].getOffset()) {
						if (end < kids[i].getEndOffset()) {
							// contained in child
							range = kids[i];
							continue LOOP;
						}
					} else if (start < kids[i].getOffset()) {
						// passed up the range
						if (end < kids[i].getOffset()) {
							// outside a range
							return null;
						} else {
							// overlap (probably error)
							return map.get(range);
						}
					}
				}
				return null;
			}
		}
	}
	
	static class Tuple {
		public Tuple(int end, int commentEnd) {
			this.end = end;
			this.commentEnd = commentEnd;
		}

		int end, commentEnd;
	}
	
	/**
	 * Scan for owned sections in the source and delete them
	 * if no currently generated location maps to it.
	 */
	private void findAndRemoveUnreferencedOwnedSections(TextChanger changer, RangeMap locationEntries, CodeRange parent) {
		int idx = parent.getOffset();
		int endIdx = idx + parent.getLength();
		char[] text = parent.getText();
		while (idx < endIdx) {
			SubString subText = new SubString(text, idx, endIdx - idx);
	        Matcher m = BEGIN_SECTION_PATTERN.matcher(subText);
	        int start = -1, end = -1;
	        if (m.find()) {
	        	int commentStart = m.start() + idx;
	        	start = ParseUtils.skipToNextLine(text, m.end() + idx, endIdx);
	        	int newEnd = start;
	        	
	        	Tuple tuple = scanToEndOfLocation(new SubString(text, start, endIdx - start));
	        	if (tuple != null) {
		        	end = tuple.end + start;
		        	int commentEnd = tuple.commentEnd + start;
		        	
	        		if (end >= start) {
	        			if (DUMP_OWNED_CLEANUP && DUMP_FULL) {
	        				DUMP_OUT.println("matched opening '"  //$NON-NLS-1$
	        						+ new SubString(text, commentStart, start - commentStart) + "' with '" //$NON-NLS-1$
	        						+ new SubString(text, end, commentEnd - end) + "'"); //$NON-NLS-1$
	        			}
	    	        	// now find a created location which matches this start
	    	        	CppLocation loc = locationEntries.get(start, end);
	    	        	if (loc == null) {
	    	        		
	    	        		// If not found, but we found comments, then
	    	        		// this is either an owned something or
	    	        		// a region, which always have comments.
	    	        		String comment = new SubString(text, commentStart, start - commentStart).toString();
	    	        		
	    	        		// keep regions, until we find out how to re-add
	    	        		// missing regions in the right place
	    	        		if (CppRegionSegment.BEGIN_REGION_SECTION_PATTERN.matcher(comment).matches()) {
		    	        		if (CppRegionSegment.BEGIN_OWNED_REGION_SECTION_PATTERN.matcher(comment).matches()) {
	
			    	        		if (DUMP || DUMP_OWNED_CLEANUP)
			    	        			DUMP_OUT.println("need to delete @" + start +": " + new SubString(text, start, end - start)); //$NON-NLS-1$ //$NON-NLS-2$
			    	        		
			    	        		changer.delete(start, end - start);
			    	        		newEnd = commentEnd;
		    	        		} else {
		    	        			// keep this non-owned region...
		    	        			
		    	        			// ... and leave newEnd alone,
		    	        			// to scan inside this region for owned stuff
		    	        		}
	    	        		} else {
		    	        		if (DUMP || DUMP_OWNED_CLEANUP)
		    	        			DUMP_OUT.println("need to delete @" + commentStart +": " + new SubString(text, commentStart, commentEnd - commentStart)); //$NON-NLS-1$ //$NON-NLS-2$
		    	        		
		    	        		changer.delete(commentStart, commentEnd - commentStart);
		    	        		newEnd = commentEnd;
	    	        		}
	    	        		
	    	        	} else {
	    	        		// scan children of location
	    	        		CodeRange sub = loc.getCodeRange();
	    	        		if (sub != null && sub.getOffset() != idx && sub.getEndOffset() != endIdx) {
	    	        			findAndRemoveUnreferencedOwnedSections(changer, locationEntries, sub);
	    	        			newEnd = sub.getEndOffset();
	    	        		}
	    	        	}
	        			
	        		} else {
	        			// no end found -- probably broken source
	        		}
	        	}
	        	
	        	idx = newEnd;
	        } else {
	        	break;
	        }
		}
	}
	

    /**
     * Scan to the end of a location by finding a closing comment.
     * Need to skip nested locations.
     * @return offset within text marking end of location 
	 */
	private Tuple scanToEndOfLocation(CharSequence textSeq) {
		int stackPtr = 1;  // number of starts to match
		int idx = 0;
		int endIdx = textSeq.length();
		while (idx < endIdx) {
			CharSequence subText = textSeq.subSequence(idx, endIdx);
	        Matcher m = BEGIN_OR_END_SECTION_PATTERN.matcher(subText);
	        if (m.find()) {
	        	if (m.group(2) != null) {
	        		stackPtr--;
	        		if (stackPtr == 0) {
	        			// stack now empty
		                int end = m.start() + idx;
		                int commentEnd = ParseUtils.skipToNextLine(subText, m.end()) + idx;
		                // the end of region comment always has two newlines
		                commentEnd = ParseUtils.skipIfNewLine(subText, commentEnd - idx) + idx;
		                // scan backwards to beginning of line
		                while (end > idx && textSeq.charAt(end - 1) != '\n' && textSeq.charAt(end - 1) != '\r')
		                	end--;
		                return new Tuple(end, commentEnd);
	        		}
	        	} else {
	        		stackPtr++;
	        	}
	        	idx = m.end() + idx;
	        } else
	        	break;
		}
		return null;
	}

	/**
     * Remove owned sections for which there are no corresponding
     * created locations.
	 * @param srcManip
	 * @param fileLoc
	 * @param allCreatedLocations
	 */
	private boolean removeUnreferencedOwnedSections(Set<CppLocation> allCreatedLocations, ISourceManipulator srcManip, CppLocation fileLoc) throws CoreException {
		CodeRange fileRange = fileLoc.getCodeRange();
		if (fileRange == null)
			return false;
		
		RangeMap locationEntries = new RangeMap(fileRange);
		if (!findCreatedSections(allCreatedLocations, locationEntries, fileLoc, fileRange))
			return false;
		
		if (!fileRange.validateChildren()) {
            Messages.emit(IMessage.ERROR,
                    fileLoc.createMessageLocation(),
                    "CppDomain.CodeRangesOverlap", //$NON-NLS-1$
                    new Object[0]);  //$NON-NLS-1$
            return false;
		}
		
		if (DUMP_OWNED_CLEANUP)
			DUMP_OUT.println(locationEntries);
		
		TextChanger changer = new TextChanger(srcManip.getCurrentText());

		findAndRemoveUnreferencedOwnedSections(changer, locationEntries, fileRange);
		if (changer.changed()) {
			if (DUMP_OWNED_CLEANUP)
				DUMP_OUT.println(changer);
			changer.apply(srcManip.getTextReplacer());
			return true;
		}
		
		return false;
	}

	/**
	 * Get a tree of the created sections.
	 * @param allCreatedLocations
	 * @param locationEntries 
	 * @param range
	 * @return true: found created sections successfully 
	 */
	private boolean findCreatedSections(Set<CppLocation> allCreatedLocations, RangeMap locationEntries, CppLocation loc, CodeRange range) {
		for (Iterator iter = loc.getChildren().iterator(); iter.hasNext();) {
			CppLocation subloc = (CppLocation) iter.next();
			CodeRange subRange = subloc.getCodeRange();
			if (subRange != null) {
				CodeRange subRangeCopy = subRange.clone();
				range.addChildRange(subRangeCopy);
				locationEntries.put(subRangeCopy, subloc);
				if (!findCreatedSections(allCreatedLocations, locationEntries, subloc, subRangeCopy))
					return false;
			} else {
				if (allCreatedLocations.contains(subloc)) {
					// Hmm, we should have seen that location -- probably a syntax error in the contribution.
					// Don't delete anything!
                    Messages.emit(IMessage.ERROR,
                            subloc.createMessageLocation(),
                            "CppDomain.PossiblyBadDefiningLocation",  //$NON-NLS-1$
                            new String[] { 
                                subloc.getPrimarySourceGenLocation().getId(),
                                subloc.getLocationPath(), 
                                subloc.getPath().toString(),
                                subloc.getPrimarySourceGenLocation().getComponentId() });
					
					return false;
				}
			}
		}
		return true;
	}

	private boolean applyContribution(TextChanger changer, IContribution contrib) {
		boolean changed = false;
		
        // is the contribution filtered out?
		CppLocation loc = (CppLocation) contrib.getLocation();
        if (loc.filterContribution(contrib)) {
            changer.insert(loc.getInsertOffset(), makeIndentingInserter(loc, contrib));
            changed = true;
        } else {
        	if (DUMP)
        		DUMP_OUT.println("Filtered out contribution: " +contrib); //$NON-NLS-1$
        }
        
        loc.removeContribution(contrib);
		return changed;
	}

	/**
     * Apply contributions whose locations are fully resolved,
     * else generate the locations
     * @param sourceManip
     * @param changer
     * @param contribs
     * @param createdLocations
     * @param allCreatedLocations 
     * @return true: made changes to location states
     */
    private boolean applyContributionsPass(ISourceManipulator sourceManip, TextChanger changer, List contribs, List createdLocations, Set allCreatedLocations) throws CoreException {
        boolean changed = false;
        
        // Scan copy so we can modify contribs
        List scanContribs = new ArrayList(contribs);
        for (Iterator iter = scanContribs.iterator(); iter.hasNext();) {
            final IContribution contrib = (IContribution) iter.next();

            // ignore these here
            if (contrib.definesLocation())
                continue;

            CppLocation loc = (CppLocation) contrib.getLocation();
            
            if (loc.getState() != ILocation.S_RESOLVED) {
                // Try to generate location or parent
                
                while (loc.getParent() instanceof CppLocation 
                		&& loc.getParent().getState() != ILocation.S_RESOLVED) 
                    loc = (CppLocation) loc.getParent();
                
                // loc is inside a resolved location
                if (loc.getState() == ILocation.S_QUEUED 
                        || loc.getState() == ILocation.S_DELETED_QUEUED) {
                    
                    if (DUMP)
                        DUMP_OUT.println("=== in order to generate '" + contrib.getText() + "', need to generate " + loc.getLocationPath()); //$NON-NLS-1$ //$NON-NLS-2$
                    
                    createQueuedLocation(changer, (CppLocation) loc, contrib, contribs);
                    createdLocations.add(loc);
                    
                    changed = true;
                }
                
                // retain this contribution for the next pass
                continue;
            } else {
                // Location exists: apply contribution

            	applyContribution(changer, contrib);
                contribs.remove(contrib);
            }
        }
        
        return changed;
    }

    /**
     * Reset any cached information in the location and children
     * @param loc
     */
    private void resetCachedRanges(CppLocation loc) {
        loc.cachedRange = false;
        List subLocs = loc.getChildren();
        for (Iterator iter = subLocs.iterator(); iter.hasNext();) {
            CppLocation subLoc = (CppLocation) iter.next();
            resetCachedRanges(subLoc);
        }
        
    }

    /**
     * Find any locations marked S_DELETE, which means they are owned
     * locations whose contents should be removed before reapplying
     * all the defining contributions.
     * @param srcManip 
     * @param changer
     * @param loc starting location
     * @param topLevel true: searching for top of a deleted subtree, false: handling children of deleted subtree
     */
    private boolean findAndRemoveDeletedLocations(ISourceManipulator srcManip, 
            Set<CppLocation> allCreatedLocations,
            TextChanger changer, CppLocation loc, char[] text, boolean topLevel) {
        boolean deletedAny = false;
        if (loc.getState() == ILocation.S_TO_REPLACE) {
        	// replace the location by deleting it and queueing
        	// it for regeneration.
        	
            // only emit a deletion at the top level;
            // deleting the parent implicitly deletes the children
            if (topLevel) {
                CodeRange range = loc.getCodeRange();
                if (range == null) {
                    if (loc.getParent() instanceof CppLocation 
                            && !allCreatedLocations.contains(loc.getParent())
                            && !loc.getPrimarySourceGenLocation().isVariable()) {
                        Messages.emit(IMessage.WARNING,
                                loc.createMessageLocation(),
                                "CppDomain.MissingGeneratedLocationAlert", //$NON-NLS-1$
                                new String[] { loc.getPrimarySourceGenLocation().getId(), loc.getLocationPath(), 
                                    loc.getPath().toString() });
                    }
                    loc.setState(ILocation.S_QUEUED);
                } else {
                    changer.delete(range.getOffset(), range.getLength());
                    loc.setState(ILocation.S_DELETED_QUEUED);
                }
            }
            else {
                loc.setState(ILocation.S_QUEUED);
            }
            
            // recursively mark as queued the contained locations
            List subLocs = loc.getChildren();
            for (Iterator iter = subLocs.iterator(); iter.hasNext();) {
                CppLocation subLoc = (CppLocation) iter.next();
                findAndRemoveDeletedLocations(srcManip, allCreatedLocations, changer, subLoc, text, false);
            }
            
            deletedAny = true;
            
        } else if (loc.getState() == ILocation.S_DELETE) {
        	// delete and do not recreate the location,
        	// but only do anything at the root of a deleted tree.
        	if (DUMP)
        		DUMP_OUT.println("Deleting location: " + loc); //$NON-NLS-1$
            if (topLevel) {
                CodeRange range = loc.getCodeRange();
                if (range != null) {
                    changer.delete(range.getOffset(), range.getLength());
                    loc.setState(ILocation.S_MISSING);
                }
            }
            else {
                loc.setState(ILocation.S_MISSING);
            }
            
            // recursively mark as to-delete the contained locations
            List subLocs = loc.getChildren();
            for (Iterator iter = subLocs.iterator(); iter.hasNext();) {
                CppLocation subLoc = (CppLocation) iter.next();
                if (subLoc.getState() != ILocation.S_MISSING) {
                	subLoc.setState(ILocation.S_DELETE);
                	findAndRemoveDeletedLocations(srcManip, allCreatedLocations, changer, subLoc, text, false);
                }
            }
            
            deletedAny = true;
            
        } else if (loc.getState() == ILocation.S_RESOLVED) {            
            // recurse to look for other changes
            List subLocs = loc.getChildren();
            boolean changed = false;
            for (Iterator iter = subLocs.iterator(); iter.hasNext();) {
                CppLocation subLoc = (CppLocation) iter.next();
                changed |= findAndRemoveDeletedLocations(srcManip, allCreatedLocations, changer, subLoc, text, topLevel);
            }
            deletedAny = changed;
        }
        
        if (deletedAny && DUMP_FULL) DUMP_OUT.println("--> after delete:\n" + changer.dump(srcManip.getPersistedText())); //$NON-NLS-1$

        return deletedAny;
    }

    /**
     * Apply the defining contributions for the given location marked 
     * S_QUEUED (new) or S_DELETED_QUEUED (existing and rewritten, i.e. owned),
     * and mark it S_PENDING.
     * @param loc location to recreate 
     * @param contribs all contributions, including ones that define locations;
     * updated to remove contributions applied
     */
    private void createQueuedLocation(TextChanger changer, final CppLocation loc, IContribution theContrib, List contribs) {
        Check.checkState(loc.getState() == ILocation.S_QUEUED 
        		|| loc.getState() == ILocation.S_DELETED_QUEUED);
        
        List locContribs = loc.getContributions();

        int insertOffset = loc.getInsertOffset();
        
        // generated comment added only for new locations;
        // in existing locations, the innards were deleted but not the comments
        if (loc.getState() == ILocation.S_QUEUED) {
            String comment = loc.getComment(loc.isOwned(), true);
            // emit head comment
            if (comment.length() > 0)
                changer.insert(insertOffset, makeIndentingInserter(loc.getParent(), 
                        comment));
        }

        for (Iterator iter = locContribs.iterator(); iter.hasNext();) {
            IContribution contrib = (IContribution) iter.next();
            if (contrib.definesLocation()) {
            	
            	ILocation contribLoc = contrib.getLocation();
            	
            	if (contribLoc.filterContribution(contrib)) {
	                ITextInserter inserter;
	
	                // When defining a location, the contributions are placed at
	                // their own location by convention, but they really exist
	                // in their parent; thus we adjust the indentation according 
	                // to the parent.
	                if (contrib.getLocation() == loc)
	                    inserter = makeIndentingInserter(loc.getParent(), contrib);
	                else
	                    inserter = makeIndentingInserter(loc, contrib);
	                
	                changer.insert(insertOffset, inserter);
            	}
            	
                contribs.remove(contrib);
                loc.removeContribution(contrib);
            }
        }

        if (loc.getState() == ILocation.S_QUEUED) {
            String comment = loc.getComment(loc.isOwned(), false);
            // emit tail comment
            if (comment.length() > 0)
                changer.insert(insertOffset, makeIndentingInserter(loc.getParent(), 
                        comment));
        }
        
        loc.setState(ILocation.S_PENDING);

        if (DUMP_FULL)
            DUMP_OUT.println("location " + loc + " changed to S_PENDING"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Reparse the source, essentially by resetting the state on all
     * the locations
     */
    private void forceReparse(ISourceManipulator sourceManip, CppLocation fileLoc) throws CoreException {
        resetCachedRanges(fileLoc);

        fileLoc.setState(ILocation.S_UNKNOWN);
        sourceManip.commit();
        resetLocations(fileLoc);
    }
    
    private void resetLocations(CppLocation loc) {
        if (loc.getState() == ILocation.S_PENDING)
            loc.setState(ILocation.S_UNKNOWN);
        loc.insideLine = false;
        List subLocs = loc.getChildren();
        for (Iterator iter = subLocs.iterator(); iter.hasNext();) {
            CppLocation subLoc = (CppLocation) iter.next();
            resetLocations(subLoc);
        }
    }

    /**
     * Format the given line as it would appear in the contribution, i.e.
     * insert the appropriate indentation.
     * @param contrib
     * @param line
     * @return
     */
    /*package*/ String getIndentedLine(final ILocation loc, String origText, int indentAdjust, final boolean continuation) {
        if (origText == null || origText.length() == 0)
            return null;

        // make the indent text
        StringBuffer buf = new StringBuffer();
        int indentLevel = loc.getIndentLevel() + indentAdjust;
        for (int i = 0; i < indentLevel; i++)
            buf.append("\t"); //$NON-NLS-1$
        
        String indent = buf.toString();

        // split the contribution into lines and prepend the
        // indentation to each line
        buf.setLength(0);
        int idx;
        char ch;
        
        boolean doIndent = !continuation
        	|| (!(loc instanceof CppLocation)
        			|| !((CppLocation) loc).insideLine);
        
        for (idx = 0; idx < origText.length(); idx++) {
            ch = origText.charAt(idx);
            if (doIndent) {
                buf.append(indent);
                doIndent = false;
            }
            buf.append(ch);
            if (ch == '\n') {
                doIndent = true;
            }
        }

        if (loc instanceof CppLocation) {
			((CppLocation) loc).insideLine = !doIndent;
		}
        
        String text = buf.toString();
        
        ISourceFormatting formatting = formatter.getSourceFormatting();
		if (formatting == null || !formatting.isUsingTabs()) {
			text = TABS_TO_SPACES.matcher(text).replaceAll(cachedIndent);
        }
		text = TextUtils.canonicalizeNewlines(text, currentEOL);
        
        return text;

    }
    

    /**
     * Get an inserter for the contribution text, indented at the
     * proper level according to loc's position within the source file.
     * <p>
     * Note: contribution text must be flush with the left margin
     * as a basic prerequisite. 
     * @param loc location
     * @param origText text
     * @param indentAdjust adjustment to indentation
     * @return an inserter
     */
    private ITextInserter makeIndentingInserter(final ILocation loc, String origText, int indentAdjust, final boolean continuation) {
    	final String text = getIndentedLine(loc, origText, indentAdjust, continuation);
        if (text == null)
            return ITextInserter.NULL_INSERTER;
        
        return new ITextInserter() {
            public String toString() {
                return loc.getLocationPath() + ": '" + TextUtils.formatForDump(TextUtils.escape(text, '"')) + "'"; //$NON-NLS-1$ //$NON-NLS-2$
            }
            public String getInsertText() {
                return text;
            }
        };

    }
    /*
    private ITextInserter makeIndentingInserter(final ILocation loc, String origText, int indentAdjust, final boolean continuation) {
        if (origText == null || origText.length() == 0)
            return ITextInserter.NULL_INSERTER;

        // make the indent text
        StringBuffer buf = new StringBuffer();
        int indentLevel = loc.getIndentLevel() + indentAdjust;
        for (int i = 0; i < indentLevel; i++)
            buf.append("\t"); //$NON-NLS-1$
        
        String indent = buf.toString();

        // split the contribution into lines and prepend the
        // indentation to each line
        buf.setLength(0);
        int idx;
        char ch;
        
        boolean doIndent = !continuation
        	|| (!(loc instanceof CppLocation)
        			|| !((CppLocation) loc).insideLine);
        
        for (idx = 0; idx < origText.length(); idx++) {
            ch = origText.charAt(idx);
            if (doIndent) {
                buf.append(indent);
                doIndent = false;
            }
            buf.append(ch);
            if (ch == '\n') {
                doIndent = true;
            }
        }

        if (loc instanceof CppLocation) {
			((CppLocation) loc).insideLine = !doIndent;
		}
        
        origText = buf.toString();
        
        ISourceFormatting formatting = provider != null ? provider.getSourceFormatter().getSourceFormatting() : null;
		if (formatting == null || !formatting.isUsingTabs()) {
            origText = TABS_TO_SPACES.matcher(origText).replaceAll(cachedIndent);
        }
		origText = TextUtils.canonicalizeNewlines(origText, currentEOL);
        final String text = origText;
        
        return new ITextInserter() {
            public String toString() {
                return loc.getLocationPath() + ": '" + TextUtils.formatForDump(TextUtils.escape(text, '"')) + "'"; //$NON-NLS-1$ //$NON-NLS-2$
            }
            public String getInsertText() {
                return text;
            }
        };
    }
*/
    
    /**
     * Get an inserter for the contribution text, indented at the
     * proper level according to loc's position within the source file.
     * <p>
     * Note: contribution text must be flush with the left margin
     * as a basic prerequisite. 
     * @param loc
     * @param origText
     * @return an inserter
     */
    private ITextInserter makeIndentingInserter(final ILocation loc, String origText) {
        return makeIndentingInserter(loc, origText, 0, false);
    }
    
    /**
     * Get an inserter for the contribution, indented at the
     * proper level according to loc's position within the source file.
     * <p>
     * Note: contribution text must be flush with the left margin
     * as a basic prerequisite. 
     * @param loc
     * @param contrib
     * @return an inserter
     */
    private ITextInserter makeIndentingInserter(final ILocation loc, IContribution contrib) {
        String origText = contrib.getText();
        return makeIndentingInserter(loc, origText, contrib.getIndentAdjust(), contrib.isContinuation());
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IDomain#reset()
     */
    public void reset() {
        root = new RootLocation();
        locationpathsToLocations.clear();
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.contributions.IDomain#encodeLocationToEventHandlerSymbol(com.nokia.sdt.sourcegen.contributions.ILocation)
	 */
	public String encodeLocationToEventHandlerSymbol(ILocation loc) {
		return "cpp@" //$NON-NLS-1$ 
		+ loc.getDir() 
		+ "//" //$NON-NLS-1$
		+ loc.getFile() 
		+ "/" //$NON-NLS-1$
		+ loc.getLocationPath(); //$NON-NLS-1$
	}

	public SourceLocation decodeEventHandlerSymbol(String symbolInfo) throws SourceGenException {
		SourceLocation loc = new SourceLocation();
		Pattern pattern = Pattern.compile("cpp@(.+?)//(.+?)/(.*)"); //$NON-NLS-1$
		Matcher matcher = pattern.matcher(symbolInfo);
		if (!matcher.matches()) {
			throw new SourceGenException("CppDomain.BadEventSymbol", //$NON-NLS-1$
					new Object[] { symbolInfo });
		}
		
		String dir = matcher.group(1);
		String file = matcher.group(2);
		String locationPath = matcher.group(3);
		
		loc.locationPath = locationPath;
		
		CppLocation location = CppLocation.createLocation(this, dir, file, locationPath);
		if (location.getState() != ILocation.S_RESOLVED) {
			throw new SourceGenException(
							"CppDomain.CannotLocateEventHandler", //$NON-NLS-1$
							new Object[] { location.getLocationPath(), location.getPath() });
		}
		
		CodeRange range = location.getCodeRange();
		Check.checkState(range != null);
	
		loc.filePath = location.getPath();
		
		loc.offset = range.getOffset();
		loc.length = range.getLength();
		loc.insertOffset = ParseUtils.getInsertPositionAfterStart(range.text, loc.offset, loc.length, '{');
		
		return loc;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.contributions.IDomain#createPatchRefactoringEngine(com.nokia.sdt.sourcegen.contributions.PatchContext)
	 */
	public IDomainPatchRefactoringEngine createPatchRefactoringEngine(PatchContext patchContext) {
		return new CppPatchRefactoringEngine(this, patchContext, project, sourceManipulatorProvider);
	}
	
}
