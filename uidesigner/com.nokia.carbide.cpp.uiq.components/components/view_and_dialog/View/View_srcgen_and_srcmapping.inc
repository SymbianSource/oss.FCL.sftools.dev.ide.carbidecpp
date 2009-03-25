		<sourceGen forms="RootAppUi">
			<!-- Code generation according to view type -->
			<inline scope="file">
				function isSinglePage(instance) { return instance.properties.type=="singlePage"; }
			</inline>
			<inline scope="file">
				function isMultiPage(instance) { return instance.properties.type=="multiPage"; }
			</inline>
			<inline scope="file">
				function isDialog(instance) { return instance.properties.type=="dialog"; }
			</inline>
			<inline scope="file">
				function isSingleOrMultiPage(instance) { return (instance.properties.type=="singlePage") || (instance.properties.type=="multiPage"); }
			</inline>
			<inline scope="file">
				include("../../srcgenLibrary.js")
				
				function getViewConfigurationsResourceName(instance) {
					if (resourceName != null)
						return resourceName.toUpperCase();
				}
				function getControlCollectionResourceName(instance) {
					for (var i in instance.children) {
						var child = instance.children[i];
						if (child.isInstanceOf("com.nokia.carbide.uiq.ControlCollection")) {
							if (Engine.getGeneratedResource(child, "controlCollection") != null)
								return Engine.getGeneratedResource(child, "controlCollection").toUpperCase();
						}
					}
					return "null";
				}
				function getApplicationTitleResourceName(instance) {
					if (Engine.getGeneratedResource(instance, "view_title") != null)
						return Engine.getGeneratedResource(instance, "view_title").toUpperCase();
					else
						return null;
				}
				function getMbgInclude(bmpfile) {
					return "#include &lt;" + getBitmapHeaderName(bmpfile) +  "&gt;";
				}
			</inline>
			<inline scope="prototype">
		 ${jsObject}.prototype.findOrCreateViewUidConstant = function(instance) {

// the algorithm can deal with either CAknView or AvkonViewReference

return Engine.findOrCreateEnumeratorForAlgorithm(instance, ".", 

"com.nokia.sdt.component.symbian.NAME_ALG_VIEW_UID");

}
		
		</inline>
			
			<!-- Base generation -->
			<useTemplateGroup ids="*"/>

			<templateGroup id="HrhInclude">			
				<template id="AdditionalUserIncludes3" location="VIEWDIALOGBASE_SOURCE_OWNED_USER_INCLUDES">
<![CDATA[#include "${instanceName}.hrh"]]>
				</template>
			</templateGroup>
			
			<!-- Generation for single/multi page -->
			<templateGroup id="CommonMethodsSinglePageView" ifExpr="isSingleOrMultiPage(instance)">
				<expandMacro name="GenerateMethod"
					DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE"
					FunctionName="${className}"
					FunctionArgs="CQikAppUi&amp; aAppUi, const TVwsViewId aParentViewId"
					ReturnType=""
					FunctionLocationId="VIEW_CLASS_CONSTRUCTOR"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Constructor for the view.
 * Passes the application UI reference to the construction of the super class.
 * 
 * KNullViewId should normally be passed as parent view for the applications 
 * default view. The parent view is the logical view that is normally activated 
 * when a go back command is issued. KNullViewId will activate the system 
 * default view. 
 * 
 * @param aAppUi Reference to the application UI
 * @param aParentViewId UID of the parent view 
 */
					</expandArgument>
					<expandArgument name="Initializers">
${className}Base(aAppUi, aParentViewId)
					</expandArgument>
					<expandArgument name="FunctionBody">

					</expandArgument>
				</expandMacro>
				
				<expandMacro name="GenerateMethodWithOwnedBody"
					DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
					FunctionName="~${className}"
					FunctionArgs=""
					ReturnType=""
					FunctionLocationId="VIEW_CLASS_DESTRUCTOR"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Destructor for the view
 */
					</expandArgument>
					<expandArgument name="StartFunctionBody">
iControlEventDispatch.Reset();
iCommandEventDispatch.Reset();					

					</expandArgument>
				</expandMacro>
				
				<expandMacro name="GenerateMethod"
					DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
					FunctionName="NewLC"
					FunctionArgs="CQikAppUi&amp; aAppUi, const TVwsViewId aParentViewId"
					ReturnType="${className}*"
					IsStatic="true"
					FunctionLocationId="VIEW_CLASS_NEWLC"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Creates and constructs the view.
 * 
 * @param aAppUi Reference to the AppUi
 * @param aParentViewId UID of the parent view 
 * @return Pointer to a ${className} object
 */
					</expandArgument>
					<expandArgument name="FunctionBody">
${className}* self = new (ELeave) ${className}($(FunctionArgs::as-function-call-args::add-spaces-unless-empty::split-and-indent));
CleanupStack::PushL(self);
self->ConstructL();
return self;
					</expandArgument>
				</expandMacro>
				
				<expandMacro name="GenerateMethod"
					DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE"
					FunctionName="ConstructL"
					FunctionArgs=""
					ReturnType="void"
					FunctionLocationId="VIEW_CLASS_CONSTRUCTL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * 2nd stage construction of the view.
 */
					</expandArgument>
					<expandArgument name="FunctionBody">
// Calls ConstructL that initialises the standard values. 
// This should always be called in the concrete view implementations.
BaseConstructL();
					</expandArgument>
				</expandMacro>
				
				<expandMacro name="GenerateMethodWithOwnedBody"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED"
					FunctionName="ViewConstructL"
					FunctionArgs=""
					ReturnType="void"
					FunctionLocationId="VIEW_CLASS_VIEWCONSTRUCTL"
					OwnedRegionLocationId="VIEW_CLASS_VIEWCONSTRUCTL_BODY2"
					OwnedRegionName="Pre-ViewConstructFromResourceL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Inherited from ${className}Base and called upon by the UI Framework. 
 * It creates the view from resource.
 */
					</expandArgument>
				</expandMacro>
				
			<defineLocation id="VIEW_CLASS_VIEWCONSTRUCTL_BODY" baseLocation="VIEW_CLASS_VIEWCONSTRUCTL"
				location="region(Generated Contents)" realize="true">
				<template>
// Loads information about the UI configurations this view supports
// together with definition of each view.
ViewConstructFromResourceL(${getViewConfigurationsResourceName(instance)},
	${getControlCollectionResourceName(instance)});

InitializeControlsL();
				</template>
			</defineLocation>
				
				<expandMacro name="GenerateMethod"
					DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
					FunctionName="ViewId"
					FunctionArgs=""
					ReturnType="TVwsViewId"
					IsConst="true"
					FunctionLocationId="VIEW_CLASS_VIEWID"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Returns the view Id
 *
 * @return Returns UID of the view
 */
					</expandArgument>
					<expandArgument name="FunctionBody">
return TVwsViewId(KUid${getProjectName()}App, KUid${instanceName});
					</expandArgument>
				</expandMacro>
				
				<expandMacro name="GenerateMethodWithOwnedBody"
					DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
					FunctionName="HandleCommandL"
					FunctionArgs="CQikCommand&amp; aCommand"
					ReturnType="void"
					FunctionLocationId="VIEW_CLASS_HANDLECOMMANDL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}"
					OwnedRegionLocationId="VIEW_CLASS_HANDLECOMMANDL_BODY">
					<expandArgument name="FunctionComment">
/**
 * Handles all commands in the view.
 * Called by the UI framework when a command has been issued.
 * The command Ids are defined in the .hrh file.
 * 
 * @param aCommand The command to be executed
 * @see ${className}Base::HandleCommandL
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
					ReturnType="void"
					FunctionLocationId="VIEW_CLASS_HANDLECONTROLEVENTL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}"
					OwnedRegionLocationId="VIEW_CLASS_HANDLECONTROLEVENTL_BODY">
					<expandArgument name="FunctionComment">
/**
 * Handles an event from an observed control.
 * 
 * @param aControl The control that sent the event.
 * @param aEventType The event type.
 */
					</expandArgument>
					<expandArgument name="StartFunctionBody">
${className}Base::HandleControlEventL($(FunctionArgs::as-function-call-args::add-spaces-unless-empty));
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
					FunctionLocationId="VIEW_CLASS_INITIALIZECONTROLSL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}"
					OwnedRegionLocationId="VIEW_CLASS_INITIALIZECONTROLSL_BODY">
					<expandArgument name="FunctionComment">
/**
 *	Initialize each control upon creation.
 */				
					</expandArgument>
					<expandArgument name="FunctionBody">
iControlEventDispatch.Reset();
iCommandEventDispatch.Reset();					
					</expandArgument>
				</expandMacro>
				
				<expandMacro name="GenerateMethodWithOwnedBody"
					DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE"
					FunctionName="AddControlEventHandlerL"
					FunctionArgs="CCoeControl* aControl, TCoeEvent aEvent, ControlEventHandler aHandler"
					ReturnType="void"
					FunctionLocationId="VIEW_CLASS_ADDCONTROLEVENTHANDLERL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}"
					OwnedRegionLocationId="VIEW_CLASS_ADDCONTROLEVENTHANDLERL_BODY">
					<expandArgument name="FunctionComment">
/** 
 * Helper function to register MCoeControlObserver event handlers
 *
 * @param aControl The control that sent the event
 * @param aEvent The event
 * @param aHandler The Handler
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
					FunctionArgs="TInt aId, CommandEventHandler aHandler"
					ReturnType="void"
					FunctionLocationId="VIEW_CLASS_ADDCOMMANDEVENTHANDLERL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}"
					OwnedRegionLocationId="VIEW_CLASS_ADDCOMMANDEVENTHANDLERL_BODY">
					<expandArgument name="FunctionComment">
/** 
 * Helper function to register command event handlers
 *
 * @param aId Command ID
 * @param aHandler The Handler
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
				

				<template phase="Initialize" form="RootAppUi_ParentView"><![CDATA[
// Create the multi page view and add it to the framework as the parent view.
// The parent view is the logical view that is normally activated when a go
// back command is issued. KNullViewId will activate the system default view. 
TVwsViewId parentView( KUid${getProjectName()}App, KUid${instanceName} );
${instance.className}* ${instance.name} = ${instance.className}::NewLC(
	*this,
	KNullViewId );
AddViewL( *${instance.name} );
CleanupStack::Pop( ${instance.name} );
]]>			</template>
		
		<template phase="Initialize" form="RootAppUi_NormalView"><![CDATA[
// Create the start view and add it to the framework
// The first added view becomes the default view
${instance.className}* ${instance.name} = ${instance.className}::NewLC(
	*this,
	parentView );
AddViewL( *${instance.name} );
CleanupStack::Pop( ${instance.name} );
]]>			</template>
				
			</templateGroup>
			
			<!-- Generation for dialog -->
			<templateGroup id="CommonMethodsDialogView" ifExpr="isDialog(instance)">
				<expandMacro name="GenerateMethod"
					DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE"
					FunctionName="${className}"
					FunctionArgs=""
					ReturnType=""
					FunctionLocationId="DIALOG_CLASS_CONSTRUCTOR"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}"
					Realize="true">
					<expandArgument name="FunctionComment">
/**
 * Constructor for the dialog.
 */
					</expandArgument>
				</expandMacro>
				
				<expandMacro name="GenerateMethodWithOwnedBody"
					DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
					FunctionName="~${className}"
					FunctionArgs=""
					ReturnType=""
					FunctionLocationId="DIALOG_CLASS_DESTRUCTOR"
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
				
				<expandMacro name="GenerateMethod"
					DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
					FunctionName="RunDlgLD"
					FunctionArgs=""
					ReturnType="TInt"
					IsStatic="true"
					FunctionLocationId="DIALOG_CLASS_RUNDLGLD"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Creates, constructs, execute and destroys the dialog.
 */
					</expandArgument>
					<expandArgument name="FunctionBody">
${className}* self = new (ELeave) ${className}();
CleanupStack::PushL(self);
self->ConstructL();
CleanupStack::Pop();
return self->ExecuteLD();
					</expandArgument>
				</expandMacro>
				
				<expandMacro name="GenerateMethod"
					DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE"
					FunctionName="ConstructL"
					FunctionArgs=""
					ReturnType="void"
					FunctionLocationId="DIALOG_CLASS_CONSTRUCTL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * 2nd stage construction of the dialog.
 */
					</expandArgument>
					<expandArgument name="FunctionBody">

					</expandArgument>
				</expandMacro>
				
				<expandMacro name="GenerateMethodWithOwnedBody"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED"
					FunctionName="ViewConstructL"
					FunctionArgs=""
					ReturnType="void"
					FunctionLocationId="DIALOG_CLASS_VIEWCONSTRUCTL"
					OwnedRegionLocationId="DIALOG_CLASS_VIEWCONSTRUCTL_BODY2"
					OwnedRegionName="Pre-ViewConstructFromResourceL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Inherited from ${className}Base and called upon by the UI Framework. 
 * It creates the dialog from resource.
 */
					</expandArgument>
				</expandMacro>

			<defineLocation id="DIALOG_CLASS_VIEWCONSTRUCTL_BODY" baseLocation="DIALOG_CLASS_VIEWCONSTRUCTL"
				location="region(Generated Contents)" realize="true">
				<template>
// Loads information about the UI configurations this view supports
// together with definition of each view.
ViewConstructFromResourceL(${getViewConfigurationsResourceName(instance)},
	${getControlCollectionResourceName(instance)});

InitializeControlsL();
				</template>
			</defineLocation>

				<expandMacro name="GenerateMethodWithOwnedBody"
					DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
					FunctionName="HandleCommandL"
					FunctionArgs="CQikCommand&amp; aCommand"
					ReturnType="void"
					FunctionLocationId="DIALOG_CLASS_HANDLECOMMANDL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}"
					OwnedRegionLocationId="DIALOG_CLASS_HANDLECOMMANDL_BODY">
					<expandArgument name="FunctionComment">
/**
 * Handles all commands in the dialog.
 * Called by the UI framework when a command has been issued.
 * The command Ids are defined in the .hrh file.
 * 
 * @param aCommand The command to be executed
 * @see ${className}Base::HandleCommandL
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
					ReturnType="void"
					FunctionLocationId="DIALOG_CLASS_HANDLECONTROLEVENTL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}"
					OwnedRegionLocationId="DIALOG_CLASS_HANDLECONTROLEVENTL_BODY">
					<expandArgument name="FunctionComment">
/**
 * Handles an event from an observed control.
 * 
 * @param aControl The control that sent the event.
 * @param aEventType The event type.
 */
					</expandArgument>
					<expandArgument name="StartFunctionBody">
${className}Base::HandleControlEventL($(FunctionArgs::as-function-call-args::add-spaces-unless-empty));
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
					FunctionLocationId="DIALOG_CLASS_INITIALIZECONTROLSL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}"
					OwnedRegionLocationId="DIALOG_CLASS_INITIALIZECONTROLSL_BODY">
					<expandArgument name="FunctionComment">
/**
 *	Initialize each control upon creation.
 */				
					</expandArgument>
					<expandArgument name="FunctionBody">
iControlEventDispatch.Reset();
iCommandEventDispatch.Reset();
					</expandArgument>
				</expandMacro>
				
				<expandMacro name="GenerateMethodWithOwnedBody"
					DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE"
					FunctionName="AddControlEventHandlerL"
					FunctionArgs="CCoeControl* aControl, TCoeEvent aEvent, ControlEventHandler aHandler"
					ReturnType="void"
					FunctionLocationId="DIALOG_CLASS_ADDCONTROLEVENTHANDLERL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}"
					OwnedRegionLocationId="DIALOG_CLASS_ADDCONTROLEVENTHANDLERL_BODY">
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
					FunctionArgs="TInt aId, CommandEventHandler aHandler"
					ReturnType="void"
					FunctionLocationId="DIALOG_CLASS_ADDCOMMANDEVENTHANDLERL"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					ClassName="${className}"
					OwnedRegionLocationId="DIALOG_CLASS_ADDCOMMANDEVENTHANDLERL_BODY">
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


				<template phase="Initialize" form="RootAppUi_ParentView"><![CDATA[
${instance.className}::RunDlgLD();
]]>			</template>
			
			</templateGroup>
			
			<!-- Common events sourceGen -->
			<templateGroup id="GenerateUiConfigChanged" ifEvents="uiConfigChanged">
				<expandMacro name="GenerateVirtualMethodOverrideForEventHandler"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED"
					FunctionLocationId="VIEWDIALOG_CLASS_UICONFIGCHANGED"
					ReturnType="void"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionName="HandleUiConfigChangedL"
					FunctionArgs=""
					IsConst="false"
					OwnedRegionLocationId="VIEWDIALOG_CLASS_UICONFIGCHANGED_BODY"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Called when the client is notified about a ui config change.
 */
					</expandArgument>
					<expandArgument name="StartFunctionBody">
${className}Base::HandleUiConfigChangedL();
InitializeControlsL();
					</expandArgument>
				</expandMacro>
				<expandMacro name="GenerateUserEventHandlerFunction"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED_USER_HANDLERS"
					FunctionArgs=""
					ReturnType="void"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionLocationId="VIEWDIALOG_CLASS_UICONFIGCHANGED_EVENTHANDLER">
					<expandArgument name="FunctionBody">
// Handle here the event, i.e.
/*TQikUiConfig config = CQUiConfigClient::Static().CurrentConfig(); //get config
switch( config.ScreenMode() )
	{
	case EQikUiConfigPortrait:
	//handle change
	break;
	case EQikUiConfigSmallPortrait:
	//handle change
	break;
	}*/
					</expandArgument>
				</expandMacro>
			</templateGroup>
			
			<!-- Not sure if this event needs to be implemented as it was generated after view creation 
			<templateGroup id="GenerateHandleCommand" ifEvents="handleCommand">
				<expandMacro name="GenerateVirtualMethodOverrideForEventHandler"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED"
					FunctionLocationId="VIEWDIALOG_CLASS_HANDLECOMMAND"
					ReturnType="void"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionName="HandleCommandL"
					FunctionArgs="CQikCommand&amp; aCommand"
					IsConst="false"
					OwnedRegionLocationId="VIEWDIALOG_CLASS_HANDLECOMMAND_BODY"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * A default implementation that shows an infoprint in debug builds.
 * This method is intended for you to override if you want to
 * set the view as MQikCommandHandler for some of your Commands.
 * 
 * @param aCommand The command to be executed
 */
					</expandArgument>
				</expandMacro>
				<expandMacro name="GenerateUserEventHandlerFunction"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED_USER_HANDLERS"
					FunctionArgs="CQikCommand&amp; aCommand"
					ReturnType="void"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionLocationId="VIEWDIALOG_CLASS_HANDLECOMMAND_EVENTHANDLER">
				</expandMacro>
			</templateGroup>
			-->
			
			<templateGroup id="GenerateHandleAttemptDimmedSelection" ifEvents="handleAttemptDimmedSelection">
				<expandMacro name="GenerateVirtualMethodOverrideForEventHandler"
					DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC"
					FunctionLocationId="VIEWDIALOG_CLASS_HANDLEATTEMPTDIMMEDSELECTION"
					ReturnType="void"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionName="HandleAttemptDimmedSelectionL"
					FunctionArgs="CQikCommand&amp; aCommand"
					IsConst="false"
					OwnedRegionLocationId="VIEWDIALOG_CLASS_HANDLEATTEMPTDIMMEDSELECTION_BODY"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Called when a user try to select a command which has the EQikCommandFlagDimmed flag set.
 */
					</expandArgument>
					<expandArgument name="StartFunctionBody">
${className}Base::HandleAttemptDimmedSelectionL($(FunctionArgs::as-function-call-args::add-spaces-unless-empty));
					</expandArgument>
				</expandMacro>
				<expandMacro name="GenerateUserEventHandlerFunction"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED_USER_HANDLERS"
					FunctionArgs="CQikCommand&amp; aCommand"
					ReturnType="void"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionLocationId="VIEWDIALOG_CLASS_HANDLEATTEMPTDIMMEDSELECTION_EVENTHANDLER">
				</expandMacro>
			</templateGroup>
			
			<templateGroup id="GenerateViewActivated" ifEvents="viewActivated">
				<expandMacro name="GenerateVirtualMethodOverrideForEventHandler"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED"
					FunctionLocationId="VIEWDIALOG_CLASS_VIEWACTIVATED"
					ReturnType="void"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionName="ViewActivatedL"
					FunctionArgs="const TVwsViewId&amp; aPrevViewId, TUid aCustomMessageId, const TDesC8&amp; aCustomMessage"
					IsConst="false"
					OwnedRegionLocationId="VIEWDIALOG_CLASS_VIEWACTIVATED_BODY"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Forwarded asynchronously from the viewserver when this view is requested to the foreground.
 */
					</expandArgument>
				</expandMacro>
				<expandMacro name="GenerateUserEventHandlerFunction"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED_USER_HANDLERS"
					FunctionArgs="const TVwsViewId&amp; aPrevViewId, TUid aCustomMessageId, const TDesC8&amp; aCustomMessage"
					ReturnType="void"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionLocationId="VIEWDIALOG_CLASS_VIEWACTIVATED_EVENTHANDLER">
				</expandMacro>
			</templateGroup>
			
			<templateGroup id="GenerateViewDeactivated" ifEvents="viewDeactivated">
				<expandMacro name="GenerateVirtualMethodOverrideForEventHandler"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED"
					FunctionLocationId="VIEWDIALOG_CLASS_VIEWDEACTIVATED"
					ReturnType="void"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionName="ViewDeactivated"
					FunctionArgs=""
					IsConst="false"
					OwnedRegionLocationId="VIEWDIALOG_CLASS_VIEWDEACTIVATED_BODY"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Forwarded asynchronously from the viewserver when this view is requested to the background.
 */
					</expandArgument>
				</expandMacro>
				<expandMacro name="GenerateUserEventHandlerFunction"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED_USER_HANDLERS"
					FunctionArgs=""
					ReturnType="void"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionLocationId="VIEWDIALOG_CLASS_VIEWDEACTIVATED_EVENTHANDLER">
				</expandMacro>
			</templateGroup>
			
			<templateGroup id="GenerateTabActivated" ifEvents="tabActivated">
				<expandMacro name="GenerateVirtualMethodOverrideForEventHandler"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED"
					FunctionLocationId="VIEWDIALOG_CLASS_TABACTIVATED"
					ReturnType="void"
					IsOwned="false"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionName="TabActivatedL"
					FunctionArgs="TInt aTabId"
					IsConst="false"
					OwnedRegionLocationId="VIEWDIALOG_CLASS_TABACTIVATED_BODY"
					ClassName="${className}">
					<expandArgument name="FunctionComment">
/**
 * Will be called by the framework when a tab has been activated.
 */
					</expandArgument>
				</expandMacro>
				<expandMacro name="GenerateUserEventHandlerFunction"
					DeclLocation="VIEWDIALOGBASE_CLASS_PROTECTED_USER_HANDLERS"
					FunctionArgs="TInt aTabId"
					ReturnType="void"
					DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
					FunctionLocationId="VIEWDIALOG_CLASS_TABACTIVATED_EVENTHANDLER">
				</expandMacro>
								
			</templateGroup>
			
			<template id="OverridenAppTitleSourceGen" location="VIEW_CLASS_INITIALIZECONTROLSL_BODY" ifExpr="instance.properties.overriddenAppTitleName.length>0"><![CDATA[
HBufC* appTitleText = iEikonEnv->AllocReadResourceLC( ${getApplicationTitleResourceName(instance)} );
SetAppTitleNameL( appTitleText->Des() );
CleanupStack::PopAndDestroy( appTitleText );]]>
			</template>

			<template id="OverridenAppIconSourceGen" location="VIEW_CLASS_INITIALIZECONTROLSL_BODY" ifExpr="instance.properties.overriddenAppTitleIcon.bmpid.length>0"><![CDATA[
_LIT( KFullSearchPath, "z:${TextUtils.escape(instance.properties.overriddenAppTitleIcon.bmpfile, '\')}" );
// With bitmap mask which is supplied as a third argument
CGulIcon* viewIcon = iEikonEnv->CreateIconL( KFullSearchPath,
	${instance.properties.overriddenAppTitleIcon.bmpid},
	${instance.properties.overriddenAppTitleIcon.bmpmask} );
CleanupStack::PushL( viewIcon );
SetAppTitleIconL( viewIcon->Bitmap(), viewIcon->Mask() );
viewIcon->SetBitmapsOwnedExternally( ETrue );
CleanupStack::PopAndDestroy( viewIcon );]]>

			</template>
			<template id="OverridenAppIconIncludes" location="VIEWDIALOGBASE_SOURCE_OWNED_SYSTEM_INCLUDES" ifExpr="instance.properties.overriddenAppTitleIcon.bmpid.length>0">
#include &lt;gulicon.h&gt;
${getMbgInclude(instance.properties.overriddenAppTitleIcon.bmpfile)}				
			</template>
			
			<template phase="Initialize" form="ExternalInterface"><![CDATA[
const TUid KUid${instanceName} = {${this.findOrCreateViewUidConstant(instance)}};]]>
			</template>
			<template phase="OwnedUserIncludes" form="ExternalInterface">
#include "${getProjectName()}.hrh"
			</template>
			
			<template phase="MainUserIncludes" form="RootAppUi">
#include "${getRootModelInstanceOfType("com.nokia.carbide.uiq.Application").name}ExternalInterface.h"
			</template>
			<template phase="MainUserIncludes" form="RootAppUi">
#include "${getRootModelInstanceOfType("com.nokia.carbide.uiq.Application").name}ExternalInterface.h"
			</template>
			<template phase="MainUserIncludes" form="RootAppUi">
#include "${instanceName}.h"
			</template>

			<defineLocation id="VIEW_CLASS_INITIALIZECONTROLSL_BODY_1"
				baseLocation="VIEW_CLASS_INITIALIZECONTROLSL_BODY"
				location="region(Recover Control From Control Collection)"
				realize="true">
				<template>
				</template>
			</defineLocation>

			<defineLocation id="VIEW_CLASS_INITIALIZECONTROLSL_BODY_2"
				baseLocation="VIEW_CLASS_INITIALIZECONTROLSL_BODY"
				location="region(Control setup)"
				realize="true">
				<template>
				</template>
			</defineLocation>

			<defineLocation id="DIALOG_CLASS_INITIALIZECONTROLSL_BODY_1"
				baseLocation="DIALOG_CLASS_INITIALIZECONTROLSL_BODY"
				location="region(Recover Control From Control Collection)"
				realize="true">
				<template>
				</template>
			</defineLocation>

			<defineLocation id="DIALOG_CLASS_INITIALIZECONTROLSL_BODY_2"
				baseLocation="DIALOG_CLASS_INITIALIZECONTROLSL_BODY"
				location="region(Control setup)"
				realize="true">
				<template>
				</template>
			</defineLocation>



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
		
					if (isDialog(instance)) {
						Engine.assignLocationsForPhase(allContribs, "Initialize", "DIALOG_CLASS_INITIALIZECONTROLSL_BODY_1")
						Engine.assignLocationsForPhase(allContribs, "Destroy", "DIALOG_CLASS_DESTRUCTOR_BODY")
						Engine.assignLocationsForPhase(allContribs, "EventControlHandlers","DIALOG_CLASS_HANDLECONTROLEVENTL_BODY")						
						Engine.assignLocationsForPhase(allContribs, "InitializeControlHandlers","DIALOG_CLASS_INITIALIZECONTROLSL_BODY_2")						
						Engine.assignLocationsForPhase(allContribs, "PreConstructFromResourceL","DIALOG_CLASS_VIEWCONSTRUCTL_BODY2")
						
						Engine.removeDuplicateContributionsForLocation(allContribs, "DIALOG_CLASS_DESTRUCTOR_BODY");
						Engine.removeDuplicateContributionsForLocation(allContribs, "DIALOG_CLASS_INITIALIZECONTROLSL_BODY_1");
						Engine.removeDuplicateContributionsForLocation(allContribs, "DIALOG_CLASS_INITIALIZECONTROLSL_BODY_2");
					} else if (isSingleOrMultiPage(instance)) {
						Engine.assignLocationsForPhase(allContribs, "Initialize", "VIEW_CLASS_INITIALIZECONTROLSL_BODY_1")
						Engine.assignLocationsForPhase(allContribs, "Destroy", "VIEW_CLASS_DESTRUCTOR_BODY")
						Engine.assignLocationsForPhase(allContribs, "EventControlHandlers","VIEW_CLASS_HANDLECONTROLEVENTL_BODY")
						Engine.assignLocationsForPhase(allContribs, "InitializeControlHandlers","VIEW_CLASS_INITIALIZECONTROLSL_BODY_2")
						Engine.assignLocationsForPhase(allContribs, "PreConstructFromResourceL","VIEW_CLASS_VIEWCONSTRUCTL_BODY2")
						
						Engine.removeDuplicateContributionsForLocation(allContribs, "VIEW_CLASS_DESTRUCTOR_BODY");
						Engine.removeDuplicateContributionsForLocation(allContribs, "VIEW_CLASS_INITIALIZECONTROLSL_BODY_1");
						Engine.removeDuplicateContributionsForLocation(allContribs, "VIEW_CLASS_INITIALIZECONTROLSL_BODY_2");
					}
					contribs.addAll(allContribs);
					// remove duplicate contributions where the location doesn't already have a filter
					Engine.removeDuplicateContributionsForLocation(contribs, "HEADER_OWNED_FORWARD_DECLARATIONS");
					Engine.removeDuplicateContributionsForLocation(contribs, "VIEWDIALOGBASE_CLASS_PRIVATE_OWNED_INSTANCE_VARIABLES");
				}
			</inline>
		</sourceGen>
		
		<sourceMapping>
			<mapResource headers="qikon.rh"
				struct="QIK_VIEW_CONFIGURATIONS">
				<mapArrayMember property="[com.nokia.carbide.uiq.ViewConfigurationsGroup]" member="configurations">
				</mapArrayMember>
			</mapResource>
			<mapResource struct="TBUF" headers="qikon.rh" id="view_title">
			    <mapSimpleMember property="overriddenAppTitleName" member="buf"></mapSimpleMember>
			</mapResource>
		</sourceMapping>