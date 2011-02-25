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

import org.springframework.batch.item.ItemWriter;
import psidev.psi.mi.tab.model.BinaryInteraction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabCollectionCompositeWriter implements ItemWriter<Collection<? extends BinaryInteraction>> {

    private List<BinaryInteractionItemWriter> delegates;

    public void write(List<? extends Collection<? extends BinaryInteraction>> items) throws Exception {
        for (Collection<? extends BinaryInteraction> binaryInteraction : items) {
            for (BinaryInteractionItemWriter delegate : delegates) {
                delegate.write(new ArrayList<BinaryInteraction>(binaryInteraction));
            }
        }
    }

    public void setDelegates(List<BinaryInteractionItemWriter> delegates) {
        this.delegates = delegates;
    }
}