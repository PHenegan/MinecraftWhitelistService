package dev.henegan.controller;

import java.io.IOException;

/**
 * A Wrapper class for a Socket Controller which runs the socket communication in a
 * separate thread so that the controller can be run asynchronously.
 */
public class ThreadedSocketController implements SocketController {
  private final SocketController delegate;

  public ThreadedSocketController(SocketController delegate) {
    this.delegate = delegate;
  }

  @Override
  public void start() throws IOException {
  }

  @Override
  public void stop() throws IOException {

  }
}
