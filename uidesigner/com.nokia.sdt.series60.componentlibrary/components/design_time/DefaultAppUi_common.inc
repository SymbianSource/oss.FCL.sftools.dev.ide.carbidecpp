			<inline scope="prototype"><![CDATA[
include("../containers/containerLibrary.js")		
]]>			</inline>
			
			<inline><![CDATA[
			for (var i in children) {
				if (!isControlPane(children[i].component)) {
					if (form == "")
						contribs.addAll(Engine.generateChildContributions(children[i], "|AppUi"));
					else
						contribs.addAll(Engine.generateChildContributions(children[i], form));
				}
			}
]]>			</inline>

