package theChaser.cards.temp;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Acrobatics;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;

import static theChaser.TheChaserMod.makeCardPath;

public class AcrobaticsAlt extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Acrobatics");
    public static final String IMG = makeCardPath("Acrobatics.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int AMOUNT = 3;
    private static final int UP_AMOUNT = 1;

    public AcrobaticsAlt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, 0, AMOUNT);
        this.exhaust = true;
    }

    public void onChoseThisOption() {
        this.addToBot(new MakeTempCardInHandAction(this));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
        this.addToBot(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, 1, false));
    }

    @Override
    public void upgradeCard() {
        upgradeMagicNumber(UP_AMOUNT);
    }

}
