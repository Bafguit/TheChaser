package theChaser.cards.common;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.cards.temp.ThrowingKnife;
import theChaser.characters.TheChaser;

import static theChaser.TheChaserMod.makeCardPath;

public class RandomThrow extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("RandomThrow");
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int TAG = 3;
    private static final int UP_TAG = 1;

    public RandomThrow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, 0, TAG);
        this.cardsToPreview = new ThrowingKnife();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MakeTempCardInHandAction(new ThrowingKnife(), this.magicNumber));
    }

    @Override
    public void upgradeCard() {
        upgradeMagicNumber(UP_TAG);
    }

}
