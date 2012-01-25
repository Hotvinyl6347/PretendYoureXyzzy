package net.socialgamer.cah;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.socialgamer.cah.Constants.DoubleLocalizable;
import net.socialgamer.cah.Constants.Localizable;


public class UpdateJsConstants {

  private static final String enumHeaderFmt =
      "cah.$.%s = function() {\r\n  // Dummy constructor to make Eclipse auto-complete.\r\n};\r\n";
  private static final String enumDummyFmt =
      "cah.$.%s.prototype.dummyForAutocomplete = undefined;\r\n";
  private static final String enumValueFmt = "cah.$.%s.%s = \"%s\";\r\n";
  private static final String msgHeaderFmt = "cah.$.%s_msg = {};\r\n";
  private static final String msgValueFmt = "cah.$.%s_msg['%s'] = \"%s\";\r\n";
  private static final String msg2HeaderFmt = "cah.$.%s_msg_2 = {};\r\n";
  private static final String msg2ValueFmt = "cah.$.%s_msg_2['%s'] = \"%s\";\r\n";

  /**
   * @param args
   */
  @SuppressWarnings("rawtypes")
  public static void main(final String[] args) throws Exception {
    final String dir = "WebContent/js/";
    final File outFile = new File(dir + "cah.constants.js");
    assert outFile.canWrite();
    assert outFile.delete();
    assert outFile.createNewFile();
    final PrintWriter writer = new PrintWriter(outFile);

    writer.println("// This file is automatically generated. Do not edit.");
    writer.println();
    writer.println("cah.$ = {};");
    writer.println();

    final Class[] classes = Constants.class.getClasses();
    for (final Class<?> c : classes) {
      if (!c.isEnum()) {
        continue;
      }
      final String cName = c.getName().split("\\$")[1];
      System.out.println(cName);
      writer.format(enumHeaderFmt, cName);
      writer.format(enumDummyFmt, cName);
      final Map<String, String> values = getEnumValues(c);
      for (final String key : values.keySet()) {
        final String value = values.get(key);
        writer.format(enumValueFmt, cName, key, value);
      }
      if (Localizable.class.isAssignableFrom(c) || DoubleLocalizable.class.isAssignableFrom(c)) {
        System.out.println(cName + "_msg");
        writer.format(msgHeaderFmt, cName);
        final Map<String, String> messages = getEnumMessageValues(c);
        for (final String key : messages.keySet()) {
          final String value = messages.get(key);
          writer.format(msgValueFmt, cName, key, value);
        }
      }
      if (DoubleLocalizable.class.isAssignableFrom(c)) {
        System.out.println(cName + "_msg_2");
        writer.format(msg2HeaderFmt, cName);
        final Map<String, String> messages = getEnumMessage2Values(c);
        for (final String key : messages.keySet()) {
          final String value = messages.get(key);
          writer.format(msg2ValueFmt, cName, key, value);
        }
      }
      writer.println();
    }
    writer.flush();
    writer.close();
  }

  private static Map<String, String> getEnumValues(final Class<?> enumClass)
      throws IllegalArgumentException, IllegalAccessException {
    if (!enumClass.isEnum()) {
      throw new IllegalArgumentException(enumClass.getName() + " is not an enum");
    }

    final Field[] flds = enumClass.getDeclaredFields();
    final HashMap<String, String> enumMap = new HashMap<String, String>();
    for (final Field f : flds) {
      if (f.isEnumConstant()) {
        enumMap.put(f.getName(), f.get(null).toString());
      }
    }
    return enumMap;
  }

  private static Map<String, String> getEnumMessageValues(final Class<?> enumClass)
      throws IllegalArgumentException, IllegalAccessException {
    if (!enumClass.isEnum()) {
      throw new IllegalArgumentException(enumClass.getName() + " is not an enum");
    } else if (!Localizable.class.isAssignableFrom(enumClass)
        && !DoubleLocalizable.class.isAssignableFrom(enumClass)) {
      throw new IllegalArgumentException(enumClass.getName()
          + " does not implement Localizable or DoubleLocalizable.");
    }

    final Field[] flds = enumClass.getDeclaredFields();
    final HashMap<String, String> messageMap = new HashMap<String, String>();
    for (final Field f : flds) {
      if (f.isEnumConstant()) {
        if (Localizable.class.isAssignableFrom(enumClass)) {
          messageMap.put(f.get(null).toString(), ((Localizable) f.get(null)).getString());
        } else {
          messageMap.put(f.get(null).toString(), ((DoubleLocalizable) f.get(null)).getString());
        }
      }
    }
    return messageMap;
  }

  private static Map<String, String> getEnumMessage2Values(final Class<?> enumClass)
      throws IllegalArgumentException, IllegalAccessException {
    if (!enumClass.isEnum()) {
      throw new IllegalArgumentException(enumClass.getName() + " is not an enum");
    } else if (!DoubleLocalizable.class.isAssignableFrom(enumClass)) {
      throw new IllegalArgumentException(enumClass.getName()
          + " does not implement DoubleLocalizable.");
    }

    final Field[] flds = enumClass.getDeclaredFields();
    final HashMap<String, String> messageMap = new HashMap<String, String>();
    for (final Field f : flds) {
      if (f.isEnumConstant()) {
        messageMap.put(f.get(null).toString(), ((DoubleLocalizable) f.get(null)).getString2());
      }
    }
    return messageMap;
  }
}

////Automatically generated file. Do not edit!
//
//cah.$ = {};
//
//cah.$.DisconnectReason = {};
//cah.$.DisconnectReason.prototype.dummy = undefined;
//cah.$.DisconnectReason.KICKED = "kicked";
//cah.$.DisconnectReason.MANUAL = "manual";
//cah.$.DisconnectReason.PING_TIMEOUT = "ping_timeout";
