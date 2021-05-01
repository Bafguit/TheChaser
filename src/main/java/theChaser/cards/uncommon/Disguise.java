package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;

import static theChaser.TheChaserMod.makeCardPath;

public class Disguise extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Disguise");
    public static final String IMG = makeCardPath("Disguise.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 2;
    private static final int BLOCK = 8;
    private static final int UP_BLOCK = 2;

    public Disguise() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, BLOCK, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block, true));
        if(ChaserUtil.getPlayerDebuffAmount() > 0) {
            for(int i = 0; i < ChaserUtil.getPlayerDebuffAmount(); i++) {
                addToBot(new GainBlockAction(p, this.block, true));
            }
        }
    }

    @Override
    public void upgradeCard() {
        upgradeBlock(UP_BLOCK);
    }

}
