/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.test.srcmapping;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.sourcemapping.SourceMappingAdapterXML;
import com.nokia.sdt.emf.component.*;

/**
 * mapResourceXXX elements need to have unique ids, after default ids
 * are assigned to mapResourceMember and mapResourceElement elements. 
 * 
 *
 */
public class SrcMappingTestRsrcIds extends SrcMappingBase {
	public void testAuto() throws Exception {
		IComponent component = set.lookupComponent("com.nokia.examples.srcmapRsrcIdsAuto");
		getSourceMapping(component);
		checkNoMessages();

		// first unnamed has no id
		MapResourceType type = SourceMappingAdapterXML.getPrimaryMapResourceType(component);
		assertNull(type.getId());

		// auto set
		MapResourceMemberType mrm = (MapResourceMemberType) type.getMapResourceMember().get(0);
		assertEquals("member$"+mrm.getMember(), mrm.getId());
		
		// also auto set
		MapArrayMemberType mam = (MapArrayMemberType) type.getMapArrayMember().get(0);
		MapResourceElementType mre = (MapResourceElementType) mam.getTwoWayMapping();
		assertEquals("$element", mre.getId());
	}

	public void testExplicit() throws Exception {
		IComponent component = set.lookupComponent("com.nokia.examples.srcmapRsrcIdsExplicit");
		getSourceMapping(component);
		checkNoMessages();
		
		MapResourceType type = SourceMappingAdapterXML.getPrimaryMapResourceType(component);
		assertEquals("foo",type.getId());

		MapResourceMemberType mrm = (MapResourceMemberType) type.getMapResourceMember().get(0);
		assertEquals("bar", mrm.getId());
		
		MapArrayMemberType mam = (MapArrayMemberType) type.getMapArrayMember().get(0);
		MapResourceElementType mre = (MapResourceElementType) mam.getTwoWayMapping();
		assertEquals("baz", mre.getId());
	}

	public void testExplicitMulti() throws Exception {
		IComponent component = set.lookupComponent("com.nokia.examples.srcmapRsrcIdsExplicitMulti");
		getSourceMapping(component);
		checkNoMessages();
		
		MapResourceType type = SourceMappingAdapterXML.getPrimaryMapResourceType(component);
		assertEquals("foo",type.getId());

		MapResourceMemberType mrm = (MapResourceMemberType) type.getMapResourceMember().get(0);
		assertEquals("bar", mrm.getId());

		MapResourceMemberType mrm2 = (MapResourceMemberType) type.getMapResourceMember().get(1);
		assertEquals("goo", mrm2.getId());

		MapArrayMemberType mam = (MapArrayMemberType) type.getMapArrayMember().get(0);
		MapResourceElementType mre = (MapResourceElementType) mam.getTwoWayMapping();
		assertEquals("baz", mre.getId());
	}

}
