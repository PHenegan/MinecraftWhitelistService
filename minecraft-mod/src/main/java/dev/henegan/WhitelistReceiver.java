package dev.henegan;

import net.fabricmc.api.DedicatedServerModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

import dev.henegan.controller.SocketController;
import dev.henegan.controller.ThreadedSocketController;
import dev.henegan.controller.WhitelistController;

public class WhitelistReceiver implements DedicatedServerModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("whitelist-api-receiver");

		private SocketController whitelistSocket;

	@Override
	public void onInitializeServer() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Socket socket = new Socket();

		LOGGER.info("Whitelist receiver started");




		}
}