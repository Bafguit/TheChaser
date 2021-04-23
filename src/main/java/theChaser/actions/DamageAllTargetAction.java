//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theChaser.patches.interfaces.OnAfterTargetAttackSubscriber;
import theChaser.powers.TargetPower;

import java.util.ArrayList;

public class DamageAllTargetAction extends AbstractGameAction {
    public int damage;
    private boolean firstFrame;
    private ArrayList<AbstractMonster> monsters;

    public DamageAllTargetAction(int amount, ArrayList<AbstractMonster> mo) {
        this.firstFrame = true;
        this.source = AbstractDungeon.player;
        this.damage = amount;
        this.monsters = mo;
        this.actionType = ActionType.DAMAGE;
        this.damageType = DamageType.NORMAL;
        this.attackEffect = AttackEffect.BLUNT_LIGHT;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.firstFrame) {
            boolean playedMusic = false;

            for(AbstractMonster m : this.monsters) {
                if (!m.isDeadOrEscaped() && m.currentHealth > 0 ) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect, true));
                    } else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect));
                    }
                }
            }

            this.firstFrame = false;
        }

        this.tickDuration();
        if (this.isDone) {

            for(AbstractMonster m : this.monsters) {
                if (!m.isDeadOrEscaped() && m.hasPower(TargetPower.POWER_ID)) {
                    m.getPower(TargetPower.POWER_ID).flash();
                    m.damage(new DamageInfo(this.source, this.damage, this.damageType));
                }
            }

            for(AbstractRelic r : AbstractDungeon.player.relics) {
                if(r instanceof OnAfterTargetAttackSubscriber) {
                    ((OnAfterTargetAttackSubscriber) r).onAfterTargetAttack(this.monsters, this.damage);
                }
            }

            for(AbstractPower p : AbstractDungeon.player.powers) {
                if(p instanceof OnAfterTargetAttackSubscriber) {
                    ((OnAfterTargetAttackSubscriber) p).onAfterTargetAttack(this.monsters, this.damage);
                }
            }

            for(AbstractCard c : AbstractDungeon.player.hand.group) {
                if(c instanceof OnAfterTargetAttackSubscriber) {
                    ((OnAfterTargetAttackSubscriber) c).onAfterTargetAttack(this.monsters, this.damage);
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

    }
}
