package theChaser.patches.interfaces;

import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public interface OnTargetAttackSubscriber {
    int onTargetAttack(ArrayList<AbstractMonster> mo, int damage);
}