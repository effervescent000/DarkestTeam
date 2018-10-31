/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import static darkestteam.RandomFunctions.getRandomNumberInRange;
import javafx.collections.ObservableList;

/**
 *
 * @author Tara
 */
public class ChooseTarget {

    private static Enemy returnEnemy;
    private static Hero returnHero;

    public ChooseTarget() {
    }

    public static Hero chooseHero(int front, int back) {
        ObservableList<Hero> roster = Combat.getHeroRoster();
        //to use this, we pass an array of valid positions of targets and our current
        //roster of heroes, sorted by their position
//        returnHero = null;
//        while (returnHero == null) {
//            ChooseTarget.returnHero = posMap.get(getRandomNumberInRange(front, back));
//        }
//        return returnHero;

//        int posRand = getRandomNumberInRange(front, back);
//
//        Hero hBack = Combat.getHeroInPosition(back);
//        Hero hFront = Combat.getHeroInPosition(front);
//
//        if (back - front > 1) {
//            ChooseTarget.returnHero = Combat.getHeroInPosition(posRand);
//            if (returnHero == null) {
//                if (hBack == null) {
//                    return hFront;
//                } else {
//                    return hBack;
//                }
//            } else {
//                return returnHero;
//            }
//        } else if (Combat.getHeroInPosition(posRand) != null) {
//            return Combat.getHeroInPosition(posRand);
//        } else if (hFront == null) {
//            return hBack;
//        } else {
//            return hFront;
//        }
        //+++++++++
        //new version
        int pos = getRandomNumberInRange(front, back);
        int spread = back - front;

        if (back > roster.size()) {
            if (spread > 1) {
                if (roster.size() > front) {
                    pos = getRandomNumberInRange(front, roster.size());
                    returnHero = Combat.getHeroInPosition(pos);
                    return returnHero;
                } else if (roster.size() == front) {
                    returnHero = Combat.getHeroInPosition(front);
                    return returnHero;
                }
            } else if (roster.size() > front) {
                returnHero = Combat.getHeroInPosition(front);
                return returnHero;
            }
        } else {
            returnHero = Combat.getHeroInPosition(pos);
            return returnHero;
        }
        return null; //this return statement shouldn't ever fire but it's showing an error without it

    }

    public static Enemy chooseEnemy(int front, int back) {

        ObservableList<Enemy> roster = Zone.getEnemyRoster();
        int pos = getRandomNumberInRange(front, back);

//        Enemy enBack = Combat.getEnemyInPosition(back);
//        Enemy enFront = Combat.getEnemyInPosition(front);
//
//        if (back - front > 1) {
//            ChooseTarget.returnEnemy = Combat.getEnemyInPosition(posRand);
//            if (returnEnemy == null) {
//                if (enBack == null) {
//                    return enFront;
//                } else {
//                    return enBack;
//                }
//            } else {
//                return returnEnemy;
//            }
//        } else if (Combat.getEnemyInPosition(posRand) != null) {
//            return Combat.getEnemyInPosition(posRand);
//        } else if (enFront == null) {
//            return enBack;
//        } else {
//            return enFront;
//        }
        //+++++++++++++++++++++++++++
        int spread = back - front;

        if (back > roster.size()) {
            if (spread > 1) {
                if (roster.size() > front) {
                    pos = getRandomNumberInRange(front, roster.size());
                    returnEnemy = Combat.getEnemyInPosition(pos);
                    return returnEnemy;
                } else if (roster.size() == front) {
                    returnEnemy = Combat.getEnemyInPosition(front);
                    return returnEnemy;
                }
            } else if (roster.size() > front) {
                returnEnemy = Combat.getEnemyInPosition(front);
                return returnEnemy;
            }
        } else {
            returnEnemy = Combat.getEnemyInPosition(pos);
            return returnEnemy;
        }
        return null; //this return statement shouldn't ever fire but it's showing an error without it
    }

}
