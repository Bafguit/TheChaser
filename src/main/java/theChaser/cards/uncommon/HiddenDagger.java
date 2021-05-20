package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.cards.temp.ThrowingKnife;
import theChaser.characters.TheChaser;

import static theChaser.TheChaserMod.makeCardPath;

public class HiddenDagger extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Hidden Dagger");
    public static final String IMG = makeCardPath("HiddenDagger.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 0;
    private static final int BLOCK = 3;
    private static final int AMOUNT = 1;
    private static final int UP_AMOUNT = 1;

    public HiddenDagger() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, BLOCK, AMOUNT);
        this.cardsToPreview = new ThrowingKnife();
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
        addToBot(new GainBlockAction(p, this.block));
        ChaserCard temp = new ThrowingKnife();
        if(ChaserUtil.isAccel()) temp.upgrade();
        addToBot(new MakeTempCardInHandAction(temp, this.magicNumber));
        if(ChaserUtil.isAccel()) {
            for(AbstractCard c : p.hand.group) {
                if(c instanceof ThrowingKnife && !c.upgraded) {
                    c.upgrade();
                }
            }
        }
    }

    @Override
    public void upgradeCard() {
        upgradeMagicNumber(UP_AMOUNT);
    }

}
