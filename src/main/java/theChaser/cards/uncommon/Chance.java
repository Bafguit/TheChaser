package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
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
import theChaser.cards.temp.ContinuousSlashAlt;
import theChaser.characters.TheChaser;

import java.util.ArrayList;

import static theChaser.TheChaserMod.makeCardPath;

public class Chance extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Chance");
    public static final String IMG = makeCardPath("Chance.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 0;
    private static final int BLOCK = 4;
    private static final int UP_BLOCK = 3;

    public Chance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, BLOCK, 0);
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
        addToBot(new GainBlockAction(p, this.block));
        if(ChaserUtil.isAccel()) {
            ArrayList<AbstractCard> cards = new ArrayList<>();
            for(AbstractCard c : p.hand.group) {
                if(c.costForTurn > 0 || !c.freeToPlay()) {
                    cards.add(c);
                }
            }

            if(cards.size() > 0) {
                cards.get(AbstractDungeon.cardRandomRng.random.nextInt(cards.size())).setCostForTurn(0);
            }
        }
    }

    @Override
    public void upgradeCard() {
        upgradeBlock(UP_BLOCK);
    }

}
