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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.intact.core.unit.IntactBasicTestCase;
import uk.ac.ebi.intact.model.Experiment;
import uk.ac.ebi.intact.model.Publication;
import uk.ac.ebi.intact.model.CvInteraction;
import uk.ac.ebi.intact.model.clone.IntactCloner;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */

public class ExperimentEnricherTest extends IntactBasicTestCase {

    private ExperimentEnricher enricher;

    @Before
    public void beforeMethod() {
        enricher = ExperimentEnricher.getInstance();
    }

    @After
    public void afterMethod() {
        enricher.close();
        enricher = null;
    }


    @Test
    public void enrich_pub() {
        final String pubmedId = "15733859";

        Publication publication = getMockBuilder().createPublication(pubmedId);
        Experiment experiment = getMockBuilder().createExperimentEmpty();
        experiment.setPublication(publication);

        enricher.enrich(experiment);

        Assert.assertEquals("kang-2005", experiment.getShortLabel());
        Assert.assertEquals("The flexible loop of Bcl-2 is required for molecular interaction with immunosuppressant FK-506 binding protein 38 (FKBP38).", experiment.getFullName());
    }

    @Test
    public void enrich_hostOrganism() {
        Experiment experiment = getMockBuilder().createExperimentEmpty();
        experiment.getBioSource().setTaxId("83333");

        enricher.enrich(experiment);

        Assert.assertEquals("ecoli", experiment.getBioSource().getShortLabel());
    }

}