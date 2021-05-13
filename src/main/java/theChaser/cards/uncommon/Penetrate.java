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
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.TargetPower;
import theChaser.powers.UnfortifiedPower;

import static theChaser.TheChaserMod.makeCardPath;

public class Penetrate extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Penetrate");
    public static final String IMG = makeCardPath("Penetrate.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UP_DMG = 2;
    private static final int TAG = 2;
    private static final int UP_TAG = 1;

    public Penetrate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, TAG);
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.hasPower(VulnerablePower.POWER_ID)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.BLUNT_LIGHT));
        if(m.hasPower(VulnerablePower.POWER_ID)) {
            addToBot(new ApplyPowerAction(m, p, new UnfortifiedPower(m, p, this.magicNumber), this.magicNumber));
        } else {
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
        }
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_TAG);
    }

}
