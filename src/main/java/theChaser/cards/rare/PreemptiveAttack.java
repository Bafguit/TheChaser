package theChaser.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.PreemptiveAttackPower;
import theChaser.powers.UnwaryPower;

import static theChaser.TheChaserMod.makeCardPath;

public class PreemptiveAttack extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Preemptive Attack");
    public static final String IMG = makeCardPath("PreemptiveAttack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    public PreemptiveAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PreemptiveAttackPower(p, 1)));
    }


    @Override
    public void upgradeCard() {
        upgradeBaseCost(UP_COST);
    }
}
