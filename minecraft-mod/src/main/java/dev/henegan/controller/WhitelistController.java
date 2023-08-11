package dev.henegan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import dev.henegan.controller.communication.UserCommandRequest;
import dev.henegan.service.WhitelistService;


/**
 * A Controller opening a connection over a socket, and listening to add or remove players
 * from a Minecraft server whitelist (using a {@code WhitelistService} object)
 */
public class WhitelistController implements SocketController {

  private final WhitelistService service;
  private final Socket socket;

  // IO for the socket communications
  private final PrintWriter socketOutput;
  private final BufferedReader socketInput;
  private final ObjectMapper jsonMapper;

  // In this case, this mod is acting as a client,
  // taking data sent over by a server and adding it to the whitelist
  // the mod is considered a client because the mod is dependent on the receiving end
  // being up, and ideally the receiving end could manage whitelisting for multiple servers at once

  public WhitelistController(WhitelistService service, Socket socket, ObjectMapper jsonMapper) {
    this.service = service;
    this.socket = socket;
    this.jsonMapper = jsonMapper;

    try {
      this.socketOutput = new PrintWriter(socket.getOutputStream());
      InputStreamReader inStream = new InputStreamReader(socket.getInputStream());
      this.socketInput = new BufferedReader(inStream);
    } catch (IOException e) {
      throw new IllegalStateException(String.format(
          "Could not initialize IO for socket at address %s:%d",
          socket.getInetAddress().getHostAddress(),
          socket.getPort()
          ));
    }
  }

  @Override
  public void start() throws IOException {
    while (socket.isConnected() && !socket.isClosed()) {
      String serializedRequest = socketInput.readLine();

      UserCommandRequest request = this.jsonMapper.readValue(
          serializedRequest,
          UserCommandRequest.class);

      boolean result = switch(request.command()) {
        case WHITELIST_ADD -> this.service.addToWhitelist(request.targetUser());
        case WHITELIST_REMOVE -> this.service.removeFromWhitelist(request.targetUser());
      };

      this.socketOutput.println(result);
    }
  }

  @Override
  public void stop() throws IOException {
    if (!this.socket.isClosed())
      this.socket.close();
  }
}
