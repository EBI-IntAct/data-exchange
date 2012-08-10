/**
 * Copyright 2009 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.intact.task.mitab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import psidev.psi.mi.tab.model.BinaryInteraction;
import uk.ac.ebi.intact.core.context.IntactContext;
import uk.ac.ebi.intact.model.Interaction;
import uk.ac.ebi.intact.psimitab.converters.Intact2BinaryInteractionConverter;
import uk.ac.ebi.intact.psimitab.converters.expansion.ExpansionStrategy;
import uk.ac.ebi.intact.psimitab.converters.expansion.SpokeWithoutBaitExpansion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class InteractionExpansionCompositeProcessor implements ItemProcessor<Interaction, Collection<? extends BinaryInteraction>> {
    private static final Log log = LogFactory.getLog( InteractionExpansionCompositeProcessor.class );

    private ExpansionStrategy expansionStategy;

    private List<BinaryIteractionItemProcessor> binaryItemProcessors;

    private Intact2BinaryInteractionConverter intactInteractionConverter;

    public InteractionExpansionCompositeProcessor() {
        this.expansionStategy = new SpokeWithoutBaitExpansion();
        this.binaryItemProcessors = new ArrayList<BinaryIteractionItemProcessor>();
        this.intactInteractionConverter = new Intact2BinaryInteractionConverter(this.expansionStategy);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Collection<? extends BinaryInteraction> process(Interaction item) throws Exception {

        if (item == null){
            return null;
        }

        // reattach the interaction object to the entity manager because connection may have been closed after reading the object
        Interaction intactInteraction = IntactContext.getCurrentInstance().getDaoFactory().getEntityManager().merge(item);

        Collection<BinaryInteraction> binaryInteractions = intactInteractionConverter.convert(intactInteraction);

        if (binaryInteractions.isEmpty()) {
            if (log.isErrorEnabled()) {
                log.error("Could not not generate any binary interactions for: "+item);
                throw new InteractionExpansionException("Could not not generate any binary interactions for: "+item);
            }
        }

        log.info("Processing interaction : " + item.getAc());

        boolean isFirst = true;

        for (BinaryInteraction binaryInteraction : binaryInteractions) {

            if (isFirst){
                for (BinaryIteractionItemProcessor delegate : binaryItemProcessors) {
                    delegate.onlyProcessInteractors(false);
                    binaryInteraction = delegate.process(binaryInteraction);
                }
            }
            else {
                for (BinaryIteractionItemProcessor delegate : binaryItemProcessors) {
                    delegate.onlyProcessInteractors(true);
                    binaryInteraction = delegate.process(binaryInteraction);
                }
            }
        }

        return binaryInteractions;
    }

    public void setExpansionStategy(ExpansionStrategy expansionStategy) {
        this.expansionStategy = expansionStategy;

        this.intactInteractionConverter = new Intact2BinaryInteractionConverter(this.expansionStategy);
    }

    public void setBinaryItemProcessors(List<BinaryIteractionItemProcessor> delegates) {
        if(delegates != null){
            this.binaryItemProcessors = delegates;
        }
    }
}
