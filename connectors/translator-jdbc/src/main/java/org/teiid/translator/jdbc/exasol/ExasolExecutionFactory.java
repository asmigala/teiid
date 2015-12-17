package org.teiid.translator.jdbc.exasol;
import static org.teiid.translator.TypeFacility.RUNTIME_NAMES.CHAR;
import static org.teiid.translator.TypeFacility.RUNTIME_NAMES.DATE;
import static org.teiid.translator.TypeFacility.RUNTIME_NAMES.DOUBLE;
import static org.teiid.translator.TypeFacility.RUNTIME_NAMES.INTEGER;
import static org.teiid.translator.TypeFacility.RUNTIME_NAMES.STRING;
import static org.teiid.translator.TypeFacility.RUNTIME_NAMES.TIMESTAMP;

import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.verifier.statics.LONG_Upper;
import org.teiid.core.types.DataTypeManager;
import org.teiid.translator.SourceSystemFunctions;
import org.teiid.translator.Translator;
import org.teiid.translator.TranslatorException;
import org.teiid.translator.jdbc.AliasModifier;
import org.teiid.translator.jdbc.ConvertModifier;
import org.teiid.translator.jdbc.FunctionModifier;
import org.teiid.translator.jdbc.JDBCExecutionFactory;

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
   
   /*
    * Date/Time functions
    */
   public static final String ADD_DAYS = "ADD_DAYS";
   public static final String ADD_HOURS = "ADD_HOURS";
   public static final String ADD_MINUTES = "ADD_MINUTES";
   public static final String ADD_MONTHS = "ADD_MONTHS";
   public static final String ADD_SECONDS = "ADD_SECONDS";
   public static final String ADD_WEEKS = "ADD_WEEKS";
   public static final String ADD_YEARS = "ADD_YEARS";
   public static final String DATE_TRUNC = "DATE_TRUNC";
   public static final String DAY = "DAY";
   public static final String DAYS_BETWEEN = "DAYS_BETWEEN";
   public static final String FROM_POSIX_TIME = "FROM_POSIX_TIME";
   public static final String HOURS_BETWEEN = "HOURS_BETWEEN";
   public static final String MINUTES_BETWEEN = "MINUTES_BETWEEN";
   public static final String MONTHS_BETWEEN = "MONTHS_BETWEEN";
   public static final String POSIX_TIME = "POSIX_TIME";
   public static final String ROUND = "ROUND";
   public static final String SECONDS_BETWEEN = "SECONDS_BETWEEN";
   public static final String TO_CHAR = "TO_CHAR";
   public static final String TO_DATE = "TO_DATE";
   public static final String TO_TIMESTAMP = "TO_TIMESTAMP";
   public static final String TRUNC = "TRUNC";
   public static final String TRUNCATE = "TRUNCATE";
   public static final String YEARS_BETWEEN = "YEARS_BETWEEN";
   
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
   
        /*
         * Date/Time functions
         */
        addPushDownFunction(EXASOL, ADD_DAYS, DATE, DATE, INTEGER);
        addPushDownFunction(EXASOL, ADD_DAYS, TIMESTAMP, TIMESTAMP, INTEGER);
        addPushDownFunction(EXASOL, ADD_HOURS, TIMESTAMP, TIMESTAMP, INTEGER);
        addPushDownFunction(EXASOL, ADD_MINUTES, TIMESTAMP, TIMESTAMP, INTEGER);
        addPushDownFunction(EXASOL, ADD_MONTHS, DATE, DATE, INTEGER);
        addPushDownFunction(EXASOL, ADD_MONTHS, TIMESTAMP, TIMESTAMP, INTEGER);
        addPushDownFunction(EXASOL, ADD_SECONDS, TIMESTAMP, TIMESTAMP, DOUBLE);
        addPushDownFunction(EXASOL, ADD_WEEKS, DATE, DATE, INTEGER);
        addPushDownFunction(EXASOL, ADD_WEEKS, TIMESTAMP, TIMESTAMP, INTEGER);
        addPushDownFunction(EXASOL, ADD_YEARS, DATE, DATE, INTEGER);
        addPushDownFunction(EXASOL, ADD_YEARS, TIMESTAMP, TIMESTAMP, INTEGER);
        registerFunctionModifier(SourceSystemFunctions.CURDATE, new AliasModifier(UPPER));
        addPushDownFunction(EXASOL, DATE_TRUNC, DATE, STRING, DATE);
        addPushDownFunction(EXASOL, DATE_TRUNC, TIMESTAMP, STRING, TIMESTAMP);
        registerFunctionModifier(SourceSystemFunctions.DAYOFMONTH, new AliasModifier(DAY));
        addPushDownFunction(EXASOL, DAYS_BETWEEN, INTEGER, DATE, DATE);
        addPushDownFunction(EXASOL, DAYS_BETWEEN, INTEGER, TIMESTAMP, TIMESTAMP);
        registerFunctionModifier("FROM_UNIXTIME", new AliasModifier(FROM_POSIX_TIME));
        addPushDownFunction(EXASOL, HOURS_BETWEEN, DOUBLE, TIMESTAMP, TIMESTAMP);
        addPushDownFunction(EXASOL, MINUTES_BETWEEN, DOUBLE, TIMESTAMP, TIMESTAMP);
        addPushDownFunction(EXASOL, MONTHS_BETWEEN, DOUBLE, DATE, DATE);
        addPushDownFunction(EXASOL, MONTHS_BETWEEN, DOUBLE, TIMESTAMP, TIMESTAMP);
        addPushDownFunction(EXASOL, POSIX_TIME, DOUBLE, TIMESTAMP);
        addPushDownFunction(EXASOL, ROUND, DATE, DATE);
        addPushDownFunction(EXASOL, ROUND, DATE, DATE, STRING);
        addPushDownFunction(EXASOL, ROUND, TIMESTAMP, TIMESTAMP);
        addPushDownFunction(EXASOL, ROUND, TIMESTAMP, TIMESTAMP, STRING);
        addPushDownFunction(EXASOL, SECONDS_BETWEEN, DOUBLE, TIMESTAMP, TIMESTAMP);
        registerFunctionModifier("FORMATDATE", new AliasModifier(TO_CHAR));
        registerFunctionModifier(SourceSystemFunctions.FORMATTIMESTAMP, new AliasModifier(TO_CHAR));
        registerFunctionModifier("PARSEDATE", new AliasModifier(TO_DATE));
        registerFunctionModifier(SourceSystemFunctions.PARSETIMESTAMP, new AliasModifier(TO_TIMESTAMP));
        addPushDownFunction(EXASOL, TRUNC, DATE, DATE);
        addPushDownFunction(EXASOL, TRUNC, DATE, DATE, STRING);
        addPushDownFunction(EXASOL, TRUNC, TIMESTAMP, TIMESTAMP);
        addPushDownFunction(EXASOL, TRUNC, TIMESTAMP, TIMESTAMP, STRING);
        addPushDownFunction(EXASOL, TRUNCATE, DATE, DATE);
        addPushDownFunction(EXASOL, TRUNCATE, DATE, DATE, STRING);
        addPushDownFunction(EXASOL, TRUNCATE, TIMESTAMP, TIMESTAMP);
        addPushDownFunction(EXASOL, TRUNCATE, TIMESTAMP, TIMESTAMP, STRING);
        addPushDownFunction(EXASOL, YEARS_BETWEEN, DOUBLE, DATE, DATE);
        addPushDownFunction(EXASOL, YEARS_BETWEEN, DOUBLE, TIMESTAMP, TIMESTAMP);
   
        /*
         * Conversion functions
         */
        ConvertModifier convertModifier = new ConvertModifier();
        convertModifier.addTypeMapping("VARCHAR(4000)", FunctionModifier.STRING);  //$NON-NLS-1$
        convertModifier.addTypeMapping("DECIMAL(3,0)", FunctionModifier.BYTE);  //$NON-NLS-1$
        convertModifier.addTypeMapping("DECIMAL(5)", FunctionModifier.SHORT);
        convertModifier.addTypeMapping("DECIMAL(10,0)", FunctionModifier.LONG);
        convertModifier.addTypeMapping("DOUBLE PRECISION", FunctionModifier.BIGINTEGER);
        
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
        
        /*
         * Date/Time functions
         */
        //CONVERT_TZ
        supportedFunctions.add(SourceSystemFunctions.CURDATE);
        //CURRENT_DATE
        //CURRENT_TIMESTAMP
        //DBTIMEZONE
        //EXTRACT
        supportedFunctions.add("FROM_UNIXTIME");
        //HOUR
        //LOCALTIMESTAMP
        //MINUTE
        supportedFunctions.add(SourceSystemFunctions.MONTH);
        supportedFunctions.add(SourceSystemFunctions.NOW);
        //NUMTODSINTERVAL
        //NUMTOYMINTERVAL
        //SECOND
        //SESSIONTIMEZONE
        //SYSDATE
        //SYSTIMESTAMP
        supportedFunctions.add("FORMATDATE");
        supportedFunctions.add("PARSEDATE");
        //TO_DSINTERVAL
        supportedFunctions.add(SourceSystemFunctions.PARSETIMESTAMP);
        //TO_YMINTERVAL
        supportedFunctions.add(SourceSystemFunctions.WEEK);
        supportedFunctions.add(SourceSystemFunctions.YEAR);
        
        /*
         * Conversion functions
         */
        
        return supportedFunctions;
    }

}