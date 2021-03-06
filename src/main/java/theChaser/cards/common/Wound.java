package theChaser.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.BleedingPower;

import static theChaser.TheChaserMod.makeCardPath;

public class Wound extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Wound");
    public static final String IMG = makeCardPath("Wound.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UP_DMG = 2;
    private static final int TAG = 4;
    private static final int UP_TAG = 1;

    public Wound() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, TAG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_TAG);
    }

}
