package work.xeltica.craft.core.stores;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class ItemStore {
    public static final String ITEM_NAME_XPHONE = "xphone";

    public ItemStore() {
        ItemStore.instance = this;
        registerItems();
    }

    public static ItemStore getInstance() {
        return instance;
    }

    /**
     * 代わりに getItem(ItemStore.ITEM_NAME_XPHONE) を使ってください
     */
    @Deprecated(forRemoval = true)
    public ItemStack getXPhone() {
        return customItems.get(ITEM_NAME_XPHONE);
    }

    public ItemStack getItem(String key) {
        return customItems.get(key).clone();
    }

    /**
     * カスタムアイテムを作成
     */
    public ItemStack createCustomItem(String name, String... lore) {
        var st = new ItemStack(Material.WRITTEN_BOOK);

        st.editMeta(meta -> {
            meta.displayName(
                Component.text(name).style(Style.style(TextColor.color(37, 113, 255), TextDecoration.BOLD))
            );
            meta.lore(Stream.of(lore).map(s -> Component.text(s).asComponent()).toList());
        });
        return st;
    }

    /**
     * カスタムアイテムの比較
     */
    public boolean compareCustomItem(ItemStack stack1, ItemStack stack2) {
        // -- どっちもnull
        if (stack1 == null && stack2 == null) return true;

        // -- どっちかがnull
        if (stack1 == null || stack2 == null) return false;

        // -- 種類が違う
        if (stack1.getType() != stack2.getType()) return false;

        var meta1 = stack1.getItemMeta();
        var meta2 = stack2.getItemMeta();

        // -- 名前の比較
        var name1 = PlainTextComponentSerializer.plainText().serialize(meta1.displayName());
        var name2 = PlainTextComponentSerializer.plainText().serialize(meta2.displayName());
        if (!name1.equals(name2)) return false;
        
        // -- lore の比較
        var lore1 = meta1.lore().stream().map(c -> PlainTextComponentSerializer.plainText().serialize(c)).toList();
        var lore2 = meta2.lore().stream().map(c -> PlainTextComponentSerializer.plainText().serialize(c)).toList();
        if (!lore1.equals(lore2)) return false;

        return true;
    }

    private void registerItems() {
        customItems.put(ITEM_NAME_XPHONE, createCustomItem("X Phone", "XelticaMCの独自機能にアクセスできるスマホ。"));
    }
    
    private final Map<String, ItemStack> customItems = new HashMap<>();
    private static ItemStore instance;
}
