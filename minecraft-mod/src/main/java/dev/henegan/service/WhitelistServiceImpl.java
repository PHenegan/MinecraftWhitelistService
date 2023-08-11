package dev.henegan.service;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.Whitelist;
import net.minecraft.server.WhitelistEntry;

import org.slf4j.Logger;

import java.io.IOException;

/**
 * Implementation of the service functionality which can add and remove players to a
 * Minecraft server's whitelist
 */

public class WhitelistServiceImpl implements WhitelistService {

  private final Whitelist whitelist;
  private final Logger logger;

  public WhitelistServiceImpl(Whitelist whitelist, Logger logger) {
    this.whitelist = whitelist;
    this.logger = logger;
  }


  @Override
  public boolean addToWhitelist(String username) {
    GameProfile profile = new GameProfile(null, username);
    // if the profile is already added, don't try to add them again
    if (whitelist.get(profile) != null) {
      return false;
    }

    this.whitelist.add(new WhitelistEntry(profile));
    try {
      this.whitelist.save();
    }
    catch (IOException e) {
      this.logger.error("Unable to update Whitelist due to IOException");
      return false;
    }

    this.logger.info(String.format("Added user '%s' to the whitelist", username));
    return true;
  }

  public boolean removeFromWhitelist(String username) {
    GameProfile profile = new GameProfile(null, username);
    if (this.whitelist.get(profile) == null) {
      return false;
    }

    this.whitelist.remove(profile);

    try {
      this.whitelist.save();
    }
    catch (IOException e) {
      this.logger.error("Unable to update Whitelist due to IOException");
      return false;
    }

    return true;
  }
}


