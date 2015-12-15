package org.teiid.translator.jdbc.exasol;
import java.util.ArrayList;
import java.util.List;

import org.teiid.metadata.index.MetadataConstants.DATATYPE_TYPES;
import org.teiid.translator.SourceSystemFunctions;
import org.teiid.translator.Translator;
import org.teiid.translator.TranslatorException;
import org.teiid.translator.jdbc.AliasModifier;
import org.teiid.translator.jdbc.JDBCExecutionFactory;
import static org.teiid.translator.TypeFacility.RUNTIME_NAMES.*;

@Translator(name="exasol", description="A translator for EXASOL Analytic Database Server")

public class ExasolExecutionFactory extends JDBCExecutionFactory {

   public static final String EXASOL = "exasol";

   /*
    * Numeric functions
    */
   public static final String CEIL = "CEIL";
   public static final String LN = "LN";
   
   /*
    * Bitwise functions
    */
   public static final String BIT_AND = "BIT_AND";
   public static final String BIT_NOT = "BIT_NOT";
   public static final String BIT_OR = "BIT_OR";
   public static final String BIT_XOR = "BIT_XOR";
   
   /*
    * String functions
    */
   public static final String BIT_LENGTH = "BIT_LENGTH";
   public static final String CHARACTER_LENGTH = "CHARACTER_LENGTH";
   public static final String CHR = "CHR";
   public static final String COLOGNE_PHONETIC = "COLOGNE_PHONETIC";
   public static final String EDIT_DISTANCE = "EDIT_DISTANCE";
   public static final String INSTR = "INSTR";
   public static final String LOWER = "LOWER";
   public static final String LTRIM = "LTRIM";
   public static final String MID = "MID";
   public static final String OCTET_LENGTH = "OCTET_LENGTH";
   public static final String REGEXP_INSTR = "REGEXP_INSTR";
   public static final String REGEXP_REPLACE = "REGEXP_REPLACE";
   public static final String REGEXP_SUBSTR = "REGEXP_SUBSTR";
   public static final String REVERSE = "REVERSE";
   public static final String SOUNDEX = "SOUNDEX";
   public static final String SPACE = "SPACE";
   public static final String TO_NUMBER = "TO_NUMBER";
   public static final String UNICODE = "UNICODE";
   public static final String UNICODECHR = "UNICODECHR";
   public static final String UPPER = "UPPER";
   
   @Override
   public void start() throws TranslatorException {

        super.start();
       
        /*
         * Numeric functions
         */
        registerFunctionModifier(SourceSystemFunctions.CEILING, new AliasModifier(CEIL));
        registerFunctionModifier(SourceSystemFunctions.LOG, new AliasModifier(LN));
        /*
         * Bitwise functions
         */
        registerFunctionModifier(SourceSystemFunctions.BITAND, new AliasModifier(BIT_AND)); 
        registerFunctionModifier(SourceSystemFunctions.BITNOT, new AliasModifier(BIT_NOT));
        registerFunctionModifier(SourceSystemFunctions.BITOR, new AliasModifier(BIT_OR));
        registerFunctionModifier(SourceSystemFunctions.BITXOR, new AliasModifier(BIT_XOR));
        /*
         * String functions
         */
        addPushDownFunction(EXASOL, BIT_LENGTH, INTEGER, STRING);
        registerFunctionModifier(SourceSystemFunctions.LENGTH, new AliasModifier(CHARACTER_LENGTH));
        registerFunctionModifier(SourceSystemFunctions.CHAR, new AliasModifier(CHR));
        addPushDownFunction(EXASOL, COLOGNE_PHONETIC, STRING, STRING);
        addPushDownFunction(EXASOL, EDIT_DISTANCE, INTEGER, STRING, STRING);
        addPushDownFunction(EXASOL, INSTR, INTEGER, STRING, STRING);
        addPushDownFunction(EXASOL, INSTR, INTEGER, STRING, STRING, INTEGER);
        addPushDownFunction(EXASOL, INSTR, INTEGER, STRING, STRING, INTEGER, INTEGER);
        registerFunctionModifier(SourceSystemFunctions.LCASE, new AliasModifier(LOWER));
        addPushDownFunction(EXASOL, LTRIM, STRING, STRING, STRING);
        addPushDownFunction(EXASOL, MID, STRING, STRING, INTEGER);
        addPushDownFunction(EXASOL, MID, STRING, STRING, INTEGER, INTEGER);
        addPushDownFunction(EXASOL, OCTET_LENGTH, INTEGER, STRING);
        addPushDownFunction(EXASOL, REGEXP_INSTR, INTEGER, STRING, STRING);
        addPushDownFunction(EXASOL, REGEXP_INSTR, INTEGER, STRING, STRING, INTEGER);
        addPushDownFunction(EXASOL, REGEXP_INSTR, INTEGER, STRING, STRING, INTEGER, INTEGER);
        addPushDownFunction(EXASOL, REGEXP_REPLACE, STRING, STRING, STRING);
        addPushDownFunction(EXASOL, REGEXP_REPLACE, STRING, STRING, STRING, STRING);
        addPushDownFunction(EXASOL, REGEXP_REPLACE, STRING, STRING, STRING, STRING, INTEGER);
        addPushDownFunction(EXASOL, REGEXP_REPLACE, STRING, STRING, STRING, STRING, INTEGER, INTEGER);
        addPushDownFunction(EXASOL, REGEXP_SUBSTR, STRING, STRING, STRING);
        addPushDownFunction(EXASOL, REGEXP_SUBSTR, STRING, STRING, STRING, INTEGER);
        addPushDownFunction(EXASOL, REGEXP_SUBSTR, STRING, STRING, STRING, INTEGER, INTEGER);
        addPushDownFunction(EXASOL, SourceSystemFunctions.REPLACE, STRING, STRING, STRING);
        addPushDownFunction(EXASOL, REVERSE, STRING, STRING);
        addPushDownFunction(EXASOL, SourceSystemFunctions.RTRIM, STRING, STRING, STRING);
        addPushDownFunction(EXASOL, SOUNDEX, STRING, STRING);
        addPushDownFunction(EXASOL, SPACE, STRING, INTEGER);
        addPushDownFunction(EXASOL, TO_NUMBER, DOUBLE, STRING);
        addPushDownFunction(EXASOL, TO_NUMBER, DOUBLE, STRING, STRING);
        addPushDownFunction(EXASOL, UNICODE, INTEGER, CHAR);
        addPushDownFunction(EXASOL, UNICODECHR, CHAR, INTEGER);
        registerFunctionModifier(SourceSystemFunctions.UCASE, new AliasModifier(UPPER));
   }

    @Override
    public List<String> getSupportedFunctions() {

        List<String> supportedFunctions = new ArrayList<String>();

        supportedFunctions.addAll(super.getSupportedFunctions());
        
        /*
         * Numeric functions
         */
        supportedFunctions.add(SourceSystemFunctions.ABS);
        supportedFunctions.add(SourceSystemFunctions.ACOS);
        supportedFunctions.add(SourceSystemFunctions.ASIN);
        supportedFunctions.add(SourceSystemFunctions.ATAN);
        supportedFunctions.add(SourceSystemFunctions.ATAN2);
        supportedFunctions.add(SourceSystemFunctions.CEILING);
        supportedFunctions.add(SourceSystemFunctions.COS);
        //COSH
        supportedFunctions.add(SourceSystemFunctions.COT);
        //DEGREES
        //DIV
        supportedFunctions.add(SourceSystemFunctions.EXP);
        supportedFunctions.add(SourceSystemFunctions.FLOOR);
        supportedFunctions.add(SourceSystemFunctions.LOG);
        //LOG(base,n)
        supportedFunctions.add(SourceSystemFunctions.LOG10);
        //LOG2
        supportedFunctions.add(SourceSystemFunctions.MOD);
        supportedFunctions.add(SourceSystemFunctions.PI);
        supportedFunctions.add(SourceSystemFunctions.POWER);
        //RADIANS
        //RAND(), RAND(x,y), RANDOM(), RANDOM(x,y)
        //ROUND(number)
        supportedFunctions.add(SourceSystemFunctions.SIGN);
        supportedFunctions.add(SourceSystemFunctions.SIN);
        //SINH
        supportedFunctions.add(SourceSystemFunctions.SQRT);
        supportedFunctions.add(SourceSystemFunctions.TAN);
        //TANH
        
        /*
         * Bitwise functions
         */
        supportedFunctions.add(SourceSystemFunctions.BITAND);
        //BIT_CHECK
        //BIT_LENGTH
        supportedFunctions.add(SourceSystemFunctions.BITOR);
        supportedFunctions.add(SourceSystemFunctions.BITXOR);
        //BIT_SET
        //BIT_TO_NUM
        supportedFunctions.add(SourceSystemFunctions.BITNOT);
        
        /*
         * String functions
         */
        //ASCII
        supportedFunctions.add(SourceSystemFunctions.LENGTH);
        supportedFunctions.add(SourceSystemFunctions.CHAR);
        supportedFunctions.add(SourceSystemFunctions.CONCAT);
        //DUMP
        supportedFunctions.add(SourceSystemFunctions.INSERT);
        supportedFunctions.add(SourceSystemFunctions.LCASE);
        supportedFunctions.add(SourceSystemFunctions.LEFT);
        supportedFunctions.add(SourceSystemFunctions.LENGTH);
        supportedFunctions.add(SourceSystemFunctions.LOCATE);
        supportedFunctions.add(SourceSystemFunctions.LPAD);
        supportedFunctions.add(SourceSystemFunctions.LTRIM);
        //POSITION
        supportedFunctions.add(SourceSystemFunctions.REPEAT);
        supportedFunctions.add(SourceSystemFunctions.REPLACE);
        supportedFunctions.add(SourceSystemFunctions.RIGHT);
        supportedFunctions.add(SourceSystemFunctions.RPAD);
        supportedFunctions.add(SourceSystemFunctions.RTRIM);
        //SUBSTR(x,y), SUBSTR(x,y,z), SUBSTRING(x FROM y), SUBSCRING(x FROM y FOR z)
        //TO_CHAR(datetime), TO_CHAR(number)
        supportedFunctions.add(SourceSystemFunctions.TRANSLATE);
        //TRIM
        supportedFunctions.add(SourceSystemFunctions.UCASE);
        
        return supportedFunctions;
    }

}