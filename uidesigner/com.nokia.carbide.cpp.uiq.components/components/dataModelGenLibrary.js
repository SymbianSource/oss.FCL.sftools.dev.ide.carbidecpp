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
// Routines to help generate C/C++ data model code

/**
 *	Declare accessors for the given instance, which is
 *	expected to have an attribute data-model-cpp-type representing
 *	the C/C++ type (e.g. "int", "TBool"), which dictates how
 *	its code is generated.  Note that "TDes" is the expected
 *	type for generic string access and underneath "TBuf<...>" is
 *	used for mutable storage; a property "maxLength" should appear on the 
 *	instance to describe the maximum length.
 *	@param contribs the contrib list to append
 *	@param instance the instance
 *	@param dataModelClassName the name of the generated class
 *	@param methodPhase the phase receiving method declarations
 *	@param memberPhase the phase receiving member declarations
 *	@param constantsPhase the phase receiving constant declarations
 *	@param mainPhase the phase receiving the main *.cpp file contributions
 */
function setupDataModelAccessors(contribs, instance, dataModelClassName,  methodPhase, memberPhase, constantsPhase, mainPhase) {
	var type = getDataModelItemType(instance);
	var memberName = "i"+titleCase(instance.name);
	
	var contrib;
	
	var getterName = getDataModelGetterMethodName(instance);
	var setterName = getDataModelSetterMethodName(instance);
	var storageType;
	var paramType;
	var returnType;
	var setterCode;
	if (type != "TDes") {
		storageType = type;
		paramType = type;
		returnType = type;
		setterCode = "\t" + memberName + " = aValue;\n";
	} else {
		var constName = "K" + titleCase(instance.name) + "MaxLength";
		contrib = Engine.createContributionForPhase(constantsPhase);
		contrib.setFormattedText("const int {0} = {1};\n", 
			[ constName,
			instance.properties.maxLength ]);
		contribs.add(contrib);
		paramType = "TDesC";
		returnType = "TDes";
		storageType = "TBuf<" + constName + ">";
		setterCode = 
			"\tif ( aValue.Length() < " + constName + ")\n"+
			"\t\t" + memberName + ".Copy( aValue );\n"+
			"\telse\n"+
			"\t\t" + memberName + ".Copy( aValue.Ptr(), " + constName + ");\n";
	}
	contrib = Engine.createContributionForPhase(memberPhase);
	contrib.setFormattedText("{0} {1};\n", [ storageType, memberName ]);
	contribs.add(contrib);
	
	contrib = Engine.createContributionForPhase(methodPhase);
	contrib.setFormattedText("{0}& {1}();\n", [ returnType, getterName ]);
	contribs.add(contrib);

	contrib = Engine.createContributionForPhase(mainPhase);
	contrib.setFormattedText(
		"{0}& {2}::{1}()\n"+
		"\t'{'\n"+
		"\treturn {3};\n"+
		"\t'}'\n"+
		"\n",
		[ returnType, getterName, dataModelClassName, memberName ]);
	contribs.add(contrib);
	
	contrib = Engine.createContributionForPhase(methodPhase);
	contrib.setFormattedText("void {1}(const {0}& aValue);\n", 
		[ paramType, setterName ]);
	contribs.add(contrib);

	contrib = Engine.createContributionForPhase(mainPhase);
	contrib.setFormattedText(
		"void {2}::{1}(const {0}& aValue)\n"+
		"\t'{'\n"+
		setterCode +
		"\t'}'\n"+
		"\n",
		[ paramType, setterName, dataModelClassName ]);
	contribs.add(contrib);

}

/**
 *	Get the name of the getter method for an item.
 */ 
function getDataModelGetterMethodName(instance) {
	return titleCase(instance.name);
}

/**
 *	Get the name of the setter method for an item.
 */ 
function getDataModelSetterMethodName(instance) {
	return "Set" + titleCase(instance.name);
}

/**
 *	Get the name of the C/C++ parameter type of an item.
 */ 
function getDataModelItemType(instance) {
    if (instance.component != null)
		return instance.component.attributes["data-model-cpp-type"];
	return null;
}

/**
 *	Generate a call that sets the data model item value.
 *	@param contribs the contribution list to append
 *	@param indent indentation adjustment
 *	@param phaseName name of phase to use
 *	@param formName the form to use
 *	@param modelName the name of the model object (or null for this)
 *	@param instance the instance
 *	@param valueName the name of the value to set (expression)
 */
function generateDataModelItemInitializer(contribs, indent, phaseName, formName, modelName, instance, valueName) {
	var setterName = getDataModelSetterMethodName(instance);
	var contrib = Engine.createContributionForPhase(phaseName);
	contrib.setFormattedText("{0}{1}( {2} );\n", 
		[modelName != null ? modelName + "." : "",  
		setterName,
		valueName]);
	contrib.setForm(formName);
	contrib.indentAdjust(indent);
	contribs.add(contrib);
}
