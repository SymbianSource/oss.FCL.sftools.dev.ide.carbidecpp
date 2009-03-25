/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.tests;

import com.nokia.sdt.emf.dm.DmFactory;
import com.nokia.sdt.emf.dm.INode;

import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.impl.ValidatingEObjectContainmentEList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>INode</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class INodeTest extends TestCase {
	/**
	 * The fixture for this INode test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected INode fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(INodeTest.class);
	}

	/**
	 * Constructs a new INode test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public INodeTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this INode test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(INode fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this INode test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private INode getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(DmFactory.eINSTANCE.createINode());
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

	class TestListener extends AdapterImpl {
		
		int changeCount;

		public void notifyChanged(Notification msg) {
			++changeCount;
		}	
	}
	
	public void testGetChildrenList() {
		INode node = getFixture();
		EList children = node.getChildren();
		assertTrue(children instanceof ValidatingEObjectContainmentEList);
	}
	
	public void testNoFireOnGet() {
		// regression test that getting a property does not fire a property changed event
		INode node = getFixture();
		
		StringValue value = new StringValue(StringValue.LITERAL, "foo");
		node.getProperties().set("name", value);
	
		TestListener listener = new TestListener();
		node.eAdapters().add(listener);
		
		Object value2 = node.getProperties().get("name");
		assertEquals(value, value2);
		assertTrue(listener.changeCount == 0);
	}

} //INodeTest
