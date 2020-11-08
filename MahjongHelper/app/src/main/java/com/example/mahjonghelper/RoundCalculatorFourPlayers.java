package com.example.mahjonghelper;

import java.util.List;

public class RoundCalculatorFourPlayers {

    public static void updateGameStateFromRon(Game game, Player loser, List<Player> winners, List<HandScore> handScores, List<Player> playersDeclaredRiichi) {

    }

    public static void updateGameStateFromTsumo(Game game, Player winner, HandScore handScore, List<Player> playersDeclaredRiichi) {

    }

    public static void updateGameStateFromRyuukyoku(Game game, List<Player> playersInTenpai, List<Player> playersDeclaredRiichi) {
        if (playersInTenpai.size() == 1) {
            for (Player player : game.getPlayers()) {
                if (playersInTenpai.contains(player))
                    player.changeScoreBy(3000);
                else
                    player.changeScoreBy(-1000);
            }
        }
        if (playersInTenpai.size() == 2) {
            for (Player player : game.getPlayers()) {
                if (playersInTenpai.contains(player))
                    player.changeScoreBy(1500);
                else
                    player.changeScoreBy(-1500);
            }
        }
        if (playersInTenpai.size() == 3) {
            for (Player player : game.getPlayers()) {
                if (playersInTenpai.contains(player))
                    player.changeScoreBy(1000);
                else
                    player.changeScoreBy(-3000);
            }
        }

        for (Player player : playersDeclaredRiichi) {
            player.changeScoreBy(-1000);
            game.incrementRiichiStickCount();
        }

        if (!playersInTenpai.contains(game.getDealer()))
            game.nextRound();

        game.incrementHonbaStickCounter();
    }

    public static void updateGameStateFromChombo(Game game, List<Player> playersFailed) {

    }
}
