package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.UnfortifiedPower;

import static theChaser.TheChaserMod.makeCardPath;

public class WeaknessStrike extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Weakness Strike");
    public static final String IMG = makeCardPath("WeaknessStrike.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UP_DMG = 2;

    private boolean debuffCheck;

    public WeaknessStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, 0);
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void triggerOnGlowCheck() {
        this.debuffCheck = false;
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for(AbstractPower p : m.powers) {
                if (p.type == AbstractPower.PowerType.DEBUFF) {
                    this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                    this.debuffCheck = true;
                    break;
                }
            }

            if(this.debuffCheck) break;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SLASH_HORIZONTAL, true));

        int count = 0;
        if(m.powers.size() > 0) {
            for (AbstractPower power : m.powers) {
                if (power.type == AbstractPower.PowerType.DEBUFF) {
                    count++;
                }
            }
        }

        if(count > 0) {
            for(int i = 0; i < count; i++) {
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SLASH_HORIZONTAL, true));
            }
        }
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_DMG);
    }

}
