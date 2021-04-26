package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.cards.temp.ContinuousSlashAlt;
import theChaser.cards.temp.SequenceFlow;
import theChaser.characters.TheChaser;

import java.util.ArrayList;

import static theChaser.TheChaserMod.makeCardPath;

public class Sequence extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Sequence");
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int UP_DAMAGE = 1;
    private static final int MAGIC = 5;

    public Sequence() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, MAGIC);
        this.cardsToPreview = new SequenceFlow();
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
        if(ChaserUtil.isAccel()) {
            ArrayList<AbstractGameAction.AttackEffect> effects = new ArrayList<>();
            effects.add(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
            effects.add(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
            effects.add(AbstractGameAction.AttackEffect.SLASH_VERTICAL);

            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), effects.get(AbstractDungeon.cardRandomRng.random.nextInt(3))));
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), effects.get(AbstractDungeon.cardRandomRng.random.nextInt(3))));
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), effects.get(AbstractDungeon.cardRandomRng.random.nextInt(3))));
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), effects.get(AbstractDungeon.cardRandomRng.random.nextInt(3))));
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), effects.get(AbstractDungeon.cardRandomRng.random.nextInt(3))));
        } else {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            ChaserCard temp = new SequenceFlow();
            if(this.upgraded) temp.upgrade();
            addToBot(new MakeTempCardInHandAction(temp, 2));
        }
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_DAMAGE);
        ChaserCard temp = new SequenceFlow();
        temp.upgrade();
        this.cardsToPreview = temp;
    }

}
