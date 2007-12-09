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

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.dataexchange.imex.repository.dao.RepoEntrySetDao;
import uk.ac.ebi.intact.dataexchange.imex.repository.dao.RepoEntrySetService;
import uk.ac.ebi.intact.dataexchange.imex.repository.model.RepoEntrySet;

import java.util.List;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class JpaRepoEntrySetService implements RepoEntrySetService
{

    private RepoEntrySetDao entrySetDao;

    public void setEntrySetDao(RepoEntrySetDao entrySetDao) {
        this.entrySetDao = entrySetDao;
    }

    public List<RepoEntrySet> findAllRepoEntrySets() {
        return entrySetDao.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRepoEntrySet(RepoEntrySet entrySet) {
        entrySetDao.save(entrySet);
    }

    public RepoEntrySet findByName(String name) {
        return entrySetDao.findByName(name);
    }

    public Long countAll() {
        return entrySetDao.countAll();
    }
}