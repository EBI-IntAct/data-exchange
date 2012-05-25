package uk.ac.ebi.intact.calimocho.converters;

import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;
import psidev.psi.mi.tab.utils.MitabEscapeUtils;
import uk.ac.ebi.intact.model.BioSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Biosource converter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/05/12</pre>
 */

public class BioSourceConverter {

    public static String TAXID = "taxid";
    
    public Collection<Field> toCalimocho(BioSource organism){
        if (organism != null && organism.getTaxId() != null){
            Collection<Field> fields = new ArrayList<Field>(2);
            
            Field common = new DefaultField();

            String name = MitabEscapeUtils.escapeFieldElement(organism.getShortLabel());
            String fullName = MitabEscapeUtils.escapeFieldElement(organism.getFullName());
            String taxId = MitabEscapeUtils.escapeFieldElement(organism.getTaxId());

            common.set(CalimochoKeys.KEY, TAXID);
            common.set(CalimochoKeys.DB, TAXID);
            common.set(CalimochoKeys.VALUE, taxId);
            
            if (name != null){
                common.set(CalimochoKeys.TEXT, MitabEscapeUtils.escapeFieldElement(name));
            }
            
            if (fullName != null){
                Field scientific = new DefaultField();
                scientific.set(CalimochoKeys.KEY, TAXID);
                scientific.set(CalimochoKeys.DB, TAXID);
                scientific.set(CalimochoKeys.VALUE, taxId);

                scientific.set(CalimochoKeys.TEXT, MitabEscapeUtils.escapeFieldElement(fullName));
                
                fields.add(common);
                fields.add(scientific);
            }
            else {
                fields.add(common);
            }
            
            return fields;
        }

        return Collections.EMPTY_LIST;
    }
}
