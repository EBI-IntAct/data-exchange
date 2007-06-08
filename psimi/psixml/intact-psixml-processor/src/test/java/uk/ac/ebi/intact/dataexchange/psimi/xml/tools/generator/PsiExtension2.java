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
package uk.ac.ebi.intact.dataexchange.psimi.xml.tools.generator;

import psidev.psi.mi.xml.model.Interaction;
import uk.ac.ebi.intact.dataexchange.psimi.xml.tools.extension.annotation.PsiExtension;
import uk.ac.ebi.intact.dataexchange.psimi.xml.tools.extension.annotation.PsiExtensionMethod;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@PsiExtension(forClass = Interaction.class)
public class PsiExtension2 {

    @PsiExtensionMethod
    public void insertIntoDatabase() {
        System.out.println("MyMethod Extension");
    }

    @PsiExtensionMethod
    public void aThirdMethod() throws Exception {
        System.out.println("MyMethod Extension");

        throw new Exception();
    }

}