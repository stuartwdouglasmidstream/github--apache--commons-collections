package org.apache.commons.collections;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache Turbine" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache Turbine", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit tests {@link org.apache.commons.collections.SequencedHashMap}.
 *
 * @author <a href="mailto:dlr@collab.net">Daniel Rall</a>
 * @author <a href="mailto:hps@intermeta.de">Henning P. Schmiedehausen</a>
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 */
public class TestSequencedHashMap extends TestHashMap
{
    /**
     * The instance to experiment on.
     */
    protected SequencedHashMap labRat;

    public TestSequencedHashMap(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(TestSequencedHashMap.class);
    }

    public static void main(String[] args[]) {
        String[] testCaseName = { TestSequencedHashMap.class.getName() };
        junit.textui.TestRunner.main(testCaseName);
    }

    public void setUp() {
        super.setUp();
        labRat = new SequencedHashMap();
    }

    public Map makeMap() {
        return new SequencedHashMap();
    }

    protected Object[] getKeys() {
        return new Object[] { "foo", "baz", "eek" };
    }

    protected Object[] getValues() {
        return new Object[] { "bar", "frob", new Object() };
    }

    public void testSequenceMap() throws Throwable {
        Object[] keys = getKeys();
        int expectedSize = keys.length;
        Object[] values = getValues();
        for (int i = 0; i < expectedSize; i++) {
            labRat.put(keys[i], values[i]);
        }

        // Test size().
        assertEquals("size() does not match expected size",
                     expectedSize, labRat.size());

        // Test clone(), iterator(), and get(Object).
        SequencedHashMap clone = (SequencedHashMap) labRat.clone();
        assertEquals("Size of clone does not match original",
                     labRat.size(), clone.size());
        Iterator origKeys = labRat.keySet().iterator();
        Iterator copiedKeys = clone.keySet().iterator();
        while (origKeys.hasNext()) {
            Object origKey = origKeys.next();
            Object copiedKey = copiedKeys.next();
            assertEquals("Cloned key does not match orginal",
                         origKey, copiedKey);
            assertEquals("Cloned value does not match original",
                         labRat.get(origKey), clone.get(copiedKey));
        }
        assertTrue("iterator() returned different number of elements than keys()",
               !copiedKeys.hasNext());

        // Test sequence()
        List seq = labRat.sequence();
        assertEquals("sequence() returns more keys than in the Map",
                     expectedSize, seq.size());

        for (int i = 0; i < seq.size(); i++) {
            assertEquals("Key " + i + " is not the same as the key in the Map",
                         keys[i], seq.get(i));
        }
    }

    protected void tearDown() {
        labRat = null;
    }
}