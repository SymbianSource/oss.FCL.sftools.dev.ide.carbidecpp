/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.doms.rss.parser.gen;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.core.LineTable;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile;

import org.eclipse.jface.text.IRegion;

/**
 * This class represents an RSS token for use by SymbianRssParser
 * 
 *
 */
public class RssToken extends Token implements SymbianRssParserConstants {

    ISourceFile file;
    public IAstSourceFile sourceFile;
    int offset;
    int length;

    /**
     * Create a token given the EPOC token
     * @param text 
     * @param type 
     */
    public RssToken(ISourceFile file, IAstSourceFile sourceFile, IRegion region, int type, String text) {
        super();
        
        this.kind = type;
        this.file = file;
        this.sourceFile = sourceFile;
        this.image = text;
        
        this.offset = region.getOffset();
        this.length = region.getLength();
        
        LineTable table = file.getLineTable();
        this.beginLine = table.getLineForOffset(offset);
        this.endLine = table.getLineForEndOffset(offset + length); 
        this.beginColumn = table.getColumnForOffset(offset);
        this.endColumn = table.getColumnForEndOffset(offset + length);

    }

    /**
     * Create a token given the CDT token
     */
    public RssToken(ISourceFile file, IAstSourceFile sourceFile, org.eclipse.cdt.core.parser.IToken tok) {
        super();
        
        this.file = file;
        this.sourceFile = sourceFile;
        
        int startOffset, endOffset;
        int type;
        if (tok != null) {
            startOffset = tok.getOffset();
            endOffset = tok.getEndOffset(); //startOffset + tok.getLength();
            this.image = tok.getImage();
            type = tok.getType();
        } else {
            startOffset = file.getText().length;
            endOffset = startOffset;
            this.image = ""; //$NON-NLS-1$
            type = 0;
        }
        
        this.offset = startOffset;
        this.length = endOffset - startOffset;
        
        LineTable table = file.getLineTable();
        this.beginLine = table.getLineForOffset(startOffset);
        this.endLine = table.getLineForEndOffset(endOffset); 
        this.beginColumn = table.getColumnForOffset(startOffset);
        this.endColumn = table.getColumnForEndOffset(endOffset);
        
        //Check.checkState(table.getOffsetForLine(this.beginLine) + this.beginColumn - 1 == startOffset);
        
        // convert C++ token to RSS token
        switch (type) {
        case 0:
            this.kind = EOF;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tIDENTIFIER:
            this.kind = IDENTIFIER;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tINTEGER:
            this.kind = INTEGER;
            break;

        case org.eclipse.cdt.core.parser.IToken.tSTRING:
            this.kind = STRING;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tCHAR:
            this.kind = CHAR;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tFLOATINGPT:
            this.kind = FLOAT;
            break;
           
        case org.eclipse.cdt.core.parser.IToken.tLSTRING:
        case org.eclipse.cdt.core.parser.IToken.tLCHAR:
            this.kind = ERROR;
            break;

        case org.eclipse.cdt.core.parser.IToken.tPOUNDPOUND:
        default:
            System.out.println("unknown or unexpected token: " + type + " '" + image + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            this.kind = ERROR;
            break;
        
            // these are not used in RSS
        case org.eclipse.cdt.core.parser.IToken.tMINUSASSIGN:
        case org.eclipse.cdt.core.parser.IToken.tDECR:
        case org.eclipse.cdt.core.parser.IToken.tARROWSTAR:
        case org.eclipse.cdt.core.parser.IToken.tARROW:
        case org.eclipse.cdt.core.parser.IToken.tSTARASSIGN:
        case org.eclipse.cdt.core.parser.IToken.tPLUSASSIGN:
        case org.eclipse.cdt.core.parser.IToken.tINCR:
        case org.eclipse.cdt.core.parser.IToken.tCOLONCOLON:
        case org.eclipse.cdt.core.parser.IToken.tMODASSIGN:
        case org.eclipse.cdt.core.parser.IToken.tXORASSIGN:
        case org.eclipse.cdt.core.parser.IToken.tAMPERASSIGN:
        case org.eclipse.cdt.core.parser.IToken.tBITORASSIGN:
        case org.eclipse.cdt.core.parser.IToken.tSHIFTRASSIGN:
        case org.eclipse.cdt.core.parser.IToken.tSHIFTLASSIGN:
        case org.eclipse.cdt.core.parser.IToken.tELLIPSIS:
        case org.eclipse.cdt.core.parser.IToken.tDOTSTAR:
        case org.eclipse.cdt.core.parser.IToken.tDIVASSIGN:
            this.kind = ERROR;
            break;

            // nor these, though you'd think...
        case org.eclipse.cdt.core.parser.IToken.tSHIFTL:
        case org.eclipse.cdt.core.parser.IToken.tLTEQUAL:
        case org.eclipse.cdt.core.parser.IToken.tSHIFTR:
        case org.eclipse.cdt.core.parser.IToken.tGTEQUAL:
        case org.eclipse.cdt.core.parser.IToken.tCOLON:
        case org.eclipse.cdt.core.parser.IToken.tQUESTION:
        case org.eclipse.cdt.core.parser.IToken.tXOR:
        case org.eclipse.cdt.core.parser.IToken.tAND:
        case org.eclipse.cdt.core.parser.IToken.tAMPER:
        case org.eclipse.cdt.core.parser.IToken.tOR:
        case org.eclipse.cdt.core.parser.IToken.tNOTEQUAL:
        case org.eclipse.cdt.core.parser.IToken.tNOT:
        case org.eclipse.cdt.core.parser.IToken.tEQUAL:
        case org.eclipse.cdt.core.parser.IToken.tDOT:
            this.kind = ERROR;
            break;

        case org.eclipse.cdt.core.parser.IToken.tLT:
            this.kind = LT;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tGT:
            this.kind = GT;
            break;

        case org.eclipse.cdt.core.parser.IToken.tSEMI:
            this.kind = SEMICOLON;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tCOMMA:
            this.kind = COMMA;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tLPAREN:
            this.kind = LPAREN;
            break;

        case org.eclipse.cdt.core.parser.IToken.tRPAREN:
            this.kind = RPAREN;
            break;

        case org.eclipse.cdt.core.parser.IToken.tLBRACKET:
            this.kind = LBRACKET;
            break;

        case org.eclipse.cdt.core.parser.IToken.tRBRACKET:
            this.kind = RBRACKET;
            break;

        case org.eclipse.cdt.core.parser.IToken.tLBRACE:
            this.kind = LBRACE;
            break;

        case org.eclipse.cdt.core.parser.IToken.tRBRACE:
            this.kind = RBRACE;
            break;

        case org.eclipse.cdt.core.parser.IToken.tPLUS:
            this.kind = PLUS;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tMINUS:
            this.kind = MINUS;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tSTAR:
            this.kind = TIMES;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tMOD:
            this.kind = MOD;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tBITOR:
            this.kind = OR;
            break;

        case org.eclipse.cdt.core.parser.IToken.tASSIGN:
            this.kind = EQUALS;
            break;
            
        case org.eclipse.cdt.core.parser.IToken.tDIV:
            this.kind = DIV;
            break;

            // all the C/C++ keywords, obviously, are not recognized
        case org.eclipse.cdt.core.parser.IToken.t_asm:
        case org.eclipse.cdt.core.parser.IToken.t_auto:
        case org.eclipse.cdt.core.parser.IToken.t_bool:
        case org.eclipse.cdt.core.parser.IToken.t_break:
        case org.eclipse.cdt.core.parser.IToken.t_case:
        case org.eclipse.cdt.core.parser.IToken.t_catch:
        case org.eclipse.cdt.core.parser.IToken.t_char:
        case org.eclipse.cdt.core.parser.IToken.t_class:
        case org.eclipse.cdt.core.parser.IToken.t_const:
        case org.eclipse.cdt.core.parser.IToken.t_const_cast:
        case org.eclipse.cdt.core.parser.IToken.t_continue:
        case org.eclipse.cdt.core.parser.IToken.t_default:
        case org.eclipse.cdt.core.parser.IToken.t_delete:
        case org.eclipse.cdt.core.parser.IToken.t_do:
        case org.eclipse.cdt.core.parser.IToken.t_double:
        case org.eclipse.cdt.core.parser.IToken.t_dynamic_cast:
        case org.eclipse.cdt.core.parser.IToken.t_else:
        case org.eclipse.cdt.core.parser.IToken.t_enum:
        case org.eclipse.cdt.core.parser.IToken.t_explicit:
        case org.eclipse.cdt.core.parser.IToken.t_export:
        case org.eclipse.cdt.core.parser.IToken.t_extern:
        case org.eclipse.cdt.core.parser.IToken.t_false:
        case org.eclipse.cdt.core.parser.IToken.t_float:
        case org.eclipse.cdt.core.parser.IToken.t_for:
        case org.eclipse.cdt.core.parser.IToken.t_friend:
        case org.eclipse.cdt.core.parser.IToken.t_goto:
        case org.eclipse.cdt.core.parser.IToken.t_if:
        case org.eclipse.cdt.core.parser.IToken.t_inline:
        case org.eclipse.cdt.core.parser.IToken.t_int:
        case org.eclipse.cdt.core.parser.IToken.t_long:
        case org.eclipse.cdt.core.parser.IToken.t_mutable:
        case org.eclipse.cdt.core.parser.IToken.t_namespace:
        case org.eclipse.cdt.core.parser.IToken.t_new:
        case org.eclipse.cdt.core.parser.IToken.t_operator:
        case org.eclipse.cdt.core.parser.IToken.t_private:
        case org.eclipse.cdt.core.parser.IToken.t_protected:
        case org.eclipse.cdt.core.parser.IToken.t_public:
        case org.eclipse.cdt.core.parser.IToken.t_register:
        case org.eclipse.cdt.core.parser.IToken.t_reinterpret_cast:
        case org.eclipse.cdt.core.parser.IToken.t_return:
        case org.eclipse.cdt.core.parser.IToken.t_short:
        case org.eclipse.cdt.core.parser.IToken.t_sizeof:
        case org.eclipse.cdt.core.parser.IToken.t_static:
        case org.eclipse.cdt.core.parser.IToken.t_static_cast:
        case org.eclipse.cdt.core.parser.IToken.t_signed:
        case org.eclipse.cdt.core.parser.IToken.t_struct:
        case org.eclipse.cdt.core.parser.IToken.t_switch:
        case org.eclipse.cdt.core.parser.IToken.t_template:
        case org.eclipse.cdt.core.parser.IToken.t_this:
        case org.eclipse.cdt.core.parser.IToken.t_throw:
        case org.eclipse.cdt.core.parser.IToken.t_true:
        case org.eclipse.cdt.core.parser.IToken.t_try:
        case org.eclipse.cdt.core.parser.IToken.t_typedef:
        case org.eclipse.cdt.core.parser.IToken.t_typeid:
        case org.eclipse.cdt.core.parser.IToken.t_typename:
        case org.eclipse.cdt.core.parser.IToken.t_union:
        case org.eclipse.cdt.core.parser.IToken.t_unsigned:
        case org.eclipse.cdt.core.parser.IToken.t_using:
        case org.eclipse.cdt.core.parser.IToken.t_virtual:
        case org.eclipse.cdt.core.parser.IToken.t_void:
        case org.eclipse.cdt.core.parser.IToken.t_volatile:
        case org.eclipse.cdt.core.parser.IToken.t_wchar_t:
        case org.eclipse.cdt.core.parser.IToken.t_while:
        case org.eclipse.cdt.core.parser.IToken.t__Bool:
        case org.eclipse.cdt.core.parser.IToken.t__Complex:
        case org.eclipse.cdt.core.parser.IToken.t__Imaginary:
        case org.eclipse.cdt.core.parser.IToken.t_restrict:
            this.kind = IDENTIFIER;
            break;
        }

        // map any identifiers to RSS keywords
        //Integer kw = (Integer) keywords.get(this.image);
        Integer kw = null;
        if (kw != null)
            this.kind = kw.intValue();

        /*
        if (this.file.getFileName().indexOf("badef") >= 0)
        System.out.println(this.file.getFileName()+"]] tok #"+kind +
                " ["+startOffset+"] "+
                " ("+this.beginLine+":"+this.beginColumn
                +"-"+this.endLine+":"+this.endColumn+")="+this.image);
                */
    }

}
