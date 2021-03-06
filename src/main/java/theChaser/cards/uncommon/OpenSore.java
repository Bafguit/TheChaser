package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.OpenSorePower;
import theChaser.powers.SpaceOutPower;

import static theChaser.TheChaserMod.makeCardPath;

public class OpenSore extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Open Sore");
    public static final String IMG = makeCardPath("OpenSore.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int B = 3;

    public OpenSore() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, 0, B);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new OpenSorePower(p, this.magicNumber)));
    }


    @Override
    public void upgradeCard() {
        upgradeMagicNumber(2);
    }
}
