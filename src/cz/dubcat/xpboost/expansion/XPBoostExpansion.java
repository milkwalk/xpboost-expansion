package cz.dubcat.xpboost.expansion;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import cz.dubcat.xpboost.XPBoostMain;
import cz.dubcat.xpboost.api.XPBoostAPI;
import cz.dubcat.xpboost.constructors.XPBoost;

public class XPBoostExpansion extends PlaceholderExpansion {

    private XPBoostMain plugin;

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
        return "XPboost";
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

        UUID id = p.getUniqueId();

        if (identifier.equals("hasboost")) {
            return XPBoostAPI.hasBoost(id) ? "yes" : "no";
        }

        XPBoost xpb = XPBoostAPI.getBoost(id);
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
