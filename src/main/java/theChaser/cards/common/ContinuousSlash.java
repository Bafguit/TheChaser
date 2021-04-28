package theChaser.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.cards.temp.ContinuousSlashAlt;
import theChaser.characters.TheChaser;

import static theChaser.TheChaserMod.makeCardPath;

public class ContinuousSlash extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Continuous Slash");
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int UP_DMG = 1;

    public ContinuousSlash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, 0);
        this.cardsToPreview = new ContinuousSlashAlt();
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(ChaserUtil.isAccel()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SLASH_DIAGONAL, true));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SLASH_HORIZONTAL, true));
        if(ChaserUtil.isAccel()) {
            ChaserCard temp = new ContinuousSlashAlt();
            if(this.upgraded) temp.upgrade();
            addToBot(new MakeTempCardInHandAction(temp));
            addToBot(new MakeTempCardInHandAction(temp));
        }
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_DMG);
        ChaserCard temp = new ContinuousSlashAlt();
        temp.upgrade();
        this.cardsToPreview = temp;
    }

}
