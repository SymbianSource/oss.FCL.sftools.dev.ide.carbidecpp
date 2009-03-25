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

package com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl;

import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.*;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getAbstract <em>Abstract</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getAlt <em>Alt</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getApiname <em>Apiname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getArea <em>Area</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getB <em>B</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getBody <em>Body</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getBrand <em>Brand</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCall <em>Call</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCite <em>Cite</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCmdname <em>Cmdname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCodeblock <em>Codeblock</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCodeph <em>Codeph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getColspec <em>Colspec</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getComponent <em>Component</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCoords <em>Coords</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCopyrholder <em>Copyrholder</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCopyright <em>Copyright</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCopyryear <em>Copyryear</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCreated <em>Created</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getCritdates <em>Critdates</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getData <em>Data</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDataAbout <em>Data About</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDd <em>Dd</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDdhd <em>Ddhd</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDelim <em>Delim</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDesc <em>Desc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDevice <em>Device</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDl <em>Dl</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDlentry <em>Dlentry</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDlhead <em>Dlhead</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDraftComment <em>Draft Comment</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDt <em>Dt</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDthd <em>Dthd</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getEntry <em>Entry</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getExample <em>Example</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getFeatnum <em>Featnum</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getFig <em>Fig</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getFiggroup <em>Figgroup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getFilepath <em>Filepath</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getFiletype <em>Filetype</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getFn <em>Fn</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getForeign <em>Foreign</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getFragment <em>Fragment</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getFragref <em>Fragref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getGroupchoice <em>Groupchoice</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getGroupcomp <em>Groupcomp</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getGroupseq <em>Groupseq</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getI <em>I</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getImage <em>Image</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getImagemap <em>Imagemap</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getIndexBase <em>Index Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getIndexSee <em>Index See</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getIndexSeeAlso <em>Index See Also</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getIndexSortAs <em>Index Sort As</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getIndexterm <em>Indexterm</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getIndextermref <em>Indextermref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getInheritance <em>Inheritance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getItemgroup <em>Itemgroup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKbdata <em>Kbdata</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKbdataBody <em>Kbdata Body</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKbdataCategory <em>Kbdata Category</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKbdataId <em>Kbdata Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKbdataKeyword <em>Kbdata Keyword</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKbdataKeywords <em>Kbdata Keywords</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKbdataMetadata <em>Kbdata Metadata</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKbdataPlatform <em>Kbdata Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKbdataRef <em>Kbdata Ref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKeyword <em>Keyword</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKeywords <em>Keywords</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getKwd <em>Kwd</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getLi <em>Li</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getLines <em>Lines</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getLink <em>Link</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getLinkinfo <em>Linkinfo</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getLinklist <em>Linklist</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getLinkpool <em>Linkpool</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getLinktext <em>Linktext</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getLocal <em>Local</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getLq <em>Lq</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getMacro <em>Macro</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getMember <em>Member</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getMenucascade <em>Menucascade</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getMessages <em>Messages</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getMethod <em>Method</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getMsgblock <em>Msgblock</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getMsgnum <em>Msgnum</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getMsgph <em>Msgph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getNavtitle <em>Navtitle</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getNote <em>Note</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getNoTopicNesting <em>No Topic Nesting</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getOl <em>Ol</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getOper <em>Oper</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getOption <em>Option</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getOthermeta <em>Othermeta</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getP <em>P</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getParam <em>Param</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getParml <em>Parml</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getParmname <em>Parmname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPd <em>Pd</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPermissions <em>Permissions</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPh <em>Ph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPlentry <em>Plentry</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPre <em>Pre</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getProdinfo <em>Prodinfo</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getProdname <em>Prodname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPrognum <em>Prognum</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getProlog <em>Prolog</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPropdesc <em>Propdesc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPropdeschd <em>Propdeschd</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getProphead <em>Prophead</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getProptype <em>Proptype</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getProptypehd <em>Proptypehd</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPropvalue <em>Propvalue</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPropvaluehd <em>Propvaluehd</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPt <em>Pt</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getPublisher <em>Publisher</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getQ <em>Q</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getRefbody <em>Refbody</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getReference <em>Reference</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getRefsyn <em>Refsyn</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getRelatedLinks <em>Related Links</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getRepsep <em>Repsep</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getRequiredCleanup <em>Required Cleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getResourceid <em>Resourceid</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getRevised <em>Revised</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getRow <em>Row</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getScreen <em>Screen</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSearchtitle <em>Searchtitle</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSection <em>Section</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSep <em>Sep</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSeries <em>Series</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getShape <em>Shape</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getShortcut <em>Shortcut</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getShortdesc <em>Shortdesc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSimpletable <em>Simpletable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSl <em>Sl</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSli <em>Sli</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSolution <em>Solution</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSource <em>Source</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getState <em>State</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getStentry <em>Stentry</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSthead <em>Sthead</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getStrow <em>Strow</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSub <em>Sub</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSup <em>Sup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSymptom <em>Symptom</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSynblk <em>Synblk</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSynnote <em>Synnote</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSynnoteref <em>Synnoteref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSynph <em>Synph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSyntaxdiagram <em>Syntaxdiagram</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSystemoutput <em>Systemoutput</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getTable <em>Table</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getTbody <em>Tbody</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getTerm <em>Term</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getTgroup <em>Tgroup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getThead <em>Thead</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getTitlealts <em>Titlealts</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getTm <em>Tm</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getTopic <em>Topic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getTt <em>Tt</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getU <em>U</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getUicontrol <em>Uicontrol</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getUl <em>Ul</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getUnknown <em>Unknown</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getUserinput <em>Userinput</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getVar <em>Var</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getVarname <em>Varname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getVrm <em>Vrm</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getVrmlist <em>Vrmlist</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getWintitle <em>Wintitle</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getXref <em>Xref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getDITAArchVersion <em>DITA Arch Version</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.DocumentRootImpl#getSpace <em>Space</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * The default value of the '{@link #getDITAArchVersion() <em>DITA Arch Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDITAArchVersion()
	 * @generated
	 * @ordered
	 */
	protected static final Object DITA_ARCH_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDITAArchVersion() <em>DITA Arch Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDITAArchVersion()
	 * @generated
	 * @ordered
	 */
	protected Object dITAArchVersion = DITA_ARCH_VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getLang() <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLang()
	 * @generated
	 * @ordered
	 */
	protected static final String LANG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLang() <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLang()
	 * @generated
	 * @ordered
	 */
	protected String lang = LANG_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpace() <em>Space</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpace()
	 * @generated
	 * @ordered
	 */
	protected static final SpaceType SPACE_EDEFAULT = SpaceType.PRESERVE;

	/**
	 * The cached value of the '{@link #getSpace() <em>Space</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpace()
	 * @generated
	 * @ordered
	 */
	protected SpaceType space = SPACE_EDEFAULT;

	/**
	 * This is true if the Space attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean spaceESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KbdataPackage.eINSTANCE.getDocumentRoot();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, KbdataPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, KbdataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, KbdataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractType getAbstract() {
		return (AbstractType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Abstract(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAbstract(AbstractType newAbstract, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Abstract(), newAbstract, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbstract(AbstractType newAbstract) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Abstract(), newAbstract);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AltType getAlt() {
		return (AltType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Alt(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAlt(AltType newAlt, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Alt(), newAlt, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlt(AltType newAlt) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Alt(), newAlt);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApinameType getApiname() {
		return (ApinameType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Apiname(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetApiname(ApinameType newApiname, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Apiname(), newApiname, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setApiname(ApinameType newApiname) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Apiname(), newApiname);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AreaType getArea() {
		return (AreaType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Area(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArea(AreaType newArea, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Area(), newArea, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArea(AreaType newArea) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Area(), newArea);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AudienceType getAudience() {
		return (AudienceType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Audience(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAudience(AudienceType newAudience, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Audience(), newAudience, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAudience(AudienceType newAudience) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Audience(), newAudience);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthorType getAuthor() {
		return (AuthorType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Author(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthor(AuthorType newAuthor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Author(), newAuthor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(AuthorType newAuthor) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Author(), newAuthor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BType getB() {
		return (BType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_B(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetB(BType newB, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_B(), newB, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setB(BType newB) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_B(), newB);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BodyType getBody() {
		return (BodyType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Body(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBody(BodyType newBody, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Body(), newBody, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(BodyType newBody) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Body(), newBody);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanType getBoolean() {
		return (BooleanType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Boolean(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBoolean(BooleanType newBoolean, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Boolean(), newBoolean, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBoolean(BooleanType newBoolean) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Boolean(), newBoolean);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BrandType getBrand() {
		return (BrandType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Brand(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBrand(BrandType newBrand, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Brand(), newBrand, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBrand(BrandType newBrand) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Brand(), newBrand);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CallType getCall() {
		return (CallType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Call(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCall(CallType newCall, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Call(), newCall, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCall(CallType newCall) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Call(), newCall);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CategoryType getCategory() {
		return (CategoryType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Category(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCategory(CategoryType newCategory, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Category(), newCategory, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategory(CategoryType newCategory) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Category(), newCategory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CiteType getCite() {
		return (CiteType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Cite(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCite(CiteType newCite, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Cite(), newCite, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCite(CiteType newCite) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Cite(), newCite);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassType getClass_() {
		return (ClassType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Class(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClass(ClassType newClass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Class(), newClass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClass(ClassType newClass) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Class(), newClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CmdnameType getCmdname() {
		return (CmdnameType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Cmdname(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCmdname(CmdnameType newCmdname, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Cmdname(), newCmdname, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCmdname(CmdnameType newCmdname) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Cmdname(), newCmdname);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodeblockType getCodeblock() {
		return (CodeblockType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Codeblock(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCodeblock(CodeblockType newCodeblock, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Codeblock(), newCodeblock, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCodeblock(CodeblockType newCodeblock) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Codeblock(), newCodeblock);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodephType getCodeph() {
		return (CodephType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Codeph(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCodeph(CodephType newCodeph, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Codeph(), newCodeph, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCodeph(CodephType newCodeph) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Codeph(), newCodeph);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColspecType getColspec() {
		return (ColspecType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Colspec(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColspec(ColspecType newColspec, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Colspec(), newColspec, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColspec(ColspecType newColspec) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Colspec(), newColspec);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommentType getComment() {
		return (CommentType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Comment(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComment(CommentType newComment, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Comment(), newComment, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(CommentType newComment) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Comment(), newComment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentType getComponent() {
		return (ComponentType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Component(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComponent(ComponentType newComponent, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Component(), newComponent, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(ComponentType newComponent) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Component(), newComponent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CoordsType getCoords() {
		return (CoordsType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Coords(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCoords(CoordsType newCoords, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Coords(), newCoords, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCoords(CoordsType newCoords) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Coords(), newCoords);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CopyrholderType getCopyrholder() {
		return (CopyrholderType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Copyrholder(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCopyrholder(CopyrholderType newCopyrholder, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Copyrholder(), newCopyrholder, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCopyrholder(CopyrholderType newCopyrholder) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Copyrholder(), newCopyrholder);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CopyrightType getCopyright() {
		return (CopyrightType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Copyright(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCopyright(CopyrightType newCopyright, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Copyright(), newCopyright, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCopyright(CopyrightType newCopyright) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Copyright(), newCopyright);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CopyryearType getCopyryear() {
		return (CopyryearType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Copyryear(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCopyryear(CopyryearType newCopyryear, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Copyryear(), newCopyryear, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCopyryear(CopyryearType newCopyryear) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Copyryear(), newCopyryear);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreatedType getCreated() {
		return (CreatedType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Created(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreated(CreatedType newCreated, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Created(), newCreated, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreated(CreatedType newCreated) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Created(), newCreated);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CritdatesType getCritdates() {
		return (CritdatesType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Critdates(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCritdates(CritdatesType newCritdates, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Critdates(), newCritdates, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCritdates(CritdatesType newCritdates) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Critdates(), newCritdates);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType getData() {
		return (DataType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Data(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetData(DataType newData, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Data(), newData, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setData(DataType newData) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Data(), newData);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataAboutType getDataAbout() {
		return (DataAboutType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_DataAbout(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDataAbout(DataAboutType newDataAbout, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_DataAbout(), newDataAbout, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataAbout(DataAboutType newDataAbout) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_DataAbout(), newDataAbout);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DdType getDd() {
		return (DdType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Dd(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDd(DdType newDd, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Dd(), newDd, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDd(DdType newDd) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Dd(), newDd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DdhdType getDdhd() {
		return (DdhdType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Ddhd(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDdhd(DdhdType newDdhd, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Ddhd(), newDdhd, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDdhd(DdhdType newDdhd) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Ddhd(), newDdhd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DelimType getDelim() {
		return (DelimType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Delim(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDelim(DelimType newDelim, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Delim(), newDelim, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDelim(DelimType newDelim) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Delim(), newDelim);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DescType getDesc() {
		return (DescType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Desc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDesc(DescType newDesc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Desc(), newDesc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDesc(DescType newDesc) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Desc(), newDesc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeviceType getDevice() {
		return (DeviceType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Device(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDevice(DeviceType newDevice, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Device(), newDevice, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDevice(DeviceType newDevice) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Device(), newDevice);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DlType getDl() {
		return (DlType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Dl(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDl(DlType newDl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Dl(), newDl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDl(DlType newDl) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Dl(), newDl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DlentryType getDlentry() {
		return (DlentryType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Dlentry(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDlentry(DlentryType newDlentry, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Dlentry(), newDlentry, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDlentry(DlentryType newDlentry) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Dlentry(), newDlentry);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DlheadType getDlhead() {
		return (DlheadType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Dlhead(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDlhead(DlheadType newDlhead, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Dlhead(), newDlhead, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDlhead(DlheadType newDlhead) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Dlhead(), newDlhead);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DraftCommentType getDraftComment() {
		return (DraftCommentType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_DraftComment(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDraftComment(DraftCommentType newDraftComment, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_DraftComment(), newDraftComment, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDraftComment(DraftCommentType newDraftComment) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_DraftComment(), newDraftComment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DtType getDt() {
		return (DtType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Dt(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDt(DtType newDt, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Dt(), newDt, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDt(DtType newDt) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Dt(), newDt);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DthdType getDthd() {
		return (DthdType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Dthd(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDthd(DthdType newDthd, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Dthd(), newDthd, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDthd(DthdType newDthd) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Dthd(), newDthd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntryType getEntry() {
		return (EntryType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Entry(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntry(EntryType newEntry, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Entry(), newEntry, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntry(EntryType newEntry) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Entry(), newEntry);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExampleType getExample() {
		return (ExampleType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Example(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExample(ExampleType newExample, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Example(), newExample, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExample(ExampleType newExample) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Example(), newExample);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatnumType getFeatnum() {
		return (FeatnumType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Featnum(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeatnum(FeatnumType newFeatnum, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Featnum(), newFeatnum, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatnum(FeatnumType newFeatnum) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Featnum(), newFeatnum);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FigType getFig() {
		return (FigType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Fig(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFig(FigType newFig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Fig(), newFig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFig(FigType newFig) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Fig(), newFig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FiggroupType getFiggroup() {
		return (FiggroupType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Figgroup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFiggroup(FiggroupType newFiggroup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Figgroup(), newFiggroup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFiggroup(FiggroupType newFiggroup) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Figgroup(), newFiggroup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FilepathType getFilepath() {
		return (FilepathType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Filepath(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFilepath(FilepathType newFilepath, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Filepath(), newFilepath, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilepath(FilepathType newFilepath) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Filepath(), newFilepath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FiletypeType getFiletype() {
		return (FiletypeType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Filetype(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFiletype(FiletypeType newFiletype, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Filetype(), newFiletype, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFiletype(FiletypeType newFiletype) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Filetype(), newFiletype);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FnType getFn() {
		return (FnType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Fn(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFn(FnType newFn, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Fn(), newFn, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFn(FnType newFn) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Fn(), newFn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForeignType getForeign() {
		return (ForeignType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Foreign(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetForeign(ForeignType newForeign, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Foreign(), newForeign, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForeign(ForeignType newForeign) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Foreign(), newForeign);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FragmentType getFragment() {
		return (FragmentType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Fragment(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFragment(FragmentType newFragment, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Fragment(), newFragment, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFragment(FragmentType newFragment) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Fragment(), newFragment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FragrefType getFragref() {
		return (FragrefType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Fragref(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFragref(FragrefType newFragref, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Fragref(), newFragref, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFragref(FragrefType newFragref) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Fragref(), newFragref);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupchoiceType getGroupchoice() {
		return (GroupchoiceType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Groupchoice(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGroupchoice(GroupchoiceType newGroupchoice, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Groupchoice(), newGroupchoice, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroupchoice(GroupchoiceType newGroupchoice) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Groupchoice(), newGroupchoice);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupcompType getGroupcomp() {
		return (GroupcompType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Groupcomp(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGroupcomp(GroupcompType newGroupcomp, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Groupcomp(), newGroupcomp, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroupcomp(GroupcompType newGroupcomp) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Groupcomp(), newGroupcomp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupseqType getGroupseq() {
		return (GroupseqType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Groupseq(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGroupseq(GroupseqType newGroupseq, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Groupseq(), newGroupseq, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroupseq(GroupseqType newGroupseq) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Groupseq(), newGroupseq);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IType getI() {
		return (IType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_I(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetI(IType newI, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_I(), newI, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setI(IType newI) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_I(), newI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImageType getImage() {
		return (ImageType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Image(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetImage(ImageType newImage, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Image(), newImage, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImage(ImageType newImage) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Image(), newImage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImagemapType getImagemap() {
		return (ImagemapType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Imagemap(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetImagemap(ImagemapType newImagemap, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Imagemap(), newImagemap, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImagemap(ImagemapType newImagemap) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Imagemap(), newImagemap);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexBaseType getIndexBase() {
		return (IndexBaseType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_IndexBase(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndexBase(IndexBaseType newIndexBase, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_IndexBase(), newIndexBase, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndexBase(IndexBaseType newIndexBase) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_IndexBase(), newIndexBase);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexSeeType getIndexSee() {
		return (IndexSeeType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_IndexSee(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndexSee(IndexSeeType newIndexSee, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_IndexSee(), newIndexSee, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndexSee(IndexSeeType newIndexSee) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_IndexSee(), newIndexSee);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexSeeAlsoType getIndexSeeAlso() {
		return (IndexSeeAlsoType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_IndexSeeAlso(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndexSeeAlso(IndexSeeAlsoType newIndexSeeAlso, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_IndexSeeAlso(), newIndexSeeAlso, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndexSeeAlso(IndexSeeAlsoType newIndexSeeAlso) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_IndexSeeAlso(), newIndexSeeAlso);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexSortAsType getIndexSortAs() {
		return (IndexSortAsType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_IndexSortAs(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndexSortAs(IndexSortAsType newIndexSortAs, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_IndexSortAs(), newIndexSortAs, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndexSortAs(IndexSortAsType newIndexSortAs) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_IndexSortAs(), newIndexSortAs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndextermType getIndexterm() {
		return (IndextermType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Indexterm(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndexterm(IndextermType newIndexterm, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Indexterm(), newIndexterm, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndexterm(IndextermType newIndexterm) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Indexterm(), newIndexterm);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndextermrefType getIndextermref() {
		return (IndextermrefType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Indextermref(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndextermref(IndextermrefType newIndextermref, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Indextermref(), newIndextermref, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndextermref(IndextermrefType newIndextermref) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Indextermref(), newIndextermref);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InheritanceType getInheritance() {
		return (InheritanceType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Inheritance(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInheritance(InheritanceType newInheritance, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Inheritance(), newInheritance, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInheritance(InheritanceType newInheritance) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Inheritance(), newInheritance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ItemgroupType getItemgroup() {
		return (ItemgroupType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Itemgroup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetItemgroup(ItemgroupType newItemgroup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Itemgroup(), newItemgroup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setItemgroup(ItemgroupType newItemgroup) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Itemgroup(), newItemgroup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataType getKbdata() {
		return (KbdataType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Kbdata(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKbdata(KbdataType newKbdata, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Kbdata(), newKbdata, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKbdata(KbdataType newKbdata) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Kbdata(), newKbdata);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataBodyType getKbdataBody() {
		return (KbdataBodyType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataBody(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKbdataBody(KbdataBodyType newKbdataBody, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataBody(), newKbdataBody, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKbdataBody(KbdataBodyType newKbdataBody) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataBody(), newKbdataBody);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataCategoryType getKbdataCategory() {
		return (KbdataCategoryType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataCategory(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKbdataCategory(KbdataCategoryType newKbdataCategory, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataCategory(), newKbdataCategory, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKbdataCategory(KbdataCategoryType newKbdataCategory) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataCategory(), newKbdataCategory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataIdType getKbdataId() {
		return (KbdataIdType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataId(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKbdataId(KbdataIdType newKbdataId, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataId(), newKbdataId, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKbdataId(KbdataIdType newKbdataId) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataId(), newKbdataId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataKeywordType getKbdataKeyword() {
		return (KbdataKeywordType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataKeyword(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKbdataKeyword(KbdataKeywordType newKbdataKeyword, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataKeyword(), newKbdataKeyword, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKbdataKeyword(KbdataKeywordType newKbdataKeyword) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataKeyword(), newKbdataKeyword);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataKeywordsType getKbdataKeywords() {
		return (KbdataKeywordsType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataKeywords(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKbdataKeywords(KbdataKeywordsType newKbdataKeywords, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataKeywords(), newKbdataKeywords, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKbdataKeywords(KbdataKeywordsType newKbdataKeywords) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataKeywords(), newKbdataKeywords);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataMetadataType getKbdataMetadata() {
		return (KbdataMetadataType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataMetadata(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKbdataMetadata(KbdataMetadataType newKbdataMetadata, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataMetadata(), newKbdataMetadata, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKbdataMetadata(KbdataMetadataType newKbdataMetadata) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataMetadata(), newKbdataMetadata);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataPlatformType getKbdataPlatform() {
		return (KbdataPlatformType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataPlatform(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKbdataPlatform(KbdataPlatformType newKbdataPlatform, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataPlatform(), newKbdataPlatform, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKbdataPlatform(KbdataPlatformType newKbdataPlatform) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataPlatform(), newKbdataPlatform);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataRefType getKbdataRef() {
		return (KbdataRefType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataRef(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKbdataRef(KbdataRefType newKbdataRef, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataRef(), newKbdataRef, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKbdataRef(KbdataRefType newKbdataRef) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_KbdataRef(), newKbdataRef);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KeywordType getKeyword() {
		return (KeywordType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Keyword(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKeyword(KeywordType newKeyword, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Keyword(), newKeyword, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeyword(KeywordType newKeyword) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Keyword(), newKeyword);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KeywordsType getKeywords() {
		return (KeywordsType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Keywords(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKeywords(KeywordsType newKeywords, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Keywords(), newKeywords, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeywords(KeywordsType newKeywords) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Keywords(), newKeywords);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KwdType getKwd() {
		return (KwdType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Kwd(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKwd(KwdType newKwd, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Kwd(), newKwd, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKwd(KwdType newKwd) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Kwd(), newKwd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LiType getLi() {
		return (LiType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Li(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLi(LiType newLi, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Li(), newLi, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLi(LiType newLi) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Li(), newLi);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinesType getLines() {
		return (LinesType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Lines(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLines(LinesType newLines, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Lines(), newLines, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLines(LinesType newLines) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Lines(), newLines);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkType getLink() {
		return (LinkType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Link(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLink(LinkType newLink, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Link(), newLink, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLink(LinkType newLink) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Link(), newLink);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkinfoType getLinkinfo() {
		return (LinkinfoType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Linkinfo(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLinkinfo(LinkinfoType newLinkinfo, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Linkinfo(), newLinkinfo, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLinkinfo(LinkinfoType newLinkinfo) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Linkinfo(), newLinkinfo);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinklistType getLinklist() {
		return (LinklistType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Linklist(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLinklist(LinklistType newLinklist, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Linklist(), newLinklist, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLinklist(LinklistType newLinklist) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Linklist(), newLinklist);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkpoolType getLinkpool() {
		return (LinkpoolType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Linkpool(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLinkpool(LinkpoolType newLinkpool, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Linkpool(), newLinkpool, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLinkpool(LinkpoolType newLinkpool) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Linkpool(), newLinkpool);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinktextType getLinktext() {
		return (LinktextType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Linktext(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLinktext(LinktextType newLinktext, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Linktext(), newLinktext, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLinktext(LinktextType newLinktext) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Linktext(), newLinktext);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalType getLocal() {
		return (LocalType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Local(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocal(LocalType newLocal, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Local(), newLocal, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocal(LocalType newLocal) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Local(), newLocal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LqType getLq() {
		return (LqType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Lq(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLq(LqType newLq, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Lq(), newLq, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLq(LqType newLq) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Lq(), newLq);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MacroType getMacro() {
		return (MacroType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Macro(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMacro(MacroType newMacro, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Macro(), newMacro, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMacro(MacroType newMacro) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Macro(), newMacro);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberType getMember() {
		return (MemberType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Member(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMember(MemberType newMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Member(), newMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMember(MemberType newMember) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Member(), newMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MenucascadeType getMenucascade() {
		return (MenucascadeType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Menucascade(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMenucascade(MenucascadeType newMenucascade, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Menucascade(), newMenucascade, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMenucascade(MenucascadeType newMenucascade) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Menucascade(), newMenucascade);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MessagesType getMessages() {
		return (MessagesType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Messages(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMessages(MessagesType newMessages, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Messages(), newMessages, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessages(MessagesType newMessages) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Messages(), newMessages);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetadataType getMetadata() {
		return (MetadataType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Metadata(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMetadata(MetadataType newMetadata, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Metadata(), newMetadata, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetadata(MetadataType newMetadata) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Metadata(), newMetadata);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MethodType getMethod() {
		return (MethodType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Method(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMethod(MethodType newMethod, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Method(), newMethod, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethod(MethodType newMethod) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Method(), newMethod);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MsgblockType getMsgblock() {
		return (MsgblockType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Msgblock(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMsgblock(MsgblockType newMsgblock, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Msgblock(), newMsgblock, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMsgblock(MsgblockType newMsgblock) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Msgblock(), newMsgblock);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MsgnumType getMsgnum() {
		return (MsgnumType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Msgnum(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMsgnum(MsgnumType newMsgnum, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Msgnum(), newMsgnum, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMsgnum(MsgnumType newMsgnum) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Msgnum(), newMsgnum);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MsgphType getMsgph() {
		return (MsgphType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Msgph(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMsgph(MsgphType newMsgph, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Msgph(), newMsgph, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMsgph(MsgphType newMsgph) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Msgph(), newMsgph);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NavtitleType getNavtitle() {
		return (NavtitleType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Navtitle(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNavtitle(NavtitleType newNavtitle, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Navtitle(), newNavtitle, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNavtitle(NavtitleType newNavtitle) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Navtitle(), newNavtitle);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NoteType getNote() {
		return (NoteType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Note(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNote(NoteType newNote, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Note(), newNote, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNote(NoteType newNote) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Note(), newNote);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NoTopicNestingType getNoTopicNesting() {
		return (NoTopicNestingType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_NoTopicNesting(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNoTopicNesting(NoTopicNestingType newNoTopicNesting, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_NoTopicNesting(), newNoTopicNesting, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNoTopicNesting(NoTopicNestingType newNoTopicNesting) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_NoTopicNesting(), newNoTopicNesting);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectType getObject() {
		return (ObjectType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Object(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetObject(ObjectType newObject, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Object(), newObject, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObject(ObjectType newObject) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Object(), newObject);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OlType getOl() {
		return (OlType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Ol(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOl(OlType newOl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Ol(), newOl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOl(OlType newOl) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Ol(), newOl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperType getOper() {
		return (OperType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Oper(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOper(OperType newOper, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Oper(), newOper, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOper(OperType newOper) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Oper(), newOper);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OptionType getOption() {
		return (OptionType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Option(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOption(OptionType newOption, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Option(), newOption, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOption(OptionType newOption) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Option(), newOption);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OthermetaType getOthermeta() {
		return (OthermetaType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Othermeta(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOthermeta(OthermetaType newOthermeta, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Othermeta(), newOthermeta, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOthermeta(OthermetaType newOthermeta) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Othermeta(), newOthermeta);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PType getP() {
		return (PType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_P(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetP(PType newP, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_P(), newP, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setP(PType newP) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_P(), newP);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParamType getParam() {
		return (ParamType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Param(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParam(ParamType newParam, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Param(), newParam, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParam(ParamType newParam) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Param(), newParam);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterType getParameter() {
		return (ParameterType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Parameter(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParameter(ParameterType newParameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Parameter(), newParameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParameter(ParameterType newParameter) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Parameter(), newParameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParmlType getParml() {
		return (ParmlType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Parml(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParml(ParmlType newParml, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Parml(), newParml, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParml(ParmlType newParml) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Parml(), newParml);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParmnameType getParmname() {
		return (ParmnameType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Parmname(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParmname(ParmnameType newParmname, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Parmname(), newParmname, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParmname(ParmnameType newParmname) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Parmname(), newParmname);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PdType getPd() {
		return (PdType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Pd(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPd(PdType newPd, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Pd(), newPd, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPd(PdType newPd) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Pd(), newPd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PermissionsType getPermissions() {
		return (PermissionsType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Permissions(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPermissions(PermissionsType newPermissions, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Permissions(), newPermissions, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPermissions(PermissionsType newPermissions) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Permissions(), newPermissions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhType getPh() {
		return (PhType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Ph(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPh(PhType newPh, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Ph(), newPh, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPh(PhType newPh) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Ph(), newPh);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlatformType getPlatform() {
		return (PlatformType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Platform(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlatform(PlatformType newPlatform, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Platform(), newPlatform, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlatform(PlatformType newPlatform) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Platform(), newPlatform);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlentryType getPlentry() {
		return (PlentryType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Plentry(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlentry(PlentryType newPlentry, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Plentry(), newPlentry, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlentry(PlentryType newPlentry) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Plentry(), newPlentry);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreType getPre() {
		return (PreType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Pre(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPre(PreType newPre, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Pre(), newPre, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPre(PreType newPre) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Pre(), newPre);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProdinfoType getProdinfo() {
		return (ProdinfoType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Prodinfo(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProdinfo(ProdinfoType newProdinfo, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Prodinfo(), newProdinfo, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProdinfo(ProdinfoType newProdinfo) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Prodinfo(), newProdinfo);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProdnameType getProdname() {
		return (ProdnameType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Prodname(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProdname(ProdnameType newProdname, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Prodname(), newProdname, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProdname(ProdnameType newProdname) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Prodname(), newProdname);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrognumType getPrognum() {
		return (PrognumType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Prognum(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrognum(PrognumType newPrognum, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Prognum(), newPrognum, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrognum(PrognumType newPrognum) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Prognum(), newPrognum);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrologType getProlog() {
		return (PrologType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Prolog(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProlog(PrologType newProlog, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Prolog(), newProlog, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProlog(PrologType newProlog) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Prolog(), newProlog);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropdescType getPropdesc() {
		return (PropdescType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Propdesc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropdesc(PropdescType newPropdesc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Propdesc(), newPropdesc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropdesc(PropdescType newPropdesc) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Propdesc(), newPropdesc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropdeschdType getPropdeschd() {
		return (PropdeschdType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Propdeschd(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropdeschd(PropdeschdType newPropdeschd, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Propdeschd(), newPropdeschd, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropdeschd(PropdeschdType newPropdeschd) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Propdeschd(), newPropdeschd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesType getProperties() {
		return (PropertiesType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Properties(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(PropertiesType newProperties, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Properties(), newProperties, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperties(PropertiesType newProperties) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Properties(), newProperties);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType getProperty() {
		return (PropertyType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Property(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperty(PropertyType newProperty, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Property(), newProperty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperty(PropertyType newProperty) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Property(), newProperty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropheadType getProphead() {
		return (PropheadType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Prophead(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProphead(PropheadType newProphead, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Prophead(), newProphead, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProphead(PropheadType newProphead) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Prophead(), newProphead);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProptypeType getProptype() {
		return (ProptypeType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Proptype(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProptype(ProptypeType newProptype, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Proptype(), newProptype, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProptype(ProptypeType newProptype) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Proptype(), newProptype);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProptypehdType getProptypehd() {
		return (ProptypehdType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Proptypehd(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProptypehd(ProptypehdType newProptypehd, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Proptypehd(), newProptypehd, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProptypehd(ProptypehdType newProptypehd) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Proptypehd(), newProptypehd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropvalueType getPropvalue() {
		return (PropvalueType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Propvalue(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropvalue(PropvalueType newPropvalue, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Propvalue(), newPropvalue, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropvalue(PropvalueType newPropvalue) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Propvalue(), newPropvalue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropvaluehdType getPropvaluehd() {
		return (PropvaluehdType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Propvaluehd(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropvaluehd(PropvaluehdType newPropvaluehd, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Propvaluehd(), newPropvaluehd, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropvaluehd(PropvaluehdType newPropvaluehd) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Propvaluehd(), newPropvaluehd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PtType getPt() {
		return (PtType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Pt(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPt(PtType newPt, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Pt(), newPt, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPt(PtType newPt) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Pt(), newPt);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PublisherType getPublisher() {
		return (PublisherType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Publisher(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPublisher(PublisherType newPublisher, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Publisher(), newPublisher, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublisher(PublisherType newPublisher) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Publisher(), newPublisher);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QType getQ() {
		return (QType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Q(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQ(QType newQ, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Q(), newQ, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQ(QType newQ) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Q(), newQ);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RefbodyType getRefbody() {
		return (RefbodyType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Refbody(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRefbody(RefbodyType newRefbody, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Refbody(), newRefbody, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRefbody(RefbodyType newRefbody) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Refbody(), newRefbody);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceType getReference() {
		return (ReferenceType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Reference(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReference(ReferenceType newReference, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Reference(), newReference, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReference(ReferenceType newReference) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Reference(), newReference);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RefsynType getRefsyn() {
		return (RefsynType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Refsyn(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRefsyn(RefsynType newRefsyn, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Refsyn(), newRefsyn, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRefsyn(RefsynType newRefsyn) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Refsyn(), newRefsyn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelatedLinksType getRelatedLinks() {
		return (RelatedLinksType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_RelatedLinks(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRelatedLinks(RelatedLinksType newRelatedLinks, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_RelatedLinks(), newRelatedLinks, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelatedLinks(RelatedLinksType newRelatedLinks) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_RelatedLinks(), newRelatedLinks);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepsepType getRepsep() {
		return (RepsepType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Repsep(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRepsep(RepsepType newRepsep, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Repsep(), newRepsep, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepsep(RepsepType newRepsep) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Repsep(), newRepsep);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredCleanupType getRequiredCleanup() {
		return (RequiredCleanupType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_RequiredCleanup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRequiredCleanup(RequiredCleanupType newRequiredCleanup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_RequiredCleanup(), newRequiredCleanup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequiredCleanup(RequiredCleanupType newRequiredCleanup) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_RequiredCleanup(), newRequiredCleanup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceidType getResourceid() {
		return (ResourceidType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Resourceid(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResourceid(ResourceidType newResourceid, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Resourceid(), newResourceid, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceid(ResourceidType newResourceid) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Resourceid(), newResourceid);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevisedType getRevised() {
		return (RevisedType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Revised(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRevised(RevisedType newRevised, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Revised(), newRevised, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRevised(RevisedType newRevised) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Revised(), newRevised);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RowType getRow() {
		return (RowType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Row(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRow(RowType newRow, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Row(), newRow, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRow(RowType newRow) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Row(), newRow);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScreenType getScreen() {
		return (ScreenType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Screen(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScreen(ScreenType newScreen, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Screen(), newScreen, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScreen(ScreenType newScreen) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Screen(), newScreen);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SearchtitleType getSearchtitle() {
		return (SearchtitleType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Searchtitle(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSearchtitle(SearchtitleType newSearchtitle, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Searchtitle(), newSearchtitle, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSearchtitle(SearchtitleType newSearchtitle) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Searchtitle(), newSearchtitle);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SectionType getSection() {
		return (SectionType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Section(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSection(SectionType newSection, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Section(), newSection, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSection(SectionType newSection) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Section(), newSection);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SepType getSep() {
		return (SepType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Sep(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSep(SepType newSep, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Sep(), newSep, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSep(SepType newSep) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Sep(), newSep);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeriesType getSeries() {
		return (SeriesType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Series(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSeries(SeriesType newSeries, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Series(), newSeries, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeries(SeriesType newSeries) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Series(), newSeries);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShapeType getShape() {
		return (ShapeType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Shape(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShape(ShapeType newShape, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Shape(), newShape, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShape(ShapeType newShape) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Shape(), newShape);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShortcutType getShortcut() {
		return (ShortcutType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Shortcut(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShortcut(ShortcutType newShortcut, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Shortcut(), newShortcut, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShortcut(ShortcutType newShortcut) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Shortcut(), newShortcut);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShortdescType getShortdesc() {
		return (ShortdescType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Shortdesc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShortdesc(ShortdescType newShortdesc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Shortdesc(), newShortdesc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShortdesc(ShortdescType newShortdesc) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Shortdesc(), newShortdesc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpletableType getSimpletable() {
		return (SimpletableType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Simpletable(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSimpletable(SimpletableType newSimpletable, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Simpletable(), newSimpletable, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSimpletable(SimpletableType newSimpletable) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Simpletable(), newSimpletable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlType getSl() {
		return (SlType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Sl(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSl(SlType newSl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Sl(), newSl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSl(SlType newSl) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Sl(), newSl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SliType getSli() {
		return (SliType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Sli(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSli(SliType newSli, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Sli(), newSli, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSli(SliType newSli) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Sli(), newSli);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SolutionType getSolution() {
		return (SolutionType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Solution(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSolution(SolutionType newSolution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Solution(), newSolution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSolution(SolutionType newSolution) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Solution(), newSolution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourceType getSource() {
		return (SourceType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Source(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(SourceType newSource, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Source(), newSource, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(SourceType newSource) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Source(), newSource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateType getState() {
		return (StateType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_State(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetState(StateType newState, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_State(), newState, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(StateType newState) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_State(), newState);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StentryType getStentry() {
		return (StentryType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Stentry(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStentry(StentryType newStentry, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Stentry(), newStentry, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStentry(StentryType newStentry) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Stentry(), newStentry);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StheadType getSthead() {
		return (StheadType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Sthead(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSthead(StheadType newSthead, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Sthead(), newSthead, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSthead(StheadType newSthead) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Sthead(), newSthead);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StrowType getStrow() {
		return (StrowType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Strow(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStrow(StrowType newStrow, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Strow(), newStrow, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStrow(StrowType newStrow) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Strow(), newStrow);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubType getSub() {
		return (SubType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Sub(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSub(SubType newSub, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Sub(), newSub, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSub(SubType newSub) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Sub(), newSub);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SupType getSup() {
		return (SupType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Sup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSup(SupType newSup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Sup(), newSup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSup(SupType newSup) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Sup(), newSup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymptomType getSymptom() {
		return (SymptomType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Symptom(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSymptom(SymptomType newSymptom, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Symptom(), newSymptom, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymptom(SymptomType newSymptom) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Symptom(), newSymptom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SynblkType getSynblk() {
		return (SynblkType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Synblk(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSynblk(SynblkType newSynblk, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Synblk(), newSynblk, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSynblk(SynblkType newSynblk) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Synblk(), newSynblk);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SynnoteType getSynnote() {
		return (SynnoteType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Synnote(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSynnote(SynnoteType newSynnote, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Synnote(), newSynnote, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSynnote(SynnoteType newSynnote) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Synnote(), newSynnote);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SynnoterefType getSynnoteref() {
		return (SynnoterefType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Synnoteref(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSynnoteref(SynnoterefType newSynnoteref, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Synnoteref(), newSynnoteref, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSynnoteref(SynnoterefType newSynnoteref) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Synnoteref(), newSynnoteref);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SynphType getSynph() {
		return (SynphType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Synph(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSynph(SynphType newSynph, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Synph(), newSynph, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSynph(SynphType newSynph) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Synph(), newSynph);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SyntaxdiagramType getSyntaxdiagram() {
		return (SyntaxdiagramType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Syntaxdiagram(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSyntaxdiagram(SyntaxdiagramType newSyntaxdiagram, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Syntaxdiagram(), newSyntaxdiagram, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSyntaxdiagram(SyntaxdiagramType newSyntaxdiagram) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Syntaxdiagram(), newSyntaxdiagram);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemoutputType getSystemoutput() {
		return (SystemoutputType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Systemoutput(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSystemoutput(SystemoutputType newSystemoutput, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Systemoutput(), newSystemoutput, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSystemoutput(SystemoutputType newSystemoutput) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Systemoutput(), newSystemoutput);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TableType getTable() {
		return (TableType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Table(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTable(TableType newTable, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Table(), newTable, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTable(TableType newTable) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Table(), newTable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TbodyType getTbody() {
		return (TbodyType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Tbody(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTbody(TbodyType newTbody, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Tbody(), newTbody, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTbody(TbodyType newTbody) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Tbody(), newTbody);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TermType getTerm() {
		return (TermType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Term(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTerm(TermType newTerm, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Term(), newTerm, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTerm(TermType newTerm) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Term(), newTerm);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TgroupType getTgroup() {
		return (TgroupType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Tgroup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTgroup(TgroupType newTgroup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Tgroup(), newTgroup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTgroup(TgroupType newTgroup) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Tgroup(), newTgroup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TheadType getThead() {
		return (TheadType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Thead(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetThead(TheadType newThead, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Thead(), newThead, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThead(TheadType newThead) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Thead(), newThead);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TitleType getTitle() {
		return (TitleType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Title(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTitle(TitleType newTitle, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Title(), newTitle, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(TitleType newTitle) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Title(), newTitle);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TitlealtsType getTitlealts() {
		return (TitlealtsType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Titlealts(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTitlealts(TitlealtsType newTitlealts, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Titlealts(), newTitlealts, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitlealts(TitlealtsType newTitlealts) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Titlealts(), newTitlealts);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TmType getTm() {
		return (TmType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Tm(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTm(TmType newTm, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Tm(), newTm, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTm(TmType newTm) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Tm(), newTm);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TopicType getTopic() {
		return (TopicType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Topic(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTopic(TopicType newTopic, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Topic(), newTopic, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTopic(TopicType newTopic) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Topic(), newTopic);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TtType getTt() {
		return (TtType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Tt(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTt(TtType newTt, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Tt(), newTt, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTt(TtType newTt) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Tt(), newTt);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UType getU() {
		return (UType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_U(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetU(UType newU, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_U(), newU, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setU(UType newU) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_U(), newU);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UicontrolType getUicontrol() {
		return (UicontrolType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Uicontrol(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUicontrol(UicontrolType newUicontrol, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Uicontrol(), newUicontrol, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUicontrol(UicontrolType newUicontrol) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Uicontrol(), newUicontrol);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UlType getUl() {
		return (UlType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Ul(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUl(UlType newUl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Ul(), newUl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUl(UlType newUl) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Ul(), newUl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnknownType getUnknown() {
		return (UnknownType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Unknown(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUnknown(UnknownType newUnknown, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Unknown(), newUnknown, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnknown(UnknownType newUnknown) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Unknown(), newUnknown);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserinputType getUserinput() {
		return (UserinputType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Userinput(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserinput(UserinputType newUserinput, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Userinput(), newUserinput, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserinput(UserinputType newUserinput) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Userinput(), newUserinput);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VarType getVar() {
		return (VarType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Var(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVar(VarType newVar, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Var(), newVar, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVar(VarType newVar) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Var(), newVar);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VarnameType getVarname() {
		return (VarnameType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Varname(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVarname(VarnameType newVarname, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Varname(), newVarname, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVarname(VarnameType newVarname) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Varname(), newVarname);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VrmType getVrm() {
		return (VrmType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Vrm(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVrm(VrmType newVrm, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Vrm(), newVrm, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVrm(VrmType newVrm) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Vrm(), newVrm);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VrmlistType getVrmlist() {
		return (VrmlistType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Vrmlist(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVrmlist(VrmlistType newVrmlist, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Vrmlist(), newVrmlist, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVrmlist(VrmlistType newVrmlist) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Vrmlist(), newVrmlist);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WintitleType getWintitle() {
		return (WintitleType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Wintitle(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWintitle(WintitleType newWintitle, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Wintitle(), newWintitle, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWintitle(WintitleType newWintitle) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Wintitle(), newWintitle);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XrefType getXref() {
		return (XrefType)getMixed().get(KbdataPackage.eINSTANCE.getDocumentRoot_Xref(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXref(XrefType newXref, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(KbdataPackage.eINSTANCE.getDocumentRoot_Xref(), newXref, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setXref(XrefType newXref) {
		((FeatureMap.Internal)getMixed()).set(KbdataPackage.eINSTANCE.getDocumentRoot_Xref(), newXref);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getDITAArchVersion() {
		return dITAArchVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDITAArchVersion(Object newDITAArchVersion) {
		Object oldDITAArchVersion = dITAArchVersion;
		dITAArchVersion = newDITAArchVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.DOCUMENT_ROOT__DITA_ARCH_VERSION, oldDITAArchVersion, dITAArchVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLang(String newLang) {
		String oldLang = lang;
		lang = newLang;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.DOCUMENT_ROOT__LANG, oldLang, lang));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SpaceType getSpace() {
		return space;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpace(SpaceType newSpace) {
		SpaceType oldSpace = space;
		space = newSpace == null ? SPACE_EDEFAULT : newSpace;
		boolean oldSpaceESet = spaceESet;
		spaceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.DOCUMENT_ROOT__SPACE, oldSpace, space, !oldSpaceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSpace() {
		SpaceType oldSpace = space;
		boolean oldSpaceESet = spaceESet;
		space = SPACE_EDEFAULT;
		spaceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.DOCUMENT_ROOT__SPACE, oldSpace, SPACE_EDEFAULT, oldSpaceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSpace() {
		return spaceESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case KbdataPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case KbdataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case KbdataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case KbdataPackage.DOCUMENT_ROOT__ABSTRACT:
				return basicSetAbstract(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__ALT:
				return basicSetAlt(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__APINAME:
				return basicSetApiname(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__AREA:
				return basicSetArea(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__AUDIENCE:
				return basicSetAudience(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__AUTHOR:
				return basicSetAuthor(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__B:
				return basicSetB(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__BODY:
				return basicSetBody(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__BOOLEAN:
				return basicSetBoolean(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__BRAND:
				return basicSetBrand(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__CALL:
				return basicSetCall(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__CATEGORY:
				return basicSetCategory(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__CITE:
				return basicSetCite(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__CLASS:
				return basicSetClass(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__CMDNAME:
				return basicSetCmdname(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__CODEBLOCK:
				return basicSetCodeblock(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__CODEPH:
				return basicSetCodeph(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__COLSPEC:
				return basicSetColspec(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__COMMENT:
				return basicSetComment(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__COMPONENT:
				return basicSetComponent(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__COORDS:
				return basicSetCoords(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__COPYRHOLDER:
				return basicSetCopyrholder(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__COPYRIGHT:
				return basicSetCopyright(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__COPYRYEAR:
				return basicSetCopyryear(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__CREATED:
				return basicSetCreated(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__CRITDATES:
				return basicSetCritdates(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DATA:
				return basicSetData(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DATA_ABOUT:
				return basicSetDataAbout(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DD:
				return basicSetDd(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DDHD:
				return basicSetDdhd(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DELIM:
				return basicSetDelim(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DESC:
				return basicSetDesc(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DEVICE:
				return basicSetDevice(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DL:
				return basicSetDl(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DLENTRY:
				return basicSetDlentry(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DLHEAD:
				return basicSetDlhead(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DRAFT_COMMENT:
				return basicSetDraftComment(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DT:
				return basicSetDt(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__DTHD:
				return basicSetDthd(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__ENTRY:
				return basicSetEntry(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__EXAMPLE:
				return basicSetExample(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__FEATNUM:
				return basicSetFeatnum(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__FIG:
				return basicSetFig(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__FIGGROUP:
				return basicSetFiggroup(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__FILEPATH:
				return basicSetFilepath(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__FILETYPE:
				return basicSetFiletype(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__FN:
				return basicSetFn(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__FOREIGN:
				return basicSetForeign(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__FRAGMENT:
				return basicSetFragment(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__FRAGREF:
				return basicSetFragref(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__GROUPCHOICE:
				return basicSetGroupchoice(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__GROUPCOMP:
				return basicSetGroupcomp(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__GROUPSEQ:
				return basicSetGroupseq(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__I:
				return basicSetI(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__IMAGE:
				return basicSetImage(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__IMAGEMAP:
				return basicSetImagemap(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__INDEX_BASE:
				return basicSetIndexBase(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SEE:
				return basicSetIndexSee(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SEE_ALSO:
				return basicSetIndexSeeAlso(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SORT_AS:
				return basicSetIndexSortAs(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__INDEXTERM:
				return basicSetIndexterm(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__INDEXTERMREF:
				return basicSetIndextermref(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__INHERITANCE:
				return basicSetInheritance(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__ITEMGROUP:
				return basicSetItemgroup(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KBDATA:
				return basicSetKbdata(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_BODY:
				return basicSetKbdataBody(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_CATEGORY:
				return basicSetKbdataCategory(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_ID:
				return basicSetKbdataId(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_KEYWORD:
				return basicSetKbdataKeyword(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_KEYWORDS:
				return basicSetKbdataKeywords(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_METADATA:
				return basicSetKbdataMetadata(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_PLATFORM:
				return basicSetKbdataPlatform(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_REF:
				return basicSetKbdataRef(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KEYWORD:
				return basicSetKeyword(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KEYWORDS:
				return basicSetKeywords(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__KWD:
				return basicSetKwd(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__LI:
				return basicSetLi(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__LINES:
				return basicSetLines(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__LINK:
				return basicSetLink(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__LINKINFO:
				return basicSetLinkinfo(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__LINKLIST:
				return basicSetLinklist(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__LINKPOOL:
				return basicSetLinkpool(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__LINKTEXT:
				return basicSetLinktext(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__LOCAL:
				return basicSetLocal(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__LQ:
				return basicSetLq(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__MACRO:
				return basicSetMacro(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__MEMBER:
				return basicSetMember(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__MENUCASCADE:
				return basicSetMenucascade(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__MESSAGES:
				return basicSetMessages(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__METADATA:
				return basicSetMetadata(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__METHOD:
				return basicSetMethod(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__MSGBLOCK:
				return basicSetMsgblock(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__MSGNUM:
				return basicSetMsgnum(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__MSGPH:
				return basicSetMsgph(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__NAVTITLE:
				return basicSetNavtitle(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__NOTE:
				return basicSetNote(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__NO_TOPIC_NESTING:
				return basicSetNoTopicNesting(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__OBJECT:
				return basicSetObject(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__OL:
				return basicSetOl(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__OPER:
				return basicSetOper(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__OPTION:
				return basicSetOption(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__OTHERMETA:
				return basicSetOthermeta(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__P:
				return basicSetP(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PARAM:
				return basicSetParam(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PARAMETER:
				return basicSetParameter(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PARML:
				return basicSetParml(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PARMNAME:
				return basicSetParmname(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PD:
				return basicSetPd(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PERMISSIONS:
				return basicSetPermissions(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PH:
				return basicSetPh(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PLATFORM:
				return basicSetPlatform(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PLENTRY:
				return basicSetPlentry(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PRE:
				return basicSetPre(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PRODINFO:
				return basicSetProdinfo(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PRODNAME:
				return basicSetProdname(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PROGNUM:
				return basicSetPrognum(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PROLOG:
				return basicSetProlog(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PROPDESC:
				return basicSetPropdesc(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PROPDESCHD:
				return basicSetPropdeschd(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PROPERTIES:
				return basicSetProperties(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PROPERTY:
				return basicSetProperty(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PROPHEAD:
				return basicSetProphead(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PROPTYPE:
				return basicSetProptype(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PROPTYPEHD:
				return basicSetProptypehd(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PROPVALUE:
				return basicSetPropvalue(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PROPVALUEHD:
				return basicSetPropvaluehd(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PT:
				return basicSetPt(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__PUBLISHER:
				return basicSetPublisher(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__Q:
				return basicSetQ(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__REFBODY:
				return basicSetRefbody(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__REFERENCE:
				return basicSetReference(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__REFSYN:
				return basicSetRefsyn(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__RELATED_LINKS:
				return basicSetRelatedLinks(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__REPSEP:
				return basicSetRepsep(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__REQUIRED_CLEANUP:
				return basicSetRequiredCleanup(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__RESOURCEID:
				return basicSetResourceid(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__REVISED:
				return basicSetRevised(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__ROW:
				return basicSetRow(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SCREEN:
				return basicSetScreen(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SEARCHTITLE:
				return basicSetSearchtitle(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SECTION:
				return basicSetSection(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SEP:
				return basicSetSep(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SERIES:
				return basicSetSeries(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SHAPE:
				return basicSetShape(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SHORTCUT:
				return basicSetShortcut(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SHORTDESC:
				return basicSetShortdesc(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SIMPLETABLE:
				return basicSetSimpletable(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SL:
				return basicSetSl(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SLI:
				return basicSetSli(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SOLUTION:
				return basicSetSolution(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SOURCE:
				return basicSetSource(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__STATE:
				return basicSetState(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__STENTRY:
				return basicSetStentry(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__STHEAD:
				return basicSetSthead(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__STROW:
				return basicSetStrow(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SUB:
				return basicSetSub(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SUP:
				return basicSetSup(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SYMPTOM:
				return basicSetSymptom(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SYNBLK:
				return basicSetSynblk(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SYNNOTE:
				return basicSetSynnote(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SYNNOTEREF:
				return basicSetSynnoteref(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SYNPH:
				return basicSetSynph(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SYNTAXDIAGRAM:
				return basicSetSyntaxdiagram(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__SYSTEMOUTPUT:
				return basicSetSystemoutput(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__TABLE:
				return basicSetTable(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__TBODY:
				return basicSetTbody(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__TERM:
				return basicSetTerm(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__TGROUP:
				return basicSetTgroup(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__THEAD:
				return basicSetThead(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__TITLE:
				return basicSetTitle(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__TITLEALTS:
				return basicSetTitlealts(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__TM:
				return basicSetTm(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__TOPIC:
				return basicSetTopic(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__TT:
				return basicSetTt(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__U:
				return basicSetU(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__UICONTROL:
				return basicSetUicontrol(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__UL:
				return basicSetUl(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__UNKNOWN:
				return basicSetUnknown(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__USERINPUT:
				return basicSetUserinput(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__VAR:
				return basicSetVar(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__VARNAME:
				return basicSetVarname(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__VRM:
				return basicSetVrm(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__VRMLIST:
				return basicSetVrmlist(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__WINTITLE:
				return basicSetWintitle(null, msgs);
			case KbdataPackage.DOCUMENT_ROOT__XREF:
				return basicSetXref(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case KbdataPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case KbdataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case KbdataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case KbdataPackage.DOCUMENT_ROOT__ABSTRACT:
				return getAbstract();
			case KbdataPackage.DOCUMENT_ROOT__ALT:
				return getAlt();
			case KbdataPackage.DOCUMENT_ROOT__APINAME:
				return getApiname();
			case KbdataPackage.DOCUMENT_ROOT__AREA:
				return getArea();
			case KbdataPackage.DOCUMENT_ROOT__AUDIENCE:
				return getAudience();
			case KbdataPackage.DOCUMENT_ROOT__AUTHOR:
				return getAuthor();
			case KbdataPackage.DOCUMENT_ROOT__B:
				return getB();
			case KbdataPackage.DOCUMENT_ROOT__BODY:
				return getBody();
			case KbdataPackage.DOCUMENT_ROOT__BOOLEAN:
				return getBoolean();
			case KbdataPackage.DOCUMENT_ROOT__BRAND:
				return getBrand();
			case KbdataPackage.DOCUMENT_ROOT__CALL:
				return getCall();
			case KbdataPackage.DOCUMENT_ROOT__CATEGORY:
				return getCategory();
			case KbdataPackage.DOCUMENT_ROOT__CITE:
				return getCite();
			case KbdataPackage.DOCUMENT_ROOT__CLASS:
				return getClass_();
			case KbdataPackage.DOCUMENT_ROOT__CMDNAME:
				return getCmdname();
			case KbdataPackage.DOCUMENT_ROOT__CODEBLOCK:
				return getCodeblock();
			case KbdataPackage.DOCUMENT_ROOT__CODEPH:
				return getCodeph();
			case KbdataPackage.DOCUMENT_ROOT__COLSPEC:
				return getColspec();
			case KbdataPackage.DOCUMENT_ROOT__COMMENT:
				return getComment();
			case KbdataPackage.DOCUMENT_ROOT__COMPONENT:
				return getComponent();
			case KbdataPackage.DOCUMENT_ROOT__COORDS:
				return getCoords();
			case KbdataPackage.DOCUMENT_ROOT__COPYRHOLDER:
				return getCopyrholder();
			case KbdataPackage.DOCUMENT_ROOT__COPYRIGHT:
				return getCopyright();
			case KbdataPackage.DOCUMENT_ROOT__COPYRYEAR:
				return getCopyryear();
			case KbdataPackage.DOCUMENT_ROOT__CREATED:
				return getCreated();
			case KbdataPackage.DOCUMENT_ROOT__CRITDATES:
				return getCritdates();
			case KbdataPackage.DOCUMENT_ROOT__DATA:
				return getData();
			case KbdataPackage.DOCUMENT_ROOT__DATA_ABOUT:
				return getDataAbout();
			case KbdataPackage.DOCUMENT_ROOT__DD:
				return getDd();
			case KbdataPackage.DOCUMENT_ROOT__DDHD:
				return getDdhd();
			case KbdataPackage.DOCUMENT_ROOT__DELIM:
				return getDelim();
			case KbdataPackage.DOCUMENT_ROOT__DESC:
				return getDesc();
			case KbdataPackage.DOCUMENT_ROOT__DEVICE:
				return getDevice();
			case KbdataPackage.DOCUMENT_ROOT__DL:
				return getDl();
			case KbdataPackage.DOCUMENT_ROOT__DLENTRY:
				return getDlentry();
			case KbdataPackage.DOCUMENT_ROOT__DLHEAD:
				return getDlhead();
			case KbdataPackage.DOCUMENT_ROOT__DRAFT_COMMENT:
				return getDraftComment();
			case KbdataPackage.DOCUMENT_ROOT__DT:
				return getDt();
			case KbdataPackage.DOCUMENT_ROOT__DTHD:
				return getDthd();
			case KbdataPackage.DOCUMENT_ROOT__ENTRY:
				return getEntry();
			case KbdataPackage.DOCUMENT_ROOT__EXAMPLE:
				return getExample();
			case KbdataPackage.DOCUMENT_ROOT__FEATNUM:
				return getFeatnum();
			case KbdataPackage.DOCUMENT_ROOT__FIG:
				return getFig();
			case KbdataPackage.DOCUMENT_ROOT__FIGGROUP:
				return getFiggroup();
			case KbdataPackage.DOCUMENT_ROOT__FILEPATH:
				return getFilepath();
			case KbdataPackage.DOCUMENT_ROOT__FILETYPE:
				return getFiletype();
			case KbdataPackage.DOCUMENT_ROOT__FN:
				return getFn();
			case KbdataPackage.DOCUMENT_ROOT__FOREIGN:
				return getForeign();
			case KbdataPackage.DOCUMENT_ROOT__FRAGMENT:
				return getFragment();
			case KbdataPackage.DOCUMENT_ROOT__FRAGREF:
				return getFragref();
			case KbdataPackage.DOCUMENT_ROOT__GROUPCHOICE:
				return getGroupchoice();
			case KbdataPackage.DOCUMENT_ROOT__GROUPCOMP:
				return getGroupcomp();
			case KbdataPackage.DOCUMENT_ROOT__GROUPSEQ:
				return getGroupseq();
			case KbdataPackage.DOCUMENT_ROOT__I:
				return getI();
			case KbdataPackage.DOCUMENT_ROOT__IMAGE:
				return getImage();
			case KbdataPackage.DOCUMENT_ROOT__IMAGEMAP:
				return getImagemap();
			case KbdataPackage.DOCUMENT_ROOT__INDEX_BASE:
				return getIndexBase();
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SEE:
				return getIndexSee();
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SEE_ALSO:
				return getIndexSeeAlso();
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SORT_AS:
				return getIndexSortAs();
			case KbdataPackage.DOCUMENT_ROOT__INDEXTERM:
				return getIndexterm();
			case KbdataPackage.DOCUMENT_ROOT__INDEXTERMREF:
				return getIndextermref();
			case KbdataPackage.DOCUMENT_ROOT__INHERITANCE:
				return getInheritance();
			case KbdataPackage.DOCUMENT_ROOT__ITEMGROUP:
				return getItemgroup();
			case KbdataPackage.DOCUMENT_ROOT__KBDATA:
				return getKbdata();
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_BODY:
				return getKbdataBody();
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_CATEGORY:
				return getKbdataCategory();
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_ID:
				return getKbdataId();
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_KEYWORD:
				return getKbdataKeyword();
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_KEYWORDS:
				return getKbdataKeywords();
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_METADATA:
				return getKbdataMetadata();
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_PLATFORM:
				return getKbdataPlatform();
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_REF:
				return getKbdataRef();
			case KbdataPackage.DOCUMENT_ROOT__KEYWORD:
				return getKeyword();
			case KbdataPackage.DOCUMENT_ROOT__KEYWORDS:
				return getKeywords();
			case KbdataPackage.DOCUMENT_ROOT__KWD:
				return getKwd();
			case KbdataPackage.DOCUMENT_ROOT__LI:
				return getLi();
			case KbdataPackage.DOCUMENT_ROOT__LINES:
				return getLines();
			case KbdataPackage.DOCUMENT_ROOT__LINK:
				return getLink();
			case KbdataPackage.DOCUMENT_ROOT__LINKINFO:
				return getLinkinfo();
			case KbdataPackage.DOCUMENT_ROOT__LINKLIST:
				return getLinklist();
			case KbdataPackage.DOCUMENT_ROOT__LINKPOOL:
				return getLinkpool();
			case KbdataPackage.DOCUMENT_ROOT__LINKTEXT:
				return getLinktext();
			case KbdataPackage.DOCUMENT_ROOT__LOCAL:
				return getLocal();
			case KbdataPackage.DOCUMENT_ROOT__LQ:
				return getLq();
			case KbdataPackage.DOCUMENT_ROOT__MACRO:
				return getMacro();
			case KbdataPackage.DOCUMENT_ROOT__MEMBER:
				return getMember();
			case KbdataPackage.DOCUMENT_ROOT__MENUCASCADE:
				return getMenucascade();
			case KbdataPackage.DOCUMENT_ROOT__MESSAGES:
				return getMessages();
			case KbdataPackage.DOCUMENT_ROOT__METADATA:
				return getMetadata();
			case KbdataPackage.DOCUMENT_ROOT__METHOD:
				return getMethod();
			case KbdataPackage.DOCUMENT_ROOT__MSGBLOCK:
				return getMsgblock();
			case KbdataPackage.DOCUMENT_ROOT__MSGNUM:
				return getMsgnum();
			case KbdataPackage.DOCUMENT_ROOT__MSGPH:
				return getMsgph();
			case KbdataPackage.DOCUMENT_ROOT__NAVTITLE:
				return getNavtitle();
			case KbdataPackage.DOCUMENT_ROOT__NOTE:
				return getNote();
			case KbdataPackage.DOCUMENT_ROOT__NO_TOPIC_NESTING:
				return getNoTopicNesting();
			case KbdataPackage.DOCUMENT_ROOT__OBJECT:
				return getObject();
			case KbdataPackage.DOCUMENT_ROOT__OL:
				return getOl();
			case KbdataPackage.DOCUMENT_ROOT__OPER:
				return getOper();
			case KbdataPackage.DOCUMENT_ROOT__OPTION:
				return getOption();
			case KbdataPackage.DOCUMENT_ROOT__OTHERMETA:
				return getOthermeta();
			case KbdataPackage.DOCUMENT_ROOT__P:
				return getP();
			case KbdataPackage.DOCUMENT_ROOT__PARAM:
				return getParam();
			case KbdataPackage.DOCUMENT_ROOT__PARAMETER:
				return getParameter();
			case KbdataPackage.DOCUMENT_ROOT__PARML:
				return getParml();
			case KbdataPackage.DOCUMENT_ROOT__PARMNAME:
				return getParmname();
			case KbdataPackage.DOCUMENT_ROOT__PD:
				return getPd();
			case KbdataPackage.DOCUMENT_ROOT__PERMISSIONS:
				return getPermissions();
			case KbdataPackage.DOCUMENT_ROOT__PH:
				return getPh();
			case KbdataPackage.DOCUMENT_ROOT__PLATFORM:
				return getPlatform();
			case KbdataPackage.DOCUMENT_ROOT__PLENTRY:
				return getPlentry();
			case KbdataPackage.DOCUMENT_ROOT__PRE:
				return getPre();
			case KbdataPackage.DOCUMENT_ROOT__PRODINFO:
				return getProdinfo();
			case KbdataPackage.DOCUMENT_ROOT__PRODNAME:
				return getProdname();
			case KbdataPackage.DOCUMENT_ROOT__PROGNUM:
				return getPrognum();
			case KbdataPackage.DOCUMENT_ROOT__PROLOG:
				return getProlog();
			case KbdataPackage.DOCUMENT_ROOT__PROPDESC:
				return getPropdesc();
			case KbdataPackage.DOCUMENT_ROOT__PROPDESCHD:
				return getPropdeschd();
			case KbdataPackage.DOCUMENT_ROOT__PROPERTIES:
				return getProperties();
			case KbdataPackage.DOCUMENT_ROOT__PROPERTY:
				return getProperty();
			case KbdataPackage.DOCUMENT_ROOT__PROPHEAD:
				return getProphead();
			case KbdataPackage.DOCUMENT_ROOT__PROPTYPE:
				return getProptype();
			case KbdataPackage.DOCUMENT_ROOT__PROPTYPEHD:
				return getProptypehd();
			case KbdataPackage.DOCUMENT_ROOT__PROPVALUE:
				return getPropvalue();
			case KbdataPackage.DOCUMENT_ROOT__PROPVALUEHD:
				return getPropvaluehd();
			case KbdataPackage.DOCUMENT_ROOT__PT:
				return getPt();
			case KbdataPackage.DOCUMENT_ROOT__PUBLISHER:
				return getPublisher();
			case KbdataPackage.DOCUMENT_ROOT__Q:
				return getQ();
			case KbdataPackage.DOCUMENT_ROOT__REFBODY:
				return getRefbody();
			case KbdataPackage.DOCUMENT_ROOT__REFERENCE:
				return getReference();
			case KbdataPackage.DOCUMENT_ROOT__REFSYN:
				return getRefsyn();
			case KbdataPackage.DOCUMENT_ROOT__RELATED_LINKS:
				return getRelatedLinks();
			case KbdataPackage.DOCUMENT_ROOT__REPSEP:
				return getRepsep();
			case KbdataPackage.DOCUMENT_ROOT__REQUIRED_CLEANUP:
				return getRequiredCleanup();
			case KbdataPackage.DOCUMENT_ROOT__RESOURCEID:
				return getResourceid();
			case KbdataPackage.DOCUMENT_ROOT__REVISED:
				return getRevised();
			case KbdataPackage.DOCUMENT_ROOT__ROW:
				return getRow();
			case KbdataPackage.DOCUMENT_ROOT__SCREEN:
				return getScreen();
			case KbdataPackage.DOCUMENT_ROOT__SEARCHTITLE:
				return getSearchtitle();
			case KbdataPackage.DOCUMENT_ROOT__SECTION:
				return getSection();
			case KbdataPackage.DOCUMENT_ROOT__SEP:
				return getSep();
			case KbdataPackage.DOCUMENT_ROOT__SERIES:
				return getSeries();
			case KbdataPackage.DOCUMENT_ROOT__SHAPE:
				return getShape();
			case KbdataPackage.DOCUMENT_ROOT__SHORTCUT:
				return getShortcut();
			case KbdataPackage.DOCUMENT_ROOT__SHORTDESC:
				return getShortdesc();
			case KbdataPackage.DOCUMENT_ROOT__SIMPLETABLE:
				return getSimpletable();
			case KbdataPackage.DOCUMENT_ROOT__SL:
				return getSl();
			case KbdataPackage.DOCUMENT_ROOT__SLI:
				return getSli();
			case KbdataPackage.DOCUMENT_ROOT__SOLUTION:
				return getSolution();
			case KbdataPackage.DOCUMENT_ROOT__SOURCE:
				return getSource();
			case KbdataPackage.DOCUMENT_ROOT__STATE:
				return getState();
			case KbdataPackage.DOCUMENT_ROOT__STENTRY:
				return getStentry();
			case KbdataPackage.DOCUMENT_ROOT__STHEAD:
				return getSthead();
			case KbdataPackage.DOCUMENT_ROOT__STROW:
				return getStrow();
			case KbdataPackage.DOCUMENT_ROOT__SUB:
				return getSub();
			case KbdataPackage.DOCUMENT_ROOT__SUP:
				return getSup();
			case KbdataPackage.DOCUMENT_ROOT__SYMPTOM:
				return getSymptom();
			case KbdataPackage.DOCUMENT_ROOT__SYNBLK:
				return getSynblk();
			case KbdataPackage.DOCUMENT_ROOT__SYNNOTE:
				return getSynnote();
			case KbdataPackage.DOCUMENT_ROOT__SYNNOTEREF:
				return getSynnoteref();
			case KbdataPackage.DOCUMENT_ROOT__SYNPH:
				return getSynph();
			case KbdataPackage.DOCUMENT_ROOT__SYNTAXDIAGRAM:
				return getSyntaxdiagram();
			case KbdataPackage.DOCUMENT_ROOT__SYSTEMOUTPUT:
				return getSystemoutput();
			case KbdataPackage.DOCUMENT_ROOT__TABLE:
				return getTable();
			case KbdataPackage.DOCUMENT_ROOT__TBODY:
				return getTbody();
			case KbdataPackage.DOCUMENT_ROOT__TERM:
				return getTerm();
			case KbdataPackage.DOCUMENT_ROOT__TGROUP:
				return getTgroup();
			case KbdataPackage.DOCUMENT_ROOT__THEAD:
				return getThead();
			case KbdataPackage.DOCUMENT_ROOT__TITLE:
				return getTitle();
			case KbdataPackage.DOCUMENT_ROOT__TITLEALTS:
				return getTitlealts();
			case KbdataPackage.DOCUMENT_ROOT__TM:
				return getTm();
			case KbdataPackage.DOCUMENT_ROOT__TOPIC:
				return getTopic();
			case KbdataPackage.DOCUMENT_ROOT__TT:
				return getTt();
			case KbdataPackage.DOCUMENT_ROOT__U:
				return getU();
			case KbdataPackage.DOCUMENT_ROOT__UICONTROL:
				return getUicontrol();
			case KbdataPackage.DOCUMENT_ROOT__UL:
				return getUl();
			case KbdataPackage.DOCUMENT_ROOT__UNKNOWN:
				return getUnknown();
			case KbdataPackage.DOCUMENT_ROOT__USERINPUT:
				return getUserinput();
			case KbdataPackage.DOCUMENT_ROOT__VAR:
				return getVar();
			case KbdataPackage.DOCUMENT_ROOT__VARNAME:
				return getVarname();
			case KbdataPackage.DOCUMENT_ROOT__VRM:
				return getVrm();
			case KbdataPackage.DOCUMENT_ROOT__VRMLIST:
				return getVrmlist();
			case KbdataPackage.DOCUMENT_ROOT__WINTITLE:
				return getWintitle();
			case KbdataPackage.DOCUMENT_ROOT__XREF:
				return getXref();
			case KbdataPackage.DOCUMENT_ROOT__DITA_ARCH_VERSION:
				return getDITAArchVersion();
			case KbdataPackage.DOCUMENT_ROOT__LANG:
				return getLang();
			case KbdataPackage.DOCUMENT_ROOT__SPACE:
				return getSpace();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case KbdataPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__ABSTRACT:
				setAbstract((AbstractType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__ALT:
				setAlt((AltType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__APINAME:
				setApiname((ApinameType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__AREA:
				setArea((AreaType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__AUDIENCE:
				setAudience((AudienceType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__AUTHOR:
				setAuthor((AuthorType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__B:
				setB((BType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__BODY:
				setBody((BodyType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__BOOLEAN:
				setBoolean((BooleanType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__BRAND:
				setBrand((BrandType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CALL:
				setCall((CallType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CATEGORY:
				setCategory((CategoryType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CITE:
				setCite((CiteType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CLASS:
				setClass((ClassType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CMDNAME:
				setCmdname((CmdnameType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CODEBLOCK:
				setCodeblock((CodeblockType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CODEPH:
				setCodeph((CodephType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COLSPEC:
				setColspec((ColspecType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COMMENT:
				setComment((CommentType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COMPONENT:
				setComponent((ComponentType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COORDS:
				setCoords((CoordsType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COPYRHOLDER:
				setCopyrholder((CopyrholderType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COPYRIGHT:
				setCopyright((CopyrightType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COPYRYEAR:
				setCopyryear((CopyryearType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CREATED:
				setCreated((CreatedType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CRITDATES:
				setCritdates((CritdatesType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DATA:
				setData((DataType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DATA_ABOUT:
				setDataAbout((DataAboutType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DD:
				setDd((DdType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DDHD:
				setDdhd((DdhdType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DELIM:
				setDelim((DelimType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DESC:
				setDesc((DescType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DEVICE:
				setDevice((DeviceType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DL:
				setDl((DlType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DLENTRY:
				setDlentry((DlentryType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DLHEAD:
				setDlhead((DlheadType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DRAFT_COMMENT:
				setDraftComment((DraftCommentType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DT:
				setDt((DtType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DTHD:
				setDthd((DthdType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__ENTRY:
				setEntry((EntryType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__EXAMPLE:
				setExample((ExampleType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FEATNUM:
				setFeatnum((FeatnumType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FIG:
				setFig((FigType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FIGGROUP:
				setFiggroup((FiggroupType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FILEPATH:
				setFilepath((FilepathType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FILETYPE:
				setFiletype((FiletypeType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FN:
				setFn((FnType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FOREIGN:
				setForeign((ForeignType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FRAGMENT:
				setFragment((FragmentType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FRAGREF:
				setFragref((FragrefType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__GROUPCHOICE:
				setGroupchoice((GroupchoiceType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__GROUPCOMP:
				setGroupcomp((GroupcompType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__GROUPSEQ:
				setGroupseq((GroupseqType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__I:
				setI((IType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__IMAGE:
				setImage((ImageType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__IMAGEMAP:
				setImagemap((ImagemapType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_BASE:
				setIndexBase((IndexBaseType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SEE:
				setIndexSee((IndexSeeType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SEE_ALSO:
				setIndexSeeAlso((IndexSeeAlsoType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SORT_AS:
				setIndexSortAs((IndexSortAsType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEXTERM:
				setIndexterm((IndextermType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEXTERMREF:
				setIndextermref((IndextermrefType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INHERITANCE:
				setInheritance((InheritanceType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__ITEMGROUP:
				setItemgroup((ItemgroupType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA:
				setKbdata((KbdataType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_BODY:
				setKbdataBody((KbdataBodyType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_CATEGORY:
				setKbdataCategory((KbdataCategoryType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_ID:
				setKbdataId((KbdataIdType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_KEYWORD:
				setKbdataKeyword((KbdataKeywordType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_KEYWORDS:
				setKbdataKeywords((KbdataKeywordsType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_METADATA:
				setKbdataMetadata((KbdataMetadataType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_PLATFORM:
				setKbdataPlatform((KbdataPlatformType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_REF:
				setKbdataRef((KbdataRefType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KEYWORD:
				setKeyword((KeywordType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KEYWORDS:
				setKeywords((KeywordsType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KWD:
				setKwd((KwdType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LI:
				setLi((LiType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINES:
				setLines((LinesType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINK:
				setLink((LinkType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINKINFO:
				setLinkinfo((LinkinfoType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINKLIST:
				setLinklist((LinklistType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINKPOOL:
				setLinkpool((LinkpoolType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINKTEXT:
				setLinktext((LinktextType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LOCAL:
				setLocal((LocalType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LQ:
				setLq((LqType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MACRO:
				setMacro((MacroType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MEMBER:
				setMember((MemberType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MENUCASCADE:
				setMenucascade((MenucascadeType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MESSAGES:
				setMessages((MessagesType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__METADATA:
				setMetadata((MetadataType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__METHOD:
				setMethod((MethodType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MSGBLOCK:
				setMsgblock((MsgblockType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MSGNUM:
				setMsgnum((MsgnumType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MSGPH:
				setMsgph((MsgphType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__NAVTITLE:
				setNavtitle((NavtitleType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__NOTE:
				setNote((NoteType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__NO_TOPIC_NESTING:
				setNoTopicNesting((NoTopicNestingType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__OBJECT:
				setObject((ObjectType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__OL:
				setOl((OlType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__OPER:
				setOper((OperType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__OPTION:
				setOption((OptionType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__OTHERMETA:
				setOthermeta((OthermetaType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__P:
				setP((PType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PARAM:
				setParam((ParamType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PARAMETER:
				setParameter((ParameterType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PARML:
				setParml((ParmlType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PARMNAME:
				setParmname((ParmnameType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PD:
				setPd((PdType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PERMISSIONS:
				setPermissions((PermissionsType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PH:
				setPh((PhType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PLATFORM:
				setPlatform((PlatformType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PLENTRY:
				setPlentry((PlentryType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PRE:
				setPre((PreType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PRODINFO:
				setProdinfo((ProdinfoType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PRODNAME:
				setProdname((ProdnameType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROGNUM:
				setPrognum((PrognumType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROLOG:
				setProlog((PrologType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPDESC:
				setPropdesc((PropdescType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPDESCHD:
				setPropdeschd((PropdeschdType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPERTIES:
				setProperties((PropertiesType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPERTY:
				setProperty((PropertyType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPHEAD:
				setProphead((PropheadType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPTYPE:
				setProptype((ProptypeType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPTYPEHD:
				setProptypehd((ProptypehdType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPVALUE:
				setPropvalue((PropvalueType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPVALUEHD:
				setPropvaluehd((PropvaluehdType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PT:
				setPt((PtType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PUBLISHER:
				setPublisher((PublisherType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__Q:
				setQ((QType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REFBODY:
				setRefbody((RefbodyType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REFERENCE:
				setReference((ReferenceType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REFSYN:
				setRefsyn((RefsynType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__RELATED_LINKS:
				setRelatedLinks((RelatedLinksType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REPSEP:
				setRepsep((RepsepType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REQUIRED_CLEANUP:
				setRequiredCleanup((RequiredCleanupType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__RESOURCEID:
				setResourceid((ResourceidType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REVISED:
				setRevised((RevisedType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__ROW:
				setRow((RowType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SCREEN:
				setScreen((ScreenType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SEARCHTITLE:
				setSearchtitle((SearchtitleType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SECTION:
				setSection((SectionType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SEP:
				setSep((SepType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SERIES:
				setSeries((SeriesType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SHAPE:
				setShape((ShapeType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SHORTCUT:
				setShortcut((ShortcutType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SHORTDESC:
				setShortdesc((ShortdescType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SIMPLETABLE:
				setSimpletable((SimpletableType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SL:
				setSl((SlType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SLI:
				setSli((SliType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SOLUTION:
				setSolution((SolutionType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SOURCE:
				setSource((SourceType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__STATE:
				setState((StateType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__STENTRY:
				setStentry((StentryType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__STHEAD:
				setSthead((StheadType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__STROW:
				setStrow((StrowType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SUB:
				setSub((SubType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SUP:
				setSup((SupType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYMPTOM:
				setSymptom((SymptomType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYNBLK:
				setSynblk((SynblkType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYNNOTE:
				setSynnote((SynnoteType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYNNOTEREF:
				setSynnoteref((SynnoterefType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYNPH:
				setSynph((SynphType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYNTAXDIAGRAM:
				setSyntaxdiagram((SyntaxdiagramType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYSTEMOUTPUT:
				setSystemoutput((SystemoutputType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TABLE:
				setTable((TableType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TBODY:
				setTbody((TbodyType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TERM:
				setTerm((TermType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TGROUP:
				setTgroup((TgroupType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__THEAD:
				setThead((TheadType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TITLE:
				setTitle((TitleType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TITLEALTS:
				setTitlealts((TitlealtsType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TM:
				setTm((TmType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TOPIC:
				setTopic((TopicType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TT:
				setTt((TtType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__U:
				setU((UType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__UICONTROL:
				setUicontrol((UicontrolType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__UL:
				setUl((UlType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__UNKNOWN:
				setUnknown((UnknownType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__USERINPUT:
				setUserinput((UserinputType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__VAR:
				setVar((VarType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__VARNAME:
				setVarname((VarnameType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__VRM:
				setVrm((VrmType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__VRMLIST:
				setVrmlist((VrmlistType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__WINTITLE:
				setWintitle((WintitleType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__XREF:
				setXref((XrefType)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DITA_ARCH_VERSION:
				setDITAArchVersion(newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LANG:
				setLang((String)newValue);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SPACE:
				setSpace((SpaceType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case KbdataPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case KbdataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case KbdataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case KbdataPackage.DOCUMENT_ROOT__ABSTRACT:
				setAbstract((AbstractType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__ALT:
				setAlt((AltType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__APINAME:
				setApiname((ApinameType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__AREA:
				setArea((AreaType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__AUDIENCE:
				setAudience((AudienceType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__AUTHOR:
				setAuthor((AuthorType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__B:
				setB((BType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__BODY:
				setBody((BodyType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__BOOLEAN:
				setBoolean((BooleanType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__BRAND:
				setBrand((BrandType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CALL:
				setCall((CallType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CATEGORY:
				setCategory((CategoryType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CITE:
				setCite((CiteType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CLASS:
				setClass((ClassType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CMDNAME:
				setCmdname((CmdnameType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CODEBLOCK:
				setCodeblock((CodeblockType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CODEPH:
				setCodeph((CodephType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COLSPEC:
				setColspec((ColspecType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COMMENT:
				setComment((CommentType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COMPONENT:
				setComponent((ComponentType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COORDS:
				setCoords((CoordsType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COPYRHOLDER:
				setCopyrholder((CopyrholderType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COPYRIGHT:
				setCopyright((CopyrightType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__COPYRYEAR:
				setCopyryear((CopyryearType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CREATED:
				setCreated((CreatedType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__CRITDATES:
				setCritdates((CritdatesType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DATA:
				setData((DataType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DATA_ABOUT:
				setDataAbout((DataAboutType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DD:
				setDd((DdType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DDHD:
				setDdhd((DdhdType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DELIM:
				setDelim((DelimType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DESC:
				setDesc((DescType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DEVICE:
				setDevice((DeviceType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DL:
				setDl((DlType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DLENTRY:
				setDlentry((DlentryType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DLHEAD:
				setDlhead((DlheadType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DRAFT_COMMENT:
				setDraftComment((DraftCommentType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DT:
				setDt((DtType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DTHD:
				setDthd((DthdType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__ENTRY:
				setEntry((EntryType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__EXAMPLE:
				setExample((ExampleType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FEATNUM:
				setFeatnum((FeatnumType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FIG:
				setFig((FigType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FIGGROUP:
				setFiggroup((FiggroupType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FILEPATH:
				setFilepath((FilepathType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FILETYPE:
				setFiletype((FiletypeType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FN:
				setFn((FnType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FOREIGN:
				setForeign((ForeignType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FRAGMENT:
				setFragment((FragmentType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__FRAGREF:
				setFragref((FragrefType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__GROUPCHOICE:
				setGroupchoice((GroupchoiceType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__GROUPCOMP:
				setGroupcomp((GroupcompType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__GROUPSEQ:
				setGroupseq((GroupseqType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__I:
				setI((IType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__IMAGE:
				setImage((ImageType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__IMAGEMAP:
				setImagemap((ImagemapType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_BASE:
				setIndexBase((IndexBaseType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SEE:
				setIndexSee((IndexSeeType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SEE_ALSO:
				setIndexSeeAlso((IndexSeeAlsoType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SORT_AS:
				setIndexSortAs((IndexSortAsType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEXTERM:
				setIndexterm((IndextermType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INDEXTERMREF:
				setIndextermref((IndextermrefType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__INHERITANCE:
				setInheritance((InheritanceType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__ITEMGROUP:
				setItemgroup((ItemgroupType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA:
				setKbdata((KbdataType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_BODY:
				setKbdataBody((KbdataBodyType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_CATEGORY:
				setKbdataCategory((KbdataCategoryType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_ID:
				setKbdataId((KbdataIdType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_KEYWORD:
				setKbdataKeyword((KbdataKeywordType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_KEYWORDS:
				setKbdataKeywords((KbdataKeywordsType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_METADATA:
				setKbdataMetadata((KbdataMetadataType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_PLATFORM:
				setKbdataPlatform((KbdataPlatformType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_REF:
				setKbdataRef((KbdataRefType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KEYWORD:
				setKeyword((KeywordType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KEYWORDS:
				setKeywords((KeywordsType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__KWD:
				setKwd((KwdType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LI:
				setLi((LiType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINES:
				setLines((LinesType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINK:
				setLink((LinkType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINKINFO:
				setLinkinfo((LinkinfoType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINKLIST:
				setLinklist((LinklistType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINKPOOL:
				setLinkpool((LinkpoolType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LINKTEXT:
				setLinktext((LinktextType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LOCAL:
				setLocal((LocalType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LQ:
				setLq((LqType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MACRO:
				setMacro((MacroType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MEMBER:
				setMember((MemberType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MENUCASCADE:
				setMenucascade((MenucascadeType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MESSAGES:
				setMessages((MessagesType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__METADATA:
				setMetadata((MetadataType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__METHOD:
				setMethod((MethodType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MSGBLOCK:
				setMsgblock((MsgblockType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MSGNUM:
				setMsgnum((MsgnumType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__MSGPH:
				setMsgph((MsgphType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__NAVTITLE:
				setNavtitle((NavtitleType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__NOTE:
				setNote((NoteType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__NO_TOPIC_NESTING:
				setNoTopicNesting((NoTopicNestingType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__OBJECT:
				setObject((ObjectType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__OL:
				setOl((OlType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__OPER:
				setOper((OperType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__OPTION:
				setOption((OptionType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__OTHERMETA:
				setOthermeta((OthermetaType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__P:
				setP((PType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PARAM:
				setParam((ParamType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PARAMETER:
				setParameter((ParameterType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PARML:
				setParml((ParmlType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PARMNAME:
				setParmname((ParmnameType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PD:
				setPd((PdType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PERMISSIONS:
				setPermissions((PermissionsType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PH:
				setPh((PhType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PLATFORM:
				setPlatform((PlatformType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PLENTRY:
				setPlentry((PlentryType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PRE:
				setPre((PreType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PRODINFO:
				setProdinfo((ProdinfoType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PRODNAME:
				setProdname((ProdnameType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROGNUM:
				setPrognum((PrognumType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROLOG:
				setProlog((PrologType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPDESC:
				setPropdesc((PropdescType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPDESCHD:
				setPropdeschd((PropdeschdType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPERTIES:
				setProperties((PropertiesType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPERTY:
				setProperty((PropertyType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPHEAD:
				setProphead((PropheadType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPTYPE:
				setProptype((ProptypeType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPTYPEHD:
				setProptypehd((ProptypehdType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPVALUE:
				setPropvalue((PropvalueType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PROPVALUEHD:
				setPropvaluehd((PropvaluehdType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PT:
				setPt((PtType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__PUBLISHER:
				setPublisher((PublisherType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__Q:
				setQ((QType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REFBODY:
				setRefbody((RefbodyType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REFERENCE:
				setReference((ReferenceType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REFSYN:
				setRefsyn((RefsynType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__RELATED_LINKS:
				setRelatedLinks((RelatedLinksType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REPSEP:
				setRepsep((RepsepType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REQUIRED_CLEANUP:
				setRequiredCleanup((RequiredCleanupType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__RESOURCEID:
				setResourceid((ResourceidType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__REVISED:
				setRevised((RevisedType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__ROW:
				setRow((RowType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SCREEN:
				setScreen((ScreenType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SEARCHTITLE:
				setSearchtitle((SearchtitleType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SECTION:
				setSection((SectionType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SEP:
				setSep((SepType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SERIES:
				setSeries((SeriesType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SHAPE:
				setShape((ShapeType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SHORTCUT:
				setShortcut((ShortcutType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SHORTDESC:
				setShortdesc((ShortdescType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SIMPLETABLE:
				setSimpletable((SimpletableType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SL:
				setSl((SlType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SLI:
				setSli((SliType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SOLUTION:
				setSolution((SolutionType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SOURCE:
				setSource((SourceType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__STATE:
				setState((StateType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__STENTRY:
				setStentry((StentryType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__STHEAD:
				setSthead((StheadType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__STROW:
				setStrow((StrowType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SUB:
				setSub((SubType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SUP:
				setSup((SupType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYMPTOM:
				setSymptom((SymptomType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYNBLK:
				setSynblk((SynblkType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYNNOTE:
				setSynnote((SynnoteType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYNNOTEREF:
				setSynnoteref((SynnoterefType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYNPH:
				setSynph((SynphType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYNTAXDIAGRAM:
				setSyntaxdiagram((SyntaxdiagramType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SYSTEMOUTPUT:
				setSystemoutput((SystemoutputType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TABLE:
				setTable((TableType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TBODY:
				setTbody((TbodyType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TERM:
				setTerm((TermType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TGROUP:
				setTgroup((TgroupType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__THEAD:
				setThead((TheadType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TITLE:
				setTitle((TitleType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TITLEALTS:
				setTitlealts((TitlealtsType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TM:
				setTm((TmType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TOPIC:
				setTopic((TopicType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__TT:
				setTt((TtType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__U:
				setU((UType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__UICONTROL:
				setUicontrol((UicontrolType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__UL:
				setUl((UlType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__UNKNOWN:
				setUnknown((UnknownType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__USERINPUT:
				setUserinput((UserinputType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__VAR:
				setVar((VarType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__VARNAME:
				setVarname((VarnameType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__VRM:
				setVrm((VrmType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__VRMLIST:
				setVrmlist((VrmlistType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__WINTITLE:
				setWintitle((WintitleType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__XREF:
				setXref((XrefType)null);
				return;
			case KbdataPackage.DOCUMENT_ROOT__DITA_ARCH_VERSION:
				setDITAArchVersion(DITA_ARCH_VERSION_EDEFAULT);
				return;
			case KbdataPackage.DOCUMENT_ROOT__LANG:
				setLang(LANG_EDEFAULT);
				return;
			case KbdataPackage.DOCUMENT_ROOT__SPACE:
				unsetSpace();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case KbdataPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case KbdataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case KbdataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case KbdataPackage.DOCUMENT_ROOT__ABSTRACT:
				return getAbstract() != null;
			case KbdataPackage.DOCUMENT_ROOT__ALT:
				return getAlt() != null;
			case KbdataPackage.DOCUMENT_ROOT__APINAME:
				return getApiname() != null;
			case KbdataPackage.DOCUMENT_ROOT__AREA:
				return getArea() != null;
			case KbdataPackage.DOCUMENT_ROOT__AUDIENCE:
				return getAudience() != null;
			case KbdataPackage.DOCUMENT_ROOT__AUTHOR:
				return getAuthor() != null;
			case KbdataPackage.DOCUMENT_ROOT__B:
				return getB() != null;
			case KbdataPackage.DOCUMENT_ROOT__BODY:
				return getBody() != null;
			case KbdataPackage.DOCUMENT_ROOT__BOOLEAN:
				return getBoolean() != null;
			case KbdataPackage.DOCUMENT_ROOT__BRAND:
				return getBrand() != null;
			case KbdataPackage.DOCUMENT_ROOT__CALL:
				return getCall() != null;
			case KbdataPackage.DOCUMENT_ROOT__CATEGORY:
				return getCategory() != null;
			case KbdataPackage.DOCUMENT_ROOT__CITE:
				return getCite() != null;
			case KbdataPackage.DOCUMENT_ROOT__CLASS:
				return getClass_() != null;
			case KbdataPackage.DOCUMENT_ROOT__CMDNAME:
				return getCmdname() != null;
			case KbdataPackage.DOCUMENT_ROOT__CODEBLOCK:
				return getCodeblock() != null;
			case KbdataPackage.DOCUMENT_ROOT__CODEPH:
				return getCodeph() != null;
			case KbdataPackage.DOCUMENT_ROOT__COLSPEC:
				return getColspec() != null;
			case KbdataPackage.DOCUMENT_ROOT__COMMENT:
				return getComment() != null;
			case KbdataPackage.DOCUMENT_ROOT__COMPONENT:
				return getComponent() != null;
			case KbdataPackage.DOCUMENT_ROOT__COORDS:
				return getCoords() != null;
			case KbdataPackage.DOCUMENT_ROOT__COPYRHOLDER:
				return getCopyrholder() != null;
			case KbdataPackage.DOCUMENT_ROOT__COPYRIGHT:
				return getCopyright() != null;
			case KbdataPackage.DOCUMENT_ROOT__COPYRYEAR:
				return getCopyryear() != null;
			case KbdataPackage.DOCUMENT_ROOT__CREATED:
				return getCreated() != null;
			case KbdataPackage.DOCUMENT_ROOT__CRITDATES:
				return getCritdates() != null;
			case KbdataPackage.DOCUMENT_ROOT__DATA:
				return getData() != null;
			case KbdataPackage.DOCUMENT_ROOT__DATA_ABOUT:
				return getDataAbout() != null;
			case KbdataPackage.DOCUMENT_ROOT__DD:
				return getDd() != null;
			case KbdataPackage.DOCUMENT_ROOT__DDHD:
				return getDdhd() != null;
			case KbdataPackage.DOCUMENT_ROOT__DELIM:
				return getDelim() != null;
			case KbdataPackage.DOCUMENT_ROOT__DESC:
				return getDesc() != null;
			case KbdataPackage.DOCUMENT_ROOT__DEVICE:
				return getDevice() != null;
			case KbdataPackage.DOCUMENT_ROOT__DL:
				return getDl() != null;
			case KbdataPackage.DOCUMENT_ROOT__DLENTRY:
				return getDlentry() != null;
			case KbdataPackage.DOCUMENT_ROOT__DLHEAD:
				return getDlhead() != null;
			case KbdataPackage.DOCUMENT_ROOT__DRAFT_COMMENT:
				return getDraftComment() != null;
			case KbdataPackage.DOCUMENT_ROOT__DT:
				return getDt() != null;
			case KbdataPackage.DOCUMENT_ROOT__DTHD:
				return getDthd() != null;
			case KbdataPackage.DOCUMENT_ROOT__ENTRY:
				return getEntry() != null;
			case KbdataPackage.DOCUMENT_ROOT__EXAMPLE:
				return getExample() != null;
			case KbdataPackage.DOCUMENT_ROOT__FEATNUM:
				return getFeatnum() != null;
			case KbdataPackage.DOCUMENT_ROOT__FIG:
				return getFig() != null;
			case KbdataPackage.DOCUMENT_ROOT__FIGGROUP:
				return getFiggroup() != null;
			case KbdataPackage.DOCUMENT_ROOT__FILEPATH:
				return getFilepath() != null;
			case KbdataPackage.DOCUMENT_ROOT__FILETYPE:
				return getFiletype() != null;
			case KbdataPackage.DOCUMENT_ROOT__FN:
				return getFn() != null;
			case KbdataPackage.DOCUMENT_ROOT__FOREIGN:
				return getForeign() != null;
			case KbdataPackage.DOCUMENT_ROOT__FRAGMENT:
				return getFragment() != null;
			case KbdataPackage.DOCUMENT_ROOT__FRAGREF:
				return getFragref() != null;
			case KbdataPackage.DOCUMENT_ROOT__GROUPCHOICE:
				return getGroupchoice() != null;
			case KbdataPackage.DOCUMENT_ROOT__GROUPCOMP:
				return getGroupcomp() != null;
			case KbdataPackage.DOCUMENT_ROOT__GROUPSEQ:
				return getGroupseq() != null;
			case KbdataPackage.DOCUMENT_ROOT__I:
				return getI() != null;
			case KbdataPackage.DOCUMENT_ROOT__IMAGE:
				return getImage() != null;
			case KbdataPackage.DOCUMENT_ROOT__IMAGEMAP:
				return getImagemap() != null;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_BASE:
				return getIndexBase() != null;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SEE:
				return getIndexSee() != null;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SEE_ALSO:
				return getIndexSeeAlso() != null;
			case KbdataPackage.DOCUMENT_ROOT__INDEX_SORT_AS:
				return getIndexSortAs() != null;
			case KbdataPackage.DOCUMENT_ROOT__INDEXTERM:
				return getIndexterm() != null;
			case KbdataPackage.DOCUMENT_ROOT__INDEXTERMREF:
				return getIndextermref() != null;
			case KbdataPackage.DOCUMENT_ROOT__INHERITANCE:
				return getInheritance() != null;
			case KbdataPackage.DOCUMENT_ROOT__ITEMGROUP:
				return getItemgroup() != null;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA:
				return getKbdata() != null;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_BODY:
				return getKbdataBody() != null;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_CATEGORY:
				return getKbdataCategory() != null;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_ID:
				return getKbdataId() != null;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_KEYWORD:
				return getKbdataKeyword() != null;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_KEYWORDS:
				return getKbdataKeywords() != null;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_METADATA:
				return getKbdataMetadata() != null;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_PLATFORM:
				return getKbdataPlatform() != null;
			case KbdataPackage.DOCUMENT_ROOT__KBDATA_REF:
				return getKbdataRef() != null;
			case KbdataPackage.DOCUMENT_ROOT__KEYWORD:
				return getKeyword() != null;
			case KbdataPackage.DOCUMENT_ROOT__KEYWORDS:
				return getKeywords() != null;
			case KbdataPackage.DOCUMENT_ROOT__KWD:
				return getKwd() != null;
			case KbdataPackage.DOCUMENT_ROOT__LI:
				return getLi() != null;
			case KbdataPackage.DOCUMENT_ROOT__LINES:
				return getLines() != null;
			case KbdataPackage.DOCUMENT_ROOT__LINK:
				return getLink() != null;
			case KbdataPackage.DOCUMENT_ROOT__LINKINFO:
				return getLinkinfo() != null;
			case KbdataPackage.DOCUMENT_ROOT__LINKLIST:
				return getLinklist() != null;
			case KbdataPackage.DOCUMENT_ROOT__LINKPOOL:
				return getLinkpool() != null;
			case KbdataPackage.DOCUMENT_ROOT__LINKTEXT:
				return getLinktext() != null;
			case KbdataPackage.DOCUMENT_ROOT__LOCAL:
				return getLocal() != null;
			case KbdataPackage.DOCUMENT_ROOT__LQ:
				return getLq() != null;
			case KbdataPackage.DOCUMENT_ROOT__MACRO:
				return getMacro() != null;
			case KbdataPackage.DOCUMENT_ROOT__MEMBER:
				return getMember() != null;
			case KbdataPackage.DOCUMENT_ROOT__MENUCASCADE:
				return getMenucascade() != null;
			case KbdataPackage.DOCUMENT_ROOT__MESSAGES:
				return getMessages() != null;
			case KbdataPackage.DOCUMENT_ROOT__METADATA:
				return getMetadata() != null;
			case KbdataPackage.DOCUMENT_ROOT__METHOD:
				return getMethod() != null;
			case KbdataPackage.DOCUMENT_ROOT__MSGBLOCK:
				return getMsgblock() != null;
			case KbdataPackage.DOCUMENT_ROOT__MSGNUM:
				return getMsgnum() != null;
			case KbdataPackage.DOCUMENT_ROOT__MSGPH:
				return getMsgph() != null;
			case KbdataPackage.DOCUMENT_ROOT__NAVTITLE:
				return getNavtitle() != null;
			case KbdataPackage.DOCUMENT_ROOT__NOTE:
				return getNote() != null;
			case KbdataPackage.DOCUMENT_ROOT__NO_TOPIC_NESTING:
				return getNoTopicNesting() != null;
			case KbdataPackage.DOCUMENT_ROOT__OBJECT:
				return getObject() != null;
			case KbdataPackage.DOCUMENT_ROOT__OL:
				return getOl() != null;
			case KbdataPackage.DOCUMENT_ROOT__OPER:
				return getOper() != null;
			case KbdataPackage.DOCUMENT_ROOT__OPTION:
				return getOption() != null;
			case KbdataPackage.DOCUMENT_ROOT__OTHERMETA:
				return getOthermeta() != null;
			case KbdataPackage.DOCUMENT_ROOT__P:
				return getP() != null;
			case KbdataPackage.DOCUMENT_ROOT__PARAM:
				return getParam() != null;
			case KbdataPackage.DOCUMENT_ROOT__PARAMETER:
				return getParameter() != null;
			case KbdataPackage.DOCUMENT_ROOT__PARML:
				return getParml() != null;
			case KbdataPackage.DOCUMENT_ROOT__PARMNAME:
				return getParmname() != null;
			case KbdataPackage.DOCUMENT_ROOT__PD:
				return getPd() != null;
			case KbdataPackage.DOCUMENT_ROOT__PERMISSIONS:
				return getPermissions() != null;
			case KbdataPackage.DOCUMENT_ROOT__PH:
				return getPh() != null;
			case KbdataPackage.DOCUMENT_ROOT__PLATFORM:
				return getPlatform() != null;
			case KbdataPackage.DOCUMENT_ROOT__PLENTRY:
				return getPlentry() != null;
			case KbdataPackage.DOCUMENT_ROOT__PRE:
				return getPre() != null;
			case KbdataPackage.DOCUMENT_ROOT__PRODINFO:
				return getProdinfo() != null;
			case KbdataPackage.DOCUMENT_ROOT__PRODNAME:
				return getProdname() != null;
			case KbdataPackage.DOCUMENT_ROOT__PROGNUM:
				return getPrognum() != null;
			case KbdataPackage.DOCUMENT_ROOT__PROLOG:
				return getProlog() != null;
			case KbdataPackage.DOCUMENT_ROOT__PROPDESC:
				return getPropdesc() != null;
			case KbdataPackage.DOCUMENT_ROOT__PROPDESCHD:
				return getPropdeschd() != null;
			case KbdataPackage.DOCUMENT_ROOT__PROPERTIES:
				return getProperties() != null;
			case KbdataPackage.DOCUMENT_ROOT__PROPERTY:
				return getProperty() != null;
			case KbdataPackage.DOCUMENT_ROOT__PROPHEAD:
				return getProphead() != null;
			case KbdataPackage.DOCUMENT_ROOT__PROPTYPE:
				return getProptype() != null;
			case KbdataPackage.DOCUMENT_ROOT__PROPTYPEHD:
				return getProptypehd() != null;
			case KbdataPackage.DOCUMENT_ROOT__PROPVALUE:
				return getPropvalue() != null;
			case KbdataPackage.DOCUMENT_ROOT__PROPVALUEHD:
				return getPropvaluehd() != null;
			case KbdataPackage.DOCUMENT_ROOT__PT:
				return getPt() != null;
			case KbdataPackage.DOCUMENT_ROOT__PUBLISHER:
				return getPublisher() != null;
			case KbdataPackage.DOCUMENT_ROOT__Q:
				return getQ() != null;
			case KbdataPackage.DOCUMENT_ROOT__REFBODY:
				return getRefbody() != null;
			case KbdataPackage.DOCUMENT_ROOT__REFERENCE:
				return getReference() != null;
			case KbdataPackage.DOCUMENT_ROOT__REFSYN:
				return getRefsyn() != null;
			case KbdataPackage.DOCUMENT_ROOT__RELATED_LINKS:
				return getRelatedLinks() != null;
			case KbdataPackage.DOCUMENT_ROOT__REPSEP:
				return getRepsep() != null;
			case KbdataPackage.DOCUMENT_ROOT__REQUIRED_CLEANUP:
				return getRequiredCleanup() != null;
			case KbdataPackage.DOCUMENT_ROOT__RESOURCEID:
				return getResourceid() != null;
			case KbdataPackage.DOCUMENT_ROOT__REVISED:
				return getRevised() != null;
			case KbdataPackage.DOCUMENT_ROOT__ROW:
				return getRow() != null;
			case KbdataPackage.DOCUMENT_ROOT__SCREEN:
				return getScreen() != null;
			case KbdataPackage.DOCUMENT_ROOT__SEARCHTITLE:
				return getSearchtitle() != null;
			case KbdataPackage.DOCUMENT_ROOT__SECTION:
				return getSection() != null;
			case KbdataPackage.DOCUMENT_ROOT__SEP:
				return getSep() != null;
			case KbdataPackage.DOCUMENT_ROOT__SERIES:
				return getSeries() != null;
			case KbdataPackage.DOCUMENT_ROOT__SHAPE:
				return getShape() != null;
			case KbdataPackage.DOCUMENT_ROOT__SHORTCUT:
				return getShortcut() != null;
			case KbdataPackage.DOCUMENT_ROOT__SHORTDESC:
				return getShortdesc() != null;
			case KbdataPackage.DOCUMENT_ROOT__SIMPLETABLE:
				return getSimpletable() != null;
			case KbdataPackage.DOCUMENT_ROOT__SL:
				return getSl() != null;
			case KbdataPackage.DOCUMENT_ROOT__SLI:
				return getSli() != null;
			case KbdataPackage.DOCUMENT_ROOT__SOLUTION:
				return getSolution() != null;
			case KbdataPackage.DOCUMENT_ROOT__SOURCE:
				return getSource() != null;
			case KbdataPackage.DOCUMENT_ROOT__STATE:
				return getState() != null;
			case KbdataPackage.DOCUMENT_ROOT__STENTRY:
				return getStentry() != null;
			case KbdataPackage.DOCUMENT_ROOT__STHEAD:
				return getSthead() != null;
			case KbdataPackage.DOCUMENT_ROOT__STROW:
				return getStrow() != null;
			case KbdataPackage.DOCUMENT_ROOT__SUB:
				return getSub() != null;
			case KbdataPackage.DOCUMENT_ROOT__SUP:
				return getSup() != null;
			case KbdataPackage.DOCUMENT_ROOT__SYMPTOM:
				return getSymptom() != null;
			case KbdataPackage.DOCUMENT_ROOT__SYNBLK:
				return getSynblk() != null;
			case KbdataPackage.DOCUMENT_ROOT__SYNNOTE:
				return getSynnote() != null;
			case KbdataPackage.DOCUMENT_ROOT__SYNNOTEREF:
				return getSynnoteref() != null;
			case KbdataPackage.DOCUMENT_ROOT__SYNPH:
				return getSynph() != null;
			case KbdataPackage.DOCUMENT_ROOT__SYNTAXDIAGRAM:
				return getSyntaxdiagram() != null;
			case KbdataPackage.DOCUMENT_ROOT__SYSTEMOUTPUT:
				return getSystemoutput() != null;
			case KbdataPackage.DOCUMENT_ROOT__TABLE:
				return getTable() != null;
			case KbdataPackage.DOCUMENT_ROOT__TBODY:
				return getTbody() != null;
			case KbdataPackage.DOCUMENT_ROOT__TERM:
				return getTerm() != null;
			case KbdataPackage.DOCUMENT_ROOT__TGROUP:
				return getTgroup() != null;
			case KbdataPackage.DOCUMENT_ROOT__THEAD:
				return getThead() != null;
			case KbdataPackage.DOCUMENT_ROOT__TITLE:
				return getTitle() != null;
			case KbdataPackage.DOCUMENT_ROOT__TITLEALTS:
				return getTitlealts() != null;
			case KbdataPackage.DOCUMENT_ROOT__TM:
				return getTm() != null;
			case KbdataPackage.DOCUMENT_ROOT__TOPIC:
				return getTopic() != null;
			case KbdataPackage.DOCUMENT_ROOT__TT:
				return getTt() != null;
			case KbdataPackage.DOCUMENT_ROOT__U:
				return getU() != null;
			case KbdataPackage.DOCUMENT_ROOT__UICONTROL:
				return getUicontrol() != null;
			case KbdataPackage.DOCUMENT_ROOT__UL:
				return getUl() != null;
			case KbdataPackage.DOCUMENT_ROOT__UNKNOWN:
				return getUnknown() != null;
			case KbdataPackage.DOCUMENT_ROOT__USERINPUT:
				return getUserinput() != null;
			case KbdataPackage.DOCUMENT_ROOT__VAR:
				return getVar() != null;
			case KbdataPackage.DOCUMENT_ROOT__VARNAME:
				return getVarname() != null;
			case KbdataPackage.DOCUMENT_ROOT__VRM:
				return getVrm() != null;
			case KbdataPackage.DOCUMENT_ROOT__VRMLIST:
				return getVrmlist() != null;
			case KbdataPackage.DOCUMENT_ROOT__WINTITLE:
				return getWintitle() != null;
			case KbdataPackage.DOCUMENT_ROOT__XREF:
				return getXref() != null;
			case KbdataPackage.DOCUMENT_ROOT__DITA_ARCH_VERSION:
				return DITA_ARCH_VERSION_EDEFAULT == null ? dITAArchVersion != null : !DITA_ARCH_VERSION_EDEFAULT.equals(dITAArchVersion);
			case KbdataPackage.DOCUMENT_ROOT__LANG:
				return LANG_EDEFAULT == null ? lang != null : !LANG_EDEFAULT.equals(lang);
			case KbdataPackage.DOCUMENT_ROOT__SPACE:
				return isSetSpace();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(", dITAArchVersion: ");
		result.append(dITAArchVersion);
		result.append(", lang: ");
		result.append(lang);
		result.append(", space: ");
		if (spaceESet) result.append(space); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
