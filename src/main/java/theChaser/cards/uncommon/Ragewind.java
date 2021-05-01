package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.blue.GeneticAlgorithm;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theChaser.TheChaserMod;
import theChaser.actions.IncreaseMagicAction;
import theChaser.actions.TargetAttackAction;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;

import static theChaser.TheChaserMod.makeCardPath;

public class Ragewind extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Ragewind");
    public static final String IMG = makeCardPath("Ragewind.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int MAGIC = 1;

    public Ragewind() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.misc = 1;
        this.magicNumber = this.baseMagicNumber = this.misc;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new IncreaseMiscAction(this.uuid, this.misc, 1));
        for(int i = 0; i < this.magicNumber; i++) {
            addToBot(new TargetAttackAction());
        }
    }

    public void applyPowers() {
        this.magicNumber = this.baseMagicNumber = this.misc;
        super.applyPowers();
        this.initializeDescription();
    }

    @Override
    public void upgradeCard() {
        upgradeBaseCost(UP_COST);
    }

}
