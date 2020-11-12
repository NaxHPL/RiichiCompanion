package com.example.riichicompanion;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class RoundCalculator {

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

        atamahaneWinner.changeScoreBy(1000 * game.getRiichiCount());
        game.setRiichiCount(0);

        loser.changeScoreBy(-game.getHonbaValue() * game.getHonbaCount());
        atamahaneWinner.changeScoreBy(game.getHonbaValue() * game.getHonbaCount());

        if (repeatRound)
            game.incrementHonbaCount();
        else {
            game.setHonbaCount(0);
            game.nextRound();
        }
    }

    private static void updateGameStateFromTsumo(Game game, Player winner, HandScore handScore, List<Player> playersDeclaredRiichi) {
        ScoreEntry scoreEntry = Scoring.getScoreEntry(handScore);
        boolean repeatRound = false;

        if (winner.isDealer()) {
            for (Player player : game.getPlayers()) {
                if (player != winner) {
                    player.changeScoreBy(-(scoreEntry.getDealerTsumo() + 100 * game.getHonbaCount()));
                    winner.changeScoreBy(scoreEntry.getDealerTsumo() + 100 * game.getHonbaCount());

                    if (game.getNumberOfPlayers() == 3 && !game.usesTsumoLoss()) {
                        player.changeScoreBy(-roundUpToNearest100(scoreEntry.getDealerTsumo() / 2));
                        winner.changeScoreBy(roundUpToNearest100(scoreEntry.getDealerTsumo() / 2));
                    }
                }
            }

            repeatRound = true;
        }
        else {
            for (Player player : game.getPlayers()) {
                if (player != winner) {
                    if (player.isDealer()) {
                        player.changeScoreBy(-(scoreEntry.getNonDealerTsumo().second + 100 * game.getHonbaCount()));
                        winner.changeScoreBy(scoreEntry.getNonDealerTsumo().second + 100 * game.getHonbaCount());
                    }
                    else {
                        player.changeScoreBy(-(scoreEntry.getNonDealerTsumo().first + 100 * game.getHonbaCount()));
                        winner.changeScoreBy(scoreEntry.getNonDealerTsumo().first + 100 * game.getHonbaCount());
                    }

                    if (game.getNumberOfPlayers() == 3 && !game.usesTsumoLoss()) {
                        player.changeScoreBy(-roundUpToNearest100(scoreEntry.getNonDealerTsumo().first / 2));
                        winner.changeScoreBy(roundUpToNearest100(scoreEntry.getNonDealerTsumo().first / 2));
                    }
                }
            }
        }

        for (Player player : playersDeclaredRiichi) {
            player.changeScoreBy(-1000);
            game.incrementRiichiStickCount();
        }

        winner.changeScoreBy(1000 * game.getRiichiCount());
        game.setRiichiCount(0);

        if (repeatRound)
            game.incrementHonbaCount();
        else {
            game.setHonbaCount(0);
            game.nextRound();
        }
    }

    public static void updateGameStateFromRyuukyoku(Game game, List<Player> playersInTenpai, List<Player> playersDeclaredRiichi) {
        if (game.getNumberOfPlayers() == 3)
            updateGameRyuukyoku3Players(game, playersInTenpai, playersDeclaredRiichi);
        else
            updateGameRyuukyoku4Players(game, playersInTenpai, playersDeclaredRiichi);
    }

    public static void updateGameStateFromChombo(Game game, Player playerFailed) {
        ScoreEntry mangan = Scoring.getManganEntry();

        if (playerFailed.isDealer()) {
            for (Player player : game.getPlayers()) {
                if (player != playerFailed) {
                    playerFailed.changeScoreBy(-mangan.getDealerTsumo());
                    player.changeScoreBy(mangan.getDealerTsumo());

                    if (game.getNumberOfPlayers() == 3 && !game.usesTsumoLoss()) {
                        playerFailed.changeScoreBy(-roundUpToNearest100(mangan.getDealerTsumo() / 2));
                        player.changeScoreBy(roundUpToNearest100(mangan.getDealerTsumo() / 2));
                    }
                }
            }

            return;
        }

        for (Player player : game.getPlayers()) {
            if (player != playerFailed) {
                if (player.isDealer()) {
                    playerFailed.changeScoreBy(-mangan.getNonDealerTsumo().second);
                    player.changeScoreBy(mangan.getNonDealerTsumo().second);
                }
                else {
                    playerFailed.changeScoreBy(-mangan.getNonDealerTsumo().first);
                    player.changeScoreBy(mangan.getNonDealerTsumo().first);
                }

                if (game.getNumberOfPlayers() == 3 && !game.usesTsumoLoss()) {
                    playerFailed.changeScoreBy(-roundUpToNearest100(mangan.getNonDealerTsumo().first / 2));
                    player.changeScoreBy(roundUpToNearest100(mangan.getNonDealerTsumo().first / 2));
                }
            }
        }
    }

    private static void updateGameRyuukyoku3Players(Game game, List<Player> playersInTenpai, List<Player> playersDeclaredRiichi) {
        if (playersInTenpai.size() == 1) {
            for (Player player : game.getPlayers()) {
                if (playersInTenpai.contains(player))
                    player.changeScoreBy(2000);
                else
                    player.changeScoreBy(-1000);
            }
        }
        else if (playersInTenpai.size() == 2) {
            for (Player player : game.getPlayers()) {
                if (playersInTenpai.contains(player))
                    player.changeScoreBy(1000);
                else
                    player.changeScoreBy(-2000);
            }
        }

        for (Player player : playersDeclaredRiichi) {
            player.changeScoreBy(-1000);
            game.incrementRiichiStickCount();
        }

        game.incrementHonbaCount();

        if (!playersInTenpai.contains(game.getDealer()))
            game.nextRound();
    }

    private static void updateGameRyuukyoku4Players(Game game, List<Player> playersInTenpai, List<Player> playersDeclaredRiichi) {
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

        game.incrementHonbaCount();

        if (!playersInTenpai.contains(game.getDealer()))
            game.nextRound();
    }

    private static int roundUpToNearest100(int x) {
        return ((x + 99) / 100 ) * 100;
    }
}
