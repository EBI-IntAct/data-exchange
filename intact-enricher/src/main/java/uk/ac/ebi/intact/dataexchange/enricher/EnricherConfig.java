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
package uk.ac.ebi.intact.dataexchange.enricher;

/**
 * Enricher config.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class EnricherConfig {

    private boolean updateOrganisms = true;
    private boolean updateProteins = true;
    private boolean updateSmallMolecules = true;
    private boolean updateExperiments = true;
    private boolean updateInteractionShortLabels = false;
    //private String oboUrl = "http://psidev.cvs.sourceforge.net/*checkout*/psidev/psi/mi/rel25/data/psi-mi25.obo";
    private String oboUrl = "http://psidev.cvs.sourceforge.net/viewvc/psidev/psi/mi/rel25/data/psi-mi25.obo";
    private boolean updateSmallMoleculeChebiXrefs = true;

    public EnricherConfig() {
    }

    public boolean isUpdateOrganisms() {
        return updateOrganisms;
    }

    public void setUpdateOrganisms(boolean updateOrganisms) {
        this.updateOrganisms = updateOrganisms;
    }

    public boolean isUpdateProteins() {
        return updateProteins;
    }

    public void setUpdateProteins(boolean updateProteins) {
        this.updateProteins = updateProteins;
    }

    public boolean isUpdateSmallMolecules() {
        return updateSmallMolecules;
    }

    public void setUpdateSmallMolecules( boolean updateSmallMolecules ) {
        this.updateSmallMolecules = updateSmallMolecules;
    }

    public String getOboUrl() {
        return oboUrl;
    }

    public void setOboUrl(String oboUrl) {
        this.oboUrl = oboUrl;
    }

    public boolean isUpdateInteractionShortLabels() {
        return updateInteractionShortLabels;
    }

    public void setUpdateInteractionShortLabels(boolean updateInteractionShortLabels) {
        this.updateInteractionShortLabels = updateInteractionShortLabels;
    }

    public boolean isUpdateExperiments() {
        return updateExperiments;
    }

    public void setUpdateExperiments(boolean updateExperiments) {
        this.updateExperiments = updateExperiments;
    }

    public boolean isUpdateSmallMoleculeChebiXrefs() {
        return updateSmallMoleculeChebiXrefs;
    }

    public void setUpdateSmallMoleculeChebiXrefs( boolean updateSmallMoleculeChebiXrefs ) {
        this.updateSmallMoleculeChebiXrefs = updateSmallMoleculeChebiXrefs;
    }
}