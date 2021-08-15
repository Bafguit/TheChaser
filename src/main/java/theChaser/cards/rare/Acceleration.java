package theChaser.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.AccelerationPower;
import theChaser.powers.SpaceOutPower;

import static theChaser.TheChaserMod.makeCardPath;

public class Acceleration extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Acceleration");
    public static final String IMG = makeCardPath("Acceleration.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 2;
    private static final int STR = 1;

    public Acceleration() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, 0, STR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AccelerationPower(p, this.magicNumber)));
    }


    @Override
    public void upgradeCard() {
        upgradeBaseCost(1);
    }
}
