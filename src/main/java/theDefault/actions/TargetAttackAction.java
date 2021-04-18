//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theDefault.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class TargetAttackAction extends AbstractGameAction {
    private DamageInfo info;
    private boolean isTag = false;

    public TargetAttackAction(DamageInfo info, AttackEffect effect) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.source = info.owner;
    }

    public TargetAttackAction(DamageInfo info, AttackEffect effect, boolean isTag, int amt) {
        this(info, effect);
        this.isTag = isTag;
        this.amount = amt;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
            if(this.isTag) {
                this.addToTop(new ApplyPowerAction(this.target, this.source, new VulnerablePower(this.target, this.amount, !this.source.isPlayer)));
            }
        }

        this.isDone = true;
    }
}
