package Card.Blue

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.monsters.AbstractMonster
import power.MultipleProcessPower

class MultipleProcess : AbstractCard(ID, NAME, "1", COST, DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        this.baseMagicNumber = 3
        this.magicNumber = baseMagicNumber
    }


    override fun use(player: AbstractPlayer, monster: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(player, player, MultipleProcessPower(player, this.magicNumber), this.magicNumber))
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeMagicNumber(1)
        }
    }

    override fun makeCopy(): AbstractCard {
        return MultipleProcess()
    }

    companion object {
        const val ID = "Multiple Process"
        private val cardStrings: CardStrings
        val NAME: String
        val DESCRIPTION: String
        const  val COST = 1

        init {
            cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
            NAME = cardStrings.NAME
            DESCRIPTION = cardStrings.DESCRIPTION
        }
    }
}
