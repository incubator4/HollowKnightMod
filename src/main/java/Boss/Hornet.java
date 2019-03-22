package Boss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Spiker;
import com.megacrit.cardcrawl.powers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import power.AntiPoisonPower;

import java.util.HashMap;


public class Hornet extends AbstractMonster {
    private boolean thresholdReached = false;
    private boolean firstMove = true;
    private final float spawnX = -100.0F;
    public static final String ID = "HollowKnightMod:Hornet";
    private static final String ENCOUNTER_NAME = ID;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    private static final String NAME = monsterStrings.NAME;
    private static final String[] MOVES = monsterStrings.MOVES;
    private static final String[] DIALOG = monsterStrings.DIALOG;
    private HashMap<Integer, AbstractMonster> enemySlots = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(Hornet.class.getName());
    //location
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 0.0F;
    private static final float HB_H = 0.0F;

    //stats
    private static final int HP_MIN = 370;
    private static final int HP_MAX = 380;
    private static final int HP_MIN_A = 400;
    private static final int HP_MAX_A = 420;


    //Attack para
    private static final byte STINGER = 1;
    private static final int stingerDamage = 1;

    private static final int dancingsewingDamage = 10;
    private static final byte DANCINGSEWING = 2;
    private static final int dancingsewingCount = 2;

    private static final int needleDamage = 20;
    private static final byte NEEDLE = 3;

    private static final int SprintDamage = 4;
    private static final byte SPRINT = 4;

    private static final byte STRENGTHUP = 5;
    private static final byte SUMMOMSPIKER = 6;



    public Hornet() {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, null, -50.0f, 30.0f);
        this.type = EnemyType.BOSS;
        this.dialogX = 0.0F;
        this.dialogY = 0.0F;
        this.img = new Texture(Gdx.files.internal("images/monster/Hornet.png"));
        this.setHp(HP_MIN, HP_MAX_A);
        this.damage.add(new DamageInfo(this, stingerDamage));
        this.damage.add(new DamageInfo(this, dancingsewingDamage));
        this.damage.add(new DamageInfo(this, needleDamage));
        this.damage.add(new DamageInfo(this, SprintDamage));

    }

    @Override
    public void usePreBattleAction() {
        logger.info("Before usePreBattleAction()");
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(this, this,
                        new AntiPoisonPower(this)));
        logger.info("After usePreBattleAction()");
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case STINGER:
                stringer();
                break;
            case DANCINGSEWING:
                dancingsewing();
                break;
            case NEEDLE:
                needle();
                break;
            case SPRINT:
                sprint();
                break;
            case STRENGTHUP:
                strengthup();
            case SUMMOMSPIKER:
                summonSpiker();
            default:
                //logger.info("ERROR: Default Take Turn was called on " + this.name);
        }
    }


    private void stringer() {
        AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        AbstractDungeon.player, this, new  VulnerablePower(AbstractDungeon.player, 2, true)));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        AbstractDungeon.player, this, new  WeakPower(AbstractDungeon.player, 2, true)));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        AbstractDungeon.player, this, new  ConstrictedPower(AbstractDungeon.player, this, 2)));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        AbstractDungeon.player, this, new  FrailPower(AbstractDungeon.player, 2, true)));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        AbstractDungeon.player, this, new  DrawReductionPower(AbstractDungeon.player, 2)));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player,this,
                        new StrengthPower(this, -1),-1));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player,this,
                        new DexterityPower(this, -1),-1));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player,this,
                        new FocusPower(this, -1),-1));



        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    private void dancingsewing() {
        for (int i = 0; i < dancingsewingCount; i++) {
            AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.BLUNT_LIGHT));
        }

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(this, this, new PlatedArmorPower(this, 7)));
    }

    private void needle() {
        AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(AbstractDungeon.player, this.damage.get(2), AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(this,this, new BlurPower(this,3)));
    }

    private void sprint() {
        AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(AbstractDungeon.player, this.damage.get(3), AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 20));
    }

    private void strengthup() {
        AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(this,this,
                        new StrengthPower(this, 3),3));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5)));
    }

    private void summonSpiker() {
        for (int i = 0; i < 2; i++) {
            Spiker spiker = new Spiker(this.spawnX + -185.0F * (float)(i+1), MathUtils.random(-5.0F, 25.0F));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_SUMMON"));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(spiker, true));
            this.enemySlots.put(i, spiker);
        }
    }


    public void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove(STINGER, Intent.ATTACK_DEBUFF);
        } else if (this.currentHealth < this.maxHealth / 2 && !this.thresholdReached) {
            this.thresholdReached = true;
            this.setMove(SUMMOMSPIKER, Intent.UNKNOWN);
        } else {
            int tmp =   MathUtils.random(2,5);
            switch (tmp) {
                case DANCINGSEWING:
                    this.setMove(DANCINGSEWING,Intent.ATTACK);
                    break;
                case NEEDLE:
                    this.setMove(NEEDLE,Intent.ATTACK_BUFF);
                    break;
                case SPRINT:
                    this.setMove(DANCINGSEWING,Intent.ATTACK_DEFEND);
                    break;
                case STRENGTHUP:
                    strengthup();
            }
        }

    }



    public void die() {
        super.die();
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDying) {
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(monster));
            }
        }

        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            this.onBossVictoryLogic();
            this.onFinalBossVictoryLogic();
        }
    }


}
