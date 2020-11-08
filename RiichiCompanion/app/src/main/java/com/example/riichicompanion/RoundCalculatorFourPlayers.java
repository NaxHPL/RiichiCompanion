package com.example.riichicompanion;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class RoundCalculatorFourPlayers {

    public static void updateGameStateFromRon(Game game, Player loser, List<Pair<Player, HandScore>> winnerHandScorePairs, List<Player> playersDeclaredRiichi) {
        ArrayList<Player> winners = new ArrayList<>();
        boolean repeatRound = false;

        for (Pair<Player, HandScore> pair : winnerHandScorePairs) {
            Player winner = pair.first;
            ScoreEntry scoreEntry = Scoring.getScoreEntry(pair.second);

            if (winner.isDealer()) {
                loser.changeScoreBy(-scoreEntry.getDealerRon());
                winner.changeScoreBy(scoreEntry.getDealerRon());
                repeatRound = true;
            }
            else {
                loser.changeScoreBy(-scoreEntry.getNonDealerRon());
                winner.changeScoreBy(scoreEntry.getNonDealerRon());
            }

            playersDeclaredRiichi.remove(winner);
            winners.add(winner);
        }

        for (Player player : playersDeclaredRiichi) {
            player.changeScoreBy(-1000);
            game.incrementRiichiStickCount();
        }

        Player atamahaneWinner = Scoring.getAtamahaneWinner(loser, winners);

        atamahaneWinner.changeScoreBy(1000 * game.getRiichiStickCount());
        game.setRiichiStickCount(0);

        loser.changeScoreBy(-300 * game.getHonbaStickCount());
        atamahaneWinner.changeScoreBy(300 * game.getHonbaStickCount());

        if (repeatRound)
            game.incrementHonbaStickCounter();
        else {
            game.setHonbaStickCount(0);
            game.nextRound();
        }
    }

    public static void updateGameStateFromTsumo(Game game, Player winner, HandScore handScore, List<Player> playersDeclaredRiichi) {
        ScoreEntry scoreEntry = Scoring.getScoreEntry(handScore);
        boolean repeatRound = false;

        if (winner.isDealer()) {
            for (Player player : game.getPlayers()) {
                if (player != winner) {
                    player.changeScoreBy(-scoreEntry.getDealerTsumo());
                    winner.changeScoreBy(scoreEntry.getDealerTsumo());
                }
            }

            repeatRound = true;
        }
        else {
            for (Player player : game.getPlayers()) {
                if (player != winner) {
                    if (player.isDealer()) {
                        player.changeScoreBy(-(scoreEntry.getNonDealerTsumo().second + 100 * game.getHonbaStickCount()));
                        winner.changeScoreBy(scoreEntry.getNonDealerTsumo().second + 100 * game.getHonbaStickCount());
                    }
                    else {
                        player.changeScoreBy(-(scoreEntry.getNonDealerTsumo().first + 100 * game.getHonbaStickCount()));
                        winner.changeScoreBy(scoreEntry.getNonDealerTsumo().first + 100 * game.getHonbaStickCount());
                    }
                }
            }
        }

        for (Player player : playersDeclaredRiichi) {
            player.changeScoreBy(-1000);
            game.incrementRiichiStickCount();
        }

        winner.changeScoreBy(1000 * game.getRiichiStickCount());
        game.setRiichiStickCount(0);

        if (repeatRound)
            game.incrementHonbaStickCounter();
        else {
            game.setHonbaStickCount(0);
            game.nextRound();
        }
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
        else if (playersInTenpai.size() == 2) {
            for (Player player : game.getPlayers()) {
                if (playersInTenpai.contains(player))
                    player.changeScoreBy(1500);
                else
                    player.changeScoreBy(-1500);
            }
        }
        else if (playersInTenpai.size() == 3) {
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

        game.incrementHonbaStickCounter();

        if (!playersInTenpai.contains(game.getDealer()))
            game.nextRound();
    }

    public static void updateGameStateFromChombo(Game game, Player playerFailed) {
        ScoreEntry reverseMangan = Scoring.getReverseManganEntry();

        if (playerFailed.isDealer()) {
            for (Player player : game.getPlayers()) {
                if (player != playerFailed) {
                    player.changeScoreBy(-reverseMangan.getDealerTsumo());
                    playerFailed.changeScoreBy(reverseMangan.getDealerTsumo());
                }
            }

            return;
        }

        for (Player player : game.getPlayers()) {
            if (player != playerFailed) {
                if (player.isDealer()) {
                    player.changeScoreBy(-reverseMangan.getNonDealerTsumo().second);
                    playerFailed.changeScoreBy(reverseMangan.getNonDealerTsumo().second);
                }
                else {
                    player.changeScoreBy(-reverseMangan.getNonDealerTsumo().first);
                    playerFailed.changeScoreBy(reverseMangan.getNonDealerTsumo().first);
                }
            }
        }
    }
}
