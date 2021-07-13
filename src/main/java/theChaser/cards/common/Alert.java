package theChaser.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.actions.AddCardPerTurnAction;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.FakeSorePower;

import java.util.ArrayList;

import static theChaser.TheChaserMod.makeCardPath;

public class Alert extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Alert");
    public static final String IMG = makeCardPath("Vigilance.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int UP_BLOCK = 3;
    private static final int MAGIC = 1;
    private static final int UP_MAGIC = 1;

    public Alert() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, BLOCK, MAGIC);
        loadJokeCardImage(this, "Alert");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new AddCardPerTurnAction(this.magicNumber));
        for(int i = 0; i < this.magicNumber; i++) {
            addToBot(new ApplyPowerAction(p, p, new FakeSorePower(p, 1), 1, true));
        }
    }

    @Override
    public void upgradeCard() {
        upgradeBlock(UP_BLOCK);
        upgradeMagicNumber(UP_MAGIC);
    }

}
