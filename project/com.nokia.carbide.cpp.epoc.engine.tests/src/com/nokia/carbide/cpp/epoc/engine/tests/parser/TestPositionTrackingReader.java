/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests.parser;

import com.nokia.carbide.internal.cpp.epoc.engine.parser.PositionTrackingReader;

import java.io.StringReader;

import junit.framework.TestCase;

/**
 * Test this extension of Reader
 *
 */
public class TestPositionTrackingReader extends TestCase {
	public void testBasic() throws Exception {
		PositionTrackingReader reader = new PositionTrackingReader(
				new StringReader("foo"));
		assertEquals('f', reader.read());
		assertEquals('o', reader.read());
		assertEquals('o', reader.read());
		assertEquals(-1, reader.read());
		assertEquals(-1, reader.read());
	}
	
	public void testPositions1() throws Exception {
		PositionTrackingReader reader = new PositionTrackingReader(
				new StringReader("foo"));
		assertEquals(0, reader.getOffset());
		assertEquals(0, reader.getOffset());
		assertEquals(1, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());
		
		assertEquals('f', reader.read());
		assertEquals(1, reader.getOffset());
		assertEquals(1, reader.getLineNumber());
		assertEquals(2, reader.getColumnNumber());
		
		assertEquals('o', reader.read());
		assertEquals(2, reader.getOffset());
		assertEquals(1, reader.getLineNumber());
		assertEquals(3, reader.getColumnNumber());
		
		assertEquals('o', reader.read());
		assertEquals(3, reader.getOffset());
		assertEquals(1, reader.getLineNumber());
		assertEquals(4, reader.getColumnNumber());
		
		assertEquals(-1, reader.read());
		assertEquals(3, reader.getOffset());
		assertEquals(1, reader.getLineNumber());
		assertEquals(4, reader.getColumnNumber());
		
		assertEquals(-1, reader.read());
		assertEquals(3, reader.getOffset());
		assertEquals(1, reader.getLineNumber());
		assertEquals(4, reader.getColumnNumber());
		
	}
	
	public void testPositions2() throws Exception {
		PositionTrackingReader reader = new PositionTrackingReader(
				new StringReader("foo\nbar\n"));
		char[] buf = new char[3];
		assertEquals(3, reader.read(buf));
		assertEquals('f', buf[0]);
		assertEquals('o', buf[1]);
		assertEquals('o', buf[2]);
		
		assertEquals(3, reader.getOffset());
		assertEquals(1, reader.getLineNumber());
		assertEquals(4, reader.getColumnNumber());
		
		assertEquals('\n', reader.read());
		assertEquals(4, reader.getOffset());
		assertEquals(2, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());

		assertEquals(3, reader.read(buf));
		assertEquals('b', buf[0]);
		assertEquals('a', buf[1]);
		assertEquals('r', buf[2]);
		assertEquals(7, reader.getOffset());
		assertEquals(2, reader.getLineNumber());
		assertEquals(4, reader.getColumnNumber());
		
		assertEquals('\n', reader.read());
		assertEquals(8, reader.getOffset());
		assertEquals(3, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());

		assertEquals(-1, reader.read());
		assertEquals(8, reader.getOffset());
		assertEquals(3, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());
		
	}
	public void testLines1() throws Exception {
		PositionTrackingReader reader = new PositionTrackingReader(
				new StringReader("foo\nbar\n"));
		String line = reader.readLine();
		assertEquals("foo", line);
		assertEquals(4, reader.getOffset());
		assertEquals(2, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());

		assertEquals('b', reader.read());
		assertEquals(5, reader.getOffset());
		assertEquals(2, reader.getLineNumber());
		assertEquals(2, reader.getColumnNumber());

		line = reader.readLine();
		assertEquals("ar", line);

		assertEquals(8, reader.getOffset());
		assertEquals(3, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());
		
		assertEquals(-1, reader.read());
		assertEquals(8, reader.getOffset());
		assertEquals(3, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());
		
	}

	public void testLines2() throws Exception {
		PositionTrackingReader reader = new PositionTrackingReader(
				new StringReader("foo\r\nbar\r\n"));
		String line = reader.readLine();
		assertEquals("foo", line);
		assertEquals(5, reader.getOffset());
		assertEquals(2, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());

		assertEquals('b', reader.read());
		assertEquals(6, reader.getOffset());
		assertEquals(2, reader.getLineNumber());
		assertEquals(2, reader.getColumnNumber());

		line = reader.readLine();
		assertEquals("ar", line);

		assertEquals(10, reader.getOffset());
		assertEquals(3, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());
		
		assertEquals(-1, reader.read());
		assertEquals(10, reader.getOffset());
		assertEquals(3, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());
		
	}

	public void testMarkReset1() throws Exception {
		PositionTrackingReader reader = new PositionTrackingReader(
				new StringReader("foo\nbar\n"));
		
		reader.mark(10);
		
		String line = reader.readLine();
		assertEquals("foo", line);
		assertEquals(4, reader.getOffset());
		assertEquals(2, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());

		reader.reset();
		assertEquals(0, reader.getOffset());
		assertEquals(1, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());
		
		assertEquals('f', reader.read());
		assertEquals(1, reader.getOffset());
		assertEquals(1, reader.getLineNumber());
		assertEquals(2, reader.getColumnNumber());

		line = reader.readLine();
		assertEquals("oo", line);
		assertEquals(4, reader.getOffset());
		assertEquals(2, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());
		
	}
	
	public void testMarkReset2() throws Exception {
		PositionTrackingReader reader = new PositionTrackingReader(
				new StringReader("foo bar\r\nzap"));
		
		reader.mark(10);
		
		while (reader.read() != ' ') ;
		
		reader.mark(10);
		
		while (reader.read() != '\r') ;
		
		assertEquals(8, reader.getOffset());
		assertEquals(2, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());
		
		reader.reset();
		
		assertEquals(4, reader.getOffset());
		assertEquals(1, reader.getLineNumber());
		assertEquals(5, reader.getColumnNumber());
		
		reader.mark(1);
		
		assertEquals('b', reader.read());
		reader.reset();
		
		String line = reader.readLine();
		assertEquals("bar", line);
		assertEquals(9, reader.getOffset());
		assertEquals(2, reader.getLineNumber());
		assertEquals(1, reader.getColumnNumber());

		line = reader.readLine();
		assertEquals("zap", line);
		assertEquals(12, reader.getOffset());
		assertEquals(2, reader.getLineNumber());
		assertEquals(4, reader.getColumnNumber());
		
	}
	
}
