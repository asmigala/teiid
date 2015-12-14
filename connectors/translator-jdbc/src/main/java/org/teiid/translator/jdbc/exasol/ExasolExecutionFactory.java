package org.teiid.translator.jdbc.exasol;
import java.util.ArrayList;
import java.util.List;
import org.teiid.translator.SourceSystemFunctions;
import org.teiid.translator.Translator;
import org.teiid.translator.TranslatorException;
import org.teiid.translator.jdbc.AliasModifier;
import org.teiid.translator.jdbc.JDBCExecutionFactory;

@Translator(name="exasol", description="A translator for EXASOL Analytic Database Server")

public class ExasolExecutionFactory extends JDBCExecutionFactory {

   public static final String EXASOL = "exasol";
   
   public static final String BIT_AND = "BIT_AND";
   public static final String BIT_NOT = "BIT_NOT";
   public static final String BIT_OR = "BIT_OR";
   public static final String BIT_XOR = "BIT_XOR";
   public static final String CEIL = "CEIL";
   public static final String CHARACTER_LENGTH = "CHARACTER_LENGTH";
   public static final String CHR = "CHR";
   public static final String CURRENT_DATE = "CURRENT_DATE";
   public static final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";
   public static final String LOCALTIMESTAMP = "LOCALTIMESTAMP";
   public static final String DAY = "DAY";
   
    @Override
    public void start() throws TranslatorException {

        super.start();
        
        registerFunctionModifier(SourceSystemFunctions.BITAND, new AliasModifier(BIT_AND)); 
        registerFunctionModifier(SourceSystemFunctions.BITNOT, new AliasModifier(BIT_NOT));
        registerFunctionModifier(SourceSystemFunctions.BITOR, new AliasModifier(BIT_OR));
        registerFunctionModifier(SourceSystemFunctions.BITXOR, new AliasModifier(BIT_XOR));
        registerFunctionModifier(SourceSystemFunctions.CEILING, new AliasModifier(CEIL));
        registerFunctionModifier(SourceSystemFunctions.LENGTH, new AliasModifier(CHARACTER_LENGTH));
        registerFunctionModifier(SourceSystemFunctions.CHAR, new AliasModifier(CHR));
        registerFunctionModifier(SourceSystemFunctions.CURDATE, new AliasModifier(CURRENT_DATE));
        registerFunctionModifier(SourceSystemFunctions.CURTIME, new AliasModifier(CURRENT_TIMESTAMP));
        registerFunctionModifier(SourceSystemFunctions.CURTIME, new AliasModifier(LOCALTIMESTAMP));
        registerFunctionModifier(SourceSystemFunctions.DAYOFMONTH, new AliasModifier(DAY));
        
    }

    @Override
    public List<String> getSupportedFunctions() {

        List<String> supportedFunctions = new ArrayList<String>();

        supportedFunctions.addAll(super.getSupportedFunctions());

        supportedFunctions.add(SourceSystemFunctions.SQRT);
        
        supportedFunctions.add(SourceSystemFunctions.ABS);
        supportedFunctions.add(SourceSystemFunctions.ACOS);
        supportedFunctions.add(SourceSystemFunctions.ASCII);
        supportedFunctions.add(SourceSystemFunctions.ASIN);
        supportedFunctions.add(SourceSystemFunctions.ATAN);
        supportedFunctions.add(SourceSystemFunctions.ATAN2);
        supportedFunctions.add(SourceSystemFunctions.BITAND);
        supportedFunctions.add(SourceSystemFunctions.BITNOT);
        supportedFunctions.add(SourceSystemFunctions.BITOR);
        supportedFunctions.add(SourceSystemFunctions.BITXOR);
        supportedFunctions.add(SourceSystemFunctions.CEILING);
        supportedFunctions.add(SourceSystemFunctions.LENGTH);
        supportedFunctions.add(SourceSystemFunctions.CHAR);
        supportedFunctions.add(SourceSystemFunctions.COALESCE);
        supportedFunctions.add(SourceSystemFunctions.CONCAT);
        supportedFunctions.add(SourceSystemFunctions.CONVERT);
        supportedFunctions.add(SourceSystemFunctions.COS);
        supportedFunctions.add(SourceSystemFunctions.COT);
        supportedFunctions.add(SourceSystemFunctions.CURDATE);
        supportedFunctions.add(SourceSystemFunctions.CURTIME);
        supportedFunctions.add(SourceSystemFunctions.DAYOFMONTH);
        supportedFunctions.add(SourceSystemFunctions.DEGREES);
        supportedFunctions.add(SourceSystemFunctions.EXP);
        supportedFunctions.add(SourceSystemFunctions.FLOOR);
        
        return supportedFunctions;
    }

}