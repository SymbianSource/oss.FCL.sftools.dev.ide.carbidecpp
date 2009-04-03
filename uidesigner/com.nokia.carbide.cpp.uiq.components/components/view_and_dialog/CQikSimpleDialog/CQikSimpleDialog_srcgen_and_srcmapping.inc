<sourceGen forms="RootAppUi">

	<inline scope="file">
			include("../../srcgenLibrary.js")
			
			function getDialogConfigurationsResourceName(instance) {
				if (resourceName != null)
					return resourceName.toUpperCase();
			}
	</inline>

<!--	<inline scope="prototype">
		 ${jsObject}.prototype.findOrCreateSimpleDialogUidConstant = function(instance) {

// the algorithm can deal with either CAknView or AvkonViewReference

return Engine.findOrCreateEnumeratorForAlgorithm(instance, ".", 

"com.nokia.sdt.component.symbian.NAME_ALG_DIALOG_UID");

}
		</inline>-->

	<useTemplateGroup ids="*"/>			
<!--
		<templateGroup id="HrhInclude">			
			<template id="AdditionalUserIncludes3" location="VIEWDIALOGBASE_SOURCE_OWNED_USER_INCLUDES">
<![CDATA[#include "${instanceName}.hrh"]]>
			</template>
		</templateGroup>
-->
	<templateGroup id="CommonMethodsSimpleDialog">

		<expandMacro name="GenerateMethod"
			DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
			FunctionName="RunDlgLD"
			FunctionArgs=""
			ReturnType="TInt"
			FunctionLocationId="SIMPLEDIALOG_CLASS_RUNDLGLD"
			IsOwned="false"
			IsStatic="true"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}">
			<expandArgument name="FunctionComment">
/**
Creates, constructs, execute and destroys the dialog.
*/
			</expandArgument>
			<expandArgument name="FunctionBody">
${className}* self = new (ELeave) ${className}();
CleanupStack::PushL(self);
self->ConstructL();
CleanupStack::Pop();
self->PrepareLC(${getDialogConfigurationsResourceName(instance)});
self->PreRun();
return self->RunLD();
			</expandArgument>
		</expandMacro>

		<expandMacro name="GenerateMethod"
			DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE"
			FunctionName="${className}"
			FunctionArgs=""
			ReturnType=""
			FunctionLocationId="SIMPLEDIALOG_CLASS_CONSTRUCTOR"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}">
			<expandArgument name="FunctionComment">
/**
Constructor for the dialog.
*/
			</expandArgument>
			<expandArgument name="Initializers">
iCmdManager(CQikCommandManager::Static(*iCoeEnv)) 
			</expandArgument>
			<expandArgument name="FunctionBody">

			</expandArgument>
		</expandMacro>

		<expandMacro name="GenerateMethodWithOwnedBody"
			DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
			FunctionName="~${className}"
			FunctionArgs=""
			ReturnType=""
			FunctionLocationId="SIMPLEDIALOG_CLASS_DESTRUCTOR"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}">
			<expandArgument name="FunctionComment">
/**
 * Destructor for the dialog
 */
			</expandArgument>
			<expandArgument name="StartFunctionBody">
iControlEventDispatch.Reset();
iCommandEventDispatch.Reset();					
			</expandArgument>
		</expandMacro>

		<expandMacro name="GenerateMethodWithOwnedBody"
			DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE"
			FunctionName="ConstructL"
			FunctionArgs=""
			FunctionLocationId="SIMPLEDIALOG_CLASS_CONSTRUCTL"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}">
			<expandArgument name="FunctionComment">
/**
2nd stage construction of the dialog.
*/
			</expandArgument>
			<expandArgument name="StartFunctionBody">

			</expandArgument>
		</expandMacro>

		<expandMacro name="GenerateMethodWithOwnedBody"
			DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
			FunctionName="HandleCommandL"
			FunctionArgs="CQikCommand&amp; aCommand"
			FunctionLocationId="SIMPLEDIALOG_CLASS_HANDLECOMMANDL"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}"
			OwnedRegionLocationId="SIMPLEDIALOG_CLASS_HANDLECOMMANDL_BODY">
			<expandArgument name="FunctionComment">
/**
Handles all commands in the dialog.
Called by the UI framework when a command has been issued.
The command Ids are defined in the .hrh file.

@param aCommand The command to be executed
*/
			</expandArgument>
			<expandArgument name="FunctionBody">
<![CDATA[
	for (int i = 0; i < iCommandEventDispatch.Count(); i++)
		{
		const TCommandEventDispatch& currEntry = iCommandEventDispatch[i];
		if ( currEntry.id == aCommand.Id() )
			{
			( this->*currEntry.handler )( aCommand );
			break;
			}
		}
		
	${className}Base::HandleCommandL(aCommand);		
]]>	
			</expandArgument>
		</expandMacro>

		<expandMacro name="GenerateMethodWithOwnedBody"
			DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
			FunctionName="HandleControlEventL"
			FunctionArgs="CCoeControl* aControl, TCoeEvent aEventType"
			FunctionLocationId="SIMPLEDIALOG_CLASS_HANDLECONTROLEVENTL"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}"
			OwnedRegionLocationId="SIMPLEDIALOG_CLASS_HANDLECONTROLEVENTL_BODY">
			<expandArgument name="FunctionComment">
/**
Handles an event from an observed control. Used to get event when the edwin 
has been changed. If the edwin contains any text the login command shall be
available.
*/
			</expandArgument>
			<expandArgument name="StartFunctionBody">
CQikSimpleDialog::HandleControlEventL($(FunctionArgs::as-function-call-args::add-spaces-unless-empty));
			</expandArgument>
			<expandArgument name="EndFunctionBody">
<![CDATA[
	for (int i = 0; i < iControlEventDispatch.Count(); i++)
		{
		const TControlEventDispatch& currEntry = iControlEventDispatch[i];
		if ( currEntry.src == aControl && currEntry.event == aEventType )
			{
			( this->*currEntry.handler )( aControl, aEventType );
			break;
			}
		}
]]>						
			</expandArgument>
		</expandMacro>

		<expandMacro name="GenerateMethodWithOwnedBody"
			DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE"
			FunctionName="InitializeControlsL"
			FunctionArgs=""
			ReturnType="void"
			FunctionLocationId="SIMPLEDIALOG_CLASS_INITIALIZECONTROLSL"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}"
			OwnedRegionLocationId="SIMPLEDIALOG_CLASS_INITIALIZECONTROLSL_BODY">
			<expandArgument name="FunctionComment">
/**
 *	Initialize each control upon creation.
 */				
			</expandArgument>
			<expandArgument name="FunctionBody">
iControlEventDispatch.Reset();
			</expandArgument>
		</expandMacro>
				
		<expandMacro name="GenerateMethodWithOwnedBody"
			DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE"
			FunctionName="AddControlEventHandlerL"
			FunctionArgs="CCoeControl* aControl, TCoeEvent aEvent, ControlEventHandler aHandler"
			ReturnType="void"
			FunctionLocationId="SIMPLEDIALOG_CLASS_ADDCONTROLEVENTHANDLERL"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}"
			OwnedRegionLocationId="SIMPLEDIALOG_CLASS_ADDCONTROLEVENTHANDLERL_BODY">
			<expandArgument name="FunctionComment">
/** 
 * Helper function to register MCoeControlObserver event handlers
 */
			</expandArgument>
			<expandArgument name="FunctionBody">
<![CDATA[
	TControlEventDispatch entry;
	entry.src = aControl;
	entry.event = aEvent;
	entry.handler = aHandler;
	iControlEventDispatch.AppendL( entry );
]]>
			</expandArgument>
		</expandMacro>

		<expandMacro name="GenerateMethodWithOwnedBody"
			DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE"
			FunctionName="AddCommandEventHandlerL"
			FunctionArgs="TInt aId,CommandEventHandler aHandler"
			ReturnType="void"
			FunctionLocationId="SIMPLEDIALOG_CLASS_ADDCOMMANDEVENTHANDLERL"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}"
			OwnedRegionLocationId="SIMPLEDIALOG_CLASS_ADDCOMMANDEVENTHANDLERL_BODY">
			<expandArgument name="FunctionComment">
/** 
 * Helper function to register command event handlers
 */
			</expandArgument>
			<expandArgument name="FunctionBody">
<![CDATA[
	TCommandEventDispatch entry;
	entry.id = aId;
	entry.handler = aHandler;
	iCommandEventDispatch.AppendL( entry );
]]>
			</expandArgument>
		</expandMacro>


		<expandMacro name="GenerateMethodWithOwnedBody"
			DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED"
			FunctionName="PostLayoutDynInitL"
			FunctionArgs=""
			ReturnType="void"
			FunctionLocationId="SIMPLEDIALOG_CLASS_POSTLAYOUTDYNINITL"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}">
			<expandArgument name="FunctionComment">
/**
 * Called when the dialog has been layed out 
 */
			</expandArgument>
			<expandArgument name="StartFunctionBody">
InitializeControlsL();		
			</expandArgument>
		</expandMacro>

		
		<template id="CommandManagerReference" location="VIEWDIALOGBASE_CLASS_PRIVATE_OWNED_INSTANCE_VARIABLES">
<![CDATA[
/** Reference to the command manager that are used to change commands with. */
CQikCommandManager& iCmdManager;
]]>
		</template>
		
		<template phase="Initialize" form="RootAppUi_ParentView">
<![CDATA[
${instance.className}::RunDlgLD();
]]>		
		</template>

		<template phase="MainUserIncludes" form="RootAppUi_ParentView">
<![CDATA[
#include "${instanceName}.h"
]]>
		</template>
		
	</templateGroup>	

	<templateGroup id="GeneratePreLayout" ifEvents="preLayout">
		<expandMacro name="GenerateVirtualMethodOverrideForEventHandler"
			DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED"
			FunctionLocationId="SIMPLEDIALOG_CLASS_PRELAYOUT"
			ReturnType="void"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			FunctionName="PreLayout"
			FunctionArgs=""
			IsConst="false"
			OwnedRegionLocationId="SIMPLEDIALOG_CLASS_PRELAYOUT_BODY"
			ClassName="${className}">
			<expandArgument name="FunctionComment">
/**
 * Used when...
 */
			</expandArgument>
			<expandArgument name="StartFunctionBody">
CQikSimpleDialog::PreLayoutDynInitL();
			</expandArgument>
		</expandMacro>
		<expandMacro name="GenerateUserEventHandlerFunction"
			DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED_USER_HANDLERS"
			FunctionArgs=""
			ReturnType="void"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			FunctionLocationId="SIMPLEDIALOG_CLASS_PRELAYOUT_EVENTHANDLER">
		</expandMacro>
	</templateGroup>

	<templateGroup id="GeneratePostLayout" ifEvents="postLayout">
		<expandMacro name="GenerateVirtualMethodOverrideForEventHandler"
			DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED"
			FunctionLocationId="SIMPLEDIALOG_CLASS_POSTLAYOUT"
			ReturnType="void"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			FunctionName="PostLayout"
			FunctionArgs=""
			IsConst="false"
			OwnedRegionLocationId="SIMPLEDIALOG_CLASS_POSTLAYOUT_BODY"
			ClassName="${className}">
			<expandArgument name="FunctionComment">
/**
 * Used when...
 */
			</expandArgument>
			<expandArgument name="StartFunctionBody">
CQikSimpleDialog::PostLayoutDynInitL();
			</expandArgument>
		</expandMacro>
		<expandMacro name="GenerateUserEventHandlerFunction"
			DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED_USER_HANDLERS"
			FunctionArgs=""
			ReturnType="void"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			FunctionLocationId="SIMPLEDIALOG_CLASS_POSTLAYOUT_EVENTHANDLER">
		</expandMacro>
	</templateGroup>
	
	<defineLocation id="SIMPLEDIALOG_CLASS_INITIALIZECONTROLSL_BODY_1" baseLocation="SIMPLEDIALOG_CLASS_INITIALIZECONTROLSL_BODY"
		location="region(Recover Control From Control Collection)" realize="true">
		<template>
		</template>
	</defineLocation>
	
	<defineLocation id="SIMPLEDIALOG_CLASS_INITIALIZECONTROLSL_BODY_2" baseLocation="SIMPLEDIALOG_CLASS_INITIALIZECONTROLSL_BODY"
		location="region(Control setup)" realize="true">
		<template>
		</template>
	</defineLocation>
	
		<expandMacro name="GenerateMethodWithOwnedBody"
			DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED"
			FunctionName="PreRun"
			FunctionArgs=""
			ReturnType="void"
			FunctionLocationId="SIMPLEDIALOG_CLASS_PRERUN"
			IsOwned="false"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}">
			<expandArgument name="FunctionComment">
/**
 * Called from RunDlgLD before the call to RunLD
 */
			</expandArgument>
		</expandMacro>
	
	<inline>
		if (Engine.formMatches(form, [""])) {
			Engine.getGlobalDictionary().put("InstanceGenRegistry", new java.util.HashSet());
			var allContribs = Engine.generateChildContributions("|InstanceGen|EventGen");

			Engine.assignLocationsForPhase(allContribs, "MainSystemIncludes", "VIEWDIALOGBASE_SOURCE_OWNED_SYSTEM_INCLUDES")					
			Engine.assignLocationsForPhase(allContribs, "ForwardDeclarations", "HEADER_OWNED_FORWARD_DECLARATIONS")
			Engine.assignLocationsForPhase(allContribs, "UserHandlers", "VIEWDIALOGBASE_CLASS_PROTECTED_USER_HANDLERS")		
			Engine.assignLocationsForPhase(allContribs, "ClassPrivateInstanceVariables", "VIEWDIALOGBASE_CLASS_PRIVATE_OWNED_INSTANCE_VARIABLES")
			Engine.assignLocationsForPhase(allContribs, "ClassPrivateOwnedMethods", "VIEWDIALOGBASE_CLASS_PRIVATE_OWNED_METHODS")
			Engine.assignLocationsForPhase(allContribs, "ClassPrivateMethods", "VIEWDIALOGBASE_CLASS_PRIVATE_METHODS")
			Engine.assignLocationsForPhase(allContribs, "HeaderEventHandlerIncludes", "VIEWDIALOGBASE_HEADER_EVENT_HANDLER_INCLUDES")					
			Engine.assignLocationsForPhase(allContribs, "HeaderClassPublic", "VIEWDIALOGBASE_CLASS_PUBLIC")
			Engine.assignLocationsForPhase(allContribs, "HeaderClassPrivate", "VIEWDIALOGBASE_CLASS_PRIVATE")
		    Engine.assignLocationsForPhase(allContribs, "ClassBases", "VIEWDIALOGBASE_CLASS_BASES")
			Engine.assignLocationsForPhase(allContribs, "OwnedBases", "VIEWDIALOGBASE_OWNED_BASES")
			Engine.assignLocationsForPhase(allContribs, "MainUserIncludes", "VIEWDIALOGBASE_HEADER_OWNED_USER_INCLUDES")
											
			Engine.assignLocationsForPhase(allContribs, "Initialize", "SIMPLEDIALOG_CLASS_INITIALIZECONTROLSL_BODY_1")
			Engine.assignLocationsForPhase(allContribs, "Destroy", "SIMPLEDIALOG_CLASS_DESTRUCTOR_BODY")
			Engine.assignLocationsForPhase(allContribs, "EventControlHandlers","SIMPLEDIALOG_CLASS_HANDLECONTROLEVENTL_BODY")						
			Engine.assignLocationsForPhase(allContribs, "InitializeControlHandlers","SIMPLEDIALOG_CLASS_INITIALIZECONTROLSL_BODY_2")						
			Engine.assignLocationsForPhase(allContribs, "Hrhinclude", "VIEWDIALOGBASE_SOURCE_OWNED_USER_INCLUDES")
			Engine.assignLocationsForPhase(allContribs, "PreConstructFromResourceL","SIMPLEDIALOG_CLASS_PRERUN_BODY")
			
			Engine.removeDuplicateContributionsForLocation(allContribs, "SIMPLEDIALOG_CLASS_INITIALIZECONTROLSL_BODY_1");
			Engine.removeDuplicateContributionsForLocation(allContribs, "SIMPLEDIALOG_CLASS_DESTRUCTOR_BODY");
			Engine.removeDuplicateContributionsForLocation(allContribs, "SIMPLEDIALOG_CLASS_INITIALIZECONTROLSL_BODY_2");
			
			contribs.addAll(allContribs);
			// remove duplicate contributions where the location doesn't already have a filter
			Engine.removeDuplicateContributionsForLocation(contribs, "HEADER_OWNED_FORWARD_DECLARATIONS");
			Engine.removeDuplicateContributionsForLocation(contribs, "VIEWDIALOGBASE_CLASS_PRIVATE_OWNED_INSTANCE_VARIABLES");
			}
	</inline>


</sourceGen>

<sourceMapping>
	<mapResource struct="QIK_DIALOG" headers="qikon.rh">
	<mapSimpleMember property="overriddenAppTitleName" member="title"/>
		<mapArrayMember property="[com.nokia.carbide.uiq.DialogConfigurationsGroup]"  member="configurations">
		</mapArrayMember>
	    <mapReferenceMember property="[com.nokia.carbide.uiq.ControlCollection]" member="controls"></mapReferenceMember>
		<select property="overriddenAppTitleIcon.bmpfile">
			<choice value="">
			</choice>
            <choice>
				<mapResourceMember property="." member="icon" struct="QIK_CONTENT_MBM" headers="qikon.rh">
		    		<mapSimpleMember property="overriddenAppTitleIcon.bmpfile" member="bmpfile"/>
		            <select property="overriddenAppTitleIcon.bmpid">
						<choice value="">
						</choice>
			            <choice>	
							<mapEnumMember property="overriddenAppTitleIcon.bmpid" member="bmpid" validate="false"/>				
						</choice>
					</select>
					<select property="overriddenAppTitleIcon.bmpmask">
						<choice value="">
						</choice>
			            <choice>	
							<mapEnumMember property="overriddenAppTitleIcon.bmpmask" member="bmpmask" validate="false"/>						
						</choice>
					</select>
								
				</mapResourceMember>
			</choice>
		</select>												
	</mapResource>
</sourceMapping>
