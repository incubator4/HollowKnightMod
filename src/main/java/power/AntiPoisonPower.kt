package power

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.powers.AbstractPower


class AntiPoisonPower(owner: AbstractCreature) : AbstractPower() {

    init {
        this.name = NAME
        this.ID = Power_ID
        this.owner = owner
        this.img = ImageMaster.loadImage(IMG)
        this.type = PowerType.BUFF
        this.updateDescription()
        this.loadRegion(Power_ID)
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1]
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (owner.getPower("Poison") != null) {
            owner.getPower("Poison").amount /= 2
        }
    }

    companion object {
        val Power_ID = "AntiPoisonPower"
        val IMG = "images/ui/power/AntiPoisonPower.png"
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(Power_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
