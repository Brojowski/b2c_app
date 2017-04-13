package com.example.b2c_core;

/**
 * Created by alex on 4/6/17.
 */

public class Routes
{
    // Client to server.
    public class ToServer
    {
        public static final String DRAFT_COMPLETE = "draft_done";
        public static final String PLAY_TILE = "play_tile";
        public static final String SUGGEST = "suggest";
        public static final String JOIN_GAME = "join";
        public static final String SIGN_IN = "login";
        public static final String REQUEST_GAME_LIST = "get_games";
    }

    // Server to client.
    public class FromServer
    {
        public static final String BEGIN_DRAFT = "draft_start";
    }

}
