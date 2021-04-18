package theDefault.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDefault.TheChaserMod;
import theDefault.characters.TheChaser;
import theDefault.powers.CommonPower;

import static theDefault.TheChaserMod.makeCardPath;

public class ShadowForm extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Shadow Form");
    public static final String IMG = makeCardPath("Power.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 3;
    private static final int MAGIC = 2;
    private static final int UP_MAGIC = 1;

    public ShadowForm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, 0, MAGIC);
        this.tags.add(BaseModCardTags.FORM);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new CommonPower(p, p, magicNumber), magicNumber));
    }

    @Override
    public void upgradeCard() {
        upgradeMagicNumber(UP_MAGIC);
    }
}