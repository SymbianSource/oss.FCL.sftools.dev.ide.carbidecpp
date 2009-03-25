/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.tests;

import com.nokia.sdt.emf.dm.DmFactory;
import com.nokia.sdt.emf.dm.ILocalizedStringBundle;

import com.nokia.sdt.emf.dm.*;

import org.eclipse.emf.common.util.EList;

import java.util.Map;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>ILocalized String Bundle</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ILocalizedStringBundleTest extends TestCase {
	
	static final String UNTRANSLATED_PREFIX = "??";
	/**
	 * The fixture for this ILocalized String Bundle test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ILocalizedStringBundle fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ILocalizedStringBundleTest.class);
	}

	/**
	 * Constructs a new ILocalized String Bundle test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ILocalizedStringBundleTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this ILocalized String Bundle test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ILocalizedStringBundle fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this ILocalized String Bundle test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ILocalizedStringBundle getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	protected void setUp() throws Exception {
		ILocalizedStringBundle bundle = DmFactory.eINSTANCE.createILocalizedStringBundle();
		bundle.setKeyProvider(new IStringKeyProvider() {
			int sequence = 1;
			public String assignLocalizedStringKey() {
				return "STR_" + sequence++;
			}
			public String assignMacroStringKey() {
				return "STR_" + sequence++;
			}
            /* (non-Javadoc)
             * @see com.nokia.sdt.emf.dm.IStringKeyProvider#compareKeys(java.lang.String, java.lang.String)
             */
            public int compareKeys(String key, String otherKey) {
                return key.compareTo(otherKey);
            }
		});
		setFixture(bundle);
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

	public void testAddLocalizedStringDefault() {
		// one language case
		StringValue sv = fixture.addLocalizedStringDefault("abc");
		assertNotNull(sv);
		assertTrue(sv.isLocalized());
		assertNotNull(sv.getValue());
		assertTrue(sv.getType() == StringValue.LOCALIZED);
		
		assertEquals("abc", fixture.getLocalizedStringDefault(sv.getValue()));
		Map values = fixture.findStrings(sv.getValue());
		assertNotNull(values);
		assertTrue(values.size() == 1);
		assertEquals("abc", values.get(fixture.getDefaultLanguage()));
		
		// two languages
		Language lang = new Language(Language.LANG_Spanish);
		fixture.addLocalizedStringTable(lang);
		
		StringValue sv2 = fixture.addLocalizedStringDefault("zzz");
		values = fixture.findStrings(sv2.getValue());
		assertNotNull(values);
		assertTrue(values.size() == 2);
		assertEquals("zzz", values.get(fixture.getDefaultLanguage()));
		assertEquals("??zzz", values.get(lang));
		}

	public void testAddLocalizedStringTable() {
		
		StringValue sv = fixture.addLocalizedStringDefault("abc");
		
		Language lang = new Language(Language.LANG_Spanish);
		fixture.addLocalizedStringTable(lang);
		
		EList tables = fixture.getLocalizedStringTables();
		assertNotNull(tables);
		assertTrue(tables.size()==2);
		
		Map values = fixture.findStrings(sv.getValue());
		assertEquals("abc", values.get(fixture.getDefaultLanguage()));
		String newLangValue = (String) values.get(lang);
		assertNotNull(newLangValue);
		assertTrue(newLangValue.startsWith(UNTRANSLATED_PREFIX));
		assertNotNull(null, values.get(lang));
	}
	
	public void testFindLocalizedStringTable() {
		Language lang = new Language(Language.LANG_Spanish);
		fixture.addLocalizedStringTable(lang);

		ILocalizedStringTable table = fixture.findLocalizedStringTable(lang);
		assertNotNull(table);
		assertEquals(lang, table.getLanguage());		
	}
	
	public void testFindString() {
		StringValue sv = fixture.addLocalizedStringDefault("foo");
		String s = fixture.findString(fixture.getDefaultLanguage(), sv.getValue());
		assertEquals("foo", s);
		
		fixture.removeLocalizedStringAllLanguages(sv.getValue());
		s = fixture.findString(fixture.getDefaultLanguage(), sv.getValue());
		assertNull(s);
	}

	public void testGetLocalizedStringDefault() {
		StringValue sv = fixture.addLocalizedStringDefault("abc");
		String value = fixture.getLocalizedStringDefault(sv.getValue());
		assertEquals("abc", value);
	}

	public void testHasStringKey() {
		StringValue sv = fixture.addLocalizedStringDefault("abc");
		assertTrue(fixture.hasStringKey(sv.getValue()));
		assertFalse(fixture.hasStringKey("missing"));
		
		fixture.removeLocalizedStringAllLanguages(sv.getValue());
		assertFalse(fixture.hasStringKey(sv.getValue()));
	}

	public void testRegisterString() {
		Language lang1 = new Language(Language.LANG_Australian);
		
		assertNull(fixture.findLocalizedStringTable(lang1));
		StringValue sv = fixture.registerString(lang1, "aaa", "bbb");
	
		ILocalizedStringTable table = fixture.findLocalizedStringTable(lang1);
		assertNotNull(table);
		assertEquals(lang1, table.getLanguage());
		assertTrue(table.getStrings().containsKey("aaa"));
	}

	public void testRemoveLocalizedStringallLanguages() {
		Language lang1 = new Language(Language.LANG_Australian);
		Language lang2 = new Language(Language.LANG_BelgianFlemish);
		
		fixture.addLocalizedStringTable(lang1);
		fixture.addLocalizedStringTable(lang2);
		
		StringValue sv = fixture.addLocalizedStringDefault("abc");
		Map values = fixture.findStrings(sv.getValue());
		assertNotNull(values);
		assertTrue(values.size() == 3);
		
		fixture.removeLocalizedStringAllLanguages(sv.getValue());
		values = fixture.findStrings(sv.getValue());
		assertNull(values);
	}

	public void testUpdateLocalizedStringDefault() {
		StringValue sv = fixture.addLocalizedStringDefault("abc");
		StringValue sv2 = fixture.updateLocalizedStringDefault(sv.getValue(), "def");
		assertEquals(sv, sv2);
		assertEquals("def", fixture.getLocalizedStringDefault(sv.getValue()));
	}

} //ILocalizedStringBundleTest
