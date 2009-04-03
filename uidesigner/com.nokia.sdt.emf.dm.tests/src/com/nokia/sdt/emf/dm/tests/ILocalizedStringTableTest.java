/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.tests;

import com.nokia.sdt.emf.dm.DmFactory;
import com.nokia.sdt.emf.dm.ILocalizedStringTable;

import com.nokia.sdt.emf.dm.*;

import org.eclipse.emf.common.util.EMap;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>ILocalized String Table</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ILocalizedStringTableTest extends TestCase {
	/**
	 * The fixture for this ILocalized String Table test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ILocalizedStringTable fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ILocalizedStringTableTest.class);
	}

	/**
	 * Constructs a new ILocalized String Table test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ILocalizedStringTableTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this ILocalized String Table test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ILocalizedStringTable fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this ILocalized String Table test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ILocalizedStringTable getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(DmFactory.eINSTANCE.createILocalizedStringTable());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	public void testLanguage() {
		assertNull(fixture.getLanguage());
		Language lang = new Language(Language.LANG_American);
		fixture.setLanguage(lang);
		assertEquals(lang, fixture.getLanguage());
	}
	
	public void testStrings() {
		EMap map = fixture.getStrings();
		assertTrue(map.size() == 0);
		
		map.put("abc", "def");
		assertTrue(map.containsKey("abc"));
		assertTrue(map.size() == 1);
		assertEquals("def", map.get("abc"));
		
		map.removeKey("abc");
		assertTrue(map.size()==0);
		
		try {
			map.add("a"); // can only add map entry
			fail();
		}
		catch (Exception x) {
		}
		
		try {
			map.put("abc", new Integer(0)); // can only put String
			fail();
		}
		catch (Exception x) {
		}
	}

} //ILocalizedStringTableTest
