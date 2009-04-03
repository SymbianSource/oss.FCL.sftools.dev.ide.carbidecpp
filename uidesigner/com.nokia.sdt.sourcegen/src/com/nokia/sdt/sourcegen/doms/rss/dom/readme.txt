
Reformatting text.

States:

Single node:
	1) New: no source range
	2) Dirty: existing source range but dirty
	3) Clean: source range exists and node is clean
	
Trees includes:
	4) Update: node is clean but has dirty children

Source ranges:
	-- Source range contains the same semantic
		content emitted by the getTextSegments() call.
		If a newline is part of that content, then
		any trailing comment is also included
		in this source range.  (TODO: fix).
	-- Extended source range contains leading 
		and trailing comments.  
		The head contains all the whitespace preceding
		it up to the line start.
		The tail is nonempty only for nodes that
		do not emit a newline, when a comment follows
		(e.g. it never contains lone newlines or spaces).
		
Lists:
	-- A list node contains a source range which precisely
	encapsulates its children.  Every child node starts and ends
	at a newline if possible.  
		
Transitions:

	Start -> New:
		-- need to indent (zero places)
		-- emit text from getTextSegments()
		-- newline emitted if necessary either explicitly
			or implicitly via {/}
		-- cursor left at start of new line if necessary
			
	Start -> Dirty:
		-- emit existing extended source range header
		-- inside, rewrite from scratch (+ newline)
		-- if existing extended tail has a newline at the end,
		remove the last newline 
	



