package theChaser.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.actions.AddCardPerTurnAction;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;
import theChaser.powers.FakeSorePower;
import theChaser.powers.TargetPower;

import static theChaser.TheChaserMod.makeCardPath;

public class Stalling extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Stalling");
    public static final String IMG = makeCardPath("Stalling.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UP_DMG = 3;
    private static final int TAG = 1;
    private static final int UP_TAG = 1;

    public Stalling() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, TAG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SLASH_DIAGONAL));
        addToBot(new AddCardPerTurnAction(this.magicNumber));
        for(int i = 0; i < this.magicNumber; i++) {
            addToBot(new ApplyPowerAction(p, p, new FakeSorePower(p, 1), 1, true));
        }
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_TAG);
    }

}
