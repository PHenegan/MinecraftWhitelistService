package dev.henegan.service;

import com.mojang.authlib.GameProfile;

/**
 * A Service layer for adding and removing players from a server's Whitelist
 */
public interface WhitelistService {

  /**
   * Add the given player to the whitelist
   *
   * @param username the name of the player to add
   * @return true if the player was removed, false otherwise
   */
  boolean addToWhitelist(String username);

  /**
   * Remove the given player from the whitelist
   *
   * @param username the name of the player to remove
   * @return true if the player was removed, false otherwise
   */
  boolean removeFromWhitelist(String username);

}
