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
import theChaser.powers.TargetPower;

import static theChaser.TheChaserMod.makeCardPath;

public class Identify extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Identify");
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UP_DMG = 2;
    private static final int TAG = 2;
    private static final int UP_TAG = 1;

    public Identify() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, TAG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(m, p, new TargetPower(m, this.magicNumber)));
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_TAG);
    }

}
