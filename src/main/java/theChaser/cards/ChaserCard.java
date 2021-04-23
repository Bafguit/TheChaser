package theChaser.cards;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public abstract class ChaserCard extends CustomCard {

    // Custom Abstract Cards can be a bit confusing. While this is a simple base for simply adding a second magic number,
    // if you're new to modding I suggest you skip this file until you know what unique things that aren't provided
    // by default, that you need in your own cards.

    // In this example, we use a custom Abstract Card in order to define a new magic number. From here on out, we can
    // simply use that in our cards, so long as we put "extends AbstractDynamicCard" instead of "extends CustomCard" at the start.
    // In simple terms, it's for things that we don't want to define again and again in every single card we make.

    public int magicNumber2;
    public int baseMagicNumber2;
    public boolean upgradedMagicNumber2;
    public boolean isMagicNumber2Modified;
    public boolean isTempCard;
    public String upgradeDescription;
    public String[] extendedDescription;
    public String betaArtPath;

    protected CardStrings cardStrings;
    protected String NORMAL_DESCRIPTION;
    protected String UPGRADE_DESCRIPTION;
    protected String[] EXTENDED_DESCRIPTION;

    public ChaserCard(String id, String name, String img, int cost, String rawDescription,
                      CardType type, CardColor color, CardRarity rarity, CardTarget target,
                      int damage, int block, int magicNumber, int magicNumber2) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        NORMAL_DESCRIPTION = cardStrings.DESCRIPTION;
        this.upgradeDescription = UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        this.extendedDescription = EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        this.damage = this.baseDamage = damage;
        this.block = this.baseBlock = block;
        this.magicNumber = this.baseMagicNumber = magicNumber;
        this.magicNumber2 = this.baseMagicNumber2 = magicNumber2;
        this.isTempCard = false;
    }

    public ChaserCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target,
                      int damage, int block, int magicNumber) {
        this(id, CardCrawlGame.languagePack.getCardStrings(id).NAME, img, cost, CardCrawlGame.languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target, damage, block, magicNumber, 0);
    }

    public ChaserCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        this(id, CardCrawlGame.languagePack.getCardStrings(id).NAME, img, cost, CardCrawlGame.languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target, 0, 0, 0, 0);
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        if (this.upgradedMagicNumber2) {
            this.magicNumber2 = this.baseMagicNumber2;
            this.isMagicNumber2Modified = true;
        }

    }

    public void upgradeMagicNumber2(int amount) {
        this.baseMagicNumber2 += amount;
        this.magicNumber2 = this.baseMagicNumber2;
        this.upgradedMagicNumber2 = true;
    }

    @Override
    public final void upgrade() {
        if (!upgraded) {
            upgradeCard();
            upgradeName();
            if(UPGRADE_DESCRIPTION != null) {
                this.rawDescription = UPGRADE_DESCRIPTION;
            }
            initializeDescription();
        }
    }

    public static void loadJokeCardImage(AbstractCard card, String img) {
        if (card instanceof ChaserCard) {
            ((ChaserCard) card).betaArtPath = img;
        }
        Texture cardTexture;
        cardTexture = ImageMaster.loadImage(img);
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivate(card, AbstractCard.class, "jokePortrait", cardImg);
    }

    @Override
    protected Texture getPortraitImage() {
        if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(this.cardID, false)) {
            if (this.textureImg == null) {
                return null;
            } else {
                if (betaArtPath != null) {
                    int endingIndex = betaArtPath.lastIndexOf(".");
                    String newPath = betaArtPath.substring(0, endingIndex) + "_p" + betaArtPath.substring(endingIndex);
                    System.out.println("Finding texture: " + newPath);

                    Texture portraitTexture;
                    try {
                        portraitTexture = ImageMaster.loadImage(newPath);
                    } catch (Exception var5) {
                        portraitTexture = null;
                    }

                    return portraitTexture;
                }
            }
        }
        return super.getPortraitImage();
    }

    public abstract void upgradeCard();

}