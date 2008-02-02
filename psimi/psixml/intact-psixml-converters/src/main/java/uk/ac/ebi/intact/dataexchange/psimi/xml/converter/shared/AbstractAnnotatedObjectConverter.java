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
package uk.ac.ebi.intact.dataexchange.psimi.xml.converter.shared;

import uk.ac.ebi.intact.dataexchange.psimi.xml.converter.AbstractIntactPsiConverter;
import uk.ac.ebi.intact.dataexchange.psimi.xml.converter.ConverterContext;
import uk.ac.ebi.intact.dataexchange.psimi.xml.converter.location.LocationItem;
import uk.ac.ebi.intact.dataexchange.psimi.xml.converter.util.ConversionCache;
import uk.ac.ebi.intact.dataexchange.psimi.xml.converter.util.PsiConverterUtils;
import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.model.util.CvObjectUtils;
import uk.ac.ebi.intact.model.util.XrefUtils;
import psidev.psi.mi.xml.model.HasId;
import psidev.psi.mi.xml.model.DbReference;

import java.util.Collection;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public abstract class AbstractAnnotatedObjectConverter<A extends AnnotatedObject, T> extends AbstractIntactPsiConverter<A, T> {

    private Class<? extends A> intactClass;
    private Class<T> psiClass;
    private boolean newIntactObjectCreated;
    private boolean newPsiObjectCreated;

    public AbstractAnnotatedObjectConverter(Institution institution, Class<? extends A> intactClass, Class<T> psiClass) {
        super(institution);
        this.intactClass = intactClass;
        this.psiClass = psiClass;
    }


    public A psiToIntact(T psiObject) {
        A intactObject = (A) ConversionCache.getElement(psiElementKey(psiObject));
        
        if (intactObject != null) {
            newIntactObjectCreated = false;
            return intactObject;
        }

        intactObject = newIntactObjectInstance(psiObject);

        if (!(intactObject instanceof Institution)) {
            intactObject.setOwner(getInstitution());
        }

        ConversionCache.putElement(psiElementKey(psiObject), intactObject);

        newIntactObjectCreated = true;

        return intactObject;
    }


    public T intactToPsi(A intactObject) {
        T psiObject = (T) ConversionCache.getElement(intactElementKey(intactObject));

        if (psiObject != null) {
            newPsiObjectCreated = false;
            return psiObject;
        }

        // ac - create a xref to the institution db
        if (intactObject.getAc() != null)  {
            boolean containsAcXref = false;
            for (Xref xref : (Collection<Xref>) intactObject.getXrefs()) {
                if (intactObject.getAc().equals(xref.getPrimaryId())) {
                    containsAcXref = true;
                    break;
                }
            }

            if (!containsAcXref) {
                CvXrefQualifier sourceRef = CvObjectUtils.createCvObject(getInstitution(), CvXrefQualifier.class,
                        CvXrefQualifier.SOURCE_REFERENCE_MI_REF, CvXrefQualifier.SOURCE_REFERENCE);
                CvDatabase db = CvObjectUtils.createCvObject(getInstitution(), CvDatabase.class,
                        getInstitutionPrimaryId(), getInstitution().getShortLabel());

                Xref xref = XrefUtils.newXrefInstanceFor(intactClass);
                xref.setCvXrefQualifier(sourceRef);
                xref.setCvDatabase(db);
                xref.setPrimaryId(intactObject.getAc());
                xref.setSecondaryId(intactObject.getShortLabel());
                intactObject.addXref(xref);
            }
        }

        psiObject = newInstance(psiClass);
        PsiConverterUtils.populate(intactObject, psiObject);


        ConversionCache.putElement(intactElementKey(intactObject), psiObject);

        newPsiObjectCreated = true;

        return psiObject;
    }

    protected A newIntactObjectInstance(T psiObject) {
        return newInstance(intactClass);
    }

    private static <T> T newInstance(Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return instance;
    }

    protected Object intactElementKey(A intactObject) {
        return intactObject;
        /*
        if (intactObject.getAc() != null) {
            return intactObject.getAc();
        }
        // no caching by default
        return null;  */
    }

    protected Object psiElementKey(T psiObject) {
        return psiObject;
       /* if (psiObject instanceof HasId) {
            return psiObject.getClass().getSimpleName()+":"+((HasId) psiObject).getId();
        }
        
        // no caching by default
        return null;     */
    }

    protected boolean isNewIntactObjectCreated() {
        return newIntactObjectCreated;
    }

    protected boolean isNewPsiObjectCreated() {
        return newPsiObjectCreated;
    }
}