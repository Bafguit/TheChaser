package theChaser.cards.temp;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.actions.ChaserUtil;
import theChaser.cards.ChaserCard;
import theChaser.characters.TheChaser;

import java.util.ArrayList;

import static theChaser.TheChaserMod.makeCardPath;

public class SequenceFlow extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Sequence Flow");
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int UP_DAMAGE = 1;

    public SequenceFlow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, DAMAGE, 0, 0);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractGameAction.AttackEffect> effects = new ArrayList<>();
        effects.add(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        effects.add(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        effects.add(AbstractGameAction.AttackEffect.SLASH_VERTICAL);

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), effects.get(AbstractDungeon.cardRandomRng.random.nextInt(3))));
    }

    @Override
    public void upgradeCard() {
        upgradeDamage(UP_DAMAGE);
    }

}
