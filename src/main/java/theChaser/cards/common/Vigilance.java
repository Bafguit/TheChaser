package theChaser.cards.common;

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

import java.util.ArrayList;

import static theChaser.TheChaserMod.makeCardPath;

public class Vigilance extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Vigilance");
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int UP_BLOCK = 3;
    private static final int MAGIC = 1;
    private static final int UP_MAGIC = 1;

    public Vigilance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, BLOCK, MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new AddCardPerTurnAction(this.magicNumber));
    }

    @Override
    public void upgradeCard() {
        upgradeBlock(UP_BLOCK);
        upgradeMagicNumber(UP_MAGIC);
    }

}
