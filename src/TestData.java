
import java.util.GregorianCalendar;

import com.redbugz.macpaf.*;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Sep 14, 2003
 * Time: 2:59:52 PM
 * To change this template use Options | File Templates.
 */
public class TestData {
   Individual logan = new Indi("Logan Thomas", "Allred", Gender.MALE);
   Individual bec = new Indi("Rebecca Renee", "Beadle", Gender.FEMALE);
   Individual bryce = new Indi("Bryce Cory", "Allred", Gender.MALE);
   Individual jayden = new Indi("Jayden Kathleen", "Allred", Gender.FEMALE);
   Individual mom = new Indi("Janice", "Thompson", Gender.FEMALE);
   Individual dad = new Indi("Cory Wendell", "Allred", Gender.MALE);
   Individual bev = new Indi("Beverly", "Allred", Gender.FEMALE);
   Individual gpaAllred = new Indi("Wendell Union", "Allred", Gender.MALE);
   Individual gmaAllred = new Indi("Virginia", "Booth", Gender.FEMALE);
   Individual gmaNorma = new Indi("Norma Jean", "Hall", Gender.FEMALE);
   Individual gpaThompson = new Indi("Joseph Ralph", "Thompson", Gender.MALE);
   Individual gmaThompson = new Indi("Dorothy", "Wood", Gender.FEMALE);
   Family log_bec = new Fam(logan, bec, new Individual[]{bryce, jayden}, new GregorianCalendar(2000, 6, 23).getTime());
   Family mom_dad = new Fam(dad, mom, new Individual[]{logan}, null);
   Family units = new Fam(gpaAllred, gmaAllred, new Individual[]{bev}, null);
   Family units2 = new Fam(gpaAllred, gmaNorma, new Individual[]{dad}, null);
   Family thompson = new Fam(gpaThompson, gmaThompson, new Individual[]{mom}, null);

//   public void createTestData() {
//      assignIndividualToButton(logan, individualButton);
////         assignIndividualToButton(bec, spouseButton);
////         assignIndividualToButton(dad, fatherButton);
////         assignIndividualToButton(mom, motherButton);
////         assignIndividualToButton(gpaAllred, paternalGrandfatherButton);
////         assignIndividualToButton(gmaAllred, paternalGrandmotherButton);
////         assignIndividualToButton(gpaThompson, maternalGrandfatherButton);
////         assignIndividualToButton(gmaThompson, maternalGrandmotherButton);
//      familyList.add(log_bec);
//      familyList.add(mom_dad);
//      familyList.add(units);
//      familyList.add(units2);
//      familyList.add(thompson);
//      individualList.add(logan);
//      individualList.add(bec);
//      individualList.add(bryce);
//      individualList.add(jayden);
//      individualList.add(mom);
//      individualList.add(dad);
//      individualList.add(bev);
//      individualList.add(gpaAllred);
//      individualList.add(gpaThompson);
//      individualList.add(gmaAllred);
//      individualList.add(gmaNorma);
//      individualList.add(gmaThompson);
//      setIndividual(individualButton);
//   }
}
