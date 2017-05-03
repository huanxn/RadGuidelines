package com.radicalpeas.radguidelines.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent
{

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static
    {
        // Add some sample items.
    /*
        for (int i = 1; i <= COUNT; i++)
        {
            addItem(createDummyItem(i));
        }
        */

        addItem(new DummyItem("1", "Lung", "Amphoteric is the property of certain substances of acting either as acids or as bases depending on the reaction in which they are involved"));
        addItem(new DummyItem("2", "Kidney", "Anion is a negatively charged ion"));
        addItem(new DummyItem("3", "Liver", "Anode is a positively charged ion"));
        addItem(new DummyItem("4", "Adrenal Gland", "Amphoteric is the property of certain substances of acting either as acids or as bases depending on the reaction in which they are involved"));
        addItem(new DummyItem("5", "Pancreas", "Anion is a negatively charged ion"));
        addItem(new DummyItem("6", "Adnexa", "Anode is a positively charged ion"));
        addItem(new DummyItem("7", "Vascular", "Amphoteric is the property of certain substances of acting either as acids or as bases depending on the reaction in which they are involved"));
        addItem(new DummyItem("8", "Spleen", "Anion is a negatively charged ion"));
        addItem(new DummyItem("9", "Lymph Node", "Anode is a positively charged ion"));
        addItem(new DummyItem("10", "Gallbladder", "Anion is a negatively charged ion"));
        addItem(new DummyItem("11", "Thyroid", "Anode is a positively charged ion"));
        addItem(new DummyItem("12", "Prostate", "Anion is a negatively charged ion"));

    }

    private static void addItem(DummyItem item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position)
    {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++)
        {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem
    {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details)
        {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString()
        {
            return content;
        }
    }
}
