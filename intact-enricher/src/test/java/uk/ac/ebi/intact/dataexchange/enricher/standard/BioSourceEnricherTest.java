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
import uk.ac.ebi.intact.model.BioSource;
import uk.ac.ebi.intact.dataexchange.enricher.EnricherException;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class BioSourceEnricherTest extends IntactBasicTestCase {

     private BioSourceEnricher enricher;

    @Before
    public void beforeMethod() {
        enricher = BioSourceEnricher.getInstance();
    }

    @After
    public void afterMethod() {
        enricher.close();
        enricher = null;
    }

    @Test
    public void enrich_default() throws Exception {
        BioSource human = getMockBuilder().createBioSource(9606, "unknown");

        enricher.enrich(human);

        Assert.assertEquals("human", human.getShortLabel());
    }

    @Test
    public void enrich_noCommonName() throws Exception {
        BioSource unculturedBacterium = getMockBuilder().createBioSource(77133, "unknown");

        enricher.enrich(unculturedBacterium);

        Assert.assertEquals("uncultured bacterium", unculturedBacterium.getShortLabel());
    }

    @Test (expected = EnricherException.class)
    public void enrich_invalidTaxid() throws Exception {
        BioSource invalidBioSource = getMockBuilder().createBioSource(0, "NONE");
         enricher.enrich(invalidBioSource);
    }
}