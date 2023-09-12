package dev.henegan.config;

import java.io.IOException;
import java.net.Socket;

public record WhitelistConfig(
    String hostname,
    int port
) {

  public Socket getSocket() throws IOException {
    return new Socket(hostname, port);
  }
}
