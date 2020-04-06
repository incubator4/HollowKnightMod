package power

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower

class SwiftPower(owner: AbstractCreature, private val maxCardPlay: Int) : AbstractPower() {
    private var count: Int = 0

    init {
        this.name = NAME
        this.ID = Power_ID
        this.owner = owner
        this.count = maxCardPlay
        this.img = ImageMaster.loadImage(IMG)
        this.updateDescription()
        this.loadRegion(Power_ID)
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + count.toString() + DESCRIPTIONS[1]
    }

    override fun onAfterUseCard(card: AbstractCard, action: UseCardAction) {
        if (card.type !== AbstractCard.CardType.ATTACK) {
            count--
            if (count == 0) {
                this.takeTurn()
                count = maxCardPlay
            }
        }
    }

    private fun takeTurn() {
        if (this.owner is AbstractMonster) {
            val monster = this.owner as AbstractMonster
            monster.takeTurn()
            AbstractDungeon.actionManager.addToBottom(object : AbstractGameAction() {

                override fun update() {
                    monster.createIntent()
                    isDone = true
                }
            })
        }
    }

    companion object {
        val Power_ID = "SwiftPower"
        val IMG = "images/ui/power/SwiftPower.png"
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(Power_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
