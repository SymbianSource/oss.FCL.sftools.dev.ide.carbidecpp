/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


include("renderLibrary.js")

/**
 * Create a contribution that sets a property if its value
 * is not equal to a given default.  
 * @param phase the phase to supply
 * @param indent extra indentation to add (i.e. 1 if inside an "if")
 * @param instanceMemberName the name of the member variable
 * @param propertyValue the current value of the property
 * @param defaultValue the value considered the default
 * @param format a format string for setting the property, where
 *		{0} is substituted with instanceMemberName and 
 *		{1} is substituted with the propertyValue
 * @return a contribution, or null
 */
function createSetPropertyForPhase(contribs, phase, indent, instanceMemberName, 
			propertyValue, defaultValue, format) {
	if (propertyValue != defaultValue) {
		contrib = Engine.createContributionForPhase(phase);
		contrib.indentAdjust(indent);
		contrib.setFormattedText(format, new Array( instanceMemberName, propertyValue) );
		return contrib;
	}
	return null;
}

/**
 * Generate a contribution that sets a property if its value
 * is not equal to a given default.  
 * @param contribs the contribution list
 * @param phase the phase to supply
 * @param indent extra indentation to add (i.e. 1 if inside an "if")
 * @param instanceMemberName the name of the member variable
 * @param propertyValue the current value of the property
 * @param defaultValue the value considered the default
 * @param format a format string for setting the property, where
 *		{0} is substituted with instanceMemberName and 
 *		{1} is substituted with the propertyValue
 * @param checkNull if true, insert check for null instance around sette
 */
function setPropertyForPhase(contribs, phase, indent, instanceMemberName, 
			propertyValue, defaultValue, format, checkNull) {
	contrib = createSetPropertyForPhase(contribs, phase, indent, 
			instanceMemberName, propertyValue, defaultValue, format);
	if (contrib == null)
		return;
	if (checkNull) {
		acontrib = Engine.createContributionForPhase(phase);
		acontrib.indentAdjust(indent);
		acontrib.setFormattedText("if ( {0} )\n", [ instanceMemberName ]);
		contribs.add(acontrib);
		
		acontrib = Engine.createContributionForPhase(phase);
		acontrib.indentAdjust(indent+1);
		acontrib.setText("{\n");
		contribs.add(acontrib);
		
		contribs.add(contrib);
		
		acontrib = Engine.createContributionForPhase(phase);
		acontrib.indentAdjust(indent+1);
		acontrib.setText("}\n");
		contribs.add(acontrib);
	} else {
		contribs.add(contrib);
	}
}

/**
 * Generate a set of contributions to set multiple possible
 * properties.
 * Like #setPropertyForPhase(), where the 5th and later arguments
 * are taken in groups of three as [propertyValue, defaultValue, format].
 */
function setPropertiesForPhase(contribs, phase, indent, instanceMemberName, checkNull) {
	var ourContribStr = "";
	var any = false;
	var innerIndent = indent + (checkNull ? 1 : 0);
	for (var h = 5; h < arguments.length; h += 3) {
		contrib = createSetPropertyForPhase(contribs, phase, innerIndent, instanceMemberName,
			arguments[h], arguments[h+1], arguments[h+2]);
		if (contrib == null)
			continue;
		if (!any && checkNull) {
			acontrib = Engine.createContributionForPhase(phase);
			acontrib.indentAdjust(indent);
			acontrib.setFormattedText("if ( {0} )\n", [ instanceMemberName ]);
			ourContribStr = acontrib.getText();
			
			acontrib = Engine.createContributionForPhase(phase);
			acontrib.indentAdjust(indent+1);
			acontrib.setText("\t" + "{\n");
			ourContribStr = ourContribStr + acontrib.getText();
			
			any	= true;
		}
		ourContribStr = ourContribStr + "\t" + contrib.getText();
	}

	if (any && checkNull) {
		acontrib = Engine.createContributionForPhase(phase);
		acontrib.indentAdjust(indent+1);
		acontrib.setText("\t" + "}\n");
		ourContribStr = ourContribStr + acontrib.getText();
	}
	acontrib = Engine.createContributionForPhase(phase);
	acontrib.indentAdjust(indent);
	acontrib.setText(ourContribStr);
	contribs.add(acontrib);
}

/**
 *	Get the base filename
 *	@param path a path
 */
function getBaseFileName(path) {
	var idx = path.lastIndexOf('/');
	if (idx >= 0)
		base = path.substring(idx+1);
	else {
		idx = path.lastIndexOf('\\');
		if (idx >= 0)
			base = path.substring(idx+1);
		else
			base = path;
	}
	idx = base.lastIndexOf('.');
	if (idx >= 0)
		base = base.substring(0, idx);
		
	return base;
}

/**
 *	Get the bitmap header file
 *	@param path the system MBM/MIF name from the image.bmpfile property
 */
function getBitmapHeaderName(path) {
	var base = getBaseFileName(path);
	base = base + ".mbg";	
	return base.toLowerCase();
}

function addContrib(contribs, phase, loc, indent, text) {
	var c;
	if (phase != null)
		c = Engine.createContributionForPhase(phase);
	else
		c = Engine.createContributionForLocation(loc);
	c.setText(text);
	c.indentAdjust(indent);
	contribs.add(c);
}

function addFormattedContrib(contribs, phase, loc, indent, format, args) {
	var c;
	if (phase != null)
		c = Engine.createContributionForPhase(phase);
	else
		c = Engine.createContributionForLocation(loc);
	c.setFormattedText(format, args);
	c.indentAdjust(indent);
	contribs.add(c);
}

/**
 *	Get the nearest enclosing instance that defines a class.
 */
function getClassHolder(instance) {
	while (instance != null && !instance.properties.className) {
		instance = instance.parent;
	}
	return instance;
}

/**
 *	Get the table of multi-image filenames to literal names
 */
function getFilenameToLiteralTable(instance) {
	var holder = getClassHolder(instance);
	var key = "ImageSupport.filenameToLiteralTable:" + holder;
	var table = Engine.getGlobalDictionary().get(key);
	if (table == null) {
		table = new java.util.HashMap();
		Engine.getGlobalDictionary().put(key, table);
	}
	return table;
}

/**
 *	Get an identifier name from a MBM/MIF filepath
 */
function makeLiteralIdentifier(map, path) {
	// no need to uniquify, as the base mbmdef/mifdef name must be unique as well
	var base = getBaseFileName(path);
	var litname = "K" + base + "File";
	return litname;
}

/**
 *	Prepare a container for sourcegen that involves generating
 *	image properties.  We need to ensure the MBM/MIF filename for
 *	an image is emitted as a constant only once.
 */
function resetImagePropertyState(instance) {
	var classHolder = getClassHolder(instance);
	classHolder.filenameToLiteralTable = new java.util.HashMap();
}

/**
 *	Tell if an image property is set.  If not, it should be ignored.
 */		
function isImagePropertySet(imageProperty) {
	return imageProperty.bmpfile != "" 
		&& imageProperty.bmpid != "";
}

/** 
 *	Tell if we support scaling at all
 */
function isScalingIcons() {
	var version = getComponentVersions();
	if (version.getMajor() >= 3)
		return true;
	if (version.getMajor() == 2 && version.getMinor() >= 8) {
		appUi = getRootModelInstanceOfType("com.nokia.sdt.series60.CAknAppUi");
		if (appUi != null)
			return appUi.properties.layoutAware;
	}
	return false;
}

/**
 *	Set up contributions that set up an image property given
 *	its compound property.  This uses CFbsBitmap* objects and
 *	thus allows for resizing behavior.  Use a separate routine if
 *	setting up multiple images, since the repeated code for SVG setup
 *	can get quite long.
 *	@param contribs the contributions to append to
 *	@param instance instance the image is for (used to find the class)
 *	@param phase primary phase to write to (or null, loc must be set)
 *	@param loc primary location to write to (or null, phase must be set)
 *	@param indent indentation adjust
 *	@param imageProperty the property value
 *	@param aspectOption the appropriate enumeration for aspect ratio handling (or null)
 *	@param bitmapPattern when only a bitmap is set,
 *		a MessageFormat string to be substituted with arguments ([ CFbsBitmap* ])
 *	@param bitmapAndMaskPattern when a bitmap and mask is set,
 *		a MessageFormat string to be substituted with arguments (or null)
 *		([ CFbsBitmap*, CFbsBitmap* ])
 *	@param theSize text representing the TSize argument (e.g. "control->Size()" or "TSize(45, 66)")
 *			or null to use the image's real size
 */
function setupImageFromPropertyViaCFbsBitmap(contribs, instance, phase, loc, indent,
		imageProperty, aspectOption, 
		bitmapPattern, bitmapAndMaskPattern, theSize) {

	var version = getComponentVersions();
	var contrib;

	// moved here to precede system includes of .mbg files	
	var isSVG = imageProperty.editableValue.isSVG();

	if (isScalingIcons() || isSVG) {
		// get icon utilities header
		addContrib(contribs, "MainSystemIncludes", loc, 0,
			"#include <akniconutils.h>\n");
	}
	
	if (imageProperty.bmpfile != "") {
		// make the literal string for the filename
		var table = getFilenameToLiteralTable(instance);
		var litname = table.get(imageProperty.bmpfile);
		if (!litname) {
			litname = makeLiteralIdentifier(table, imageProperty.bmpfile);
			addContrib(contribs, "MainConstants", null, 0,
				"_LIT( " + litname +", \"" + 
					TextUtils.escape(imageProperty.bmpfile, '"') + "\" );\n");
	
			// get the mbg header
			addContrib(contribs, "MainSystemIncludes", null, 0,
				"#include <" + getBitmapHeaderName(imageProperty.bmpfile) + ">\n");
	
			table.put(imageProperty.bmpfile, litname);
		}
	}

	if (!isScalingIcons() && !isSVG) {
		// only unscalable bitmaps allowed, and initialized via CFbsBitmap
		indent++;
		addContrib(contribs, phase, loc, indent,
			"{\n");
		if (bitmapAndMaskPattern != null 
				&& imageProperty.bmpfile != ""
				&& imageProperty.bmpmask != "" 
				&& imageProperty.bmpid != "") {
			addContrib(contribs, phase, loc, indent,
				"CFbsBitmap* bitmap = iEikonEnv->CreateBitmapL( " + litname + ", " + imageProperty.bmpid + " );\n"
				);
			addContrib(contribs, phase, loc, indent,
				"CFbsBitmap* mask = iEikonEnv->CreateBitmapL( " + litname + ", " + imageProperty.bmpmask + " );\n"
				);
			
			addFormattedContrib(contribs, phase, loc, indent,
				bitmapAndMaskPattern, [ "bitmap", "mask" ]);
		}
		else if (bitmapPattern != null) {
			if (imageProperty.bmpfile != "" && imageProperty.bmpid != "") {
				addContrib(contribs, phase, loc, indent,
					"CFbsBitmap* bitmap = iEikonEnv->CreateBitmapL( " + litname + ", " + imageProperty.bmpid + " );\n"
					);
			} else {
				// no image, but at least a bitmap is required
				addContrib(contribs, phase, loc, indent,
					"CFbsBitmap* bitmap = new (ELeave) CFbsBitmap;\n"
					);
			}						
			addFormattedContrib(contribs, phase, loc, indent,
				bitmapPattern, [ "bitmap" ]);
		}

		addContrib(contribs, phase, loc, indent,
			"}\n");
		indent--;
		
	} else {
		// S60 >= 3.0
		
		indent++;

		addContrib(contribs, phase, loc, indent,
			"{\n");

		var sizeArg = theSize;
		if (sizeArg == null)
			sizeArg = "size"; 	// for SVG 

		if (imageProperty.bmpfile != "") {		
			// load the bitmap and/or mask
			addContrib(contribs, phase, loc, indent,
				"CFbsBitmap *bitmap, *mask;\n");
				
			addContrib(contribs, phase, loc, indent,
				"AknIconUtils::CreateIconL( bitmap, mask,\n"+
				"\t\t" + litname + ", " + 
				((bitmapPattern != null && imageProperty.bmpid != "") ?
					imageProperty.bmpid : "-1") + 
					", " +
				((bitmapAndMaskPattern != null && imageProperty.bmpmask != "") ?
					imageProperty.bmpmask : "-1") +
					" );\n");
					
			// prepare to issue two calls needing SVG info
			if (isSVG && theSize == null) {
				addContrib(contribs, phase, loc, indent,
					"TSize size;\n"+
					"AknIconUtils::PreserveIconData( bitmap );\n"+
					//"AknIconUtils::PreserveIconData( mask );\n"+
					"");
			}

		} else {
			// no image, but at least a bitmap WITH CONTENT is required
			addContrib(contribs, phase, loc, indent,
				"CFbsBitmap *bitmap = new (ELeave) CFbsBitmap;\n"+
				"bitmap->Create( TSize( 1, 1), EGray2 );\n");
		}
		
		// set the size
		var aspectText;
		if (aspectOption != null && aspectOption != "")
			aspectText = ", " + aspectOption;
		else
			aspectText = ", EAspectRatioNotPreserved";

		if (imageProperty.bmpfile != "") {		
			if (isSVG && theSize == null) {
				// get the size
				addContrib(contribs, phase, loc, indent,
					"AknIconUtils::GetContentDimensions( bitmap, size );\n");
			}
		}

		if (bitmapAndMaskPattern != null 
				&& imageProperty.bmpfile != ""
				&& imageProperty.bmpmask != "" 
				&& imageProperty.bmpid != "") {

			addContrib(contribs, phase, loc, indent,
				"AknIconUtils::SetSize( bitmap, " + sizeArg + aspectText + " );\n");
			addContrib(contribs, phase, loc, indent,
				"AknIconUtils::SetSize( mask, " + sizeArg + aspectText + " );\n");

			addFormattedContrib(contribs, phase, loc, indent,
				bitmapAndMaskPattern, [ "bitmap", "mask" ]);
					
		} else if (bitmapPattern != null) {
			addContrib(contribs, phase, loc, indent,
				"AknIconUtils::SetSize( bitmap, " + theSize + aspectText + " );\n");

			addFormattedContrib(contribs, phase, loc, indent,
				bitmapPattern, [ "bitmap" ]);
		}

		if (isSVG && theSize == null) {
			addContrib(contribs, phase, loc, indent,
				"AknIconUtils::DestroyIconData( bitmap );\n");
		}
		
		addContrib(contribs, phase, loc, indent,
			"}\n");
		
		indent--;
	}
}

/**
 *	Set up contributions that set up an image property given
 *	its compound property.  This passes arguments to a function
 *	which takes the (TDesc aFileName, TInt bmpid, TInt maskid)
 *	arguments.  Image resizing is not allowed.
 *	@param contribs the contributions to append to
 *	@param instance instance the image is for (used to find the class)
 *	@param phase primary phase to write to (or null, loc must be set)
 *	@param loc primary location to write to (or null, phase must be set)
 *	@param indent indentation adjust
 *	@param imageProperty the compound property providing bmpfile, bmpid, bmpmask
 *	@param bitmapPattern when only a bitmap is set,
 *		a MessageFormat string to be substituted with arguments
 *		([ filename, id ])
 *	@param bitmapAndMaskPattern when a bitmap and mask is set,
 *		a MessageFormat string to be substituted with arguments (or null)
 *		([ filename, id, maskid ])
 */
function setupImageFromPropertyViaTuple(contribs, instance, phase, loc, indent,
		imageProperty,
		bitmapPattern, bitmapAndMaskPattern) {

	if (!isImagePropertySet(imageProperty))
		return;
		
	var version = getComponentVersions();
	var contrib;
	
	// make the literal string for the filename
	var table = getFilenameToLiteralTable(instance);
	var litname = table.get(imageProperty.bmpfile);
	if (!litname) {
		litname = makeLiteralIdentifier(table, imageProperty.bmpfile);
		addContrib(contribs, "MainConstants", null, 0,
			"_LIT( " + litname +", \"" + 
				TextUtils.escape(imageProperty.bmpfile, '"') + "\" );\n");

		// get the mbg header
		addContrib(contribs, "MainSystemIncludes", null, 0,
			"#include <" + getBitmapHeaderName(imageProperty.bmpfile) + ">\n");

		table.put(imageProperty.bmpfile, litname);
	}
		
	if (bitmapAndMaskPattern != null 
			&& imageProperty.bmpmask != "") {
		
		addFormattedContrib(contribs, phase, loc, indent,
			bitmapAndMaskPattern, [ litname, imageProperty.bmpid, imageProperty.bmpmask ]);
	}
	else if (bitmapPattern != null) {
		addFormattedContrib(contribs, phase, loc, indent,
			bitmapPattern, [ litname, imageProperty.bmpid ]);
	}
	
}

/**
 *	Create a method that loads and scales SVG or BMP icons, for use when
 *	multiple icons are loaded at once.  This assumes the function defined in
 *	LoadAndScaleIconL.inc.  For S60 2.8 or newer.
 */
function defineIconLoadingRoutines(contribs, location, className) { 
	// get icon utilities headers.  
	addContrib(contribs, "HeaderIncludes", null, 0,
		"#include <akniconutils.h>\n");
	addContrib(contribs, "HeaderIncludes", null, 0,
		"#include <gulicon.h>\n");
		
	//// needs to be permanent or else it's duplicated
	//addContrib(contribs, "UserHandlers", null, 0,
	addContrib(contribs, "ClassMethods", null, 0,
		"static CGulIcon* LoadAndScaleIconL(\n"+
		"\t\tconst TDesC& aFileName,\n"+
		"\t\tTInt aBitmapId,\n"+
		"\t\tTInt aMaskId,\n"+
		"\t\tTSize* aSize,\n"+
		"\t\tTScaleMode aScaleMode );\n"
	);
	
	// force the location to be instantiated
	addContrib(contribs, null, location, 0, "");
	return;
}

var alignmentMap = {
	"EHLeft+EVTop" : "EHLeftVTop",
	"EHLeft+EVCenter" : "EHLeftVCenter",
	"EHLeft+EVBottom" : "EHLeftVBottom",
	"EHCenter+EVTop" : "EHCenterVTop",
	"EHCenter+EVCenter" : "EHCenterVCenter",
	"EHCenter+EVBottom" : "EHCenterVBottom",
	"EHRight+EVTop" : "EHRightVTop",
	"EHRight+EVCenter" : "EHRightVCenter",
	"EHRight+EVBottom" : "EHRightVBottom"
};

function getTGulAlignmentValue(horiz, vert) {
	return alignmentMap[horiz+"+"+vert];	
}

//////////////////////////

/**
 *	Get the table of unique images in an icon array
 *	@return an ImageList
 */
function getImageList(instance) {
	var key = "srcgenLibrary.ImageList:" + instance.name;
	var table = Engine.getGlobalDictionary().get(key);
	if (table == null) {
		table = new ImageList(instance);
		Engine.getGlobalDictionary().put(key, table);
	}
	return table;
}


/**
 *	This is a prototype used to track arrays of icons.  
 *	Create an instance of the class and populate it with image
 *	properties, then generate contributions that create a
 *	CArrayPtr<CGulIcon> array, then look up indices of image
 *	properties either by index or generated enum.
 *
 *	@param ownerInstance the instance that owns all the images
 */
function ImageList(ownerInstance) {
	this.ownerInstance = ownerInstance;
	this.imageMap = new java.util.LinkedHashMap();
	this.enumSet = new java.util.LinkedHashSet();
	this.imageIdx = 0;
	this.lastEnum = "0";
}

ImageList.prototype.getImagePropertyKey = function(imageProperty) {
	if (isImagePropertySet(imageProperty)) {
		return imageProperty.bmpfile + "|" + imageProperty.bmpid + "|" + imageProperty.bmpmask;
	} else {
		return null;
	}
}

ImageList.prototype.uniquifyEnum = function(enm) {
	var idx = 1;
	var base = enm;
	while (this.enumSet.contains(enm)) {
		enm = base + (++idx);
	}
	return enm;
}

IL_PROPERTY_INDEX = 0;
IL_INDEX_INDEX = 1;
IL_ENUM_INDEX = 2;

/**
 *	Register an image property.  Only adds if unique.
 *	@param instance the instance containing the property
 *	@param property the property ID
 */
ImageList.prototype.registerImageProperty = function(imageProperty) {
	var key = this.getImagePropertyKey(imageProperty);
	if (key != null && !this.imageMap.containsKey(key)) {
		var base = imageProperty.bmpid;
		if (base == "")
			base = imageProperty.bmpmask;
		if (base.substring(0, 4) == "EMbm")
			base = base.substring(4);
		
		var enm = "E" + titleCase(this.ownerInstance.name) + base + "Index";
		enm = this.uniquifyEnum(enm);
		this.enumSet.add(enm);
		this.lastEnum = enm;
			
		this.imageMap.put(key, [imageProperty, this.imageIdx++, enm]);
	}
}

ImageList.prototype.getSystemImageKey = function(bitmap, mask) {
	if (bitmap == null || bitmap.length == 0)
		return null;

	var file = "KAvkonBitmapFile";	// constant in aknconsts.h
	var prefix = "EMbmAvkon";
	var key = file 
		+ "|" 
		+ (bitmap.length > 0 ? prefix + titleCase(bitmap) : "") 
		+ "|" 
		+ (mask.length > 0 ? prefix + titleCase(mask) : "");
	return key;
}

/**
 *	Register an Avkon system image (only added if unique).  
 */
ImageList.prototype.registerAvkonSystemImage = function(bitmap, mask) {
	var key = this.getSystemImageKey(bitmap, mask);
	if (key != null && !this.imageMap.containsKey(key)) {
		var enm = "E" + titleCase(this.ownerInstance.name) 
			+ "Avkon" + titleCase(bitmap != null ? bitmap : mask) 
			+ "Index";
		enm = this.uniquifyEnum(enm);
		this.lastEnum = enm;

		this.imageMap.put(key, [null, this.imageIdx++, enm]);
	}
}

/**
 *	Get the icon index for the given image.
 */
ImageList.prototype.getListIconIndexForProperty = function(imageProperty) {
	var key = this.getImagePropertyKey(imageProperty);
	var value = this.imageMap.get(key);
	if (value != null)
		return value[IL_INDEX_INDEX];
	else
		return -1;
}

/**
 *	Get the enumerator for the given image.
 */
ImageList.prototype.getListIconEnumForProperty = function(imageProperty, getEnum) {
	var key = this.getImagePropertyKey(imageProperty);
	var value = this.imageMap.get(key);
	if (value != null)
		return value[IL_ENUM_INDEX];
	else
		return null;
}

/**
 *	Get the number of images, also the upper bound of the indices
 */
ImageList.prototype.getImageCount = function() {
	return this.imageIdx;
}

ImageList.prototype.getLastEnumerator = function() {
	return this.lastEnum;
}

/**
 *	Generate code to set up a CGulIcon icon array.  Since S60 doesn't
 *	tolerate empty arrays, the generated code allocates the array only if needed,
 *	leaving it on the cleanup stack.  The caller of this code/routine
 *	needs to examine the return value and handle cleaning up appropriately.
 *	@param contribs
 *	@param iconsVar name of array variable to create (must be declared)
 *	@param location the function to add code to
 *	@param mainLocation preferably owned section in main file to add function to if needed
 *	@param instance instance to search upwards for owning class
 *	@param isScaling true if scaling bitmaps and icons, false otherwise
 *  @return true if icon array allocated
 */
ImageList.prototype.generateSetupIconArrayL = function(contribs, iconsVar, location, mainLocation, instance, isScaling) {
	// must not generate a zero-size array or set an empty icon array
	var anyIcons = false;
	
	var iconVar = (iconsVar.match(".*s") ? iconsVar.substring(0, iconsVar.length - 1) : iconsVar + "_instance");
	
	for (var iter = this.imageMap.entrySet().iterator(); iter.hasNext(); ) {
		var entry = iter.next();
		var key = entry.getKey();
		var value = entry.getValue();
		var property = value[IL_PROPERTY_INDEX];
		var index = value[IL_INDEX_INDEX];
		var enm = value[IL_ENUM_INDEX];
		var granularity = this.enumSet.size() > 0? this.enumSet.size() : 1;

		if (!anyIcons) {
			addFormattedContrib(contribs, null, location, 0,
				 "{0} = new (ELeave) CAknIconArray( {1} );\n",
				 [ iconsVar,
				 granularity
				 ] );
		
			addFormattedContrib(contribs, null, location,  0,
					"CleanupStack::PushL( {0} );\n",
					[ iconsVar ]);

			if (isScaling) {
				defineIconLoadingRoutines(contribs, mainLocation, getClassHolder(instance).properties["className"]);
			}

			addFormattedContrib(contribs, null, location,  0,
					"CGulIcon* {0};\n",
					[ iconVar ]);

			anyIcons = true;		
		}
		
		//println("querying " + instance + ", property="+property);

		// This assumes the user doesn't modify the ordering and
		// all enums map evenly to 0...N.
		// We can't use ->InsertL or ->ExtendL since they're stupid.
		var adder = "->AppendL";
		
		// Bug 7621: please ensure that a leave in the AppendL doesn't 
		// leak the allocated image.
		var saveIcon = "CleanupStack::PushL( " + iconVar + " );\n";
		var restoreIcon = "CleanupStack::Pop( " + iconVar + " );\n";
		var addIcon = iconsVar + adder + "( " + iconVar + " );\n";
		var saveAddAndRestore = saveIcon + addIcon + restoreIcon;

		contrib = Engine.createContributionForLocation(location);
		contrib.setText("// for " + enm + "\n");
		contribs.add(contrib);
	
		if (property == null) {
			// system image
			var parts = key.split("\\|");
			
			var contrib = Engine.createContributionForPhase("MainSystemIncludes");
			contrib.setText("#include <aknconsts.h>\n");
			contribs.add(contrib);

			contrib = Engine.createContributionForPhase("MainSystemIncludes");
			contrib.setText("#include <avkon.mbg>\n");
			contribs.add(contrib);
			
			var format;
			
			if (isScaling) {
				if (parts[2] != "") {
					format = iconVar + " = LoadAndScaleIconL(\n\t\t{0}, {1}, {2},\n\t\tNULL, EAspectRatioPreserved );\n"
						+ saveAddAndRestore;
				} else {
					format = iconVar + " = LoadAndScaleIconL(\n\t\t{0}, {1}, -1,\n\t\tNULL, EAspectRatioPreserved );\n"
						+ saveAddAndRestore;
				}
			} else {
				if (parts[2] != "") {
					format = iconVar + " = CEikonEnv::Static()->CreateIconL(\n\t\t{0}, {1}, {2} );\n"
						+ saveAddAndRestore;
				} else {
					format = iconVar + " = CEikonEnv::Static()->CreateIconL(\n\t\t{0}, {1} );\n"
						+ saveAddAndRestore;
				}
			}			
			contrib = Engine.createContributionForLocation(location);
			contrib.setFormattedText(format, parts);
			contribs.add(contrib);
			
		} else {
			if (isScaling) {
				setupImageFromPropertyViaTuple(contribs, instance, null, location, 0,
					property,
					iconVar + " = LoadAndScaleIconL(\n\t\t{0}, {1}, -1,\n\t\tNULL, EAspectRatioPreserved );\n"
						+ saveAddAndRestore,
					iconVar + " = LoadAndScaleIconL(\n\t\t{0}, {1}, {2},\n\t\tNULL, EAspectRatioPreserved );\n"
						+ saveAddAndRestore
				);
			} else {
				setupImageFromPropertyViaTuple(contribs, instance, null, location, 0,
					property, 
					iconVar + " = CEikonEnv::Static()->CreateIconL(\n\t\t{0}, {1} );\n"
						+ saveAddAndRestore,
					iconVar + " = CEikonEnv::Static()->CreateIconL(\n\t\t{0}, {1}, {2} );\n"
						+ saveAddAndRestore);
			}
		}
	}
		
	if (anyIcons) {
		return true;
	}
	else
		return false;
}

/**
 *	Generate text to be substituted into an enum{} declaration.
 */
ImageList.prototype.generateImageListEnums = function() {
	var text = "";
	for (var iter = this.imageMap.entrySet().iterator(); iter.hasNext(); ) {
		var entry = iter.next();
		var index = entry.getValue()[IL_INDEX_INDEX];
		var enm = entry.getValue()[IL_ENUM_INDEX];
		text += enm + " = " + index + ",\n";
	}
	text += "E" + titleCase(this.ownerInstance.name) + "FirstUserImageIndex\n";
	return text;
}

ImageList.prototype.dump = function() {
	println("dump of image list: ");
	for (var iter = this.imageMap.entrySet().iterator(); iter.hasNext(); ) {
		var entry = iter.next();
		var key = entry.getKey();
		var value = entry.getValue();
		var property = value[IL_PROPERTY_INDEX];
		var index = value[IL_INDEX_INDEX];

		println("key="+key+", property="+property+", index="+index);
	}
}
