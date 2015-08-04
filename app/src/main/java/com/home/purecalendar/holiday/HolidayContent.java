package com.home.purecalendar.holiday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class HolidayContent {
    public static Set<String> holidayCache = new HashSet<String>();

    /**
     * An array of sample (dummy) items.
     */
    public static List<HolidayInfo> ITEMS = new ArrayList<HolidayInfo>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, HolidayInfo> ITEM_MAP = new HashMap<String, HolidayInfo>();

    static {
        // Add 3 sample items.
//        addItem(new HolidayInfo("1", "dummy holiday 1", "ic_heart"));
//        addItem(new HolidayInfo("2", "dummy holiday 2", "ic_leaf"));
//        addItem(new HolidayInfo("3", "dummy holiday 3"));
//        addItem(new HolidayInfo("4", "dummy holiday 4", "ic_heart"));
//        addItem(new HolidayInfo("5", "dummy holiday 5", "ic_leaf"));
//        addItem(new HolidayInfo("6", "dummy holiday 6"));
//        addItem(new HolidayInfo("7", "dummy holiday 7", "ic_heart"));
//        addItem(new HolidayInfo("8", "dummy holiday 8", "ic_leaf"));
//        addItem(new HolidayInfo("9", "dummy holiday 9"));
    }

    public static void addHoliday(String name) {
        addItem(new HolidayInfo("", name));
    }

    private static void addItem(HolidayInfo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class HolidayInfo {
        public String id;
        public String name;
        public String iconName="";
        public String type;

        public HolidayInfo(String id, String content) {
            this(id, content, "");
        }

        public HolidayInfo(String id, String content, String iconName) {
            this.id = id;
            this.name = content;
            this.iconName = iconName;
        }

        public String getIconName() {
            return iconName;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
