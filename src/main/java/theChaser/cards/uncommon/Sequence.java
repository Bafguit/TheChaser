package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.cards.temp.SequenceFlow;
import theChaser.characters.TheChaser;

import java.util.ArrayList;

import static theChaser.TheChaserMod.makeCardPath;

public class Sequence extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Sequence");
    public static final String IMG = makeCardPath("Sequence.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 0;
    private static final int DAMAGE = 3;
    private static final int UP_DAMAGE = 1;
    private static final int MAGIC = 1;

    public Sequence() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, MAGIC);
        this.cardsToPreview = new SequenceFlow();
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(ChaserUtil.isAccel()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if(ChaserUtil.isAccel()) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        ChaserCard temp = new SequenceFlow();
        addToBot(new MakeTempCardInHandAction(temp, this.magicNumber));
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_DAMAGE);
        upgradeMagicNumber(1);
    }

}
