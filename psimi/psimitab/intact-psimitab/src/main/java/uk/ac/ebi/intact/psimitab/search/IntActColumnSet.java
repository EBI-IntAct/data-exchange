/**
 * 
 */
package uk.ac.ebi.intact.psimitab.search;

import psidev.psi.mi.search.column.ColumnSet;
import psidev.psi.mi.search.column.DefaultColumnSet;
import psidev.psi.mi.search.column.PsimiTabColumn;

import java.util.List;

/**
 * TODO comment this!
 * 
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 * @version $Id$
 * @since 2.0.0
 */
public class IntActColumnSet extends DefaultColumnSet implements ColumnSet {

	private List<PsimiTabColumn> psimiTabColumns;
	
	public static final PsimiTabColumn EXPERIMENTAL_ROLE_A = new PsimiTabColumn(16, "experimentalRole interactor A", "roleA");
	public static final PsimiTabColumn EXPERIMENTAL_ROLE_B = new PsimiTabColumn(17, "experimentalRole interactor B", "roleB");
	public static final PsimiTabColumn PROPERTIES_A = new PsimiTabColumn(18, "properties interactor A", "propertiesA");
	public static final PsimiTabColumn PROPERTIES_B = new PsimiTabColumn(19, "properties interactor B", "propertiesB");
	public static final PsimiTabColumn INTERACTOR_TYPE_A = new PsimiTabColumn(20, "interactorType of A", "typeA");
	public static final PsimiTabColumn INTERACTOR_TYPE_B = new PsimiTabColumn(21, "interactorType of B", "typeB");
	public static final PsimiTabColumn HOSTORGANISM = new PsimiTabColumn(22, "hostOrganism", "hostOrganism");
	public static final PsimiTabColumn EXPANSION_METHOD = new PsimiTabColumn(23, "expansion method", "expansion");

	
	
	public IntActColumnSet(){
		super();
		psimiTabColumns = super.getPsimiTabColumns();
		
		psimiTabColumns.add(EXPERIMENTAL_ROLE_A);
		psimiTabColumns.add(EXPERIMENTAL_ROLE_B);
		psimiTabColumns.add(PROPERTIES_A);
		psimiTabColumns.add(PROPERTIES_B);
		psimiTabColumns.add(INTERACTOR_TYPE_A);
		psimiTabColumns.add(INTERACTOR_TYPE_B);
		psimiTabColumns.add(HOSTORGANISM);	
		psimiTabColumns.add(EXPANSION_METHOD);
	}

}
