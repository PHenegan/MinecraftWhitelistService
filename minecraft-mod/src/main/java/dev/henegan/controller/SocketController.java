package dev.henegan.controller;

import java.io.IOException;

/**
 * Represents a Controller which communicates over a socket using event-based communication
 */
public interface SocketController {

  /**
   * Start a listener which opens a connection over a socket
   */
  void start() throws IOException;

  /**
   * Close the connection over the socket
   */
  void stop() throws IOException;

}
