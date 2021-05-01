//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theChaser.powers.BleedingPower;

public class RemoveStackStrAction extends AbstractGameAction {

    public RemoveStackStrAction(final AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if(!this.target.isDeadOrEscaped()) {
            this.target.addPower(new StrengthPower(this.target, -this.amount));
        }

        this.isDone = true;
    }
}
