/**
 * Jobs Plugin for Bukkit
 * Copyright (C) 2011 Zak Ford <zak.j.ford@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.zford.jobs;

import me.zford.jobs.commands.JobsCommands;
import me.zford.jobs.config.JobConfig;
import me.zford.jobs.config.JobsConfiguration;
import me.zford.jobs.config.ConfigManager;
import me.zford.jobs.listeners.JobsListener;
import me.zford.jobs.listeners.JobsPaymentListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class JobsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Jobs.setPermissionHandler(new PermissionHandler(this));
        
        Jobs.setPluginLogger(getLogger());
        
        Jobs.setDataFolder(getDataFolder());
        
        ConfigManager.registerJobsConfiguration(new JobsConfiguration(this));
        ConfigManager.registerJobConfig(new JobConfig(this));
        
        getCommand("jobs").setExecutor(new JobsCommands());
        
        Jobs.startup();
        
        // register the listeners
        getServer().getPluginManager().registerEvents(new JobsListener(this), this);
        getServer().getPluginManager().registerEvents(new JobsPaymentListener(this), this);
        
        // register economy
        Bukkit.getScheduler().runTask(this, new HookEconomyTask(this));
        
        // all loaded properly.
        Jobs.getPluginLogger().info("Plugin has been enabled succesfully.");
    }
    
    @Override
    public void onDisable() {
        Jobs.shutdown();
        Jobs.getPluginLogger().info("Plugin has been disabled succesfully.");
    }
}
