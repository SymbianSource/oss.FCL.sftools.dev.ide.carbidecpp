			<templateGroup ifEvents="leftSoftKeyPressed">
			
			<template phase="UserHandlers">
TBool ${event.handlerName}( TInt aCommand );
			</template>

			<defineLocation id="LeftSoftKeyPressedMethod" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}(TInt))">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 * @return ETrue if the command was handled, EFalse if not
 */
TBool ${handlerClassName}::${event.handlerName}( TInt aCommand )
	{
	// TODO: implement ${event.eventName} event handler
	return EFalse;
	}
]]>				
					</template>
				</defineLocation>
			
				<template location="LeftSoftKeyPressedMethod" />
	
				<template form="HandleCommandSwitchDispatch" phase="SwitchContents" 
					ifEvents="leftSoftKeyPressed"><![CDATA[<%	
			var leftId = getCbaLeftId(instance);
			if (leftId == "EAknSoftkeyOptions") {
				// S60 explicitly stops further processing when Options is caught by the
				// AknView::HandleCommandL() or AknAppUi::ProcessCommandL() routines
				warning(formatString(lookupString("maskedLeftSoftKeyEventHandler"),
					instance.name));
			}
			if (leftId != null && leftId != "") { %>
	case ${leftId}:
		commandHandled = ${event.handlerName}( aCommand );
		break;
<% } %>]]>		</template>		
			</templateGroup>

			<templateGroup ifEvents="rightSoftKeyPressed">
			
			<template phase="UserHandlers">
TBool ${event.handlerName}( TInt aCommand );
			</template>

			<defineLocation id="RightSoftKeyPressedMethod" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}(TInt))">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 * @return ETrue if the command was handled, EFalse if not
 */
TBool ${handlerClassName}::${event.handlerName}( TInt aCommand )
	{
	// TODO: implement ${event.eventName} event handler
	return EFalse;
	}
]]>				
					</template>
				</defineLocation>
			
				<template location="RightSoftKeyPressedMethod" />
	
				<template form="HandleCommandSwitchDispatch" phase="SwitchContents" 
					ifEvents="rightSoftKeyPressed"><![CDATA[<%	
			var rightId = getCbaRightId(instance);
			if (rightId != null && rightId != "") { %>
	case ${rightId}:
		commandHandled = ${event.handlerName}( aCommand );
		break;
<% } %>]]>		</template>		
			</templateGroup>