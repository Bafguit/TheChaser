//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theChaser.powers.BleedingPower;

public class InstantBleedingDamageAction extends AbstractGameAction {

    public InstantBleedingDamageAction(final AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if(!this.target.isDeadOrEscaped()) {
            if (this.target.hasPower(BleedingPower.POWER_ID)) {
                BleedingPower bleedingPower = (BleedingPower) this.target.getPower(BleedingPower.POWER_ID);
                bleedingPower.getBleedingDamage(this.amount);
            }
        }

        this.isDone = true;
    }
}
