package theChaser.cards.common;

import basemod.abstracts.cardbuilder.actionbuilder.PoisonActionBuilder;
import com.evacipated.cardcrawl.mod.stslib.patches.powerInterfaces.HealthBarRenderPowerPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Wish;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.actions.InstantBleedingDamageAction;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.BleedingPower;
import theChaser.powers.TargetPower;

import java.util.ArrayList;
import java.util.Random;

import static theChaser.TheChaserMod.makeCardPath;

public class ScarStab extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Scar Stab");
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UP_DMG = 3;
    private static final int BLEED = 1;
    private static final int UP_BLEED = 1;

    public ScarStab() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, BLEED);
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.hasPower(BleedingPower.POWER_ID)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AttackEffect> effects = new ArrayList<>();
        effects.add(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        effects.add(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        effects.add(AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        Random random = new Random();

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), effects.get(random.nextInt(effects.size()))));
        addToBot(new InstantBleedingDamageAction(m, this.upgraded ? 2 : 1));
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_BLEED);
    }

}
