//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.patches.interfaces;

import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public interface OnAfterTargetAttackSubscriber {
    void onAfterTargetAttack(ArrayList<AbstractMonster> mo, int damage);
}
