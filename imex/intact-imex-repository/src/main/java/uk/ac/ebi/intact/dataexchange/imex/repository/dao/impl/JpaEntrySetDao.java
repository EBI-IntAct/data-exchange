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
package uk.ac.ebi.intact.dataexchange.imex.repository.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.dataexchange.imex.repository.dao.EntrySetDao;
import uk.ac.ebi.intact.dataexchange.imex.repository.model.EntrySet;
import uk.ac.ebi.intact.dataexchange.imex.repository.model.Provider;

import javax.persistence.Query;
import java.util.List;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public class JpaEntrySetDao extends JpaImexDaoSupport implements EntrySetDao {

    private final String QUERY_ALL = "select e from EntrySet e";

    public void save(EntrySet entrySet) {
        getEntityManager().persist(entrySet);
    }

    public List<EntrySet> findAll() {
        return getEntityManager().createQuery(QUERY_ALL).getResultList();
    }

    public EntrySet findByName(String name) {
        Query query = getEntityManager().createNamedQuery("entrySetByName");
        query.setParameter("name", name);
        return (EntrySet) query.getSingleResult();
    }
}