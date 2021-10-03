package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;

import static theChaser.TheChaserMod.makeCardPath;

public class FeebleDefend extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Feeble Defend");
    public static final String IMG = makeCardPath("FeebleDefend.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int MULTI = 3;
    private static final int UP_MULTI = 1;

    private boolean isFrail = false;

    public FeebleDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, BLOCK, MULTI);
    }

    public void applyPowers() {
        int realBaseBlock = this.baseBlock;
        this.isFrail = AbstractDungeon.player.hasPower(FrailPower.POWER_ID);
        this.baseBlock *= this.isFrail ? this.baseMagicNumber : 1;
        super.applyPowers();
        this.baseBlock = realBaseBlock;
        this.isBlockModified = this.block != this.baseBlock;
        this.initializeDescription();
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (this.isFrail) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.block *= this.isFrail ? this.baseMagicNumber : 1;
        this.applyPowers();
        addToBot(new GainBlockAction(p, this.block));
    }

    @Override
    public void upgradeCard() {
        upgradeMagicNumber(UP_MULTI);
    }

}
