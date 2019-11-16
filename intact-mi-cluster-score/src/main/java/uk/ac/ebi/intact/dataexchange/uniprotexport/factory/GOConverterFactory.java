package uk.ac.ebi.intact.dataexchange.uniprotexport.factory;

import uk.ac.ebi.intact.util.uniprotExport.converters.encoreconverters.GoLineConverter;
import uk.ac.ebi.intact.util.uniprotExport.converters.encoreconverters.GoLineConverterVersion1;
import uk.ac.ebi.intact.util.uniprotExport.converters.encoreconverters.GoLineConverterVersion2;
import uk.ac.ebi.intact.util.uniprotExport.parameters.golineparameters.GOParameters;

/**
 * Factory for the GO line converters
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/03/11</pre>
 */

public class GOConverterFactory {

    public static GoLineConverter<? extends GOParameters> createGOConverter(int version){
         switch (version) {
            case 1:
                return new GoLineConverterVersion1();
             case 2:
                 return new GoLineConverterVersion2();
            default:
                return null;
        }
    }
}
