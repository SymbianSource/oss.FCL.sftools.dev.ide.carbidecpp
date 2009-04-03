			<inline scope="prototype">
include("popupDialogLibrary.js");
${jsObject}.prototype.getStrings = function(instance) {
	this.dialogClassName = getChildAttribute(instance, 0, "dataquery-className"); 
	this.methodType = getChildAttribute(instance, 0, "data-model-cpp-type");
}
			</inline>
			
			<inline>
this.getStrings(instance);			
			</inline>
			
			<template phase="ClassMethods"><![CDATA[
static TInt Run${instanceName$title}L( 
		${this.methodType}& aData, 
		TBool aUseDefaults = ETrue, 
		const TDesC* aOverridePrompt = NULL );
]]>			</template>

			<defineLocation id="METHOD_LOCATION" baseLocation="MAIN_FILE" 
				owned="true"
				location="function(${className}::Run${instanceName$title}L(...))">
				<template><![CDATA[
/**
 * Show the popup dialog for ${instanceName}
 * @param aData in-out ${this.methodType} data
 * @param aUseDefaults TBool use designer default editor data if ETrue
 * @param aOverridePrompt optional override prompt text
 * @return EAknSoftkeyOk (left soft key id) or 0
 */
TInt ${className}::Run${instanceName$title}L( 
		${this.methodType}& aData, 
		TBool aUseDefaults, 
		const TDesC* aOverridePrompt )
	{
	}
]]>				</template>
				
			</defineLocation>
			<template location="METHOD_LOCATION"><![CDATA[
if ( aUseDefaults )
	{
<%		var setDataContribs = Engine.generateChildContributions("AknDataQuerySetData");
		// resolve the contents to this method
		Engine.assignLocationsForPhase(setDataContribs, "SetData", "METHOD_LOCATION")
		contribs.addAll(setDataContribs);
%>	}
<% // EFixedPointLayout case added because CAknFixedPointQueryDialog::NewL does not have default argument
if ((properties.tone != "ENoTone") || (properties.type == "EFixedPointLayout")) { %>
${this.dialogClassName}* queryDialog = ${this.dialogClassName}::NewL( 
		aData, 
		CAknQueryDialog::${properties.tone} );	
<% } else { %>			
${this.dialogClassName}* queryDialog = ${this.dialogClassName}::NewL( aData );	
<% } %>
if ( aOverridePrompt != NULL )
	{
	CleanupStack::PushL( queryDialog );
	queryDialog->SetPromptL( *aOverridePrompt );
	CleanupStack::Pop(); // queryDialog
	}

return queryDialog->ExecuteLD( ${resourceName$upper} );
]]>			</template>

			<template phase="MainSystemIncludes"><![CDATA[
#include <aknquerydialog.h>
]]>			</template>
	
	<inline>
		if (Engine.formMatches(form, [""]))
			contribs.addAll(Engine.generateChildContributions("AknDataQuery"))
	</inline>	
	
