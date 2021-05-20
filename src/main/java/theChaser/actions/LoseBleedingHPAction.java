//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class LoseBleedingHPAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;
    private AbstractPower power;

    public LoseBleedingHPAction(AbstractPower power) {
        this.setValues(power.owner, power.owner, power.amount);
        this.actionType = ActionType.DAMAGE;
        this.power = power;
        this.attackEffect = AttackEffect.NONE;
        this.duration = 0.33F;
    }

    public void update() {
        if (this.duration == 0.33F && this.target.currentHealth > 0) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }

        this.tickDuration();
        if (this.isDone) {
            this.target.damage(new DamageInfo(this.source, this.amount, DamageType.HP_LOSS));
            if(this.amount > 1) {
                this.power.amount = 1;
            } else if(this.amount == 1) {
                this.addToBot(new RemoveSpecificPowerAction(this.source, this.source, this.power));
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }

    }
}
