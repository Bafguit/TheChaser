//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class AddCardPerTurnAction extends AbstractGameAction {

    public AddCardPerTurnAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        ChaserUtil.addCardCountPerTurn(this.amount);

        this.isDone = true;
    }
}
