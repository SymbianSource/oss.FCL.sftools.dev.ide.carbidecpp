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

package com.nokia.sdt.component.symbian.componentchecker;

import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.IMessageListener;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.component.symbian.test.PluginTest;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.testsupport.*;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.*;
import org.osgi.framework.Version;

import java.io.*;
import java.util.*;

import junit.framework.*;

/**
 * Test that all the (S60) components pass some essential tests for
 * shipping.
 * <p>
 * The flag CHECK_HELP_KEYS is used to control whether helpKey is
 * tested.  Probably leave this turned off until docs takes a pass
 * at the components.
 * <p>
 * The file EXPECTED_FILE is a dump of records
 * previously reported as failures.  These will not be reported again.
 * <p>
 * A test failure is reported if expected failures no longer fail.
 * <p>
 * On each run, the file FAILURES_FILE is rewritten with the current
 * set of failures.
 * <p>
 * 
 * 
 *
 */
public class ComponentChecker {
	public static final boolean CHECK_HELP_KEYS = false;
	
	private static final String COMPONENTS_DIR = "../com.nokia.sdt.series60.componentlibrary/components";

	protected static final String EXPECTED_FILE = "data/componentchecker/expected.txt";

	protected static final String FAILURES_FILE = "c:/temp/failures.txt";

	ComponentProvider provider;
	
	/** Logging and Problems messages */
	StringBuffer errorBuffer; 

	List<Report> failures;
	List<Report> successes;
	private List<Report> expected;
	private int tested;

	private ILogListener loglistener;

	private IMessageListener msgListener;
	
	public static Test suite() {
		TestHelpers.setPlugin(PluginTest.getDefault());
		final ComponentChecker checker = new ComponentChecker();
		checker.loadComponents();

		TestSuite suite = new TestSuite(
				"Component tests for " + COMPONENTS_DIR) {
			public void run(junit.framework.TestResult arg0) {
				// remove messages from other tests
				MessageReporting.reset();
				checker.errorBuffer.setLength(0);
				
				checker.loadExpected(EXPECTED_FILE);

				super.run(arg0);
				
				checker.saveFailures(FAILURES_FILE);
			}
		};
		
		checker.populateTests(suite);
		
		return suite;
	}
	
	/**
	 * Load the list of expected failures.
	 * @param expectedFile
	 */
	protected void loadExpected(String expectedFile) {
		try {
			File file = FileHelpers.projectRelativeFile(expectedFile);
			int size = (int)file.length();
			FileReader reader = new FileReader(file);
			char[] cbuf = new char[size];
			reader.read(cbuf);
			
			String[] lines = new String(cbuf).split("\r|\n|\r\n");
			for (int i = 0; i < lines.length; i++) {
				Report report = Report.parse(lines[i]);
				if (report != null)
					expected.add(report);
				//else if (lines[i].length() > 0)
				//	System.out.println("ignored line: " + lines[i]);
			}
		} catch (Exception e) {
			TestCase.fail("could not load expected failures list: " + expectedFile);
		}
	}

	protected void saveFailures(String failureFile) {
		try {
			File file = new File(failureFile);
			FileWriter writer = new FileWriter(file);

			for (Iterator iter = failures.iterator(); iter.hasNext();) {
				Report report = (Report) iter.next();
				writer.append(report.toLine());
				writer.append('\n');
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 
	 */
	private void populateTests(TestSuite suite) {
		for (Iterator iter = iterateAllComponents(); iter.hasNext();) {
			IComponent component = (IComponent) iter.next();
			suite.addTest(new Test() {
				public int countTestCases() {
					return 0;
				}

				public void run(TestResult result) {
					// add listeners
					Logging.addListener(loglistener);
					MessageReporting.addListener(msgListener);
				}
				
			});
			suite.addTest(new TestComponentInfo(this, component, CHECK_HELP_KEYS));
			suite.addTest(new TestSourceGenDebug(this, component));
			suite.addTest(new TestSourceGenFormatting(this, component));
			suite.addTest(new Test() {
				public int countTestCases() {
					return 0;
				}

				public void run(TestResult result) {
					// remove listeners
					Logging.removeListener(loglistener);
					MessageReporting.removeListener(msgListener);
				}
				
			});
		};
		
		suite.addTest(new TestExpected());
	}

	/**
	 * Validate that the expected failures are still generated
	 * 
	 *
	 */
	class TestExpected implements Test {
		public int countTestCases() {
			return 1;
		}

		public void run(TestResult arg0) {
			arg0.startTest(this);
			
			StringBuffer buffer = new StringBuffer();
			for (Iterator iter = expected.iterator(); iter.hasNext();) {
				Report report = (Report) iter.next();
				if (!failures.contains(report)) {
					buffer.append(report.toLine());
					buffer.append('\n');
				}
			}
			if (buffer.length() > 0) {
				buffer.insert(0, "These expected results no longer fail; remove them from " + EXPECTED_FILE +":\n");
				arg0.addFailure(this, new AssertionFailedError(buffer.toString()));
			}
			
			arg0.endTest(this);
		}
		
	}

	/**
	 * 
	 */
	public ComponentChecker() {
		errorBuffer = new StringBuffer();
		failures = new ArrayList<Report>();
		expected = new ArrayList<Report>();
		
		loglistener = new ILogListener() {

			public void logging(IStatus status, String plugin) {
				if (status.getMessage().contains(".series60.")) {
					errorBuffer.append(status.getMessage());
					errorBuffer.append("\n");
				}
			}
			
		};
		
		msgListener = new IMessageListener() {
			public boolean isHandlingMessage(IMessage msg) {
				return true;
			}

			public void emitMessage(IMessage msg) {
				errorBuffer.append(msg.getMessage());
				errorBuffer.append("\n");
			}
			
		};

		// TODO: read expected failures list
	}

	boolean isExpected(Report failure) {
		for (Iterator iter = expected.iterator(); iter.hasNext();) {
			Report expected = (Report) iter.next();
			if (expected.matches(failure))
				return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public void loadComponents() {
		try {
			File dir = new File(System.getProperty("user.dir"), COMPONENTS_DIR); 
			provider = TestDataModelHelpers.createProviderForUserComponents(dir.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			provider = new ComponentProvider();
		}
	}


	/**
	 * Get all components, no matter what SDK version they're for.
	 * @return
	 */
	Iterator iterateAllComponents() {
		ComponentSDKSelector selector = new ComponentSDKSelector
			(SymbianModelUtils.S60_SDK, new Version(3, 0, 0));

		ComponentSetResult result = provider.queryComponents(selector);

		class ComponentInfo implements Comparable<ComponentInfo> {
			IComponent component;

			public ComponentInfo(IComponent component) {
				this.component = component;
			}

			public int compareTo(ComponentInfo o) {
				return component.getId().compareTo(o.component.getId());
			}
		}
		
		final List<ComponentInfo> cs = new ArrayList<ComponentInfo>();
		IComponentLibrary[] libraries = provider.getComponentLibraries(null);
		for (int i = 0; i < libraries.length; i++) {
			IComponentLibrary lib = libraries[i];
			if (lib.getId().contains("series60")) {
				try {
					lib.loadComponents();
				} catch (ComponentSystemException e) {
					e.printStackTrace();
				}
				IComponent[] components = lib.getComponents(null);
				for (int j = 0; j < components.length; j++) {
					((Component)components[j]).setComponentSet((ComponentSet)result.getComponentSet());
					cs.add(new ComponentInfo(components[j]));
				}
				Collections.sort(cs);
			}
		}
		
		final Iterator iter = cs.iterator();
		return new Iterator<IComponent>() {
				public boolean hasNext() {
					return iter.hasNext();
				}

				public IComponent next() {
					return ((ComponentInfo)iter.next()).component;
				}

				public void remove() {
					iter.remove();
				}
		};
	}

	/**
	 * Get components compatible with a specific SDK version.
	 * @return
	 */
	Iterator iterateComponentsForVersion(Version version) {
		ComponentSDKSelector selector = new ComponentSDKSelector
			(SymbianModelUtils.S60_SDK, version);

		ComponentSetResult result = provider.queryComponents(selector);
		
		MultiStatus status = (MultiStatus) result.getStatus();
		if (status != null) {
			IStatus[] kids = status.getChildren();
			for (int i = 0; i < kids.length; i++) {
				// some will fail, from the test plugin, so only look at S60
				if (kids[i].getMessage().contains(".series60.")) {
					errorBuffer.append(kids[i].getMessage());
					errorBuffer.append("\n");
				}
			}
		}
	
		IComponentSet set = result.getComponentSet();
		return set.iterator();
	}

	/**
	 * Report to console.  Not used in unit test.
	 */
	public void reportResults() throws Exception {
		StringBuffer buffer = new StringBuffer();
		if (errorBuffer.length() > 0) {
			buffer.append("*** ERRORS IN COMPONENTS:\n");
			buffer.append(errorBuffer);
		}

		if (failures.size() > 0) {
			buffer.append("*** FAILURES:\n");
			for (Iterator iter = failures.iterator(); iter.hasNext();) {
				Report failure = (Report) iter.next();
				if (!expected.contains(failure)) {
					buffer.append(failure);
					buffer.append("\n");
				}
			}
		} else if (tested == 0) {
			buffer.append("*** TEST SETUP ERROR: no components found\n");
		}

		if (buffer.length() > 0) {
			System.out.println(buffer);
		}
		
	}
	
}
