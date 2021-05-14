package theChaser.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.actions.TargetAttackAction;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.BleedingPower;

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
public class Thornmail extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Thornmail");
    public static final String IMG = makeCardPath("Thornmail.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int UP_BLOCK = 2;
    private static final int BLD = 2;
    private static final int UP_BLD = 1;

    public Thornmail() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, BLOCK, BLD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, p, new BleedingPower(mo, p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void upgradeCard() {
        upgradeBlock(UP_BLOCK);
        upgradeMagicNumber(UP_BLD);
    }

}
