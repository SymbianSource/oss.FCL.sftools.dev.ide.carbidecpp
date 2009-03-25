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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss.impl;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.impl.EStringToStringMapEntryImpl;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.doms.rss.IRssModelStringHandler;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.*;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EMap;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 *
 */
public abstract class RssModelStringHandlerBase implements IRssModelStringHandler {
	
	// greedy matcher, may contain embedded escaped quotes
	final static Pattern STRING_PATTERN = Pattern.compile("\"(.*)\"");
	private static Pattern uglyEscapePattern = Pattern.compile("\"<((?:0x)?[0-9A-F]+)>\"", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	
	static String cp1252_Chars = "\u0020\u0021\"\u0023\u0024\u0025\u0026\u0027"  //$NON-NLS-1$
		+ "\u0028\u0029\u002A\u002B\u002C\u002D\u002E\u002F"  //$NON-NLS-1$
		+ "\u0030\u0031\u0032\u0033\u0034\u0035\u0036\u0037"  //$NON-NLS-1$
		+ "\u0038\u0039\u003A\u003B\u003C\u003D\u003E\u003F"  //$NON-NLS-1$
		+ "\u0040\u0041\u0042\u0043\u0044\u0045\u0046\u0047"  //$NON-NLS-1$
		+ "\u0048\u0049\u004A\u004B\u004C\u004D\u004E\u004F"  //$NON-NLS-1$
		+ "\u0050\u0051\u0052\u0053\u0054\u0055\u0056\u0057"  //$NON-NLS-1$
		+ "\u0058\u0059\u005A\u005B\\\u005D\u005E\u005F"  //$NON-NLS-1$
		+ "\u0060\u0061\u0062\u0063\u0064\u0065\u0066\u0067"  //$NON-NLS-1$
		+ "\u0068\u0069\u006A\u006B\u006C\u006D\u006E\u006F"  //$NON-NLS-1$
		+ "\u0070\u0071\u0072\u0073\u0074\u0075\u0076\u0077"  //$NON-NLS-1$
		+ "\u0078\u0079\u007A\u007B\u007C\u007D\u007E\u007F"  //$NON-NLS-1$
		+ "\u20AC\u201A\u0192\u201E\u2026\u2020\u2021\u02C6"  //$NON-NLS-1$
		+ "\u2030\u0160\u2039\u0152\u017D\u2018\u2019\u201C"  //$NON-NLS-1$
		+ "\u201D\u2022\u2013\u2014\u02DC\u2122\u0161\u203A"  //$NON-NLS-1$
		+ "\u0153\u017E\u0178"  //$NON-NLS-1$
		+ "\u00A0\u00A1\u00A2\u00A3\u00A4\u00A5\u00A6\u00A7"  //$NON-NLS-1$
		+ "\u00A8\u00A9\u00AA\u00AB\u00AC\u00AD\u00AE\u00AF"  //$NON-NLS-1$
		+ "\u00B0\u00B1\u00B2\u00B3\u00B4\u00B5\u00B6\u00B7"  //$NON-NLS-1$
		+ "\u00B8\u00B9\u00BA\u00BB\u00BC\u00BD\u00BE\u00BF"  //$NON-NLS-1$
		+ "\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C6\u00C7"  //$NON-NLS-1$
		+ "\u00C8\u00C9\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF"  //$NON-NLS-1$
		+ "\u00D0\u00D1\u00D2\u00D3\u00D4\u00D5\u00D6\u00D7"  //$NON-NLS-1$
		+ "\u00D8\u00D9\u00DA\u00DB\u00DC\u00DD\u00DE\u00DF"  //$NON-NLS-1$
		+ "\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5\u00E6\u00E7"  //$NON-NLS-1$
		+ "\u00E8\u00E9\u00EA\u00EB\u00EC\u00ED\u00EE\u00EF"  //$NON-NLS-1$
		+ "\u00F0\u00F1\u00F2\u00F3\u00F4\u00F5\u00F6\u00F7"  //$NON-NLS-1$
		+ "\u00F8\u00F9\u00FA\u00FB\u00FC\u00FD\u00FE\u00FF"; //$NON-NLS-1$


	public RssModelStringHandlerBase() {
	}
	
	/**
	 * Tell whether the value must be encoded in UTF-8 (as opposed to CP1252)
	 * 
	 * @param value
	 * @return true: UTF-8 required, false: CP1252 will suffice
	 */
	protected boolean stringNeedsUtf8(String value) {
	    for (int i = 0; i < value.length(); i++) {
	        if (cp1252_Chars.indexOf(value.charAt(i)) == -1)
	            return true;
	    }
	    return false;
	}

	/**
	 * Update an RSS file and its eventual storage format to a given
	 * character set
	 * @param file  the file
	 * @param rssCharSet the character set as it apepars in RSS CHARACTER_SET
	 * @param charSet the canonical character set
	 */
	protected void forceCharSet(IAstRssSourceFile file, String rssCharSet, String charSet) {
	    // put some indication of the charset we think is needed
	    IAstCharacterSetStatement[] nodes = (IAstCharacterSetStatement[]) file.getFileNodes(IAstCharacterSetStatement.class);
	    IAstCharacterSetStatement charSetStmt = null;
	    if (nodes.length > 0)
	        charSetStmt = nodes[0];
	    
	    if (charSetStmt == null) {
	        charSetStmt = new AstCharacterSetStatement(rssCharSet);
	        file.addFileNode(charSetStmt);
	    } else {
	        // don't downgrade
	        if (!charSetStmt.getCharacterSet().equals("UTF8")) //$NON-NLS-1$
	            charSetStmt.setCharacterSet(rssCharSet);
	    }
	    
	    // don't downgrade
	    String currentCharSet = file.getSourceFile().getCharset(); 
	    if (currentCharSet == null || !currentCharSet.equals("UTF-8")) //$NON-NLS-1$
	        file.getSourceFile().setCharset(charSet);
	}

	/**
	 * Define or replace string macros for a given file.
	 * @param file
	 * @param macros
	 * @param provider 
	 */
	protected boolean defineStringMacros(IAstRssSourceFile file, EMap macros, final IStringKeyProvider provider) {
	    IAstPreprocessorNode[] oldNodes = file.getPreprocessorNodes();
	    List keys = new ArrayList(macros.entrySet());
	    Collections.sort(keys, new Comparator() {
	
	        public int compare(Object o1, Object o2) {
	            Map.Entry e1 = (Map.Entry) o1;
	            Map.Entry e2 = (Map.Entry) o2;
	            return provider.compareKeys((String)e1.getKey(), (String)e2.getKey());
	        }});
	
	    boolean needUtf8 = false;
	    boolean any = false;
	    
	    for (Iterator iter = keys.iterator(); iter.hasNext();) {
	        any = true;
	        EStringToStringMapEntryImpl entry = (EStringToStringMapEntryImpl) iter.next();
	        Object stringValue = entry.getValue();
	        String macroValue =  
	        		AstLiteralExpression.quoteForRss(stringValue != null ? stringValue.toString() : "", '"');  //$NON-NLS-1$ //$NON-NLS-2$
	        IAstPreprocessorDefineDirective define = null;
	        for (int i = 0; i < oldNodes.length; i++) {
	            if (oldNodes[i] instanceof IAstPreprocessorDefineDirective) {
	                IAstPreprocessorDefineDirective tmp = (IAstPreprocessorDefineDirective) oldNodes[i];
	                if (tmp.getMacro().getName().equals(entry.getKey())) {
	                    define = tmp;
	                    break;
	                }
	            }
	        }
	        if (define == null) {
	        	if (macroValue != null) {
		            // make a new macro
		            IMacro macro = new ObjectStyleMacro(entry.getKey().toString(), macroValue); 
		            define = new AstPreprocessorDefineDirective(macro);
		            file.addFileNode(define);
	        	} else {
	        		// leave undefined
	        	}
	        } else {
	        	if (macroValue != null) {
		        	if (!define.getMacro().getExpansion().equals(macroValue)) {
		        		// replace value of old macro
		        		define.getMacro().setExpansion(macroValue);
		        		define.getMacroValue().setText(macroValue);
		        	}
	        	} else {
	        		// delete macro
	        		file.removeFileNode(define);
	        	}
	        }
	        
	        if (macroValue != null)
	        	needUtf8 |= stringNeedsUtf8(macroValue);
	    }
	
	    if (any && needUtf8) {
	        forceCharSet(file, "UTF8", "UTF-8"); //$NON-NLS-1$ //$NON-NLS-2$
	    }
	    
	    return any;
	}

	static class ClearUserKeysCommand extends AbstractCommand {

		private IDesignerData designerData;
		private Set<String> bundleKeys;
		private Set<String> macroKeys;
		
		public ClearUserKeysCommand(IDesignerData designerData) {
			this.designerData = designerData;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
		 */
		@Override
		protected boolean prepare() {
			bundleKeys = new HashSet<String>(designerData.getStringBundle().getUserGeneratedStringKeys());
			macroKeys = new HashSet<String>(designerData.getMacroTable().getUserGeneratedStringKeys());
			return true;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.Command#execute()
		 */
		public void execute() {
			designerData.getStringBundle().getUserGeneratedStringKeys().clear();
			designerData.getMacroTable().getUserGeneratedStringKeys().clear();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.Command#redo()
		 */
		public void redo() {
			execute();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
		 */
		@Override
		public void undo() {
			designerData.getStringBundle().getUserGeneratedStringKeys().addAll(bundleKeys);
			designerData.getMacroTable().getUserGeneratedStringKeys().addAll(macroKeys);
		}
	}
	
	static class AddUserKeyCommand extends AbstractCommand {

		private IDesignerData designerData;
		private Language language; // may be null for macro table
		private String macroName;
		
		public AddUserKeyCommand(IDesignerData designerData, Language language, String macroName) {
			this.designerData = designerData;
			this.language = language;
			this.macroName = macroName;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
		 */
		@Override
		protected boolean prepare() {
			return true;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.Command#execute()
		 */
		public void execute() {
			if (language == null)
				designerData.getMacroTable().getUserGeneratedStringKeys().add(macroName);
			else
				designerData.getStringBundle().getUserGeneratedStringKeys().add(macroName);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.Command#redo()
		 */
		public void redo() {
			execute();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
		 */
		@Override
		public void undo() {
			if (language == null)
				designerData.getMacroTable().getUserGeneratedStringKeys().remove(macroName);
			else
				designerData.getStringBundle().getUserGeneratedStringKeys().remove(macroName);
		}
	}
	
	static class ChangeStringValueCommand extends AbstractCommand {

		private IDesignerData designerData;
		private String macroName;
		private String macroValue;
		private Language language;
		private boolean existed;
		private String oldValue;
		
		public ChangeStringValueCommand(IDesignerData designerData, Language language, String macroName, String macroValue) {
			this.designerData = designerData;
			this.language = language;
			this.macroName = macroName;
			this.macroValue = macroValue;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
		 */
		@Override
		protected boolean prepare() {
			boolean canExecute = false;
			EMap map = getStrings();
			if (map != null) {
				existed = map.containsKey(macroName);
				oldValue = (String) map.get(macroName);
				canExecute = true;
			}
			return canExecute;
		}
		
		/**
		 * @return
		 */
		private EMap getStrings() {
			if (language != null) {
				ILocalizedStringTable table = designerData.getStringBundle().findLocalizedStringTable(language);
				Check.checkState(table != null);
				return table.getStrings();
			} else
				return designerData.getMacroTable().getStringMacros();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.Command#execute()
		 */
		public void execute() {
			EMap map = getStrings();
			map.put(macroName, macroValue);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.Command#redo()
		 */
		public void redo() {
			execute();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
		 */
		@Override
		public void undo() {
			EMap map = getStrings();
			if (existed)
				map.put(macroName, oldValue);
			else
				map.removeKey(macroName);
		}
	}
	
	/**
	 * Reparse string contents which may contain ugly RSS-style escapes.
	 * @param string
	 * @return string with normal characters
	 */
	public static String reparseString(String string) {
		Matcher matcher = uglyEscapePattern.matcher(string);
		try {
			// replace <0xABCD> sequences with literal characters
			char[] temp = {0};
			while (matcher.find()) {
				String val = matcher.group(1);
				char ch = 0;
				if (val.toLowerCase().startsWith("0x")) { ////$NON-NLS-1$
					ch = (char) Integer.parseInt(val.substring(2), 16);
				} else if (val.startsWith("0")) {
					ch = (char) Integer.parseInt(val, 8);	
				} else {
					ch = (char) Integer.parseInt(val);
				}
				temp[0] = ch;
				string = matcher.replaceFirst(new String(temp));
				matcher.reset(string);
			}
		} catch (NumberFormatException e) {
			// just give up
		}
		
		return TextUtils.unescape(string);
	}

	

	/**
	 * Tell if the macro looks like a string macro
	 * and return the unquoted value, if any.
	 * @param macro
	 * @return raw string or null for invalid macro
	 */
	static public String getStringMacroValue(IMacro macro) {
		// ignore complex macros
		if (macro instanceof FunctionStyleMacro)
			return null;
		
		// ignore macros that aren't strings
		Matcher matcher = STRING_PATTERN.matcher(macro.getExpansion());
		if (!matcher.matches())
			return null;
		
		String escapedString = matcher.group(1);
		return TextUtils.unescape(reparseString(escapedString));
	}
	
	/**
	 * Import string macros from the file into the string table.
	 * We detect user-generated strings so they can eventually be
	 * selected from the property sheet.
	 * @param userCommands command list for user-generated strings 
	 * @param changeCommands command list for modifying strings
	 * @param file
	 * @param table
	 */
	protected void importStringMacros(CompoundCommand userCommands, 
			CompoundCommand changeCommands, 
			IDesignerData designerData, IAstRssSourceFile file, 
			Language language, EMap macros, Set<String> userGeneratedKeys) {
		IAstPreprocessorDefineDirective[] defines = file.getDefines();
		for (int i = 0; i < defines.length; i++) {
			IAstPreprocessorDefineDirective define = defines[i];
			IMacro macro = define.getMacro();
			
			// ignore non-string macros, and report if they were generated
			String value = getStringMacroValue(macro);
			if (value == null) {
				if (macros.containsKey(define.getMacro().getName())) {
					MessageReporting.emit(IStatus.ERROR,
							define.getSourceRange().createMessageLocation(),
							"RssModelLocStringHandler.IgnoringInvalidMacro", //$NON-NLS-1$
							Messages.getString("RssModelLocStringHandler.IgnoringInvalidMacro"), //$NON-NLS-1$ 
							new Object[] {
								macro.getName()
							});
				}
				continue;
			}

			if (!macros.containsKey(macro.getName()) || userGeneratedKeys.contains(macro.getName())) {
				// this is a user-defined macro
				userCommands.append(new AddUserKeyCommand(designerData, language, macro.getName()));
				userCommands.append(new ChangeStringValueCommand(designerData, language, macro.getName(), value));
			} else {
				// change or add macro
				String currentValue = (String) macros.get(macro.getName());
				if (currentValue == null || !currentValue.equals(value))
					changeCommands.append(new ChangeStringValueCommand(designerData, language, macro.getName(), value));
			}
		}
	}

	/**
	 * Import RLS string entries from the file into the string table.
	 * We detect user-generated strings so they can eventually be
	 * selected from the property sheet.
	 * @param commands command list to modify 
	 * @param file
	 * @param table
	 */
	protected void importStrings(CompoundCommand userCommands, CompoundCommand changeCommands, IDesignerData designerData, IAstRssSourceFile file, 
			Language language, EMap macros, Set<String> userGeneratedKeys) {
		IAstRlsString[] strings = file.getRlsStrings();
		for (int i = 0; i < strings.length; i++) {
			IAstRlsString string = strings[i];
			
			String name = string.getIdentifier().getName();
			String value = string.getString().getValue();
			if (!macros.containsKey(name) || userGeneratedKeys.contains(name)) {
				// this is a user-defined string
				userCommands.append(new AddUserKeyCommand(designerData, language, name));
				userCommands.append(new ChangeStringValueCommand(designerData, language, name, value));
			} else {
				// change or add string
				String currentValue = (String) macros.get(name);
				if (currentValue == null || !currentValue.equals(value))
					changeCommands.append(new ChangeStringValueCommand(designerData, language, name, value));
			}
		}
	}

	/**
	 * @param file
	 * @param language
	 */
	protected void emitIgnoringAddedLanguageMessage(IAstSourceFile file, Language language) {
		if (file.getSourceRange() == null)
			return;
		MessageReporting.emit(IStatus.ERROR,
				file.getSourceRange().createMessageLocation(),
				"RssModelStringHandlerBase.IgnoringLanguageChangesAddingLanguage", //$NON-NLS-1$
				Messages.getString("RssModelStringHandlerBase.IgnoringLanguageChanges"), //$NON-NLS-1$ 
				new Object[] {
				Messages.getFormattedString(
						"RssModelStringHandlerBase.IgnoringLanguageChangesAddingLanguage", //$NON-NLS-1$
						new Object[] { language })
				});
	}

	/**
	 * @param file
	 * @param language
	 */
	protected void emitIgnoringDeletedLanguageMessage(IAstSourceFile file, Language language) {
		if (file.getSourceRange() == null)
			return;
		MessageReporting.emit(IStatus.ERROR,
				file.getSourceRange().createMessageLocation(),
				"RssModelStringHandlerBase.IgnoringLanguageChangesRemovingLanguage", //$NON-NLS-1$
				Messages.getString("RssModelStringHandlerBase.IgnoringLanguageChanges"), //$NON-NLS-1$ 
				new Object[] {
				Messages.getFormattedString(
						"RssModelStringHandlerBase.IgnoringLanguageChangesRemovingLanguage", //$NON-NLS-1$
						new Object[] { language })
				});
	}

	/**
	 * Remove strings which exist in the file which are not in the string
	 * table and also not in the user strings.
	 * @param lxxSrcFile
	 * @param designerStrings
	 * @param userGeneratedStringKeys
	 */
	protected void removeUnreferencedStringMacros(IAstLxxSourceFile lxxSrcFile, EMap designerStrings, Set<String> userGeneratedStringKeys) {
		IAstPreprocessorDefineDirective[] macros = lxxSrcFile.getStringMacros();
		for (int i = 0; i < macros.length; i++) {
			String name = macros[i].getMacro().getName();
			if (designerStrings.containsKey(name)
					|| userGeneratedStringKeys.contains(name))
				continue;
			lxxSrcFile.removeFileNode(macros[i]);
		}
	}

	/**
	 * Remove strings which exist in the file which are not in the string
	 * table and also not in the user strings.
	 * @param lxxSrcFile
	 * @param strings
	 * @param userGeneratedStringKeys
	 */
	protected void removeUnreferencedStringMacros(IAstRlsSourceFile rlsSrcFile, EMap designerStrings, Set<String> userGeneratedStringKeys) {
		IAstRlsString[] strings = rlsSrcFile.getRlsStrings();
		for (int i = 0; i < strings.length; i++) {
			String name = strings[i].getIdentifier().getName();
			if (designerStrings.containsKey(name)
					|| userGeneratedStringKeys.contains(name))
				continue;
			rlsSrcFile.removeFileNode(strings[i]);
		}
	}



}
