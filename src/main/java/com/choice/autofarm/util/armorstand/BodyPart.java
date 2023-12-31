package com.choice.autofarm.util.armorstand;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import static com.choice.autofarm.util.armorstand.BodyPart.Parts.*;

public class BodyPart {
    private Parts parts;
    public enum Parts {
        HEAD, BODY, LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG;
    }

    public BodyPart(Parts parts){
        this.parts = parts;
    }

    public BodyPart(){}

    public void setBodyPose(Parts parts){
        this.parts = parts;
    }

    public void setPose(ArmorStand stand, Location target) {
        EulerAngle ea;
        Location origin;
        double initYaw;
        double yaw;
        double pitch;

        if (parts == HEAD) {
            origin = stand.getEyeLocation();
            initYaw = origin.getYaw();
            Vector tgt = target.toVector();
            origin.setDirection(((Vector) tgt).subtract(origin.toVector()));
            yaw = origin.getYaw() - initYaw;
            pitch = origin.getPitch();
            if (yaw < -180) {
                yaw = yaw + 360;
            } else if (yaw >= 180) {
                yaw -= 360;
            }
        } else {
            origin = stand.getLocation();
            if (parts == LEFT_ARM || parts == RIGHT_ARM) {
                origin = origin.add(0, 1.4, 0);
            } else if (parts == LEFT_LEG || parts == RIGHT_LEG) {
                origin = origin.add(0, 0.8, 0);
            }
            initYaw = origin.getYaw();
            Vector tgt = target.toVector();
            origin.setDirection(tgt.subtract(origin.toVector()));
            yaw = origin.getYaw() - initYaw;
            pitch = origin.getPitch();
            pitch -= 90;

        }
        ea = new EulerAngle(Math.toRadians(pitch), Math.toRadians(yaw), 0);
        setPose(stand, ea);

    }

    public void rotateBody(ArmorStand stand, Location target) {
        double deltaX = target.getX() - stand.getLocation().getX();
        double deltaZ = target.getZ() - stand.getLocation().getZ();
        float yaw = (float) Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90;
        stand.setRotation(yaw, 0);
    }

    private MiningAnimation animation;
    public void animateLeftArmAsync(ArmorStand stand) {
       animation = new MiningAnimation(stand, 8);
       animation.startAnimation();
    }

    public void cancelAnimation(ArmorStand stand) {
        if (animation == null) return;
        animation.cancel();
        setPose(stand, new EulerAngle(0, 0, 0));
        animation = null;
    }

    public void setPose(ArmorStand stand, EulerAngle angle) {
        switch (parts) {
            case HEAD:
                stand.setHeadPose(angle);
                break;
            case BODY:
                stand.setBodyPose(angle);
                break;
            case LEFT_ARM:
                stand.setLeftArmPose(angle);
                break;
            case LEFT_LEG:
                stand.setLeftLegPose(angle);
                break;
            case RIGHT_ARM:
                stand.setRightArmPose(angle);
                break;
            case RIGHT_LEG:
                stand.setRightLegPose(angle);
                break;
        }
    }
}
