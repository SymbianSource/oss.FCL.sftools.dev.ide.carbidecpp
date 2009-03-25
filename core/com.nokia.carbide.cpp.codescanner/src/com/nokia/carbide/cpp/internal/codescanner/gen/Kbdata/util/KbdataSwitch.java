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

package com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.util;

import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.*;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage
 * @generated
 */
public class KbdataSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static KbdataPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataSwitch() {
		if (modelPackage == null) {
			modelPackage = KbdataPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case KbdataPackage.ABSTRACT_TYPE: {
				AbstractType abstractType = (AbstractType)theEObject;
				T result = caseAbstractType(abstractType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.ALT_TYPE: {
				AltType altType = (AltType)theEObject;
				T result = caseAltType(altType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.ANY: {
				Any any = (Any)theEObject;
				T result = caseAny(any);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.APINAME_TYPE: {
				ApinameType apinameType = (ApinameType)theEObject;
				T result = caseApinameType(apinameType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.AREA_TYPE: {
				AreaType areaType = (AreaType)theEObject;
				T result = caseAreaType(areaType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.AUDIENCE_TYPE: {
				AudienceType audienceType = (AudienceType)theEObject;
				T result = caseAudienceType(audienceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.AUTHOR_TYPE: {
				AuthorType authorType = (AuthorType)theEObject;
				T result = caseAuthorType(authorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.BODY_TYPE: {
				BodyType bodyType = (BodyType)theEObject;
				T result = caseBodyType(bodyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.BOOLEAN_TYPE: {
				BooleanType booleanType = (BooleanType)theEObject;
				T result = caseBooleanType(booleanType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.BRAND_TYPE: {
				BrandType brandType = (BrandType)theEObject;
				T result = caseBrandType(brandType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.BTYPE: {
				BType bType = (BType)theEObject;
				T result = caseBType(bType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.CALL_TYPE: {
				CallType callType = (CallType)theEObject;
				T result = caseCallType(callType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.CATEGORY_TYPE: {
				CategoryType categoryType = (CategoryType)theEObject;
				T result = caseCategoryType(categoryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.CITE_TYPE: {
				CiteType citeType = (CiteType)theEObject;
				T result = caseCiteType(citeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.CLASS_TYPE: {
				ClassType classType = (ClassType)theEObject;
				T result = caseClassType(classType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.CMDNAME_TYPE: {
				CmdnameType cmdnameType = (CmdnameType)theEObject;
				T result = caseCmdnameType(cmdnameType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.CODEBLOCK_TYPE: {
				CodeblockType codeblockType = (CodeblockType)theEObject;
				T result = caseCodeblockType(codeblockType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.CODEPH_TYPE: {
				CodephType codephType = (CodephType)theEObject;
				T result = caseCodephType(codephType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.COLSPEC_TYPE: {
				ColspecType colspecType = (ColspecType)theEObject;
				T result = caseColspecType(colspecType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.COMMENT_TYPE: {
				CommentType commentType = (CommentType)theEObject;
				T result = caseCommentType(commentType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.COMPONENT_TYPE: {
				ComponentType componentType = (ComponentType)theEObject;
				T result = caseComponentType(componentType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.COORDS_TYPE: {
				CoordsType coordsType = (CoordsType)theEObject;
				T result = caseCoordsType(coordsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.COPYRHOLDER_TYPE: {
				CopyrholderType copyrholderType = (CopyrholderType)theEObject;
				T result = caseCopyrholderType(copyrholderType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.COPYRIGHT_TYPE: {
				CopyrightType copyrightType = (CopyrightType)theEObject;
				T result = caseCopyrightType(copyrightType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.COPYRYEAR_TYPE: {
				CopyryearType copyryearType = (CopyryearType)theEObject;
				T result = caseCopyryearType(copyryearType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.CREATED_TYPE: {
				CreatedType createdType = (CreatedType)theEObject;
				T result = caseCreatedType(createdType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.CRITDATES_TYPE: {
				CritdatesType critdatesType = (CritdatesType)theEObject;
				T result = caseCritdatesType(critdatesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DATA_ABOUT_TYPE: {
				DataAboutType dataAboutType = (DataAboutType)theEObject;
				T result = caseDataAboutType(dataAboutType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DATA_TYPE: {
				DataType dataType = (DataType)theEObject;
				T result = caseDataType(dataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DDHD_TYPE: {
				DdhdType ddhdType = (DdhdType)theEObject;
				T result = caseDdhdType(ddhdType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DD_TYPE: {
				DdType ddType = (DdType)theEObject;
				T result = caseDdType(ddType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DELIM_TYPE: {
				DelimType delimType = (DelimType)theEObject;
				T result = caseDelimType(delimType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DESC_TYPE: {
				DescType descType = (DescType)theEObject;
				T result = caseDescType(descType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DEVICE_TYPE: {
				DeviceType deviceType = (DeviceType)theEObject;
				T result = caseDeviceType(deviceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DLENTRY_TYPE: {
				DlentryType dlentryType = (DlentryType)theEObject;
				T result = caseDlentryType(dlentryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DLHEAD_TYPE: {
				DlheadType dlheadType = (DlheadType)theEObject;
				T result = caseDlheadType(dlheadType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DL_TYPE: {
				DlType dlType = (DlType)theEObject;
				T result = caseDlType(dlType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DRAFT_COMMENT_TYPE: {
				DraftCommentType draftCommentType = (DraftCommentType)theEObject;
				T result = caseDraftCommentType(draftCommentType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DTHD_TYPE: {
				DthdType dthdType = (DthdType)theEObject;
				T result = caseDthdType(dthdType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.DT_TYPE: {
				DtType dtType = (DtType)theEObject;
				T result = caseDtType(dtType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.ENTRY_TYPE: {
				EntryType entryType = (EntryType)theEObject;
				T result = caseEntryType(entryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.EXAMPLE_TYPE: {
				ExampleType exampleType = (ExampleType)theEObject;
				T result = caseExampleType(exampleType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.FEATNUM_TYPE: {
				FeatnumType featnumType = (FeatnumType)theEObject;
				T result = caseFeatnumType(featnumType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.FIGGROUP_TYPE: {
				FiggroupType figgroupType = (FiggroupType)theEObject;
				T result = caseFiggroupType(figgroupType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.FIG_TYPE: {
				FigType figType = (FigType)theEObject;
				T result = caseFigType(figType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.FILEPATH_TYPE: {
				FilepathType filepathType = (FilepathType)theEObject;
				T result = caseFilepathType(filepathType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.FILETYPE_TYPE: {
				FiletypeType filetypeType = (FiletypeType)theEObject;
				T result = caseFiletypeType(filetypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.FN_TYPE: {
				FnType fnType = (FnType)theEObject;
				T result = caseFnType(fnType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.FOREIGN_TYPE: {
				ForeignType foreignType = (ForeignType)theEObject;
				T result = caseForeignType(foreignType);
				if (result == null) result = caseAny(foreignType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.FRAGMENT_TYPE: {
				FragmentType fragmentType = (FragmentType)theEObject;
				T result = caseFragmentType(fragmentType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.FRAGREF_TYPE: {
				FragrefType fragrefType = (FragrefType)theEObject;
				T result = caseFragrefType(fragrefType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.GROUPCHOICE_TYPE: {
				GroupchoiceType groupchoiceType = (GroupchoiceType)theEObject;
				T result = caseGroupchoiceType(groupchoiceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.GROUPCOMP_TYPE: {
				GroupcompType groupcompType = (GroupcompType)theEObject;
				T result = caseGroupcompType(groupcompType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.GROUPSEQ_TYPE: {
				GroupseqType groupseqType = (GroupseqType)theEObject;
				T result = caseGroupseqType(groupseqType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.IMAGEMAP_TYPE: {
				ImagemapType imagemapType = (ImagemapType)theEObject;
				T result = caseImagemapType(imagemapType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.IMAGE_TYPE: {
				ImageType imageType = (ImageType)theEObject;
				T result = caseImageType(imageType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.INDEX_BASE_TYPE: {
				IndexBaseType indexBaseType = (IndexBaseType)theEObject;
				T result = caseIndexBaseType(indexBaseType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.INDEX_SEE_ALSO_TYPE: {
				IndexSeeAlsoType indexSeeAlsoType = (IndexSeeAlsoType)theEObject;
				T result = caseIndexSeeAlsoType(indexSeeAlsoType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.INDEX_SEE_TYPE: {
				IndexSeeType indexSeeType = (IndexSeeType)theEObject;
				T result = caseIndexSeeType(indexSeeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.INDEX_SORT_AS_TYPE: {
				IndexSortAsType indexSortAsType = (IndexSortAsType)theEObject;
				T result = caseIndexSortAsType(indexSortAsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.INDEXTERMREF_TYPE: {
				IndextermrefType indextermrefType = (IndextermrefType)theEObject;
				T result = caseIndextermrefType(indextermrefType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.INDEXTERM_TYPE: {
				IndextermType indextermType = (IndextermType)theEObject;
				T result = caseIndextermType(indextermType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.INHERITANCE_TYPE: {
				InheritanceType inheritanceType = (InheritanceType)theEObject;
				T result = caseInheritanceType(inheritanceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.ITEMGROUP_TYPE: {
				ItemgroupType itemgroupType = (ItemgroupType)theEObject;
				T result = caseItemgroupType(itemgroupType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.ITYPE: {
				IType iType = (IType)theEObject;
				T result = caseIType(iType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KBDATA_BODY_TYPE: {
				KbdataBodyType kbdataBodyType = (KbdataBodyType)theEObject;
				T result = caseKbdataBodyType(kbdataBodyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KBDATA_CATEGORY_TYPE: {
				KbdataCategoryType kbdataCategoryType = (KbdataCategoryType)theEObject;
				T result = caseKbdataCategoryType(kbdataCategoryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KBDATA_ID_TYPE: {
				KbdataIdType kbdataIdType = (KbdataIdType)theEObject;
				T result = caseKbdataIdType(kbdataIdType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KBDATA_KEYWORDS: {
				KbdataKeywords kbdataKeywords = (KbdataKeywords)theEObject;
				T result = caseKbdataKeywords(kbdataKeywords);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KBDATA_KEYWORDS_TYPE: {
				KbdataKeywordsType kbdataKeywordsType = (KbdataKeywordsType)theEObject;
				T result = caseKbdataKeywordsType(kbdataKeywordsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KBDATA_KEYWORD_TYPE: {
				KbdataKeywordType kbdataKeywordType = (KbdataKeywordType)theEObject;
				T result = caseKbdataKeywordType(kbdataKeywordType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KBDATA_METADATA_TYPE: {
				KbdataMetadataType kbdataMetadataType = (KbdataMetadataType)theEObject;
				T result = caseKbdataMetadataType(kbdataMetadataType);
				if (result == null) result = caseKbdataKeywords(kbdataMetadataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KBDATA_PLATFORM_TYPE: {
				KbdataPlatformType kbdataPlatformType = (KbdataPlatformType)theEObject;
				T result = caseKbdataPlatformType(kbdataPlatformType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KBDATA_REF_TYPE: {
				KbdataRefType kbdataRefType = (KbdataRefType)theEObject;
				T result = caseKbdataRefType(kbdataRefType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KBDATA_TYPE: {
				KbdataType kbdataType = (KbdataType)theEObject;
				T result = caseKbdataType(kbdataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KEYWORDS_TYPE: {
				KeywordsType keywordsType = (KeywordsType)theEObject;
				T result = caseKeywordsType(keywordsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KEYWORD_TYPE: {
				KeywordType keywordType = (KeywordType)theEObject;
				T result = caseKeywordType(keywordType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.KWD_TYPE: {
				KwdType kwdType = (KwdType)theEObject;
				T result = caseKwdType(kwdType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.LINES_TYPE: {
				LinesType linesType = (LinesType)theEObject;
				T result = caseLinesType(linesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.LINKINFO_TYPE: {
				LinkinfoType linkinfoType = (LinkinfoType)theEObject;
				T result = caseLinkinfoType(linkinfoType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.LINKLIST_TYPE: {
				LinklistType linklistType = (LinklistType)theEObject;
				T result = caseLinklistType(linklistType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.LINKPOOL_TYPE: {
				LinkpoolType linkpoolType = (LinkpoolType)theEObject;
				T result = caseLinkpoolType(linkpoolType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.LINKTEXT_TYPE: {
				LinktextType linktextType = (LinktextType)theEObject;
				T result = caseLinktextType(linktextType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.LINK_TYPE: {
				LinkType linkType = (LinkType)theEObject;
				T result = caseLinkType(linkType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.LI_TYPE: {
				LiType liType = (LiType)theEObject;
				T result = caseLiType(liType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.LOCAL_TYPE: {
				LocalType localType = (LocalType)theEObject;
				T result = caseLocalType(localType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.LQ_TYPE: {
				LqType lqType = (LqType)theEObject;
				T result = caseLqType(lqType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.MACRO_TYPE: {
				MacroType macroType = (MacroType)theEObject;
				T result = caseMacroType(macroType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.MEMBER_TYPE: {
				MemberType memberType = (MemberType)theEObject;
				T result = caseMemberType(memberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.MENUCASCADE_TYPE: {
				MenucascadeType menucascadeType = (MenucascadeType)theEObject;
				T result = caseMenucascadeType(menucascadeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.MESSAGES_TYPE: {
				MessagesType messagesType = (MessagesType)theEObject;
				T result = caseMessagesType(messagesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.METADATA_TYPE: {
				MetadataType metadataType = (MetadataType)theEObject;
				T result = caseMetadataType(metadataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.METHOD_TYPE: {
				MethodType methodType = (MethodType)theEObject;
				T result = caseMethodType(methodType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.MSGBLOCK_TYPE: {
				MsgblockType msgblockType = (MsgblockType)theEObject;
				T result = caseMsgblockType(msgblockType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.MSGNUM_TYPE: {
				MsgnumType msgnumType = (MsgnumType)theEObject;
				T result = caseMsgnumType(msgnumType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.MSGPH_TYPE: {
				MsgphType msgphType = (MsgphType)theEObject;
				T result = caseMsgphType(msgphType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.NAVTITLE_TYPE: {
				NavtitleType navtitleType = (NavtitleType)theEObject;
				T result = caseNavtitleType(navtitleType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.NOTE_TYPE: {
				NoteType noteType = (NoteType)theEObject;
				T result = caseNoteType(noteType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.NO_TOPIC_NESTING_TYPE: {
				NoTopicNestingType noTopicNestingType = (NoTopicNestingType)theEObject;
				T result = caseNoTopicNestingType(noTopicNestingType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.OBJECT_TYPE: {
				ObjectType objectType = (ObjectType)theEObject;
				T result = caseObjectType(objectType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.OL_TYPE: {
				OlType olType = (OlType)theEObject;
				T result = caseOlType(olType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.OPER_TYPE: {
				OperType operType = (OperType)theEObject;
				T result = caseOperType(operType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.OPTION_TYPE: {
				OptionType optionType = (OptionType)theEObject;
				T result = caseOptionType(optionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.OTHERMETA_TYPE: {
				OthermetaType othermetaType = (OthermetaType)theEObject;
				T result = caseOthermetaType(othermetaType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PARAMETER_TYPE: {
				ParameterType parameterType = (ParameterType)theEObject;
				T result = caseParameterType(parameterType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PARAM_TYPE: {
				ParamType paramType = (ParamType)theEObject;
				T result = caseParamType(paramType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PARML_TYPE: {
				ParmlType parmlType = (ParmlType)theEObject;
				T result = caseParmlType(parmlType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PARMNAME_TYPE: {
				ParmnameType parmnameType = (ParmnameType)theEObject;
				T result = caseParmnameType(parmnameType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PD_TYPE: {
				PdType pdType = (PdType)theEObject;
				T result = casePdType(pdType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PERMISSIONS_TYPE: {
				PermissionsType permissionsType = (PermissionsType)theEObject;
				T result = casePermissionsType(permissionsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PH_TYPE: {
				PhType phType = (PhType)theEObject;
				T result = casePhType(phType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PLATFORM_TYPE: {
				PlatformType platformType = (PlatformType)theEObject;
				T result = casePlatformType(platformType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PLENTRY_TYPE: {
				PlentryType plentryType = (PlentryType)theEObject;
				T result = casePlentryType(plentryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PRE_TYPE: {
				PreType preType = (PreType)theEObject;
				T result = casePreType(preType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PRODINFO_TYPE: {
				ProdinfoType prodinfoType = (ProdinfoType)theEObject;
				T result = caseProdinfoType(prodinfoType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PRODNAME_TYPE: {
				ProdnameType prodnameType = (ProdnameType)theEObject;
				T result = caseProdnameType(prodnameType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PROGNUM_TYPE: {
				PrognumType prognumType = (PrognumType)theEObject;
				T result = casePrognumType(prognumType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PROLOG_TYPE: {
				PrologType prologType = (PrologType)theEObject;
				T result = casePrologType(prologType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PROPDESCHD_TYPE: {
				PropdeschdType propdeschdType = (PropdeschdType)theEObject;
				T result = casePropdeschdType(propdeschdType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PROPDESC_TYPE: {
				PropdescType propdescType = (PropdescType)theEObject;
				T result = casePropdescType(propdescType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PROPERTIES_TYPE: {
				PropertiesType propertiesType = (PropertiesType)theEObject;
				T result = casePropertiesType(propertiesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PROPERTY_TYPE: {
				PropertyType propertyType = (PropertyType)theEObject;
				T result = casePropertyType(propertyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PROPHEAD_TYPE: {
				PropheadType propheadType = (PropheadType)theEObject;
				T result = casePropheadType(propheadType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PROPTYPEHD_TYPE: {
				ProptypehdType proptypehdType = (ProptypehdType)theEObject;
				T result = caseProptypehdType(proptypehdType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PROPTYPE_TYPE: {
				ProptypeType proptypeType = (ProptypeType)theEObject;
				T result = caseProptypeType(proptypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PROPVALUEHD_TYPE: {
				PropvaluehdType propvaluehdType = (PropvaluehdType)theEObject;
				T result = casePropvaluehdType(propvaluehdType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PROPVALUE_TYPE: {
				PropvalueType propvalueType = (PropvalueType)theEObject;
				T result = casePropvalueType(propvalueType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PT_TYPE: {
				PtType ptType = (PtType)theEObject;
				T result = casePtType(ptType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PTYPE: {
				PType pType = (PType)theEObject;
				T result = casePType(pType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.PUBLISHER_TYPE: {
				PublisherType publisherType = (PublisherType)theEObject;
				T result = casePublisherType(publisherType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.QTYPE: {
				QType qType = (QType)theEObject;
				T result = caseQType(qType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.REFBODY_TYPE: {
				RefbodyType refbodyType = (RefbodyType)theEObject;
				T result = caseRefbodyType(refbodyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.REFERENCE_TYPE: {
				ReferenceType referenceType = (ReferenceType)theEObject;
				T result = caseReferenceType(referenceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.REFSYN_TYPE: {
				RefsynType refsynType = (RefsynType)theEObject;
				T result = caseRefsynType(refsynType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.RELATED_LINKS_TYPE: {
				RelatedLinksType relatedLinksType = (RelatedLinksType)theEObject;
				T result = caseRelatedLinksType(relatedLinksType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.REPSEP_TYPE: {
				RepsepType repsepType = (RepsepType)theEObject;
				T result = caseRepsepType(repsepType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.REQUIRED_CLEANUP_TYPE: {
				RequiredCleanupType requiredCleanupType = (RequiredCleanupType)theEObject;
				T result = caseRequiredCleanupType(requiredCleanupType);
				if (result == null) result = caseAny(requiredCleanupType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.RESOURCEID_TYPE: {
				ResourceidType resourceidType = (ResourceidType)theEObject;
				T result = caseResourceidType(resourceidType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.REVISED_TYPE: {
				RevisedType revisedType = (RevisedType)theEObject;
				T result = caseRevisedType(revisedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.ROW_TYPE: {
				RowType rowType = (RowType)theEObject;
				T result = caseRowType(rowType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SCREEN_TYPE: {
				ScreenType screenType = (ScreenType)theEObject;
				T result = caseScreenType(screenType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SEARCHTITLE_TYPE: {
				SearchtitleType searchtitleType = (SearchtitleType)theEObject;
				T result = caseSearchtitleType(searchtitleType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SECTION_TYPE: {
				SectionType sectionType = (SectionType)theEObject;
				T result = caseSectionType(sectionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SEP_TYPE: {
				SepType sepType = (SepType)theEObject;
				T result = caseSepType(sepType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SERIES_TYPE: {
				SeriesType seriesType = (SeriesType)theEObject;
				T result = caseSeriesType(seriesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SHAPE_TYPE: {
				ShapeType shapeType = (ShapeType)theEObject;
				T result = caseShapeType(shapeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SHORTCUT_TYPE: {
				ShortcutType shortcutType = (ShortcutType)theEObject;
				T result = caseShortcutType(shortcutType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SHORTDESC_TYPE: {
				ShortdescType shortdescType = (ShortdescType)theEObject;
				T result = caseShortdescType(shortdescType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SIMPLETABLE_TYPE: {
				SimpletableType simpletableType = (SimpletableType)theEObject;
				T result = caseSimpletableType(simpletableType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SLI_TYPE: {
				SliType sliType = (SliType)theEObject;
				T result = caseSliType(sliType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SL_TYPE: {
				SlType slType = (SlType)theEObject;
				T result = caseSlType(slType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SOLUTION_TYPE: {
				SolutionType solutionType = (SolutionType)theEObject;
				T result = caseSolutionType(solutionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SOURCE_TYPE: {
				SourceType sourceType = (SourceType)theEObject;
				T result = caseSourceType(sourceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.STATE_TYPE: {
				StateType stateType = (StateType)theEObject;
				T result = caseStateType(stateType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.STENTRY_TYPE: {
				StentryType stentryType = (StentryType)theEObject;
				T result = caseStentryType(stentryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.STHEAD_TYPE: {
				StheadType stheadType = (StheadType)theEObject;
				T result = caseStheadType(stheadType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.STROW_TYPE: {
				StrowType strowType = (StrowType)theEObject;
				T result = caseStrowType(strowType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SUB_TYPE: {
				SubType subType = (SubType)theEObject;
				T result = caseSubType(subType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SUP_TYPE: {
				SupType supType = (SupType)theEObject;
				T result = caseSupType(supType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SYMPTOM_TYPE: {
				SymptomType symptomType = (SymptomType)theEObject;
				T result = caseSymptomType(symptomType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SYNBLK_TYPE: {
				SynblkType synblkType = (SynblkType)theEObject;
				T result = caseSynblkType(synblkType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SYNNOTEREF_TYPE: {
				SynnoterefType synnoterefType = (SynnoterefType)theEObject;
				T result = caseSynnoterefType(synnoterefType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SYNNOTE_TYPE: {
				SynnoteType synnoteType = (SynnoteType)theEObject;
				T result = caseSynnoteType(synnoteType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SYNPH_TYPE: {
				SynphType synphType = (SynphType)theEObject;
				T result = caseSynphType(synphType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SYNTAXDIAGRAM_TYPE: {
				SyntaxdiagramType syntaxdiagramType = (SyntaxdiagramType)theEObject;
				T result = caseSyntaxdiagramType(syntaxdiagramType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.SYSTEMOUTPUT_TYPE: {
				SystemoutputType systemoutputType = (SystemoutputType)theEObject;
				T result = caseSystemoutputType(systemoutputType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.TABLE_TYPE: {
				TableType tableType = (TableType)theEObject;
				T result = caseTableType(tableType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.TBODY_TYPE: {
				TbodyType tbodyType = (TbodyType)theEObject;
				T result = caseTbodyType(tbodyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.TERM_TYPE: {
				TermType termType = (TermType)theEObject;
				T result = caseTermType(termType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.TGROUP_TYPE: {
				TgroupType tgroupType = (TgroupType)theEObject;
				T result = caseTgroupType(tgroupType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.THEAD_TYPE: {
				TheadType theadType = (TheadType)theEObject;
				T result = caseTheadType(theadType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.TITLEALTS_TYPE: {
				TitlealtsType titlealtsType = (TitlealtsType)theEObject;
				T result = caseTitlealtsType(titlealtsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.TITLE_TYPE: {
				TitleType titleType = (TitleType)theEObject;
				T result = caseTitleType(titleType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.TM_TYPE: {
				TmType tmType = (TmType)theEObject;
				T result = caseTmType(tmType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.TOPIC_TYPE: {
				TopicType topicType = (TopicType)theEObject;
				T result = caseTopicType(topicType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.TT_TYPE: {
				TtType ttType = (TtType)theEObject;
				T result = caseTtType(ttType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.UICONTROL_TYPE: {
				UicontrolType uicontrolType = (UicontrolType)theEObject;
				T result = caseUicontrolType(uicontrolType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.UL_TYPE: {
				UlType ulType = (UlType)theEObject;
				T result = caseUlType(ulType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.UNKNOWN_TYPE: {
				UnknownType unknownType = (UnknownType)theEObject;
				T result = caseUnknownType(unknownType);
				if (result == null) result = caseAny(unknownType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.USERINPUT_TYPE: {
				UserinputType userinputType = (UserinputType)theEObject;
				T result = caseUserinputType(userinputType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.UTYPE: {
				UType uType = (UType)theEObject;
				T result = caseUType(uType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.VARNAME_TYPE: {
				VarnameType varnameType = (VarnameType)theEObject;
				T result = caseVarnameType(varnameType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.VAR_TYPE: {
				VarType varType = (VarType)theEObject;
				T result = caseVarType(varType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.VRMLIST_TYPE: {
				VrmlistType vrmlistType = (VrmlistType)theEObject;
				T result = caseVrmlistType(vrmlistType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.VRM_TYPE: {
				VrmType vrmType = (VrmType)theEObject;
				T result = caseVrmType(vrmType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.WINTITLE_TYPE: {
				WintitleType wintitleType = (WintitleType)theEObject;
				T result = caseWintitleType(wintitleType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KbdataPackage.XREF_TYPE: {
				XrefType xrefType = (XrefType)theEObject;
				T result = caseXrefType(xrefType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractType(AbstractType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Alt Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Alt Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAltType(AltType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAny(Any object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Apiname Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Apiname Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApinameType(ApinameType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Area Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Area Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAreaType(AreaType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Audience Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Audience Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAudienceType(AudienceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Author Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Author Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthorType(AuthorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Body Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Body Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBodyType(BodyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanType(BooleanType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Brand Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Brand Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBrandType(BrandType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>BType</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>BType</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBType(BType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Call Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Call Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCallType(CallType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Category Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Category Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCategoryType(CategoryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cite Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cite Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCiteType(CiteType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Class Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Class Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClassType(ClassType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cmdname Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cmdname Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCmdnameType(CmdnameType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Codeblock Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Codeblock Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCodeblockType(CodeblockType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Codeph Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Codeph Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCodephType(CodephType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Colspec Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Colspec Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColspecType(ColspecType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comment Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommentType(CommentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Component Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Component Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComponentType(ComponentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Coords Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Coords Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCoordsType(CoordsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Copyrholder Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Copyrholder Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCopyrholderType(CopyrholderType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Copyright Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Copyright Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCopyrightType(CopyrightType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Copyryear Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Copyryear Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCopyryearType(CopyryearType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Created Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Created Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCreatedType(CreatedType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Critdates Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Critdates Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCritdatesType(CritdatesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data About Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data About Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataAboutType(DataAboutType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataType(DataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ddhd Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ddhd Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDdhdType(DdhdType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dd Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dd Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDdType(DdType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Delim Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Delim Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDelimType(DelimType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Desc Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Desc Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDescType(DescType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Device Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Device Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeviceType(DeviceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dlentry Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dlentry Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDlentryType(DlentryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dlhead Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dlhead Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDlheadType(DlheadType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dl Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dl Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDlType(DlType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Draft Comment Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Draft Comment Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDraftCommentType(DraftCommentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dthd Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dthd Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDthdType(DthdType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dt Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dt Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDtType(DtType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entry Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entry Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntryType(EntryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Example Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Example Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExampleType(ExampleType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Featnum Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Featnum Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFeatnumType(FeatnumType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Figgroup Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Figgroup Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFiggroupType(FiggroupType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Fig Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Fig Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFigType(FigType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Filepath Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Filepath Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFilepathType(FilepathType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Filetype Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Filetype Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFiletypeType(FiletypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Fn Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Fn Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFnType(FnType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Foreign Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Foreign Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseForeignType(ForeignType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Fragment Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Fragment Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFragmentType(FragmentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Fragref Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Fragref Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFragrefType(FragrefType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Groupchoice Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Groupchoice Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGroupchoiceType(GroupchoiceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Groupcomp Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Groupcomp Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGroupcompType(GroupcompType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Groupseq Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Groupseq Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGroupseqType(GroupseqType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Imagemap Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Imagemap Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImagemapType(ImagemapType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Image Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Image Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImageType(ImageType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Index Base Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Index Base Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndexBaseType(IndexBaseType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Index See Also Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Index See Also Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndexSeeAlsoType(IndexSeeAlsoType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Index See Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Index See Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndexSeeType(IndexSeeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Index Sort As Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Index Sort As Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndexSortAsType(IndexSortAsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Indextermref Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Indextermref Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndextermrefType(IndextermrefType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Indexterm Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Indexterm Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndextermType(IndextermType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inheritance Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inheritance Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInheritanceType(InheritanceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Itemgroup Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Itemgroup Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseItemgroupType(ItemgroupType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IType</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IType</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIType(IType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Body Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Body Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKbdataBodyType(KbdataBodyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Category Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Category Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKbdataCategoryType(KbdataCategoryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Id Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Id Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKbdataIdType(KbdataIdType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Keywords</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Keywords</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKbdataKeywords(KbdataKeywords object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Keywords Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Keywords Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKbdataKeywordsType(KbdataKeywordsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Keyword Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Keyword Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKbdataKeywordType(KbdataKeywordType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metadata Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metadata Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKbdataMetadataType(KbdataMetadataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Platform Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Platform Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKbdataPlatformType(KbdataPlatformType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ref Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ref Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKbdataRefType(KbdataRefType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKbdataType(KbdataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Keywords Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Keywords Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKeywordsType(KeywordsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Keyword Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Keyword Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKeywordType(KeywordType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Kwd Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Kwd Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKwdType(KwdType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Lines Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Lines Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLinesType(LinesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Linkinfo Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Linkinfo Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLinkinfoType(LinkinfoType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Linklist Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Linklist Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLinklistType(LinklistType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Linkpool Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Linkpool Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLinkpoolType(LinkpoolType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Linktext Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Linktext Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLinktextType(LinktextType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Link Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Link Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLinkType(LinkType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Li Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Li Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiType(LiType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Local Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Local Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalType(LocalType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Lq Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Lq Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLqType(LqType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Macro Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Macro Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMacroType(MacroType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Member Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Member Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMemberType(MemberType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Menucascade Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Menucascade Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMenucascadeType(MenucascadeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Messages Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Messages Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMessagesType(MessagesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metadata Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metadata Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetadataType(MetadataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Method Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Method Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMethodType(MethodType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Msgblock Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Msgblock Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMsgblockType(MsgblockType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Msgnum Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Msgnum Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMsgnumType(MsgnumType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Msgph Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Msgph Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMsgphType(MsgphType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Navtitle Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Navtitle Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNavtitleType(NavtitleType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Note Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Note Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNoteType(NoteType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>No Topic Nesting Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>No Topic Nesting Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNoTopicNestingType(NoTopicNestingType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectType(ObjectType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ol Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ol Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOlType(OlType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Oper Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Oper Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperType(OperType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Option Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Option Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOptionType(OptionType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Othermeta Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Othermeta Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOthermetaType(OthermetaType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterType(ParameterType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Param Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Param Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParamType(ParamType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parml Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parml Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParmlType(ParmlType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parmname Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parmname Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParmnameType(ParmnameType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pd Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pd Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePdType(PdType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Permissions Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Permissions Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePermissionsType(PermissionsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ph Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ph Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePhType(PhType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Platform Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Platform Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlatformType(PlatformType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Plentry Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Plentry Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlentryType(PlentryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pre Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pre Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePreType(PreType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Prodinfo Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Prodinfo Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProdinfoType(ProdinfoType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Prodname Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Prodname Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProdnameType(ProdnameType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Prognum Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Prognum Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrognumType(PrognumType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Prolog Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Prolog Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrologType(PrologType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Propdeschd Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Propdeschd Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropdeschdType(PropdeschdType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Propdesc Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Propdesc Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropdescType(PropdescType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Properties Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Properties Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertiesType(PropertiesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyType(PropertyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Prophead Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Prophead Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropheadType(PropheadType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proptypehd Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proptypehd Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProptypehdType(ProptypehdType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proptype Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proptype Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProptypeType(ProptypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Propvaluehd Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Propvaluehd Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropvaluehdType(PropvaluehdType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Propvalue Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Propvalue Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropvalueType(PropvalueType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pt Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pt Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePtType(PtType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>PType</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>PType</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePType(PType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Publisher Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Publisher Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePublisherType(PublisherType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>QType</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>QType</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQType(QType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Refbody Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Refbody Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRefbodyType(RefbodyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reference Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reference Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferenceType(ReferenceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Refsyn Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Refsyn Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRefsynType(RefsynType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Related Links Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Related Links Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRelatedLinksType(RelatedLinksType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Repsep Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Repsep Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRepsepType(RepsepType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Required Cleanup Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Required Cleanup Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRequiredCleanupType(RequiredCleanupType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resourceid Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resourceid Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceidType(ResourceidType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Revised Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Revised Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRevisedType(RevisedType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Row Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Row Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRowType(RowType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Screen Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Screen Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseScreenType(ScreenType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Searchtitle Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Searchtitle Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSearchtitleType(SearchtitleType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Section Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Section Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSectionType(SectionType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sep Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sep Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSepType(SepType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Series Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Series Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSeriesType(SeriesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shape Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shape Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseShapeType(ShapeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shortcut Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shortcut Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseShortcutType(ShortcutType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shortdesc Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shortdesc Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseShortdescType(ShortdescType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simpletable Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simpletable Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpletableType(SimpletableType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sli Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sli Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSliType(SliType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sl Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sl Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSlType(SlType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Solution Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Solution Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSolutionType(SolutionType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Source Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Source Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSourceType(SourceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateType(StateType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stentry Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stentry Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStentryType(StentryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sthead Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sthead Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStheadType(StheadType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Strow Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Strow Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStrowType(StrowType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubType(SubType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sup Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sup Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSupType(SupType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Symptom Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Symptom Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSymptomType(SymptomType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Synblk Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Synblk Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSynblkType(SynblkType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Synnoteref Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Synnoteref Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSynnoterefType(SynnoterefType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Synnote Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Synnote Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSynnoteType(SynnoteType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Synph Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Synph Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSynphType(SynphType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Syntaxdiagram Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Syntaxdiagram Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSyntaxdiagramType(SyntaxdiagramType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Systemoutput Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Systemoutput Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSystemoutputType(SystemoutputType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableType(TableType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tbody Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tbody Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTbodyType(TbodyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Term Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Term Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTermType(TermType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tgroup Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tgroup Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTgroupType(TgroupType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Thead Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Thead Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTheadType(TheadType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Titlealts Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Titlealts Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTitlealtsType(TitlealtsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Title Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Title Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTitleType(TitleType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tm Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tm Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTmType(TmType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Topic Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Topic Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTopicType(TopicType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tt Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tt Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTtType(TtType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Uicontrol Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Uicontrol Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUicontrolType(UicontrolType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ul Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ul Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUlType(UlType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unknown Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unknown Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnknownType(UnknownType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Userinput Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Userinput Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUserinputType(UserinputType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UType</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UType</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUType(UType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Varname Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Varname Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVarnameType(VarnameType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Var Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Var Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVarType(VarType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Vrmlist Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Vrmlist Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVrmlistType(VrmlistType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Vrm Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Vrm Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVrmType(VrmType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Wintitle Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Wintitle Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWintitleType(WintitleType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xref Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xref Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXrefType(XrefType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //KbdataSwitch
