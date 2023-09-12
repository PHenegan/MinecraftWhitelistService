package dev.henegan;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Whitelist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

import dev.henegan.config.WhitelistConfig;
import dev.henegan.controller.SocketController;
import dev.henegan.controller.ThreadedSocketController;
import dev.henegan.controller.WhitelistController;
import dev.henegan.service.WhitelistService;
import dev.henegan.service.WhitelistServiceImpl;

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

		ObjectMapper mapper = new ObjectMapper();
		Optional<WhitelistConfig> config = Optional.empty();
		try {
			File configFile = new File("./config/whitelistapi.json");

			if (!configFile.createNewFile()) {
				config = Optional.ofNullable(mapper.readValue(configFile, WhitelistConfig.class));
			} else {
				config = Optional.of(defaultConfig());
			}

      config.ifPresent(whitelistConfig -> startController(whitelistConfig, mapper));

		} catch (IOException e) {
			LOGGER.error("unable to load or create config file");
			config = Optional.empty();
		}

		LOGGER.info("Whitelist receiver started");
	}

	private static WhitelistConfig defaultConfig() {
		return new WhitelistConfig("localhost", 9595);
	}

	private void startController(WhitelistConfig config, ObjectMapper mapper) {
		try {
			Socket socket = config.getSocket();
			Whitelist whitelist = MinecraftServer
			WhitelistService service = new WhitelistServiceImpl(, LOGGER);
			SocketController controller = new ThreadedSocketController(
					new WhitelistController(
							service, socket, mapper
					));

			controller.start();

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					controller.stop();
				} catch	(IOException e) {
					LOGGER.error("Unable to close socket.", e);
				}
			}));


		} catch (IOException e) {
			LOGGER.error("Socket communication error", e);
		}
	}
}