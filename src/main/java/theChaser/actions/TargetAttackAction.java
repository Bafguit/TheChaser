//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class TargetAttackAction extends AbstractGameAction {

    public TargetAttackAction() {
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        ChaserUtil.attack();

        this.isDone = true;
    }
}
