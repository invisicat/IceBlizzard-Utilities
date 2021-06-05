package dev.blizzardutils.example;

import com.google.common.collect.Lists;
import dev.blizzardutils.builder.ItemBuilder;
import dev.blizzardutils.string.StringUtils;
import dev.blizzardutils.task.kronos.KronosChain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class ExamplePlugin extends JavaPlugin implements Listener {


    private final List<String> lore = Arrays.asList("&eThis is a fire snowball", "&c1, 2, 3");
    private final Map<Enchantment, Integer> enchantMap = new ConcurrentHashMap<>();
    private KronosChain<Void> chainOne = new KronosChain<>();
    private KronosChain<Void> chainTwo = new KronosChain<>();
    private List<KronosChain<Void>> taskQueue = Collections.synchronizedList(Lists.newArrayList());


    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        long start = System.currentTimeMillis();
        if (!(sender instanceof Player)) {
            System.out.println("Only players can use this command");
            return true;
        }

        if (command.getName().equalsIgnoreCase("iceutilsexample")) {
            Player player = (Player) sender;
            if (!player.isOp()) {
                player.sendMessage(StringUtils.format("&cOnly OP Players can use this command!"));
                return true;
            }


            enchantMap.put(Enchantment.FIRE_ASPECT, 2);
            enchantMap.put(Enchantment.DURABILITY, 5);
            player.getInventory().addItem(ItemBuilder.Builder.getInstance().itemType(Material.SNOW_BALL).itemAmount(1).itemName(StringUtils.format("&6Snowball"))
                    .itemEnchant(enchantMap).itemLore(lore).buildWithEnchants());
            taskQueue.add(chainOne.createNewChain(null));
            taskQueue.add(chainTwo.createNewChain(null));
            if (!taskQueue.get(0).isCancelled()) {
                chainOne.runSync(() -> {
                    player.sendMessage("This task is running Sync");
                    for (int i = 0; i < 5; i++) {
                        player.sendMessage("Spam");
                    }
                }).whenCompleteSync((aVoid, throwable) -> {
                    taskQueue.get(0).cancel();
                    taskQueue.get(1).runSync(() -> player.sendMessage("New Chain was made!")).whenCompleteSync((aVoid1, throwable1) -> {
                        player.sendMessage("Task Queue is cancelled");
                        taskQueue.get(1).cancel();
                        taskQueue.clear();
                    });
                });
            }
            
            return true;
        }
        return true;
    }
}