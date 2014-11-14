/*
 * Copyright 2001-2007 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.intact.dataexchange.enricher.standard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.model.CvTerm;
import uk.ac.ebi.intact.dataexchange.enricher.fetch.OlsCvObjectFetcher;

/**
 * CvObject enricher.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id: MiCvObjectEnricher.java 13941 2010-01-04 14:01:28Z samuel.kerrien $
 */
@Component(value = "intactCvObjectEnricher")
@Lazy
public class CvObjectEnricher extends AbstractCvObjectEnricher<CvTerm> {

    @Autowired
    public CvObjectEnricher(@Qualifier("olsCvObjectFetcher") OlsCvObjectFetcher intactCvObjectFetcher) {
        super(intactCvObjectFetcher);
    }

    @Override
    protected CvTermEnricher<CvTerm> getCvEnricher() {
        return this;
    }
}
