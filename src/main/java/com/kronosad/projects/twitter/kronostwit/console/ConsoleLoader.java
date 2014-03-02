/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.console;

import com.kronosad.projects.twitter.kronostwit.checkers.UpdateInformation;
import com.kronosad.projects.twitter.kronostwit.data.DataDownloader;
import com.kronosad.projects.twitter.kronostwit.exceptions.PluginException;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.ResourceDownloader;
import com.kronosad.projects.twitter.kronostwit.gui.windows.Window;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowConsole;
import com.kronosad.projects.twitter.kronostwit.interfaces.IKronosPlugin;
import com.kronosad.projects.twitter.kronostwit.theme.ThemeRegistry;
import com.kronosad.projects.twitter.kronostwit.theme.ThemeUtils;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Does all tasks that need to be performed before any heavy code / javax / swing / awt code is triggered.
 * @author russjr08
 */
public class ConsoleLoader {
    public static UpdateInformation updater;
    public static WindowConsole console;
    public static ThemeRegistry themeReg;
    
    public static ArrayList<Window> windows = new ArrayList<Window>();
    public static ArrayList<IKronosPlugin> plugins = new ArrayList<IKronosPlugin>();
    private static ArrayList<String> alreadyLoaded = new ArrayList<String>();

    protected static PluginManager pm;

    
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, IllegalAccessException {
        updater = new UpdateInformation();
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "KronosTwit - Alpha");
        
        themeReg = new ThemeRegistry();

        updater.check();

        console = new WindowConsole();

        crawlPluginsDir();
        
        System.out.println("Initializing Themes!");
        
        
        ThemeUtils.initThemes();
        
        themeReg.getNewActive();


        
        
        System.out.println("Starting update check!");
        updater.check();
        System.out.println("Downloading Data in new Thread.");
        new Thread(){ // Need to download data here?
            @Override
            public void run(){
                boolean downloadData = DataDownloader.downloadData(); 
    
            }
        }.start();
        
        
        ConsoleMain.load(args);

            
        ResourceDownloader.downloadResources();

        
    }

    public static void crawlPluginsDir(){

        File pluginFolder = new File("plugins");
        if(!pluginFolder.exists()){
            pluginFolder.mkdir();
        }

        if(pluginFolder.listFiles() != null && pluginFolder.listFiles().length != 0){
            for(File file : pluginFolder.listFiles()){
                if(!alreadyLoaded.contains(file.getName())){
                    if(file.getName().endsWith(".jar")){
                        if(initPlugin(file.getName())){
                            alreadyLoaded.add(file.getName());
                        }
                    }
                }
            }
        }
    }

    private static boolean initPlugin(String fileName){
        System.out.println("Attempting plugin load of: " + fileName);
        boolean duplicate = false;
        pm = PluginManagerFactory.createPluginManager();

        File pluginsFolder = new File("plugins" + File.separator + fileName);
        if(!pluginsFolder.exists()){
            pluginsFolder.mkdirs();
        }

        pm.addPluginsFrom(pluginsFolder.toURI());

        System.out.println(pluginsFolder.getAbsolutePath());

        IKronosPlugin plugin = pm.getPlugin(IKronosPlugin.class);

        if(!plugins.isEmpty()){
            for(IKronosPlugin kronosPlugin : plugins){
                if(plugin.getName().equals(kronosPlugin.getName())){
                    duplicate = true;
                    throw new PluginException("Duplicate Plugin of " + plugin.getName() + " found!");
                }
            }
        }
        if(!duplicate){
            if(plugin.dependsOnSpecificVersion()){
                if(plugin.getKronosTwitVersion() == updater.getVersion().getVersionNumber()){
                    if(plugin.loadBeforeApp()){
                        plugins.add(plugin);
                        plugin.run();
                    }
                }else{
                    throw new PluginException(String.format("The plugin %s requires version %s of KronosTwit. It will not be loaded!",
                            plugin.getName(), plugin.getKronosTwitVersion()));
                }
            }else{
                if(plugin.loadBeforeApp()){
                    plugins.add(plugin);
                    plugin.run();
                }
            }
        }

        return !duplicate;

    }
    
    
    
}
