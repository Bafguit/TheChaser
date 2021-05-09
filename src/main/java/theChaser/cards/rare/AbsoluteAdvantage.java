package theChaser.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;

import java.util.ArrayList;

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
public class AbsoluteAdvantage extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Absolute Advantage");
    public static final String IMG = makeCardPath("AbsoluteAdvantage.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 0;
    private static final int ENERGY = 1;
    private static final int UP_ENERGY = 1;

    public AbsoluteAdvantage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, 0, ENERGY);
        this.exhaust = true;
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(getDebuffs() > 0) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void applyPowers() {
        super.applyPowers();

        if(Settings.language.name().equals("KOR")) {
            this.rawDescription = (this.upgraded ? this.extendedDescription[2] : "") + this.extendedDescription[0] + getDebuffs() + this.extendedDescription[1];

            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(this.magicNumber));

        int count = getDebuffs();
        if(count > 0) {
            addToBot(new DrawCardAction(p, count));
        }
    }

    private int getDebuffs() {
        ArrayList<AbstractPower> ps = new ArrayList<>();

        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for(AbstractPower power : mo.powers) {
                if(power.type == AbstractPower.PowerType.DEBUFF && !ps.contains(power)) {
                    ps.add(power);
                }
            }
        }

        return ps.size();
    }

    @Override
    public void upgradeCard() {
        upgradeMagicNumber(UP_ENERGY);
    }

}
