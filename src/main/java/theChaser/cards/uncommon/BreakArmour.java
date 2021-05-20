package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.BleedingPower;
import theChaser.powers.TargetPower;

import static theChaser.TheChaserMod.makeCardPath;

public class BreakArmour extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Break Armour");
    public static final String IMG = makeCardPath("BreakArmour.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;

    public BreakArmour() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.hasPower(TargetPower.POWER_ID)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower power = m.getPower(TargetPower.POWER_ID);
        if(power != null) {
            int amt = m.getPower(TargetPower.POWER_ID).amount;
            if(this.upgraded) {
                addToBot(new RemoveSpecificPowerAction(m, p, m.getPower(TargetPower.POWER_ID)));
            }
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, amt, false)));
            addToBot(new ApplyPowerAction(m, p, new WeakPower(m, amt, false)));
            addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, p, amt)));
        }
    }

    @Override
    public void upgradeCard() {
    }

}
