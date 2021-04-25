//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Iterator;

public class SecretCardAction extends AbstractGameAction {
    private int tarAtk;

    public SecretCardAction(int tarAtk) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.tarAtk = tarAtk;
    }

    public void update() {
        Iterator var1 = DrawCardAction.drawnCards.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type == CardType.ATTACK) {
                for(int i = 0; i < this.tarAtk; i++) {
                    AbstractDungeon.actionManager.addToTop(new TargetAttackAction());
                }
                break;
            }
        }

        this.isDone = true;
    }
}
