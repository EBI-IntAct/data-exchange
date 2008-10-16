/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
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
package uk.ac.ebi.intact.psimitab.util;

import psidev.psi.mi.tab.model.Interactor;
import uk.ac.ebi.intact.psimitab.IntactBinaryInteraction;
import uk.ac.ebi.intact.psimitab.model.ExtendedInteractor;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class IntactOnlyOneInteractorHandler extends IntactBinaryInteractionHandler {

    @Override
    protected IntactBinaryInteraction newBinaryInteraction(Interactor i1, Interactor i2) {
        return super.newBinaryInteraction(i1, new ExtendedInteractor());
    }

    @Override
    protected Interactor mergeInteractorA(Interactor i1, Interactor i2) {
        return cloneInteractor(i1);
    }

    @Override
    protected Interactor mergeInteractorB(Interactor i1, Interactor i2) {
        return new ExtendedInteractor();
    }
}
