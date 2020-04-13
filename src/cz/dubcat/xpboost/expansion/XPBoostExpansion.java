package cz.dubcat.xpboost.expansion;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import cz.dubcat.xpboost.XPBoostMain;
import cz.dubcat.xpboost.api.Condition;
import cz.dubcat.xpboost.api.XPBoostAPI;
import cz.dubcat.xpboost.constructors.XPBoost;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class XPBoostExpansion extends PlaceholderExpansion {

    private XPBoostMain plugin;
    private Map<String, Condition> conditionPlaceholders = new HashMap<>();

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin(getPlugin()) != null;
    }

    @Override
    public boolean register() {
        if (!canRegister()) {
            return false;
        }
        
        plugin = (XPBoostMain) Bukkit.getPluginManager().getPlugin(getPlugin());
        if (plugin == null) {
            return false;
        }
        
        for(Condition c : Condition.values()) {
            conditionPlaceholders.put("hasboost_" + c.name().toLowerCase(), c);
        }

        return PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
    }

    @Override
    public String getAuthor() {
        return "milkwalk";
    }

    @Override
    public String getIdentifier() {
        return "xpboost";
    }

    @Override
    public String getPlugin() {
        return "XPBoost";
    }

    @Override
    public String getVersion() {
        return "1.1.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (p == null) {
            return "";
        }

        UUID uuid = p.getUniqueId();

        if (identifier.equals("hasboost")) {
            return XPBoostAPI.hasBoost(uuid) ? "yes" : "no";
        }

        if(conditionPlaceholders.containsKey(identifier)) {
            XPBoost boost = XPBoostAPI.getBoost(uuid);
            
            if(boost != null) {
                return boost.hasCondition(conditionPlaceholders.get(identifier)) ? "yes" : "no";
            } else {
                return "no";
            }
        }
        
        XPBoost xpb = XPBoostAPI.getBoost(uuid);
        if (xpb == null) {
            if (identifier.equals("boost_zero")) {
                return String.valueOf(0);
            }

            if (identifier.equals("timeleft_zero")) {
                return String.valueOf(0);
            }

            if (identifier.equals("boost_time_zero")) {
                return String.valueOf(0);
            }

            return "";
        }

        if (identifier.equals("boost_zero")) {
            return String.valueOf(xpb.getBoost());
        }

        if (identifier.equals("timeleft_zero")) {
            return String.valueOf(xpb.getTimeRemaining());
        }

        if (identifier.equals("boost_time_zero")) {
            return String.valueOf(xpb.getBoostTime());
        }

        if (identifier.equals("timeleft")) {
            return String.valueOf(xpb.getTimeRemaining());
        }

        if (identifier.equals("boost")) {
            return String.valueOf(xpb.getBoost());
        }

        if (identifier.equals("boost_time")) {
            return String.valueOf(xpb.getBoostTime());
        }

        return null;

    }
}
