package org.firstinspires.ftc.teamcode.misc;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Pose2D {

    public double x, y, heading;

    public Pose2D() {
        this(0, 0, 0);
    }

    public Pose2D(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = angleWrap(heading);
    }

    public Pose2D plus(@NonNull Pose2D p2) {
        return new Pose2D(x + p2.x, y + p2.y, heading + p2.heading);
    }


    public Pose2D times(@NonNull Pose2D p2) {
        return new Pose2D(x * p2.x, y * p2.y, heading * p2.heading);
    }

    public Pose2D div(@NonNull Pose2D p2) {
        return new Pose2D(x / p2.x, y / p2.y, heading / p2.heading);
    }

    public Pose2D minus(@NonNull Pose2D p2) {
        return new Pose2D(x - p2.x, y - p2.y, heading - p2.heading);
    }

    public Pose2D times(double d) {
        return new Pose2D(x * d, y * d, heading * d);
    }

    private double angleWrap(double angle) {
        while (angle > PI) angle -= PI * 2;
        while (angle < -PI) angle += PI * 2;
        return angle;
    }

    public double aCot(){
      return atan2(x, y);
    }

    public Pose2D rotatedCW(double angle){
        double sinA = sin(angle);
        double cosA = cos(angle);
        return new Pose2D(x * cosA + y * sinA, -x * sinA + y * cosA, heading);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pose2D)) return false;
        Pose2D pose2D = (Pose2D) o;
        return approxEquals(x, pose2D.x) && approxEquals(y, pose2D.y) && approxEquals(heading, pose2D.heading);
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "{x: %.3f, y: %.3f, θ: %.3f}", x, y, toDegrees(heading));
    }

    @NonNull
    @Override
    public Pose2D clone() {
        return new Pose2D(this.x, this.y, this.heading);
    }

    boolean approxEquals(double d1, double d2){
        return abs(d1 - d2) < 1e-5;
    }
}


