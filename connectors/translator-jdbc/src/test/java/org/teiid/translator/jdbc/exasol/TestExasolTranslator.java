/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.teiid.translator.jdbc.exasol;

import org.junit.BeforeClass;
import org.junit.Test;
import org.teiid.translator.TranslatorException;
import org.teiid.translator.jdbc.TranslationHelper;

public class TestExasolTranslator {
    
    private static ExasolExecutionFactory translator; 

    @BeforeClass 
    public static void setupOnce() throws Exception {
        translator = new ExasolExecutionFactory(); 
        translator.start();
    }
    
    public String getBQT_VDB() {
        return TranslationHelper.BQT_VDB;
    }
    
    public String getPARTS_VDB() {
        return TranslationHelper.PARTS_VDB;
    }
        
    public void helpTestVisitor(String vdb, String input, String expectedOutput) throws TranslatorException {
        TranslationHelper.helpTestVisitor(vdb, input, expectedOutput, translator);
    }
    
    @Test
    public void testModifiedFunctions() throws TranslatorException {
    
    	String input = "SELECT CEILING(0.234) FROM BQT1.MediumA"; //$NON-NLS-1$
        String output = "SELECT 1.0 FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
    	input = "SELECT LOG(100) FROM BQT1.MediumA"; //$NON-NLS-1$
    	output = "SELECT 4.605170185988092 FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT BITAND(9, 3) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT 1 FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT BITNOT(1) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT -2 FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT BITOR(9, 3) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT 11 FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT BITXOR(9, 3) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT 10 FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
    }
    
    @Test
    public void testPushDownFuctions() throws TranslatorException {
       
    	String input = "SELECT exasol.BIT_LENGTH('exasol') FROM BQT1.MediumA"; //$NON-NLS-1$
        String output = "SELECT BIT_LENGTH('exasol') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.COLOGNE_PHONETIC('schmitt') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT COLOGNE_PHONETIC('schmitt') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.EDIT_DISTANCE('schmitt', 'Schmidt') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT EDIT_DISTANCE('schmitt', 'Schmidt') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.INSTR('abcabcabc', 'cab') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT INSTR('abcabcabc', 'cab') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.INSTR('abcabcabc', 'cab', 3) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT INSTR('abcabcabc', 'cab', 3) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.INSTR('abcabcabc', 'cab', -1, 2) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT INSTR('abcabcabc', 'cab', -1, 2) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.LTRIM('ab cdef') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT LTRIM('ab cdef') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.LTRIM('ab cdef', ' ab') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT LTRIM('ab cdef', ' ab') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.MID('abcdef', 2) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT MID('abcdef', 2) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.MID('abcdef', 2, 3) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT MID('abcdef', 2, 3) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.OCTET_LENGTH('abcd') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT OCTET_LENGTH('abcd') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REGEXP_INSTR('Phone: +497003927877678', '\\+?\\d+') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REGEXP_INSTR('Phone: +497003927877678', '\\+?\\d+') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REGEXP_INSTR('Phone: +497003927877678', '\\+?\\d+', 1) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REGEXP_INSTR('Phone: +497003927877678', '\\+?\\d+', 1) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REGEXP_INSTR('Phone: +497003927877678', '\\+?\\d+', 1, 2) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REGEXP_INSTR('Phone: +497003927877678', '\\+?\\d+', 1, 2) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REGEXP_REPLACE('From: my_mail@yahoo.com', '(?i)^From: ([a-z0-9._%+-]+)@([a-z0-9.-]+\\.[a-z]{2,4}$)') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REGEXP_REPLACE('From: my_mail@yahoo.com', '(?i)^From: ([a-z0-9._%+-]+)@([a-z0-9.-]+\\.[a-z]{2,4}$)') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REGEXP_REPLACE('From: my_mail@yahoo.com', '(?i)^From: ([a-z0-9._%+-]+)@([a-z0-9.-]+\\.[a-z]{2,4}$)', 'Name: \1 - Domain: \2') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REGEXP_REPLACE('From: my_mail@yahoo.com', '(?i)^From: ([a-z0-9._%+-]+)@([a-z0-9.-]+\\.[a-z]{2,4}$)', 'Name: \1 - Domain: \2') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REGEXP_REPLACE('From: my_mail@yahoo.com', '(?i)^From: ([a-z0-9._%+-]+)@([a-z0-9.-]+\\.[a-z]{2,4}$)', 'Name: \1 - Domain: \2', 0) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REGEXP_REPLACE('From: my_mail@yahoo.com', '(?i)^From: ([a-z0-9._%+-]+)@([a-z0-9.-]+\\.[a-z]{2,4}$)', 'Name: \1 - Domain: \2', 0) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REGEXP_REPLACE('From: my_mail@yahoo.com', '(?i)^From: ([a-z0-9._%+-]+)@([a-z0-9.-]+\\.[a-z]{2,4}$)', 'Name: \1 - Domain: \2', 0, 1) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REGEXP_REPLACE('From: my_mail@yahoo.com', '(?i)^From: ([a-z0-9._%+-]+)@([a-z0-9.-]+\\.[a-z]{2,4}$)', 'Name: \1 - Domain: \2', 0, 1) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REGEXP_SUBSTR('My mail address is my_mail@yahoo.com', '(?i)[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REGEXP_SUBSTR('My mail address is my_mail@yahoo.com', '(?i)[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REGEXP_SUBSTR('My mail address is my_mail@yahoo.com', '(?i)[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}', 2) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REGEXP_SUBSTR('My mail address is my_mail@yahoo.com', '(?i)[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}', 2) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REGEXP_SUBSTR('My mail address is my_mail@yahoo.com', '(?i)[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}', 2, 1) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REGEXP_SUBSTR('My mail address is my_mail@yahoo.com', '(?i)[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}', 2, 1) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REPLACE('Apple juice is great', 'Apple') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REPLACE('Apple juice is great', 'Apple') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REPLACE('Apple juice is great', 'Apple', 'Orange') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REPLACE('Apple juice is great', 'Apple', 'Orange') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.REVERSE('abcde') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT REVERSE('abcde') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.RTRIM('abc def') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT RTRIM('abc def') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.RTRIM('abc def', 'afe') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT RTRIM('abc def', 'afe') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.SOUNDEX('smythe') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT SOUNDEX('smythe') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.SPACE(5) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT SPACE(5) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.TO_NUMBER('+123') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT TO_NUMBER('+123') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.TO_NUMBER('-123.45', '99999.999') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT TO_NUMBER('-123.45', '99999.999') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.UNICODE('ä') FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT UNICODE('ä') FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.UNICODECHR(252) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT UNICODECHR(252) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
        
        input = "SELECT exasol.UNICODECHR(252) FROM BQT1.MediumA"; //$NON-NLS-1$
        output = "SELECT UNICODECHR(252) FROM MediumA"; //$NON-NLS-1$
        helpTestVisitor(getBQT_VDB(), input, output);
    }

}
