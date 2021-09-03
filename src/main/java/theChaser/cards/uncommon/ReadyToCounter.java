package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theChaser.TheChaserMod;
import theChaser.actions.InterfaceXCostAction;
import theChaser.actions.TargetAttackAction;
import theChaser.cards.ChaserCard;
import theChaser.cards.temp.ThrowingKnife;
import theChaser.characters.TheChaser;
import theChaser.patches.interfaces.OnXCostSubscriber;
import theChaser.powers.TargetPower;

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
public class ReadyToCounter extends ChaserCard implements OnXCostSubscriber {

    public static final String ID = TheChaserMod.makeID("Ready To Counter");
    public static final String IMG = makeCardPath("ReadyToCounter.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = -1;
    private static final int DMG = 6;
    private static final int UP_DMG = 2;

    public ReadyToCounter() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, DMG, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new InterfaceXCostAction(this, m));
    }

    @Override
    public void upgradeCard() {
        upgradeBlock(UP_DMG);
    }

    @Override
    public void onXCost(AbstractPlayer p, AbstractMonster m, int x) {
        for(int i = 0; i < x; i++) {
            addToTop(new TargetAttackAction());
        }

        for(int i = 0; i < x; i++) {
            addToTop(new GainBlockAction(p, this.block, true));
        }
    }
}
