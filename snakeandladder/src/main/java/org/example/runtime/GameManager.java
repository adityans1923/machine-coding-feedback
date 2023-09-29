package org.example.runtime;

import lombok.Getter;
import lombok.Setter;
import org.example.setup.Action;
import org.example.setup.ActionType;
import org.example.setup.Board;

import java.util.*;

@Getter
@Setter
public class GameManager {
    Queue<Player> players;
    Board board;
    BoardConfig config;
    Admin admin;
    private int initialNumberOfPlayers;
    List<Move> moves;
    Player lastPlayer;
    int playerHasPlayed;
    public GameManager() {
        this.admin = new Admin(this);
    }


    public void play(int player, List<String> names) {
        if (player < 2) {
            System.out.println("We can not play with " + player + " players");
            return;
        }
        // initializing players
        this.initialNumberOfPlayers = player;
        this.players = new LinkedList<>(players);
        for (int i=0;i<this.initialNumberOfPlayers;i++) {
            this.players.add(new Player(names.get(i)));
        }
        this.moves = new ArrayList<>();
        // initializing board
        this.config = this.admin.getConfig();
        this.board = new Board(this.config);
        start();
    }

    private void start() {
        Scanner sc= new Scanner(System.in);
        int totalCell = this.board.getSize() * this.board.getSize();
        playerHasPlayed = 0;
        while(true) {
            Player player = this.players.poll();
            if (lastPlayer.getId().equals(player.getId())) {
                if (this.playerHasPlayed == 3) {
                    this.players.add(player);
                    this.playerHasPlayed = 0;
                    continue;
                }
                this.playerHasPlayed++;
            }
            System.out.println("Player " + player.getName() + " turn: (press any key) ");
            sc.nextByte();
            int dice = board.getDice().roll();
            System.out.println("Rolling dice for player :" + player.getName() + " Dice: " + dice);
            if (player.getCurrentCell() + dice <= totalCell) {
                Move moving = new Move(player, player.getCurrentCell(), player.getCurrentCell() + dice, ActionType.User);
                moves.add(moving);
                player.setCurrentCell(moving.getTo());
                System.out.println("Player " + player.getName() + " moved from " + moving.getFrom() + " to " + moving.getTo());
            } else {
                System.out.println("Invalid move: by " + player.getName() + " to " + player.getCurrentCell() + dice);
            }
            applyPostAction(player);
            if (player.getCurrentCell() == totalCell) {
                System.out.println("Player " + player.getName() + " won");
                break;
            }
            this.players.add(player);
        }
    }

    private void applyPostAction(Player player) {
        while (this.board.getActions().containsKey(player.getCurrentCell())) {
            Action action  = this.board.getActions().get(player.getCurrentCell());
            Move moving = new Move(player, player.getCurrentCell(), action.getToCell(), action.getType());
            moves.add(moving);
            System.out.println("Player " + player.getName() + " is now at " + player.getCurrentCell() + " due to action " + action.getType());
        }
    }

}
