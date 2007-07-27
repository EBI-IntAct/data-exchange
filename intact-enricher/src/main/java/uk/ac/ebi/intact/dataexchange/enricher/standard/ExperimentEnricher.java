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

import uk.ac.ebi.intact.model.Experiment;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class ExperimentEnricher implements Enricher<Experiment> {

     private static ThreadLocal<ExperimentEnricher> instance = new ThreadLocal<ExperimentEnricher>() {
        @Override
        protected ExperimentEnricher initialValue() {
            return new ExperimentEnricher();
        }
    };

    public static ExperimentEnricher getInstance() {
        return instance.get();
    }

    protected ExperimentEnricher() {
    }

    public void enrich(Experiment objectToEnrich) {
        BioSourceEnricher bioSourceEnricher = BioSourceEnricher.getInstance();

        bioSourceEnricher.enrich(objectToEnrich.getBioSource());

        CvObjectEnricher cvObjectEnricher = CvObjectEnricher.getInstance();
        if (objectToEnrich.getCvIdentification() != null) {
            cvObjectEnricher.enrich(objectToEnrich.getCvIdentification());
        }
        if (objectToEnrich.getCvInteraction() != null) {
            cvObjectEnricher.enrich(objectToEnrich.getCvInteraction());
        }
    }

    public void close() {
    }
}