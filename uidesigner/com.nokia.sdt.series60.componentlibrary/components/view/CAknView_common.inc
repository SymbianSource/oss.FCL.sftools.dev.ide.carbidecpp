		<!-- This component must have a child of type CBA -->
		
		<sourceMapping>
  		  <mapResource struct="AVKON_VIEW" headers="avkon.rsg avkon.rh">
  		  <select property="[com.nokia.sdt.series60.CAknForm]">
  		  	<choice value="">
	 		  <select property="[com.nokia.sdt.series60.CBA]">
	 		    <choice value="">
	 		    	<!-- no CBA -->
	 		    </choice>
	 		    <choice>
		 		  <select property="[com.nokia.sdt.series60.CBA].info">
				    <choice value="com.nokia.sdt.series60.CBA.Type.CUSTOM">
				    	<!-- emit a reference to the resource generated -->
				        <mapReferenceMember property="[com.nokia.sdt.series60.CBA]" member="cba"/>
				    </choice>
				    <choice>
						<!-- else, emit the builtin -->
						<mapIdentifierMember property="[com.nokia.sdt.series60.CBA].info" member="cba" />
				    </choice> 
				  </select>
				</choice>
			  </select>
			  
			  <!-- only emit menubar if child is not CAknForm -->
  			  <mapReferenceMember property="optionsMenu" member="menubar" />
  			  
  			  <select property="[com.nokia.sdt.series60.Toolbar]">
	 		    <choice value="">
	 		    	<!-- no tool bar, default value is 0 -->
	 		    	 <mapFixedMember member="toolbar" value="0" />
	 		    </choice>
	 		    <choice>
			    	<!-- emit a reference to the resource generated -->
			        <mapReferenceMember property="[com.nokia.sdt.series60.Toolbar]" member="toolbar"/>
				 </choice>
			  </select>
  			  
			 </choice>
			 <choice/> <!-- don't emit the CBA reference to the view resource, if child is CAknForm -->
			</select>
		 </mapResource>
		</sourceMapping>		

		<sourceGen forms="RootAppUi">
			<inline scope="prototype">
include("../containers/containerLibrary.js")
include("../cba/cbaLibrary.js")
			</inline>

			<!-- HEADER FILE -->			

			<expandMacro name="HeaderFileTemplate" 
				LocationPrefix="HEADER"
				OwnedSystemIncludesRegionName="Generated Includes"
				RealizeOwnedUserIncludes="false" 
				RealizeOwnedTypedefs="false" />
			
			<expandMacro name="AddCppIncludes" 
				Location="HEADER_OWNED_SYSTEM_INCLUDES"
				Headers="aknview.h" />
			
			<!-- we don't make custom types here -->
			<expandMacro name="ClassTemplate"
				BaseClassName="CAknView"
				RealizePublicOwnedTypes="false"
				RealizePrivateOwnedTypes="false">
				
				<expandArgument name="ClassComment"><![CDATA[
/**
 * Avkon view class for ${instanceName}. It is register with the view server
 * by the AppUi. It owns the container control.
 * @class	${className} ${instanceName}.h
 */						
]]>				</expandArgument>
					
				<expandArgument name="InitialPublicHeadContent"><![CDATA[
// constructors and destructor
${className}();
static ${className}* NewL();
static ${className}* NewLC();        
void ConstructL();
virtual ~${className}();
					
// from base class CAknView
TUid Id() const;
void HandleCommandL( TInt aCommand );
]]>				</expandArgument>

				<expandArgument name="InitialProtectedHeadContent"><![CDATA[
// from base class CAknView
void DoActivateL(
	const TVwsViewId& aPrevViewId,
	TUid aCustomMessageId,
	const TDesC8& aCustomMessage );
void DoDeactivate();
void HandleStatusPaneSizeChange();
]]>				</expandArgument>

				<expandArgument name="InitialPrivateHeadContent"><![CDATA[
void SetupStatusPaneL();
void CleanupStatusPane();
]]>				</expandArgument>
					
			</expandMacro>
						
			<!-- MAIN FILE -->
			
			<!-- make the source file -->
			
			<!-- don't automatically generate header #include since we need
				to inject .hrh first -->
			<expandMacro name="SourceFileTemplate" 
				LocationPrefix="MAIN"
				HeaderFileName="" />

			<expandMacro name="AddCppIncludes"
				Location="MAIN_OWNED_SYSTEM_INCLUDES"
				Headers="aknviewappui.h eikmenub.h avkon.hrh" />

			<!-- forcibly include the app's hrh file since the ::Id() method is not owned -->
			<template location="MAIN_OWNED_USER_INCLUDES">
#include "${getProjectName()}.hrh"
			</template>
			<template location="MAIN_OWNED_USER_INCLUDES">
#include "${instanceName}.h"
			</template>

			<!-- only define; declared above -->
			<expandMacro name="DefineMethodWithOwnedBody"
				DefnLocation="MAIN_FILE"
				IsOwned="false"
				FunctionLocationId="CONSTRUCTOR"
				FunctionName="${className}"
				ReturnType=""
				FunctionArgs="">
				<expandArgument name="FunctionComment">
/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
				</expandArgument>
				
			</expandMacro>
				
			<!-- only define; declared above -->
			<expandMacro name="DefineMethodWithOwnedBody"
				DefnLocation="MAIN_FILE"
				IsOwned="false"
				FunctionLocationId="DESTRUCTOR"
				FunctionName="~${className}"
				ReturnType=""
				FunctionArgs="">
				<expandArgument name="FunctionComment">
/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
				</expandArgument>
			</expandMacro>

			<!-- stock template code for one-time non-owned functions --> 
			<template location="MAIN_FILE"><![CDATA[
/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of ${className}
 */
${className}* ${className}::NewL()
	{
	${className}* self = ${className}::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of ${className}
 */
${className}* ${className}::NewLC()
	{
	${className}* self = new ( ELeave ) ${className}();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}

]]>
			</template>

			<!-- only define; declared above -->
			<expandMacro name="DefineMethodWithOwnedBody"
				DefnLocation="MAIN_FILE"
				IsOwned="false"
				FunctionLocationId="CONSTRUCTL_METHOD"
				FunctionName="ConstructL"
				ReturnType="void"
				FunctionArgs=""
				OwnedRegionName="Generated Code">
				<expandArgument name="FunctionComment">
/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
				</expandArgument>
				<expandArgument name="FunctionBody">
BaseConstructL( ${resourceName$upper} );
				</expandArgument>
				<expandArgument name="EndFunctionBody">
// add your own initialization code here
				</expandArgument>
				
			</expandMacro>
			
			<!-- only define; declared above -->
			<expandMacro name="DefineMethod"
				DefnLocation="MAIN_FILE"
				IsOwned="false"
				FunctionLocationId="ID_METHOD"
				FunctionName="Id"
				ReturnType="TUid"
				IsConst="true"
				FunctionArgs="">
				<expandArgument name="FunctionComment">
/**
 * @return The UID for this view
 */
				</expandArgument>
				<expandArgument name="FunctionBody">
return TUid::Uid( ${getViewUidConstant(instance, contribs)} );
				</expandArgument>
			</expandMacro>
			
			<!-- only define; declared above -->
			<expandMacro name="DefineMethodWithOwnedBody"
				DefnLocation="MAIN_FILE"
				IsOwned="false"
				FunctionLocationId="HANDLECOMMANDL"
				FunctionName="HandleCommandL"
				ReturnType="void"
				FunctionArgs="TInt aCommand"
				OwnedRegionName="Generated Code">
				<expandArgument name="FunctionComment">
/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
				</expandArgument>
				<expandArgument name="FunctionBody" />
			</expandMacro>

			<template location="HANDLECOMMANDL_BODY"><![CDATA[
TBool commandHandled = EFalse;
switch ( aCommand )
	{	// code to dispatch to the AknView's menu and CBA commands is generated here
<%	var handlerContribs = Engine.generateChildContributions("HandleCommandSwitchDispatch");
		// resolve the contents for switch to this method
		var switchContribs = Engine.getContributionsForPhase(handlerContribs, "SwitchContents");
		Engine.assignLocationsForPhase(switchContribs, "SwitchContents", "HANDLECOMMANDL_BODY")
		contribs.addAll(switchContribs);
		handlerContribs.removeAll(switchContribs);
	%>	default:
		break;
	}

<%	// Handle any leftover handler contribs that occur after the switch here.
	var postSwitchContribs = Engine.getContributionsForPhase(handlerContribs, "PostSwitchContents");
	Engine.assignLocationsForPhase(postSwitchContribs, "PostSwitchContents", "HANDLECOMMANDL_BODY")
	contribs.addAll(postSwitchContribs); 
	handlerContribs.removeAll(postSwitchContribs); 
	
	// Remaining ones go into this file
	var includeContribs = Engine.getContributionsForPhase(handlerContribs, "MainSystemIncludes");
	Engine.assignLocationsForPhase(includeContribs, "MainSystemIncludes", "MAIN_OWNED_SYSTEM_INCLUDES");
	contribs.addAll(includeContribs); 
	%>	
if ( !commandHandled ) 
	{
<%	// Handle 'handleCommand' event here.  This is a special case for handler
	// code since the HandleCommandL method is mostly entirely generated.
	var event = instance.events['handleCommand'];
	var hasFormChild = findAknFormChild(children) != null;
	var childWantsExit = childWantsExitBehavior(children);
	var id = findCbaRightId(children);
	if (event) { 
%>	// dispatch to ${event.eventName} handler
	commandHandled = ${event.handlerName}( aCommand );
<%		if (!hasFormChild && childWantsExit && id != null && id != "") { %>		
	if ( !commandHandled ) 
		{
		if ( aCommand == ${id} )
			{
			AppUi()->HandleCommandL( EEikCmdExit );
			}
		}<% 
		}
	} else if (!hasFormChild && childWantsExit && id != null && id != "") { %>
	if ( aCommand == ${id} )
		{
		AppUi()->HandleCommandL( EEikCmdExit );
		}
<% } %>
	}
]]>			</template>
			<!-- only define; declared above -->
			<expandMacro name="DefineMethodWithOwnedBody"
				DefnLocation="MAIN_FILE"
				IsOwned="false"
				FunctionLocationId="DOACTIVATEL_METHOD"
				FunctionName="DoActivateL"
				ReturnType="void"
				FunctionArgs="const TVwsViewId&amp; /*aPrevViewId*/, TUid /*aCustomMessageId*/, const TDesC8&amp; /*aCustomMessage*/">
				<expandArgument name="FunctionComment">
/**
 *	Handles user actions during activation of the view, 
 *	such as initializing the content.
 */
				</expandArgument>
				<expandArgument name="FunctionBody"><![CDATA[
SetupStatusPaneL();
<% var cba = findImmediateChildByComponentId(children, "com.nokia.sdt.series60.CBABase");
   if (cba && getComponentVersions().getMajor() < 3) {%>
CEikButtonGroupContainer* cba = AppUi()->Cba();
if ( cba != NULL ) 
	{
	cba->MakeVisible( EFalse );
	}
<% } %>
]]>				</expandArgument>		
			</expandMacro>	

			<!-- only define; declared above -->
			<expandMacro name="DefineMethodWithOwnedBody"
				DefnLocation="MAIN_FILE"
				IsOwned="false"
				FunctionLocationId="DODEACTIVATE_METHOD"
				FunctionName="DoDeactivate"
				ReturnType="void"
				FunctionArgs="">
				<expandArgument name="FunctionComment">
/**
 */
				</expandArgument>
			</expandMacro>	
				
					
			<!-- START EVENT deactivated: placed here since it must precede standard code -->

			<templateGroup ifEvents="deactivated">
				<!-- API impl above -->
				
				<expandMacro name="GenerateUserEventHandlerFunction"
					DefnLocation="MAIN_FILE"
					DeclLocation="CLASS_PROTECTED_USER_HANDLERS"
					ReturnType="void"
					FunctionArgs=""
					FunctionLocationId="DeactivatedHandlerBody" />

				<template location="DODEACTIVATE_METHOD_BODY">
${event.handlerName}();
				</template>

				<!--
				<template phase="UserHandlers">
void ${event.handlerName}();
				</template>

				<defineLocation id="DeactivatedHandlerBody" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}())">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 */
void ${handlerClassName}::${event.handlerName}()
	{
	// TODO: implement ${event.eventName} event handler
	}
]]>				
					</template>
				</defineLocation>
			
				<template location="DeactivatedHandlerBody" />
					-->
			</templateGroup>

			<!-- END EVENT deactivated -->
			
			<template location="DODEACTIVATE_METHOD_BODY"><![CDATA[
CleanupStatusPane();
<% var cba = findImmediateChildByComponentId(children, "com.nokia.sdt.series60.CBABase");
   if (cba && getComponentVersions().getMajor() < 3) {%>
CEikButtonGroupContainer* cba = AppUi()->Cba();
if ( cba != NULL ) 
	{
	cba->MakeVisible( ETrue );
	cba->DrawDeferred();
	}
<% } %>
]]>			</template>

			<!-- only define; declared above -->
			<!-- any navi container of StatusPane fills this in -->
			<expandMacro name="DefineMethod"
				DefnLocation="MAIN_FILE"
				IsOwned="true"
				FunctionLocationId="SETUPSTATUSPANEL_METHOD"
				FunctionName="SetupStatusPaneL"
				ReturnType="void"
				FunctionArgs="">
				<expandArgument name="FunctionComment">
				</expandArgument>
			</expandMacro>	

			<!-- only define; declared above -->
			<!-- any navi container of StatusPane fills this in -->
			<expandMacro name="DefineMethod"
				DefnLocation="MAIN_FILE"
				IsOwned="true"
				FunctionLocationId="CLEANUPSTATUSPANE_METHOD"
				FunctionName="CleanupStatusPane"
				ReturnType="void"
				FunctionArgs="">
				<expandArgument name="FunctionComment">
				</expandArgument>
			</expandMacro>	

			<!-- only define; declared above -->
			<expandMacro name="DefineMethodWithOwnedBody"
				DefnLocation="MAIN_FILE"
				IsOwned="false"
				FunctionLocationId="HANDLESTATUSPANESIZECHANGE_METHOD"
				FunctionName="HandleStatusPaneSizeChange"
				ReturnType="void"
				FunctionArgs=""
				OwnedRegionName="Generated Code">
				<expandArgument name="FunctionComment">
/** 
 * Handle status pane size change for this view (override)
 */
				</expandArgument>
				<expandArgument name="StartFunctionBody">
CAknView::HandleStatusPaneSizeChange();

// this may fail, but we're not able to propagate exceptions here
TVwsViewId view;
AppUi()->GetActiveViewId( view );
if ( view.iViewUid == Id() )
	{
	TInt result;
	TRAP( result, SetupStatusPaneL() );
	}
				</expandArgument>		
			</expandMacro>	

			<template mode="upgrade(1.1,1.2)" location="HANDLESTATUSPANESIZECHANGE_METHOD">
@@@ Remove call to SetupStatusPaneL() from HandleStatusPaneSizeChange()
- TInt result;
- TRAP( result, SetupStatusPaneL() ); 
+ TVwsViewId view;
+ AppUi()->GetActiveViewId( view );
+ if ( view.iViewUid == Id() )
+ 	{
+ 	TInt result;
+ 	TRAP( result, SetupStatusPaneL() );
+ 	}
			</template>
			
			<!-- event handling code -->

			<templateGroup ifEvents="handleCommand">
			
				<!-- API half and invocation in HandleCommandL code above -->
				
				<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="CommandHandlerMethod"
					FunctionArgs="TInt aCommand"
					ReturnType="TBool"
					DefaultReturn="EFalse"
					DeclLocation="CLASS_PROTECTED_USER_HANDLERS"
					DefnLocation="MAIN_FILE">
					<expandArgument name="FunctionComment">
/** 
 * Handle the ${event.eventName} event.
 * @return ETrue if event was handled, EFalse if not
 */
					</expandArgument>
				</expandMacro>
				
			</templateGroup>
			
			<!-- START EVENT handleStatusPaneSizeChange -->
			
			<templateGroup ifEvents="handleStatusPaneSizeChange">

				<!-- API impl above -->

				<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="StatusPaneSizeChangeHandlerMethod"
					FunctionArgs=""
					DeclLocation="CLASS_PROTECTED_USER_HANDLERS"
					DefnLocation="MAIN_FILE">
				</expandMacro>
				
				<template location="HANDLESTATUSPANESIZECHANGE_METHOD_BODY">
${event.handlerName}();
				</template>

			</templateGroup>

			<!-- END EVENT handleStatusPaneSizeChange -->

			<inline>
			if (!Engine.formMatches(form, ["RootAppUi"])) {
				this.getHrhFiles(contribs);
			}
			</inline>

			<!-- child contributions -->
			
			<inline scope="prototype">
			include("../srcgenLibrary.js");
			</inline>
			
			<inline>
			
			if (!Engine.formMatches(form, ["RootAppUi"])) {
				// clear out any image stuff
				resetImagePropertyState(instance);
				
				// We expect one container child to supply the contents
				// for the phases below.
				
				// gather default and view-specific contributions
				contribs.addAll(Engine.generateChildContributions("|AknView"))
			}
						
			</inline>		
			
			<!-- START EVENT activated: handle here to ensure it's the last call -->
					
			<templateGroup ifEvents="activated">

				<!-- API impl above -->

				<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="ActivatedHandlerBody"
					FunctionArgs=""
					DeclLocation="CLASS_PROTECTED_USER_HANDLERS"
					DefnLocation="MAIN_FILE">
				</expandMacro>

				<template location="DOACTIVATEL_METHOD_BODY">
${event.handlerName}();
				</template>
			</templateGroup>

			<!-- END EVENT activated -->
			
			
			<inline>
			
			if (!Engine.formMatches(form, ["RootAppUi"])) {
				// fix up the phases so they point to real locations
				Engine.assignLocationsForPhase(contribs, "ClassSource", "MAIN_FILE");
				Engine.assignLocationsForPhase(contribs, "ClassHeader", "HEADER_FILE");
	
				Engine.assignLocationsForPhase(contribs, "MainConstants", "MAIN_OWNED_CONSTANTS");
				Engine.assignLocationsForPhase(contribs, "MainSystemIncludes", "MAIN_OWNED_SYSTEM_INCLUDES");
				Engine.assignLocationsForPhase(contribs, "MainUserIncludes", "MAIN_OWNED_USER_INCLUDES");
				Engine.assignLocationsForPhase(contribs, "Construct", "CONSTRUCTOR_BODY");
				Engine.assignLocationsForPhase(contribs, "Initialize", "CONSTRUCTL_METHOD_BODY");
				Engine.assignLocationsForPhase(contribs, "Destroy", "DESTRUCTOR_BODY");
				Engine.assignLocationsForPhase(contribs, "DoActivateL", "DOACTIVATEL_METHOD_BODY");
				Engine.assignLocationsForPhase(contribs, "DoDeactivate", "DODEACTIVATE_METHOD_BODY");
	
				Engine.assignLocationsForPhase(contribs, "HeaderIncludes", "HEADER_OWNED_SYSTEM_INCLUDES");
				Engine.assignLocationsForPhase(contribs, "ForwardDeclarations", "HEADER_OWNED_FORWARD_DECLARATIONS");
				Engine.assignLocationsForPhase(contribs, "InstanceVariables", "CLASS_PRIVATE_OWNED_INSTANCE_VARIABLES");
				Engine.assignLocationsForPhase(contribs, "ClassMethods", "CLASS_PUBLIC_OWNED_METHODS");

				Engine.assignLocationsForPhase(contribs, "SetupStatusPane", "SETUPSTATUSPANEL_METHOD");
				Engine.assignLocationsForPhase(contribs, "CleanupStatusPane", "CLEANUPSTATUSPANE_METHOD");

				Engine.assignLocationsForPhase(contribs, "OverriddenMethods", "CLASS_PROTECTED_OVERRIDDEN_METHODS");
				Engine.assignLocationsForPhase(contribs, "UserHandlers", "CLASS_PROTECTED_USER_HANDLERS");
				Engine.assignLocationsForPhase(contribs, "EventIncludes", "HEADER_EVENT_HANDLER_INCLUDES");
				Engine.assignLocationsForPhase(contribs, "BaseClassList", "CLASS_BASES");
				
				Engine.assignLocationsForPhase(contribs, "ClassPublic", "CLASS_PUBLIC");
				Engine.assignLocationsForPhase(contribs, "ClassProtected", "CLASS_PROTECTED");
				Engine.assignLocationsForPhase(contribs, "ClassPrivate", "CLASS_PRIVATE");
			}
						
			</inline>		

			<template id="IncludeRsgFile" location="MAIN_OWNED_SYSTEM_INCLUDES"><![CDATA[
#include <${resourceFileNameBase}.rsg>
]]>			</template>

			<!-- contributions as child -->
			
			<template phase="MainUserIncludes" form="RootAppUi">
#include "${instanceName}.h"
			</template>

			<template phase="ForwardDeclarations" form="RootAppUi"><![CDATA[
class ${className};
]]>			</template>

			<template phase="InstanceVariables" form="RootAppUi"><![CDATA[
${className}* ${instanceMemberName};
]]>			</template>

			<template phase="Initialize" form="RootAppUi"><![CDATA[
${instanceMemberName} = ${className}::NewL();
AddViewL( ${instanceMemberName} );
]]>			</template>

			<template phase="Initialize" form="RootAppUi_InitialView"><![CDATA[
SetDefaultViewL( *${instanceMemberName} );
]]>			</template>

			<template phase="Initialize" form="RootAppUi_SetActiveTab"><![CDATA[
SetActiveTabByViewId( ${instanceMemberName}->Id() );
]]>			</template>

			<!-- patch from Carbide.c++ 1.2 by adding the regions -->

			<!-- note: these patches are kind of ugly.
				Since we can't unindent the access specifiers, we have to
				add the patches to the main file. -->
			<template mode="upgrade(1.0,1.1)" location="HEADER_FILE"><![CDATA[
@@@ Update class region comments to include public/protected/private section				
- 	// [[[ begin generated region: do not modify [Generated Methods]
+ 	// [[[ begin [Public Section]
+ public:
+ 	// [[[ begin generated region: do not modify [Generated Methods]
- public:
]]>			</template>

			<template mode="upgrade(1.0,1.1)" location="HEADER_FILE"><![CDATA[
@@@ Update class region comments to include public/protected/private section
- 	// [[[ begin [Overridden Methods]
+ 				
+ 	// ]]] end [Public Section]
+ 				
+ 	// [[[ begin [Protected Section]
+ protected:
+ 	// [[[ begin [Overridden Methods]
- protected:
]]>			</template>

			<template mode="upgrade(1.0,1.1)" location="HEADER_FILE"><![CDATA[
@@@ Update class region comments to include public/protected/private section
- 	// [[[ begin generated region: do not modify [Generated Instance Variables]
+ 
+ 	// ]]] end [Protected Section]
+ 
+ 	// [[[ begin [Private Section]
+ private:
+ 	// [[[ begin generated region: do not modify [Generated Instance Variables]
- private:
]]>			</template>
			
			<template mode="upgrade(1.0,1.1)" location="HEADER_FILE"><![CDATA[
@@@ Update class region comments to include public/protected/private section
- 	// ]]] end generated region [Generated Instance Variables]
+ 	// ]]] end generated region [Generated Instance Variables]
+ 
+ 	// ]]] end [Private Section]
]]>			</template>
						
		</sourceGen>
