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

package com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl;

import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.*;

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
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getAccessArrayElementWithoutCheck <em>Access Array Element Without Check</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getAccessArrayElementWithoutCheck2 <em>Access Array Element Without Check2</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getActivestart <em>Activestart</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getActivestop <em>Activestop</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getArguments <em>Arguments</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getArraypassing <em>Arraypassing</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getArrayptrcleanup <em>Arrayptrcleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getAssertdebuginvariant <em>Assertdebuginvariant</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getBaddefines <em>Baddefines</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getBaseconstruct <em>Baseconstruct</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCallActiveObjectWithoutCheckingOrStopping <em>Call Active Object Without Checking Or Stopping</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCanpanic <em>Canpanic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCategories <em>Categories</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCclassIgnoreRE <em>Cclass Ignore RE</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getChangenotification <em>Changenotification</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCleanup <em>Cleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCodereview <em>Codereview</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCodescannerConfig <em>Codescanner Config</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCodingstandards <em>Codingstandards</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCommentcode <em>Commentcode</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getConnect <em>Connect</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getConnectAndDontCloseMemberVariable <em>Connect And Dont Close Member Variable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getConstnames <em>Constnames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getConsttdescptr <em>Consttdescptr</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getControlornull <em>Controlornull</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCtltargettype <em>Ctltargettype</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCustomrule <em>Customrule</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCustomrules <em>Customrules</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getDebugrom <em>Debugrom</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getDeclarename <em>Declarename</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getDeleteMemberVariable <em>Delete Member Variable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getDestructor <em>Destructor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getDoubleSemiColon <em>Double Semi Colon</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getDriveletters <em>Driveletters</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getEikbuttons <em>Eikbuttons</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getEikonenvstatic <em>Eikonenvstatic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getEnummembers <em>Enummembers</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getEnumnames <em>Enumnames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getExclude <em>Exclude</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getExportinline <em>Exportinline</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getExportpurevirtual <em>Exportpurevirtual</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getExternaldriveletters <em>Externaldriveletters</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getFiletype <em>Filetype</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getFoff <em>Foff</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getForbiddenwords <em>Forbiddenwords</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getForgottoputptroncleanupstack <em>Forgottoputptroncleanupstack</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getFriend <em>Friend</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getFunctionality <em>Functionality</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getGoto <em>Goto</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getHigh <em>High</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getIfassignments <em>Ifassignments</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getIfpreprocessor <em>Ifpreprocessor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getInheritanceorder <em>Inheritanceorder</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getInput <em>Input</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getIntleaves <em>Intleaves</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getJmp <em>Jmp</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getKeyword <em>Keyword</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLeave <em>Leave</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLeaveNoError <em>Leave No Error</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLeavingoperators <em>Leavingoperators</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLegal <em>Legal</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLFunctionCantLeave <em>LFunction Cant Leave</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLFunctionIgnoreRE <em>LFunction Ignore RE</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLink <em>Link</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLocalisation <em>Localisation</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLonglines <em>Longlines</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLow <em>Low</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLxr <em>Lxr</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getLxrversion <em>Lxrversion</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMagicnumbers <em>Magicnumbers</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMclassdestructor <em>Mclassdestructor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMedium <em>Medium</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMemberlc <em>Memberlc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMembervariablecallld <em>Membervariablecallld</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMissingcancel <em>Missingcancel</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMissingcclass <em>Missingcclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMmpsourcepath <em>Mmpsourcepath</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMultilangrsc <em>Multilangrsc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMultipledeclarations <em>Multipledeclarations</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMultipleinheritance <em>Multipleinheritance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getMydocs <em>Mydocs</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getNewlreferences <em>Newlreferences</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getNoleavetrap <em>Noleavetrap</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getNonconsthbufc <em>Nonconsthbufc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getNonconsttdesc <em>Nonconsttdesc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getNonleavenew <em>Nonleavenew</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getNonunicodeskins <em>Nonunicodeskins</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getNull <em>Null</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getOpen <em>Open</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getOpenIgnoreRE <em>Open Ignore RE</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getOther <em>Other</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getOutputformat <em>Outputformat</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getPanic <em>Panic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getPerformance <em>Performance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getPointertoarrays <em>Pointertoarrays</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getPragmadisable <em>Pragmadisable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getPragmamessage <em>Pragmamessage</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getPragmaother <em>Pragmaother</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getPrivateinheritance <em>Privateinheritance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getPushaddrvar <em>Pushaddrvar</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getPushmember <em>Pushmember</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getReadresource <em>Readresource</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getResourcenotoncleanupstack <em>Resourcenotoncleanupstack</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getResourcesonheap <em>Resourcesonheap</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getReturndescriptoroutofscope <em>Returndescriptoroutofscope</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getRfs <em>Rfs</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getRssnames <em>Rssnames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getScripts <em>Scripts</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getSeverities <em>Severities</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getSeverity <em>Severity</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getSources <em>Sources</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getStringliterals <em>Stringliterals</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getStringsinresourcefiles <em>Stringsinresourcefiles</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getStruct <em>Struct</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getTcclasses <em>Tcclasses</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getTclassdestructor <em>Tclassdestructor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getTimestampedoutput <em>Timestampedoutput</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getTodocomments <em>Todocomments</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getTrapcleanup <em>Trapcleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getTrapeleave <em>Trapeleave</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getTraprunl <em>Traprunl</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getTrspassing <em>Trspassing</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getUids <em>Uids</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getUncompressedaif <em>Uncompressedaif</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getUncompressedbmp <em>Uncompressedbmp</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getUnicodesource <em>Unicodesource</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getUserafter <em>Userafter</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getUserfree <em>Userfree</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getUserWaitForRequest <em>User Wait For Request</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getVariablenames <em>Variablenames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getVoidparameter <em>Voidparameter</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getWordsRE <em>Words RE</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getWorryingcomments <em>Worryingcomments</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getWorryRE <em>Worry RE</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.DocumentRootImpl#getSeverity1 <em>Severity1</em>}</li>
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
	 * The default value of the '{@link #getCclassIgnoreRE() <em>Cclass Ignore RE</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCclassIgnoreRE()
	 * @generated
	 * @ordered
	 */
	protected static final String CCLASS_IGNORE_RE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getExclude() <em>Exclude</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExclude()
	 * @generated
	 * @ordered
	 */
	protected static final String EXCLUDE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getFiletype() <em>Filetype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFiletype()
	 * @generated
	 * @ordered
	 */
	protected static final String FILETYPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getInput() <em>Input</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInput()
	 * @generated
	 * @ordered
	 */
	protected static final String INPUT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getLFunctionIgnoreRE() <em>LFunction Ignore RE</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLFunctionIgnoreRE()
	 * @generated
	 * @ordered
	 */
	protected static final String LFUNCTION_IGNORE_RE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getLink() <em>Link</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLink()
	 * @generated
	 * @ordered
	 */
	protected static final String LINK_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getLxr() <em>Lxr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLxr()
	 * @generated
	 * @ordered
	 */
	protected static final String LXR_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getLxrversion() <em>Lxrversion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLxrversion()
	 * @generated
	 * @ordered
	 */
	protected static final String LXRVERSION_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getOpenIgnoreRE() <em>Open Ignore RE</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpenIgnoreRE()
	 * @generated
	 * @ordered
	 */
	protected static final String OPEN_IGNORE_RE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getOutputformat() <em>Outputformat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputformat()
	 * @generated
	 * @ordered
	 */
	protected static final String OUTPUTFORMAT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected static final String SEVERITY_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getTimestampedoutput() <em>Timestampedoutput</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestampedoutput()
	 * @generated
	 * @ordered
	 */
	protected static final String TIMESTAMPEDOUTPUT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getWordsRE() <em>Words RE</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWordsRE()
	 * @generated
	 * @ordered
	 */
	protected static final String WORDS_RE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getWorryRE() <em>Worry RE</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorryRE()
	 * @generated
	 * @ordered
	 */
	protected static final String WORRY_RE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected static final CategoryType CATEGORY_EDEFAULT = CategoryType.CANPANIC;

	/**
	 * The cached value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected CategoryType category = CATEGORY_EDEFAULT;

	/**
	 * This is true if the Category attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean categoryESet;

	/**
	 * The default value of the '{@link #getSeverity1() <em>Severity1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity1()
	 * @generated
	 * @ordered
	 */
	protected static final SeverityType SEVERITY1_EDEFAULT = SeverityType.HIGH;

	/**
	 * The cached value of the '{@link #getSeverity1() <em>Severity1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity1()
	 * @generated
	 * @ordered
	 */
	protected SeverityType severity1 = SEVERITY1_EDEFAULT;

	/**
	 * This is true if the Severity1 attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean severity1ESet;

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
		return CSConfigPackage.eINSTANCE.getDocumentRoot();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, CSConfigPackage.DOCUMENT_ROOT__MIXED);
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
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, CSConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
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
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, CSConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AccessArrayElementWithoutCheckType getAccessArrayElementWithoutCheck() {
		return (AccessArrayElementWithoutCheckType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_AccessArrayElementWithoutCheck(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAccessArrayElementWithoutCheck(AccessArrayElementWithoutCheckType newAccessArrayElementWithoutCheck, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_AccessArrayElementWithoutCheck(), newAccessArrayElementWithoutCheck, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAccessArrayElementWithoutCheck(AccessArrayElementWithoutCheckType newAccessArrayElementWithoutCheck) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_AccessArrayElementWithoutCheck(), newAccessArrayElementWithoutCheck);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AccessArrayElementWithoutCheck2Type getAccessArrayElementWithoutCheck2() {
		return (AccessArrayElementWithoutCheck2Type)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_AccessArrayElementWithoutCheck2(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAccessArrayElementWithoutCheck2(AccessArrayElementWithoutCheck2Type newAccessArrayElementWithoutCheck2, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_AccessArrayElementWithoutCheck2(), newAccessArrayElementWithoutCheck2, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAccessArrayElementWithoutCheck2(AccessArrayElementWithoutCheck2Type newAccessArrayElementWithoutCheck2) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_AccessArrayElementWithoutCheck2(), newAccessArrayElementWithoutCheck2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivestartType getActivestart() {
		return (ActivestartType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Activestart(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActivestart(ActivestartType newActivestart, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Activestart(), newActivestart, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivestart(ActivestartType newActivestart) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Activestart(), newActivestart);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivestopType getActivestop() {
		return (ActivestopType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Activestop(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActivestop(ActivestopType newActivestop, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Activestop(), newActivestop, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivestop(ActivestopType newActivestop) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Activestop(), newActivestop);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArgumentsType getArguments() {
		return (ArgumentsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Arguments(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArguments(ArgumentsType newArguments, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Arguments(), newArguments, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArguments(ArgumentsType newArguments) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Arguments(), newArguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArraypassingType getArraypassing() {
		return (ArraypassingType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Arraypassing(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArraypassing(ArraypassingType newArraypassing, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Arraypassing(), newArraypassing, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArraypassing(ArraypassingType newArraypassing) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Arraypassing(), newArraypassing);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayptrcleanupType getArrayptrcleanup() {
		return (ArrayptrcleanupType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Arrayptrcleanup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArrayptrcleanup(ArrayptrcleanupType newArrayptrcleanup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Arrayptrcleanup(), newArrayptrcleanup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArrayptrcleanup(ArrayptrcleanupType newArrayptrcleanup) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Arrayptrcleanup(), newArrayptrcleanup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertdebuginvariantType getAssertdebuginvariant() {
		return (AssertdebuginvariantType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Assertdebuginvariant(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAssertdebuginvariant(AssertdebuginvariantType newAssertdebuginvariant, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Assertdebuginvariant(), newAssertdebuginvariant, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssertdebuginvariant(AssertdebuginvariantType newAssertdebuginvariant) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Assertdebuginvariant(), newAssertdebuginvariant);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaddefinesType getBaddefines() {
		return (BaddefinesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Baddefines(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBaddefines(BaddefinesType newBaddefines, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Baddefines(), newBaddefines, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaddefines(BaddefinesType newBaddefines) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Baddefines(), newBaddefines);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseconstructType getBaseconstruct() {
		return (BaseconstructType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Baseconstruct(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBaseconstruct(BaseconstructType newBaseconstruct, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Baseconstruct(), newBaseconstruct, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseconstruct(BaseconstructType newBaseconstruct) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Baseconstruct(), newBaseconstruct);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CallActiveObjectWithoutCheckingOrStoppingType getCallActiveObjectWithoutCheckingOrStopping() {
		return (CallActiveObjectWithoutCheckingOrStoppingType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_CallActiveObjectWithoutCheckingOrStopping(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCallActiveObjectWithoutCheckingOrStopping(CallActiveObjectWithoutCheckingOrStoppingType newCallActiveObjectWithoutCheckingOrStopping, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_CallActiveObjectWithoutCheckingOrStopping(), newCallActiveObjectWithoutCheckingOrStopping, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCallActiveObjectWithoutCheckingOrStopping(CallActiveObjectWithoutCheckingOrStoppingType newCallActiveObjectWithoutCheckingOrStopping) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_CallActiveObjectWithoutCheckingOrStopping(), newCallActiveObjectWithoutCheckingOrStopping);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CanpanicType getCanpanic() {
		return (CanpanicType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Canpanic(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCanpanic(CanpanicType newCanpanic, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Canpanic(), newCanpanic, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCanpanic(CanpanicType newCanpanic) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Canpanic(), newCanpanic);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CategoriesType getCategories() {
		return (CategoriesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Categories(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCategories(CategoriesType newCategories, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Categories(), newCategories, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategories(CategoriesType newCategories) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Categories(), newCategories);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCclassIgnoreRE() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_CclassIgnoreRE(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCclassIgnoreRE(String newCclassIgnoreRE) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_CclassIgnoreRE(), newCclassIgnoreRE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangenotificationType getChangenotification() {
		return (ChangenotificationType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Changenotification(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChangenotification(ChangenotificationType newChangenotification, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Changenotification(), newChangenotification, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChangenotification(ChangenotificationType newChangenotification) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Changenotification(), newChangenotification);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CleanupType getCleanup() {
		return (CleanupType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Cleanup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCleanup(CleanupType newCleanup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Cleanup(), newCleanup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCleanup(CleanupType newCleanup) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Cleanup(), newCleanup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodereviewType getCodereview() {
		return (CodereviewType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Codereview(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCodereview(CodereviewType newCodereview, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Codereview(), newCodereview, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCodereview(CodereviewType newCodereview) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Codereview(), newCodereview);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodescannerConfigType getCodescannerConfig() {
		return (CodescannerConfigType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_CodescannerConfig(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCodescannerConfig(CodescannerConfigType newCodescannerConfig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_CodescannerConfig(), newCodescannerConfig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCodescannerConfig(CodescannerConfigType newCodescannerConfig) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_CodescannerConfig(), newCodescannerConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodingstandardsType getCodingstandards() {
		return (CodingstandardsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Codingstandards(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCodingstandards(CodingstandardsType newCodingstandards, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Codingstandards(), newCodingstandards, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCodingstandards(CodingstandardsType newCodingstandards) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Codingstandards(), newCodingstandards);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommentcodeType getCommentcode() {
		return (CommentcodeType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Commentcode(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommentcode(CommentcodeType newCommentcode, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Commentcode(), newCommentcode, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommentcode(CommentcodeType newCommentcode) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Commentcode(), newCommentcode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectType getConnect() {
		return (ConnectType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Connect(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnect(ConnectType newConnect, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Connect(), newConnect, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnect(ConnectType newConnect) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Connect(), newConnect);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectAndDontCloseMemberVariableType getConnectAndDontCloseMemberVariable() {
		return (ConnectAndDontCloseMemberVariableType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_ConnectAndDontCloseMemberVariable(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnectAndDontCloseMemberVariable(ConnectAndDontCloseMemberVariableType newConnectAndDontCloseMemberVariable, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_ConnectAndDontCloseMemberVariable(), newConnectAndDontCloseMemberVariable, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectAndDontCloseMemberVariable(ConnectAndDontCloseMemberVariableType newConnectAndDontCloseMemberVariable) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_ConnectAndDontCloseMemberVariable(), newConnectAndDontCloseMemberVariable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstnamesType getConstnames() {
		return (ConstnamesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Constnames(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConstnames(ConstnamesType newConstnames, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Constnames(), newConstnames, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConstnames(ConstnamesType newConstnames) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Constnames(), newConstnames);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConsttdescptrType getConsttdescptr() {
		return (ConsttdescptrType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Consttdescptr(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConsttdescptr(ConsttdescptrType newConsttdescptr, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Consttdescptr(), newConsttdescptr, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConsttdescptr(ConsttdescptrType newConsttdescptr) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Consttdescptr(), newConsttdescptr);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ControlornullType getControlornull() {
		return (ControlornullType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Controlornull(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetControlornull(ControlornullType newControlornull, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Controlornull(), newControlornull, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setControlornull(ControlornullType newControlornull) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Controlornull(), newControlornull);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtltargettypeType getCtltargettype() {
		return (CtltargettypeType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Ctltargettype(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCtltargettype(CtltargettypeType newCtltargettype, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Ctltargettype(), newCtltargettype, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCtltargettype(CtltargettypeType newCtltargettype) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Ctltargettype(), newCtltargettype);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CustomruleType getCustomrule() {
		return (CustomruleType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Customrule(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCustomrule(CustomruleType newCustomrule, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Customrule(), newCustomrule, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCustomrule(CustomruleType newCustomrule) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Customrule(), newCustomrule);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CustomrulesType getCustomrules() {
		return (CustomrulesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Customrules(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCustomrules(CustomrulesType newCustomrules, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Customrules(), newCustomrules, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCustomrules(CustomrulesType newCustomrules) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Customrules(), newCustomrules);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DebugromType getDebugrom() {
		return (DebugromType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Debugrom(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDebugrom(DebugromType newDebugrom, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Debugrom(), newDebugrom, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDebugrom(DebugromType newDebugrom) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Debugrom(), newDebugrom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeclarenameType getDeclarename() {
		return (DeclarenameType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Declarename(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclarename(DeclarenameType newDeclarename, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Declarename(), newDeclarename, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclarename(DeclarenameType newDeclarename) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Declarename(), newDeclarename);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeleteMemberVariableType getDeleteMemberVariable() {
		return (DeleteMemberVariableType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_DeleteMemberVariable(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeleteMemberVariable(DeleteMemberVariableType newDeleteMemberVariable, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_DeleteMemberVariable(), newDeleteMemberVariable, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeleteMemberVariable(DeleteMemberVariableType newDeleteMemberVariable) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_DeleteMemberVariable(), newDeleteMemberVariable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Description(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Description(), newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestructorType getDestructor() {
		return (DestructorType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Destructor(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDestructor(DestructorType newDestructor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Destructor(), newDestructor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestructor(DestructorType newDestructor) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Destructor(), newDestructor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentationType getDocumentation() {
		return (DocumentationType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Documentation(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentation(DocumentationType newDocumentation, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Documentation(), newDocumentation, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentation(DocumentationType newDocumentation) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Documentation(), newDocumentation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DoubleSemiColonType getDoubleSemiColon() {
		return (DoubleSemiColonType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_DoubleSemiColon(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDoubleSemiColon(DoubleSemiColonType newDoubleSemiColon, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_DoubleSemiColon(), newDoubleSemiColon, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDoubleSemiColon(DoubleSemiColonType newDoubleSemiColon) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_DoubleSemiColon(), newDoubleSemiColon);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DrivelettersType getDriveletters() {
		return (DrivelettersType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Driveletters(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDriveletters(DrivelettersType newDriveletters, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Driveletters(), newDriveletters, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDriveletters(DrivelettersType newDriveletters) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Driveletters(), newDriveletters);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EikbuttonsType getEikbuttons() {
		return (EikbuttonsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Eikbuttons(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEikbuttons(EikbuttonsType newEikbuttons, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Eikbuttons(), newEikbuttons, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEikbuttons(EikbuttonsType newEikbuttons) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Eikbuttons(), newEikbuttons);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EikonenvstaticType getEikonenvstatic() {
		return (EikonenvstaticType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Eikonenvstatic(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEikonenvstatic(EikonenvstaticType newEikonenvstatic, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Eikonenvstatic(), newEikonenvstatic, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEikonenvstatic(EikonenvstaticType newEikonenvstatic) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Eikonenvstatic(), newEikonenvstatic);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnummembersType getEnummembers() {
		return (EnummembersType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Enummembers(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnummembers(EnummembersType newEnummembers, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Enummembers(), newEnummembers, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnummembers(EnummembersType newEnummembers) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Enummembers(), newEnummembers);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumnamesType getEnumnames() {
		return (EnumnamesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Enumnames(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnumnames(EnumnamesType newEnumnames, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Enumnames(), newEnumnames, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnumnames(EnumnamesType newEnumnames) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Enumnames(), newEnumnames);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExclude() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Exclude(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExclude(String newExclude) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Exclude(), newExclude);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExportinlineType getExportinline() {
		return (ExportinlineType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Exportinline(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExportinline(ExportinlineType newExportinline, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Exportinline(), newExportinline, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExportinline(ExportinlineType newExportinline) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Exportinline(), newExportinline);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExportpurevirtualType getExportpurevirtual() {
		return (ExportpurevirtualType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Exportpurevirtual(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExportpurevirtual(ExportpurevirtualType newExportpurevirtual, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Exportpurevirtual(), newExportpurevirtual, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExportpurevirtual(ExportpurevirtualType newExportpurevirtual) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Exportpurevirtual(), newExportpurevirtual);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExternaldrivelettersType getExternaldriveletters() {
		return (ExternaldrivelettersType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Externaldriveletters(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExternaldriveletters(ExternaldrivelettersType newExternaldriveletters, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Externaldriveletters(), newExternaldriveletters, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExternaldriveletters(ExternaldrivelettersType newExternaldriveletters) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Externaldriveletters(), newExternaldriveletters);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFiletype() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Filetype(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFiletype(String newFiletype) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Filetype(), newFiletype);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FoffType getFoff() {
		return (FoffType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Foff(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFoff(FoffType newFoff, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Foff(), newFoff, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFoff(FoffType newFoff) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Foff(), newFoff);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForbiddenwordsType getForbiddenwords() {
		return (ForbiddenwordsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Forbiddenwords(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetForbiddenwords(ForbiddenwordsType newForbiddenwords, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Forbiddenwords(), newForbiddenwords, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForbiddenwords(ForbiddenwordsType newForbiddenwords) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Forbiddenwords(), newForbiddenwords);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForgottoputptroncleanupstackType getForgottoputptroncleanupstack() {
		return (ForgottoputptroncleanupstackType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Forgottoputptroncleanupstack(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetForgottoputptroncleanupstack(ForgottoputptroncleanupstackType newForgottoputptroncleanupstack, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Forgottoputptroncleanupstack(), newForgottoputptroncleanupstack, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForgottoputptroncleanupstack(ForgottoputptroncleanupstackType newForgottoputptroncleanupstack) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Forgottoputptroncleanupstack(), newForgottoputptroncleanupstack);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FriendType getFriend() {
		return (FriendType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Friend(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFriend(FriendType newFriend, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Friend(), newFriend, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFriend(FriendType newFriend) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Friend(), newFriend);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionalityType getFunctionality() {
		return (FunctionalityType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Functionality(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunctionality(FunctionalityType newFunctionality, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Functionality(), newFunctionality, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionality(FunctionalityType newFunctionality) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Functionality(), newFunctionality);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GotoType getGoto() {
		return (GotoType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Goto(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGoto(GotoType newGoto, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Goto(), newGoto, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGoto(GotoType newGoto) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Goto(), newGoto);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HighType getHigh() {
		return (HighType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_High(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHigh(HighType newHigh, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_High(), newHigh, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHigh(HighType newHigh) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_High(), newHigh);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IfassignmentsType getIfassignments() {
		return (IfassignmentsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Ifassignments(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIfassignments(IfassignmentsType newIfassignments, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Ifassignments(), newIfassignments, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIfassignments(IfassignmentsType newIfassignments) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Ifassignments(), newIfassignments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IfpreprocessorType getIfpreprocessor() {
		return (IfpreprocessorType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Ifpreprocessor(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIfpreprocessor(IfpreprocessorType newIfpreprocessor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Ifpreprocessor(), newIfpreprocessor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIfpreprocessor(IfpreprocessorType newIfpreprocessor) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Ifpreprocessor(), newIfpreprocessor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InheritanceorderType getInheritanceorder() {
		return (InheritanceorderType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Inheritanceorder(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInheritanceorder(InheritanceorderType newInheritanceorder, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Inheritanceorder(), newInheritanceorder, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInheritanceorder(InheritanceorderType newInheritanceorder) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Inheritanceorder(), newInheritanceorder);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInput() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Input(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInput(String newInput) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Input(), newInput);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntleavesType getIntleaves() {
		return (IntleavesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Intleaves(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIntleaves(IntleavesType newIntleaves, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Intleaves(), newIntleaves, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntleaves(IntleavesType newIntleaves) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Intleaves(), newIntleaves);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JmpType getJmp() {
		return (JmpType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Jmp(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJmp(JmpType newJmp, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Jmp(), newJmp, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJmp(JmpType newJmp) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Jmp(), newJmp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KeywordType getKeyword() {
		return (KeywordType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Keyword(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKeyword(KeywordType newKeyword, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Keyword(), newKeyword, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeyword(KeywordType newKeyword) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Keyword(), newKeyword);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LeaveType getLeave() {
		return (LeaveType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Leave(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLeave(LeaveType newLeave, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Leave(), newLeave, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeave(LeaveType newLeave) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Leave(), newLeave);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LeaveNoErrorType getLeaveNoError() {
		return (LeaveNoErrorType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_LeaveNoError(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLeaveNoError(LeaveNoErrorType newLeaveNoError, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_LeaveNoError(), newLeaveNoError, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeaveNoError(LeaveNoErrorType newLeaveNoError) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_LeaveNoError(), newLeaveNoError);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LeavingoperatorsType getLeavingoperators() {
		return (LeavingoperatorsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Leavingoperators(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLeavingoperators(LeavingoperatorsType newLeavingoperators, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Leavingoperators(), newLeavingoperators, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeavingoperators(LeavingoperatorsType newLeavingoperators) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Leavingoperators(), newLeavingoperators);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LegalType getLegal() {
		return (LegalType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Legal(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLegal(LegalType newLegal, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Legal(), newLegal, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLegal(LegalType newLegal) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Legal(), newLegal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LFunctionCantLeaveType getLFunctionCantLeave() {
		return (LFunctionCantLeaveType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_LFunctionCantLeave(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLFunctionCantLeave(LFunctionCantLeaveType newLFunctionCantLeave, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_LFunctionCantLeave(), newLFunctionCantLeave, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLFunctionCantLeave(LFunctionCantLeaveType newLFunctionCantLeave) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_LFunctionCantLeave(), newLFunctionCantLeave);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLFunctionIgnoreRE() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_LFunctionIgnoreRE(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLFunctionIgnoreRE(String newLFunctionIgnoreRE) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_LFunctionIgnoreRE(), newLFunctionIgnoreRE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLink() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Link(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLink(String newLink) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Link(), newLink);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalisationType getLocalisation() {
		return (LocalisationType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Localisation(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocalisation(LocalisationType newLocalisation, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Localisation(), newLocalisation, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalisation(LocalisationType newLocalisation) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Localisation(), newLocalisation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LonglinesType getLonglines() {
		return (LonglinesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Longlines(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLonglines(LonglinesType newLonglines, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Longlines(), newLonglines, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLonglines(LonglinesType newLonglines) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Longlines(), newLonglines);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LowType getLow() {
		return (LowType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Low(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLow(LowType newLow, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Low(), newLow, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLow(LowType newLow) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Low(), newLow);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLxr() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Lxr(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLxr(String newLxr) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Lxr(), newLxr);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLxrversion() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Lxrversion(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLxrversion(String newLxrversion) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Lxrversion(), newLxrversion);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MagicnumbersType getMagicnumbers() {
		return (MagicnumbersType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Magicnumbers(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMagicnumbers(MagicnumbersType newMagicnumbers, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Magicnumbers(), newMagicnumbers, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMagicnumbers(MagicnumbersType newMagicnumbers) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Magicnumbers(), newMagicnumbers);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MclassdestructorType getMclassdestructor() {
		return (MclassdestructorType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Mclassdestructor(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMclassdestructor(MclassdestructorType newMclassdestructor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Mclassdestructor(), newMclassdestructor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMclassdestructor(MclassdestructorType newMclassdestructor) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Mclassdestructor(), newMclassdestructor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MediumType getMedium() {
		return (MediumType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Medium(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMedium(MediumType newMedium, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Medium(), newMedium, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMedium(MediumType newMedium) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Medium(), newMedium);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberlcType getMemberlc() {
		return (MemberlcType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Memberlc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMemberlc(MemberlcType newMemberlc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Memberlc(), newMemberlc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemberlc(MemberlcType newMemberlc) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Memberlc(), newMemberlc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MembervariablecallldType getMembervariablecallld() {
		return (MembervariablecallldType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Membervariablecallld(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMembervariablecallld(MembervariablecallldType newMembervariablecallld, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Membervariablecallld(), newMembervariablecallld, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMembervariablecallld(MembervariablecallldType newMembervariablecallld) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Membervariablecallld(), newMembervariablecallld);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MissingcancelType getMissingcancel() {
		return (MissingcancelType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Missingcancel(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMissingcancel(MissingcancelType newMissingcancel, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Missingcancel(), newMissingcancel, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingcancel(MissingcancelType newMissingcancel) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Missingcancel(), newMissingcancel);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MissingcclassType getMissingcclass() {
		return (MissingcclassType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Missingcclass(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMissingcclass(MissingcclassType newMissingcclass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Missingcclass(), newMissingcclass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingcclass(MissingcclassType newMissingcclass) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Missingcclass(), newMissingcclass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmpsourcepathType getMmpsourcepath() {
		return (MmpsourcepathType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Mmpsourcepath(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmpsourcepath(MmpsourcepathType newMmpsourcepath, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Mmpsourcepath(), newMmpsourcepath, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmpsourcepath(MmpsourcepathType newMmpsourcepath) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Mmpsourcepath(), newMmpsourcepath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultilangrscType getMultilangrsc() {
		return (MultilangrscType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Multilangrsc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMultilangrsc(MultilangrscType newMultilangrsc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Multilangrsc(), newMultilangrsc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultilangrsc(MultilangrscType newMultilangrsc) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Multilangrsc(), newMultilangrsc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultipledeclarationsType getMultipledeclarations() {
		return (MultipledeclarationsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Multipledeclarations(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMultipledeclarations(MultipledeclarationsType newMultipledeclarations, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Multipledeclarations(), newMultipledeclarations, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultipledeclarations(MultipledeclarationsType newMultipledeclarations) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Multipledeclarations(), newMultipledeclarations);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultipleinheritanceType getMultipleinheritance() {
		return (MultipleinheritanceType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Multipleinheritance(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMultipleinheritance(MultipleinheritanceType newMultipleinheritance, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Multipleinheritance(), newMultipleinheritance, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultipleinheritance(MultipleinheritanceType newMultipleinheritance) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Multipleinheritance(), newMultipleinheritance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MydocsType getMydocs() {
		return (MydocsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Mydocs(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMydocs(MydocsType newMydocs, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Mydocs(), newMydocs, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMydocs(MydocsType newMydocs) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Mydocs(), newMydocs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Name(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Name(), newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamespaceType getNamespace() {
		return (NamespaceType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Namespace(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNamespace(NamespaceType newNamespace, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Namespace(), newNamespace, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamespace(NamespaceType newNamespace) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Namespace(), newNamespace);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NewlreferencesType getNewlreferences() {
		return (NewlreferencesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Newlreferences(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNewlreferences(NewlreferencesType newNewlreferences, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Newlreferences(), newNewlreferences, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewlreferences(NewlreferencesType newNewlreferences) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Newlreferences(), newNewlreferences);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NoleavetrapType getNoleavetrap() {
		return (NoleavetrapType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Noleavetrap(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNoleavetrap(NoleavetrapType newNoleavetrap, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Noleavetrap(), newNoleavetrap, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNoleavetrap(NoleavetrapType newNoleavetrap) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Noleavetrap(), newNoleavetrap);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonconsthbufcType getNonconsthbufc() {
		return (NonconsthbufcType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonconsthbufc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNonconsthbufc(NonconsthbufcType newNonconsthbufc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonconsthbufc(), newNonconsthbufc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNonconsthbufc(NonconsthbufcType newNonconsthbufc) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonconsthbufc(), newNonconsthbufc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonconsttdescType getNonconsttdesc() {
		return (NonconsttdescType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonconsttdesc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNonconsttdesc(NonconsttdescType newNonconsttdesc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonconsttdesc(), newNonconsttdesc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNonconsttdesc(NonconsttdescType newNonconsttdesc) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonconsttdesc(), newNonconsttdesc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonleavenewType getNonleavenew() {
		return (NonleavenewType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonleavenew(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNonleavenew(NonleavenewType newNonleavenew, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonleavenew(), newNonleavenew, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNonleavenew(NonleavenewType newNonleavenew) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonleavenew(), newNonleavenew);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonunicodeskinsType getNonunicodeskins() {
		return (NonunicodeskinsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonunicodeskins(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNonunicodeskins(NonunicodeskinsType newNonunicodeskins, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonunicodeskins(), newNonunicodeskins, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNonunicodeskins(NonunicodeskinsType newNonunicodeskins) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Nonunicodeskins(), newNonunicodeskins);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NullType getNull() {
		return (NullType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Null(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNull(NullType newNull, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Null(), newNull, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNull(NullType newNull) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Null(), newNull);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OpenType getOpen() {
		return (OpenType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Open(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOpen(OpenType newOpen, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Open(), newOpen, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOpen(OpenType newOpen) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Open(), newOpen);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOpenIgnoreRE() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_OpenIgnoreRE(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOpenIgnoreRE(String newOpenIgnoreRE) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_OpenIgnoreRE(), newOpenIgnoreRE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OtherType getOther() {
		return (OtherType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Other(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOther(OtherType newOther, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Other(), newOther, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOther(OtherType newOther) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Other(), newOther);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOutputformat() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Outputformat(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputformat(String newOutputformat) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Outputformat(), newOutputformat);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PanicType getPanic() {
		return (PanicType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Panic(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPanic(PanicType newPanic, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Panic(), newPanic, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPanic(PanicType newPanic) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Panic(), newPanic);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformanceType getPerformance() {
		return (PerformanceType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Performance(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPerformance(PerformanceType newPerformance, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Performance(), newPerformance, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPerformance(PerformanceType newPerformance) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Performance(), newPerformance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PointertoarraysType getPointertoarrays() {
		return (PointertoarraysType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Pointertoarrays(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPointertoarrays(PointertoarraysType newPointertoarrays, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Pointertoarrays(), newPointertoarrays, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointertoarrays(PointertoarraysType newPointertoarrays) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Pointertoarrays(), newPointertoarrays);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragmadisableType getPragmadisable() {
		return (PragmadisableType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Pragmadisable(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPragmadisable(PragmadisableType newPragmadisable, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Pragmadisable(), newPragmadisable, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPragmadisable(PragmadisableType newPragmadisable) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Pragmadisable(), newPragmadisable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragmamessageType getPragmamessage() {
		return (PragmamessageType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Pragmamessage(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPragmamessage(PragmamessageType newPragmamessage, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Pragmamessage(), newPragmamessage, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPragmamessage(PragmamessageType newPragmamessage) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Pragmamessage(), newPragmamessage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragmaotherType getPragmaother() {
		return (PragmaotherType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Pragmaother(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPragmaother(PragmaotherType newPragmaother, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Pragmaother(), newPragmaother, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPragmaother(PragmaotherType newPragmaother) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Pragmaother(), newPragmaother);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrivateinheritanceType getPrivateinheritance() {
		return (PrivateinheritanceType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Privateinheritance(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrivateinheritance(PrivateinheritanceType newPrivateinheritance, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Privateinheritance(), newPrivateinheritance, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrivateinheritance(PrivateinheritanceType newPrivateinheritance) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Privateinheritance(), newPrivateinheritance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PushaddrvarType getPushaddrvar() {
		return (PushaddrvarType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Pushaddrvar(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPushaddrvar(PushaddrvarType newPushaddrvar, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Pushaddrvar(), newPushaddrvar, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPushaddrvar(PushaddrvarType newPushaddrvar) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Pushaddrvar(), newPushaddrvar);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PushmemberType getPushmember() {
		return (PushmemberType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Pushmember(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPushmember(PushmemberType newPushmember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Pushmember(), newPushmember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPushmember(PushmemberType newPushmember) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Pushmember(), newPushmember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReadresourceType getReadresource() {
		return (ReadresourceType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Readresource(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReadresource(ReadresourceType newReadresource, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Readresource(), newReadresource, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadresource(ReadresourceType newReadresource) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Readresource(), newReadresource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourcenotoncleanupstackType getResourcenotoncleanupstack() {
		return (ResourcenotoncleanupstackType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Resourcenotoncleanupstack(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResourcenotoncleanupstack(ResourcenotoncleanupstackType newResourcenotoncleanupstack, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Resourcenotoncleanupstack(), newResourcenotoncleanupstack, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourcenotoncleanupstack(ResourcenotoncleanupstackType newResourcenotoncleanupstack) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Resourcenotoncleanupstack(), newResourcenotoncleanupstack);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourcesonheapType getResourcesonheap() {
		return (ResourcesonheapType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Resourcesonheap(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResourcesonheap(ResourcesonheapType newResourcesonheap, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Resourcesonheap(), newResourcesonheap, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourcesonheap(ResourcesonheapType newResourcesonheap) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Resourcesonheap(), newResourcesonheap);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReturndescriptoroutofscopeType getReturndescriptoroutofscope() {
		return (ReturndescriptoroutofscopeType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Returndescriptoroutofscope(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReturndescriptoroutofscope(ReturndescriptoroutofscopeType newReturndescriptoroutofscope, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Returndescriptoroutofscope(), newReturndescriptoroutofscope, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturndescriptoroutofscope(ReturndescriptoroutofscopeType newReturndescriptoroutofscope) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Returndescriptoroutofscope(), newReturndescriptoroutofscope);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RfsType getRfs() {
		return (RfsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Rfs(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRfs(RfsType newRfs, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Rfs(), newRfs, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRfs(RfsType newRfs) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Rfs(), newRfs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RssnamesType getRssnames() {
		return (RssnamesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Rssnames(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRssnames(RssnamesType newRssnames, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Rssnames(), newRssnames, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRssnames(RssnamesType newRssnames) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Rssnames(), newRssnames);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScriptsType getScripts() {
		return (ScriptsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Scripts(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScripts(ScriptsType newScripts, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Scripts(), newScripts, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScripts(ScriptsType newScripts) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Scripts(), newScripts);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeveritiesType getSeverities() {
		return (SeveritiesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Severities(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSeverities(SeveritiesType newSeverities, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Severities(), newSeverities, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeverities(SeveritiesType newSeverities) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Severities(), newSeverities);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourcesType getSources() {
		return (SourcesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Sources(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSources(SourcesType newSources, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Sources(), newSources, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSources(SourcesType newSources) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Sources(), newSources);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringliteralsType getStringliterals() {
		return (StringliteralsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Stringliterals(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStringliterals(StringliteralsType newStringliterals, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Stringliterals(), newStringliterals, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringliterals(StringliteralsType newStringliterals) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Stringliterals(), newStringliterals);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringsinresourcefilesType getStringsinresourcefiles() {
		return (StringsinresourcefilesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Stringsinresourcefiles(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStringsinresourcefiles(StringsinresourcefilesType newStringsinresourcefiles, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Stringsinresourcefiles(), newStringsinresourcefiles, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringsinresourcefiles(StringsinresourcefilesType newStringsinresourcefiles) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Stringsinresourcefiles(), newStringsinresourcefiles);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructType getStruct() {
		return (StructType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Struct(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStruct(StructType newStruct, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Struct(), newStruct, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStruct(StructType newStruct) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Struct(), newStruct);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TcclassesType getTcclasses() {
		return (TcclassesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Tcclasses(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTcclasses(TcclassesType newTcclasses, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Tcclasses(), newTcclasses, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTcclasses(TcclassesType newTcclasses) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Tcclasses(), newTcclasses);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TclassdestructorType getTclassdestructor() {
		return (TclassdestructorType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Tclassdestructor(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTclassdestructor(TclassdestructorType newTclassdestructor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Tclassdestructor(), newTclassdestructor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTclassdestructor(TclassdestructorType newTclassdestructor) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Tclassdestructor(), newTclassdestructor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTimestampedoutput() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Timestampedoutput(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimestampedoutput(String newTimestampedoutput) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Timestampedoutput(), newTimestampedoutput);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitle() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Title(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(String newTitle) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Title(), newTitle);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TodocommentsType getTodocomments() {
		return (TodocommentsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Todocomments(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTodocomments(TodocommentsType newTodocomments, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Todocomments(), newTodocomments, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTodocomments(TodocommentsType newTodocomments) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Todocomments(), newTodocomments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TrapcleanupType getTrapcleanup() {
		return (TrapcleanupType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Trapcleanup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrapcleanup(TrapcleanupType newTrapcleanup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Trapcleanup(), newTrapcleanup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrapcleanup(TrapcleanupType newTrapcleanup) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Trapcleanup(), newTrapcleanup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TrapeleaveType getTrapeleave() {
		return (TrapeleaveType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Trapeleave(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrapeleave(TrapeleaveType newTrapeleave, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Trapeleave(), newTrapeleave, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrapeleave(TrapeleaveType newTrapeleave) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Trapeleave(), newTrapeleave);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TraprunlType getTraprunl() {
		return (TraprunlType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Traprunl(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTraprunl(TraprunlType newTraprunl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Traprunl(), newTraprunl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTraprunl(TraprunlType newTraprunl) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Traprunl(), newTraprunl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TrspassingType getTrspassing() {
		return (TrspassingType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Trspassing(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrspassing(TrspassingType newTrspassing, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Trspassing(), newTrspassing, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrspassing(TrspassingType newTrspassing) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Trspassing(), newTrspassing);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UidsType getUids() {
		return (UidsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Uids(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUids(UidsType newUids, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Uids(), newUids, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUids(UidsType newUids) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Uids(), newUids);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncompressedaifType getUncompressedaif() {
		return (UncompressedaifType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Uncompressedaif(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUncompressedaif(UncompressedaifType newUncompressedaif, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Uncompressedaif(), newUncompressedaif, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUncompressedaif(UncompressedaifType newUncompressedaif) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Uncompressedaif(), newUncompressedaif);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncompressedbmpType getUncompressedbmp() {
		return (UncompressedbmpType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Uncompressedbmp(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUncompressedbmp(UncompressedbmpType newUncompressedbmp, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Uncompressedbmp(), newUncompressedbmp, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUncompressedbmp(UncompressedbmpType newUncompressedbmp) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Uncompressedbmp(), newUncompressedbmp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnicodesourceType getUnicodesource() {
		return (UnicodesourceType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Unicodesource(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUnicodesource(UnicodesourceType newUnicodesource, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Unicodesource(), newUnicodesource, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnicodesource(UnicodesourceType newUnicodesource) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Unicodesource(), newUnicodesource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserafterType getUserafter() {
		return (UserafterType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Userafter(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserafter(UserafterType newUserafter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Userafter(), newUserafter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserafter(UserafterType newUserafter) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Userafter(), newUserafter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserfreeType getUserfree() {
		return (UserfreeType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Userfree(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserfree(UserfreeType newUserfree, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Userfree(), newUserfree, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserfree(UserfreeType newUserfree) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Userfree(), newUserfree);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserWaitForRequestType getUserWaitForRequest() {
		return (UserWaitForRequestType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_UserWaitForRequest(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserWaitForRequest(UserWaitForRequestType newUserWaitForRequest, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_UserWaitForRequest(), newUserWaitForRequest, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserWaitForRequest(UserWaitForRequestType newUserWaitForRequest) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_UserWaitForRequest(), newUserWaitForRequest);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariablenamesType getVariablenames() {
		return (VariablenamesType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Variablenames(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVariablenames(VariablenamesType newVariablenames, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Variablenames(), newVariablenames, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariablenames(VariablenamesType newVariablenames) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Variablenames(), newVariablenames);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VoidparameterType getVoidparameter() {
		return (VoidparameterType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Voidparameter(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVoidparameter(VoidparameterType newVoidparameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Voidparameter(), newVoidparameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVoidparameter(VoidparameterType newVoidparameter) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Voidparameter(), newVoidparameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWordsRE() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_WordsRE(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWordsRE(String newWordsRE) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_WordsRE(), newWordsRE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WorryingcommentsType getWorryingcomments() {
		return (WorryingcommentsType)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Worryingcomments(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWorryingcomments(WorryingcommentsType newWorryingcomments, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getDocumentRoot_Worryingcomments(), newWorryingcomments, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorryingcomments(WorryingcommentsType newWorryingcomments) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Worryingcomments(), newWorryingcomments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWorryRE() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_WorryRE(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorryRE(String newWorryRE) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_WorryRE(), newWorryRE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CategoryType getCategory() {
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategory(CategoryType newCategory) {
		CategoryType oldCategory = category;
		category = newCategory == null ? CATEGORY_EDEFAULT : newCategory;
		boolean oldCategoryESet = categoryESet;
		categoryESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.DOCUMENT_ROOT__CATEGORY, oldCategory, category, !oldCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCategory() {
		CategoryType oldCategory = category;
		boolean oldCategoryESet = categoryESet;
		category = CATEGORY_EDEFAULT;
		categoryESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CSConfigPackage.DOCUMENT_ROOT__CATEGORY, oldCategory, CATEGORY_EDEFAULT, oldCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCategory() {
		return categoryESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeverityType getSeverity1() {
		return severity1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeverity1(SeverityType newSeverity1) {
		SeverityType oldSeverity1 = severity1;
		severity1 = newSeverity1 == null ? SEVERITY1_EDEFAULT : newSeverity1;
		boolean oldSeverity1ESet = severity1ESet;
		severity1ESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.DOCUMENT_ROOT__SEVERITY1, oldSeverity1, severity1, !oldSeverity1ESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSeverity1() {
		SeverityType oldSeverity1 = severity1;
		boolean oldSeverity1ESet = severity1ESet;
		severity1 = SEVERITY1_EDEFAULT;
		severity1ESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CSConfigPackage.DOCUMENT_ROOT__SEVERITY1, oldSeverity1, SEVERITY1_EDEFAULT, oldSeverity1ESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSeverity1() {
		return severity1ESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSeverity() {
		return (String)getMixed().get(CSConfigPackage.eINSTANCE.getDocumentRoot_Severity(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeverity(String newSeverity) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getDocumentRoot_Severity(), newSeverity);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CSConfigPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK:
				return basicSetAccessArrayElementWithoutCheck(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2:
				return basicSetAccessArrayElementWithoutCheck2(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__ACTIVESTART:
				return basicSetActivestart(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__ACTIVESTOP:
				return basicSetActivestop(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__ARGUMENTS:
				return basicSetArguments(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__ARRAYPASSING:
				return basicSetArraypassing(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__ARRAYPTRCLEANUP:
				return basicSetArrayptrcleanup(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__ASSERTDEBUGINVARIANT:
				return basicSetAssertdebuginvariant(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__BADDEFINES:
				return basicSetBaddefines(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__BASECONSTRUCT:
				return basicSetBaseconstruct(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING:
				return basicSetCallActiveObjectWithoutCheckingOrStopping(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CANPANIC:
				return basicSetCanpanic(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CATEGORIES:
				return basicSetCategories(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CHANGENOTIFICATION:
				return basicSetChangenotification(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CLEANUP:
				return basicSetCleanup(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CODEREVIEW:
				return basicSetCodereview(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CODESCANNER_CONFIG:
				return basicSetCodescannerConfig(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CODINGSTANDARDS:
				return basicSetCodingstandards(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__COMMENTCODE:
				return basicSetCommentcode(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CONNECT:
				return basicSetConnect(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE:
				return basicSetConnectAndDontCloseMemberVariable(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CONSTNAMES:
				return basicSetConstnames(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CONSTTDESCPTR:
				return basicSetConsttdescptr(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CONTROLORNULL:
				return basicSetControlornull(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CTLTARGETTYPE:
				return basicSetCtltargettype(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CUSTOMRULE:
				return basicSetCustomrule(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__CUSTOMRULES:
				return basicSetCustomrules(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__DEBUGROM:
				return basicSetDebugrom(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__DECLARENAME:
				return basicSetDeclarename(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__DELETE_MEMBER_VARIABLE:
				return basicSetDeleteMemberVariable(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__DESTRUCTOR:
				return basicSetDestructor(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__DOUBLE_SEMI_COLON:
				return basicSetDoubleSemiColon(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__DRIVELETTERS:
				return basicSetDriveletters(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__EIKBUTTONS:
				return basicSetEikbuttons(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__EIKONENVSTATIC:
				return basicSetEikonenvstatic(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__ENUMMEMBERS:
				return basicSetEnummembers(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__ENUMNAMES:
				return basicSetEnumnames(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__EXPORTINLINE:
				return basicSetExportinline(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__EXPORTPUREVIRTUAL:
				return basicSetExportpurevirtual(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__EXTERNALDRIVELETTERS:
				return basicSetExternaldriveletters(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__FOFF:
				return basicSetFoff(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__FORBIDDENWORDS:
				return basicSetForbiddenwords(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__FORGOTTOPUTPTRONCLEANUPSTACK:
				return basicSetForgottoputptroncleanupstack(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__FRIEND:
				return basicSetFriend(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__FUNCTIONALITY:
				return basicSetFunctionality(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__GOTO:
				return basicSetGoto(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__HIGH:
				return basicSetHigh(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__IFASSIGNMENTS:
				return basicSetIfassignments(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__IFPREPROCESSOR:
				return basicSetIfpreprocessor(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__INHERITANCEORDER:
				return basicSetInheritanceorder(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__INTLEAVES:
				return basicSetIntleaves(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__JMP:
				return basicSetJmp(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__KEYWORD:
				return basicSetKeyword(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__LEAVE:
				return basicSetLeave(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__LEAVE_NO_ERROR:
				return basicSetLeaveNoError(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__LEAVINGOPERATORS:
				return basicSetLeavingoperators(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__LEGAL:
				return basicSetLegal(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__LFUNCTION_CANT_LEAVE:
				return basicSetLFunctionCantLeave(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__LOCALISATION:
				return basicSetLocalisation(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__LONGLINES:
				return basicSetLonglines(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__LOW:
				return basicSetLow(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MAGICNUMBERS:
				return basicSetMagicnumbers(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MCLASSDESTRUCTOR:
				return basicSetMclassdestructor(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MEDIUM:
				return basicSetMedium(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MEMBERLC:
				return basicSetMemberlc(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MEMBERVARIABLECALLLD:
				return basicSetMembervariablecallld(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MISSINGCANCEL:
				return basicSetMissingcancel(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MISSINGCCLASS:
				return basicSetMissingcclass(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MMPSOURCEPATH:
				return basicSetMmpsourcepath(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MULTILANGRSC:
				return basicSetMultilangrsc(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MULTIPLEDECLARATIONS:
				return basicSetMultipledeclarations(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MULTIPLEINHERITANCE:
				return basicSetMultipleinheritance(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__MYDOCS:
				return basicSetMydocs(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__NAMESPACE:
				return basicSetNamespace(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__NEWLREFERENCES:
				return basicSetNewlreferences(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__NOLEAVETRAP:
				return basicSetNoleavetrap(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__NONCONSTHBUFC:
				return basicSetNonconsthbufc(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__NONCONSTTDESC:
				return basicSetNonconsttdesc(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__NONLEAVENEW:
				return basicSetNonleavenew(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__NONUNICODESKINS:
				return basicSetNonunicodeskins(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__NULL:
				return basicSetNull(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__OPEN:
				return basicSetOpen(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__OTHER:
				return basicSetOther(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__PANIC:
				return basicSetPanic(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__PERFORMANCE:
				return basicSetPerformance(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__POINTERTOARRAYS:
				return basicSetPointertoarrays(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMADISABLE:
				return basicSetPragmadisable(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMAMESSAGE:
				return basicSetPragmamessage(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMAOTHER:
				return basicSetPragmaother(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__PRIVATEINHERITANCE:
				return basicSetPrivateinheritance(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__PUSHADDRVAR:
				return basicSetPushaddrvar(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__PUSHMEMBER:
				return basicSetPushmember(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__READRESOURCE:
				return basicSetReadresource(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__RESOURCENOTONCLEANUPSTACK:
				return basicSetResourcenotoncleanupstack(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__RESOURCESONHEAP:
				return basicSetResourcesonheap(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__RETURNDESCRIPTOROUTOFSCOPE:
				return basicSetReturndescriptoroutofscope(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__RFS:
				return basicSetRfs(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__RSSNAMES:
				return basicSetRssnames(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__SCRIPTS:
				return basicSetScripts(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITIES:
				return basicSetSeverities(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__SOURCES:
				return basicSetSources(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__STRINGLITERALS:
				return basicSetStringliterals(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__STRINGSINRESOURCEFILES:
				return basicSetStringsinresourcefiles(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__STRUCT:
				return basicSetStruct(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__TCCLASSES:
				return basicSetTcclasses(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__TCLASSDESTRUCTOR:
				return basicSetTclassdestructor(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__TODOCOMMENTS:
				return basicSetTodocomments(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__TRAPCLEANUP:
				return basicSetTrapcleanup(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__TRAPELEAVE:
				return basicSetTrapeleave(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__TRAPRUNL:
				return basicSetTraprunl(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__TRSPASSING:
				return basicSetTrspassing(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__UIDS:
				return basicSetUids(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__UNCOMPRESSEDAIF:
				return basicSetUncompressedaif(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__UNCOMPRESSEDBMP:
				return basicSetUncompressedbmp(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__UNICODESOURCE:
				return basicSetUnicodesource(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__USERAFTER:
				return basicSetUserafter(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__USERFREE:
				return basicSetUserfree(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__USER_WAIT_FOR_REQUEST:
				return basicSetUserWaitForRequest(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__VARIABLENAMES:
				return basicSetVariablenames(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__VOIDPARAMETER:
				return basicSetVoidparameter(null, msgs);
			case CSConfigPackage.DOCUMENT_ROOT__WORRYINGCOMMENTS:
				return basicSetWorryingcomments(null, msgs);
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
			case CSConfigPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case CSConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case CSConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case CSConfigPackage.DOCUMENT_ROOT__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK:
				return getAccessArrayElementWithoutCheck();
			case CSConfigPackage.DOCUMENT_ROOT__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2:
				return getAccessArrayElementWithoutCheck2();
			case CSConfigPackage.DOCUMENT_ROOT__ACTIVESTART:
				return getActivestart();
			case CSConfigPackage.DOCUMENT_ROOT__ACTIVESTOP:
				return getActivestop();
			case CSConfigPackage.DOCUMENT_ROOT__ARGUMENTS:
				return getArguments();
			case CSConfigPackage.DOCUMENT_ROOT__ARRAYPASSING:
				return getArraypassing();
			case CSConfigPackage.DOCUMENT_ROOT__ARRAYPTRCLEANUP:
				return getArrayptrcleanup();
			case CSConfigPackage.DOCUMENT_ROOT__ASSERTDEBUGINVARIANT:
				return getAssertdebuginvariant();
			case CSConfigPackage.DOCUMENT_ROOT__BADDEFINES:
				return getBaddefines();
			case CSConfigPackage.DOCUMENT_ROOT__BASECONSTRUCT:
				return getBaseconstruct();
			case CSConfigPackage.DOCUMENT_ROOT__CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING:
				return getCallActiveObjectWithoutCheckingOrStopping();
			case CSConfigPackage.DOCUMENT_ROOT__CANPANIC:
				return getCanpanic();
			case CSConfigPackage.DOCUMENT_ROOT__CATEGORIES:
				return getCategories();
			case CSConfigPackage.DOCUMENT_ROOT__CCLASS_IGNORE_RE:
				return getCclassIgnoreRE();
			case CSConfigPackage.DOCUMENT_ROOT__CHANGENOTIFICATION:
				return getChangenotification();
			case CSConfigPackage.DOCUMENT_ROOT__CLEANUP:
				return getCleanup();
			case CSConfigPackage.DOCUMENT_ROOT__CODEREVIEW:
				return getCodereview();
			case CSConfigPackage.DOCUMENT_ROOT__CODESCANNER_CONFIG:
				return getCodescannerConfig();
			case CSConfigPackage.DOCUMENT_ROOT__CODINGSTANDARDS:
				return getCodingstandards();
			case CSConfigPackage.DOCUMENT_ROOT__COMMENTCODE:
				return getCommentcode();
			case CSConfigPackage.DOCUMENT_ROOT__CONNECT:
				return getConnect();
			case CSConfigPackage.DOCUMENT_ROOT__CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE:
				return getConnectAndDontCloseMemberVariable();
			case CSConfigPackage.DOCUMENT_ROOT__CONSTNAMES:
				return getConstnames();
			case CSConfigPackage.DOCUMENT_ROOT__CONSTTDESCPTR:
				return getConsttdescptr();
			case CSConfigPackage.DOCUMENT_ROOT__CONTROLORNULL:
				return getControlornull();
			case CSConfigPackage.DOCUMENT_ROOT__CTLTARGETTYPE:
				return getCtltargettype();
			case CSConfigPackage.DOCUMENT_ROOT__CUSTOMRULE:
				return getCustomrule();
			case CSConfigPackage.DOCUMENT_ROOT__CUSTOMRULES:
				return getCustomrules();
			case CSConfigPackage.DOCUMENT_ROOT__DEBUGROM:
				return getDebugrom();
			case CSConfigPackage.DOCUMENT_ROOT__DECLARENAME:
				return getDeclarename();
			case CSConfigPackage.DOCUMENT_ROOT__DELETE_MEMBER_VARIABLE:
				return getDeleteMemberVariable();
			case CSConfigPackage.DOCUMENT_ROOT__DESCRIPTION:
				return getDescription();
			case CSConfigPackage.DOCUMENT_ROOT__DESTRUCTOR:
				return getDestructor();
			case CSConfigPackage.DOCUMENT_ROOT__DOCUMENTATION:
				return getDocumentation();
			case CSConfigPackage.DOCUMENT_ROOT__DOUBLE_SEMI_COLON:
				return getDoubleSemiColon();
			case CSConfigPackage.DOCUMENT_ROOT__DRIVELETTERS:
				return getDriveletters();
			case CSConfigPackage.DOCUMENT_ROOT__EIKBUTTONS:
				return getEikbuttons();
			case CSConfigPackage.DOCUMENT_ROOT__EIKONENVSTATIC:
				return getEikonenvstatic();
			case CSConfigPackage.DOCUMENT_ROOT__ENUMMEMBERS:
				return getEnummembers();
			case CSConfigPackage.DOCUMENT_ROOT__ENUMNAMES:
				return getEnumnames();
			case CSConfigPackage.DOCUMENT_ROOT__EXCLUDE:
				return getExclude();
			case CSConfigPackage.DOCUMENT_ROOT__EXPORTINLINE:
				return getExportinline();
			case CSConfigPackage.DOCUMENT_ROOT__EXPORTPUREVIRTUAL:
				return getExportpurevirtual();
			case CSConfigPackage.DOCUMENT_ROOT__EXTERNALDRIVELETTERS:
				return getExternaldriveletters();
			case CSConfigPackage.DOCUMENT_ROOT__FILETYPE:
				return getFiletype();
			case CSConfigPackage.DOCUMENT_ROOT__FOFF:
				return getFoff();
			case CSConfigPackage.DOCUMENT_ROOT__FORBIDDENWORDS:
				return getForbiddenwords();
			case CSConfigPackage.DOCUMENT_ROOT__FORGOTTOPUTPTRONCLEANUPSTACK:
				return getForgottoputptroncleanupstack();
			case CSConfigPackage.DOCUMENT_ROOT__FRIEND:
				return getFriend();
			case CSConfigPackage.DOCUMENT_ROOT__FUNCTIONALITY:
				return getFunctionality();
			case CSConfigPackage.DOCUMENT_ROOT__GOTO:
				return getGoto();
			case CSConfigPackage.DOCUMENT_ROOT__HIGH:
				return getHigh();
			case CSConfigPackage.DOCUMENT_ROOT__IFASSIGNMENTS:
				return getIfassignments();
			case CSConfigPackage.DOCUMENT_ROOT__IFPREPROCESSOR:
				return getIfpreprocessor();
			case CSConfigPackage.DOCUMENT_ROOT__INHERITANCEORDER:
				return getInheritanceorder();
			case CSConfigPackage.DOCUMENT_ROOT__INPUT:
				return getInput();
			case CSConfigPackage.DOCUMENT_ROOT__INTLEAVES:
				return getIntleaves();
			case CSConfigPackage.DOCUMENT_ROOT__JMP:
				return getJmp();
			case CSConfigPackage.DOCUMENT_ROOT__KEYWORD:
				return getKeyword();
			case CSConfigPackage.DOCUMENT_ROOT__LEAVE:
				return getLeave();
			case CSConfigPackage.DOCUMENT_ROOT__LEAVE_NO_ERROR:
				return getLeaveNoError();
			case CSConfigPackage.DOCUMENT_ROOT__LEAVINGOPERATORS:
				return getLeavingoperators();
			case CSConfigPackage.DOCUMENT_ROOT__LEGAL:
				return getLegal();
			case CSConfigPackage.DOCUMENT_ROOT__LFUNCTION_CANT_LEAVE:
				return getLFunctionCantLeave();
			case CSConfigPackage.DOCUMENT_ROOT__LFUNCTION_IGNORE_RE:
				return getLFunctionIgnoreRE();
			case CSConfigPackage.DOCUMENT_ROOT__LINK:
				return getLink();
			case CSConfigPackage.DOCUMENT_ROOT__LOCALISATION:
				return getLocalisation();
			case CSConfigPackage.DOCUMENT_ROOT__LONGLINES:
				return getLonglines();
			case CSConfigPackage.DOCUMENT_ROOT__LOW:
				return getLow();
			case CSConfigPackage.DOCUMENT_ROOT__LXR:
				return getLxr();
			case CSConfigPackage.DOCUMENT_ROOT__LXRVERSION:
				return getLxrversion();
			case CSConfigPackage.DOCUMENT_ROOT__MAGICNUMBERS:
				return getMagicnumbers();
			case CSConfigPackage.DOCUMENT_ROOT__MCLASSDESTRUCTOR:
				return getMclassdestructor();
			case CSConfigPackage.DOCUMENT_ROOT__MEDIUM:
				return getMedium();
			case CSConfigPackage.DOCUMENT_ROOT__MEMBERLC:
				return getMemberlc();
			case CSConfigPackage.DOCUMENT_ROOT__MEMBERVARIABLECALLLD:
				return getMembervariablecallld();
			case CSConfigPackage.DOCUMENT_ROOT__MISSINGCANCEL:
				return getMissingcancel();
			case CSConfigPackage.DOCUMENT_ROOT__MISSINGCCLASS:
				return getMissingcclass();
			case CSConfigPackage.DOCUMENT_ROOT__MMPSOURCEPATH:
				return getMmpsourcepath();
			case CSConfigPackage.DOCUMENT_ROOT__MULTILANGRSC:
				return getMultilangrsc();
			case CSConfigPackage.DOCUMENT_ROOT__MULTIPLEDECLARATIONS:
				return getMultipledeclarations();
			case CSConfigPackage.DOCUMENT_ROOT__MULTIPLEINHERITANCE:
				return getMultipleinheritance();
			case CSConfigPackage.DOCUMENT_ROOT__MYDOCS:
				return getMydocs();
			case CSConfigPackage.DOCUMENT_ROOT__NAME:
				return getName();
			case CSConfigPackage.DOCUMENT_ROOT__NAMESPACE:
				return getNamespace();
			case CSConfigPackage.DOCUMENT_ROOT__NEWLREFERENCES:
				return getNewlreferences();
			case CSConfigPackage.DOCUMENT_ROOT__NOLEAVETRAP:
				return getNoleavetrap();
			case CSConfigPackage.DOCUMENT_ROOT__NONCONSTHBUFC:
				return getNonconsthbufc();
			case CSConfigPackage.DOCUMENT_ROOT__NONCONSTTDESC:
				return getNonconsttdesc();
			case CSConfigPackage.DOCUMENT_ROOT__NONLEAVENEW:
				return getNonleavenew();
			case CSConfigPackage.DOCUMENT_ROOT__NONUNICODESKINS:
				return getNonunicodeskins();
			case CSConfigPackage.DOCUMENT_ROOT__NULL:
				return getNull();
			case CSConfigPackage.DOCUMENT_ROOT__OPEN:
				return getOpen();
			case CSConfigPackage.DOCUMENT_ROOT__OPEN_IGNORE_RE:
				return getOpenIgnoreRE();
			case CSConfigPackage.DOCUMENT_ROOT__OTHER:
				return getOther();
			case CSConfigPackage.DOCUMENT_ROOT__OUTPUTFORMAT:
				return getOutputformat();
			case CSConfigPackage.DOCUMENT_ROOT__PANIC:
				return getPanic();
			case CSConfigPackage.DOCUMENT_ROOT__PERFORMANCE:
				return getPerformance();
			case CSConfigPackage.DOCUMENT_ROOT__POINTERTOARRAYS:
				return getPointertoarrays();
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMADISABLE:
				return getPragmadisable();
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMAMESSAGE:
				return getPragmamessage();
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMAOTHER:
				return getPragmaother();
			case CSConfigPackage.DOCUMENT_ROOT__PRIVATEINHERITANCE:
				return getPrivateinheritance();
			case CSConfigPackage.DOCUMENT_ROOT__PUSHADDRVAR:
				return getPushaddrvar();
			case CSConfigPackage.DOCUMENT_ROOT__PUSHMEMBER:
				return getPushmember();
			case CSConfigPackage.DOCUMENT_ROOT__READRESOURCE:
				return getReadresource();
			case CSConfigPackage.DOCUMENT_ROOT__RESOURCENOTONCLEANUPSTACK:
				return getResourcenotoncleanupstack();
			case CSConfigPackage.DOCUMENT_ROOT__RESOURCESONHEAP:
				return getResourcesonheap();
			case CSConfigPackage.DOCUMENT_ROOT__RETURNDESCRIPTOROUTOFSCOPE:
				return getReturndescriptoroutofscope();
			case CSConfigPackage.DOCUMENT_ROOT__RFS:
				return getRfs();
			case CSConfigPackage.DOCUMENT_ROOT__RSSNAMES:
				return getRssnames();
			case CSConfigPackage.DOCUMENT_ROOT__SCRIPTS:
				return getScripts();
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITIES:
				return getSeverities();
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITY:
				return getSeverity();
			case CSConfigPackage.DOCUMENT_ROOT__SOURCES:
				return getSources();
			case CSConfigPackage.DOCUMENT_ROOT__STRINGLITERALS:
				return getStringliterals();
			case CSConfigPackage.DOCUMENT_ROOT__STRINGSINRESOURCEFILES:
				return getStringsinresourcefiles();
			case CSConfigPackage.DOCUMENT_ROOT__STRUCT:
				return getStruct();
			case CSConfigPackage.DOCUMENT_ROOT__TCCLASSES:
				return getTcclasses();
			case CSConfigPackage.DOCUMENT_ROOT__TCLASSDESTRUCTOR:
				return getTclassdestructor();
			case CSConfigPackage.DOCUMENT_ROOT__TIMESTAMPEDOUTPUT:
				return getTimestampedoutput();
			case CSConfigPackage.DOCUMENT_ROOT__TITLE:
				return getTitle();
			case CSConfigPackage.DOCUMENT_ROOT__TODOCOMMENTS:
				return getTodocomments();
			case CSConfigPackage.DOCUMENT_ROOT__TRAPCLEANUP:
				return getTrapcleanup();
			case CSConfigPackage.DOCUMENT_ROOT__TRAPELEAVE:
				return getTrapeleave();
			case CSConfigPackage.DOCUMENT_ROOT__TRAPRUNL:
				return getTraprunl();
			case CSConfigPackage.DOCUMENT_ROOT__TRSPASSING:
				return getTrspassing();
			case CSConfigPackage.DOCUMENT_ROOT__UIDS:
				return getUids();
			case CSConfigPackage.DOCUMENT_ROOT__UNCOMPRESSEDAIF:
				return getUncompressedaif();
			case CSConfigPackage.DOCUMENT_ROOT__UNCOMPRESSEDBMP:
				return getUncompressedbmp();
			case CSConfigPackage.DOCUMENT_ROOT__UNICODESOURCE:
				return getUnicodesource();
			case CSConfigPackage.DOCUMENT_ROOT__USERAFTER:
				return getUserafter();
			case CSConfigPackage.DOCUMENT_ROOT__USERFREE:
				return getUserfree();
			case CSConfigPackage.DOCUMENT_ROOT__USER_WAIT_FOR_REQUEST:
				return getUserWaitForRequest();
			case CSConfigPackage.DOCUMENT_ROOT__VARIABLENAMES:
				return getVariablenames();
			case CSConfigPackage.DOCUMENT_ROOT__VOIDPARAMETER:
				return getVoidparameter();
			case CSConfigPackage.DOCUMENT_ROOT__WORDS_RE:
				return getWordsRE();
			case CSConfigPackage.DOCUMENT_ROOT__WORRYINGCOMMENTS:
				return getWorryingcomments();
			case CSConfigPackage.DOCUMENT_ROOT__WORRY_RE:
				return getWorryRE();
			case CSConfigPackage.DOCUMENT_ROOT__CATEGORY:
				return getCategory();
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITY1:
				return getSeverity1();
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
			case CSConfigPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK:
				setAccessArrayElementWithoutCheck((AccessArrayElementWithoutCheckType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2:
				setAccessArrayElementWithoutCheck2((AccessArrayElementWithoutCheck2Type)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ACTIVESTART:
				setActivestart((ActivestartType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ACTIVESTOP:
				setActivestop((ActivestopType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ARGUMENTS:
				setArguments((ArgumentsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ARRAYPASSING:
				setArraypassing((ArraypassingType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ARRAYPTRCLEANUP:
				setArrayptrcleanup((ArrayptrcleanupType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ASSERTDEBUGINVARIANT:
				setAssertdebuginvariant((AssertdebuginvariantType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__BADDEFINES:
				setBaddefines((BaddefinesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__BASECONSTRUCT:
				setBaseconstruct((BaseconstructType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING:
				setCallActiveObjectWithoutCheckingOrStopping((CallActiveObjectWithoutCheckingOrStoppingType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CANPANIC:
				setCanpanic((CanpanicType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CATEGORIES:
				setCategories((CategoriesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CCLASS_IGNORE_RE:
				setCclassIgnoreRE((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CHANGENOTIFICATION:
				setChangenotification((ChangenotificationType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CLEANUP:
				setCleanup((CleanupType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CODEREVIEW:
				setCodereview((CodereviewType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CODESCANNER_CONFIG:
				setCodescannerConfig((CodescannerConfigType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CODINGSTANDARDS:
				setCodingstandards((CodingstandardsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__COMMENTCODE:
				setCommentcode((CommentcodeType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CONNECT:
				setConnect((ConnectType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE:
				setConnectAndDontCloseMemberVariable((ConnectAndDontCloseMemberVariableType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CONSTNAMES:
				setConstnames((ConstnamesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CONSTTDESCPTR:
				setConsttdescptr((ConsttdescptrType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CONTROLORNULL:
				setControlornull((ControlornullType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CTLTARGETTYPE:
				setCtltargettype((CtltargettypeType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CUSTOMRULE:
				setCustomrule((CustomruleType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CUSTOMRULES:
				setCustomrules((CustomrulesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DEBUGROM:
				setDebugrom((DebugromType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DECLARENAME:
				setDeclarename((DeclarenameType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DELETE_MEMBER_VARIABLE:
				setDeleteMemberVariable((DeleteMemberVariableType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DESTRUCTOR:
				setDestructor((DestructorType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DOCUMENTATION:
				setDocumentation((DocumentationType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DOUBLE_SEMI_COLON:
				setDoubleSemiColon((DoubleSemiColonType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DRIVELETTERS:
				setDriveletters((DrivelettersType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EIKBUTTONS:
				setEikbuttons((EikbuttonsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EIKONENVSTATIC:
				setEikonenvstatic((EikonenvstaticType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ENUMMEMBERS:
				setEnummembers((EnummembersType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ENUMNAMES:
				setEnumnames((EnumnamesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EXCLUDE:
				setExclude((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EXPORTINLINE:
				setExportinline((ExportinlineType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EXPORTPUREVIRTUAL:
				setExportpurevirtual((ExportpurevirtualType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EXTERNALDRIVELETTERS:
				setExternaldriveletters((ExternaldrivelettersType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FILETYPE:
				setFiletype((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FOFF:
				setFoff((FoffType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FORBIDDENWORDS:
				setForbiddenwords((ForbiddenwordsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FORGOTTOPUTPTRONCLEANUPSTACK:
				setForgottoputptroncleanupstack((ForgottoputptroncleanupstackType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FRIEND:
				setFriend((FriendType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FUNCTIONALITY:
				setFunctionality((FunctionalityType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__GOTO:
				setGoto((GotoType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__HIGH:
				setHigh((HighType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__IFASSIGNMENTS:
				setIfassignments((IfassignmentsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__IFPREPROCESSOR:
				setIfpreprocessor((IfpreprocessorType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__INHERITANCEORDER:
				setInheritanceorder((InheritanceorderType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__INPUT:
				setInput((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__INTLEAVES:
				setIntleaves((IntleavesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__JMP:
				setJmp((JmpType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__KEYWORD:
				setKeyword((KeywordType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LEAVE:
				setLeave((LeaveType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LEAVE_NO_ERROR:
				setLeaveNoError((LeaveNoErrorType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LEAVINGOPERATORS:
				setLeavingoperators((LeavingoperatorsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LEGAL:
				setLegal((LegalType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LFUNCTION_CANT_LEAVE:
				setLFunctionCantLeave((LFunctionCantLeaveType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LFUNCTION_IGNORE_RE:
				setLFunctionIgnoreRE((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LINK:
				setLink((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LOCALISATION:
				setLocalisation((LocalisationType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LONGLINES:
				setLonglines((LonglinesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LOW:
				setLow((LowType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LXR:
				setLxr((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LXRVERSION:
				setLxrversion((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MAGICNUMBERS:
				setMagicnumbers((MagicnumbersType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MCLASSDESTRUCTOR:
				setMclassdestructor((MclassdestructorType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MEDIUM:
				setMedium((MediumType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MEMBERLC:
				setMemberlc((MemberlcType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MEMBERVARIABLECALLLD:
				setMembervariablecallld((MembervariablecallldType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MISSINGCANCEL:
				setMissingcancel((MissingcancelType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MISSINGCCLASS:
				setMissingcclass((MissingcclassType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MMPSOURCEPATH:
				setMmpsourcepath((MmpsourcepathType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MULTILANGRSC:
				setMultilangrsc((MultilangrscType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MULTIPLEDECLARATIONS:
				setMultipledeclarations((MultipledeclarationsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MULTIPLEINHERITANCE:
				setMultipleinheritance((MultipleinheritanceType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MYDOCS:
				setMydocs((MydocsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NAME:
				setName((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NAMESPACE:
				setNamespace((NamespaceType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NEWLREFERENCES:
				setNewlreferences((NewlreferencesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NOLEAVETRAP:
				setNoleavetrap((NoleavetrapType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NONCONSTHBUFC:
				setNonconsthbufc((NonconsthbufcType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NONCONSTTDESC:
				setNonconsttdesc((NonconsttdescType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NONLEAVENEW:
				setNonleavenew((NonleavenewType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NONUNICODESKINS:
				setNonunicodeskins((NonunicodeskinsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NULL:
				setNull((NullType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__OPEN:
				setOpen((OpenType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__OPEN_IGNORE_RE:
				setOpenIgnoreRE((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__OTHER:
				setOther((OtherType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__OUTPUTFORMAT:
				setOutputformat((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PANIC:
				setPanic((PanicType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PERFORMANCE:
				setPerformance((PerformanceType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__POINTERTOARRAYS:
				setPointertoarrays((PointertoarraysType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMADISABLE:
				setPragmadisable((PragmadisableType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMAMESSAGE:
				setPragmamessage((PragmamessageType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMAOTHER:
				setPragmaother((PragmaotherType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PRIVATEINHERITANCE:
				setPrivateinheritance((PrivateinheritanceType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PUSHADDRVAR:
				setPushaddrvar((PushaddrvarType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PUSHMEMBER:
				setPushmember((PushmemberType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__READRESOURCE:
				setReadresource((ReadresourceType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__RESOURCENOTONCLEANUPSTACK:
				setResourcenotoncleanupstack((ResourcenotoncleanupstackType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__RESOURCESONHEAP:
				setResourcesonheap((ResourcesonheapType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__RETURNDESCRIPTOROUTOFSCOPE:
				setReturndescriptoroutofscope((ReturndescriptoroutofscopeType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__RFS:
				setRfs((RfsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__RSSNAMES:
				setRssnames((RssnamesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__SCRIPTS:
				setScripts((ScriptsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITIES:
				setSeverities((SeveritiesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITY:
				setSeverity((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__SOURCES:
				setSources((SourcesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__STRINGLITERALS:
				setStringliterals((StringliteralsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__STRINGSINRESOURCEFILES:
				setStringsinresourcefiles((StringsinresourcefilesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__STRUCT:
				setStruct((StructType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TCCLASSES:
				setTcclasses((TcclassesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TCLASSDESTRUCTOR:
				setTclassdestructor((TclassdestructorType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TIMESTAMPEDOUTPUT:
				setTimestampedoutput((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TITLE:
				setTitle((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TODOCOMMENTS:
				setTodocomments((TodocommentsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TRAPCLEANUP:
				setTrapcleanup((TrapcleanupType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TRAPELEAVE:
				setTrapeleave((TrapeleaveType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TRAPRUNL:
				setTraprunl((TraprunlType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TRSPASSING:
				setTrspassing((TrspassingType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__UIDS:
				setUids((UidsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__UNCOMPRESSEDAIF:
				setUncompressedaif((UncompressedaifType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__UNCOMPRESSEDBMP:
				setUncompressedbmp((UncompressedbmpType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__UNICODESOURCE:
				setUnicodesource((UnicodesourceType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__USERAFTER:
				setUserafter((UserafterType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__USERFREE:
				setUserfree((UserfreeType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__USER_WAIT_FOR_REQUEST:
				setUserWaitForRequest((UserWaitForRequestType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__VARIABLENAMES:
				setVariablenames((VariablenamesType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__VOIDPARAMETER:
				setVoidparameter((VoidparameterType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__WORDS_RE:
				setWordsRE((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__WORRYINGCOMMENTS:
				setWorryingcomments((WorryingcommentsType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__WORRY_RE:
				setWorryRE((String)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CATEGORY:
				setCategory((CategoryType)newValue);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITY1:
				setSeverity1((SeverityType)newValue);
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
			case CSConfigPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case CSConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case CSConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK:
				setAccessArrayElementWithoutCheck((AccessArrayElementWithoutCheckType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2:
				setAccessArrayElementWithoutCheck2((AccessArrayElementWithoutCheck2Type)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ACTIVESTART:
				setActivestart((ActivestartType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ACTIVESTOP:
				setActivestop((ActivestopType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ARGUMENTS:
				setArguments((ArgumentsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ARRAYPASSING:
				setArraypassing((ArraypassingType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ARRAYPTRCLEANUP:
				setArrayptrcleanup((ArrayptrcleanupType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ASSERTDEBUGINVARIANT:
				setAssertdebuginvariant((AssertdebuginvariantType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__BADDEFINES:
				setBaddefines((BaddefinesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__BASECONSTRUCT:
				setBaseconstruct((BaseconstructType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING:
				setCallActiveObjectWithoutCheckingOrStopping((CallActiveObjectWithoutCheckingOrStoppingType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CANPANIC:
				setCanpanic((CanpanicType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CATEGORIES:
				setCategories((CategoriesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CCLASS_IGNORE_RE:
				setCclassIgnoreRE(CCLASS_IGNORE_RE_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CHANGENOTIFICATION:
				setChangenotification((ChangenotificationType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CLEANUP:
				setCleanup((CleanupType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CODEREVIEW:
				setCodereview((CodereviewType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CODESCANNER_CONFIG:
				setCodescannerConfig((CodescannerConfigType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CODINGSTANDARDS:
				setCodingstandards((CodingstandardsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__COMMENTCODE:
				setCommentcode((CommentcodeType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CONNECT:
				setConnect((ConnectType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE:
				setConnectAndDontCloseMemberVariable((ConnectAndDontCloseMemberVariableType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CONSTNAMES:
				setConstnames((ConstnamesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CONSTTDESCPTR:
				setConsttdescptr((ConsttdescptrType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CONTROLORNULL:
				setControlornull((ControlornullType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CTLTARGETTYPE:
				setCtltargettype((CtltargettypeType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CUSTOMRULE:
				setCustomrule((CustomruleType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CUSTOMRULES:
				setCustomrules((CustomrulesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DEBUGROM:
				setDebugrom((DebugromType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DECLARENAME:
				setDeclarename((DeclarenameType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DELETE_MEMBER_VARIABLE:
				setDeleteMemberVariable((DeleteMemberVariableType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DESTRUCTOR:
				setDestructor((DestructorType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DOCUMENTATION:
				setDocumentation((DocumentationType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DOUBLE_SEMI_COLON:
				setDoubleSemiColon((DoubleSemiColonType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__DRIVELETTERS:
				setDriveletters((DrivelettersType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EIKBUTTONS:
				setEikbuttons((EikbuttonsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EIKONENVSTATIC:
				setEikonenvstatic((EikonenvstaticType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ENUMMEMBERS:
				setEnummembers((EnummembersType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__ENUMNAMES:
				setEnumnames((EnumnamesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EXCLUDE:
				setExclude(EXCLUDE_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EXPORTINLINE:
				setExportinline((ExportinlineType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EXPORTPUREVIRTUAL:
				setExportpurevirtual((ExportpurevirtualType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__EXTERNALDRIVELETTERS:
				setExternaldriveletters((ExternaldrivelettersType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FILETYPE:
				setFiletype(FILETYPE_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FOFF:
				setFoff((FoffType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FORBIDDENWORDS:
				setForbiddenwords((ForbiddenwordsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FORGOTTOPUTPTRONCLEANUPSTACK:
				setForgottoputptroncleanupstack((ForgottoputptroncleanupstackType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FRIEND:
				setFriend((FriendType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__FUNCTIONALITY:
				setFunctionality((FunctionalityType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__GOTO:
				setGoto((GotoType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__HIGH:
				setHigh((HighType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__IFASSIGNMENTS:
				setIfassignments((IfassignmentsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__IFPREPROCESSOR:
				setIfpreprocessor((IfpreprocessorType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__INHERITANCEORDER:
				setInheritanceorder((InheritanceorderType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__INPUT:
				setInput(INPUT_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__INTLEAVES:
				setIntleaves((IntleavesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__JMP:
				setJmp((JmpType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__KEYWORD:
				setKeyword((KeywordType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LEAVE:
				setLeave((LeaveType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LEAVE_NO_ERROR:
				setLeaveNoError((LeaveNoErrorType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LEAVINGOPERATORS:
				setLeavingoperators((LeavingoperatorsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LEGAL:
				setLegal((LegalType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LFUNCTION_CANT_LEAVE:
				setLFunctionCantLeave((LFunctionCantLeaveType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LFUNCTION_IGNORE_RE:
				setLFunctionIgnoreRE(LFUNCTION_IGNORE_RE_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LINK:
				setLink(LINK_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LOCALISATION:
				setLocalisation((LocalisationType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LONGLINES:
				setLonglines((LonglinesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LOW:
				setLow((LowType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LXR:
				setLxr(LXR_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__LXRVERSION:
				setLxrversion(LXRVERSION_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MAGICNUMBERS:
				setMagicnumbers((MagicnumbersType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MCLASSDESTRUCTOR:
				setMclassdestructor((MclassdestructorType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MEDIUM:
				setMedium((MediumType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MEMBERLC:
				setMemberlc((MemberlcType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MEMBERVARIABLECALLLD:
				setMembervariablecallld((MembervariablecallldType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MISSINGCANCEL:
				setMissingcancel((MissingcancelType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MISSINGCCLASS:
				setMissingcclass((MissingcclassType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MMPSOURCEPATH:
				setMmpsourcepath((MmpsourcepathType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MULTILANGRSC:
				setMultilangrsc((MultilangrscType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MULTIPLEDECLARATIONS:
				setMultipledeclarations((MultipledeclarationsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MULTIPLEINHERITANCE:
				setMultipleinheritance((MultipleinheritanceType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__MYDOCS:
				setMydocs((MydocsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NAMESPACE:
				setNamespace((NamespaceType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NEWLREFERENCES:
				setNewlreferences((NewlreferencesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NOLEAVETRAP:
				setNoleavetrap((NoleavetrapType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NONCONSTHBUFC:
				setNonconsthbufc((NonconsthbufcType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NONCONSTTDESC:
				setNonconsttdesc((NonconsttdescType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NONLEAVENEW:
				setNonleavenew((NonleavenewType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NONUNICODESKINS:
				setNonunicodeskins((NonunicodeskinsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__NULL:
				setNull((NullType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__OPEN:
				setOpen((OpenType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__OPEN_IGNORE_RE:
				setOpenIgnoreRE(OPEN_IGNORE_RE_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__OTHER:
				setOther((OtherType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__OUTPUTFORMAT:
				setOutputformat(OUTPUTFORMAT_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PANIC:
				setPanic((PanicType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PERFORMANCE:
				setPerformance((PerformanceType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__POINTERTOARRAYS:
				setPointertoarrays((PointertoarraysType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMADISABLE:
				setPragmadisable((PragmadisableType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMAMESSAGE:
				setPragmamessage((PragmamessageType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMAOTHER:
				setPragmaother((PragmaotherType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PRIVATEINHERITANCE:
				setPrivateinheritance((PrivateinheritanceType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PUSHADDRVAR:
				setPushaddrvar((PushaddrvarType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__PUSHMEMBER:
				setPushmember((PushmemberType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__READRESOURCE:
				setReadresource((ReadresourceType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__RESOURCENOTONCLEANUPSTACK:
				setResourcenotoncleanupstack((ResourcenotoncleanupstackType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__RESOURCESONHEAP:
				setResourcesonheap((ResourcesonheapType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__RETURNDESCRIPTOROUTOFSCOPE:
				setReturndescriptoroutofscope((ReturndescriptoroutofscopeType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__RFS:
				setRfs((RfsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__RSSNAMES:
				setRssnames((RssnamesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__SCRIPTS:
				setScripts((ScriptsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITIES:
				setSeverities((SeveritiesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITY:
				setSeverity(SEVERITY_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__SOURCES:
				setSources((SourcesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__STRINGLITERALS:
				setStringliterals((StringliteralsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__STRINGSINRESOURCEFILES:
				setStringsinresourcefiles((StringsinresourcefilesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__STRUCT:
				setStruct((StructType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TCCLASSES:
				setTcclasses((TcclassesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TCLASSDESTRUCTOR:
				setTclassdestructor((TclassdestructorType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TIMESTAMPEDOUTPUT:
				setTimestampedoutput(TIMESTAMPEDOUTPUT_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TODOCOMMENTS:
				setTodocomments((TodocommentsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TRAPCLEANUP:
				setTrapcleanup((TrapcleanupType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TRAPELEAVE:
				setTrapeleave((TrapeleaveType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TRAPRUNL:
				setTraprunl((TraprunlType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__TRSPASSING:
				setTrspassing((TrspassingType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__UIDS:
				setUids((UidsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__UNCOMPRESSEDAIF:
				setUncompressedaif((UncompressedaifType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__UNCOMPRESSEDBMP:
				setUncompressedbmp((UncompressedbmpType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__UNICODESOURCE:
				setUnicodesource((UnicodesourceType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__USERAFTER:
				setUserafter((UserafterType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__USERFREE:
				setUserfree((UserfreeType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__USER_WAIT_FOR_REQUEST:
				setUserWaitForRequest((UserWaitForRequestType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__VARIABLENAMES:
				setVariablenames((VariablenamesType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__VOIDPARAMETER:
				setVoidparameter((VoidparameterType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__WORDS_RE:
				setWordsRE(WORDS_RE_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__WORRYINGCOMMENTS:
				setWorryingcomments((WorryingcommentsType)null);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__WORRY_RE:
				setWorryRE(WORRY_RE_EDEFAULT);
				return;
			case CSConfigPackage.DOCUMENT_ROOT__CATEGORY:
				unsetCategory();
				return;
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITY1:
				unsetSeverity1();
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
			case CSConfigPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case CSConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case CSConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case CSConfigPackage.DOCUMENT_ROOT__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK:
				return getAccessArrayElementWithoutCheck() != null;
			case CSConfigPackage.DOCUMENT_ROOT__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2:
				return getAccessArrayElementWithoutCheck2() != null;
			case CSConfigPackage.DOCUMENT_ROOT__ACTIVESTART:
				return getActivestart() != null;
			case CSConfigPackage.DOCUMENT_ROOT__ACTIVESTOP:
				return getActivestop() != null;
			case CSConfigPackage.DOCUMENT_ROOT__ARGUMENTS:
				return getArguments() != null;
			case CSConfigPackage.DOCUMENT_ROOT__ARRAYPASSING:
				return getArraypassing() != null;
			case CSConfigPackage.DOCUMENT_ROOT__ARRAYPTRCLEANUP:
				return getArrayptrcleanup() != null;
			case CSConfigPackage.DOCUMENT_ROOT__ASSERTDEBUGINVARIANT:
				return getAssertdebuginvariant() != null;
			case CSConfigPackage.DOCUMENT_ROOT__BADDEFINES:
				return getBaddefines() != null;
			case CSConfigPackage.DOCUMENT_ROOT__BASECONSTRUCT:
				return getBaseconstruct() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING:
				return getCallActiveObjectWithoutCheckingOrStopping() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CANPANIC:
				return getCanpanic() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CATEGORIES:
				return getCategories() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CCLASS_IGNORE_RE:
				return CCLASS_IGNORE_RE_EDEFAULT == null ? getCclassIgnoreRE() != null : !CCLASS_IGNORE_RE_EDEFAULT.equals(getCclassIgnoreRE());
			case CSConfigPackage.DOCUMENT_ROOT__CHANGENOTIFICATION:
				return getChangenotification() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CLEANUP:
				return getCleanup() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CODEREVIEW:
				return getCodereview() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CODESCANNER_CONFIG:
				return getCodescannerConfig() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CODINGSTANDARDS:
				return getCodingstandards() != null;
			case CSConfigPackage.DOCUMENT_ROOT__COMMENTCODE:
				return getCommentcode() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CONNECT:
				return getConnect() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE:
				return getConnectAndDontCloseMemberVariable() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CONSTNAMES:
				return getConstnames() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CONSTTDESCPTR:
				return getConsttdescptr() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CONTROLORNULL:
				return getControlornull() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CTLTARGETTYPE:
				return getCtltargettype() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CUSTOMRULE:
				return getCustomrule() != null;
			case CSConfigPackage.DOCUMENT_ROOT__CUSTOMRULES:
				return getCustomrules() != null;
			case CSConfigPackage.DOCUMENT_ROOT__DEBUGROM:
				return getDebugrom() != null;
			case CSConfigPackage.DOCUMENT_ROOT__DECLARENAME:
				return getDeclarename() != null;
			case CSConfigPackage.DOCUMENT_ROOT__DELETE_MEMBER_VARIABLE:
				return getDeleteMemberVariable() != null;
			case CSConfigPackage.DOCUMENT_ROOT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case CSConfigPackage.DOCUMENT_ROOT__DESTRUCTOR:
				return getDestructor() != null;
			case CSConfigPackage.DOCUMENT_ROOT__DOCUMENTATION:
				return getDocumentation() != null;
			case CSConfigPackage.DOCUMENT_ROOT__DOUBLE_SEMI_COLON:
				return getDoubleSemiColon() != null;
			case CSConfigPackage.DOCUMENT_ROOT__DRIVELETTERS:
				return getDriveletters() != null;
			case CSConfigPackage.DOCUMENT_ROOT__EIKBUTTONS:
				return getEikbuttons() != null;
			case CSConfigPackage.DOCUMENT_ROOT__EIKONENVSTATIC:
				return getEikonenvstatic() != null;
			case CSConfigPackage.DOCUMENT_ROOT__ENUMMEMBERS:
				return getEnummembers() != null;
			case CSConfigPackage.DOCUMENT_ROOT__ENUMNAMES:
				return getEnumnames() != null;
			case CSConfigPackage.DOCUMENT_ROOT__EXCLUDE:
				return EXCLUDE_EDEFAULT == null ? getExclude() != null : !EXCLUDE_EDEFAULT.equals(getExclude());
			case CSConfigPackage.DOCUMENT_ROOT__EXPORTINLINE:
				return getExportinline() != null;
			case CSConfigPackage.DOCUMENT_ROOT__EXPORTPUREVIRTUAL:
				return getExportpurevirtual() != null;
			case CSConfigPackage.DOCUMENT_ROOT__EXTERNALDRIVELETTERS:
				return getExternaldriveletters() != null;
			case CSConfigPackage.DOCUMENT_ROOT__FILETYPE:
				return FILETYPE_EDEFAULT == null ? getFiletype() != null : !FILETYPE_EDEFAULT.equals(getFiletype());
			case CSConfigPackage.DOCUMENT_ROOT__FOFF:
				return getFoff() != null;
			case CSConfigPackage.DOCUMENT_ROOT__FORBIDDENWORDS:
				return getForbiddenwords() != null;
			case CSConfigPackage.DOCUMENT_ROOT__FORGOTTOPUTPTRONCLEANUPSTACK:
				return getForgottoputptroncleanupstack() != null;
			case CSConfigPackage.DOCUMENT_ROOT__FRIEND:
				return getFriend() != null;
			case CSConfigPackage.DOCUMENT_ROOT__FUNCTIONALITY:
				return getFunctionality() != null;
			case CSConfigPackage.DOCUMENT_ROOT__GOTO:
				return getGoto() != null;
			case CSConfigPackage.DOCUMENT_ROOT__HIGH:
				return getHigh() != null;
			case CSConfigPackage.DOCUMENT_ROOT__IFASSIGNMENTS:
				return getIfassignments() != null;
			case CSConfigPackage.DOCUMENT_ROOT__IFPREPROCESSOR:
				return getIfpreprocessor() != null;
			case CSConfigPackage.DOCUMENT_ROOT__INHERITANCEORDER:
				return getInheritanceorder() != null;
			case CSConfigPackage.DOCUMENT_ROOT__INPUT:
				return INPUT_EDEFAULT == null ? getInput() != null : !INPUT_EDEFAULT.equals(getInput());
			case CSConfigPackage.DOCUMENT_ROOT__INTLEAVES:
				return getIntleaves() != null;
			case CSConfigPackage.DOCUMENT_ROOT__JMP:
				return getJmp() != null;
			case CSConfigPackage.DOCUMENT_ROOT__KEYWORD:
				return getKeyword() != null;
			case CSConfigPackage.DOCUMENT_ROOT__LEAVE:
				return getLeave() != null;
			case CSConfigPackage.DOCUMENT_ROOT__LEAVE_NO_ERROR:
				return getLeaveNoError() != null;
			case CSConfigPackage.DOCUMENT_ROOT__LEAVINGOPERATORS:
				return getLeavingoperators() != null;
			case CSConfigPackage.DOCUMENT_ROOT__LEGAL:
				return getLegal() != null;
			case CSConfigPackage.DOCUMENT_ROOT__LFUNCTION_CANT_LEAVE:
				return getLFunctionCantLeave() != null;
			case CSConfigPackage.DOCUMENT_ROOT__LFUNCTION_IGNORE_RE:
				return LFUNCTION_IGNORE_RE_EDEFAULT == null ? getLFunctionIgnoreRE() != null : !LFUNCTION_IGNORE_RE_EDEFAULT.equals(getLFunctionIgnoreRE());
			case CSConfigPackage.DOCUMENT_ROOT__LINK:
				return LINK_EDEFAULT == null ? getLink() != null : !LINK_EDEFAULT.equals(getLink());
			case CSConfigPackage.DOCUMENT_ROOT__LOCALISATION:
				return getLocalisation() != null;
			case CSConfigPackage.DOCUMENT_ROOT__LONGLINES:
				return getLonglines() != null;
			case CSConfigPackage.DOCUMENT_ROOT__LOW:
				return getLow() != null;
			case CSConfigPackage.DOCUMENT_ROOT__LXR:
				return LXR_EDEFAULT == null ? getLxr() != null : !LXR_EDEFAULT.equals(getLxr());
			case CSConfigPackage.DOCUMENT_ROOT__LXRVERSION:
				return LXRVERSION_EDEFAULT == null ? getLxrversion() != null : !LXRVERSION_EDEFAULT.equals(getLxrversion());
			case CSConfigPackage.DOCUMENT_ROOT__MAGICNUMBERS:
				return getMagicnumbers() != null;
			case CSConfigPackage.DOCUMENT_ROOT__MCLASSDESTRUCTOR:
				return getMclassdestructor() != null;
			case CSConfigPackage.DOCUMENT_ROOT__MEDIUM:
				return getMedium() != null;
			case CSConfigPackage.DOCUMENT_ROOT__MEMBERLC:
				return getMemberlc() != null;
			case CSConfigPackage.DOCUMENT_ROOT__MEMBERVARIABLECALLLD:
				return getMembervariablecallld() != null;
			case CSConfigPackage.DOCUMENT_ROOT__MISSINGCANCEL:
				return getMissingcancel() != null;
			case CSConfigPackage.DOCUMENT_ROOT__MISSINGCCLASS:
				return getMissingcclass() != null;
			case CSConfigPackage.DOCUMENT_ROOT__MMPSOURCEPATH:
				return getMmpsourcepath() != null;
			case CSConfigPackage.DOCUMENT_ROOT__MULTILANGRSC:
				return getMultilangrsc() != null;
			case CSConfigPackage.DOCUMENT_ROOT__MULTIPLEDECLARATIONS:
				return getMultipledeclarations() != null;
			case CSConfigPackage.DOCUMENT_ROOT__MULTIPLEINHERITANCE:
				return getMultipleinheritance() != null;
			case CSConfigPackage.DOCUMENT_ROOT__MYDOCS:
				return getMydocs() != null;
			case CSConfigPackage.DOCUMENT_ROOT__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case CSConfigPackage.DOCUMENT_ROOT__NAMESPACE:
				return getNamespace() != null;
			case CSConfigPackage.DOCUMENT_ROOT__NEWLREFERENCES:
				return getNewlreferences() != null;
			case CSConfigPackage.DOCUMENT_ROOT__NOLEAVETRAP:
				return getNoleavetrap() != null;
			case CSConfigPackage.DOCUMENT_ROOT__NONCONSTHBUFC:
				return getNonconsthbufc() != null;
			case CSConfigPackage.DOCUMENT_ROOT__NONCONSTTDESC:
				return getNonconsttdesc() != null;
			case CSConfigPackage.DOCUMENT_ROOT__NONLEAVENEW:
				return getNonleavenew() != null;
			case CSConfigPackage.DOCUMENT_ROOT__NONUNICODESKINS:
				return getNonunicodeskins() != null;
			case CSConfigPackage.DOCUMENT_ROOT__NULL:
				return getNull() != null;
			case CSConfigPackage.DOCUMENT_ROOT__OPEN:
				return getOpen() != null;
			case CSConfigPackage.DOCUMENT_ROOT__OPEN_IGNORE_RE:
				return OPEN_IGNORE_RE_EDEFAULT == null ? getOpenIgnoreRE() != null : !OPEN_IGNORE_RE_EDEFAULT.equals(getOpenIgnoreRE());
			case CSConfigPackage.DOCUMENT_ROOT__OTHER:
				return getOther() != null;
			case CSConfigPackage.DOCUMENT_ROOT__OUTPUTFORMAT:
				return OUTPUTFORMAT_EDEFAULT == null ? getOutputformat() != null : !OUTPUTFORMAT_EDEFAULT.equals(getOutputformat());
			case CSConfigPackage.DOCUMENT_ROOT__PANIC:
				return getPanic() != null;
			case CSConfigPackage.DOCUMENT_ROOT__PERFORMANCE:
				return getPerformance() != null;
			case CSConfigPackage.DOCUMENT_ROOT__POINTERTOARRAYS:
				return getPointertoarrays() != null;
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMADISABLE:
				return getPragmadisable() != null;
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMAMESSAGE:
				return getPragmamessage() != null;
			case CSConfigPackage.DOCUMENT_ROOT__PRAGMAOTHER:
				return getPragmaother() != null;
			case CSConfigPackage.DOCUMENT_ROOT__PRIVATEINHERITANCE:
				return getPrivateinheritance() != null;
			case CSConfigPackage.DOCUMENT_ROOT__PUSHADDRVAR:
				return getPushaddrvar() != null;
			case CSConfigPackage.DOCUMENT_ROOT__PUSHMEMBER:
				return getPushmember() != null;
			case CSConfigPackage.DOCUMENT_ROOT__READRESOURCE:
				return getReadresource() != null;
			case CSConfigPackage.DOCUMENT_ROOT__RESOURCENOTONCLEANUPSTACK:
				return getResourcenotoncleanupstack() != null;
			case CSConfigPackage.DOCUMENT_ROOT__RESOURCESONHEAP:
				return getResourcesonheap() != null;
			case CSConfigPackage.DOCUMENT_ROOT__RETURNDESCRIPTOROUTOFSCOPE:
				return getReturndescriptoroutofscope() != null;
			case CSConfigPackage.DOCUMENT_ROOT__RFS:
				return getRfs() != null;
			case CSConfigPackage.DOCUMENT_ROOT__RSSNAMES:
				return getRssnames() != null;
			case CSConfigPackage.DOCUMENT_ROOT__SCRIPTS:
				return getScripts() != null;
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITIES:
				return getSeverities() != null;
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITY:
				return SEVERITY_EDEFAULT == null ? getSeverity() != null : !SEVERITY_EDEFAULT.equals(getSeverity());
			case CSConfigPackage.DOCUMENT_ROOT__SOURCES:
				return getSources() != null;
			case CSConfigPackage.DOCUMENT_ROOT__STRINGLITERALS:
				return getStringliterals() != null;
			case CSConfigPackage.DOCUMENT_ROOT__STRINGSINRESOURCEFILES:
				return getStringsinresourcefiles() != null;
			case CSConfigPackage.DOCUMENT_ROOT__STRUCT:
				return getStruct() != null;
			case CSConfigPackage.DOCUMENT_ROOT__TCCLASSES:
				return getTcclasses() != null;
			case CSConfigPackage.DOCUMENT_ROOT__TCLASSDESTRUCTOR:
				return getTclassdestructor() != null;
			case CSConfigPackage.DOCUMENT_ROOT__TIMESTAMPEDOUTPUT:
				return TIMESTAMPEDOUTPUT_EDEFAULT == null ? getTimestampedoutput() != null : !TIMESTAMPEDOUTPUT_EDEFAULT.equals(getTimestampedoutput());
			case CSConfigPackage.DOCUMENT_ROOT__TITLE:
				return TITLE_EDEFAULT == null ? getTitle() != null : !TITLE_EDEFAULT.equals(getTitle());
			case CSConfigPackage.DOCUMENT_ROOT__TODOCOMMENTS:
				return getTodocomments() != null;
			case CSConfigPackage.DOCUMENT_ROOT__TRAPCLEANUP:
				return getTrapcleanup() != null;
			case CSConfigPackage.DOCUMENT_ROOT__TRAPELEAVE:
				return getTrapeleave() != null;
			case CSConfigPackage.DOCUMENT_ROOT__TRAPRUNL:
				return getTraprunl() != null;
			case CSConfigPackage.DOCUMENT_ROOT__TRSPASSING:
				return getTrspassing() != null;
			case CSConfigPackage.DOCUMENT_ROOT__UIDS:
				return getUids() != null;
			case CSConfigPackage.DOCUMENT_ROOT__UNCOMPRESSEDAIF:
				return getUncompressedaif() != null;
			case CSConfigPackage.DOCUMENT_ROOT__UNCOMPRESSEDBMP:
				return getUncompressedbmp() != null;
			case CSConfigPackage.DOCUMENT_ROOT__UNICODESOURCE:
				return getUnicodesource() != null;
			case CSConfigPackage.DOCUMENT_ROOT__USERAFTER:
				return getUserafter() != null;
			case CSConfigPackage.DOCUMENT_ROOT__USERFREE:
				return getUserfree() != null;
			case CSConfigPackage.DOCUMENT_ROOT__USER_WAIT_FOR_REQUEST:
				return getUserWaitForRequest() != null;
			case CSConfigPackage.DOCUMENT_ROOT__VARIABLENAMES:
				return getVariablenames() != null;
			case CSConfigPackage.DOCUMENT_ROOT__VOIDPARAMETER:
				return getVoidparameter() != null;
			case CSConfigPackage.DOCUMENT_ROOT__WORDS_RE:
				return WORDS_RE_EDEFAULT == null ? getWordsRE() != null : !WORDS_RE_EDEFAULT.equals(getWordsRE());
			case CSConfigPackage.DOCUMENT_ROOT__WORRYINGCOMMENTS:
				return getWorryingcomments() != null;
			case CSConfigPackage.DOCUMENT_ROOT__WORRY_RE:
				return WORRY_RE_EDEFAULT == null ? getWorryRE() != null : !WORRY_RE_EDEFAULT.equals(getWorryRE());
			case CSConfigPackage.DOCUMENT_ROOT__CATEGORY:
				return isSetCategory();
			case CSConfigPackage.DOCUMENT_ROOT__SEVERITY1:
				return isSetSeverity1();
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
		result.append(", category: ");
		if (categoryESet) result.append(category); else result.append("<unset>");
		result.append(", severity1: ");
		if (severity1ESet) result.append(severity1); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl