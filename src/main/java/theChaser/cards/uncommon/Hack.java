package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;

import static theChaser.TheChaserMod.makeCardPath;

public class Hack extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Hack");
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 2;
    private static final int DAMAGE = 4;
    private static final int TAG = 4;
    private static final int UP_TAG = 5;

    public Hack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, TAG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SLASH_VERTICAL, true));
        }
    }

    @Override
    public void upgradeCard() {
        upgradeMagicNumber(UP_TAG);
    }

}
