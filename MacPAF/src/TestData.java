import java.util.*;

import com.redbugz.macpaf.*;
import com.redbugz.macpaf.test.*;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Sep 14, 2003
 * Time: 2:59:52 PM
 * To change this template use Options | File Templates.
 */
public class TestData {
  Individual primary = new MyIndividual("Test Data", "Person", Gender.MALE);
  Individual spouse = new MyIndividual("Spouse", "Maiden", Gender.FEMALE);
  Individual child1 = new MyIndividual("Child One Son", "Person", Gender.MALE);
  Individual child2 = new MyIndividual("Child Two Daughter", "Person", Gender.FEMALE);
  Individual mom = new MyIndividual("Mother", "Maiden", Gender.FEMALE);
  Individual dad = new MyIndividual("Father", "Person", Gender.MALE);
  Individual aunt = new MyIndividual("Aunt \"Auntie\"", "Person", Gender.FEMALE);
  Individual paternalGrandpa = new MyIndividual("Grandpa", "Person", Gender.MALE);
  Individual paternalGrandma = new MyIndividual("Grandma", "Maiden", Gender.FEMALE);
  Individual paternalGrandma2 = new MyIndividual("Grandma Two", "Maiden", Gender.FEMALE);
  Individual maternalGrandpa = new MyIndividual("Gramps", "Maiden", Gender.MALE);
  Individual maternalGrandma = new MyIndividual("Granny", "Da Silva", Gender.FEMALE);
  Family primaryFam = new TestFamily(primary, spouse, new Individual[] {child1, child2}
						   , new GregorianCalendar(2000, 6, 23).getTime());
  Family mom_dad = new TestFamily(dad, mom, new Individual[] {primary}
						   , null);
  Family paternalGrandparents = new TestFamily(paternalGrandpa, paternalGrandma, new Individual[] {aunt}
						 , null);
  Family paternalGrandparents2 = new TestFamily(paternalGrandpa, paternalGrandma2, new Individual[] {dad}
						  , null);
  Family maternalGrandparents = new TestFamily(maternalGrandpa, maternalGrandma, new Individual[] {mom}
							, null);
}
