package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.UnfortifiedPower;

import static theChaser.TheChaserMod.makeCardPath;

public class FeebleStrike extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Feeble Strike");
    public static final String IMG = makeCardPath("FeebleStrike.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int MULTI = 3;
    private static final int UP_MULTI = 1;

    private boolean isWeak = false;

    public FeebleStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, MULTI);
        this.tags.add(CardTags.STRIKE);
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.isWeak = AbstractDungeon.player.hasPower(WeakPower.POWER_ID);
        this.baseDamage *= this.isWeak ? this.baseMagicNumber : 1;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage *= this.isWeak ? this.baseMagicNumber : 1;
        super.calculateCardDamage(m);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (this.isWeak) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.damage *= this.isWeak ? this.magicNumber : 1;
        this.calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), this.isWeak ? AttackEffect.SLASH_HEAVY : AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgradeCard() {
        upgradeMagicNumber(UP_MULTI);
    }

}
