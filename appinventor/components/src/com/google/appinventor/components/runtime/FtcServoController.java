// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2011-2015 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.ErrorMessages;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoController.PwmStatus;

/**
 * A component for a servo controller input of an FTC robot.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
@DesignerComponent(version = YaVersion.FTC_SERVO_CONTROLLER_COMPONENT_VERSION,
    description = "A component for a servo controller of an FTC robot.",
    category = ComponentCategory.FIRSTTECHCHALLENGE,
    nonVisible = true,
    iconName = "images/ftc.png")
@SimpleObject
@UsesLibraries(libraries = "FtcRobotCore.jar")
public final class FtcServoController extends FtcHardwareDevice {

  private volatile ServoController servoController;

  /**
   * Creates a new FtcServoController component.
   */
  public FtcServoController(ComponentContainer container) {
    super(container.$form());
  }

  /**
   * PwmStatus_ENABLED property getter.
   */
  @SimpleProperty(description = "The constant for PwmStatus_ENABLED.",
      category = PropertyCategory.BEHAVIOR)
  public String PwmStatus_ENABLED() {
    return PwmStatus.ENABLED.toString();
  }

  /**
   * PwmStatus_DISABLED property getter.
   */
  @SimpleProperty(description = "The constant for PwmStatus_DISABLED.",
      category = PropertyCategory.BEHAVIOR)
  public String PwmStatus_DISABLED() {
    return PwmStatus.DISABLED.toString();
  }

  @SimpleFunction(description = "PWM enable.")
  public void PwmEnable() {
    if (servoController != null) {
      try {
        servoController.pwmEnable();
      } catch (Throwable e) {
        e.printStackTrace();
        form.dispatchErrorOccurredEvent(this, "PwmEnable",
            ErrorMessages.ERROR_FTC_UNEXPECTED_ERROR, e.toString());
      }
    }
  }

  @SimpleFunction(description = "PWM disable.")
  public void PwmDisable() {
    if (servoController != null) {
      try {
        servoController.pwmDisable();
      } catch (Throwable e) {
        e.printStackTrace();
        form.dispatchErrorOccurredEvent(this, "PwmDisable",
            ErrorMessages.ERROR_FTC_UNEXPECTED_ERROR, e.toString());
      }
    }
  }

  @SimpleFunction(description = "Get the PWM status.")
  public String GetPwmStatus() {
    if (servoController != null) {
      try {
        PwmStatus pwmStatus = servoController.getPwmStatus();
        if (pwmStatus != null) {
          return pwmStatus.toString();
        }
      } catch (Throwable e) {
        e.printStackTrace();
        form.dispatchErrorOccurredEvent(this, "GetPwmStatus",
            ErrorMessages.ERROR_FTC_UNEXPECTED_ERROR, e.toString());
      }
    }
    return "";
  }

  @SimpleFunction(description = "Set the position of a servo at the given channel.")
  public void SetServoPosition(int channel, double position) {
    if (servoController != null) {
      try {
        servoController.setServoPosition(channel, position);
      } catch (Throwable e) {
        e.printStackTrace();
        form.dispatchErrorOccurredEvent(this, "SetServoPosition",
            ErrorMessages.ERROR_FTC_UNEXPECTED_ERROR, e.toString());
      }
    }
  }

  @SimpleFunction(description = "Get the position of a servo at a given channel.")
  public double GetServoPosition(int channel) {
    if (servoController != null) {
      try {
        return servoController.getServoPosition(channel);
      } catch (Throwable e) {
        e.printStackTrace();
        form.dispatchErrorOccurredEvent(this, "GetServoPosition",
            ErrorMessages.ERROR_FTC_UNEXPECTED_ERROR, e.toString());
      }
    }
    return 0.0;
  }

  // FtcRobotController.HardwareDevice implementation

  @Override
  public Object initHardwareDeviceImpl(HardwareMap hardwareMap) {
    if (hardwareMap != null) {
      servoController = hardwareMap.servoController.get(getDeviceName());
      if (servoController == null) {
        deviceNotFound("ServoController", hardwareMap.servoController);
      }
    }
    return servoController;
  }

  @Override
  public void clearHardwareDevice() {
    servoController = null;
  }
}