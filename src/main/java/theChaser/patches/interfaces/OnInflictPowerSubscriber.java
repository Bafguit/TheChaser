//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.patches.interfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public interface OnInflictPowerSubscriber {
    AbstractPower onInflictPower(AbstractPower power, AbstractCreature target, AbstractCreature source);
}
