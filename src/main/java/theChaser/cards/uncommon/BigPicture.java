package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.BigPicturePower;
import theChaser.powers.PenetrativePower;

import static theChaser.TheChaserMod.makeCardPath;

public class BigPicture extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Big Picture");
    public static final String IMG = makeCardPath("Power.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int DAMAGE = 1;

    public BigPicture() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, 0, DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BigPicturePower(p, this.magicNumber)));
    }

    @Override
    public void upgradeCard() {
        this.isInnate = true;
    }

}
