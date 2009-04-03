			<!-- locations provided by component -->

			<defineLocation id="HEADER_FILE" domain="cpp" dir="${inc}" 
				file="${instanceName}.h"
				owned="false"
				location="">
			</defineLocation>
			
			<template location="HEADER_FILE">
#ifndef ${instanceName.toUpperCase()}_H
#define ${instanceName.toUpperCase()}_H

			</template>
			
			<defineLocation id="HEADER_INCLUDES" baseLocation="HEADER_FILE"
				location="region(Generated Includes)"
				filter="unique-includes"
				>
			</defineLocation>		

			<template location="HEADER_INCLUDES"><![CDATA[
#include <aknappui.h>
]]>			</template>
			<template location="HEADER_INCLUDES"><![CDATA[
#include <eikmenub.h>
]]>			</template>

			<defineLocation id="FORWARD_DECLARATIONS" baseLocation="HEADER_FILE"
				location="region(Generated Forward Declarations)">
			</defineLocation>		

			<template location="FORWARD_DECLARATIONS"/>
			
			<defineLocation id="CLASS" baseLocation="HEADER_FILE"
				owned="false"
				location="class(${className})">
				<template><![CDATA[
/**
 * @class	${className} ${instanceName}.h
 * @brief The AppUi class handles application-wide aspects of the user interface, including
 *        container management and the default menu, control pane, and status pane.
 */
class ${className} : public CAknAppUi
	{
public: 
	// constructor and destructor
	${className}();
	void ConstructL();
	virtual ~${className}();

public: 
	// from CEikAppUi
	void HandleCommandL( TInt aCommand );
	void HandleResourceChangeL( TInt aType );	
	
private:
	void InitializeContainersL();
	};
]]>
				</template>
			</defineLocation>
			
			<template location="CLASS"/>
			
			<defineLocation id="CLASS_METHODS" baseLocation="CLASS"
				filter="unique-prototypes"
				location="region(Generated Methods)">
				<template><![CDATA[
public: <% contrib.indentAdjust(-1); %>
]]>				</template>
			</defineLocation>

			<template location="CLASS_METHODS"/>
					
			<defineLocation id="CLASS_IVARS" baseLocation="CLASS"
			location="region(Generated Instance Variables)">
				<template><![CDATA[
private: <% contrib.indentAdjust(-1); %>
]]>				</template>
			</defineLocation>

			<template location="CLASS_IVARS"/>
			
			<defineLocation id="BASE_CLASS_LIST" baseLocation="CLASS"
				owned="false"
				filter="unique-bases"
				location="bases()">
			</defineLocation>
			
			<template location="BASE_CLASS_LIST" />

			<defineLocation id="CLASS_OVERRIDES" baseLocation="CLASS"
				owned="false"
				filter="unique-prototypes"
				location="region(Overridden Methods)">
				<template><![CDATA[
protected: <% contrib.indentAdjust(-1); %>
]]>				</template>
				
			</defineLocation>			
				
			<template location="CLASS_OVERRIDES"/>

			<defineLocation id="CLASS_USERHANDLERS" baseLocation="CLASS"
				owned="false"
				filter="unique-prototypes"
				location="region(User Handlers)">
				<template><![CDATA[
protected: <% contrib.indentAdjust(-1); %>
]]>				</template>
				
			</defineLocation>				
			
			<template location="CLASS_USERHANDLERS"/>

			<template location="HEADER_FILE">
#endif // ${instanceName.toUpperCase()}_H			
			</template>
			

			<defineLocation id="MAIN_FILE" domain="cpp" dir="${src}" 
				file="${instanceName}.cpp"
				owned="false"
				location="">
			</defineLocation>
			
			<template location="MAIN_FILE"/>
			
			<defineLocation id="MAIN_SYSTEM_INCLUDES" baseLocation="MAIN_FILE"
				location="region(Generated System Includes)"
				filter="unique-includes">
				<template><![CDATA[
#include <eikmenub.h>
]]>				</template>
			</defineLocation>
		
			<template location="MAIN_SYSTEM_INCLUDES" />
				
			<defineLocation id="MAIN_USER_INCLUDES" baseLocation="MAIN_FILE"
				location="region(Generated User Includes)"
				filter="unique-includes">
				<template><![CDATA[
#include "${instanceName}.h"
]]>				</template>
			</defineLocation>	
			
			<template location="MAIN_USER_INCLUDES"/>

			<defineLocation id="CONSTANTS" baseLocation="MAIN_FILE"
				location="region(Generated Constants)">
			</defineLocation>

			<template location="CONSTANTS" />

			<defineLocation id="CONSTRUCTOR" baseLocation="MAIN_FILE"
				owned="false"
				location="function(${className}::${className}())">
				<template><![CDATA[
/**
 * Construct the ${className} instance
 */ 
${className}::${className}()
	{
	}
]]>
				</template>			
			</defineLocation>
			
			<template location="CONSTRUCTOR"/>

			<defineLocation id="CONSTRUCTOR_BODY" baseLocation="CONSTRUCTOR"
				location="region(Generated Contents)">
			</defineLocation>
					
			<template location="CONSTRUCTOR_BODY"/>

			<defineLocation id="DESTRUCTOR" baseLocation="MAIN_FILE"
				owned="false"
				location="function(${className}::~${className}())">
				<template><![CDATA[
/** 
 * The appui's destructor removes the container from the control
 * stack and destroys it.
 */
${className}::~${className}()
	{
	}
]]>
				</template>			
			</defineLocation>
			
			<template location="DESTRUCTOR"/>

			<defineLocation id="DESTRUCTOR_BODY" baseLocation="DESTRUCTOR"
				location="region(Generated Contents)">
			</defineLocation>
			
			<template location="DESTRUCTOR_BODY"/>

			<defineLocation id="CONSTRUCTL_METHOD" baseLocation="MAIN_FILE"
				owned="false"
				location="function(${className}::ConstructL())">
				<template><![CDATA[
/**
 * @brief Completes the second phase of Symbian object construction. 
 * Put initialization code that could leave here. 
 */ 
void ${className}::ConstructL()
	{
	}
]]>
				</template>			
			</defineLocation>
			
			<template location="CONSTRUCTL_METHOD"/>

			<defineLocation id="CONSTRUCTL_BODY" baseLocation="CONSTRUCTL_METHOD"
				location="region(Generated Contents)">
			</defineLocation>
			
			<template id="MakeConstructL" location="CONSTRUCTL_BODY"><![CDATA[
<% var version = getComponentVersions();
if (version.major < 3 || (version.major == 3 && version.minor < 2)) { %>
BaseConstructL(${(properties.skinsSupported ? " EAknEnableSkin " : "")}); 
<% } else { %>
BaseConstructL(${(properties.skinsSupported ? " EAknEnableSkin " : "")}<% %>${(properties.skinsSupported && properties.enableMSK ? " | " : "")}
				${(properties.enableMSK ? " EAknEnableMSK " : "")}); 
<% }
	// In 2.x, this flag is false.  
	// In 2.8, the default is true but may be false.
	// In 3.0, the default is true and must remain so.
if (version.major == 2 && version.minor == 8 && !properties.layoutAware) { %>
SetLayoutAwareApp( false );
<% }
	if (properties.orientation && properties.orientation != "EAppUiOrientationUnspecified") { 
%>SetOrientationL( ${properties.orientation} );
<% }
	var event = instance.events['viewEvent'];
	if (event) { %>
// Enable support for view events. They will be dispatched to ${event.handlerName}()
AddViewObserverL( this );
<% } %>InitializeContainersL();
]]>		</template>


			<defineLocation id="INIT_CONTAINERSL" baseLocation="MAIN_FILE"
				owned="true"
				location="function(${className}::InitializeContainersL())">
				<template><![CDATA[
void ${className}::InitializeContainersL()
	{
	}
]]>				</template>
			</defineLocation>

			<template location="INIT_CONTAINERSL"/>
			
			<defineLocation id="HANDLECOMMANDL_METHOD" baseLocation="MAIN_FILE"
				owned="false"
				location="function(${className}::HandleCommandL(TInt))">
				<template><![CDATA[
/**
 * Handle a command for this appui (override)
 * @param aCommand command id to be handled
 */
void ${className}::HandleCommandL( TInt aCommand )
	{
	}
]]>
				</template>
			</defineLocation>

			<template location="HANDLECOMMANDL_METHOD"/>

			<defineLocation id="HANDLECOMMANDL_BODY" baseLocation="HANDLECOMMANDL_METHOD"
				owned="true"
				location="region(Generated Code)">
			</defineLocation>

			<template location="HANDLECOMMANDL_BODY" id="GenerateHandleCommandBody"><![CDATA[
TBool commandHandled = EFalse;
switch ( aCommand )
	{ // code to dispatch to the AppUi's menu and CBA commands is generated here
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
	handlerContribs.removeAll(postSwitchContribs);
	Engine.assignLocationsForPhase(postSwitchContribs, "PostSwitchContents", "HANDLECOMMANDL_BODY")
	contribs.addAll(postSwitchContribs); 
	
	// add the rest, which presumably point to other locations
	contribs.addAll(handlerContribs); %>	
if ( !commandHandled ) 
	{<%
	var event = instance.events['handleCommand'];
	if (event) { 
%>		// dispatch to ${event.eventName} handler
	commandHandled = ${event.handlerName}( aCommand );
	if ( !commandHandled ) 
		{
		if ( aCommand == EAknSoftkeyExit || aCommand == EEikCmdExit )
			{
			Exit();
			}
		}<% } else { %>
	if ( aCommand == EAknSoftkeyExit || aCommand == EEikCmdExit )
		{
		Exit();
		}
<% }
%>	}
]]>			</template>

			<defineLocation id="HANDLERESOURCECHANGEL_METHOD" baseLocation="MAIN_FILE"
					owned="false"
					location="function(${handlerClassName}::HandleResourceChangeL(TInt))">
					<template><![CDATA[
/** 
 * Override of the HandleResourceChangeL virtual function
 */
void ${handlerClassName}::HandleResourceChangeL( TInt aType )
	{
	${instance.attributes["base-appui-class"]}::HandleResourceChangeL( aType );
	}
]]>				
					</template>
			</defineLocation>
				
			<template location="HANDLERESOURCECHANGEL_METHOD"/>

			<defineLocation id="HANDLERESOURCECHANGEL_BODY" baseLocation="HANDLERESOURCECHANGEL_METHOD"
					owned="true"
					location="region(Generated Code)">
			</defineLocation>

			<template location="HANDLERESOURCECHANGEL_BODY"/>


<!-- BEGIN EVENT handleKeyEvent -->
			<templateGroup id="GenerateHandleKeyEventHandler" ifEvents="handleKeyEvent">
			
				<template id="OverrideDecl" phase="OverriddenMethods"><![CDATA[
TKeyResponse HandleKeyEventL( 
		const TKeyEvent& aKeyEvent,
		TEventCode aType );
]]>				</template>
				<template id="HandlerDecl" phase="UserHandlers"><![CDATA[
TKeyResponse ${event.handlerName}( 
		const TKeyEvent& aKeyEvent, 
		TEventCode aType );
]]>				</template>
	
				<defineLocation id="KeyEventOverride_Method" baseLocation="MAIN_FILE"
					owned="false"
					location="function(${handlerClassName}::HandleKeyEventL(const TKeyEvent&amp;,TEventCode))">
					<template><![CDATA[
/** 
 * Override of the HandleKeyEventL virtual function
 * @return EKeyWasConsumed if event was handled, EKeyWasNotConsumed if not
 */
TKeyResponse ${handlerClassName}::HandleKeyEventL( 
		const TKeyEvent& aKeyEvent, 
		TEventCode aType )
	{
		// The inherited HandleKeyEventL is private and cannot be called
	}
]]>				
					</template>
				</defineLocation>
				
				<template id="RealizeOverrideMethod" location="KeyEventOverride_Method"/>
								
				<defineLocation id="KeyEventOverride_Body" baseLocation="KeyEventOverride_Method"
					owned="true"
					location="region(Generated Code)">
				</defineLocation>
				
				<template id="DispatchToHandler" location="KeyEventOverride_Body"><![CDATA[
if ( ${event.handlerName}( aKeyEvent, aType ) == EKeyWasConsumed )
	{
	return EKeyWasConsumed;
	}
]]>				</template>
								
				<defineLocation id="KeyEventHandler" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}(const TKeyEvent&amp;, TEventCode))">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 * @return EKeyWasConsumed if event was handled, EKeyWasNotConsumed if not
 */
TKeyResponse ${handlerClassName}::${event.handlerName}( 
		const TKeyEvent& /* aKeyEvent */, 
		TEventCode /* aType */ )
	{
	// TODO: implement ${event.eventName} event handler
	return EKeyWasNotConsumed;
	}
]]>				
					</template>
				</defineLocation>
			
				<template id="RealizeHandler" location="KeyEventHandler"/>
			</templateGroup>
<!-- END EVENT handleKeyEvent -->

<!-- BEGIN EVENT handleCommand -->
			<templateGroup id="GenerateHandleCommandEventHandler" ifEvents="handleCommand">
			
				<template id="1" phase="UserHandlers">
TBool ${event.handlerName}( TInt /* aCommand */);
				</template>
			
				<defineLocation id="CommandHandlerMethod" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}(TInt))">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 * @return ETrue if event was handled, EFalse if not
 */
TBool ${handlerClassName}::${event.handlerName}( TInt /* aCommand */)
	{
	// TODO: implement ${event.eventName} event handler
	return EFalse;
	}
]]>				
					</template>
				</defineLocation>
			
				<template id="2" location="CommandHandlerMethod"/>
			</templateGroup>
<!-- END EVENT handleCommand -->

<!-- BEGIN EVENT foregroundEvent -->
			<templateGroup id="GenerateForegroundEventHandler" ifEvents="foregroundEvent">
			
				<template id="OverrideDecl" phase="OverriddenMethods"><![CDATA[
void HandleForegroundEventL( TBool aForeground );
]]>				</template>
				<template id="HandlerDecl" phase="UserHandlers"><![CDATA[
void ${event.handlerName}( TBool aForeground );
]]>				</template>
	
				<defineLocation id="HandleForegroundOverride_Method" baseLocation="MAIN_FILE"
					owned="false"
					location="function(${handlerClassName}::HandleForegroundEventL(TBool))">
					<template><![CDATA[
/** 
 * Override of the HandleForegroundEventL virtual function
 */
void ${handlerClassName}::HandleForegroundEventL( TBool aForeground )
	{
	${instance.attributes["base-appui-class"]}::HandleForegroundEventL( aForeground );
	}
]]>				
					</template>
				</defineLocation>
				
				<template id="RealizeOverrideMethod" location="HandleForegroundOverride_Method"/>
								
				<defineLocation id="HandleForegroundOverride_Body" baseLocation="HandleForegroundOverride_Method"
					owned="true"
					location="region(Generated Code)">
				</defineLocation>
				
				<template id="DispatchToHandler" location="HandleForegroundOverride_Body"><![CDATA[
${event.handlerName}( aForeground );
]]>				</template>
								
				<defineLocation id="ForegroundEventHandler" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}(TBool))">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 */
void ${handlerClassName}::${event.handlerName}( TBool /* aForeground */ )
	{
	// TODO: implement ${event.eventName} event handler
	}
]]>				
					</template>
				</defineLocation>
			
				<template id="RealizeHandler" location="ForegroundEventHandler"/>
			</templateGroup>
<!-- END EVENT foregroundEvent -->

<!-- BEGIN EVENT switchOnEvent -->
			<templateGroup id="GenerateSwitchOnEventHandler" ifEvents="switchOnEvent">
			
				<template id="OverrideDecl" phase="OverriddenMethods"><![CDATA[
void HandleSwitchOnEventL( CCoeControl* aDestination );
]]>				</template>
				<template id="HandlerDecl" phase="UserHandlers"><![CDATA[
void ${event.handlerName}();
]]>				</template>
	
				<defineLocation id="HandleSwitchOnOverride_Method" baseLocation="MAIN_FILE"
					owned="false"
					location="function(${handlerClassName}::HandleSwitchOnEventL(CCoeControl*))">
					<template><![CDATA[
/** 
 * Override of the HandleSwitchOnEventL virtual function
 */
void ${handlerClassName}::HandleSwitchOnEventL( CCoeControl* /* aDestination */ )
	{
	// The inherited HandleSwitchOnEventL is private and cannot be called
	}
]]>				
					</template>
				</defineLocation>
				
				<template id="RealizeOverrideMethod" location="HandleSwitchOnOverride_Method"/>
								
				<defineLocation id="HandleSwitchOnOverride_Body" baseLocation="HandleSwitchOnOverride_Method"
					owned="true"
					location="region(Generated Code)">
				</defineLocation>
				
				<template id="DispatchToHandler" location="HandleSwitchOnOverride_Body"><![CDATA[
${event.handlerName}();
]]>				</template>
								
				<defineLocation id="SwitchOnEventHandler" baseLocation="MAIN_FILE"
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
			
				<template id="RealizeHandler" location="SwitchOnEventHandler"/>
			</templateGroup>
<!-- END EVENT switchOnEvent -->

<!-- BEGIN EVENT systemEvent -->
			<templateGroup id="GenerateSystemEventHandler" ifEvents="systemEvent">
			
				<template id="OverrideDecl" phase="OverriddenMethods"><![CDATA[
void HandleSystemEventL( const TWsEvent& anEvent );
]]>				</template>
				<template id="HandlerDecl" phase="UserHandlers"><![CDATA[
void ${event.handlerName}( const TWsEvent& /* anEvent */);
]]>				</template>
	
				<defineLocation id="HandleSystemOverride_Method" baseLocation="MAIN_FILE"
					owned="false"
					location="function(${handlerClassName}::HandleSystemEventL(const TWsEvent&amp;))">
					<template><![CDATA[
/** 
 * Override of the HandleSystemEventL virtual function
 */
void ${handlerClassName}::HandleSystemEventL( const TWsEvent& anEvent )
	{
	${instance.attributes["base-appui-class"]}::HandleSystemEventL( anEvent );
	}
]]>				
					</template>
				</defineLocation>
				
				<template id="RealizeOverrideMethod" location="HandleSystemOverride_Method"/>
								
				<defineLocation id="HandleSystemOverride_Body" baseLocation="HandleSystemOverride_Method"
					owned="true"
					location="region(Generated Code)">
				</defineLocation>
				
				<template id="DispatchToHandler" location="HandleSystemOverride_Body"><![CDATA[
${event.handlerName}( anEvent );
]]>				</template>
								
				<defineLocation id="SystemEventHandler" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}(const TWsEvent&amp;))">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 */
void ${handlerClassName}::${event.handlerName}( const TWsEvent& /* anEvent */ )
	{
	// TODO: implement ${event.eventName} event handler
	}
]]>				
					</template>
				</defineLocation>
			
				<template id="RealizeHandler" location="SystemEventHandler"/>
			</templateGroup>
<!-- END EVENT systemEvent -->

<!-- BEGIN EVENT applicationSpecificEvent -->
			<templateGroup id="GenerateApplicationSpecificEventHandler" ifEvents="applicationSpecificEvent">
			
				<template id="OverrideDecl" phase="OverriddenMethods"><![CDATA[
void HandleApplicationSpecificEventL( 
		TInt aType, 
		const TWsEvent& anEvent );
]]>				</template>
				<template id="HandlerDecl" phase="UserHandlers"><![CDATA[
void ${event.handlerName}( 
		TInt aType, 
		const TWsEvent& anEvent );
]]>				</template>
	
				<defineLocation id="HandleApplicationSpecificOverride_Method" baseLocation="MAIN_FILE"
					owned="false"
					location="function(${handlerClassName}::HandleApplicationSpecificEventL(TInt, const TWsEvent&amp;))">
					<template><![CDATA[
/** 
 * Override of the HandleApplicationSpecificEventL virtual function
 */
void ${handlerClassName}::HandleApplicationSpecificEventL( 
		TInt aType, 
		const TWsEvent& anEvent )
	{
	${instance.attributes["base-appui-class"]}::HandleApplicationSpecificEventL( aType, anEvent );
	}
]]>				
					</template>
				</defineLocation>
				
				<template id="RealizeOverrideMethod" location="HandleApplicationSpecificOverride_Method"/>
								
				<defineLocation id="HandleApplicationSpecificOverride_Body" baseLocation="HandleApplicationSpecificOverride_Method"
					owned="true"
					location="region(Generated Code)">
				</defineLocation>
				
				<template id="DispatchToHandler" location="HandleApplicationSpecificOverride_Body"><![CDATA[
${event.handlerName}( aType, anEvent );
]]>				</template>
								
				<defineLocation id="ApplicationSpecificEventHandler" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}(TInt, const TWsEvent&amp;))">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 */
void ${handlerClassName}::${event.handlerName}( 
		TInt /* aType */, 
		const TWsEvent& /* anEvent */ )
	{
	// TODO: implement ${event.eventName} event handler
	}
]]>				
					</template>
				</defineLocation>
			
				<template id="RealizeHandler" location="ApplicationSpecificEventHandler"/>
			</templateGroup>
<!-- END EVENT applicationSpecificEvent -->

<!-- BEGIN EVENT handleStatusPaneSizeChanged -->
			<templateGroup id="GenerateHandleStatusPaneSizeChangeHandler" ifEvents="handleStatusPaneSizeChanged">
			
				<template id="OverrideDecl" phase="OverriddenMethods"><![CDATA[
void HandleStatusPaneSizeChange();
]]>				</template>
				<template id="HandlerDecl" phase="UserHandlers"><![CDATA[
void ${event.handlerName}();
]]>				</template>
	
				<defineLocation id="HandleStatusPaneSizeChangeOverride_Method" baseLocation="MAIN_FILE"
					owned="false"
					location="function(${handlerClassName}::HandleStatusPaneSizeChange())">
					<template><![CDATA[
/** 
 * Override of the HandleStatusPaneSizeChange virtual function
 */
void ${handlerClassName}::HandleStatusPaneSizeChange()
	{
	${instance.attributes["base-appui-class"]}::HandleStatusPaneSizeChange();
	}
]]>				
					</template>
				</defineLocation>
				
				<template id="RealizeOverrideMethod" location="HandleStatusPaneSizeChangeOverride_Method"/>
								
				<defineLocation id="HandleStatusPaneSizeChangedOverride_Body" baseLocation="HandleStatusPaneSizeChangeOverride_Method"
					owned="true"
					location="region(Generated Code)">
				</defineLocation>
				
				<template id="DispatchToHandler" location="HandleStatusPaneSizeChangedOverride_Body"><![CDATA[
${event.handlerName}();
]]>				</template>
								
				<defineLocation id="HandleStatusPaneSizeChangeHandler" baseLocation="MAIN_FILE"
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
			
				<template id="RealizeHandler" location="HandleStatusPaneSizeChangeHandler"/>
			</templateGroup>
<!-- END EVENT handleStatusPaneSizeChanged -->

<!-- BEGIN EVENT viewEvent -->
			<templateGroup id="GenerateViewEventHandler" ifEvents="viewEvent">
			
				<template id="BaseDecl" phase="BaseClassList">,public MCoeViewObserver</template>
			
				<template id="OverrideDecl" phase="OverriddenMethods"><![CDATA[
void HandleViewEventL( const TVwsViewEvent& anEvent );
]]>				</template>
				<template id="HandlerDecl" phase="UserHandlers"><![CDATA[
void ${event.handlerName}( const TVwsViewEvent& /* anEvent */);
]]>				</template>
	
				<defineLocation id="HandleViewEventOverride_Method" baseLocation="MAIN_FILE"
					owned="false"
					location="function(${handlerClassName}::HandleViewEventL(const TVwsViewEvent&amp;))">
					<template><![CDATA[
/** 
 * Override of the HandleViewEventL virtual function
 */
void ${handlerClassName}::HandleViewEventL( const TVwsViewEvent& anEvent )
	{
	}
]]>				
					</template>
				</defineLocation>
				
				<template id="RealizeOverrideMethod" location="HandleViewEventOverride_Method"/>
								
				<defineLocation id="HandleViewEventOverride_Body" baseLocation="HandleViewEventOverride_Method"
					owned="true"
					location="region(Generated Code)">
				</defineLocation>
				
				<template id="DispatchToHandler" location="HandleViewEventOverride_Body"><![CDATA[
${event.handlerName}( anEvent );
]]>				</template>
								
				<defineLocation id="HandleViewEventHandler" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}(const TVwsViewEvent&amp;))">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 */
void ${handlerClassName}::${event.handlerName}( const TVwsViewEvent& /* anEvent */)
	{
	// TODO: implement ${event.eventName} event handler
	}
]]>				
					</template>
				</defineLocation>
			
				<template id="RealizeHandler" location="HandleViewEventHandler"/>
			</templateGroup>
<!-- END EVENT viewEvent -->

<!-- BEGIN EVENT initializeMenuPane -->
			<templateGroup id="GenerateInitMenuPaneHandler" ifEvents="initializeMenuPane">
			
				<template id="OverrideDecl" phase="OverriddenMethods"><![CDATA[
void DynInitMenuPaneL( TInt aResourceId, CEikMenuPane* aMenuPane );
]]>				</template>
				<template id="HandlerDecl" phase="UserHandlers"><![CDATA[
void ${event.handlerName}( TInt aResourceId , CEikMenuPane* aMenuPane );
]]>				</template>
	
				<defineLocation id="InitMenuPaneOverride_Method" baseLocation="MAIN_FILE"
					owned="false"
					location="function(${handlerClassName}::DynInitMenuPaneL(TInt, CEikMenuPane*))">
					<template><![CDATA[
/** 
 * Override of the DynInitMenuPaneL virtual function
 */
void ${handlerClassName}::DynInitMenuPaneL( TInt aResourceId, CEikMenuPane* aMenuPane )
	{
	${instance.attributes["base-appui-class"]}::DynInitMenuPaneL( aResourceId, aMenuPane );
	}
]]>				
					</template>
				</defineLocation>
				
				<template id="RealizeOverrideMethod" location="InitMenuPaneOverride_Method"/>
								
				<defineLocation id="InitMenuPaneOverride_Body" baseLocation="InitMenuPaneOverride_Method"
					owned="true"
					location="region(Generated Code)">
				</defineLocation>
				
				<template id="DispatchToHandler" location="InitMenuPaneOverride_Body"><![CDATA[
${event.handlerName}( aResourceId, aMenuPane );
]]>				</template>
								
				<defineLocation id="InitMenuPaneHandler" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}(TInt, CEikMenuPane*))">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 */
void ${handlerClassName}::${event.handlerName}( 
		TInt /* aResourceId */, 
		CEikMenuPane* /* aMenuPane */ )
	{
	// TODO: implement ${event.eventName} event handler
	}
]]>				
					</template>
				</defineLocation>
			
				<template id="RealizeHandler" location="InitMenuPaneHandler"/>
			</templateGroup>
<!-- END EVENT initializeMenuPane -->

<!-- BEGIN EVENT offerKeyToApp -->
			<templateGroup id="GenerateOfferKeyToAppHandler" ifEvents="offerKeyToApp">
			
				<template id="OverrideDecl" phase="OverriddenMethods"><![CDATA[
void OfferKeyToAppL( 
		const TKeyEvent& aKeyEvent, 
		TEventCode aType );
]]>				</template>
				<template id="HandlerDecl" phase="UserHandlers"><![CDATA[
void ${event.handlerName}( const TKeyEvent& aKeyEvent, TEventCode aType );
]]>				</template>
	
				<defineLocation id="OfferKeyToAppOverride_Method" baseLocation="MAIN_FILE"
					owned="false"
					location="function(${handlerClassName}::OfferKeyToAppL(const TKeyEvent&amp;, TEventCode))">
					<template><![CDATA[
/** 
 * Override of the OfferKeyToAppL virtual function
 */
void ${handlerClassName}::OfferKeyToAppL( 
		const TKeyEvent& aKeyEvent, 
		TEventCode aType )
	{
	}
]]>				
					</template>
				</defineLocation>
				
				<template id="RealizeOverrideMethod" location="OfferKeyToAppOverride_Method"/>
								
				<defineLocation id="OfferKeyToAppOverride_Body" baseLocation="OfferKeyToAppOverride_Method"
					owned="true"
					location="region(Generated Code)">
				</defineLocation>
				
				<template id="DispatchToHandler" location="OfferKeyToAppOverride_Body"><![CDATA[
${event.handlerName}( aKeyEvent, aType );
]]>				</template>
								
				<defineLocation id="OfferKeyEventHandler" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}(const TKeyEvent&amp;,TEventCode))">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 */
void ${handlerClassName}::${event.handlerName}( 
		const TKeyEvent& /* aKeyEvent */, 
		TEventCode /* aType */ )
	{
	// TODO: implement ${event.eventName} event handler
	}
]]>				
					</template>
				</defineLocation>
			
				<template id="RealizeHandler" location="OfferKeyEventHandler"/>
			</templateGroup>
<!-- END EVENT offerKeyToApp -->

<!-- BEGIN EVENT handleResourceChanged -->
			<templateGroup id="GenerateHandleResourceChangeHandler" ifEvents="handleResourceChanged">

				<!-- API overrides above -->
				<template id="HandlerDecl" phase="UserHandlers"><![CDATA[
void ${event.handlerName}();
]]>				</template>
	
				<template id="DispatchToHandler" location="HANDLERESOURCECHANGEL_BODY"><![CDATA[
${event.handlerName}();
]]>				</template>
								
				<defineLocation id="HandleResourceChangeHandler" baseLocation="MAIN_FILE"
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
			
				<template id="RealizeHandler" location="HandleResourceChangeHandler"/>
			</templateGroup>
<!-- END EVENT handleResourceChanged -->

			<!-- our contributions -->
			
			<inline scope="prototype">
include("../containers/containerLibrary.js")
include("../cba/cbaLibrary.js")
			</inline>			
			
			<inline>
this.getHrhFiles(contribs);
			</inline>

			<inline scope="prototype"><![CDATA[
${jsObject}.prototype.getViewContribs = function(contribs, instance) {
			// gather contributions from views
			for (i in instance.children) {
				var view = instance.children[i];
				if (view.isInstanceOf("com.nokia.sdt.series60.DesignReference") 
				&& (instance.properties.initialDesign == view.name)) {
					var viewContribs = Engine.generateViewContributions(view.properties.filePath, "RootAppUi");
					if (viewContribs != null)
						contribs.addAll(viewContribs);
				}
			}
}
]]>			</inline>

			<inline scope="prototype"><![CDATA[
${jsObject}.prototype.finalizeContribs = function(contribs) {
			// fix up the phases so they point to real locations
			Engine.assignLocationsForPhase(contribs, "ClassSource", "MAIN_FILE");
			Engine.assignLocationsForPhase(contribs, "ClassHeader", "HEADER_FILE");
			
			Engine.assignLocationsForPhase(contribs, "MainConstants", "CONSTANTS");
			Engine.assignLocationsForPhase(contribs, "MainSystemIncludes", "MAIN_SYSTEM_INCLUDES");
			Engine.assignLocationsForPhase(contribs, "MainUserIncludes", "MAIN_USER_INCLUDES");
			Engine.assignLocationsForPhase(contribs, "Construct", "CONSTRUCTOR_BODY");
			Engine.assignLocationsForPhase(contribs, "Destroy", "DESTRUCTOR_BODY");
			Engine.assignLocationsForPhase(contribs, "Initialize", "INIT_CONTAINERSL");
			Engine.assignLocationsForPhase(contribs, "HeaderIncludes", "HEADER_INCLUDES");
			Engine.assignLocationsForPhase(contribs, "ForwardDeclarations", "FORWARD_DECLARATIONS");
			Engine.assignLocationsForPhase(contribs, "BaseClassList", "BASE_CLASS_LIST");
			Engine.assignLocationsForPhase(contribs, "InstanceVariables", "CLASS_IVARS");
			Engine.assignLocationsForPhase(contribs, "ClassMethods", "CLASS_METHODS");
			Engine.assignLocationsForPhase(contribs, "UserHandlers", "CLASS_USERHANDLERS");
			Engine.assignLocationsForPhase(contribs, "OverriddenMethods", "CLASS_OVERRIDES");

			Engine.assignLocationsForPhase(contribs, "HandleResourceChange", "HANDLERESOURCECHANGEL_BODY");

			//Engine.removeDuplicateContributionsForLocation(contribs, "MAIN_SYSTEM_INCLUDES");
			//Engine.removeDuplicateContributionsForLocation(contribs, "MAIN_USER_INCLUDES");
			//Engine.removeDuplicateContributionsForLocation(contribs, "HEADER_INCLUDES");
}
]]>			</inline>

			<inline scope="prototype"><![CDATA[
${jsObject}.prototype.getChildContribs = function(contribs, form) {
			// iterate children
			contribs.addAll(Engine.generateChildContributions(form));
			
			// delete contributions for the status pane, since we initialize these with resources
			Engine.removeContributionsForPhase(contribs, "SetupStatusPane");
			Engine.removeContributionsForPhase(contribs, "CleanupStatusPane");
}
]]>			</inline>
	
			<inline>
			
			this.getChildContribs(contribs, form);
			
			this.getViewContribs(contribs, instance);
			
			this.finalizeContribs(contribs);

			</inline>

			<template id="IncludeRsgFile" location="MAIN_SYSTEM_INCLUDES"><![CDATA[
#include <${resourceFileNameBase}.rsg>
]]>			</template>
			
			<!-- contributions as child (to CAknDocument) -->
	
			<template phase="MainUserIncludes">
#include "${instanceName}.h"
			</template>
		
			<template phase="CreateAppUiL">
return new ( ELeave ) ${className};
			</template>
		
