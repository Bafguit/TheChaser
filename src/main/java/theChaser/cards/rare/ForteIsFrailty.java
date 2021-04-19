package theChaser.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.ForteIsFrailtyPower;
import theChaser.powers.PenetrativePower;

import static theChaser.TheChaserMod.makeCardPath;

public class ForteIsFrailty extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Forte Is Frailty");
    public static final String IMG = makeCardPath("Power.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 3;
    private static final int UP_COST = 2;

    public ForteIsFrailty() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ForteIsFrailtyPower(p)));
    }

    @Override
    public void upgradeCard() {
        upgradeBaseCost(UP_COST);
    }

}
