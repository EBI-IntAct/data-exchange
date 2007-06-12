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
package uk.ac.ebi.intact.dataexchange.psimi.xml.persister;

import uk.ac.ebi.intact.persistence.util.CgLibUtil;

import java.util.*;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class PersisterReport {

    private Map<Class, Collection<ReportedIntactObject>> updated;
    private Map<Class, Collection<ReportedIntactObject>> created;
    private Map<Class, Collection<ReportedIntactObject>> ignored;

    public PersisterReport() {
    }

    public Map<Class, Collection<ReportedIntactObject>> getCreated() {
        if (created == null) {
            created = new HashMap<Class, Collection<ReportedIntactObject>>();
        }
        return created;
    }

    public void addCreated(ReportedIntactObject intactObject) {
        add(intactObject, getCreated());
    }

    public Map<Class, Collection<ReportedIntactObject>> getUpdated() {
        if (updated == null) {
            updated = new HashMap<Class, Collection<ReportedIntactObject>>();
        }
        return updated;
    }

    public void addUpdated(ReportedIntactObject intactObject) {
        add(intactObject, getUpdated());
    }

    public Map<Class, Collection<ReportedIntactObject>> getIgnored() {
        if (ignored == null) {
            ignored = new HashMap<Class, Collection<ReportedIntactObject>>();
        }
        return ignored;
    }

    public void addIgnored(ReportedIntactObject intactObject) {
        // a real ignored object cannot be in the created list (this would mean the object has been created just before,
        // so it should be considered as ignored)
        if (!mapContainsIntactObject(getCreated(), intactObject)) {
            add(intactObject, getIgnored());
        }
    }

    private void add(ReportedIntactObject intactObject, Map<Class, Collection<ReportedIntactObject>> map) {
        Class key = CgLibUtil.getRealClassName(intactObject);

        if (map.containsKey(key)) {
            map.get(key).add(intactObject);
        } else {
            Set<ReportedIntactObject> intactObjects = new HashSet<ReportedIntactObject>();
            intactObjects.add(intactObject);
            map.put(key, intactObjects);
        }
    }

    public void mergeWith(PersisterReport mergeReport) {
        for (Collection<ReportedIntactObject> intactObjects : mergeReport.getCreated().values()) {
            for (ReportedIntactObject intactObject : intactObjects) {
                addCreated(intactObject);
            }
        }
        for (Collection<ReportedIntactObject> intactObjects : mergeReport.getUpdated().values()) {
            for (ReportedIntactObject intactObject : intactObjects) {
                addUpdated(intactObject);
            }
        }
        for (Collection<ReportedIntactObject> intactObjects : mergeReport.getIgnored().values()) {
            for (ReportedIntactObject intactObject : intactObjects) {
                addIgnored(intactObject);
            }
        }
    }

    private boolean mapContainsIntactObject(Map<Class, Collection<ReportedIntactObject>> map, ReportedIntactObject intactObject) {
        Class key = CgLibUtil.getRealClassName(intactObject);

        if (map.containsKey(key)) {
            return map.get(key).contains(intactObject);
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PersisterReport { \n");
        sb.append("\tCREATED = " + mapToString(getCreated())).append("\n");
        sb.append("\tUPDATED = " + mapToString(getUpdated())).append("\n");
        sb.append("\tIGNORED = " + mapToString(getIgnored())).append("\n}");

        return sb.toString();
    }

    private String mapToString(Map<Class, Collection<ReportedIntactObject>> map) {
        StringBuilder sb = new StringBuilder();

        for (Iterator<Class> iterator = map.keySet().iterator(); iterator.hasNext();) {
            Class key = iterator.next();
            Collection<ReportedIntactObject> intactObjects = map.get(key);

            sb.append(key.getSimpleName()).append("(").append(intactObjects.size()).append(")");

            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }

        if (sb.length() == 0) {
            return "0";
        }

        return sb.toString();
    }

}