import java.util.GregorianCalendar;

import com.redbugz.macpaf.Fam;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Gender;
import com.redbugz.macpaf.Indi;
import com.redbugz.macpaf.Individual;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Sep 14, 2003
 * Time: 2:59:52 PM
 * To change this template use Options | File Templates.
 */
public class TestData {
  Individual primary = new Indi("Test Data", "Person", Gender.MALE);
  Individual spouse = new Indi("Spouse", "Maiden", Gender.FEMALE);
  Individual child1 = new Indi("Child One Son", "Person", Gender.MALE);
  Individual child2 = new Indi("Child Two Daughter", "Person", Gender.FEMALE);
  Individual mom = new Indi("Mother", "Maiden", Gender.FEMALE);
  Individual dad = new Indi("Father", "Person", Gender.MALE);
  Individual aunt = new Indi("Aunt \"Auntie\"", "Person", Gender.FEMALE);
  Individual paternalGrandpa = new Indi("Grandpa", "Person", Gender.MALE);
  Individual paternalGrandma = new Indi("Grandma", "Maiden", Gender.FEMALE);
  Individual paternalGrandma2 = new Indi("Grandma Two", "Maiden", Gender.FEMALE);
  Individual maternalGrandpa = new Indi("Gramps", "Maiden", Gender.MALE);
  Individual maternalGrandma = new Indi("Granny", "Da Silva", Gender.FEMALE);
  Family primaryFam = new Fam(primary, spouse, new Individual[] {child1, child2}
						   , new GregorianCalendar(2000, 6, 23).getTime());
  Family mom_dad = new Fam(dad, mom, new Individual[] {primary}
						   , null);
  Family paternalGrandparents = new Fam(paternalGrandpa, paternalGrandma, new Individual[] {aunt}
						 , null);
  Family paternalGrandparents2 = new Fam(paternalGrandpa, paternalGrandma2, new Individual[] {dad}
						  , null);
  Family maternalGrandparents = new Fam(maternalGrandpa, maternalGrandma, new Individual[] {mom}
							, null);
}
