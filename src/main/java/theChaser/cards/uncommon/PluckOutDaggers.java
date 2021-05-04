package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Eviscerate;
import com.megacrit.cardcrawl.cards.red.BloodForBlood;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.patches.interfaces.OnAfterTargetAttackSubscriber;

import java.util.ArrayList;

import static theChaser.TheChaserMod.makeCardPath;

public class PluckOutDaggers extends ChaserCard implements OnAfterTargetAttackSubscriber {

    public static final String ID = TheChaserMod.makeID("Pluck Out Daggers");
    public static final String IMG = makeCardPath("PluckOutDaggers.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 3;
    private static final int DAMAGE = 6;
    private static final int UP_TAG = 2;
    private static final int TAG = 3;

    public PluckOutDaggers() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, TAG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SLASH_VERTICAL, true));
        }
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_TAG);
    }

    @Override
    public void atTurnStart() {
        this.resetAttributes();
        this.applyPowers();
    }

    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        this.setCostForTurn(this.cost - ChaserUtil.getTargetAttackCountPerTurn());
    }

    @Override
    public void onAfterTargetAttack(ArrayList<AbstractMonster> mo, int damage) {
        this.setCostForTurn(this.costForTurn - 1);
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractCard tmp = new PluckOutDaggers();
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.setCostForTurn(this.cost - ChaserUtil.getTargetAttackCountPerTurn());
        }

        return tmp;
    }
}
