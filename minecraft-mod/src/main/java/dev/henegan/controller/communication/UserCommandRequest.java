package dev.henegan.controller.communication;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A request for a command acting upon the given user
 * @param targetUser the user being targeted by the command
 * @param command the type of command applied on the user
 */
public record UserCommandRequest(
    @JsonProperty
    String targetUser,
    @JsonProperty
    RequestCommandType command) {
}
