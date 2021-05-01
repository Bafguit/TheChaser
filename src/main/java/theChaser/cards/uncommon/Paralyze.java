package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;

import static theChaser.TheChaserMod.makeCardPath;

public class Paralyze extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Paralyze");
    public static final String IMG = makeCardPath("Paralyze.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;

    public Paralyze() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        if(m.powers.size() > 0) {
            for (AbstractPower power : m.powers) {
                if (power.type == AbstractPower.PowerType.DEBUFF) {
                    count++;
                }
            }
        }

        int temp = -count - (this.upgraded ? 1 : 0);
        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, temp), temp));
    }

    @Override
    public void upgradeCard() {
    }

}
