/*
 * Copyright 2001-2008 The European Bioinformatics Institute.
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
package uk.ac.ebi.intact.psimitab;

import psidev.psi.mi.tab.converter.xml2tab.Xml2Tab;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.model.EntrySet;

import java.util.Collection;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class IntactXml2Tab extends Xml2Tab {

    public IntactXml2Tab( OntologyNameFinder finder ) {
        super(new IntactInteractionConverter( finder ) );
    }

    public IntactXml2Tab() {
        super(new IntactInteractionConverter() );
    }

    @Override
    protected void processAfterConversion(BinaryInteraction binaryInteraction, boolean expanded) {
        if (expanded) {
            ((IntactBinaryInteraction)binaryInteraction).getExpansionMethods().add(getExpansionStrategy().getName());
        }
    }
}