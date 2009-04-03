<defineMacro id="DeclareMethod" 
	help="Declares a method, e.g., the prototype.">
	
	<macroArgument name="DeclLocation" optional="true" default="" 
		help="name of location into which to add the prototype (either this or DeclPhase must be defined)" />

	<macroArgument name="DeclPhase" optional="true" default="" 
		help="name of phase into which to add the prototype (either this or DeclLocation must be defined)" />
	
	<macroArgument name="FunctionName"
		help="name of function" />
	
	<macroArgument name="FunctionArgs" 
		help="canonical argument list (e.g. &quot;const TInt&amp; aArg, TInt aFoo = 3&quot;)"/>
	
	<macroArgument name="ReturnType" optional="true" default="void"
		help="return type for function" />
	
	<macroArgument name="IsStatic" optional="true" default="false" 
		help="is the function static?" />

	<macroArgument name="IsVirtual" optional="true" default="false"
		help="is the function virtual? (ignored if IsStatic)" />
	
	<macroArgument name="IsConst" optional="true" default="false"
		help="add 'const' modifier to function?" />
	
	<!-- N.B.: only one of location or phase will be non-empty -->
	<template location="$(DeclLocation)" phase="$(DeclPhase)" >
${($(IsStatic) ? "static " : ($(IsVirtual) ? "virtual " : "")
)}$(ReturnType::append-space-unless-empty)$(FunctionName)($(FunctionArgs::as-function-declaration-args::split-and-indent::add-spaces-unless-empty))${($(IsConst) ? " const" : "")};
	</template>
</defineMacro>

<defineMacro id="GenerateDefaultFunctionBody"
	help="Create the default body for a function.  If FunctionBody is set, 
emits that.  If ReturnType is not void and DefaultReturn is set, 
create a return statement using its value." >
	
	<macroArgument name="FunctionLocationId" 
		help="name of the function location to define; body is named id+_BODY" /> 
	
	<macroArgument name="DefaultReturn" optional="true" 
		help="default value to return; overridden if FunctionBody is specified" />

	<macroArgument name="ReturnType" optional="true" default="void"
		help="return type for function" />
	
	<macroArgument name="FunctionBody" optional="true" default=""
		help="text inside function body (comment and/or code)" />

	<template location="$(FunctionLocationId)"><![CDATA[<% 
if ($(FunctionBody::is-defined)) { 
%>$(FunctionBody)<% } 
if ($(DefaultReturn::is-defined) && $(ReturnType::as-string) != "void" && $(ReturnType::as-string) != "") { 
%>return $(DefaultReturn);
<% } %>]]></template>

</defineMacro>


<defineMacro id="DefineMethod"
		help="Defines a method to one location, optionally generating body text,
or a default return statement.
The generated function is owned by default." >
	
	<importArguments macroName="GenerateDefaultFunctionBody" />

	<macroArgument name="IsOwned" optional="true" default="true"
		help="is the function body owned?" />

	<macroArgument name="DefnLocation" 
		help="name of location into which to add the function" />

	<macroArgument name="FunctionName"
		help="name of function" />
	
	<macroArgument name="FunctionArgs" 
		help="canonical argument list (e.g. &quot;const TInt&amp; aArg, TInt aFoo = 3&quot;)"/>

	<macroArgument name="IsConst" optional="true" default="false" 
		help="add 'const' modifier to function?" />
	
	<macroArgument name="ClassName" optional="true" default="${className}"
		help="the class name" />

	<macroArgument name="FunctionComment" optional="true" 
		help="comment appearing before function defn" />

	<macroArgument name="IsEventHandler" optional="true" default="false" 
		help="is the function body the user event handler?" />
	
	<macroArgument name="Initializers" optional="true" default="" 
		help="provide any initializer expressions, e.g. for constructors, appearing
		on a separate line after the argument list and before the function body.
		Do not provide the leading colon (':') as this is added automatically" />
	
	<macroArgument name="Realize" optional="true" default="false"
		help="If true, force the function to be generated.  Otherwise, the
		function is generated only if the FunctionBody is non-empty or if
		another template contributions to the FunctionLocationId." />
	
	<defineLocation id="$(FunctionLocationId)" 
		baseLocation="$(DefnLocation)"
		owned="$(IsOwned)"	
		isEventHandler="$(IsEventHandler)"			
		realize="$(Realize)"
		location="function($(ClassName)::$(FunctionName)($(FunctionArgs::as-function-location-args)))">
		<template><![CDATA[<% 
if ($(FunctionComment::is-defined)) { 
%>$(FunctionComment)<% } 
%>$(ReturnType::append-space-unless-empty)$(ClassName)::$(FunctionName)($(FunctionArgs::as-function-definition-args::split-and-indent::add-spaces-unless-empty))${($(IsConst) ? " const" : "")}<%
if ($(Initializers::as-string) != "") { 
%>
	: $(Initializers)<% } %>
	{
	}
]]>
		</template>
	</defineLocation>
	
	<expandMacro name="GenerateDefaultFunctionBody" />

</defineMacro>



<defineMacro id="GenerateMethod" 
		help="Declares and defines a method to one location
and adds the prototype to another location." >
	
	<importArguments macroName="DeclareMethod" />

	<importArguments macroName="DefineMethod" />
	
	<expandMacro name="DeclareMethod" />
			
	<expandMacro name="DefineMethod" />

</defineMacro>

<defineMacro id="DefineMethodWithOwnedBody"
	help="Defines a method with an owned body section.
$p$
The generated function is not owned by default, unlike Method.
There may be uses for an owned function with a named owned region, though.
$p$	
Also, no default function code is generated, so the provided 
StartFunctionBody, FunctionBody, and EndFunctionBody must account for
any return statements." >
	
	<importArguments macroName="DefineMethod" />

	<macroArgument name="OwnedRegionLocationId" optional="true" default="$(FunctionLocationId)_BODY" 
		help="location id for enclosed body" />
	
	<macroArgument name="OwnedRegionName" optional="true" default="Generated Contents" 
		help="region name for enclosed body" />
	
	<macroArgument name="StartFunctionBody" optional="true" default="" 
		help="text inside function body (comment and/or code) before the owned section" />

	<macroArgument name="FunctionBody" optional="true" default="" 
		help="text inside owned part of function body" />

	<macroArgument name="EndFunctionBody" optional="true" default="" 
		help="text inside function body (comment and/or code) after the owned section" />

	<expandMacro name="DefineMethod"
		dontPassArguments="DefaultReturn FunctionBody"
		help="make a method first: does not pass DefaultReturn
		since either FunctionBody or EndFunctionBody should be
		handling the return"
		/>

	<template location="$(FunctionLocationId)">$(StartFunctionBody)
	</template>

	<defineLocation id="$(OwnedRegionLocationId)" baseLocation="$(FunctionLocationId)"
		location="region($(OwnedRegionName))" realize="true">
		<template>$(FunctionBody)			
		</template>
	</defineLocation>

	<template location="$(FunctionLocationId)">$(EndFunctionBody)
	</template>
	
</defineMacro>

<defineMacro id="GenerateMethodWithOwnedBody"
	help="Declares and defines a method with an owned body section to one location
and adds the prototype to another location. 
$p$
The generated function is not owned by default, unlike Method.
There may be uses for an owned function with a named owned region, though.">
	
	<importArguments macroName="DefineMethodWithOwnedBody" />

	<importArguments macroName="DeclareMethod" />

	<expandMacro name="DeclareMethod" />
	
	<expandMacro name="DefineMethodWithOwnedBody" />

</defineMacro>

<defineMacro id="GenerateMethodWithVariantArguments"
		help=" 
Declares and defines an owned method with variant arguments to one location
and adds the prototype to another location.  
$p$
Variant arguments means design-time variable (not C varargs).
The function has a non-empty list of fixed arguments followed by a 
non-empty list of variable arguments which are generated by a Javascript expression.
$p$
The generated function is owned by default.  It doesn't make sense to use 
this non-owned, since this will introduce compile errors when the prototype
changes and the old body remains behind.
	">
	<importArguments macroName="GenerateMethod"
		exceptArguments="FunctionArgs IsOwned"
		 
		help="use all the same arguments from Method, 
		excluding FunctionArgs, whose semantics are different,
		and IsOwned, which changes defaults"/>

	<macroArgument name="IsOwned" optional="true" default="true"
			help="tell whether the function is owned.  In general, it should be,
		otherwise compile errors will be introduced when the 
		method's signature changes and the old version is left behind." />
	
	<macroArgument name="FunctionArgs" 
		help="canonical leading static argument list (e.g. &quot;const TInt&amp; aArg, TInt aFoo = 3&quot;) 
		Do not include a trailing comma" />

	<macroArgument name="FunctionVarArgsAllowEmpty" optional="false" 
		help="tell whether the varargs may be empty"/>
	
	<macroArgument name="FunctionVarArgsDeclExpr" 
		help="generator for the variable arguments as appearing in the declaration
		(a Javascript expression); 
		if FunctionVarArgsAllowEmpty==false, do not generate a leading comma"/>

	<macroArgument name="FunctionVarArgsDefnExpr"
		help="generator for the variable arguments as appearing in the definition
		(a Javascript expression);
		if FunctionVarArgsAllowEmpty==false, do not generate a leading comma" />

	<!-- make a method declaration first -->
	<template location="$(DeclLocation)" phase="$(DeclPhase)" >
${($(IsStatic) ? "static " : ($(IsVirtual) ? "virtual " : "")
)}$(ReturnType::append-space-unless-empty)$(FunctionName)( $(FunctionArgs::as-function-declaration-args::split-and-indent)${($(FunctionVarArgsAllowEmpty) ? "" : ", ")}${$(FunctionVarArgsDefnExpr::split-and-indent)} )${($(IsConst) ? " const" : "")};
	</template>

	<!-- now define the function -->
	<defineLocation id="$(FunctionLocationId)" 
		baseLocation="$(DefnLocation)"
		owned="$(IsOwned)"				
		location="function($(ClassName)::$(FunctionName)($(FunctionArgs::as-function-location-args),...))">
		<template><![CDATA[<% 
if ($(FunctionComment::is-defined)) { 
%>$(FunctionComment)<% } 
%>$(ReturnType::append-space-unless-empty)$(ClassName)::$(FunctionName)( $(FunctionArgs::as-function-definition-args::split-and-indent)${($(FunctionVarArgsAllowEmpty) ? "" : ", ")}${$(FunctionVarArgsDefnExpr::split-and-indent)} )${($(IsConst) ? " const" : "")}
	{
	} 
]]>				
		</template>
	</defineLocation>

	<!-- and define the body -->
	<expandMacro name="GenerateDefaultFunctionBody" />

</defineMacro>

<defineMacro id="GenerateVirtualMethodOverrideForEventHandler"
	help="Override a virtual method with a non-owned function with an owned body.  
This must be invoked in a templateGroup that has an ifEvents=&quot;...&quot; attribute,
so the 'event' variable is available. ">
	<importArguments macroName="GenerateMethodWithOwnedBody"
		exceptArguments="IsVirtual IsStatic" />

	<macroArgument name="ClassName" optional="true" default="${handlerClassName}"
		help="the name of the class" />

	<macroArgument name="UserHandlerFunctionArgs" optional="true" 
		help="the arguments passed to the user handler function (== FunctionArgs by default)"/>

	<!-- declare the virtual function override -->
	<expandMacro name="GenerateMethodWithOwnedBody"
		IsStatic="false"
		IsVirtual="false"
	/>
	
	<!-- generate a call to the user handler -->
	<template location="$(OwnedRegionLocationId)"><![CDATA[<%
if ($(UserHandlerFunctionArgs::is-defined)) {
%>${event.handlerName}($(UserHandlerFunctionArgs::as-function-call-args::add-spaces-unless-empty));
<% } else { %>${event.handlerName}($(FunctionArgs::as-function-call-args::add-spaces-unless-empty));
<% } %>]]></template>
	

</defineMacro>


<defineMacro id="GenerateUserEventHandlerFunction"
		help="
Define a user handler declaration and function.
	$p$
Provides a default header comment and body comment.
	$p$
This is NOT conditional, so include it in a &lt;templateGroup ifEvents=&quot;...&quot; /&gt;
	" >
	<!-- extends GenerateMethod, but excludes
		IsVirtual
		IsStatic
	and overrides:
		FunctionBody
		FunctionComment
		FunctionName
		FunctionLocationId
	-->
	<importArguments macroName="GenerateMethod"
		exceptArguments="IsVirtual IsStatic" />
	
	<macroArgument name="ClassName" optional="true" default="${handlerClassName}" 
			help="the name of the class that receives the handler" />

	<macroArgument name="FunctionName" optional="true" default="${event.handlerName}" 
			help="the name of the function/method for the handler; 
			generally the default should be used (the name specified in the Events view)" />

	<macroArgument name="FunctionLocationId"
			help="the id for the event handler function" />

	<macroArgument name="FunctionComment" optional="true"
			help="the comment for the function" >
/**
 *	Handle the ${event.eventName} event
 */
	</macroArgument>

	<!--  -->
	<macroArgument name="FunctionBody" optional="true"
			help="the body of the function, which by default is a TODO comment" >
// TODO: implement ${event.eventName} event handler		
	</macroArgument> 
	
	<!-- declare the user handler method -->
	<expandMacro name="GenerateMethod"
		passArguments="DeclLocation DeclPhase 
			FunctionName FunctionArgs ReturnType IsConst
			DefnLocation ClassName FunctionLocationId
			FunctionComment 
			FunctionBody DefaultReturn"
		IsStatic="false"
		IsVirtual="false"
		IsEventHandler="true"
		IsOwned="false"
	/>

</defineMacro>
