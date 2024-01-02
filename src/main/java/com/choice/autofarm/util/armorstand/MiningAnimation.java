package com.choice.autofarm.util.armorstand;

import com.choice.autofarm.AutoFarm;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class MiningAnimation extends BukkitRunnable {

    private final double UPPER_ROTATION = 7.0/6 * Math.PI;
    private final double LOWER_ROTATION = 7.0/4 * Math.PI;

    private final ArmorStand miningArmStand;
    private double currentRotation;
    private AnimationDirection animationDirection;
    private final double rotationChangesPerTick;

    public MiningAnimation(ArmorStand miningArmStand, double velocity) {
        this.miningArmStand = miningArmStand;
        this.currentRotation = UPPER_ROTATION;
        this.animationDirection = AnimationDirection.DOWN;
        this.rotationChangesPerTick = Math.abs(UPPER_ROTATION - LOWER_ROTATION) / (velocity / 2);

        initializeMiningArmStand();
    }

    private void initializeMiningArmStand() {
        miningArmStand.setRightArmPose(new EulerAngle(UPPER_ROTATION, 0, 0));
    }

    public void startAnimation() {
        runTaskTimer(AutoFarm.getInstance(), 0L, 1L);
    }

    @Override
    public void run() {
        updateRotation();
        updateAnimationDirection();
        updateArmStandPose();
    }

    private void updateRotation() {
        switch (animationDirection) {
            case DOWN:
                currentRotation += rotationChangesPerTick;
                break;
            case UP:
                currentRotation -= rotationChangesPerTick;
                break;
        }
    }

    private void updateAnimationDirection() {
        if (currentRotation <= UPPER_ROTATION) {
            animationDirection = AnimationDirection.DOWN;
        }
        if (currentRotation >= LOWER_ROTATION) {
            animationDirection = AnimationDirection.UP;
        }
    }

    private void updateArmStandPose() {
        miningArmStand.setRightArmPose(new EulerAngle(currentRotation, 0, 0));
    }

    private enum AnimationDirection {
        DOWN, UP
    }
}
