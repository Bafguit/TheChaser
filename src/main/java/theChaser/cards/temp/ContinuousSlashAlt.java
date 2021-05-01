package theChaser.cards.temp;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;

import static theChaser.TheChaserMod.makeCardPath;

public class ContinuousSlashAlt extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Continuous Slash Alt");
    public static final String IMG = makeCardPath("ContinuousSlash.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int UP_DMG = 1;

    public ContinuousSlashAlt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, 0);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SLASH_DIAGONAL, true));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SLASH_HORIZONTAL, true));
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_DMG);
    }

}
