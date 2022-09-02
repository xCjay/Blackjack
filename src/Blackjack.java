import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import svu.csc213.Dialog;


public class Blackjack extends GraphicsProgram{


    private JButton playButton, hitButton, stayButton, quitButton;

    private GLabel bankLabel, wagerLabel, balanceLabel;


    private int wager = 0;
    private int balance = 10000;
    private int bank = 10000;



    private Deck deck;
    private GHand player;
    private GHand dealer;




    @Override
    public void init() {
        this.setBackground(Color.pink);

        deck = new Deck();
        dealer = new GHand(new Hand(deck, true));
        player = new GHand(new Hand(deck, false));


        playButton = new JButton("Play");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        quitButton = new JButton("Quit");



        add(playButton, SOUTH);
        add(hitButton, SOUTH);
        add(stayButton, SOUTH);
        add(quitButton, SOUTH);

        addActionListeners();

        bankLabel = new GLabel("Bank: " + bank);
        wagerLabel = new GLabel("Wager: " + wager);
        balanceLabel = new GLabel("Balance: " + balance);

        add(bankLabel, 5, 10);
        add(wagerLabel, 5, 30);
        add(balanceLabel, 5, 50);
        }

    @Override
    public void run() {
        this.getMenuBar().setVisible(false);



        System.out.println(player.getTotal());
        System.out.println(dealer.getTotal());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()){

            case "Play":
                play();
                break;

            case "Hit":
                hit();
                break;

            case "Stay":
                stay();
                break;

            case "Quit":
                System.exit(0);
                break;

        }
    }



    private void wager() {
        wager = Dialog.getInteger("How much would you like to Wager?");
        while (wager > balance){
            Dialog.showMessage("Invalid Amount, Enter another amount");
            wager = Dialog.getInteger("How much would you like to Wager?");
        }
        wagerLabel.setLabel("Wager: " + wager);

    }

    private void play() {
        wager();

        add(player, 50, getHeight()-player.getHeight());
        add(dealer, 50, dealer.getHeight());
        checkDealerWin();
        checkWin();
    }

    private void hit() {
        dealer.hit();
        player.hit();

        checkDealerWin();
        checkWin();

        System.out.println(player.getTotal());
        System.out.println(dealer.getTotal());
    }

    private void stay() {
        dealer.hit();
        checkDealerWin();
        checkWin();

        System.out.println(player.getTotal());
        System.out.println(dealer.getTotal());
    }

    private void checkWin() {
        if (player.getTotal() > 21){
            Dialog.showMessage("Bust");
            dealerWin();
        } else if (player.getTotal() == 21) {
            playerWin();
        }
    }

    private void checkDealerWin(){
        if (dealer.getTotal() > 21){
            Dialog.showMessage("Win");
            playerWin();
        } else if (player.getTotal() == 21){
            dealerWin();
        }
    }

    private void playerWin(){
        balance += wager;
        bank -= wager;
        wager = 0;
        refreshLabels();
    }

    private void dealerWin(){
        balance -= wager;
        bank += wager;
        wager = 0;
        refreshLabels();
    }

    private void refreshLabels() {
        bankLabel.setLabel("Bank: " + bank);
        wagerLabel.setLabel("Wager: " + wager);
        balanceLabel.setLabel("Balance: " + balance);
    }

    public static void main(String[] args) {
        new Blackjack().start();
    }
}
