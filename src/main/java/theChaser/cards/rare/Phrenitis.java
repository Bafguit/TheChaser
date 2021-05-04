package theChaser.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.BleedingPower;
import theChaser.powers.TargetPower;
import theChaser.powers.UnfortifiedPower;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom;
import static theChaser.TheChaserMod.makeCardPath;
// "How come this card extends CustomCard and not DynamicCard like all the rest?"
// Skip this question until you start figuring out the AbstractDefaultCard/AbstractDynamicCard and just extend DynamicCard
// for your own ones like all the other cards.

// Well every card, at the end of the day, extends CustomCard.
// Abstract Default Card extends CustomCard and builds up on it, adding a second magic number. Your card can extend it and
// bam - you can have a second magic number in that card (Learn Java inheritance if you want to know how that works).
// Abstract Dynamic Card builds up on Abstract Default Card even more and makes it so that you don't need to add
// the NAME and the DESCRIPTION into your card - it'll get it automatically. Of course, this functionality could have easily
// Been added to the default card rather than creating a new Dynamic one, but was done so to deliberately to showcase custom cards/inheritance a bit more.
public class Phrenitis extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Phrenitis");
    public static final String IMG = makeCardPath("Phrenitis.png");
    public static final String IMG_BETA = makeCardPath("beta/Phrenitis.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    public Phrenitis() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        loadJokeCardImage(this, IMG_BETA);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        /*
        ArrayList<AbstractMonster> mo = new ArrayList<>();

        for(AbstractMonster mm : getCurrRoom().monsters.monsters) {
            if(!mm.isDeadOrEscaped()) {
                mo.add(mm);
            }
        }

        int size = mo.size();

        addToBot(new ApplyPowerAction(mo.get(cardRandomRng.random.nextInt(size)), p, new WeakPower(mo.get(cardRandomRng.random.nextInt(size)), 1, false), 1, true));
        addToBot(new ApplyPowerAction(mo.get(cardRandomRng.random.nextInt(size)), p, new VulnerablePower(mo.get(cardRandomRng.random.nextInt(size)), 1, false), 1, true));
        addToBot(new ApplyPowerAction(mo.get(cardRandomRng.random.nextInt(size)), p, new PoisonPower(mo.get(cardRandomRng.random.nextInt(size)), p, 1), 1, true));
        addToBot(new ApplyPowerAction(mo.get(cardRandomRng.random.nextInt(size)), p, new BleedingPower(mo.get(cardRandomRng.random.nextInt(size)), p, 1), 1, true));
        addToBot(new ApplyPowerAction(mo.get(cardRandomRng.random.nextInt(size)), p, new TargetPower(mo.get(cardRandomRng.random.nextInt(size)), 1), 1, true));
        addToBot(new ApplyPowerAction(mo.get(cardRandomRng.random.nextInt(size)), p, new UnfortifiedPower(mo.get(cardRandomRng.random.nextInt(size)), p, 1), 1, true));
         */


        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 1, false), 1, true));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false), 1, true));
        addToBot(new ApplyPowerAction(m, p, new FrailPower(m, 1, false), 1, true));
        addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, 1), 1, true));
        addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, p, 1), 1, true));
        addToBot(new ApplyPowerAction(m, p, new TargetPower(m, 1), 1, true));
        addToBot(new ApplyPowerAction(m, p, new UnfortifiedPower(m, p, 1), 1, true));
    }

    @Override
    public void upgradeCard() {
        upgradeBaseCost(UP_COST);
    }

}
