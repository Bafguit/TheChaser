package theChaser.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;

import static theChaser.TheChaserMod.makeCardPath;

public class Makeready extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Makeready");
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int DRAW = 2;
    private static final int UP_DRAW = 1;

    public Makeready() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, 0, DRAW);
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(ChaserUtil.isFirstCardPerTurn()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber + (ChaserUtil.isFirstCardPerTurn() ? 1 : 0)));
    }

    @Override
    public void upgradeCard() {
        upgradeMagicNumber(UP_DRAW);
    }

}
