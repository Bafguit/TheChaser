//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
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
    private AbstractPower power;
    private boolean isInstant;

    public LoseBleedingHPAction(AbstractPower power, boolean isInstant) {
        this.setValues(power.owner, power.owner, power.amount);
        this.actionType = ActionType.DAMAGE;
        this.power = power;
        this.attackEffect = AttackEffect.NONE;
        this.isInstant = isInstant;
        this.duration = this.startDuration = isInstant ? 0.1F : 0.33F;
    }

    public void update() {

        this.tickDuration();
        if (this.isDone) {
            this.target.damage(new DamageInfo(this.source, this.amount, DamageType.HP_LOSS));
            if(!this.isInstant) {
                this.addToBot(new ReducePowerAction(this.source, this.source, this.power, MathUtils.ceil(this.amount / 2)));
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
