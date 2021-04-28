package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;

import static theChaser.TheChaserMod.makeCardPath;

public class Testing extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Testing");
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 2;
    private static final int DAMAGE = 8;
    private static final int UP_DAMAGE = 2;
    private static final int TAG = 2;
    private static final int UP_TAG = 1;

    public Testing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, TAG);
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AttackEffect.NONE));
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(m, this.magicNumber, false)));
        }
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_TAG);
    }

}
