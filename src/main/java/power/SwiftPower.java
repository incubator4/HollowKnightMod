package power;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SwiftPower extends AbstractPower {
    public static final String Power_ID = "SwiftPower";
    public static final String IMG = "images/ui/power/SwiftPower.png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(Power_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int maxCardPlay;
    private int count;

    public SwiftPower(AbstractCreature owner, int maxCards) {
        this.maxCardPlay = maxCards;
        this.name = NAME;
        this.ID = Power_ID;
        this.owner = owner;
        this.count = maxCardPlay;
        this.img = ImageMaster.loadImage(IMG);
        this.updateDescription();
        this.loadRegion(Power_ID);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + String.valueOf(count) + DESCRIPTIONS[1];
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type != AbstractCard.CardType.ATTACK) {
            count --;
            if (count == 0) {
                this.takeTurn();
                count = maxCardPlay;
            }
        }
    }
    private void takeTurn() {
        if (this.owner instanceof AbstractMonster) {
            final AbstractMonster monster = (AbstractMonster)this.owner;
            monster.takeTurn();
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {

                @Override
                public void update() {
                    monster.createIntent();
                    isDone=true;
                }
            });
        }
    }
}
