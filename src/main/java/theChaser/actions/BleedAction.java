//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class BleedAction extends AbstractGameAction {

    public BleedAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if(!this.target.isDeadOrEscaped()) {
            this.target.damage(new DamageInfo(null, this.amount, DamageInfo.DamageType.HP_LOSS));
        }

        this.isDone = true;
    }
}
