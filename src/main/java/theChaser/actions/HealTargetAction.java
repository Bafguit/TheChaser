//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class HealTargetAction extends AbstractGameAction {
    public HealTargetAction(AbstractCreature target) {
        this.target = target;
        this.startDuration = this.duration;
        if (Settings.FAST_MODE) {
            this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.actionType = ActionType.HEAL;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            this.target.heal(ChaserUtil.getTargetAttackCountPerTurn());
        }

        this.tickDuration();
    }
}
