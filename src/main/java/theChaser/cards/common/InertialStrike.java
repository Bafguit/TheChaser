package theChaser.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.EndlessAgony;
import com.megacrit.cardcrawl.cards.purple.Brilliance;
import com.megacrit.cardcrawl.cards.purple.DeusExMachina;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.EternalFeather;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.TargetPower;

import static theChaser.TheChaserMod.makeCardPath;

public class InertialStrike extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Inertial Strike");
    public static final String IMG = makeCardPath("Identify.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int DAMAGE = 7;

    public InertialStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, 0);
        this.tags.add(CardTags.STRIKE);
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseMagicNumber = ChaserUtil.getCardCountPerTurn();
        this.baseDamage += this.baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
        this.rawDescription = this.extendedDescription[0] + ChaserUtil.getCardCountPerTurn() + this.extendedDescription[1];
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //int ca = ChaserUtil.getCardCountPerTurn();
        this.damage += this.magicNumber;
        this.calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SLASH_HEAVY));
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster m) {
        this.baseMagicNumber = ChaserUtil.getCardCountPerTurn();
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.baseMagicNumber;
        super.calculateCardDamage(m);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void upgradeCard() {
        this.upgradeDamage(3);
    }

}
