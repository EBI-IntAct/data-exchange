package uk.ac.ebi.intact.dataexchange.psimi.exporter.complexes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.util.Assert;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.factory.options.PsiXmlWriterOptions;

/**
 * The ComplexWriter is an ItemStream and ItemWriter which writes for each ComplexFileEntry a psi xml file
 * of type compact.
 *
 * Several properties can be customized :
 * - parentFolderPath which is the absolute path name of the parent folder where to write the xml files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/09/11</pre>
 */

public class ComplexXmlWriter extends ComplexWriter {

    private static final Log log = LogFactory.getLog(ComplexXmlWriter.class);

    /**
     * The name of the sequence id which is persisted
     */
    private final static String SEQUENCE_ID = "sequence_id";

    private int currentId = 0;

    public ComplexXmlWriter(){
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        Assert.notNull(executionContext, "ExecutionContext must not be null");

        // we get the last id generated by this processor
        if (executionContext.containsKey(SEQUENCE_ID)){
            currentId = executionContext.getInt(SEQUENCE_ID);
        }
        else {
            // we need to reset the current id of the IdSequenceGenerator to the first position
            currentId = 0;
        }

        super.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        Assert.notNull(executionContext, "ExecutionContext must not be null");

        // we can persist the current position
        if (getWriterOptions().containsKey(PsiXmlWriterOptions.ELEMENT_WITH_ID_CACHE_OPTION)){
            PsiXmlObjectCache previousCache = (PsiXmlObjectCache)getWriterOptions().get(PsiXmlWriterOptions.ELEMENT_WITH_ID_CACHE_OPTION);
            this.currentId = previousCache.getLastGeneratedId();
        }
        executionContext.putInt(SEQUENCE_ID, this.currentId);

        super.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        this.currentId = 0;
        super.close();
    }

    @Override
    protected void addSupplementaryOptions() {
        if (getWriterOptions().containsKey(PsiXmlWriterOptions.ELEMENT_WITH_ID_CACHE_OPTION)){
            PsiXmlObjectCache previousCache = (PsiXmlObjectCache)getWriterOptions().get(PsiXmlWriterOptions.ELEMENT_WITH_ID_CACHE_OPTION);
            this.currentId = previousCache.getLastGeneratedId();
        }
        // add cache with id cache
        PsiXmlObjectCache cache = new InMemoryIdentityObjectCache();
        cache.resetLastGeneratedIdTo(this.currentId);
        getWriterOptions().put(PsiXmlWriterOptions.ELEMENT_WITH_ID_CACHE_OPTION, cache);
    }
}