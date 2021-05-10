//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.cards.ChaserCard;
import theChaser.powers.TargetPower;

import java.util.ArrayList;
import java.util.Iterator;

public class RandomAttackAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractCard card;
    private boolean isTag = false;
    private boolean tagFirst = false;

    public RandomAttackAction(AbstractCard card, AttackEffect effect) {
        this.card = card;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.source = AbstractDungeon.player;
    }

    public RandomAttackAction(AbstractCard card, AttackEffect effect, boolean tagFirst) {
        this(card, effect);
        this.tagFirst = true;
    }

    public RandomAttackAction(AbstractCard card, AttackEffect effect, boolean isTag, int amt) {
        this(card, effect);
        this.isTag = isTag;
        this.amount = amt;
    }

    public void update() {
        if(ChaserUtil.canAttack() && this.tagFirst) {
            this.target = getRandomTagMonsters();
        } else {
            this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
        }
        if (this.target != null) {
            this.card.calculateCardDamage((AbstractMonster)this.target);
            this.info = new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn);
            this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
            if (this.isTag) {
                this.addToTop(new ApplyPowerAction(this.target, this.source, new TargetPower(this.target, this.amount), this.amount));
            }
        }

        this.isDone = true;
    }

    public static boolean areMonstersBasicallyDead() {
        Iterator var1 = ChaserUtil.getTarget().iterator();

        AbstractMonster m;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            m = (AbstractMonster)var1.next();
        } while(m.isDying || m.isEscaping);

        return false;
    }

    private static AbstractMonster getRandomTagMonsters() {
        if (!areMonstersBasicallyDead()) {
            ArrayList tmp;
            Iterator var5;
            AbstractMonster m;
            if (ChaserUtil.getTarget().size() > 1) {
                tmp = new ArrayList();
                var5 = ChaserUtil.getTarget().iterator();
                while (var5.hasNext()) {
                    m = (AbstractMonster) var5.next();
                    if (!m.halfDead && !m.isDying && !m.isEscaping) {
                        tmp.add(m);
                    }
                }

                if (tmp.size() <= 0) {
                    return null;
                } else {
                    return (AbstractMonster) tmp.get(AbstractDungeon.cardRandomRng.random(0, tmp.size() - 1));
                }
            } else if (ChaserUtil.getTarget().size() == 1) {
                return ChaserUtil.getTarget().get(0);
            }
        }

        return null;
    }
}
